package com.nianien.jooq;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.DSL;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字段生成函数
 * scm.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
public class FieldGenerator {
    private static Pattern STR_PATTERN = Pattern.compile("[A-Z]");

    /**
     * 同名映射
     *
     * @return
     */
    public static Function<String, Field> byName() {
        return name -> DSL.field(name);
    }

    /**
     * 转下划线映射
     *
     * @return
     */
    public static Function<String, Field> byUnderLine() {
        return name -> DSL.field(humpToUnderLine(name));
    }

    /**
     * 表字段映射,自动适配下划线风格
     *
     * @param table
     * @return
     */
    public static Function<String, Field> byTable(Table table) {
        return (name) -> findField(table, name);
    }


    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    private static String humpToUnderLine(String str) {
        Matcher matcher = STR_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 根据对象字段名查询表字段,自动识别下划线风格
     *
     * @param table 数据库表
     * @param name  对象字段
     * @return 数据库表字段
     */
    private static Field findField(Table table, String name) {
        Field field = table.field(name);
        if (field == null) {
            name = humpToUnderLine(name);
            field = table.field(name);
        }
        return field;
    }
}
