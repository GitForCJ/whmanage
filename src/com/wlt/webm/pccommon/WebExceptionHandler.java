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
 * �쳣������<br>
 */
public class WebExceptionHandler extends ExceptionHandler
{
    /**
     * �����쳣
     * @param e �쳣
     * @param config �쳣����
     * @param mapping �ͻ��������Ӧ������
     * @param form �û���
     * @param request �û��������
     * @param response ����������Ӧ����
     * @return ��ת·��
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
