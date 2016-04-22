package com.wlt.webm.ten.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBService;

/**
 * 财付通转账、Q币充值开关控制<br>
 */
public class CftSwitchAction extends DispatchAction {

	/**
	 * 判断返回结果的Action
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DBService dbService = new DBService();
		String flag =(String) request.getParameter("flag");
		request.setAttribute("flag", flag);
		String sql = "select upswitch from ec_switch where cftswitch="+flag;
		String str = dbService.getString(sql);
		if (null != str) {
			if (str.trim().equals("0")&&flag.equals("1")) {// 开启财付通
				return new ActionForward("/operation/tenpaytransfer.jsp");
			} 
			else if(str.trim().equals("0")&&flag.equals("2"))// 开启新生支付
			{
				return new ActionForward("/operation/xinshengtransfer.jsp");
			}
            else if(str.trim().equals("0")&&flag.equals("3"))// 开启Q币充值
			{
				return new ActionForward("/operation/QBtenpaytransfer.jsp");
			}
            else if(str.trim().equals("0")&&flag.equals("4"))// 开启天作充值
			{
				return new ActionForward("/operation/tzphonebillinfo.jsp");
			}
            else if(str.trim().equals("0")&&flag.equals("5"))// 开启易票联充值
			{
				return new ActionForward("/operation/epaylinksphonebillinfo.jsp");
			}
			else {
				return new ActionForward("/operation/noresult.jsp");
			}
		} else {
			return new ActionForward("/operation/noresult.jsp");
		}
	}

	/**
	 * 查询是否存在某条记录 或更新某条记录
	 * 
	 * @param sql
	 * @param params
	 * @param lg
	 * @return flag
	 */
	public boolean getRecord(String sql, Object[] params, String lg) {
			return true;
	}
}
