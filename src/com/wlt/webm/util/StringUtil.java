package com.wlt.webm.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.List;

public class StringUtil
{
    /**
     * ��������С�������λ���ֵĸ�ʽ�������ࡣ
     */
    private static final DecimalFormat moneyFormat = new DecimalFormat("#################0.00");

    /**
     * ��ʽ�����֣���������С�������λ���֡����磺0.43789��ʽ��Ϊ"0.44"
     * @param d ����
     * @return String ��ʽ������ַ���
     */
    public static String format(double d)
    {
        return moneyFormat.format(d);
    }
    
    /**
     * ��ʽ�����֣���������С�������λ���֡����磺0.43789��ʽ��Ϊ"0.44"
     * @param str ����
     * @return String ��ʽ������ַ���
     */
    public static String format(String str)
    {
        return format(parseDouble(str));
    }
    /**
     * ���ַ��������� GBK ת��Ϊ ISO-8859-1��
     * @param str Ҫת�����ַ���
     * @return String �� GBK ת��Ϊ ISO-8859-1 ���ַ���
     */
    public static String encode(String str)
    {
        try
        {
            return str == null ? null : new String(str.getBytes("GBK"), "ISO-8859-1");
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * �����ַ���Ϊ˫���ȸ�����������ַ���Ϊnull�����Ϊ0��
     * @param str Ҫ�������ַ���
     * @return ���ַ��������ĸ�����
     */
    public static double parseDouble(String str)
    {
        return str == null ? 0 : Double.parseDouble(str);
    }
    /**
     * ���ַ��������� ISO-8859-1 ת��Ϊ GBK��
     * @param str Ҫת�����ַ���
     * @return String �� ISO-8859-1 ת��Ϊ GBK ���ַ���
     */
    public static String decode(String str)
    {
        try
        {
            return str == null ? null : new String(str.getBytes("ISO-8859-1"), "GBK");
        }
        catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * ���ַ��еĿո�ȥ��,��������ַ���
     * @param str   ��������ַ�
     * @return   ����û���пո���ַ���
     */
    public static String formatBlank(String str) {
        char s = ' ';
        String reStr = "";
        if (str != null && !str.trim().equals("")) {
            for (int i = 0; i < str.trim().length(); i++) {
                if (str.trim().charAt(i) != s) {
                    reStr += str.trim().charAt(i);
                }
            }
        }

        return reStr;
    }

    /**
     * ��һ��String������","���ӳ�һ���ַ�����<br>
     * ���紫��new String[] { "aaa", "bbb", "ccc" }���򷵻�"aaa,bbb,ccc"��
     * @param strings String����
     * @return ���Ӻ���ַ���
     */
    public static String connect(Object[] strings)
    {
        if (strings.length < 1)
        {
            return "";
        }

        StringBuffer str = new StringBuffer();
        str.append(strings[0]);

        for (int i = 1; i < strings.length; i++)
        {
            str.append(",").append(strings[i]);
        }

        return str.toString();
    }
    
    /**
     * ��һ��List�д�ŵ�String������","���ӳ�һ���ַ�����<br>
     * ���紫��new ArrayList().add(new String[] { "aaa", "bbb", "ccc" }...)���򷵻�"aaa,bbb,ccc"��
     * @param list List(String[])
     * @return ���Ӻ���ַ���
     */
    public static String connect(List list)
    {
		StringBuffer str=new StringBuffer();
		
    	if(list!=null&&list.size()>0){
    		for(int i=0;i<list.size();i++){
        		
        		String[] string=(String[])list.get(i);
        		str.append(string[0]).append(",");	
        	
        	}
   
    		return str.toString().substring(0,str.length()-1);
    		
    	}
    	else
    		return "";
    	
    }

    /**
     * �ظ��ַ��涨�Ĵ���
     * @param str Ҫ�ظ����ַ���
     * @param repeat �ظ��Ĵ������������0��Ϊ0ʱ����""��
     * @return �����ظ�����ַ���
     */
    public static String repeat(String str, int repeat)
    {
        if (str == null)
            throw new NullPointerException("�ظ����ַ�������Ϊnull��");

        if (repeat < 0)
            throw new IllegalArgumentException("�ظ��Ĵ���(" + repeat + ")С�ڵ���0��");

        StringBuffer s = new StringBuffer();
        for (int i = 0; i < repeat; i++)
        {
            s.append(str);
        }

        return s.toString();
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
    }
    
}
