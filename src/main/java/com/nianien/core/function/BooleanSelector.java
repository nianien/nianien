package com.nianien.core.function;

/**
 * 布尔选择器,支持与或非运算
 *
 * @author skyfalling
 */
public class BooleanSelector<T> implements Selector<T> {

    private Selector<T> selector;

    /**
     * 构造方法,提供默认选中器
     *
     * @param selector
     */
    public BooleanSelector(Selector<T> selector) {
        this.selector = selector;
    }

    /**
     * 构造方法,构建默认选择器
     *
     * @param select 指定默认是否匹配选择条件
     */
    public BooleanSelector(final boolean select) {
        this.selector = new Selector<T>() {
            @Override
            public boolean select(T target) {
                return select;
            }
        };
    }

    /**
     * 当前选择条件与selectors的选择条件进行AND组合
     *
     * @param selectors
     * @return
     */
    public BooleanSelector and(final Selector<T>... selectors) {
        return new BooleanSelector(new Selector<T>() {
            @Override
            public boolean select(T target) {
                boolean bl = BooleanSelector.this.select(target);
                for (Selector selector : selectors) {
                    bl &= selector.select(target);
                    if (!bl)
                        return bl;
                }
                return bl;
            }
        });
    }


    /**
     * 当前选择条件与selectors的选择条件进行OR组合
     *
     * @param selectors
     * @return
     */
    public BooleanSelector or(final Selector<T>... selectors) {
        return new BooleanSelector(new Selector<T>() {
            @Override
            public boolean select(T target) {
                boolean bl = BooleanSelector.this.select(target);
                for (Selector selector : selectors) {
                    bl |= selector.select(target);
                    if (bl)
                        return bl;
                }
                return bl;
            }
        });
    }

    /**
     * 当前选择条件取反
     *
     * @return
     */
    public BooleanSelector not() {
        return new BooleanSelector(new Selector<T>() {
            @Override
            public boolean select(T target) {
                return !BooleanSelector.this.select(target);
            }
        });
    }


    @Override
    public boolean select(T target) {
        return selector.select(target);
    }

}
