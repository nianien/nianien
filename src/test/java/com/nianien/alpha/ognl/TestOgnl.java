package com.nianien.alpha.ognl;

import com.nianien.demo.A;
import com.nianien.demo.C;
import com.nianien.demo.D;

/**
 * Created with IntelliJ IDEA.
 * User: lining05
 * Date: 2013-03-01
 */
public class TestOgnl {

    public static void main(String[] args) throws Exception {
        A a =new A();
        Ognl.set(a, "bs[0].c", new C());
        Ognl.set(a, "b.c", new C());
        Ognl.set(a, "bs[2].c.d", new C(), new D());
        Ognl.set(a, "list[2].c.d", new C(), new D());
        System.out.println(a);
    }
}
