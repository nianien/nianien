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
     * 当前选择条件与selectors的选择条件进行AND组合
     *
     * @param selectors
     * @return a new BooleanSelector
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
     * @return a new BooleanSelector
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
     * @return a new BooleanSelector
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
