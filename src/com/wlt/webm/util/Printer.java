package com.wlt.webm.util;

import java.util.*;

public class Printer
{
    /**
     * TAB键值
     */
    private static final String KEY_TAB = "\t";

    /**
     * 左中括号
     */
    private static final String KEY_LBRACKET = "[";

    /**
     * 右中括号
     */
    private static final String KEY_RBRACKET = "]";

    /**
     * 打印对象
     * @param obj Object
     */
    public static void print(Object obj)
    {
    }

    /**
     * 打印换行
     */
    public static void println()
    {
    }

    /**
     * 打印对象和换行符
     * @param obj Object
     */
    public static void println(Object obj)
    {
    }

    /**
     * 打印对象数组的值
     * @param os 对象数组
     */
    public static void print(Object[] os)
    {
        for (int i = 0; i < os.length; i++)
        {
            printWithBracket(os[i]);
        }
    }

    /**
     * 打印对象数组的值，每项独占一行。
     * @param os 对象数组
     */
    public static void println(Object[] os)
    {
        for (int i = 0; i < os.length; i++)
        {
            printlnWithBracket(os[i]);
        }
    }

    /**
     * 打印二维对象数组的内容
     * @param objs 二维对象数组的内容
     */
    public static void print(Object[][] objs)
    {
        for (int i = 0; i < objs.length; i++)
        {
            for (int j = 0; j < objs[i].length; j++)
            {
                printWithBracket(objs[i][j]);
                print(KEY_TAB);
            }
            println();
        }
    }

    /**
     * 打印列表的内容
     * @param list 普通对象或二维对象数组对象的列表
     */
    public static void print(List list)
    {
        for (int i = 0, il = list.size(); i < il; i++)
        {
            Object o = list.get(i);
            if (o instanceof Object[])
            {
                print((Object[]) o);
                println();
            }
            else
            {
                println(o);
            }
        }
    }

    /**
     * 以缩行的形式打印Map中的数据，支持深层嵌套Map，支持打印最里层的List，其他数据则打印其toString()方法的值。
     * @param map Map数据
     */
    public static void print(Map map)
    {
        print(map, 0);
    }

    /**
     * 根据缩行级别打印Map中的数据，支持深层嵌套Map，支持打印最里层的List，其他数据则打印其toString()方法的值。
     * @param map Map数据
     * @param level 缩行级别
     */
    private static void print(Map map, int level)
    {
        int nextLevel = level + 1;
        for (Iterator it = map.keySet().iterator(); it.hasNext();)
        {
            Object key = it.next();
            print(StringUtil.repeat(KEY_TAB, level));
            printlnWithBracket(key);
            Object obj = map.get(key);
            if (obj instanceof Map)
            {
                print((Map) obj, nextLevel);
            }
            else if (obj instanceof List)
            {
                List list = (List) obj;
                for (int i = 0, il = list.size(); i < il; i++)
                {
                    print(StringUtil.repeat(KEY_TAB, nextLevel));
                    String[] row = (String[]) list.get(i);
                    for (int j = 0; j < row.length; j++)
                    {
                        printWithBracket(row[j]);
                        print(KEY_TAB);
                    }
                    println();
                }
            }
            else if (obj instanceof Object[])
            {
                print(StringUtil.repeat(KEY_TAB, nextLevel));
                Object[] objs = (Object[]) obj;
                for (int i = 0; i < objs.length; i++)
                {
                    printWithBracket(objs[i]);
                    print(KEY_TAB);
                }
                println();
            }
            else
            {
                print(StringUtil.repeat(KEY_TAB, nextLevel));
                printlnWithBracket(obj.toString());
            }
        }
    }

    /**
     * 打印中括号括住的对象的toString()的值
     * @param obj 要打印的对象
     */
    private static void printWithBracket(Object obj)
    {
    }

    /**
     * 打印中括号括住的对象的toString()的值，并换行。
     * @param obj 要打印的对象
     */
    private static void printlnWithBracket(Object obj)
    {
    }
}
