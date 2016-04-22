package com.wlt.webm.business.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.business.bean.SysInvoke;
import com.wlt.webm.tool.Tools;


/**
 * 捷呗回调<br>
 */
public class JieBeiChargeReturnAction extends DispatchAction
{
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter pWriter = response.getWriter();
		MobileChargeService service = new MobileChargeService();
		String userid = request.getParameter("userid");
		String requestid = request.getParameter("requestid");
		String resultno = request.getParameter("resultno");
		String vstr = request.getParameter("vstr");
		String nowTime=Tools.getNow();
		if("2".equals(resultno) || "02".equals(resultno)){
			service.updOrderStatus(requestid, "0", "wlt_orderForm_"+nowTime.substring(2, 6));
		}else if("3".equals(resultno) || "03".equals(resultno)||"20".equals(resultno)){
			service.updOrderStatus(requestid, "1", "wlt_orderForm_"+nowTime.substring(2, 6));
		}else{
			service.updOrderStatus(requestid, "6", "wlt_orderForm_"+nowTime.substring(2, 6));
		}
		if(null != resultno && !"".equals(resultno)){
			pWriter.print("ok");
			pWriter.flush();
			pWriter.close();
		}
		//更新回调输入
		SysInvoke invoke = new SysInvoke();
		invoke.update(resultno,requestid,nowTime);
		return null;
	}
}