package com.ejet.util.format;

import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 * 日期格式化类
 * Company：深圳市###EJET###有限公司
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author ###ejet###
 */
public class DateFormat implements Format
{
    /**
     * 解析日期的对象
     */
    private SimpleDateFormat dateParse;

    /**
     * 格式化日期的对象
     */
    private SimpleDateFormat dateFormat;

    /**
     * 自定义解析和格式化日期的构造方法
     * @param parsePattern 自定义解析日期的字符串模式
     * @param formatPattern 自定义格式化日期的字符串模式
     */
    public DateFormat(String parsePattern, String formatPattern)
    {
        this.dateParse = new SimpleDateFormat(parsePattern);
        this.dateFormat = new SimpleDateFormat(formatPattern);
    }

    /**
     * 将紧凑的日期时间字符串转换直观的日期字符串。
     * @param str 被格式化的紧凑日期字符串
     * @return 格式化后的字符串，无法格式化的字符串则直接返回。
     */
    public String format(String str)
    {
        if (str == null)
        {
            return null;
        }

        try
        {
            return dateFormat.format(dateParse.parse(str));
        }
        catch (ParseException e)
        {
            return str;
        }
    }
}
