package com.nianien.core.util;

import com.nianien.core.log.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * 枚举工具类
 *
 * @author skyfalling
 */
public class EnumUtils {

    private static Logger logger = LoggerFactory.getLogger(EnumUtils.class);

    /**
     * 返回字段名为"value"且值为value的枚举值<br/>
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
     * 返回字段名为"value"且值为value的枚举值<br/>
     * 如果不存在则返回默认枚举值
     *
     * @param enumClass
     * @param value
     * @param defaultEnum
     * @param <T>
     * @return
     */
    public static <T extends Enum<T>> T valueOf(Class<T> enumClass, Object value, T defaultEnum) {
        T t = valueOf(enumClass, value);
        return t != null ? t : defaultEnum;
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
        logger.warning("cannot find enum type: " + enumClass.getName() + "[" + fieldName + "=" + fieldValue + "]");
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
