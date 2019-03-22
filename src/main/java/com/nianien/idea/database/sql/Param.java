package com.nianien.idea.database.sql;

import java.util.function.Supplier;

/**
 * @author scorpio
 * @version 1.0.0
 * @email tengzhe.ln@alibaba-inc.com
 */
public interface Param<T> extends Supplier<T> {

    /**
     * 是否有效
     *
     * @return
     */
    boolean isValid();


}
