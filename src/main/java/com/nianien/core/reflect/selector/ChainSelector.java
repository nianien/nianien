package com.nianien.core.reflect.selector;

import com.nianien.core.function.Selector;

import java.util.ArrayList;
import java.util.List;

/**
 * 链式选择器
 *
 * @author skyfalling
 */
public class ChainSelector<T> implements Selector<T> {

    private List<Selector<T>> chain = new ArrayList<Selector<T>>();


    public ChainSelector(Selector<T>... selectors) {
        for (Selector selector : selectors) {
            add(selector);
        }
    }

    public ChainSelector<T> add(Selector<T> selector) {
        chain.add(selector);
        return this;
    }

    public ChainSelector<T> set(Selector<T> selector) {
        return clear().add(selector);
    }

    public ChainSelector<T> clear() {
        this.chain.clear();
        return this;
    }


    @Override
    public boolean select(T target) {
        for (Selector selector : chain) {
            if (!selector.select(target))
                return false;
        }
        return true;
    }

}
