package com.nianien.core.container;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于寄存数据的容器类 ,该类是线程安全的<br>
 * 将数据放入容器时容器会返回一个惟一标识,凭借该标识可以获取之前寄存的数据<br>
 * 
 * @author skyfalling
 * @param <T>
 */
public class IDContainer<T> {

    /**
     * 唯一自增长标识,原子操作
     */
    private long identifier = 0L;

    /**
     * 标识左边界
     */
    private String left;
    /**
     * 标识右边界
     */
    private String right;

    /**
     * 同步的Map对象
     */
    private Map<String, T> map = new HashMap<String, T>();

    /**
     * 构造方法,指定标志边界
     * 
     * @param border
     */
    public IDContainer(String border) {
        this(border, border);
    }

    /**
     * 构造方法,指定标志左右边界
     * 
     * @param left
     * @param right
     */
    public IDContainer(String left, String right) {
        this.left = left;
        this.right = right;
    }

    /**
     * 存储变量,返回变量标识<br>
     * 
     * @param value
     * @return 变量对应的key值
     */
    public synchronized String putValue(T value) {
        String key = left + (identifier++) + right;
        this.map.put(key, value);
        return key;
    }

    /**
     * 根据标识获取变量
     * 
     * @param key
     * @return key对应的变量
     */
    public synchronized T getValue(String key) {
        return this.map.get(key);
    }

    /**
     * 移除变量并返回该变量 <br>
     * 
     * @param key
     * @return key对应的变量
     */
    public synchronized T removeValue(String key) {
        T result = this.map.get(key);
        this.map.remove(key);
        return result;
    }

    /**
     * 清空容器
     */
    public synchronized void clear() {
        this.map.clear();
    }
}
