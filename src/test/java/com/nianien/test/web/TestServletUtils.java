package com.nianien.test.web;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.nianien.core.web.ServletUtils;
import com.nianien.test.bean.CXString;
import com.nianien.test.bean.DemoBean;

public class TestServletUtils {

    @Test
    public void test() {
        DemoBean bean = new DemoBean();
        Map<String, String[]> map = new HashMap<String, String[]>();
        map.put("names", new String[]{"a", "b", "c"});
        map.put("ages", new String[]{"1", "2", "3"});
        ServletUtils.setValue(bean, map);

        for (String s : bean.getNames()) {
            System.out.println(s);
        }
        for (CXString<Integer> s : bean.getAges()) {
            System.out.println(s);
        }
    }

}
