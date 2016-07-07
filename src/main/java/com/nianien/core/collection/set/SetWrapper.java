package com.nianien.core.collection.set;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.nianien.core.collection.CollectionWrapper;

/**
 * {@link Set}接口的包装类,包装Set实例以支持链式语法<br/>
 * 如果未提供Set实例,则默认为{@link  HashSet}实现<br/>
 *
 * @author skyfalling
 */
public class SetWrapper<E> extends CollectionWrapper<E> implements Set<E> {

    /**
     * 构造方法,默认Set实例
     */
    public SetWrapper() {
        this(new HashSet<E>());
    }

    /**
     * 构造方法,指定Set实例
     *
     * @param set
     */
    public SetWrapper(Set<E> set) {
        super(set);
    }

    /**
     * 构造方法,默认Set实例,并提供初始元素
     *
     * @param elements
     */
    public SetWrapper(E... elements) {
        super(new HashSet<E>(), elements);
    }

    /**
     * 调用{@link Set#addAll(java.util.Collection)}方法
     *
     * @param elements
     *
     * @return 返回当前对象
     */
    public SetWrapper<E> append(E... elements) {
        super.append(elements);
        return this;
    }

    /**
     * 调用{@link Set#addAll(java.util.Collection)}方法
     *
     * @param elements
     *
     * @return 返回当前对象
     */
    public SetWrapper<E> append(Collection<? extends E> elements) {
        super.append(elements);
        return this;
    }

    /**
     * 调用{@link Set#retainAll(java.util.Collection)}方法
     *
     * @param elements
     *
     * @return 返回当前对象
     */
    public SetWrapper<E> keep(E... elements) {
        super.keep(elements);
        return this;
    }

    /**
     * 调用{@link Set#retainAll(java.util.Collection)}方法
     *
     * @param elements
     *
     * @return 返回当前对象
     */
    public SetWrapper<E> keep(Collection<? extends E> elements) {
        super.keep(elements);
        return this;
    }

    /**
     * 调用{@link Set#removeAll(java.util.Collection)}
     *
     * @param elements
     *
     * @return 返回当前对象
     */
    public SetWrapper<E> delete(E... elements) {
        super.delete(elements);
        return this;
    }

    /**
     * 调用{@link Set#removeAll(java.util.Collection)}
     *
     * @param elements
     *
     * @return 返回当前对象
     */
    public SetWrapper<E> delete(Collection<? extends E> elements) {
        super.delete(elements);
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

}
