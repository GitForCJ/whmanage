package com.wlt.webm.util;

public class PaginationException extends RuntimeException
{
    /**
     * 构造方法
     * @param message 错误提示信息
     */
    public PaginationException(String message)
    {
        super(message);
    }
}
