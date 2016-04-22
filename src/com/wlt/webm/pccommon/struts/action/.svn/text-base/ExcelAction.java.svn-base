package com.wlt.webm.pccommon.struts.action;

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

import com.wlt.webm.pccommon.struts.bean.Count;
import com.wlt.webm.pccommon.struts.form.CountForm;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.util.Excel;

public class ExcelAction extends Action
{
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        HttpSession session = request.getSession();
        SysUserForm user = (SysUserForm) session.getAttribute("userSession");//用户信息
        CountForm fm = (CountForm) form;//表单
        // 设置当前页数为最大值，则导出所有数据
        fm.setPageIndex(Integer.MAX_VALUE);
        Count count = (Count) Class.forName(fm.getBeanName()).newInstance();
        count.setCountForm(fm);
//        count.setUserForm(user);
        count.init();

        String title = count.getTitle();//报表标题
        String[][] colTitles = (String[][]) count.getColTitles();//列头标题
        Object bodyData = count.getBodyData();//报表数据
        String[] remarks = count.getRemarks();//备注

        Excel excel = new Excel();
        excel.setCols(colTitles[0].length);
        excel.createCaption(title);
        excel.createColCaption(colTitles);
        excel.createBody(bodyData);
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
