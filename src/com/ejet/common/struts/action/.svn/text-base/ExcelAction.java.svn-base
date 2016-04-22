package com.ejet.common.struts.action;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ejet.common.struts.bean.Count;
import com.ejet.common.struts.form.CountForm;
import com.ejet.util.Excel;
import com.wlt.webm.rights.form.SysUserForm;

/**
 * 导出EXCEL的公用Action。<hr>
 * 要求：用户表单中必须有beanName属性，该属性内容必须是实现了<code>Count</code>接口的报表统计类的全名称。<br>
 * Company：深圳市###EJET###有限公司
 * Copyright: Copyright (c) 2009
 * @author ###ejet###
 * 修改人：胡俊
 * 修改日期：2009-9-28
 * @version V2.1.2.0
 */
public class ExcelAction extends Action
{
    /**
     * 执行导出EXCEL的方法
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
        //设置当前页数为最大值，则导出所有数据
         fm.setPageIndex(Integer.MAX_VALUE);
        Count count = (Count) Class.forName(fm.getBeanName()).newInstance();
        count.setCountForm(fm);
        count.setUserForm(user); 
        count.init();

        String title = count.getTitle();//报表标题
        String[][] colTitles = (String[][]) count.getColTitles();//列头标题
        count.setExcelState("1");
        Object bodyData = count.getBodyData();//报表数据
        String[] remarks = count.getRemarks();//备注

        Excel excel = new Excel();
        excel.setCols(colTitles[0].length);
        excel.createCaption(title);
        excel.createColCaption(colTitles);
        if(bodyData!=null){
        	excel.createBody(bodyData);
        }
        
        if (remarks != null)
        {
            excel.createRemarks(remarks);
        }
        String excelFileName = URLEncoder.encode(title + ".xls", "UTF-8");
        response.addHeader("Content-Disposition", "attachment; filename=" + excelFileName);
//        response.setContentType("application/vnd.ms-excel");
//        response.reset();
        OutputStream out = null;
        try
        {
            out = response.getOutputStream();
            excel.createFile(out);
        }
        finally
        {
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch(IOException e)
                {
                }
            }
        }

        return mapping.findForward(null);
    }

}
