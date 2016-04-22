package com.wlt.webm.business.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import whmessgae.TradeMsg;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.business.bean.SysUserInterface;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.message.PortalSend;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.ten.action.QbMiddle;
import com.wlt.webm.ten.bean.WhQb;
import com.wlt.webm.ten.service.TenpayXmlPath;
import com.wlt.webm.tool.FloatArith;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.TenpayUtil;

/**
 * 中石化话费充值
 * 
 * @author caiSJ
 * 
 */
public class ZshChargeAction extends DispatchAction {
	/**
	 * 中石化充值
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 跳转页面
	 * @throws Exception
	 */
	public ActionForward tzFill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm loginUser = (SysUserForm) request.getSession().getAttribute(
		"userSession");
		if(null==loginUser){
			request.setAttribute("mess", "登录超时,请重新登录");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		if(!loginUser.getRoleType().equals("3")){
			request.setAttribute("mess", "当前用户类型不支持交易");
			return new ActionForward("/business/zshbusiness.jsp");
		}
		if(!Tools.validateTime()){
			request.setAttribute("mess", "23:50到00:10不允许交易");
			return new ActionForward("/business/zshbusiness.jsp");
		}
		String phonePid=request.getParameter("prodId");
		String phone=request.getParameter("tradeObject").replaceAll(" ", "");
		String phoneType=request.getParameter("telType");
		String payfee=request.getParameter("money1");
		if(payfee.indexOf("-")!=-1){
			request.setAttribute("mess", "交易金额不合法!");
			return new ActionForward("/business/zshbusiness.jsp");
		}
		if("".equals(phonePid) || phonePid==null || 
				"".equals(phone) || phone==null ||
				  "".equals(phoneType) || phoneType==null ||
				  	"".equals(payfee) || payfee==null)
			{
				request.setAttribute("mess", "参数不足！");
				return new ActionForward("/business/zshbusiness.jsp");
			}
		
		String userPid=loginUser.getUsersite();
		String parentID=loginUser.getParent_id();
		String userno=loginUser.getUserno();
		String seqNo=Tools.getStreamSerial(phone);
		double fee=FloatArith.yun2li(payfee);
		DBService db=null;
		BiProd bp=new BiProd();
		
		if (Integer.parseInt(phonePid) == 35
				&& Integer.parseInt(phoneType) == 0) {
			db=new DBService();
			String serialNo=serialNo = "wh09015" + Tools.getNow3().substring(6)
			+ Tools.buildRandom(2) + Tools.buildRandom(3); 
				//Tools.getNow3().substring(2)+"110800003243"+Tools.buildRandom(2);
			int n=bp.zshDxFill(parentID, userno, phonePid,userPid, phoneType, phone, seqNo, 
					fee, loginUser.getSa_zone(), loginUser.getUsername(),"000004",serialNo,db);
			 // 0 成功 1 充值消息发送失败 -1 系统繁忙  -2 无对应充值接口  2 处理中 3失败
			if(n==0){
				request.setAttribute("barcode",1);
				request.setAttribute("mess", "充值成功");
			return new ActionForward("/business/zshbusiness.jsp");
			}else if(n==1||n==-1||n==6){
				request.setAttribute("mess", "充值失败");
				return new ActionForward("/business/zshbusiness.jsp");
			}else if(n==-5){
				request.setAttribute("mess", "充值失败,余额不足");
				return new ActionForward("/business/zshbusiness.jsp");
			}else if(n==10){
				request.setAttribute("mess", "一分钟内不允许重复交易");
				return new ActionForward("/business/zshbusiness.jsp");
			} else if (n == 2 || n == -2 || n == 3) {
				request.setAttribute("mess", "交易处理中,请联系客服");
				return new ActionForward("/business/zshbusiness.jsp");
			}else {
				request.setAttribute("mess", "处理中，请联系客服");
				return new ActionForward("/business/zshbusiness.jsp");
			}
		}else{
			try{
				db=new DBService();
				String ip=com.wlt.webm.util.Tools.getIpAddr(request);
				Map<String,Integer> map=bp.zshNationFill(parentID, userno, phonePid,
						userPid, phoneType, phone, seqNo, 
						fee, loginUser.getSa_zone(), db,loginUser.getUsername(), ip,null);
				int k=map.get("state");
				Integer barcode=0;
				barcode = map.get("barcode");
				Log.info("订单号:" + seqNo + " 充值结果=" + k);
				if (k == 0) {
					request.setAttribute("barcode",barcode);
					request.setAttribute("mess", "充值成功");
					return new ActionForward("/business/zshbusiness.jsp");	
				} else if (k > 20) {
					request.setAttribute("mess", "充值成功,用户话费余额:" + ((float) k)
							/ 1000);
					request.setAttribute("barcode",barcode);
					return new ActionForward("/business/zshbusiness.jsp");
				} else if (k == 1 || k == -1) {
					request.setAttribute("mess", "充值失败");
					return new ActionForward("/business/zshbusiness.jsp");
				} else if (k == 2 || k == -2 || k == 3) {
					request.setAttribute("mess", "交易处理中,请联系客服");
					return new ActionForward("/business/zshbusiness.jsp");
				}else if(k==10){
					request.setAttribute("mess", "一分钟内不允许重复交易");
					return new ActionForward("/business/zshbusiness.jsp");
				}else if(k==-5){
					request.setAttribute("mess", "充值失败,余额不足");
					return new ActionForward("/business/zshbusiness.jsp");
				}  else {
					request.setAttribute("mess", "充值处理中请联系客服");
					return new ActionForward("/business/zshbusiness.jsp");
				}
			}catch(SQLException e){
				e.printStackTrace();
				request.setAttribute("mess", "系统繁忙请稍后再试");
				return new ActionForward("/business/zshbusiness.jsp");	
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
				return new ActionForward(
						"/business/order.do?method=listReverse&msg=" + n);
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
			+ Tools.buildRandom(2) + Tools.buildRandom(3); //华鸿移动
		}else {
			return new ActionForward(
					"/business/order.do?method=listReverse&msg=4");
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
			return new ActionForward(
					"/business/order.do?method=listReverse&msg=1");//发送消息失败,充值失败
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
			return new ActionForward(
					"/business/order.do?method=listReverse&msg=3");
		}
		String code = rsMsg.getRsCode();
		if ("000".equals(code)) {
			return new ActionForward(
					"/business/order.do?method=listReverse&msg=0");
		} else {
			return new ActionForward(
					"/business/order.do?method=listReverse&msg=1");
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
		String ordid = request.getParameter("ordid");
		String ordno = request.getParameter("ordno");
		DBService db = null;
		try {
			db = new DBService();
			String orderTable = "wht_orderform_" + ordno.substring(2, 6);
			String acctTable = "wht_acctbill_" + ordno.substring(2, 6);
			String sql0 = "select childfacct,infacctfee from " + acctTable
					+ " where state=0 and tradeserial='" + ordid + "'";

			String[] str = db.getStringArray(sql0);
			if (null == str) {
				return new ActionForward(
						"/business/order.do?method=listReverse&msg=1");
			}
			db.setAutoCommit(false);
			String sql1 = "update " + orderTable
					+ " set state=7 where tradeserial='" + ordid + "'";
			String sql2 = "update " + acctTable
					+ " set state=1,accountleft=accountleft+"
					+ Integer.parseInt(str[1])
					+ " where state=0 and tradeserial='" + ordid + "'";
			String sql3 = "update wht_childfacct set accountleft=accountleft+"
					+ Integer.parseInt(str[1]) + " where childfacct='" + str[0]
					+ "'";
			String sql4 = "select childfacct,infacctfee from " + acctTable
					+ " where state=0 and tradetype=15 and tradeserial='"
					+ ordid + "'";
			String[] str1 = db.getStringArray(sql4);
			db.update(sql1);
			db.update(sql2);
			db.update(sql3);
			if (null != str1) {
				String sql5 = "update " + acctTable
						+ " set state=1,accountleft=accountleft-"
						+ Integer.parseInt(str1[1])
						+ " where state=0 and tradetype=15 and tradeserial='"
						+ ordid + "'";
				String sql6 = "update wht_childfacct set accountleft=accountleft-"
						+ Integer.parseInt(str1[1])
						+ " where childfacct='"
						+ str1[0] + "'";
				db.update(sql5);
				db.update(sql6);
			}
			db.commit();
		} catch (Exception ex) {
			db.rollback();
			Log.error("SMP话费退款出错:", ex);
			return new ActionForward(
					"/business/order.do?method=listReverse&msg=1");
		}
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
			int k = db.getInt(selectStr);
			if (k >= 10) {
				request.setAttribute("mess", "当月冲正次数已用完,无法冲正");
				return new ActionForward("/business/daibanhuRevert.jsp");
			}
			str = phone.split("#");
			if (null == str || str.length != 2) {
				request.setAttribute("mess", "不存在该笔交易或者已超过24小时");
				return new ActionForward("/business/daibanhuRevert.jsp");
			}
			String ordid = str[0];
			String buyid = str[1];
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
			}else if (buyid.equals("7")) {// 华鸿
				tradeNo = "06022";
				serialNo = "wh06022" + Tools.getNow3().substring(6)
				+ Tools.buildRandom(2) + Tools.buildRandom(3); //华鸿移动
			} else {
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
				Log.info("冲正处理中:" + seqNo);
				request.setAttribute("mess", "冲正处理中");
				return new ActionForward("/business/daibanhuRevert.jsp");// 发送消息失败
																			// ,
																			// 充值失败
			}
			String code = rsMsg.getRsCode();
			Log.info("代办点冲正状态:" + code);
			if ("000".equals(code)) {
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
			int k = db.getInt(selectStr);
			if (k >= 10) {
				request.setAttribute("mess", "当月冲正次数已用完,无法进行任何冲正操作");
				return new ActionForward("/business/daibanhuRevert.jsp");
			}
			String tablename = "wht_orderform_" + time.substring(2, 6);
			String sql = "select tradeobject,fee,DATE_FORMAT(tradetime,'%Y-%m-%d %k:%i:%s'),state,tradeserial,buyid from "
					+ tablename
					+ " where tradeobject='"
					+ phone
					+ "' and tradetime>'"
					+ Tools.getOtherTime(-1)
					+ "' and state not in(1,5,7) and buyid in(3,4,5)";
			list = db.getList(sql);
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
			request.setAttribute("ph", phone);
			request.setAttribute("revertList", list);
			return new ActionForward("/business/daibanhuRevert.jsp");
		}
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
			Log.error("成功出错");
			e.printStackTrace();
			request.setAttribute("mess", "操作失败");
			return new ActionForward(
					"/business/order.do?method=listReverse&msg=1");
		}
		request.setAttribute("mess", "操作成功");
		return new ActionForward("/business/order.do?method=listReverse&msg=0");
	}
	/**
	 * 判断返回结果的Action
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward qbZhiFuBaoTransfer(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response){
		if(null==request.getSession().getAttribute("userSession")){
			request.setAttribute("mess", "请先登录");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute("userSession");
		if(!userForm.getRoleType().equals("3")){
			request.setAttribute("mess", "当前用户类型不支持交易");
			return new ActionForward("/wlttencent/zshqbpay.jsp");
		}
		if(!Tools.validateTime()){
			request.setAttribute("mess", "23:50到00:10不允许交易");
			return new ActionForward("/wlttencent/zshqbpay.jsp");
		}
		String num=request.getParameter("order_price"); //充值数量
		String in_acct=request.getParameter("in_acct");//充值号码
		if("0".equals(num)||null==num||null==in_acct||num==""||in_acct==""){
			request.setAttribute("mess", "请输入完整的数据");
			return new ActionForward("/wlttencent/zshqbpay.jsp");
		}
		if(num.indexOf("-")!=-1){
			request.setAttribute("mess", "交易个数不合法!");
			return new ActionForward("/business/zshqbpay.jsp");
		}
		if(Integer.parseInt(num)>200){
			request.setAttribute("mess", "单次最多200个Q币");
			return new ActionForward("/wlttencent/zshqbpay.jsp");
		}
		BiProd bp=new BiProd();
		String[] strss=bp.getPlaceMoney("1", userForm.getUserno());
		if((Float.parseFloat(strss[0])-(Float.parseFloat(strss[1])+Float.parseFloat(num)))<0)
		{
			request.setAttribute("mess", "今天额度已用完,添加额度,请联系管理员!");
			return new ActionForward("/wlttencent/zshqbpay.jsp");
		}
		
		String user_no=userForm.getUserno();
		String sql0="select fundacct from wht_facct where user_no='"+user_no+"'";
		DBService dbService=null;
		//当前时间 yyyyMMddHHmmss
		TenpayXmlPath tenpayxmlpath = new TenpayXmlPath();
	    String currTime = TenpayUtil.getCurrTime();
		String transaction_id =TenpayUtil.getQBChars(currTime);
		String account="";
		try{
		dbService=new DBService(); 
		if(dbService.hasData("select tradeserial from wht_orderform_"+currTime.substring(2,6)+" where tradeserial='"+transaction_id+"'")){
			request.setAttribute("mess", "重复交易");
			return new ActionForward("/wlttencent/zshqbpay.jsp");
		}
		String facct=dbService.getString(sql0);
		String sql="select accountleft from  wht_childfacct where  childfacct ='"+facct+"01'";
		int money=dbService.getInt(sql);
		int rebate=1000;//qq币单价
		if(money-Integer.parseInt(num)*1000<=0){
			if(null!=dbService){
				dbService.close();
			}
			request.setAttribute("mess", "账户余额不足");
			return new ActionForward("/wlttencent/zshqbpay.jsp");
		}
		//广信指定订单流水号
		String GuangXinSerialNo=com.wlt.webm.util.Tools.getGuangXinSerialNo();
		String interfaceTypes="";
		//判断走那个接口
		if(WhQb.isUse(dbService,Integer.parseInt(num))){
			interfaceTypes="16";//广信
		}else{
			interfaceTypes="10";//冠名
		}
		
		Object[] orderObj={null,userForm.getSa_zone(),transaction_id,
				in_acct,interfaceTypes,"0005",
				Integer.parseInt(num)*rebate,Integer.parseInt(num)*rebate,
				facct+"01",currTime,currTime,4,GuangXinSerialNo,
				transaction_id+"#"+Integer.parseInt(num)*rebate,"0",
				user_no,userForm.getUsername(),3,381,null};//20
		String acct1=Tools.getAccountSerial(currTime, user_no);
		Object[] acctObj={null,facct+"01",acct1,in_acct,currTime,Integer.parseInt(num)*rebate,
				Integer.parseInt(num)*rebate,6,"购买Q币",0,currTime,money-(Integer.parseInt(num)*rebate),
				transaction_id,facct+"01",1};
		boolean rsFlag=false;
		rsFlag=bp.innerDeal(dbService, orderObj, acctObj, null, "1", facct,
					null, currTime, Integer.parseInt(num)*rebate, 0);
		String barSeqNo = transaction_id;
		bp.saveBarcodeAndOrder(5,barSeqNo, dbService); // 保存条码及订单
		if(rsFlag==false){
			request.setAttribute("mess", "交易失败");
			bp.delBarcodeAndOrder(barSeqNo,dbService);
			return new ActionForward("/wlttencent/zshqbpay.jsp");		
		}
		String ip=Tools.getIpAddr(request);
//		Map map=new HashMap<String, String>();
//		map.put("gameapi", "qqes");
//		map.put("account", in_acct);
//		map.put("buynum", num);
//		map.put("sporderid", transaction_id);
//		map.put("gamecode", null);
//		map.put("gamearea", null);
//		map.put("gameserver", null);
//		map.put("gamerole", null);
//		map.put("clientip", ip);
//		String returl="http://www.wanhuipay.com/business/bank.do?method=GuanMingCallBack";
////		String returl="http://shijianqiao321.xicp.net:8888/business/bank.do?method=GuanMingCallBack";
//		map.put("returl",returl);
//		GuanMingBean gmBean=new GuanMingBean();
//		Map<String,String> rs=gmBean.fillGameOrder(map);
		
		Map<String, String> rs=null;
		if("16".equals(interfaceTypes)){//广信
			Log.info("中石化万恒Q币充值,广信充值,WhQb.isUse=true,,,transaction_id："+transaction_id+",writeoff:"+GuangXinSerialNo);
			//广信
			int ss=WhQb.useQB(GuangXinSerialNo, GuangXinSerialNo, in_acct, Integer.parseInt(num)*rebate);
			if(ss==0){
				Log.info("中石化万恒Q币充值,广信充值,充值成功,WhQb.isUse=true,,,transaction_id："+transaction_id+",writeoff:"+GuangXinSerialNo);
				rs=new HashMap<String, String>();
				rs.put("state",ss+"".trim());
				rs.put("orderid", "GuangXing09024");
				rs.put("InterfaceType","1");//广信
			}else if(ss==-1){
				Log.info("中石化万恒Q币充值,广信充值,充值失败,调用冠名接口充值,WhQb.isUse=true,,,transaction_id："+transaction_id+",writeoff:"+GuangXinSerialNo);
				//失败，调用冠名
				rs=QbMiddle.QueryGuanMingState(in_acct,num,transaction_id,ip);
				rs.put("InterfaceType","2");//冠名
				Log.info("中石化万恒Q币充值,广信充值,充值失败,调用冠名接口充值,充值结果:"+rs.get("state")+",WhQb.isUse=true,,,transaction_id："+transaction_id+",writeoff:"+GuangXinSerialNo);
				dbService.update("UPDATE wht_orderform_"+Tools.getNowDate().substring(2,6)+" SET buyid='10' WHERE tradeserial='"+transaction_id+"'");
			}else{
				Log.info("中石化万恒Q币充值,广信充值,充值处理中,WhQb.isUse=true,,,transaction_id："+transaction_id+",writeoff:"+GuangXinSerialNo);
				//处理中
				rs=new HashMap<String, String>();
				rs.put("state",ss+"".trim());
				rs.put("orderid", "GuangXing09024");
				rs.put("InterfaceType","1");//广信
			}
		}else{//冠名
			Log.info("中石化万恒Q币充值,冠名充值,WhQb.isUse=false,,,transaction_id："+transaction_id+",writeoff:"+GuangXinSerialNo);
			//冠名
			rs=QbMiddle.QueryGuanMingState(in_acct,num,transaction_id,ip);
			rs.put("InterfaceType","2");//冠名
			Log.info("中石化万恒Q币充值,冠名充值,充值结果:"+rs.get("state")+",WhQb.isUse=false,,,transaction_id："+transaction_id+",writeoff:"+GuangXinSerialNo);
		}
		String state=rs.get("state");
		
		String msg="";
		String orderTable="wht_orderform_"+currTime.substring(2, 6);
		String acctTable="wht_acctbill_"+currTime.substring(2, 6);
		if(state.equals("0")){
			msg="交易成功";
			bp.innerQQSuccessDeal(dbService, orderTable, 
					transaction_id+"#"+Integer.parseInt(num)*rebate+"#"+rs.get("orderid"),
					transaction_id, user_no,rs.get("InterfaceType"));
		}else if(state.equals("-1")){
			msg="交易 失败";
			bp.delBarcodeAndOrder(barSeqNo,dbService);
			bp.innerFailDeal(dbService, orderTable, acctTable, 
						transaction_id+"#"+Integer.parseInt(num)*rebate+"#"+rs.get("orderid"), 
						transaction_id, user_no, acct1, "",
						Integer.parseInt(num)*rebate, 0, facct,"", "1");
		}else {
			msg="交易处理中请联系客服";
			bp.innerTimeOutDeal(dbService, orderTable,transaction_id, user_no);
		}
		request.setAttribute("mess", msg);
		return new ActionForward("/wlttencent/zshqbpay.jsp");	
		
		}catch (Exception e) {
			request.setAttribute("mess", "系统异常请联系客服");
			return new ActionForward("/wlttencent/zshqbpay.jsp");	
		}
	}
	
}
