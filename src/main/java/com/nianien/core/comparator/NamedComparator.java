package com.nianien.core.comparator;

import java.util.Comparator;

/**
 * 根据指定名称的对象的比较来确定宿主对象的大小<br>
 * 这里的比较对象可以是字段名称或者属性名称<br>
 * 该类为抽象类,实现类参考{@link FieldComparator}和 {@link PropertyComparator}
 * 
 * @author skyfalling
 * @param <T>
 * 
 */
public abstract class NamedComparator<T> implements Comparator<T> {
	/**
	 * 比较对象的名称
	 */
	private String comparableName;

	/**
	 * 获取比较对象的名称
	 * 
	 * @return String
	 */
	public String getComparableName() {
		return comparableName;
	}

	/**
	 * 设置比较对象的名称
	 * 
	 * @param comparatorName
	 */
	public void setComparableName(String comparatorName) {
		this.comparableName = comparatorName;
	}

	/**
	 * 构造函数,指定比较对象的名称
	 * 
	 * @param comparatorName
	 */
	public NamedComparator(String comparatorName) {
		this.setComparableName(comparatorName);
	}
}
