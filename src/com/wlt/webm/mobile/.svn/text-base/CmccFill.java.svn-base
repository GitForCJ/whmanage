package com.wlt.webm.mobile;

import java.util.HashMap;
import java.util.Map;


import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.InnerProcessDeal;
import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.business.cmcc.CMPayPayBusiness;
import com.wlt.webm.business.dianx.bean.TelcomPayBean;
import com.wlt.webm.business.dianx.form.TelcomForm;
import com.wlt.webm.business.form.SysPhoneAreaForm;
import com.wlt.webm.business.form.SysUserInterfaceForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.pccommon.UniqueNo;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.tool.Constant;
import com.wlt.webm.tool.Tools;

	/**
	 * 手机移动
	 * @author 
	 *
	 */
public class CmccFill {
	
	   
	   /**
	    * app 移动充值服务 com.wlt.webm.mobile.CmccFill.telMobile(,,)
	    * @param phonePayForm TelcomForm类型 其中：下列字段必须有
	    *                     tradeObject  电话号码
	    *                     seqNo   交易流水号（26位唯一）
	    *                     payFee  交易金额 （元）
	    *                     
	    *                                 
	    * @param user  session中获取的SysUserForm
	    * 
	    * @return  1;无对应充值接口   2;未知用户类型  3;无对应佣金组 4;押金账号不存在
	    *          5;佣金子账号不存在  6:押金账号不可用   7;佣金子账号不可用   8;押金账号余额不足
	    *          9;佣金明细不存在  10;生成订单失败  11;数据处理错误  12;充值异常,请联系客服
	    *          0:充值成功  -1;//充值失败 -2;//充值无响应
	    * @throws Exception
	    */
	   public static int telMobile(TelcomForm phonePayForm, SysUserForm user) throws Exception{
			String payNo=phonePayForm.getTradeObject().trim();
	        String paraMoney = phonePayForm.getPayFee();
	        String mon=Tools.yuanToFen(paraMoney);
	        System.out.println("moblie移动号码:"+payNo+"   金额:"+mon);
	        String sepNo=phonePayForm.getSeqNo();
	        MobileChargeService service = new MobileChargeService();
	        String serialNo = service.getNoSix_bank();
	        String sqlno="HR"+Constant.CMPaySUPhone.trim()+serialNo.trim();
	        phonePayForm.setSeqNo(sepNo);
	        int payFee=Integer.parseInt(mon);
	        String nowTime=Tools.getNow();
	        //用户区域
	        String userArea = service.getUserArea(user.getUser_id());
	        //号码类型和归属id是否存在
	        SysPhoneAreaForm spa = service.getPhoneInfo(payNo.substring(0,7));
	        spa.setProvince_code(new Integer(35));
	        spa.setPhone_type(1);
	        //用户接口id是否存在
	        SysUserInterfaceForm sui = new SysUserInterfaceForm();
	        sui.setProvince_code(spa.getProvince_code());
	        sui.setCharge_type(spa.getPhone_type());
	        sui.setUser_id(Integer.parseInt(user.getUser_id()));
	        //用户的佣金组是否存在
	        String roleType = service.getUserRoleType(user.getUser_role());
	        if(null == roleType || "".equals(roleType)){
	           return 2;//未知用户类型
	        }
	        String sgId = service.getUserCommission(user.getUser_id());
	        if(null == sgId || "".equals(sgId)){
	        	return 3;//无对应佣金组
	        }
	        //佣金子账号和押金账号是否存在
	        String fundAcct02 = service.getUserFundAcct("02", user.getUser_id());
	        if(null == fundAcct02 || "".equals(fundAcct02)){
	           return 4;//押金账号不存在
	        }
	        String fundAcct03 = service.getUserFundAcct("03", user.getUser_id());
	        if(null == fundAcct03 || "".equals(fundAcct03)){
	        	    return 5;//佣金子账号不存在
	        }
	        //佣金子账号和押金账号状态是否正常
	        String state = service.getFundAcctState(fundAcct02);
	        if(!"1".equals(state)){
	        	return 6;//押金账号不可用
	        }
	        String state1 = service.getFundAcctState(fundAcct03);
	        if(!"1".equals(state1)){
                 return 7;//佣金子账号不可用
	        }
	        //押金子账号的金额是否大于扣费金额
	        int acctLeft = Integer.parseInt(service.getFundAcctLeft(fundAcct02));
	        if(0 == acctLeft || acctLeft - Integer.parseInt(mon) < 0){
	        return 8;//押金账号余额不足
	        }
	        //佣金明细是否存在
	        String scId = service.getCommMx(sgId, "1",Integer.parseInt(userArea),spa.getProvince_code(),mon);
	        if(null == scId || "".equals(scId)){
	        	    return 9;//佣金明细不存在
	        }
	        //记录订单
	        int isSuccess = 0;
	        try {
	        	isSuccess = InnerProcessDeal.logOrder(String.valueOf(spa.getProvince_code()), sepNo, payNo, "0008","P0001", Integer.parseInt(mon), fundAcct02.substring(0, fundAcct02.length()-2),
	        			nowTime, nowTime, sqlno, "", "0", user.getUser_id(), acctLeft - Integer.parseInt(mon), user.getUsername(), String.valueOf(spa.getPhone_type()));
			} catch (Exception e) {
				   return 10;//生成订单失败
			}
			if(0 != isSuccess){
				return 10;//生成订单失败
			}
			Map dealMap = new HashMap();
	        if(0 == isSuccess){
	        	try {
	        		//内部 扣款、返佣、更改订单状态 
	        		dealMap = InnerProcessDeal.indeal(fundAcct02.substring(0, fundAcct02.length()-2), payNo, nowTime, Integer.parseInt(mon), String.valueOf(spa.getProvince_code()), service.getTradeType("0008"), "", 
	        				spa.getPhone_type(), Integer.parseInt(userArea), Integer.parseInt(user.getUser_id()), Integer.parseInt(user.getUser_role()), spa.getProvince_code(), sepNo, sgId);
				} catch (Exception e) {
					return 11;//数据处理错误
				}
	        }
	        int dealFlag = (Integer)dealMap.get("flag");
	        if(1 == dealFlag){
	        	return 11;//数据处理错误
	        }
	        //调用充值接口
	        try {
	        	CMPayPayBusiness business=new CMPayPayBusiness(payNo,mon,sqlno);
        		business.deal();
	        } catch (Exception e) {
	        	service.updOrderStatus(sepNo, "6", "wlt_orderForm_"+nowTime.substring(2, 6));
				return 12;//充值异常,请联系客服
				
			}
    		String mainKey=null;
    		int times=0;
			while(times < 30){
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					  Log.info("广东移动充值结果读取出错"+e.toString());
					e.printStackTrace();
				}
				 mainKey = service.selectState("cmccpay"+sqlno);
				 Log.info("广东移动充值结果------------------"+mainKey);
				if(null==mainKey){
					times++;
				}else{
					service.deleteState("cmccpay"+sqlno);
					break;
				}
			}
			if(times==30){
				mainKey="3";
			}
			if("0".equals(mainKey)){//充值成功,修改订单状态成功
				service.updOrderStatus(sepNo, "0", "wlt_orderForm_"+nowTime.substring(2, 6));
		        return 0;//充值成功
			}else if("2".equals(mainKey)){//充值失败,修改订单状态失败，退钱，退佣金
				//更新订单状态
				service.updOrderState("wlt_orderForm_"+nowTime.substring(2, 6), sepNo);
				//返款，更新佣金、账户明细
				if(null != dealMap){
					//自身返佣金额
					String empFee = String.valueOf(dealMap.get("empFee-self"));
					//账户明细表名
					String tableName = "wlt_acctbill_"+nowTime.substring(2, 6);
					//上一级账户
					String empAcctLevlOne = (String)dealMap.get("acct-levelone");
					String empFeeLevlOne = (String)dealMap.get("empfee-levelone");
					//上上一级账户
//					String empAcctLevlTwo = (String)dealMap.get("acct-leveltwo");
//					String empFeeLevlTwo = (String)dealMap.get("empfee-leveltwo");
					//自身返款,退回自身佣金并更新账户明细
					service.updAccount(payFee, fundAcct02.substring(0, fundAcct02.length()-2), empFee, tableName,sepNo);
					//上级返回佣金并更新账户明细
					if(null != empAcctLevlOne && !"".equals(empAcctLevlOne)){
						service.updEmpAccount(empAcctLevlOne, empFeeLevlOne, tableName,sepNo);
					}
				}
				return -1;//充值失败
			}
		else {//无响应,修改订单状态无响应
				service.updOrderStatus(sepNo, "2", "wlt_orderForm_"+nowTime.substring(2, 6));
				return -2;//充值无响应
			}

		}
	   

}
