package com.nianien.core.functions;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 构建Fluent API的Function工具<br/>
 * <p>
 * <ul>
 * <li>
 * 执行函数，并持有函数执行结果<br/>
 * {@link F#$(Function)}
 * </li>
 * <li>
 * 执行函数，并忽略函数执行结果（持有对象不变）<br/>
 * {@link F#$$(Consumer)}
 * </li>
 * <li>
 * 绑定方法，并忽略返回结果（持有对象不变）<br/>
 * {@link F#consumer(Consumer)} & {@link F#consumer2(BiConsumer)} & {@link F#consumer3(TriConsumer)}
 * </li>
 * <li>
 * 绑定方法，并持有返回结果（持有对象不变）<br/>
 * {@link F#function(Function)} & {@link F#function2(BiFunction)} & {@link F#function3(TriFunction)}
 * </li>
 * </ul>
 * </p>
 *
 * @param <T> 输出参数类型
 * @param <U> 函数的第一个参数类型
 * @param <V> 函数的第二个参数类型
 * @author scorpio
 * @version 1.0.0
 * @email tengzhe.ln@alibaba-inc.com
 */
public class F<T, U, V> {

    /**
     * 绑定对象
     */
    protected T target;

    /**
     * 绑定的无参方法
     */
    protected Consumer<T> c1;
    /**
     * 含有单个参数的绑定方法
     */
    protected BiConsumer<T, U> c2;
    /**
     * 含有两个参数的绑定方法
     */
    protected TriConsumer<T, U, V> c3;

    /**
     * 绑定的无参方法
     */
    protected Function<T, T> f1;
    /**
     * 含有单个参数的绑定方法
     */
    protected BiFunction<T, U, T> f2;
    /**
     * 含有两个参数的绑定方法
     */
    protected TriFunction<T, U, V, T> f3;

    /**
     * 私有方法
     *
     * @param target
     */
    protected F(T target) {
        this.target = target;
    }


    /**
     * 获取持有对象
     *
     * @return
     */
    public T $() {
        return target;
    }

    /**
     * 持有对象
     *
     * @param target
     * @param <T>
     * @return
     */
    public static <T, U, V> F<T, U, V> $(T target) {
        return new F<>(target);
    }

    /**
     * 执行函数，持有返回结果
     *
     * @param function
     * @param <R>      函数返回类型
     * @return
     */
    public <R> F<R, U, V> $(Function<T, R> function) {
        F<R, U, V> f = (F<R, U, V>) this;
        if (target != null) {
            f.target = function.apply(target);
        }
        return f;
    }

    /**
     * 执行函数，参数为p，持有返回结果
     *
     * @param function
     * @param p        函数参数
     * @param <R>      函数返回类型
     * @param <P>      参数类型
     * @return
     */
    public <R, P> F<R, U, V> $(BiFunction<T, P, R> function, P p) {
        F<R, U, V> f = (F<R, U, V>) this;
        if (target != null) {
            f.target = function.apply(target, p);
        }
        return f;
    }

    /**
     * 执行函数，参数为p和q，持有返回结果
     *
     * @param function
     * @param p        第一个参数
     * @param q        第二个参数
     * @param <R>      函数返回类型
     * @param <P>      第一个参数类型
     * @param <Q>      第二个参数类型
     * @return
     */
    public <R, P, Q> F<R, U, V> $(TriFunction<T, P, Q, R> function, P p, Q q) {
        F<R, U, V> f = (F<R, U, V>) this;
        if (target != null) {
            f.target = function.apply(target, p, q);
        }
        return f;
    }


    /**
     * 执行函数，忽略返回结果
     *
     * @param consumer
     * @return
     */
    public F<T, U, V> $$(Consumer<T> consumer) {
        if (target != null) {
            consumer.accept(target);
        }
        return this;
    }

    /**
     * 执行函数，参数为p，忽略返回结果
     *
     * @param consumer
     * @param p
     * @param <P>      参数类型
     * @return
     */
    public <P> F<T, U, V> $$(BiConsumer<T, P> consumer, P p) {
        if (target != null) {
            consumer.accept(target, p);
        }
        return this;
    }


    /**
     * 执行函数，参数为p和q，忽略返回结果
     *
     * @param consumer
     * @param p        第一个参数
     * @param q        第二个参数
     * @param <P>      第一个参数类型
     * @param <P>      第二个参数类型
     * @return
     */
    public <P, Q> F<T, U, V> $$(TriConsumer<T, P, Q> consumer, P p, Q q) {
        if (target != null) {
            consumer.accept(target, p, q);
        }
        return this;
    }


    /**
     * 绑定无参方法，执行{@link #accept()}进行方法调用
     *
     * @param consumer
     * @return
     */
    public F<T, U, V> consumer(Consumer<T> consumer) {
        this.c1 = consumer;
        return this;
    }


    /**
     * 绑定方法，执行{@link #accept(U)}进行方法调用
     *
     * @param consumer
     * @return
     */
    public <U> F<T, U, V> consumer2(BiConsumer<T, U> consumer) {
        F<T, U, V> f = (F<T, U, V>) this;
        f.c2 = consumer;
        return f;
    }


    /**
     * 绑定方法，执行{@link #accept(U, V)}进行方法调用
     *
     * @param consumer
     * @return
     */
    public <U, V> F<T, U, V> consumer3(TriConsumer<T, U, V> consumer) {
        F<T, U, V> f = (F<T, U, V>) this;
        f.c3 = consumer;
        return f;
    }

    /**
     * 绑定无参方法，执行{@link #apply()}进行方法调用
     *
     * @param function
     * @return
     */
    public F<T, U, V> function(Function<T, T> function) {
        this.f1 = function;
        return this;
    }

    /**
     * 绑定方法，执行{@link #apply(U)}进行方法调用
     *
     * @param function
     * @param <U>      参数类型
     * @return
     */
    public <U> F<T, U, V> function2(BiFunction<T, U, T> function) {
        F<T, U, V> f = (F<T, U, V>) this;
        f.f2 = function;
        return f;
    }

    /**
     * 绑定方法，执行{@link #apply(U, V)}进行方法调用
     *
     * @param function
     * @param <U>      第一个参数类型
     * @param <V>      第二个参数类型
     * @return
     */
    public <U, V> F<T, U, V> function3(TriFunction<T, U, V, T> function) {
        F<T, U, V> f = (F<T, U, V>) this;
        f.f3 = function;
        return f;
    }


    /**
     * 调用无参方法，忽略方法执行结果
     *
     * @return
     */
    public F<T, U, V> accept() {
        if (c1 != null) {
            c1.accept(target);
        }
        return this;
    }

    /**
     * 执行绑定方法，方法参数为u，忽略方法执行结果
     *
     * @param u 方法参数
     * @return
     */
    public F<T, U, V> accept(U u) {
        if (u instanceof Param) {
            return accept((Param) u);
        }
        if (c2 != null) {
            c2.accept(target, u);
        }
        return this;
    }


    /**
     * 执行绑定方法，方法参数为u和v，忽略方法执行结果
     *
     * @param u 第一个方法参数
     * @param v 第二个方法参数
     * @return
     */
    public F<T, U, V> accept(U u, V v) {
        if (c3 != null) {
            c3.accept(target, u, v);
        }
        return this;
    }

    /**
     * 如果{@link Param#test()}为真，执行绑定方法，方法参数为{@link Param#get()}，忽略方法执行结果
     *
     * @param u 条件参数
     * @return
     */
    public F<T, U, V> accept(Param<U> u) {
        if (c2 != null && u.test()) {
            c2.accept(target, u.get());
        }
        return this;
    }

    /**
     * 执行绑定的无参方法，持有方法执行结果
     *
     * @return
     */
    public F<T, U, V> apply() {
        if (f1 != null) {
            target = f1.apply(target);
        }
        return this;
    }


    /**
     * 执行绑定方法，方法参数为u，持有方法执行结果
     *
     * @param u 方法参数
     * @return
     */
    public F<T, U, V> apply(U u) {
        if (u instanceof Param) {
            return apply((Param) u);
        }
        if (f2 != null) {
            target = f2.apply(target, u);
        }
        return this;
    }


    /**
     * 执行绑定方法，方法参数为u和v，持有方法执行结果
     *
     * @param u 第一个方法参数
     * @param v 第二个方法参数
     * @return
     */
    public F<T, U, V> apply(U u, V v) {
        if (f3 != null) {
            target = f3.apply(target, u, v);
        }
        return this;
    }

    /**
     * 如果{@link Param#test()}为真，执行绑定方法，方法参数为{@link Param#get()}，持有方法执行结果
     *
     * @param u 条件参数
     * @return
     */
    public F<T, U, V> apply(Param<U> u) {
        if (f2 != null && u.test()) {
            target = f2.apply(target, u.get());
        }
        return this;
    }
}
