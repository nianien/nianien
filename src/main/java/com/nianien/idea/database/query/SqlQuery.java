package com.nianien.idea.database.query;

import com.nianien.core.exception.ExceptionHandler;
import com.nianien.core.exception.NotImplementException;
import com.nianien.core.io.Closer;
import com.nianien.idea.database.sql.SqlGenerator;
import com.nianien.idea.database.sql.SqlStatement;
import com.nianien.idea.database.table.DataField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

/**
 * 数据库访问接口实现,该类是线程安全的
 *
 * @author skyfalling
 */
public class SqlQuery implements Query {
    /**
     * 数据源
     */
    protected DataSource dataSource;
    /**
     * SqlStatement对象
     */
    protected ThreadLocal<SqlStatement> sqlStatement = new ThreadLocal<SqlStatement>();

    /**
     * 构建方法,提供数据源
     *
     * @param dataSource
     */
    public SqlQuery(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    /**
     * 获取当前SqlStatement对象
     *
     * @return
     */
    @Override
    public SqlStatement getSqlStatement() {
        ExceptionHandler.throwIfNull(sqlStatement.get(), new NullPointerException("SqlStatement required!"));
        return this.sqlStatement.get();
    }

    /**
     * 设置当前SqlStatement对象
     *
     * @param sqlStatement
     * @return
     */
    @Override
    public SqlQuery setSqlStatement(SqlStatement sqlStatement) {
        this.sqlStatement.set(sqlStatement);
        return this;
    }


    @Override
    public List<Object> getColumns(final int index) {
        return this.executeQuery(new ResultSetHandler<List<Object>>() {
            @Override
            public List<Object> handle(ResultSet resultset) throws NotImplementException {
                return ResultSetAdapter.getColumns(resultset, index);
            }
        });
    }

    @Override
    public List<Object> getColumns(final String name) {
        return executeQuery(new ResultSetHandler<List<Object>>() {
            @Override
            public List<Object> handle(ResultSet resultset) throws NotImplementException {
                return ResultSetAdapter.getColumns(resultset, name);
            }
        });
    }

    @Override
    public Map<Object, Object> getColumnsMap(final int keyIndex, final int valueIndex) {
        return executeQuery(new ResultSetHandler<Map<Object, Object>>() {
            @Override
            public Map<Object, Object> handle(ResultSet resultset) throws NotImplementException {
                return ResultSetAdapter.getColumnsMap(resultset, keyIndex, valueIndex);
            }
        });
    }

    @Override
    public Map<Object, Object> getColumnsMap(final String keyName, final String valueName) {
        return executeQuery(new ResultSetHandler<Map<Object, Object>>() {
            @Override
            public Map<Object, Object> handle(ResultSet resultset) throws NotImplementException {
                return ResultSetAdapter.getColumnsMap(resultset, keyName, valueName);
            }
        });
    }

    @Override
    public Map<String, Object> getFirstRow() {
        return executeQuery(new ResultSetHandler<Map<String, Object>>() {
            @Override
            public Map<String, Object> handle(ResultSet resultset) throws NotImplementException {
                return ResultSetAdapter.getFirstRow(resultset);
            }
        });
    }

    @Override
    public <T> T getFirstRow(final Class<T> clazz) {
        return executeQuery(new ResultSetHandler<T>() {
            @Override
            public T handle(ResultSet resultset) throws NotImplementException {
                return ResultSetAdapter.getFirstRow(resultset, clazz);
            }
        });
    }

    @Override
    public List<Map<String, Object>> getRows() {
        return executeQuery(new ResultSetHandler<List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> handle(ResultSet resultset) throws NotImplementException {
                return ResultSetAdapter.getRows(resultset);
            }
        });
    }

    @Override
    public <T> List<T> getRows(final Class<T> clazz) {
        return executeQuery(new ResultSetHandler<List<T>>() {
            @Override
            public List<T> handle(ResultSet resultset) throws NotImplementException {
                return ResultSetAdapter.getRows(resultset, clazz);
            }
        });
    }

    @Override
    public List<Map<String, Object>> getRows(final int start, final int size) {
        return executeQuery(new ResultSetHandler<List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> handle(ResultSet resultset) throws NotImplementException {
                return ResultSetAdapter.getRows(resultset, start, size);
            }
        });
    }

    @Override
    public <T> List<T> getRows(final Class<T> clazz, final int start, final int size) {
        return executeQuery(new ResultSetHandler<List<T>>() {
            @Override
            public List<T> handle(ResultSet resultset) throws NotImplementException {
                return ResultSetAdapter.getRows(resultset, start, size, clazz);
            }
        });
    }

    @Override
    public int getRowsCount() {
        return executeQuery(new ResultSetHandler<Integer>() {
            @Override
            public Integer handle(ResultSet resultset) throws NotImplementException {
                return ResultSetAdapter.getRowsCount(resultset);
            }
        });
    }

    @Override
    public <T> Map<Object, T> getRowsMap(final Class<T> clazz, final int index) {
        return executeQuery(new ResultSetHandler<Map<Object, T>>() {
            @Override
            public Map<Object, T> handle(ResultSet resultset) throws NotImplementException {
                return ResultSetAdapter.getRowsMap(resultset, index, clazz);
            }
        });
    }

    @Override
    public <T> Map<Object, T> getRowsMap(final Class<T> clazz, final String name) {
        return executeQuery(new ResultSetHandler<Map<Object, T>>() {
            @Override
            public Map<Object, T> handle(ResultSet resultset) throws NotImplementException {
                return ResultSetAdapter.getRowsMap(resultset, name, clazz);
            }
        });
    }

    @Override
    public <T> void insert(T bean) {
        this.setSqlStatement(SqlGenerator.insertSql(bean)).executeUpdate();
    }

    @Override
    public <T> void update(T bean, String... conditionFields) {
        this.setSqlStatement(SqlGenerator.updateSql(bean, conditionFields)).executeUpdate();
    }

    @Override
    public <T> void delete(T bean, String... conditionFields) {
        this.setSqlStatement(SqlGenerator.deleteSql(bean, conditionFields)).executeUpdate();
    }


    @Override
    public <T> T executeQuery(ResultSetHandler<T> rsh) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = connection();
            stmt = prepare(connection);
            return rsh.handle((rs = stmt.executeQuery()));
        } catch (Exception e) {
            throw ExceptionHandler.throwException(e);
        } finally {
            Closer.close(rs, stmt);
            releaseConnection(connection);
        }
    }

    @Override
    public int executeUpdate() {
        PreparedStatement stmt = null;
        Connection connection = null;
        try {
            connection = connection();
            stmt = prepare(connection);
            return stmt.executeUpdate();
        } catch (Exception e) {
            throw ExceptionHandler.throwException(e);
        } finally {
            Closer.close(stmt);
            releaseConnection(connection);
        }
    }


    @Override
    public int[] executeBatch(String... sqlList) {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = connection();
            stmt = connection.createStatement();
            for (String sql : sqlList) {
                stmt.addBatch(sql);
            }
            return stmt.executeBatch();
        } catch (SQLException e) {
            throw ExceptionHandler.throwException(e);
        } finally {
            Closer.close(stmt);
            releaseConnection(connection);
        }
    }


    @Override
    public int[] executeBatch(String sql, Object[][] parametersList) {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = connection();
            stmt = connection.prepareStatement(sql);
            for (Object[] parameters : parametersList) {
                int i = 1;
                for (Object p : parameters) {
                    stmt.setObject(i++, p);
                }
                stmt.addBatch();
            }
            return stmt.executeBatch();
        } catch (Exception e) {
            throw ExceptionHandler.throwException(e);
        } finally {
            Closer.close(stmt);
            releaseConnection(connection);
        }

    }

    /**
     * 获取数据库连接,可以调用{@link #releaseConnection(java.sql.Connection)}方法释放连接
     *
     * @return
     */
    public Connection connection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw ExceptionHandler.throwException(e);
        }
    }

    /**
     * 释放数据库连接
     *
     * @param connection
     */
    public void releaseConnection(Connection connection) {
        Closer.close(connection);
    }

    /**
     * 创建PreparedStatement对象
     *
     * @param connection
     * @return
     * @throws SQLException
     */
    protected PreparedStatement prepare(Connection connection) throws SQLException {
        SqlStatement sqlStatement = getSqlStatement();
        PreparedStatement stmt = connection.prepareStatement(sqlStatement.preparedSql());
        int i = 1;
        for (DataField field : sqlStatement.preparedParameters()) {
            if (field.type == null || field.type == DataField.GenericType) {
                stmt.setObject(i++, field.value);
            } else {
                stmt.setObject(i++, field.value, field.type.getVendorTypeNumber());
            }
        }
        return stmt;
    }

}
