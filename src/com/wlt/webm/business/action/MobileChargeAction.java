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
 * 全国话费充值
 * 
 * @author caiSJ
 * 
 */
public class MobileChargeAction extends DispatchAction {
	/**
	 * 全国充值
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 跳转页面
	 * @throws Exception
	 */
	public ActionForward tzFill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SysUserForm loginUser = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		if (null == loginUser) {
			request.setAttribute("mess", "登录超时,请重新登录");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		if (!loginUser.getRoleType().equals("3")) {
			request.setAttribute("mess", "当前用户类型不支持交易");
			return new ActionForward("/business/cmccbusiness.jsp");
		}
		if (!Tools.validateTime()) {
			request.setAttribute("mess", "23:50到00:10不允许交易");
			return new ActionForward("/business/cmccbusiness.jsp");
		}
		String phonePid = request.getParameter("prodId");
		String phone = request.getParameter("tradeObject").replaceAll(" ", "");
		String phoneType = request.getParameter("telType");
		String payfee = request.getParameter("money1");
		Log.info("全国话费充值:"+phonePid+"  "+phone+"   "+phoneType+"   "+payfee);
		if (phonePid == null || "".equals(phonePid) || phone == null
				|| "".equals(phone) || phoneType == null
				|| "".equals(phoneType) || payfee == null || "".equals(payfee)) {
			log
					.info("全国充值：程序异常:phonePid:" + phonePid + "    phone:"
							+ phone + "      phoneType:" + phoneType
							+ "      payfee:" + payfee);
			request.setAttribute("mess", "系统异常,重新登录!");
			return new ActionForward("/business/cmccbusiness.jsp");
		}
		if (payfee.indexOf("-") != -1) {
			request.setAttribute("mess", "金额必须为正数!");
			return new ActionForward("/business/cmccbusiness.jsp");
		}

		String userPid = loginUser.getUsersite();
		String parentID = loginUser.getParent_id();
		String userno = loginUser.getUserno();
		String seqNo = Tools.getStreamSerial(phone);
		String flag = "0";
		if (payfee.indexOf("-") != -1) {
			request.setAttribute("mess", "交易金额不合法!");
			return new ActionForward("/business/cmccbusiness.jsp");
		}
		double fee = FloatArith.yun2li(payfee);
		DBService db = null;
		try {
			db = new DBService();
			if (AcctBillBean.getStatus(parentID)) {// 直营
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
				request.setAttribute("mess", "充值失败,佣金明细不存在");
				return new ActionForward("/business/cmccbusiness.jsp");
			}
			if(phoneType.equals("0")&&phonePid.equals("35")){//广东电信走讯源或者君宝
				String serialNo= "wh09015" + Tools.getNow3().substring(6)+ Tools.buildRandom(2) + Tools.buildRandom(3); 
				int n=bp.XYdxFill(parentID, userno, phonePid, userPid, "0",
						phone, seqNo, fee,loginUser.getSa_zone(),loginUser.getUsername(),"01",serialNo,null);
				if(n==0){
					request.setAttribute("mess", "充值成功");
					return new ActionForward("/business/cmccbusiness.jsp");
				}else if(n==1||n==3||n==-2||n==6){
					request.setAttribute("mess", "充值失败");
					return new ActionForward("/business/cmccbusiness.jsp");
				}else if(n==10){
					request.setAttribute("mess", "一分钟内不允许重复交易");
					return new ActionForward("/business/cmccbusiness.jsp");
				}else if(n==-5){
					request.setAttribute("mess", "充值失败,余额不足");
					return new ActionForward("/business/cmccbusiness.jsp");
				}else if(n==-1){
					request.setAttribute("mess", "系统繁忙,请稍后充值!");
					return new ActionForward("/business/cmccbusiness.jsp");
				}else if(n==10){
					request.setAttribute("mess", "佣金比不存在请联系客服");
					return new ActionForward("/business/cmccbusiness.jsp");
				}else {
					request.setAttribute("mess", "处理中，请联系客服");
					return new ActionForward("/business/cmccbusiness.jsp");
				}
			}else{
			String ip = com.wlt.webm.util.Tools.getIpAddr(request);
			int k = bp.nationFill(parentID, userno, phonePid, userPid,
					phoneType, phone, seqNo, fee, result, loginUser
							.getSa_zone(), db, loginUser.getUsername(), ip,
					null, flag);
			Log.info("订单号:" + seqNo + " 充值结果=" + k);
			// 订单号 交易时间 代办户账户 商品名称 充值号码 付款金额 打印小票需要的数据
			String str = seqNo + ";" + Tools.getNewDate() + ";"
					+ loginUser.getUsername() + ";话费充值;" + phone + ";" + payfee;
			if (k == 0) {
				request.setAttribute("mess", "充值成功");
				request.setAttribute("str", str);
				return new ActionForward("/business/cmccbusiness.jsp");
			} else if (k > 20) {
				request.setAttribute("mess", "充值成功,用户话费余额:" + ((float) k)
						/ 1000);
				request.setAttribute("str", str);
				return new ActionForward("/business/cmccbusiness.jsp");
			} else if (k == 1 || k == -1||k==6) {
				request.setAttribute("mess", "充值失败");
				return new ActionForward("/business/cmccbusiness.jsp");
			}else if(k==-5){
				request.setAttribute("mess", "充值失败,余额不足");
				return new ActionForward("/business/cmccbusiness.jsp");
			} else if (k == 2 || k == -2 || k == 3) {
				request.setAttribute("mess", "交易处理中,请联系客服");
				return new ActionForward("/business/cmccbusiness.jsp");
			} else if (k == 10) {
				request.setAttribute("mess", "一分钟内不允许重复交易");
				return new ActionForward("/business/cmccbusiness.jsp");
			} else {
				request.setAttribute("mess", "交易异常请联系客服");
				return new ActionForward("/business/cmccbusiness.jsp");
			}
			}
		} catch (Exception e) {
			Log.error("全国充值：" + e.toString());
			request.setAttribute("mess", "系统繁忙请稍后再试");
			return new ActionForward("/business/cmccbusiness.jsp");
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}

	/**
	 * 全国电信固话充值
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
			request.setAttribute("mess", "登录超时,请重新登录");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		if (!loginUser.getRoleType().equals("3")) {
			request.setAttribute("mess", "当前用户类型不支持交易");
			return new ActionForward("/rights/fixedline.jsp");
		}
		if (!Tools.validateTime()) {
			request.setAttribute("mess", "23:50到00:10不允许交易");
			return new ActionForward("/rights/fixedline.jsp");
		}
		String phonePid = request.getParameter("prodId");
		String phone = request.getParameter("tradeObject");
		String phoneType = request.getParameter("telType");
		String payfee = request.getParameter("money1");

		if (phonePid == null || "".equals(phonePid) || phone == null
				|| "".equals(phone) || phoneType == null
				|| "".equals(phoneType) || payfee == null || "".equals(payfee)) {
			Log.info("全国电信固话充值:程序异常;     phonePid:" + phonePid + "     phone:"
					+ phone + "     phoneType:" + phoneType + "     payfee:"
					+ payfee);
			request.setAttribute("mess", "系统异常,请重新登录!");
			return new ActionForward("/rights/fixedline.jsp");
		}
		if (payfee.indexOf("-") != -1) {
			request.setAttribute("mess", "金额必须为正数!");
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
			if (AcctBillBean.getStatus(parentID)) {// 直营
				flag = "1";
			}
			BiProd bp = new BiProd();
			String[] result = bp.getEmploy(db, phoneType, userPid, phonePid,
					userno, flag, Integer.parseInt(payfee), Integer
							.parseInt(parentID), 0);
			if (null == result) {
				request.setAttribute("mess", "充值失败,佣金明细不存在");
				return new ActionForward("/rights/fixedline.jsp");
			}
			String ip = com.wlt.webm.util.Tools.getIpAddr(request);
			int k = bp.nationFillGH(parentID, userno, phonePid, userPid,
					phoneType, phone, seqNo, fee, result, loginUser
							.getSa_zone(), db, loginUser.getUsername(), ip,
					null, flag);
			String strA = seqNo + ";" + Tools.getNewDate() + ";"
			+ loginUser.getUsername() + ";电信充值;" + phone + ";" + payfee;
			Log.info("订单号:" + seqNo + " 充值结果=" + k);
			if (k == 0) {
				request.setAttribute("mess", "充值成功");
				request.setAttribute("strA", strA);
				return new ActionForward("/rights/fixedline.jsp");
			} else if (k > 20) {
				request.setAttribute("mess", "充值成功,用户话费余额:" + ((float) k)
						/ 1000);
				request.setAttribute("strA", strA);
				return new ActionForward("/rights/fixedline.jsp");
			} else if (k == 1 || k == -1) {
				request.setAttribute("mess", "充值失败");
				return new ActionForward("/rights/fixedline.jsp");
			} else if (k == 2 || k == -2 || k == 3) {
				request.setAttribute("mess", "交易处理中,请联系客服");
				return new ActionForward("/rights/fixedline.jsp");
			} else if (k == 10) {
				request.setAttribute("mess", "一分钟内不允许重复交易");
				return new ActionForward("/rights/fixedline.jsp");
			}else if(k==-5){
				request.setAttribute("mess", "充值失败,余额不足");
				return new ActionForward("/rights/fixedline.jsp");
			} else {
				request.setAttribute("mess", "交易异常请联系客服");
				return new ActionForward("/rights/fixedline.jsp");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("mess", "系统繁忙请稍后再试");
			return new ActionForward("/rights/fixedline.jsp");
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}

	/**
	 * 冲正
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
		// 判断冲正数量是否用完
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
		if (buyid.equals("3")) {// 移动
			tradeNo = "06013";
			serialNo = "XY0315012880537" + Tools.getNow().substring(2)
					+ Tools.buildRandom(2) + Tools.buildRandom(3);
		} else if (buyid.equals("4")) {// 联通
			tradeNo = "06015";
			serialNo = "XY0418682022602" + Tools.getNow().substring(2)
					+ Tools.buildRandom(2) + Tools.buildRandom(3);
		} else if (buyid.equals("5")) {// 电信
			tradeNo = "06012";
			serialNo = "";
		} else if (buyid.equals("7")) {// 华鸿
			tradeNo = "06022";
			serialNo = "wh06022" + Tools.getNow3().substring(6)
					+ Tools.buildRandom(2) + Tools.buildRandom(3); // 华鸿移动
		} else if (buyid.equals("9")) {// 讯源
			tradeNo = "06025";
			serialNo = "wh06025" + Tools.getNow3().substring(6)
					+ Tools.buildRandom(2) + Tools.buildRandom(3);
		} else if (buyid.equals("13")) {// 亿快 http 冲正
			String phoneNum="";
			String money="";
			DBService db=null;
			try{
				db=new DBService();
				String[] s=db.getStringArray("select tradeobject,fee from wht_orderform_"+Tools.getNow3().substring(2,6)+" where tradeserial='"+ordid+"'");
				if(s==null || "".equals(s) || s.length<=0){
					return new ActionForward("/business/order.do?method=listReverse&msg=3");//系统异常
				}
				phoneNum=s[0];
				money=s[1];
			}catch(Exception ex){
				Log.error(" 亿快 http 冲正，系统异常!");
				return new ActionForward("/business/order.do?method=listReverse&msg=3");//系统异常
			}finally{
				if(db!=null)
					db.close();
			}
			
			String strState=NineteenRecharge.reverseServices(ordid,phoneNum, ((int)(Float.parseFloat(money)/1000))+"");
			if("000".equals(strState)){
				return new ActionForward("/business/order.do?method=listReverse&msg=0");//成功
			}else if("001".equals(strState)){
				return new ActionForward("/business/order.do?method=listReverse&msg=1");//失败
			}else{
				return new ActionForward("/business/order.do?method=listReverse&msg=3");//系统异常
			}
		}else if (buyid.equals("14")) {// 恒通移动
			tradeNo = "06026";
			serialNo = ((char)(new Random().nextInt(26) + (int)'a'))+""+((int)(Math.random()*1000)+1000)+""+((int)(Math.random()*100)+100);
		}else if (buyid.equals("15")) {// 省联通  冲正
			tradeNo = "06027";
			serialNo = Tools.getNow()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
		}else if (buyid.equals("18")) {// 君宝移动冲正
			String sts=BiProd.getWriteOff(ordid);
			if(sts==null){
				return new ActionForward("/business/order.do?method=listReverse&msg=3");
			}else{
				String[] sa=sts.split("#");
				if("".equals(sa[4]) || "@@Ab@@".equals(sa[4])){
					//查询上游订单
					tradeNo = "09027";
					serialNo=sa[0];
				}else{
					//冲正
					tradeNo = "06028";
				}
			}
		}else if (buyid.equals("19")) {// 君宝电信冲正
			String sts=BiProd.getWriteOff(ordid);
			if(sts==null){
				return new ActionForward("/business/order.do?method=listReverse&msg=3");
			}else{
				String[] sa=sts.split("#");
				if("".equals(sa[4]) || "@@Ab@@".equals(sa[4])){
					//查询上游订单
					tradeNo = "09027";
					serialNo=sa[0];
				}else{
					//冲正
					tradeNo = "06029";
				}
			}
		}else if (buyid.equals("22")) {// 银盛移动冲正
			tradeNo = "06030";
		}else if(buyid.equals("24")){//乐充冲正
			int rsCode=MobileRecharge.Correct_Confim(ordid);
			if(rsCode==0){
				return new ActionForward("/business/order.do?method=listReverse&msg=0");//成功
			}else if(rsCode==-1){
				return new ActionForward("/business/order.do?method=listReverse&msg=1");//失败
			}else{
				return new ActionForward("/business/order.do?method=listReverse&msg=3");//系统异常
			}
		}else if(buyid.equals("28")){//连胜接口
			String phoneNum="";
			String money="";
			DBService db=null;
			try{
				db=new DBService();
				String[] s=db.getStringArray("select tradeobject,fee,userno from wht_orderform_"+Tools.getNow3().substring(2,6)+" where tradeserial='"+ordid+"'");
				if(s==null || "".equals(s) || s.length<=0){
					return new ActionForward("/business/order.do?method=listReverse&msg=1");//失败
				}
				phoneNum=s[0];
				money=s[1];
				String strState=Mobile_Interface.Correct(ordid,((int)(Float.parseFloat(money)/1000))+"",phoneNum,s[2]);
				if("0".equals(strState)){
					return new ActionForward("/business/order.do?method=listReverse&msg=0");//成功
				}else if("-1".equals(strState)){
					return new ActionForward("/business/order.do?method=listReverse&msg=1");//失败
				}else{
					return new ActionForward("/business/order.do?method=listReverse&msg=3");//系统异常
				}
			}catch(Exception ex){
				Log.error("连胜接口冲正异常,订单号："+ordid+",,,ex："+ex);
				return new ActionForward("/business/order.do?method=listReverse&msg=1");//失败
			}finally{
				if(db!=null)
					db.close();
			}
		}else {
			return new ActionForward("/business/order.do?method=listReverse&msg=4");
		}
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// 有内容
		msg.setMsgFrom2("0203");
		msg.setServNo("00");
		msg.setTradeNo(tradeNo);
		Map<String, String> content = new HashMap<String, String>();
		content.put("BackSeq", ordid);
		content.put("BackType", "0");
		content.put("serialNo", serialNo);
		msg.setContent(content);
		if (!PortalSend.getInstance().sendmsg(msg)) {
			return new ActionForward("/business/order.do?method=listReverse&msg=1");//发送消息失败,充值失败
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
			if("09027".equals(tradeNo)){//查询上游订单号
				return new ActionForward("/business/order.do?method=listReverse&msg=6");
			}
			return new ActionForward("/business/order.do?method=listReverse&msg=0");
		} else {
			return new ActionForward("/business/order.do?method=listReverse&msg=1");
		}
	}

	/**
	 * 退费
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
		String ordid = request.getParameter("ordid"); // 订单编号
		String ordno = request.getParameter("ordno"); // 时间
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
			
			//判断订单状态，，，1失败，5冲正，7已退费 ，，不处理
			String isState=db.getString("SELECT state FROM "+orderTable+" WHERE tradeserial='" + ordid + "' AND state IN (1,5,7)");
			if(isState!=null && !"".equals(isState)){
				Log.info("退费，，，订单tradeserial="+ordid+",状态为 state="+isState+",不允许再次退费!");
				return new ActionForward("/business/order.do?method=listReverse&msg=5");
			}
			String isState_=db.getString("SELECT 1 FROM "+acctTable+" WHERE tradeserial='" + ordid + "' AND pay_type=2 AND tradetype!=15");
			if(null!=isState_ && !"".equals(isState_)){
				Log.info("退费，，，订单tradeserial="+ordid+",状态为 state="+isState_+",账务已退过费用,不允许重复退费!");
				return new ActionForward("/business/order.do?method=listReverse&msg=5");
			}
			
			db.setAutoCommit(false);
			// 修改订单为失败
			String sql1 = "update " + orderTable
					+ " set state=1 where tradeserial='" + ordid + "'";
			/**
			 * 收支未分开的 String sql2="update "+acctTable+
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
			// 收支分开
			String newtime = Tools.getNow();
			String tableName = "wht_acctbill_" + newtime.substring(2, 6);
			String userleft0 = "select accountleft from wht_childfacct where childfacct='"
					+ str[0] + "'";
			int userleft = db.getInt(userleft0);
			String fialAcct1 = Tools.getAccountSerial(newtime, "DTF" + str[2]);
			// childfacct,infacctfee,tradeaccount,tradetype
			Object[] acctObj1 = { null, str[0], fialAcct1, str[2], newtime,
					Integer.parseInt(str[1]), Integer.parseInt(str[1]), str[3],
					"平台退费", 0, newtime, userleft + Integer.parseInt(str[1]),
					ordid, str[0], 2 };
			db.logData(15, acctObj1, tableName);
			String sql3 = "update wht_childfacct set accountleft=accountleft+"
					+ Integer.parseInt(str[1]) + " where childfacct='" + str[0]
					+ "'";
			db.update(sql3);
			// 查询上级
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
						Integer.parseInt(str1[1]), str[3], "交易撤销", 0, newtime,
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
			Log.error("SMP话费退款出错:", ex);
			return new ActionForward(
					"/business/order.do?method=listReverse&msg=1");
		} finally {
			if (null != db) {
				db.close();
			}
		}
		Log.info("订单退费成功，订单号：" + ordid + "    操作时间：" + Tools.getNow3());
		return new ActionForward("/business/order.do?method=listReverse&msg=0");
	}

	/**
	 * 代办户冲正
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
		// 判断冲正数量是否用完
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		String userid = userForm.getUserno();
		DBService db = null;
		String[] str = null;
		String tradeobject = request.getParameter("tradeObject");
		String phone = request.getParameter("ids");
		if (null == phone) {
			request.setAttribute("mess", "冲正信息不能为空");
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
					request.setAttribute("mess", "当月冲正次数已用完,无法冲正");
					return new ActionForward("/business/daibanhuRevert.jsp");
				}
			} else {
				if (k >= count) {
					request.setAttribute("mess", "当月冲正次数已用完,无法冲正");
					return new ActionForward("/business/daibanhuRevert.jsp");
				}
			}

			str = phone.split("#");
			if (null == str || str.length != 3) {
				request.setAttribute("mess", "不存在该笔交易或者已超过24小时");
				return new ActionForward("/business/daibanhuRevert.jsp");
			}
			
			//亿快 判断是否已经冲正过，，10分钟
			String yikuai_reverse="SELECT 1 FROM wht_yikuai_reverse WHERE phone='"+tradeobject+"'  AND TIMESTAMPDIFF(MINUTE, datetimes, '"+Tools.getNow3()+"')<10 ";
			String reverses=db.getString(yikuai_reverse);
			if(reverses!=null && !"".equals(reverses)) //冲正表中有记录，已发起冲正
			{
				request.setAttribute("mess", "已发起过一次冲正，请稍后，，，");
				return new ActionForward("/business/daibanhuRevert.jsp");
			}
			
			String ordid = str[0];
			String buyid = str[1];
			String tradeFee = str[2];
			String tradeNo = null;
			String serialNo = null;
			String seqNo = Tools.getStreamSerial("userfx"
					+ Tools.buildRandom(4));
			if (buyid.equals("3")) {// 移动
				tradeNo = "06013";
				serialNo = "XY0315012880537" + Tools.getNow().substring(2)
						+ Tools.buildRandom(2) + Tools.buildRandom(3);
			} else if (buyid.equals("4")) {// 联通
				tradeNo = "06015";
				serialNo = "XY0418682022602" + Tools.getNow().substring(2)
						+ Tools.buildRandom(2) + Tools.buildRandom(3);
			} else if (buyid.equals("5")) {// 电信
				tradeNo = "06012";
				serialNo = "";
			} else if (buyid.equals("7")) {// 华鸿
				tradeNo = "06022";
				serialNo = "wh06022" + Tools.getNow3().substring(6)
						+ Tools.buildRandom(2) + Tools.buildRandom(3); // 华鸿移动
			} else if (buyid.equals("9")) {// 讯源
				tradeNo = "06025";
				serialNo = "wh06025" + Tools.getNow3().substring(6)
						+ Tools.buildRandom(2) + Tools.buildRandom(3);
			} else if (buyid.equals("13")) {// 亿快 http 冲正
				String strState=NineteenRecharge.reverseServices(ordid,tradeobject, ((int)(Float.parseFloat(phone.split("#")[2])/1000))+"");
				if("000".equals(strState)){
					request.setAttribute("mess", "冲正成功!");//成功
				}else if("001".equals(strState)){
					request.setAttribute("mess", "冲正失败");//失败
				}else{
					request.setAttribute("mess", "系统异常");//系统异常
				}
				return new ActionForward("/business/daibanhuRevert.jsp");
			} else if (buyid.equals("14")) {// 恒通移动
				tradeNo = "06026";
				serialNo = ((char)(new Random().nextInt(26) + (int)'a'))+""+((int)(Math.random()*1000)+1000)+""+((int)(Math.random()*100)+100);
			}else if (buyid.equals("15")) {// 省联通  冲正
				tradeNo = "06027";
				serialNo = Tools.getNow()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
			}else if (buyid.equals("18")) {// 君宝  冲正
				String sts=BiProd.getWriteOff(ordid);
				if(sts==null){
					request.setAttribute("mess", "冲正失败");//失败
					return new ActionForward("/business/daibanhuRevert.jsp");
				}else{
					String[] sa=sts.split("#");
					if("".equals(sa[4]) || "@@Ab@@".equals(sa[4])){
						//查询上游订单
						tradeNo = "09027";
						serialNo=sa[0];
					}else{
						//冲正
						tradeNo = "06028";
					}
				}
			}else if (buyid.equals("19")) {// 君宝电信冲正
				String sts=BiProd.getWriteOff(ordid);
				if(sts==null){
					request.setAttribute("mess", "冲正失败");//失败
					return new ActionForward("/business/daibanhuRevert.jsp");
				}else{
					String[] sa=sts.split("#");
					if("".equals(sa[4]) || "@@Ab@@".equals(sa[4])){
						//查询上游订单
						tradeNo = "09027";
						serialNo=sa[0];
					}else{
						//冲正
						tradeNo = "06029";
					}
				}
			}else if (buyid.equals("22")) {// 银盛移动冲正
				tradeNo = "06030";
			}else if(buyid.equals("24")){//乐充冲正
				int rsCode=MobileRecharge.Correct_Confim(ordid);
				if(rsCode==0){
					request.setAttribute("mess", "冲正成功!");//成功
				}else if(rsCode==-1){
					request.setAttribute("mess", "冲正失败");//失败
				}else{
					request.setAttribute("mess", "系统异常");//系统异常
				}
				return new ActionForward("/business/daibanhuRevert.jsp");
			}else if(buyid.equals("28")){//连胜接口
				String[] s=db.getStringArray("select tradeobject,fee,userno from wht_orderform_"+Tools.getNow3().substring(2,6)+" where tradeserial='"+ordid+"'");
				if(s==null || "".equals(s) || s.length<=0){
					request.setAttribute("mess", "系统异常");//系统异常
					return new ActionForward("/business/daibanhuRevert.jsp");
				}
				String phoneNum=s[0];
				String money=s[1];
				String strState=Mobile_Interface.Correct(ordid,((int)(Float.parseFloat(money)/1000))+"",phoneNum,s[2]);
				if("0".equals(strState)){
					request.setAttribute("mess", "冲正成功!");//成功
				}else if("-1".equals(strState)){
					request.setAttribute("mess", "冲正失败");//失败
				}else{
					request.setAttribute("mess", "系统异常");//系统异常
				}
				return new ActionForward("/business/daibanhuRevert.jsp");
			}else {
				request.setAttribute("mess", "该号码不支持冲正");
				return new ActionForward("/business/daibanhuRevert.jsp");
			}
			TradeMsg msg = new TradeMsg();
			msg.setSeqNo(seqNo);
			msg.setFlag("0");// 有内容
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
				request.setAttribute("mess", "冲正失败");
				return new ActionForward("/business/daibanhuRevert.jsp");// 发送消息失败
				// ,
				// 充值失败
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
				Log.info("冲正处理中:" + seqNo);
				request.setAttribute("mess", "冲正处理中");
				return new ActionForward("/business/daibanhuRevert.jsp");// 发送消息失败
				// ,
				// 充值失败
			}
			String code = rsMsg.getRsCode();
			Log.info("代办点冲正状态:" + code);
			if ("000".equals(code)) {
				if("09027".equals(tradeNo)){//查询上游订单号
					request.setAttribute("mess", "请稍后发起冲正!");
					return new ActionForward("/business/daibanhuRevert.jsp");
				}
				db.update("insert into wlt_revertlimit values(null,'" + userid
						+ "','" + time.substring(0, 6) + "')");
				request.setAttribute("mess", "冲正成功");
				return new ActionForward("/business/daibanhuRevert.jsp");// 发送消息失败
				// ,
				// 充值失败
			} else {
				request.setAttribute("mess", "冲正失败");
				return new ActionForward("/business/daibanhuRevert.jsp");// 发送消息失败
				// ,
				// 充值失败
			}
		} catch (Exception e) {
			Log.error("用户冲正异常:" + e.toString());
			request.setAttribute("mess", "系统繁忙,请稍后再试");
			return new ActionForward("/business/daibanhuRevert.jsp");
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}

	/**
	 * 页面列表冲正，，，
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
		// 判断冲正数量是否用完
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		String userid = userForm.getUserno();
		DBService db = null;
		
		String orderId=request.getParameter("orderid");
		String datetime=request.getParameter("datetime");
		if (null == orderId || datetime==null || "".equals(orderId) || "".equals(datetime)) {
			out.write("-5");//缺少参数;
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
					out.write("-2");//当月冲正次数已用完,无法冲正
					out.flush();
					out.close();
					return null;
				}
			} else {
				if (k >= count) {
					out.write("-2");//当月冲正次数已用完,无法冲正
					out.flush();
					out.close();
					return null;
				}
			}
			String orderSelect="SELECT tradeserial,buyid,tradeobject,fee FROM wht_orderform_"+datetime.substring(2,6)+" WHERE id='"+orderId+"' AND  tradetime>'"+Tools.getOtherTime(-1)+"'";
			String[] strList=db.getStringArray(orderSelect, null);
			if (strList==null ) {
				out.write("-3");//不存在该笔交易或者已超过24小时
				out.flush();
				out.close();
				return null;
			}
			
			//亿快 判断是否已经冲正过，，
			String yikuai_reverse="SELECT 1 FROM wht_yikuai_reverse WHERE phone='"+strList[2]+"' AND TIMESTAMPDIFF(MINUTE, datetimes, '"+Tools.getNow3()+"')<10 ";
			String reverses=db.getString(yikuai_reverse);
			if(reverses!=null && !"".equals(reverses)) //冲正表中有记录，已发起冲正
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
			if (buyid.equals("3")) {// 移动
				tradeNo = "06013";
				serialNo = "XY0315012880537" + Tools.getNow().substring(2)
						+ Tools.buildRandom(2) + Tools.buildRandom(3);
			} else if (buyid.equals("4")) {// 联通
				tradeNo = "06015";
				serialNo = "XY0418682022602" + Tools.getNow().substring(2)
						+ Tools.buildRandom(2) + Tools.buildRandom(3);
			} else if (buyid.equals("5")) {// 电信
				tradeNo = "06012";
				serialNo = "";
			} else if (buyid.equals("7")) {// 华鸿
				tradeNo = "06022";
				serialNo = "wh06022" + Tools.getNow3().substring(6)
						+ Tools.buildRandom(2) + Tools.buildRandom(3); // 华鸿移动
			} else if (buyid.equals("9")) {// 讯源
				tradeNo = "06025";
				serialNo = "wh06025" + Tools.getNow3().substring(6)
						+ Tools.buildRandom(2) + Tools.buildRandom(3);
			}  else if (buyid.equals("13")) {// 亿快 http 冲正
				String strState=NineteenRecharge.reverseServices(ordid,tradeFee, (int)(Float.parseFloat(accountFee)/1000)+"");
				if("000".equals(strState)){
					out.write("0");//冲正成功
				}else if("001".equals(strState)){
					out.write("-5");//冲正失败
				}else{
					out.write("-1");//系统异常
				}
				out.flush();
				out.close();
				return null;
			} else if (buyid.equals("14")) {// 恒通移动
				tradeNo = "06026";
				serialNo = ((char)(new Random().nextInt(26) + (int)'a'))+""+((int)(Math.random()*1000)+1000)+""+((int)(Math.random()*100)+100);
			}else if (buyid.equals("15")) {// 省联通  冲正
				tradeNo = "06027";
				serialNo = Tools.getNow()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
			}else if (buyid.equals("18")) {// 君宝  冲正
				String sts=BiProd.getWriteOff(ordid);
				if(sts==null){
					out.write("-5");
					out.flush();
					out.close();
					return null;
				}else{
					String[] sa=sts.split("#");
					if("".equals(sa[4]) || "@@Ab@@".equals(sa[4])){
						//查询上游订单
						tradeNo = "09027";
						serialNo=sa[0];
					}else{
						//冲正
						tradeNo = "06028";
					}
				}
			}else if (buyid.equals("19")) {// 君宝电信冲正
				String sts=BiProd.getWriteOff(ordid);
				if(sts==null){
					out.write("-5");
					out.flush();
					out.close();
					return null;
				}else{
					String[] sa=sts.split("#");
					if("".equals(sa[4]) || "@@Ab@@".equals(sa[4])){
						//查询上游订单
						tradeNo = "09027";
						serialNo=sa[0];
					}else{
						//冲正
						tradeNo = "06029";
					}
				}
			}else if (buyid.equals("22")) {// 银盛移动冲正
				tradeNo = "06030";
			}else if(buyid.equals("24")){//乐充冲正
				int rsCode=MobileRecharge.Correct_Confim(ordid);
				if(rsCode==0){
					out.write("0");//冲正成功
				}else if(rsCode==-1){
					out.write("-5");//冲正失败
				}else{
					out.write("-1");//系统异常
				}
				out.flush();
				out.close();
				return null;
			}else if(buyid.equals("28")){//连胜接口
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
					out.write("0");//冲正成功
				}else if("-1".equals(strState)){
					out.write("-5");//冲正失败
				}else{
					out.write("-6");//系统异常
				}
				out.flush();
				out.close();
				return null;
			}else {
				out.write("-4");//该号码不支持冲正
				out.flush();
				out.close();
				return null;
			}
			TradeMsg msg = new TradeMsg();
			msg.setSeqNo(seqNo);
			msg.setFlag("0");// 有内容
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
				out.write("-5");//冲正失败  发送消息失败
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
				out.write("-6");//冲正处理中
				out.flush();
				out.close();
				return null;
			}
			String code = rsMsg.getRsCode();
			if ("000".equals(code)) {
				if("09027".equals(tradeNo)){//君宝查询上游订单号
					out.write("-11");//君宝查询
					out.flush();
					out.close();
					return null;
				}
				db.update("insert into wlt_revertlimit values(null,'" + userid
						+ "','" + time.substring(0, 6) + "')");
				out.write("0");//冲正成功
				out.flush();
				out.close();
				return null;
				// ,
				// 充值失败
			} else {
				out.write("-5");//冲正失败
				out.flush();
				out.close();
				return null;
			}
		} catch (Exception e) {
			out.write("-1");//系统异常
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
	 * 查询可以冲正的记录
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
		// 判断冲正数量是否用完
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		String userid = userForm.getUserno();
		DBService db = null;
		List<String[]> list = null;
		String phone = request.getParameter("tradeObject");
		if (null == phone) {
			request.setAttribute("mess", "号码不能为空");
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
					request.setAttribute("mess", "当月冲正次数已用完,无法冲正");
					return new ActionForward("/business/daibanhuRevert.jsp");
				}
			} else {
				if (k >= count) {
					request.setAttribute("mess", "当月冲正次数已用完,无法冲正");
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
			Log.error("用户冲正查询异常:" + e.toString());
			request.setAttribute("mess", "系统繁忙,请稍后再试");
			return new ActionForward("/business/daibanhuRevert.jsp");
		} finally {
			if (null != db) {
				db.close();
			}
		}
		if (null == list || list.size() < 1) {
			request.setAttribute("mess", "不存在相关交易或者已超过24小时");
			request.setAttribute("revertList", list);
			return new ActionForward("/business/daibanhuRevert.jsp");
		} else {
			for (Object tmp : list) {
				String[] temp = (String[]) tmp;
				if (null != temp[3] && !"".equals(temp[3])) {
					if ("0".equals(temp[3])) {
						temp[3] = "成功";
					} else if ("1".equals(temp[3])) {
						temp[3] = "失败";
					} else if ("4".equals(temp[3])) {
						temp[3] = "处理中";
					} else if ("5".equals(temp[3])) {
						temp[3] = "冲正";
					} else if ("6".equals(temp[3])) {
						temp[3] = "异常订单";
					}
				}
			}
		}
		request.setAttribute("ph", phone);
		request.setAttribute("revertList", list);
		return new ActionForward("/business/daibanhuRevert.jsp");
	}

	/**
	 * 成功
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
					.error("订单状态改为成功出错，订单号：" + ordid + "    操作时间："
							+ Tools.getNow3());
			e.printStackTrace();
			request.setAttribute("mess", "操作失败");
			return new ActionForward(
					"/business/order.do?method=listReverse&msg=1");
		}
		Log.info("订单状态改为成功 ok ，订单号：" + ordid + "    操作时间：" + Tools.getNow3());
		request.setAttribute("mess", "操作成功");
		return new ActionForward("/business/order.do?method=listReverse&msg=0");
	}

	/**
	 * 订单直接查询接口
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
			request.setAttribute("mess", "登录超时,请重新登录");
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
			}else if (buyid.equals("15")){//省联通
				n="-2";
			}else if (buyid.equals("17")){//劲峰  全国三网
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
	
//	/**违章处理查询
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
//		String carNameText=request.getParameter("carNameText");//车辆类型名称
//		String interfaceType=request.getParameter("interfaceType");
//		if(plateNumber==null || "".equals(plateNumber) ||
//				plateNumberType==null || "".equals(plateNumberType) ||
//					carFrameNum==null || "".equals(carFrameNum) ||
//						engineNumber==null || "".equals(engineNumber))
//		{
//			Log.info("违章处理，请求参数不足，，，异常操作，");
//			request.setAttribute("mess","参数不足,异常操作");
//			return new ActionForward("/business/catmanage.jsp");
//		}
//		request.setAttribute("catNumbers", plateNumber);//车牌号
//		request.setAttribute("carNameText",carNameText);
//		request.setAttribute("interfaceType",interfaceType);
//		List<PeccancyInfoModel> list=null;
//		
//		int zshMoney=0;
//		//中石化手续费
//		if("382".equals(userSession.getUsersite())){
//			zshMoney=DBConfig.getInstance().getJtfkzsh()/1000;
//		}
//		
//		if("2".equals(interfaceType)){//百世邦
//			plateNumberType=("99".equals(plateNumberType))?"02":plateNumberType;//99代表小型车需要转换02，，，
//			request.setAttribute("catmessage",plateNumber+"#"+plateNumberType+"#"+carFrameNum+"#"+engineNumber);
//			
//			String catUserName=request.getParameter("catUserName");
//			String catUserPhone=request.getParameter("catUserPhone");
//			String carAddress=request.getParameter("carAddress");
//			if(catUserName==null || "".equals(catUserName) || catUserPhone==null || "".equals(catUserPhone)){
//				Log.info("违章处理，请求参数不足，，，异常操作，");
//				request.setAttribute("mess","参数不足,异常操作");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//			request.setAttribute("catUserName",catUserName);
//			request.setAttribute("catUserPhone",catUserPhone);
//			request.setAttribute("carAddress",carAddress);
//			//组装加密 queryText 的值 
////			将QueryText应包含的字段组成源串A（不必区分排列顺序），字段之间以&分隔，参数名字和参数值之间用=分隔，样式如下：
////			Vehicle=粤A12345&VehicleType=1&FrameNo=554545&EngineNo=21555&OwnerName=小李
//			StringBuffer A=new StringBuffer();
//			A.append("Vehicle="+plateNumber);
//			A.append("&VehicleType="+plateNumberType);
//			A.append("&FrameNo="+carFrameNum);
//			A.append("&EngineNo="+engineNumber);
//			A.append("&OwnerName="+catUserName);
//			A.append("&Limit=100");
//			A.append("&Offset=100");
////			在A后加上md5的key（key由财付通提供）得到串B，样式如下：
////			Vehicle=A12345&VehicleType=1&FrameNo=554545&EngineNo=21555&OwnerName=小李&Md5Key=123456
//			String B=A.toString()+"&Md5Key="+Md5AndBase64.KEY;
//			//对B做md5得到Md5Sign（大写） 
//			String Md5Sign=MD5.encode(B).toUpperCase();
////			将Md5Sign缀到A后面得到串C，样式如下：
////			Vehicle=A12345&VehicleType=1&FrameNo=554545&EngineNo=21555&OwnerName=小李&Md5Sign=FD0CF5B71C3D3A6D651078A0B568390F
//			String c=A.toString()+"&Md5Sign="+Md5Sign;
//			//对C串做BASE64加密得到D（使用GB2312）
//			String D=Md5AndBase64.base64EN(c,"GB2312");
//			
//			//组装 加密 sign 的值
//			//字段按照字段名进行字典升序排列
//			//在s1后面追加& Md5Key=xxxxxxxxxxxxx得到签名源串s2，对s2做MD5得到Sign的值   MD5统一采用大写
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
//			//拼接 http url 
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
//			//处理百世邦 业务逻辑
//			String result=b.sendMsgRequest(buffer.toString());
//			if(result==null || "".equals(result) ){
//				//本次查询返回的违章条数
//				Log.info("百世邦违章查询，无数据返回，，"+result);
//				request.setAttribute("mess","未获取违章记录!");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//			Document docResult=DocumentHelper.parseText(result);
//			Element rootResult = docResult.getRootElement();
//			//获取 指定节点
//			Element respText=rootResult.element("RespText");
//			if(respText==null || "".equals(respText) ){
//				//本次查询返回的违章条数
//				Log.info("百世邦违章查询，无数据返回，，"+result);
//				request.setAttribute("mess","未获取违章记录!");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//			
//			String respTextXML=Md5AndBase64.base64DE(respText.getText(),"GB2312");
//			String[] ssa=respTextXML.split("&");
//			
//			//本次查询返回的违章条数;
//			String RetNum=ssa[0].substring(ssa[0].indexOf("RetNum=")+"RetNum=".length(),ssa[0].length());
//			if(RetNum==null || "".equals(RetNum) || Integer.parseInt(RetNum)<=0){
//				//本次查询返回的违章条数
//				Log.info("百世邦违章查询，无数据返回，，"+result);
//				request.setAttribute("mess","未获取违章记录!");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//			
//			//解密指定字符串
//			String Records=ssa[2].substring(ssa[2].indexOf("Records=")+"Records=".length(),ssa[2].length());
//			Records=URLDecoder.decode(Records,"GB2312");
//			//拼接成 xml 格式字符串
//			String Test = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
//			"<root>" +
//				Records+
//			"</root>";
//			//解析指定的   xml  字符串
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
//				p.setId(mm.get("ViolationId")+""); //违章信息id
//				p.setPecNum(mm.get("Wsh")==null?"":mm.get("Wsh").toString());//违法受理号(文书号)
//				p.setPlateNumber(plateNumber);//车牌号码
//				p.setPecCode(mm.get("Wzdm")==null?"":mm.get("Wzdm").toString());//违章代码
//				p.setPecDesc(mm.get("ViloationDetail")==null?"":mm.get("ViloationDetail").toString());//违章行为描述
//				p.setPecAddr(mm.get("ViloationLocation")==null?"":mm.get("ViloationLocation").toString());//违章地点
//				p.setPecDate(mm.get("ViolationTime")==null?"":mm.get("ViolationTime").toString());//违章时间
//				//扣分
//				String koufen=mm.get("Wzdm")==null?"-1":mm.get("Wzdm")+"";
//				if("-1".equals(koufen)){
//					koufen="0";
//				}else if("7".equals(koufen)){
//					koufen="12";
//				}else{
//					koufen=koufen.substring(1,2);
//				}
//				p.setPecScore(koufen);//扣分情况
//				p.setCorpus(mm.get("FineFee")==null?"0":Integer.parseInt(mm.get("FineFee")+"".trim())/100+"".trim());//本金
//				p.setLateFee(mm.get("LateFee")==null?"0":Integer.parseInt(mm.get("LateFee")+"".trim())/100+"".trim());//滞纳金
//				p.setReplaceMoney(mm.get("DealFee")==null?"0":Integer.parseInt(mm.get("DealFee")+"".trim())/100+"".trim());//代办费	
//				String DealFlag=mm.get("DealFlag")==null?"":Integer.parseInt(mm.get("DealFlag")+"".trim())+"".trim();//代办状态转换
//				if("0".equals(DealFlag)){
//					DealFlag="2";
//				}
//				if("1".equals(DealFlag)){
//					DealFlag="1";
//				}
//				p.setAgent(DealFlag);//是否可代办(1：可以代办 ,2：不可以代办)
//				p.setPecState("");//状态(1：审核通过 ,2：审核不通过)
//				p.setPecChanl("");//采集渠道(1：公安厅接口 ,2：手工采集)
//				p.setCreateDate("");//记录创建时间
//				p.setUpdateDate("");//记录修改时间
//				p.setUpdateWorkerNo("");//记录修改人工号
//				p.setWoState("");//代办单状态代办单状态 1、 下单成功未支付 2、 已支付 3、 办理中 4、 办理成功 5、 办理失败 6、 办理异常 7、 取消 -99、删除代办单 100  处理中  101已退费
//				p.setAreaCode("");//违法地点对应地区代码
//				p.setIllegalType("");//违章类型 (ELEC表示电子单，不产生滞纳金的;SCEN代办现场单或已认罚的，产生相应滞纳金)
//				p.setIsdaibai(DealFlag);//下单状态 1 可代办 2 已经下单 3 代办成功 4 不可代办
//				
//				//中石化 代理点  滞纳金
//				if("382".equals(userSession.getUsersite())){
//					int ca=mm.get("LateFee")==null?0:Integer.parseInt(mm.get("LateFee")+"".trim())/100;
//					ca=(int)Math.ceil(ca);
//					p.setNew_LateFee(ca+"");//滞纳金
//				}else{
//					p.setNew_LateFee(mm.get("LateFee")==null?"0":Integer.parseInt(mm.get("LateFee")+"".trim())/100+"".trim());//滞纳金
//				}
//				
//				if(!"382".equals(userSession.getUsersite())){//普通代办点
//					//根据查询的代办费加款
//					if(mm.get("DealFee")==null || "".equals(mm.get("DealFee")) || "0".equals(mm.get("DealFee"))){
//						zshMoney=DBConfig.getInstance().getJtfkdefault()/1000;
//					}else{
//						zshMoney=(Integer.parseInt(mm.get("DealFee")+"")/100)+(DBConfig.getInstance().getJtfkfloat()/1000);
//						//查询返回代办费 大于 25元，不允许代办
//						if((Integer.parseInt(mm.get("DealFee")+"")/100)>25){
//							p.setIsdaibai("4");//下单状态 1 可代办 2 已经下单 3 代办成功 4 不可代办
//							p.setAgent("4");//是否可代办(1：可以代办 ,2：不可以代办)
//						}
//					}
//				}
//				p.setMyPoundage(zshMoney+"".trim());//本平台代办费
//				list.add(p);
//			}
//			request.setAttribute("arrylist",list);
//			return new ActionForward("/business/catmanageList.jsp");
//		}
//		
//		//处理电信业务逻辑
//		plateNumberType=("99".equals(plateNumberType))?"1":plateNumberType;//电信 99代表小型车需要转换1，，
//		request.setAttribute("catmessage",plateNumber+"#"+plateNumberType+"#"+carFrameNum+"#"+engineNumber);
//		String tokenId=PublicMethod.TOKEN_ID;
//		if(tokenId==null || "".equals(tokenId))
//		{
//			tokenId=PublicMethod.getTokenID();
//			if(tokenId==null || "".equals(tokenId))
//			{
//				Log.info("违章处理，tokenId，获取失败，");
//				request.setAttribute("mess","系统异常，tokenId,获取不到");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//		}
//		BreakRulesReq b=new BreakRulesReq(plateNumber,plateNumberType,carFrameNum,engineNumber,tokenId);
//		BreakRulesResp obj=(BreakRulesResp)PublicMethod.breakrulesMethod(BreakRulesResp.class,b,PublicMethod.QUERY_BREAKRULES);
//		if(obj!=null && !"".equals(obj) && obj.getCode()==0){
//			list=obj.getPeccancyInfos();
//			for(PeccancyInfoModel m:list)
//			{
//				//是否可代办
//				int states=getstate(m.getAgent(),m.getWoState());
//				m.setIsdaibai(states+"");
//				
//				//根据地势获取指定的手续费
//				if(!"382".equals(userSession.getUsersite())){//普通代理店
//					//根据查询的代办费加款
//					if(m.getReplaceMoney()==null || "".equals(m.getReplaceMoney()) || "0".equals(m.getReplaceMoney()) ){ //如果没有代办费，取默认值
//						zshMoney=DBConfig.getInstance().getJtfkdefault()/1000;
//					}else{  //有代办费，在加上指定费用
//						zshMoney=Integer.parseInt(m.getReplaceMoney())+(DBConfig.getInstance().getJtfkfloat()/1000);
//						//查询返回代办费 大于 25元，不允许代办
//						if(Integer.parseInt(m.getReplaceMoney())>25){
//							m.setIsdaibai("4");
//						}
//					}
//				}
//				m.setMyPoundage(zshMoney+"".trim());
//				
//				//中石化 代理点  滞纳金
//				if("382".equals(userSession.getUsersite())){
//					int ca=0;
//					if(m.getLateFee()!=null && !"".equals(m.getLateFee())){
//						ca=(int)Math.ceil(Double.parseDouble(m.getLateFee()+""));
//					}
//					m.setNew_LateFee(ca+"");//滞纳金
//				}else{
//					m.setNew_LateFee(m.getLateFee());//滞纳金
//				}
//				
//				//日期格式
//			    m.setPecDate(m.getPecDate()!=null?Tools.timestamp2String(Long.parseLong(m.getPecDate())):"");
//			    m.setCreateDate(m.getCreateDate()!=null?Tools.timestamp2String(Long.parseLong(m.getCreateDate())):"");
//			    m.setUpdateDate(m.getUpdateDate()!=null?Tools.timestamp2String(Long.parseLong(m.getUpdateDate())):"");
//			  list.add(m);
//			}
//			request.setAttribute("arrylist",list);
//			return new ActionForward("/business/catmanageList.jsp");
//		}else{
//			Log.info("违章处理，返回结果标识 code!=0，，，");
//			request.setAttribute("mess","无违章记录！");
//			return new ActionForward("/business/catmanage.jsp");
//		}
//	}
//	
//	/** 交通罚款 下单
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
//		String carNameText=request.getParameter("carNameText");//车辆类型名称
//		
//		String interfaceType=request.getParameter("interfaceType");//查询接口
//		//操作权限判断
//		if(!"3".equals(userSession.getRoleType())){
//			request.setAttribute("mess","您没有下单的权限!");
//			return new ActionForward("/business/catmanage.jsp");
//		}
//		
//		if(catmessage==null || "".equals(catmessage) || str==null || "".equals(str) || catUserName==null || "".equals(catUserName) || "".equals(catUserPhone) || catUserPhone==null){
//			Log.info("违章处理，请求参数不足，，，异常操作,异常操作");
//			request.setAttribute("mess","参数不足,系统异常");
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
//		//下单总额
//		int zMoney=Integer.parseInt(sumMoney)*1000;
//		int accountMoney=-1;
//		String maxid=Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);//工单id
//		//当前系统时间
//		String date=Tools.getNewDate();
//		//账务 dealserial 流水号
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
//						request.setAttribute("mess","工单ID[违章ID]: "+abcdef.substring(0,abcdef.length()-1)+" 已下单,不能重复下单");
//						return new ActionForward("/business/catmanage.jsp");
//					}
//				}
//			}else{
//				request.setAttribute("mess","未选中违章记录，异常操作!");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//			
//			String getSql="SELECT childfacct,accountleft FROM wht_childfacct WHERE childfacct=(SELECT CONCAT(fundacct,'01') FROM wht_facct WHERE user_no='"+userSession.getUserno()+"')";
//			funs=dbs.getStringArray(getSql);
//			if(funs==null || "".equals(funs) || funs.length<=0){
//				request.setAttribute("mess","账户信息异常！");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//			accountMoney=Integer.parseInt(funs[1])-zMoney;
//			if(accountMoney<0){
//				request.setAttribute("mess","账户余额不足！");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//		}catch(Exception e){
//			request.setAttribute("mess","系统异常!");
//			return new ActionForward("/business/catmanage.jsp");
//		}finally{
//			if(null!=dbs){
//				dbs.close();
//			}
//		}
//		//百世邦违章下单处理 
//		String SpTransIdss=""; //预查询 返回的   商户侧订单号
//		String resWzid=""; // 下单 预查询返回可下单 违章 id
//		int oldMoney=0;//oldMoney  下单请求上游的 金额
//		if("2".equals(interfaceType)){
//			int cass=0;
//			for(int i=0;i<str.length;i++){
//				if(str[i]!=null && !"".equals(str[i])){
//					String[] arr=str[i].split("#");
//					float corpusa=(arr[8]==null || "".equals(arr[8]))?0f:Float.parseFloat(arr[8])*100;
//					float lateFee=(arr[9]==null || "".equals(arr[9]))?0f:Float.parseFloat(arr[9])*100;
//					float replaceMoney=(arr[10]==null || "".equals(arr[10]))?0f:Float.parseFloat(arr[10])*100;
//					//提交到 百事帮交易总额
//					oldMoney=(int)(oldMoney+corpusa+lateFee+replaceMoney);
//					
//					
//					//处理后的滞纳金
//					float ss=(arr[21]==null || "".equals(arr[21]))?0f:Float.parseFloat(arr[21])*1000;
//					//处理后的万汇通代办费
//					float ownMoney=(arr[20]==null || "".equals(arr[20]))?0f:Float.parseFloat(arr[20])*1000;
//					//万汇通平台下单总额
//					cass=cass+(int)((corpusa*10)+ss+ownMoney);
//				}
//			}
//			//页面总额 和 后台总额 对比
//			if(zMoney!=cass){
//				Log.info("百事帮交通罚款下单，交易金额有问题,页面总和:"+zMoney+",后台循环叠加总和:"+cass);
//				request.setAttribute("mess","交易金额有问题!");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//			
//			//财付通订单号
//			String CftTransId="baishi"+Tools.getNow3()+((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+100);
//			//订单日期，
//			String Datetime=date;
//			//代办商侧的违章ID，多个ID以“|”分隔
//			String ViolationId=inString.substring(0,inString.length()-1).replace(",","|");
//			//是否邮寄回执：0，不邮寄；1，邮寄；2，扫描单据；3，保留单据，打包邮寄。
//			String MailReceipt="0";
//			//1 -- RMB
//			String FeeType="1";
//			//总费用（单位为分），含邮费
//			String TotalFee=oldMoney+"".trim();
//			//用户联系方式，建议是手机
//			String MobileNo=catUserPhone;
//			//发动机号后4位或是后6位
//			String EngineNo=strs[2].substring(strs[2].length()-6,strs[2].length());
//			//拼接 queryText 字符串
//			String queryText="CftTransId="+CftTransId+"&Datetime="+Datetime+"&ViolationId="+ViolationId+"&MailReceipt="+MailReceipt+"&FeeType="+FeeType+"&TotalFee="+TotalFee+"&MobileNo="+MobileNo+"&EngineNo="+EngineNo;
//			
////			在A后加上md5的key（key由财付通提供）得到串B，样式如下：
////			Vehicle=A12345&VehicleType=1&FrameNo=554545&EngineNo=21555&OwnerName=小李&Md5Key=123456
//			String B=queryText.toString()+"&Md5Key="+Md5AndBase64.KEY;
//			//对B做md5得到Md5Sign（大写） 
//			String Md5Sign=MD5.encode(B).toUpperCase();
////			将Md5Sign缀到A后面得到串C，样式如下：
////			Vehicle=A12345&VehicleType=1&FrameNo=554545&EngineNo=21555&OwnerName=小李&Md5Sign=FD0CF5B71C3D3A6D651078A0B568390F
//			String c=queryText.toString()+"&Md5Sign="+Md5Sign;
//			//对C串做BASE64加密得到D（使用GB2312）
//			String D=Md5AndBase64.base64EN(c,"GB2312");
//			
//			//组装 加密 sign 的值
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
//			//拼接 http url 
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
//			//处理百世邦 业务逻辑
//			
//			String result=b.sendMsgRequest(buffer.toString());
//			Document docResult=DocumentHelper.parseText(result);
//			Element root = docResult.getRootElement();
//			//预查询返回状态
//			String resCode=root.element("SpRetCode").getText();
//			//预查询返回可下单 违章 id
//			resWzid=root.element("ViolationId").getText();
//			if(resCode==null || "".equals(resCode) || !"0000".equals(resCode) || resWzid==null || "".equals(resWzid)){
//				Log.info("百世邦违章预查询，返回值：SpRetCode="+resCode+",返回违章id："+ViolationId);
//				request.setAttribute("mess","此订单下单失败!");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//			//预查询 返回的   商户侧订单号
//			SpTransIdss=root.element("SpTransId").getText();
//		}
//		//角色判断
//		boolean fl=false;
//		if("382".equals(userSession.getUsersite())){
//			fl=true;
//		}else{
//			fl=false;
//		}
//		
//		
//		//入库
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
//					"'"+dealserial+"'," +//workerNo 默认为空，百世邦则是 下单订单号
//					"100," +//woState
//					"111," +//spID
//					"'02'," +//resultCode
//					"'"+strs[2]+"'," +//carFrameNum
//					"'"+strs[3]+"'," +//engineNumber
//					"'"+strs[1]+"'," +//exp1 汽车类型
//					"'"+interfaceType+"'," +//exp2 下单类型 电信1  白世邦2   入库0 
//					"'"+carNameText+"'," +//exp2 车辆名称
//						"'"+carAddress+"')";//联系人地址
//			db.update(sql);
//			for(int i=0;i<str.length;i++){
//				if(str[i]!=null && !"".equals(str[i])){
//					String[] arr=str[i].split("#");
//					float corpusa=(arr[8]==null || "".equals(arr[8]))?0f:Float.parseFloat(arr[8])*1000;
//					float lateFee=(arr[9]==null || "".equals(arr[9]))?0f:Float.parseFloat(arr[9])*1000;
//					float replaceMoney=(arr[10]==null || "".equals(arr[10]))?0f:Float.parseFloat(arr[10])*1000;
//					float ownMoney=(arr[20]==null || "".equals(arr[20]))?0f:Float.parseFloat(arr[20])*1000;
//					
//					//处理后的滞纳金
//					float ss=(arr[21]==null || "".equals(arr[21]))?0f:Float.parseFloat(arr[21])*1000;
//					//违章 总额
//					int kfmoney=(int)(corpusa+ss+ownMoney);
//					
//					//条形码
////					String barcode=getTiaoMa(arr[8]);
//					String inSql="INSERT INTO wht_breakrules(id,gdid,pecNum,pecCode,pecDesc,pecAddr," +
//							"pecDate,pecScore,corpus,lateFee,replaceMoney,ownMoney,agent,pecState,pecChanl," +
//							"createDate,updateDate,updateWorkerNo,woState,areaCode,illegalType,Exp1,Exp4,Exp5) " +
//							"VALUES("+(arr[0]==null?-1:arr[0])+",'"+maxid+"','"+(arr[1]==null?"":arr[1])+"','"+(arr[3]==null?"":arr[3])+"','"+(arr[4]==null?"":arr[4])+"','"+(arr[5]==null?"":arr[5])+"','"+(arr[6]==null?"":arr[6])+"',"+(arr[7]==null?-1:arr[7])+","+corpusa+","+lateFee+","+replaceMoney+","+ownMoney+",'"+(arr[11]==null?-1:arr[11])+"','"+(arr[12]==null?-1:arr[12])+"','" +(arr[13]==null?-1:arr[13])+"','"+(arr[14]==null?"":arr[14])+"','"+(arr[15]==null?"":arr[15])+"','"+(arr[16]==null?"":arr[16])+"','100','"+(arr[18]==null?"":arr[18])+"','"+(arr[19]==null?"":arr[19])+"','"+interfaceType+"','"+kfmoney+"','"+ss+"')";
//					db.update(inSql);
//				}
//			}
//			//账务信息录入
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
//					"'交通罚款下单'," +
//					"0," +
//					Tools.getNow3()+","+accountMoney+"," +
//					"'"+dealserial+"'," +
//					"'"+funs[0]+"'," +
//					"1)";
//			//修改账户余额
//			String updatesql="UPDATE wht_childfacct SET accountleft="+accountMoney+" WHERE childfacct='"+funs[0]+"'";
//			db.update(acctSql);
//			db.update(updatesql);
//			db.commit();
//			
//			String isxiadan=db.getString("select interface from wht_carInterface where interface="+interfaceType+" and operationType=2");
//			//入库
//			if("0".equals(isxiadan)){
//				request.setAttribute("mess","下单成功!");
//				return new ActionForward("/business/catmanage.jsp");
//			}else if("2".equals(interfaceType) && "2".equals(isxiadan)){//百世邦 下单
//				//isOrder  SpTransIdss resWzid oldMoney（下单到上游金额 分）    zMoney交易金额(里）
//				int flag=isBaiShiBang(dealserial,SpTransIdss,resWzid,oldMoney+"".trim(),zMoney);
//				if(flag==0){//下单成功，，改为办理中状态 
//					String s9="【万恒科技】 车牌号:"+strs[0]+","+str.length+"条违章,受理成功,唯一热线：400-9923-988";
//					if(YMmessage.qxtSendSMS(catUserPhone,s9)){
//						Log.info("违章下单短信发送成功,文件输出,发送号码:"+catUserPhone+",内容:"+s9);
//						System.out.println("违章下单短信发送成功,控制台输出,发送号码:"+catUserPhone+",内容:"+s9);
//					}
//					request.setAttribute("mess","工单ID:"+maxid+",违章ID["+resWzid+"],下单成功,收取金额："+Float.parseFloat((zMoney/1000)+"".trim())+"元");
//				}else if(flag==1){
//					request.setAttribute("mess","工单ID:"+maxid+",违章ID["+resWzid+"],下单失败,收取金额："+Float.parseFloat((zMoney/1000)+"".trim())+"元,退费成功,请查看账务!");
//				}else{
//					request.setAttribute("mess","工单ID:"+maxid+",违章ID["+resWzid+"],下单异常,收取金额："+Float.parseFloat((zMoney/1000)+"".trim())+"元");
//				}
//				return new ActionForward("/business/catmanage.jsp");
//			}else if("1".equals(interfaceType) && "1".equals(isxiadan)){//电信下单
//				request.setAttribute("mess","下单成功!");
//				return new ActionForward("/business/catmanage.jsp");
//			}else{
//				request.setAttribute("mess","下单成功!");
//				return new ActionForward("/business/catmanage.jsp");
//			}
//		}catch(Exception e){
//			db.rollback();
//			Log.error("交通罚款下单异常，，，"+e);
//			request.setAttribute("mess","系统异常，联系客服！");
//			return new ActionForward("/business/catmanage.jsp");
//		}finally{
//			if(null!=db)
//				db.close();
//		}
//	}
//	
	/**交通罚款列表
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
		String whereType=request.getParameter("wheretype");//查询(0) or  导出(1)
		
		String inter=request.getParameter("inter");//接口类型 1电信   2百事帮
		
		//导出excel
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
				whereInfo=whereInfo+"  开始时间 >= "+inDate+" 00:00:00 ";
				excelsql=excelsql+" AND createDate<='"+endDate+" 23:59:59' ";
				whereInfo=whereInfo+"  结束日期 <= "+endDate+" 23:59:59 ";
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
		
		String whereStr=" ";//where 条件
		String whereInfo=" ";// 描述 where 条件
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
		whereInfo=whereInfo+"  开始时间 >= "+inDate+" 00:00:00 ";
		
		if(endDate!=null && !"".equals(endDate)){
			
		}else{
			endDate=Tools.getNowDateStr();
		}
		countSql=countSql+" AND createDate<='"+endDate+" 23:59:59' ";
		listSql=listSql+" AND createDate<='"+endDate+" 23:59:59' ";
		whereStr=whereStr+" AND createDate<='"+endDate+" 23:59:59' ";
		whereInfo=whereInfo+"  结束日期 <= "+endDate+" 23:59:59 ";
		
		if(state!=null && !"".equals(state)){
			countSql=countSql+" AND woState='"+state+"' ";
			listSql=listSql+" AND woState='"+state+"' ";
			whereStr=whereStr+" AND woState='"+state+"' ";
			whereInfo=whereInfo+" 办理状态 = "+state;
		}
		if(plateNumberB!=null && !"".equals(plateNumberB)){
			String plateNumber=plateNumberA+plateNumberB;
			countSql=countSql+" AND carnum='"+plateNumber+"'";
			listSql=listSql+" AND carnum='"+plateNumber+"' ";
			whereStr=whereStr+" AND carnum='"+plateNumber+"' ";
			whereInfo=whereInfo+" 车牌号 = "+plateNumber+" ";
		}
		if(catName!=null && !"".equals(catName)){
			countSql=countSql+" AND name='"+catName+"'";
			listSql=listSql+" AND name='"+catName+"' ";
			whereStr=whereStr+" AND name='"+catName+"' ";
			whereInfo=whereInfo+" 车主姓名 = "+catName+" ";
		}
		if(userName!=null && !"".equals(userName)){
			countSql=countSql+" AND userno=(SELECT user_no FROM sys_user WHERE user_login='"+userName+"')";
			listSql=listSql+" AND userno=(SELECT user_no FROM sys_user WHERE user_login='"+userName+"')";
			whereStr=whereStr+" AND userno=(SELECT user_no FROM sys_user WHERE user_login='"+userName+"')";
			whereInfo=whereInfo+" 代理点账号 = "+userName;
		}
		if("-22".equals(inter)){
			whereInfo=whereInfo+" 接口类型 = 全部 ";
//		}else if("1".equals(inter)){
//			countSql=countSql+" AND Exp2='"+inter+"'";
//			listSql=listSql+" AND Exp2='"+inter+"'";
//			whereStr=whereStr+" AND Exp2='"+inter+"'";
//			whereInfo=whereInfo+" 接口类型 = 电信";
		}else if("2".equals(inter)){
			countSql=countSql+" AND Exp2='"+inter+"'";
			listSql=listSql+" AND Exp2='"+inter+"'";
			whereStr=whereStr+" AND Exp2='"+inter+"'";
			whereInfo=whereInfo+" 接口类型 = 百事帮";
		}
		
		DBService db=null;
		List arryList=null;
		try{
			db=new DBService();
			//查询
			count=db.getInt(countSql);
			if(count>0)
				lastIndex=count%pagesize==0?count/pagesize:count/pagesize+1;
			if(index>=lastIndex)
				index=lastIndex;
//			//不是超级管理员
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
		//查询条件
		request.setAttribute("whereStr",whereStr);
		request.setAttribute("whereInfo",whereInfo);
		return new ActionForward("/business/catMessageArray.jsp");
	}
	/**根据工单ID，查询违章记录
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
			request.setAttribute("mess", "登录超时,请重新登录");
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
	
	/** 工单状态，违章状态，工单退费，违章退费 业务处理
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
				request.setAttribute("mess", "登录超时,请重新登录");
				return new ActionForward("/rights/wltlogin.jsp");
			}
		 JSONObject json=new JSONObject();
		 PrintWriter  out= response.getWriter();
		 SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
		 if(!"0".equals(userSession.getRoleType())){
			 json.put("msg","你没有操作权限!");
			 out.print(json.toString());
			 out.flush();
			 out.close();
			 return null;
		 }
		 
		 // type 0（工单状态)   1违章状态
		 String type=request.getParameter("typeA");//0（工单状态)   1违章状态
		 String gdid=request.getParameter("gdid");//工单id
		 String wzid=request.getParameter("wzid");//违章id
		 String inter=request.getParameter("inter");//接口id
		 if(type!=null && !"".equals(type) && ("0".equals(type) || "1".equals(type))){ 
			 String state=request.getParameter("state");//修改后状态
			 String msg="";
			 String sql="";
			 String ifwoState="";//工单or违章状态为 101  不允许修改状态
			 if("0".equals(type) && state!=null && !"".equals(state)){//修改工单状态
				 ifwoState="SELECT woState FROM wht_bruleorder WHERE id='"+gdid+"'";
				 sql="UPDATE wht_bruleorder SET woState='"+state+"' WHERE id='"+gdid+"'";
				 msg="修改工单ID: "+gdid;
			 }else if("1".equals(type)  && state!=null && !"".equals(state)){//修改违章状态
				 ifwoState="SELECT woState FROM wht_breakrules WHERE gdid='"+gdid+"' AND id="+wzid;
				 sql="UPDATE wht_breakrules SET woState='"+state+"' WHERE gdid='"+gdid+"' AND id="+wzid;
				 msg="修改工单ID:"+gdid+",违章ID:"+wzid;
			 }else{
				 json.put("msg","未知状态,异常操作!");
				 out.print(json.toString());
				 out.flush();
				 out.close();
				 return null;
			 }
			 DBService db=null;
			 try{
				 db=new DBService();
				 String PutlicwoState=db.getString(ifwoState);
				 if("101".equals(PutlicwoState)){ //工单已退费，不能修改状态
					 json.put("msg","此订单不能修改状态,异常操作!");
					 out.print(json.toString());
					 out.flush();
					 out.close();
					 return null;
				 }
				 //工单修改状态，判断下级违章是否有成功或已退费状态
				 if("0".equals(type)){//工单
					 String ssss=db.getString("SELECT 1 FROM wht_breakrules WHERE gdid='"+gdid+"' AND (woState=101 OR woState=4)");
					 if(ssss!=null && !"".equals(ssss)){
						 //不能修改工单状体
						 json.put("msg","不能修改此工单状态!");
						 out.print(json.toString());
						 out.flush();
						 out.close();
						 return null;
					 }
				 }
				 //违章修改状态，判断上级 工单是否退费状态
				 if("1".equals(type)){//违章
					 String wzwoState=db.getString("SELECT woState FROM wht_bruleorder WHERE id='"+gdid+"'");
					 if("101".equals(wzwoState)){
						 json.put("msg","此违章不能修改状态,异常操作!");
						 out.print(json.toString());
						 out.flush();
						 out.close();
						 return null;
					 }
				 }
				 
				 if(db.update(sql)>0){
					 if("1".equals(type)){//违章
						 
					 }else if("0".equals(type)){//工单 //修改工单下级所有的违章状态
						 db.update("UPDATE wht_breakrules SET woState='"+state+"' WHERE gdid='"+gdid+"'");
					 }
					 msg=msg+" ,修改完成!";
				 }
			 }catch(Exception e){
				 Log.error("交通罚款，修改状态，系统异常，，，"+e);
				 msg=msg+" ,系统异常!";
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
		 // 11(工单退费)  22(违章退费)
		 if(type!=null && !"".equals(type) && ("11".equals(type) || "22".equals(type))){ 
			 String money=request.getParameter("money");//退费金额  厘
			 String sql="";//退费工单or违章信息sql
			 String updatestate="";//退费工单or违章信息  状态修改sql
			 String updatestateAll="";//更新工单下所有的  违章状态
			 String ifgetwoState="";//违章退费，判断工单是否 允许退费(工单 woState= 4 or 101  不允许退费
			 
			 if(money==null || "".equals(money)){
				 json.put("msg","退费金额为空,异常操作!");
				 out.print(json.toString());
				 out.flush();
				 out.close();
				 return null;
			 }
			 if("11".equals(type)){//工单退费
				 sql="SELECT id,userno,dealserial,woState,Exp5 FROM wht_bruleorder WHERE id='"+gdid+"'";
				 updatestate="UPDATE wht_bruleorder SET woState='101' WHERE id='"+gdid+"'";
				 updatestateAll="UPDATE wht_breakrules SET woState='101' WHERE  gdid='"+gdid+"'";
			 }else if("22".equals(type)){//违章退费
				 sql="SELECT w.id,g.userno,g.dealserial,w.woState,Exp4 FROM wht_breakrules w,wht_bruleorder g WHERE  w.gdid=g.id AND id="+wzid+" AND gdid='"+gdid+"'";
				 updatestate="UPDATE wht_breakrules SET woState='101' WHERE id="+wzid+" AND gdid='"+gdid+"'";
				 ifgetwoState="SELECT woState FROM wht_bruleorder WHERE id='"+wzid+"'";
			 }else{
				 json.put("msg","未知退费,异常操作!");
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
					 json.put("msg","没有找到退费订单,异常操作!");
					 out.print(json.toString());
					 out.flush();
					 out.close();
					 return null;
				 }
				 //页面上发起退费金额和 数据金额对比
				 if(!strs[4].equals(money)){
					 json.put("msg","退费金额不符,异常操作!");
					 out.print(json.toString());
					 out.flush();
					 out.close();
					 return null; 
				 }
				 //成功 or 已退费的订单不能再退费
				 if("4".equals(strs[3]) || "101".equals(strs[3])){
					 json.put("msg","此订单不能退费,异常操作!");
					 out.print(json.toString());
					 out.flush();
					 out.close();
					 return null; 
				 }
				 //违章退费，判断工单是否允许退费 (工单 woState= 4 or 101  不允许退费
				 if("22".equals(type)){
					 String gdwoState=db.getString(ifgetwoState);
					 if("4".equals(gdwoState) || "101".equals(gdwoState)){
						 json.put("msg","此违章不能退费,异常操作!");
						 out.print(json.toString());
						 out.flush();
						 out.close();
						 return null;
					 }
				 }
				 if("11".equals(type)){//工单退费，判断下级违章是否有退费记录
					 String sssssss=db.getString("SELECT 1 FROM  wht_breakrules  WHERE  gdid='"+gdid+"' AND (woState=101 OR woState=4)");
					 if(sssssss!=null && !"".equals(sssssss)){
						 json.put("msg","此工单不允许退费!");
						 out.print(json.toString());
						 out.flush();
						 out.close();
						 return null;
					 }
				 }
				 //根据userno 查询账户信息
				 String getFee="SELECT u.user_login,f.childfacct,f.accountleft FROM sys_user u,(SELECT childfacct,accountleft FROM wht_childfacct WHERE childfacct=(SELECT CONCAT(fundacct,'01') FROM wht_facct WHERE user_no='"+strs[1]+"')) f WHERE u.user_no='"+strs[1]+"'";
				 String[] fees=db.getStringArray(getFee);
				 if(fees==null || fees.length<=0){
					 json.put("msg","账户信息不存在,异常操作");
					 out.print(json.toString());
					 out.flush();
					 out.close();
					 return null;
				 }
				 String dealserial="catReturn"+Tools.getNow3()+Tools.buildRandom(5); //退费账务订单号
				 float accountleft=Float.parseFloat(fees[2])+Float.parseFloat(strs[4]); //退费后的余额
				 db.setAutoCommit(false);
					 //账务信息录入
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
							"'交通罚款退费(平台退费)'," +
							"0," +
							""+accountleft+"," +
							"'"+strs[0]+"'," +
							"'"+fees[1]+"'," +
							"2)";
					//修改账户余额
					String updatesql="UPDATE wht_childfacct SET accountleft="+accountleft+" WHERE childfacct='"+fees[1]+"'";
					db.update(updatestate);//修改工单or违章状态 为 已退费状态
					if(updatestateAll!=null && !"".equals(updatestateAll) && "11".equals(type)){//更新工单下的所有违章 状态
						db.update(updatestateAll);
					}
					db.update(acctSql); //账务
					db.update(updatesql); //账户余额
					db.commit();
					
					json.put("msg","退费成功!");
					 out.print(json.toString());
					 out.flush();
					 out.close();
					 return null;
			 }catch(Exception e){
				 db.rollback();
				 Log.error("交通罚款，退费，系统异常，，，"+e);
				 json.put("msg","系统异常");
				 out.print(json.toString());
				 out.flush();
				 out.close();
				 return null;
			 }finally{
				 if(null!=db)
					 db.close();
			 }
		 }
		 //工单下单
//		 if(type!=null && !"".equals(type) && "33".equals(type)){
//			 String money=request.getParameter("money");//退费金额  厘
//			 //判断工单当前状态为  处理中，
//			 String gdsql="SELECT 1 FROM wht_bruleorder WHERE id='"+gdid+"' AND woState<>100";
//			 //判断工单 下级所有违章状态 都是处理中状态
//			 String wzsql="SELECT 1 FROM wht_breakrules WHERE gdid='"+gdid+"' AND woState<>100";
//			 //工单 页面下单金额 与 数据库金额对比
//			 String gdmoney="SELECT totalCharge FROM wht_bruleorder WHERE id='"+gdid+"' AND woState=100";
//			 DBService db=null;
//			 try{
//				 db=new DBService();
//				 //当前工单是否处理过
//				 String gdifelse=db.getString(gdsql);
//				 if(gdifelse!=null && !"".equals(gdifelse)){
//					 json.put("msg","工单ID: "+gdid+" ,此工单已处理过,不允许再次下单!");
//					 out.print(json.toString());
//					 out.flush();
//					 out.close();
//					 return null;
//				 }
//				 
//				 //工单下的违章是否处理过
//				 String wzifelse=db.getString(wzsql);
//				 if(wzifelse!=null && !"".equals(wzifelse)){
//					 json.put("msg","工单ID: "+gdid+" ,下级违章有处理过,不允许再次下单!");
//					 out.print(json.toString());
//					 out.flush();
//					 out.close();
//					 return null;
//				 }
//				//判断工单页面下单 金额与数据库金额是否一致
//				 String mm=db.getString(gdmoney);
//				 if(mm==null || "".equals(mm) || !money.equals(mm)){
//					 json.put("msg","工单ID: "+gdid+" ,下单金额不符!");
//					 out.print(json.toString());
//					 out.flush();
//					 out.close();
//					 return null;
//				 }
//				 //调用 下单方法  ,返回状态码   0 无数据可下单，1成功 ，2 上游受理失败，3内部异常失败
//				 SynchronousDatas ss=new SynchronousDatas();
//				 int returnState=ss.submitOrder(Long.parseLong(gdid));
//				 String msg="工单ID: "+gdid+" ,";
//				 if(returnState==0){
//					 msg="无数据可下单";
//				 }else if(returnState==1){
//					 msg="下单成功";
//				 }else if(returnState==2){
//					 msg="上游受理失败";
//				 }else if(returnState==3){
//					 msg="内部异常失败";
//				 }else{
//					 msg="下单返回未知状态!";
//				 }
//				 json.put("msg",msg);
//				 out.print(json.toString());
//				 out.flush();
//				 out.close();
//				 return null;
//			 }catch(Exception e){
//				 Log.error("下单异常，，，"+e);
//				 json.put("msg","系统异常");
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
//	    * 交通罚款  是否可以代办 
//	    * @param agent  是否可代办  代办单状态
//	    * @param woState
//	    * @return 1 可代办 2 已经下单 3 代办成功 4 不可代办
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
//   /** 根据 车类型，业务 查询所用接口
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
//					request.setAttribute("mess", "登录超时,请重新登录");
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
//	/**百世邦 下单
//	 * @param dealserial 白世邦 下单账务订单号
//	 * @param SpTransId
//	 * @param ViolationId
//	 * @param TotalFee 下单金额  分
//	 * @param orderMoney  我们平台交易金额  里 
//	 * @return int 1失败   0成功  -1异常
//	 */
//	public static int isBaiShiBang(String dealserial,String SpTransId,String ViolationId,String TotalFee,int orderMoney){
//		int bool=-1;
//		
//		try{
//			String queryText="CftTransId="+dealserial+"&SpTransId="+SpTransId+"&Datetime="+Tools.getNewDate()+"&ViolationId="+ViolationId+"&MailReceipt=0&FeeType=1&TotalFee="+TotalFee+"";
////			在A后加上md5的key（key由财付通提供）得到串B，样式如下：
////			Vehicle=A12345&VehicleType=1&FrameNo=554545&EngineNo=21555&OwnerName=小李&Md5Key=123456
//			String B=queryText.toString()+"&Md5Key="+Md5AndBase64.KEY;
//			//对B做md5得到Md5Sign（大写） 
//			String Md5Sign=MD5.encode(B).toUpperCase();
////			将Md5Sign缀到A后面得到串C，样式如下：
////			Vehicle=A12345&VehicleType=1&FrameNo=554545&EngineNo=21555&OwnerName=小李&Md5Sign=FD0CF5B71C3D3A6D651078A0B568390F
//			String c=queryText.toString()+"&Md5Sign="+Md5Sign;
//			//对C串做BASE64加密得到D（使用GB2312）
//			String D=Md5AndBase64.base64EN(c,"GB2312");
//			
//			//组装 加密 sign 的值
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
//			//拼接 http url 
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
//			Log.info("百事帮交通罚款下单响应字符串result:"+result);
//			Document docResult=DocumentHelper.parseText(result);
//			Element root = docResult.getRootElement();
//			String resCode=root.element("SpRetCode").getText();
//			Log.info("百事帮交通罚款下单响应状态，SpRetCode="+resCode);
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
//				Log.info("百事帮交通罚款下单成功，修改数据状态为 办理中,dealserial="+dealserial);
//				db.setAutoCommit(false);
//				//该状态
//				db.update("UPDATE wht_bruleorder SET resultCode='00',woState='3' WHERE Exp2=2 AND workerNo='"+dealserial+"' AND dealserial='"+dealserial+"'");
//				db.update("UPDATE wht_breakrules SET woState=3 WHERE Exp1=2 AND gdid=(SELECT id FROM wht_bruleorder  WHERE Exp2=2 AND workerNo='"+dealserial+"'  AND dealserial='"+dealserial+"')");
//				db.commit();
//				Log.info("百事帮交通罚款下单成功，修改数据状态为 办理中，修改成功,dealserial="+dealserial);
//				return 0;
//			}else{
//				Log.info("百事帮交通罚款下单失败，准备退费业务，，，,dealserial="+dealserial);
//				//退费
//				String tableName="wht_acctbill_"+Tools.getNow3().substring(2,6);
//				//下单 ：tradeaccount 用户登录账号，，，，childfacct 交易账号
//				String[] acc=db.getStringArray(" SELECT tradeaccount,childfacct FROM "+tableName+" WHERE tradeserial='"+dealserial+"' AND dealserial='"+dealserial+"'");
//				//根据白世邦 下单记录账户的  用户账号，查找此用户的账户，余额
//				String getSql="SELECT childfacct,accountleft FROM wht_childfacct WHERE childfacct=(SELECT CONCAT(fundacct,'01') FROM wht_facct WHERE user_no=(SELECT user_no FROM sys_user WHERE user_login='"+acc[0]+"'))";
//				String[] funs=db.getStringArray(getSql);
//				if(!acc[1].equals(funs[0])){
//					Log.info("百事帮交通罚款下单失败，账户不匹配，，，,dealserial="+dealserial);
//					return -1;
//				}
//				int accountMoney=Integer.parseInt(funs[1])+orderMoney;
//				//退费账务 流水号
//				String serial="resBSB"+Tools.getNow3()+((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+100);
//				db.setAutoCommit(false);
//				//账务信息录入
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
//						"'交通罚款(失败退费)'," +
//						"0," +
//						Tools.getNow3()+","+accountMoney+"," +
//						"'"+dealserial+"'," +
//						"'"+acc[1]+"'," +
//						"2)";
//				//修改下单状态
//				db.update("UPDATE wht_bruleorder SET resultCode='03',woState='101' WHERE Exp2=2 AND workerNo='"+dealserial+"' AND dealserial='"+dealserial+"'");
//				db.update("UPDATE wht_breakrules SET woState=101 WHERE Exp1=2 AND gdid=(SELECT id FROM wht_bruleorder  WHERE Exp2=2 AND workerNo='"+dealserial+"'  AND dealserial='"+dealserial+"')");
//				//修改账户余额
//				String updatesql="UPDATE wht_childfacct SET accountleft="+accountMoney+" WHERE childfacct='"+acc[1]+"'";
//				db.update(acctSql);
//				db.update(updatesql);
//				db.commit();
//				Log.info("百事帮交通罚款下单失败，退费成功，，,dealserial="+dealserial);
//				return 1;
//			}
//		}catch(Exception e){
//			db.rollback();
//			Log.error("百事通交通罚款下单异常，，"+e);
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
