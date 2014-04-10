package com.nianien.core.text;

import com.nianien.core.util.StringUtils;

/**
 * 针对文本提供编码解码操作的工具类
 *
 * @author skyfalling
 */
public class Encryptor {

    /**
     * 默认字符集合[0-9A-Za-z]
     */
    public final static char[] defaultCharset = Characters.NumberAndLetter;

    /**
     * 采用默认的字符集进行编码<br>
     * 这里默认字符集合[0-9A-Za-z]
     *
     * @param source
     * @return 编码后的字符串
     */
    public static String encrypt(String source) {
        return encrypt(source, defaultCharset);
    }

    /**
     * 采用指定的字符集进行编码
     *
     * @param source
     * @param charset 编码所使用的字符集
     * @return 编码后的字符串
     */
    public static String encrypt(String source, char[] charset) {
        StringBuilder sb = new StringBuilder();
        for (char c : source.toCharArray()) {
            String str = HexDecimal.decToHex(c, charset);
            sb.append(StringUtils.lefPad(str, getDigit(charset.length),
                    charset[0]));
        }
        return sb.toString();

    }

    /**
     * 采用默认的字符集进行解码<br>
     * 默认字符集合[0-9A-Za-z]
     *
     * @param source
     * @return 返回解码后的字符串
     */
    public static String decrypt(String source) {
        String[] arr = groupByLength(source, 3);
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append((char) HexDecimal.hexToDec(s, 62));
        }
        return sb.toString();
    }

    /**
     * 采用指定的字符集进行解码
     *
     * @param source
     * @param charset 解码所使用的字符集
     * @return 返回解码后的字符串
     */
    public static String decrypt(String source, char[] charset) {
        String[] arr = groupByLength(source, getDigit(charset.length));
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append((char) HexDecimal.hexToDec(s, charset));
        }
        return sb.toString();
    }

    /**
     * 对给定的字符串用数字编码
     *
     * @param source
     * @return 数字字符串
     */
    public static String encryptToNumber(String source) {
        StringBuilder sb = new StringBuilder();
        // 纯数字表示,需要5位
        int width = 5;
        char[] chs = source.toCharArray();
        for (char c : chs) {
            String str = (int) c + "";
            sb.append(StringUtils.lefPad(str, width, '0'));
        }
        return sb.toString();
    }

    /**
     * 对用数字编码的字符串进行解码<br>
     * 待解码的字符串必须为数字字符串
     *
     * @param source
     * @return 解码后字符串
     */
    public static String decryptFromNumber(String source) {
        // 纯数字表示,需要5位
        int width = 5;
        String[] arr = groupByLength(source, width);
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append((char) Integer.parseInt(s));
        }
        return sb.toString();
    }

    /**
     * 根据进制获取字符表示所需的位数
     *
     * @param N
     * @return N进制数的位数
     */
    private static int getDigit(int N) {
        int len = 0;
        if (N == 2) {
            len = 16;
        } else if (N == 3) {
            len = 11;
        } else if (N == 4) {
            len = 8;
        } else if (N >= 5 && N <= 6) {
            len = 7;
        } else if (N >= 7 && N <= 9) {
            len = 6;
        } else if (N >= 10 && N <= 15) {
            len = 5;
        } else if (N >= 16 && N <= 40) {
            len = 4;
        } else if (N >= 41 && N <= 255) {
            len = 3;
        } else if (N >= 256 && N <= 65535) {
            len = 2;
        } else if (N >= 65536) {
            len = 1;
        } else {
            throw new IllegalArgumentException("进制数不能小于2!");
        }
        return len;
    }

    /**
     * 将字符串按长度从后往前进行分组<br>
     * 分组后除第一组长度可能小于指定长度外,其余均等于指定长度
     * 例如:groupByLength("123456789",4)//返回:{[1],[2345],[6789]}<br>
     *
     * @param source
     * @param length
     * @return 分组后的字符串数组
     */
    private static String[] groupByLength(String source, int length) {
        int len = source.length() % length == 0 ? source.length() / length
                : source.length() / length + 1;
        String[] arr = new String[len];
        int begin = 0;
        for (int i = len; i > 0; i--) {
            begin = source.length() > length ? source.length() - length : 0;
            arr[i - 1] = source.substring(begin, source.length());
            source = source.substring(0, begin);

        }
        return arr;
    }
}
