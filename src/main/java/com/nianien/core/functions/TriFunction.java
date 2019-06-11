package com.nianien.core.functions;

/**
 * 接受三个参数的Function
 *
 * @param <T>
 * @param <U>
 * @param <V>
 * @param <W>
 */
@FunctionalInterface
public interface TriFunction<T, U, V, W> {

    W apply(T t, U u, V v);
}