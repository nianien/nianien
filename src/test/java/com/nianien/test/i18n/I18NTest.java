package com.nianien.test.i18n;


import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: lining05
 * Date: 2013-03-05
 */
public class I18NTest {


    @Test
    public void test() {
        String name = Message.class.getName();
        ResourceBundle resourceBundle;
        resourceBundle = ResourceBundle.getBundle(name, Locale.ENGLISH);
        assertThat(resourceBundle.getString(Message.hello.name()), equalTo("Hello, {name}!"));
        assertThat(resourceBundle.getString("Global.Button.confirm"), equalTo("confirm"));
        assertThat(resourceBundle.getString("Global-Button-Confirm"), equalTo("確定"));
        assertThat(resourceBundle.getString("Global_Button_Confirm"), equalTo("確定"));
        System.out.println(resourceBundle.keySet());
        resourceBundle = ResourceBundle.getBundle(name, Locale.TAIWAN);
        assertThat(resourceBundle.getString(Message.hello.name()), equalTo("你好 {name}!"));
        assertThat(resourceBundle.getString("Global.Button.confirm"), equalTo("確定"));
        assertThat(resourceBundle.getString("Global-Button-Confirm"), equalTo("確定"));
        assertThat(resourceBundle.getString("Global_Button_Confirm"), equalTo("確定"));
        System.out.println(resourceBundle.keySet());
    }

}
