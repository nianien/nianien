package com.nianien.core.util;

/**
 * 基本类型转字节数组
 * 
 * @author skyfalling
 */
public class ByteUtils {

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
