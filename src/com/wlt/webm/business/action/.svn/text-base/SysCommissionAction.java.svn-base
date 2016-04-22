package com.wlt.webm.business.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.wlt.webm.business.MessageUtil;
import com.wlt.webm.business.NetPayUtil;
import com.wlt.webm.business.bean.SysCommission;
import com.wlt.webm.business.form.SysCommissionForm;
import com.wlt.webm.rights.bean.SysRole;
import com.wlt.webm.rights.bean.SysUser;
import com.wlt.webm.rights.form.SysRoleForm;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.util.PageAttribute;


/**
 * 佣金管理<br>
 */
public class SysCommissionAction extends DispatchAction
{
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward validateName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			String name = request.getParameter("name");
			SysCommission sc = new SysCommission();
			List list = sc.listSc(name);
			if(null != list && list.size() > 0){//名称已经存在
				printWriter.print(0);
				printWriter.flush();
				printWriter.close();
			}else {
				printWriter.print(1);
				printWriter.flush();
				printWriter.close();
			}
		} catch (Exception e) {
			printWriter.print(1);
			printWriter.flush();
			printWriter.close();
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 增加信息
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
		SysCommissionForm scForm = (SysCommissionForm) form;
		SysCommission sc= new SysCommission();
		sc.add(scForm);
		return mapping.findForward("success"+scForm.getSc_type());
	}
	
	/**
	 * 增加明细
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward addmxa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysCommissionForm scForm = (SysCommissionForm) form;
		SysCommission sc= new SysCommission();
		sc.addmxa(scForm);
		request.setAttribute("sg_id", scForm.getSg_id());
		return mapping.findForward("successcheckmx");
	}
	
	/**
	 * 增加明细
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward addjkmx(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysCommissionForm scForm = (SysCommissionForm) form;
		SysCommission sc= new SysCommission();
		sc.addmxa(scForm);
		request.setAttribute("sg_id", scForm.getSg_id());
		return mapping.findForward("successcheckjkmx");
	}
	
	/**
	 * 增加明细
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward addtcmx(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysCommissionForm scForm = (SysCommissionForm) form;
		SysCommission sc= new SysCommission();
		sc.addtcmx(scForm);
		request.setAttribute("sg_id", scForm.getSg_id());
		return mapping.findForward("successchecktcmx");
	}
	
	/**
	 * 增加明细
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward addsmx(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysCommissionForm scForm = (SysCommissionForm) form;
		SysCommission sc= new SysCommission();
		sc.addsmx(scForm);
		request.setAttribute("sg_id", scForm.getSg_id());
		return mapping.findForward("successchecktcmx");
	}
	/**
	 * 增加明细
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward addjtmx(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysCommissionForm scForm = (SysCommissionForm) form;
		SysCommission sc= new SysCommission();
		sc.addjtmx(scForm);
		request.setAttribute("sg_id", scForm.getSg_id());
		return mapping.findForward("successcheckjtmx");
	}
	
	/**
	 * 修改信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysCommissionForm scForm = (SysCommissionForm) form;
		SysCommission sc= new SysCommission();
		sc.modify(scForm);
		return mapping.findForward("success"+scForm.getSc_type());
	}
	
	/**
	 * 跳转到区域添加界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward del(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysCommissionForm scForm = (SysCommissionForm) form;
		SysCommission sc = new SysCommission();
		sc.del(scForm);
		return mapping.findForward("success");
	}
	
	/**
	 * 跳转到区域添加界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward delmx(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysCommissionForm scForm = (SysCommissionForm) form;
		SysCommission sc = new SysCommission();
		sc.delmx(scForm);
		return mapping.findForward("successcheckmx");
	}
	/**
	 * 跳转到区域添加界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward deljtmx(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysCommissionForm scForm = (SysCommissionForm) form;
		SysCommission sc = new SysCommission();
		sc.delmx(scForm);
		return mapping.findForward("successcheckjtmx");
	}
	
	/**
	 * 跳转到区域添加界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward setModuleRights(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysRoleForm roleForm = (SysRoleForm) form;
		SysRole sa = new SysRole();
		sa.modifyMenu(roleForm);
		return mapping.findForward("success");
	}
	

}