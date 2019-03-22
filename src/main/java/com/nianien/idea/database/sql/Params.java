package com.nianien.idea.database.sql;

import com.nianien.core.util.StringUtils;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 预定义参数
 *
 * @author scorpio
 * @version 1.0.0
 * @email tengzhe.ln@alibaba-inc.com
 */
public class Params {

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static Param<String> isNotEmpty(String str) {
        return with(str).when(StringUtils::isNotEmpty);
    }


    /**
     * 判断参数为空
     *
     * @param obj
     * @return
     */
    public static Param<Object> isNotNull(Object obj) {
        return with(obj).when(Objects::nonNull);
    }


    /**
     * 判断大于
     *
     * @param number
     * @param other
     * @return
     */
    public static Param<Number> gt(Number number, int other) {
        return with(number).when(e -> e.intValue() > other);
    }

    /**
     * 判断参数大于等于
     *
     * @param number
     * @param other
     * @return
     */
    public static Param<Number> ge(Number number, int other) {
        return with(number).when(e -> e.intValue() >= other);
    }

    /**
     * 判断参数为空
     *
     * @param number
     * @param other
     * @return
     */
    public static Param<Number> lt(Number number, int other) {
        return with(number).when(e -> e.intValue() < other);
    }


    /**
     * 判断参数小于等于
     *
     * @param number
     * @param other
     * @return
     */
    public static Param<Number> le(Number number, int other) {
        return with(number).when(e -> e.intValue() <= other);
    }


    /**
     * 构建复合参数对象,绑定初始参数
     *
     * @param parameter
     * @param <T>
     * @return
     */
    public static <T> CompositeParam<T, T> $(T parameter) {
        return with(parameter);
    }

    /**
     * 构建复合参数对象,绑定初始参数
     *
     * @param parameter
     * @param <T>
     * @return
     */
    public static <T> CompositeParam<T, T> with(T parameter) {
        return new CompositeParam<>(parameter, e -> true, Function.identity());
    }

    /**
     * 构建复合参数对象,绑定初始参数,参数断言以及转换函数
     *
     * @param parameter
     * @param predicate
     * @param <T>
     * @return
     */
    public static <T> CompositeParam<T, T> with(T parameter, Predicate<T> predicate) {
        return new CompositeParam<>(parameter, predicate, Function.identity());
    }

    /**
     * 构建复合参数对象,绑定初始参数和参数断言
     *
     * @param parameter
     * @param predicate
     * @param <T>
     * @return
     */
    public static <T, R> CompositeParam<T, R> with(T parameter,
                                                   Predicate<T> predicate,
                                                   Function<T, R> function) {
        return new CompositeParam<>(parameter, predicate, function);
    }

}
