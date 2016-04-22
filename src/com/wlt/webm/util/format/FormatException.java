package com.wlt.webm.util.format;

/**
 * ��ʽ���쳣��<br>
 */
public class FormatException extends RuntimeException
{
    /**
     * ����һ��������ϸ��Ϣ�ĸ�ʽ���쳣
     * @param message ��ϸ��Ϣ
     */
    public FormatException(String message)
    {
        super(message);
    }

    /**
     * ����һ��������ϸ��Ϣ���쳣ԭ�����ϸ��Ϣ�����쳣ԭ��ĸ�ʽ���쳣
     * @param cause �쳣ԭ��
     */
    public FormatException(Throwable cause)
    {
        super(cause);
    }

    /**
     * ����һ��������ϸ��Ϣ���쳣ԭ��ĸ�ʽ���쳣
     * @param message ��ϸ��Ϣ
     * @param cause �쳣ԭ��
     */
    public FormatException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
