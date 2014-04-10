package com.nianien.core.function;

/**
 * 对象选择器
 *
 * @author skyfalling
 */
public interface Selector<T> {
    /**
     * 判断对象target是否符合选择条件
     *
     * @param target
     * @return 符合条件返回true, 否则返回false
     */
    boolean select(T target);
}
