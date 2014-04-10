package com.nianien.core.collection.map;

import java.util.Map.Entry;

/**
 * Key/Value键值对, <code>{@link java.util.Map.Entry }</code>的接口实现
 *
 * @param <K>
 * @param <V>
 * @author skyfalling
 */
public class KeyValue<K, V> implements Entry<K, V> {

    private K key;
    private V value;

    /**
     * @see java.util.Map.Entry#getKey()
     */
    @Override
    public K getKey() {
        return key;
    }

    /**
     * @see java.util.Map.Entry#getValue()
     */
    @Override
    public V getValue() {
        return value;
    }

    /**
     * @see java.util.Map.Entry#setValue(Object)
     */
    @Override
    public V setValue(V value) {
        V old = value;
        this.value = value;
        return old;
    }

    /**
     * 构造方法
     *
     * @param key
     * @param value
     */
    public KeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public String toString() {
        return "{" + key + ":" + value + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeyValue keyValue = (KeyValue) o;

        if (!key.equals(keyValue.key)) return false;
        if (!value.equals(keyValue.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
