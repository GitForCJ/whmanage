package com.wlt.webm.util;

import java.util.*;

/**
 * �������㴦����<br>
 */
public class Operation
{
    /**
     * �������ַ���ת����double������Ӳ����ַ��������ʽ���أ�nullת��Ϊ0��
     * @param str1 ������
     * @param str2 ����
     * @return ������Ӻ������ת���ɵ��ַ���
     */
    public static String plus(String str1, String str2)
    {
        return String.valueOf(parseDouble(str1) + parseDouble(str2));
    }

    /**
     * ����ַ�������������Ԫ����double��ʽ�ĺϼ�
     * @param strs String[] �ַ�������
     * @return �ϼƵ�����
     */
    public static String plus(String[] strs)
    {
        return plus(strs, 0, strs.length);
    }

    /**
     * ����ַ���������ĳ����Ԫ����double��ʽ�ĺϼ�
     * @param strs String[] �ַ�������
     * @param start �������ʼԪ�أ�������
     * @return �ϼƵ�����
     */
    public static String plus(String[] strs, int start)
    {
        return plus(strs, start, strs.length);
    }

    /**
     * ����ַ���������ĳ����Ԫ����double��ʽ�ĺϼ�
     * @param strs String[] �ַ�������
     * @param start �������ʼԪ�أ�������
     * @param end �������ֹԪ�أ���������
     * @return �ϼƵ�����
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
     * �������������ͬ������һһ��Ӻ�Ľ��
     * @param str1 String[] ����������
     * @param str2 String[] ��������
     * @return String[] ��������һһ��Ӻ�Ľ������
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
     * ����������List������ӻ��һ���µ�List���ݡ�
     * @param firstList List(String[]) ��һ��List����
     * @param secondList List(String[]) �ڶ���List����
     * @param startCol Ҫ�������ʼ��
     * @return List(String[]) ��һ��List��ȥ�ڶ���List�����ɵ�����
     */
    public static List plus(List firstList, List secondList, int startCol)
    {
        int firstSize = firstList.size();
        int secondSize = secondList.size();
        int minSize = firstSize > secondSize ? secondSize : firstSize;

        List list = new ArrayList();

        for (int i = 0; i < minSize; i++)//��ͬ����
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
     * ����һ�µ�����Ƕ��Map�е���Ͳ�List(String[])������double��ʽ��ӻ��һ���µ�Map
     * @param firstMap ������
     * @param secondMap ����
     * @param startCol Map����Ͳ�List�м������ʼ��
     * @return Map ��Ӻ������
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
     * �������ַ���ת����double������������ַ��������ʽ���أ�nullת��Ϊ0��
     * @param str1 ������
     * @param str2 ����
     * @return ��������������ת���ɵ��ַ���
     */
    public static String minus(String str1, String str2)
    {
        return String.valueOf(parseDouble(str1) - parseDouble(str2));
    }

    /**
     * �����double��ʽ�ѵ�һ�������������ÿһ��Ľ��
     * @param strs String[] �ַ�������
     * @return ����������
     */
    public static String minus(String[] strs)
    {
        return minus(strs, 0, strs.length);
    }

    /**
     * �����double��ʽ����ʼ�������������ÿһ��Ľ��
     * @param strs String[] �ַ�������
     * @param start �������ʼԪ�أ�������
     * @return ����������
     */
    public static String minus(String[] strs, int start)
    {
        return minus(strs, start, strs.length);
    }

    /**
     * �����double��ʽ����ʼ�������������ÿһ������ֹ��Ľ��
     * @param strs String[] �ַ�������
     * @param start �������ʼԪ�أ�������
     * @param end �������ֹԪ�أ���������
     * @return ����������
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
     * �������������ͬ������һһ�����Ľ��
     * @param str1 String[] ����������
     * @param str2 String[] ��������
     * @return String[] ��������һһ�����Ľ������
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
     * ����������List����������һ���µ�List���ݡ�
     * @param firstList List(String[]) ��һ��List����
     * @param secondList List(String[]) �ڶ���List����
     * @param startCol Ҫ�������ʼ��
     * @return List(String[]) ��һ��List��ȥ�ڶ���List�����ɵ�����
     */
    public static List minus(List firstList, List secondList, int startCol)
    {
        int firstSize = firstList.size();
        int secondSize = secondList.size();
        int minSize = firstSize > secondSize ? secondSize : firstSize;

        List list = new ArrayList();

        for (int i = 0; i < minSize; i++)//��ͬ����
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
     * ����һ�µ�����Ƕ��Map�е���Ͳ�List(String[])������double��ʽ������һ���µ�Map
     * @param firstMap ������
     * @param secondMap ����
     * @param startCol Map����Ͳ�List�м������ʼ��
     * @return Map �����������
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
     * �������ַ���ת����double������˲����ַ��������ʽ���أ�nullת��Ϊ0��
     * @param str1 ������
     * @param str2 ����
     * @return ������˺������ת���ɵ��ַ���
     */
    public static String multiply(String str1, String str2)
    {
        return String.valueOf(parseDouble(str1) * parseDouble(str2));
    }

    /**
     * ����ַ�������������Ԫ����double��˵Ľ��
     * @param strs String[] �ַ�������
     * @return ��˵Ľ��
     */
    public static String multiply(String[] strs)
    {
        return multiply(strs, 0, strs.length);
    }

    /**
     * ����ַ���������ĳ����Ԫ����double��˵Ľ��
     * @param strs String[] �ַ�������
     * @param start �������ʼԪ�أ�������
     * @return ��˵Ľ��
     */
    public static String multiply(String[] strs, int start)
    {
        return multiply(strs, start, strs.length);
    }

    /**
     * ����ַ���������ĳ����Ԫ����double��ʽ��˵Ľ��
     * @param strs String[] �ַ�������
     * @param start �������ʼԪ�أ�������
     * @param end �������ֹԪ�أ���������
     * @return �˷�����Ľ��
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
     * �������������ͬ������һһ��˺�Ľ��
     * @param str1 String[] ����������
     * @param str2 String[] ��������
     * @return String[] ��������һһ��˺�Ľ������
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
     * ����������List������˻��һ���µ�List���ݡ�
     * @param firstList List(String[]) ��һ��List����
     * @param secondList List(String[]) �ڶ���List����
     * @param startCol Ҫ�������ʼ��
     * @return List(String[]) ��һ��List���Եڶ���List�����ɵ�����
     */
    public static List multiply(List firstList, List secondList, int startCol)
    {
        int firstSize = firstList.size();
        int secondSize = secondList.size();
        int minSize = firstSize > secondSize ? secondSize : firstSize;

        List list = new ArrayList();

        for (int i = 0; i < minSize; i++)//��ͬ����
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
     * ����һ�µ�����Ƕ��Map�е���Ͳ�List(String[])������double��ʽ��˻��һ���µ�Map
     * @param firstMap ������
     * @param secondMap ����
     * @param startCol Map����Ͳ�List�м������ʼ��
     * @return Map ��˺������
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
     * ������������������ַ��������ʽ���أ�����Ϊ0����Ϊ"0"����
     * @param d1 ������
     * @param d2 ����
     * @return ��������������ת���ɵ��ַ���
     */
    public static String divide(double d1, double d2)
    {
        return d2 == 0 ? "0" : String.valueOf(d1 / d2);
    }

    /**
     * �������ַ���ת����double������������ַ��������ʽ���أ�nullת��Ϊ0������Ϊ0����Ϊ"0"����
     * @param str1 ������
     * @param str2 ����
     * @return ��������������ת���ɵ��ַ���
     */
    public static String divide(String str1, String str2)
    {
        return divide(parseDouble(str1), parseDouble(str2));
    }

    /**
     * ����ַ�������������Ԫ����double����Ľ��
     * @param strs String[] �ַ�������
     * @return ����Ľ��
     */
    public static String divide(String[] strs)
    {
        return divide(strs, 0, strs.length);
    }

    /**
     * ����ַ���������ĳ����Ԫ����double����Ľ��
     * @param strs String[] �ַ�������
     * @param start �������ʼԪ�أ�������
     * @return ����Ľ��
     */
    public static String divide(String[] strs, int start)
    {
        return divide(strs, start, strs.length);
    }

    /**
     * ����ַ���������ĳ����Ԫ����double��ʽ����Ľ��
     * @param strs String[] �ַ�������
     * @param start �������ʼԪ�أ�������
     * @param end �������ֹԪ�أ���������
     * @return �������Ľ��
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
     * �������������ͬ������һһ�����Ľ��
     * @param str1 String[] ����������
     * @param str2 String[] ��������
     * @return String[] ��������һһ�����Ľ������
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
     * ����List�е�������double��ʽ������һ���µ�List������Ϊ0����Ϊ"0"��
     * @param firstList List(String[]) ������
     * @param secondList List(String[]) ����
     * @param startCol List�м������ʼ��
     * @return List(String[]) ����������
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
     * ����һ�µ�����Ƕ��Map�е���Ͳ�List(String[])������double��ʽ������һ���µ�Map������Ϊ0����Ϊ"0"��
     * @param firstMap ������
     * @param secondMap ����
     * @param startCol Map����Ͳ�List�м������ʼ��
     * @return Map ����������
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
     * �����ַ���Ϊ˫���ȸ�����������ַ���Ϊnull�����Ϊ0��
     * @param str Ҫ�������ַ���
     * @return ���ַ��������ĸ�����
     */
    private static double parseDouble(String str)
    {
        return str == null || str.length() == 0 ? 0 : Double.parseDouble(str);
    }
}
