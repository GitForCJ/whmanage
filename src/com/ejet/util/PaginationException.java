package com.ejet.util;

/**
 * 分页异常类
 * Company：深圳市###EJET###有限公司
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author ###ejet###
 */
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
