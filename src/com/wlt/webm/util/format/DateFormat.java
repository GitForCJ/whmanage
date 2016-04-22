package com.wlt.webm.util.format;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * ���ڸ�ʽ����<br>
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
