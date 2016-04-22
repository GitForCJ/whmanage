package com.wlt.webm.business.action;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ejet.util.DataUtil;
import com.ejet.util.Excel;
import com.wlt.webm.business.bean.CheckAccountErrorLogBean;
import com.wlt.webm.business.bean.SysUserInterface;
import com.wlt.webm.business.form.CheckAccountErrorLogForm;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.util.PageAttribute;


/**
 * 对账日志管理<br>
 */
public class CheckAccountErrorLogAction extends DispatchAction
{
	
	
	/**
	 * 对账日志列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CheckAccountErrorLogForm checkAcountErrorLogForm=(CheckAccountErrorLogForm)form;
		CheckAccountErrorLogBean checkAccountErrorLogBean= new CheckAccountErrorLogBean();
		PageAttribute page = new PageAttribute(checkAcountErrorLogForm.getCurPage(),Constant.PAGE_SIZE);
		String[] totalFee = checkAccountErrorLogBean.checkAccountErrorLogCount("wht_accountErrorLog",checkAcountErrorLogForm);
		page.setRsCount(Integer.parseInt(totalFee[0]));
		SysUserInterface sit = new SysUserInterface();
		List list = checkAccountErrorLogBean.listCheckAccountErrorLog("wht_accountErrorLog",checkAcountErrorLogForm,page);
		request.setAttribute("totalFee", totalFee);
		request.setAttribute("itypeSel",getStingSel(sit.listInterfaceType(),"接口商")); 
		request.setAttribute("checkAccountErrorLog",checkAcountErrorLogForm); 
		request.setAttribute("page",page); 
		request.setAttribute("checkAccountErrorLogList",list); 
		return new ActionForward("/task/checkAccountErrorLogList.jsp");
	}
	public static String getStingSel(List list,String fix){
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("请选择"+fix+"[]");
		  for(Object tmp : list){
			String[] temp = (String[])tmp;
			sBuffer.append("|"+temp[1]+"["+temp[0]+"]");
		  }
		return sBuffer.toString();
	}
	/**
	 * 导出对账日志明细
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward excelExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CheckAccountErrorLogForm checkAcountErrorLogForm=(CheckAccountErrorLogForm)form;
		CheckAccountErrorLogBean checkAccountErrorLogBean= new CheckAccountErrorLogBean();
		PageAttribute page = new PageAttribute(checkAcountErrorLogForm.getCurPage(),Constant.PAGE_SIZE);
		String[] totalFee = checkAccountErrorLogBean.checkAccountErrorLogCount("wht_accountErrorLog",checkAcountErrorLogForm);
		page.setRsCount(Integer.parseInt(totalFee[0]));
		List list = checkAccountErrorLogBean.listCheckAccountErrorLog_EXCEL("wht_accountErrorLog",checkAcountErrorLogForm,page);
		//导出excel
		String[][] colTitles = new String[][] { {"编号","接口商", "账目日期", "任务执行日期", "对账结果"} };
		int size = colTitles[0].length ;
		List  body = new ArrayList();
		//只取其中某些字段
		int  count=0;
		int sum1=0;
		for(Object tmp : list){
			String[] temp = (String[])tmp;
			String[] recode = new String[size];
			//取其中某些字段
			int i  = 0;
			recode[i++] = ++count+"";
			recode[i++] = temp[0];
			recode[i++] = temp[1];
			recode[i++] = temp[2];
			recode[i++] = temp[3];
			body.add(recode);
		}
		Map rsMap = null;
		//转换成excel格式数据
		if(body.size()>0){
			rsMap = DataUtil.toNestedStringsListMap(1, body);
		}
		
		Excel excel = new Excel();
	    excel.setCols(colTitles[0].length);
	    excel.createCaption("对账日志明细报表");
	    excel.createColCaption(colTitles);
	    if(rsMap!=null){
	        excel.createBody(rsMap);
	    }
       // excel.createRemarks("交易明细");
	    String excelFileName = URLEncoder.encode("对账日志明细报表" + ".xls", "UTF-8");
        response.addHeader("Content-Disposition", "attachment; filename=" + excelFileName);
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
            checkAccountErrorLogBean=null;
            checkAcountErrorLogForm= null;
        }
		return mapping.findForward(null);
	}
	public ActionForward changeCheckAccountErrorLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		CheckAccountErrorLogForm checkAcountErrorLogForm=(CheckAccountErrorLogForm)form;
		CheckAccountErrorLogBean checkAccountErrorLogBean= new CheckAccountErrorLogBean();
		checkAccountErrorLogBean.changeCheckAccountErrorLog(checkAcountErrorLogForm);//执行重对账
		PageAttribute page = new PageAttribute(checkAcountErrorLogForm.getCurPage(),Constant.PAGE_SIZE);
		String[] totalFee = checkAccountErrorLogBean.checkAccountErrorLogCount("wht_accountErrorLog",checkAcountErrorLogForm);
		page.setRsCount(Integer.parseInt(totalFee[0]));
		SysUserInterface sit = new SysUserInterface();
		List list = checkAccountErrorLogBean.listCheckAccountErrorLog("wht_accountErrorLog",checkAcountErrorLogForm,page);
		request.setAttribute("totalFee", totalFee);
		request.setAttribute("itypeSel",getStingSel(sit.listInterfaceType(),"接口商")); 
		request.setAttribute("checkAccountErrorLog",checkAcountErrorLogForm); 
		request.setAttribute("page",page); 
		request.setAttribute("checkAccountErrorLogList",list); 
		return new ActionForward("/task/checkAccountErrorLogList.jsp");
	}
	
}