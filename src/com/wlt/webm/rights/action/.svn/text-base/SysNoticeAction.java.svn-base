package com.wlt.webm.rights.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.wlt.webm.rights.bean.SysNotice;
import com.wlt.webm.rights.form.SysNoticeForm;


/**
 * 公告管理<br>
 */
public class SysNoticeAction extends DispatchAction
{

	/**
	 * 添加公告信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysNoticeForm noticeForm = (SysNoticeForm) form;
		noticeForm.setAn_type("00");
		noticeForm.setAn_createtime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		noticeForm.setIf_active("1");
		SysNotice sn = new SysNotice();
		sn.add(noticeForm);
		return new ActionForward("/rights/wltnoticeadd.jsp");
	}
	/**
	 * 更新公告信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysNoticeForm noticeForm = (SysNoticeForm) form;
		noticeForm.setAn_type("00");
		noticeForm.setAn_createtime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		SysNotice sn = new SysNotice();
		sn.update(noticeForm);
		return mapping.findForward("success");
	}
}