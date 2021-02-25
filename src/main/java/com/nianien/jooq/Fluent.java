package com.nianien.jooq;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 基于lambda的流式条件判断
 *
 * @author scorpio
 * @version 1.0.0
 * @email tengzhe.ln@alibaba-inc.com
 */
public class Fluent<T> {

    /**
     * 绑定对象
     */
    protected T target;

    /**
     * 私有方法
     *
     * @param target
     */
    protected Fluent(T target) {
        this.target = target;
    }

    /**
     * 构建对象
     *
     * @param target
     * @param <T>
     * @return
     */
    public static <T> Fluent<T> $(T target) {
        return new Fluent<>(target);
    }

    /**
     * 执行函数,重新绑定返回结果
     *
     * @param function
     * @param <U>      函数返回类型
     * @return
     */
    public <U> Fluent<U> $(Function<T, U> function) {
        return new Fluent<>(function.apply(target));
    }

    /**
     * 执行函数,重新绑定返回结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @param <P>      函数第二个参数类型
     * @return
     */
    public <P> Fluent<T> $(P param, BiFunction<T, P, T> function) {
        return new Fluent<>(function.apply(target, param));
    }

    /**
     * 绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @return
     */
    public <P> Fluent<T> with(P param, BiFunction<T, P, T> function) {
        return when(param, (p) -> true, function);
    }


    /**
     * 迭代绑定每个元素的函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @return
     */
    public <P> Fluent<T> batch(Collection<P> param, BiFunction<T, P, T> function) {
        for (P p : param) {
            this.with(p, function);
        }
        return this;
    }

    /**
     * 迭代绑定每个元素的函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @return
     */
    public <P> Fluent<T> batch(P[] param, BiFunction<T, P, T> function) {
        for (P p : param) {
            this.with(p, function);
        }
        return this;
    }

    /**
     * 当断言predicate为true,绑定函数结果
     *
     * @param param     条件参数
     * @param predicate 断言表达式
     * @param function  函数表达式
     * @param <P>       参数类型&函数第二个参数类型
     * @return
     */
    public <P> Fluent<T> when(P param, boolean predicate, BiFunction<T, P, T> function) {
        if (predicate) {
            target = function.apply(target, param);
        }
        return this;
    }

    /**
     * 当断言predicate为true,绑定函数结果
     *
     * @param param     条件参数
     * @param predicate 断言表达式
     * @param function  函数表达式
     * @param <P>       参数类型&函数第二个参数类型
     * @return
     */
    public <P> Fluent<T> when(P param, Predicate<P> predicate, BiFunction<T, P, T> function) {
        return when(param, predicate.test(param), function);
    }

    /**
     * 当参数param为true,绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @return
     */
    public Fluent<T> when(boolean param, BiFunction<T, Boolean, T> function) {
        return when(param, param, function);
    }

    /**
     * 当参数param为false,绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @return
     */
    public Fluent<T> whenNot(boolean param, BiFunction<T, Boolean, T> function) {
        return when(param, !param, function);
    }

    /**
     * 当参数不为null,绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @return
     */
    public <P> Fluent<T> notNull(P param, BiFunction<T, P, T> function) {
        return when(param, param != null, function);
    }

    /**
     * 当参数不为空,绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @return
     */
    public Fluent<T> notEmpty(String param, BiFunction<T, String, T> function) {
        return when(param, param != null && !param.isEmpty(), function);
    }


    /**
     * 当参数不为空,绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @return
     */
    public <P> Fluent<T> notEmpty(Collection<P> param, BiFunction<T, Collection<P>, T> function) {
        return when(param, param != null && !param.isEmpty(), function);
    }

    /**
     * 当参数不为空,绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @return
     */
    public <P> Fluent<T> notEmpty(P[] param, BiFunction<T, P[], T> function) {
        return when(param, param != null && param.length > 0, function);
    }

    /**
     * 当参数param>0,绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @return
     */
    public <P extends Number> Fluent<T> gt0(P param, BiFunction<T, P, T> function) {
        return when(param, param != null && param.intValue() > 0, function);
    }

    /**
     * 当参数param>=0,绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @return
     */
    public <P extends Number> Fluent<T> ge0(P param, BiFunction<T, P, T> function) {
        return when(param, param != null && param.intValue() >= 0, function);
    }

    /**
     * 当参数param<0,绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @return
     */
    public <P extends Number> Fluent<T> lt0(P param, BiFunction<T, P, T> function) {
        return when(param, param != null && param.intValue() < 0, function);
    }

    /**
     * 当参数param<=0,绑定函数结果
     *
     * @param param    条件参数
     * @param function 函数表达式
     * @return
     */
    public <P extends Number> Fluent<T> le0(P param, BiFunction<T, P, T> function) {
        return when(param, param != null && param.intValue() <= 0, function);
    }


    /**
     * 获取绑定对象
     *
     * @return
     */
    public T $() {
        return target;
    }

}
