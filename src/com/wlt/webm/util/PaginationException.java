package com.wlt.webm.util;

public class PaginationException extends RuntimeException
{
    /**
     * ���췽��
     * @param message ������ʾ��Ϣ
     */
    public PaginationException(String message)
    {
        super(message);
    }
}
