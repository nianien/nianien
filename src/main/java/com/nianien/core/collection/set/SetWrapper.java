package com.nianien.core.collection.set;

import java.util.*;

/**
 * {@link Set}接口的包装类,包装Set实例以支持链式语法<br/>
 * 如果未提供Set实例,则默认为{@link  HashSet}实现<br/>
 *
 * @author skyfalling
 */
public class SetWrapper<E> implements Set<E> {

    private Set<E> setObject;


    /**
     * 默认包装HashSet实例
     */
    public SetWrapper() {
        this(new HashSet<E>());
    }

    /**
     * 指定Set实例
     *
     * @param set
     */
    public SetWrapper(Set<E> set) {
        this.setObject = set;
    }

    /**
     * 指定默认元素
     *
     * @param elements
     */
    public SetWrapper(E... elements) {
        this(new HashSet<E>(), elements);
    }

    /**
     * 指定Set实例和默认元素
     *
     * @param set
     * @param elements
     */
    protected SetWrapper(Set<E> set, E... elements) {
        this.setObject = set;
        this.append(elements);
    }

    /**
     * 调用{@link Set#addAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public SetWrapper<E> append(E... elements) {
        return append(Arrays.asList(elements));
    }

    /**
     * 调用{@link Set#addAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public SetWrapper<E> append(Collection<E> elements) {
        this.addAll(elements);
        return this;
    }

    /**
     * 调用{@link Set#retainAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public SetWrapper<E> keep(E... elements) {
        return keep(Arrays.asList(elements));
    }

    /**
     * 调用{@link Set#retainAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public SetWrapper<E> keep(Collection<E> elements) {
        this.retainAll(elements);
        return this;
    }

    /**
     * 调用{@link Set#removeAll(java.util.Collection)}
     *
     * @param elements
     * @return 返回当前对象
     */
    public SetWrapper<E> delete(E... elements) {
        return delete(Arrays.asList(elements));
    }

    /**
     * 调用{@link Set#removeAll(java.util.Collection)}
     *
     * @param elements
     * @return 返回当前对象
     */
    public SetWrapper<E> delete(Collection<E> elements) {
        this.removeAll(elements);
        return this;
    }


    /**
     * 如果不存在元素e,则调用{@link Set#add(Object)}方法
     *
     * @return 返回当前对象
     */
    public SetWrapper<E> addIfAbsent(boolean expression, E e) {
        if (!this.contains(e)) {
            this.add(e);
        }
        return this;
    }

    /**
     * 如果表达式expression为true,则调用{@link Set#add(Object)}方法
     *
     * @return 返回当前对象
     */
    public SetWrapper<E> addIf(boolean expression, E e) {
        if (expression) {
            this.add(e);
        }
        return this;
    }

    @Override
    public int size() {
        return setObject.size();
    }

    @Override
    public boolean isEmpty() {
        return setObject.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return setObject.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return setObject.iterator();
    }

    @Override
    public Object[] toArray() {
        return setObject.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return setObject.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return setObject.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return setObject.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return setObject.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return setObject.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return setObject.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return setObject.removeAll(c);
    }

    @Override
    public void clear() {
        setObject.clear();
    }

    @Override
    public String toString() {
        return setObject.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SetWrapper)) return false;

        SetWrapper that = (SetWrapper) o;

        if (!setObject.equals(that.setObject)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return setObject.hashCode();
    }
}
