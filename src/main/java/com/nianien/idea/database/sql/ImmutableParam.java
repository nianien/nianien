package com.nianien.idea.database.sql;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 不可变参数类型,通过条件断言和转换函数实现参数的校验和使用
 *
 * @param <U> 初始参数类型
 * @param <V> 处理后的参数类型
 * @author scorpio
 * @version 1.0.0
 * @email tengzhe.ln@alibaba-inc.com
 */

public class ImmutableParam<U, V> implements Param<V> {

    /**
     * 初始参数
     */
    private U parameter;
    /**
     * 条件断言,用于条件判定
     */
    private Predicate<U> condition;
    /**
     * 转换函数,用于参数处理
     */
    private Function<U, V> resolver;

    /**
     * @param parameter 原始参数
     * @param condition 条件断言
     * @param resolver  转换函数
     */
    public ImmutableParam(U parameter, Predicate<U> condition, Function<U, V> resolver) {
        this.parameter = parameter;
        this.condition = condition;
        this.resolver = resolver;
    }

    /**
     * 绑定指定条件
     *
     * @param condition
     * @return
     */
    public ImmutableParam<U, U> when(Predicate<U> condition) {
        return new ImmutableParam<>(parameter, condition, Function.identity());
    }


    /**
     * 将当前条件取反
     *
     * @return
     */
    public ImmutableParam<U, V> negate() {
        return new ImmutableParam<>(parameter, condition.negate(), resolver);
    }

    /**
     * 绑定转换函数
     *
     * @param resolver
     * @param <R>
     * @return
     */
    public <R> ImmutableParam<U, R> then(Function<U, R> resolver) {
        return new ImmutableParam<>(parameter, condition, resolver);
    }

    /**
     * 验证条件
     *
     * @return
     */
    @Override
    public boolean validate() {
        return condition.test(parameter);
    }

    /**
     * 返回处理后的参数
     *
     * @return
     */
    @Override
    public V get() {
        return resolver.apply(parameter);
    }


}
