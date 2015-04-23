package com.nianien.core.util;

import java.io.ByteArrayOutputStream;

/**
 * 基本类型转字节数组
 *
 * @author skyfalling
 */
public class ByteUtils {

    private static final String hexString = "0123456789abcdef";

    /**
     * 将字节数组转成编码成16进制数字
     *
     * @param bytes
     * @return
     */
    public static String byte2Hex(byte[] bytes) {
        char[] buffer = new char[bytes.length * 2];
        for (int i = 0, j = 0; i < bytes.length; ++i) {
            int u = unsigned(bytes[i]);
            buffer[j++] = hexString.charAt(u >>> 4);
            buffer[j++] = hexString.charAt(u & 0xf);
        }
        return new String(buffer);
    }


    /**
     * 将16进制数字二进制数组
     *
     * @param hex 十六进制字符串
     * @return
     */
    public static byte[] hex2Byte(String hex) {
        String lowerCase = hex.toLowerCase();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(lowerCase.length() / 2);
        // 将每2位16进制整数组装成一个字节
        for (int i = 0; i < lowerCase.length(); i += 2)
            baos.write(
                    hexString.indexOf(lowerCase.charAt(i)) << 4
                            | hexString.indexOf(lowerCase.charAt(i + 1))
            );
        return baos.toByteArray();
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

    public static int unsigned(byte b) {
        return b < 0 ? b + 256 : b;
    }

}
