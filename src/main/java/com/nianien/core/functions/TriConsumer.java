package com.nianien.core.functions;

/**
 * 接受三个参数的Consumer
 *
 * @param <T>
 * @param <U>
 * @param <V>
 */
@FunctionalInterface
public interface TriConsumer<T, U, V> {

    void accept(T t, U u, V v);
}