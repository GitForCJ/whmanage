package com.wlt.webm.business.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.business.bean.SysInvoke;
import com.wlt.webm.scputil.DateParser;
import com.wlt.webm.tool.Tools;


/**
 * 殴飞回调<br>
 */
public class OuFeiChargeReturnAction extends DispatchAction
{
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nowTime=Tools.getNow();
		System.out.println("回调开始----------------------------------"+nowTime);
		MobileChargeService service = new MobileChargeService();
		String ret_code = request.getParameter("ret_code");
		String sporder_id = request.getParameter("sporder_id");
		String ordersuccesstime = request.getParameter("ordersuccesstime");
		String err_msg = request.getParameter("err_msg");
		if(null != err_msg && !"".equals(err_msg)){
			System.out.println("失败原因-----"+err_msg);
		}
		//判断订单状态是否是在处理
		String state = service.getOrderStatus(sporder_id, "wlt_orderForm_"+nowTime.substring(2, 6));
		if(!"4".equals(state)){
			return null;
		}
		if("1".equals(ret_code)){
			service.updOrderStatus(sporder_id, "0", "wlt_orderForm_"+nowTime.substring(2, 6));
		}else if("9".equals(ret_code)){
			service.updOrderStatus(sporder_id, "1", "wlt_orderForm_"+nowTime.substring(2, 6));
		}
		//更新回调输入
		SysInvoke invoke = new SysInvoke();
		invoke.update(ret_code, sporder_id,nowTime);
		return null;
	}
}