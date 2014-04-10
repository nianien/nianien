package com.nianien.core.collection.map;

/**
 * 可以将两种数据类型绑定,用作DoubleMap对象Value值的数据类型
 * @author skyfalling
 *
 * @param <V1>
 * @param <V2>
 */
public interface DoubleValue<V1,V2> {

	/**
	 * 获取第一个数据对象
	 * @return
	 */
	V1 getValue1();
	/**
	 * 获取第一个数据对象
	 * @return
	 */
	V2 getValue2();
	/**
	 * 设置第一个数据对象
	 * @param value1
	 */
	void setValue1(V1 value1);
	/**
	 * 设置第二个数据对象
	 * @param value2
	 */
	void setValue2(V2 value2);
}
