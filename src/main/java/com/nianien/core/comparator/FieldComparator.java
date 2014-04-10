package com.nianien.core.comparator;

import com.nianien.core.reflect.Reflections;

/**
 * 根据指定名称的字段值来确定对象比较的结果
 *
 * @param <T>
 * @author skyfalling
 */
public class FieldComparator<T> extends NamedComparator<T> {

    /**
     * 构造方法
     *
     * @param fieldName 待比较字段的名称
     */
    public FieldComparator(String fieldName) {
        super(fieldName);
    }

    /**
     * 重写基类的比较方法
     */
    @SuppressWarnings("unchecked")
    @Override
    public int compare(T t1, T t2) {
        Comparable<Object> c1 = (Comparable<Object>) Reflections.getFieldValue(t1, getComparableName());
        Comparable<Object> c2 = (Comparable<Object>) Reflections.getFieldValue(t2, getComparableName());
        return c1.compareTo(c2);
    }

}
