package com.nianien.core.function;

/**
 * 函数式,输出{@link T}
 *
 * @author skyfalling
 */
public interface Supplier<T> {
    T apply();
}
