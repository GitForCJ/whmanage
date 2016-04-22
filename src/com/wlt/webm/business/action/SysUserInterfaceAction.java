package com.wlt.webm.business.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.wlt.webm.business.bean.SysUserInterface;
import com.wlt.webm.business.form.SysUserInterfaceForm;


/**
 * 用户充值接口管理<br>
 */
public class SysUserInterfaceAction extends DispatchAction
{
	
	/**
	 * 添加用户充值接口
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
		SysUserInterfaceForm srForm = (SysUserInterfaceForm) form;
		SysUserInterface sit= new SysUserInterface();
		sit.add(srForm);
		request.setAttribute("mess", "添加成功");
		return new ActionForward("/business/wltuserinterfacelist.jsp?uid="+srForm.getUser_id());
	}
	
	
	/**
	 * 删除用户充值接口
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
		SysUserInterfaceForm srForm = (SysUserInterfaceForm) form;
		SysUserInterface sit= new SysUserInterface();
		int uid = sit.del(srForm);
		return new ActionForward("/business/wltuserinterfacelist.jsp?uid="+uid);
	}
	 /**
     * 更新用户充值接口
     * @param mapping 
     * @param form 
     * @param request 
     * @param response 
     * @return ActionForward
     * @throws Exception 
     */
    public ActionForward update(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
    	SysUserInterfaceForm srForm = (SysUserInterfaceForm) form;
		SysUserInterface sit= new SysUserInterface();
        sit.update(srForm);
        return new ActionForward("/business/wltuserinterfacelist.jsp?uid="+srForm.getUser_id());
    }
}