package com.nianien.core.util;

import com.nianien.core.exception.ExceptionHandler;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 发送邮件的工具类,不支持附件功能
 * 
 * @author skyfalling
 */
public class EmailSender {

	/**
	 * 邮件会话
	 */
	private Session session;

	/**
	 * 构造方法<br>
	 * 
	 * @param user
	 *            用户名
	 * @param password
	 *            密码,不需要密码时置为null
	 */
	public EmailSender(String user, String password) {
		int index = user.indexOf('@');
		String host = "smtp." + user.substring(index + 1);
		this.init(user, password, host);
	}

	/**
	 * 构造方法<br>
	 * 
	 * @param user
	 *            用户名
	 * @param password
	 *            密码,不需要密码时置为null
	 * @param host
	 *            邮件服务器的主机地址
	 */
	public EmailSender(String user, String password, String host) {
		this.init(user, password, host);
	}

	/**
	 * 配置Session信息
	 * 
	 * @param user
	 *            用户
	 * @param password
	 *            密码
	 * @param host
	 *            主机地址
	 */
	public Session init(final String user, final String password,
			final String host) {
		boolean needAuth = password != null && !password.isEmpty();
		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", needAuth + "");
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", host);
		props.setProperty("mail.smtp.connectiontimeout", "5000");
		props.setProperty("mail.smtp.timeout", "5000");
		props.setProperty("mail.from", user);
		props.setProperty("mail.user", user);
		session = Session.getDefaultInstance(props,
				needAuth ? new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(user, password);
					}
				}
						: null);
		session.setDebug(true);
		return session;
	}

	/**
	 * 是否启用调试信息
	 * 
	 * @param debug
	 */
	public void setDebug(boolean debug) {
		this.session.setDebug(debug);
	}

	/**
	 * 发送HTML邮件
	 * 
	 * @param toAddresses
	 *            收件人地址列表
	 * @param subject
	 *            主题
	 * @param content
	 *            邮件内容
	 */
	public void sendHtml(String[] toAddresses, String subject, String content) {
		send(toAddresses, subject, content, "text/html;charset=utf-8;");
	}

	/**
	 * 发送文本邮件
	 * 
	 * @param toAddresses
	 *            收件人地址列表
	 * @param subject
	 *            主题
	 * @param content
	 *            邮件内容
	 */
	public void sendText(String[] toAddresses, String subject, String content) {
		send(toAddresses, subject, content, "text/plain;charset=utf-8;");
	}

	/**
	 * 发送指定类型的邮件
	 * 
	 * @param toAddresses
	 * @param subject
	 * @param content
	 */
	protected void send(String[] toAddresses, String subject, String content,
			String contentType) {
		try {
			List<InternetAddress> list = new ArrayList<InternetAddress>(
					toAddresses.length);
			for (int i = 0; i < toAddresses.length; i++) {
				try {
					list.add(new InternetAddress(toAddresses[i]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Message msg = new MimeMessage(this.session);
			msg.setRecipients(Message.RecipientType.TO,
					list.toArray(new InternetAddress[0]));
			msg.setContent(content, contentType);
			msg.setSubject(subject);
			Transport.send(msg);
		} catch (MessagingException e) {
			ExceptionHandler.throwException(e);
		}
	}

}
