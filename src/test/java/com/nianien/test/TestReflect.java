package com.nianien.test;

import com.nianien.test.bean.User;

import java.lang.reflect.Method;
import java.util.*;

public class TestReflect {

    class A<T> {

        public void dot(T t){

        }
    }
    class B extends A<User>{

        public void dot(User t){

        }
    }

    public static List<Method> getAllMethods(Class<?> clazz) {
        List<Method> list = new ArrayList<Method>();
        for (; clazz != null; clazz = clazz.getSuperclass()) {
            for (Method method : clazz.getDeclaredMethods()) {
                list.add(method);
            }
        }
        return list;
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

        User user = new User();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uuid", 123);
        System.out.println(user);

        List<Method> allMethods = getAllMethods(B.class);
        for (Method allMethod : allMethods) {
            System.out.println(allMethod);
        }

//        Home h = new Home();
//        h.setAddress("aaa");
//        h.setUsers(null);
//        Home b = new Home();
//        Reflector.copyProperties(b, h);
//        System.out.println(b.getAddress());
//
//        Class clazz = Integer.class;
//        int r = (Integer) clazz.getConstructor(String.class).newInstance("1");
//        int n = Classes.simpleInstance(Integer.TYPE, "1");
//        System.out.println(n);


    }

}
