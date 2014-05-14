package com.nianien.idea.database;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.nianien.core.exception.ExceptionHandler;
import com.nianien.core.text.Expression;
import com.nianien.core.xml.XMLDocument;
import com.nianien.core.xml.XMLNode;
import org.dom4j.Document;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据源管理类,加载数据源配置文件,并提供对源文件参数赋值的功能<br/>
 * 其中,参数形如：${include}, 指定Map对象为参数赋值,key=include,value=${include}
 *
 * @author skyfalling
 */
public class DataSourceManager {

    /**
     * 默认数据源名称
     */
    protected String defaultSource;
    /**
     * 数据源映射
     */
    protected Map<String, DataSource> sourceMapping = new HashMap<String, DataSource>();
    /**
     * 配置文件的参数表达式
     */
    private Expression expression = new Expression("${", "}");


    /**
     * 加载数据源<br>
     *
     * @param file
     * @return
     */
    public static DataSourceManager loadFile(File file) {
        return load(file, null);
    }

    /**
     * 加载数据源,并指定动态参数
     *
     * @param file
     * @param parameters
     * @return
     */
    public static DataSourceManager load(File file, Map<String, String> parameters) {
        DataSourceManager manager = new DataSourceManager();
        manager.loadFile(file, parameters);
        return manager;
    }

    /**
     * 获取默认的数据源
     *
     * @return DataSource
     */
    public DataSource getDataSource() {
        return getDataSource(defaultSource);
    }

    /**
     * 根据名称获取相应数据源
     *
     * @param name
     * @return DataSource
     */
    public DataSource getDataSource(String name) {
        return sourceMapping.get(name);
    }

    /**
     * 获取全部数据源名称
     *
     * @return
     */
    public Set<String> getDataSources() {
        return sourceMapping.keySet();
    }

    /**
     * 根据jdbc配置获取数据源,默认使用C3P0
     *
     * @param driver
     * @param url
     * @param user
     * @param password
     * @return
     */
    protected DataSource dataSource(String driver, String url, String user, String password) {
        ComboPooledDataSource source = null;
        try {
            source = new ComboPooledDataSource();
            source.setDriverClass(driver);
            source.setJdbcUrl(url);
            source.setUser(user);
            source.setPassword(password);
        } catch (Exception e) {
            ExceptionHandler.throwException(e);
        }
        return source;
    }

    /**
     * 根据JNDI获取数据源
     *
     * @param jndiName
     */
    protected DataSource dataSource(String jndiName) {
        try {
            InitialContext ctx = new InitialContext();
            return (DataSource) ctx.lookup(jndiName);
        } catch (Exception e) {
            throw ExceptionHandler.throwException(e);
        }
    }

    /**
     * 加载数据源,并为其提供动态参数<br>
     *
     * @param file
     * @param parameters
     */
    private void loadFile(File file, Map<String, String> parameters) {
        Document document = XMLDocument.xmlFromFile(file);
        // 加载数据源source节点
        List<XMLNode> sources = XMLDocument.getXMLNodes(document, "source");
        for (XMLNode node : sources) {
            String name = node.getAttribute("name");
            XMLNode child = node.getChild(0);
            ExceptionHandler.throwIfNull(child, "dataSource[" + name + "] has no configuration");
            //配置节点类型
            String type = child.getNodeName();
            if (type.equals("config")) {// config节点
                String driver = getAttribute(child, "driver", parameters);
                String url = getAttribute(child, "url", parameters);
                String user = getAttribute(child, "user", parameters);
                String password = getAttribute(child, "password", parameters);
                this.sourceMapping.put(name, dataSource(driver, url, user, password));
            } else if (type.equals("jndi")) {// jndi子节点
                String jndi = getAttribute(child, "jndi", parameters);
                this.sourceMapping.put(name, dataSource(jndi));
            } else {
                ExceptionHandler.throwIf(true, "invalid configuration for dataSource[" + name + "]");
            }
        }
        // 设置默认数据源,default节点
        List<XMLNode> defaults = XMLDocument.getXMLNodes(document, "default");
        ExceptionHandler.throwIf(defaults.size() > 1, "only one dataSource can be defined as default");
        if (defaults.size() == 1) {
            this.defaultSource = defaults.get(0).getAttribute("source");
        }
    }

    /**
     * 获取配置属性,并对其中的参数进行赋值
     *
     * @param node
     * @param attribute
     * @param parameters
     * @return
     */
    private String getAttribute(XMLNode node, String attribute, Map<String, String> parameters) {
        String value = node.getAttribute(attribute);
        return value == null || parameters == null ? value : expression.eval(value, parameters);
    }
}
