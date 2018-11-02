package com.nianien.idea.database.datasource;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author scorpio
 * @version 1.0.0
 * @email tengzhe.ln@alibaba-inc.com
 */
@Slf4j
public class DataSourceFactory {

    /**
     * 默认配置
     */
    private final Map<String, Object> defaultConfig = new HashMap<>();
    /**
     * 命名配置
     */
    private final Map<String, Map<String, Object>> namedConfigs = new LinkedHashMap<>();

    private Class<? extends DataSource> dataSourceType;


    /**
     * 创建指定数据源
     *
     * @param name
     * @return
     */
    public DataSource create(String name) {
        return new DatasourceHandler(dataSourceType, renderConfig(name)).get();
    }

    /**
     * 创建配置数据源列表
     *
     * @return
     */
    public LinkedHashMap<String, DataSource> create() {
        LinkedHashMap<String, DataSource> dataSourceMap = new LinkedHashMap<>();
        for (String name : namedConfigs.keySet()) {
            DataSource dataSource = create(name);
            dataSourceMap.put(name, dataSource);
        }
        return dataSourceMap;
    }


    /**
     * 获取渲染后的配置
     *
     * @param name
     * @return
     */
    public Map<String, Object> renderConfig(String name) {
        Map<String, Object> properties = new HashMap<>(defaultConfig);
        properties.putAll(namedConfigs.getOrDefault(name, Collections.EMPTY_MAP));
        return properties;
    }

    /**
     * 获取渲染后的配置列表
     *
     * @return
     */
    public Map<String, Map<String, Object>> renderConfigs() {
        Map<String, Map<String, Object>> configMap = new HashMap<>();
        for (String key : configMap.keySet()) {
            configMap.put(key, renderConfig(key));
        }
        return configMap;
    }


}