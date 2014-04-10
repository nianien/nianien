package com.nianien.idea.database.table;

import java.lang.annotation.*;

/**
 * 标记数据库字段名称的注解
 *
 * @author skyfalling
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Column {
    /**
     * 数据库字段名称
     *
     * @return 表名称
     */
    public String value();

    /**
     * JDBC数据类型
     *
     * @return
     * @see java.sql.Types
     */
    public int sqlType() default DataField.GenericType;
}
