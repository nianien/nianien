package com.nianien.jooq;


import com.nianien.core.reflect.Reflections;
import com.nianien.core.util.CollectionUtils;
import com.nianien.core.util.StringUtils;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.DSL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 匹配条件适配器<br/>
 * scm.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 *
 * @see Match
 */
public class ConditionAdapter {

    private Object query;
    private Predicate<java.lang.reflect.Field> fieldFilter;

    /**
     * 匹配对象
     *
     * @param query
     */
    public ConditionAdapter(Object query) {
        this(query, null);
    }


    public ConditionAdapter(Object query, Predicate<java.lang.reflect.Field> fieldFilter) {
        this.query = query;
        this.fieldFilter = fieldFilter;
    }

    /**
     * 属性字段驼峰转下划线作为匹配条件
     *
     * @return
     */
    public Condition toCondition() {
        return toCondition(FieldGenerator.byUnderLine());
    }

    /**
     * 数据库表中对应的字段作为匹配条件
     *
     * @param table
     * @return
     */
    public Condition toCondition(Table table) {
        return toCondition(FieldGenerator.byTable(table));
    }

    /**
     * 函数生成的字段作为匹配条件
     *
     * @param fieldFunc
     * @return
     */
    public Condition toCondition(Function<String, Field> fieldFunc) {
        Condition condition = DSL.trueCondition();
        List<QueryField> queryFields = getFieldInfos(query);
        for (QueryField info : queryFields) {
            Field field = fieldFunc.apply(info.name);
            if (field == null) {
                continue;
            }
            Operator operator = info.operator == null ? Operator.EQ : info.operator;
            Condition subCondition = singleCondition(operator, field, info);
            if (subCondition != null) {
                condition = condition.and(subCondition);
            }
        }
        return condition;
    }


    /**
     * 基于字段创建单个匹配条件
     *
     * @param operator
     * @param field
     * @param info
     * @return
     */
    private static Condition singleCondition(Operator operator, Field field, QueryField info) {
        Object[] values = info.asList().toArray(new Object[0]);
        if (values.length == 0) {
            return null;
        }
        Object value = values[0];
        switch (operator) {
            case EQ:
                if (values.length > 1) {
                    return field.in(values);
                }
                return field.eq(value);
            case NE:
                return field.ne(value);
            case GT:
                return field.gt(value);
            case GE:
                return field.ge(value);
            case LT:
                return field.lt(value);
            case LE:
                return field.le(value);
            case LIKE:
                return field.contains(value);
            case NOT_LIKE:
                return field.notContains(value);
            case IN:
                return field.in(values);
            case NOT_IN:
                return field.notIn(values);
            case BETWEEN:
                if (values.length == 2) {
                    return field.between(values[0], values[1]);
                }
                if (values.length == 1) {
                    return field.ge(values[0]);
                } else {
                    throw new IllegalArgumentException("size of field[" + info.name + "] must be 2!");
                }
            case NOT_BETWEEN:
                if (values.length == 2) {
                    return field.notBetween(values[0], values[1]);
                } else {
                    throw new IllegalArgumentException("size of field[" + info.name + "] must be 2!");
                }
            default:
                return null;
        }
    }

    /**
     * 获取对象的字段信息
     *
     * @param query
     * @return
     */
    private List<QueryField> getFieldInfos(Object query) {
        List<java.lang.reflect.Field> fields = Reflections.getFields(query.getClass(), null, fieldFilter);
        List<QueryField> queryFields = new ArrayList<>(fields.size());
        for (java.lang.reflect.Field field : fields) {
            Operator operator = Operator.EQ;
            String name = field.getName();
            Match match = Reflections.findAnnotation(field, Match.class);
            if (match != null) {
                if (match.ignore()) {
                    continue;
                }
                if (StringUtils.isNotEmpty(match.name())) {
                    name = match.name();
                }
                operator = match.value();
            }
            Object value = Reflections.getFieldValue(query, field);
            queryFields.add(new QueryField(field.getType(), name, value, operator));
        }
        return queryFields;
    }


    /**
     * 查询字段
     */
    @Data
    @AllArgsConstructor
    static class QueryField {
        Class type;
        String name;
        Object value;
        Operator operator;

        /**
         * 非空值转集合
         *
         * @return
         */
        Collection asList() {
            if (value == null ||
                    value instanceof String && StringUtils.isEmpty((String) value)) {
                return Collections.EMPTY_LIST;
            }
            if (type.isArray()) {
                return CollectionUtils.arrayToList(value);
            } else if (value instanceof Collection) {
                return (Collection) value;
            } else {
                return Arrays.asList(value);
            }
        }
    }
}
