package com.nianien.demo;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lining05
 * Date: 2013-01-28
 */
public class A {

    B b;

    B[] bs;
    List<B> list;

    public List<B> getList() {
        return list;
    }

    public void setList(List<B> list) {
        this.list = list;
    }

    public B[] getBs() {
        return bs;
    }

    public void setBs(B[] bs) {
        this.bs = bs;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "A{" +
                "b=" + b +
                ", bs=" + (bs == null ? null : Arrays.asList(bs)) +
                ", list=" + list +
                '}';
    }
}

