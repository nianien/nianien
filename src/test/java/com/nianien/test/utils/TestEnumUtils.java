package com.nianien.test.utils;

import com.nianien.core.util.EnumUtils;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author skyfalling
 */
public class TestEnumUtils {

    static enum Goat {
        A('A'), B('B'), C('C'), D('D'), E('E');
        int value;

        Goat(int v) {
            this.value = v;
        }
    }

    @Test
    public void test() {
        assert EnumUtils.find(TimeUnit.class, "C0", 1) == null;
        assert EnumUtils.find(Goat.class, "value", (int) 'A') == Goat.A;
    }
}
