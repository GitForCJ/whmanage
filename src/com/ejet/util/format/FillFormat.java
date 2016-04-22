package com.ejet.util.format;

/**
 * �� null �� "" ��ʽ��Ϊָ�����ַ���
 * Company��������###EJET###���޹�˾
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author ###ejet###
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
