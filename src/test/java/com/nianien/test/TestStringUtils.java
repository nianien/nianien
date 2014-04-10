package com.nianien.test;

import com.nianien.core.util.ArrayUtils;
import com.nianien.core.util.StringUtils;

import java.beans.Expression;
import java.util.Arrays;

/**
 * @author skyfalling
 */
public class TestStringUtils {

    public static void main(String[] args) throws Exception {
        System.out.println(StringUtils.lefPad("a", 3, 'c'));
        System.out.println(StringUtils.lefPad("!", 6, "abc"));
        System.out.println(StringUtils.fill("?a?b?c???", '?', "1", "2","3"));
        System.out.println(StringUtils.fill("a?b?c?", '?', "1", "2","3","4"));
        System.out.println(StringUtils.fill("a??b??c??","??", "1", "2","3","4"));
        System.out.println(StringUtils.fill("a??b??????c??","???", "1", "2"));


        System.out.println(ArrayUtils.toString(Arrays.asList("1","2","3").toArray(),"|"));
        Expression expression = new Expression("abc", "toCharArray", null);
        System.out.println(expression.getValue());
    }
}
