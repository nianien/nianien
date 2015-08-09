package com.nianien.core.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.nianien.core.reflect.Reflections;

/**
 * 集合工具类,提供对集合各种扩展操作的支持
 *
 * @author skyfalling
 */
public class CollectionUtils {
    /**
     * 集合处理接口定义
     *
     * @author skyfalling
     */
    public interface CollectionHandler<T> {
        void handle(Collection<T> list);
    }

    /**
     * 分批处理集合元素
     *
     * @param list    集合
     * @param limit   每次处理元素的最大限制
     * @param handler 集合处理类
     * @param <T>
     */
    public static <T> void batchHandle(Collection<T> list, int limit, CollectionHandler<T> handler) {
        List<T> subList = new ArrayList<T>(limit);
        for (T it : list) {
            subList.add(it);
            if (subList.size() == limit) {
                handler.handle(subList);
                subList.clear();
            }
        }
        if (subList.size() > 0) {
            handler.handle(subList);
        }
    }

    /**
     * 将Iterator对象转化成Enumeration对象
     *
     * @param <T>
     * @param iterator Iterator对象实例
     *
     * @return Enumeration对象实例
     */
    public static <T> Enumeration<T> enumeration(final Iterator<T> iterator) {

        return new Enumeration<T>() {

            @Override
            public boolean hasMoreElements() {
                return iterator.hasNext();
            }

            @Override
            public T nextElement() {
                return iterator.next();
            }

        };

    }

    /**
     * 将Enumeration对象转化成Iterator对象
     *
     * @param <T>
     * @param enumeration Enumeration对象实例
     *
     * @return Iterator对象实例
     */
    public static <T> Iterator<T> iterator(Enumeration<T> enumeration) {
        List<T> list = new ArrayList<T>();
        while (enumeration.hasMoreElements()) {
            list.add(enumeration.nextElement());
        }
        return list.iterator();

    }

    /**
     * 数组转链表
     *
     * @param <T>
     *
     * @return List泛型实例
     */
    public static <T> List<T> list(T[] array) {
        List<T> list = new ArrayList<T>();
        for (T t : array) {
            list.add(t);
        }
        return list;
    }

    /**
     * 可枚举对象转链表
     *
     * @param <T>
     *
     * @return List泛型实例
     */
    public static <T> List<T> list(Iterable<T> iterable) {
        if (iterable instanceof List) {
            return (List<T>) iterable;
        }
        List<T> list = new ArrayList<T>();
        for (T t : iterable) {
            list.add(t);
        }
        return list;
    }

    /**
     * 取元素的某个属性形成新的链表
     *
     * @param iterable
     * @param propertyName 属性名
     * @param propertyType 属性类型
     * @param <T>          属性类型的泛型约束
     *
     * @return 属性列表
     */
    public static <T> List<T> list(Iterable iterable, String propertyName, Class<T> propertyType) {
        List<T> result = new ArrayList<T>();
        for (Object o : iterable) {
            result.add((T) Reflections.getProperty(o, propertyName));
        }
        return result;
    }

    /**
     * 可枚举对象转链表
     *
     * @param <T>
     *
     * @return List泛型实例
     */
    public static <T> List<T> list(Iterator<T> iterator) {
        List<T> list = new ArrayList<T>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    /**
     * 集合转数组
     *
     * @param list
     *
     * @return
     */
    public static <T> T[] array(Collection<T> list, Class<T> clazz) {
        T[] array = (T[]) Array.newInstance(clazz, list.size());
        int i = 0;
        for (T t : list) {
            array[i++] = t;
        }
        return array;
    }

    /**
     * 集合转数组
     *
     * @param list
     *
     * @return
     */
    public static boolean[] booleanArray(Collection<Boolean> list) {
        boolean[] array = new boolean[list.size()];
        int i = 0;
        for (boolean t : list) {
            array[i++] = t;
        }
        return array;
    }

    /**
     * 集合转数组
     *
     * @param list
     *
     * @return
     */
    public static byte[] byteArray(Collection<Byte> list) {
        byte[] array = new byte[list.size()];
        int i = 0;
        for (byte t : list) {
            array[i++] = t;
        }
        return array;
    }

    /**
     * 集合转数组
     *
     * @param list
     *
     * @return
     */
    public static char[] charArray(Collection<Character> list) {
        char[] array = new char[list.size()];
        int i = 0;
        for (char t : list) {
            array[i++] = t;
        }
        return array;
    }

    /**
     * 集合转数组
     *
     * @param list
     *
     * @return
     */
    public static short[] shortArray(Collection<Short> list) {
        short[] array = new short[list.size()];
        int i = 0;
        for (short t : list) {
            array[i++] = t;
        }
        return array;
    }

    /**
     * 集合转数组
     *
     * @param list
     *
     * @return
     */
    public static int[] intArray(Collection<Integer> list) {
        int[] array = new int[list.size()];
        int i = 0;
        for (int t : list) {
            array[i++] = t;
        }
        return array;
    }

    /**
     * 集合转数组
     *
     * @param list
     *
     * @return
     */
    public static long[] longArray(Collection<Long> list) {
        long[] array = new long[list.size()];
        int i = 0;
        for (long t : list) {
            array[i++] = t;
        }
        return array;
    }

    /**
     * 集合转数组
     *
     * @param list
     *
     * @return
     */
    public static float[] floatArray(Collection<Float> list) {
        float[] array = new float[list.size()];
        int i = 0;
        for (float t : list) {
            array[i++] = t;
        }
        return array;
    }

    /**
     * 集合转数组
     *
     * @param list
     *
     * @return
     */
    public static double[] doubleArray(Collection<Double> list) {
        double[] array = new double[list.size()];
        int i = 0;
        for (double t : list) {
            array[i++] = t;
        }
        return array;
    }

    /**
     * 删除链表从from到to(不包括)索引位置的元素
     *
     * @param <T>
     * @param list
     * @param from
     * @param to
     */
    public static <T> void remove(List<T> list, int from, int to) {
        list.subList(from, to).clear();
    }

    /**
     * 删除集合中指定元素
     *
     * @param <T>
     * @param list
     */
    public static <T> void remove(Collection<T> list, Object... items) {
        list.removeAll(Arrays.asList(items));
    }

    /**
     * 删除集合中null元素
     *
     * @param <T>
     * @param list
     */
    public static <T> void removeNull(Collection<T> list) {
        list.removeAll(Collections.singleton(null));
    }

    /**
     * 删除Map中value为null的键值对
     *
     * @param map
     * @param <K>
     * @param <V>
     */
    public static <K, V> void removeNull(Map<K, V> map) {
        Iterator<V> iterator = map.values().iterator();
        while (iterator.hasNext()) {
            if (iterator.next() == null) {
                iterator.remove();
            }
        }
    }

    /**
     * 集合转Map,属性keyProperty作为Map的key值,属性valueProperty作为Map的value值
     *
     * @param list
     * @param keyProperty   作为key的属性名
     * @param valueProperty 作为value的属性名
     * @param keyType       作为key的属性类型
     * @param valueType     作为value的属性类型
     * @param <K>           key的泛型约束
     * @param <V>           value的泛型约束
     *
     * @return
     */
    public static <K, V> Map<K, V> map(Iterable list, String keyProperty, String valueProperty, Class<K> keyType,
                                       Class<V> valueType) {
        Map<K, V> map = new HashMap<K, V>();
        for (Object obj : list) {
            K keyObj = (K) Reflections.getProperty(obj, keyProperty);
            V valueObj = (V) Reflections.getProperty(obj, valueProperty);
            map.put(keyObj, valueObj);
        }
        return map;
    }

    /**
     * 集合转Map,属性keyProperty作为Map的key值,元素本身作为Map的value值
     *
     * @param list
     * @param keyProperty 作为key的属性名
     * @param keyType     作为key的属性类型
     * @param <K>         key的泛型约束
     * @param <V>         value的泛型约束
     *
     * @return
     */
    public static <K, V> Map<K, V> map(Iterable<V> list, String keyProperty, Class<K> keyType) {
        Map<K, V> map = new HashMap<K, V>();
        for (V obj : list) {
            K keyObj = (K) Reflections.getProperty(obj, keyProperty);
            map.put(keyObj, obj);
        }
        return map;
    }

    /**
     * 将集合中元素按照指定属性分组
     *
     * @param list
     * @param keyProperty 作为key的属性名
     * @param keyType     作为key的属性类型
     * @param <K>         key的泛型约束
     * @param <V>         value的泛型约束
     *
     * @return
     */
    public static <K, V> Map<K, List<V>> groupBy(Iterable<V> list, String keyProperty, Class<K> keyType) {
        Map<K, List<V>> map = new HashMap<K, List<V>>();
        for (V obj : list) {
            K keyObj = (K) Reflections.getProperty(obj, keyProperty);
            List<V> values = map.get(keyObj);
            if (values == null) {
                values = new ArrayList<V>();
                map.put(keyObj, values);
            }
            values.add(obj);
        }
        return map;
    }

}
