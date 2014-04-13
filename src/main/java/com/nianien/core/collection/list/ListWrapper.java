package com.nianien.core.collection.list;

import com.nianien.core.collection.CollectionWrapper;

import java.util.*;

/**
 * {@link List}接口的包装类,包装List实例以支持链式语法<br/>
 * 如果未提供List实例,则默认为{@link ArrayList}实现<br/>
 *
 * @author skyfalling
 */
public class ListWrapper<E> extends CollectionWrapper<E> implements List<E> {

    private List<E> list;

    /**
     * 构造方法,默认List实例
     */
    public ListWrapper() {
        this(new ArrayList<E>());
    }

    /**
     * 构造方法,指定List实例
     *
     * @param list
     */
    public ListWrapper(List<E> list) {
        super(list);
    }

    /**
     * 构造方法,默认List实例并提供初始元素
     *
     * @param elements
     */
    public ListWrapper(E... elements) {
        super(new ArrayList<E>(), elements);
        this.list = (List<E>) collection;
    }


    /**
     * 调用{@link List#add(Object)}方法
     *
     * @param elements
     * @return
     */
    public ListWrapper<E> append(E... elements) {
        super.append(elements);
        return this;
    }

    /**
     * 调用{@link List#addAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public ListWrapper<E> append(Collection<? extends E> elements) {
        super.append(elements);
        return this;
    }

    /**
     * 调用{@link List#add(int, Object)}方法
     *
     * @param index
     * @param elements
     * @return 返回当前对象
     */
    public ListWrapper<E> insert(int index, E... elements) {
        return insert(index, Arrays.asList(elements));
    }

    /**
     * 调用{@link List#addAll(int, java.util.Collection)}方法
     *
     * @param index
     * @param elements
     * @return 返回当前对象
     */
    public ListWrapper<E> insert(int index, Collection<? extends E> elements) {
        this.addAll(index, elements);
        return this;
    }

    /**
     * 调用{@link List#retainAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public ListWrapper<E> keep(E... elements) {
        super.keep(elements);
        return this;
    }

    /**
     * 调用{@link List#retainAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public ListWrapper<E> keep(Collection<? extends E> elements) {
        super.keep(elements);
        return this;
    }

    /**
     * 调用{@link List#remove(int)}方法
     *
     * @param index
     * @return 返回当前对象
     */
    public ListWrapper<E> delete(int index) {
        this.remove(index);
        return this;
    }

    /**
     * 调用{@link List#removeAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public ListWrapper<E> delete(E... elements) {
        super.delete(elements);
        return this;
    }

    /**
     * 调用{@link List#removeAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public ListWrapper<E> delete(Collection<? extends E> elements) {
        super.delete(elements);
        return this;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return list.addAll(index, c);
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
    }

    @Override
    public E remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }
}
