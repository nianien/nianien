package com.nianien.idea.database.datasource;


import org.apache.commons.beanutils.BeanUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import lombok.Data;
import lombok.SneakyThrows;

/**
 * 数据源构建对象, 自适应多种数据源
 *
 * @author scorpio
 * @version 1.0.0
 * @email tengzhe.ln@alibaba-inc.com
 */
@Data
public class DataSourceBuilder {

    /**
     * 默认配置
     */
    private final Map<String, Object> defaultProperties = new HashMap<>();
    /**
     * 个性化配置
     */
    private final Map<String, Map<String, Object>> namingProperties = new LinkedHashMap<>();


    /**
     * 添加默认配置
     *
     * @param properties
     * @return
     */
    public DataSourceBuilder addDefault(Map<String, Object> properties) {
        this.defaultProperties.putAll(properties);
        return this;
    }

    /**
     * 添加命名配置
     *
     * @param name
     * @param properties
     * @return
     */
    public DataSourceBuilder addNaming(String name, Map<String, Object> properties) {
        this.namingProperties.computeIfAbsent(name, (key) -> new HashMap<>());
        this.namingProperties.get(name).putAll(properties);
        return this;
    }

    /**
     * 创建指定数据源
     *
     * @param name
     * @return
     */
    @SneakyThrows
    public DataSource build(String name) {
        Map<String, Object> config = renderConfig(name);
        Object dsType = config.get("type");
        Class<? extends DataSource> dsClass = null;
        if (dsType instanceof Class) {
            dsClass = (Class<? extends DataSource>) dsType;
        } else if (dsType instanceof String && !((String) dsType).isEmpty()) {
            dsClass = (Class<? extends DataSource>) Class.forName((String) dsType);
        }
        if (dsClass == null) {
            throw new IllegalArgumentException("datasource type is required!");
        }
        DataSource dataSource = dsClass.newInstance();
        BeanUtils.populate(dataSource, config);
        return dataSource;
    }

    /**
     * 创建配置数据源列表
     *
     * @return
     */
    public LinkedHashMap<String, DataSource> build() {
        LinkedHashMap<String, DataSource> dataSourceMap = new LinkedHashMap<>();
        for (String name : namingProperties.keySet()) {
            DataSource dataSource = build(name);
            dataSourceMap.put(name, dataSource);
        }
        return dataSourceMap;
    }


    /**
     * 渲染配置项: 驼峰转换,Map-->Properties转换
     *
     * @param name
     * @return
     */
    private Map<String, Object> renderConfig(String name) {
        Map<String, Object> config = new HashMap<>(defaultProperties);
        if (namingProperties.containsKey(name)) {
            config.putAll(namingProperties.get(name));
        }
        Set<String> keys = new HashSet<>(config.keySet());
        for (String origin : keys) {
            String hump = hyphenToHump(origin);
            if (!config.containsKey(hump)) {
                config.put(hump, config.get(origin));
            }
        }
        for (Entry<String, Object> entry : config.entrySet()) {
            if (entry.getValue() instanceof Map) {
                Properties properties = new Properties();
                properties.putAll((Map) entry.getValue());
                entry.setValue(properties);
            }
        }
        return config;
    }


    /**
     * 连字符转驼峰
     *
     * @param src
     * @return
     */
    private static String hyphenToHump(String src) {
        String dest = Arrays.stream(src.split("-"))
                .filter(e -> e.length() > 0)
                .map(e -> e.substring(0, 1).toUpperCase() + e.substring(1).toLowerCase())
                .collect(Collectors.joining());
        return dest.substring(0, 1).toLowerCase() + dest.substring(1);

    }

}