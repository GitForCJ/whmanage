package com.ejet.util.format; 
/**
 * 去字符串两边空格的格式化类
 * Company：深圳市###EJET###有限公司
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author ###ejet###
 */
public class TrimFormat implements Format
{
    /**
     * 把字符串两边的空格去掉
     * @param str 被格式化的字符串
     * @return 格式化后的字符串
     */
    public String format(String str)
    {
        return str == null ? str : str.trim();
    }
}
