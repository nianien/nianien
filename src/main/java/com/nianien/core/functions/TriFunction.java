package com.nianien.core.functions;

/**
 * 接受三个参数的Function
 *
 * @param <T> 第一个参数类型
 * @param <U> 第二个参数类型
 * @param <V> 第三个参数类型
 * @param <W> 返回类型
 */
@FunctionalInterface
public interface TriFunction<T, U, V, W> {

    W apply(T t, U u, V v);
}