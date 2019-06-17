package com.nianien.core.functions;

/**
 * 条件参数，当{@link #test()}为真时，可获取参数对象{@link #get()}
 *
 * @author scorpio
 * @version 1.0.0
 * @email tengzhe.ln@alibaba-inc.com
 */
public interface Param<T> {

    /**
     * 是否满足条件
     *
     * @return
     */
    boolean test();

    /**
     * 获取参数值
     *
     * @return
     */
    T get();
}
