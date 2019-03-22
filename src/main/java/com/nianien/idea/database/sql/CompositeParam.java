package com.nianien.idea.database.sql;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 复合参数类型,支持参数类型转换
 *
 * @param <U> 初始参数类型
 * @param <V> 转换后参数类型
 * @author scorpio
 * @version 1.0.0
 * @email tengzhe.ln@alibaba-inc.com
 */

public class CompositeParam<U, V> implements Param<V> {

    /**
     * 参数
     */
    private U parameter;
    /**
     * 条件断言,用于参数判定
     */
    private Predicate<U> condition;
    /**
     * 转换函数,用于参数转换
     */
    private Function<U, V> converter;

    /**
     * @param parameter 原始参数
     * @param condition 条件断言
     * @param converter 转换函数
     */
    public CompositeParam(U parameter, Predicate<U> condition, Function<U, V> converter) {
        this.parameter = parameter;
        this.condition = condition;
        this.converter = converter;
    }

    /**
     * 返回绑定该断言的参数对象
     *
     * @param predicate
     * @return
     */
    public CompositeParam<U, U> when(Predicate<U> predicate) {
        return new CompositeParam<>(parameter, predicate, Function.identity());
    }

    /**
     * 返回绑定该函数的参数对象
     *
     * @param function
     * @param <R>
     * @return
     */
    public <R> CompositeParam<U, R> then(Function<U, R> function) {
        return new CompositeParam<>(parameter, condition, function);
    }

    @Override
    public boolean isValid() {
        return condition.test(parameter);
    }

    @Override
    public V get() {
        return converter.apply(parameter);
    }


}
