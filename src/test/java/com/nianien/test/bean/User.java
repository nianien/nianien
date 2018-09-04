package com.nianien.test.bean;

import com.nianien.core.annotation.Ignore;
import com.nianien.core.annotation.Property;
import com.nianien.idea.database.table.Column;
import com.nianien.idea.database.table.Id;
import com.nianien.idea.database.table.Table;

import java.sql.JDBCType;

/**
 * @author skyfalling
 */
@Table("users")
public class User {

    private int id;
    private String userId, userName, password;
    private String[] userDesc;

    /**
     * @return
     */
    @Property("uuid")
    @Id
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Ignore
    @Column(value = "desc", sqlType = JDBCType.VARCHAR)
    public String[] getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String[] userDesc) {
        this.userDesc = userDesc;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


}
