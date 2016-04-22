package com.wlt.webm.rights.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.wlt.webm.business.YinLianYanZhen;
import com.wlt.webm.rights.bean.SysUserBank;
import com.wlt.webm.rights.form.SysUserBankForm;

/**
 * 用户银行卡管理<br>
 */
public class SysUserBankAction extends DispatchAction
{
	/**
     * 绑定用户银行卡
     * @param mapping 
     * @param form 
     * @param request 
     * @param response 
     * @return ActionForward
     * @throws Exception 
     */
    public ActionForward bankBound(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysUserBankForm userBankForm = (SysUserBankForm) form;
        SysUserBank userBank = new SysUserBank();
        String isNull = request.getParameter("isNullBank");
        try {
        	if("true".equals(isNull)){
        		YinLianYanZhen test = new YinLianYanZhen();
        		test.setCardNo(userBankForm.getUser_bankcard());//银联卡号
        		test.setCreditNo(userBankForm.getUser_icard());//身份证号
        		test.setCreditNoType(userBankForm.getUser_icard_type());//身份证类别
        		test.setName(userBankForm.getUser_name());//用户姓名
        		if(test.send()){
        			userBank.addUserBank(userBankForm);
        		}else {
        			request.setAttribute("mess", "绑定失败，错误代码为："+test.getRecode());
        			return mapping.findForward("success");
        		}
        	}else {
        		userBank.updateUserBank(userBankForm);
        	}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("mess", "绑定失败");
			return mapping.findForward("success");
		}
        request.setAttribute("mess", "绑定成功");
        return mapping.findForward("success");
    }
}