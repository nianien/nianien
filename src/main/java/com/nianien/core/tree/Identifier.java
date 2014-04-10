package com.nianien.core.tree;

/**
 * 获取对象唯一标识的接口定义
 *
 * @param <K>
 * @param <V>
 */
public interface Identifier<K, V> {

    /**
     * 获取当前对象的唯一标识
     *
     * @param node
     * @return
     */
    K id(V node);

}
