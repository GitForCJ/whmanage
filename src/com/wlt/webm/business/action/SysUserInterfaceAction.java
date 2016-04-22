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
 * �û���ֵ�ӿڹ���<br>
 */
public class SysUserInterfaceAction extends DispatchAction
{
	
	/**
	 * ����û���ֵ�ӿ�
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
		request.setAttribute("mess", "��ӳɹ�");
		return new ActionForward("/business/wltuserinterfacelist.jsp?uid="+srForm.getUser_id());
	}
	
	
	/**
	 * ɾ���û���ֵ�ӿ�
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
     * �����û���ֵ�ӿ�
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