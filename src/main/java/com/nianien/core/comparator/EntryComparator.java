package com.nianien.core.comparator;

import java.util.Comparator;
import java.util.Map.Entry;

/**
 * Entry对象的比较类
 *
 * @author skyfalling
 */
public class EntryComparator implements Comparator<Entry<?, ?>> {

    /**
     * Entry对象的排序方式
     *
     * @author skyfalling
     */
    public enum SortType {
        /**
         * 按Key升序
         */
        KeyAsc,
        /**
         * 按Key降序
         */
        KeyDesc,
        /**
         * 按Value升序
         */
        ValueAsc,
        /**
         * 按Value降序
         */
        ValueDesc,
        /**
         * 先按Value升序再按Key升序
         */
        ValueAscKeyAsc,
        /**
         * 先按Value升序再按Key降序
         */
        ValueAscKeyDesc,
        /**
         * 先按Value降序再按Key升序
         */
        ValueDescKeyAsc,
        /**
         * 先按Value降序再按Key降序
         */
        ValueDescKeyDesc;
    }

    /**
     * 排序方式
     */
    private SortType sortType;

    /**
     * 构造方法
     *
     * @param sortType 排序方式
     */
    public EntryComparator(SortType sortType) {
        this.sortType = sortType;
    }

    /**
     * 按照指定的排序方式进行比较
     */
    @Override
    public int compare(Entry<?, ?> d1, Entry<?, ?> d2) {
        int n = 0;
        switch (sortType) {
            case KeyAsc:
                return compareByKey(d1, d2);
            case KeyDesc:
                return compareByKey(d2, d1);
            case ValueAsc:
                return compareByValue(d1, d2);
            case ValueDesc:
                return compareByValue(d2, d1);
            case ValueAscKeyAsc:
                n = compareByValue(d1, d2);
                return n == 0 ? compareByKey(d1, d2) : n;
            case ValueAscKeyDesc:
                n = compareByValue(d1, d2);
                return n == 0 ? compareByKey(d2, d1) : n;
            case ValueDescKeyAsc:
                n = compareByValue(d2, d1);
                return n == 0 ? compareByKey(d1, d2) : n;
            case ValueDescKeyDesc:
                n = compareByValue(d2, d1);
                return n == 0 ? compareByKey(d2, d1) : n;
        }
        return n;
    }

    /**
     * 按key升序
     *
     * @param d1
     * @param d2
     * @return
     */
    @SuppressWarnings("unchecked")
    private int compareByKey(Entry<?, ?> d1, Entry<?, ?> d2) {
        Object key1 = d1.getKey();
        Object key2 = d2.getKey();
        return ((Comparable<Object>) key1).compareTo(key2);
    }

    /**
     * 按value升序
     *
     * @param d1
     * @param d2
     * @return
     */
    @SuppressWarnings("unchecked")
    private int compareByValue(Entry<?, ?> d1, Entry<?, ?> d2) {
        Object value1 = d1.getValue();
        Object value2 = d2.getValue();
        return ((Comparable<Object>) value1).compareTo(value2);
    }

    /**
     * 获取排序方式
     *
     * @return 排序方式
     */
    public SortType getSortType() {
        return this.sortType;
    }

    /**
     * 设置排序方式
     *
     * @param sortType
     */
    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }
}
