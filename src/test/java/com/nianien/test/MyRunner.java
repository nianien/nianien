package com.nianien.test;

import org.junit.internal.runners.MethodRoadie;
import org.junit.internal.runners.TestClass;
import org.junit.internal.runners.TestMethod;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * @author scorpio
 * @version 1.0.0
 */
public class MyRunner extends Runner {

    private final TestClass fTestClass;

    public MyRunner(Class clazz){
        this.fTestClass = new TestClass(clazz);
    }
    @Override
    public Description getDescription() {
        Description spec = Description.createSuiteDescription("我自己的测试",this.fTestClass.getJavaClass().getAnnotations());
        return spec;
    }

    @Override
    public void run(final RunNotifier notifier) {
        Iterator i$ = this.fTestClass.getTestMethods().iterator();
        while(i$.hasNext()) {
            Method method = (Method)i$.next();
            try {
                (new MethodRoadie(this.fTestClass.getConstructor().newInstance(), new TestMethod(method, this.fTestClass),
                        notifier, Description.createTestDescription
                        (fTestClass.getJavaClass(), method.getName(), method.getAnnotations()))).run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
