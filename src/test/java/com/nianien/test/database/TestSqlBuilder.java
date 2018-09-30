package com.nianien.test.database;

import com.nianien.core.collection.wrapper.MapWrapper;
import com.nianien.core.date.DateFormatter;
import com.nianien.core.util.StringUtils;
import com.nianien.idea.database.sql.SqlBuilder;
import com.nianien.idea.database.sql.SqlStatement;
import com.nianien.test.bean.User;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author skyfalling
 */
public class TestSqlBuilder {


    @Test
    public void testStatement() {
        SqlStatement sqlStatement1 = new SqlStatement("select * from users where (userName,password) in ?", Arrays.asList(new String[]{"userName1", "password1"}, new String[]{"userName2", "password2"}));
//        SqlStatement sqlStatement2 = new SqlStatement("select * from users where (userName,password) in ?", new Object[][][]{{{"userName1", "password1"}, {"userName2", "password2"}}});
        SqlStatement sqlStatement2 = new SqlStatement("select * from users where (userName,password) in ?", new Object[]{new Object[][]{{"userName1", "password1"}, {"userName2", "password2"}}});
        System.out.println(sqlStatement1.expandSql());
        System.out.println(sqlStatement2.expandSql());
        assertThat(sqlStatement1.expandSql(), equalTo(sqlStatement2.expandSql()));
    }

    @Test
    public void testEntityBuilder() {
        User user = new User();
        user.setId(1);
        user.setUserName("who");
        user.setUserId("skyfalling");
        user.setUserDesc(new String[]{"test1", "desc2"});
        SqlStatement sqlStatement;
        sqlStatement = SqlBuilder.updateSql(user);

        assertThat(sqlStatement.expandSql(), equalTo("update users set userId = 'skyfalling' , userName = 'who' , uuid = 1 , password = '' where uuid = 1"));
        sqlStatement = SqlBuilder.updateSql(user, (String[])null);
        assertThat(sqlStatement.expandSql(), equalTo("update users set userId = 'skyfalling' , userName = 'who' , uuid = 1 , password = ''"));


        sqlStatement = SqlBuilder.deleteSql(user);
        assertThat(sqlStatement.expandSql(), equalTo("delete from users where userId = 'skyfalling' and userName = 'who' and uuid = 1"));
        sqlStatement = SqlBuilder.deleteSql(user,  (String[])null);
        assertThat(sqlStatement.expandSql(), equalTo("delete from users"));


        sqlStatement = SqlBuilder.insertSql(user);
        assertThat(sqlStatement.expandSql(), equalTo("insert into users (userId,userName,uuid) values('skyfalling','who',1)"));

        sqlStatement = SqlBuilder.selectSql(user);
        assertThat(sqlStatement.expandSql(), equalTo("select * from users where userId = 'skyfalling' and userName = 'who' and uuid = 1"));
        sqlStatement = SqlBuilder.selectSql(user,  (String[])null);
        assertThat(sqlStatement.expandSql(), equalTo("select * from users"));

        sqlStatement = SqlBuilder.whereSql(new SqlStatement("select * from users"), user, "userId", "userName");
        assertThat(sqlStatement.expandSql(), equalTo("select * from users where userId = 'skyfalling' and userName = 'who'"));
        sqlStatement = SqlBuilder.whereSql(new SqlStatement("select * from users"), new MapWrapper<String, String>("userId", "skyfalling").append("userName", "who"));
        assertThat(sqlStatement.expandSql(), equalTo("select * from users where userId = 'skyfalling' and userName = 'who'"));

    }

    @Test
    public void testMapBuilder() {
        SqlStatement sqlStatement;

        Map condition = new MapWrapper("age", Arrays.asList(new Object[]{28, 29, 30})).append("desc", null);
        sqlStatement = SqlBuilder.deleteSql("user", condition);
        assertThat(sqlStatement.originalSql(), equalTo("delete from user where desc is null and age in ?"));
        assertThat(sqlStatement.preparedSql(), equalTo("delete from user where desc is null and age in (?,?,?)"));
        assertThat(sqlStatement.expandSql(), equalTo("delete from user where desc is null and age in (28,29,30)"));
        System.out.println(Arrays.toString(sqlStatement.preparedParameters()));

        MapWrapper user = new MapWrapper("name", "lining").append("sex", "man");
        sqlStatement = SqlBuilder.insertSql("user", user);
        assertThat(sqlStatement.originalSql(), equalTo("insert into user (sex,name) values(?,?)"));
        assertThat(sqlStatement.preparedSql(), equalTo("insert into user (sex,name) values(?,?)"));
        assertThat(sqlStatement.expandSql(), equalTo("insert into user (sex,name) values('man','lining')"));
        System.out.println(Arrays.toString(sqlStatement.preparedParameters()));

        sqlStatement = SqlBuilder.updateSql("user", user, condition);
        assertThat(sqlStatement.originalSql(), equalTo("update user set sex = ? , name = ? where desc is null and age in ?"));
        assertThat(sqlStatement.preparedSql(), equalTo("update user set sex = ? , name = ? where desc is null and age in (?,?,?)"));
        assertThat(sqlStatement.expandSql(), equalTo("update user set sex = 'man' , name = 'lining' where desc is null and age in (28,29,30)"));
        System.out.println(Arrays.toString(sqlStatement.preparedParameters()));

    }

    @Test
    public void testSql() {
        String name = null;
        int age = 28;
        //MM-dd-yyyy HH:mm:ss
        Date now = new Date();
        Object[] dates = new Object[]{"2014-02-08 10:00:00", now};
        SqlStatement sqlStatement = new SqlStatement();
        sqlStatement.append("select * from user where 1=1").append("and name=:0", name).append("and desc=':0' and (age>:0 and age<2*:0) and date in :1", age, dates).appendIf("--注释", true);
        assertThat(sqlStatement.originalSql(), equalTo("select * from user where 1=1 and name=:0 and desc=':0' and (age>:0 and age<2*:0) and date in :1 --注释"));
        System.out.println(sqlStatement.originalSql());
        assertThat(sqlStatement.preparedSql(), equalTo("select * from user where 1=1 and name=? and desc=':0' and (age>? and age<2*?) and date in (?,?) --注释"));
        System.out.println(sqlStatement.preparedSql());
        assertThat(sqlStatement.expandSql(), equalTo("select * from user where 1=1 and name='" + StringUtils.defaultIfNull(name, "") + "' and desc=':0' and (age>28 and age<2*28) and date in ('2014-02-08 10:00:00','" + DateFormatter.format(now) + "') --注释"));
        System.out.println(sqlStatement.expandSql());
        System.out.println(Arrays.toString(sqlStatement.preparedParameters()));

    }


}
