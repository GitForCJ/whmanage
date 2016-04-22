package com.wlt.webm.rights.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.business.form.BiProdForm;
import com.wlt.webm.rights.bean.SysArea;
import com.wlt.webm.rights.form.SysAreaForm;


/**
 * 用户管理<br>
 */
public class SysAreaAction extends DispatchAction
{

	/**
	 * 跳转到区域添加界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward showAddTPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysAreaForm areaForm = (SysAreaForm) form;
		SysArea sa = new SysArea();
		String areacode = areaForm.getAreacope();
		String result = "";
		if(areacode.equals("0")){
			result ="0";
		}else{
			result = sa.getPareacode(areacode);
		}
		request.setAttribute("areacope", result);
		return new ActionForward("/rights/wltareaadd.jsp");
	}
	
	/**
	 * 跳转到区域修改界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward toModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysAreaForm areaForm = (SysAreaForm) form;
		String result = areaForm.getAreacope();
		request.setAttribute("areacope", result);
		return new ActionForward("/rights/wltareamodify.jsp");
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
	public ActionForward showAddUPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysAreaForm areaForm = (SysAreaForm) form;
		String result  = areaForm.getAreacope();
		request.setAttribute("areacope", result);
		return new ActionForward("/rights/wltareaadd.jsp");
	}
	
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
		SysAreaForm areaForm = (SysAreaForm) form;
		String pareacode  = areaForm.getPareacope();
		String areazone  = areaForm.getAreacode();
		String areaname  = areaForm.getAreaname();
		String flag  = areaForm.getFlag();
		SysArea sa = new SysArea();
		sa.add(pareacode,areaname,areazone,flag);
		return mapping.findForward("success");
	}
	
	/**
	 * 修改区域信息
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
		SysAreaForm areaForm = (SysAreaForm) form;
		String areacope  = areaForm.getAreacope();
		String areazone  = areaForm.getAreacode();
		String areaname  = areaForm.getAreaname();
		SysArea sa = new SysArea();
		sa.modifycode(areacope,areaname,areazone);
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
	public ActionForward showModify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysAreaForm areaForm = (SysAreaForm) form;
		String areacope  = areaForm.getAreacope();
		String areacode  = areaForm.getAreacope();
		String areazone  = areaForm.getAreacope();
		SysArea sa = new SysArea();
		sa.modifycode(areacope,areacode,areazone);
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
		SysAreaForm areaForm = (SysAreaForm) form;
		String areacope  = areaForm.getAreacope();
		SysArea sa = new SysArea();
		sa.delcode(areacope);
		return mapping.findForward("success");
	}
	/**
     * 是否虚拟（AJAX）
     * @param mapping 
     * @param form 
     * @param request 
     * @param response 
     * @return ActionForward
     * @throws Exception 
     */
    public ActionForward getAreaFlag(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
    	PrintWriter pWriter = response.getWriter();
    	SysAreaForm areaForm = (SysAreaForm) form;
    	String areacope  = areaForm.getAreacope();
    	SysArea sa = new SysArea();
        String flag = sa.getAreaFlag(areacope);
        if(null == flag || "".equals(flag) || "1".equals(flag)){
        	pWriter.print("1");
        	pWriter.flush();
        	pWriter.close();
        }else {
        	pWriter.print("0");
        	pWriter.flush();
        	pWriter.close();
		}
        return null;
    }
}