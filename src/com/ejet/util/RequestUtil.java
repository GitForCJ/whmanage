package com.ejet.util;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletRequest工具类
 * Company：深圳市###EJET###有限公司
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author ###ejet###
 */
public class RequestUtil
{
    /**
     * 获得表单的所有参数，并以Map的形式返回。
     * @param request http请求对象
     * @return Map(String, String[]) 参数名称对应的值组
     */
    public static Map getParamMap(HttpServletRequest request)
    {
        Map map = new LinkedHashMap();

        Enumeration names = request.getParameterNames();
        while (names.hasMoreElements())
        {
            String name = (String) names.nextElement();
            String[] values = request.getParameterValues(name);
            map.put(name, values);
        }

        return map;
    }
}
