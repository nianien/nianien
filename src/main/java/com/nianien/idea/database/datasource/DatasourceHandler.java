package com.nianien.idea.database.datasource;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;
import java.util.function.Supplier;

import javax.sql.DataSource;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

/**
 * @author scorpio
 * @version 1.0.0
 * @email tengzhe.ln@alibaba-inc.com
 */
@AllArgsConstructor
public class DatasourceHandler implements Supplier<DataSource> {
    private Class<? extends DataSource> type;
    private Map<String, Object> config;

    @SneakyThrows
    @Override
    public DataSource get() {
        DataSource dataSource = type.newInstance();
        BeanUtils.populate(dataSource, config);
        return dataSource;
    }
}
