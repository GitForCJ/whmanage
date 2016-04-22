package com.wlt.webm.util;

import java.util.*;

/**
 * 数据运算处理类<br>
 */
public class Operation
{
    /**
     * 把两个字符串转换成double数据相加并以字符串结果形式返回，null转换为0。
     * @param str1 被加数
     * @param str2 加数
     * @return 返回相加后的数字转换成的字符串
     */
    public static String plus(String str1, String str2)
    {
        return String.valueOf(parseDouble(str1) + parseDouble(str2));
    }

    /**
     * 获得字符串数组中所有元素以double形式的合计
     * @param strs String[] 字符串数组
     * @return 合计的数据
     */
    public static String plus(String[] strs)
    {
        return plus(strs, 0, strs.length);
    }

    /**
     * 获得字符串数组中某几个元素以double形式的合计
     * @param strs String[] 字符串数组
     * @param start 计算的起始元素（包含）
     * @return 合计的数据
     */
    public static String plus(String[] strs, int start)
    {
        return plus(strs, start, strs.length);
    }

    /**
     * 获得字符串数组中某几个元素以double形式的合计
     * @param strs String[] 字符串数组
     * @param start 计算的起始元素（包含）
     * @param end 计算的终止元素（不包含）
     * @return 合计的数据
     */
    public static String plus(String[] strs, int start, int end)
    {
        double sum = 0;
        for (int i = start; i < end; i++)
        {
            sum += parseDouble(strs[i]);
        }
        return String.valueOf(sum);
    }

    /**
     * 获得两个长度相同的数组一一相加后的结果
     * @param str1 String[] 被加数数组
     * @param str2 String[] 加数数组
     * @return String[] 两个数组一一相加后的结果数组
     */
    public static String[] plus(String[] str1, String[] str2)
    {
        String[] result = new String[str1.length];
        for (int i = 0; i < result.length; i++)
        {
            result[i] = plus(str1[i], str2[i]);
        }
        return result;
    }

    /**
     * 根据两个的List数据相加获得一个新的List数据。
     * @param firstList List(String[]) 第一个List数据
     * @param secondList List(String[]) 第二个List数据
     * @param startCol 要计算的起始列
     * @return List(String[]) 第一个List减去第二个List后生成的数据
     */
    public static List plus(List firstList, List secondList, int startCol)
    {
        int firstSize = firstList.size();
        int secondSize = secondList.size();
        int minSize = firstSize > secondSize ? secondSize : firstSize;

        List list = new ArrayList();

        for (int i = 0; i < minSize; i++)//相同的行
        {
            String[] firstRow = (String[]) firstList.get(i);
            String[] secondRow = (String[]) secondList.get(i);
            String[] row = new String[firstRow.length];
            System.arraycopy(firstRow, 0, row, 0, startCol);
            for (int j = startCol; j < firstRow.length; j++)
            {
                row[j] = plus(firstRow[j], secondRow[j]);
            }
            list.add(row);
        }

        return list;
    }

    /**
     * 两个一致的任意嵌套Map中的最低层List(String[])数据以double格式相加获得一个新的Map
     * @param firstMap 被加数
     * @param secondMap 加数
     * @param startCol Map中最低层List中计算的起始列
     * @return Map 相加后的数据
     */
    public static Map plus(Map firstMap, Map secondMap, int startCol)
    {
        Map map = new LinkedHashMap();

        Iterator it = firstMap.keySet().iterator();
        while (it.hasNext())
        {
            Object key = it.next();
            Object firstObj = firstMap.get(key);
            Object secondObj = secondMap.get(key);

            if (firstObj instanceof Map)
            {
                map.put(key, plus((Map) firstObj, (Map) secondObj, startCol));
            }
            else
            {
                map.put(key, plus((List) firstObj, (List) secondObj, startCol));
            }
        }

        return map;
    }

    /**
     * 把两个字符串转换成double数据相减并以字符串结果形式返回，null转换为0。
     * @param str1 被减数
     * @param str2 减数
     * @return 返回相减后的数字转换成的字符串
     */
    public static String minus(String str1, String str2)
    {
        return String.valueOf(parseDouble(str1) - parseDouble(str2));
    }

    /**
     * 获得以double格式把第一项依次相减后面每一项的结果
     * @param strs String[] 字符串数组
     * @return 相减后的数据
     */
    public static String minus(String[] strs)
    {
        return minus(strs, 0, strs.length);
    }

    /**
     * 获得以double格式把起始项依次相减后面每一项的结果
     * @param strs String[] 字符串数组
     * @param start 计算的起始元素（包含）
     * @return 相减后的数据
     */
    public static String minus(String[] strs, int start)
    {
        return minus(strs, start, strs.length);
    }

    /**
     * 获得以double格式把起始项依次相减后面每一项至终止项的结果
     * @param strs String[] 字符串数组
     * @param start 计算的起始元素（包含）
     * @param end 计算的终止元素（不包含）
     * @return 相减后的数据
     */
    public static String minus(String[] strs, int start, int end)
    {
        double d = 0;
        if (strs.length > 0)
        {
            d = parseDouble(strs[start]);
            for (int i = start + 1; i < end; i++)
            {
                d -= parseDouble(strs[i]);
            }
        }
        return String.valueOf(d);
    }

    /**
     * 获得两个长度相同的数组一一相减后的结果
     * @param str1 String[] 被减数数组
     * @param str2 String[] 减数数组
     * @return String[] 两个数组一一相减后的结果数组
     */
    public static String[] minus(String[] str1, String[] str2)
    {
        String[] result = new String[str1.length];
        for (int i = 0; i < result.length; i++)
        {
            result[i] = minus(str1[i], str2[i]);
        }
        return result;
    }

    /**
     * 根据两个的List数据相减获得一个新的List数据。
     * @param firstList List(String[]) 第一个List数据
     * @param secondList List(String[]) 第二个List数据
     * @param startCol 要计算的起始列
     * @return List(String[]) 第一个List减去第二个List后生成的数据
     */
    public static List minus(List firstList, List secondList, int startCol)
    {
        int firstSize = firstList.size();
        int secondSize = secondList.size();
        int minSize = firstSize > secondSize ? secondSize : firstSize;

        List list = new ArrayList();

        for (int i = 0; i < minSize; i++)//相同的行
        {
            String[] firstRow = (String[]) firstList.get(i);
            String[] secondRow = (String[]) secondList.get(i);
            String[] row = new String[firstRow.length];
            System.arraycopy(firstRow, 0, row, 0, startCol);
            for (int j = startCol; j < firstRow.length; j++)
            {
                row[j] = minus(firstRow[j], secondRow[j]);
            }
            list.add(row);
        }

        return list;
    }

    /**
     * 两个一致的任意嵌套Map中的最低层List(String[])数据以double格式相减获得一个新的Map
     * @param firstMap 被减数
     * @param secondMap 减数
     * @param startCol Map中最低层List中计算的起始列
     * @return Map 减数后的数据
     */
    public static Map minus(Map firstMap, Map secondMap, int startCol)
    {
        Map map = new LinkedHashMap();

        Iterator it = firstMap.keySet().iterator();
        while (it.hasNext())
        {
            Object key = it.next();
            Object firstObj = firstMap.get(key);
            Object secondObj = secondMap.get(key);

            if (firstObj instanceof Map)
            {
                map.put(key, minus((Map) firstObj, (Map) secondObj, startCol));
            }
            else
            {
                map.put(key, minus((List) firstObj, (List) secondObj, startCol));
            }
        }

        return map;
    }

    /**
     * 把两个字符串转换成double数据相乘并以字符串结果形式返回，null转换为0。
     * @param str1 被乘数
     * @param str2 乘数
     * @return 返回相乘后的数字转换成的字符串
     */
    public static String multiply(String str1, String str2)
    {
        return String.valueOf(parseDouble(str1) * parseDouble(str2));
    }

    /**
     * 获得字符串数组中所有元素以double相乘的结果
     * @param strs String[] 字符串数组
     * @return 相乘的结果
     */
    public static String multiply(String[] strs)
    {
        return multiply(strs, 0, strs.length);
    }

    /**
     * 获得字符串数组中某几个元素以double相乘的结果
     * @param strs String[] 字符串数组
     * @param start 计算的起始元素（包含）
     * @return 相乘的结果
     */
    public static String multiply(String[] strs, int start)
    {
        return multiply(strs, start, strs.length);
    }

    /**
     * 获得字符串数组中某几个元素以double形式相乘的结果
     * @param strs String[] 字符串数组
     * @param start 计算的起始元素（包含）
     * @param end 计算的终止元素（不包含）
     * @return 乘法运算的结果
     */
    public static String multiply(String[] strs, int start, int end)
    {
        double total = 0;
        for (int i = start; i < end; i++)
        {
            total *= parseDouble(strs[i]);
        }
        return String.valueOf(total);
    }

    /**
     * 获得两个长度相同的数组一一相乘后的结果
     * @param str1 String[] 被乘数数组
     * @param str2 String[] 乘数数组
     * @return String[] 两个数组一一相乘后的结果数组
     */
    public static String[] multiply(String[] str1, String[] str2)
    {
        String[] result = new String[str1.length];
        for (int i = 0; i < result.length; i++)
        {
            result[i] = multiply(str1[i], str2[i]);
        }
        return result;
    }

    /**
     * 根据两个的List数据相乘获得一个新的List数据。
     * @param firstList List(String[]) 第一个List数据
     * @param secondList List(String[]) 第二个List数据
     * @param startCol 要计算的起始列
     * @return List(String[]) 第一个List乘以第二个List后生成的数据
     */
    public static List multiply(List firstList, List secondList, int startCol)
    {
        int firstSize = firstList.size();
        int secondSize = secondList.size();
        int minSize = firstSize > secondSize ? secondSize : firstSize;

        List list = new ArrayList();

        for (int i = 0; i < minSize; i++)//相同的行
        {
            String[] firstRow = (String[]) firstList.get(i);
            String[] secondRow = (String[]) secondList.get(i);
            String[] row = new String[firstRow.length];
            System.arraycopy(firstRow, 0, row, 0, startCol);
            for (int j = startCol; j < firstRow.length; j++)
            {
                row[j] = multiply(firstRow[j], secondRow[j]);
            }
            list.add(row);
        }

        return list;
    }

    /**
     * 两个一致的任意嵌套Map中的最低层List(String[])数据以double格式相乘获得一个新的Map
     * @param firstMap 被乘数
     * @param secondMap 乘数
     * @param startCol Map中最低层List中计算的起始列
     * @return Map 相乘后的数据
     */
    public static Map multiply(Map firstMap, Map secondMap, int startCol)
    {
        Map map = new LinkedHashMap();

        Iterator it = firstMap.keySet().iterator();
        while (it.hasNext())
        {
            Object key = it.next();
            Object firstObj = firstMap.get(key);
            Object secondObj = secondMap.get(key);

            if (firstObj instanceof Map)
            {
                map.put(key, multiply((Map) firstObj, (Map) secondObj, startCol));
            }
            else
            {
                map.put(key, multiply((List) firstObj, (List) secondObj, startCol));
            }
        }

        return map;
    }

    /**
     * 把两个数据相除并以字符串结果形式返回（除数为0则结果为"0"）。
     * @param d1 被除数
     * @param d2 除数
     * @return 返回相除后的数字转换成的字符串
     */
    public static String divide(double d1, double d2)
    {
        return d2 == 0 ? "0" : String.valueOf(d1 / d2);
    }

    /**
     * 把两个字符串转换成double数据相除并以字符串结果形式返回，null转换为0（除数为0则结果为"0"）。
     * @param str1 被除数
     * @param str2 除数
     * @return 返回相除后的数字转换成的字符串
     */
    public static String divide(String str1, String str2)
    {
        return divide(parseDouble(str1), parseDouble(str2));
    }

    /**
     * 获得字符串数组中所有元素以double相除的结果
     * @param strs String[] 字符串数组
     * @return 相除的结果
     */
    public static String divide(String[] strs)
    {
        return divide(strs, 0, strs.length);
    }

    /**
     * 获得字符串数组中某几个元素以double相除的结果
     * @param strs String[] 字符串数组
     * @param start 计算的起始元素（包含）
     * @return 相除的结果
     */
    public static String divide(String[] strs, int start)
    {
        return divide(strs, start, strs.length);
    }

    /**
     * 获得字符串数组中某几个元素以double形式相除的结果
     * @param strs String[] 字符串数组
     * @param start 计算的起始元素（包含）
     * @param end 计算的终止元素（不包含）
     * @return 相除运算的结果
     */
    public static String divide(String[] strs, int start, int end)
    {
        String total = null;
        for (int i = start; i < end; i++)
        {
            total = divide(total, strs[i]);
        }
        return total;
    }

    /**
     * 获得两个长度相同的数组一一相除后的结果
     * @param str1 String[] 被除数数组
     * @param str2 String[] 除数数组
     * @return String[] 两个数组一一相除后的结果数组
     */
    public static String[] divide(String[] str1, String[] str2)
    {
        String[] result = new String[str1.length];
        for (int i = 0; i < result.length; i++)
        {
            result[i] = divide(str1[i], str2[i]);
        }
        return result;
    }

    /**
     * 两个List中的数据以double格式相除获得一个新的List（除数为0则结果为"0"）
     * @param firstList List(String[]) 被除数
     * @param secondList List(String[]) 除数
     * @param startCol List中计算的起始列
     * @return List(String[]) 相除后的数据
     */
    public static List divide(List firstList, List secondList, int startCol)
    {
        List rsList = new ArrayList();

        for (int i = 0; i < firstList.size(); i++)
        {
            String[] firstRow = (String[]) firstList.get(i);
            String[] secondRow = (String[]) secondList.get(i);
            String[] row = new String[firstRow.length];

            for (int j = 0; j < startCol; j++)
            {
                row[j] = firstRow[j];
            }

            for (int j = startCol; j < firstRow.length; j++)
            {
                row[j] = divide(firstRow[j], secondRow[j]);
            }

            rsList.add(row);
        }

        return rsList;
    }

    /**
     * 两个一致的任意嵌套Map中的最低层List(String[])数据以double格式相除获得一个新的Map（除数为0则结果为"0"）
     * @param firstMap 被除数
     * @param secondMap 除数
     * @param startCol Map中最低层List中计算的起始列
     * @return Map 相除后的数据
     */
    public static Map divide(Map firstMap, Map secondMap, int startCol)
    {
        Map map = new LinkedHashMap();

        Iterator it = firstMap.keySet().iterator();
        while (it.hasNext())
        {
            Object key = it.next();
            Object firstObj = firstMap.get(key);
            Object secondObj = secondMap.get(key);

            if (firstObj instanceof Map)
            {
                map.put(key, divide((Map) firstObj, (Map) secondObj, startCol));
            }
            else
            {
                map.put(key, divide((List) firstObj, (List) secondObj, startCol));
            }
        }

        return map;
    }

    /**
     * 解析字符串为双精度浮点数，如果字符串为null则解析为0。
     * @param str 要解析的字符串
     * @return 由字符串解析的浮点数
     */
    private static double parseDouble(String str)
    {
        return str == null || str.length() == 0 ? 0 : Double.parseDouble(str);
    }
}
