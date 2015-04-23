package com.nianien.test.utils;

import com.nianien.core.util.ByteUtils;
import org.junit.Test;

/**
 * @author skyfalling.
 */
public class TestByteUtils {

    @Test
    public void test() {
        assert ByteUtils.byte2Hex("中国".getBytes()).equals("d6d0b9fa");
        assert new String(ByteUtils.hex2Byte("D6D0B9FA".toUpperCase())).equals("中国");
    }
}
