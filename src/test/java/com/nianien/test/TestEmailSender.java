package com.nianien.test;

import com.nianien.core.util.EmailSender;
import org.junit.Test;

/**
 * @author skyfalling
 */
public class TestEmailSender {

    @Test
    public void test() throws Exception {
        new EmailSender("nianien@126.com", "fighting!").subject("邮件发送升级测试2").content("<div style='color:red'>你看看效果如何</div>", "text/html;charset=utf-8;").to("lining05@baidu.com").cc("lining05@baidu.com").send();
    }

}
