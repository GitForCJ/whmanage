package com.ejet.phone.dao;

import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mpi.client.data.OrderData;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wlt.webm.business.InnerProcessDeal;
import com.wlt.webm.business.JieBeiChargeUtil;
import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.business.NetPayUtil;
import com.wlt.webm.business.bean.FaacBean;
import com.wlt.webm.business.bean.OrderBean;
import com.wlt.webm.business.bean.SysBankLog;
import com.wlt.webm.business.bean.SysInvoke;
import com.wlt.webm.business.bean.SysRebate;
import com.wlt.webm.business.dianx.form.TelcomForm;
import com.wlt.webm.business.form.BankLogForm;
import com.wlt.webm.business.form.ChildFaactForm;
import com.wlt.webm.business.form.JtfkForm;
import com.wlt.webm.business.form.OrderForm;
import com.wlt.webm.business.form.SysInvokeForm;
import com.wlt.webm.business.form.SysPhoneAreaForm;
import com.wlt.webm.business.form.SysRebateForm;
import com.wlt.webm.business.form.SysUserInterfaceForm;
import com.wlt.webm.business.service.JtfkService;
import com.wlt.webm.db.DBService;
import com.wlt.webm.rights.bean.SysLoginUser;
import com.wlt.webm.rights.bean.SysRole;
import com.wlt.webm.rights.bean.SysUser;
import com.wlt.webm.rights.bean.SysUserBank;
import com.wlt.webm.rights.form.SysLoginUserForm;
import com.wlt.webm.rights.form.SysUserBankForm;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.scputil.DateParser;
import com.wlt.webm.scputil.bean.FacctBillBean;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.MD5;
import com.wlt.webm.util.MacUtil;
import com.wlt.webm.xunjie.bean.SlsBean;

public class PhoneDao {
	
	/**
	 * 获得公告
	 */
	public static List getSysNoticeListLatest(SysUserForm userForm) throws Exception {
		List rep = new ArrayList();
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append("select a.an_id,a.an_title,a.an_faceid,a.an_activedate,a.an_deaddate,a.if_active,b.sa_name,a.an_content ");
		sql.append(" from sys_annotice a left join sys_area b");
		sql.append(" on b.sa_id = a.an_faceid where 1=1 ");
		
		if(  !StringUtils.isEmpty( userForm.getAreaid()) )
			sql.append(" and a.an_faceid='").append(userForm.getUser_site_city().trim()).append("' or a.an_faceid='0'");
		
		sql.append(" order by a.an_type desc, a.an_deaddate desc limit 0,3 ");
		List list = dbService.getList(sql.toString());
		for(Object tmp : list){
			String[] temp = (String[])tmp;
			
			if(null != temp[5] && !"".equals(temp[5])){
				if("0".equals(temp[5])){
					temp[5] = "无效";
				}else if("1".equals(temp[5])){
					temp[5] = "有效";
				}
			}
			if(null != temp[2] && !"".equals(temp[2])){
				if("0".equals(temp[2])){
					temp[6] = "所有";
				}
			}
			
			//
			Map map = new HashMap();
			map.put("title", URLEncoder.encode(temp[1], "UTF-8"));
			map.put("content", URLEncoder.encode(temp[7], "UTF-8"));
			map.put("activedate", URLEncoder.encode(temp[3], "UTF-8"));
			map.put("time", temp[4]);
			rep.add(map);
			
		}
		return rep;
	}
	
	 /**
	 * 获取code信息获得名字
	 * @throws Exception
	 */
	public static String getSite(String code) throws Exception {
        DBService db = new DBService();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select sa_name from sys_area ")
                    .append(" where sa_id='").append(code).append("'");
            return db.getString(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	
	 /**
	 * 获取号码区域信息
	 * @return 号码区域信息
	 * @throws Exception
	 */
	public static SysPhoneAreaForm getPhoneInfo(String pnum) throws Exception {
        DBService db = new DBService();
        SysPhoneAreaForm spForm = new SysPhoneAreaForm();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select province_code, city_name, phone_type, cart_type, area_code, phone_type ")
                    .append(" from sys_phone_area ")
                    .append(" where phone_num=? ");

            String[] params = { pnum };
            String[] fields = { "province_code","city_name", "phone_type", "cart_type", "area_code", "phone_type"};
            
            db.populate(spForm, fields, sql.toString(), params);

            return spForm;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	
	
	/**
	 * 交通罚款查询
	 */
	public static int queryJtfk(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JtfkForm jtForm = (JtfkForm) form;
//		String vehicleSel = request.getParameter("vehicleSel");
//		jtForm.setVehicle(vehicleSel+jtForm.getVehicle());
		List jtfkList = new ArrayList();
		try {
			jtfkList = JtfkService.jtfkQuery(jtForm);
		} catch (Exception e) {
//			e.printStackTrace();
			request.setAttribute("mess", "查询出错");
			return -1;
		} 
		request.setAttribute("jtfkList", jtfkList);
		return 0;
	}
	
	/**
	 * 交通罚款支付订单
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public static int payOrder(ActionMapping mapping, ActionForm form, SysUserForm userForm, 
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JtfkForm jtForm = (JtfkForm) form;

//		HttpSession session = request.getSession();
//		SysUserForm userForm = (SysUserForm) session.getAttribute("userSession");
//		//查找折扣率
		SysRebate sr = new SysRebate();
		SysRebateForm sRebate = sr.getRebateByRole(null, "3");
		if(null == sRebate || null == sRebate.getSc_traderpercent() || "".equals(sRebate.getSc_traderpercent())){
			request.setAttribute("mess", "无对应折扣率");
//			return mapping.findForward("paysuccess");
			return -1;
		}
		String rebatePercent = sRebate.getSc_traderpercent();
		//流水号
		String cftTransId = Tools.getSerialForJtfk();
		//交易时间
		String datetime = DateParser.getJtfkDateTime();
		//当前时间yyyyMMddHHmmss
		String nowTime=Tools.getNow();
		//违章id
		String violationId = request.getParameter("violationId");
		//是否邮寄
		String mailReceipt = request.getParameter("isMail");
		//邮寄地址
		String mailAddr = "";
		//邮寄费用
		String mailFee = "";
		//总费用
		String totalFee = request.getParameter("totalFee");
		if("1".equals(mailReceipt)){
			mailAddr = request.getParameter("mailAddr");
			mailFee = "1500";
			totalFee = String.valueOf(Integer.parseInt(totalFee)+1500);
		}
		//币种 1-RMB
		String feeType = "1"; 
		//查询资金账号
		FaacBean fb = new FaacBean();
		ChildFaactForm cfForm = fb.getUserFundAcct(userForm.getUser_id(), "02");
		if(cfForm.getUsableleft() <= 0 || cfForm.getUsableleft() - Integer.parseInt(totalFee) < 0){
			request.setAttribute("mess", "余额不足");
//			return mapping.findForward("paysuccess");
			return -1;
		}
		String actFee = String.valueOf(Integer.parseInt(totalFee)+1000);
		//记录订单信息
		OrderForm ordForm = new OrderForm();
		//区域
		ordForm.setAreacode(userForm.getUsersite());
		ordForm.setTradeserial(cftTransId);
		ordForm.setTradeobject(violationId);
		ordForm.setBuyid("A999");
		//需要修改
		ordForm.setService("A9999");
		ordForm.setFee(actFee);
		//需要修改
		ordForm.setFundacct(cfForm.getChildfacct());
		ordForm.setTradetime(nowTime);
		ordForm.setChgtime(nowTime);
		ordForm.setState("3");
		ordForm.setWriteoff("");
		ordForm.setWritecheck("");
		ordForm.setTerm_type("0");
		ordForm.setUserId(userForm.getUser_id());
		ordForm.setAccountleft(String.valueOf((cfForm.getUsableleft()-Integer.parseInt(actFee))));
		ordForm.setUsername(userForm.getLogin());
		ordForm.setPhone_type("A1");
		ordForm.setTableName("wlt_orderform_"+DateParser.getNowDateTable());
		OrderBean orderBean = new OrderBean();
		orderBean.getOrderId(ordForm);
		orderBean.add(ordForm);
		
		Map queryMap = new HashMap();
		queryMap.put("cfttransid", cftTransId);
		queryMap.put("datetime", datetime);
		queryMap.put("violationid", violationId);
		queryMap.put("mailreceipt", mailReceipt);
		queryMap.put("mailaddr", mailAddr);
		queryMap.put("mailfee", mailFee);
		queryMap.put("feetype", feeType);
		queryMap.put("totalfee", totalFee);
		//下订单
		Map<String, String> resultMap = null;
		MobileChargeService service = new MobileChargeService();
		try{
			resultMap = JtfkService.createOrder(cftTransId, datetime, violationId, mailReceipt, mailAddr, mailFee, feeType, totalFee);
		}catch (Exception e) {
			//更新订单状态
			service.updOrderState("wlt_orderForm_"+nowTime.substring(2, 6), ordForm.getTradeserial());
			request.setAttribute("mess", "下订单失败");
//			return mapping.findForward("paysuccess");
			return -1;
		}
		if(null == resultMap || null == resultMap.get("SpRetCode") || "".equals(resultMap.get("SpRetCode")) || !"0000".equals(resultMap.get("SpRetCode"))){
			//更新订单状态
			service.updOrderState("wlt_orderForm_"+nowTime.substring(2, 6), ordForm.getTradeserial());
			request.setAttribute("mess", "下订单失败");
//			return mapping.findForward("paysuccess");
			return -1;
		}
		queryMap.put("sptransid", resultMap.get("SpTransId"));
		//扣资金帐号,修改订单状态，记录帐号日志，记录交通罚款的明细单
		JtfkService.updateAccountAndStatus(rebatePercent, userForm.getUser_id(), Integer.parseInt(actFee), ordForm, jtForm, queryMap,DateParser.getNowDateTable());
		Map<String, String> payMap = null;
		try{
			//支付订单
			payMap = JtfkService.payOrder(cftTransId, resultMap.get("SpTransId"), datetime, violationId, mailReceipt, mailAddr, mailFee, feeType, totalFee);
		}catch (Exception e) {
			//更新资金账户，账户明细---交通罚款
			service.updAccountJtfk(Integer.parseInt(actFee)-Integer.parseInt(actFee)*(Integer.parseInt(rebatePercent))/100, userForm.getUser_id(), "wlt_acctbill_"+nowTime.substring(2, 6), ordForm.getTradeserial());
			request.setAttribute("mess", "支付失败");
//			return mapping.findForward("paysuccess");
			return -1;
		}
		if(null == payMap || null == payMap.get("SpRetCode") || "".equals(payMap.get("SpRetCode")) || !"0000".equals(payMap.get("SpRetCode"))){
			//更新资金账户，账户明细---交通罚款
			service.updAccountJtfk(Integer.parseInt(actFee)-Integer.parseInt(actFee)*(Integer.parseInt(rebatePercent))/100, userForm.getUser_id(), "wlt_acctbill_"+nowTime.substring(2, 6), ordForm.getTradeserial());
			request.setAttribute("mess", "支付失败");
//			return mapping.findForward("paysuccess");
			return -1;
		}
		
		request.setAttribute("mess", "支付成功");
//		return mapping.findForward("paysuccess");
		return 0;
	}
	
	 /**
     * 全国充值
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public static int tzFill( TelcomForm form, SysUserForm user,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		String payNo = request.getParameter("tradeObject").replaceAll(" ", "");
		String payNo = form.getTradeObject();
//		String paraMoney = request.getParameter("money");
		String paraMoney = form.getPayFee();
		String mon = Tools.yuanToFen(paraMoney);
		System.out.println("号码:" + payNo + "   金额:" + mon);
		String sepNo = Tools.getSeqNo(payNo);
		int payFee = Integer.parseInt(mon);
		String nowTime = Tools.getNow();
		   String inId="0005";
		//
//		SysUserForm user = (SysUserForm) request.getSession().getAttribute(
//				"userSession");
		MobileChargeService service = new MobileChargeService();
		// 用户区域
		String userArea = service.getUserArea(user.getUser_id());
		// 号码类型和归属id是否存在
		SysPhoneAreaForm spa = service.getPhoneInfo(payNo.substring(0, 7));
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
		
		
		if (null == sgId || "".equals(sgId)) {
			request.setAttribute("mess", "无对应佣金组");
			//return mapping.findForward("mobilecharge");
			return -1;
		}
		//System.out.println("===================="+sgId+"-------");
		// 用户接口id是否存在
//		SysUserInterfaceForm sui = new SysUserInterfaceForm();
//		sui.setProvince_code(spa.getProvince_code());
//		sui.setCharge_type(spa.getPhone_type());
//		sui.setUser_id(Integer.parseInt(user.getUser_id()));
//		String inId = service.getInterfaceId(sui);
//		if (null == inId || "".equals(inId)) {
//			request.setAttribute("mess", "无对应充值接口");
//			return mapping.findForward("mobilecharge");
//		}
		// 用户的佣金组是否存在
		String roleType = service.getUserRoleType(user.getUser_role());
		if (null == roleType || "".equals(roleType)) {
			request.setAttribute("mess", "未知用户类型");
//			return mapping.findForward("mobilecharge");
			return -1;
		}
		
		// 佣金子账号和押金账号是否存在
		String fundAcct02 = service.getUserFundAcct("02", user.getUser_id());
		if (null == fundAcct02 || "".equals(fundAcct02)) {
			request.setAttribute("mess", "押金账号不存在");
//			return mapping.findForward("mobilecharge");
			return -1;
		}
		String fundAcct03 = service.getUserFundAcct("03", user.getUser_id());
		if (null == fundAcct03 || "".equals(fundAcct03)) {
			request.setAttribute("mess", "佣金子账号不存在");
//			return mapping.findForward("mobilecharge");
			return -1;
		}
		// 佣金子账号和押金账号状态是否正常
		String state = service.getFundAcctState(fundAcct02);
		if (!"1".equals(state)) {
			request.setAttribute("mess", "押金账号不可用");
//			return mapping.findForward("mobilecharge");
			return -1;
		}
		String state1 = service.getFundAcctState(fundAcct03);
		if (!"1".equals(state1)) {
			request.setAttribute("mess", "佣金子账号不可用");
//			return mapping.findForward("mobilecharge");
			return -1;
		}
		// 押金子账号的金额是否大于扣费金额
		int acctLeft = Integer.parseInt(service.getFundAcctLeft(fundAcct02));
		if (0 == acctLeft || acctLeft - Integer.parseInt(mon) < 0) {
			request.setAttribute("mess", "押金账号余额不足");
//			return mapping.findForward("mobilecharge");
			return -1;
		}
		// 佣金明细是否存在
		String scId = service.getCommMx(sgId, String.valueOf(spa
				.getPhone_type()), Integer.parseInt(userArea), spa
				.getProvince_code(), mon);
		if (null == scId || "".equals(scId)) {
			request.setAttribute("mess", "佣金明细不存在");
//			return mapping.findForward("mobilecharge");
			return -1;
			
		}
		// 记录订单
		int isSuccess = 0;
		try {
			isSuccess = InnerProcessDeal.logOrder(String.valueOf(spa
					.getProvince_code()), sepNo, payNo, inId,
					Constant.MOBILE_CHARGE, Integer.parseInt(mon), fundAcct02
							.substring(0, fundAcct02.length() - 2), nowTime,
					nowTime, "", "", "0", user.getUser_id(), acctLeft
							- Integer.parseInt(mon), user.getLogin(), String
							.valueOf(spa.getPhone_type()));
		} catch (Exception e) {
			request.setAttribute("mess", "生成订单失败");
//			return mapping.findForward("mobilecharge");
			return -1;
		}
		if (0 != isSuccess) {
			request.setAttribute("mess", "生成订单失败");
//			return mapping.findForward("mobilecharge");
			return -1;
		}
		Map dealMap = new HashMap();
		if (0 == isSuccess) {
			try {
				// 内部 扣款、返佣、更改订单状态
				dealMap = InnerProcessDeal.indeal(fundAcct02.substring(0,
						fundAcct02.length() - 2), payNo, nowTime, Integer
						.parseInt(mon), String.valueOf(spa.getProvince_code()),
						service.getTradeType(inId), "", spa.getPhone_type(),
						Integer.parseInt(userArea), Integer.parseInt(user
								.getUser_id()), Integer.parseInt(user
								.getUser_role()), spa.getProvince_code(),
						sepNo, sgId);
			} catch (Exception e) {
				request.setAttribute("mess", "数据处理错误");
//				return mapping.findForward("mobilecharge");
				return -1;
			}
		}
		int dealFlag = (Integer) dealMap.get("flag");
		if (1 == dealFlag) {
			request.setAttribute("mess", "数据处理错误");
//			return mapping.findForward("mobilecharge");
			return -1;
		}
		// 调用充值接口
		int status = 0;
		try {
			SysInvokeForm invokeForm = new SysInvokeForm();
			SysInvoke invoke = new SysInvoke();
			if ("0005".equals(inId)) {// 殴飞充值
//				Map ofResultMap = OuFeiChargeUtil.charge(paraMoney, sepNo,
//						nowTime, payNo);
//				String retCode = (String) ofResultMap.get("retcode");
//				// 插入调用日志
//				invokeForm.setUser_id(user.getUser_id());
//				invokeForm.setOrd_id(sepNo);
//				invokeForm.setIn_input((String) ofResultMap.get("input"));
//				invokeForm.setIn_output(retCode);
//				invokeForm.setIn_time(nowTime);
//				invokeForm.setIn_desc("殴飞充值");
//				invokeForm.setIn_code(inId);
//				invokeForm.setIn_otherput("");
//				invokeForm.setSe_code("P0001");
//				invoke.add(invokeForm);
//				if ("9".equals(retCode)) {// 失败
//					status = 1;
//				} else {// 循环查询订单状态
//					int times = 0;
//					while (times < 50) {
//						try {
//							Thread.sleep(6000);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//						String ordState = service.getOrderStatus(sepNo,
//								"wlt_orderForm_" + nowTime.substring(2, 6));
//						if ("4".equals(ordState)) {
//							times++;
//						} else {
//							if ("1".equals(ordState)) {// 失败
//								status = 1;
//							} else if ("6".equals(ordState)) {// 异常
//								status = 2;
//							}
//							break;
//						}
//					}
//					// 如果没有回调，发送查询请求
//					if (times == 50) {
//						// 发送查询请求
//						int ouf = OuFeiChargeUtil.query(sepNo);
//						if (1 == ouf) {// 成功
//							status = 0;
//						} else if (0 == ouf) {// 充值中
//							status = 3;
//						} else if (9 == ouf) {// 失败
//							status = 1;
//						} else if (-1 == ouf) {// 无此订单
//							status = 1;
//						} else {
//							status = 3;
//						}
//					}
//				}
				System.out.println("调用手拉手===");
				SlsBean sls=new SlsBean();
				int k=sls.slsFill(payNo, sepNo, paraMoney);
				System.out.println("sls最终返回:"+k);
				if(k==0){
					status=0;
				}else if(k==1){
					status=1;
				}else if(k==2){
					status=2;
				}else{
					status=3;
				}
        	}else if("0006".equals(inId)){//捷贝充值
        		Map jbResultMap = JieBeiChargeUtil.charge(sepNo, payNo, paraMoney);
        		String retCode = (String)jbResultMap.get("resultno");
        		//插入调用日志
        		invokeForm.setUser_id(user.getUser_id());
        		invokeForm.setOrd_id(sepNo);
        		invokeForm.setIn_input((String)jbResultMap.get("input"));
        		invokeForm.setIn_output(retCode);
        		invokeForm.setIn_time(nowTime);
        		invokeForm.setIn_desc("捷贝充值");
        		invokeForm.setIn_code(inId);
        		invokeForm.setIn_otherput("");
        		invokeForm.setSe_code("P0001");
        		invoke.add(invokeForm);
        		if(!"01".equals(retCode) || !"1".equals(retCode) || !"2".equals(retCode) || !"02".equals(retCode)){//失败
        			status = 1;
        		}else {//循环查询订单状态
        			int times=0;
        			while(times < 6){
        				times++;//后补
//						String ordState = service.getOrderStatus(sepNo, "wlt_orderForm_"+nowTime.substring(2, 6));
//						if("4".equals(ordState)){
//							times++;
//						}else {
//							if("1".equals(ordState)){//失败
//								status = 1;
//							}else if("6".equals(ordState)){//异常
//								status = 2;
//							}
//							break;
//						}
//        			}
//        			//如果没有回调，发送查询请求
//        			if(times == 20){
						//发送查询请求
						Map jbMap = JieBeiChargeUtil.query(sepNo);
						String resultno = (String)jbMap.get("resultno");
						if("02".equals(resultno) || "2".equals(resultno)){//成功
							status = 0;
							break;
						}else if("03".equals(resultno) || "3".equals(resultno)||"20".equals(resultno)){//失败
							status=1;
							break;
						}
        				try {
							Thread.sleep(20000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
        			}
        			if(times==6){
        				status=2;
        			}
        			String ordState = service.getOrderStatus(sepNo, "wlt_orderForm_"+nowTime.substring(2, 6));
        			if("0".equals(ordState)){
        				status=0;
        			}else if("1".equals(ordState)){
        				status=1;
        			}
				}
			}else {
				status = 1;
			}
		} catch (Exception e) {
			//更新订单状态
//			service.updOrderState("wlt_orderForm_"+nowTime.substring(2, 6), sepNo);
			//返款，更新佣金、账户明细--
//			if(null != dealMap){
//				//自身返佣金额
//				String empFee = String.valueOf(dealMap.get("empFee-self"));
//				//账户明细表名
//				String tableName = "wlt_acctbill_"+nowTime.substring(2, 6);
//				//上一级账户
//				String empAcctLevlOne = String.valueOf(dealMap.get("acct-levelone"));
//				String empFeeLevlOne = String.valueOf(dealMap.get("empfee-levelone"));
//				//上上一级账户
////				String empAcctLevlTwo = (String)dealMap.get("acct-leveltwo");
////				String empFeeLevlTwo = (String)dealMap.get("empfee-leveltwo");
//				//自身返款,退回自身佣金并更新账户明细
//				service.updAccount(payFee, fundAcct02.substring(0, fundAcct02.length()-2), empFee, tableName,sepNo);
//				//上级返回佣金并更新账户明细
//				if(null != empAcctLevlOne && !"".equals(empAcctLevlOne)){
//					service.updEmpAccount(empAcctLevlOne, empFeeLevlOne, tableName,sepNo);
//				}
//				//上上级返回佣金并更新账户明细
////				if(null != empAcctLevlTwo && !"".equals(empAcctLevlTwo)){
////					service.updEmpAccount(empAcctLevlTwo, empFeeLevlTwo, tableName,sepNo);
////				}
//			}
			service.updOrderStatus(sepNo, "6", "wlt_orderForm_"+nowTime.substring(2, 6));
			request.setAttribute("mess", "充值接口异常");
//	        return mapping.findForward("mobilecharge");
			return -1;
			
		}
		if(0 == status){//充值成功,修改订单状态成功
			service.updOrderStatus(sepNo, "0", "wlt_orderForm_"+nowTime.substring(2, 6));
		}else if(1 == status){//充值失败,修改订单状态失败，退钱，退佣金
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
//				String empAcctLevlTwo = (String)dealMap.get("acct-leveltwo");
//				String empFeeLevlTwo = (String)dealMap.get("empfee-leveltwo");
				//自身返款,退回自身佣金并更新账户明细
				service.updAccount(payFee, fundAcct02.substring(0, fundAcct02.length()-2), empFee, tableName,sepNo);
				//上级返回佣金并更新账户明细
				if(null != empAcctLevlOne && !"".equals(empAcctLevlOne)){
					service.updEmpAccount(empAcctLevlOne, empFeeLevlOne, tableName,sepNo);
				}
//				//上上级返回佣金并更新账户明细
//				if(null != empAcctLevlTwo && !"".equals(empAcctLevlTwo)){
//					service.updEmpAccount(empAcctLevlTwo, empFeeLevlTwo, tableName,sepNo);
//				}
			}
			request.setAttribute("mess", "充值失败");
//	        return mapping.findForward("mobilecharge");
	        return -1;
		}else if(2 == status){//无返回结果,修改订单状态异常
			service.updOrderStatus(sepNo, "6", "wlt_orderForm_"+nowTime.substring(2, 6));
			request.setAttribute("mess", "充值异常");
//	        return mapping.findForward("mobilecharge");
	        return -1;
		}else if(3 == status){//无响应,修改订单状态无响应
			service.updOrderStatus(sepNo, "6", "wlt_orderForm_"+nowTime.substring(2, 6));
			request.setAttribute("mess", "充值无响应");
//	        return mapping.findForward("mobilecharge");
			return -1;
		}
        request.setAttribute("mess", "充值成功");
//        return mapping.findForward("mobilecharge");
        return 0;
	}
	//获得账户余额
	public static int getLeftFee(TelcomForm phonePayForm, SysUserForm user, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
        
		MobileChargeService service = new MobileChargeService();
        //佣金子账号和押金账号是否存在
        String fundAcct02 = service.getUserFundAcct("02", user.getUser_id());
        if(null == fundAcct02 || "".equals(fundAcct02)){
           return 4;//押金账号不存在
        }
        //押金子账号的金额是否大于扣费金额
        int acctLeft = Integer.parseInt(service.getFundAcctLeft(fundAcct02));
        request.setAttribute("balance", acctLeft);             // 账户余额\   实际扣费金额\应付金额\  号码类型\  号码区域\  号码信息
        return acctLeft;
	}
	
	//充值验证
	public static int validateAndGetPhone(TelcomForm phonePayForm, SysUserForm user, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String payNo		= phonePayForm.getTradeObject().trim();
        String paraMoney 	= phonePayForm.getPayFee();
        String numType		= phonePayForm.getNumType();
        String realFee		= Tools.yuanToFen(paraMoney);
//        String realFee		= phonePayForm.getPayFee();
        
		MobileChargeService service = new MobileChargeService();
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
        if(0 == acctLeft || acctLeft - Integer.parseInt(realFee) < 0){
        return 8;//押金账号余额不足
        }
        request.setAttribute("balance", acctLeft);             // 账户余额\   实际扣费金额\应付金额\  号码类型\  号码区域\  号码信息
        request.setAttribute("dedmoney", realFee);
        request.setAttribute("money", realFee);
        request.setAttribute("phone_type",numType);
        request.setAttribute("area_msg", "");
        request.setAttribute("phone_msg", "");
        return 0;
	}
	
	
	
	public static int validatePhone(TelcomForm phonePayForm, SysUserForm user, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String payNo		= phonePayForm.getTradeObject().trim();
        String paraMoney 	= phonePayForm.getPayFee();
        String numType		= phonePayForm.getNumType();
        String realFee		= Tools.yuanToFen(paraMoney);
       // String realFee		= phonePayForm.getPayFee();
        
		MobileChargeService service = new MobileChargeService();
		  //号码类型和归属id是否存在
        SysPhoneAreaForm spa = getPhoneInfo(payNo.substring(0,7));
        if(spa.getCart_type()==null)
        	return 2;//未知用户类型
	     
        //用户区域
	    String userArea = service.getUserArea(user.getUser_id());
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
        if(0 == acctLeft || acctLeft - Integer.parseInt(realFee) < 0){
        return 8;//押金账号余额不足
        }
      /*  //佣金明细是否存在
        String scId = service.getCommMx(sgId, String.valueOf( numType ), Integer.parseInt(userArea), spa.getProvince_code(), realFee );
        if(null == scId || "".equals(scId)){
        	    return 9;//佣金明细不存在
        }
        */
        request.setAttribute("balance", acctLeft);             // 账户余额\   实际扣费金额\应付金额\  号码类型\  号码区域\  号码信息
        request.setAttribute("dedmoney", realFee);
        request.setAttribute("money", realFee);
        request.setAttribute("phone_type",spa.getPhone_type());
        request.setAttribute("area_msg", spa.getCity_name());
        request.setAttribute("phone_msg", spa.getCart_type());
        return 0;
	}
	
	
	public static int login(SysUserForm userForm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	        String inputMsgCode = userForm.getPassword();
	        SysUser user=new SysUser();
	        int state = user.login(userForm,request);
	        if (state == -1) 
	        {
	            request.setAttribute("mess", "登录名不存在，请重新登录。");
	            return state;
	        }
	        if(null==request.getParameter("flag")&&(userForm.getRoleType().equals("0")||userForm.getRoleType().equals("1"))){
				request.setAttribute("mess", "请到管理平台进行登陆");
				return -3;
	        }
	        
	        if (null != userForm.getMacflag() && Integer.parseInt(userForm.getMacflag()) == 0) {
	        	String ip = MacUtil.getIpAddr(request);
				String localMacAddr = MacUtil.getMACAddress(ip);
				if(!localMacAddr.equals(userForm.getMac())){
					request.setAttribute("mess", "非指定电脑登陆");
					return -3;
				}
			}
	        if(null != userForm.getShortflag() && !"".equals(userForm.getShortflag()) && Integer.parseInt(userForm.getShortflag()) == 0){
	        	String msgCode = (String)request.getSession().getAttribute(userForm.getLogin()+"-login");
	        	if(!inputMsgCode.equals(msgCode)){
	        		request.setAttribute("mess", "短信验证码输入错误");
	        		return -3;
	        	}
	        }else if (state == -2) {
	            request.setAttribute("mess", "登录密码错误，请重新登录。");
	            return -3;
	        }
	        //session.setAttribute("userSession", userForm);
	        //查询用户权限
	        SysRole sRole = new SysRole();
	        List menuList = sRole.getMenuByRole(userForm.getUser_role());
	        String menuStr = "";
	        if(null != menuList && menuList.size() > 0){
	     	   for(int i = 0; i < menuList.size(); i++){
	     		  menuStr += ((String[])menuList.get(i))[0]+"|";
	     	   }
	        }
	        if(!"".equals(menuStr)){
	        	String[] powerArr = menuStr.substring(0, menuStr.length()-1).split("\\|");
	        	//session.setAttribute("powerArr", powerArr);
	        }
	        String posid=userForm.getUser_id();
	        request.setAttribute("useleft", user.getUseLeft(posid));
	        //激活
	        if(!user.activate(userForm.getLogin())){
	        	request.setAttribute("phonenum", userForm.getLogin().substring(0,3)+"***"+userForm.getLogin().substring(8));
	        	//return new ActionForward("/business/phoneValidata.jsp");
	        	return - 3;
	        }
	        //
	        //return mapping.findForward("login_success");
	        return 0;
		
	}
	
	public static int login1(SysUserForm userForm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	        String inputMsgCode = userForm.getPassword();
	        SysUser user=new SysUser();
	        int state = user.login1(userForm,request);
	        if (state == -1) 
	        {
	            request.setAttribute("mess", "登录名不存在，请重新登录。");
	            return state;
	        }
	        if(null==request.getParameter("flag")&&(userForm.getRoleType().equals("0")||userForm.getRoleType().equals("1"))){
				request.setAttribute("mess", "请到管理平台进行登陆");
				return -3;
	        }
	        
//	        if (null != userForm.getMacflag() && Integer.parseInt(userForm.getMacflag()) == 0) {
//	        	String ip = MacUtil.getIpAddr(request);
//				String localMacAddr = MacUtil.getMACAddress(ip);
//				if(!localMacAddr.equals(userForm.getMac())){
//					request.setAttribute("mess", "非指定电脑登陆");
//					return -3;
//				}
//			}
//	        if(null != userForm.getShortflag() && !"".equals(userForm.getShortflag()) && Integer.parseInt(userForm.getShortflag()) == 0){
//	        	String msgCode = (String)request.getSession().getAttribute(userForm.getLogin()+"-login");
//	        	if(!inputMsgCode.equals(msgCode)){
//	        		request.setAttribute("mess", "短信验证码输入错误");
//	        		return -3;
//	        	}
//	        }
//	        }else if (state == -2) {
//	            request.setAttribute("mess", "登录密码错误，请重新登录。");
//	            return -3;
//	        }
	        //session.setAttribute("userSession", userForm);
	        //查询用户权限
	        SysRole sRole = new SysRole();
	        List menuList = sRole.getMenuByRole(userForm.getUser_role());
	        String menuStr = "";
	        if(null != menuList && menuList.size() > 0){
	     	   for(int i = 0; i < menuList.size(); i++){
	     		  menuStr += ((String[])menuList.get(i))[0]+"|";
	     	   }
	        }
	        if(!"".equals(menuStr)){
	        	String[] powerArr = menuStr.substring(0, menuStr.length()-1).split("\\|");
	        	//session.setAttribute("powerArr", powerArr);
	        }
	        String posid=userForm.getUser_id();
	        request.setAttribute("useleft", user.getUseLeft(posid));
	        //激活
	        if(!user.activate(userForm.getLogin())){
	        	request.setAttribute("phonenum", userForm.getLogin().substring(0,3)+"***"+userForm.getLogin().substring(8));
	        	//return new ActionForward("/business/phoneValidata.jsp");
	        	return - 3;
	        }
	        //
	        //return mapping.findForward("login_success");
	        return 0;
		
	}
	
	/**
	 * 获取资金账户(单个)
	 * @return cfForm
	 * @throws Exception
	 */
	public static ChildFaactForm getUserFundAcct(String userid,String acctType) throws Exception {
        DBService db = new DBService();
        ChildFaactForm cfForm = new ChildFaactForm();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select a.accttypeid,a.usableleft,a.state,a.childfacct")
                    .append(" from wlt_childfacct a ")
                    .append(" where a.accttypeid = '"+acctType+"' and a.fundacct=(select fundacct from wlt_facct where termid = ?) ");

            String[] params = { userid };
            String[] fields = { "accttypeid","usableleft","state","childfacct"};
            
            db.populate(cfForm, fields, sql.toString(), params);

            return cfForm;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	
	//获得bank
	public static Map getAccountData(String userid) throws SQLException
	{
		DBService dbService = new DBService();
		Map map = new HashMap();
		StringBuffer sql = new StringBuffer();
		sql.append("select a.user_id, a.user_bankcard, a.user_name, a.user_icard_type, a.user_icard ");
		sql.append(" from sys_userbank a where 1=1");
		sql.append(" and a.user_id='").append(userid).append("'");
		List list = dbService.getList(sql.toString());
		for(Object tmp : list){
			String[] temp = (String[])tmp;
			map.put("bankno", temp[1]);
			map.put("user_name", temp[2]);
			map.put("user_icard_type", temp[3]);
			map.put("user_icard", temp[4]);
		}
		return map;
	}
	
	
	/**
	 * 银联转款验证及获取信
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public static int  transFoward(String fee, String password, SysUserForm userForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//获取银行信息
		SysUserBank userBank = new SysUserBank();
		SysUserBankForm bankForm = userBank.getUserBankInfo(userForm.getUser_id());
		if("01".equals(bankForm.getUser_icard_type())){
			bankForm.setUser_icard_type("身份证");
		}else if("02".equals(bankForm.getUser_icard_type())){
			bankForm.setUser_icard_type("军官证");
		}else if("03".equals(bankForm.getUser_icard_type())){
			bankForm.setUser_icard_type("护照");
		}else if("04".equals(bankForm.getUser_icard_type())){
			bankForm.setUser_icard_type("回乡证");
		}else if("05".equals(bankForm.getUser_icard_type())){
			bankForm.setUser_icard_type("台胞证");
		}else if("06".equals(bankForm.getUser_icard_type())){
			bankForm.setUser_icard_type("警官证");
		}else if("07".equals(bankForm.getUser_icard_type())){
			bankForm.setUser_icard_type("士兵证");
		}else if("99".equals(bankForm.getUser_icard_type())){
			bankForm.setUser_icard_type("其它证件");
		}
		
		String ka=bankForm.getUser_bankcard();
		if(null!=ka && !"".equals(ka)){
			String k=ka.substring(0, 4)+"***"+ka.substring(ka.length()-3, ka.length());
			bankForm.setUser_bankcard(k);
		}
		String zj=bankForm.getUser_icard();
		if(null!=zj && !"".equals(zj)){
			String z=zj.substring(0, 4)+"***"+zj.substring(zj.length()-3, zj.length());
			bankForm.setUser_icard(z);
		}
		request.setAttribute("bankinfo", bankForm);
		if(null != request.getParameter("mess") && !"".equals(request.getParameter("mess"))){
			request.setAttribute("mess", request.getParameter("mess"));
		}
		SysUser sysUser=new SysUser();
		String pass=sysUser.getPassWord(userForm.getUser_id());
		//System.out.println("mima--" + password + "--" + password + "--" + fee+ "==="+pass+"=====" + userForm.getUser_id());
		if (null == fee || "".equals(fee)) {
			request.setAttribute("mess", "金额无效！");
			return -1;
		}else if(!pass.equals(password)){
			request.setAttribute("mess", "交易密码错误！");
			return -1;
		}
		return 0;
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public static int trans(String fee,	SysUserForm userForm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//当前时间
		String nowTime=Tools.getNow();
		//商户id
		String mid = Constant.NETPAY_MERCHANT_ID;
		//流水号
		String serial = mid.substring(4,8)+""+mid.substring(mid.length()-4,mid.length())+""+NetPayUtil.getRandEight();
		request.setAttribute("serial", serial);
		//短信验证码
//		String inputMsgCode = (String)request.getParameter("msgcode");
		//交易金额
//		String feeYuan = (String)request.getParameter("fee");
//		request.setAttribute("fee", feeYuan);
//		String feeFen2 = Tools.yuanToFen(feeYuan);
		String feeFen2 = fee;
		String feeFen = feeFen2;
		
//		int fee=Integer.parseInt(feeFen);
//		if(fee<100000){
//			fee=fee-100;
//		}
//		feeFen=fee+"";
//		
		
//		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute("userSession");
		SysUserBank userBank = new SysUserBank();
		SysUserBankForm bankForm = userBank.getUserBankInfo(userForm.getUser_id());
		//银行卡是否绑定
		if(null == bankForm.getUser_bankcard() || "".equals(bankForm.getUser_bankcard())){
			request.setAttribute("mess", "未绑定银行卡");
//			return mapping.findForward("transnext");
			return -1;
		}
//		//获取验证标识
//		SysLoginUser loginUser = new SysLoginUser();
//		SysLoginUserForm loginUserForm =  loginUser.getLoginUserInfo(userForm.getLogin());
//		if(null != loginUserForm.getFeeshortflag() && !"".equals(loginUserForm.getFeeshortflag())){
//			if("0".equals(loginUserForm.getFeeshortflag())){//需要验证
//				String msgCode = (String)request.getSession().getAttribute(userForm.getLogin()+"-trans");
//				if(null == msgCode || "".equals(msgCode) || !inputMsgCode.equals(msgCode)){
//					request.setAttribute("mess", "短信验证码输入不正确");
//					return mapping.findForward("transnext");
//				}
//			}
//		}
		//转款日志注入
		SysBankLog bankLog = new SysBankLog();
		BankLogForm blForm = new BankLogForm();
		blForm.setDealserial(serial);
		blForm.setTradeaccount(bankForm.getUser_bankcard());
		blForm.setTradetime(nowTime);
		blForm.setTradefee(feeFen);
		blForm.setTradetype("0");
		blForm.setExplain("深圳银联转款");
		blForm.setState("3");
		blForm.setDistime(nowTime);
		blForm.setReturnmsg("");
		blForm.setCheckmsg("");
		blForm.setQrymsg("");
		bankLog.add(blForm);
		//账户明细注入
		String fundAcct = bankLog.getFundAcct(userForm.getUser_id());
		int acctLeft = Integer.parseInt(bankLog.getFundAcctLeft(fundAcct+"02"));
		FacctBillBean facct = new FacctBillBean();
		facct.setFacctTrade(fundAcct+"02", serial, bankForm.getUser_bankcard(), nowTime,
				feeFen, "37","深圳银联转款","0",nowTime,String.valueOf(acctLeft + Integer.parseInt(feeFen)), serial,bankForm.getUser_bankcard(),""+2);
		//记录账户明细信息,更新账户余额
		int result = bankLog.updateAcct(feeFen,facct,userForm);
		if(result == 1){
			request.setAttribute("mess", "转款失败");
//			return new ActionForward("/business/bank.do?method=transFoward");
			return -1;
		}
		//转款
		OrderData orderData = NetPayUtil.transMoney(serial,bankForm.getUser_bankcard(), feeFen2, bankForm.getUser_icard_type(), bankForm.getUser_icard(), bankForm.getUser_name());
		String resultCode = orderData.getRespCode();
		int status = 0;//成功标识
		if(null != resultCode && !"".equals(resultCode)){
			if(!"000000".equals(resultCode)){
				status = 1;
			}
		}else {
			status = 1;
		}
		if(0 == status){//转款成功
			//更新转款日志状态
			bankLog.update("0", serial);
			request.setAttribute("mess", "转款成功");
//			return new ActionForward("/business/bank.do?method=transFoward");
			return 0;
		}else {//转款失败
			//更新账户余额
			bankLog.update("1", serial);
			//更新账户余额
			bankLog.updAccountFee(userForm,feeFen);
			//更新账户明细状态
			String tableName = "wlt_acctbill_"+nowTime.substring(2, 6);
			bankLog.updAccount(feeFen,tableName,serial);
			request.setAttribute("mess", "转款失败");
//			return new ActionForward("/business/bank.do?method=transFoward");
			return -1;
		}
	}
	
	public static int updatePass(String type, String orgPassword, SysLoginUserForm userLoginForm, HttpServletRequest request) throws SQLException
	{
		SysLoginUser user = new SysLoginUser();
		String pwd = null;
		int    rs = -3;
		if( type.equals("0") )//登录密码
		{
			pwd = user.getPass(userLoginForm);
			if(!MD5.encode(orgPassword).equals(pwd)){
				request.setAttribute("mess", "原登录密码不正确!");
				return -1;
			}
			rs = user.updatePass(userLoginForm);
			
		}else if( type.equals("1")) 
		{
			pwd = user.getPassWord(userLoginForm);
			if(!orgPassword.equals(pwd)){
				request.setAttribute("mess", "原交易密码不正确!");
				return -1;
			}
			rs = user.updatePassWord(userLoginForm);
		}
		if( rs==1 )
			return 0;
		else
			return rs;
	}
	
	public static int getAccountInfo(SysUserForm userForm, HttpServletRequest request ) throws Exception
	{
		SysUser user = new SysUser();
		SysUserForm form = user.getUserAccount(userForm.getUser_id());
		request.setAttribute("yongjinbalance", form.getCommissionAmount());
		request.setAttribute("yajingbalance", form.getAccountAmount());
		request.setAttribute("dongjiebalance", form.getFrozenAmount());
		return 0;
	}
	
	public static int transChild(SysUserForm userForm, HttpServletRequest request) throws Exception
	{
		String fee = request.getParameter("transFee");
    	String mon=Tools.yuanToFen(fee);
    	String sepNo=Tools.getSeqNo("");
    	String nowTime=Tools.getNow();
    	MobileChargeService service = new MobileChargeService();
//        SysUserForm userForm = (SysUserForm) request.getSession().getAttribute("userSession");
        String fromAccount = service.getUserFundAcct("03",userForm.getUser_id());
        String toAccount = service.getUserFundAcct("02",userForm.getUser_id());
        String acctLeft = service.getFundAcctLeft(fromAccount);
        request.setAttribute("orderno", sepNo);
        if(null != acctLeft && !"".equals(acctLeft)){
        	int tmp = Integer.parseInt(acctLeft);
        	if(tmp - Integer.parseInt(mon) < 0){
        		request.setAttribute("mess", "余额不足");
        		return -1;
        	}
        	SysUser user=new SysUser();
        	user.transChild(fromAccount, toAccount, mon, sepNo, nowTime, "", "45", sepNo);
        	request.setAttribute("mess", "转移成功");
        	return 0;
        }else {
        	request.setAttribute("mess", "资金账户不存在");
        	return -1;
		}
	}
	
	
	
}