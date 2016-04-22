package com.wlt.webm.business.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.dom4j.DocumentException;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.business.bean.FaacBean;
import com.wlt.webm.business.bean.JtfkBean;
import com.wlt.webm.business.bean.OrderBean;
import com.wlt.webm.business.bean.SysRebate;
import com.wlt.webm.business.form.BiProdForm;
import com.wlt.webm.business.form.ChildFaactForm;
import com.wlt.webm.business.form.JtfkForm;
import com.wlt.webm.business.form.OrderForm;
import com.wlt.webm.business.form.SysRebateForm;
import com.wlt.webm.business.service.JtfkService;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scputil.DateParser;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.TenpayUtil;


/**
 * ��ͨ����<br>
 */
public class JtfkAction extends DispatchAction
{
	
	/**
	 * ��ͨ�����ѯ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			 {
		JtfkForm jtForm = (JtfkForm) form;
		String vehicleSel = request.getParameter("vehicleSel");
		jtForm.setVehicle(vehicleSel+jtForm.getVehicle());
		List jtfkList = new ArrayList();
		try {
			jtfkList = JtfkService.jtfkQuery(jtForm);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("mess", "��ѯ����");
			return mapping.findForward("paysuccess");
		} 
		request.setAttribute("jtfkList", jtfkList);
		return mapping.findForward("success");
	}
	/**
	 * ֧������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward payOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JtfkForm jtForm = (JtfkForm) form;
//		if(1 == 1){
//			request.setAttribute("mess", "�޶�Ӧ�ۿ���");
//			return new ActionForward("/business/weizhangdetail.jsp?"+jtForm.getDetailUrl());
//		}
		HttpSession session = request.getSession();
		SysUserForm userForm = (SysUserForm) session.getAttribute("userSession");
		//�����ۿ���
		SysRebate sr = new SysRebate();
		SysRebateForm sRebate = sr.getRebateByRole(null, "3");
		if(null == sRebate || null == sRebate.getSc_traderpercent() || "".equals(sRebate.getSc_traderpercent())){
			request.setAttribute("mess", "�޶�Ӧ�ۿ���");
			return mapping.findForward("paysuccess");
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
			return mapping.findForward("paysuccess");
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
		ordForm.setUsername(userForm.getUsername());
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
			return mapping.findForward("paysuccess");
		}
		if(null == resultMap || null == resultMap.get("SpRetCode") || "".equals(resultMap.get("SpRetCode")) || !"0000".equals(resultMap.get("SpRetCode"))){
			//���¶���״̬
			service.updOrderState("wlt_orderForm_"+nowTime.substring(2, 6), ordForm.getTradeserial());
			request.setAttribute("mess", "�¶���ʧ��");
			return mapping.findForward("paysuccess");
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
			return mapping.findForward("paysuccess");
		}
		if(null == payMap || null == payMap.get("SpRetCode") || "".equals(payMap.get("SpRetCode")) || !"0000".equals(payMap.get("SpRetCode"))){
			//�����ʽ��˻����˻���ϸ---��ͨ����
			service.updAccountJtfk(Integer.parseInt(actFee)-Integer.parseInt(actFee)*(Integer.parseInt(rebatePercent))/100, userForm.getUser_id(), "wlt_acctbill_"+nowTime.substring(2, 6), ordForm.getTradeserial());
			request.setAttribute("mess", "֧��ʧ��");
			return mapping.findForward("paysuccess");
		}
		
		request.setAttribute("mess", "֧���ɹ�");
		return mapping.findForward("paysuccess");
	}
	/**
	 * ��ͨ����ص�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward reOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String date =Tools.getNow3();
		Log.info("��ͨ����ص�reOrder"+date);
		String cftTransId = request.getParameter("CftTransId");
		String spTransId = request.getParameter("SpTransId");
		String violationId = request.getParameter("ViolationId");
		String dealStatus = request.getParameter("DealStatus");
		String dealFileUrl = request.getParameter("DealFileUrl");
		String state = "";
		JtfkBean jBean = new JtfkBean();
//		if("1".equals(dealStatus)){
//			state = "0";
//		}else {
//			state = "1";
//		}
		Log.info("��ͨ����ص�reOrder"+cftTransId+"-"+dealStatus+"-"+violationId);
		String tableName = jBean.getOrderTabName(cftTransId, violationId);
		JtfkService.updateOrderStatus(cftTransId, tableName, dealStatus);
		return null;
	}
}