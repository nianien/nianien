package com.nianien.core.util;

import com.sun.istack.internal.Nullable;

import java.lang.reflect.Array;
import java.util.Arrays;

import static java.util.Arrays.binarySearch;

/**
 * 支持数组操作的工具类
 *
 * @author skyfalling
 */
public class ArrayUtils {

    public static boolean isEmpty(boolean[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(byte[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(char[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(short[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(long[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(float[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(double[] array) {
        return array == null || array.length == 0;
    }

    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }


    public static boolean isNotEmpty(final boolean[] array) {
        return (array != null && array.length != 0);
    }

    public static boolean isNotEmpty(final byte[] array) {
        return (array != null && array.length != 0);
    }

    public static boolean isNotEmpty(final char[] array) {
        return (array != null && array.length != 0);
    }

    public static boolean isNotEmpty(final short[] array) {
        return (array != null && array.length != 0);
    }

    public static boolean isNotEmpty(final int[] array) {
        return (array != null && array.length != 0);
    }

    public static boolean isNotEmpty(final long[] array) {
        return (array != null && array.length != 0);
    }

    public static boolean isNotEmpty(final float[] array) {
        return (array != null && array.length != 0);
    }

    public static boolean isNotEmpty(final double[] array) {
        return (array != null && array.length != 0);
    }

    public static <T> boolean isNotEmpty(final T[] array) {
        return (array != null && array.length != 0);
    }


    /**
     * 判断数组array是否包含target元素
     *
     * @param array
     * @param target
     * @return 如果array包含true, 否则返回false
     */
    public static boolean contains(boolean[] array, boolean target) {
        for (boolean t : array) {
            if (t == target) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断数组array是否包含target元素
     *
     * @param array
     * @param target
     * @return 如果array包含true, 否则返回false
     */
    public static boolean contains(byte[] array, byte target) {
        for (byte t : array) {
            if (t == target) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断数组array是否包含target元素
     *
     * @param array
     * @param target
     * @return 如果array包含true, 否则返回false
     */
    public static boolean contains(char[] array, char target) {
        return binarySearch(array, target) >= 0;
    }

    /**
     * 判断数组array是否包含target元素
     *
     * @param array
     * @param target
     * @return 如果array包含true, 否则返回false
     */
    public static boolean contains(short[] array, int target) {
        return binarySearch(array, (short) target) >= 0;
    }

    /**
     * 判断数组array是否包含target元素
     *
     * @param array
     * @param target
     * @return 如果array包含true, 否则返回false
     */
    public static boolean contains(int[] array, int target) {
        return binarySearch(array, target) >= 0;
    }

    /**
     * 判断数组array是否包含target元素
     *
     * @param array
     * @param target
     * @return 如果array包含true, 否则返回false
     */
    public static boolean contains(long[] array, long target) {
        return binarySearch(array, target) >= 0;
    }

    /**
     * 判断数组array是否包含target元素
     *
     * @param array
     * @param target
     * @return 如果array包含true, 否则返回false
     */
    public static boolean contains(float[] array, float target) {
        return binarySearch(array, target) >= 0;
    }

    /**
     * 判断数组array是否包含target元素
     *
     * @param array
     * @param target
     * @return 如果array包含true, 否则返回false
     */
    public static boolean contains(double[] array, double target) {
        return binarySearch(array, target) >= 0;
    }

    /**
     * 判断数组array是否包含target元素
     *
     * @param <T>
     * @param array
     * @param target
     * @return 如果array包含true, 否则返回false
     */
    public static <T> boolean contains(T[] array, T target) {
        return binarySearch(array, target) >= 0;
    }

    /**
     * 数组求交集
     *
     * @param arr1
     * @param arr2
     * @return 交集
     */
    public static boolean[] intersect(boolean[] arr1, boolean[] arr2) {
        if (arr1.length == 0 || arr2.length == 0)
            return new boolean[0];
        boolean[] arr = new boolean[arr1.length];
        int i = 0;
        for (boolean t : arr1) {
            if (contains(arr2, t)) {
                arr[i++] = t;
            }
        }
        return Arrays.copyOf(arr, i);
    }


    /**
     * 数组求交集
     *
     * @param arr1
     * @param arr2
     * @return 交集
     */
    public static byte[] intersect(byte[] arr1, byte[] arr2) {
        if (arr1.length == 0 || arr2.length == 0)
            return new byte[0];
        byte[] arr = new byte[arr1.length];
        int i = 0;
        for (byte t : arr1) {
            if (contains(arr2, t)) {
                arr[i++] = t;
            }
        }
        return Arrays.copyOf(arr, i);
    }

    /**
     * 数组求交集
     *
     * @param arr1
     * @param arr2
     * @return 交集
     */
    public static char[] intersect(char[] arr1, char[] arr2) {
        if (arr1.length == 0 || arr2.length == 0)
            return new char[0];
        char[] arr = new char[arr1.length];
        int i = 0;
        for (char t : arr1) {
            if (contains(arr2, t)) {
                arr[i++] = t;
            }
        }
        return Arrays.copyOf(arr, i);
    }

    /**
     * 数组求交集
     *
     * @param arr1
     * @param arr2
     * @return 交集
     */
    public static short[] intersect(short[] arr1, short[] arr2) {
        if (arr1.length == 0 || arr2.length == 0)
            return new short[0];
        short[] arr = new short[arr1.length];
        int i = 0;
        for (short t : arr1) {
            if (contains(arr2, t)) {
                arr[i++] = t;
            }
        }
        return Arrays.copyOf(arr, i);
    }

    /**
     * 数组求交集
     *
     * @param arr1
     * @param arr2
     * @return 交集
     */
    public static int[] intersect(int[] arr1, int[] arr2) {
        if (arr1.length == 0 || arr2.length == 0)
            return new int[0];
        int[] arr = new int[arr1.length];
        int i = 0;
        for (int t : arr1) {
            if (contains(arr2, t)) {
                arr[i++] = t;
            }
        }
        return Arrays.copyOf(arr, i);
    }

    /**
     * 数组求交集
     *
     * @param arr1
     * @param arr2
     * @return 交集
     */
    public static long[] intersect(long[] arr1, long[] arr2) {
        if (arr1.length == 0 || arr2.length == 0)
            return new long[0];
        long[] arr = new long[arr1.length];
        int i = 0;
        for (long t : arr1) {
            if (contains(arr2, t)) {
                arr[i++] = t;
            }
        }
        return Arrays.copyOf(arr, i);
    }

    /**
     * 数组求交集
     *
     * @param arr1
     * @param arr2
     * @return 交集
     */
    public static float[] intersect(float[] arr1, float[] arr2) {
        if (arr1.length == 0 || arr2.length == 0)
            return new float[0];
        float[] arr = new float[arr1.length];
        int i = 0;
        for (float t : arr1) {
            if (contains(arr2, t)) {
                arr[i++] = t;
            }
        }
        return Arrays.copyOf(arr, i);
    }

    /**
     * 数组求交集
     *
     * @param arr1
     * @param arr2
     * @return 交集
     */
    public static double[] intersect(double[] arr1, double[] arr2) {
        if (arr1.length == 0 || arr2.length == 0)
            return new double[0];
        double[] arr = new double[arr1.length];
        int i = 0;
        for (double t : arr1) {
            if (contains(arr2, t)) {
                arr[i++] = t;
            }
        }
        return Arrays.copyOf(arr, i);
    }

    /**
     * 数组求交集
     *
     * @param <T>
     * @param arr1
     * @param arr2
     * @param clazz
     * @return 合并后的数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] intersect(T[] arr1, T[] arr2, Class<T> clazz) {
        if (arr1.length == 0 || arr2.length == 0)
            return (T[]) Array.newInstance(clazz, 0);
        T[] arr = (T[]) Array.newInstance(clazz, arr1.length);
        int i = 0;
        for (T t : arr1) {
            if (contains(arr2, t)) {
                arr[i++] = t;
            }
        }
        return Arrays.copyOf(arr, i);
    }


    /**
     * 数组求差集
     *
     * @param arr1
     * @param arr2
     * @return 差集
     */
    public static boolean[] subtract(boolean[] arr1, boolean[] arr2) {
        if (arr1.length == 0 || arr2.length == 0)
            return new boolean[0];
        boolean[] arr = new boolean[arr1.length];
        int i = 0;
        for (boolean t : arr1) {
            if (!contains(arr2, t)) {
                arr[i++] = t;
            }
        }
        return Arrays.copyOf(arr, i);
    }

    /**
     * 数组求差集
     *
     * @param arr1
     * @param arr2
     * @return 差集
     */
    public static byte[] subtract(byte[] arr1, byte[] arr2) {
        if (arr1.length == 0 || arr2.length == 0)
            return new byte[0];
        byte[] arr = new byte[arr1.length];
        int i = 0;
        for (byte t : arr1) {
            if (!contains(arr2, t)) {
                arr[i++] = t;
            }
        }
        return Arrays.copyOf(arr, i);
    }

    /**
     * 数组求差集
     *
     * @param arr1
     * @param arr2
     * @return 差集
     */
    public static char[] subtract(char[] arr1, char[] arr2) {
        if (arr1.length == 0 || arr2.length == 0)
            return new char[0];
        char[] arr = new char[arr1.length];
        int i = 0;
        for (char t : arr1) {
            if (!contains(arr2, t)) {
                arr[i++] = t;
            }
        }
        return Arrays.copyOf(arr, i);
    }

    /**
     * 数组求差集
     *
     * @param arr1
     * @param arr2
     * @return 差集
     */
    public static short[] subtract(short[] arr1, short[] arr2) {
        if (arr1.length == 0 || arr2.length == 0)
            return new short[0];
        short[] arr = new short[arr1.length];
        int i = 0;
        for (short t : arr1) {
            if (!contains(arr2, t)) {
                arr[i++] = t;
            }
        }
        return Arrays.copyOf(arr, i);
    }

    /**
     * 数组求差集
     *
     * @param arr1
     * @param arr2
     * @return 差集
     */
    public static int[] subtract(int[] arr1, int[] arr2) {
        if (arr1.length == 0 || arr2.length == 0)
            return new int[0];
        int[] arr = new int[arr1.length];
        int i = 0;
        for (int t : arr1) {
            if (!contains(arr2, t)) {
                arr[i++] = t;
            }
        }
        return Arrays.copyOf(arr, i);
    }

    /**
     * 数组求差集
     *
     * @param arr1
     * @param arr2
     * @return 差集
     */
    public static long[] subtract(long[] arr1, long[] arr2) {
        if (arr1.length == 0 || arr2.length == 0)
            return new long[0];
        long[] arr = new long[arr1.length];
        int i = 0;
        for (long t : arr1) {
            if (!contains(arr2, t)) {
                arr[i++] = t;
            }
        }
        return Arrays.copyOf(arr, i);
    }

    /**
     * 数组求差集
     *
     * @param arr1
     * @param arr2
     * @return 差集
     */
    public static float[] subtract(float[] arr1, float[] arr2) {
        if (arr1.length == 0 || arr2.length == 0)
            return new float[0];
        float[] arr = new float[arr1.length];
        int i = 0;
        for (float t : arr1) {
            if (!contains(arr2, t)) {
                arr[i++] = t;
            }
        }
        return Arrays.copyOf(arr, i);
    }

    /**
     * 数组求差集
     *
     * @param arr1
     * @param arr2
     * @return 差集
     */
    public static double[] subtract(double[] arr1, double[] arr2) {
        if (arr1.length == 0 || arr2.length == 0)
            return new double[0];
        double[] arr = new double[arr1.length];
        int i = 0;
        for (double t : arr1) {
            if (!contains(arr2, t)) {
                arr[i++] = t;
            }
        }
        return Arrays.copyOf(arr, i);
    }

    /**
     * 数组求差集
     *
     * @param <T>
     * @param arr1
     * @param arr2
     * @param clazz
     * @return 差集
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] subtract(T[] arr1, T[] arr2, Class<T> clazz) {
        if (arr1.length == 0 || arr2.length == 0)
            return (T[]) Array.newInstance(clazz, 0);
        T[] arr = (T[]) Array.newInstance(clazz, arr1.length);
        int i = 0;
        for (T t : arr1) {
            if (!contains(arr2, t)) {
                arr[i++] = t;
            }
        }
        return Arrays.copyOf(arr, i);
    }

    /**
     * 将字符串数组array以分隔符split连接成字符串<br>
     * 例如:toString(new int[]{1,2},",")//返回:1,2<br>
     *
     * @param array
     * @param splitter
     * @return 转换后的字符串<br>
     */
    public static String toString(boolean[] array, String splitter) {
        return toString(array, splitter, "", "");
    }

    /**
     * 将字符串array数组以分隔符split连接成字符串,每个数组元素分别用字符surround包围<br>
     * 例如:toString(new int[]{1,2},",","\"")//返回:"1","2"<br>
     *
     * @param array
     * @param splitter
     * @param surround
     * @return 转换后的字符串<br>
     */
    public static String toString(boolean[] array, String splitter,
                                  String surround) {
        return toString(array, splitter, surround, surround);
    }

    /**
     * 将字符串数组array以分隔符split连接成字符串,每个数组元素分别被left和right字符包围<br>
     * 例如:toString(new int[]{1,2},",","[","]")//返回:[1],[2]<br>
     *
     * @param array
     * @param splitter
     * @param left
     * @param right
     * @return 转换后的字符串<br>
     */
    public static String toString(boolean[] array, String splitter,
                                  String left, String right) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(left).append(array[i]).append(right)
                    .append(i == array.length - 1 ? "" : splitter);
        }
        return sb.toString();
    }

    /**
     * 将字符串数组array以分隔符split连接成字符串<br>
     * 例如:toString(new String[]{"aa","bb"},",")//返回:aa,bb<br>
     *
     * @param array
     * @param splitter
     * @return 转换后的字符串<br>
     */
    public static String toString(byte[] array, String splitter) {
        return toString(array, splitter, "", "");
    }

    /**
     * 将字符串array数组以分隔符split连接成字符串,每个数组元素分别用字符surround包围<br>
     * 例如:toString(new byte[]{1,2},",","\"")//返回:"1","2"<br>
     *
     * @param array
     * @param splitter
     * @param surround
     * @return 转换后的字符串<br>
     */
    public static String toString(byte[] array, String splitter,
                                  String surround) {
        return toString(array, splitter, surround, surround);
    }

    /**
     * 将字符串数组array以分隔符split连接成字符串,每个数组元素分别被left和right字符包围<br>
     * 例如:toString(new byte[]{1,2},",","[","]")//返回:[1],[2]<br>
     *
     * @param array
     * @param splitter
     * @param left
     * @param right
     * @return 转换后的字符串<br>
     */
    public static String toString(byte[] array, String splitter,
                                  String left, String right) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(left).append(array[i]).append(right)
                    .append(i == array.length - 1 ? "" : splitter);
        }
        return sb.toString();
    }

    /**
     * 将字符串数组array以分隔符split连接成字符串<br>
     * 例如:toString(new String[]{"aa","bb"},",")//返回:aa,bb<br>
     *
     * @param array
     * @param splitter
     * @return 转换后的字符串<br>
     */
    public static String toString(char[] array, String splitter) {
        return toString(array, splitter, "", "");
    }

    /**
     * 将字符串array数组以分隔符split连接成字符串,每个数组元素分别用字符surround包围<br>
     * 例如:toString(new char[]{'a','b'},",","\"")//返回:"a","b"<br>
     *
     * @param array
     * @param splitter
     * @param surround
     * @return 转换后的字符串<br>
     */
    public static String toString(char[] array, String splitter,
                                  String surround) {
        return toString(array, splitter, surround, surround);
    }

    /**
     * 将字符串数组array以分隔符split连接成字符串,每个数组元素分别被left和right字符包围<br>
     * 例如:toString(new char[]{'a','b'},",","[","]")//返回:[a],[b]<br>
     *
     * @param array
     * @param splitter
     * @param left
     * @param right
     * @return 转换后的字符串<br>
     */
    public static String toString(char[] array, String splitter,
                                  String left, String right) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(left).append(array[i]).append(right)
                    .append(i == array.length - 1 ? "" : splitter);
        }
        return sb.toString();
    }

    /**
     * 将字符串数组array以分隔符split连接成字符串<br>
     * 例如:toString(new int[]{1,2},",")//返回:1,2<br>
     *
     * @param array
     * @param splitter
     * @return 转换后的字符串<br>
     */
    public static String toString(short[] array, String splitter) {
        return toString(array, splitter, "", "");
    }

    /**
     * 将字符串array数组以分隔符split连接成字符串,每个数组元素分别用字符surround包围<br>
     * 例如:toString(new int[]{1,2},",","\"")//返回:"1","2"<br>
     *
     * @param array
     * @param splitter
     * @param surround
     * @return 转换后的字符串<br>
     */
    public static String toString(short[] array, String splitter,
                                  String surround) {
        return toString(array, splitter, surround, surround);
    }

    /**
     * 将字符串数组array以分隔符split连接成字符串,每个数组元素分别被left和right字符包围<br>
     * 例如:toString(new int[]{1,2},",","[","]")//返回:[1],[2]<br>
     *
     * @param array
     * @param splitter
     * @param left
     * @param right
     * @return 转换后的字符串<br>
     */
    public static String toString(short[] array, String splitter,
                                  String left, String right) {
        StringBuilder sb = new StringBuilder();
        for (short i = 0; i < array.length; i++) {
            sb.append(left).append(array[i]).append(right)
                    .append(i == array.length - 1 ? "" : splitter);
        }
        return sb.toString();
    }

    /**
     * 将字符串数组array以分隔符split连接成字符串<br>
     * 例如:toString(new int[]{1,2},",")//返回:1,2<br>
     *
     * @param array
     * @param splitter
     * @return 转换后的字符串<br>
     */
    public static String toString(int[] array, String splitter) {
        return toString(array, splitter, "", "");
    }

    /**
     * 将字符串array数组以分隔符split连接成字符串,每个数组元素分别用字符surround包围<br>
     * 例如:toString(new int[]{1,2},",","\"")//返回:"1","2"<br>
     *
     * @param array
     * @param splitter
     * @param surround
     * @return 转换后的字符串<br>
     */
    public static String toString(int[] array, String splitter,
                                  String surround) {
        return toString(array, splitter, surround, surround);
    }

    /**
     * 将字符串数组array以分隔符split连接成字符串,每个数组元素分别被left和right字符包围<br>
     * 例如:toString(new int[]{1,2},",","[","]")//返回:[1],[2]<br>
     *
     * @param array
     * @param splitter
     * @param left
     * @param right
     * @return 转换后的字符串<br>
     */
    public static String toString(int[] array, String splitter,
                                  String left, String right) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(left).append(array[i]).append(right)
                    .append(i == array.length - 1 ? "" : splitter);
        }
        return sb.toString();
    }

    /**
     * 将字符串数组array以分隔符split连接成字符串<br>
     * 例如:toString(new long[]{1,2},",")//返回:1,2<br>
     *
     * @param array
     * @param splitter
     * @return 转换后的字符串<br>
     */
    public static String toString(long[] array, String splitter) {
        return toString(array, splitter, "", "");
    }

    /**
     * 将字符串array数组以分隔符split连接成字符串,每个数组元素分别用字符surround包围<br>
     * 例如:toString(new long[]{1,2},",","\"")//返回:"1","2"<br>
     *
     * @param array
     * @param splitter
     * @param surround
     * @return 转换后的字符串<br>
     */
    public static String toString(long[] array, String splitter,
                                  String surround) {
        return toString(array, splitter, surround, surround);
    }

    /**
     * 将字符串数组array以分隔符split连接成字符串,每个数组元素分别被left和right字符包围<br>
     * 例如:toString(new long[]{1,2},",","[","]")//返回:[1],[2]<br>
     *
     * @param array
     * @param splitter
     * @param left
     * @param right
     * @return 转换后的字符串<br>
     */
    public static String toString(long[] array, String splitter,
                                  String left, String right) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(left).append(array[i]).append(right)
                    .append(i == array.length - 1 ? "" : splitter);
        }
        return sb.toString();
    }

    /**
     * 将字符串数组array以分隔符split连接成字符串<br>
     * 例如:toString(new float[]{1.0,2.0},",")//返回:1.0,2.0<br>
     *
     * @param array
     * @param splitter
     * @return 转换后的字符串<br>
     */
    public static String toString(float[] array, String splitter) {
        return toString(array, splitter, "", "");
    }

    /**
     * 将字符串array数组以分隔符split连接成字符串,每个数组元素分别用字符surround包围<br>
     * 例如:toString(new float[]{1.0,2.0},",","\"")//返回:"1.0","2.0"<br>
     *
     * @param array
     * @param splitter
     * @param surround
     * @return 转换后的字符串<br>
     */
    public static String toString(float[] array, String splitter,
                                  String surround) {
        return toString(array, splitter, surround, surround);
    }

    /**
     * 将字符串数组array以分隔符split连接成字符串,每个数组元素分别被left和right字符包围<br>
     * 例如:toString(new float[]{1.0,2.0},",","[","]")//返回:[1.0],[2.0]<br>
     *
     * @param array
     * @param splitter
     * @param left
     * @param right
     * @return 转换后的字符串<br>
     */
    public static String toString(float[] array, String splitter,
                                  String left, String right) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(left).append(array[i]).append(right)
                    .append(i == array.length - 1 ? "" : splitter);
        }
        return sb.toString();
    }

    /**
     * 将字符串数组array以分隔符split连接成字符串<br>
     * 例如:toString(new double[]{1.0,2.0},",")//返回:1.0,2.0<br>
     *
     * @param array
     * @param splitter
     * @return 转换后的字符串<br>
     */
    public static String toString(double[] array, String splitter) {
        return toString(array, splitter, "", "");
    }

    /**
     * 将字符串array数组以分隔符split连接成字符串,每个数组元素分别用字符surround包围<br>
     * 例如:toString(new double[]{1.0,2.0},",","\"")//返回:"1.0","2.0"<br>
     *
     * @param array
     * @param splitter
     * @param surround
     * @return 转换后的字符串<br>
     */
    public static String toString(double[] array, String splitter,
                                  String surround) {
        return toString(array, splitter, surround, surround);
    }

    /**
     * 将字符串数组array以分隔符split连接成字符串,每个数组元素分别被left和right字符包围<br>
     * 例如:toString(new double[]{1.0,2.0},",","[","]")//返回:[1.0],[2.0]<br>
     *
     * @param array
     * @param splitter
     * @param left
     * @param right
     * @return 转换后的字符串<br>
     */
    public static String toString(double[] array, String splitter,
                                  String left, String right) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(left).append(array[i]).append(right)
                    .append(i == array.length - 1 ? "" : splitter);
        }
        return sb.toString();
    }

    /**
     * 将字符串数组array以分隔符split连接成字符串<br>
     * 例如:toString(new String[]{"aa","bb"},",")//返回:aa,bb<br>
     *
     * @param array
     * @param splitter
     * @return 转换后的字符串<br>
     */
    public static <T> String toString(T[] array, String splitter) {
        return toString(array, splitter, "", "");
    }

    /**
     * 将字符串array数组以分隔符split连接成字符串,每个数组元素分别用字符surround包围<br>
     * 例如:toString(new String[]{"aa","bb"},",","\"")//返回:"aa","bb"<br>
     *
     * @param array
     * @param splitter
     * @param surround
     * @return 转换后的字符串<br>
     */
    public static <T> String toString(T[] array, String splitter,
                                      String surround) {
        return toString(array, splitter, surround, surround);
    }

    /**
     * 将字符串数组array以分隔符split连接成字符串,每个数组元素分别被left和right字符包围<br>
     * 例如:toString(new String[]{"aa","bb"},",","[","]")//返回:[aa],[bb]<br>
     *
     * @param array
     * @param splitter
     * @param left
     * @param right
     * @return 转换后的字符串<br>
     */
    public static <T> String toString(T[] array, String splitter, String left,
                                      String right) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(left).append(array[i]).append(right)
                    .append(i == array.length - 1 ? "" : splitter);
        }
        return sb.toString();
    }

    /**
     * 数组求并集
     *
     * @param arr1
     * @param arr2
     * @return 并集
     */
    public static boolean[] union(boolean[] arr1, boolean[] arr2) {
        if (arr1.length == 0)
            return arr2;
        if (arr2.length == 0)
            return arr1;
        boolean[] booleans = new boolean[arr1.length + arr2.length];
        int i = 0;
        for (boolean b : arr1) {
            booleans[i++] = b;
        }
        for (boolean b : arr2) {
            booleans[i++] = b;
        }
        return booleans;
    }

    /**
     * 数组求并集
     *
     * @param arr1
     * @param arr2
     * @return 并集
     */
    public static byte[] union(byte[] arr1, byte[] arr2) {
        if (arr1.length == 0)
            return arr2;
        if (arr2.length == 0)
            return arr1;
        byte[] bytes = new byte[arr1.length + arr2.length];
        int i = 0;
        for (byte b : arr1) {
            bytes[i++] = b;
        }
        for (byte b : arr2) {
            bytes[i++] = b;
        }
        return bytes;
    }

    /**
     * 数组求并集
     *
     * @param arr1
     * @param arr2
     * @return 并集
     */
    public static char[] union(char[] arr1, char[] arr2) {
        if (arr1.length == 0)
            return arr2;
        if (arr2.length == 0)
            return arr1;
        char[] chars = new char[arr1.length + arr2.length];
        int i = 0;
        for (char b : arr1) {
            chars[i++] = b;
        }
        for (char b : arr2) {
            chars[i++] = b;
        }
        return chars;
    }

    /**
     * 数组求并集
     *
     * @param arr1
     * @param arr2
     * @return 并集
     */
    public static double[] union(double[] arr1, double[] arr2) {
        if (arr1.length == 0)
            return arr2;
        if (arr2.length == 0)
            return arr1;
        double[] doubles = new double[arr1.length + arr2.length];
        int i = 0;
        for (double b : arr1) {
            doubles[i++] = b;
        }
        for (double b : arr2) {
            doubles[i++] = b;
        }
        return doubles;
    }

    /**
     * 数组求并集
     *
     * @param arr1
     * @param arr2
     * @return 并集
     */
    public static float[] union(float[] arr1, float[] arr2) {
        if (arr1.length == 0)
            return arr2;
        if (arr2.length == 0)
            return arr1;
        float[] floats = new float[arr1.length + arr2.length];
        int i = 0;
        for (float b : arr1) {
            floats[i++] = b;
        }
        for (float b : arr2) {
            floats[i++] = b;
        }
        return floats;
    }

    /**
     * 数组求并集
     *
     * @param arr1
     * @param arr2
     * @return 并集
     */
    public static int[] union(int[] arr1, int[] arr2) {
        if (arr1.length == 0)
            return arr2;
        if (arr2.length == 0)
            return arr1;
        int[] ints = new int[arr1.length + arr2.length];
        int i = 0;
        for (int b : arr1) {
            ints[i++] = b;
        }
        for (int b : arr2) {
            ints[i++] = b;
        }
        return ints;
    }

    /**
     * 数组求并集
     *
     * @param arr1
     * @param arr2
     * @return 并集
     */
    public static long[] union(long[] arr1, long[] arr2) {
        if (arr1.length == 0)
            return arr2;
        if (arr2.length == 0)
            return arr1;
        long[] longs = new long[arr1.length + arr2.length];
        int i = 0;
        for (long b : arr1) {
            longs[i++] = b;
        }
        for (long b : arr2) {
            longs[i++] = b;
        }
        return longs;
    }

    /**
     * 数组求并集
     *
     * @param <T>
     * @param arr1
     * @param arr2
     * @param clazz
     * @return 并集
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] union(T[] arr1, T[] arr2, Class<T> clazz) {
        if (arr1.length == 0)
            return arr2;
        if (arr2.length == 0)
            return arr1;
        T[] array = (T[]) Array.newInstance(clazz, arr1.length + arr2.length);
        int i = 0;
        for (T t : arr1) {
            array[i++] = t;
        }
        for (T t : arr2) {
            array[i++] = t;
        }
        return array;
    }


    public static Object[] toObjectArray(@Nullable Object source) {
        if (source instanceof Object[]) {
            return (Object[]) source;
        }
        if (source == null) {
            return new Object[0];
        }
        if (!source.getClass().isArray()) {
            throw new IllegalArgumentException("Source is not an array: " + source);
        }
        int length = Array.getLength(source);
        if (length == 0) {
            return new Object[0];
        }
        Class<?> wrapperType = Array.get(source, 0).getClass();
        Object[] newArray = (Object[]) Array.newInstance(wrapperType, length);
        for (int i = 0; i < length; i++) {
            newArray[i] = Array.get(source, i);
        }
        return newArray;
    }

}
