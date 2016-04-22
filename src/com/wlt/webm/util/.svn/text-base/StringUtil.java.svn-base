package com.wlt.webm.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.List;

public class StringUtil
{
    /**
     * 四舍五入小数点后两位数字的格式化数字类。
     */
    private static final DecimalFormat moneyFormat = new DecimalFormat("#################0.00");

    /**
     * 格式化数字，四舍五入小数点后两位数字。例如：0.43789格式化为"0.44"
     * @param d 数字
     * @return String 格式化后的字符串
     */
    public static String format(double d)
    {
        return moneyFormat.format(d);
    }
    
    /**
     * 格式化数字，四舍五入小数点后两位数字。例如：0.43789格式化为"0.44"
     * @param str 数字
     * @return String 格式化后的字符串
     */
    public static String format(String str)
    {
        return format(parseDouble(str));
    }
    /**
     * 将字符串编码由 GBK 转换为 ISO-8859-1。
     * @param str 要转换的字符串
     * @return String 由 GBK 转换为 ISO-8859-1 的字符串
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
     * 解析字符串为双精度浮点数，如果字符串为null则解析为0。
     * @param str 要解析的字符串
     * @return 由字符串解析的浮点数
     */
    public static double parseDouble(String str)
    {
        return str == null ? 0 : Double.parseDouble(str);
    }
    /**
     * 将字符串编码由 ISO-8859-1 转换为 GBK。
     * @param str 要转换的字符串
     * @return String 由 ISO-8859-1 转换为 GBK 的字符串
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
     * 将字符中的空格去掉,重新组合字符串
     * @param str   待处理的字符
     * @return   返回没出有空格的字符串
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
     * 把一个String数组用","连接成一个字符串。<br>
     * 例如传入new String[] { "aaa", "bbb", "ccc" }，则返回"aaa,bbb,ccc"。
     * @param strings String数组
     * @return 连接后的字符串
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
     * 把一个List中存放的String数组用","连接成一个字符串。<br>
     * 例如传入new ArrayList().add(new String[] { "aaa", "bbb", "ccc" }...)，则返回"aaa,bbb,ccc"。
     * @param list List(String[])
     * @return 连接后的字符串
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
     * 重复字符规定的次数
     * @param str 要重复的字符串
     * @param repeat 重复的次数，必须大于0，为0时返回""。
     * @return 返回重复后的字符串
     */
    public static String repeat(String str, int repeat)
    {
        if (str == null)
            throw new NullPointerException("重复的字符串不能为null。");

        if (repeat < 0)
            throw new IllegalArgumentException("重复的次数(" + repeat + ")小于底限0。");

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
