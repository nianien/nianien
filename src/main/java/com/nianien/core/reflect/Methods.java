package com.nianien.core.reflect;

import java.lang.reflect.Method;

import com.nianien.core.exception.ExceptionHandler;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

/**
 * 获取方法相关信息的工具类
 *
 * @author skyfalling
 */
public class Methods {

    /**
     * 获取指定调用深度的方法所在类的名称<br/>
     * 当前方法的堆栈深度为0,调用当前方法的方法堆栈深度为1,依次类推
     *
     * @param depth
     * @return 返回方法所在类的名称
     */
    public static String getCallerClassName(int depth) {
        StackTraceElement trace = getStackTraceElement(depth);
        return trace.getClassName();
    }


    /**
     * 获取指定堆栈的方法名称<br/>
     * 当前方法的堆栈深度为0,调用当前方法的方法堆栈深度为1,依次类推
     *
     * @param depth
     * @return 返回方法名称
     */
    public static String getCallerMethodName(int depth) {
        StackTraceElement trace = getStackTraceElement(depth);
        return trace.getMethodName();
    }

    /**
     * 获取当前方法所在类的名称
     *
     * @return 返回当前类的名称
     */
    public static String getCurrentClassName() {
        return getCallerClassName(1);
    }

    /**
     * 获取当前方法的名称
     *
     * @return 返回当前方法名称
     */
    public static String getCurrentMethodName() {
        return getCallerMethodName(1);
    }

    /**
     * 获取指定深度的堆栈信息<br/>
     *
     * @return 返回StackTraceElement实例
     */
    private static StackTraceElement getStackTraceElement(int n) {
        // return new Exception().getStackTrace()[n + 2];
        return Thread.currentThread().getStackTrace()[n + 3];
    }

    /**
     * 获取指定方法的参数名称
     *
     * @param method
     * @return 方法参数名称数组
     */
    public static String[] getParameterNames(Method method) {
        Class<?> clazz = method.getDeclaringClass();
        String methodName = method.getName();
        Class<?>[] paramTypes = method.getParameterTypes();
        return getParameterNames(clazz, methodName, paramTypes);

    }

    /**
     * 根据方法名称和参数类型列表获取方法参数名称
     *
     * @param clazz
     * @param methodName
     * @param paramTypes
     * @return 方法参数名称数组
     */
    public static String[] getParameterNames(Class<?> clazz, String methodName,
                                             Class<?>... paramTypes) {
        try {
            String[] paramTypeNames = new String[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++)
                paramTypeNames[i] = paramTypes[i].getName();
            ClassPool pool = ClassPool.getDefault();
            CtClass cc = pool.get(clazz.getName());
            CtMethod cm = cc.getDeclaredMethod(methodName, pool
                    .get(paramTypeNames));
            return getMethodParamNames(cm);
        } catch (Exception e) {
            throw ExceptionHandler.throwException(e);
        }
    }


    /**
     * 获取方法参数名称
     *
     * @param cm
     * @return
     * @throws javassist.NotFoundException
     * @throws javassist.NotFoundException
     * @throws Methods.MissingLVException
     *                                     如果最终编译的class文件不包含局部变量表信息
     */
    private static String[] getMethodParamNames(CtMethod cm)
            throws NotFoundException {
        CtClass cc = cm.getDeclaringClass();
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                .getAttribute(LocalVariableAttribute.tag);
        ExceptionHandler.throwIf(attr == null, cc.getName());

        String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramNames.length; i++)
            paramNames[i] = attr.variableName(i + pos);
        return paramNames;
    }

    /**
     * 在class字节码中未找到局部变量表信息的异常<br>
     * 使用编译器选项 javac -g:{vars}来编译源文件
     *
     * @author Administrator
     */
    static class MissingLVException extends Exception {
        private static final long serialVersionUID = 1L;
        static String msg = "class:%s not found local variable table info,please compile source file with javac -g:{vars} ";

        public MissingLVException(String clazzName) {
            super(String.format(msg, clazzName));
        }
    }

}
