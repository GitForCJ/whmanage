package com.wlt.webm.xunjie.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.InnerProcessDeal;
import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.business.form.SysPhoneAreaForm;
import com.wlt.webm.business.form.SysUserInterfaceForm;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.xunjie.bean.XjDeal;

public class XjAction  extends DispatchAction{
	
	
    /**
     * 湖北接口充值
     * @param mapping
     * @param form
     * @param request接收计费消息处理线程错误
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward xjCharge(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String payNo=request.getParameter("tradeObject").replaceAll(" ", "");
        String paraMoney = request.getParameter("money");
        String xjtype = request.getParameter("xjtype");
        String inId=null;
        if("0".equals(xjtype)){
        	inId="1001";
        }else if("1".equals(xjtype)){
        	inId="1002";
        }else{
        	inId="1003";
        }
        String mon = Tools.yuanToFen(paraMoney);
        MobileChargeService service = new MobileChargeService();
        String sepNo=Tools.getSeqNo(payNo);
        Log.info("湖北接口充值号码------------:"+payNo+"金额:"+paraMoney+"类型"+xjtype+"流水号"+sepNo.substring(6, 17)+"---"+inId);
        String  sqlno="8"+sepNo.substring(6, 17);
        int payFee=Integer.parseInt(mon);
        String nowTime=Tools.getNow();
        
        
        SysUserForm user = (SysUserForm) request.getSession().getAttribute("userSession");
        //用户区域
        String userArea = service.getUserArea(user.getUser_id());
        //号码类型和归属id是否存在
        SysPhoneAreaForm spa = service.getPhoneInfo(payNo.substring(0,7));
//        if(null == spa.getProvince_code() || "".equals(spa.getProvince_code()) || null == spa.getPhone_type() || "".equals(spa.getPhone_type())){
//        	request.setAttribute("mess", "无此号码类型");
//        	if("1001".equals(inId)){
//        		return new ActionForward("/business/xjydbusiness.jsp");
//        	}else if("1002".equals(inId)){
//        		return new ActionForward("/business/xjltbusiness.jsp");
//        	}else{
//        		return new ActionForward("/business/xjdxbusiness.jsp");
//        	}
//        }
        //用户接口id是否存在
//        SysUserInterfaceForm sui = new SysUserInterfaceForm();
//        sui.setProvince_code(spa.getProvince_code());
//        sui.setCharge_type(spa.getPhone_type());
//        sui.setUser_id(Integer.parseInt(user.getUser_id()));
//        String inId = service.getInterfaceId(sui);
//        if(null == inId || "".equals(inId)){
//        	request.setAttribute("mess", "无对应充值接口");
//        	return new ActionForward("/business/cmccbusiness.jsp");
//        }
        //用户的佣金组是否存在
        String roleType = service.getUserRoleType(user.getUser_role());
        if(null == roleType || "".equals(roleType)){
        	request.setAttribute("mess", "未知用户类型");
        	if("1001".equals(inId)){
        		return new ActionForward("/business/xjydbusiness.jsp");
        	}else if("1002".equals(inId)){
        		return new ActionForward("/business/xjltbusiness.jsp");
        	}else{
        		return new ActionForward("/business/xjdxbusiness.jsp");
        	}
        }
        String sgId = "";

		if (null == spa.getProvince_code() || "".equals(spa.getProvince_code())
				|| null == spa.getPhone_type()
				|| "".equals(spa.getPhone_type())) {
			sgId="51";
			SysUserInterfaceForm sui = new SysUserInterfaceForm();
			spa.setProvince_code(8888);
			spa.setPhone_type(0);
			sui.setUser_id(Integer.parseInt(user.getUser_id()));
		}else{
			sgId = service.getUserCommission(user.getUser_id());
		}
        
        
       // String sgId = service.getUserCommission(user.getUser_id());
        if(null == sgId || "".equals(sgId)){
        	request.setAttribute("mess", "无对应佣金组");
        	if("1001".equals(inId)){
        		return new ActionForward("/business/xjydbusiness.jsp");
        	}else if("1002".equals(inId)){
        		return new ActionForward("/business/xjltbusiness.jsp");
        	}else{
        		return new ActionForward("/business/xjdxbusiness.jsp");
        	}
        }
        //佣金子账号和押金账号是否存在
        String fundAcct02 = service.getUserFundAcct("02", user.getUser_id());
        if(null == fundAcct02 || "".equals(fundAcct02)){
        	request.setAttribute("mess", "押金账号不存在");
        	if("1001".equals(inId)){
        		return new ActionForward("/business/xjydbusiness.jsp");
        	}else if("1002".equals(inId)){
        		return new ActionForward("/business/xjltbusiness.jsp");
        	}else{
        		return new ActionForward("/business/xjdxbusiness.jsp");
        	}
        }
        String fundAcct03 = service.getUserFundAcct("03", user.getUser_id());
        if(null == fundAcct03 || "".equals(fundAcct03)){
        	request.setAttribute("mess", "佣金子账号不存在");
        	if("1001".equals(inId)){
        		return new ActionForward("/business/xjydbusiness.jsp");
        	}else if("1002".equals(inId)){
        		return new ActionForward("/business/xjltbusiness.jsp");
        	}else{
        		return new ActionForward("/business/xjdxbusiness.jsp");
        	}
        }
        //佣金子账号和押金账号状态是否正常
        String state = service.getFundAcctState(fundAcct02);
        if(!"1".equals(state)){
        	request.setAttribute("mess", "押金账号不可用");
        	if("1001".equals(inId)){
        		return new ActionForward("/business/xjydbusiness.jsp");
        	}else if("1002".equals(inId)){
        		return new ActionForward("/business/xjltbusiness.jsp");
        	}else{
        		return new ActionForward("/business/xjdxbusiness.jsp");
        	}
        }
        String state1 = service.getFundAcctState(fundAcct03);
        if(!"1".equals(state1)){
        	request.setAttribute("mess", "佣金子账号不可用");
        	if("1001".equals(inId)){
        		return new ActionForward("/business/xjydbusiness.jsp");
        	}else if("1002".equals(inId)){
        		return new ActionForward("/business/xjltbusiness.jsp");
        	}else{
        		return new ActionForward("/business/xjdxbusiness.jsp");
        	}
        }
        //押金子账号的金额是否大于扣费金额
        int acctLeft = Integer.parseInt(service.getFundAcctLeft(fundAcct02));
        if(0 == acctLeft || acctLeft - Integer.parseInt(mon) < 0){
        	request.setAttribute("mess", "押金账号余额不足");
        	if("1001".equals(inId)){
        		return new ActionForward("/business/xjydbusiness.jsp");
        	}else if("1002".equals(inId)){
        		return new ActionForward("/business/xjltbusiness.jsp");
        	}else{
        		return new ActionForward("/business/xjdxbusiness.jsp");
        	}
        }
        //佣金明细是否存在
        String scId = service.getCommMx(sgId, String.valueOf(spa.getPhone_type()),Integer.parseInt(userArea),spa.getProvince_code(),mon);
        if(null == scId || "".equals(scId)){
        	request.setAttribute("mess", "佣金明细不存在");
        	if("1001".equals(inId)){
        		return new ActionForward("/business/xjydbusiness.jsp");
        	}else if("1002".equals(inId)){
        		return new ActionForward("/business/xjltbusiness.jsp");
        	}else{
        		return new ActionForward("/business/xjdxbusiness.jsp");
        	}
        }
        //记录订单
        int isSuccess = 0;
        try {
        	isSuccess = InnerProcessDeal.logOrder(String.valueOf(spa.getProvince_code()), sepNo, payNo, inId, "P0001", Integer.parseInt(mon), fundAcct02.substring(0, fundAcct02.length()-2),
        			nowTime, nowTime, sqlno, sqlno, "0", user.getUser_id(), acctLeft - Integer.parseInt(mon), user.getUserename(), String.valueOf(spa.getPhone_type()));
		} catch (Exception e) {
			request.setAttribute("mess", "生成订单失败");
			if("1001".equals(inId)){
        		return new ActionForward("/business/xjydbusiness.jsp");
        	}else if("1002".equals(inId)){
        		return new ActionForward("/business/xjltbusiness.jsp");
        	}else{
        		return new ActionForward("/business/xjdxbusiness.jsp");
        	}
		}
		if(0 != isSuccess){
			request.setAttribute("mess", "生成订单失败");
			if("1001".equals(inId)){
        		return new ActionForward("/business/xjydbusiness.jsp");
        	}else if("1002".equals(inId)){
        		return new ActionForward("/business/xjltbusiness.jsp");
        	}else{
        		return new ActionForward("/business/xjdxbusiness.jsp");
        	}
		}
		Map dealMap = new HashMap();
        if(0 == isSuccess){
        	try {
        		//内部 扣款、返佣、更改订单状态 
        		dealMap = InnerProcessDeal.indeal(fundAcct02.substring(0, fundAcct02.length()-2), payNo, nowTime, Integer.parseInt(mon), String.valueOf(spa.getProvince_code()), service.getTradeType(inId), "", 
        				spa.getPhone_type(), Integer.parseInt(userArea), Integer.parseInt(user.getUser_id()), Integer.parseInt(user.getUser_role()), spa.getProvince_code(), sepNo, sgId);
			} catch (Exception e) {
				request.setAttribute("mess", "数据处理错误");
				if("1001".equals(inId)){
	        		return new ActionForward("/business/xjydbusiness.jsp");
	        	}else if("1002".equals(inId)){
	        		return new ActionForward("/business/xjltbusiness.jsp");
	        	}else{
	        		return new ActionForward("/business/xjdxbusiness.jsp");
	        	}
			}
        }
        int dealFlag = (Integer)dealMap.get("flag");
        if(1 == dealFlag){
        	request.setAttribute("mess", "数据处理错误");
        	if("1001".equals(inId)){
        		return new ActionForward("/business/xjydbusiness.jsp");
        	}else if("1002".equals(inId)){
        		return new ActionForward("/business/xjltbusiness.jsp");
        	}else{
        		return new ActionForward("/business/xjdxbusiness.jsp");
        	}
        }
        Log.info("湖北充值"+payNo+"-"+paraMoney+"-"+sqlno);
        int mainKey=2;
        		try {
        			XjDeal deal=new XjDeal();
        			mainKey=deal.xjFill(sqlno,xjtype,payNo,paraMoney);
        			Log.info("湖北充值响应"+payNo+"---"+sqlno+"---"+mainKey);
        			//mainKey=2;
				} catch (Exception e) {
					//更新订单状态
					service.updOrderState("wlt_orderForm_"+nowTime.substring(2, 6), sepNo);
					//返款，更新佣金、账户明细--
					if(null != dealMap){
						//自身返佣金额
						String empFee = String.valueOf(dealMap.get("empFee-self"));
						//账户明细表名
						String tableName = "wlt_acctbill_"+nowTime.substring(2, 6);
						//上一级账户
						String empAcctLevlOne = String.valueOf(dealMap.get("acct-levelone"));
						String empFeeLevlOne = String.valueOf(dealMap.get("empfee-levelone"));
						//上上一级账户
//						String empAcctLevlTwo = (String)dealMap.get("acct-leveltwo");
//						String empFeeLevlTwo = (String)dealMap.get("empfee-leveltwo");
						//自身返款,退回自身佣金并更新账户明细
						service.updAccount(payFee, fundAcct02.substring(0, fundAcct02.length()-2), empFee, tableName,sepNo);
						//上级返回佣金并更新账户明细
						if(null != empAcctLevlOne && !"".equals(empAcctLevlOne)){
							service.updEmpAccount(empAcctLevlOne, empFeeLevlOne, tableName,sepNo);
						}
//						//上上级返回佣金并更新账户明细
//						if(null != empAcctLevlTwo && !"".equals(empAcctLevlTwo)){
//							service.updEmpAccount(empAcctLevlTwo, empFeeLevlTwo, tableName,sepNo);
//						}
					}
					request.setAttribute("mess", "充值接口异常");
					if("1001".equals(inId)){
		        		return new ActionForward("/business/xjydbusiness.jsp");
		        	}else if("1002".equals(inId)){
		        		return new ActionForward("/business/xjltbusiness.jsp");
		        	}else{
		        		return new ActionForward("/business/xjdxbusiness.jsp");
		        	}
					
				}
        		if(mainKey==0){//充值成功,修改订单状态成功
        			service.updOrderStatus(sepNo, "0", "wlt_orderForm_"+nowTime.substring(2, 6));
        		}else if(mainKey==2){//充值失败,修改订单状态失败，退钱，退佣金
        			//更新订单状态
        			service.updOrderState("wlt_orderForm_"+nowTime.substring(2, 6), sepNo);
        			//返款，更新佣金、账户明细--
        			// Log.info("湖北移动充值--------1111111---------------------------"+sepNo+"---"+mainKey);
        			if(null != dealMap){
        				// Log.info("湖北移动充值-------22222222----------------------------"+sepNo+"---"+mainKey);
        				//自身返佣金额
        				String empFee = String.valueOf(dealMap.get("empFee-self"));
        				//账户明细表名
        				String tableName = "wlt_acctbill_"+nowTime.substring(2, 6);
        				//上一级账户
        				String empAcctLevlOne = String.valueOf(dealMap.get("acct-levelone"));
        				String empFeeLevlOne = String.valueOf(dealMap.get("empfee-levelone"));
        				//上上一级账户
//        				String empAcctLevlTwo = (String)dealMap.get("acct-leveltwo");
//        				String empFeeLevlTwo = (String)dealMap.get("empfee-leveltwo");
        				//自身返款,退回自身佣金并更新账户明细
        				// Log.info("湖北移动充值------333-----------"+payFee+"---"+fundAcct02.substring(0, fundAcct02.length()-2)+"---"+empFee+"---");
        				service.updAccount(payFee, fundAcct02.substring(0, fundAcct02.length()-2), empFee, tableName,sepNo);
        				//上级返回佣金并更新账户明细
        				if(null != empAcctLevlOne && !"".equals(empAcctLevlOne)){
        					service.updEmpAccount(empAcctLevlOne, empFeeLevlOne, tableName,sepNo);
        				}
//        				//上上级返回佣金并更新账户明细
//        				if(null != empAcctLevlTwo && !"".equals(empAcctLevlTwo)){
//        					service.updEmpAccount(empAcctLevlTwo, empFeeLevlTwo, tableName,sepNo);
//        				}
        			}
        			request.setAttribute("mess", "充值失败");
        			if("1001".equals(inId)){
                		return new ActionForward("/business/xjydbusiness.jsp");
                	}else if("1002".equals(inId)){
                		return new ActionForward("/business/xjltbusiness.jsp");
                	}else{
                		return new ActionForward("/business/xjdxbusiness.jsp");
                	}
        		}else if(mainKey==3){//无响应,修改订单状态无响应
        			service.updOrderStatus(sepNo, "2", "wlt_orderForm_"+nowTime.substring(2, 6));
        			request.setAttribute("mess", "充值无响应");
        			if("1001".equals(inId)){
                		return new ActionForward("/business/xjydbusiness.jsp");
                	}else if("1002".equals(inId)){
                		return new ActionForward("/business/xjltbusiness.jsp");
                	}else{
                		return new ActionForward("/business/xjdxbusiness.jsp");
                	}
        		}else if(mainKey==1){
        			service.updOrderStatus(sepNo, "6", "wlt_orderForm_"+nowTime.substring(2, 6));
        			request.setAttribute("mess", "充值异常");
        			if("1001".equals(inId)){
                		return new ActionForward("/business/xjydbusiness.jsp");
                	}else if("1002".equals(inId)){
                		return new ActionForward("/business/xjltbusiness.jsp");
                	}else{
                		return new ActionForward("/business/xjdxbusiness.jsp");
                	}
        		}
                request.setAttribute("mess", "充值成功");
                if("1001".equals(inId)){
            		return new ActionForward("/business/xjydbusiness.jsp");
            	}else if("1002".equals(inId)){
            		return new ActionForward("/business/xjltbusiness.jsp");
            	}else{
            		return new ActionForward("/business/xjdxbusiness.jsp");
            	}
                
	}
	

}
