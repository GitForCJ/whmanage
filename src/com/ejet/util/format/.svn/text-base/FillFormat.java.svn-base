package com.ejet.util.format;

/**
 * 将 null 或 "" 格式化为指定的字符串
 * Company：深圳市###EJET###有限公司
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author ###ejet###
 */
public class FillFormat implements Format
{
    /**
     * 被填充的目标字符串
     */
    private String fillString;

    /**
     * 构造方法
     * @param fillString 被填充的目标字符串
     */
    public FillFormat(String fillString)
    {
        this.fillString = fillString;
    }

    /**
     * 将 null 或 "" 格式化为指定的字符串，否则返回原字符串。
     * @param str 被格式化的字符串
     * @return 格式化后的字符串
     */
    public String format(String str)
    {
        return str == null || str.length() == 0 ? fillString : str;
    }
}
