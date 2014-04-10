package com.nianien.core.annotation;

import java.lang.annotation.*;

/**
 * 是否忽略
 *
 * @author skyfalling
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Ignore {
    /**
     * 属性名称
     *
     * @return
     */
    public boolean value() default true;
}
