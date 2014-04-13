package com.nianien.core.collection.map;

import com.nianien.core.function.Function;

import java.util.*;

/**
 * {@link Map}接口的包装类,包装Map实例以支持链式语法<br/>
 * 如果未提供Map实例,则默认为{@link HashMap}实现<br/>
 *
 * @author skyfalling
 */
public class MapWrapper<K, V> implements Map<K, V> {

    private Map<K, V> mapObject;

    /**
     * 构造方法,提供默认Map实例
     */
    public MapWrapper() {
        this(new HashMap<K, V>());
    }

    /**
     * 构造方法,提供Map实例
     *
     * @param map
     */
    public MapWrapper(Map<K, V> map) {
        this.mapObject = map;
    }

    /**
     * 构造方法,提供默认Map实例以及初始键值对
     *
     * @param k
     * @param v
     */
    public MapWrapper(K k, V v) {
        this(new HashMap<K, V>(), k, v);
    }

    /**
     * 构造方法,提供Map实例以及初始键值对
     *
     * @param map
     * @param key
     * @param value
     */
    protected MapWrapper(Map<K, V> map, K key, V value) {
        this.mapObject = map;
        this.put(key, value);
    }

    /**
     * 调用{@link Map#put(K, V)}方法
     *
     * @param key
     * @param value
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
     * @return 返回当前对象
     */
    public MapWrapper<K, V> append(Map<? extends K, ? extends V> map) {
        this.putAll(map);
        return this;
    }

    /**
     * 调用{@link Map#remove(Object)}}方法
     *
     * @param key
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
     * @return 返回当前对象
     */

    public MapWrapper<K, V> delete(Object... keys) {
        for (Object key : keys) {
            this.remove(key);
        }
        return this;
    }

    /**
     * 调用{@link Map#remove(Object)}}方法
     *
     * @param keys
     * @return 返回当前对象
     */

    public MapWrapper<K, V> delete(Collection keys) {
        for (Object key : keys) {
            this.remove(key);
        }
        return this;
    }

    /**
     * 如果不存在key值,则调用{@link Map#put(K, V)}方法,value为function的调用结果
     *
     * @param key
     * @param function
     * @return
     */
    public MapWrapper<K, V> putIfAbsent(K key, Function<K, V> function) {
        if (!containsKey(key)) {
            this.put(key, function.call(key));
        }
        return this;
    }

    /**
     * 如果表达式expression为true,,则调用{@link Map#put(K, V)}方法
     *
     * @param key
     * @param value
     * @return 返回当前对象
     */
    public MapWrapper<K, V> putIf(boolean expression, K key, V value) {
        if (expression) {
            this.put(key, value);
        }
        return this;
    }


    @Override
    public int size() {
        return mapObject.size();
    }

    @Override
    public boolean isEmpty() {
        return mapObject.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return mapObject.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return mapObject.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return mapObject.get(key);
    }

    @Override
    public V put(K key, V value) {
        return mapObject.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return mapObject.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> t) {
        mapObject.putAll(t);
    }

    @Override
    public void clear() {
        mapObject.clear();
    }

    @Override
    public Set<K> keySet() {
        return mapObject.keySet();
    }

    @Override
    public Collection<V> values() {
        return mapObject.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return mapObject.entrySet();
    }

    @Override
    public String toString() {
        return mapObject.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MapWrapper)) return false;

        MapWrapper that = (MapWrapper) o;

        if (!mapObject.equals(that.mapObject)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return mapObject.hashCode();
    }
}