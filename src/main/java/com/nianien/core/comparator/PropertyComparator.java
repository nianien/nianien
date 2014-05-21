package com.nianien.core.comparator;

import com.nianien.core.reflect.Reflections;

/**
 * 根据指定名称的属性值来确定对象比较的结果
 * 
 * @author skyfalling
 * 
 * @param <T>
 */
public class PropertyComparator<T> extends NamedComparator<T> {

	/**
	 * 构造方法
	 * 
	 * @param propertyName
	 *            待比较属性的名称
	 */
	public PropertyComparator(String propertyName) {
		super(propertyName);
	}

	/**
	 * 重写基类的比较方法
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int compare(T t1, T t2) {
		Comparable<Object> c1 = (Comparable<Object>) Reflections.getProperty(t1,
                getComparableName());
		Comparable<Object> c2 = (Comparable<Object>) Reflections.getProperty(t2,
                getComparableName());
		return c1.compareTo(c2);
	}
}
