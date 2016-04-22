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
	 * ��ù���
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
					temp[5] = "��Ч";
				}else if("1".equals(temp[5])){
					temp[5] = "��Ч";
				}
			}
			if(null != temp[2] && !"".equals(temp[2])){
				if("0".equals(temp[2])){
					temp[6] = "����";
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
	 * ��ȡcode��Ϣ�������
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
	 * ��ȡ����������Ϣ
	 * @return ����������Ϣ
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
	 * ��ͨ�����ѯ
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
			request.setAttribute("mess", "��ѯ����");
			return -1;
		} 
		request.setAttribute("jtfkList", jtfkList);
		return 0;
	}
	
	/**
	 * ��ͨ����֧������
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
//		//�����ۿ���
		SysRebate sr = new SysRebate();
		SysRebateForm sRebate = sr.getRebateByRole(null, "3");
		if(null == sRebate || null == sRebate.getSc_traderpercent() || "".equals(sRebate.getSc_traderpercent())){
			request.setAttribute("mess", "�޶�Ӧ�ۿ���");
//			return mapping.findForward("paysuccess");
			return -1;
		}
		String rebatePercent = sRebate.getSc_traderpercent();
		//��ˮ��
		String cftTransId = Tools.getSerialForJtfk();
		//����ʱ��
		String datetime = DateParser.getJtfkDateTime();
		//��ǰʱ��yyyyMMddHHmmss
		String nowTime=Tools.getNow();
		//Υ��id
		String violationId = request.getParameter("violationId");
		//�Ƿ��ʼ�
		String mailReceipt = request.getParameter("isMail");
		//�ʼĵ�ַ
		String mailAddr = "";
		//�ʼķ���
		String mailFee = "";
		//�ܷ���
		String totalFee = request.getParameter("totalFee");
		if("1".equals(mailReceipt)){
			mailAddr = request.getParameter("mailAddr");
			mailFee = "1500";
			totalFee = String.valueOf(Integer.parseInt(totalFee)+1500);
		}
		//���� 1-RMB
		String feeType = "1"; 
		//��ѯ�ʽ��˺�
		FaacBean fb = new FaacBean();
		ChildFaactForm cfForm = fb.getUserFundAcct(userForm.getUser_id(), "02");
		if(cfForm.getUsableleft() <= 0 || cfForm.getUsableleft() - Integer.parseInt(totalFee) < 0){
			request.setAttribute("mess", "����");
//			return mapping.findForward("paysuccess");
			return -1;
		}
		String actFee = String.valueOf(Integer.parseInt(totalFee)+1000);
		//��¼������Ϣ
		OrderForm ordForm = new OrderForm();
		//����
		ordForm.setAreacode(userForm.getUsersite());
		ordForm.setTradeserial(cftTransId);
		ordForm.setTradeobject(violationId);
		ordForm.setBuyid("A999");
		//��Ҫ�޸�
		ordForm.setService("A9999");
		ordForm.setFee(actFee);
		//��Ҫ�޸�
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
		//�¶���
		Map<String, String> resultMap = null;
		MobileChargeService service = new MobileChargeService();
		try{
			resultMap = JtfkService.createOrder(cftTransId, datetime, violationId, mailReceipt, mailAddr, mailFee, feeType, totalFee);
		}catch (Exception e) {
			//���¶���״̬
			service.updOrderState("wlt_orderForm_"+nowTime.substring(2, 6), ordForm.getTradeserial());
			request.setAttribute("mess", "�¶���ʧ��");
//			return mapping.findForward("paysuccess");
			return -1;
		}
		if(null == resultMap || null == resultMap.get("SpRetCode") || "".equals(resultMap.get("SpRetCode")) || !"0000".equals(resultMap.get("SpRetCode"))){
			//���¶���״̬
			service.updOrderState("wlt_orderForm_"+nowTime.substring(2, 6), ordForm.getTradeserial());
			request.setAttribute("mess", "�¶���ʧ��");
//			return mapping.findForward("paysuccess");
			return -1;
		}
		queryMap.put("sptransid", resultMap.get("SpTransId"));
		//���ʽ��ʺ�,�޸Ķ���״̬����¼�ʺ���־����¼��ͨ�������ϸ��
		JtfkService.updateAccountAndStatus(rebatePercent, userForm.getUser_id(), Integer.parseInt(actFee), ordForm, jtForm, queryMap,DateParser.getNowDateTable());
		Map<String, String> payMap = null;
		try{
			//֧������
			payMap = JtfkService.payOrder(cftTransId, resultMap.get("SpTransId"), datetime, violationId, mailReceipt, mailAddr, mailFee, feeType, totalFee);
		}catch (Exception e) {
			//�����ʽ��˻����˻���ϸ---��ͨ����
			service.updAccountJtfk(Integer.parseInt(actFee)-Integer.parseInt(actFee)*(Integer.parseInt(rebatePercent))/100, userForm.getUser_id(), "wlt_acctbill_"+nowTime.substring(2, 6), ordForm.getTradeserial());
			request.setAttribute("mess", "֧��ʧ��");
//			return mapping.findForward("paysuccess");
			return -1;
		}
		if(null == payMap || null == payMap.get("SpRetCode") || "".equals(payMap.get("SpRetCode")) || !"0000".equals(payMap.get("SpRetCode"))){
			//�����ʽ��˻����˻���ϸ---��ͨ����
			service.updAccountJtfk(Integer.parseInt(actFee)-Integer.parseInt(actFee)*(Integer.parseInt(rebatePercent))/100, userForm.getUser_id(), "wlt_acctbill_"+nowTime.substring(2, 6), ordForm.getTradeserial());
			request.setAttribute("mess", "֧��ʧ��");
//			return mapping.findForward("paysuccess");
			return -1;
		}
		
		request.setAttribute("mess", "֧���ɹ�");
//		return mapping.findForward("paysuccess");
		return 0;
	}
	
	 /**
     * ȫ����ֵ
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
		System.out.println("����:" + payNo + "   ���:" + mon);
		String sepNo = Tools.getSeqNo(payNo);
		int payFee = Integer.parseInt(mon);
		String nowTime = Tools.getNow();
		   String inId="0005";
		//
//		SysUserForm user = (SysUserForm) request.getSession().getAttribute(
//				"userSession");
		MobileChargeService service = new MobileChargeService();
		// �û�����
		String userArea = service.getUserArea(user.getUser_id());
		// �������ͺ͹���id�Ƿ����
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
			request.setAttribute("mess", "�޶�ӦӶ����");
			//return mapping.findForward("mobilecharge");
			return -1;
		}
		//System.out.println("===================="+sgId+"-------");
		// �û��ӿ�id�Ƿ����
//		SysUserInterfaceForm sui = new SysUserInterfaceForm();
//		sui.setProvince_code(spa.getProvince_code());
//		sui.setCharge_type(spa.getPhone_type());
//		sui.setUser_id(Integer.parseInt(user.getUser_id()));
//		String inId = service.getInterfaceId(sui);
//		if (null == inId || "".equals(inId)) {
//			request.setAttribute("mess", "�޶�Ӧ��ֵ�ӿ�");
//			return mapping.findForward("mobilecharge");
//		}
		// �û���Ӷ�����Ƿ����
		String roleType = service.getUserRoleType(user.getUser_role());
		if (null == roleType || "".equals(roleType)) {
			request.setAttribute("mess", "δ֪�û�����");
//			return mapping.findForward("mobilecharge");
			return -1;
		}
		
		// Ӷ�����˺ź�Ѻ���˺��Ƿ����
		String fundAcct02 = service.getUserFundAcct("02", user.getUser_id());
		if (null == fundAcct02 || "".equals(fundAcct02)) {
			request.setAttribute("mess", "Ѻ���˺Ų�����");
//			return mapping.findForward("mobilecharge");
			return -1;
		}
		String fundAcct03 = service.getUserFundAcct("03", user.getUser_id());
		if (null == fundAcct03 || "".equals(fundAcct03)) {
			request.setAttribute("mess", "Ӷ�����˺Ų�����");
//			return mapping.findForward("mobilecharge");
			return -1;
		}
		// Ӷ�����˺ź�Ѻ���˺�״̬�Ƿ�����
		String state = service.getFundAcctState(fundAcct02);
		if (!"1".equals(state)) {
			request.setAttribute("mess", "Ѻ���˺Ų�����");
//			return mapping.findForward("mobilecharge");
			return -1;
		}
		String state1 = service.getFundAcctState(fundAcct03);
		if (!"1".equals(state1)) {
			request.setAttribute("mess", "Ӷ�����˺Ų�����");
//			return mapping.findForward("mobilecharge");
			return -1;
		}
		// Ѻ�����˺ŵĽ���Ƿ���ڿ۷ѽ��
		int acctLeft = Integer.parseInt(service.getFundAcctLeft(fundAcct02));
		if (0 == acctLeft || acctLeft - Integer.parseInt(mon) < 0) {
			request.setAttribute("mess", "Ѻ���˺�����");
//			return mapping.findForward("mobilecharge");
			return -1;
		}
		// Ӷ����ϸ�Ƿ����
		String scId = service.getCommMx(sgId, String.valueOf(spa
				.getPhone_type()), Integer.parseInt(userArea), spa
				.getProvince_code(), mon);
		if (null == scId || "".equals(scId)) {
			request.setAttribute("mess", "Ӷ����ϸ������");
//			return mapping.findForward("mobilecharge");
			return -1;
			
		}
		// ��¼����
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
			request.setAttribute("mess", "���ɶ���ʧ��");
//			return mapping.findForward("mobilecharge");
			return -1;
		}
		if (0 != isSuccess) {
			request.setAttribute("mess", "���ɶ���ʧ��");
//			return mapping.findForward("mobilecharge");
			return -1;
		}
		Map dealMap = new HashMap();
		if (0 == isSuccess) {
			try {
				// �ڲ� �ۿ��Ӷ�����Ķ���״̬
				dealMap = InnerProcessDeal.indeal(fundAcct02.substring(0,
						fundAcct02.length() - 2), payNo, nowTime, Integer
						.parseInt(mon), String.valueOf(spa.getProvince_code()),
						service.getTradeType(inId), "", spa.getPhone_type(),
						Integer.parseInt(userArea), Integer.parseInt(user
								.getUser_id()), Integer.parseInt(user
								.getUser_role()), spa.getProvince_code(),
						sepNo, sgId);
			} catch (Exception e) {
				request.setAttribute("mess", "���ݴ������");
//				return mapping.findForward("mobilecharge");
				return -1;
			}
		}
		int dealFlag = (Integer) dealMap.get("flag");
		if (1 == dealFlag) {
			request.setAttribute("mess", "���ݴ������");
//			return mapping.findForward("mobilecharge");
			return -1;
		}
		// ���ó�ֵ�ӿ�
		int status = 0;
		try {
			SysInvokeForm invokeForm = new SysInvokeForm();
			SysInvoke invoke = new SysInvoke();
			if ("0005".equals(inId)) {// Ź�ɳ�ֵ
//				Map ofResultMap = OuFeiChargeUtil.charge(paraMoney, sepNo,
//						nowTime, payNo);
//				String retCode = (String) ofResultMap.get("retcode");
//				// ���������־
//				invokeForm.setUser_id(user.getUser_id());
//				invokeForm.setOrd_id(sepNo);
//				invokeForm.setIn_input((String) ofResultMap.get("input"));
//				invokeForm.setIn_output(retCode);
//				invokeForm.setIn_time(nowTime);
//				invokeForm.setIn_desc("Ź�ɳ�ֵ");
//				invokeForm.setIn_code(inId);
//				invokeForm.setIn_otherput("");
//				invokeForm.setSe_code("P0001");
//				invoke.add(invokeForm);
//				if ("9".equals(retCode)) {// ʧ��
//					status = 1;
//				} else {// ѭ����ѯ����״̬
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
//							if ("1".equals(ordState)) {// ʧ��
//								status = 1;
//							} else if ("6".equals(ordState)) {// �쳣
//								status = 2;
//							}
//							break;
//						}
//					}
//					// ���û�лص������Ͳ�ѯ����
//					if (times == 50) {
//						// ���Ͳ�ѯ����
//						int ouf = OuFeiChargeUtil.query(sepNo);
//						if (1 == ouf) {// �ɹ�
//							status = 0;
//						} else if (0 == ouf) {// ��ֵ��
//							status = 3;
//						} else if (9 == ouf) {// ʧ��
//							status = 1;
//						} else if (-1 == ouf) {// �޴˶���
//							status = 1;
//						} else {
//							status = 3;
//						}
//					}
//				}
				System.out.println("����������===");
				SlsBean sls=new SlsBean();
				int k=sls.slsFill(payNo, sepNo, paraMoney);
				System.out.println("sls���շ���:"+k);
				if(k==0){
					status=0;
				}else if(k==1){
					status=1;
				}else if(k==2){
					status=2;
				}else{
					status=3;
				}
        	}else if("0006".equals(inId)){//�ݱ���ֵ
        		Map jbResultMap = JieBeiChargeUtil.charge(sepNo, payNo, paraMoney);
        		String retCode = (String)jbResultMap.get("resultno");
        		//���������־
        		invokeForm.setUser_id(user.getUser_id());
        		invokeForm.setOrd_id(sepNo);
        		invokeForm.setIn_input((String)jbResultMap.get("input"));
        		invokeForm.setIn_output(retCode);
        		invokeForm.setIn_time(nowTime);
        		invokeForm.setIn_desc("�ݱ���ֵ");
        		invokeForm.setIn_code(inId);
        		invokeForm.setIn_otherput("");
        		invokeForm.setSe_code("P0001");
        		invoke.add(invokeForm);
        		if(!"01".equals(retCode) || !"1".equals(retCode) || !"2".equals(retCode) || !"02".equals(retCode)){//ʧ��
        			status = 1;
        		}else {//ѭ����ѯ����״̬
        			int times=0;
        			while(times < 6){
        				times++;//��
//						String ordState = service.getOrderStatus(sepNo, "wlt_orderForm_"+nowTime.substring(2, 6));
//						if("4".equals(ordState)){
//							times++;
//						}else {
//							if("1".equals(ordState)){//ʧ��
//								status = 1;
//							}else if("6".equals(ordState)){//�쳣
//								status = 2;
//							}
//							break;
//						}
//        			}
//        			//���û�лص������Ͳ�ѯ����
//        			if(times == 20){
						//���Ͳ�ѯ����
						Map jbMap = JieBeiChargeUtil.query(sepNo);
						String resultno = (String)jbMap.get("resultno");
						if("02".equals(resultno) || "2".equals(resultno)){//�ɹ�
							status = 0;
							break;
						}else if("03".equals(resultno) || "3".equals(resultno)||"20".equals(resultno)){//ʧ��
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
			//���¶���״̬
//			service.updOrderState("wlt_orderForm_"+nowTime.substring(2, 6), sepNo);
			//�������Ӷ���˻���ϸ--
//			if(null != dealMap){
//				//����Ӷ���
//				String empFee = String.valueOf(dealMap.get("empFee-self"));
//				//�˻���ϸ����
//				String tableName = "wlt_acctbill_"+nowTime.substring(2, 6);
//				//��һ���˻�
//				String empAcctLevlOne = String.valueOf(dealMap.get("acct-levelone"));
//				String empFeeLevlOne = String.valueOf(dealMap.get("empfee-levelone"));
//				//����һ���˻�
////				String empAcctLevlTwo = (String)dealMap.get("acct-leveltwo");
////				String empFeeLevlTwo = (String)dealMap.get("empfee-leveltwo");
//				//������,�˻�����Ӷ�𲢸����˻���ϸ
//				service.updAccount(payFee, fundAcct02.substring(0, fundAcct02.length()-2), empFee, tableName,sepNo);
//				//�ϼ�����Ӷ�𲢸����˻���ϸ
//				if(null != empAcctLevlOne && !"".equals(empAcctLevlOne)){
//					service.updEmpAccount(empAcctLevlOne, empFeeLevlOne, tableName,sepNo);
//				}
//				//���ϼ�����Ӷ�𲢸����˻���ϸ
////				if(null != empAcctLevlTwo && !"".equals(empAcctLevlTwo)){
////					service.updEmpAccount(empAcctLevlTwo, empFeeLevlTwo, tableName,sepNo);
////				}
//			}
			service.updOrderStatus(sepNo, "6", "wlt_orderForm_"+nowTime.substring(2, 6));
			request.setAttribute("mess", "��ֵ�ӿ��쳣");
//	        return mapping.findForward("mobilecharge");
			return -1;
			
		}
		if(0 == status){//��ֵ�ɹ�,�޸Ķ���״̬�ɹ�
			service.updOrderStatus(sepNo, "0", "wlt_orderForm_"+nowTime.substring(2, 6));
		}else if(1 == status){//��ֵʧ��,�޸Ķ���״̬ʧ�ܣ���Ǯ����Ӷ��
			//���¶���״̬
			service.updOrderState("wlt_orderForm_"+nowTime.substring(2, 6), sepNo);
			//�������Ӷ���˻���ϸ--
			if(null != dealMap){
				//����Ӷ���
				String empFee = String.valueOf(dealMap.get("empFee-self"));
				//�˻���ϸ����
				String tableName = "wlt_acctbill_"+nowTime.substring(2, 6);
				//��һ���˻�
				String empAcctLevlOne = String.valueOf(dealMap.get("acct-levelone"));
				String empFeeLevlOne = String.valueOf(dealMap.get("empfee-levelone"));
				//����һ���˻�
//				String empAcctLevlTwo = (String)dealMap.get("acct-leveltwo");
//				String empFeeLevlTwo = (String)dealMap.get("empfee-leveltwo");
				//������,�˻�����Ӷ�𲢸����˻���ϸ
				service.updAccount(payFee, fundAcct02.substring(0, fundAcct02.length()-2), empFee, tableName,sepNo);
				//�ϼ�����Ӷ�𲢸����˻���ϸ
				if(null != empAcctLevlOne && !"".equals(empAcctLevlOne)){
					service.updEmpAccount(empAcctLevlOne, empFeeLevlOne, tableName,sepNo);
				}
//				//���ϼ�����Ӷ�𲢸����˻���ϸ
//				if(null != empAcctLevlTwo && !"".equals(empAcctLevlTwo)){
//					service.updEmpAccount(empAcctLevlTwo, empFeeLevlTwo, tableName,sepNo);
//				}
			}
			request.setAttribute("mess", "��ֵʧ��");
//	        return mapping.findForward("mobilecharge");
	        return -1;
		}else if(2 == status){//�޷��ؽ��,�޸Ķ���״̬�쳣
			service.updOrderStatus(sepNo, "6", "wlt_orderForm_"+nowTime.substring(2, 6));
			request.setAttribute("mess", "��ֵ�쳣");
//	        return mapping.findForward("mobilecharge");
	        return -1;
		}else if(3 == status){//����Ӧ,�޸Ķ���״̬����Ӧ
			service.updOrderStatus(sepNo, "6", "wlt_orderForm_"+nowTime.substring(2, 6));
			request.setAttribute("mess", "��ֵ����Ӧ");
//	        return mapping.findForward("mobilecharge");
			return -1;
		}
        request.setAttribute("mess", "��ֵ�ɹ�");
//        return mapping.findForward("mobilecharge");
        return 0;
	}
	//����˻����
	public static int getLeftFee(TelcomForm phonePayForm, SysUserForm user, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
        
		MobileChargeService service = new MobileChargeService();
        //Ӷ�����˺ź�Ѻ���˺��Ƿ����
        String fundAcct02 = service.getUserFundAcct("02", user.getUser_id());
        if(null == fundAcct02 || "".equals(fundAcct02)){
           return 4;//Ѻ���˺Ų�����
        }
        //Ѻ�����˺ŵĽ���Ƿ���ڿ۷ѽ��
        int acctLeft = Integer.parseInt(service.getFundAcctLeft(fundAcct02));
        request.setAttribute("balance", acctLeft);             // �˻����\   ʵ�ʿ۷ѽ��\Ӧ�����\  ��������\  ��������\  ������Ϣ
        return acctLeft;
	}
	
	//��ֵ��֤
	public static int validateAndGetPhone(TelcomForm phonePayForm, SysUserForm user, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String payNo		= phonePayForm.getTradeObject().trim();
        String paraMoney 	= phonePayForm.getPayFee();
        String numType		= phonePayForm.getNumType();
        String realFee		= Tools.yuanToFen(paraMoney);
//        String realFee		= phonePayForm.getPayFee();
        
		MobileChargeService service = new MobileChargeService();
		 //�û���Ӷ�����Ƿ����
        String roleType = service.getUserRoleType(user.getUser_role());
        if(null == roleType || "".equals(roleType)){
           return 2;//δ֪�û�����
        }
        String sgId = service.getUserCommission(user.getUser_id());
        if(null == sgId || "".equals(sgId)){
        	return 3;//�޶�ӦӶ����
        }
        //Ӷ�����˺ź�Ѻ���˺��Ƿ����
        String fundAcct02 = service.getUserFundAcct("02", user.getUser_id());
        if(null == fundAcct02 || "".equals(fundAcct02)){
           return 4;//Ѻ���˺Ų�����
        }
        String fundAcct03 = service.getUserFundAcct("03", user.getUser_id());
        if(null == fundAcct03 || "".equals(fundAcct03)){
        	    return 5;//Ӷ�����˺Ų�����
        }
        //Ӷ�����˺ź�Ѻ���˺�״̬�Ƿ�����
        String state = service.getFundAcctState(fundAcct02);
        if(!"1".equals(state)){
        	return 6;//Ѻ���˺Ų�����
        }
        String state1 = service.getFundAcctState(fundAcct03);
        if(!"1".equals(state1)){
             return 7;//Ӷ�����˺Ų�����
        }
        //Ѻ�����˺ŵĽ���Ƿ���ڿ۷ѽ��
        int acctLeft = Integer.parseInt(service.getFundAcctLeft(fundAcct02));
        if(0 == acctLeft || acctLeft - Integer.parseInt(realFee) < 0){
        return 8;//Ѻ���˺�����
        }
        request.setAttribute("balance", acctLeft);             // �˻����\   ʵ�ʿ۷ѽ��\Ӧ�����\  ��������\  ��������\  ������Ϣ
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
		  //�������ͺ͹���id�Ƿ����
        SysPhoneAreaForm spa = getPhoneInfo(payNo.substring(0,7));
        if(spa.getCart_type()==null)
        	return 2;//δ֪�û�����
	     
        //�û�����
	    String userArea = service.getUserArea(user.getUser_id());
		 //�û���Ӷ�����Ƿ����
        String roleType = service.getUserRoleType(user.getUser_role());
        if(null == roleType || "".equals(roleType)){
           return 2;//δ֪�û�����
        }
        String sgId = service.getUserCommission(user.getUser_id());
        if(null == sgId || "".equals(sgId)){
        	return 3;//�޶�ӦӶ����
        }
        //Ӷ�����˺ź�Ѻ���˺��Ƿ����
        String fundAcct02 = service.getUserFundAcct("02", user.getUser_id());
        if(null == fundAcct02 || "".equals(fundAcct02)){
           return 4;//Ѻ���˺Ų�����
        }
        String fundAcct03 = service.getUserFundAcct("03", user.getUser_id());
        if(null == fundAcct03 || "".equals(fundAcct03)){
        	    return 5;//Ӷ�����˺Ų�����
        }
        //Ӷ�����˺ź�Ѻ���˺�״̬�Ƿ�����
        String state = service.getFundAcctState(fundAcct02);
        if(!"1".equals(state)){
        	return 6;//Ѻ���˺Ų�����
        }
        String state1 = service.getFundAcctState(fundAcct03);
        if(!"1".equals(state1)){
             return 7;//Ӷ�����˺Ų�����
        }
        //Ѻ�����˺ŵĽ���Ƿ���ڿ۷ѽ��
        int acctLeft = Integer.parseInt(service.getFundAcctLeft(fundAcct02));
        if(0 == acctLeft || acctLeft - Integer.parseInt(realFee) < 0){
        return 8;//Ѻ���˺�����
        }
      /*  //Ӷ����ϸ�Ƿ����
        String scId = service.getCommMx(sgId, String.valueOf( numType ), Integer.parseInt(userArea), spa.getProvince_code(), realFee );
        if(null == scId || "".equals(scId)){
        	    return 9;//Ӷ����ϸ������
        }
        */
        request.setAttribute("balance", acctLeft);             // �˻����\   ʵ�ʿ۷ѽ��\Ӧ�����\  ��������\  ��������\  ������Ϣ
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
	            request.setAttribute("mess", "��¼�������ڣ������µ�¼��");
	            return state;
	        }
	        if(null==request.getParameter("flag")&&(userForm.getRoleType().equals("0")||userForm.getRoleType().equals("1"))){
				request.setAttribute("mess", "�뵽����ƽ̨���е�½");
				return -3;
	        }
	        
	        if (null != userForm.getMacflag() && Integer.parseInt(userForm.getMacflag()) == 0) {
	        	String ip = MacUtil.getIpAddr(request);
				String localMacAddr = MacUtil.getMACAddress(ip);
				if(!localMacAddr.equals(userForm.getMac())){
					request.setAttribute("mess", "��ָ�����Ե�½");
					return -3;
				}
			}
	        if(null != userForm.getShortflag() && !"".equals(userForm.getShortflag()) && Integer.parseInt(userForm.getShortflag()) == 0){
	        	String msgCode = (String)request.getSession().getAttribute(userForm.getLogin()+"-login");
	        	if(!inputMsgCode.equals(msgCode)){
	        		request.setAttribute("mess", "������֤���������");
	        		return -3;
	        	}
	        }else if (state == -2) {
	            request.setAttribute("mess", "��¼������������µ�¼��");
	            return -3;
	        }
	        //session.setAttribute("userSession", userForm);
	        //��ѯ�û�Ȩ��
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
	        //����
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
	            request.setAttribute("mess", "��¼�������ڣ������µ�¼��");
	            return state;
	        }
	        if(null==request.getParameter("flag")&&(userForm.getRoleType().equals("0")||userForm.getRoleType().equals("1"))){
				request.setAttribute("mess", "�뵽����ƽ̨���е�½");
				return -3;
	        }
	        
//	        if (null != userForm.getMacflag() && Integer.parseInt(userForm.getMacflag()) == 0) {
//	        	String ip = MacUtil.getIpAddr(request);
//				String localMacAddr = MacUtil.getMACAddress(ip);
//				if(!localMacAddr.equals(userForm.getMac())){
//					request.setAttribute("mess", "��ָ�����Ե�½");
//					return -3;
//				}
//			}
//	        if(null != userForm.getShortflag() && !"".equals(userForm.getShortflag()) && Integer.parseInt(userForm.getShortflag()) == 0){
//	        	String msgCode = (String)request.getSession().getAttribute(userForm.getLogin()+"-login");
//	        	if(!inputMsgCode.equals(msgCode)){
//	        		request.setAttribute("mess", "������֤���������");
//	        		return -3;
//	        	}
//	        }
//	        }else if (state == -2) {
//	            request.setAttribute("mess", "��¼������������µ�¼��");
//	            return -3;
//	        }
	        //session.setAttribute("userSession", userForm);
	        //��ѯ�û�Ȩ��
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
	        //����
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
	 * ��ȡ�ʽ��˻�(����)
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
	
	//���bank
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
	 * ����ת����֤����ȡ��
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
		//��ȡ������Ϣ
		SysUserBank userBank = new SysUserBank();
		SysUserBankForm bankForm = userBank.getUserBankInfo(userForm.getUser_id());
		if("01".equals(bankForm.getUser_icard_type())){
			bankForm.setUser_icard_type("���֤");
		}else if("02".equals(bankForm.getUser_icard_type())){
			bankForm.setUser_icard_type("����֤");
		}else if("03".equals(bankForm.getUser_icard_type())){
			bankForm.setUser_icard_type("����");
		}else if("04".equals(bankForm.getUser_icard_type())){
			bankForm.setUser_icard_type("����֤");
		}else if("05".equals(bankForm.getUser_icard_type())){
			bankForm.setUser_icard_type("̨��֤");
		}else if("06".equals(bankForm.getUser_icard_type())){
			bankForm.setUser_icard_type("����֤");
		}else if("07".equals(bankForm.getUser_icard_type())){
			bankForm.setUser_icard_type("ʿ��֤");
		}else if("99".equals(bankForm.getUser_icard_type())){
			bankForm.setUser_icard_type("����֤��");
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
			request.setAttribute("mess", "�����Ч��");
			return -1;
		}else if(!pass.equals(password)){
			request.setAttribute("mess", "�����������");
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
		//��ǰʱ��
		String nowTime=Tools.getNow();
		//�̻�id
		String mid = Constant.NETPAY_MERCHANT_ID;
		//��ˮ��
		String serial = mid.substring(4,8)+""+mid.substring(mid.length()-4,mid.length())+""+NetPayUtil.getRandEight();
		request.setAttribute("serial", serial);
		//������֤��
//		String inputMsgCode = (String)request.getParameter("msgcode");
		//���׽��
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
		//���п��Ƿ��
		if(null == bankForm.getUser_bankcard() || "".equals(bankForm.getUser_bankcard())){
			request.setAttribute("mess", "δ�����п�");
//			return mapping.findForward("transnext");
			return -1;
		}
//		//��ȡ��֤��ʶ
//		SysLoginUser loginUser = new SysLoginUser();
//		SysLoginUserForm loginUserForm =  loginUser.getLoginUserInfo(userForm.getLogin());
//		if(null != loginUserForm.getFeeshortflag() && !"".equals(loginUserForm.getFeeshortflag())){
//			if("0".equals(loginUserForm.getFeeshortflag())){//��Ҫ��֤
//				String msgCode = (String)request.getSession().getAttribute(userForm.getLogin()+"-trans");
//				if(null == msgCode || "".equals(msgCode) || !inputMsgCode.equals(msgCode)){
//					request.setAttribute("mess", "������֤�����벻��ȷ");
//					return mapping.findForward("transnext");
//				}
//			}
//		}
		//ת����־ע��
		SysBankLog bankLog = new SysBankLog();
		BankLogForm blForm = new BankLogForm();
		blForm.setDealserial(serial);
		blForm.setTradeaccount(bankForm.getUser_bankcard());
		blForm.setTradetime(nowTime);
		blForm.setTradefee(feeFen);
		blForm.setTradetype("0");
		blForm.setExplain("��������ת��");
		blForm.setState("3");
		blForm.setDistime(nowTime);
		blForm.setReturnmsg("");
		blForm.setCheckmsg("");
		blForm.setQrymsg("");
		bankLog.add(blForm);
		//�˻���ϸע��
		String fundAcct = bankLog.getFundAcct(userForm.getUser_id());
		int acctLeft = Integer.parseInt(bankLog.getFundAcctLeft(fundAcct+"02"));
		FacctBillBean facct = new FacctBillBean();
		facct.setFacctTrade(fundAcct+"02", serial, bankForm.getUser_bankcard(), nowTime,
				feeFen, "37","��������ת��","0",nowTime,String.valueOf(acctLeft + Integer.parseInt(feeFen)), serial,bankForm.getUser_bankcard(),""+2);
		//��¼�˻���ϸ��Ϣ,�����˻����
		int result = bankLog.updateAcct(feeFen,facct,userForm);
		if(result == 1){
			request.setAttribute("mess", "ת��ʧ��");
//			return new ActionForward("/business/bank.do?method=transFoward");
			return -1;
		}
		//ת��
		OrderData orderData = NetPayUtil.transMoney(serial,bankForm.getUser_bankcard(), feeFen2, bankForm.getUser_icard_type(), bankForm.getUser_icard(), bankForm.getUser_name());
		String resultCode = orderData.getRespCode();
		int status = 0;//�ɹ���ʶ
		if(null != resultCode && !"".equals(resultCode)){
			if(!"000000".equals(resultCode)){
				status = 1;
			}
		}else {
			status = 1;
		}
		if(0 == status){//ת��ɹ�
			//����ת����־״̬
			bankLog.update("0", serial);
			request.setAttribute("mess", "ת��ɹ�");
//			return new ActionForward("/business/bank.do?method=transFoward");
			return 0;
		}else {//ת��ʧ��
			//�����˻����
			bankLog.update("1", serial);
			//�����˻����
			bankLog.updAccountFee(userForm,feeFen);
			//�����˻���ϸ״̬
			String tableName = "wlt_acctbill_"+nowTime.substring(2, 6);
			bankLog.updAccount(feeFen,tableName,serial);
			request.setAttribute("mess", "ת��ʧ��");
//			return new ActionForward("/business/bank.do?method=transFoward");
			return -1;
		}
	}
	
	public static int updatePass(String type, String orgPassword, SysLoginUserForm userLoginForm, HttpServletRequest request) throws SQLException
	{
		SysLoginUser user = new SysLoginUser();
		String pwd = null;
		int    rs = -3;
		if( type.equals("0") )//��¼����
		{
			pwd = user.getPass(userLoginForm);
			if(!MD5.encode(orgPassword).equals(pwd)){
				request.setAttribute("mess", "ԭ��¼���벻��ȷ!");
				return -1;
			}
			rs = user.updatePass(userLoginForm);
			
		}else if( type.equals("1")) 
		{
			pwd = user.getPassWord(userLoginForm);
			if(!orgPassword.equals(pwd)){
				request.setAttribute("mess", "ԭ�������벻��ȷ!");
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
        		request.setAttribute("mess", "����");
        		return -1;
        	}
        	SysUser user=new SysUser();
        	user.transChild(fromAccount, toAccount, mon, sepNo, nowTime, "", "45", sepNo);
        	request.setAttribute("mess", "ת�Ƴɹ�");
        	return 0;
        }else {
        	request.setAttribute("mess", "�ʽ��˻�������");
        	return -1;
		}
	}
	
	
	
}