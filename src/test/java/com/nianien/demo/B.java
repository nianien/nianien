package com.nianien.demo;

/**
 * Created with IntelliJ IDEA.
 * User: lining05
 * Date: 2013-01-28
 */
public class B {
    C c;

    public C getC() {
        return c;
    }

    public void setC(C c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "B{" +
                "c=" + c +
                '}';
    }
}