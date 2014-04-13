package com.nianien.core.collection;

import com.nianien.core.collection.map.MapWrapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * {@link Collection}接口的包装类,包装Collection实例以支持链式语法<br/>
 *
 * @author skyfalling
 */
public class CollectionWrapper<E> implements Collection<E> {

    protected Collection<E> collection;

    /**
     * 构造方法,指定Collection实例
     *
     * @param collection
     */
    protected CollectionWrapper(Collection<E> collection, E... elements) {
        this.collection = collection;
        this.append(elements);
    }


    /**
     * 调用{@link java.util.List#add(Object)}方法
     *
     * @param elements
     * @return
     */
    public CollectionWrapper<E> append(E... elements) {
        return append(Arrays.asList(elements));
    }

    /**
     * 调用{@link List#addAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public CollectionWrapper<E> append(Collection<? extends E> elements) {
        this.addAll(elements);
        return this;
    }


    /**
     * 调用{@link List#retainAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public CollectionWrapper<E> keep(E... elements) {
        return keep(Arrays.asList(elements));
    }

    /**
     * 调用{@link List#retainAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public CollectionWrapper<E> keep(Collection<? extends E> elements) {
        this.retainAll(elements);
        return this;
    }


    /**
     * 调用{@link List#removeAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public CollectionWrapper<E> delete(E... elements) {
        return delete(Arrays.asList(elements));
    }

    /**
     * 调用{@link List#removeAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public CollectionWrapper<E> delete(Collection<? extends E> elements) {
        this.removeAll(elements);
        return this;
    }


    @Override
    public int size() {
        return collection.size();
    }

    @Override
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return collection.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return collection.iterator();
    }

    @Override
    public Object[] toArray() {
        return collection.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return collection.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return collection.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return collection.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return collection.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return collection.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return collection.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return collection.retainAll(c);
    }

    @Override
    public void clear() {
        collection.clear();
    }

    @Override
    public String toString() {
        return collection.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MapWrapper)) return false;

        CollectionWrapper that = (CollectionWrapper) o;

        if (!collection.equals(that.collection)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return collection.hashCode();
    }
}
