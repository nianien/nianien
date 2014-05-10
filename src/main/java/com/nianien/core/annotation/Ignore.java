package com.nianien.core.annotation;

import java.lang.annotation.*;

/**
 * 用示是否忽略
 *
 * @author skyfalling
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Ignore {
    /**
     * 是否忽略
     *
     * @return
     */
    public boolean value() default true;
}
