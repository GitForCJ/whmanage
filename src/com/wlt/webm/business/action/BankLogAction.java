package com.wlt.webm.business.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.wlt.webm.business.bean.OrderBean;
import com.wlt.webm.business.bean.SysBankLog;
import com.wlt.webm.business.bean.SysUserInterface;
import com.wlt.webm.business.form.BankLogForm;
import com.wlt.webm.business.form.JtfkForm;
import com.wlt.webm.business.form.OrderForm;
import com.wlt.webm.rights.bean.SysRole;
import com.wlt.webm.scputil.DateParser;


/**
 * 银联日志管理<br>
 */
public class BankLogAction extends DispatchAction
{
	
	/**
	 * 银联日志列表
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
		BankLogForm bankForm = (BankLogForm) form;
		SysBankLog bankLog = new SysBankLog();
		List list = bankLog.list(bankForm);
		request.setAttribute("logList",list); 
		return mapping.findForward("loglist");
	}
	/**
	 * 返销
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward orderCancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = (String)request.getParameter("id");
		SysBankLog bankLog = new SysBankLog();
		List list = bankLog.list(bankForm);
		request.setAttribute("logList",list); 
		return mapping.findForward("loglist");
	}
}