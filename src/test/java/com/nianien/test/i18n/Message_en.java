package com.nianien.test.i18n;

/**
 * Created with IntelliJ IDEA.
 * User: lining05
 * Date: 2013-03-05
 */
public class Message_en extends Message {
    public final static String hello ="Hello, {name}!";

    public  class Global {
        public final static String error = "error";
        public final static String message = "message";
        public final static String tips = "tips";

        public  class Button {
            public final static String confirm = "confirm";
            public final static String cancel = "cancel";
            public final static String yes = "yes";
            public final static String no = "no";
        }
    }
}
