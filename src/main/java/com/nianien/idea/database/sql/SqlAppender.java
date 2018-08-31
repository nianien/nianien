package com.nianien.idea.database.sql;

import com.nianien.core.util.CollectionUtils;
import com.nianien.core.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 快速构建SQL的工具类<br/>可以通过{@link #getParameters()}获取已缓存的SQL参数,对于SQL语句使用JDBC占位符"?"时很有帮助<br/>
 *
 * @author skyfalling
 */
public class SqlAppender {

    /**
     * SQL语句
     */
    private StringBuilder sqlBuffer = new StringBuilder();
    /**
     * 参数类表
     */
    private List<Object> parameters = new ArrayList<Object>();


    public SqlAppender() {
    }

    /**
     * @param sql    初始SQL
     * @param params SQL参数
     */
    public SqlAppender(String sql, Object... params) {
        this.append(sql, params);
    }


    /**
     * 追加sql
     *
     * @param sql
     * @return
     */
    public SqlAppender append(String sql, Object... params) {
        return appendIfTrue(sql, true, params);
    }

    /**
     * 如果target对象不为null,则追加sql
     *
     * @param sql
     * @param target
     * @return
     */
    public SqlAppender appendIfNotNull(String sql, Object target) {
        return appendIfTrue(sql, target != null, target);
    }

    /**
     * 如果target对象不为空,则追加sql
     *
     * @param sql
     * @param target
     * @return
     */
    public SqlAppender appendIfNotEmpty(String sql, String target) {
        return appendIfTrue(sql, StringUtils.isNotEmpty(target), target);
    }

    /**
     * 如果target对象不为空,则追加SQL
     *
     * @param sql
     * @param target
     * @param <T>
     * @return
     */
    public <T> SqlAppender appendIfNotEmpty(String sql, Collection<T> target) {
        return appendIfTrue(sql, CollectionUtils.isNotEmpty(target), target);
    }

    /**
     * 若number非零,则追加SQL
     *
     * @param sql
     * @param number
     * @return
     */
    public SqlAppender appendIfNotZero(String sql, Number number) {
        return appendIfTrue(sql, number != null && number.doubleValue() != 0, number);
    }

    /**
     * 若number正数,则追加SQL
     *
     * @param sql
     * @param number
     * @return
     */
    public SqlAppender appendIfPositive(String sql, Number number) {
        return appendIfTrue(sql, number != null && number.doubleValue() > 0, number);
    }


    /**
     * 若number为负数,则追加SQL
     *
     * @param sql
     * @param number
     * @return
     */
    public SqlAppender appendIfNegative(String sql, Number number) {
        return appendIfTrue(sql, number != null && number.doubleValue() < 0, number);
    }

    /**
     * 若number为零,则追加SQL
     *
     * @param sql
     * @param number
     * @return
     */
    public SqlAppender appendIfZero(String sql, Number number) {
        return appendIfTrue(sql, number == null || number.doubleValue() == 0, number);
    }

    /**
     * 若number非正数,则追加SQL
     *
     * @param sql
     * @param number
     * @return
     */
    public SqlAppender appendIfNotPositive(String sql, Number number) {
        return appendIfTrue(sql, number == null || number.doubleValue() <= 0, number);
    }

    /**
     * 若number非负数,则追加SQL
     *
     * @param sql
     * @param number
     * @return
     */
    public SqlAppender appendIfNotNegative(String sql, Number number) {
        return appendIfTrue(sql, number == null || number.doubleValue() >= 0, number);
    }


    /**
     * 若expression为true,则追加SQL
     *
     * @param sql
     * @param expression
     * @param sqlParams
     * @return
     */
    public SqlAppender appendIfTrue(String sql, boolean expression, Object... sqlParams) {
        if (expression) {
            sqlBuffer.append(sql);
            for (Object sqlParam : sqlParams) {
                this.parameters.add(sqlParam);
            }
        }
        return this;
    }

    /**
     * 获取SQL语句
     *
     * @return
     */
    public String toSql() {
        return sqlBuffer.toString();
    }

    /**
     * 获取SQL参数
     *
     * @return
     */
    public Object[] getParameters() {
        return parameters.toArray(new Object[0]);
    }


    @Override
    public String toString() {
        return "{sql=\"" +
                sqlBuffer + "\", parameters=" +
                parameters +
                "}";
    }

}
