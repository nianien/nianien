package com.nianien.core.collection.wrapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * {@link Set}接口的包装类,包装Set实例以支持链式语法<br/>
 * 如果未提供Set实例,则默认为{@link  HashSet}实现<br/>
 *
 * @author skyfalling
 */
public class SetWrapper<E> implements Set<E>, Wrapper<Set<E>> {

    private Set<E> set;

    /**
     * 构造方法,默认Set实例
     */
    public SetWrapper() {
        this(new HashSet<E>());
    }

    /**
     * 构造方法,指定Set实例
     *
     * @param elements
     */
    public SetWrapper(E... elements) {
        this(new HashSet<>(), elements);
    }

    /**
     * 构造方法,默认Set实例,并提供初始元素
     *
     * @param set
     * @param elements
     */
    public SetWrapper(Set<E> set, E... elements) {
        this.set = set;
        this.append(elements);
    }

    /**
     * 调用{@link Set#addAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public SetWrapper<E> append(E... elements) {
        set.addAll(Arrays.asList(elements));
        return this;
    }

    /**
     * 调用{@link Set#addAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public SetWrapper<E> append(Collection<? extends E> elements) {
        set.addAll(elements);
        return this;
    }

    /**
     * 调用{@link Set#retainAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public SetWrapper<E> remain(E... elements) {
        set.retainAll(Arrays.asList(elements));
        return this;
    }

    /**
     * 调用{@link Set#retainAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public SetWrapper<E> remain(Collection<? extends E> elements) {
        set.retainAll(elements);
        return this;
    }

    /**
     * 调用{@link Set#removeAll(java.util.Collection)}
     *
     * @param elements
     * @return 返回当前对象
     */
    public SetWrapper<E> delete(E... elements) {
        set.removeAll(Arrays.asList(elements));
        return this;
    }

    /**
     * 调用{@link Set#removeAll(java.util.Collection)}
     *
     * @param elements
     * @return 返回当前对象
     */
    public SetWrapper<E> delete(Collection<? extends E> elements) {
        set.removeAll(elements);
        return this;
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return set.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return set.iterator();
    }

    @Override
    public Object[] toArray() {
        return set.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return set.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return set.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return set.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return set.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return set.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return set.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return set.removeAll(c);
    }

    @Override
    public void clear() {
        set.clear();
    }

    @Override
    public boolean equals(Object o) {
        return set.equals(o);
    }

    @Override
    public int hashCode() {
        return set.hashCode();
    }

    @Override
    public Spliterator<E> spliterator() {
        return set.spliterator();
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return set.removeIf(filter);
    }

    @Override
    public Stream<E> stream() {
        return set.stream();
    }

    @Override
    public Stream<E> parallelStream() {
        return set.parallelStream();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        set.forEach(action);
    }

    @Override
    public Set<E> unwrap() {
        return set;
    }
}
