package com.nianien.core.util;

import com.nianien.core.exception.ExceptionHandler;
import com.nianien.core.io.Closer;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5值生成器, 用于计算文件、文本或字节的MD5值
 *
 * @author skyfalling
 */
public class MD5Generator {

	private ThreadLocal<MessageDigest> local = new ThreadLocal<MessageDigest>() {
		@Override
		protected MessageDigest initialValue() {
			return md5Instance();
		}
	};


	/**
	 * 计算文件的MD5值
	 *
	 * @param file
	 * @return
	 */
	public byte[] md5(File file) {
		InputStream fis = null;
		try {
			fis = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int numRead;
			MessageDigest md5 = local.get();
			md5.reset();
			while ((numRead = fis.read(buffer)) > 0) {
				md5.update(buffer, 0, numRead);
			}
			return md5.digest();
		} catch (Exception e) {
			throw  ExceptionHandler.throwException(e);
		} finally {
			Closer.close(fis);
		}
	}

	/**
	 * 计算文本内容的MD5值
	 *
	 * @param content
	 * @return
	 */
	public byte[] md5(String content) {
		return md5(content.getBytes());
	}

	/**
	 * 计算字节数组的MD5值
	 *
	 * @param bytes
	 * @return
	 */
	public byte[] md5(byte[] bytes) {
		MessageDigest md5 = local.get();
		md5.update(bytes);
		return md5.digest();
	}

	/**
	 * 计算文件的MD5值
	 *
	 * @param file
	 * @return
	 */
	public String md5ToString(File file) {
		return toString(md5(file));
	}

	/**
	 * 计算文本内容的MD5值
	 *
	 * @param content
	 * @return
	 */
	public String md5ToString(String content) {
		return toString(md5(content));
	}

	/**
	 * 计算字节数组的MD5值
	 *
	 * @param bytes
	 * @return
	 */
	public String md5ToString(byte[] bytes) {
		return toString(md5(bytes));
	}

	/**
	 * 将字节数组转化成十六进制字符串
	 *
	 * @param bytes
	 * @return
	 */
	private static String toString(byte[] bytes) {
		char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			// 四位的十六进制数表示一个字节
			sb.append(hexChar[(bytes[i] & 0xf0) >>> 4]);
			sb.append(hexChar[bytes[i] & 0x0f]);
		}
		return sb.toString();
	}

	/**
	 * 获取MD5实例
	 *
	 * @return
	 */
	public static MessageDigest md5Instance() {
		try {
			return MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw  ExceptionHandler.throwException(e);
		}
	}
}
