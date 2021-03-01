package com.nianien.test.database;

import com.nianien.core.collection.wrapper.MapWrapper;
import com.nianien.idea.database.datasource.DataSourceBuilder;
import com.nianien.idea.database.datasource.DataSourceManager;
import com.nianien.idea.database.query.Query;
import com.nianien.idea.database.query.SqlQuery;
import com.nianien.idea.database.sql.SqlGenerator;
import com.nianien.idea.database.sql.SqlStatement;
import com.nianien.test.bean.User;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author skyfalling
 */
public class TestDataBase {

    @Test
    public void testQuery() {
        User user = new User();
        user.setId(1);
        List<User> list = getQuery().setSqlStatement(SqlGenerator.selectSql(user, (String[]) null)).getRows(User.class);
        for (User user1 : list) {
            System.out.println(user1);
        }
    }

    @Test
    public void testInsert() {
        testQuery();
        User u = new User();
        u.setId(5);
        u.setUserId("test");
        u.setPassword("test");
        u.setUserName("落地飞天");
        getQuery().insert(u);
        testQuery();
    }

    @Test
    public void testUpdate() {
        testQuery();
        User u = new User();
        u.setId(5);
        u.setUserId("test");
        u.setPassword("test");
        u.setUserName("test");
        getQuery().update(u, "id");
        testQuery();
    }

    @Test
    public void testDelete() {
        testQuery();
        User u = new User();
        u.setId(5);
        u.setUserId("test");
        u.setPassword("test");
        u.setUserName("test");
        getQuery().delete(u, "id");
        testQuery();
    }

    @Test
    public void testBase() {
        Query query = getQuery();
        String sql = "select * from user where (user_id,user_name) in :p";
        SqlStatement sqlStatement = new SqlStatement(sql, new MapWrapper("p", new Object[]{new String[]{"nianien", "落地飞天"}, new String[]{"wuhao1", "wuhao1"}}));
        System.out.println(sqlStatement.preparedSql());
        assertThat(sqlStatement.preparedSql(), equalTo("select * from user where (user_id,user_name) in ((?,?),(?,?))"));
        List<Map<String, Object>> list = query.setSqlStatement(sqlStatement).getRows();
        for (Map<String, Object> map : list) {
            System.out.println(map);
        }
    }

    public static Query getQuery() {
        Map<String, Object> map = new MapWrapper<String, Object>()
                .with("driverClass", "com.mysql.jdbc.Driver")
                .with("type", org.apache.tomcat.jdbc.pool.DataSource.class)
                .with("jdbcUrl", "jdbc:mysql://127.0.0.1:3306/test?autoReconnect=true&;useUnicode=true&;characterEncoding=utf8")
                .with("user", "root")
                .with("password", "root");
        DataSourceBuilder builder = new DataSourceBuilder();
        builder.addProperties("default", map);
        DataSourceManager manager = new DataSourceManager(builder);
        DataSource ds = manager.getDataSource();
        return new SqlQuery(ds);
    }


}
