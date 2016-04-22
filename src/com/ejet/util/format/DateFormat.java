package com.ejet.util.format;

import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 * ���ڸ�ʽ����
 * Company��������###EJET###���޹�˾
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author ###ejet###
 */
public class DateFormat implements Format
{
    /**
     * �������ڵĶ���
     */
    private SimpleDateFormat dateParse;

    /**
     * ��ʽ�����ڵĶ���
     */
    private SimpleDateFormat dateFormat;

    /**
     * �Զ�������͸�ʽ�����ڵĹ��췽��
     * @param parsePattern �Զ���������ڵ��ַ���ģʽ
     * @param formatPattern �Զ����ʽ�����ڵ��ַ���ģʽ
     */
    public DateFormat(String parsePattern, String formatPattern)
    {
        this.dateParse = new SimpleDateFormat(parsePattern);
        this.dateFormat = new SimpleDateFormat(formatPattern);
    }

    /**
     * �����յ�����ʱ���ַ���ת��ֱ�۵������ַ�����
     * @param str ����ʽ���Ľ��������ַ���
     * @return ��ʽ������ַ������޷���ʽ�����ַ�����ֱ�ӷ��ء�
     */
    public String format(String str)
    {
        if (str == null)
        {
            return null;
        }

        try
        {
            return dateFormat.format(dateParse.parse(str));
        }
        catch (ParseException e)
        {
            return str;
        }
    }
}
