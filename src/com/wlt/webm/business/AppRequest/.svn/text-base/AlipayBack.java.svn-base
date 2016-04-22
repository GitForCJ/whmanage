package com.wlt.webm.business.AppRequest;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commsoft.epay.util.logging.Log;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.wlt.webm.business.AppRequest.alipay.AlipayNotify;

public class AlipayBack extends DispatchAction {

	/**
	 * 支付宝回调
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			//订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
			Log.info("接收到支付宝通知信息,,订单号:"+out_trade_no);
			
			// 获取支付宝POST过来反馈信息
			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				params.put(name,valueStr);
			}
			
			Log.info("接收到支付宝通知信息,,订单号:"+out_trade_no+",,回调内容:"+params.toString());
			
			if (AlipayNotify.verify(params)) {// 验证成功
				// 交易状态
				String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
				
				Log.info("支付宝回调通知，，RSA校验成功，，，订单号:"+out_trade_no+",,订单状态:"+trade_status);
				
				if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
					
					PrintWriter out = response.getWriter();
					
					Log.info("支付宝回调通知，，RSA校验成功，，，订单号:"+out_trade_no+",,支付成功，，进入充值业务方法");
					BaiduBack.services_oper(out_trade_no);
					
					out.println("success"); // 请不要修改或删除
					out.flush();
					out.close();
					Log.info("支付宝回调通知，，RSA校验成功，，，订单号:"+out_trade_no+",,响应支付宝seccess,完成");
				}
			}else{
				Log.info("支付宝回调通知，，RSA校验失败，，，订单号:"+out_trade_no);
			}
		} catch (Exception e) {
			Log.info("支付宝回调通知，系统异常,,ex:"+e);
		}
		return null;
	}
}
