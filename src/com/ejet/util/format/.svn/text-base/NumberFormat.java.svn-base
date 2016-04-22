package com.ejet.util.format;

import java.text.DecimalFormat;

/**
 * 浮点数格式化类。<br>
 * 1、可格式化为任意位数的小数位（包括整数）；<br>
 * 2、提供简单单位转换功能，例：元转换为分，分钟转换为小时……<br>
 * Company：深圳市###EJET###有限公司
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author ###ejet###
 */
public class NumberFormat implements Format
{
    /**
     * 小数位
     */
    private int radixPoint;

    /**
     * 系数
     */
    private double factor;

    /**
     * 格式化参数的整数部分
     */
    private String integerFormatParam;

    /**
     * 格式化参数的小数部分（包含小数点）
     */
    private String decimalFormatParam;

    /** 
     * 格式化类
     */
    private DecimalFormat format;

    /**
     * 构造一个带有指定小数位的浮点格式化类
     * @param radixPoint 保留的小数位
     */
    public NumberFormat(int radixPoint)
    {
        this(1, radixPoint);
    }

    /**
     * 构造一个指定系数并保留指定小数位的浮点格式化类
     * @param factor 系数，例如：将分钟转化为小时可传入1/60；将分转化为元可传入1/100。
     * @param radixPoint 保留的小数位
     */
    public NumberFormat(double factor, int radixPoint)
    {
        if (radixPoint < 0)
        {
            throw new FormatException("格式化小数位(" + radixPoint + ")小于0错误。");
        }

        this.radixPoint = radixPoint;
        this.factor = factor;
        this.integerFormatParam = "#########################0";
        this.decimalFormatParam = getDecimalFormatParam();
        this.format = new DecimalFormat(integerFormatParam + decimalFormatParam);
    }

    /**
     * 把数字字符串格式化为指定的字符串。
     * @param str 被格式化的数字字符串
     * @return 格式化后的字符串，若字符串为null或""则返回"0.00..."(小数点后的0的个数由保留的小数位决定)
     */
    public String format(String str)
    {
        if (str == null || str.length() == 0)
        {
            return "0" + decimalFormatParam;
        }
        else
        {
            return format.format(Double.parseDouble(str) * factor);
        }
    }

    /**
     * 获得格式化参数的小数部分（包含小数点）
     * @return 格式化参数的小数部分
     */
    private String getDecimalFormatParam()
    {
        StringBuffer s = new StringBuffer();
        if (radixPoint > 0)
        {
            s.append(".");
            for (int i = 0; i < radixPoint; i++)
            {
                s.append("0");
            }
        }
        return s.toString();
    }
}
