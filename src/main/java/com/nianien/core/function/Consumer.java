package com.nianien.core.function;

/**
 * 函数式,输入{@link T}
 *
 * @author skyfalling
 */
public interface Consumer<T> {
    void apply(T t);
}
