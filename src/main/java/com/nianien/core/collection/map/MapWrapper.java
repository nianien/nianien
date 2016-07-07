package com.nianien.core.collection.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.nianien.core.function.Function;

/**
 * {@link Map}接口的包装类,包装Map实例以支持链式语法<br/>
 * 如果未提供Map实例,则默认为{@link HashMap}实现<br/>
 *
 * @author skyfalling
 */
public class MapWrapper<K, V> implements Map<K, V> {

    private Map<K, V> map;

    /**
     * 构造方法,默认Map实例
     */
    public MapWrapper() {
        this(new HashMap<K, V>());
    }

    /**
     * 构造方法,指定Map实例
     *
     * @param map
     */
    public MapWrapper(Map<K, V> map) {
        this.map = map;
    }

    /**
     * 构造方法,默认Map实例并提供初始键值对
     *
     * @param k
     * @param v
     */
    public MapWrapper(K k, V v) {
        this(new HashMap<K, V>(), k, v);
    }

    /**
     * 构造方法,指定Map实例以及初始键值对
     *
     * @param map
     * @param key
     * @param value
     */
    protected MapWrapper(Map<K, V> map, K key, V value) {
        this.map = map;
        this.put(key, value);
    }

    /**
     * 调用{@link Map#put(K, V)}方法
     *
     * @param key
     * @param value
     *
     * @return 返回当前对象
     */
    public MapWrapper<K, V> append(K key, V value) {
        this.put(key, value);
        return this;
    }

    /**
     * 调用{@link Map#putAll(java.util.Map)}方法
     *
     * @param map
     *
     * @return 返回当前对象
     */
    public MapWrapper<K, V> append(Map<? extends K, ? extends V> map) {
        this.putAll(map);
        return this;
    }

    /**
     * 调用{@link Map#remove(Object)}}方法
     *
     * @param keys
     *
     * @return 返回当前对象
     */

    public MapWrapper<K, V> delete(Collection keys) {
        for (Object key : keys) {
            this.remove(key);
        }
        return this;
    }

    /**
     * 如果key值不存在,则调用{@link Map#put(K, V)}方法,value为function的调用结果
     *
     * @param key
     * @param function
     *
     * @return 返回当前对象
     */
    public MapWrapper<K, V> appendIfAbsent(K key, Function<K, V> function) {
        if (!containsKey(key)) {
            this.put(key, function.apply(key));
        }
        return this;
    }

    /**
     * 如果不存在key值,则调用{@link Map#put(K, V)}方法
     *
     * @param key
     * @param value
     *
     * @return 返回当前对象
     */
    public MapWrapper<K, V> appendIfAbsent(K key, V value) {
        if (!containsKey(key)) {
            this.put(key, value);
        }
        return this;
    }

    /**
     * 如果表达式expression为true,,则调用{@link Map#put(K, V)}方法
     *
     * @param key
     * @param value
     *
     * @return 返回当前对象
     */
    public MapWrapper<K, V> appendIf(boolean expression, K key, V value) {
        if (expression) {
            this.put(key, value);
        }
        return this;
    }

    /**
     * 调用{@link Map#remove(Object)}}方法
     *
     * @param key
     *
     * @return 返回当前对象
     */

    public MapWrapper<K, V> delete(Object key) {
        this.remove(key);
        return this;
    }

    /**
     * 调用{@link Map#remove(Object)}}方法
     *
     * @param keys
     *
     * @return 返回当前对象
     */

    public MapWrapper<K, V> delete(Object... keys) {
        for (Object key : keys) {
            this.remove(key);
        }
        return this;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> t) {
        map.putAll(t);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    @Override
    public String toString() {
        return map.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MapWrapper)) {
            return false;
        }

        MapWrapper that = (MapWrapper) o;

        if (!map.equals(that.map)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }
}