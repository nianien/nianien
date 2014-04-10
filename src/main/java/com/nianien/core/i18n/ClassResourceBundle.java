package com.nianien.core.i18n;

import com.nianien.core.exception.ExceptionHandler;
import com.nianien.core.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 资源国际化的类实现,以类定义取代*.properties文件定义,以字段定义取代字符串定义<br/>
 * 继承类只需定义静态常量字段,由该类自动组装国际化资源<br/>
 * 资源对象的键值根据静态字段宿主类的不同定义如下:<br/>
 * <ul>
 * <li>
 * 若为当前类,则取字段名称
 * </li>
 * <li>
 * 若为内部类,则取内部类名称.字段名称
 * </li>
 * <li>
 * 若为嵌套内部类,则内部类名称之间以"."分隔
 * </li>
 * </ul>
 * 示例: Message类定义如下,则资源"OK"对应的键值为:System.Button.okay
 * <pre>
 * public class Message extends ClassResourceBundle {
 *  private  class System {
 *      private  class Button {
 *          public final static String okay = "OK";
 *      }
 *  }
 * }
 * </pre>
 *
 * @author: skyfalling
 */
public class ClassResourceBundle extends ResourceBundle {

    protected static Map<Class, Map<String, Object>> resources = new HashMap<Class, Map<String, Object>>();

    public ClassResourceBundle() {
        loadResources(this.getClass());
    }

    /**
     * 加载指定类定义的资源对象,这里只加载静态字段
     *
     * @param clazz
     */
    protected void loadResources(Class clazz) {
        if (resources.containsKey(clazz))
            return;
        synchronized (clazz) {
            if (resources.containsKey(clazz))
                return;
            Map<String, Object> resource = new HashMap<String, Object>();
            loadResources(clazz, "", resource);
            resources.put(clazz, resource);
        }
    }


    /**
     * 获取当前对象定义的资源列表
     *
     * @return
     */
    public Map<String, Object> getResource() {
        return resources.get(this.getClass());
    }

    /**
     * 加载类定义的静态字段<br/>
     */
    private static void loadResources(Class baseClass, String baseName, Map<String, Object> map) {
        Field[] fields = baseClass.getDeclaredFields();
        for (Field field : fields) {
            try {
                if (Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true);
                    Object value = field.get(null);
                    String name = baseName + field.getName();
                    if (value instanceof I18N) {
                        I18N i18n = (I18N) value;
                        i18n.name = name;
                        value = i18n.value;
                    }
                    map.put(name, value);
                }
            } catch (Exception e) {
                ExceptionHandler.throwException(e);
            }
        }
        for (Class clazz : baseClass.getDeclaredClasses()) {
            if (StringUtils.notEmpty(baseName)) {
                baseName = baseName + clazz.getSimpleName() + ".";
            } else {
                baseName = clazz.getSimpleName() + ".";
            }
            loadResources(clazz, baseName, map);
        }
    }


    @Override
    protected Object handleGetObject(String key) {
        return getResource().get(key);
    }


    @Override
    public Enumeration<String> getKeys() {
        Set<String> keySet = getResource().keySet();
        return Collections.enumeration(keySet);
    }

    /**
     * 绑定名称的资源对象
     *
     * @param <T>
     */
    public static class I18N<T> {
        private String name;
        private T value;

        public I18N(String name, T value) {
            this.name = name;
            this.value = value;
        }

        public I18N(T value) {
            this.value = value;
        }

        /**
         * 资源名称
         *
         * @return
         */
        public String name() {
            return name;
        }

        /**
         * 资源值
         *
         * @return
         */
        public T value() {
            return value;
        }
    }
}
