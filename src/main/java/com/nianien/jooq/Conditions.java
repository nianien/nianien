package com.nianien.jooq;

import org.jooq.Condition;
import org.jooq.Field;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

/**
 * 继承自{@link Fluent}类, 用于{@link Condition}对象的流式组合
 *
 * @author scorpio
 * @version 1.0.0
 * @email tengzhe.ln@alibaba-inc.com
 */
public class Conditions extends Fluent<Condition> {

    /**
     * 连接符
     */
    private BinaryOperator<Condition> op;

    /**
     * 指定连接操作
     *
     * @param target
     * @param op     连接符
     */
    protected Conditions(Condition target, BinaryOperator<Condition> op) {
        super(target);
        this.op = op;
    }


    /**
     * 使用AND构建Condition对象
     *
     * @param target
     * @return
     */
    public static Conditions and(Condition target) {
        return new Conditions(target, (c1, c2) -> c1.and(c2));
    }

    /**
     * 使用OR构建Condition对象
     *
     * @param target
     * @return
     */
    public static Conditions or(Condition target) {
        return new Conditions(target, (c1, c2) -> c1.or(c2));
    }

    /**
     * 绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @return
     */
    public <P, F> Conditions with(P param, Field<F> field,
                                                                      BiFunction<Field<F>, P, Condition> function) {
        return (Conditions) this.when(param, (p) -> true, (c, p) -> op.apply(c, function.apply(field, p)));
    }


    /**
     * 字段相等
     *
     * @param param 条件参数
     * @param field 表字段
     * @return
     */
    public <P> Conditions with(P param, Field field) {
        return (Conditions) this.when(param, (p) -> true, (c, p) -> op.apply(c, field.eq(p)));
    }

    /**
     * 迭代绑定每个元素的函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @param <F>      字段类型
     * @return
     */
    public <P, F> Conditions batch(Collection<P> param, Field<F> field, BiFunction<Field<F>, P, Condition> function) {
        for (P p : param) {
            this.with(p, field, function);
        }
        return this;
    }

    /**
     * 迭代绑定每个元素的函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @param <F>      字段类型
     * @return
     */
    public <P, F> Conditions batch(P[] param, Field<F> field, BiFunction<Field<F>, P, Condition> function) {
        for (P p : param) {
            this.with(p, field, function);
        }
        return this;
    }

    /**
     * 当断言predicate为true,绑定函数结果
     *
     * @param param     条件参数
     * @param field     表字段
     * @param predicate 断言表达式
     * @param function  函数表达式
     * @param <P>       参数类型&函数第二个参数类型
     * @param <F>       字段类型
     * @return
     */
    public <P, F> Conditions when(P param, Field<F> field, boolean predicate,
                                                                      BiFunction<Field<F>, P, Condition> function) {
        return (Conditions) this.when(param, predicate, (c, p) -> op.apply(c, function.apply(field, p)));
    }


    /**
     * 当断言predicate为true,绑定函数结果
     *
     * @param param     条件参数
     * @param field     表字段
     * @param predicate 断言表达式
     * @param function  函数表达式
     * @param <P>       参数类型&函数第二个参数类型
     * @return
     */
    public <P, F> Conditions when(P param, Field<F> field,
                                                                      Predicate<P> predicate,
                                                                      BiFunction<Field<F>, P, Condition> function) {
        return (Conditions) this.when(param, predicate, (c, p) -> op.apply(c, function.apply(field, p)));
    }


    /**
     * 当参数param为true,绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @return
     */
    public <F> Conditions when(boolean param, Field<F> field,
                                                                   BiFunction<Field<F>, Boolean, Condition> function) {
        return (Conditions) this.when(param, (c, p) -> op.apply(c, function.apply(field, p)));
    }

    /**
     * 当参数param为true, 批量绑定函数结果
     *
     * @param predicate 前置检查条件
     * @param params    条件参数
     * @param field     表字段
     * @param function  函数表达式
     * @return
     */
    public <P, F> Conditions when(boolean predicate, P[] params, Field<F> field, BiFunction<Field<F>, P, Condition> function) {
        if (predicate) {
            for (P p : params) {
                this.with(p, field, function);
            }
        }

        return this;
    }

    /**
     * 当参数param为false,绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @return
     */
    public <F> Conditions whenNot(boolean param, Field<F> field, BiFunction<Field<F>, Boolean, Condition> function) {
        return (Conditions) this.whenNot(param, (c, p) -> op.apply(c, function.apply(field, p)));
    }


    /**
     * 当参数不为null,绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @return
     */
    public <P, F> Conditions notNull(P param, Field<F> field, BiFunction<Field<F>, P, Condition> function) {
        return (Conditions) this.notNull(param, (c, p) -> op.apply(c, function.apply(field, p)));
    }

    /**
     * 当参数不为null,绑定函数结果
     *
     * @param param 条件参数
     * @param field 表字段
     * @param <P>   参数类型&函数第二个参数类型
     * @return
     */
    public <P> Conditions notNull(P param, Field<P> field) {
        return this.notNull(param, field, (f, p) -> f.eq(p));
    }


    /**
     * 当参数不为空,绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @return
     */
    public <F> Conditions notEmpty(String param, Field<F> field, BiFunction<Field<F>, String, Condition> function) {
        return (Conditions) this.notEmpty(param, (c, p) -> op.apply(c, function.apply(field, p)));
    }

    /**
     * 当参数不为空,绑定函数结果
     *
     * @param param 条件参数
     * @param field 表字段
     * @return
     */
    public Conditions notEmpty(String param, Field<String> field) {
        return this.notEmpty(param, field, (f, p) -> f.eq(p));
    }

    /**
     * 当参数不为空,绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @param <F>      字段类型
     * @return
     */
    public <P, F> Conditions notEmpty(Collection<P> param, Field<F> field,
                                                                          BiFunction<Field<F>, Collection<P>, Condition> function) {
        return (Conditions) this.notEmpty(param, (c, p) -> op.apply(c, function.apply(field, p)));
    }

    /**
     * 当参数不为空,绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @param <P>      参数类型&函数第二个参数类型
     * @param <F>      字段类型
     * @return
     */
    public <P, F> Conditions notEmpty(P[] param, Field<F> field, BiFunction<Field<F>, P[], Condition> function) {
        return (Conditions) this.notEmpty(param, (c, p) -> op.apply(c, function.apply(field, p)));
    }

    /**
     * 当参数number>0,绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @param <F>      字段类型
     * @return
     */
    public <F extends Number> Conditions gt0(F param, Field<F> field, BiFunction<Field<F>, F, Condition> function) {
        return (Conditions) this.gt0(param, (c, p) -> op.apply(c, function.apply(field, p)));
    }

    /**
     * 当参数number>0,绑定函数结果
     *
     * @param param 条件参数
     * @param field 表字段
     * @return
     */
    public <F extends Number> Conditions gt0(F param, Field<F> field) {
        return this.gt0(param, field, (f, p) -> f.eq(p));
    }

    /**
     * 当参数number>=0,绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @return
     */
    public <F extends Number> Conditions ge0(F param, Field<F> field, BiFunction<Field<F>, F, Condition> function) {
        return (Conditions) this.ge0(param, (c, p) -> op.apply(c, function.apply(field, p)));
    }

    /**
     * 当参数number>=0,绑定函数结果
     *
     * @param param 条件参数
     * @param field 表字段
     * @return
     */
    public <F extends Number> Conditions ge0(F param, Field<F> field) {
        return this.gt0(param, field, (f, p) -> f.eq(p));
    }

    /**
     * 当参数number<0,绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @return
     */
    public <F extends Number> Conditions lt0(F param, Field<F> field, BiFunction<Field<F>, F, Condition> function) {
        return (Conditions) this.lt0(param, (c, p) -> op.apply(c, function.apply(field, p)));
    }


    /**
     * 当参数number<0,绑定函数结果
     *
     * @param param 条件参数
     * @param field 表字段
     * @return
     */
    public <F extends Number> Conditions lt0(F param, Field<F> field) {
        return this.gt0(param, field, (f, p) -> f.eq(p));
    }

    /**
     * 当参数number<=0,绑定函数结果
     *
     * @param param    条件参数
     * @param field    表字段
     * @param function 函数表达式
     * @return
     */
    public <F extends Number> Conditions le0(F param, Field<F> field, BiFunction<Field<F>, F, Condition> function) {
        return (Conditions) this.le0(param, (c, p) -> op.apply(c, function.apply(field, p)));
    }

    /**
     * 当参数number<=0,绑定函数结果
     *
     * @param param 条件参数
     * @param field 表字段
     * @return
     */
    public <F extends Number> Conditions le0(F param, Field<F> field) {
        return this.gt0(param, field, (f, p) -> f.eq(p));
    }

}
