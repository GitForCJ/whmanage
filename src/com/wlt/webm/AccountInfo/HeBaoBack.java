package com.wlt.webm.AccountInfo;

import java.io.PrintWriter;
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
import com.wlt.webm.business.bean.OrderBean;


public class HeBaoBack extends DispatchAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		
		Log.info("和包订单结果回调,,,参数:"+params);
		
		String requestId=request.getParameter("requestId");
		String status=request.getParameter("status");
		if(requestId==null || "".equals(requestId.trim()) || status==null || "".equals(status.trim())){
			return null;
		}
		
		try {
			
			PrintWriter out = response.getWriter();
			out.write("SUCCESS");

			int st = "SUCCESS".equals(status) ? 0 : -1;
			OrderBean.httpReturnDeal(st, requestId, 30);

			out.flush();
			out.close();
		} catch (Exception e) {
			Log.error("和包订单结果回调,,订单号:"+requestId+",,,系统异常,,ex:"+e);
		}
		return null;
	}
}
