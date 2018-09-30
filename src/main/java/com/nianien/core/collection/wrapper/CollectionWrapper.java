package com.nianien.core.collection.wrapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * {@link Collection}接口的包装类,包装Collection实例以支持链式语法<br/>
 *
 * @author skyfalling
 */
public class CollectionWrapper<E> implements Collection<E>, Wrapper<Collection<E>> {

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
        collection.addAll(Arrays.asList(elements));
        return this;
    }

    /**
     * 调用{@link List#addAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public CollectionWrapper<E> append(Collection<? extends E> elements) {
        collection.addAll(elements);
        return this;
    }


    /**
     * 调用{@link List#retainAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public CollectionWrapper<E> remain(E... elements) {
        collection.retainAll(Arrays.asList(elements));
        return this;
    }

    /**
     * 调用{@link List#retainAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public CollectionWrapper<E> remain(Collection<? extends E> elements) {
        collection.retainAll(elements);
        return this;
    }


    /**
     * 调用{@link List#removeAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public CollectionWrapper<E> delete(E... elements) {
        collection.removeAll(Arrays.asList(elements));
        return this;
    }

    /**
     * 调用{@link List#removeAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public CollectionWrapper<E> delete(Collection<? extends E> elements) {
        collection.removeAll(elements);
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
    public boolean removeIf(Predicate<? super E> filter) {
        return collection.removeIf(filter);
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
    public boolean equals(Object o) {
        return collection.equals(o);
    }

    @Override
    public int hashCode() {
        return collection.hashCode();
    }

    @Override
    public Spliterator<E> spliterator() {
        return collection.spliterator();
    }

    @Override
    public Stream<E> stream() {
        return collection.stream();
    }

    @Override
    public Stream<E> parallelStream() {
        return collection.parallelStream();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        collection.forEach(action);
    }

    @Override
    public Collection<E> unwrap() {
        return collection;
    }
}
