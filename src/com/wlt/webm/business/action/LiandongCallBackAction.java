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
 * 联动优势回调接口
 */
public class LiandongCallBackAction extends DispatchAction {
	
	/**
	 * 联动优势回调方法
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
		Log.info("联动优势回调订单号："+orderId+",,手机号："+mobile+",,响应码："+respCode+",,响应描述："+rspDesc);
		
		if (null == respCode || "".equals(respCode)){
			return null;
		}
		String nowTime=Tools.getNow();
		// 成功[0]|失败[1]|处理中[4]|冲正[5]|异常订单[6]
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
		//更新回调输入
		SysInvoke invoke = new SysInvoke();
		invoke.update(respCode,orderId,nowTime);
		return null;
	}

	
}
