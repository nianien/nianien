package com.nianien.core.collection.list;

import java.util.*;

/**
 * {@link List}接口的包装类,包装List实例以支持链式语法<br/>
 * 如果未提供List实例,则默认为{@link HashMap}实现<br/>
 *
 * @author skyfalling
 */
public class ListWrapper<E> implements List<E> {

    private List<E> listObject;


    /**
     * 构造方法
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
        this.listObject = list;
    }

    /**
     * 构造方法,添加默认元素
     *
     * @param elements
     */
    public ListWrapper(E... elements) {
        this(new ArrayList<E>(), elements);
    }

    /**
     * 构造方法,指定List实例并添加默认元素
     *
     * @param list
     * @param elements
     */
    protected ListWrapper(List<E> list, E... elements) {
        this.listObject = list;
        this.append(elements);
    }

    /**
     * 调用{@link List#add(Object)}方法
     *
     * @param elements
     * @return
     */
    public List<E> append(E... elements) {
        return append(Arrays.asList(elements));
    }

    /**
     * 调用{@link List#addAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public List<E> append(Collection<? extends E> elements) {
        this.addAll(elements);
        return this;
    }

    /**
     * 调用{@link List#add(int, Object)}方法
     *
     * @param index
     * @param elements
     * @return 返回当前对象
     */
    public List<E> insert(int index, E... elements) {
        return insert(index, Arrays.asList(elements));
    }

    /**
     * 调用{@link List#addAll(int, java.util.Collection)}方法
     *
     * @param index
     * @param elements
     * @return 返回当前对象
     */
    public List<E> insert(int index, Collection<? extends E> elements) {
        this.addAll(index, elements);
        return this;
    }

    /**
     * 调用{@link List#retainAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public List<E> keep(E... elements) {
        return keep(Arrays.asList(elements));
    }

    /**
     * 调用{@link List#retainAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public List<E> keep(Collection<? extends E> elements) {
        this.retainAll(elements);
        return this;
    }

    /**
     * 调用{@link List#remove(int)}方法
     *
     * @param index
     * @return 返回当前对象
     */
    public List<E> delete(int index) {
        this.remove(index);
        return this;
    }

    /**
     * 调用{@link List#removeAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public List<E> delete(E... elements) {
        return delete(Arrays.asList(elements));
    }

    /**
     * 调用{@link List#removeAll(java.util.Collection)}方法
     *
     * @param elements
     * @return 返回当前对象
     */
    public List<E> delete(Collection<? extends E> elements) {
        this.removeAll(elements);
        return this;
    }


    @Override
    public int size() {
        return listObject.size();
    }

    @Override
    public boolean isEmpty() {
        return listObject.isEmpty();
    }


    @Override
    public boolean contains(Object o) {
        return listObject.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return listObject.iterator();
    }

    @Override
    public Object[] toArray() {
        return listObject.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return listObject.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return listObject.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return listObject.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return listObject.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return listObject.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return listObject.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return listObject.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return listObject.retainAll(c);
    }

    @Override
    public void clear() {
        listObject.clear();
    }

    @Override
    public E get(int index) {
        return listObject.get(index);
    }

    @Override
    public E set(int index, E element) {
        return listObject.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        listObject.add(index, element);
    }

    @Override
    public E remove(int index) {
        return listObject.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return listObject.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return listObject.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return listObject.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return listObject.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return listObject.subList(fromIndex, toIndex);
    }

    @Override
    public String toString() {
        return listObject.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListWrapper)) return false;

        ListWrapper that = (ListWrapper) o;

        if (!listObject.equals(that.listObject)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return listObject.hashCode();
    }
}
