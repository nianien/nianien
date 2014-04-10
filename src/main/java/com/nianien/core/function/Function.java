package com.nianien.core.function;

/**
 * 函数式,输入{@link T},输出{@link R}
 *
 * @author skyfalling
 */
public interface Function<T, R> {
    R call(T t);
}
