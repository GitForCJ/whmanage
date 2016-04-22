package com.wlt.webm.util.format;

/**
 * �� null �� "" ��ʽ��Ϊָ�����ַ���<br>
 */
public class FillFormat implements Format
{
    /**
     * ������Ŀ���ַ���
     */
    private String fillString;

    /**
     * ���췽��
     * @param fillString ������Ŀ���ַ���
     */
    public FillFormat(String fillString)
    {
        this.fillString = fillString;
    }

    /**
     * �� null �� "" ��ʽ��Ϊָ�����ַ��������򷵻�ԭ�ַ�����
     * @param str ����ʽ�����ַ���
     * @return ��ʽ������ַ���
     */
    public String format(String str)
    {
        return str == null || str.length() == 0 ? fillString : str;
    }
}
