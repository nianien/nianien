package com.nianien.core.function;

/**
 * 函数式,输入{@link T},输出boolean
 *
 * @author skyfalling
 */
public interface Predicate<T> {
    boolean apply(T t);
}
