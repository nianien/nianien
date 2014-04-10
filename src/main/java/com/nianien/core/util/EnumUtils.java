package com.nianien.core.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 枚举工具类
 *
 * @author skyfalling
 */
public class EnumUtils {

    /**
     * 返回字段名"value",值为value的枚举值<br/>
     * 如果不存在则返回null
     *
     * @param enumClass
     * @param value
     * @param <T>
     * @return
     */
    public static <T extends Enum<T>> T valueOf(Class<T> enumClass, Object value) {
        return find(enumClass, "value", value);
    }

    /**
     * 根据字段值返回相应的枚举对象<br/>
     *
     * @param enumClass
     * @param fieldValue
     * @param <T>
     * @return
     */
    public static <T extends Enum<T>> T find(Class<T> enumClass, String fieldName, Object fieldValue) {
        T[] types = enumClass.getEnumConstants();
        try {
            for (T t : types) {
                Field field = t.getDeclaringClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                if (field.get(t).equals(fieldValue))
                    return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.err.println("cannot find enum type: " + enumClass.getName() + "[" + fieldName + "=" + fieldValue + "]");
        return null;
    }


    /**
     * 取枚举对象除excludes之外的实例
     *
     * @param enumClass
     * @param excludes
     * @param <T>
     * @return
     */
    public static <T extends Enum<T>> List<T> list(Class<T> enumClass, T... excludes) {
        List<T> list = new ArrayList<T>(Arrays.asList(enumClass.getEnumConstants()));
        list.removeAll(Arrays.asList(excludes));
        return list;
    }

    /**
     * 取枚举对象除exclude和others之外的实例,是{@link #list(Class, Enum[])}方法的便捷形式
     *
     * @param exclude
     * @param others
     * @param <T>
     * @return
     */
    public static <T extends Enum<T>> List<T> except(T exclude, T... others) {
        List<T> list = new ArrayList<T>(Arrays.asList(exclude.getDeclaringClass().getEnumConstants()));
        list.remove(exclude);
        list.removeAll(Arrays.asList(others));
        return list;
    }
}