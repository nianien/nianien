package com.nianien.alpha.ognl;

import com.nianien.core.reflect.Reflections;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: lining05
 * Date: 2013-03-01
 */
public class Ognl2 {

    /**
     * 根据表达式取值
     *
     * @param target
     * @param expressions
     * @return
     */
    public static Object get(Object target, String expressions) {
        List<Integer> list = splitExpression(expressions);
        for (int i = 0; i < list.size(); i++) {
            target = getValue(expressions, target, false);
        }
        return target;
    }

    /**
     * 根据表达式链赋值
     *
     * @param target
     * @param expressions
     * @param values
     */
    public static void set(Object target, String expressions, Object... values) {
        List<Integer> list = splitExpression(expressions);
        int resolved = list.size() - values.length;
        int last = 0;
        for (int i = 0; i < list.size(); i++) {
            int index = list.get(i);
            String expression = expressions.substring(last, index);
            if (i < resolved) {
                target = getValue(expression, target, true);
            } else {
                setValue(expression, target, values[i - resolved]);
            }
            last = index + 1;
        }
    }

    /**
     * 获取表达式值对应的值
     *
     * @param expression
     * @param target
     * @param createDefaultValue 指定是否创建默认值
     * @return
     */
    private static Object getValue(String expression, Object target, boolean createDefaultValue) {
        if (target instanceof Map) {
            return ((Map) target).get(expression);
        }
        int begin = expression.indexOf('[');
        int end = expression.indexOf(']');
        if (end > begin) {
            String property = expression.substring(0, begin);
            int index = Integer.parseInt(expression.substring(begin + 1, end));
            return getIndexedValue(property, index, target, createDefaultValue);
        }
        begin = expression.indexOf('(');
        end = expression.indexOf(')');
        if (end - begin == 1) {
            String method = expression.substring(0, begin);
            return getMethodValue(method, target);
        }
        return getPropertyValue(expression, target, createDefaultValue);
    }

    /**
     * 获取属性值
     *
     * @param expression
     * @param target
     * @param createDefaultValue 属性为空时是否创建默认值
     * @return
     */
    private static Object getPropertyValue(String expression, Object target, boolean createDefaultValue) {
        Method getter = Reflections.getter(target.getClass(), expression);
        if (getter == null)
            return null;
        Object propertyValue = Reflections.invoke(getter, target);
        if (propertyValue == null && createDefaultValue) {
            Class propertyType = getter.getReturnType();
            Method setter = Reflections.setter(target.getClass(), expression, propertyType);
            propertyValue = instance(propertyType);
            Reflections.invoke(setter, target, propertyValue);
        }
        return propertyValue;
    }


    /**
     * 获取方法返回值
     *
     * @param expression
     * @param target
     * @return
     */
    private static Object getMethodValue(String expression, Object target) {
        Method method = Reflections.getMethod(target.getClass(), expression);
        return Reflections.invoke(method, target);
    }


    /**
     * 获取索引属性值
     *
     * @param expression
     * @param index
     * @param target
     * @param createDefaultValue 属性为空时是否创建默认值
     * @return
     */
    private static Object getIndexedValue(String expression, int index, Object target, boolean createDefaultValue) {
        Method getter = Reflections.getter(target.getClass(), expression);
        if (getter == null)
            return null;
        Object propertyValue = Reflections.invoke(getter, target);
        if (!createDefaultValue) {
            if (propertyValue == null)
                return null;
            Class propertyType = getter.getReturnType();
            if (propertyType.isArray() && Array.getLength(propertyValue) > index) {
                return Array.get(propertyValue, index);
            } else {
                List list = (List) propertyValue;
                if (list.size() > index)
                    return list.get(index);
                return null;
            }
        }
        Class propertyType = getter.getReturnType();
        Method setter = Reflections.setter(target.getClass(), expression, propertyType);
        if (propertyValue != null) {
            if (propertyType.isArray() && Array.getLength(propertyValue) < index + 1) {
                if (setter != null) {
                    Object newPropertyValue = Array.newInstance(propertyType.getComponentType(), index + 1);
                    System.arraycopy(propertyValue, 0, newPropertyValue, 0, Array.getLength(propertyValue));
                    Reflections.invoke(setter, target, newPropertyValue);
                    propertyValue = newPropertyValue;
                }
            } else {
                List list = (List) propertyValue;
                while (index > list.size() - 1) {
                    list.add(null);
                }
            }
        } else if (setter != null) {
            if (propertyType.isArray()) {
                Class<?> componentType = propertyType.getComponentType();
                propertyValue = Array.newInstance(componentType, index + 1);
            } else {
                propertyValue = new ArrayList(index + 1);
                List list = (List) propertyValue;
                while (index > list.size() - 1) {
                    list.add(null);
                }
            }
            Reflections.invoke(setter, target, propertyValue);
        }
        if (propertyValue == null)
            return null;
        Object element;
        if (propertyType.isArray()) {
            element = Array.get(propertyValue, index);
            if (element == null) {
                element = instance(propertyType.getComponentType());
                Array.set(propertyValue, index, element);
            }
        } else {
            List list = (List) propertyValue;
            element = list.get(index);
            if (element == null) {
                Class elementType = getGenericType(getter.getGenericReturnType());
                if (elementType != null) {
                    element = instance(elementType);
                    list.set(index, element);
                }
            }
        }
        return element;
    }

    /**
     * 设置表达式对应的值
     *
     * @param expression
     * @param target
     * @param value
     */
    private static void setValue(String expression, Object target, Object value) {
        if (target instanceof Map) {
            ((Map) target).put(expression, value);
        }
        int begin = expression.indexOf('[');
        int end = expression.indexOf(']');
        if (end > begin) {
            String property = expression.substring(0, begin);
            int index = Integer.parseInt(expression.substring(begin + 1, end));
            setIndexedValue(property, index, target, value);
        } else {
            Method setter = null;
            Method getter = Reflections.getter(target.getClass(), expression);
            if (getter != null) {
                setter = Reflections.setter(target.getClass(), expression, getter.getReturnType());
            }
            if (setter == null || !setter.getParameterTypes()[0].isInstance(value)) {
                setter = Reflections.setter(target.getClass(), expression, value);
            }
            if (setter != null) {
                Reflections.invoke(setter, target, value);
            }
        }

    }

    /**
     * 设置索引属性值
     *
     * @param expression
     * @param index
     * @param target
     * @param value
     */
    private static void setIndexedValue(String expression, int index, Object target, Object value) {
        Method getter = Reflections.getter(target.getClass(), expression);
        Object propertyValue = Reflections.invoke(getter, target);
        Class propertyType = getter.getReturnType();
        Method setter = Reflections.setter(target.getClass(), expression, propertyType);
        if (propertyValue != null) {
            if (propertyType.isArray() && Array.getLength(propertyValue) < index + 1) {
                if (setter != null) {
                    propertyValue = Array.newInstance(propertyType.getComponentType(), index + 1);
                    System.arraycopy(propertyValue, 0, propertyValue, 0, Array.getLength(propertyValue));
                    Reflections.invoke(setter, target, propertyValue);
                }
            } else {
                List list = (List) propertyValue;
                while (index > list.size() - 1) {
                    list.add(null);
                }
            }
        } else if (setter != null) {
            if (propertyType.isArray()) {
                Class<?> componentType = propertyType.getComponentType();
                propertyValue = Array.newInstance(componentType, index + 1);
            } else {
                propertyValue = new ArrayList(index + 1);
                List list = (List) propertyValue;
                while (index > list.size() - 1) {
                    list.add(null);
                }
            }
            Reflections.invoke(setter, target, propertyValue);
        }
        if (propertyType.isArray()) {
            Array.set(propertyValue, index, value);
        } else {
            List list = (List) propertyValue;
            list.set(index, value);
        }
    }


    /**
     * 创建指定类型的默认实例
     *
     * @param clazz
     * @return
     */
    private static Object instance(Class<?> clazz) {
        if (Reflections.isAbstract(clazz)) {
            if (Reflections.isSubClass(Iterable.class, clazz)) {
                if (Reflections.isSubClass(Map.class, clazz))
                    return new HashMap();
                if (Reflections.isSubClass(Set.class, clazz))
                    return new HashSet();
                return new ArrayList();
            }
            if (Reflections.isSubClass(Iterator.class, clazz)) {
                return new ArrayList().iterator();
            }
        }
        if (clazz.isArray()) {
            // 数组元素类型
            Class<?> componentType = clazz.getComponentType();
            // 创建元素类型数组实例
            return Array.newInstance(componentType, 0);
        }
        return Reflections.newInstance(clazz);
    }

    /**
     * 获取泛型类型
     *
     * @param type
     * @return
     */
    private static Class getGenericType(Type type) {
        if (!(type instanceof ParameterizedType))
            return null;
        ParameterizedType pType = (ParameterizedType) type;
        // 泛型类型
        Type actualType = pType.getActualTypeArguments()[0];

        // 如果泛型参数还是泛型, 则取RawType
        if (actualType instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) actualType)
                    .getRawType();
        } else {
            return (Class<?>) actualType;
        }
    }


    /**
     * 根据"."分割表达式,记录每个表达式结束符的索引位置
     *
     * @param expression
     * @return
     */
    private static List<Integer> splitExpression(String expression) {
        List<Integer> cursors = new ArrayList<Integer>();
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == '.') {
                cursors.add(i);
            }
        }
        cursors.add(expression.length());
        return cursors;
    }

}
