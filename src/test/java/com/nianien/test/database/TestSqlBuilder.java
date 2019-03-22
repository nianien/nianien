package com.nianien.test.database;

import com.nianien.core.collection.wrapper.MapWrapper;
import com.nianien.idea.database.sql.SqlBuilder;
import com.nianien.idea.database.sql.SqlStatement;
import com.nianien.test.bean.User;

import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author skyfalling
 */
public class TestSqlBuilder {


    @Test
    public void testEntityBuilder() {
        User user = new User();
        user.setId(1);
        user.setUserName("who");
        user.setUserId("skyfalling");
        user.setUserDesc(new String[]{"test1", "desc2"});
        SqlStatement sqlStatement;
        sqlStatement = SqlBuilder.updateSql(user);

        assertThat(sqlStatement.expandSql(), equalTo("update users set password = '' , userName = 'who' , uuid = 1 , userId = 'skyfalling' where uuid = 1"));
        sqlStatement = SqlBuilder.updateSql(user, (String[]) null);
        assertThat(sqlStatement.expandSql(), equalTo("update users set password = '' , userName = 'who' , uuid = 1 , userId = 'skyfalling'"));


        sqlStatement = SqlBuilder.deleteSql(user);
        assertThat(sqlStatement.expandSql(), equalTo("delete from users where userName = 'who' and uuid = 1 and userId = 'skyfalling'"));
        sqlStatement = SqlBuilder.deleteSql(user, (String[]) null);
        assertThat(sqlStatement.expandSql(), equalTo("delete from users"));


        sqlStatement = SqlBuilder.insertSql(user);
        assertThat(sqlStatement.expandSql(), equalTo("insert into users (userName,uuid,userId) values('who',1,'skyfalling')"));

        sqlStatement = SqlBuilder.selectSql(user);
        assertThat(sqlStatement.expandSql(), equalTo("select * from users where userName = 'who' and uuid = 1 and userId = 'skyfalling'"));
        sqlStatement = SqlBuilder.selectSql(user, (String[]) null);
        assertThat(sqlStatement.expandSql(), equalTo("select * from users"));

        sqlStatement = SqlBuilder.whereSql(new SqlStatement("select * from users"), user, "userId", "userName");
        assertThat(sqlStatement.expandSql(), equalTo("select * from users where userId = 'skyfalling' and userName = 'who'"));
        sqlStatement = SqlBuilder.whereSql(new SqlStatement("select * from users"), new MapWrapper<>("userId", "skyfalling").append("userName", "who"));
        assertThat(sqlStatement.expandSql(), equalTo("select * from users where userName = 'who' and userId = 'skyfalling'"));

    }



}
