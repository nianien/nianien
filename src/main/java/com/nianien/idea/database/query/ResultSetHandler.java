package com.nianien.idea.database.query;

import java.sql.ResultSet;

import com.nianien.core.exception.NotImplementException;

/**
 * 声明处理数据库查询结果集的接口
 * 
 * @author skyfalling
 * @param <T> 
 * 
 */
public interface ResultSetHandler<T> {

	/**
	 * 处理结果集
	 * 
	 * @param resultSet
	 * @return 返回处理结果
	 * @throws NotImplementException 
	 */
	T handle(ResultSet resultSet) throws NotImplementException;

}
