package com.ejet.util.format; 
/**
 * ȥ�ַ������߿ո�ĸ�ʽ����
 * Company��������###EJET###���޹�˾
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author ###ejet###
 */
public class TrimFormat implements Format
{
    /**
     * ���ַ������ߵĿո�ȥ��
     * @param str ����ʽ�����ַ���
     * @return ��ʽ������ַ���
     */
    public String format(String str)
    {
        return str == null ? str : str.trim();
    }
}
