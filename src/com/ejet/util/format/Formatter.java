package com.ejet.util.format;

import java.util.*;

/**
 * 格式化数据工具类
 * Company：深圳市###EJET###有限公司
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author ###ejet###
 */
public class Formatter
{
    /** null 格式化为 "" */
    public static final Format EMPTY = new FillFormat("");

    /** 去两边空格 */
    public static final Format TRIM = new TrimFormat();

    /** 格式化为整数 */
    public static final Format INT = new NumberFormat(0);

    /** 格式化为保留两位小数的浮点数 */
    public static final Format FLOAT2 = new NumberFormat(2);

    /** 除以60后并格式化为两位小数的浮点数（例：将分钟转换为小时） */
    public static final Format D60F2 = new NumberFormat(1D/60, 2);

    /** 除以100后并格式化为两位小数的浮点数（例：将分转换为元） */
    public static final Format D100F2 = new NumberFormat(1D/100, 2);

    /** 除以1000后并格式化为两位小数的浮点数（例：将厘转换为元） */
    public static final Format D1000F2 = new NumberFormat(1D/1000, 2);

    /** 格式化为没有小数的百分比格式 */
    public static final Format PERCENT = new PercentFormat(0);

    /** 格式化为保留两位小数点的百分比格式 */
    public static final Format PERCENT2 = new PercentFormat(2);

    /** 除以100后并格式化为保留两位小数点的百分比格式 */
    public static final Format D100P2 = new PercentFormat(1D/100, 2);

    /** 除以10000后并格式化为保留两位小数点的百分比格式 */
    public static final Format D10000P2 = new PercentFormat(1D/10000, 2);

    /** 格式化日期：yyyyMM 转换为 yyyy-MM */
    public static final Format YM_ = new DateFormat("yyyyMM", "yyyy-MM");

    /** 格式化日期：yyyyMMdd 转换为 yyyy-MM-dd */
    public static final Format YMD_ = new DateFormat("yyyyMMdd", "yyyy-MM-dd");

    /** 格式化日期：HHmmss 转换为 HH:mm:ss */
    public static final Format HMS_ = new DateFormat("HHmmss", "HH:mm:ss");

    /** 格式化日期：yyyyMMddHHmmss 转换为 yyyy-MM-dd HH:mm:ss */
    public static final Format YMDHMS_ = new DateFormat("yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss");

    /** 格式化日期：yyyy-MM 转换为 yyyyMM */
    public static final Format YM = new DateFormat("yyyy-MM", "yyyyMM");

    /** 格式化日期：yyyy-MM-dd 转换为 yyyyMMdd */
    public static final Format YMD = new DateFormat("yyyy-MM-dd", "yyyyMMdd");

    /** 格式化日期：HH:mm:ss 转换为 HHmmss */
    public static final Format HMS = new DateFormat("HH:mm:ss", "HHmmss");

    /** 格式化日期：yyyy-MM-dd HH:mm:ss 转换为 yyyyMMddHHmmss */
    public static final Format YMDHMS = new DateFormat("yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss");

    /**
     * 格式化数字
     * @param d 数字
     * @param f 格式化类，可为null。
     * @return 格式化后的字符串，若格式化类为null则返回字符串表示的数字。
     */
    public static String format(double d, Format f)
    {
        return format(String.valueOf(d), f);
    }

    /**
     * 格式化字符串
     * @param str 被格式化的字符串，可为null。
     * @param f 格式化类，可为null。
     * @return 格式化后的字符串，若格式化类为null则返回原字符串。
     */
    public static String format(String str, Format f)
    {
        return f == null ? str : f.format(str);
    }

    /**
     * 格式化字符串数组（改变了源数据）
     * @param strs 字符串数组，可为null。
     * @param f 格式化类，可为null。
     * @return 被格式化后的字符串数组，若格式化类为null则返回原字符串数组。
     */
    public static String[] format(String[] strs, Format f)
    {
        return strs == null ? null : format(strs, 0, strs.length, f);
    }

    /**
     * 从字符串数组的起始位置格式化该字符串数组（改变了源数据）
     * @param strs 字符串数组，可为null。
     * @param start 字符串数组的起始位置，0开始（包含）。
     * @param f 格式化类，可为null。
     * @return 被格式化后的字符串数组，若格式化类为null则返回原字符串数组。
     */
    public static String[] format(String[] strs, int start, Format f)
    {
        return strs == null ? null : format(strs, start, strs.length, f);
    }

    /**
     * 从字符串数组的起始位置至结束位置格式化该字符串数组（改变了源数据）
     * @param strs 字符串数组，可为null。
     * @param start 起始位置，0开始（包含）
     * @param end 结束位置（不包含）
     * @param f 格式化类，可为null。
     * @return 被格式化后的字符串数组，若格式化类为null则返回原字符串数组。
     */
    public static String[] format(String[] strs, int start, int end, Format f)
    {
        if (strs != null)
        {
            for (int i = start; i < end; i++)
            {
                strs[i] = format(strs[i], f);
            }
        }
        return strs;
    }

    /**
     * 格式化字符串数组（改变了源数据）
     * @param strs 字符串数组，可为null。
     * @param fs 格式化类数组
     * @return 被格式化后的字符串数组
     */
    public static String[] format(String[] strs, Format[] fs)
    {
        return strs == null ? null : format(strs, 0, strs.length, fs);
    }

    /**
     * 从字符串数组的起始位置格式化该字符串数组（改变了源数据）
     * @param strs 字符串数组，可为null。
     * @param start 起始位置，0开始（包含）
     * @param fs 格式化类数组
     * @return 被格式化后的字符串数组
     */
    public static String[] format(String[] strs, int start, Format[] fs)
    {
        return strs == null ? null : format(strs, start, strs.length, fs);
    }

    /**
     * 从字符串数组的起始位置至结束位置格式化该字符串数组（改变了源数据）
     * @param strs 字符串数组，可为null。
     * @param start 起始位置，0开始（包含）
     * @param end 结束位置（不包含）
     * @param fs 格式化类数组
     * @return 被格式化后的字符串数组
     */
    public static String[] format(String[] strs, int start, int end, Format[] fs)
    {
        if (strs != null)
        {
            for (int i = start, j = 0; i < end; i++, j++)
            {
                strs[i] = format(strs[i], fs[j]);
            }
        }
        return strs;
    }

    /**
     * 格式化数据列表（改变了源数据）
     * @param list List(String[]) 数组元素长度不定长的数据列表
     * @param f 格式化类
     * @return 被格式化后的数据列表
     */
    public static List format(List list, Format f)
    {
        return list == null ? null : format(list, 0, f);
    }

    /**
     * 格式化数据列表（改变了源数据）
     * @param list List(String[]) 数组元素长度不定长的数据列表，可为null。
     * @param startCol 起始列
     * @param f 格式化类
     * @return 被格式化后的数据列表
     */
    public static List format(List list, int startCol, Format f)
    {
        if (list != null)
        {
            for (int i = 0, ii = list.size(); i < ii; i++)
            {
                String[] strs = (String[]) list.get(i);
                format(strs, startCol, strs.length, f);
            }
        }
        return list;
    }

    /**
     * 格式化数据列表（改变了源数据）
     * @param list List(String[]) 数组元素长度不定长的数据列表，可为null。
     * @param startCol 起始列，0开始（包含）
     * @param endCol 终止列（不包含）
     * @param f 格式化类
     * @return 被格式化后的数据列表
     */
    public static List format(List list, int startCol, int endCol, Format f)
    {
        if (list != null)
        {
            for (int i = 0, ii = list.size(); i < ii; i++)
            {
                format((String[]) list.get(i), startCol, endCol, f);
            }
        }
        return list;
    }

    /**
     * 格式化数据列表（改变了源数据）
     * @param list List(String[]) 数组元素长度和格式化类数组长度一致的数据列表，可为null。
     * @param fs 格式化类数组
     * @return 被格式化后的数据列表
     */
    public static List format(List list, Format[] fs)
    {
        if (list != null)
        {
            for (int i = 0, ii = list.size(); i < ii; i++)
            {
                format((String[]) list.get(i), fs);
            }
        }
        return list;
    }

    /**
     * 格式化任意嵌套的数据Map，最低层必须是数据列表List(String[])（改变了源数据）
     * @param map 任意嵌套的Map，最低层为字符串数组元素的列表List(String[])，其中字符串数组元素可不定长。该参数可为null。
     * @param f 格式化类
     * @return 被格式化后的Map
     */
    public static Map format(Map map, Format f)
    {
        return map == null ? null : format(map, 0, f);
    }

    /**
     * 格式化任意嵌套的数据Map，最低层必须是数据列表List(String[])（改变了源数据）
     * @param map 任意嵌套的Map，最低层为字符串数组元素的列表List(String[])，其中字符串数组元素可不定长。该参数可为null。
     * @param startCol 格式化的起始列，0开始（包含）
     * @param f 格式化类
     * @return 被格式化后的Map
     */
    public static Map format(Map map, int startCol, Format f)
    {
        if (map != null)
        {
            for (Iterator i = map.keySet().iterator(); i.hasNext();)
            {
                Object value = map.get(i.next());
                if (value instanceof Map)
                {
                    format((Map) value, startCol, f);
                }
                else
                {
                    format((List) value, startCol, f);
                }
            }
        }
        return map;
    }

    /**
     * 格式化任意嵌套的数据Map，最低层必须是数据列表List(String[])（改变了源数据）
     * @param map 任意嵌套的Map，最低层为字符串数组元素的列表List(String[])，其中字符串数组元素可不定长。该参数可为null。
     * @param startCol 格式化的起始列，0开始（包含）
     * @param endCol 格式化的结束列（不包含）
     * @param f 格式化类
     * @return 被格式化后的Map
     */
    public static Map format(Map map, int startCol, int endCol, Format f)
    {
        if (map != null)
        {
            for (Iterator i = map.keySet().iterator(); i.hasNext();)
            {
                Object value = map.get(i.next());
                if (value instanceof Map)
                {
                    format((Map) value, startCol, endCol, f);
                }
                else
                {
                    format((List) value, startCol, endCol, f);
                }
            }
        }
        return map;
    }

    /**
     * 格式化任意嵌套的数据Map，最低层必须是数据列表List(String[])（改变了源数据）
     * @param map 任意嵌套的Map，最低层为字符串数组元素的列表List(String[])，其中字符串数组元素可不定长。该参数可为null。
     * @param fs 格式化类数组
     * @return 被格式化后的Map
     */
    public static Map format(Map map, Format[] fs)
    {
        if (map != null)
        {
            for (Iterator i = map.keySet().iterator(); i.hasNext();)
            {
                Object value = map.get(i.next());
                if (value instanceof Map)
                {
                    format((Map) value, fs);
                }
                else
                {
                    format((List) value, fs);
                }
            }
        }
        return map;
    }
}
