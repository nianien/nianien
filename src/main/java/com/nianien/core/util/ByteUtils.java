package com.nianien.core.util;

/**
 * 基本类型转字节数组
 *
 * @author skyfalling
 */
public class ByteUtils {

    /**
     * 将字节数组转化成十六进制字符串
     *
     * @param bytes
     * @return
     */
    public static String toString(byte[] bytes) {
        char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            // 四位的十六进制数表示一个字节
            sb.append(hexChar[(bytes[i] & 0xf0) >>> 4]);
            sb.append(hexChar[bytes[i] & 0x0f]);
        }
        return sb.toString();
    }

    /**
     * char型转字节数组,高位在前,低位在后
     *
     * @param ch
     * @return
     */
    public static byte[] getBytes(char ch) {
        return getBytes(ch, 2);
    }

    /**
     * short型转字节数组,高位在前,低位在后
     *
     * @param s
     * @return
     */
    public static byte[] getBytes(short s) {
        return getBytes(s, 2);
    }

    /**
     * int型转字节数组,高位在前,低位在后
     *
     * @param i
     * @return
     */
    public static byte[] getBytes(int i) {
        return getBytes(i, 4);
    }

    /**
     * long型转字节数组,高位在前,低位在后
     *
     * @param l
     * @return
     */
    public static byte[] getBytes(long l) {
        return getBytes(l, 8);
    }


    /**
     * 将long类型数字转换成bytes字节数的数组
     *
     * @param l
     * @param bytes
     * @return
     */
    private static byte[] getBytes(long l, int bytes) {
        byte[] result = new byte[bytes];
        for (int i = 0; i < bytes; i++) {
            result[bytes - i - 1] = (byte) ((l >> (8 * i)) & get(bytes));
        }
        return result;
    }

    /**
     * 字节数为bytes,bit位值全为1,对应的十进制数
     *
     * @param bytes
     * @return
     */
    private static long get(int bytes) {
        int n = bytes / 2;
        long s = 1;
        for (int i = 0; i < n; i++)
            s *= 16;
        return s - 1;
    }

}
