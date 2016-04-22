package com.ejet.common.struts.action;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ejet.common.struts.bean.Count;
import com.ejet.common.struts.bean.CountUtil;
import com.ejet.common.struts.form.CountForm;
import com.ejet.util.DataUtil;
import com.ejet.util.RequestUtil;
import com.wlt.webm.rights.form.SysUserForm;

/**
 * 报表统计的公用Action。<hr>
 * 要求：用户表单中必须有beanName属性，该属性内容必须是实现了<code>Count</code>接口的报表统计类的全名称。<br>
 * Company：深圳市###EJET###有限公司
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author ###ejet###
 */
public class CountAction extends Action
{
    /**
     * 执行报表统计的方法
     * @param mapping 客户端请求对应相关处理的映射
     * @param form 报表统计条件的表单
     * @param request 客户端请求对象
     * @param response 服务端响应对象
     * @return 页面跳转对象
     * @throws Exception 
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        HttpSession session = request.getSession();
        SysUserForm user = (SysUserForm) session.getAttribute("userSession");//用户信息
        CountForm fm = (CountForm) form;//表单

        Count count = (Count) Class.forName(fm.getBeanName()).newInstance();
        count.setCountForm(fm);
        count.setUserForm(user);
        count.init();

        String message = count.getAlertMessage();//提示信息
        request.setAttribute("message", message);
        if (message == null)
        {
            request.setAttribute("title", count.getTitle());//报表标题
            String[][] colTitles = (String[][]) count.getColTitles();//列头标题
            request.setAttribute("colTitles", colTitles);
            request.setAttribute("colTitlesSpans", CountUtil.getSpans(colTitles));//列头标题的合并行列数据
            Object bodyData = count.getBodyData();//报表数据
            int nestedCount = DataUtil.getNestedCount(bodyData);//报表数据的合并列数
            request.setAttribute("bodyData", bodyData);
            request.setAttribute("nestedCount", new Integer(nestedCount));
            if (nestedCount == 2)//两列合并报表中的第一列的合并行数
            {
                int[] rowSpans = CountUtil.getRowSpanOfTwoMap((Map) bodyData);
                request.setAttribute("bodyDataRowSpans", rowSpans);
            }
            request.setAttribute("pagination", count.getPagination());//分页信息
            request.setAttribute("remarks", count.getRemarks());//备注
    
            request.setAttribute("params", RequestUtil.getParamMap(request));//表单参数

            return mapping.findForward("count");
        }
        return mapping.findForward("counterror");
    }
}
