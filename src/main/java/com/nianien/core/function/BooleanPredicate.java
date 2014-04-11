package com.nianien.core.function;


/**
 * 布尔函数式,支持与或非运算
 *
 * @author skyfalling
 */
public class BooleanPredicate<T> implements Predicate<T> {

    private Predicate<T> predicate;


    /**
     * 默认函数式
     *
     * @param predicate
     */
    public BooleanPredicate(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    /**
     * 自动生成默认函数式
     *
     * @param predicate 自动生成函数式的默认结果
     */
    public BooleanPredicate(final boolean predicate) {
        this.predicate = new Predicate<T>() {
            @Override
            public boolean call(T t) {
                return predicate;
            }
        };
    }

    /**
     * AND操作,支持短路
     *
     * @param predicates
     * @return
     */
    public BooleanPredicate and(final Predicate... predicates) {
        return new BooleanPredicate(new Predicate<T>() {
            @Override
            public boolean call(T t) {
                boolean bl = BooleanPredicate.this.call(t);
                for (Predicate predicate : predicates) {
                    bl &= predicate.call(t);
                    if (!bl)
                        return bl;
                }
                return bl;
            }
        });
    }


    /**
     * OR操作,支持短路
     *
     * @param predicates
     * @return
     */
    public BooleanPredicate or(final Predicate... predicates) {
        return new BooleanPredicate(new Predicate<T>() {
            @Override
            public boolean call(T t) {
                boolean bl = BooleanPredicate.this.call(t);
                for (Predicate predicate : predicates) {
                    bl |= predicate.call(t);
                    if (bl)
                        return bl;
                }
                return bl;
            }
        });
    }

    /**
     * 非操作
     *
     * @return
     */
    public BooleanPredicate not() {
        return new BooleanPredicate(new Predicate<T>() {
            @Override
            public boolean call(T t) {
                return !BooleanPredicate.this.call(t);
            }
        });
    }

    @Override
    public boolean call(T t) {
        return predicate.call(t);
    }
}
