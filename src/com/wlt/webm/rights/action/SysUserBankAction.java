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
 * �û����п�����<br>
 */
public class SysUserBankAction extends DispatchAction
{
	/**
     * ���û����п�
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
        		test.setCardNo(userBankForm.getUser_bankcard());//��������
        		test.setCreditNo(userBankForm.getUser_icard());//���֤��
        		test.setCreditNoType(userBankForm.getUser_icard_type());//���֤���
        		test.setName(userBankForm.getUser_name());//�û�����
        		if(test.send()){
        			userBank.addUserBank(userBankForm);
        		}else {
        			request.setAttribute("mess", "��ʧ�ܣ��������Ϊ��"+test.getRecode());
        			return mapping.findForward("success");
        		}
        	}else {
        		userBank.updateUserBank(userBankForm);
        	}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("mess", "��ʧ��");
			return mapping.findForward("success");
		}
        request.setAttribute("mess", "�󶨳ɹ�");
        return mapping.findForward("success");
    }
}