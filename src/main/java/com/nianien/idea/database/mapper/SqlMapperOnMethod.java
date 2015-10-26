package com.nianien.idea.database.mapper;

import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.nianien.core.loader.PropertiesLoader;
import com.nianien.core.loader.ResourceLoader;
import com.nianien.core.reflect.Methods;

/**
 * 基于方法名的{@link SqlMapper}实现，可以根据当前方法自动获取SQL配置项<br/>
 * sql.properties文件存在三种配置方式:<br/>
 * <ol>
 * <li>path=classpath:/，file=sql.properties，key=${class.fullName}.${method}</li>
 * <li>path=classpath:/${package}/，file=sql.properties，配置项为：key=${class.simpleName}.${method}</li>
 * <li>path=classpath:/${package}/，file=${class.simpleName}.sql.properties，key=${method}</li>
 * </ol>
 *
 * @author skyfalling.
 */
public abstract class SqlMapperOnMethod implements SqlMapper {

    private static final String SQL_FILE = "sql.properties";
    ;
    private final Properties properties = new Properties();

    private final String splitter;

    /**
     * 构造方法,默认分隔符";"
     */
    public SqlMapperOnMethod() {
        this(";");
    }

    /**
     * 构造方法
     *
     * @param splitter SQL语句分隔符
     */
    public SqlMapperOnMethod(String splitter) {
        this.splitter = splitter;
        // 加载类对应的SQL
        loadFromClass();
        // 加载包对应的SQL
        loadFromPackage();
        // 加载全局SQL
        load(SQL_FILE, "", false);
    }

    /**
     * 加载类级别的SQL
     */
    private void loadFromClass() {
        String prefix = getClass().getName();
        String sqlResource = prefix.replace('.', '/') + "." + SQL_FILE;
        load(sqlResource, prefix, false);
    }

    /**
     * 加载包级别的SQL
     */
    private void loadFromPackage() {
        String prefix = getClass().getPackage().getName();
        String sqlResource = prefix.replace('.', '/') + "/" + SQL_FILE;
        load(sqlResource, prefix, false);
    }

    /**
     * 加载配置
     *
     * @param sqlResource SQL资源文件
     * @param prefix      配置项前导符
     * @param override    是否覆盖已有选项
     */
    public void load(String sqlResource, String prefix, boolean override) {
        if (ResourceLoader.findResource(sqlResource) != null) {
            Properties properties = PropertiesLoader.load(sqlResource);
            load(properties, prefix, override);
        }
    }

    /**
     * 加载配置
     *
     * @param properties SQL配置
     * @param prefix     配置项前导符
     * @param override   是否覆盖已有选项
     */
    public void load(Properties properties, String prefix, boolean override) {
        for (String key : properties.stringPropertyNames()) {
            if (override || !this.properties.containsKey(key)) {
                String realKey = key;
                if (!StringUtils.isEmpty(prefix)) {
                    realKey = prefix + "." + key;
                }
                this.properties.setProperty(realKey, properties.getProperty(key));
            }
        }
    }

    /**
     * 获取当前方法对应的SQL<br/>
     * properties文件中的属性名为${class}.${method}
     *
     * @return
     */
    public final String getSql() {
        return getNotNullSql(getKeyByCallerMethod(null));
    }

    /**
     * 获取当前方法标签对应的SQL<br/>
     * properties文件中的属性名为${class}.${method}.${tag}
     *
     * @param tag 方法内标签
     *
     * @return
     */
    @Override
    public String getSql(String tag) {
        return getNotNullSql(getKeyByCallerMethod(tag));
    }

    /**
     * 获取当前方法对应的SQL列表<br/>
     * 对应sql.properties文件中配置项：${class}.${method}
     *
     * @return
     */
    public final String[] getSqls() {
        return getNotNullSql(getKeyByCallerMethod(null)).split(splitter);
    }

    /**
     * 获取当前方法标签对应的SQL列表<br/>
     * 对应sql.properties文件中配置项：${class}.${method}#${tag}
     * <br/>
     *
     * @param tag 方法内标签
     *
     * @return
     */
    @Override
    public String[] getSqls(String tag) {
        return getNotNullSql(getKeyByCallerMethod(tag)).split(splitter);
    }

    /**
     * 获取sql.properties文件中配置项对应的SQL
     *
     * @param key sql.properties文件中的配置项
     *
     * @return
     */
    @Override
    public String findSql(String key) {
        return properties.getProperty(key, "");
    }

    /**
     * 获取sql.properties文件中配置项对应的SQL<br/>
     * 如果不存在该配置项或者配置为空,则抛出{@link NoSuchElementException}异常
     *
     * @param key sql.properties文件中的配置项
     *
     * @return
     */
    private String getNotNullSql(String key) {
        String sql = findSql(key);
        if (StringUtils.isEmpty(sql)) {
            throw new NoSuchElementException("no sql defined with key: [" + key + "]");
        }
        return sql;
    }

    /**
     * 获取用户的当前调用方法
     *
     * @param methodTag 方法内标签
     *
     * @return
     */
    private String getKeyByCallerMethod(String methodTag) {
        String callerMethodName = Methods.getCallerClassName(2) + "." + Methods.getCallerMethodName(2);
        if (StringUtils.isNotEmpty(methodTag)) {
            callerMethodName = "#" + methodTag;
        }
        return callerMethodName;
    }

}
