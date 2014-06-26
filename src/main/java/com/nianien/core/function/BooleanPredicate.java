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
     * AND操作,支持短路
     *
     * @param predicates
     * @return a new BooleanPredicate
     */
    public BooleanPredicate and(final Predicate... predicates) {
        if (predicates == null || predicates.length == 0)
            return this;
        return new BooleanPredicate(new Predicate<T>() {
            @Override
            public boolean apply(T t) {
                boolean bl = BooleanPredicate.this.apply(t);
                if (!bl)
                    return false;
                for (Predicate predicate : predicates) {
                    if (!predicate.apply(t))
                        return false;
                }
                return true;
            }
        });
    }


    /**
     * OR操作,支持短路
     *
     * @param predicates
     * @return a new BooleanPredicate
     */
    public BooleanPredicate or(final Predicate... predicates) {
        if (predicates == null || predicates.length == 0)
            return this;
        return new BooleanPredicate(new Predicate<T>() {
            @Override
            public boolean apply(T t) {
                boolean bl = BooleanPredicate.this.apply(t);
                if (bl)
                    return true;
                for (Predicate predicate : predicates) {
                    if (predicate.apply(t))
                        return true;
                }
                return false;
            }
        });
    }

    /**
     * 非操作
     *
     * @return a new BooleanPredicate
     */
    public BooleanPredicate not() {
        return new BooleanPredicate(new Predicate<T>() {
            @Override
            public boolean apply(T t) {
                return !BooleanPredicate.this.apply(t);
            }
        });
    }

    @Override
    public boolean apply(T t) {
        return predicate.apply(t);
    }
}
