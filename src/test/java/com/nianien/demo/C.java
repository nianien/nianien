package com.nianien.demo;

/**
 * Created with IntelliJ IDEA.
 * User: lining05
 * Date: 2013-01-28
 */
public class C {
    D d;

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return "C{" +
                "d=" + d +
                '}';
    }
}