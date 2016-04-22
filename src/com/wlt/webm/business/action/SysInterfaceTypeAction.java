package com.wlt.webm.business.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.wlt.webm.business.bean.SysInterfaceType;
import com.wlt.webm.business.form.SysInterfaceTypeForm;


/**
 * 充值接口管理<br>
 */
public class SysInterfaceTypeAction extends DispatchAction
{
	
	/**
	 * 添加充值接口
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
		SysInterfaceTypeForm srForm = (SysInterfaceTypeForm) form;
		SysInterfaceType sit= new SysInterfaceType();
		sit.add(srForm);
		return mapping.findForward("success");
	}
	
	
	/**
	 * 删除充值接口
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
		SysInterfaceTypeForm srForm = (SysInterfaceTypeForm) form;
		SysInterfaceType sit = new SysInterfaceType();
		sit.del(srForm);
		return mapping.findForward("success");
	}
	 /**
     * 更新充值接口
     * @param mapping 
     * @param form 
     * @param request 
     * @param response 
     * @return ActionForward
     * @throws Exception 
     */
    public ActionForward update(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
    	SysInterfaceTypeForm sForm = (SysInterfaceTypeForm) form;
        SysInterfaceType sit =new SysInterfaceType();
        sit.update(sForm);
        return mapping.findForward("success");
    }
}