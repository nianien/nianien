package com.nianien.idea.database.sql;

import java.util.function.Supplier;

/**
 * 条件参数, 只有满足特定条件后,参数值生效
 *
 * @author scorpio
 * @version 1.0.0
 * @email tengzhe.ln@alibaba-inc.com
 */
public interface Param<T> extends Supplier<T> {

    /**
     * 是否满足条件
     *
     * @return
     */
    boolean validate();

    /**
     * 返回参数值
     *
     * @return
     */
    @Override
    T get();
}
