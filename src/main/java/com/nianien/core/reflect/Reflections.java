package com.nianien.core.reflect;

import com.nianien.core.annotation.Property;
import com.nianien.core.function.BooleanSelector;
import com.nianien.core.function.Selector;
import com.nianien.core.reflect.selector.GetterSelector;
import com.nianien.core.reflect.selector.SetterSelector;
import com.nianien.core.util.EnumUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nianien.core.exception.ExceptionHandler.throwException;
import static com.nianien.core.exception.ExceptionHandler.throwIfNull;
import static com.nianien.core.util.StringUtils.headLower;
import static com.nianien.core.util.StringUtils.notEmpty;

/**
 * 反射工具类
 *
 * @author skyfalling
 */
public class Reflections {

    /**
     * 基本类型
     */
    public static enum Primitive {
        Boolean("boolean", java.lang.Boolean.class),
        Byte("byte", java.lang.Byte.class),
        Short("short", java.lang.Short.class),
        Integer("int", java.lang.Integer.class),
        Long("long", java.lang.Long.class),
        Float("float", java.lang.Float.class),
        Double("double", java.lang.Double.class),
        Character("char", java.lang.Character.class);
        String name;
        Class type;
        Class clazz;

        Primitive(String name, Class clazz) {
            try {
                this.name = name;
                this.clazz = clazz;
                this.type = (Class) clazz.getField("TYPE").get(null);
            } catch (Exception e) {
                throwException(e);
            }
        }

        public static Primitive get(String name) {
            return EnumUtils.find(Primitive.class, "name", name);
        }

        public static Primitive get(Class clazz) {
            return EnumUtils.find(Primitive.class, "clazz", clazz);
        }

    }


    /**
     * 选择非Object定义的方法
     */
    public static final Selector<Method> notObjectSelector = new Selector<Method>() {
        @Override
        public boolean select(Method method) {
            return method.getDeclaringClass() != Object.class;
        }
    };
    /**
     * 选择getter方法
     */
    public static final Selector<Method> getterSelector = new GetterSelector();

    /**
     * 选择setter方法
     */
    public static final Selector<Method> setterSelector = new SetterSelector();

    /**
     * 查找指定名称和参数类型的方法<br/>
     * 首先查找该类的public方法,如果不存在,则查找该类声明的方法,如果不存在,则递归查找父类声明的方法<br/>
     * 如果查找不到相应的方法,则返回null
     *
     * @param clazz
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            return clazz.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            for (; clazz != null; clazz = clazz.getSuperclass()) {
                try {
                    return clazz.getDeclaredMethod(methodName, parameterTypes);
                } catch (NoSuchMethodException ex) {
                    //ignore
                }
            }
            return null;
        }
    }

    /**
     * 查找指定名称的字段<br/>
     * 首先查找该类的public字段,如果不存在,则查找该类声明的字段,如果不存在,则递归查找父类声明的字段<br/>
     * 如果查找不到相应的字段,则返回null
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field getField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getField(fieldName);
        } catch (NoSuchFieldException e) {
            for (; clazz != null; clazz = clazz.getSuperclass()) {
                try {
                    return clazz.getDeclaredField(fieldName);
                } catch (NoSuchFieldException ex) {
                    //ignore
                }
            }
            return null;
        }

    }

    /**
     * 获取clazz及其父类声明的public方法
     *
     * @param clazz
     * @return
     */
    public static List<Method> getMethods(Class<?> clazz) {
        return getMethods(clazz, null);
    }


    /**
     * 获取clazz及其父类声明的符合条件的public方法
     *
     * @param clazz
     * @param selector
     * @return
     */
    public static List<Method> getMethods(Class<?> clazz, Selector<Method> selector) {
        List<Method> list = new ArrayList<Method>();
        for (Method method : clazz.getMethods()) {
            if (selector.select(method))
                list.add(method);
        }
        return list;
    }

    /**
     * 获取clazz及其父类({@link Object}除外)声明的public方法
     *
     * @param clazz
     * @return
     */
    public static List<Method> getDefinedMethods(Class<?> clazz) {
        return getMethods(clazz, notObjectSelector);
    }


    /**
     * 获取clazz及其父类({@link Object}除外)声明的符合条件的public方法
     *
     * @param clazz
     * @param selector
     * @return
     */
    public static List<Method> getDefinedMethods(Class<?> clazz, Selector<Method> selector) {
        return getMethods(clazz, new BooleanSelector<Method>(notObjectSelector).and(selector));
    }


    /**
     * 获取clazz及其父类声明的方法
     *
     * @param clazz
     * @return
     */
    public static List<Method> getAllMethods(Class<?> clazz) {
        return getAllMethods(clazz, null);
    }


    /**
     * 获取clazz及其父类声明的符合条件的方法
     *
     * @param clazz
     * @param selector
     * @return
     */
    public static List<Method> getAllMethods(Class<?> clazz, Selector<Method> selector) {
        List<Method> list = new ArrayList<Method>();
        for (; clazz != null; clazz = clazz.getSuperclass()) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (selector == null || selector.select(method)) {
                    list.add(method);
                }
            }
        }
        return list;
    }

    /**
     * 获取clazz及其父类声明的public字段
     *
     * @param clazz
     * @return
     */
    public static List<Field> getFields(Class<?> clazz) {
        return getFields(clazz, null);
    }


    /**
     * 获取clazz及其父类声明的符合条件的public字段
     *
     * @param clazz
     * @param selector
     * @return
     */
    public static List<Field> getFields(Class<?> clazz, Selector<Field> selector) {
        List<Field> list = new ArrayList<Field>();
        for (Field field : clazz.getFields()) {
            if (selector == null || selector.select(field))
                list.add(field);
        }
        return list;
    }


    /**
     * 获取clazz及其父类声明的字段
     *
     * @param clazz
     * @return
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        return getAllFields(clazz, null);
    }

    /**
     * 获取clazz及其父类声明的符合条件的字段
     *
     * @param clazz
     * @param selector
     * @return
     */
    public static List<Field> getAllFields(Class<?> clazz, Selector<Field> selector) {
        List<Field> list = new ArrayList<Field>();
        for (; clazz != null; clazz = clazz.getSuperclass()) {
            for (Field field : clazz.getDeclaredFields()) {
                if (selector == null || selector.select(field)) {
                    list.add(field);
                }
            }
        }
        return list;
    }

    /**
     * 获取getter或setter方法对应的属性名称<br/>
     * 如果方法声明了{@link com.nianien.core.annotation.Property}注解,则以注解为准; 否则按照getter和setter规则取其属性
     *
     * @param method
     * @return getter或setter方法对应的属性名<br/>
     *         注意:这里只对形如getXxx()或isXxx()或SetXxx()的方法有效,对应属性名为xxx<br/>
     */
    public static String propertyName(Method method) {
        Property property = method.getAnnotation(Property.class);
        String name = property != null ? property.value() : null;
        if (notEmpty(name)) {
            return name;
        }
        name = method.getName();
        return headLower(name.substring(name.startsWith("is") ? 2 : 3));
    }

    /**
     * 执行指定方法,返回执行结果
     *
     * @param method
     * @param obj    声明该方法的实例对象
     * @param args   方法的参数
     * @return 方法的执行结果<br/>
     */
    public static Object invoke(Method method, Object obj, Object... args) {
        try {
            method.setAccessible(true);
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw throwException(e);
        }
    }

    /**
     * 判断是否为符合条件的getter方法,参数为null的条件被忽略
     *
     * @param method
     * @return
     */
    public static boolean isGetter(Method method) {
        return getterSelector.select(method);
    }


    /**
     * 判断是否为符合条件的setter方法,参数为null的条件被忽略
     *
     * @param method
     * @return
     */
    public static boolean isSetter(Method method) {
        return setterSelector.select(method);
    }


    /**
     * 获取public getter方法, 如果不存在,则返回null
     *
     * @param clazz
     * @param propertyName 属性名称,注解{@link Property}的优先级高于方法名
     * @return
     */
    public static Method getter(Class<?> clazz, String propertyName) {
        return method(clazz, new GetterSelector(propertyName, null));
    }

    /**
     * 获取public getter方法, 如果不存在,则返回null
     *
     * @param clazz
     * @param propertyName 属性名称,注解{@link Property}的优先级高于方法名
     * @param propertyType 属性类型
     * @return
     */
    public static Method getter(Class<?> clazz, String propertyName, Class<?> propertyType) {
        return method(clazz, new GetterSelector(propertyName, propertyType));
    }

    /**
     * 获取setter方法, 如果不存在,则返回null
     *
     * @param clazz
     * @param propertyName 属性名称,注解{@link Property}的优先级高于方法名
     * @return
     */
    public static Method setter(Class<?> clazz, String propertyName) {
        return method(clazz, new SetterSelector(propertyName, null, null));
    }

    /**
     * 获取setter方法, 如果不存在,则返回null
     *
     * @param clazz
     * @param propertyName 属性名称,注解{@link Property}的优先级高于方法名
     * @param propertyType 属性类型
     * @return
     */
    public static Method setter(Class<?> clazz, String propertyName, Class<?> propertyType) {
        return method(clazz, new SetterSelector(propertyName, propertyType, null));
    }

    /**
     * 获取setter方法, 如果不存在,则返回null
     *
     * @param clazz
     * @param propertyName  属性名称,注解{@link Property}的优先级高于方法名
     * @param propertyValue 属性值
     * @return
     */
    public static Method setter(Class<?> clazz, String propertyName, Object propertyValue) {
        return method(clazz, new SetterSelector(propertyName, null, propertyValue));
    }

    /**
     * 获取符合条件的方法, 如果不存在,则返回null
     *
     * @param clazz
     * @return
     */
    public static Method method(Class<?> clazz, Selector<Method> selector) {
        for (Method method : clazz.getMethods()) {
            if (selector.select(method))
                return method;
        }
        return null;
    }

    /**
     * 获取getter方法列表,不包含{@link Object}声明的getter方法
     *
     * @param clazz
     * @return getter方法列表
     */
    public static List<Method> getters(Class<?> clazz) {
        return getDefinedMethods(clazz, getterSelector);
    }

    /**
     * 获取符合条件的getter方法列表,不包含{@link Object}声明的getter方法
     *
     * @param clazz
     * @return getter方法列表
     */
    public static List<Method> getters(Class<?> clazz, Selector<Method> selector) {
        return getDefinedMethods(clazz, new BooleanSelector<Method>(getterSelector).and(selector));
    }

    /**
     * 获取setter方法列表,不包含{@link Object}声明的setter方法
     *
     * @param clazz
     * @return getter方法列表
     */
    public static List<Method> setters(Class<?> clazz) {
        return getDefinedMethods(clazz, setterSelector);
    }

    /**
     * 获取符合条件的setter方法列表,不包含{@link Object}声明的setter方法
     *
     * @param clazz
     * @return getter方法列表
     */
    public static List<Method> setters(Class<?> clazz, Selector<Method> selector) {
        return getDefinedMethods(clazz, new BooleanSelector<Method>(setterSelector).and(selector));
    }

    /**
     * 根据map的key为bean的同名属性赋值
     *
     * @param <T>
     * @param bean
     * @param map
     * @return bean
     */
    public static <T> T setProperties(T bean, Map<String, ?> map) {
        List<Method> methods = setters(bean.getClass());
        for (Method method : methods) {
            String key = propertyName(method);
            Object value = map.get(key);
            if (value != null && instanceOf(method.getParameterTypes()[0], value)) {
                invoke(method, bean, value);
            }
        }
        return bean;
    }

    /**
     * 获取bean的属性集合,属性名称作为键值<br/>
     * 注: 这里不包括null属性以及{@link Object}声明的属性
     *
     * @param bean
     * @return Map对象
     */
    public static Map<String, Object> getProperties(Object bean) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Method> methods = getters(bean.getClass());
        for (Method method : methods) {
            Object value = invoke(method, bean);
            if (value != null) {
                map.put(propertyName(method), value);
            }
        }
        return map;
    }


    /**
     * 获取指定名称的public属性值
     * 即方法getXxx()或方法isXxx()的执行结果
     *
     * @param obj
     * @param propertyName
     * @return 属性值
     */
    public static Object getPropertyValue(Object obj, String propertyName) {
        Method getter = getter(obj.getClass(), propertyName);
        throwIfNull(getter, new NoSuchMethodException("No such getter Method for property: " + propertyName));
        return invoke(getter, obj);
    }

    /**
     * 设置指定名称的public属性值<br/>
     * 即执行方法setXxx(propertyValue)
     *
     * @param obj
     * @param propertyName
     * @param propertyValue
     */
    public static void setPropertyValue(Object obj, String propertyName, Object propertyValue) {
        Method setter = setter(obj.getClass(), propertyName, propertyValue);
        throwIfNull(setter, new NoSuchMethodException("No such setter Method for property: " + propertyName));
        invoke(setter, obj, propertyValue);
    }


    /**
     * 获取字段值
     *
     * @param obj
     * @param field
     * @return
     */
    public static Object getFieldValue(Object obj, Field field) {
        try {
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            throw throwException(e);
        }
    }

    /**
     * 获取字段值
     *
     * @param obj
     * @param fieldName
     * @return Object
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        return getFieldValue(obj, getField(obj.getClass(), fieldName));
    }

    /**
     * 设置字段值
     *
     * @param obj
     * @param field
     * @param fieldValue
     */
    public static void setFieldValue(Object obj, Field field, Object fieldValue) {
        try {
            field.setAccessible(true);
            field.set(obj, fieldValue);
        } catch (Exception e) {
            throwException(e);
        }
    }

    /**
     * 设置字段值
     *
     * @param obj
     * @param fieldName
     * @param fieldValue
     */

    public static void setFieldValue(Object obj, String fieldName, Object fieldValue) {
        setFieldValue(obj, getField(obj.getClass(), fieldName), fieldValue);
    }

    /**
     * 将from实例中的属性赋值给to实例中的同名属性<br/>
     * 如果同名属性类型不兼容,则不赋值
     *
     * @param from
     * @param to
     */
    public static void copyProperties(Object to, Object from) {
        List<Method> setters = setters(to.getClass());
        Class<?> fromClass = from.getClass();
        for (Method setter : setters) {
            try {
                // 获取getter方法
                Method getter = getter(fromClass, propertyName(setter));
                // 调用setter方法赋值
                if (getter != null && setter.getParameterTypes()[0].isAssignableFrom(getter.getReturnType())) {
                    setter.invoke(to, getter.invoke(from));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将from实例中的字段值赋值给to实例中的同名字段<br/>
     * 其中to中的final字段不被赋值<br/>
     * 另外,如果同名字段类型不兼容,也不赋值
     *
     * @param src
     * @param dest
     */
    public static void copyFields(Object dest, Object src) {
        List<Field> destFields = getAllFields(dest.getClass());
        List<Field> srcFields = getAllFields(src.getClass());
        for (Field destField : destFields) {
            try {
                for (Field srcField : srcFields) {
                    if (!Modifier.isFinal(destField.getModifiers()) && destField.getType().isAssignableFrom(srcField.getType())) {
                        // 设置私有字段
                        srcField.setAccessible(true);
                        destField.setAccessible(true);
                        destField.set(dest, srcField.get(src));
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 根据字符串尽可能地去获取指定类型的实例<br/>
     * 1) 如果是原始类型,返回对应的值<br/>
     * 2) 如果是枚举类型,获取对应名称的实例<br/>
     * 3) 如果是字符串类型,返回字符串<br/>
     * 4) 其他类型,则将字符串作为构造参数以获取实例
     *
     * @param <T>
     * @param clazz
     * @param valueString
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T simpleInstance(Class<T> clazz, String valueString) {
        if (clazz.equals(String.class)) {
            return (T) valueString;
        }
        if (clazz.equals(Boolean.TYPE) || clazz.equals(Boolean.class)) {
            return (T) new Boolean(valueString);
        }
        if (clazz.equals(Byte.TYPE) || clazz.equals(Byte.class)) {
            return (T) new Byte(valueString);
        }
        if (clazz.equals(Short.TYPE) || clazz.equals(Short.class)) {
            return (T) new Short(valueString);
        }
        if (clazz.equals(Integer.TYPE) || clazz.equals(Integer.class)) {
            return (T) new Integer(valueString);
        }
        if (clazz.equals(Long.TYPE) || clazz.equals(Long.class)) {
            return (T) new Long(valueString);
        }
        if (clazz.equals(Float.TYPE) || clazz.equals(Float.class)) {
            return (T) new Float(valueString);
        }
        if (clazz.equals(Double.TYPE) || clazz.equals(Double.class)) {
            return (T) new Double(valueString);
        }
        if (clazz.equals(Character.TYPE) || clazz.equals(Character.class)) {
            return (T) new Character(valueString.charAt(0));
        }
        if (clazz.isEnum()) {
            return (T) Enum.valueOf((Class<Enum>) clazz, valueString);
        }
        try {
            return (T) clazz.getConstructor(String.class).newInstance(valueString);
        } catch (Exception e) {
            throw throwException(e);
        }
    }

    /**
     * 创建名称为className的类的实例<br/>
     * 这里,首先使用给定参数去匹配构造方法,如果存在直接进行实例化 <br/>
     * 否则,寻找符合以下条件的构造方法进行实例化:<br/>
     * 1) 给定参数与构造参数数目一致<br/>
     * 2) 给定参数是构造参数类型的实例
     *
     * @param className
     * @param args      构造参数
     * @return 对象实例
     */
    public static Object newInstance(String className, Object... args) {
        return newInstance(getClass(className), args);
    }

    /**
     * 创建clazz类型的实例<br/>
     * 这里,首先使用给定参数去匹配构造方法,如果存在直接进行实例化 <br/>
     * 否则,寻找符合以下条件的构造方法进行实例化:<br/>
     * 1) 给定参数与构造参数数目一致<br/>
     * 2) 给定参数是构造参数类型的实例
     *
     * @param clazz
     * @param args  构造参数
     * @return 对象实例
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> clazz, Object... args) {
        try {
            if (args.length == 0)
                return clazz.newInstance();
            Class<?>[] parameterTypes = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = args[i].getClass();
            }
            try {
                Constructor<T> con = clazz.getConstructor(parameterTypes);
                return con.newInstance(args);
            } catch (NoSuchMethodException e) {
                for (Constructor<?> con : clazz.getConstructors()) {
                    if (instanceOfTypes(con.getParameterTypes(), args)) {
                        return (T) con.newInstance(args);
                    }
                }
                throw new IllegalArgumentException("no matched constructor!");
            }
        } catch (Exception e) {
            throw throwException(e);
        }
    }

    /**
     * 根据类型名称获取数据类型,支持原始类型<br/>
     *
     * @param className
     * @return
     */
    public static Class<?> getClass(String className) {
        try {
            Primitive Primitive = Reflections.Primitive.get(className);
            if (Primitive != null)
                return Primitive.type;
            return Class.forName(className);
        } catch (Exception e) {
            throw throwException(e);
        }
    }

    /**
     * 对于原始类型返回包装类型
     *
     * @param clazz
     * @return
     */
    public static Class wrapClass(Class<?> clazz) {
        Primitive Primitive = Reflections.Primitive.get(clazz);
        if (Primitive != null)
            return Primitive.clazz;
        return clazz;
    }

    /**
     * 判断指定对象obj是不为是clazz类的实例
     *
     * @param clazz
     * @param obj
     * @return
     */
    public static boolean instanceOf(Class<?> clazz, Object obj) {
        return wrapClass(clazz).isInstance(obj);
    }

    /**
     * 判断对象数组中的元素是否分别是类型数组元素的实例
     *
     * @param types
     * @param args
     * @return
     */
    public static boolean instanceOfTypes(Class<?>[] types, Object[] args) {
        if (types.length != args.length)
            return false;
        int i = 0;
        for (Object obj : args) {
            if (!types[i].isInstance(obj))
                return false;
        }
        return true;
    }

    /**
     * 判断给定的sub类型是否为base类型的继承类
     *
     * @param sub
     * @param base
     * @return 如果是返回true, 否则返回false
     */
    public static boolean isSubClass(Class<?> base, Class<?> sub) {
        return base.isAssignableFrom(sub);
    }


    /**
     * 判断clazz类型是否为抽象类型
     *
     * @param clazz
     * @return 如果是返回true, 否则返回false
     */
    public static boolean isAbstract(Class<?> clazz) {
        return Modifier.isAbstract(clazz.getModifiers());
    }


    /**
     * 根据类型名称判断是否为原始数据类型
     *
     * @param className
     * @return 如果是返回true, 否则返回false
     */
    public static boolean isPrimitive(String className) {
        return Primitive.get(className) != null;
    }


}
