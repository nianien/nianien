package com.nianien.core.collection.map;

import java.util.HashMap;


/**
 * 双值Map对象
 *
 * @param <K>
 * @param <V1>
 * @param <V2>
 * @author skyfalling
 */
public class DoubleMap<K, V1, V2> extends HashMap<K, DoubleValue<V1, V2>> {

    private static final long serialVersionUID = 1L;


    /**
     * 获取key对应的第一个数据对象
     *
     * @param key
     * @return
     */
    public V1 getValue1(Object key) {
        return this.get(key).getValue1();

    }

    /**
     * 获取key对应的第二个数据对象
     *
     * @param key
     * @return
     */
    public V2 getValue2(Object key) {
        return this.get(key).getValue2();

    }

    /**
     * 添加一个键值对<br>
     * 这里将创建一个DoubleValue(value1,value2)作为value值
     *
     * @param key
     * @param value1
     * @param value2
     * @return
     */
    public DoubleValue<V1, V2> put(K key, V1 value1, V2 value2) {
        DoubleValue<V1, V2> dv = new DoubleValue<V1, V2>() {
            private V1 value1;
            private V2 value2;

            @Override
            public V1 getValue1() {
                return value1;
            }

            @Override
            public V2 getValue2() {
                return value2;
            }

            @Override
            public void setValue1(V1 value1) {
                this.value1 = value1;

            }

            @Override
            public void setValue2(V2 value2) {
                this.value2 = value2;
            }

        };
        dv.setValue1(value1);
        dv.setValue2(value2);
        return this.put(key, dv);
    }

    /**
     * 更新key对应的第一个数据对象,返回更新前的值
     *
     * @param key
     * @param value1
     * @return
     */
    public V1 updateValue1(K key, V1 value1) {
        V1 old = this.get(key).getValue1();
        this.get(key).setValue1(value1);
        return old;
    }


    /**
     * 更新key对应的第二个数据对象,返回更新前的值
     *
     * @param key
     * @param value2
     * @return
     */
    public V2 updateValue2(K key, V2 value2) {
        V2 old = this.get(key).getValue2();
        this.get(key).setValue2(value2);
        return old;
    }
}
