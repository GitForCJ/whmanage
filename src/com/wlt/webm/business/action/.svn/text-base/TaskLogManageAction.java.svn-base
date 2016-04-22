package com.wlt.webm.business.action;

import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.wlt.webm.business.bean.AcctBillBean;
import com.wlt.webm.business.bean.TaskLog;

import com.wlt.webm.business.bean.ReportBean;
import com.wlt.webm.business.form.CityReportForm;
import com.wlt.webm.business.form.TaskForm;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.util.PageAttribute;

public class TaskLogManageAction extends DispatchAction {

	/**
	 * 显示后台任务执行日志
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward listTasklog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TaskForm taskForm = (TaskForm) form;
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute("userSession");
		PageAttribute page = new PageAttribute(taskForm.getPageIndex(),Constant.PAGE_SIZE);
		TaskLog tasklog = new TaskLog();
		List tasklogList = tasklog.listTaskLog(taskForm);
		request.setAttribute("tasklogList", tasklogList);
		request.setAttribute("pagination", tasklog.getPage());
		return new ActionForward("/ejet/wlt_tasklist.jsp");
	}
	/**
	 * 任务重新执行
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward reExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TaskForm taskForm = (TaskForm) form;
		TaskLog tasklog = new TaskLog();
		tasklog.reExecute(taskForm);
		List tasklogList = tasklog.listTaskLog(taskForm);
		request.setAttribute("tasklogList", tasklogList);
		request.setAttribute("pagination", tasklog.getPage());
		request.setAttribute("mess", "任务重执行设置成功！");
		return new ActionForward("/ejet/wlt_tasklist.jsp");
	}

}
