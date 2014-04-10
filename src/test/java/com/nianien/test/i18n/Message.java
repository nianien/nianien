package com.nianien.test.i18n;

import com.nianien.core.i18n.ClassResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: lining05
 * Date: 2013-03-05
 */
public class Message extends ClassResourceBundle {


    public final static I18N<String> hello = new I18N<String>("你好 {name}!");

    private final static String Global_Button_Confirm = "確定";

    public class Global {
        public final static String error = "错误";
        public final static String message = "消息";
        public final static String tips = "提示";

        public class Button {
            public final static String confirm = "确定";
            public final static String cancel = "取消";
            public final static String yes = "是";
            public final static String no = "否";
        }
    }


    @Override
    protected Object handleGetObject(String key) {
        return super.handleGetObject(key.replace('-', '_'));
    }
}
