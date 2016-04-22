package com.ejet.util.format;

/**
 * 格式化异常类
 * Company：深圳市###EJET###有限公司
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author ###ejet###
 */
public class FormatException extends RuntimeException
{
    /**
     * 构造一个带有详细信息的格式化异常
     * @param message 详细信息
     */
    public FormatException(String message)
    {
        super(message);
    }

    /**
     * 构造一个带有详细信息（异常原因的详细信息）和异常原因的格式化异常
     * @param cause 异常原因
     */
    public FormatException(Throwable cause)
    {
        super(cause);
    }

    /**
     * 构造一个带有详细信息和异常原因的格式化异常
     * @param message 详细信息
     * @param cause 异常原因
     */
    public FormatException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
