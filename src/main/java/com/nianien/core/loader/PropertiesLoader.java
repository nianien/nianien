package com.nianien.core.loader;

import com.nianien.core.exception.ExceptionHandler;
import com.nianien.core.io.Closer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 加载Properties对象的工具类
 *
 * @author skyfalling
 */
public class PropertiesLoader {

    /**
     * 加载properties属性配置文件,返回属性对象
     *
     * @param fileName
     * @return Properties对象
     */
    public static Properties load(String fileName) {
        InputStream is = null;
        try {
            is = ResourceLoader.getInputStream(fileName);
            Properties p = new Properties();
            p.load(is);
            return p;
        } catch (Exception e) {
            throw ExceptionHandler.throwException(e);
        } finally {
            Closer.close(is);
        }
    }

    /**
     * 加载properties属性配置文件,返回属性对象
     *
     * @param fileName
     * @param charset
     * @return Properties对象
     */
    public static Properties load(String fileName, String charset) {
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(fileName), charset);
            Properties p = new Properties();
            p.load(reader);
            return p;
        } catch (Exception e) {
            throw ExceptionHandler.throwException(e);
        } finally {
            Closer.close(reader);
        }
    }

    /**
     * 加载XML配置文件,返回属性对象
     *
     * @param fileName
     * @return Properties对象
     */
    public static Properties loadXML(String fileName) {
        InputStream is = null;
        try {
            Properties p = new Properties();
            is = ResourceLoader.getInputStream(fileName);
            p.loadFromXML(is);
            return p;
        } catch (Exception e) {
            throw ExceptionHandler.throwException(e);
        } finally {
            Closer.close(is);
        }
    }

}
