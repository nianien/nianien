package com.nianien.test;

import com.nianien.core.util.EmailSender;
import org.junit.Test;

/**
 * @author skyfalling
 */
public class TestEmailSender {

    @Test
    public void test() throws Exception {
        EmailSender sender = new EmailSender("nianien@126.com", "fighting!");
        sender.sendHtml(new String[]{"lining05@baidu.com"},
                "新一轮测试", "<div style='color:red'>你看看效果如何</div>");
    }
}
