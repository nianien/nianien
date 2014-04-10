package com.nianien.test.map;

import com.nianien.core.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author skyfalling
 */
public class TestMapWrapper {

    public static void main(String[] args) {

        Map map=new HashMap();
        map.put("1",2);
        map.put("2",null);
        CollectionUtils.removeNull(map);
        System.out.println(map);
    }
}
