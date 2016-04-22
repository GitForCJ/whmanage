package com.wlt.webm.business.action;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import whmessgae.TradeMsg;

import com.commsoft.epay.util.logging.Log;
import com.informix.msg.nerm_en_US;
import com.wlt.webm.AccountInfo.JingFengInter;
import com.wlt.webm.AccountInfo.NineteenRecharge;
import com.wlt.webm.AccountInfo.scdx;
import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.business.bean.AcctBillBean;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.business.bean.SysUserInterface;
import com.wlt.webm.business.bean.baibang.BaiBean;
import com.wlt.webm.business.bean.baibang.Md5AndBase64;
import com.wlt.webm.business.bean.baibang.XmlHead;
import com.wlt.webm.business.bean.lechong.MobileRecharge;
import com.wlt.webm.business.bean.liansheng.Mobile_Interface;
import com.wlt.webm.business.bean.trafficfines.BreakRulesReq;
import com.wlt.webm.business.bean.trafficfines.BreakRulesResp;
import com.wlt.webm.business.bean.trafficfines.PeccancyInfoModel;
import com.wlt.webm.business.bean.trafficfines.PublicMethod;
import com.wlt.webm.business.bean.trafficfines.SynchronousDatas;
import com.wlt.webm.db.DBConfig;
import com.wlt.webm.db.DBService;
import com.wlt.webm.gm.bean.GuanMingBean;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.message.PortalSend;
import com.wlt.webm.message.YMmessage;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.tool.FloatArith;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.MD5;
import com.wlt.webm.xunjie.bean.YiKahuiTrade;
import com.wlt.webm.yx.bean.YxBean;

/**
 * ȫ�����ѳ�ֵ
 * 
 * @author caiSJ
 * 
 */
public class MobileChargeAction extends DispatchAction {
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
			HttpServletRequest request, HttpServletResponse response) {
		SysUserForm loginUser = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		if (null == loginUser) {
			request.setAttribute("mess", "��¼��ʱ,�����µ�¼");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		if (!loginUser.getRoleType().equals("3")) {
			request.setAttribute("mess", "��ǰ�û����Ͳ�֧�ֽ���");
			return new ActionForward("/business/cmccbusiness.jsp");
		}
		if (!Tools.validateTime()) {
			request.setAttribute("mess", "23:50��00:10��������");
			return new ActionForward("/business/cmccbusiness.jsp");
		}
		String phonePid = request.getParameter("prodId");
		String phone = request.getParameter("tradeObject").replaceAll(" ", "");
		String phoneType = request.getParameter("telType");
		String payfee = request.getParameter("money1");
		Log.info("ȫ�����ѳ�ֵ:"+phonePid+"  "+phone+"   "+phoneType+"   "+payfee);
		if (phonePid == null || "".equals(phonePid) || phone == null
				|| "".equals(phone) || phoneType == null
				|| "".equals(phoneType) || payfee == null || "".equals(payfee)) {
			log
					.info("ȫ����ֵ�������쳣:phonePid:" + phonePid + "    phone:"
							+ phone + "      phoneType:" + phoneType
							+ "      payfee:" + payfee);
			request.setAttribute("mess", "ϵͳ�쳣,���µ�¼!");
			return new ActionForward("/business/cmccbusiness.jsp");
		}
		if (payfee.indexOf("-") != -1) {
			request.setAttribute("mess", "������Ϊ����!");
			return new ActionForward("/business/cmccbusiness.jsp");
		}

		String userPid = loginUser.getUsersite();
		String parentID = loginUser.getParent_id();
		String userno = loginUser.getUserno();
		String seqNo = Tools.getStreamSerial(phone);
		String flag = "0";
		if (payfee.indexOf("-") != -1) {
			request.setAttribute("mess", "���׽��Ϸ�!");
			return new ActionForward("/business/cmccbusiness.jsp");
		}
		double fee = FloatArith.yun2li(payfee);
		DBService db = null;
		try {
			db = new DBService();
			if (AcctBillBean.getStatus(parentID)) {// ֱӪ
				flag = "1";
			}
			BiProd bp = new BiProd();
			String[] result = null;
			if (com.wlt.webm.util.Tools.isJC(phone)) {
				result = bp.getJCEmploy(db, flag, userPid, phonePid, Integer
						.parseInt(payfee), phoneType);
			} else {
				result = bp.getEmploy(db, phoneType, userPid, phonePid, userno,
						flag, Integer.parseInt(payfee), Integer
								.parseInt(parentID), 0);
			}
			if (null == result) {
				request.setAttribute("mess", "��ֵʧ��,Ӷ����ϸ������");
				return new ActionForward("/business/cmccbusiness.jsp");
			}
			if(phoneType.equals("0")&&phonePid.equals("35")){//�㶫������ѶԴ���߾���
				String serialNo= "wh09015" + Tools.getNow3().substring(6)+ Tools.buildRandom(2) + Tools.buildRandom(3); 
				int n=bp.XYdxFill(parentID, userno, phonePid, userPid, "0",
						phone, seqNo, fee,loginUser.getSa_zone(),loginUser.getUsername(),"01",serialNo,null);
				if(n==0){
					request.setAttribute("mess", "��ֵ�ɹ�");
					return new ActionForward("/business/cmccbusiness.jsp");
				}else if(n==1||n==3||n==-2||n==6){
					request.setAttribute("mess", "��ֵʧ��");
					return new ActionForward("/business/cmccbusiness.jsp");
				}else if(n==10){
					request.setAttribute("mess", "һ�����ڲ������ظ�����");
					return new ActionForward("/business/cmccbusiness.jsp");
				}else if(n==-5){
					request.setAttribute("mess", "��ֵʧ��,����");
					return new ActionForward("/business/cmccbusiness.jsp");
				}else if(n==-1){
					request.setAttribute("mess", "ϵͳ��æ,���Ժ��ֵ!");
					return new ActionForward("/business/cmccbusiness.jsp");
				}else if(n==10){
					request.setAttribute("mess", "Ӷ��Ȳ���������ϵ�ͷ�");
					return new ActionForward("/business/cmccbusiness.jsp");
				}else {
					request.setAttribute("mess", "�����У�����ϵ�ͷ�");
					return new ActionForward("/business/cmccbusiness.jsp");
				}
			}else{
			String ip = com.wlt.webm.util.Tools.getIpAddr(request);
			int k = bp.nationFill(parentID, userno, phonePid, userPid,
					phoneType, phone, seqNo, fee, result, loginUser
							.getSa_zone(), db, loginUser.getUsername(), ip,
					null, flag);
			Log.info("������:" + seqNo + " ��ֵ���=" + k);
			// ������ ����ʱ�� ���컧�˻� ��Ʒ���� ��ֵ���� ������ ��ӡСƱ��Ҫ������
			String str = seqNo + ";" + Tools.getNewDate() + ";"
					+ loginUser.getUsername() + ";���ѳ�ֵ;" + phone + ";" + payfee;
			if (k == 0) {
				request.setAttribute("mess", "��ֵ�ɹ�");
				request.setAttribute("str", str);
				return new ActionForward("/business/cmccbusiness.jsp");
			} else if (k > 20) {
				request.setAttribute("mess", "��ֵ�ɹ�,�û��������:" + ((float) k)
						/ 1000);
				request.setAttribute("str", str);
				return new ActionForward("/business/cmccbusiness.jsp");
			} else if (k == 1 || k == -1||k==6) {
				request.setAttribute("mess", "��ֵʧ��");
				return new ActionForward("/business/cmccbusiness.jsp");
			}else if(k==-5){
				request.setAttribute("mess", "��ֵʧ��,����");
				return new ActionForward("/business/cmccbusiness.jsp");
			} else if (k == 2 || k == -2 || k == 3) {
				request.setAttribute("mess", "���״�����,����ϵ�ͷ�");
				return new ActionForward("/business/cmccbusiness.jsp");
			} else if (k == 10) {
				request.setAttribute("mess", "һ�����ڲ������ظ�����");
				return new ActionForward("/business/cmccbusiness.jsp");
			} else {
				request.setAttribute("mess", "�����쳣����ϵ�ͷ�");
				return new ActionForward("/business/cmccbusiness.jsp");
			}
			}
		} catch (Exception e) {
			Log.error("ȫ����ֵ��" + e.toString());
			request.setAttribute("mess", "ϵͳ��æ���Ժ�����");
			return new ActionForward("/business/cmccbusiness.jsp");
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}

	/**
	 * ȫ�����Ź̻���ֵ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward tzFillTwo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm loginUser = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		if (null == loginUser) {
			request.setAttribute("mess", "��¼��ʱ,�����µ�¼");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		if (!loginUser.getRoleType().equals("3")) {
			request.setAttribute("mess", "��ǰ�û����Ͳ�֧�ֽ���");
			return new ActionForward("/rights/fixedline.jsp");
		}
		if (!Tools.validateTime()) {
			request.setAttribute("mess", "23:50��00:10��������");
			return new ActionForward("/rights/fixedline.jsp");
		}
		String phonePid = request.getParameter("prodId");
		String phone = request.getParameter("tradeObject");
		String phoneType = request.getParameter("telType");
		String payfee = request.getParameter("money1");

		if (phonePid == null || "".equals(phonePid) || phone == null
				|| "".equals(phone) || phoneType == null
				|| "".equals(phoneType) || payfee == null || "".equals(payfee)) {
			Log.info("ȫ�����Ź̻���ֵ:�����쳣;     phonePid:" + phonePid + "     phone:"
					+ phone + "     phoneType:" + phoneType + "     payfee:"
					+ payfee);
			request.setAttribute("mess", "ϵͳ�쳣,�����µ�¼!");
			return new ActionForward("/rights/fixedline.jsp");
		}
		if (payfee.indexOf("-") != -1) {
			request.setAttribute("mess", "������Ϊ����!");
			return new ActionForward("/rights/fixedline.jsp");
		}
		String userPid = loginUser.getUsersite();
		String parentID = loginUser.getParent_id();
		String userno = loginUser.getUserno();
		String seqNo = Tools.getStreamSerial(phone);
		String flag = "0";
		double fee = FloatArith.yun2li(payfee);
		DBService db = null;
		try {
			db = new DBService();
			if (AcctBillBean.getStatus(parentID)) {// ֱӪ
				flag = "1";
			}
			BiProd bp = new BiProd();
			String[] result = bp.getEmploy(db, phoneType, userPid, phonePid,
					userno, flag, Integer.parseInt(payfee), Integer
							.parseInt(parentID), 0);
			if (null == result) {
				request.setAttribute("mess", "��ֵʧ��,Ӷ����ϸ������");
				return new ActionForward("/rights/fixedline.jsp");
			}
			String ip = com.wlt.webm.util.Tools.getIpAddr(request);
			int k = bp.nationFillGH(parentID, userno, phonePid, userPid,
					phoneType, phone, seqNo, fee, result, loginUser
							.getSa_zone(), db, loginUser.getUsername(), ip,
					null, flag);
			String strA = seqNo + ";" + Tools.getNewDate() + ";"
			+ loginUser.getUsername() + ";���ų�ֵ;" + phone + ";" + payfee;
			Log.info("������:" + seqNo + " ��ֵ���=" + k);
			if (k == 0) {
				request.setAttribute("mess", "��ֵ�ɹ�");
				request.setAttribute("strA", strA);
				return new ActionForward("/rights/fixedline.jsp");
			} else if (k > 20) {
				request.setAttribute("mess", "��ֵ�ɹ�,�û��������:" + ((float) k)
						/ 1000);
				request.setAttribute("strA", strA);
				return new ActionForward("/rights/fixedline.jsp");
			} else if (k == 1 || k == -1) {
				request.setAttribute("mess", "��ֵʧ��");
				return new ActionForward("/rights/fixedline.jsp");
			} else if (k == 2 || k == -2 || k == 3) {
				request.setAttribute("mess", "���״�����,����ϵ�ͷ�");
				return new ActionForward("/rights/fixedline.jsp");
			} else if (k == 10) {
				request.setAttribute("mess", "һ�����ڲ������ظ�����");
				return new ActionForward("/rights/fixedline.jsp");
			}else if(k==-5){
				request.setAttribute("mess", "��ֵʧ��,����");
				return new ActionForward("/rights/fixedline.jsp");
			} else {
				request.setAttribute("mess", "�����쳣����ϵ�ͷ�");
				return new ActionForward("/rights/fixedline.jsp");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("mess", "ϵͳ��æ���Ժ�����");
			return new ActionForward("/rights/fixedline.jsp");
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}

	/**
	 * ����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reverse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// �жϳ��������Ƿ�����
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		String userid = userForm.getUserno();
		if (!"1".equals(userForm.getRoleType())
				|| !"0".equals(userForm.getRoleType())) {
			int n = SysUserInterface.revertCount(userid);
			if (0 != n) {
				return new ActionForward("/business/order.do?method=listReverse&msg=" + n);
			}
		}
		String ordid = request.getParameter("ordid");
		String buyid = request.getParameter("ordno");
		String tradeNo = null;
		String serialNo = null;
		String seqNo = Tools.getStreamSerial("mangefx" + Tools.buildRandom(4));
		if (buyid.equals("3")) {// �ƶ�
			tradeNo = "06013";
			serialNo = "XY0315012880537" + Tools.getNow().substring(2)
					+ Tools.buildRandom(2) + Tools.buildRandom(3);
		} else if (buyid.equals("4")) {// ��ͨ
			tradeNo = "06015";
			serialNo = "XY0418682022602" + Tools.getNow().substring(2)
					+ Tools.buildRandom(2) + Tools.buildRandom(3);
		} else if (buyid.equals("5")) {// ����
			tradeNo = "06012";
			serialNo = "";
		} else if (buyid.equals("7")) {// ����
			tradeNo = "06022";
			serialNo = "wh06022" + Tools.getNow3().substring(6)
					+ Tools.buildRandom(2) + Tools.buildRandom(3); // �����ƶ�
		} else if (buyid.equals("9")) {// ѶԴ
			tradeNo = "06025";
			serialNo = "wh06025" + Tools.getNow3().substring(6)
					+ Tools.buildRandom(2) + Tools.buildRandom(3);
		} else if (buyid.equals("13")) {// �ڿ� http ����
			String phoneNum="";
			String money="";
			DBService db=null;
			try{
				db=new DBService();
				String[] s=db.getStringArray("select tradeobject,fee from wht_orderform_"+Tools.getNow3().substring(2,6)+" where tradeserial='"+ordid+"'");
				if(s==null || "".equals(s) || s.length<=0){
					return new ActionForward("/business/order.do?method=listReverse&msg=3");//ϵͳ�쳣
				}
				phoneNum=s[0];
				money=s[1];
			}catch(Exception ex){
				Log.error(" �ڿ� http ������ϵͳ�쳣!");
				return new ActionForward("/business/order.do?method=listReverse&msg=3");//ϵͳ�쳣
			}finally{
				if(db!=null)
					db.close();
			}
			
			String strState=NineteenRecharge.reverseServices(ordid,phoneNum, ((int)(Float.parseFloat(money)/1000))+"");
			if("000".equals(strState)){
				return new ActionForward("/business/order.do?method=listReverse&msg=0");//�ɹ�
			}else if("001".equals(strState)){
				return new ActionForward("/business/order.do?method=listReverse&msg=1");//ʧ��
			}else{
				return new ActionForward("/business/order.do?method=listReverse&msg=3");//ϵͳ�쳣
			}
		}else if (buyid.equals("14")) {// ��ͨ�ƶ�
			tradeNo = "06026";
			serialNo = ((char)(new Random().nextInt(26) + (int)'a'))+""+((int)(Math.random()*1000)+1000)+""+((int)(Math.random()*100)+100);
		}else if (buyid.equals("15")) {// ʡ��ͨ  ����
			tradeNo = "06027";
			serialNo = Tools.getNow()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
		}else if (buyid.equals("18")) {// �����ƶ�����
			String sts=BiProd.getWriteOff(ordid);
			if(sts==null){
				return new ActionForward("/business/order.do?method=listReverse&msg=3");
			}else{
				String[] sa=sts.split("#");
				if("".equals(sa[4]) || "@@Ab@@".equals(sa[4])){
					//��ѯ���ζ���
					tradeNo = "09027";
					serialNo=sa[0];
				}else{
					//����
					tradeNo = "06028";
				}
			}
		}else if (buyid.equals("19")) {// �������ų���
			String sts=BiProd.getWriteOff(ordid);
			if(sts==null){
				return new ActionForward("/business/order.do?method=listReverse&msg=3");
			}else{
				String[] sa=sts.split("#");
				if("".equals(sa[4]) || "@@Ab@@".equals(sa[4])){
					//��ѯ���ζ���
					tradeNo = "09027";
					serialNo=sa[0];
				}else{
					//����
					tradeNo = "06029";
				}
			}
		}else if (buyid.equals("22")) {// ��ʢ�ƶ�����
			tradeNo = "06030";
		}else if(buyid.equals("24")){//�ֳ����
			int rsCode=MobileRecharge.Correct_Confim(ordid);
			if(rsCode==0){
				return new ActionForward("/business/order.do?method=listReverse&msg=0");//�ɹ�
			}else if(rsCode==-1){
				return new ActionForward("/business/order.do?method=listReverse&msg=1");//ʧ��
			}else{
				return new ActionForward("/business/order.do?method=listReverse&msg=3");//ϵͳ�쳣
			}
		}else if(buyid.equals("28")){//��ʤ�ӿ�
			String phoneNum="";
			String money="";
			DBService db=null;
			try{
				db=new DBService();
				String[] s=db.getStringArray("select tradeobject,fee,userno from wht_orderform_"+Tools.getNow3().substring(2,6)+" where tradeserial='"+ordid+"'");
				if(s==null || "".equals(s) || s.length<=0){
					return new ActionForward("/business/order.do?method=listReverse&msg=1");//ʧ��
				}
				phoneNum=s[0];
				money=s[1];
				String strState=Mobile_Interface.Correct(ordid,((int)(Float.parseFloat(money)/1000))+"",phoneNum,s[2]);
				if("0".equals(strState)){
					return new ActionForward("/business/order.do?method=listReverse&msg=0");//�ɹ�
				}else if("-1".equals(strState)){
					return new ActionForward("/business/order.do?method=listReverse&msg=1");//ʧ��
				}else{
					return new ActionForward("/business/order.do?method=listReverse&msg=3");//ϵͳ�쳣
				}
			}catch(Exception ex){
				Log.error("��ʤ�ӿڳ����쳣,�����ţ�"+ordid+",,,ex��"+ex);
				return new ActionForward("/business/order.do?method=listReverse&msg=1");//ʧ��
			}finally{
				if(db!=null)
					db.close();
			}
		}else {
			return new ActionForward("/business/order.do?method=listReverse&msg=4");
		}
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// ������
		msg.setMsgFrom2("0203");
		msg.setServNo("00");
		msg.setTradeNo(tradeNo);
		Map<String, String> content = new HashMap<String, String>();
		content.put("BackSeq", ordid);
		content.put("BackType", "0");
		content.put("serialNo", serialNo);
		msg.setContent(content);
		if (!PortalSend.getInstance().sendmsg(msg)) {
			return new ActionForward("/business/order.do?method=listReverse&msg=1");//������Ϣʧ��,��ֵʧ��
		}
		int k = 0;
		TradeMsg rsMsg = null;
		while (k < 180) {
			k++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			rsMsg = MemcacheConfig.getInstance().get(seqNo);
			if (null == rsMsg) {
				continue;
			} else {
				MemcacheConfig.getInstance().delete(seqNo);
				break;
			}
		}
		if (null == rsMsg && k >= 90) {
			return new ActionForward("/business/order.do?method=listReverse&msg=3");
		}
		String code = rsMsg.getRsCode();
		if ("000".equals(code)) {
			if("09027".equals(tradeNo)){//��ѯ���ζ�����
				return new ActionForward("/business/order.do?method=listReverse&msg=6");
			}
			return new ActionForward("/business/order.do?method=listReverse&msg=0");
		} else {
			return new ActionForward("/business/order.do?method=listReverse&msg=1");
		}
	}

	/**
	 * �˷�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward returnFee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ordid = request.getParameter("ordid"); // �������
		String ordno = request.getParameter("ordno"); // ʱ��
		DBService db = null;
		try {
			db = new DBService();
			String orderTable = "wht_orderform_" + ordno.substring(2, 6);
			String acctTable = "wht_acctbill_" + ordno.substring(2, 6);
			String sql0 = "select childfacct,infacctfee,tradeaccount,tradetype from "
					+ acctTable
					+ " where state=0 and tradetype!=15 and tradeserial='"
					+ ordid + "'";

			String[] str = db.getStringArray(sql0);
			if (null == str) {
				return new ActionForward(
						"/business/order.do?method=listReverse&msg=1");
			}
			
			//�ж϶���״̬������1ʧ�ܣ�5������7���˷� ����������
			String isState=db.getString("SELECT state FROM "+orderTable+" WHERE tradeserial='" + ordid + "' AND state IN (1,5,7)");
			if(isState!=null && !"".equals(isState)){
				Log.info("�˷ѣ���������tradeserial="+ordid+",״̬Ϊ state="+isState+",�������ٴ��˷�!");
				return new ActionForward("/business/order.do?method=listReverse&msg=5");
			}
			String isState_=db.getString("SELECT 1 FROM "+acctTable+" WHERE tradeserial='" + ordid + "' AND pay_type=2 AND tradetype!=15");
			if(null!=isState_ && !"".equals(isState_)){
				Log.info("�˷ѣ���������tradeserial="+ordid+",״̬Ϊ state="+isState_+",�������˹�����,�������ظ��˷�!");
				return new ActionForward("/business/order.do?method=listReverse&msg=5");
			}
			
			db.setAutoCommit(false);
			// �޸Ķ���Ϊʧ��
			String sql1 = "update " + orderTable
					+ " set state=1 where tradeserial='" + ordid + "'";
			/**
			 * ��֧δ�ֿ��� String sql2="update "+acctTable+
			 * " set state=1,accountleft=accountleft+"
			 * +Integer.parseInt(str[1])+" where state=0 and tradeserial='"
			 * +ordid+"'"; String
			 * sql3="update wht_childfacct set accountleft=accountleft+"+
			 * Integer.parseInt(str[1])+" where childfacct='"+str[0]+"'"; String
			 * sql4="select childfacct,infacctfee from " +
			 * acctTable+" where state=0 and tradetype=15 and tradeserial='"
			 * +ordid+"'"; String[] str1=db.getStringArray(sql4);
			 * db.update(sql1); db.update(sql2); db.update(sql3);
			 * if(null!=str1){ String sql5="update "+acctTable+
			 * " set state=1,accountleft=accountleft-"+Integer.parseInt(str1[1])
			 * +" where state=0 and tradetype=15 and tradeserial='"+ordid+"'";
			 * String sql6="update wht_childfacct set accountleft=accountleft-"+
			 * Integer.parseInt(str1[1])+" where childfacct='"+str1[0]+"'";
			 * db.update(sql5); db.update(sql6); }
			 */
			db.update(sql1);
			// ��֧�ֿ�
			String newtime = Tools.getNow();
			String tableName = "wht_acctbill_" + newtime.substring(2, 6);
			String userleft0 = "select accountleft from wht_childfacct where childfacct='"
					+ str[0] + "'";
			int userleft = db.getInt(userleft0);
			String fialAcct1 = Tools.getAccountSerial(newtime, "DTF" + str[2]);
			// childfacct,infacctfee,tradeaccount,tradetype
			Object[] acctObj1 = { null, str[0], fialAcct1, str[2], newtime,
					Integer.parseInt(str[1]), Integer.parseInt(str[1]), str[3],
					"ƽ̨�˷�", 0, newtime, userleft + Integer.parseInt(str[1]),
					ordid, str[0], 2 };
			db.logData(15, acctObj1, tableName);
			String sql3 = "update wht_childfacct set accountleft=accountleft+"
					+ Integer.parseInt(str[1]) + " where childfacct='" + str[0]
					+ "'";
			db.update(sql3);
			// ��ѯ�ϼ�
			String sql4 = "select childfacct,infacctfee from " + acctTable
					+ " where state=0 and tradetype=15 and tradeserial='"
					+ ordid + "'";
			String[] str1 = db.getStringArray(sql4);
			if (null != str1) {
				String userleft2 = "select accountleft from wht_childfacct where childfacct='"
						+ str1[0] + "'";
				int userleft3 = db.getInt(userleft2);
				String fialAcct2 = Tools.getAccountSerial(newtime, "STF"
						+ str[2]);

				Object[] acctObj2 = { null, str1[0], fialAcct2, str[2],
						newtime, Integer.parseInt(str1[1]),
						Integer.parseInt(str1[1]), str[3], "���׳���", 0, newtime,
						userleft3 - Integer.parseInt(str1[1]), ordid, str1[0],
						1 };
				db.logData(15, acctObj2, tableName);

				String sql6 = "update wht_childfacct set accountleft=accountleft-"
						+ Integer.parseInt(str1[1])
						+ " where childfacct='"
						+ str1[0] + "'";
				db.update(sql6);
			}
			db.commit();
		} catch (Exception ex) {
			db.rollback();
			Log.error("SMP�����˿����:", ex);
			return new ActionForward(
					"/business/order.do?method=listReverse&msg=1");
		} finally {
			if (null != db) {
				db.close();
			}
		}
		Log.info("�����˷ѳɹ��������ţ�" + ordid + "    ����ʱ�䣺" + Tools.getNow3());
		return new ActionForward("/business/order.do?method=listReverse&msg=0");
	}

	/**
	 * ���컧����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward userRevert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// �жϳ��������Ƿ�����
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		String userid = userForm.getUserno();
		DBService db = null;
		String[] str = null;
		String tradeobject = request.getParameter("tradeObject");
		String phone = request.getParameter("ids");
		if (null == phone) {
			request.setAttribute("mess", "������Ϣ����Ϊ��");
			return new ActionForward("/business/daibanhuRevert.jsp");
		}
		try {
			String time = Tools.getNow();
			db = new DBService();
			String selectStr = "select count(*) from wlt_revertlimit where userno='"
					+ userid + "'  and date='" + time.substring(0, 6) + "'";
			String sql = "select reversalcount from sys_reversalcount where tradetype=0 and user_no="
					+ userid;
			int count = db.getInt(sql);
			int k = db.getInt(selectStr);
			if (count == 0) {
				if (k >= 10) {
					request.setAttribute("mess", "���³�������������,�޷�����");
					return new ActionForward("/business/daibanhuRevert.jsp");
				}
			} else {
				if (k >= count) {
					request.setAttribute("mess", "���³�������������,�޷�����");
					return new ActionForward("/business/daibanhuRevert.jsp");
				}
			}

			str = phone.split("#");
			if (null == str || str.length != 3) {
				request.setAttribute("mess", "�����ڸñʽ��׻����ѳ���24Сʱ");
				return new ActionForward("/business/daibanhuRevert.jsp");
			}
			
			//�ڿ� �ж��Ƿ��Ѿ�����������10����
			String yikuai_reverse="SELECT 1 FROM wht_yikuai_reverse WHERE phone='"+tradeobject+"'  AND TIMESTAMPDIFF(MINUTE, datetimes, '"+Tools.getNow3()+"')<10 ";
			String reverses=db.getString(yikuai_reverse);
			if(reverses!=null && !"".equals(reverses)) //���������м�¼���ѷ������
			{
				request.setAttribute("mess", "�ѷ����һ�γ��������Ժ󣬣���");
				return new ActionForward("/business/daibanhuRevert.jsp");
			}
			
			String ordid = str[0];
			String buyid = str[1];
			String tradeFee = str[2];
			String tradeNo = null;
			String serialNo = null;
			String seqNo = Tools.getStreamSerial("userfx"
					+ Tools.buildRandom(4));
			if (buyid.equals("3")) {// �ƶ�
				tradeNo = "06013";
				serialNo = "XY0315012880537" + Tools.getNow().substring(2)
						+ Tools.buildRandom(2) + Tools.buildRandom(3);
			} else if (buyid.equals("4")) {// ��ͨ
				tradeNo = "06015";
				serialNo = "XY0418682022602" + Tools.getNow().substring(2)
						+ Tools.buildRandom(2) + Tools.buildRandom(3);
			} else if (buyid.equals("5")) {// ����
				tradeNo = "06012";
				serialNo = "";
			} else if (buyid.equals("7")) {// ����
				tradeNo = "06022";
				serialNo = "wh06022" + Tools.getNow3().substring(6)
						+ Tools.buildRandom(2) + Tools.buildRandom(3); // �����ƶ�
			} else if (buyid.equals("9")) {// ѶԴ
				tradeNo = "06025";
				serialNo = "wh06025" + Tools.getNow3().substring(6)
						+ Tools.buildRandom(2) + Tools.buildRandom(3);
			} else if (buyid.equals("13")) {// �ڿ� http ����
				String strState=NineteenRecharge.reverseServices(ordid,tradeobject, ((int)(Float.parseFloat(phone.split("#")[2])/1000))+"");
				if("000".equals(strState)){
					request.setAttribute("mess", "�����ɹ�!");//�ɹ�
				}else if("001".equals(strState)){
					request.setAttribute("mess", "����ʧ��");//ʧ��
				}else{
					request.setAttribute("mess", "ϵͳ�쳣");//ϵͳ�쳣
				}
				return new ActionForward("/business/daibanhuRevert.jsp");
			} else if (buyid.equals("14")) {// ��ͨ�ƶ�
				tradeNo = "06026";
				serialNo = ((char)(new Random().nextInt(26) + (int)'a'))+""+((int)(Math.random()*1000)+1000)+""+((int)(Math.random()*100)+100);
			}else if (buyid.equals("15")) {// ʡ��ͨ  ����
				tradeNo = "06027";
				serialNo = Tools.getNow()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
			}else if (buyid.equals("18")) {// ����  ����
				String sts=BiProd.getWriteOff(ordid);
				if(sts==null){
					request.setAttribute("mess", "����ʧ��");//ʧ��
					return new ActionForward("/business/daibanhuRevert.jsp");
				}else{
					String[] sa=sts.split("#");
					if("".equals(sa[4]) || "@@Ab@@".equals(sa[4])){
						//��ѯ���ζ���
						tradeNo = "09027";
						serialNo=sa[0];
					}else{
						//����
						tradeNo = "06028";
					}
				}
			}else if (buyid.equals("19")) {// �������ų���
				String sts=BiProd.getWriteOff(ordid);
				if(sts==null){
					request.setAttribute("mess", "����ʧ��");//ʧ��
					return new ActionForward("/business/daibanhuRevert.jsp");
				}else{
					String[] sa=sts.split("#");
					if("".equals(sa[4]) || "@@Ab@@".equals(sa[4])){
						//��ѯ���ζ���
						tradeNo = "09027";
						serialNo=sa[0];
					}else{
						//����
						tradeNo = "06029";
					}
				}
			}else if (buyid.equals("22")) {// ��ʢ�ƶ�����
				tradeNo = "06030";
			}else if(buyid.equals("24")){//�ֳ����
				int rsCode=MobileRecharge.Correct_Confim(ordid);
				if(rsCode==0){
					request.setAttribute("mess", "�����ɹ�!");//�ɹ�
				}else if(rsCode==-1){
					request.setAttribute("mess", "����ʧ��");//ʧ��
				}else{
					request.setAttribute("mess", "ϵͳ�쳣");//ϵͳ�쳣
				}
				return new ActionForward("/business/daibanhuRevert.jsp");
			}else if(buyid.equals("28")){//��ʤ�ӿ�
				String[] s=db.getStringArray("select tradeobject,fee,userno from wht_orderform_"+Tools.getNow3().substring(2,6)+" where tradeserial='"+ordid+"'");
				if(s==null || "".equals(s) || s.length<=0){
					request.setAttribute("mess", "ϵͳ�쳣");//ϵͳ�쳣
					return new ActionForward("/business/daibanhuRevert.jsp");
				}
				String phoneNum=s[0];
				String money=s[1];
				String strState=Mobile_Interface.Correct(ordid,((int)(Float.parseFloat(money)/1000))+"",phoneNum,s[2]);
				if("0".equals(strState)){
					request.setAttribute("mess", "�����ɹ�!");//�ɹ�
				}else if("-1".equals(strState)){
					request.setAttribute("mess", "����ʧ��");//ʧ��
				}else{
					request.setAttribute("mess", "ϵͳ�쳣");//ϵͳ�쳣
				}
				return new ActionForward("/business/daibanhuRevert.jsp");
			}else {
				request.setAttribute("mess", "�ú��벻֧�ֳ���");
				return new ActionForward("/business/daibanhuRevert.jsp");
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
			content.put("phone", tradeobject);
			content.put("tradeFee", tradeFee);

			msg.setContent(content);
			if (!PortalSend.getInstance().sendmsg(msg)) {
				request.setAttribute("mess", "����ʧ��");
				return new ActionForward("/business/daibanhuRevert.jsp");// ������Ϣʧ��
				// ,
				// ��ֵʧ��
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
				// Log.info("  key:" + seqNo);
				rsMsg = MemcacheConfig.getInstance().get(seqNo);
				if (null == rsMsg) {
					continue;
				} else {
					MemcacheConfig.getInstance().delete(seqNo);
					break;
				}
			}
			if (null == rsMsg && k1 >= 90) {
				Log.info("����������:" + seqNo);
				request.setAttribute("mess", "����������");
				return new ActionForward("/business/daibanhuRevert.jsp");// ������Ϣʧ��
				// ,
				// ��ֵʧ��
			}
			String code = rsMsg.getRsCode();
			Log.info("��������״̬:" + code);
			if ("000".equals(code)) {
				if("09027".equals(tradeNo)){//��ѯ���ζ�����
					request.setAttribute("mess", "���Ժ������!");
					return new ActionForward("/business/daibanhuRevert.jsp");
				}
				db.update("insert into wlt_revertlimit values(null,'" + userid
						+ "','" + time.substring(0, 6) + "')");
				request.setAttribute("mess", "�����ɹ�");
				return new ActionForward("/business/daibanhuRevert.jsp");// ������Ϣʧ��
				// ,
				// ��ֵʧ��
			} else {
				request.setAttribute("mess", "����ʧ��");
				return new ActionForward("/business/daibanhuRevert.jsp");// ������Ϣʧ��
				// ,
				// ��ֵʧ��
			}
		} catch (Exception e) {
			Log.error("�û������쳣:" + e.toString());
			request.setAttribute("mess", "ϵͳ��æ,���Ժ�����");
			return new ActionForward("/business/daibanhuRevert.jsp");
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}

	/**
	 * ҳ���б����������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward userAccountListReverse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out=response.getWriter();
		// �жϳ��������Ƿ�����
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		String userid = userForm.getUserno();
		DBService db = null;
		
		String orderId=request.getParameter("orderid");
		String datetime=request.getParameter("datetime");
		if (null == orderId || datetime==null || "".equals(orderId) || "".equals(datetime)) {
			out.write("-5");//ȱ�ٲ���;
			out.flush();
			out.close();
			return null;
		}
		try {
			String time = Tools.getNow();
			db = new DBService();
			String selectStr = "select count(*) from wlt_revertlimit where userno='"
					+ userid + "'  and date='" + time.substring(0, 6) + "'";
			String sql = "select reversalcount from sys_reversalcount where tradetype=0 and user_no="
					+ userid;
			int count = db.getInt(sql);
			int k = db.getInt(selectStr);
			if (count == 0) {
				if (k >= 10) {
					out.write("-2");//���³�������������,�޷�����
					out.flush();
					out.close();
					return null;
				}
			} else {
				if (k >= count) {
					out.write("-2");//���³�������������,�޷�����
					out.flush();
					out.close();
					return null;
				}
			}
			String orderSelect="SELECT tradeserial,buyid,tradeobject,fee FROM wht_orderform_"+datetime.substring(2,6)+" WHERE id='"+orderId+"' AND  tradetime>'"+Tools.getOtherTime(-1)+"'";
			String[] strList=db.getStringArray(orderSelect, null);
			if (strList==null ) {
				out.write("-3");//�����ڸñʽ��׻����ѳ���24Сʱ
				out.flush();
				out.close();
				return null;
			}
			
			//�ڿ� �ж��Ƿ��Ѿ�����������
			String yikuai_reverse="SELECT 1 FROM wht_yikuai_reverse WHERE phone='"+strList[2]+"' AND TIMESTAMPDIFF(MINUTE, datetimes, '"+Tools.getNow3()+"')<10 ";
			String reverses=db.getString(yikuai_reverse);
			if(reverses!=null && !"".equals(reverses)) //���������м�¼���ѷ������
			{
				out.write("-9");
				out.flush();
				out.close();
				return null;
			}
			
			String ordid = strList[0];
			String buyid = strList[1];
			String tradeFee = strList[2];
			String accountFee=strList[3];
			String tradeNo = null;
			String serialNo = null;
			String seqNo = Tools.getStreamSerial("userfx"
					+ Tools.buildRandom(4));
			if (buyid.equals("3")) {// �ƶ�
				tradeNo = "06013";
				serialNo = "XY0315012880537" + Tools.getNow().substring(2)
						+ Tools.buildRandom(2) + Tools.buildRandom(3);
			} else if (buyid.equals("4")) {// ��ͨ
				tradeNo = "06015";
				serialNo = "XY0418682022602" + Tools.getNow().substring(2)
						+ Tools.buildRandom(2) + Tools.buildRandom(3);
			} else if (buyid.equals("5")) {// ����
				tradeNo = "06012";
				serialNo = "";
			} else if (buyid.equals("7")) {// ����
				tradeNo = "06022";
				serialNo = "wh06022" + Tools.getNow3().substring(6)
						+ Tools.buildRandom(2) + Tools.buildRandom(3); // �����ƶ�
			} else if (buyid.equals("9")) {// ѶԴ
				tradeNo = "06025";
				serialNo = "wh06025" + Tools.getNow3().substring(6)
						+ Tools.buildRandom(2) + Tools.buildRandom(3);
			}  else if (buyid.equals("13")) {// �ڿ� http ����
				String strState=NineteenRecharge.reverseServices(ordid,tradeFee, (int)(Float.parseFloat(accountFee)/1000)+"");
				if("000".equals(strState)){
					out.write("0");//�����ɹ�
				}else if("001".equals(strState)){
					out.write("-5");//����ʧ��
				}else{
					out.write("-1");//ϵͳ�쳣
				}
				out.flush();
				out.close();
				return null;
			} else if (buyid.equals("14")) {// ��ͨ�ƶ�
				tradeNo = "06026";
				serialNo = ((char)(new Random().nextInt(26) + (int)'a'))+""+((int)(Math.random()*1000)+1000)+""+((int)(Math.random()*100)+100);
			}else if (buyid.equals("15")) {// ʡ��ͨ  ����
				tradeNo = "06027";
				serialNo = Tools.getNow()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
			}else if (buyid.equals("18")) {// ����  ����
				String sts=BiProd.getWriteOff(ordid);
				if(sts==null){
					out.write("-5");
					out.flush();
					out.close();
					return null;
				}else{
					String[] sa=sts.split("#");
					if("".equals(sa[4]) || "@@Ab@@".equals(sa[4])){
						//��ѯ���ζ���
						tradeNo = "09027";
						serialNo=sa[0];
					}else{
						//����
						tradeNo = "06028";
					}
				}
			}else if (buyid.equals("19")) {// �������ų���
				String sts=BiProd.getWriteOff(ordid);
				if(sts==null){
					out.write("-5");
					out.flush();
					out.close();
					return null;
				}else{
					String[] sa=sts.split("#");
					if("".equals(sa[4]) || "@@Ab@@".equals(sa[4])){
						//��ѯ���ζ���
						tradeNo = "09027";
						serialNo=sa[0];
					}else{
						//����
						tradeNo = "06029";
					}
				}
			}else if (buyid.equals("22")) {// ��ʢ�ƶ�����
				tradeNo = "06030";
			}else if(buyid.equals("24")){//�ֳ����
				int rsCode=MobileRecharge.Correct_Confim(ordid);
				if(rsCode==0){
					out.write("0");//�����ɹ�
				}else if(rsCode==-1){
					out.write("-5");//����ʧ��
				}else{
					out.write("-1");//ϵͳ�쳣
				}
				out.flush();
				out.close();
				return null;
			}else if(buyid.equals("28")){//��ʤ�ӿ�
				String[] s=db.getStringArray("select tradeobject,fee,userno from wht_orderform_"+Tools.getNow3().substring(2,6)+" where tradeserial='"+ordid+"'");
				if(s==null || "".equals(s) || s.length<=0){
					out.write("-5");
					out.flush();
					out.close();
					return null;
				}
				String phoneNum=s[0];
				String money=s[1];
				String strState=Mobile_Interface.Correct(ordid,((int)(Float.parseFloat(money)/1000))+"",phoneNum,s[2]);
				if("0".equals(strState)){
					out.write("0");//�����ɹ�
				}else if("-1".equals(strState)){
					out.write("-5");//����ʧ��
				}else{
					out.write("-6");//ϵͳ�쳣
				}
				out.flush();
				out.close();
				return null;
			}else {
				out.write("-4");//�ú��벻֧�ֳ���
				out.flush();
				out.close();
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
			content.put("phone", strList[2]);
			content.put("tradeFee", tradeFee);

			msg.setContent(content);
			if (!PortalSend.getInstance().sendmsg(msg)) {
				out.write("-5");//����ʧ��  ������Ϣʧ��
				out.flush();
				out.close();
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
				rsMsg = MemcacheConfig.getInstance().get(seqNo);
				if (null == rsMsg) {
					continue;
				} else {
					MemcacheConfig.getInstance().delete(seqNo);
					break;
				}
			}
			if (null == rsMsg && k1 >= 90) {
				out.write("-6");//����������
				out.flush();
				out.close();
				return null;
			}
			String code = rsMsg.getRsCode();
			if ("000".equals(code)) {
				if("09027".equals(tradeNo)){//������ѯ���ζ�����
					out.write("-11");//������ѯ
					out.flush();
					out.close();
					return null;
				}
				db.update("insert into wlt_revertlimit values(null,'" + userid
						+ "','" + time.substring(0, 6) + "')");
				out.write("0");//�����ɹ�
				out.flush();
				out.close();
				return null;
				// ,
				// ��ֵʧ��
			} else {
				out.write("-5");//����ʧ��
				out.flush();
				out.close();
				return null;
			}
		} catch (Exception e) {
			out.write("-1");//ϵͳ�쳣
			out.flush();
			out.close();
			return null;
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}
	/**
	 * ��ѯ���Գ����ļ�¼
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getReverts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// �жϳ��������Ƿ�����
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		String userid = userForm.getUserno();
		DBService db = null;
		List<String[]> list = null;
		String phone = request.getParameter("tradeObject");
		if (null == phone) {
			request.setAttribute("mess", "���벻��Ϊ��");
			return new ActionForward("/business/daibanhuRevert.jsp");
		}
		phone = request.getParameter("tradeObject").replaceAll(" ", "");
		try {
			String time = Tools.getNow();
			db = new DBService();
			String selectStr = "select count(*) from wlt_revertlimit where userno='"
					+ userid + "'  and date='" + time.substring(0, 6) + "'";
			String sql = "select reversalcount from sys_reversalcount where tradetype=0 and user_no="
					+ userid;
			int count = db.getInt(sql);
			int k = db.getInt(selectStr);
			if (count == 0) {
				if (k >= 10) {
					request.setAttribute("mess", "���³�������������,�޷�����");
					return new ActionForward("/business/daibanhuRevert.jsp");
				}
			} else {
				if (k >= count) {
					request.setAttribute("mess", "���³�������������,�޷�����");
					return new ActionForward("/business/daibanhuRevert.jsp");
				}
			}
			String tablename = "wht_orderform_" + time.substring(2, 6);
			String sql1 = "select tradeobject,fee,DATE_FORMAT(tradetime,'%Y-%m-%d %k:%i:%s'),state,tradeserial,buyid from "
					+ tablename
					+ " where tradeobject='"
					+ phone
					+ "' and tradetime>'"
					+ Tools.getOtherTime(-1)
					+ "' and state not in(1,5,7) and buyid in(3,4,5,7,9,13,15,14,18,19,22,24,28) and userno='"
					+ userid + "'";
			list = db.getList(sql1);
		} catch (Exception e) {
			Log.error("�û�������ѯ�쳣:" + e.toString());
			request.setAttribute("mess", "ϵͳ��æ,���Ժ�����");
			return new ActionForward("/business/daibanhuRevert.jsp");
		} finally {
			if (null != db) {
				db.close();
			}
		}
		if (null == list || list.size() < 1) {
			request.setAttribute("mess", "��������ؽ��׻����ѳ���24Сʱ");
			request.setAttribute("revertList", list);
			return new ActionForward("/business/daibanhuRevert.jsp");
		} else {
			for (Object tmp : list) {
				String[] temp = (String[]) tmp;
				if (null != temp[3] && !"".equals(temp[3])) {
					if ("0".equals(temp[3])) {
						temp[3] = "�ɹ�";
					} else if ("1".equals(temp[3])) {
						temp[3] = "ʧ��";
					} else if ("4".equals(temp[3])) {
						temp[3] = "������";
					} else if ("5".equals(temp[3])) {
						temp[3] = "����";
					} else if ("6".equals(temp[3])) {
						temp[3] = "�쳣����";
					}
				}
			}
		}
		request.setAttribute("ph", phone);
		request.setAttribute("revertList", list);
		return new ActionForward("/business/daibanhuRevert.jsp");
	}

	/**
	 * �ɹ�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward success(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ordid = request.getParameter("ordid");
		String ordno = request.getParameter("ordno");
		try {
			MobileChargeService service = new MobileChargeService();
			service.updOrderStatus(ordid, "0", "wht_orderForm_"
					+ ordno.substring(2, 6));
		} catch (Exception e) {
			log
					.error("����״̬��Ϊ�ɹ����������ţ�" + ordid + "    ����ʱ�䣺"
							+ Tools.getNow3());
			e.printStackTrace();
			request.setAttribute("mess", "����ʧ��");
			return new ActionForward(
					"/business/order.do?method=listReverse&msg=1");
		}
		Log.info("����״̬��Ϊ�ɹ� ok �������ţ�" + ordid + "    ����ʱ�䣺" + Tools.getNow3());
		request.setAttribute("mess", "�����ɹ�");
		return new ActionForward("/business/order.do?method=listReverse&msg=0");
	}

	/**
	 * ����ֱ�Ӳ�ѯ�ӿ�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward orderQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (null == request.getSession().getAttribute("userSession")) {
			request.setAttribute("mess", "��¼��ʱ,�����µ�¼");
			return new ActionForward("/rights/wltlogin.jsp");
		}

		String orderid = request.getParameter("orderid");
		String buyid = request.getParameter("buyid");
		String phone = request.getParameter("phone");
		String tradetime = request.getParameter("tradetime");
		String money=request.getParameter("money");
		String n = "-1";
		if (null != orderid && null != buyid) {
			if (buyid.equals("6")) {
				n = scdx.getOrderState(orderid);
			} else if (buyid.equals("8")) {
				YxBean yxBean = new YxBean();
				n = yxBean.yxQuey(orderid, phone) + "";
			} else if (buyid.equals("1")) {
				YiKahuiTrade yi = new YiKahuiTrade();
				n = yi.YikahuiQuery1(orderid) + "";
			} else if (buyid.equals("10")) {
				GuanMingBean gb = new GuanMingBean();
				n = gb.GuanMingQuey("epayeszfb", orderid) + "";
			} else if (buyid.equals("12")) {
				BiProd bp = new BiProd();
				n = bp.queryYiBaiMi(orderid, phone, tradetime);
			} else if (buyid.equals("13")){
				n=NineteenRecharge.query(orderid,(int)(Float.parseFloat(money)/1000),phone)+"".trim();
			}else if (buyid.equals("15")){//ʡ��ͨ
				n="-2";
			}else if (buyid.equals("17")){//����  ȫ������
				n=JingFengInter.getOrderState(orderid);
			}
		}
		PrintWriter printWriter = null;
		printWriter = response.getWriter();
		printWriter.print(n);
		printWriter.flush();
		printWriter.close();
		return null;
	}
	
//	/**Υ�´����ѯ
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return null
//	 * @throws Exception
//	 */
//	@SuppressWarnings("unchecked")
//	public ActionForward catMessage(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		
//		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
//		
//		String plateNumber=new String((request.getParameter("plateNumberA")+"".trim()).getBytes("iso-8859-1"),"GBK")+request.getParameter("plateNumberB");
//		String plateNumberType=request.getParameter("plateNumberType");
//		String carFrameNum=request.getParameter("carFrameNum");
//		String engineNumber=request.getParameter("engineNumber");
//		String carNameText=request.getParameter("carNameText");//������������
//		String interfaceType=request.getParameter("interfaceType");
//		if(plateNumber==null || "".equals(plateNumber) ||
//				plateNumberType==null || "".equals(plateNumberType) ||
//					carFrameNum==null || "".equals(carFrameNum) ||
//						engineNumber==null || "".equals(engineNumber))
//		{
//			Log.info("Υ�´�������������㣬�����쳣������");
//			request.setAttribute("mess","��������,�쳣����");
//			return new ActionForward("/business/catmanage.jsp");
//		}
//		request.setAttribute("catNumbers", plateNumber);//���ƺ�
//		request.setAttribute("carNameText",carNameText);
//		request.setAttribute("interfaceType",interfaceType);
//		List<PeccancyInfoModel> list=null;
//		
//		int zshMoney=0;
//		//��ʯ��������
//		if("382".equals(userSession.getUsersite())){
//			zshMoney=DBConfig.getInstance().getJtfkzsh()/1000;
//		}
//		
//		if("2".equals(interfaceType)){//������
//			plateNumberType=("99".equals(plateNumberType))?"02":plateNumberType;//99����С�ͳ���Ҫת��02������
//			request.setAttribute("catmessage",plateNumber+"#"+plateNumberType+"#"+carFrameNum+"#"+engineNumber);
//			
//			String catUserName=request.getParameter("catUserName");
//			String catUserPhone=request.getParameter("catUserPhone");
//			String carAddress=request.getParameter("carAddress");
//			if(catUserName==null || "".equals(catUserName) || catUserPhone==null || "".equals(catUserPhone)){
//				Log.info("Υ�´�������������㣬�����쳣������");
//				request.setAttribute("mess","��������,�쳣����");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//			request.setAttribute("catUserName",catUserName);
//			request.setAttribute("catUserPhone",catUserPhone);
//			request.setAttribute("carAddress",carAddress);
//			//��װ���� queryText ��ֵ 
////			��QueryTextӦ�������ֶ����Դ��A��������������˳�򣩣��ֶ�֮����&�ָ����������ֺͲ���ֵ֮����=�ָ�����ʽ���£�
////			Vehicle=��A12345&VehicleType=1&FrameNo=554545&EngineNo=21555&OwnerName=С��
//			StringBuffer A=new StringBuffer();
//			A.append("Vehicle="+plateNumber);
//			A.append("&VehicleType="+plateNumberType);
//			A.append("&FrameNo="+carFrameNum);
//			A.append("&EngineNo="+engineNumber);
//			A.append("&OwnerName="+catUserName);
//			A.append("&Limit=100");
//			A.append("&Offset=100");
////			��A�����md5��key��key�ɲƸ�ͨ�ṩ���õ���B����ʽ���£�
////			Vehicle=A12345&VehicleType=1&FrameNo=554545&EngineNo=21555&OwnerName=С��&Md5Key=123456
//			String B=A.toString()+"&Md5Key="+Md5AndBase64.KEY;
//			//��B��md5�õ�Md5Sign����д�� 
//			String Md5Sign=MD5.encode(B).toUpperCase();
////			��Md5Sign׺��A����õ���C����ʽ���£�
////			Vehicle=A12345&VehicleType=1&FrameNo=554545&EngineNo=21555&OwnerName=С��&Md5Sign=FD0CF5B71C3D3A6D651078A0B568390F
//			String c=A.toString()+"&Md5Sign="+Md5Sign;
//			//��C����BASE64���ܵõ�D��ʹ��GB2312��
//			String D=Md5AndBase64.base64EN(c,"GB2312");
//			
//			//��װ ���� sign ��ֵ
//			//�ֶΰ����ֶ��������ֵ���������
//			//��s1����׷��& Md5Key=xxxxxxxxxxxxx�õ�ǩ��Դ��s2����s2��MD5�õ�Sign��ֵ   MD5ͳһ���ô�д
//			XmlHead head=new XmlHead("","");
//			StringBuffer s2=new StringBuffer();
//			s2.append("Attach="+head.getAttach());
//			s2.append("&CftMerId="+head.getCftMerId());
//			s2.append("&Command="+head.getCommand());
//			s2.append("&MerchantId="+head.getMerchantId());
//			s2.append("&QueryText="+D);
//			s2.append("&ResFormat="+head.getResFormat());
//			s2.append("&UserId="+head.getUserId());
//			s2.append("&Version="+head.getVersion());
//			s2.append("&Md5Key="+Md5AndBase64.KEY);
//			String sign=MD5.encode(s2.toString()).toUpperCase();
//			
//			BaiBean b=new BaiBean();
//			//ƴ�� http url 
//			StringBuffer buffer=new StringBuffer();
//			buffer.append(b.QUERY);//url
//			buffer.append("Attach="+head.getAttach());
//			buffer.append("&CftMerId="+head.getCftMerId());
//			buffer.append("&Command="+head.getCommand());
//			buffer.append("&MerchantId="+head.getMerchantId());
//			buffer.append("&QueryText="+URLEncoder.encode(D,"GB2312"));
//			buffer.append("&ResFormat="+head.getResFormat());
//			buffer.append("&UserId="+head.getUserId());
//			buffer.append("&Version="+head.getVersion());
//			buffer.append("&Sign="+sign);
//			
//			List<HashMap> arr=null;
//			//��������� ҵ���߼�
//			String result=b.sendMsgRequest(buffer.toString());
//			if(result==null || "".equals(result) ){
//				//���β�ѯ���ص�Υ������
//				Log.info("������Υ�²�ѯ�������ݷ��أ���"+result);
//				request.setAttribute("mess","δ��ȡΥ�¼�¼!");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//			Document docResult=DocumentHelper.parseText(result);
//			Element rootResult = docResult.getRootElement();
//			//��ȡ ָ���ڵ�
//			Element respText=rootResult.element("RespText");
//			if(respText==null || "".equals(respText) ){
//				//���β�ѯ���ص�Υ������
//				Log.info("������Υ�²�ѯ�������ݷ��أ���"+result);
//				request.setAttribute("mess","δ��ȡΥ�¼�¼!");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//			
//			String respTextXML=Md5AndBase64.base64DE(respText.getText(),"GB2312");
//			String[] ssa=respTextXML.split("&");
//			
//			//���β�ѯ���ص�Υ������;
//			String RetNum=ssa[0].substring(ssa[0].indexOf("RetNum=")+"RetNum=".length(),ssa[0].length());
//			if(RetNum==null || "".equals(RetNum) || Integer.parseInt(RetNum)<=0){
//				//���β�ѯ���ص�Υ������
//				Log.info("������Υ�²�ѯ�������ݷ��أ���"+result);
//				request.setAttribute("mess","δ��ȡΥ�¼�¼!");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//			
//			//����ָ���ַ���
//			String Records=ssa[2].substring(ssa[2].indexOf("Records=")+"Records=".length(),ssa[2].length());
//			Records=URLDecoder.decode(Records,"GB2312");
//			//ƴ�ӳ� xml ��ʽ�ַ���
//			String Test = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
//			"<root>" +
//				Records+
//			"</root>";
//			//����ָ����   xml  �ַ���
//			Document doc=DocumentHelper.parseText(Test);
//			Element rootss = doc.getRootElement();
//			Element RecordsXML=rootss.element("Records");
//			arr=new ArrayList<HashMap>();
//			List<Element> ras=RecordsXML.elements();
//			for(Element aa:ras){
//				HashMap map=new HashMap();
//				List<Element> records=aa.elements();
//				for(Element rr:records){
//					map.put(rr.getName(),rr.getText());
//				}
//				arr.add(map);
//			}
//				
//			list=new ArrayList<PeccancyInfoModel>();
//			for(int i=0;i<arr.size();i++){
//				HashMap mm=arr.get(i);
//				PeccancyInfoModel p=new PeccancyInfoModel();
//				p.setId(mm.get("ViolationId")+""); //Υ����Ϣid
//				p.setPecNum(mm.get("Wsh")==null?"":mm.get("Wsh").toString());//Υ�������(�����)
//				p.setPlateNumber(plateNumber);//���ƺ���
//				p.setPecCode(mm.get("Wzdm")==null?"":mm.get("Wzdm").toString());//Υ�´���
//				p.setPecDesc(mm.get("ViloationDetail")==null?"":mm.get("ViloationDetail").toString());//Υ����Ϊ����
//				p.setPecAddr(mm.get("ViloationLocation")==null?"":mm.get("ViloationLocation").toString());//Υ�µص�
//				p.setPecDate(mm.get("ViolationTime")==null?"":mm.get("ViolationTime").toString());//Υ��ʱ��
//				//�۷�
//				String koufen=mm.get("Wzdm")==null?"-1":mm.get("Wzdm")+"";
//				if("-1".equals(koufen)){
//					koufen="0";
//				}else if("7".equals(koufen)){
//					koufen="12";
//				}else{
//					koufen=koufen.substring(1,2);
//				}
//				p.setPecScore(koufen);//�۷����
//				p.setCorpus(mm.get("FineFee")==null?"0":Integer.parseInt(mm.get("FineFee")+"".trim())/100+"".trim());//����
//				p.setLateFee(mm.get("LateFee")==null?"0":Integer.parseInt(mm.get("LateFee")+"".trim())/100+"".trim());//���ɽ�
//				p.setReplaceMoney(mm.get("DealFee")==null?"0":Integer.parseInt(mm.get("DealFee")+"".trim())/100+"".trim());//�����	
//				String DealFlag=mm.get("DealFlag")==null?"":Integer.parseInt(mm.get("DealFlag")+"".trim())+"".trim();//����״̬ת��
//				if("0".equals(DealFlag)){
//					DealFlag="2";
//				}
//				if("1".equals(DealFlag)){
//					DealFlag="1";
//				}
//				p.setAgent(DealFlag);//�Ƿ�ɴ���(1�����Դ��� ,2�������Դ���)
//				p.setPecState("");//״̬(1�����ͨ�� ,2����˲�ͨ��)
//				p.setPecChanl("");//�ɼ�����(1���������ӿ� ,2���ֹ��ɼ�)
//				p.setCreateDate("");//��¼����ʱ��
//				p.setUpdateDate("");//��¼�޸�ʱ��
//				p.setUpdateWorkerNo("");//��¼�޸��˹���
//				p.setWoState("");//���쵥״̬���쵥״̬ 1�� �µ��ɹ�δ֧�� 2�� ��֧�� 3�� ������ 4�� ����ɹ� 5�� ����ʧ�� 6�� �����쳣 7�� ȡ�� -99��ɾ�����쵥 100  ������  101���˷�
//				p.setAreaCode("");//Υ���ص��Ӧ��������
//				p.setIllegalType("");//Υ������ (ELEC��ʾ���ӵ������������ɽ��;SCEN�����ֳ��������Ϸ��ģ�������Ӧ���ɽ�)
//				p.setIsdaibai(DealFlag);//�µ�״̬ 1 �ɴ��� 2 �Ѿ��µ� 3 ����ɹ� 4 ���ɴ���
//				
//				//��ʯ�� �����  ���ɽ�
//				if("382".equals(userSession.getUsersite())){
//					int ca=mm.get("LateFee")==null?0:Integer.parseInt(mm.get("LateFee")+"".trim())/100;
//					ca=(int)Math.ceil(ca);
//					p.setNew_LateFee(ca+"");//���ɽ�
//				}else{
//					p.setNew_LateFee(mm.get("LateFee")==null?"0":Integer.parseInt(mm.get("LateFee")+"".trim())/100+"".trim());//���ɽ�
//				}
//				
//				if(!"382".equals(userSession.getUsersite())){//��ͨ�����
//					//���ݲ�ѯ�Ĵ���Ѽӿ�
//					if(mm.get("DealFee")==null || "".equals(mm.get("DealFee")) || "0".equals(mm.get("DealFee"))){
//						zshMoney=DBConfig.getInstance().getJtfkdefault()/1000;
//					}else{
//						zshMoney=(Integer.parseInt(mm.get("DealFee")+"")/100)+(DBConfig.getInstance().getJtfkfloat()/1000);
//						//��ѯ���ش���� ���� 25Ԫ�����������
//						if((Integer.parseInt(mm.get("DealFee")+"")/100)>25){
//							p.setIsdaibai("4");//�µ�״̬ 1 �ɴ��� 2 �Ѿ��µ� 3 ����ɹ� 4 ���ɴ���
//							p.setAgent("4");//�Ƿ�ɴ���(1�����Դ��� ,2�������Դ���)
//						}
//					}
//				}
//				p.setMyPoundage(zshMoney+"".trim());//��ƽ̨�����
//				list.add(p);
//			}
//			request.setAttribute("arrylist",list);
//			return new ActionForward("/business/catmanageList.jsp");
//		}
//		
//		//�������ҵ���߼�
//		plateNumberType=("99".equals(plateNumberType))?"1":plateNumberType;//���� 99����С�ͳ���Ҫת��1����
//		request.setAttribute("catmessage",plateNumber+"#"+plateNumberType+"#"+carFrameNum+"#"+engineNumber);
//		String tokenId=PublicMethod.TOKEN_ID;
//		if(tokenId==null || "".equals(tokenId))
//		{
//			tokenId=PublicMethod.getTokenID();
//			if(tokenId==null || "".equals(tokenId))
//			{
//				Log.info("Υ�´���tokenId����ȡʧ�ܣ�");
//				request.setAttribute("mess","ϵͳ�쳣��tokenId,��ȡ����");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//		}
//		BreakRulesReq b=new BreakRulesReq(plateNumber,plateNumberType,carFrameNum,engineNumber,tokenId);
//		BreakRulesResp obj=(BreakRulesResp)PublicMethod.breakrulesMethod(BreakRulesResp.class,b,PublicMethod.QUERY_BREAKRULES);
//		if(obj!=null && !"".equals(obj) && obj.getCode()==0){
//			list=obj.getPeccancyInfos();
//			for(PeccancyInfoModel m:list)
//			{
//				//�Ƿ�ɴ���
//				int states=getstate(m.getAgent(),m.getWoState());
//				m.setIsdaibai(states+"");
//				
//				//���ݵ��ƻ�ȡָ����������
//				if(!"382".equals(userSession.getUsersite())){//��ͨ�����
//					//���ݲ�ѯ�Ĵ���Ѽӿ�
//					if(m.getReplaceMoney()==null || "".equals(m.getReplaceMoney()) || "0".equals(m.getReplaceMoney()) ){ //���û�д���ѣ�ȡĬ��ֵ
//						zshMoney=DBConfig.getInstance().getJtfkdefault()/1000;
//					}else{  //�д���ѣ��ڼ���ָ������
//						zshMoney=Integer.parseInt(m.getReplaceMoney())+(DBConfig.getInstance().getJtfkfloat()/1000);
//						//��ѯ���ش���� ���� 25Ԫ�����������
//						if(Integer.parseInt(m.getReplaceMoney())>25){
//							m.setIsdaibai("4");
//						}
//					}
//				}
//				m.setMyPoundage(zshMoney+"".trim());
//				
//				//��ʯ�� �����  ���ɽ�
//				if("382".equals(userSession.getUsersite())){
//					int ca=0;
//					if(m.getLateFee()!=null && !"".equals(m.getLateFee())){
//						ca=(int)Math.ceil(Double.parseDouble(m.getLateFee()+""));
//					}
//					m.setNew_LateFee(ca+"");//���ɽ�
//				}else{
//					m.setNew_LateFee(m.getLateFee());//���ɽ�
//				}
//				
//				//���ڸ�ʽ
//			    m.setPecDate(m.getPecDate()!=null?Tools.timestamp2String(Long.parseLong(m.getPecDate())):"");
//			    m.setCreateDate(m.getCreateDate()!=null?Tools.timestamp2String(Long.parseLong(m.getCreateDate())):"");
//			    m.setUpdateDate(m.getUpdateDate()!=null?Tools.timestamp2String(Long.parseLong(m.getUpdateDate())):"");
//			  list.add(m);
//			}
//			request.setAttribute("arrylist",list);
//			return new ActionForward("/business/catmanageList.jsp");
//		}else{
//			Log.info("Υ�´������ؽ����ʶ code!=0������");
//			request.setAttribute("mess","��Υ�¼�¼��");
//			return new ActionForward("/business/catmanage.jsp");
//		}
//	}
//	
//	/** ��ͨ���� �µ�
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return null;
//	 * @throws Exception
//	 */
//	public ActionForward insertMessage(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
//		
//		String catmessage=request.getParameter("catmessage");
//		String[] str=request.getParameterValues("infos");
//		String sumMoney=request.getParameter("sumMoney");
//		String catUserName=request.getParameter("catUserName");
//		String catUserPhone=request.getParameter("catUserPhone");     
//		String carAddress=request.getParameter("carAddress");
//		String carNameText=request.getParameter("carNameText");//������������
//		
//		String interfaceType=request.getParameter("interfaceType");//��ѯ�ӿ�
//		//����Ȩ���ж�
//		if(!"3".equals(userSession.getRoleType())){
//			request.setAttribute("mess","��û���µ���Ȩ��!");
//			return new ActionForward("/business/catmanage.jsp");
//		}
//		
//		if(catmessage==null || "".equals(catmessage) || str==null || "".equals(str) || catUserName==null || "".equals(catUserName) || "".equals(catUserPhone) || catUserPhone==null){
//			Log.info("Υ�´�������������㣬�����쳣����,�쳣����");
//			request.setAttribute("mess","��������,ϵͳ�쳣");
//			return new ActionForward("/business/catmanage.jsp");
//		}
//		String[] strs=catmessage.split("#");
//		String inString="";
//		String[] funs=null;
//		for(int i=0;i<str.length;i++){
//			if(str[i]!=null && !"".equals(str[i])){
//				String[] sasa=str[i].split("#");
//				inString=inString+sasa[0]+",";
//			}
//		}
//		//�µ��ܶ�
//		int zMoney=Integer.parseInt(sumMoney)*1000;
//		int accountMoney=-1;
//		String maxid=Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);//����id
//		//��ǰϵͳʱ��
//		String date=Tools.getNewDate();
//		//���� dealserial ��ˮ��
//		String dealserial="Order"+Tools.getNow3()+((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+100);
//		DBService dbs=null;
//		try{
//			dbs=new DBService();
//			if(inString!=null && !"".equals(inString)){
//				List ssss=dbs.getList("SELECT id,gdid FROM wht_breakrules WHERE Exp1='"+interfaceType+"' AND id IN ("+inString.substring(0,inString.length()-1)+")");
//				if(ssss!=null && !"".equals(ssss) && ssss.size()>0){
//					String abcdef="";
//					for(int k=0;k<ssss.size();k++){
//						abcdef=abcdef+((String[])ssss.get(k))[1]+"["+((String[])ssss.get(k))[0]+"],";
//					}
//					if(abcdef!=null && !"".equals(abcdef)){
//						request.setAttribute("mess","����ID[Υ��ID]: "+abcdef.substring(0,abcdef.length()-1)+" ���µ�,�����ظ��µ�");
//						return new ActionForward("/business/catmanage.jsp");
//					}
//				}
//			}else{
//				request.setAttribute("mess","δѡ��Υ�¼�¼���쳣����!");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//			
//			String getSql="SELECT childfacct,accountleft FROM wht_childfacct WHERE childfacct=(SELECT CONCAT(fundacct,'01') FROM wht_facct WHERE user_no='"+userSession.getUserno()+"')";
//			funs=dbs.getStringArray(getSql);
//			if(funs==null || "".equals(funs) || funs.length<=0){
//				request.setAttribute("mess","�˻���Ϣ�쳣��");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//			accountMoney=Integer.parseInt(funs[1])-zMoney;
//			if(accountMoney<0){
//				request.setAttribute("mess","�˻����㣡");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//		}catch(Exception e){
//			request.setAttribute("mess","ϵͳ�쳣!");
//			return new ActionForward("/business/catmanage.jsp");
//		}finally{
//			if(null!=dbs){
//				dbs.close();
//			}
//		}
//		//������Υ���µ����� 
//		String SpTransIdss=""; //Ԥ��ѯ ���ص�   �̻��ඩ����
//		String resWzid=""; // �µ� Ԥ��ѯ���ؿ��µ� Υ�� id
//		int oldMoney=0;//oldMoney  �µ��������ε� ���
//		if("2".equals(interfaceType)){
//			int cass=0;
//			for(int i=0;i<str.length;i++){
//				if(str[i]!=null && !"".equals(str[i])){
//					String[] arr=str[i].split("#");
//					float corpusa=(arr[8]==null || "".equals(arr[8]))?0f:Float.parseFloat(arr[8])*100;
//					float lateFee=(arr[9]==null || "".equals(arr[9]))?0f:Float.parseFloat(arr[9])*100;
//					float replaceMoney=(arr[10]==null || "".equals(arr[10]))?0f:Float.parseFloat(arr[10])*100;
//					//�ύ�� ���°ｻ���ܶ�
//					oldMoney=(int)(oldMoney+corpusa+lateFee+replaceMoney);
//					
//					
//					//���������ɽ�
//					float ss=(arr[21]==null || "".equals(arr[21]))?0f:Float.parseFloat(arr[21])*1000;
//					//���������ͨ�����
//					float ownMoney=(arr[20]==null || "".equals(arr[20]))?0f:Float.parseFloat(arr[20])*1000;
//					//���ͨƽ̨�µ��ܶ�
//					cass=cass+(int)((corpusa*10)+ss+ownMoney);
//				}
//			}
//			//ҳ���ܶ� �� ��̨�ܶ� �Ա�
//			if(zMoney!=cass){
//				Log.info("���°ｻͨ�����µ������׽��������,ҳ���ܺ�:"+zMoney+",��̨ѭ�������ܺ�:"+cass);
//				request.setAttribute("mess","���׽��������!");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//			
//			//�Ƹ�ͨ������
//			String CftTransId="baishi"+Tools.getNow3()+((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+100);
//			//�������ڣ�
//			String Datetime=date;
//			//�����̲��Υ��ID�����ID�ԡ�|���ָ�
//			String ViolationId=inString.substring(0,inString.length()-1).replace(",","|");
//			//�Ƿ��ʼĻ�ִ��0�����ʼģ�1���ʼģ�2��ɨ�赥�ݣ�3���������ݣ�����ʼġ�
//			String MailReceipt="0";
//			//1 -- RMB
//			String FeeType="1";
//			//�ܷ��ã���λΪ�֣������ʷ�
//			String TotalFee=oldMoney+"".trim();
//			//�û���ϵ��ʽ���������ֻ�
//			String MobileNo=catUserPhone;
//			//�������ź�4λ���Ǻ�6λ
//			String EngineNo=strs[2].substring(strs[2].length()-6,strs[2].length());
//			//ƴ�� queryText �ַ���
//			String queryText="CftTransId="+CftTransId+"&Datetime="+Datetime+"&ViolationId="+ViolationId+"&MailReceipt="+MailReceipt+"&FeeType="+FeeType+"&TotalFee="+TotalFee+"&MobileNo="+MobileNo+"&EngineNo="+EngineNo;
//			
////			��A�����md5��key��key�ɲƸ�ͨ�ṩ���õ���B����ʽ���£�
////			Vehicle=A12345&VehicleType=1&FrameNo=554545&EngineNo=21555&OwnerName=С��&Md5Key=123456
//			String B=queryText.toString()+"&Md5Key="+Md5AndBase64.KEY;
//			//��B��md5�õ�Md5Sign����д�� 
//			String Md5Sign=MD5.encode(B).toUpperCase();
////			��Md5Sign׺��A����õ���C����ʽ���£�
////			Vehicle=A12345&VehicleType=1&FrameNo=554545&EngineNo=21555&OwnerName=С��&Md5Sign=FD0CF5B71C3D3A6D651078A0B568390F
//			String c=queryText.toString()+"&Md5Sign="+Md5Sign;
//			//��C����BASE64���ܵõ�D��ʹ��GB2312��
//			String D=Md5AndBase64.base64EN(c,"GB2312");
//			
//			//��װ ���� sign ��ֵ
//			XmlHead head=new XmlHead("","");
//			StringBuffer buf=new StringBuffer();
//			buf.append("Attach="+head.getAttach());
//			buf.append("&CftMerId="+head.getCftMerId());
//			buf.append("&Command="+head.getCommand());
//			buf.append("&MerchantId="+head.getMerchantId());
//			buf.append("&QueryText="+D);
//			buf.append("&ResFormat="+head.getResFormat());
//			buf.append("&UserId="+head.getUserId());
//			buf.append("&Version="+head.getVersion());
//			buf.append("&Md5Key="+Md5AndBase64.KEY);
//			String sign=MD5.encode(buf.toString()).toUpperCase();
//			
//			BaiBean b=new BaiBean();
//			//ƴ�� http url 
//			StringBuffer buffer=new StringBuffer();
//			buffer.append(b.PREQUERY);//url
//			buffer.append("&Attach="+head.getAttach());
//			buffer.append("&CftMerId="+head.getCftMerId());
//			buffer.append("&Command="+head.getCommand());
//			buffer.append("&MerchantId="+head.getMerchantId());
//			buffer.append("&QueryText="+URLEncoder.encode(D,"GB2312"));
//			buffer.append("&ResFormat="+head.getResFormat());
//			buffer.append("&UserId="+head.getUserId());
//			buffer.append("&Version="+head.getVersion());
//			buffer.append("&Sign="+sign);
//			
//			//��������� ҵ���߼�
//			
//			String result=b.sendMsgRequest(buffer.toString());
//			Document docResult=DocumentHelper.parseText(result);
//			Element root = docResult.getRootElement();
//			//Ԥ��ѯ����״̬
//			String resCode=root.element("SpRetCode").getText();
//			//Ԥ��ѯ���ؿ��µ� Υ�� id
//			resWzid=root.element("ViolationId").getText();
//			if(resCode==null || "".equals(resCode) || !"0000".equals(resCode) || resWzid==null || "".equals(resWzid)){
//				Log.info("������Υ��Ԥ��ѯ������ֵ��SpRetCode="+resCode+",����Υ��id��"+ViolationId);
//				request.setAttribute("mess","�˶����µ�ʧ��!");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//			//Ԥ��ѯ ���ص�   �̻��ඩ����
//			SpTransIdss=root.element("SpTransId").getText();
//		}
//		//��ɫ�ж�
//		boolean fl=false;
//		if("382".equals(userSession.getUsersite())){
//			fl=true;
//		}else{
//			fl=false;
//		}
//		
//		
//		//���
//		DBService db=null;
//		try{
//			db=new DBService();
//			db.setAutoCommit(false);
//			String sql="INSERT INTO wht_bruleorder(id,woNum,userno,contactNum,name,autoCarID,carnum,dealserial,payMethod,isVisit,totalCharge," +
//					"fromCanal,createDate,workerNo,woState,spID,resultCode,carFrameNum,engineNumber,Exp1,Exp2,Exp3,Exp4) " +
//					"VALUES(" +
//					"'"+maxid+"'," +  //id
//					"'"+maxid+"'," + //woNum
//					"'"+userSession.getUserno()+"'," +//userno
//					"'"+catUserPhone+"'," +//contactNum
//					"'"+catUserName+"'," +//name
//					"123," +//autoCarID
//					"'"+strs[0]+"'," + //carnum    
//					"'"+dealserial+"'," +//dealserial
//					"4," +//payMethod
//					"2," +//isVisit
//					""+zMoney+"," +//totalCharge
//					"5," +//fromCanal
//					"'"+date+"'," +//createDate
//					"'"+dealserial+"'," +//workerNo Ĭ��Ϊ�գ����������� �µ�������
//					"100," +//woState
//					"111," +//spID
//					"'02'," +//resultCode
//					"'"+strs[2]+"'," +//carFrameNum
//					"'"+strs[3]+"'," +//engineNumber
//					"'"+strs[1]+"'," +//exp1 ��������
//					"'"+interfaceType+"'," +//exp2 �µ����� ����1  ������2   ���0 
//					"'"+carNameText+"'," +//exp2 ��������
//						"'"+carAddress+"')";//��ϵ�˵�ַ
//			db.update(sql);
//			for(int i=0;i<str.length;i++){
//				if(str[i]!=null && !"".equals(str[i])){
//					String[] arr=str[i].split("#");
//					float corpusa=(arr[8]==null || "".equals(arr[8]))?0f:Float.parseFloat(arr[8])*1000;
//					float lateFee=(arr[9]==null || "".equals(arr[9]))?0f:Float.parseFloat(arr[9])*1000;
//					float replaceMoney=(arr[10]==null || "".equals(arr[10]))?0f:Float.parseFloat(arr[10])*1000;
//					float ownMoney=(arr[20]==null || "".equals(arr[20]))?0f:Float.parseFloat(arr[20])*1000;
//					
//					//���������ɽ�
//					float ss=(arr[21]==null || "".equals(arr[21]))?0f:Float.parseFloat(arr[21])*1000;
//					//Υ�� �ܶ�
//					int kfmoney=(int)(corpusa+ss+ownMoney);
//					
//					//������
////					String barcode=getTiaoMa(arr[8]);
//					String inSql="INSERT INTO wht_breakrules(id,gdid,pecNum,pecCode,pecDesc,pecAddr," +
//							"pecDate,pecScore,corpus,lateFee,replaceMoney,ownMoney,agent,pecState,pecChanl," +
//							"createDate,updateDate,updateWorkerNo,woState,areaCode,illegalType,Exp1,Exp4,Exp5) " +
//							"VALUES("+(arr[0]==null?-1:arr[0])+",'"+maxid+"','"+(arr[1]==null?"":arr[1])+"','"+(arr[3]==null?"":arr[3])+"','"+(arr[4]==null?"":arr[4])+"','"+(arr[5]==null?"":arr[5])+"','"+(arr[6]==null?"":arr[6])+"',"+(arr[7]==null?-1:arr[7])+","+corpusa+","+lateFee+","+replaceMoney+","+ownMoney+",'"+(arr[11]==null?-1:arr[11])+"','"+(arr[12]==null?-1:arr[12])+"','" +(arr[13]==null?-1:arr[13])+"','"+(arr[14]==null?"":arr[14])+"','"+(arr[15]==null?"":arr[15])+"','"+(arr[16]==null?"":arr[16])+"','100','"+(arr[18]==null?"":arr[18])+"','"+(arr[19]==null?"":arr[19])+"','"+interfaceType+"','"+kfmoney+"','"+ss+"')";
//					db.update(inSql);
//				}
//			}
//			//������Ϣ¼��
//			String tableName="wht_acctbill_"+Tools.getNow3().substring(2,6);
//			String acctSql="INSERT INTO "+tableName+"(childfacct,dealserial," +
//					"tradeaccount,tradetime,tradefee,infacctfee,tradetype,expl,state,distime," +
//					"accountleft,tradeserial,other_childfacct,pay_type) VALUES(" +
//					"'"+funs[0]+"'," +
//					"'"+dealserial+"'," +
//					"'"+userSession.getUsername()+"'," +
//					"'"+Tools.getNow3()+"'," +
//					""+zMoney+"," +
//					""+zMoney+"," +
//					"8," +
//					"'��ͨ�����µ�'," +
//					"0," +
//					Tools.getNow3()+","+accountMoney+"," +
//					"'"+dealserial+"'," +
//					"'"+funs[0]+"'," +
//					"1)";
//			//�޸��˻����
//			String updatesql="UPDATE wht_childfacct SET accountleft="+accountMoney+" WHERE childfacct='"+funs[0]+"'";
//			db.update(acctSql);
//			db.update(updatesql);
//			db.commit();
//			
//			String isxiadan=db.getString("select interface from wht_carInterface where interface="+interfaceType+" and operationType=2");
//			//���
//			if("0".equals(isxiadan)){
//				request.setAttribute("mess","�µ��ɹ�!");
//				return new ActionForward("/business/catmanage.jsp");
//			}else if("2".equals(interfaceType) && "2".equals(isxiadan)){//������ �µ�
//				//isOrder  SpTransIdss resWzid oldMoney���µ������ν�� �֣�    zMoney���׽��(�
//				int flag=isBaiShiBang(dealserial,SpTransIdss,resWzid,oldMoney+"".trim(),zMoney);
//				if(flag==0){//�µ��ɹ�������Ϊ������״̬ 
//					String s9="�����Ƽ��� ���ƺ�:"+strs[0]+","+str.length+"��Υ��,����ɹ�,Ψһ���ߣ�400-9923-988";
//					if(YMmessage.qxtSendSMS(catUserPhone,s9)){
//						Log.info("Υ���µ����ŷ��ͳɹ�,�ļ����,���ͺ���:"+catUserPhone+",����:"+s9);
//						System.out.println("Υ���µ����ŷ��ͳɹ�,����̨���,���ͺ���:"+catUserPhone+",����:"+s9);
//					}
//					request.setAttribute("mess","����ID:"+maxid+",Υ��ID["+resWzid+"],�µ��ɹ�,��ȡ��"+Float.parseFloat((zMoney/1000)+"".trim())+"Ԫ");
//				}else if(flag==1){
//					request.setAttribute("mess","����ID:"+maxid+",Υ��ID["+resWzid+"],�µ�ʧ��,��ȡ��"+Float.parseFloat((zMoney/1000)+"".trim())+"Ԫ,�˷ѳɹ�,��鿴����!");
//				}else{
//					request.setAttribute("mess","����ID:"+maxid+",Υ��ID["+resWzid+"],�µ��쳣,��ȡ��"+Float.parseFloat((zMoney/1000)+"".trim())+"Ԫ");
//				}
//				return new ActionForward("/business/catmanage.jsp");
//			}else if("1".equals(interfaceType) && "1".equals(isxiadan)){//�����µ�
//				request.setAttribute("mess","�µ��ɹ�!");
//				return new ActionForward("/business/catmanage.jsp");
//			}else{
//				request.setAttribute("mess","�µ��ɹ�!");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//		}catch(Exception e){
//			db.rollback();
//			Log.error("��ͨ�����µ��쳣������"+e);
//			request.setAttribute("mess","ϵͳ�쳣����ϵ�ͷ���");
//			return new ActionForward("/business/catmanage.jsp");
//		}finally{
//			if(null!=db)
//				db.close();
//		}
//	}
//	
	/**��ͨ�����б�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward catMessageList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
		
		String inDate=request.getParameter("inDate");
		String endDate=request.getParameter("endDate");
		String state=request.getParameter("state");
		String plateNumberA=request.getParameter("plateNumberA");
		String plateNumberB=request.getParameter("plateNumberB");
		String catName=request.getParameter("catName");
		String userName=request.getParameter("userName");
		String whereType=request.getParameter("wheretype");//��ѯ(0) or  ����(1)
		
		String inter=request.getParameter("inter");//�ӿ����� 1����   2���°�
		
		//����excel
		if("1".equals(whereType)){
			DBService dbs=null;
			try{
				dbs=new DBService();
			String where=request.getParameter("where");
			String whereInfo=request.getParameter("whereInfo");
			
			String excelsql="SELECT id,NAME,contactNum,carnum,Exp5 AS money,createDate,woState,carFrameNum,engineNumber,Exp1,userno,Exp3 FROM wht_bruleorder WHERE 1=1 ";
			if(where!=null && !"".equals(where)){
				excelsql=excelsql+where;
			}else{
				excelsql=excelsql+" AND createDate>='"+inDate+" 00:00:00' ";
				whereInfo=whereInfo+"  ��ʼʱ�� >= "+inDate+" 00:00:00 ";
				excelsql=excelsql+" AND createDate<='"+endDate+" 23:59:59' ";
				whereInfo=whereInfo+"  �������� <= "+endDate+" 23:59:59 ";
			}
			String hql="SELECT o.id,o.name,o.contactNum,o.carnum,o.money, "+
					" o.createDate,o.woState,o.carFrameNum,o.engineNumber,(SELECT carName FROM sys_car_type WHERE id=o.Exp1) AS es, "+
					" r.id,r.pecNum,r.pecCode,r.pecDesc,r.pecAddr,r.pecDate, "+
					" r.pecScore,r.corpus,r.lateFee,r.replaceMoney,r.ownMoney, "+
					" r.agent,r.pecState,r.pecChanl,r.createDate,r.updateDate, "+
					" r.updateWorkerNo,r.woState,a.sa_name,r.illegalType,r.Exp1,o.Exp3,r.Exp4  "+
					" FROM wht_breakrules r LEFT JOIN sys_area a ON a.sa_zone=r.areaCode AND r.areaCode!='' ,( "+excelsql+" ) o "+
					" WHERE r.gdid=o.id ORDER BY o.id,o.userno,o.createDate ASC ";
			List arrss=dbs.getList(hql);
			HashMap map=new HashMap();
			for(int i=0;i<arrss.size();i++){
				String s1=((String[])arrss.get(i))[0];
				if(map.containsKey(s1)){
					int con=Integer.parseInt(map.get(s1)+"");
					map.remove(s1);
					map.put(s1,(con+1));
				}else{
					map.put(s1,1);
				}
			}
			
			request.setAttribute("excelList",arrss);
			request.setAttribute("whereInfo",whereInfo);
			request.setAttribute("map",map);
			return new ActionForward("/business/catMessageExcel.jsp");
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(dbs!=null)
					dbs.close();
			}
		}
		
		String whereStr=" ";//where ����
		String whereInfo=" ";// ���� where ����
		int index=1;
		int lastIndex=1;
	    int pagesize=15;
		if(request.getParameter("index")!=null && !"".equals(request.getParameter("index"))){
			index=Integer.parseInt(request.getParameter("index"));
		}
		if(index<=0)
			index=1;
		int count=0;
		String countSql="SELECT COUNT(*) con FROM wht_bruleorder where 1=1 ";
		String listSql="SELECT id,NAME,contactNum,carnum,Exp5,createDate,woState,carFrameNum,engineNumber,Exp2,totalCharge,userno FROM wht_bruleorder o WHERE 1=1  ";
		if(inDate!=null && !"".equals(inDate)){
			
		}else{
			inDate=Tools.getNowDateStr();
		}
		countSql=countSql+" AND createDate>='"+inDate+" 00:00:00' ";
		listSql=listSql+" AND createDate>='"+inDate+" 00:00:00' ";
		whereStr=whereStr+" AND createDate>='"+inDate+" 00:00:00' ";
		whereInfo=whereInfo+"  ��ʼʱ�� >= "+inDate+" 00:00:00 ";
		
		if(endDate!=null && !"".equals(endDate)){
			
		}else{
			endDate=Tools.getNowDateStr();
		}
		countSql=countSql+" AND createDate<='"+endDate+" 23:59:59' ";
		listSql=listSql+" AND createDate<='"+endDate+" 23:59:59' ";
		whereStr=whereStr+" AND createDate<='"+endDate+" 23:59:59' ";
		whereInfo=whereInfo+"  �������� <= "+endDate+" 23:59:59 ";
		
		if(state!=null && !"".equals(state)){
			countSql=countSql+" AND woState='"+state+"' ";
			listSql=listSql+" AND woState='"+state+"' ";
			whereStr=whereStr+" AND woState='"+state+"' ";
			whereInfo=whereInfo+" ����״̬ = "+state;
		}
		if(plateNumberB!=null && !"".equals(plateNumberB)){
			String plateNumber=plateNumberA+plateNumberB;
			countSql=countSql+" AND carnum='"+plateNumber+"'";
			listSql=listSql+" AND carnum='"+plateNumber+"' ";
			whereStr=whereStr+" AND carnum='"+plateNumber+"' ";
			whereInfo=whereInfo+" ���ƺ� = "+plateNumber+" ";
		}
		if(catName!=null && !"".equals(catName)){
			countSql=countSql+" AND name='"+catName+"'";
			listSql=listSql+" AND name='"+catName+"' ";
			whereStr=whereStr+" AND name='"+catName+"' ";
			whereInfo=whereInfo+" �������� = "+catName+" ";
		}
		if(userName!=null && !"".equals(userName)){
			countSql=countSql+" AND userno=(SELECT user_no FROM sys_user WHERE user_login='"+userName+"')";
			listSql=listSql+" AND userno=(SELECT user_no FROM sys_user WHERE user_login='"+userName+"')";
			whereStr=whereStr+" AND userno=(SELECT user_no FROM sys_user WHERE user_login='"+userName+"')";
			whereInfo=whereInfo+" ������˺� = "+userName;
		}
		if("-22".equals(inter)){
			whereInfo=whereInfo+" �ӿ����� = ȫ�� ";
//		}else if("1".equals(inter)){
//			countSql=countSql+" AND Exp2='"+inter+"'";
//			listSql=listSql+" AND Exp2='"+inter+"'";
//			whereStr=whereStr+" AND Exp2='"+inter+"'";
//			whereInfo=whereInfo+" �ӿ����� = ����";
		}else if("2".equals(inter)){
			countSql=countSql+" AND Exp2='"+inter+"'";
			listSql=listSql+" AND Exp2='"+inter+"'";
			whereStr=whereStr+" AND Exp2='"+inter+"'";
			whereInfo=whereInfo+" �ӿ����� = ���°�";
		}
		
		DBService db=null;
		List arryList=null;
		try{
			db=new DBService();
			//��ѯ
			count=db.getInt(countSql);
			if(count>0)
				lastIndex=count%pagesize==0?count/pagesize:count/pagesize+1;
			if(index>=lastIndex)
				index=lastIndex;
//			//���ǳ�������Ա
//			String role="SELECT sr_type FROM sys_role WHERE sr_id=(SELECT user_role FROM sys_user WHERE user_no='"+userSession.getUserno()+"')";
//			int s=db.getInt(role);
//			if(s!=0){
//				countSql=countSql+" AND userno='"+userSession.getUserno()+"' ";
//				listSql=listSql+" AND userno='"+userSession.getUserno()+"' ";
//			}
			listSql=listSql+" order by createDate asc limit "+(index-1)*pagesize+" , "+pagesize;
		    arryList=db.getList(listSql);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(db!=null)
				db.close();
		}
		request.setAttribute("arrList",arryList);
		request.setAttribute("inDate", inDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("plateNumberA",plateNumberA);
		request.setAttribute("plateNumberB",plateNumberB);
		request.setAttribute("catName", catName);
		request.setAttribute("userName", userName);
		request.setAttribute("inter", inter);
		request.setAttribute("state", state);
		request.setAttribute("index",index);
		request.setAttribute("lastIndex",lastIndex);
		//��ѯ����
		request.setAttribute("whereStr",whereStr);
		request.setAttribute("whereInfo",whereInfo);
		return new ActionForward("/business/catMessageArray.jsp");
	}
	/**���ݹ���ID����ѯΥ�¼�¼
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward show_breakrules(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		 response.setContentType("application/json;charset=gbk");
		 response.setCharacterEncoding("gbk");
		if (null == request.getSession().getAttribute("userSession")) {
			request.setAttribute("mess", "��¼��ʱ,�����µ�¼");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
		String gdID = request.getParameter("gdID");
		String sql="SELECT id,pecNum,pecCode,pecDesc,pecAddr,pecDate,pecScore,corpus,lateFee,replaceMoney,ownMoney,agent,pecState,pecChanl,createDate,updateDate,updateWorkerNo,woState,areaCode,illegalType,lateFee,replaceMoney,Exp3,Exp4,Exp5 FROM wht_breakrules WHERE gdid='"+gdID+"'";
		DBService db=null;
		List arryList=null;
		try{
			db=new DBService();
			arryList=db.getList(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		PrintWriter  out= response.getWriter();
		JSONArray json=JSONArray.fromObject(arryList); 
		out.print(json);
		out.flush();
		out.close();
		return null;
	}
	
	/** ����״̬��Υ��״̬�������˷ѣ�Υ���˷� ҵ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return out
	 * @throws Exception
	 */
	public ActionForward catStateHandle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		 response.setContentType("application/json;charset=gbk");
		 response.setCharacterEncoding("gbk");
		 
		 if (null == request.getSession().getAttribute("userSession")) {
				request.setAttribute("mess", "��¼��ʱ,�����µ�¼");
				return new ActionForward("/rights/wltlogin.jsp");
			}
		 JSONObject json=new JSONObject();
		 PrintWriter  out= response.getWriter();
		 SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
		 if(!"0".equals(userSession.getRoleType())){
			 json.put("msg","��û�в���Ȩ��!");
			 out.print(json.toString());
			 out.flush();
			 out.close();
			 return null;
		 }
		 
		 // type 0������״̬)   1Υ��״̬
		 String type=request.getParameter("typeA");//0������״̬)   1Υ��״̬
		 String gdid=request.getParameter("gdid");//����id
		 String wzid=request.getParameter("wzid");//Υ��id
		 String inter=request.getParameter("inter");//�ӿ�id
		 if(type!=null && !"".equals(type) && ("0".equals(type) || "1".equals(type))){ 
			 String state=request.getParameter("state");//�޸ĺ�״̬
			 String msg="";
			 String sql="";
			 String ifwoState="";//����orΥ��״̬Ϊ 101  �������޸�״̬
			 if("0".equals(type) && state!=null && !"".equals(state)){//�޸Ĺ���״̬
				 ifwoState="SELECT woState FROM wht_bruleorder WHERE id='"+gdid+"'";
				 sql="UPDATE wht_bruleorder SET woState='"+state+"' WHERE id='"+gdid+"'";
				 msg="�޸Ĺ���ID: "+gdid;
			 }else if("1".equals(type)  && state!=null && !"".equals(state)){//�޸�Υ��״̬
				 ifwoState="SELECT woState FROM wht_breakrules WHERE gdid='"+gdid+"' AND id="+wzid;
				 sql="UPDATE wht_breakrules SET woState='"+state+"' WHERE gdid='"+gdid+"' AND id="+wzid;
				 msg="�޸Ĺ���ID:"+gdid+",Υ��ID:"+wzid;
			 }else{
				 json.put("msg","δ֪״̬,�쳣����!");
				 out.print(json.toString());
				 out.flush();
				 out.close();
				 return null;
			 }
			 DBService db=null;
			 try{
				 db=new DBService();
				 String PutlicwoState=db.getString(ifwoState);
				 if("101".equals(PutlicwoState)){ //�������˷ѣ������޸�״̬
					 json.put("msg","�˶��������޸�״̬,�쳣����!");
					 out.print(json.toString());
					 out.flush();
					 out.close();
					 return null;
				 }
				 //�����޸�״̬���ж��¼�Υ���Ƿ��гɹ������˷�״̬
				 if("0".equals(type)){//����
					 String ssss=db.getString("SELECT 1 FROM wht_breakrules WHERE gdid='"+gdid+"' AND (woState=101 OR woState=4)");
					 if(ssss!=null && !"".equals(ssss)){
						 //�����޸Ĺ���״��
						 json.put("msg","�����޸Ĵ˹���״̬!");
						 out.print(json.toString());
						 out.flush();
						 out.close();
						 return null;
					 }
				 }
				 //Υ���޸�״̬���ж��ϼ� �����Ƿ��˷�״̬
				 if("1".equals(type)){//Υ��
					 String wzwoState=db.getString("SELECT woState FROM wht_bruleorder WHERE id='"+gdid+"'");
					 if("101".equals(wzwoState)){
						 json.put("msg","��Υ�²����޸�״̬,�쳣����!");
						 out.print(json.toString());
						 out.flush();
						 out.close();
						 return null;
					 }
				 }
				 
				 if(db.update(sql)>0){
					 if("1".equals(type)){//Υ��
						 
					 }else if("0".equals(type)){//���� //�޸Ĺ����¼����е�Υ��״̬
						 db.update("UPDATE wht_breakrules SET woState='"+state+"' WHERE gdid='"+gdid+"'");
					 }
					 msg=msg+" ,�޸����!";
				 }
			 }catch(Exception e){
				 Log.error("��ͨ����޸�״̬��ϵͳ�쳣������"+e);
				 msg=msg+" ,ϵͳ�쳣!";
			 }finally{
				 if(null!=db)
					 db.close();
			 }
			
			 json.put("msg",msg);
			 out.print(json.toString());
			 out.flush();
			 out.close();
			 return null;
		 }
		 // 11(�����˷�)  22(Υ���˷�)
		 if(type!=null && !"".equals(type) && ("11".equals(type) || "22".equals(type))){ 
			 String money=request.getParameter("money");//�˷ѽ��  ��
			 String sql="";//�˷ѹ���orΥ����Ϣsql
			 String updatestate="";//�˷ѹ���orΥ����Ϣ  ״̬�޸�sql
			 String updatestateAll="";//���¹��������е�  Υ��״̬
			 String ifgetwoState="";//Υ���˷ѣ��жϹ����Ƿ� �����˷�(���� woState= 4 or 101  �������˷�
			 
			 if(money==null || "".equals(money)){
				 json.put("msg","�˷ѽ��Ϊ��,�쳣����!");
				 out.print(json.toString());
				 out.flush();
				 out.close();
				 return null;
			 }
			 if("11".equals(type)){//�����˷�
				 sql="SELECT id,userno,dealserial,woState,Exp5 FROM wht_bruleorder WHERE id='"+gdid+"'";
				 updatestate="UPDATE wht_bruleorder SET woState='101' WHERE id='"+gdid+"'";
				 updatestateAll="UPDATE wht_breakrules SET woState='101' WHERE  gdid='"+gdid+"'";
			 }else if("22".equals(type)){//Υ���˷�
				 sql="SELECT w.id,g.userno,g.dealserial,w.woState,Exp4 FROM wht_breakrules w,wht_bruleorder g WHERE  w.gdid=g.id AND id="+wzid+" AND gdid='"+gdid+"'";
				 updatestate="UPDATE wht_breakrules SET woState='101' WHERE id="+wzid+" AND gdid='"+gdid+"'";
				 ifgetwoState="SELECT woState FROM wht_bruleorder WHERE id='"+wzid+"'";
			 }else{
				 json.put("msg","δ֪�˷�,�쳣����!");
				 out.print(json.toString());
				 out.flush();
				 out.close();
				 return null;
			 }
			 DBService db=null;
			 String[] strs=null;
			 try{
				 db=new DBService();
				 strs=db.getStringArray(sql);
				 if(strs==null || "".equals(strs)){
					 json.put("msg","û���ҵ��˷Ѷ���,�쳣����!");
					 out.print(json.toString());
					 out.flush();
					 out.close();
					 return null;
				 }
				 //ҳ���Ϸ����˷ѽ��� ���ݽ��Ա�
				 if(!strs[4].equals(money)){
					 json.put("msg","�˷ѽ���,�쳣����!");
					 out.print(json.toString());
					 out.flush();
					 out.close();
					 return null; 
				 }
				 //�ɹ� or ���˷ѵĶ����������˷�
				 if("4".equals(strs[3]) || "101".equals(strs[3])){
					 json.put("msg","�˶��������˷�,�쳣����!");
					 out.print(json.toString());
					 out.flush();
					 out.close();
					 return null; 
				 }
				 //Υ���˷ѣ��жϹ����Ƿ������˷� (���� woState= 4 or 101  �������˷�
				 if("22".equals(type)){
					 String gdwoState=db.getString(ifgetwoState);
					 if("4".equals(gdwoState) || "101".equals(gdwoState)){
						 json.put("msg","��Υ�²����˷�,�쳣����!");
						 out.print(json.toString());
						 out.flush();
						 out.close();
						 return null;
					 }
				 }
				 if("11".equals(type)){//�����˷ѣ��ж��¼�Υ���Ƿ����˷Ѽ�¼
					 String sssssss=db.getString("SELECT 1 FROM  wht_breakrules  WHERE  gdid='"+gdid+"' AND (woState=101 OR woState=4)");
					 if(sssssss!=null && !"".equals(sssssss)){
						 json.put("msg","�˹����������˷�!");
						 out.print(json.toString());
						 out.flush();
						 out.close();
						 return null;
					 }
				 }
				 //����userno ��ѯ�˻���Ϣ
				 String getFee="SELECT u.user_login,f.childfacct,f.accountleft FROM sys_user u,(SELECT childfacct,accountleft FROM wht_childfacct WHERE childfacct=(SELECT CONCAT(fundacct,'01') FROM wht_facct WHERE user_no='"+strs[1]+"')) f WHERE u.user_no='"+strs[1]+"'";
				 String[] fees=db.getStringArray(getFee);
				 if(fees==null || fees.length<=0){
					 json.put("msg","�˻���Ϣ������,�쳣����");
					 out.print(json.toString());
					 out.flush();
					 out.close();
					 return null;
				 }
				 String dealserial="catReturn"+Tools.getNow3()+Tools.buildRandom(5); //�˷����񶩵���
				 float accountleft=Float.parseFloat(fees[2])+Float.parseFloat(strs[4]); //�˷Ѻ�����
				 db.setAutoCommit(false);
					 //������Ϣ¼��
					String tableName="wht_acctbill_"+Tools.getNow3().substring(2,6);
					String acctSql="INSERT INTO "+tableName+"(childfacct,dealserial," +
							"tradeaccount,tradetime,tradefee,infacctfee,tradetype,expl,state," +
							"accountleft,tradeserial,other_childfacct,pay_type) VALUES(" +
							"'"+fees[1]+"'," +
							"'"+dealserial+"'," +
							"'"+fees[0]+"'," +
							"'"+Tools.getNow3()+"'," +
							""+strs[4]+"," +
							""+strs[4]+"," +
							"8," +
							"'��ͨ�����˷�(ƽ̨�˷�)'," +
							"0," +
							""+accountleft+"," +
							"'"+strs[0]+"'," +
							"'"+fees[1]+"'," +
							"2)";
					//�޸��˻����
					String updatesql="UPDATE wht_childfacct SET accountleft="+accountleft+" WHERE childfacct='"+fees[1]+"'";
					db.update(updatestate);//�޸Ĺ���orΥ��״̬ Ϊ ���˷�״̬
					if(updatestateAll!=null && !"".equals(updatestateAll) && "11".equals(type)){//���¹����µ�����Υ�� ״̬
						db.update(updatestateAll);
					}
					db.update(acctSql); //����
					db.update(updatesql); //�˻����
					db.commit();
					
					json.put("msg","�˷ѳɹ�!");
					 out.print(json.toString());
					 out.flush();
					 out.close();
					 return null;
			 }catch(Exception e){
				 db.rollback();
				 Log.error("��ͨ����˷ѣ�ϵͳ�쳣������"+e);
				 json.put("msg","ϵͳ�쳣");
				 out.print(json.toString());
				 out.flush();
				 out.close();
				 return null;
			 }finally{
				 if(null!=db)
					 db.close();
			 }
		 }
		 //�����µ�
//		 if(type!=null && !"".equals(type) && "33".equals(type)){
//			 String money=request.getParameter("money");//�˷ѽ��  ��
//			 //�жϹ�����ǰ״̬Ϊ  �����У�
//			 String gdsql="SELECT 1 FROM wht_bruleorder WHERE id='"+gdid+"' AND woState<>100";
//			 //�жϹ��� �¼�����Υ��״̬ ���Ǵ�����״̬
//			 String wzsql="SELECT 1 FROM wht_breakrules WHERE gdid='"+gdid+"' AND woState<>100";
//			 //���� ҳ���µ���� �� ���ݿ���Ա�
//			 String gdmoney="SELECT totalCharge FROM wht_bruleorder WHERE id='"+gdid+"' AND woState=100";
//			 DBService db=null;
//			 try{
//				 db=new DBService();
//				 //��ǰ�����Ƿ����
//				 String gdifelse=db.getString(gdsql);
//				 if(gdifelse!=null && !"".equals(gdifelse)){
//					 json.put("msg","����ID: "+gdid+" ,�˹����Ѵ����,�������ٴ��µ�!");
//					 out.print(json.toString());
//					 out.flush();
//					 out.close();
//					 return null;
//				 }
//				 
//				 //�����µ�Υ���Ƿ����
//				 String wzifelse=db.getString(wzsql);
//				 if(wzifelse!=null && !"".equals(wzifelse)){
//					 json.put("msg","����ID: "+gdid+" ,�¼�Υ���д����,�������ٴ��µ�!");
//					 out.print(json.toString());
//					 out.flush();
//					 out.close();
//					 return null;
//				 }
//				//�жϹ���ҳ���µ� ��������ݿ����Ƿ�һ��
//				 String mm=db.getString(gdmoney);
//				 if(mm==null || "".equals(mm) || !money.equals(mm)){
//					 json.put("msg","����ID: "+gdid+" ,�µ�����!");
//					 out.print(json.toString());
//					 out.flush();
//					 out.close();
//					 return null;
//				 }
//				 //���� �µ�����  ,����״̬��   0 �����ݿ��µ���1�ɹ� ��2 ��������ʧ�ܣ�3�ڲ��쳣ʧ��
//				 SynchronousDatas ss=new SynchronousDatas();
//				 int returnState=ss.submitOrder(Long.parseLong(gdid));
//				 String msg="����ID: "+gdid+" ,";
//				 if(returnState==0){
//					 msg="�����ݿ��µ�";
//				 }else if(returnState==1){
//					 msg="�µ��ɹ�";
//				 }else if(returnState==2){
//					 msg="��������ʧ��";
//				 }else if(returnState==3){
//					 msg="�ڲ��쳣ʧ��";
//				 }else{
//					 msg="�µ�����δ֪״̬!";
//				 }
//				 json.put("msg",msg);
//				 out.print(json.toString());
//				 out.flush();
//				 out.close();
//				 return null;
//			 }catch(Exception e){
//				 Log.error("�µ��쳣������"+e);
//				 json.put("msg","ϵͳ�쳣");
//				 out.print(json.toString());
//				 out.flush();
//				 out.close();
//			 }finally{
//				 if(null!=db)
//					 db.close();
//			 }
//			 return null;
//		 }
		return null;
	}
	
//	 /**
//	    * ��ͨ����  �Ƿ���Դ��� 
//	    * @param agent  �Ƿ�ɴ���  ���쵥״̬
//	    * @param woState
//	    * @return 1 �ɴ��� 2 �Ѿ��µ� 3 ����ɹ� 4 ���ɴ���
//	    */
//   public static int getstate(String agent,String woState){
//	   if("1".equals(agent)){
//		   if(woState==null||woState.equals("5")||woState.equals("7")||woState.equals("-99")){
//			 return 1;  
//		   }else if(woState.equals("1")||woState.equals("2")||woState.equals("3")||woState.equals("6")){
//			  return 2; 
//		   }else if(woState.equals("4")){
//			   return 3;
//		   }else{
//			   return 4;
//		   }
//	   }else{
//		   return 4;
//	   }
//   }
//
//   
//   /** ���� �����ͣ�ҵ�� ��ѯ���ýӿ�
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return null
//	 * @throws Exception
//	 */
//	public ActionForward getCarInterface(ActionMapping mapping, ActionForm form,
//				HttpServletRequest request, HttpServletResponse response)
//				throws Exception {
//	//		 response.setContentType("application/json;charset=gbk");
//			 response.setCharacterEncoding("gbk");
//			 
//			 if (null == request.getSession().getAttribute("userSession")) {
//					request.setAttribute("mess", "��¼��ʱ,�����µ�¼");
//					return new ActionForward("/rights/wltlogin.jsp");
//				}
//			 String cattype=request.getParameter("cattype");
//			 String operationType=request.getParameter("operationType");
//			 
//			 PrintWriter  out= response.getWriter();
//			 DBService db=null;
//			 try{
//				 db=new DBService();
//				 String interfacetype=db.getString("SELECT interface FROM wht_carInterface WHERE cattype='"+cattype+"' AND operationType='"+operationType+"' LIMIT 0,1");
//				 if(interfacetype!=null && !"".equals(interfacetype)){
//					 out.print(interfacetype);
//					 out.flush();
//					 out.close();
//				 }
//			 }catch(Exception ex){
//				 ex.printStackTrace();
//			 }
//			 return null;
//	   }
//	
//	/**������ �µ�
//	 * @param dealserial ������ �µ����񶩵���
//	 * @param SpTransId
//	 * @param ViolationId
//	 * @param TotalFee �µ����  ��
//	 * @param orderMoney  ����ƽ̨���׽��  �� 
//	 * @return int 1ʧ��   0�ɹ�  -1�쳣
//	 */
//	public static int isBaiShiBang(String dealserial,String SpTransId,String ViolationId,String TotalFee,int orderMoney){
//		int bool=-1;
//		
//		try{
//			String queryText="CftTransId="+dealserial+"&SpTransId="+SpTransId+"&Datetime="+Tools.getNewDate()+"&ViolationId="+ViolationId+"&MailReceipt=0&FeeType=1&TotalFee="+TotalFee+"";
////			��A�����md5��key��key�ɲƸ�ͨ�ṩ���õ���B����ʽ���£�
////			Vehicle=A12345&VehicleType=1&FrameNo=554545&EngineNo=21555&OwnerName=С��&Md5Key=123456
//			String B=queryText.toString()+"&Md5Key="+Md5AndBase64.KEY;
//			//��B��md5�õ�Md5Sign����д�� 
//			String Md5Sign=MD5.encode(B).toUpperCase();
////			��Md5Sign׺��A����õ���C����ʽ���£�
////			Vehicle=A12345&VehicleType=1&FrameNo=554545&EngineNo=21555&OwnerName=С��&Md5Sign=FD0CF5B71C3D3A6D651078A0B568390F
//			String c=queryText.toString()+"&Md5Sign="+Md5Sign;
//			//��C����BASE64���ܵõ�D��ʹ��GB2312��
//			String D=Md5AndBase64.base64EN(c,"GB2312");
//			
//			//��װ ���� sign ��ֵ
//			XmlHead head=new XmlHead("","");
//			StringBuffer buf=new StringBuffer();
//			buf.append("Attach="+head.getAttach());
//			buf.append("&CftMerId="+head.getCftMerId());
//			buf.append("&Command="+head.getCommand());
//			buf.append("&MerchantId="+head.getMerchantId());
//			buf.append("&QueryText="+D);
//			buf.append("&ResFormat="+head.getResFormat());
//			buf.append("&UserId="+head.getUserId());
//			buf.append("&Version="+head.getVersion());
//			buf.append("&Md5Key="+Md5AndBase64.KEY);
//			String sign=MD5.encode(buf.toString()).toUpperCase();
//			
//			BaiBean b=new BaiBean();
//			//ƴ�� http url 
//			StringBuffer buffer=new StringBuffer();
//			buffer.append(b.SUBMIT);//url
//			buffer.append("&Attach="+head.getAttach());
//			buffer.append("&CftMerId="+head.getCftMerId());
//			buffer.append("&Command="+head.getCommand());
//			buffer.append("&MerchantId="+head.getMerchantId());
//			buffer.append("&QueryText="+URLEncoder.encode(D,"GB2312"));
//			buffer.append("&ResFormat="+head.getResFormat());
//			buffer.append("&UserId="+head.getUserId());
//			buffer.append("&Version="+head.getVersion());
//			buffer.append("&Sign="+sign);
//			
//			String result=b.sendMsgRequest(buffer.toString());
//			Log.info("���°ｻͨ�����µ���Ӧ�ַ���result:"+result);
//			Document docResult=DocumentHelper.parseText(result);
//			Element root = docResult.getRootElement();
//			String resCode=root.element("SpRetCode").getText();
//			Log.info("���°ｻͨ�����µ���Ӧ״̬��SpRetCode="+resCode);
//			if("0000".equals(resCode)){
//				bool=0;
//			}else{
//				bool= 1;
//			}
//		}catch(Exception ex){
//			return -1;
//		}
//		
//		DBService db=null;
//		try{
//			db=new DBService();
//			if(bool==0){
//				Log.info("���°ｻͨ�����µ��ɹ����޸�����״̬Ϊ ������,dealserial="+dealserial);
//				db.setAutoCommit(false);
//				//��״̬
//				db.update("UPDATE wht_bruleorder SET resultCode='00',woState='3' WHERE Exp2=2 AND workerNo='"+dealserial+"' AND dealserial='"+dealserial+"'");
//				db.update("UPDATE wht_breakrules SET woState=3 WHERE Exp1=2 AND gdid=(SELECT id FROM wht_bruleorder  WHERE Exp2=2 AND workerNo='"+dealserial+"'  AND dealserial='"+dealserial+"')");
//				db.commit();
//				Log.info("���°ｻͨ�����µ��ɹ����޸�����״̬Ϊ �����У��޸ĳɹ�,dealserial="+dealserial);
//				return 0;
//			}else{
//				Log.info("���°ｻͨ�����µ�ʧ�ܣ�׼���˷�ҵ�񣬣���,dealserial="+dealserial);
//				//�˷�
//				String tableName="wht_acctbill_"+Tools.getNow3().substring(2,6);
//				//�µ� ��tradeaccount �û���¼�˺ţ�������childfacct �����˺�
//				String[] acc=db.getStringArray(" SELECT tradeaccount,childfacct FROM "+tableName+" WHERE tradeserial='"+dealserial+"' AND dealserial='"+dealserial+"'");
//				//���ݰ����� �µ���¼�˻���  �û��˺ţ����Ҵ��û����˻������
//				String getSql="SELECT childfacct,accountleft FROM wht_childfacct WHERE childfacct=(SELECT CONCAT(fundacct,'01') FROM wht_facct WHERE user_no=(SELECT user_no FROM sys_user WHERE user_login='"+acc[0]+"'))";
//				String[] funs=db.getStringArray(getSql);
//				if(!acc[1].equals(funs[0])){
//					Log.info("���°ｻͨ�����µ�ʧ�ܣ��˻���ƥ�䣬����,dealserial="+dealserial);
//					return -1;
//				}
//				int accountMoney=Integer.parseInt(funs[1])+orderMoney;
//				//�˷����� ��ˮ��
//				String serial="resBSB"+Tools.getNow3()+((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+100);
//				db.setAutoCommit(false);
//				//������Ϣ¼��
//				String acctSql="INSERT INTO "+tableName+"(childfacct,dealserial," +
//						"tradeaccount,tradetime,tradefee,infacctfee,tradetype,expl,state,distime," +
//						"accountleft,tradeserial,other_childfacct,pay_type) VALUES(" +
//						"'"+acc[1]+"'," +
//						"'"+serial+"'," +
//						"'"+acc[0]+"'," +
//						"'"+Tools.getNow3()+"'," +
//						""+orderMoney+"," +
//						""+orderMoney+"," +
//						"8," +
//						"'��ͨ����(ʧ���˷�)'," +
//						"0," +
//						Tools.getNow3()+","+accountMoney+"," +
//						"'"+dealserial+"'," +
//						"'"+acc[1]+"'," +
//						"2)";
//				//�޸��µ�״̬
//				db.update("UPDATE wht_bruleorder SET resultCode='03',woState='101' WHERE Exp2=2 AND workerNo='"+dealserial+"' AND dealserial='"+dealserial+"'");
//				db.update("UPDATE wht_breakrules SET woState=101 WHERE Exp1=2 AND gdid=(SELECT id FROM wht_bruleorder  WHERE Exp2=2 AND workerNo='"+dealserial+"'  AND dealserial='"+dealserial+"')");
//				//�޸��˻����
//				String updatesql="UPDATE wht_childfacct SET accountleft="+accountMoney+" WHERE childfacct='"+acc[1]+"'";
//				db.update(acctSql);
//				db.update(updatesql);
//				db.commit();
//				Log.info("���°ｻͨ�����µ�ʧ�ܣ��˷ѳɹ�����,dealserial="+dealserial);
//				return 1;
//			}
//		}catch(Exception e){
//			db.rollback();
//			Log.error("����ͨ��ͨ�����µ��쳣����"+e);
//			return -1;
//		}finally{
//			if(db!=null){
//				db.close();
//			}
//		}
//	}

	public static void main(String[] ar){
		System.out.println(Math.ceil(0.1));
	}

}
