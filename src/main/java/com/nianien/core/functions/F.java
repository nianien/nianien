package com.nianien.core.functions;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 构建Fluent API的Function工具，支持绑定函数流式调用
 *
 * @author scorpio
 * @version 1.0.0
 * @email tengzhe.ln@alibaba-inc.com
 */
public class F<T, U, V> {

    /**
     * 绑定对象
     */
    protected T target;

    protected Consumer<T> c1;
    protected BiConsumer<T, U> c2;
    protected TriConsumer<T, U, V> c3;

    protected Function<T, T> f1;
    protected BiFunction<T, U, T> f2;
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
     * 获取绑定对象
     *
     * @return
     */
    public T $() {
        return target;
    }

    /**
     * 绑定对象
     *
     * @param target
     * @param <T>
     * @return
     */
    public static <T, U, V> F<T, U, V> $(T target) {
        return new F<>(target);
    }

    /**
     * 执行函数,绑定执行结果
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
     * 执行函数,绑定执行结果
     *
     * @param function
     * @param p
     * @param <R>      函数返回类型
     * @param <P>      第一个参数类型
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
     * 执行函数,绑定执行结果
     *
     * @param function
     * @param p
     * @param q
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
     * 执行函数,绑定执行结果
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
     * 执行函数,绑定执行结果
     *
     * @param consumer
     * @param p
     * @param <P>      第一个参数类型
     * @return
     */
    public <P> F<T, U, V> $$(BiConsumer<T, P> consumer, P p) {
        if (target != null) {
            consumer.accept(target, p);
        }
        return this;
    }


    /**
     * 执行函数,绑定执行结果
     *
     * @param consumer
     * @param p
     * @param <P>      第一个参数类型
     * @return
     */
    public <P, Q> F<T, U, V> $$(TriConsumer<T, P, Q> consumer, P p, Q q) {
        if (target != null) {
            consumer.accept(target, p, q);
        }
        return this;
    }


    /**
     * 绑定无参方法
     *
     * @param consumer
     * @return
     */
    public F<T, U, V> consumer(Consumer<T> consumer) {
        this.c1 = consumer;
        return this;
    }


    /**
     * 绑定单个参数的方法
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
     * 绑定两个参数的方法
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
     * 绑定无参方法作为function
     *
     * @param function
     * @return
     */
    public F<T, U, V> function(Function<T, T> function) {
        this.f1 = function;
        return this;
    }

    /**
     * 绑定单个参数的方法作为function
     *
     * @param function
     * @return
     */
    public <U> F<T, U, V> function2(BiFunction<T, U, T> function) {
        F<T, U, V> f = (F<T, U, V>) this;
        f.f2 = function;
        return f;
    }

    /**
     * 绑定两个参数的方法作为function
     *
     * @param function
     * @return
     */
    public <U, V> F<T, U, V> function3(TriFunction<T, U, V, T> function) {
        F<T, U, V> f = (F<T, U, V>) this;
        f.f3 = function;
        return f;
    }


    /**
     * 调用无参方法
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
     * 调用单个参数的方法
     *
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
     * 调用单个参数的方法
     *
     * @return
     */
    public F<T, U, V> accept(Param<U> u) {
        if (c2 != null) {
            if (u.test()) {
                c2.accept(target, u.get());
            }
        }
        return this;
    }

    /**
     * 调用两个参数的方法
     *
     * @return
     */
    public F<T, U, V> accept(U u, V v) {
        if (c3 != null) {
            c3.accept(target, u, v);
        }
        return this;
    }

    /**
     * 调用无参方法,绑定执行结果
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
     * 调用单个参数的方法,绑定执行结果
     *
     * @return
     */
    public F<T, U, V> apply(U u) {
        if (f2 != null) {
            target = f2.apply(target, u);
        }
        return this;
    }

    /**
     * 调用两个参数的方法,绑定执行结果
     *
     * @return
     */
    public F<T, U, V> apply(U u, V v) {
        if (f3 != null) {
            target = f3.apply(target, u, v);
        }
        return this;
    }
}
