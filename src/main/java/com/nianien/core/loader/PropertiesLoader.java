package com.nianien.core.loader;

import com.nianien.core.exception.ExceptionHandler;
import com.nianien.core.io.Closer;

import java.io.File;
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
     * 加载properties文件
     *
     * @param file
     * @return Properties对象
     */
    public static Properties load(File file) {
        InputStream is = null;
        try {
            Properties p = new Properties();
            p.load((is = new FileInputStream(file)));
            return p;
        } catch (Exception e) {
            throw ExceptionHandler.throwException(e);
        } finally {
            Closer.close(is);
        }
    }


    /**
     * 加载properties文件,并指定编码
     *
     * @param file
     * @param charset
     * @return Properties对象
     */
    public static Properties load(File file, String charset) {
        InputStreamReader reader = null;
        try {
            Properties p = new Properties();
            p.load(reader = new InputStreamReader(new FileInputStream(file), charset));
            return p;
        } catch (Exception e) {
            throw ExceptionHandler.throwException(e);
        } finally {
            Closer.close(reader);
        }
    }

    /**
     * 加载XML文件
     *
     * @param file
     * @return Properties对象
     */
    public static Properties loadXML(File file) {
        InputStream is = null;
        try {
            Properties p = new Properties();
            p.loadFromXML(is = new FileInputStream(file));
            return p;
        } catch (Exception e) {
            throw ExceptionHandler.throwException(e);
        } finally {
            Closer.close(is);
        }
    }

}
