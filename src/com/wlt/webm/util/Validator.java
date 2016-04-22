package com.wlt.webm.util;
public class Validator
{
    /**
     * 是否是数字格式的字符串
     * @param s 字符串
     * @return true，是
     */
    public static boolean isDigit(String s)
    {
        return s.matches("^\\d+$");
    }

    /**
     * 是否是26个大小写英文字符组成的字符串
     * @param s 字符串
     * @return true，是
     */
    public static boolean isAlpha(String s)
    {
        return s.matches("^[a-zA-Z]+$");
    }

    /**
     * 是否只含有大写英文字符
     * @param s 字符串
     * @return true，是
     */
    public static boolean isUpper(String s)
    {
        return s.matches("^[A-Z]+$");
    }

    /**
     * 是否只含有小写英文字符
     * @param s 字符串
     * @return true，是
     */
    public static boolean isLower(String s)
    {
        return s.matches("^[a-z]+$");
    }

    /**
     * 是否只含有26个大小写英文字符和数字字符的字符串
     * @param s 字符串
     * @return true，是
     */
    public static boolean isAlnum(String s)
    {
        return s.matches("^[a-zA-Z\\d]+$");
    }

    /**
     * 是否是可用于注册用户名和密码的字符（26个大小写英文字符、数字、下划线、横线）
     * @param s 字符串
     * @return true，是
     */
    public static boolean isCode(String s)
    {
        return s.matches("^[a-zA-Z\\d_-]+$");
    }

    /**
     * 是否是整数
     * @param s 字符串
     * @return true，是
     */
    public static boolean isInt(String s)
    {
        return s.matches("^[+-]?\\d+$");
    }

    /**
     * 是否是浮点数
     * @param s 字符串
     * @return true，是
     */
    public static boolean isFloat(String s)
    {
        return s.matches("^[+-]?(0\\.\\d+|0|[1-9]\\d*(\\.\\d+)?)$");
    }

    /**
     * 是否是邮件地址格式
     * @param s 字符串
     * @return true，是
     */
    public static boolean isEmail(String s)
    {
        return s.matches("^([a-zA-Z0-9_\\-\\.])+@([a-zA-Z0-9_-])+\\.([a-zA-Z0-9]){2,3}$");
    }

    /**
     * 是否是闰年
     * @param year 整数
     * @return true，是
     */
    public static boolean isLeapYear(int year)
    {
        return ((year % 4 == 0 && year % 100 != 0) || (year % 100 == 0 && year % 400 == 0));
    }

    /**
     * 是否是闰年
     * @param s 字符串
     * @return true，是
     */
    public static boolean isLeapYear(String s)
    {
        int year = 0;
        try
        {
            year = Integer.parseInt(s);
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return isLeapYear(year);
    }

    /**
     * 是否是日期格式（yyyy-MM），包括对日期范围的验证。
     * @param s 字符串
     * @return true，是
     */
    public static boolean isYearMonth(String s)
    {
        return s.matches("^\\d{4}-(0[1-9]|1[0-2])$");
    }

    /**
     * 是否是日期格式（yyyy-MM-dd），包括对日期范围的验证。
     * @param s 字符串
     * @return true，是
     */
    public static boolean isDate(String s)
    {
        if (s.length() == 10)
        {
            String dateRegex = "\\d{4}-(((01|03|05|07|08|10|12)-(0[1-9]|[12]\\d|3[01]))|((04|06|09|11)-(0[1-9]|[12]\\d|30))|(02-";
            dateRegex += isLeapYear(s.substring(0, 4)) ? "(0[1-9]|[12]\\d)" : "(0[1-9]|1\\d|2[0-8])";
            dateRegex += "))";
            return s.matches(dateRegex);
        }
        return false;
    }

    /**
     * 是否是日期格式（yyyyMMdd）
     * @param s 字符串
     * @return true，是
     */
    public static boolean isDate2(String s)
    {
        if (s.length() == 8)
        {
            String dateRegex = "\\d{4}(((01|03|05|07|08|10|12)(0[1-9]|[12]\\d|3[01]))|((04|06|09|11)(0[1-9]|[12]\\d|30))|(02";
            dateRegex += isLeapYear(s.substring(0, 4)) ? "(0[1-9]|[12]\\d)" : "(0[1-9]|1\\d|2[0-8])";
            dateRegex += "))";
            return s.matches(dateRegex);
        }
        return false;
    }

    /**
     * 是否是日期格式（yyMMdd），其中年前面两位默认为20开头。
     * @param s 字符串
     * @return true，是
     */
    public static boolean isDate3(String s)
    {
        if (s.length() == 6)
        {
            String dateRegex = "\\d{2}(((01|03|05|07|08|10|12)(0[1-9]|[12]\\d|3[01]))|((04|06|09|11)(0[1-9]|[12]\\d|30))|(02";
            dateRegex += isLeapYear("20" + s.substring(0, 2)) ? "(0[1-9]|[12]\\d)" : "(0[1-9]|1\\d|2[0-8])";
            dateRegex += "))";
            return s.matches(dateRegex);
        }
        return false;
    }

    /**
     * 是否是时间格式（HH:mm:ss），包括对时间范围的验证。
     * @param s 字符串
     * @return true，是
     */
    public static boolean isTime(String s)
    {
        return s.matches("^([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$");
    }

    /**
     * 是否是时间格式（HHmmss），包括对时间范围的验证。
     * @param s 字符串
     * @return true，是
     */
    public static boolean isTime2(String s)
    {
    	return s.matches("^([01]\\d|2[0-3])[0-5]\\d[0-5]\\d$");
    }
    
    /**
     * 是否是日期时间格式（yyyy-MM-dd HH:mm:ss），包括对日期范围的验证。
     * @param s 字符串
     * @return true，是
     */
    public static boolean isDateTime(String s)
    {
        return (s.length() == 19 && s.charAt(10) == ' ') ? (isDate(s.substring(0, 10)) && isTime(s.substring(11, 19))) : false;
    }

    /**
     * 是否是日期时间格式（yyyyMMddHHmmss）
     * @param s 字符串
     * @return true，是
     */
    public static boolean isDateTime2(String s)
    {
        return (s.length() == 14) ? (isDate2(s.substring(0, 8)) && isTime2(s.substring(8, 14))) : false;
    }

    /**
     * 是否是日期时间格式（yyMMddHHmmss），其中年前面两位默认为20开头。
     * @param s 字符串
     * @return true，是
     */
    public static boolean isDateTime3(String s)
    {
        return (s.length() == 12) ? (isDate3(s.substring(0, 6)) && isTime2(s.substring(6, 12))) : false;
    }

    /**
     * 是否是IP地址格式（用点"."分割的四组数字中，第一组数字范围1-223，其他三组数字范围0-255）
     * @param s 字符串
     * @return true，是
     */
    public static boolean isIP(String s)
    {
        return s.matches("^(0?0?[1-9]|0?[1-9]\\d|1\\d\\d|2[01]\\d|22[0-3])(\\.([01]?\\d?\\d|2[0-4]\\d|25[0-5])){3}$");
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args)
    {
    }
}
