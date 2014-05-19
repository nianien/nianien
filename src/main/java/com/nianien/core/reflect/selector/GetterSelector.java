package com.nianien.core.reflect.selector;

import com.nianien.core.function.Predicate;
import com.nianien.core.reflect.Reflections;

import java.lang.reflect.Method;

/**
 * Getter方法选择器
 *
 * @author skyfalling
 */
public class GetterSelector implements Predicate<Method> {

    private final String propertyName;
    private final Class<?> propertyType;

    public GetterSelector() {
        this(null, null);
    }

    public GetterSelector(String propertyName, Class<?> propertyType) {
        this.propertyName = propertyName;
        this.propertyType = propertyType;
    }

    @Override
    public boolean apply(Method method) {
        String methodName = method.getName();
        boolean isGetter = method.getReturnType() != Void.TYPE && method.getParameterTypes().length == 0 &&
                (methodName.startsWith("get") && methodName.length() > 3 ||
                        methodName.startsWith("is") && method.getReturnType() == Boolean.TYPE && methodName.length() > 2);
        return isGetter &&
                (propertyName == null || Reflections.propertyName(method).equals(propertyName))
                && (propertyType == null || method.getReturnType().equals(propertyType));
    }
}
