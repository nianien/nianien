package com.nianien.core.log;

import com.nianien.core.loader.ResourceLoader;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * {@link java.util.logging.Logger}对象的工厂类,优先加载classpath中的logging.properties文件中的配置,如果配置文件不存在,则加载系统配置
 *
 * @author skyfalling
 */
public class Loggers {
    static {
        try {
            File file = new File("logging.properties");
            if (!file.exists()) {
                file = ResourceLoader.getFile("logging.properties");
            }
            if (file != null && file.exists()) {
                LogManager.getLogManager().readConfiguration(new FileInputStream(file));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根日志对象
     */
    private static Logger root = Logger.getLogger("");

    /**
     * 设置全局日志级别
     *
     * @param level
     */
    public static void setLevel(Level level) {
        root.setLevel(level);
        for (Handler handler : root.getHandlers()) {
            handler.setLevel(level);
        }
    }

    /**
     * 获取Logger对象
     *
     * @param clazz
     * @return
     */
    public static Logger getLogger(Class clazz) {
        return Logger.getLogger(clazz.getName());
    }

    /**
     * 获取Logger对象
     *
     * @param namespace
     * @return
     */
    public static Logger getLogger(String namespace) {
        return Logger.getLogger(namespace);
    }

    /**
     * 获取Logger对象,{@link Logger#log(java.util.logging.Level, String)}方法中的msg参数为资源文件的键值
     *
     * @param clazz
     * @param resourceBundleName
     * @return
     */
    public static Logger getLogger(Class clazz, String resourceBundleName) {
        return Logger.getLogger(clazz.getName(), resourceBundleName);
    }

    /**
     * 获取Logger对象,{@link Logger#log(java.util.logging.Level, String)}方法中的msg参数为资源文件的键值
     *
     * @param namespace
     * @param resourceBundleName
     * @return
     */
    public static Logger getLogger(String namespace, String resourceBundleName) {
        return Logger.getLogger(namespace, resourceBundleName);
    }
}
