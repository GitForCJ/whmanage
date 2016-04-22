package com.wlt.webm.business.kaimi.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.wlt.webm.scpdb.DBLog;
import com.wlt.webm.scpdb.DBService;

public class KMCallBack extends DispatchAction {
	
	
	public ActionForward kmFill(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	     String  OrderID=request.getParameter("OrderID");
	     /**DOING 
	     SUCCESS 
	     PART_SUCCESS  
	     FAIL
	     */
	     String  Status=request.getParameter("Status");   
	     String  Success_Value=request.getParameter("Success_Value");    
	     String  Stock_price=request.getParameter("Stock_price");   
	     String  Sign=request.getParameter("Sign");  
		
		//记录订单
       DBService db = new DBService();
       DBLog dbLog = new DBLog(db);
//       dbLog.insertOrderForm(String areaCode, String termAcct, String sepNo,
//			String payNo, int payFee, String remark, String accNbr,
//			String nowTime, String tradeNo,int state);
        //扣押金账户的钱  给自己返佣金   给上级节点返佣金 并记录各项日志

  return null;
	}
}
