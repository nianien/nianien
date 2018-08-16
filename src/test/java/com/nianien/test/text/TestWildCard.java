package com.nianien.test.text;

import com.nianien.core.text.Wildcard;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author scorpio
 * @version 1.0.0
 * @email tengzhe.ln@alibaba-inc.com
 */
public class TestWildCard {

    
    @Test
    public void test(){
        Assert.assertTrue(Wildcard.match("ab?", "abc"));
        Assert.assertTrue(Wildcard.match("ab*", "abc"));
        Assert.assertTrue(Wildcard.matchPath("ab/*/f/**", "ab/c/d/f"));
    }

}
