package com.wlt.webm.business.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import whmessgae.TradeMsg;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.AccountInfo.NineteenRecharge;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.business.bean.InterfaceResponseBean;
import com.wlt.webm.business.bean.OrderBean;
import com.wlt.webm.business.bean.TpBean;
import com.wlt.webm.business.bean.lechong.MobileRecharge;
import com.wlt.webm.business.bean.liansheng.Mobile_Interface;
import com.wlt.webm.business.dianx.bean.TelcomPayBean;
import com.wlt.webm.business.dianx.form.TelcomForm;
import com.wlt.webm.business.form.OrderForm;
import com.wlt.webm.business.form.TPForm;
import com.wlt.webm.db.DBConfig;
import com.wlt.webm.db.DBService;
import com.wlt.webm.gm.bean.GuanMingBean;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.message.PortalSend;
import com.wlt.webm.pccommon.Constants;
import com.wlt.webm.rights.bean.SysUser;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.ten.service.MD5Util;
import com.wlt.webm.tool.DES;
import com.wlt.webm.tool.DES2;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.PageAttribute;

/**
 * �������ӿڽ���
 * 
 * @author caiSJ
 * 
 */
public class TPcharge extends DispatchAction {
	/**
	 * ȫ����ֵ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ��תҳ��
	 * @throws Exception
	 */
	public ActionForward tzFill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ptOrderNo = request.getParameter("ptOrderNo");
		Log.info("���սӿ��̳�ֵ����:" + ptOrderNo);
		if ((!Tools.validateTime()) && (null != ptOrderNo)) {
			send2TP(fillResponseXML("FAILED", "208", "��ֵʧ��", ptOrderNo,
					ptOrderNo, Tools.getNow3(), null, null), response);
			return null;
		}
		String userSysNo = request.getParameter("userSysNo");
		String tradeType = request.getParameter("tradeType");
		String ptPayTime = request.getParameter("ptPayTime");
		String buyNum = request.getParameter("buyNum");
		String unitPrice = request.getParameter("unitPrice");
		String totalPrice = request.getParameter("totalPrice");
		String phone = request.getParameter("account");
		String notifyUrl = request.getParameter("notifyUrl");
		String merchantIP = request.getParameter("merchantIP");
		String validateOrder = request.getParameter("validateOrder");
		String sign = request.getParameter("sign");
		// �ӻ����л�ȡ�ӿ�����Ϣ
		TPForm tp = MemcacheConfig.getInstance().getTP(userSysNo);
		if (tp == null) {
			send2TP(fillResponseXML("FAILED", "108", "�����̲����ڻ���״̬������",
					ptOrderNo, null, null, null, null), response);
			return null;
		}
		// ��֤ip�����ն˺��Ƿ�Ϸ�
		String ip = Tools.getIpAddr(request);
		if (!((tp.getIp().indexOf(ip) != -1) || tp
				.getIp().indexOf(merchantIP) != -1)) {
			send2TP(fillResponseXML("FAILED", "105", "�󶨵�IP���ն˱�ŵ�ַ���Ϸ�",
					ptOrderNo, null, null, null, null), response);
			return null;
		}
		if (null == userSysNo || null == tradeType || null == ptOrderNo
				|| null == ptPayTime || null == buyNum || null == unitPrice
				|| null == totalPrice || null == phone || null == notifyUrl
				|| null == merchantIP || null == validateOrder || null == sign) {
			send2TP(fillResponseXML("FAILED", "101", "����������", ptOrderNo, null,
					null, null, null), response);
			return null;
		}
		int buynum = Integer.parseInt(buyNum);
		if (tradeType.equals("10000003") && (buynum < 100 || buynum > 1000)) {
			send2TP(fillResponseXML("FAILED", "908", "�����������ڷ�Χ��", ptOrderNo,
					null, null, null, null), response);
			return null;
		}
		if (tradeType.equals("10000001") && (buynum < 1 || buynum > 500)) {
			send2TP(fillResponseXML("FAILED", "908", "�����������ڷ�Χ��", ptOrderNo,
					null, null, null, null), response);
			return null;
		}
		if ((unitPrice.indexOf("-") != -1) || (totalPrice.indexOf("-") != -1)) {
			send2TP(fillResponseXML("FAILED", "101", "����������", ptOrderNo, null,
					null, null, null), response);
			return null;
		}
		if ((buynum * Integer.parseInt(unitPrice)) != Integer
				.parseInt(totalPrice)) {
			send2TP(fillResponseXML("FAILED", "001", "�ܼۼ�������", ptOrderNo, null,
					null, null, null), response);
			return null;
		}
		String encString = ptOrderNo + ptPayTime;
		if (tp.getUser_no().equals("0000000278")
				|| tp.getUser_no().equals("0000436959")
				|| tp.getUser_no().equals("0007611106")) {
			DES2 des2 = new DES2();
			des2.setKey(tp.getKeyvalue1());
			System.out.println(validateOrder.equals(des2
					.getEncString(encString)));
			if (!validateOrder.equals(des2.getEncString(encString))) {
				send2TP(fillResponseXML("FAILED", "109", "У�������,����ʧ��",
						ptOrderNo, null, null, null, null), response);
				return null;
			}
		} else {
			DES des = new DES();
			des.setKey(tp.getKeyvalue1());
//			System.out.println(validateOrder
//					.equals(des.getEncString(encString)));
			if (!validateOrder.equals(des.getEncString(encString))) {
				send2TP(fillResponseXML("FAILED", "109", "У�������,����ʧ��",
						ptOrderNo, null, null, null, null), response);
				return null;
			}
		}
		String signString = userSysNo + tradeType + ptOrderNo + ptPayTime
				+ buyNum + unitPrice + totalPrice + phone + tp.getKeyvalue();
		if (!MD5Util.MD5Encode(signString, "UTF-8").equals(sign)) {
			send2TP(fillResponseXML("FAILED", "102", "ǩ������,����ʧ��", ptOrderNo,
					null, null, null, null), response);
			return null;
		}

		if (tradeType.equals("10000000")) {
			BiProd biProd = new BiProd();
			String[] str = biProd.getPhoneInfo(phone.substring(0, 7));
			if (null == str) {
				send2TP(fillResponseXML("FAILED", "110", "û�ж�Ӧ���������Ϣ,����ʧ��",
						ptOrderNo, null, null, null, null), response);
				return null;
			}

			String phonePid = str[0];
			String phoneType = str[1];
			String seqNo = Tools.getStreamSerial(phone);
			String flag = "2";// �ӿ���
			double fee = Double.parseDouble(totalPrice); // FloatArith.yun2li(
			// payfee);
			DBService db = null;
			try {
				db = new DBService();
				BiProd bp = new BiProd();
				String[] result = bp.getEmploy(db, phoneType, tp.getUser_site()
						+ "", phonePid, tp.getUser_no(), flag, Integer
						.parseInt(totalPrice) / 1000, 1, Integer.parseInt(tp.getGroups()));
				if (null == result) {
					send2TP(fillResponseXML("FAILED", "111", "Ӷ����ϸ������",
							ptOrderNo, null, null, null, null), response);
					return null;
				}
				// ���ó�ֵ�ӿ�
				int k = bp.nationFill(1 + "", tp.getUser_no(), phonePid, tp
						.getUser_site()
						+ "", phoneType, phone, ptOrderNo, fee, result, tp
						.getSa_zone(), db, tp.getUser_login(), merchantIP,
						"OF", "1");
				Log.info("�������ӿڳ�ֵ������:" + ptOrderNo + " ��ֵ���:" + k);
				if (k == 0 || k >= 20) {
					if (k == 0) {
						send2TP(fillResponseXML("SUCCESS", "000", "��ֵ�ɹ�",
								ptOrderNo, ptOrderNo, Tools.getNow3(), null,
								null), response);
					} else {
						send2TP(fillResponseXML("SUCCESS", "000", "��ֵ�ɹ�",
								ptOrderNo, ptOrderNo, Tools.getNow3(), null, k
										+ ""), response);
					}
					return null;
				} else if (k == 2 || k == -2 || k == 3) {
					send2TP(fillResponseXML("FAILED", "210", "������", ptOrderNo,
							ptOrderNo, Tools.getNow3(), null, null), response);
					return null;
				} else if (k == 1 || k == -1) {
					send2TP(fillResponseXML("FAILED", "208", "��ֵʧ��", ptOrderNo,
							ptOrderNo, Tools.getNow3(), null, null), response);
					return null;
				} else if(k==-5){
					send2TP(fillResponseXML("FAILED", "201", "�˻�����", ptOrderNo,
							ptOrderNo, Tools.getNow3(), null, null), response);
					return null;	
				}else if(k==6){
					send2TP(fillResponseXML("FAILED", "502", "��ˮ���ظ�����ʧ��", ptOrderNo,
							ptOrderNo, Tools.getNow3(), null, null), response);
					return null;	
				}else {
					send2TP(fillResponseXML("FAILED", "210", "��������", ptOrderNo,
							ptOrderNo, Tools.getNow3(), null, null), response);
					return null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				send2TP(fillResponseXML("FAILED", "210", "��������", ptOrderNo,
						ptOrderNo, Tools.getNow3(), null, null), response);
				return null;
			}
		} else if (tradeType.equals("10000001") || tradeType.equals("10000003")) {// Q��
			// /
			// /
			// ֧����
			GuanMingBean gmbean = new GuanMingBean();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("num", buyNum);
			map.put("in_acct", phone);
			map.put("gameapi", tradeType.equals("10000001") ? "0005" : "0008");
			map.put("ip", merchantIP);
			map.put("flag", "1");// 1ΪֱӪ��0Ϊ��ֱӪ
			map.put("qbCommission",tp.getQbCommission());//ÿ���ӿ��̶�Ӧ��Ӷ��
			map.put("caidan",tp.getCaidan());//ÿ���ӿ��̶�Ӧ�Ĳ� ����   -- 0��  1����
			map.put("term_type","0");
			
			// 0 �ɹ� 1 ʧ�� 2�����л��쳣 3�ظ����� 4�˺����� 5Ӷ�𲻴���
			int state = gmbean.qbInterfaceDistribution(map, tp.getUser_no(),
					tp.getUser_login(), "1", tp.getSa_zone(), ptOrderNo, tp
							.getQbFlag());
			Log.info("�ӿ���Q��֧������ֵ���:" + ptOrderNo + ":" + state);
			if (state == 0) {
				send2TP(fillResponseXML("SUCCESS", "000", "��ֵ�ɹ�", ptOrderNo,
						ptOrderNo, Tools.getNow3(), null, null), response);
				return null;
			} else if (state == 1 || state == 3 || state == 4 || state == 5) {
				send2TP(fillResponseXML("FAILED", "208", "��ֵʧ��", ptOrderNo,
						ptOrderNo, Tools.getNow3(), null, null), response);
				return null;
			} else {
				send2TP(fillResponseXML("FAILED", "210", "��������", ptOrderNo,
						ptOrderNo, Tools.getNow3(), null, null), response);
				return null;
			}
		} else {
			send2TP(fillResponseXML("FAILED", "202", "��ֵ��Ʒ������", ptOrderNo,
					ptOrderNo, Tools.getNow3(), null, null), response);
			return null;
		}

	}

	/**
	 * ����ӿڳ�ֵ������Ϣ
	 * 
	 * @param resMsg
	 * @param rsCode
	 * @param failedReason
	 * @param ptOrderNo
	 * @param whtOrderNo
	 * @param payPrice
	 * @param orderSuccessTime
	 * @param result
	 * @return xml
	 */
	public String fillResponseXML(String resMsg, String rsCode,
			String failedReason, String ptOrderNo, String whtOrderNo,
			String orderSuccessTime, List<HashMap<String, String>> result,
			String price) {

		StringBuilder sf = new StringBuilder();
		sf.append(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><resMsg>")
				.append(resMsg).append("</resMsg>").append("<rsCode>").append(
						rsCode).append("</rsCode>").append("<failedReason>")
				.append(failedReason).append("</failedReason>").append(
						"<ptOrderNo>").append(ptOrderNo).append("</ptOrderNo>")
				.append("<whtOrderNo>").append(whtOrderNo).append(
						"</whtOrderNo>").append("<orderSuccessTime>").append(
						orderSuccessTime).append("</orderSuccessTime>").append(
						"<price>").append(price).append("</price>");
		if (null != result && result.size() > 0) {
			sf.append("<cards>");
			for (HashMap<String, String> rs : result) {
				sf.append("<card>").append("<cardnum>").append(
						rs.get("cardnum")).append("</cardnum>").append(
						"<cardpwd>").append(rs.get("cardpwd")).append(
						"</cardpwd>").append("<cardvalue>").append(
						rs.get("cardvalue")).append("</cardvalue>").append(
						"</card>");
			}
			sf.append("</cards>");
		}
		sf.append("</response>");
		return sf.toString();
	}

	/**
	 * ����������ͽ��
	 * 
	 * @param rs
	 * @param response
	 */
	public void send2TP(String rs, HttpServletResponse response) {
		PrintWriter out;
		try {
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			out.println(rs);
			out.flush();
			out.close();
			Log.info("��ӿ��̷��ؽ���ɹ�:" + rs);
		} catch (IOException e) {
			e.printStackTrace();
			Log.error("����������ͽ��ʧ�ܣ�" + rs + "  �ٴη���...");
			send2TP(rs, response);
		}
	}

	/**
	 * �ӿ��̳���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward interfaceRevert(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userSysNo = request.getParameter("userSysNo");
		String ptOrderNo = request.getParameter("ptOrderNo");
		String sign = request.getParameter("sign");
		if (!Tools.validateTime()) {
			send2TP(fillResponseXML("FAILED", "108", "23:50��00:10��������",
					ptOrderNo, null, null, null, null), response);
			return null;
		}
		// �ӻ����л�ȡ�ӿ�����Ϣ
		TPForm tp = MemcacheConfig.getInstance().getTP(userSysNo);
		if (tp == null) {
			send2TP(fillResponseXML("FAILED", "108", "�����̲����ڻ���״̬������",
					ptOrderNo, null, null, null, null), response);
			return null;
		}
		// ��֤ip�����ն˺��Ƿ�Ϸ�
		String ip = Tools.getIpAddr(request);
		if (!(tp.getIp().indexOf(ip) != -1)) {
			send2TP(fillResponseXML("FAILED", "105", "�󶨵�IP���ն˱�ŵ�ַ���Ϸ�",
					ptOrderNo, null, null, null, null), response);
			return null;
		}
		if (null == userSysNo || null == ptOrderNo || null == sign) {
			send2TP(fillResponseXML("FAILED", "101", "����������", ptOrderNo, null,
					null, null, null), response);
			return null;
		}

		String signString = userSysNo + ptOrderNo + tp.getKeyvalue();
		if (!MD5Util.MD5Encode(signString, "UTF-8").equals(sign)) {
			send2TP(fillResponseXML("FAILED", "102", "ǩ����֤ʧ��", ptOrderNo, null,
					null, null, null), response);
			return null;
		}

		String userid = userSysNo;
		DBService db = null;
		try {
			String time = Tools.getNow();
			db = new DBService();
			String selectStr = "select count(*) from wlt_revertlimit where userno='"
					+ userid + "'  and date='" + time.substring(0, 6) + "'";
			String sql = "select reversalcount from sys_reversalcount where tradetype=0 and user_no='"
					+ userid + "'";
			int count = db.getInt(sql);
			int k = db.getInt(selectStr);
			if (count == 0) {
				if (k >= 10) {
					send2TP(fillResponseXML("FAILED", "1001", "���³�������������,�޷�����",
							ptOrderNo, null, null, null, null), response);
					return null;
				}
			} else {
				if (k >= count) {
					send2TP(fillResponseXML("FAILED", "1001", "���³�������������,�޷�����",
							ptOrderNo, null, null, null, null), response);
					return null;
				}
			}
			OrderBean ob = new OrderBean();
			String tablename = "wht_orderform_" + time.substring(2, 6);
			OrderForm order = ob.getOrderInfotwo(ptOrderNo, tablename);
			String tradeObject = order.getTradeobject();
			String buid = order.getBuyid();
			String tradeFee = order.getFee();
			if (tradeObject == null || buid == null || tradeFee == null) {
				send2TP(fillResponseXML("FAILED", "1002", "�����ڸñʽ��׻����ѳ���24Сʱ",
						ptOrderNo, null, null, null, null), response);
				return null;
			}

			String ordid = ptOrderNo;
			String buyid = buid;
			String tradeNo = null;
			String serialNo = null;
			String seqNo = Tools.getStreamSerial("userfx"
					+ Tools.buildRandom(4));
			if (buyid.equals("3")) {// �ƶ�
				tradeNo = "06013";
				serialNo = "XY0315012880537" + Tools.getNow().substring(2)
						+ Tools.buildRandom(2) + Tools.buildRandom(3);
			} else if ("7".equals(buyid)) {
				tradeNo = "06022";// ����
				serialNo = "wh06022" + Tools.getNow3().substring(6)
						+ Tools.buildRandom(2) + Tools.buildRandom(3); // �����ƶ�
			} else if ("9".equals(buyid)) {
				tradeNo = "06025"; // ѶԴ
				serialNo = "wh06025" + Tools.getNow3().substring(6)
						+ Tools.buildRandom(2) + Tools.buildRandom(3);
			} else if (buyid.equals("4")) {// ��ͨ
				tradeNo = "06015";
				serialNo = "XY0418682022602" + Tools.getNow().substring(2)
						+ Tools.buildRandom(2) + Tools.buildRandom(3);
			} else if (buyid.equals("5")) {// ����
				tradeNo = "06012";
				serialNo = "";
			} else if (buyid.equals("13")) {// �ڿ� http ����
				// �ڿ� �ж��Ƿ��Ѿ�����������
				String yikuai_reverse = "SELECT 1 FROM wht_yikuai_reverse WHERE phone='"
						+ tradeObject
						+ "' AND TIMESTAMPDIFF(MINUTE, datetimes, '"
						+ Tools.getNow3() + "')<10 ";
				String reverses = db.getString(yikuai_reverse);
				if (reverses != null && !"".equals(reverses)) // ���������м�¼���ѷ������
				{
					Log.info("�ڿ�ʮ�������ѷ����һ�γ���������tradeObject=" + tradeObject);
					System.out.println("�ڿ�ʮ�������ѷ����һ�γ���������tradeObject="
							+ tradeObject);
					send2TP(fillResponseXML("FAILED", "1004", "����ʧ��",
							ptOrderNo, null, null, null, null), response);
					return null;
				}
				String strState = NineteenRecharge.reverseServices(ptOrderNo,
						tradeObject, (int) (Float.parseFloat(tradeFee) / 1000)
								+ "");
				if ("000".equals(strState)) {
					send2TP(fillResponseXML("SUCCESS", "907", "�ѳ���", ptOrderNo,
							null, null, null, null), response);
					return null;
				} else if ("001".equals(strState)) {
					send2TP(fillResponseXML("FAILED", "1004", "����ʧ��",
							ptOrderNo, null, null, null, null), response);
					return null;
				} else {
					send2TP(fillResponseXML("FAILED", "901", "�����쳣", ptOrderNo,
							null, null, null, null), response);
					return null;
				}
			}else if (buyid.equals("15")) {// ʡ��ͨ  ����
				tradeNo = "06027";
				serialNo = Tools.getNow()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
			}else if (buyid.equals("22")) {// ��ʢ�ƶ�����
				tradeNo = "06030";
			}else if(buyid.equals("24")){//�ֳ����
				int rsCode=MobileRecharge.Correct_Confim(ptOrderNo);
				if(rsCode==0){
					send2TP(fillResponseXML("SUCCESS", "907", "�ѳ���", ptOrderNo,
							null, null, null, null), response);
					return null;
				}else if(rsCode==-1){
					send2TP(fillResponseXML("FAILED", "1004", "����ʧ��",
							ptOrderNo, null, null, null, null), response);
					return null;
				}else{
					send2TP(fillResponseXML("FAILED", "901", "�����쳣", ptOrderNo,
							null, null, null, null), response);
					return null;
				}
			}else if(buyid.equals("28")){//��ʤ�ӿ�
				String[] s=db.getStringArray("select tradeobject,fee,userno from wht_orderform_"+Tools.getNow3().substring(2,6)+" where tradeserial='"+ordid+"'");
				if(s==null || "".equals(s) || s.length<=0){
					send2TP(fillResponseXML("FAILED", "1004", "����ʧ��", ptOrderNo, null, null, null, null), response);
					return null;
				}
				String phoneNum=s[0];
				String money=s[1];
				String strState=Mobile_Interface.Correct(ordid,((int)(Float.parseFloat(money)/1000))+"",phoneNum,s[2]);
				if("0".equals(strState)){
					send2TP(fillResponseXML("SUCCESS", "907", "�ѳ���", ptOrderNo,null, null, null, null), response);
					return null;
				}else if("-1".equals(strState)){
					send2TP(fillResponseXML("FAILED", "1004", "����ʧ��", ptOrderNo,null, null, null, null), response);
					return null;
				}else{
					send2TP(fillResponseXML("FAILED", "901", "�����쳣", ptOrderNo,null, null, null, null), response);
					return null;
				}
			} else {
				send2TP(fillResponseXML("FAILED", "1003", "�ú��벻֧�ֳ���",
						ptOrderNo, null, null, null, null), response);
				return null;
			}
			TradeMsg msg = new TradeMsg();
			msg.setSeqNo(seqNo);
			msg.setFlag("0");// ������
			msg.setMsgFrom2("0103");
			msg.setServNo("00");
			msg.setTradeNo(tradeNo);
			Map<String, String> content = new HashMap<String, String>();
			content.put("BackSeq", ordid);
			content.put("BackType", "0");
			content.put("serialNo", serialNo);
			content.put("phone", tradeObject);
			content.put("tradeFee", tradeFee);

			msg.setContent(content);
			if (!PortalSend.getInstance().sendmsg(msg)) {
				send2TP(fillResponseXML("FAILED", "1004", "����ʧ��", ptOrderNo,
						null, null, null, null), response);
				return null;
			}
			int k1 = 0;
			TradeMsg rsMsg = null;
			while (k1 < 90) {
				k1++;
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Log.info("  key:" + seqNo);
				rsMsg = MemcacheConfig.getInstance().get(seqNo);
				if (null == rsMsg) {
					continue;
				} else {
					MemcacheConfig.getInstance().delete(seqNo);
					break;
				}
			}
			if (null == rsMsg && k1 >= 90) {
				send2TP(fillResponseXML("FAILED", "1002", "�����ڸñʽ��׻����ѳ���24Сʱ",
						ptOrderNo, null, null, null, null), response);
				return null;
			}
			String code = rsMsg.getRsCode();
			Log.info("�ӿ��̳���״̬:" + code);
			if ("000".equals(code)) {
				db.update("insert into wlt_revertlimit values(null,'" + userid
						+ "','" + time.substring(0, 6) + "')");
				send2TP(fillResponseXML("SUCCESS", "907", "�ѳ���", ptOrderNo,
						null, null, null, null), response);
				return null;
			} else {
				send2TP(fillResponseXML("FAILED", "1004", "����ʧ��", ptOrderNo,
						null, null, null, null), response);
				return null;
			}
		} catch (Exception e) {
			Log.error("�ӿ��̳����쳣:" + e.toString());
			send2TP(fillResponseXML("FAILED", "901", "�����쳣", ptOrderNo, null,
					null, null, null), response);
			return null;
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}

	/**
	 * �ӿ�����Կ�б�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward agentsignlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TPForm tpfrom = (TPForm) form;
		PageAttribute page = new PageAttribute(tpfrom.getCurPage(),
				Constant.PAGE_SIZE);
		TpBean tp = new TpBean();
		page.setRsCount(tp.agentsignSum());
		List list = tp.agentsignList(page);
		request.setAttribute("tplist", list);
		request.setAttribute("page", page);
		return new ActionForward("/rights/agentsignlist.jsp");
	}

	/**
	 * ��ӽӿ�����Կ��Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward agentsignadd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TPForm tpfrom = (TPForm) form;
		TpBean tp = new TpBean();
		int state = tp.agentsignadd(tpfrom);
		if (state == -1) {
			request.setAttribute("mess", "�����ظ���ӣ�");
		} else {
			String sql = "SELECT s.userno,s.keyvalue,s.keyvalue1,s.ip,s.state,u.user_login,u.user_id,"
					+ " u.user_pass,u.trade_pass,u.user_site,u.user_city,u.user_status,a.sa_zone,f.fundacct,s.id,s.qbChannel,u.user_pt,s.qbCommission,s.caidan,s.groups,s.jfgroups,s.flowgroups,s.returnurl "
					+ " FROM sys_agentsign s,sys_user u,sys_area a,wht_facct f WHERE "
					+ "	s.userno=u.user_no AND a.sa_id=u.user_city AND f.user_no=u.user_no AND u.user_no="
					+ tpfrom.getUser_no();
			DBService db = new DBService();
			String[] strobj = db.getStringArray(sql, null);
			if (null != db) {
				db.close();
			}
			if (strobj == null || strobj.length <= 0) {
				request.setAttribute("mess", "��ӳɹ�");
				return agentsignlist(mapping, form, request, response);
			}
			if("1".equals(strobj[4].trim())){//�޸Ľӿ�����Ϣ�ɹ��������ǰ�ӿ���״̬Ϊ�����ã���ɾ�������еĽӿ�����Ϣ
				request.setAttribute("mess", "��ӳɹ�,��ɾ��������Ϣ");
				MemcacheConfig.getInstance().delete(strobj[0]);
				return agentsignlist(mapping, form, request, response);
			}
			
			TPForm f = new TPForm();
			f.setUser_no(strobj[0]);
			f.setKeyvalue(strobj[1]);
			f.setKeyvalue1(strobj[2]);
			f.setIp(strobj[3]);
			f.setState(Integer.parseInt(strobj[4]));
			f.setUser_login(strobj[5]);
			f.setUser_id(Integer.parseInt(strobj[6]));
			f.setUser_pass(strobj[7]);
			f.setTrade_pass(strobj[8]);
			f.setUser_site(Integer.parseInt(strobj[9]));
			f.setUser_city(Integer.parseInt(strobj[10]));
			f.setUser_status(Integer.parseInt(strobj[11]));
			f.setSa_zone(strobj[12]);
			f.setFundacct(strobj[13]);
			f.setEmployID(strobj[14]);
			f.setQbFlag(strobj[15]);
			f.setUserPt(strobj[16]);
			f.setQbCommission(strobj[17]);
			f.setCaidan(strobj[18]);
			f.setGroups(strobj[19]);
			f.setJfgroups(strobj[20]);
			f.setFlowgroups(strobj[21]);
			f.setReturnurl(strobj[22]);
			
			MemcacheConfig.getInstance().delete(strobj[0]);
			MemcacheConfig.getInstance().addTP(strobj[0], f);
			Log.info("��ӽӿ�����Ϣ,,,,,,key=" + strobj[0]);
			request.setAttribute("mess", "��ӳɹ�,����ӵ�������Ϣ");
		}
		return agentsignlist(mapping, form, request, response);
	}

	/**
	 * ���½ӿ�����Կ��Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward agentsignupdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TPForm tpfrom = (TPForm) form;
		TpBean tp = new TpBean();
		if (tp.agentsignupdate(tpfrom) > 0) {
			String sql = "SELECT s.userno,s.keyvalue,s.keyvalue1,s.ip,s.state,u.user_login,u.user_id,"
					+ " u.user_pass,u.trade_pass,u.user_site,u.user_city,u.user_status,a.sa_zone,f.fundacct,s.id,s.qbChannel,u.user_pt,s.qbCommission,s.caidan,s.groups,s.jfgroups,s.flowgroups,s.returnurl "
					+ " FROM sys_agentsign s,sys_user u,sys_area a,wht_facct f WHERE "
					+ "	s.userno=u.user_no AND a.sa_id=u.user_city AND f.user_no=u.user_no AND u.user_no="
					+ tpfrom.getUser_no();
			DBService db = new DBService();
			String[] strobj = db.getStringArray(sql, null);
			if (null != db) {
				db.close();
			}
			if (strobj == null || strobj.length <= 0) {
				request.setAttribute("mess", "�޸ĳɹ�,δɾ��������Ϣ");
				return agentsignlist(mapping, form, request, response);
			}
			if("1".equals(strobj[4].trim())){//�޸Ľӿ�����Ϣ�ɹ��������ǰ�ӿ���״̬Ϊ�����ã���ɾ�������еĽӿ�����Ϣ
				request.setAttribute("mess", "�޸ĳɹ�,ɾ��������Ϣ");
				MemcacheConfig.getInstance().delete(strobj[0]);
				return agentsignlist(mapping, form, request, response);
			}
			
			TPForm f = new TPForm();
			f.setUser_no(strobj[0]);
			f.setKeyvalue(strobj[1]);
			f.setKeyvalue1(strobj[2]);
			f.setIp(strobj[3]);
			f.setState(Integer.parseInt(strobj[4]));
			f.setUser_login(strobj[5]);
			f.setUser_id(Integer.parseInt(strobj[6]));
			f.setUser_pass(strobj[7]);
			f.setTrade_pass(strobj[8]);
			f.setUser_site(Integer.parseInt(strobj[9]));
			f.setUser_city(Integer.parseInt(strobj[10]));
			f.setUser_status(Integer.parseInt(strobj[11]));
			f.setSa_zone(strobj[12]);
			f.setFundacct(strobj[13]);
			f.setEmployID(strobj[14]);
			f.setQbFlag(strobj[15]);
			f.setUserPt(strobj[16]);
			f.setQbCommission(strobj[17]);
			f.setCaidan(strobj[18]);
			f.setGroups(strobj[19]);
			f.setJfgroups(strobj[20]);
			f.setFlowgroups(strobj[21]);
			f.setReturnurl(strobj[22]);
			
			MemcacheConfig.getInstance().delete(strobj[0]);
			MemcacheConfig.getInstance().addTP(strobj[0], f);
			Log.info("ɾ������ӽӿ�����Ϣ,,,,,,key=" + strobj[0]);
			request.setAttribute("mess", "�޸ĳɹ�,����ӻ�����Ϣ");
		}
		return agentsignlist(mapping, form, request, response);
	}

	/**
	 * �㶫���ų�ֵ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward telecomFill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ptOrderNo = request.getParameter("ptOrderNo");
		Log.info("telecom���սӿ��̳�ֵ����:" + ptOrderNo);
		if ((!Tools.validateTime()) && (null != ptOrderNo)) {// ������ͣ
			send2TP(fillResponseXML("FAILED", "208", "��ֵʧ��", ptOrderNo,
					ptOrderNo, Tools.getNow3(), null, null), response);
			return null;
		}
		String userSysNo = request.getParameter("userSysNo");
		String tradeType = request.getParameter("tradeType");
		String ptPayTime = request.getParameter("ptPayTime");
		String buyNum = request.getParameter("buyNum");
		String unitPrice = request.getParameter("unitPrice");
		String totalPrice = request.getParameter("totalPrice");
		String numtype = request.getParameter("numType");
		String phone = request.getParameter("account");
		String notifyUrl = request.getParameter("notifyUrl");
		String merchantIP = request.getParameter("merchantIP");
		String validateOrder = request.getParameter("validateOrder");
		String sign = request.getParameter("sign");
		// �ӻ����л�ȡ�ӿ�����Ϣ
		TPForm tp = MemcacheConfig.getInstance().getTP(userSysNo);
		if (tp == null) {
			send2TP(fillResponseXML("FAILED", "108", "�����̲����ڻ���״̬������",
					ptOrderNo, null, null, null, null), response);
			return null;
		}
		// ��֤ip�����ն˺��Ƿ�Ϸ�
		String ip = Tools.getIpAddr(request);
		if (tp.getIp().indexOf(ip) == -1) {
			send2TP(fillResponseXML("FAILED", "105", "�󶨵�IP���ն˱�ŵ�ַ���Ϸ�",
					ptOrderNo, null, null, null, null), response);
			return null;
		}
		if (null == userSysNo || null == tradeType || null == ptOrderNo
				|| numtype == null || null == ptPayTime || null == buyNum
				|| null == unitPrice || null == totalPrice || null == phone
				|| null == notifyUrl || null == merchantIP
				|| null == validateOrder || null == sign) {
			send2TP(fillResponseXML("FAILED", "101", "����������", ptOrderNo, null,
					null, null, null), response);
			return null;
		}
		int buynum = Integer.parseInt(buyNum);
		if ((unitPrice.indexOf(".") != -1) || (totalPrice.indexOf(".") != -1)) {
			send2TP(fillResponseXML("FAILED", "101", "����������", ptOrderNo, null,
					null, null, null), response);
			return null;
		}
		if ((buynum * Integer.parseInt(unitPrice)) != Integer
				.parseInt(totalPrice)) {
			send2TP(fillResponseXML("FAILED", "001", "�ܼۼ�������", ptOrderNo, null,
					null, null, null), response);
			return null;
		}
		String encString = ptOrderNo + ptPayTime;
		if (tp.getUser_no().equals("0000000278")
				|| tp.getUser_no().equals("0000436959")
				|| tp.getUser_no().equals("0007611106")) {
			DES2 des2 = new DES2();
			des2.setKey(tp.getKeyvalue1());
			if (!validateOrder.equals(des2.getEncString(encString))) {
				send2TP(fillResponseXML("FAILED", "109", "У�������,����ʧ��",
						ptOrderNo, null, null, null, null), response);
				return null;
			}
		} else {
			DES des = new DES();
			des.setKey(tp.getKeyvalue1());
			if (!validateOrder.equals(des.getEncString(encString))) {
				send2TP(fillResponseXML("FAILED", "109", "У�������,����ʧ��",
						ptOrderNo, null, null, null, null), response);
				return null;
			}
		}
		String signString = userSysNo + tradeType + ptOrderNo + ptPayTime
				+ buyNum + unitPrice + totalPrice + phone + tp.getKeyvalue();
		if (!MD5Util.MD5Encode(signString, "UTF-8").equals(sign)) {
			send2TP(fillResponseXML("FAILED", "102", "ǩ������,����ʧ��", ptOrderNo,
					null, null, null, null), response);
			return null;
		}

		if (tradeType.equals("10000005")) {
			BiProd biProd = new BiProd();
			String flag = "2";// �ӿ���
			double fee = Double.parseDouble(totalPrice);
			String serialNo = "wh09015" + Tools.getNow3().substring(6)
					+ Tools.buildRandom(2) + Tools.buildRandom(3); 
			try {
				// ���ó�ֵ�ӿ�
				int k = biProd.XYdxFill(tp.getUserPt(), userSysNo, "35", tp
						.getUser_site()
						+ "", "0", phone, ptOrderNo, fee, tp.getSa_zone(), tp
						.getUser_login(), numtype, serialNo,"OF");
				Log.info("�������ӿڹ㶫���ų�ֵ������:" + ptOrderNo + " ��ֵ���:" + k);
				if (k == 0) {
					send2TP(fillResponseXML("SUCCESS", "000", "��ֵ�ɹ�",
							ptOrderNo, ptOrderNo, Tools.getNow3(), null, null),
							response);
					return null;
				} else if (k == 1 || k == -5 || k == 3) {
					send2TP(fillResponseXML("FAILED", "208", "��ֵʧ��", ptOrderNo,
							ptOrderNo, Tools.getNow3(), null, null), response);
					return null;
				} else if (k == 6) {
					send2TP(fillResponseXML("FAILED", "502", "�������ظ�", ptOrderNo,
							ptOrderNo, Tools.getNow3(), null, null), response);
					return null;
				}else {
					send2TP(fillResponseXML("FAILED", "210", "��������", ptOrderNo,
							ptOrderNo, Tools.getNow3(), null, null), response);
					return null;
				}
			} catch (SQLException e) {
				Log.error("��������ֵ�쳣:" + ptOrderNo + " �쳣��Ϣ:" + e.toString());
				send2TP(fillResponseXML("FAILED", "210", "��������", ptOrderNo,
						ptOrderNo, Tools.getNow3(), null, null), response);
				return null;
			}
		} else {
			send2TP(fillResponseXML("FAILED", "202", "��ֵ��Ʒ������", ptOrderNo,
					ptOrderNo, Tools.getNow3(), null, null), response);
			return null;
		}
	}

	/**
	 * �㶫�����˵���ѯ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward telecomQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ptOrderNo = request.getParameter("sepNo");
		Log.info("telecom���սӿ��̲�ѯ�ʵ�����:" + ptOrderNo);
		if ((!Tools.validateTime()) && (null != ptOrderNo)) {// ������ͣ
			send2TP(fillResponseXML("FAILED", "208", "����ʧ��", ptOrderNo,
					ptOrderNo, Tools.getNow3(), null, null), response);
			return null;
		}
		String account = request.getParameter("account");
		String userSysNo = request.getParameter("userSysNo");
		String tradeType = request.getParameter("numType");
		String sign = request.getParameter("sign");
		// �ӻ����л�ȡ�ӿ�����Ϣ
		TPForm tp = MemcacheConfig.getInstance().getTP(userSysNo);
		if (tp == null) {
			send2TP(fillResponseXML("FAILED", "108", "�����̲����ڻ���״̬������",
					ptOrderNo, null, null, null, null), response);
			return null;
		}
		// ��֤ip�����ն˺��Ƿ�Ϸ�
		String ip = Tools.getIpAddr(request);
		if (tp.getIp().indexOf(ip) == -1) {
			send2TP(fillResponseXML("FAILED", "105", "�󶨵�IP���ն˱�ŵ�ַ���Ϸ�",
					ptOrderNo, null, null, null, null), response);
			return null;
		}
		if (null == userSysNo || null == tradeType || null == ptOrderNo
				|| null == account || null == sign) {
			send2TP(fillResponseXML("FAILED", "101", "����������", ptOrderNo, null,
					null, null, null), response);
			return null;
		}
		String signString = userSysNo + ptOrderNo + tp.getKeyvalue();
		if (!MD5Util.MD5Encode(signString, "UTF-8").equals(sign)) {
			send2TP(fillResponseXML("FAILED", "102", "ǩ������,����ʧ��", ptOrderNo,
					null, null, null, null), response);
			return null;
		} else {
			TelcomForm phonePayForm = new TelcomForm();
			phonePayForm.setSeqNo(ptOrderNo);
			phonePayForm.setTradeObject(account.replaceAll(" ", "").trim());
			phonePayForm.setNumType(tradeType);
			TelcomPayBean bean = new TelcomPayBean();
			int InterID=getInterfaceID();//��ȡ�ӿ�ID
			if(InterID==9){//ѶԴ�����˵���ѯ
				int state = bean.XYAccountQueryBill(phonePayForm, null);
				if (state == 1) {
	                HashMap<String, String> zd=new HashMap<String, String>();
	                zd.put("name", phonePayForm.getUserName());
	                zd.put("fee", phonePayForm.getTotalFee());
	                zd.put("phone", account);
	                zd.put("account","");
					send2TP(queryResponseXML("SUCCESS", "000", "��ѯ�˵��ɹ�",ptOrderNo, zd), response);
				} else {
					send2TP(fillResponseXML("FAILED", "208", "����ʧ��", ptOrderNo,
							null, null, null, null), response);
				}
			}else if(InterID==19){//���������˵���ѯ
				List arry=bean.JunBaoAccountQueryBill(phonePayForm,"1");
				if(arry!=null){
					HashMap<String, String> zd=new HashMap<String, String>();
	                zd.put("name", phonePayForm.getUserName());
	                zd.put("fee", Float.parseFloat(phonePayForm.getTotalFee().toString())+"");
	                zd.put("phone", account);
	                zd.put("account",phonePayForm.getMessage());
					send2TP(queryResponseXML("SUCCESS", "000", "��ѯ�˵��ɹ�",ptOrderNo, zd), response);
				}else{
					send2TP(fillResponseXML("FAILED", "208", "����ʧ��", ptOrderNo,
							null, null, null, null), response);
				}
			}else{
				//δ���ýӿ�
			}
			return null;
		}

	}

		/**
		 * �����˵���ѯ���
		 * @param resMsg
		 * @param rsCode
		 * @param failedReason
		 * @param ptOrderNo
		 * @param result
		 * @return
		 */
	public String queryResponseXML(String resMsg, String rsCode,
			String failedReason, String ptOrderNo,
			HashMap<String, String> result) {
		StringBuilder sf = new StringBuilder();
		sf.append(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><resMsg>")
				.append(resMsg).append("</resMsg>").append("<rsCode>").append(
						rsCode).append("</rsCode>").append("<failedReason>")
				.append(failedReason).append("</failedReason>").append(
						"<ptOrderNo>").append(ptOrderNo).append("</ptOrderNo>");
		if (null != result && result.size() > 0) {
			sf.append("<Msg>").append("<phone>").append(result.get("phone"))
					.append("</phone>").append("<name>").append(
							result.get("name")).append("</name>").append(
							"<fee>").append(result.get("fee")).append("</fee>")
					.append("<account>").append(result.get("account")).append("</account>")
					.append("</Msg>");
		}else{
			sf.append("<Msg>").
			append("<phone>").append("").append("</phone>").
			append("<name>").append("").append("</name>").
			append("<fee>").append("").append("</fee>").
			append("<account>").append("").append("</account>")
			.append("</Msg>");
		}
		sf.append("</response>");
		return sf.toString();
	}
	
	/**
	 * �㶫���Ų˵�����ȡ�ӿ�ID
	 * @return int 
	 */
	public int getInterfaceID(){
		int interfaceID = -1;
		DBService dbService =null;
		//�������㶫ʡ�����ţ���ȡ�ӿ�id
		try {
			dbService=new DBService();
			String sql="SELECT interid FROM sys_gddxinterfacemaping ORDER BY id ASC LIMIT 0,1";
			interfaceID = dbService.getInt(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;//ϵͳ��æ
		}finally{
			if(dbService!=null){
				dbService.close();
				dbService=null;
			}
		}
		return interfaceID;
	}

	public static void main(String[] arg){
		try {
			String str=new TPcharge().fillResponseXML("FAILED", "208", "����ʧ��", "111111111",null, null, null, null);
			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * @param resMsg	��Ӧ��Ϣ
	* @param rsCode	������
	* @param ptOrderNo	�������������
	* @param whtOrderNo	���ͨ�������
	* @param orderSuccessTime	���׷���ʱ��
	 * @param responseDescription ��������
	 * @param responseFormat ��Ӧ��ʽ xml or json
	 * @return String
	 * @throws Exception 
	 * @throws Exception 
	 */
	public String Mess(
			String resMsg,String rsCode,String ptOrderNo,
			String whtOrderNo,String orderSuccessTime,
			String responseDescription,String responseFormat) throws Exception{
		if(responseFormat==null || "".equals(responseFormat) || responseFormat.trim().equals("")){
			responseFormat="json";
		}
		InterfaceResponseBean bean=new InterfaceResponseBean();
		bean.setResMsg(resMsg==null?"":resMsg);
		bean.setRsCode(rsCode==null?"":rsCode);
		bean.setPtOrderNo(ptOrderNo==null?"":ptOrderNo);
		bean.setWhtOrderNo(whtOrderNo==null?"":whtOrderNo);
		bean.setOrderSuccessTime(orderSuccessTime==null?"":orderSuccessTime);
		bean.setOrdersDescription(responseDescription==null?"":responseDescription);
		bean.setResponseFormat(responseFormat==null?"":responseFormat);
		if("json".equals(responseFormat)){//json
			return JSON.toJSONString(bean);
		}else{//xml
			 StringWriter sw = new StringWriter();
	         JAXBContext jAXBContext;
			 jAXBContext = JAXBContext.newInstance(bean.getClass());
			 Marshaller marshaller = jAXBContext.createMarshaller();
	         marshaller.marshal(bean, sw);
			return sw.toString();
		}
	}
	/**
	 * �ƶ���ֵ �ӿ� ��ɽ�ӿ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward MobileRechargeInter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String ptOrderNo = request.getParameter("ptOrderNo");
		String userSysNo = request.getParameter("userSysNo");
		String tradeType = request.getParameter("tradeType");
		String ptPayTime = request.getParameter("ptPayTime");
		String buyNum = request.getParameter("buyNum");
		String unitPrice = request.getParameter("unitPrice");
		String totalPrice = request.getParameter("totalPrice");
		String phone = request.getParameter("account");
		String notifyUrl = request.getParameter("notifyUrl");
		String merchantIP = request.getParameter("merchantIP");
		String validateOrder = request.getParameter("validateOrder");
		String reseponseFormat=request.getParameter("reseponseFormat");//ָ����Ӧ��ʽ xml or json
		String sign = request.getParameter("sign");
		TPForm tp=null;
		try{
			Log.info("���սӿ��̳�ֵ����:" + ptOrderNo);
			if ((!Tools.isTime())) {
				send2TP(Mess("FAILED", "208", ptOrderNo,null, Tools.getNow3(),"��������ʱ���,��ֵʧ��", reseponseFormat), response);
				return null;
			}
			tp = MemcacheConfig.getInstance().getTP(userSysNo);
			if (tp == null) {
				send2TP(Mess("FAILED", "108",ptOrderNo, null, Tools.getNow3(), "�����̲����ڻ���״̬������,��ֵʧ��", reseponseFormat), response);
				return null;
			}
			String ip = Tools.getIpAddr(request);
			if (tp.getIp().indexOf(ip) == -1 && tp.getIp().indexOf(merchantIP) == -1) {
				send2TP(Mess("FAILED", "105",ptOrderNo, null, Tools.getNow3(), "�󶨵�IP���ն˱�ŵ�ַ���Ϸ�,��ֵʧ��", reseponseFormat), response);
				return null;
			}
			if (null == userSysNo || "".equals(userSysNo) ||
				null == tradeType || "".equals(tradeType) ||
				null == ptOrderNo||  "".equals(ptOrderNo) ||
				null == ptPayTime ||  "".equals(ptPayTime) ||
				null == buyNum ||  "".equals(buyNum) ||
				null == unitPrice||  "".equals(unitPrice) ||
				null == totalPrice ||  "".equals(totalPrice) ||
				null == phone ||  "".equals(phone) || 
				null == notifyUrl||  "".equals(notifyUrl) ||
				null == merchantIP ||  "".equals(merchantIP) ||
				null == validateOrder ||  "".equals(validateOrder) ||
				null == sign || "".equals(sign)) {
				send2TP(Mess("FAILED", "101",ptOrderNo, null, Tools.getNow3(), "����������,��ֵʧ��", reseponseFormat), response);
				return null;
			}
			if (unitPrice.indexOf("-") != -1 || totalPrice.indexOf("-") != -1 || phone.trim().length()<11) {
				send2TP(Mess("FAILED", "101",ptOrderNo, null, Tools.getNow3(), "��������,��ֵʧ��", reseponseFormat), response);
				return null;
			}
			int buynum = Integer.parseInt(buyNum);
			if ((buynum * Integer.parseInt(unitPrice)) != Integer.parseInt(totalPrice)) {
				send2TP(Mess("FAILED", "001",ptOrderNo, null, Tools.getNow3(), "�ܼۼ�������,��ֵʧ��", reseponseFormat), response);
				return null;
			}
			String encString = ptOrderNo + ptPayTime;
			if (tp.getUser_no().equals("0000000278")|| tp.getUser_no().equals("0000436959")|| tp.getUser_no().equals("0007611106")) {
				DES2 des2 = new DES2();
				des2.setKey(tp.getKeyvalue1());
				if (!validateOrder.equals(des2.getEncString(encString))) {
					send2TP(Mess("FAILED", "109",ptOrderNo, null, Tools.getNow3(), "У�������,��ֵʧ��", reseponseFormat), response);
					return null;
				}
			} else {
				DES des = new DES();
				des.setKey(tp.getKeyvalue1());
				if (!validateOrder.equals(des.getEncString(encString))) {
					send2TP(Mess("FAILED", "109",ptOrderNo, null, Tools.getNow3(), "У�������,��ֵʧ��", reseponseFormat), response);
					return null;
				}
			}
			String signString = userSysNo + tradeType + ptOrderNo + ptPayTime+ buyNum + unitPrice + totalPrice + phone + tp.getKeyvalue();
			if (!MD5Util.MD5Encode(signString, "UTF-8").equals(sign)) {
				send2TP(Mess("FAILED", "102",ptOrderNo, null, Tools.getNow3(), "ǩ������,��ֵʧ��", reseponseFormat), response);
				return null;
			}
		}catch(Exception ex){
			Log.error("111111111ϵͳ�쳣,�ƶ���ֵ�ӿ�,������:"+ptOrderNo+",,,ex:"+ex);
			try {
				send2TP(Mess("FAILED", "208", ptOrderNo,null, Tools.getNow3(),"ϵͳ�쳣,��ֵʧ��", reseponseFormat), response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		try{
			BiProd biProd = new BiProd();
			String[] str = biProd.getPhoneInfo(phone.substring(0, 7));
			if (null == str) {
				send2TP(Mess("FAILED", "110", ptOrderNo,null, Tools.getNow3(),"û�ж�Ӧ���������Ϣ,��ֵʧ��", reseponseFormat), response);
				return null;
			}
			String phonePid = str[0];
			String phoneType = str[1];
			double fee = Double.parseDouble(totalPrice); 
			DBService db = null;
			db = new DBService();
			BiProd bp = new BiProd();
			
			//Ӷ��
			String[] result = new String[]{DBConfig.getInstance().getFosanMoble()};
			//����ƽ̨������
			String orderid="69"+((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+100);
		
			// ���ó�ֵ�ӿ�
			int k = bp.MobleInter(
					"1", tp.getUser_no(), 
					phonePid, tp.getUser_site()+ "", 
					phoneType, phone, ptOrderNo, fee, 
					result, tp.getSa_zone(), 
					db, tp.getUser_login(), 
					merchantIP,orderid, "1");
			
			Log.info("�������ӿڳ�ֵ������:" + ptOrderNo + " ��ֵ���:" + k);
			if (k == 0) {
				send2TP(Mess("SUCCESS", "000", ptOrderNo,orderid, Tools.getNow3(),"��ֵ�ɹ�", reseponseFormat), response);
				return null;
			}else if (k == 1) {
				send2TP(Mess("FAILED", "208", ptOrderNo,orderid, Tools.getNow3(),"��ֵʧ��", reseponseFormat), response);
				return null;
			} else if (k == 2) {
				send2TP(Mess("FAILED", "209", ptOrderNo,orderid, Tools.getNow3(),"������", reseponseFormat), response);
				return null;
			} else if(k==3){
				send2TP(Mess("FAILED", "502", ptOrderNo,orderid, Tools.getNow3(),"��ˮ���ظ�����ʧ��,��ֵʧ��", reseponseFormat), response);
				return null;	
			}else if(k==4){
				send2TP(Mess("FAILED", "208", ptOrderNo,orderid, Tools.getNow3(),"�ʽ��˻�������������,��ֵʧ��", reseponseFormat), response);
				return null;	
			}else {
				send2TP(Mess("FAILED", "210", ptOrderNo,orderid, Tools.getNow3(),"��������", reseponseFormat), response);
				return null;
			}
		}catch(Exception ex){
			Log.error("2222222ϵͳ�쳣,�ƶ���ֵ�ӿ�,������:"+ptOrderNo+",,,ex:"+ex);
			try {
				send2TP(Mess("FAILED", "901", ptOrderNo,null, Tools.getNow3(),"ϵͳ�쳣", reseponseFormat), response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**�ӿ�������ѯ�ӿ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	public ActionForward queryMoneys(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String ptOrderNo = request.getParameter("ptOrderNo");
		Log.info("���սӿ��̲�ѯ�˻��������:" + ptOrderNo);
		if (!Tools.validateTime()) {
			send2TP(ResultQueryMoneysXml("FAILED", "208", "��ѯʧ��", ptOrderNo, null), response);
			return null;
		}
		String userSysNo = request.getParameter("userSysNo");
		String ptPayTime = request.getParameter("ptPayTime");
		String sign = request.getParameter("sign");
		if (null == userSysNo ||  null == ptOrderNo || null == ptPayTime || null == sign ||
				"".equals(userSysNo.trim()) || "".equals(ptOrderNo.trim()) || "".equals(sign.trim())) {
			send2TP(ResultQueryMoneysXml("FAILED", "101", "����������", ptOrderNo,null), response);
			return null;
		}
		// �ӻ����л�ȡ�ӿ�����Ϣ
		TPForm tp = MemcacheConfig.getInstance().getTP(userSysNo);
		if (tp == null) {
			send2TP(ResultQueryMoneysXml("FAILED", "108", "�����̲����ڻ���״̬������",ptOrderNo,  null), response);
			return null;
		}
		// ��֤ip�����ն˺��Ƿ�Ϸ�
		String ip = Tools.getIpAddr(request);
		if (tp.getIp().indexOf(ip) == -1) {
			send2TP(ResultQueryMoneysXml("FAILED", "105", "�󶨵�IP���ն˱�ŵ�ַ���Ϸ�",ptOrderNo,  null), response);
			return null;
		}
		String signString =userSysNo + ptOrderNo + ptPayTime +tp.getKeyvalue();
		if (!MD5Util.MD5Encode(signString, "UTF-8").equals(sign)) {
			send2TP(ResultQueryMoneysXml("FAILED", "102", "ǩ������,��ѯʧ��", ptOrderNo, null), response);
			return null;
		}
		
		DBService db = null;
		try {
			db = new DBService();
			String child = db.getString("select fundacct from wht_facct where user_no='"+ userSysNo + "'") + "01";
			String sql = "select accountleft from wht_childfacct where childfacct='" + child + "'";
			float f = db.getFloat(sql);
			send2TP(ResultQueryMoneysXml("SUCCESS", "000", "��ѯ�ɹ�", ptOrderNo, f+""), response);
			return null;
		} catch (SQLException e) {
			send2TP(ResultQueryMoneysXml("FAILED", "901", "ϵͳ�쳣�����Ժ��ѯ", ptOrderNo, null), response);
			return null;
		} finally {
			if (null != db) {
				db.close();
				db=null;
			}
		}
	}
	
	/**�����������Ӧxml
	 * @param resMsg
	 * @param rsCode
	 * @param description
	 * @param ptOrderNo
	 * @param rsMoney
	 * @return String 
	 */
	public String ResultQueryMoneysXml(String resMsg,String rsCode,String description,String ptOrderNo,String rsMoney){
		return "<?xml version='1.0' encoding='UTF-8'?>"+
				"<response>"+
				       "<resMsg>"+resMsg+"</resMsg>"+
					   "<rsCode>"+rsCode+"</rsCode>"+
					   "<description>"+description+"</description>"+
					   "<ptOrderNo>"+ptOrderNo+"</ptOrderNo>"+
				       "<rsMoney>"+rsMoney+"</rsMoney>"+
				       "<orderSuccessTime>"+Tools.getNow3()+"</orderSuccessTime>"+
				"</response>";
	}
}
