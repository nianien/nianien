package com.nianien.idea.database.mapper;

/**
 * SQL映射对象
 *
 * @author skyfalling.
 */
public interface SqlMapper {

    /**
     * 获取key对应的当前SQL
     *
     * @param key 键值
     *
     * @return
     */
    String getSql(String key);

    /**
     * 获取当前SQL
     *
     * @return
     */
    String getSql();

    /**
     * 获取当前SQL列表
     *
     * @return
     */
    String[] getSqls();

    /**
     * 获取key对应的当前SQL列表
     *
     * @param key 键值
     *
     * @return
     */
    String[] getSqls(String key);

    /**
     * 获取key对应的SQL
     *
     * @param key 键值
     *
     * @return
     */
    String findSql(String key);

}
