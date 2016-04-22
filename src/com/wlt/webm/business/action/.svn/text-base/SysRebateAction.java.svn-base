package com.wlt.webm.business.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.wlt.webm.business.bean.SysCommission;
import com.wlt.webm.business.bean.SysRebate;
import com.wlt.webm.business.form.SysCommissionForm;
import com.wlt.webm.business.form.SysRebateForm;
import com.wlt.webm.rights.bean.SysRole;
import com.wlt.webm.rights.bean.SysUser;
import com.wlt.webm.rights.form.SysRoleForm;
import com.wlt.webm.rights.form.SysUserForm;


/**
 * 用户管理<br>
 */
public class SysRebateAction extends DispatchAction
{
	
	/**
	 * 修改区域信息
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
		SysRebateForm srForm = (SysRebateForm) form;
		SysRebate sr= new SysRebate();
		sr.add(srForm);
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
		SysRebateForm srForm = (SysRebateForm) form;
		SysRebate sr = new SysRebate();
		sr.del(srForm);
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
     * 更新折扣率信息
     * @param mapping 
     * @param form 
     * @param request 
     * @param response 
     * @return ActionForward
     * @throws Exception 
     */
    public ActionForward update(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
    	SysRebateForm sForm = (SysRebateForm) form;
        SysRebate sRebate =new SysRebate();
        sRebate.update(sForm);
        return mapping.findForward("success");
    }
}