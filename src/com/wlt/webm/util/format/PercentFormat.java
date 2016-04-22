package com.wlt.webm.util.format;

/**
 * 百分比格式化类<br>
 */
public class PercentFormat implements Format
{
    /**
     * 浮点数格式化类
     */
    private NumberFormat numberFormat;

    /**
     * 构造一个保留固定小数位的百分比格式化类
     * @param radixPoint 保留的小数位
     */
    public PercentFormat(int radixPoint)
    {
        numberFormat = new NumberFormat(100, radixPoint);
    }

    /**
     * 构造一个指定因子并保留固定小数位的百分比格式化类
     * @param divisor 因子
     * @param radixPoint 保留的小数位
     */
    public PercentFormat(double divisor, int radixPoint)
    {
        numberFormat = new NumberFormat(100 * divisor, radixPoint); 
    }

    /**
     * 格式化为带有两位小数的百分格式
     * @param str 被格式化的字符串
     * @return 格式化后的百分比字符串
     */
    public String format(String str)
    {
        return numberFormat.format(str) + "%";
    }
}
