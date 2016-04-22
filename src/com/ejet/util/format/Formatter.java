package com.ejet.util.format;

import java.util.*;

/**
 * ��ʽ�����ݹ�����
 * Company��������###EJET###���޹�˾
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author ###ejet###
 */
public class Formatter
{
    /** null ��ʽ��Ϊ "" */
    public static final Format EMPTY = new FillFormat("");

    /** ȥ���߿ո� */
    public static final Format TRIM = new TrimFormat();

    /** ��ʽ��Ϊ���� */
    public static final Format INT = new NumberFormat(0);

    /** ��ʽ��Ϊ������λС���ĸ����� */
    public static final Format FLOAT2 = new NumberFormat(2);

    /** ����60�󲢸�ʽ��Ϊ��λС���ĸ�����������������ת��ΪСʱ�� */
    public static final Format D60F2 = new NumberFormat(1D/60, 2);

    /** ����100�󲢸�ʽ��Ϊ��λС���ĸ���������������ת��ΪԪ�� */
    public static final Format D100F2 = new NumberFormat(1D/100, 2);

    /** ����1000�󲢸�ʽ��Ϊ��λС���ĸ���������������ת��ΪԪ�� */
    public static final Format D1000F2 = new NumberFormat(1D/1000, 2);

    /** ��ʽ��Ϊû��С���İٷֱȸ�ʽ */
    public static final Format PERCENT = new PercentFormat(0);

    /** ��ʽ��Ϊ������λС����İٷֱȸ�ʽ */
    public static final Format PERCENT2 = new PercentFormat(2);

    /** ����100�󲢸�ʽ��Ϊ������λС����İٷֱȸ�ʽ */
    public static final Format D100P2 = new PercentFormat(1D/100, 2);

    /** ����10000�󲢸�ʽ��Ϊ������λС����İٷֱȸ�ʽ */
    public static final Format D10000P2 = new PercentFormat(1D/10000, 2);

    /** ��ʽ�����ڣ�yyyyMM ת��Ϊ yyyy-MM */
    public static final Format YM_ = new DateFormat("yyyyMM", "yyyy-MM");

    /** ��ʽ�����ڣ�yyyyMMdd ת��Ϊ yyyy-MM-dd */
    public static final Format YMD_ = new DateFormat("yyyyMMdd", "yyyy-MM-dd");

    /** ��ʽ�����ڣ�HHmmss ת��Ϊ HH:mm:ss */
    public static final Format HMS_ = new DateFormat("HHmmss", "HH:mm:ss");

    /** ��ʽ�����ڣ�yyyyMMddHHmmss ת��Ϊ yyyy-MM-dd HH:mm:ss */
    public static final Format YMDHMS_ = new DateFormat("yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss");

    /** ��ʽ�����ڣ�yyyy-MM ת��Ϊ yyyyMM */
    public static final Format YM = new DateFormat("yyyy-MM", "yyyyMM");

    /** ��ʽ�����ڣ�yyyy-MM-dd ת��Ϊ yyyyMMdd */
    public static final Format YMD = new DateFormat("yyyy-MM-dd", "yyyyMMdd");

    /** ��ʽ�����ڣ�HH:mm:ss ת��Ϊ HHmmss */
    public static final Format HMS = new DateFormat("HH:mm:ss", "HHmmss");

    /** ��ʽ�����ڣ�yyyy-MM-dd HH:mm:ss ת��Ϊ yyyyMMddHHmmss */
    public static final Format YMDHMS = new DateFormat("yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss");

    /**
     * ��ʽ������
     * @param d ����
     * @param f ��ʽ���࣬��Ϊnull��
     * @return ��ʽ������ַ���������ʽ����Ϊnull�򷵻��ַ�����ʾ�����֡�
     */
    public static String format(double d, Format f)
    {
        return format(String.valueOf(d), f);
    }

    /**
     * ��ʽ���ַ���
     * @param str ����ʽ�����ַ�������Ϊnull��
     * @param f ��ʽ���࣬��Ϊnull��
     * @return ��ʽ������ַ���������ʽ����Ϊnull�򷵻�ԭ�ַ�����
     */
    public static String format(String str, Format f)
    {
        return f == null ? str : f.format(str);
    }

    /**
     * ��ʽ���ַ������飨�ı���Դ���ݣ�
     * @param strs �ַ������飬��Ϊnull��
     * @param f ��ʽ���࣬��Ϊnull��
     * @return ����ʽ������ַ������飬����ʽ����Ϊnull�򷵻�ԭ�ַ������顣
     */
    public static String[] format(String[] strs, Format f)
    {
        return strs == null ? null : format(strs, 0, strs.length, f);
    }

    /**
     * ���ַ����������ʼλ�ø�ʽ�����ַ������飨�ı���Դ���ݣ�
     * @param strs �ַ������飬��Ϊnull��
     * @param start �ַ����������ʼλ�ã�0��ʼ����������
     * @param f ��ʽ���࣬��Ϊnull��
     * @return ����ʽ������ַ������飬����ʽ����Ϊnull�򷵻�ԭ�ַ������顣
     */
    public static String[] format(String[] strs, int start, Format f)
    {
        return strs == null ? null : format(strs, start, strs.length, f);
    }

    /**
     * ���ַ����������ʼλ��������λ�ø�ʽ�����ַ������飨�ı���Դ���ݣ�
     * @param strs �ַ������飬��Ϊnull��
     * @param start ��ʼλ�ã�0��ʼ��������
     * @param end ����λ�ã���������
     * @param f ��ʽ���࣬��Ϊnull��
     * @return ����ʽ������ַ������飬����ʽ����Ϊnull�򷵻�ԭ�ַ������顣
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
     * ��ʽ���ַ������飨�ı���Դ���ݣ�
     * @param strs �ַ������飬��Ϊnull��
     * @param fs ��ʽ��������
     * @return ����ʽ������ַ�������
     */
    public static String[] format(String[] strs, Format[] fs)
    {
        return strs == null ? null : format(strs, 0, strs.length, fs);
    }

    /**
     * ���ַ����������ʼλ�ø�ʽ�����ַ������飨�ı���Դ���ݣ�
     * @param strs �ַ������飬��Ϊnull��
     * @param start ��ʼλ�ã�0��ʼ��������
     * @param fs ��ʽ��������
     * @return ����ʽ������ַ�������
     */
    public static String[] format(String[] strs, int start, Format[] fs)
    {
        return strs == null ? null : format(strs, start, strs.length, fs);
    }

    /**
     * ���ַ����������ʼλ��������λ�ø�ʽ�����ַ������飨�ı���Դ���ݣ�
     * @param strs �ַ������飬��Ϊnull��
     * @param start ��ʼλ�ã�0��ʼ��������
     * @param end ����λ�ã���������
     * @param fs ��ʽ��������
     * @return ����ʽ������ַ�������
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
     * ��ʽ�������б��ı���Դ���ݣ�
     * @param list List(String[]) ����Ԫ�س��Ȳ������������б�
     * @param f ��ʽ����
     * @return ����ʽ����������б�
     */
    public static List format(List list, Format f)
    {
        return list == null ? null : format(list, 0, f);
    }

    /**
     * ��ʽ�������б��ı���Դ���ݣ�
     * @param list List(String[]) ����Ԫ�س��Ȳ������������б���Ϊnull��
     * @param startCol ��ʼ��
     * @param f ��ʽ����
     * @return ����ʽ����������б�
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
     * ��ʽ�������б��ı���Դ���ݣ�
     * @param list List(String[]) ����Ԫ�س��Ȳ������������б���Ϊnull��
     * @param startCol ��ʼ�У�0��ʼ��������
     * @param endCol ��ֹ�У���������
     * @param f ��ʽ����
     * @return ����ʽ����������б�
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
     * ��ʽ�������б��ı���Դ���ݣ�
     * @param list List(String[]) ����Ԫ�س��Ⱥ͸�ʽ�������鳤��һ�µ������б���Ϊnull��
     * @param fs ��ʽ��������
     * @return ����ʽ����������б�
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
     * ��ʽ������Ƕ�׵�����Map����Ͳ�����������б�List(String[])���ı���Դ���ݣ�
     * @param map ����Ƕ�׵�Map����Ͳ�Ϊ�ַ�������Ԫ�ص��б�List(String[])�������ַ�������Ԫ�ؿɲ��������ò�����Ϊnull��
     * @param f ��ʽ����
     * @return ����ʽ�����Map
     */
    public static Map format(Map map, Format f)
    {
        return map == null ? null : format(map, 0, f);
    }

    /**
     * ��ʽ������Ƕ�׵�����Map����Ͳ�����������б�List(String[])���ı���Դ���ݣ�
     * @param map ����Ƕ�׵�Map����Ͳ�Ϊ�ַ�������Ԫ�ص��б�List(String[])�������ַ�������Ԫ�ؿɲ��������ò�����Ϊnull��
     * @param startCol ��ʽ������ʼ�У�0��ʼ��������
     * @param f ��ʽ����
     * @return ����ʽ�����Map
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
     * ��ʽ������Ƕ�׵�����Map����Ͳ�����������б�List(String[])���ı���Դ���ݣ�
     * @param map ����Ƕ�׵�Map����Ͳ�Ϊ�ַ�������Ԫ�ص��б�List(String[])�������ַ�������Ԫ�ؿɲ��������ò�����Ϊnull��
     * @param startCol ��ʽ������ʼ�У�0��ʼ��������
     * @param endCol ��ʽ���Ľ����У���������
     * @param f ��ʽ����
     * @return ����ʽ�����Map
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
     * ��ʽ������Ƕ�׵�����Map����Ͳ�����������б�List(String[])���ı���Դ���ݣ�
     * @param map ����Ƕ�׵�Map����Ͳ�Ϊ�ַ�������Ԫ�ص��б�List(String[])�������ַ�������Ԫ�ؿɲ��������ò�����Ϊnull��
     * @param fs ��ʽ��������
     * @return ����ʽ�����Map
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
