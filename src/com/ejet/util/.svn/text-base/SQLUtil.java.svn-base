package com.ejet.util;

/**
 * SQL条件拼接工具类
 * Company：深圳市###EJET###有限公司
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author ###ejet###
 */
public class SQLUtil
{
    /**
     * 向SQL语句中添加一个相等式查询条件
     * @param sql SQL语句
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public static void addEqual(StringBuffer sql, String fieldName, String fieldValue)
    {
        if (fieldValue != null && fieldValue.length() > 0)
        {
            sql.append(" and ").append(fieldName).append("='").append(encode(fieldValue)).append("'");
        }
    }

    /**
     * 向SQL语句中添加一个小于式查询条件
     * @param sql SQL语句
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public static void addLess(StringBuffer sql, String fieldName, String fieldValue)
    {
        if (fieldValue != null && fieldValue.length() > 0)
        {
            sql.append(" and ").append(fieldName).append("<'").append(encode(fieldValue)).append("'");
        }
    }

    /**
     * 向SQL语句中添加一个小于等于式查询条件
     * @param sql SQL语句
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public static void addLessEqual(StringBuffer sql, String fieldName, String fieldValue)
    {
        if (fieldValue != null && fieldValue.length() > 0)
        {
            sql.append(" and ").append(fieldName).append("<='").append(encode(fieldValue)).append("'");
        }
    }

    /**
     * 向SQL语句中添加一个大于式查询条件
     * @param sql SQL语句
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public static void addGreat(StringBuffer sql, String fieldName, String fieldValue)
    {
        if (fieldValue != null && fieldValue.length() > 0)
        {
            sql.append(" and ").append(fieldName).append(">'").append(encode(fieldValue)).append("'");
        }
    }

    /**
     * 向SQL语句中添加一个大于等于式查询条件
     * @param sql SQL语句
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public static void addGreatEqual(StringBuffer sql, String fieldName, String fieldValue)
    {
        if (fieldValue != null && fieldValue.length() > 0)
        {
            sql.append(" and ").append(fieldName).append(">='").append(encode(fieldValue)).append("'");
        }
    }

    /**
     * 向SQL语句中添加一个全模糊匹配格式的查询条件
     * @param sql SQL语句
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public static void addAllLike(StringBuffer sql, String fieldName, String fieldValue)
    {
        if (fieldValue != null && fieldValue.length() > 0)
        {
            sql.append(" and ").append(fieldName).append(" like '%").append(encode(fieldValue)).append("%'");
        }
    }

    /**
     * 向SQL语句中添加一个左模糊匹配格式的查询条件
     * @param sql SQL语句
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public static void addLeftLike(StringBuffer sql, String fieldName, String fieldValue)
    {
        if (fieldValue != null && fieldValue.length() > 0)
        {
            sql.append(" and ").append(fieldName).append(" like '%").append(encode(fieldValue)).append("'");
        }
    }

    /**
     * 向SQL语句中添加一个右模糊匹配格式的查询条件
     * @param sql SQL语句
     * @param fieldName 字段名
     * @param fieldValue 字段值
     */
    public static void addRightLike(StringBuffer sql, String fieldName, String fieldValue)
    {
        if (fieldValue != null && fieldValue.length() > 0)
        {
            sql.append(" and ").append(fieldName).append(" like '").append(encode(fieldValue)).append("%'");
        }
    }

    /**
     * 获得一个相等式查询条件
     * @param fieldName 字段名
     * @param fieldValue 字段值
     * @return SQL条件语句
     */
    public static String getEqual(String fieldName, String fieldValue)
    {
        StringBuffer sql = new StringBuffer();
        if (fieldValue != null && fieldValue.length() > 0)
        {
            sql.append(" and ").append(fieldName).append("='").append(encode(fieldValue)).append("'");
        }
        return sql.toString();
    }

    /**
     * 获得一个小于式查询条件
     * @param fieldName 字段名
     * @param fieldValue 字段值
     * @return SQL条件语句
     */
    public static String getLess(String fieldName, String fieldValue)
    {
        StringBuffer sql = new StringBuffer();
        if (fieldValue != null && fieldValue.length() > 0)
        {
            sql.append(" and ").append(fieldName).append("<'").append(encode(fieldValue)).append("'");
        }
        return sql.toString();
    }

    /**
     * 获得一个小于等于式查询条件
     * @param fieldName 字段名
     * @param fieldValue 字段值
     * @return SQL条件语句
     */
    public static String getLessEqual(String fieldName, String fieldValue)
    {
        StringBuffer sql = new StringBuffer();
        if (fieldValue != null && fieldValue.length() > 0)
        {
            sql.append(" and ").append(fieldName).append("<='").append(encode(fieldValue)).append("'");
        }
        return sql.toString();
    }

    /**
     * 获得一个大于式查询条件
     * @param fieldName 字段名
     * @param fieldValue 字段值
     * @return SQL条件语句
     */
    public static String getGreat(String fieldName, String fieldValue)
    {
        StringBuffer sql = new StringBuffer();
        if (fieldValue != null && fieldValue.length() > 0)
        {
            sql.append(" and ").append(fieldName).append("<'").append(encode(fieldValue)).append("'");
        }
        return sql.toString();
    }

    /**
     * 获得一个大于等于式查询条件
     * @param fieldName 字段名
     * @param fieldValue 字段值
     * @return SQL条件语句
     */
    public static String getGreatEqual(String fieldName, String fieldValue)
    {
        StringBuffer sql = new StringBuffer();
        if (fieldValue != null && fieldValue.length() > 0)
        {
            sql.append(" and ").append(fieldName).append("<='").append(encode(fieldValue)).append("'");
        }
        return sql.toString();
    }

    /**
     * 获得一个模糊匹配格式的查询条件
     * @param fieldName 字段名
     * @param fieldValue 字段值
     * @return SQL条件语句
     */
    public static String getAllLike(String fieldName, String fieldValue)
    {
        StringBuffer sql = new StringBuffer();
        if (fieldValue != null && fieldValue.length() > 0)
        {
            sql.append(" and ").append(fieldName).append(" like '%").append(encode(fieldValue)).append("%'");
        }
        return sql.toString();
    }

    /**
     * 获得一个模糊匹配格式的查询条件
     * @param fieldName 字段名
     * @param fieldValue 字段值
     * @return SQL条件语句
     */
    public static String getLeftLike(String fieldName, String fieldValue)
    {
        StringBuffer sql = new StringBuffer();
        if (fieldValue != null && fieldValue.length() > 0)
        {
            sql.append(" and ").append(fieldName).append(" like '%").append(encode(fieldValue)).append("'");
        }
        return sql.toString();
    }

    /**
     * 获得一个模糊匹配格式的查询条件
     * @param fieldName 字段名
     * @param fieldValue 字段值
     * @return SQL条件语句
     */
    public static String getRightLike(String fieldName, String fieldValue)
    {
        StringBuffer sql = new StringBuffer();
        if (fieldValue != null && fieldValue.length() > 0)
        {
            sql.append(" and ").append(fieldName).append(" like '").append(encode(fieldValue)).append("%'");
        }
        return sql.toString();
    }

    /**
     * 对拼接SQL时传入的值进行编码，即把每一个英文单引号转换成两个英文单引号，以使拼接的SQL语句可以正常执行。
     * @param s 被编码的字符串
     * @return 编码后的字符串
     */
    public static String encode(String s)
    {
        return s.replaceAll("'", "''");
    }

    /**
     * <p>把字符串数组每一项作为SQL中带有单引号的字符串格式连接成一串字符，可用于SQL中的in条件语句。</p>
     * 例如：字符串数组为：{ "123", "abc", "qqq" }，则结果为 '123','abc','qqq'
     * @param strs 字符串数组
     * @return 拼接后的字符串
     */
    public static String connectAsString(String[] strs)
    {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < strs.length; i++)
        {
            s.append("'").append(strs[i]).append("',");
        }
        if (s.length() > 0)
        {
            s.deleteCharAt(s.length() - 1);
        }
        return s.toString();
    }

    /**
     * <p>把字符串数组每一项作为SQL中的数字格式连接成一串字符，可用于SQL中的in条件语句。</p>
     * 例如：字符串数组为：{ "123", "777", "100" }，则结果为 123,777,100
     * @param strs 字符串数组
     * @return 拼接后的字符串
     */
    public static String connectAsNumber(String[] strs)
    {
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < strs.length; i++)
        {
            s.append("'").append(strs[i]).append("',");
        }
        if (s.length() > 0)
        {
            s.deleteCharAt(s.length() - 1);
        }
        return s.toString();
    }

    /**
     * 示例
     * @param args 
     */
    public static void main(String[] args)
    {
        String areaId = "1";
        String custName = "###ejet###";

        StringBuffer sql = new StringBuffer();

        //第一种方式
        sql.append("select * from table1 a,table2 b where a.id=b.id")
                .append(SQLUtil.getEqual("a.areaid", areaId))
                .append(SQLUtil.getAllLike("a.custname", custName));

        System.out.println(sql.toString());

        //第二种方式
        sql.delete(0, sql.length());
        sql.append("select * from table1 a,table2 b where a.id=b.id");
        SQLUtil.addEqual(sql, "a.areaid", areaId);
        SQLUtil.addAllLike(sql, "a.custname", custName);

        System.out.println(sql.toString());
    }
}
