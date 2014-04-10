package com.nianien.core.collection.map;

import com.nianien.core.function.Function;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Map接口的包装类,对给定的Map实例进行包装以支持链式语法构建Map对象<br/>
 * 如果未提供Map实例,则默认对HashMap进行包装<br/>
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
        mapObject = map;
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
    public MapWrapper(Map<K, V> map, K key, V value) {
        this.mapObject = map;
        this.put(key, value);
    }

    public int size() {
        return mapObject.size();
    }

    public boolean isEmpty() {
        return mapObject.isEmpty();
    }

    public boolean containsKey(Object key) {
        return mapObject.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return mapObject.containsValue(value);
    }

    public V get(Object key) {
        return mapObject.get(key);
    }

    public V put(K key, V value) {
        return mapObject.put(key, value);
    }

    public V remove(Object key) {
        return mapObject.remove(key);
    }

    public void putAll(Map<? extends K, ? extends V> t) {
        mapObject.putAll(t);
    }

    public void clear() {
        mapObject.clear();
    }

    public Set<K> keySet() {
        return mapObject.keySet();
    }

    public Collection<V> values() {
        return mapObject.values();
    }

    public Set<Entry<K, V>> entrySet() {
        return mapObject.entrySet();
    }


    /**
     * 调用{@link Map#put(K, V)}方法,返回当前对象
     *
     * @param key
     * @param value
     * @return
     */
    public MapWrapper<K, V> append(K key, V value) {
        this.put(key, value);
        return this;
    }

    /**
     * 调用{@link Map#putAll(java.util.Map)}方法,返回当前对象
     *
     * @param map
     * @return
     */
    public MapWrapper<K, V> appendAll(Map<? extends K, ? extends V> map) {
        mapObject.putAll(map);
        return this;
    }

    /**
     * 如果不存在key值,则调用{@link Map#put(K, V)}方法
     *
     * @param key
     * @param value
     * @return
     */
    public MapWrapper<K, V> putIfAbsent(K key, V value) {
        if (!containsKey(key)) {
            mapObject.put(key, value);
        }
        return this;
    }

    /**
     * 如果不存在key值,则调用{@link Map#put(K, V)}方法,value为function的执行结果
     *
     * @param key
     * @param function
     * @return
     */
    public MapWrapper<K, V> putIfAbsent(K key, Function<K, V> function) {
        if (!containsKey(key)) {
            mapObject.put(key, function.call(key));
        }
        return this;
    }


    @Override
    public String toString() {
        return mapObject.toString();
    }
}