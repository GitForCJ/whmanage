package com.wlt.webm.pccommon.struts.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wlt.webm.pccommon.struts.bean.CountUtil;
import com.wlt.webm.pccommon.struts.bean.Counter;
import com.wlt.webm.pccommon.struts.form.CountForm;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.util.DataUtil;
import com.wlt.webm.util.RequestUtil;

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

        Counter counter = (Counter) Class.forName(fm.getBeanName()).newInstance();
        counter.setCountForm(fm);
//        counter.setUserForm(user);
        counter.init();

        String message = counter.getAlertMessage();//提示信息
        request.setAttribute("message", message);
        if (message == null)
        {
            request.setAttribute("title", counter.getTitle());//报表标题
            String[][] colTitles = (String[][]) counter.getColTitles();//列头标题
            request.setAttribute("colTitles", colTitles);
            request.setAttribute("colTitlesSpans", CountUtil.getSpans(colTitles));//列头标题的合并行列数据
            
            Object bodyData = counter.getBodyData();//报表数据
            int nestedCount = DataUtil.getNestedCount(bodyData);//报表数据的合并列数
            request.setAttribute("bodyData", bodyData);
            request.setAttribute("nestedCount", new Integer(nestedCount));
            if (nestedCount == 2)//两列合并报表中的第一列的合并行数
            {
                int[] rowSpans = CountUtil.getRowSpanOfTwoMap((Map) bodyData);
                request.setAttribute("bodyDataRowSpans", rowSpans);
            }
            request.setAttribute("pagination", counter.getPagination());//分页信息
            request.setAttribute("remarks", counter.getRemarks());//备注
            request.setAttribute("params", RequestUtil.getParamMap(request));//表单参数
            return mapping.findForward("count");
        }
        return mapping.findForward("counterror");
    }
}
