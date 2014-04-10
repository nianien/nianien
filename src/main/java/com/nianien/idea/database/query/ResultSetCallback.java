package com.nianien.idea.database.query;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author skyfalling
 *
 */
public interface ResultSetCallback {

	/**
	 * 处理结果集
	 * 
	 * @param resultSet
	 * @param args 
	 * @throws java.sql.SQLException
	 */
	void execute(ResultSet resultSet, Object... args) throws SQLException ;
}
