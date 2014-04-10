package com.nianien.test.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lining05
 * Date: 2013-01-31
 */
public class Family {

    private String address;
    private People host;
    private Map<String, People> members = new HashMap<String, People>();

    public Map<String, People> getMembers() {
        return members;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public People getHost() {
        return host;
    }

    public void setHost(People host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "Family{" +
                "address='" + address + '\'' +
                ", host=" + host +
                ", members=" + members +
                '}';
    }
}
