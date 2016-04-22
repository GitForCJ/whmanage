package com.wlt.webm.business.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.business.bean.SysInvoke;
import com.wlt.webm.tool.Tools;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * �������ƻص��ӿ�
 */
public class LiandongCallBackAction extends DispatchAction {
	
	/**
	 * �������ƻص�����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return out 
	 */

	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter pWriter = response.getWriter();
		MobileChargeService service = new MobileChargeService();
		String orderId = request.getParameter("orderId");
		String mobile = request.getParameter("mobile");
		String respCode = request.getParameter("respCode");
		String rspDesc = request.getParameter("rspDesc");
		Log.info("�������ƻص������ţ�"+orderId+",,�ֻ��ţ�"+mobile+",,��Ӧ�룺"+respCode+",,��Ӧ������"+rspDesc);
		
		if (null == respCode || "".equals(respCode)){
			return null;
		}
		String nowTime=Tools.getNow();
		// �ɹ�[0]|ʧ��[1]|������[4]|����[5]|�쳣����[6]
		if("0000".equals(respCode)){
			service.updOrderStatus(orderId, "0", "wlt_orderForm_"+nowTime.substring(2, 6));
		}else if("E0020".equals(respCode)){
			service.updOrderStatus(orderId, "1", "wlt_orderForm_"+nowTime.substring(2, 6));
		}else if("0001".equals(respCode)){
			service.updOrderStatus(orderId, "4", "wlt_orderForm_"+nowTime.substring(2, 6));
		}else{
			service.updOrderStatus(orderId, "6", "wlt_orderForm_"+nowTime.substring(2, 6));
		}
		
		pWriter.print("ok");
		pWriter.flush();
		pWriter.close();
		//���»ص�����
		SysInvoke invoke = new SysInvoke();
		invoke.update(respCode,orderId,nowTime);
		return null;
	}

	
}
