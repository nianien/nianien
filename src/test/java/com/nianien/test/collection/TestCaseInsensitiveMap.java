package com.nianien.test.collection;


import com.nianien.core.collection.map.CaseInsensitiveMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lining05
 * Date: 2013-01-29
 */
public class TestCaseInsensitiveMap {
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("a","a");
        CaseInsensitiveMap cmap = new CaseInsensitiveMap(map);
//        cmap.putAll(map);
        System.out.println(cmap.containsKey("A"));
    }

    public String toString() {
        return this.getClass().toString();
    }

    public static void test() {
        TestCaseInsensitiveMap test1 = new TestCaseInsensitiveMap();
        TestCaseInsensitiveMap test = new TestCaseInsensitiveMap();
        CaseInsensitiveMap map = new CaseInsensitiveMap();
        map.put("Abc", 123);
        map.put(test, 123);
        System.out.println("==>test=" + map.get(test1));
        System.out.println(map);
        com.nianien.core.collection.map.CaseInsensitiveMap caseInsensitiveMap = new com.nianien.core.collection.map.CaseInsensitiveMap();
        caseInsensitiveMap.put("Abc", 123);
        caseInsensitiveMap.put(test, 123);
        System.out.println("==>test=" + caseInsensitiveMap.get(test1));
        System.out.println(caseInsensitiveMap);
    }
}
