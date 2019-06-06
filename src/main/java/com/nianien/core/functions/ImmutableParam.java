package com.nianien.core.functions;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 不可变参数类型,通过条件断言和转换函数实现参数的校验和使用
 *
 * @param <T> 输出参数类型
 * @param <P> 输入参数类型
 * @author scorpio
 * @version 1.0.0
 * @email tengzhe.ln@alibaba-inc.com
 */

public class ImmutableParam<T, P> implements Param<T> {

    /**
     * 初始参数
     */
    private P parameter;
    /**
     * 条件断言,用于条件判定
     */
    private Predicate<P> condition;
    /**
     * 转换函数,用于参数处理
     */
    private Function<P, T> resolver;

    /**
     * @param parameter 原始参数
     * @param condition 条件断言
     * @param resolver  转换函数
     */
    public ImmutableParam(P parameter, Predicate<P> condition, Function<P, T> resolver) {
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
    public ImmutableParam<P, P> when(Predicate<P> condition) {
        return new ImmutableParam<>(parameter, condition, Function.identity());
    }


    /**
     * 将当前条件取反
     *
     * @return
     */
    public ImmutableParam<T, P> negate() {
        return new ImmutableParam<>(parameter, condition.negate(), resolver);
    }

    /**
     * 绑定转换函数
     *
     * @param resolver
     * @param <T>
     * @return
     */
    public <T> ImmutableParam<T, P> then(Function<P, T> resolver) {
        return new ImmutableParam<>(parameter, condition, resolver);
    }

    /**
     * 验证条件
     *
     * @return
     */
    @Override
    public boolean test() {
        return condition.test(parameter);
    }

    /**
     * 返回处理后的参数
     *
     * @return
     */
    @Override
    public T get() {
        return resolver.apply(parameter);
    }


}
