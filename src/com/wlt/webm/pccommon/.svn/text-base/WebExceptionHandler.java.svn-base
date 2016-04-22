package com.wlt.webm.pccommon;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

import com.commsoft.epay.util.logging.Log;

/**
 * 异常处理类<br>
 */
public class WebExceptionHandler extends ExceptionHandler
{
    /**
     * 处理异常
     * @param e 异常
     * @param config 异常配置
     * @param mapping 客户端请求对应的配置
     * @param form 用户表单
     * @param request 用户请求对象
     * @param response 服务器端响应对象
     * @return 跳转路径
     * @throws ServletException
     */
    public ActionForward execute(Exception e, ExceptionConfig config, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ServletException
    {
        Log.error(e);
        String message = "Sorry! System looks very busy, please try again later";
        String errtitle=e.getClass().getName()+": "+e.getMessage();
        
        request.setAttribute("message", message);
        request.setAttribute("errtitle", errtitle);
        request.setAttribute("errstacktrace", e.getStackTrace());
        return new ActionForward(config.getPath());
    }
}
