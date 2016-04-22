package com.wlt.webm.rights.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.wlt.webm.rights.bean.SysRole;
import com.wlt.webm.rights.form.SysRoleForm;


/**
 * 角色管理<br>
 */
public class SysRoleAction extends DispatchAction
{
	
	/**
	 * 添加角色
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
		SysRoleForm roleForm = (SysRoleForm) form;
		SysRole sa = new SysRole();
		int result = sa.add(roleForm);
		if(1 == result){
			request.setAttribute("mess", "角色名称已存在");
			return new ActionForward("/rights/wltroleadd.jsp");
		}
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
	public ActionForward del(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysRoleForm roleForm = (SysRoleForm) form;
		SysRole sa = new SysRole();
		sa.del(roleForm);
		return mapping.findForward("success");
	}
	/**
	 * 角色修改跳转
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward updateView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysRoleForm roleForm = (SysRoleForm) form;
		return new ActionForward("/rights/wltroleupdate.jsp?rid="+roleForm.getIds()[0]);
	}
	/**
	 * 角色修改
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
		SysRoleForm roleForm = (SysRoleForm) form;
		SysRole sa = new SysRole();
		sa.update(roleForm);
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
	public ActionForward setModuleRights(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysRoleForm roleForm = (SysRoleForm) form;
		SysRole sa = new SysRole();
		sa.modifyMenu(roleForm);
		return mapping.findForward("success");
	}
	/**
	 * 修改操作权限
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward setModuleRightsFunc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysRoleForm roleForm = (SysRoleForm) form;
		SysRole sa = new SysRole();
		sa.modifyMenuFunc(roleForm);
		return mapping.findForward("success");
	}
}