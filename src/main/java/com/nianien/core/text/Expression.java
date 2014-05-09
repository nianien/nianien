package com.nianien.core.text;

import com.nianien.core.exception.ExceptionHandler;
import com.nianien.core.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 变量表达式赋值工具类,变量表达式是指被边界符包围的字符串<br/>
 * 如左边界符为"${",右边界符为"}",则表达式形如："${variable}"<br/>
 * <code>
 * <ol>
 * <li>
 * <pre>
 * new Expression("${","}").valueOf("${0}年${1}月${2}日",2012,12,21);//2012年12月21日
 * </pre>
 * </li>
 * <li>
 * <pre>
 * Map map=new HashMap();
 * map.put("year",2012);
 * map.put("month",12);
 * map.put("day",21);
 * new Expression("${","}").valueOf("${year}年${month}月${day}日",map);//2012年12月21日
 * </pre>
 * </li>
 * <li>
 * <pre>
 * Map map=new HashMap();
 * map.put("0",2012);
 * map.put("1",12);
 * map.put("2",21);
 * new Expression("${","}").valueOf("${0}年${1}月${2}日",map,2013);//2013年12月21日
 * </pre>
 * </li>
 * <li>
 * <pre>
 * VariableHandler handler;
 * handler.handle("year"); // 2013
 * handler.handle("month"); // 12
 * handler.handle("day"); // 21
 * new Expression("${","}").valueOf("${year}年${month}月${day}日",handler);//2013年12月21日
 * </pre>
 * </li>
 * </ol>
 * </code>
 *
 * @author skyfalling
 */
public class Expression {

    /**
     * 表达式变量处理的接口声明
     *
     * @author skyfalling
     */
    public static interface VariableHandler {
        /**
         * 处理表达式变量
         *
         * @param variable 表达式变量
         * @return 变量处理结果
         */
        Object handle(String variable);
    }

    /**
     * 表达式的左边界
     */
    private final String left;
    /**
     * 表达式的右边界;
     */
    private final String right;

    /**
     * 指定保留未知变量,如果不保留未知变量,则置为null
     */
    private final boolean keepUnknownVariable;

    /**
     * 默认实例
     */
    private final static Expression defaultExpression = new Expression("{", "}");

    /**
     * 将形如"{n}"的表达式代入对应索引位置的变量<br/>
     * <code>
     * <pre>
     * Expression.eval("{{2}/{1}/{0}}", "2012", "12", "21");//{21/12/2012}
     * </pre>
     * </code>
     *
     * @param expression 变量表达式
     * @param variables  位置变量
     * @return
     */
    public static String eval(String expression, Object... variables) {
        return defaultExpression.valueOf(expression, variables);
    }

    /**
     * 将形如"{variable}"的表达式代入键值相同的变量<br/>
     * <code>
     * <pre>
     * Map map = new HashMap();
     * map.put("year", 2012);
     * map.put("month", 12);
     * map.put("day", 21);
     * Expression.eval("{year}年{month}月{day}日", map);//2012年12月21日
     * </pre>
     * </code>
     *
     * @param expression 变量表达式
     * @param variables  位置变量
     * @return
     */
    public static String eval(String expression, Map<String, ?> variables) {
        return defaultExpression.valueOf(expression, variables);
    }


    /**
     * 将形如"${n}"和"{variable}"的表达式分别代入位置变量和命名变量.如果命名变量和位置变量相同,则位置变量优先<br/>
     * <code>
     * <pre>
     * Map map = new HashMap();
     * map.put("year", 2012);
     * map.put("month", 12);
     * map.put("day", 21);
     * Expression.eval("{year}年{month}月{day}日", map,2013);//2013年12月21日
     * </pre>
     * </code>
     *
     * @param expression       变量表达式
     * @param namedVariables   命名变量
     * @param indexedVariables 位置变量
     * @return
     */
    public static String eval(String expression, Map<String, ?> namedVariables, Object... indexedVariables) {
        return defaultExpression.valueOf(expression, namedVariables, indexedVariables);
    }

    /**
     * 将形如"${n}"和"{variable}"的表达式代入VariableHandler对象的处理结果<br/>
     * <code>
     * <pre>
     * VariableHandler handler;
     * handler.handle("year"); // 2013
     * handler.handle("month"); // 12
     * handler.handle("day"); // 21
     * Expression.eval("{year}年{month}月{day}日",handler);//2013年12月21日
     * </pre>
     * </code>
     *
     * @param expression 变量表达式
     * @param handler    变量处理对象
     * @return
     */
    public static String eval(String expression, VariableHandler handler) {
        return defaultExpression.valueOf(expression, handler);
    }

    /**
     * 构造函数,默认保留未知变量
     *
     * @param border 表达式的边界符,左右边界符一致
     * @see Expression#Expression(String, String, boolean)
     */
    public Expression(String border) {
        this(border, border);
    }

    /**
     * 构造函数,默认保留未知变量<br/>
     *
     * @param left  表达式的左边界符
     * @param right 表达式的右边界符
     * @see Expression#Expression(String, String, boolean)
     */
    public Expression(String left, String right) {
        this(left, right, true);
    }

    /**
     * 构造函数
     *
     * @param left                表达式的左边界符
     * @param right               表达式的右边界符
     * @param keepUnknownVariable 是否保留未知变量,如果为false,则未知变量被置为null
     */
    public Expression(String left, String right, boolean keepUnknownVariable) {
        ExceptionHandler.throwIf(StringUtils.anyEmpty(left, right), "the left and right borders cannot be empty!");
        this.left = left;
        this.right = right;
        this.keepUnknownVariable = keepUnknownVariable;
    }

    /**
     * 代入位置变量计算表达式<br/>
     * <code>
     * <pre>
     * new Expression("${","}").valueOf("${0}年${1}月${2}日",2012,12,21);//2012年12月21日
     * </pre>
     * </code>
     *
     * @param expression 变量表达式
     * @param variables  位置变量
     * @return 代入变量后的表达式
     */
    public String valueOf(String expression, Object... variables) {
        return valueOf(expression, null, variables);
    }

    /**
     * 代入命名变量计算表达式<br/>
     * <code>
     * <pre>
     * Map map=new HashMap();
     * map.put("year",2012);
     * map.put("month",12);
     * map.put("day",21);
     * new Expression("${","}").valueOf("${year}年${month}月${day}日",map);//2012年12月21日
     * </pre>
     * </code>
     *
     * @param expression 含变量的表达式
     * @param variables  命名变量
     * @return 代入变量后的表达式
     */
    public String valueOf(String expression, Map<String, ?> variables) {
        return valueOf(expression, variables, new Object[0]);
    }

    /**
     * 代入变量计算表达式<br/>
     * 如果命名变量与位置变量相同,则取位置变量
     * <code>
     * <pre>
     * Map map=new HashMap();
     * map.put("0",2012);
     * map.put("1",12);
     * map.put("2",21);
     * new Expression("${","}").valueOf("${0}年${1}月${2}日",map,2013);//2013年12月21日
     * </pre>
     * </code>
     *
     * @param expression       变量表达式
     * @param namedVariables   命名变量
     * @param indexedVariables 位置变量
     * @return 代入变量后的表达式
     */
    public String valueOf(String expression, Map<String, ?> namedVariables, Object... indexedVariables) {
        final Map<String, Object> map = new HashMap<String, Object>();
        if (namedVariables != null) {
            map.putAll(namedVariables);
        }
        for (int i = 0; i < indexedVariables.length; i++) {
            if (indexedVariables[i] != null) {
                map.put(String.valueOf(i), indexedVariables[i]);
            }
        }
        return valueOf(expression, new VariableHandler() {
            @Override
            public Object handle(String variable) {
                return map.get(variable);
            }
        });
    }

    /**
     * 利用表达式变量处理对象解析表达式中包含的变量,将形如"${n}"和"{variable}"的表达式代入VariableHandler对象的处理结果<br/>
     * <code>
     * <pre>
     * VariableHandler handler;
     * handler.handle("year"); // 2013
     * handler.handle("month"); // 12
     * handler.handle("day"); // 21
     * new Expression("${","}").valueOf("${year}年${month}月${day}日",handler);//2013年12月21日
     * </pre>
     * </code>
     *
     * @param expression 变量表达式
     * @param handler    变量处理对象
     * @return 代入后的表达式
     */
    public String valueOf(String expression, VariableHandler handler) {
        // 左右边界符的宽度
        int nL = left.length(), nR = right.length();
        // 存储解析后的表达式子串的栈
        List<String> stack = new LinkedList<String>();
        // 表达式子串解析的临时结果
        StringBuilder variableBuilder = new StringBuilder();
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (expression.substring(i).startsWith(left)) {// 遇到左边界符,则入栈
                if (variableBuilder.length() != 0) {
                    // 非边界的表达式子串不为空
                    stack.add(variableBuilder.toString());
                }
                stack.add(left);
                variableBuilder.setLength(0);
                i += nL - 1;
            } else if (expression.substring(i).startsWith(right)) {// 遇到右边界符,则出栈
                if (!stack.isEmpty()) {
                    stack.remove(stack.size() - 1);// 左边界出栈
                    // 解析表达式变量
                    String variable = variableBuilder.toString();
                    variableBuilder.setLength(0);
                    Object value = handler.handle(variable);
                    variableBuilder.append(value != null ? value.toString() : keepUnknownVariable ? left + variable + right : null);
                    // 如果栈顶不是左边界, 则将代入后的结果与栈顶元素内容合并
                    if (!stack.isEmpty() && !stack.get(stack.size() - 1).equals(left)) {
                        variableBuilder.insert(0, stack.remove(stack.size() - 1));
                    }
                } else {
                    // 没有与之匹配的左边界符, 不解析
                    variableBuilder.append(right);
                }
                i += nR - 1;
            } else {
                variableBuilder.append(ch);
            }
        }
        while (!stack.isEmpty()) {
            variableBuilder.insert(0, stack.remove(stack.size() - 1));
        }
        return variableBuilder.toString();
    }
}
