package com.nianien.core.functions;

import com.nianien.core.util.ArrayUtils;
import com.nianien.core.util.CollectionUtils;
import com.nianien.core.util.StringUtils;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 预定义{@link Param}对象
 *
 * @author scorpio
 * @version 1.0.0
 * @email tengzhe.ln@alibaba-inc.com
 */
public class Params {

    /**
     * 判断对象不为null
     *
     * @param obj
     * @return
     */
    public static Param<Object> isNotNull(Object obj) {
        return with(obj).when(Objects::nonNull);
    }

    /**
     * 判断字符串不为空
     *
     * @param str
     * @return
     */
    public static Param<String> isNotEmpty(String str) {
        return with(str, StringUtils::isNotEmpty);
    }


    /**
     * 判断集合不为空
     *
     * @param collection
     * @return
     */
    public static <T extends Collection<E>, E> Param<T> isNotEmpty(T collection) {
        return with(collection, CollectionUtils::isNotEmpty);
    }


    /**
     * 判断数组不为空
     *
     * @param array
     * @return
     */
    public static <T> Param<T[]> isNotEmpty(T[] array) {
        return with(array, ArrayUtils::isNotEmpty);
    }


    /**
     * 判断等于指定数值
     *
     * @param number
     * @param other
     * @return
     */
    public static Param<Number> eq(Number number, int other) {
        return with(number).when(e -> e.intValue() == other);
    }


    /**
     * 判断等于0
     *
     * @param number
     * @return
     */
    public static Param<Number> eq0(Number number) {
        return eq(number, 0);
    }

    /**
     * 判断不等于指定数值
     *
     * @param number
     * @param other
     * @return
     */
    public static Param<Number> ne(Number number, int other) {
        return with(number).when(e -> e.intValue() != other);
    }


    /**
     * 判断不等于0
     *
     * @param number
     * @return
     */
    public static Param<Number> ne0(Number number) {
        return ne(number, 0);
    }

    /**
     * 判断参数大于指定数值
     *
     * @param number
     * @param other
     * @return
     */
    public static Param<Number> gt(Number number, int other) {
        return with(number).when(e -> e.intValue() > other);
    }


    /**
     * 判断参数大于0
     *
     * @param number
     * @return
     */
    public static Param<Number> gt0(Number number) {
        return gt(number, 0);
    }

    /**
     * 判断参数大于等于指定数值
     *
     * @param number
     * @param other
     * @return
     */
    public static Param<Number> ge(Number number, int other) {
        return with(number).when(e -> e.intValue() >= other);
    }

    /**
     * 判断参数大于等于指定数值
     *
     * @param number
     * @return
     */
    public static Param<Number> ge0(Number number) {
        return ge(number, 0);
    }

    /**
     * 判断参数小于指定数值
     *
     * @param number
     * @param other
     * @return
     */
    public static Param<Number> lt(Number number, int other) {
        return with(number).when(e -> e.intValue() < other);
    }

    /**
     * 判断参数小于0
     *
     * @param number
     * @return
     */
    public static Param<Number> lt0(Number number) {
        return lt(number, 0);
    }

    /**
     * 判断参数小于等于指定数值
     *
     * @param number
     * @param other
     * @return
     */
    public static Param<Number> le(Number number, int other) {
        return with(number).when(e -> e.intValue() <= other);
    }

    /**
     * 判断参数小于等于0
     *
     * @param number
     * @return
     */
    public static Param<Number> le0(Number number) {
        return le(number, 0);
    }


    /**
     * 构建参数对象
     *
     * @param parameter
     * @param <T>
     * @return
     */
    public static <T> ImmutableParam<T, T> $(T parameter) {
        return with(parameter);
    }

    /**
     * 构建参数对象
     *
     * @param parameter 原始参数
     * @param <T>
     * @return
     */
    public static <T> ImmutableParam<T, T> with(T parameter) {
        return new ImmutableParam<>(parameter, e -> true, Function.identity());
    }

    /**
     * 构建参数对象,绑定条件断言
     *
     * @param parameter 原始参数
     * @param predicate 条件断言
     * @param <T>
     * @return
     */
    public static <T> ImmutableParam<T, T> with(T parameter, Predicate<T> predicate) {
        return new ImmutableParam<>(parameter, predicate, Function.identity());
    }

    /**
     * 构建参数对象,绑定条件断言和参数转换函数
     *
     * @param parameter 原始参数
     * @param predicate 条件断言
     * @param function  参数转换函数
     * @param <T>
     * @param <P>
     * @return
     */
    public static <T, P> ImmutableParam<T, P> with(P parameter,
                                                   Predicate<P> predicate,
                                                   Function<P, T> function) {
        return new ImmutableParam<>(parameter, predicate, function);
    }

}
