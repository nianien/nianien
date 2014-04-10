package com.nianien.alpha.ognl;

import com.nianien.core.exception.ExceptionHandler;
import com.nianien.core.reflect.Reflections;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Java属性对象
 *
 * @author skyfalling
 */
public class PropertyAccessor {

    private String name;
    private Method getter;
    private Method setter;
    private Class type;
    private Object owner;


    PropertyAccessor(String name, Method getter, Method setter, Object owner) {
        this.name = name;
        this.getter = getter;
        this.setter = setter;
        this.owner = owner;
        if (getter != null) {
            type = getter.getReturnType();
        } else if (setter != null) {
            type = setter.getParameterTypes()[0];
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGetter(Method getter) {
        this.getter = getter;
    }

    public void setSetter(Method setter) {
        this.setter = setter;
    }

    public void setOwner(Object owner) {
        this.owner = owner;
    }

    public Object get() {
        ExceptionHandler.throwIf(getter == null, "no getter method defined in class: " + owner.getClass().getName());
        return Reflections.invoke(getter, owner);
    }

    public Object getDefault() {
        ExceptionHandler.throwIf(getter == null, "no getter method defined in class: " + owner.getClass().getName());
        Object value = Reflections.invoke(getter, owner);
        if (value == null)
            return setDefault();
        return value;
    }

    public Object setDefault() {
        return set(newInstance());
    }

    public Object set(Object value) {
        ExceptionHandler.throwIf(setter == null, "no setter method defined in class: " + owner.getClass().getName());
        Reflections.invoke(setter, owner, value);
        return value;
    }

    public Object newInstance() {
        if (type.isInterface() || Reflections.isAbstract(type)) {
            if (Map.class.isAssignableFrom(type)) {
                return new HashMap();
            }
            if (List.class.isAssignableFrom(type)) {
                return new ArrayList();
            }
            if (Set.class.isAssignableFrom(type)) {
                return new HashSet();
            }
            if (Collection.class.isAssignableFrom(type)) {
                return new ArrayList();
            }
        }
        return Reflections.newInstance(type);
    }

    @Override
    public String toString() {
        return "PropertyAccessor{" +
                "name='" + name + '\'' +
                ", getter=" + getter +
                ", setter=" + setter +
                ", type=" + type +
                ", owner=" + owner +
                '}';
    }
}
