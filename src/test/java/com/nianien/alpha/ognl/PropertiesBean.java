package com.nianien.alpha.ognl;

import com.nianien.core.collection.map.MapWrapper;
import com.nianien.core.exception.ExceptionHandler;
import com.nianien.core.function.Selector;
import com.nianien.core.reflect.Reflections;
import com.nianien.demo.A;

import java.lang.reflect.Method;

/**
 * @author skyfalling
 */
public class PropertiesBean {

    private MapWrapper<String, PropertyAccessor> map = new MapWrapper<String, PropertyAccessor>();
    private Object bean;
    private Class type;

    public PropertiesBean(Object obj) {
        this.bean = obj;
        this.type = obj.getClass();
        Reflections.getters(type, new Selector<Method>() {
            @Override
            public boolean select(Method target) {
                addProperty(target);
                return false;
            }
        });
    }


    /**
     * 添加字段属性方法
     *
     * @param getter
     */
    private void addProperty(Method getter) {
        String propertyName = Reflections.propertyName(getter);
        ExceptionHandler.throwIf(map.containsKey(propertyName), "duplicate property declared in class[" + type + "]: " + propertyName);
        String getterName = getter.getName();
        String setterName = "set" + getterName.substring(getterName.startsWith("is") ? 2 : 3);
        //获取getter对应的setter方法
        Method setter = Reflections.getMethod(type, setterName, getter.getReturnType());
        map.put(propertyName, new PropertyAccessor(propertyName, getter, setter, bean));
    }

    public static void main(String[] args) {
        PropertiesBean bean = new PropertiesBean(new A());
        System.out.println(bean.map);
    }
}
