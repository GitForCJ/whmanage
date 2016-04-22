package com.wlt.webm.business.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mpi.client.data.OrderData;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.AccountInfo.Jqh;
import com.wlt.webm.AccountInfo.scdx;
import com.wlt.webm.business.NetPayUtil;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.business.bean.LkalBusiness;
import com.wlt.webm.business.bean.OrderBean;
import com.wlt.webm.business.bean.SysBankLog;
import com.wlt.webm.business.bean.TyPortPayBean;
import com.wlt.webm.business.bean.dhst.QueryResultBean;
import com.wlt.webm.business.bean.lianlian.LianlianFlows;
import com.wlt.webm.business.bean.lkal.DealInfo;
import com.wlt.webm.business.bean.lkal.DealResponse;
import com.wlt.webm.business.bean.lkal.Mposinfo;
import com.wlt.webm.business.bean.sikong.Result;
import com.wlt.webm.business.bean.unicom3gquery.HttpFillOP;
import com.wlt.webm.business.form.BankLogForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.message.YMmessage;
import com.wlt.webm.pccommon.UniqueNo;
import com.wlt.webm.rights.bean.SysLoginUser;
import com.wlt.webm.rights.bean.SysUser;
import com.wlt.webm.rights.bean.SysUserBank;
import com.wlt.webm.rights.form.SysLoginUserForm;
import com.wlt.webm.rights.form.SysUserBankForm;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.scputil.MD5Util;
import com.wlt.webm.scputil.bean.FacctBillBean;
import com.wlt.webm.tool.FloatArith;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.MD5;
import com.wlt.webm.util.TenpayUtil;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * ת�����<br>
 */
public class NetPayAction extends DispatchAction {

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward transFoward(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		// ��ȡ������Ϣ
		SysUserBank userBank = new SysUserBank();
		SysUserBankForm bankForm = userBank.getUserBankInfo(userForm
				.getUser_id());
		if ("01".equals(bankForm.getUser_icard_type())) {
			bankForm.setUser_icard_type("���֤");
		} else if ("02".equals(bankForm.getUser_icard_type())) {
			bankForm.setUser_icard_type("����֤");
		} else if ("03".equals(bankForm.getUser_icard_type())) {
			bankForm.setUser_icard_type("����");
		} else if ("04".equals(bankForm.getUser_icard_type())) {
			bankForm.setUser_icard_type("����֤");
		} else if ("05".equals(bankForm.getUser_icard_type())) {
			bankForm.setUser_icard_type("̨��֤");
		} else if ("06".equals(bankForm.getUser_icard_type())) {
			bankForm.setUser_icard_type("����֤");
		} else if ("07".equals(bankForm.getUser_icard_type())) {
			bankForm.setUser_icard_type("ʿ��֤");
		} else if ("99".equals(bankForm.getUser_icard_type())) {
			bankForm.setUser_icard_type("����֤��");
		}

		String ka = bankForm.getUser_bankcard();
		if (null != ka && !"".equals(ka)) {
			String k = ka.substring(0, 4) + "***"
					+ ka.substring(ka.length() - 3, ka.length());
			bankForm.setUser_bankcard(k);
		}
		String zj = bankForm.getUser_icard();
		if (null != zj && !"".equals(zj)) {
			String z = zj.substring(0, 4) + "***"
					+ zj.substring(zj.length() - 3, zj.length());
			bankForm.setUser_icard(z);
		}

		request.setAttribute("bankinfo", bankForm);
		if (null != request.getParameter("mess")
				&& !"".equals(request.getParameter("mess"))) {
			request.setAttribute("mess", request.getParameter("mess"));
		}
		return mapping.findForward("transforwad");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward transNext(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fee = (String) request.getParameter("fee");
		String password = (String) request.getParameter("password");
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		SysUserBank userBank = new SysUserBank();
		SysUserBankForm bankForm = userBank.getUserBankInfo(userForm
				.getUser_id());
		// ��ȡ��֤��ʶ
		SysLoginUser loginUser = new SysLoginUser();
		SysLoginUserForm loginUserForm = loginUser.getLoginUserInfo(userForm
				.getUsername());

		request.setAttribute("loginUserForm", loginUserForm);
		request.setAttribute("bankinfo", bankForm);
		request.setAttribute("fee", fee);
		SysUser sysUser = new SysUser();
		String pass = sysUser.getPassWord(userForm.getUser_id());
		// System.out.println("mima--" + password + "--" + password + "--" +
		// fee+ "==="+pass+"=====" + userForm.getUser_id());
		if (null == fee || "".equals(fee)) {
			request.setAttribute("mess", "�����Ч��");
			return mapping.findForward("transforwad");
		} else if (!pass.equals(password)) {
			request.setAttribute("mess", "�����������");
			return mapping.findForward("transforwad");
		}
		return mapping.findForward("transnext");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward trans(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// ��ǰʱ��
		String nowTime = Tools.getNow();
		// �̻�id
		String mid = Constant.NETPAY_MERCHANT_ID;
		// ��ˮ��
		String serial = mid.substring(4, 8) + ""
				+ mid.substring(mid.length() - 4, mid.length()) + ""
				+ NetPayUtil.getRandEight();
		// ������֤��
		String inputMsgCode = (String) request.getParameter("msgcode");
		// ���׽��
		String feeYuan = (String) request.getParameter("fee");
		request.setAttribute("fee", feeYuan);
		String feeFen2 = Tools.yuanToFen(feeYuan);
		String feeFen = feeFen2;

		// int fee=Integer.parseInt(feeFen);
		// if(fee<100000){
		// fee=fee-100;
		// }
		// feeFen=fee+"";
		//		

		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		SysUserBank userBank = new SysUserBank();
		SysUserBankForm bankForm = userBank.getUserBankInfo(userForm
				.getUser_id());
		// ���п��Ƿ��
		if (null == bankForm.getUser_bankcard()
				|| "".equals(bankForm.getUser_bankcard())) {
			request.setAttribute("mess", "δ�����п�");
			return mapping.findForward("transnext");
		}
		// ��ȡ��֤��ʶ
		SysLoginUser loginUser = new SysLoginUser();
		SysLoginUserForm loginUserForm = loginUser.getLoginUserInfo(userForm
				.getUsername());
		if (null != loginUserForm.getFeeshortflag()
				&& !"".equals(loginUserForm.getFeeshortflag())) {
			if ("0".equals(loginUserForm.getFeeshortflag())) {// ��Ҫ��֤
				String msgCode = (String) request.getSession().getAttribute(
						userForm.getUsername() + "-trans");
				if (null == msgCode || "".equals(msgCode)
						|| !inputMsgCode.equals(msgCode)) {
					request.setAttribute("mess", "������֤�����벻��ȷ");
					return mapping.findForward("transnext");
				}
			}
		}
		// ת����־ע��
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
		// �˻���ϸע��
		String fundAcct = bankLog.getFundAcct(userForm.getUser_id());
		int acctLeft = Integer.parseInt(bankLog
				.getFundAcctLeft(fundAcct + "02"));
		FacctBillBean facct = new FacctBillBean();
		facct.setFacctTrade(fundAcct + "02", serial, bankForm
				.getUser_bankcard(), nowTime, feeFen, "37", "��������ת��", "0",
				nowTime, String.valueOf(acctLeft + Integer.parseInt(feeFen)),
				serial, bankForm.getUser_bankcard(), "" + 2);
		// ��¼�˻���ϸ��Ϣ,�����˻����
		int result = bankLog.updateAcct(feeFen, facct, userForm);
		if (result == 1) {
			request.setAttribute("mess", "ת��ʧ��");
			return new ActionForward("/business/bank.do?method=transFoward");
		}
		// ת��
		OrderData orderData = NetPayUtil.transMoney(serial, bankForm
				.getUser_bankcard(), feeFen2, bankForm.getUser_icard_type(),
				bankForm.getUser_icard(), bankForm.getUser_name());
		String resultCode = orderData.getRespCode();
		int status = 0;// �ɹ���ʶ
		if (null != resultCode && !"".equals(resultCode)) {
			if (!"000000".equals(resultCode)) {
				status = 1;
			}
		} else {
			status = 1;
		}
		if (0 == status) {// ת��ɹ�
			// ����ת����־״̬
			bankLog.update("0", serial);
			request.setAttribute("mess", "ת��ɹ�");
			return new ActionForward("/business/bank.do?method=transFoward");
		} else {// ת��ʧ��
			// �����˻����
			bankLog.update("1", serial);
			// �����˻����
			bankLog.updAccountFee(userForm, feeFen);
			// �����˻���ϸ״̬
			String tableName = "wlt_acctbill_" + nowTime.substring(2, 6);
			bankLog.updAccount(feeFen, tableName, serial);
			request.setAttribute("mess", "ת��ʧ��");
			return new ActionForward("/business/bank.do?method=transFoward");
		}
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward sendMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			SysUserForm userForm = (SysUserForm) request.getSession()
					.getAttribute("userSession");
			String msgCode = NetPayUtil.getRandEight();
			String phone = userForm.getPhone();
			if (null != phone && !"".equals(phone)) {
				Log.info(phone + " ��½��֤��:" + msgCode);
				if (YMmessage.ymSendSMS(phone, msgCode)) {
					request.getSession().setAttribute(
							userForm.getUsername() + "=dl", msgCode);
					printWriter.print(0);
					printWriter.flush();
					printWriter.close();
				} else {
					printWriter.print(1);
					printWriter.flush();
					printWriter.close();
				}
			} else {
				printWriter.print(1);
				printWriter.flush();
				printWriter.close();
			}
		} catch (IOException e) {
			printWriter.print(1);
			printWriter.flush();
			printWriter.close();
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �۸���
	 * 
	 * @param maping
	 * @param form
	 * @param request
	 * @param response
	 * @return json����
	 * @throws IOException
	 * @throws IOException
	 * @throws Exception
	 */
	public ActionForward hfkFill(ActionMapping maping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String seqNo = Tools.getNow3().substring(2) + Tools.buildRandom(5)
				+ UniqueNo.getInstance().getNoTwo();
		Log.info("  " + seqNo);
		StringBuffer sf = new StringBuffer();
		String sqp = "";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					request.getInputStream(), "UTF-8"));
			String lines;
			sf.delete(0, sf.length());
			while ((lines = reader.readLine()) != null) {
				sf.append(lines);
			}
			Log.info(seqNo + " hfk:"
					+ URLDecoder.decode(sf.toString(), "ISO-8859-1"));
			if (sf.toString().length() <= 0) {
				PrintWriter out = response.getWriter();
				out.println(getResult("0005", new String("data is null"
						.getBytes(), "UTF-8"), seqNo));
				out.flush();
				out.close();
				return null;
			}
			Map map = json2Map(sf.toString());
			String key = "OFCARD";
			// String key = "tTwoGNSHxu5472qqRIpv";
			String THIRD_ORDER_ID = (String) map.get("THIRD_ORDER_ID");
			sqp = THIRD_ORDER_ID;
			String AMOUT = (String) map.get("AMOUT");
			String PHONE = (String) map.get("PHONE");
			String PAY_DATE = (String) map.get("PAY_DATE");
			String CARD_TYPE = (String) map.get("CARD_TYPE");
			String VERIFY_STRING = (String) map.get("VERIFY_STRING");
			String signSource = "KEY=" + key + "&THIRD_ORDER_ID="
					+ THIRD_ORDER_ID

					+ "&AMOUT=" + AMOUT + "&PHONE=" + PHONE + "&KEY=" + key;
			if (!(MD5.encode(signSource).equals(VERIFY_STRING))) {
				System.out.println("�۸�����Ϣ��֤ʧ��");
				PrintWriter out = response.getWriter();
				out.println(getResult("0001", new String(
						"sign verification fail".getBytes(), "UTF-8"), seqNo));
				out.flush();
				out.close();
				return null;
			}
			int n = SysBankLog.hfkAcctT1(THIRD_ORDER_ID, seqNo, FloatArith
					.yun2li(AMOUT), PHONE, PAY_DATE, Tools.getNow(), CARD_TYPE);

			PrintWriter out = response.getWriter();
			if (0 == n) {
				out.println(getResult("0000", new String("success".getBytes(),
						"UTF-8"), THIRD_ORDER_ID));
			} else {
				out.println(getResult("0002", new String("other fail"
						.getBytes(), "UTF-8"), THIRD_ORDER_ID));
			}
			out.flush();
			out.close();
			Log.info("jieshu ");
		} catch (Exception e) {
			PrintWriter out = response.getWriter();
			out.println(getResult("0004", new String("other fail".getBytes(),
					"UTF-8"), sqp));
			out.flush();
			out.close();
			Log.error("hfk:" + e.toString());
		}
		return null;
	}

	/**
	 * 
	 * @param code
	 * @param msg
	 * @param data
	 * @return
	 */
	public String getResult(String code, String msg, String data) {

		return "{RETURN_CODE:\"" + code + "\",RETURN_MESSAGE:\"" + msg
				+ "\",DATA:{ ORDER_ID:\"" + data + "\"" + "}}";
	}

	/**
	 * 
	 * @param jsonStr
	 * @return
	 */
	public Map json2Map(String jsonStr) {
		Map map = new HashMap();
		String rs = jsonStr.replaceAll("[{}\"]", "");
		String[] ls = rs.split(",");
		for (int i = 0; i < ls.length; i++) {
			String[] str = ls[i].split(":");
			if (str.length == 1) {
				map.put(str[0], " ");
			} else {
				map.put(str[0], str[1].trim());
			}
		}
		return map;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward phoneValidata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String inputMsgCode = request.getParameter("msgcode");
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		String msgCode = (String) request.getSession().getAttribute(
				userForm.getUsername() + "=trans");
		if (null == msgCode || "".equals(msgCode)
				|| !inputMsgCode.equals(msgCode)) {
			request.setAttribute("mess", "������֤�벻��ȷ");
			return new ActionForward("/business/phoneValidata.jsp");
		} else {
			String sql = "update sys_user set user_activate=0 where user_login='"
					+ userForm.getUsername() + "'";
			DBService db = null;
			try {
				db = new DBService();
				db.update(sql);
			} finally {
				if (null != db)
					db.close();
			}
			return new ActionForward("/rights/wltframe.jsp");
		}
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward dengluValidata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String inputMsgCode = request.getParameter("msgcode");
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		String msgCode = (String) request.getSession().getAttribute(
				userForm.getUsername() + "=denglu");
		if (null == msgCode || "".equals(msgCode)
				|| !inputMsgCode.equals(msgCode)) {
			request.setAttribute("mess", "������֤�벻��ȷ");
			return new ActionForward("/business/deluphoneValidata.jsp");
		} else {
			return new ActionForward("/rights/wltframe.jsp");
		}
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward phsendMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			SysUserForm userForm = (SysUserForm) request.getSession()
					.getAttribute("userSession");
			String msgCode = NetPayUtil.getRandEight();
			String phone = userForm.getPhone();
			if (null != phone && !"".equals(phone)) {
				Log.info(phone + "  �˻�������֤��:" + msgCode);
				if (YMmessage.ymSendSMS(phone, msgCode)) {
					request.getSession().setAttribute(
							userForm.getUsername() + "=jh", msgCode);
					printWriter.print(0);
					printWriter.flush();
					printWriter.close();
				} else {
					printWriter.print(1);
					printWriter.flush();
					printWriter.close();
				}
			} else {
				printWriter.print(1);
				printWriter.flush();
				printWriter.close();
			}
		} catch (IOException e) {
			printWriter.print(1);
			printWriter.flush();
			printWriter.close();
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward userBank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String loginname = request.getParameter("loginname");
		System.out.println("loginname===" + loginname);
		SysUser user = new SysUser();
		List list = user.getSysUserBankList1(request.getSession(), loginname);
		request.setAttribute("userList", list);
		return new ActionForward("/rights/chaxunwltuserbanklist.jsp");
	}

	/**
	 * �����ֻص�
	 * 
	 * @param maping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward slsReturn(ActionMapping maping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String UserID = request.getParameter("UserID");
		String OrderNo = request.getParameter("OrderNo");
		String Phone = request.getParameter("Phone");
		String Money = request.getParameter("Money");
		String Result = request.getParameter("Result");
		String ErrCode = request.getParameter("ErrCode");
		System.out.println("�����ֻص�OrderNo:" + OrderNo + "  Phone:" + Phone
				+ "  Result:" + Result + "  ErrCode:" + ErrCode);
		PrintWriter out = response.getWriter();
		out.println("OK");
		out.flush();
		out.close();
		return null;
	}

	/**
	 * ����ص�
	 * 
	 * @param maping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws IOException
	 */
	public ActionForward xzReturn(ActionMapping maping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// StringBuffer sf = new StringBuffer();
		// try{
		// BufferedReader reader = new BufferedReader(
		// new InputStreamReader(request.getInputStream(),
		// "UTF-8"));
		// String lines;
		// while ((lines = reader.readLine()) != null) {
		// sf.append(lines);
		// }
		// System.out.println("�׿��ݻص�:"+sf.toString());
		// }catch (Exception e) {
		// System.out.println("�׿��ݻص��쳣 :"+e.toString());
		// }
		// Log.info("�׿��ۻص�...�̵߳ȴ�10�룬��");
		// try{
		// Thread.sleep(10000);
		// Log.info("�׿��ۻص�...��ʼ����ҵ�񣬣�");
		// }catch(Exception ex){
		// Log.info("�׿��ۻص�...���̵߳ȴ��쳣��"+ex);
		// }
		String ptOrderNo = request.getParameter("ptOrderNo");
		String status = request.getParameter("status");
		String sign = request.getParameter("sign");
		Log.info("�׿��ۻص�...������:" + ptOrderNo + "    status:" + status
				+ "    sign:" + sign);
		if (null == ptOrderNo || null == status || null == sign) {
			return null;
		} else {
			int st = status.equals("ORDER_SUCCESS") ? 0 : -1;
			OrderBean.httpReturnDeal(st, ptOrderNo, 1);
		}
		PrintWriter out = response.getWriter();
		out.println(getXmlData("SUCCESS", "0000"));
		out.flush();
		out.close();
		return null;
	}

	/**
	 * @param xx
	 * @param yy
	 * @return
	 */
	public String getXmlData(String xx, String yy) {
		StringBuilder sf = new StringBuilder();
		sf
				.append("<xmp><?xml version=\"1.0\" encoding=\"UTF-8\"?><response><handleResult>"
						+ xx
						+ "</handleResult><failedCode>"
						+ yy
						+ "</failedCode><failedReason></failedReason></response>");
		return sf.toString();
	}

	/**
	 * ���п���Ϣ��
	 * 
	 * @param maping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward netpaySubmit(ActionMapping maping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (null == request.getSession().getAttribute("userSession")) {
			request.setAttribute("mess", "��¼��ʱ,�����µ�¼");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		SysUserForm loginUser = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		String UserName = request.getParameter("UserName");
		String CertType = request.getParameter("CertType");
		String CertId = request.getParameter("CertId");
		String OpenBankId = request.getParameter("OpenBankId");
		String CardType = request.getParameter("CardType");
		String CardNo = request.getParameter("CardNo");
		if (null == UserName || null == CertId || null == CardNo) {
			request.setAttribute("mess", "��Ϣ������");
			return new ActionForward("/bank/userBankBindingInput.jsp");
		} else {
			DBService db = null;
			try {
				db = new DBService();
				int jieji = db
						.getInt("select count(*) from wht_netpay where bankflag in(0,1) and userno='"
								+ loginUser.getUserno() + "'");
				int xiny = db
						.getInt("select count(*) from wht_netpay where bankflag=2 and userno='"
								+ loginUser.getUserno() + "'");
				if (jieji >= 2
						&& (CardType.equals("0") || CardType.equals("1"))) {
					request.setAttribute("mess", "�Ѿ���2�Ž�ǿ�,��ǿ��������ܳ���2��");
					return new ActionForward("/bank/userBankBindingInput.jsp");
				} else if (xiny >= 5 && CardType.equals("2")) {
					request.setAttribute("mess", "�Ѿ���5�����ÿ�,���ÿ��������ܳ���2��");
					return new ActionForward("/bank/userBankBindingInput.jsp");
				} else {
					Object[] obj = { loginUser.getUserno(), UserName, CertType,
							CertId, OpenBankId, CardType, CardNo, 0,
							Tools.getNowDate1(), null, null };
					db.logData(12, obj, "wht_netpay");
				}
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("mess", "���ʧ��,���Ժ������");
				return new ActionForward("/bank/userBankBindingInput.jsp");
			} finally {
				if (null != db) {
					db.close();
				}
			}
		}
		request.setAttribute("mess", "��ӳɹ�");
		return new ActionForward("/bank/userBankBindingInput.jsp");
	}

	/**
	 * ����֧��
	 * 
	 * @param maping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward OnlinePayment(ActionMapping maping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (null == request.getSession().getAttribute("userSession")) {
			ActionForward forward = new ActionForward("/rights/wltlogin.jsp");
			forward.setRedirect(true);
			return forward;
		}

		// ��ȡ����ǰ�û���Ϣ
		SysUserForm userSession = (SysUserForm) request.getSession()
				.getAttribute("userSession");

		if (null == userSession.getUsername()
				|| "".equals(userSession.getUsername())
				|| null == userSession.getUserno()
				|| "".equals(userSession.getUserno())) {
			ActionForward forward = new ActionForward("/rights/wltlogin.jsp");
			forward.setRedirect(true);
			return forward;
		}

		String money = request.getParameter("order_price");
		// �ж�ת����
		if (money == null || "".equals(money)) {
			request.setAttribute("mess", "ת�����Ϊ�գ�");
			return new ActionForward("/AccountInfo/OnlinePayment.jsp");
		}
		if ((money.indexOf("-") != -1)) {
			request.setAttribute("mess", "ת�����Ϊ������");
			return new ActionForward("/AccountInfo/OnlinePayment.jsp");
		}

		TyPortPayBean ty = new TyPortPayBean();
		String orderNumber = NetPayAction.getOrderNumber();// ����������ˮ��
		ty.setORDERSEQ(orderNumber);// ORDERSEQ ������
		ty.setORDERREQTRANSEQ(orderNumber);// ORDERREQTRANSEQ ������������ˮ��
		String orderTime = TenpayUtil.getCurrTime();
		ty.setORDERDATE(orderTime);// ORDERDATE ��������
		int orderMoney = (int) (Float.parseFloat(money) * 100);
		ty.setORDERAMOUNT(orderMoney);// ORDERAMOUNT �����ܽ��
		ty.setPRODUCTAMOUNT(orderMoney);// PRODUCTAMOUNT ��Ʒ���
		ty.setATTACHAMOUNT(0);// ATTACHAMOUNT ���ӽ��
		ty.setCURTYPE("RMB");// CURTYPE ����
		ty.setENCODETYPE(1);// ENCODETYPE ���ܷ�ʽ
		ty.setBANKID("UPQP");// ���б���
		ty.setBUSICODE("0001");// ҵ������
		ty.setPRODUCTID("04");// ҵ���ʶ
		ty.setTMNUM("18682022602");// �ն˺���
		ty.setCUSTOMERID("10401420");// �ͻ���ʶ
		ty.setPRODUCTDESC(URLEncoder.encode("���Ƽ����޹�˾", "UTF-8"));// ��Ʒ����
		ty.setCLIENTIP(Tools.getIpAddr(request));// ip ��ַ

		String signSource = "MERCHANTID=" + ty.getMERCHANTID() + "&ORDERSEQ="
				+ ty.getORDERSEQ() + "&ORDERDATE=" + ty.getORDERDATE()
				+ "&ORDERAMOUNT=" + ty.getORDERAMOUNT() + "&CLIENTIP="
				+ ty.getCLIENTIP() + "&KEY=" + ty.getKEY();
		String mm = MD5.encode(signSource);
		ty.setMAC(mm.toUpperCase());// MACУ����

		DBService db = new DBService();
		try {
			String insertSql = "INSERT INTO wht_transactionRecord(userLogin,userNumber,orderNumber,recordMoney,bankacct,payType,recordStatus,recordTime) "
					+ " VALUES('"
					+ userSession.getUsername()
					+ "','"
					+ userSession.getUserno()
					+ "','"
					+ orderNumber
					+ "','"
					+ orderMoney
					+ "','"
					+ orderNumber
					+ "',2,0,'"
					+ orderTime
					+ "')";
			db.update(insertSql);
			Log.info("����֧���ӿڴ��붩��" + insertSql);
		} catch (Exception ex) {
			Log.error("����֧���ӿڴ��붩���쳣" + ex);
			request.setAttribute("mess", "ϵͳ�쳣��");
			return new ActionForward("/AccountInfo/OnlinePayment.jsp");
		} finally {
			if (db != null)
				db.close();
		}
		// ����url
		StringBuffer requestUrl = new StringBuffer(
				"https://webpaywg.bestpay.com.cn/payWebDirect.do");
		requestUrl.append("?");
		requestUrl.append("MERCHANTID=" + ty.getMERCHANTID());
		requestUrl.append("&ORDERSEQ=" + ty.getORDERSEQ());
		requestUrl.append("&ORDERREQTRANSEQ=" + ty.getORDERREQTRANSEQ());
		requestUrl.append("&ORDERDATE=" + ty.getORDERDATE());
		requestUrl.append("&ORDERAMOUNT=" + ty.getORDERAMOUNT());
		requestUrl.append("&PRODUCTAMOUNT=" + ty.getPRODUCTAMOUNT());
		requestUrl.append("&ATTACHAMOUNT=" + ty.getATTACHAMOUNT());
		requestUrl.append("&CURTYPE=" + ty.getCURTYPE());
		requestUrl.append("&ENCODETYPE=" + ty.getENCODETYPE());
		requestUrl.append("&MERCHANTURL=" + ty.getMERCHANTURL());
		requestUrl.append("&BACKMERCHANTURL=" + ty.getBACKMERCHANTURL());
		requestUrl.append("&BANKID=" + ty.getBANKID());// ���б���
		requestUrl.append("&BUSICODE=" + ty.getBUSICODE());
		requestUrl.append("&PRODUCTID=" + ty.getPRODUCTID());
		requestUrl.append("&TMNUM=" + ty.getTMNUM());
		requestUrl.append("&CUSTOMERID=" + ty.getCUSTOMERID());
		requestUrl.append("&PRODUCTDESC=" + ty.getPRODUCTDESC());
		requestUrl.append("&MAC=" + ty.getMAC());
		requestUrl.append("&CLIENTIP=" + ty.getCLIENTIP());
		request.setAttribute("requestUrl", requestUrl.toString());
		request.setAttribute("moeny", money);
		return new ActionForward("/AccountInfo/OnlinePaymentAccount.jsp");
	}

	/**
	 * ��֧������ƽ̨�ӿ� ���� ������������
	 * 
	 * @param maping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward tyPortPay(ActionMapping maping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (null == request.getSession().getAttribute("userSession")) {
			ActionForward forward = new ActionForward("/rights/wltlogin.jsp");
			forward.setRedirect(true);
			return forward;
		}

		// ��ȡ����ǰ�û���Ϣ
		SysUserForm userSession = (SysUserForm) request.getSession()
				.getAttribute("userSession");

		if (null == userSession.getUsername()
				|| "".equals(userSession.getUsername())
				|| null == userSession.getUserno()
				|| "".equals(userSession.getUserno())) {
			ActionForward forward = new ActionForward("/rights/wltlogin.jsp");
			forward.setRedirect(true);
			return forward;
		}

		String money = request.getParameter("order_price");
		// �ж�ת����
		if (money == null || "".equals(money)) {
			request.setAttribute("mess", "ת�����Ϊ�գ�");
			return new ActionForward("/AccountInfo/TyPortPay.jsp");
		}
		if ((money.indexOf("-") != -1)) {
			request.setAttribute("mess", "ת�����Ϊ������");
			return new ActionForward("/AccountInfo/TyPortPay.jsp");
		}

		TyPortPayBean ty = new TyPortPayBean();
		String orderNumber = NetPayAction.getOrderNumber();// ����������ˮ��
		ty.setORDERSEQ(orderNumber);// ORDERSEQ ������
		ty.setORDERREQTRANSEQ(orderNumber);// ORDERREQTRANSEQ ������������ˮ��
		String orderTime = TenpayUtil.getCurrTime();
		ty.setORDERDATE(orderTime);// ORDERDATE ��������
		int orderMoney = (int) (Float.parseFloat(money) * 100);
		ty.setORDERAMOUNT(orderMoney);// ORDERAMOUNT �����ܽ��
		ty.setPRODUCTAMOUNT(orderMoney);// PRODUCTAMOUNT ��Ʒ���
		ty.setATTACHAMOUNT(0);// ATTACHAMOUNT ���ӽ��
		ty.setCURTYPE("RMB");// CURTYPE ����
		ty.setENCODETYPE(1);// ENCODETYPE ���ܷ�ʽ
		ty.setBUSICODE("0001");// ҵ������
		ty.setPRODUCTID("04");// ҵ���ʶ
		ty.setTMNUM("18682022602");// �ն˺���
		ty.setCUSTOMERID("10401420");// �ͻ���ʶ
		ty.setPRODUCTDESC(URLEncoder.encode("���Ƽ����޹�˾", "UTF-8"));// ��Ʒ����
		ty.setCLIENTIP(Tools.getIpAddr(request));
		String signSource = "MERCHANTID=" + ty.getMERCHANTID() + "&ORDERSEQ="
				+ ty.getORDERSEQ() + "&ORDERDATE=" + ty.getORDERDATE()
				+ "&ORDERAMOUNT=" + ty.getORDERAMOUNT() + "&CLIENTIP="
				+ ty.getCLIENTIP() + "&KEY=" + ty.getKEY();
		String mm = MD5.encode(signSource);
		ty.setMAC(mm.toUpperCase());// MACУ����

		DBService db = new DBService();
		try {
			String insertSql = "INSERT INTO wht_transactionRecord(userLogin,userNumber,orderNumber,recordMoney,bankacct,payType,recordStatus,recordTime) "
					+ " VALUES('"
					+ userSession.getUsername()
					+ "','"
					+ userSession.getUserno()
					+ "','"
					+ orderNumber
					+ "','"
					+ orderMoney
					+ "','"
					+ orderNumber
					+ "',1,0,'"
					+ orderTime
					+ "')";
			db.update(insertSql);
			Log.info("��֧�����ؽӿڴ��붩��" + insertSql);
		} catch (Exception ex) {
			Log.error("��֧������ƽ̨�ӿڴ��붩���쳣" + ex);
			request.setAttribute("mess", "ϵͳ�쳣��");
			return new ActionForward("/AccountInfo/TyPortPay.jsp");
		} finally {
			if (db != null)
				db.close();
		}

		// ����url
		StringBuffer requestUrl = new StringBuffer(
				"https://webpaywg.bestpay.com.cn/payWeb.do");
		requestUrl.append("?");
		requestUrl.append("MERCHANTID=" + ty.getMERCHANTID());
		requestUrl.append("&ORDERSEQ=" + ty.getORDERSEQ());
		requestUrl.append("&ORDERREQTRANSEQ=" + ty.getORDERREQTRANSEQ());
		requestUrl.append("&ORDERDATE=" + ty.getORDERDATE());
		requestUrl.append("&ORDERAMOUNT=" + ty.getORDERAMOUNT());
		requestUrl.append("&PRODUCTAMOUNT=" + ty.getPRODUCTAMOUNT());
		requestUrl.append("&ATTACHAMOUNT=" + ty.getATTACHAMOUNT());
		requestUrl.append("&CURTYPE=" + ty.getCURTYPE());
		requestUrl.append("&ENCODETYPE=" + ty.getENCODETYPE());
		requestUrl.append("&MERCHANTURL=" + ty.getMERCHANTURL());
		requestUrl.append("&BACKMERCHANTURL=" + ty.getBACKMERCHANTURL());
		requestUrl.append("&BUSICODE=" + ty.getBUSICODE());
		requestUrl.append("&PRODUCTID=" + ty.getPRODUCTID());
		requestUrl.append("&TMNUM=" + ty.getTMNUM());
		requestUrl.append("&CUSTOMERID=" + ty.getCUSTOMERID());
		requestUrl.append("&PRODUCTDESC=" + ty.getPRODUCTDESC());
		requestUrl.append("&MAC=" + ty.getMAC());
		requestUrl.append("&CLIENTIP=" + ty.getCLIENTIP());
		request.setAttribute("requestUrl", requestUrl.toString());
		request.setAttribute("moeny", money);
		return new ActionForward("/AccountInfo/TyPortAccount.jsp");
	}

	/**
	 * ���� �����ţ�������ʱ����+��λ����� ���
	 * 
	 * @return String �������
	 */
	public static String getOrderNumber() {
		return Tools.getNow3()
				+ ((char) (new Random().nextInt(26) + (int) 'A'))
				+ ((int) (Math.random() * 10000) + 10000);
	}

	/**
	 * �Ĵ����Żص�
	 * 
	 * @param maping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 */
	public ActionForward SCCallBack(ActionMapping maping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// Log.info("�Ĵ����Żص�����.�̵߳ȴ�10�룬");
		// try{
		// Thread.sleep(10000);
		// Log.info("�Ĵ����Żص�����..��ʼ����ҵ�񣬣�");
		// }catch(Exception ex){
		// Log.info("�Ĵ����Żص������̵߳ȴ��쳣��"+ex);
		// }
		String orderNo = request.getParameter("orderNo");
		String orderStatus = request.getParameter("orderStatus");
		String accNo = request.getParameter("accNo");
		String accTime = request.getParameter("accTime");
		String signMsg = request.getParameter("signMsg");

		Log.info("�Ĵ����Żص�����orderNo=" + orderNo + "    orderStatus="
				+ orderStatus + "  accNo=" + accNo + "    accTime=" + accTime
				+ "    signMsg=" + signMsg);

		if (orderNo == null || orderStatus == null || accNo == null
				|| accTime == null || signMsg == null) {
			Log.info("�Ĵ����Żص� ȱ�ٲ���,,,,,");
			return null;
		}
		String s = "orderNo=" + orderNo + "&orderStatus=" + orderStatus
				+ "&accNo=" + accNo + "&accTime=" + accTime + scdx.key;
		String ss = MD5Util.MD5Encode(s, "GB2312");
		if (!ss.equals(signMsg)) {
			Log.info("�Ĵ����Żص�ǩ��ʧ��,,,,,,,");
			return null;
		}
		PrintWriter out = response.getWriter();
		out.write("orderStatus=0");

		int st = orderStatus.equals("0") ? 0 : -1;
		OrderBean.httpReturnDeal(st, orderNo, 6);

		out.flush();
		out.close();
		return null;
	}

	/**
	 * ������Ѹ�ص�
	 * 
	 * @param maping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 */
	public ActionForward YXCallBack(ActionMapping maping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Log.info("������Ѹ �ص�������");
		String cpid = request.getParameter("cpid");// CP �� ID
		String trade_type = request.getParameter("trade_type");// ��������
		String operator = request.getParameter("operator");// ������Ӫ��
		String charge_time = request.getParameter("charge_time");// ����ʱ��
		String mobile_num = request.getParameter("mobile_num");// �ֻ�����
		String cp_order_no = request.getParameter("cp_order_no");// CP ��������
		String amount = request.getParameter("amount");// ��ֵ���
		String status = request.getParameter("status");// �ɷѽ��
		String sign = request.getParameter("sign");// ǩ���ֶ�

		Log.info("������Ѹ �ص�������cp_order_no=" + cp_order_no + "status=" + status
				+ "mobile_num=" + mobile_num + "charge_time=" + charge_time
				+ "sign=" + sign);
		PrintWriter out = response.getWriter();
		if (cp_order_no == null || status == null) {
			Log.info("������Ѹ�ص� ȱ�ٲ���,,,,,");
			out.write("cpid=" + cpid + "&cp_order_no=" + cp_order_no
					+ "&ret_result=1111");
			return null;
		}
		out.write("cpid=" + cpid + "&cp_order_no=" + cp_order_no
				+ "&ret_result=0000");

		int st = status.equals("SUCCESS") ? 0 : -1;
		OrderBean.httpReturnDeal(st, cp_order_no, 8);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * �����ص�
	 * 
	 * @param maping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 */
	public ActionForward GuanMingCallBack(ActionMapping maping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// Log.info("�����ص�������.�̵߳ȴ�10�룬");
		// try{
		// Thread.sleep(10000);
		// Log.info("�����ص�������..��ʼ����ҵ�񣬣�");
		// }catch(Exception ex){
		// Log.info("�����ص��������̵߳ȴ��쳣��"+ex);
		// }
		String retcode = request.getParameter("retcode");// �������: 1����ɹ�; 0����ʧ��;
		// ��������Ϊ�쳣���
		String username = request.getParameter("username");// �˲���Ϊ�̻���ƽ̨���û���
		String gameapi = request.getParameter("gameapi"); // ��Ϸ�ӿڱ���
		String sporderid = request.getParameter("sporderid");// ������
		String money = request.getParameter("money");// ���
		String sign = request.getParameter("sign");

		Log.info("���� �ص�������sporderid=" + sporderid + "retcode=" + retcode
				+ "sign=" + sign);
		// PrintWriter out = response.getWriter();
		if (retcode == null || sporderid == null) {
			Log.info("�����ص� ȱ�ٲ���,,,,,");
			return null;
		}
		int st = retcode.equals("1") ? 0 : -1;
		OrderBean.httpReturnDeal(st, sporderid, 10);
		return null;
	}

	/**
	 * ���߻��˵�֧��
	 * 
	 * @param maping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward Jqhaccountpay(ActionMapping maping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (null == request.getSession().getAttribute("userSession")) {
			ActionForward forward = new ActionForward("/rights/wltlogin.jsp");
			forward.setRedirect(true);
			return forward;
		}
		// ��ȡ����ǰ�û���Ϣ
		SysUserForm userSession = (SysUserForm) request.getSession()
				.getAttribute("userSession");

		if (null == userSession.getUsername()
				|| "".equals(userSession.getUsername())
				|| null == userSession.getUserno()
				|| "".equals(userSession.getUserno())) {
			ActionForward forward = new ActionForward("/rights/wltlogin.jsp");
			forward.setRedirect(true);
			return forward;
		}

		String money = request.getParameter("fee");
		// �ж�ת����
		if (money == null || "".equals(money)) {
			request.setAttribute("mess", "�˵�����Ϊ�գ�");
			return new ActionForward("/AccountInfo/AccountPay.jsp");
		}
		if (Integer.parseInt(money) < 2 || Integer.parseInt(money) > 50) {
			request.setAttribute("mess", "�����˵������2Ԫ��50Ԫ֮�䣡");
			return new ActionForward("/AccountInfo/AccountPay.jsp");
		}
		String orderNumber = Tools.getNow3()
				+ ((int) (Math.random() * 1000) + 1000) + "s";
		String ordermoney = (Integer.parseInt(money) * 100) + "";
		DBService db = new DBService();
		try {
			String insertSql = "INSERT INTO wht_transactionRecord(userLogin,userNumber,orderNumber,recordMoney,payType,recordStatus,recordTime) "
					+ " VALUES('"
					+ userSession.getUsername()
					+ "','"
					+ userSession.getUserno()
					+ "','"
					+ orderNumber
					+ "','"
					+ ordermoney + "',3,0,'" + Tools.getNow3() + "')";
			db.update(insertSql, null);
			Log.info("���߻ݵ����˵�֧���ӿڴ��붩��" + insertSql);
		} catch (Exception ex) {
			Log.error("���߻ݵ����˵�֧���ӿڴ��붩���쳣" + ex);
			request.setAttribute("mess", "ϵͳ�쳣��");
			return new ActionForward("/AccountInfo/AccountPay.jsp");
		} finally {
			if (db != null)
				db.close();
		}
		String responseUrl = Jqh.account(orderNumber, Integer.parseInt(money));
		request.setAttribute("moeny", money);
		request.setAttribute("responseUrl", responseUrl);
		return new ActionForward("/AccountInfo/AccountPayTwo.jsp");
	}

	/**
	 * ������
	 * 
	 * @param maping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward lkal(ActionMapping maping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Log.info("receive lkl request...");
		String md5sign1 = "sz8wh3kj4tech0755xwh9whkj909tech";
		String result = "";
		String flag = "";
		String MercId = null;
		String TermId = null;
		String RefNumber = null;
		if (false) {// !Tools.getIpAddr(request).equals("127.0.0.1")
			String md5R = MD5Util.MD5Encode(MercId + TermId + RefNumber + "01"
					+ "ip not right" + md5sign1, "UTF-8");
			result = fillResponseXML(MercId, TermId, RefNumber, "01",
					"ip not right", null);
		} else {
			String Txncod = request.getParameter("Txncod");
			String RequestId = request.getParameter("RequestId");
			MercId = request.getParameter("MercId");
			TermId = request.getParameter("TermId");
			RefNumber = request.getParameter("RefNumber");
			String OrgRefNum = request.getParameter("OrgRefNum");
			String OrderID = request.getParameter("OrderID");
			String Amount = request.getParameter("Amount");
			String TransTime = request.getParameter("TransTime");
			String OrderSta = request.getParameter("OrderSta");
			String PayType = request.getParameter("PayType");
			String ExtData = request.getParameter("ExtData");
			String MD5 = request.getParameter("MD5");
			flag = RefNumber;
			Log.info("lakala:" + flag + "  status:" + OrderSta + " TermId:"
					+ TermId + " paytype:" + PayType);
			if (null == Txncod || null == RequestId || null == MercId
					|| null == TermId || null == RefNumber || null == Amount
					|| null == TransTime || null == OrderSta || null == PayType
					|| null == MD5) {
				String md5R = MD5Util.MD5Encode(MercId + TermId + RefNumber
						+ "01" + "lack of necessary parameters" + md5sign1,
						"UTF-8");
				result = fillResponseXML(MercId, TermId, RefNumber, "01",
						"lack of necessary parameters", md5R);
			} else {
				String md51 = MercId + TermId + RefNumber + OrderID + Amount
						+ TransTime + OrderSta + PayType + ExtData + md5sign1;
				String md52 = MercId + TermId + RefNumber + OrgRefNum + OrderID
						+ Amount + TransTime + OrderSta + PayType + ExtData
						+ md5sign1;
				String md5sign = (null == OrgRefNum) ? md51 : md52;
				if (!MD5Util.MD5Encode(md5sign, "UTF-8").equalsIgnoreCase(MD5)) {
					String md5R = MD5Util.MD5Encode(MercId + TermId + RefNumber
							+ "01" + "signature failure" + md5sign1, "UTF-8");
					result = fillResponseXML(MercId, TermId, RefNumber, "01",
							"signature failure", md5R);
				} else {
					String md5R = MD5Util.MD5Encode(MercId + TermId + RefNumber
							+ "00" + "success" + md5sign1, "UTF-8");
					result = fillResponseXML(MercId, TermId, RefNumber, "00",
							"success", md5R);
					Object[] obj = new Object[] { Txncod, RequestId, MercId,
							TermId, RefNumber, OrgRefNum, OrderID, Amount,
							TransTime, OrderSta, PayType, ExtData };
					new LkalBusiness(obj).start();
				}
			}
		}
		try {
			PrintWriter lkl = response.getWriter();
			lkl.print(result);
			lkl.flush();
			lkl.close();
		} catch (Exception e) {
			Log.error("lakala:" + flag + "  exception:" + e.toString());
		}
		return null;
	}

	/**
	 * �������½ӿڹ淶
	 * 
	 * @param maping
	 * @param form
	 * @param request
	 * @param response
	 *            http://shijianqiao321a.xicp.net:8888/wh/business/bank.do?method
	 *            =mlkal
	 * @return localhost:8888/wh/business/bank.do?method=mlkal
	 */
	public ActionForward mlkal(ActionMapping maping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Log.info("receive mlkl request...");
		String flag = "";
		StringBuffer sf = new StringBuffer();
		BufferedReader reader = null;
		DealResponse rs = new DealResponse();
		try {
			reader = new BufferedReader(new InputStreamReader(request
					.getInputStream(), "UTF-8"));
			String lines;
			sf.delete(0, sf.length());
			while ((lines = reader.readLine()) != null) {
				sf.append(lines);
			}

			// byte[] byteContent=new byte[2*1024];
			// int n= request.getInputStream().read(byteContent);
			// System.out.println(n);
			// System.out.println(new String(byteContent,0,n));
			// sf.append(IOUtils.toString(request.getInputStream(), "UTF-8"));
			// sf.append(request.getParameter("jsondate"));
		} catch (Exception e) {
			Log.error("��ȡmpos��Ϣ�쳣" + e.toString());
			rs.setSuccess(false);
			rs.setResultCode("FAIL");
			rs.setMessage("Abnormal");
			try {
				PrintWriter lkl = response.getWriter();
				lkl.print(JSON.toJSON(rs));
				lkl.flush();
				lkl.close();
			} catch (Exception e1) {
			}
			return null;
		}
		Log.info("receive mlakal msg:" + sf.toString());
		try {
			Mposinfo mposinfo = JSON.parseObject(sf.toString(), Mposinfo.class);
			DealInfo info = null;
			if (null != mposinfo && null != (info = mposinfo.getDealInfo())) {
				if (MemcacheConfig.getInstance().get1(info.getChannelNo())) {
					rs.setSuccess(true);
					rs.setResultCode("SUCCESS");
					rs.setMessage("Treatment success");
					Log.info("�յ��ظ���Ϣ:" + info.getChannelNo());
				} else {
					flag = info.getChannelNo();
					MemcacheConfig.getInstance().add(info.getChannelNo(), "1",
							2);
					// �������ͣ����׽��������ڣ��̻��ţ��ն˺ţ���PSIM���ţ�
					String macsource = info.getDealType()
							+ info.getDealAmount()
							+ info.getDealTime()
							+ info.getShopNo()
							+ info.getPsam()
							+ MemcacheConfig.getInstance().getMposUUID(
									"mposuuid").get(mposinfo.getIndex());
					if (!MD5Util.MD5Encode(macsource, "UTF-8")
							.equalsIgnoreCase(mposinfo.getMac())) {
						rs.setSuccess(false);
						rs.setResultCode("FAIL");
						rs.setMessage("mac check failed");
					} else {
						rs.setSuccess(true);
						rs.setResultCode("SUCCESS");
						rs.setMessage("Treatment success");
						Object[] obj = new Object[] {
								info.getChannel(),
								"01",
								info.getShopNo(),
								info.getPsam(),
								info.getChannelNo(),
								"",
								"",
								info.getDealAmount(),
								info.getDealTime(),
								info.getDealResult().equals("SUCCESS") ? "00"
										: "01", info.getDealType(),
								"mpos interface" };
						new LkalBusiness(obj).start();
					}
				}
			} else {
				rs.setSuccess(false);
				rs.setResultCode("FAIL");
				rs.setMessage("The message is not complete");
			}
		} catch (Exception e) {
			Log.error("����mpos��Ϣ�쳣:" + sf.toString() + e.toString());
			rs.setSuccess(false);
			rs.setResultCode("FAIL");
			rs.setMessage("Abnormal");
		}
		try {
			String str = JSON.toJSONString(rs);
			System.out.println("����:" + str);
			PrintWriter lkl = response.getWriter();
			lkl.print(str);
			lkl.flush();
			lkl.close();
		} catch (Exception e) {
			Log.error("mlakala:" + flag + "  exception:" + e.toString());
		}
		return null;
	}

	/**
	 * ����������״̬�ص�
	 * 
	 * @param maping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 */
	public ActionForward Lianliangoback(ActionMapping maping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Log.info("������Ʒ����ص�������");
		String partnerId = request.getParameter("partnerId");
		String streamNumber = request.getParameter("streamNumber");
		String partnerStreamNumber = request
				.getParameter("partnerStreamNumber");
		String status = request.getParameter("status");

		if (partnerId == null || "".equals(partnerId) || streamNumber == null
				|| "".equals(streamNumber) || partnerStreamNumber == null
				|| "".equals(partnerStreamNumber) || status == null
				|| "".equals(status)) {
			Log.info("�����ص����ز������㣬������partnerId=" + partnerId
					+ "    streamNumber=" + streamNumber
					+ "     partnerStreamNumber=" + partnerStreamNumber
					+ "     status=" + status);
			return null;
		}
		if ("SUCCESS".equals(status))// �ص�״̬ �����ɹ�
		{
			Log.info("�����ص� ״̬Ϊ��" + status);
			OrderBean.httpReturnDeal(0, partnerStreamNumber, 12);
			return null;
		} else// �ص�״̬ ����ʧ��
		{
			Log.info("�����ص� ״̬Ϊ��" + status);
			OrderBean.httpReturnDeal(-1, partnerStreamNumber, 12);
			return null;
		}
	}

	/**
	 * �ڿ� �ص�
	 * 
	 * @param maping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 */
	public ActionForward NineteenGoBack(ActionMapping maping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// String customer_number=request.getParameter("customer_number");
		// String ek_response=request.getParameter("ek_response");
		// String ek_serial=request.getParameter("ek_serial");
		// String ek_status=request.getParameter("ek_status");
		// String ek_time=request.getParameter("ek_time");
		// String ek_type=request.getParameter("ek_type");
		// String face_order=request.getParameter("face_order");
		// String third_order=request.getParameter("third_order");
		// String sign=request.getParameter("sign");
		// if(customer_number==null || "".equals(customer_number) ||
		// ek_response==null || "".equals(ek_response) ||
		// ek_serial==null || "".equals(ek_serial) ||
		// ek_status==null || "".equals(ek_status) ||
		// ek_time==null || "".equals(ek_time) ||
		// ek_type==null || "".equals(ek_type) ||
		// face_order==null || "".equals(face_order)||
		// third_order==null || "".equals(third_order)||
		// sign==null || "".equals(sign)){
		// Log.info("�ڿ�ص��������㣬������������");
		// return null;
		// }
		// String
		// ss="customer_number={"+customer_number+"}&ek_response={"+ek_response
		// +"}&ek_serial={"
		// +ek_serial+"}&ek_status={"+ek_status+"}&ek_time={"+ek_time
		// +"}&ek_type={"
		// +ek_type+"}&face_order={"+face_order+"}&third_order={"+third_order
		// +"}&key={"+NineteenRecharge.key+"}";
		// String str=MD5.encode(ss).toLowerCase();
		// if(!str.equals(sign)){
		// Log.info("�ڿ�ص�����ǩ�����󣬣�����ǰ�ַ�����"+ss);
		// return null;
		// }
		// Log.info("�ڿ�ص����������������");
		// PrintWriter out = response.getWriter();
		// out.write("0000");
		// out.flush();
		// out.close();
		// Log.info("�ڿ�ص�������������������0000");
		// if("111003".equals(ek_type)){//��ֵ�ص�
		// Log.info("�ڿ��ֵ�ص�,,ek_status״̬��"+ek_status);
		// int st = ek_status.equals("0") ? 0 : -1;
		//Log.info("�ڿ��ֵ�ص�,,����ҵ������,,�ص�״̬st="+st+",,,�����ţ�writeoff="+third_order
		// );
		// OrderBean.httpReturnDeal(st, third_order, 13);
		// return null;
		// }else if("111005".equals(ek_type)){//�����ص�
		// // Log.info("�ڿ�����ص�,,ek_status״̬��"+ek_status);
		// // String userno="";
		// // String tradeserial="";
		// // DBService db=null;
		// // try{
		// // db=new DBService();
		// // db.update("DELETE FROM wht_yikuai_reverse WHERE phone='"+
		// customer_number+"'");
		// // Log.info("�ڿ�����ص�,ɾ����wht_yikuai_reverse�������Ƴ������룬�ɹ���ɾ�����룺"+
		// customer_number);
		// // String
		// sss="SELECT tradeserial,userno FROM wht_orderform_"+Tools.getNow3
		// ().substring(2,6)+" WHERE writeoff='"+third_order+"'";
		// // String[] aaa=db.getStringArray(sss);
		// // if(aaa!=null && aaa.length>0){
		// // tradeserial=aaa[0];
		// // userno=aaa[1];
		// // }
		// // }catch(Exception e){
		// // Log.info("�ڿ�����ص�,ɾ����wht_yikuai_reverse�������Ƴ������룬�쳣��ɾ�����룺"+
		// customer_number+",,,,,e:"+e);
		// // return null;
		// // }finally{
		// // if(null!=db)
		// // db.close();
		// // }
		// // Log.info("�ڿ�����ص�������http�����ص�ҵ��");
		// // if(userno!=null && !"".equals(userno) && tradeserial!=null &&
		// !"".equals(tradeserial)){
		// // Log.info("�ڿ�����ص�������http�����ص�ҵ�񣬽�������˿�ҵ�������� ");
		// // if(httpReverse(userno,tradeserial)){
		// // Log.info("�ڿ�����ص�������http�����ص�ҵ���˿�ҵ������˿�ҵ����ɹ�������");
		// // }else{
		// // Log.info("�ڿ�����ص�������http�����ص�ҵ���˿�ҵ������˿�ҵ����ʧ�ܣ�����");
		// // }
		// // }else{
		// //
		// Log.info("�ڿ�����ص�������http�����ص�ҵ�񣬸���writeoffû�в�ѯ��userno,tradeserialֵ");
		// // }
		// return null;
		// }else{
		// return null;
		// }
		return null;
	}

	/**
	 * �����
	 * 
	 * @param MercId
	 * @param TermId
	 * @param RefNumber
	 * @param Result
	 * @param RspMsg
	 * @param MD5
	 * @return
	 */
	public String fillResponseXML(String MercId, String TermId,
			String RefNumber, String Result, String RspMsg, String MD5) {

		StringBuilder sf = new StringBuilder();// <xmp>
		sf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><MercId>")
				.append(MercId).append("</MercId>").append("<TermId>").append(
						TermId).append("</TermId>").append("<RefNumber>")
				.append(RefNumber).append("</RefNumber>").append("<Result>")
				.append(Result).append("</Result>").append("<RspMsg>").append(
						RspMsg).append("</RspMsg>").append("<MD5>").append(MD5)
				.append("</MD5></root>");

		return sf.toString();
	}

	public static void main(String[] args) {
		// //MD5(dealType+dealTime+dealAmount+psam+solt)
		// String macsource=
		// "0120012014091817200312300CBC4A4BF00000108285478e95284423b9df52d58371c21b3"
		// ;
		// System.out.println(MD5Util.MD5Encode(macsource,"UTF-8"));
		// System.out.println(MD5Util.MD5Encode(macsource,"UTF-8").
		// equalsIgnoreCase("15E678FAE318BD19F61A8ABB5585FAC3"));
		// System.out.println("15E678FAE318BD19F61A8ABB5585FAC3".length());
	}

	/**
	 * �����ص����� public ActionForward flowBack(ActionMapping maping, ActionForm
	 * form, HttpServletRequest request, HttpServletResponse response){ String
	 * orderid=request.getParameter("orderid"); String
	 * state=request.getParameter("state"); String
	 * sign=request.getParameter("sign");
	 * 
	 * Log.info("===================�����ص���Ϣ,������:"+orderid+",״̬:"+state);
	 * 
	 * return null; }
	 */

	/**
	 * ��֤��������
	 */
	public ActionForward validatetradePwd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrintWriter printWriter = response.getWriter();
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		DBService db = new DBService();
		String password = db
				.getString("SELECT trade_pass FROM sys_user WHERE user_no='"
						+ userForm.getUserno() + "'");
		if (password.equals(com.wlt.webm.ten.service.MD5Util.MD5Encode(request
				.getParameter("passwd"), "GBK"))) {
			printWriter.print(0);
		} else {
			printWriter.print(1);
		}
		printWriter.flush();
		printWriter.close();
		return null;
	}

	/**
	 * ����ͨ���� �ص�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward DhstFlowsBack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		StringBuffer buf = new StringBuffer();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					request.getInputStream(), "UTF-8"));
			String lines;
			buf.delete(0, buf.length());
			while ((lines = reader.readLine()) != null) {
				buf.append(lines);
			}
			Log.info("����ͨ�����ص�,����:" + buf.toString());
			if ("".equals(buf.toString().trim())) {
				Log.info("����ͨ�����ص�,����Ϊ��,return null");
				return null;
			}
			QueryResultBean bean = JSON.parseObject(buf.toString(),
					QueryResultBean.class);
			if (bean == null) {
				Log.info("����ͨ�����ص�,json to json is null,return null");
				return null;
			}
			String sendJson = "{\"resultCode\":\"0000\",\"resultMsg\":\"����ɹ���\"}";
			PrintWriter out = response.getWriter();
			out.print(sendJson);
			Flows_service_Back(bean.getClientOrderId(), bean.getStatus());
			out.flush();
			out.close();
			Log.info("����ͨ�����ص�,��Ӧ��ɣ���Ӧ���ݣ�sendJson:" + sendJson);
		} catch (Exception e) {
			Log.error("����ͨ�����ص�,ϵͳ�쳣,,ex:" + e);
		}
		return null;
	}

	/**
	 * �������� �ص�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward leliuFB(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		StringBuffer buf = new StringBuffer();
		String orderid = "";
		String phone = "";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					request.getInputStream(), "UTF-8"));
			String lines;
			buf.delete(0, buf.length());
			while ((lines = reader.readLine()) != null) {
				buf.append(lines);
			}
			System.out.println("���������ص�,����:" + buf.toString());
			if ("".equals(buf.toString().trim())) {
				Log.info("���������ص�,����Ϊ��,return null");
				return null;
			}
			HashMap<String, String> bean = JSON.parseObject(buf.toString(),
					HashMap.class);
			if (bean == null) {
				Log.info("���������ص�,json to json is null,return null");
				return null;
			}
			orderid = bean.get("order_id");
			phone = bean.get("phone_id");
			PrintWriter out = response.getWriter();
			out.print("1");
			String rs = bean.get("orderstatus");
			if ("finish".equals(rs)) {
				Flows_service_Back(bean.get("order_id"), "0");
			} else if ("fail".equals(rs)) {
				Flows_service_Back(bean.get("order_id"), "-1");
			} else {
				Log.info("����:" + orderid + "  " + phone + "  " + rs);
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			Log.error(orderid + ":" + phone + ",���������ص�,ϵͳ�쳣:" + e);
		}
		return null;
	}

	/**
	 * ���������ص�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return out
	 */
	public ActionForward lianlianBack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		Log.info("����������������ص�,,,����:" + params);
		ArrayList<String> sort = new ArrayList<String>(params.keySet());
		Collections.sort(sort);
		String md5sign = "";
		for (String key : sort) {
			if (params.get(key) != null && !"".equals(params.get(key).trim())
					&& !"method".equals(key) && !"sign".equals(key)) {
				md5sign += (key + "=" + params.get(key)) + "&";
			}
		}
		md5sign = com.wlt.webm.util.MD5.encode(md5sign.substring(0, md5sign
				.length() - 1)
				+ LianlianFlows.key);

		String sign = request.getParameter("sign");
		String order_no = request.getParameter("order_no");
		String status = request.getParameter("status");
		String stream_id = request.getParameter("stream_id");
		if (sign == null || "".equals(sign.trim()) || order_no == null
				|| "".equals(order_no.trim()) || status == null
				|| "".equals(status.trim()) || stream_id == null
				|| "".equals(stream_id.trim())) {
			Log.info("����������������ص�,��������,,����:" + params);
			return null;
		}

		if (!md5sign.equals(sign)) {
			Log.info("����������������ص�,,,������:" + order_no + ",,,ǩ������");
			return null;
		}

		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print("SUCCESS");
			Flows_service_Back(order_no, ("SUCCESS".equals(status.trim()) ? "0"
					: "1"));
			out.flush();
			out.close();
			Log.info("����������������ص�,,,������:" + order_no + ",��Ӧ��ɣ���Ӧ���ݣ�SUCCESS");
		} catch (IOException e) {
			Log.error("����������������ص�,,,������:" + order_no + ",,ϵͳ�쳣����" + e);
		}
		return null;
	}

	/**
	 * �����ص� ҵ����
	 * 
	 * @param orderid
	 *            ������
	 * @param type
	 *            ״̬ 0�ɹ� ����ʧ��
	 */
	public static void Flows_service_Back(String orderid, String type) {
		String time = Tools.getNow();
		String orderTableName = "wht_orderform_" + time.substring(2, 6);
		String acctTbaleName = "wht_acctbill_" + time.substring(2, 6);
		DBService db = null;
		try {
			db = new DBService();
			String[] strs = db
					.getStringArray("SELECT * FROM ( "
							+ "SELECT fl.o_state,fl.o_userno,'',fl.o_fee,fl.o_type FROM wht_flow_Reissue fl WHERE fl.o_tradeserial='"
							+ orderid
							+ "' "
							+ " UNION "
							+ "SELECT o.state,o.userno,o.fundacct,o.tradefee,o.phoneleft FROM "
							+ orderTableName + " o WHERE o.tradeserial='"
							+ orderid + "' " + ") AS ab LIMIT 0,1");
			if (strs == null || strs.length <= 0) {
				// �޴˶���
				Log.info("���������ص�,,,�޶�Ӧ�Ķ���,tradeserial:" + orderid);
				return;
			}
			if ("0".equals(strs[0]) || "1".equals(strs[0])) {
				// �ظ��ص�
				Log.info("���������ص�,,,�˶����ظ��ص�,tradeserial:" + orderid);
				return;
			}
			Log.info("���������ӿڻص�,��㶩����:" + orderid + ",,�ص�״̬��" + type
					+ ",0�ɹ� ����ʧ��,,������ֵ�ӿ�:" + strs[4] + ",1000��Ѷ,1001����");
			if (HttpFillOP.tencent_code.equals(strs[4])) {// ��Ѷ�ӿ�
				if ("0".equals(type)) {// �ɹ�
					Flows_Public_Method.Is_Back(orderid, "0", strs[4], null,
							null);
				} else {// ʧ��
					Flows_Public_Method.Is_Back(orderid, "1", strs[4], null,
							null);
				}
			} else if (HttpFillOP.jd_code.equals(strs[4])) {// �����ӿ�
				if ("0".equals(type)) {// �ɹ�
					Flows_Public_Method.Is_Back(orderid, "0", strs[4], null,
							null);
				} else {// ʧ��
					Flows_Public_Method.Is_Back(orderid, "1", strs[4], null,
							null);
				}
			} else {// ��ͨ�ӿڣ������
				// �ɹ�
				if ("0".equals(type)) {
					db.update("update " + orderTableName
							+ " set state=0,chgtime='" + Tools.getNow3()
							+ "' where tradeserial='" + orderid + "'");
					Log.info("���������ص�,,,�޸Ķ���״̬Ϊ�ɹ�,tradeserial:" + orderid);
				} else { // ʧ��
					String[] ars = db
							.getStringArray("select ac.tradetype from "
									+ acctTbaleName
									+ " ac where ac.tradeserial='" + orderid
									+ "'");
					Log.info("���������ص�,,,����ʧ��,�����˷ѷ���,tradeserial:" + orderid);
					new BiProd().returnDeal(time, "-1", orderid, strs[1], "",
							strs[2].substring(0, strs[2].length() - 2), "1",
							"", "", strs[3], "", Integer.parseInt(ars[0]));
				}
			}

		} catch (Exception e) {
			Log.error("���������ص�,,tradeserial:" + orderid + ",,ϵͳ�쳣��" + e);
		} finally {
			if (db != null) {
				db.close();
				db = null;
			}
		}
	}

	/**
	 * �����ֵ�ص�
	 * 
	 * @param maping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws IOException
	 */
	public ActionForward zb_back(ActionMapping maping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String orderNo = request.getParameter("tbOrderNo");
		String orderStatus = request.getParameter("coopOrderStatus");
		String bizType = request.getParameter("bizType");

		Log.info("�����ֵ�ص�,������:" + orderNo + ",,����״̬:" + orderStatus + ",,ҵ������:"
				+ bizType);

		if (orderNo == null || orderStatus == null || bizType == null
				|| "".equals(bizType.trim()) || "".equals(orderNo.trim())
				|| "".equals(orderStatus.trim())) {
			Log.info("�����ֵ�ص� ȱ�ٲ���,,,,,");
			return null;
		}
		int st = 2;
		if ("SUCCESS".equals(orderStatus)) {
			st = 0;
		} else if ("FAILED".equals(orderStatus)) {
			st = -1;
		} else {
			return null;
		}

		if ("E_CHARGE".equals(bizType)) {
			OrderBean.httpReturnDeal(st, orderNo, 40);
		} else if ("E_FLOW".equals(bizType)) {
			Flows_service_Back(orderNo, (st == 0 ? "0" : "1"));
		} else {
			Log.info("�����ֵ�ص�,������:" + orderNo + ",,����״̬:" + orderStatus
					+ ",,ҵ������:" + bizType + ",,δ֪ҵ������,������");
			return null;
		}
		PrintWriter out = response.getWriter();
		out.write("SUCCESS");
		out.flush();
		out.close();
		return null;
	}

//	/**
//	 * �㶫������
//	 * 
//	 * @param maping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	public ActionForward llfback(ActionMapping maping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws IOException {
//		String result = request.getParameter("result");
//		String order = request.getParameter("order");
//		String info = request.getParameter("info");
//		Log.info("������:"+order+"  "+result+" "+info);
//		//result	�ɹ�Ϊ1��ʧ��Ϊ0
//		
//		String st="";
//		if("1".equals(result)){
//			st="0";
//		}else if("0".equals(result)){
//			st="-1";
//		}else{
//			return null;
//		}
//		Flows_service_Back(order,st);
//		
//		return null;
//	}
	
	/**
	 * �������� �ص�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward dingxB(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String orderid=request.getParameter("orderid");
		String state=request.getParameter("state");
		String account=request.getParameter("account");
		if (null==orderid||null==state||"".equals(orderid)||"".equals(state)) {
				Log.info("���������ص�,����Ϊ��,return null");
				return null;
			}
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print("OK");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				Log.error("�����쳣:" + orderid + "  " + account + "  " + state+"\n"+e.toString());
			}
			if ("1".equals(state)||"3".equals(state)) {
				Flows_service_Back(orderid, "0");
			} else if ("2".equals(state)) {
				Flows_service_Back(orderid, "-1");
			} else {
				Log.info("����:" + orderid + "  " + account + "  " + state);
			}
		return null;
	}
	
	/**
	 * �������� �ص�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward zhiXinB(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String orderid=request.getParameter("client_order_no");
		String state=request.getParameter("recharge_status");
		String account=request.getParameter("phone_no");
		if (null==orderid||null==state||"".equals(orderid)||"".equals(state)) {
				Log.info("���������ص�,����Ϊ��,return null");
				return null;
			}
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print("SUCCESS");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				Log.error("�����쳣:" + orderid + "  " + account + "  " + state+"\n"+e.toString());
			}
			if ("2".equals(state)) {
				Flows_service_Back(orderid, "0");
			} else if ("6".equals(state)) {
				Flows_service_Back(orderid, "-1");
			} else {
				Log.info("����:" + orderid + "  " + account + "  " + state);
			}
		return null;
	}

}
