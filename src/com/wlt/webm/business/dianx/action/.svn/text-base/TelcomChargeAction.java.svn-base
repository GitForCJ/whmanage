package com.wlt.webm.business.dianx.action;

import java.io.PrintWriter;
import java.sql.SQLException;
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

import whmessgae.TradeMsg;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.business.dianx.bean.TelcomPayBean;
import com.wlt.webm.business.dianx.form.TelcomForm;
import com.wlt.webm.business.form.SysUserInterfaceForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.message.PortalSend;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.tool.FloatArith;
import com.wlt.webm.tool.Tools;

public class TelcomChargeAction  extends DispatchAction{
	
	/** 同步锁 **/
	private static Object lock = new Object();
	
	
	
	/**
	 * 查询电信缴费账单
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward queryBill(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TelcomForm phonePayForm = (TelcomForm) form;
		String sepNo=Tools.getSeqNo(phonePayForm.getTradeObject().replaceAll(" ", "").trim());
		phonePayForm.setSeqNo(sepNo);
		phonePayForm.setTradeObject(phonePayForm.getTradeObject().replaceAll(" ", "").trim());
		SysUserForm userForm = (SysUserForm)request.getSession().getAttribute("userSession");
		phonePayForm.setAreaCode(userForm.getSa_zone());
		TelcomPayBean bean = new TelcomPayBean();
		
		int interfaceID=-1;
		DBService dbService =null;
		//根据面额，广东省，电信，获取接口id
		try {
			dbService=new DBService();
			String sql = "SELECT interid FROM sys_gddxinterfacemaping ORDER BY id ASC LIMIT 0,1";
			interfaceID = dbService.getInt(sql);
		} catch (SQLException e) {
			Log.error("电信查询账单区分接口异常，"+e);
		}finally{
			if(dbService!=null){
				dbService.close();
				dbService=null;
			}
		}
		int state=-100;
		if(interfaceID==9){//讯源电信
			state = bean.XYAccountQueryBill(phonePayForm,userForm);
			request.setAttribute("bool", false);
		}else if(interfaceID==19){//君宝电信
			state = bean.JunBaoRemainderQueryBill(phonePayForm,userForm);
			request.setAttribute("bool", true);
		}else{
			Log.info("电信查询账单未找到指定接口!");
			request.setAttribute("mess","系统繁忙,请稍后查询!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
		if(state==1){//获取帐单信息成功
		Log.info("开始查询电信固话帐单....," + phonePayForm.getNumType());
			request.setAttribute("phonePayForm", phonePayForm);
			return new ActionForward("/telcom/phonebillinfo.jsp");
		}else{      
			request.setAttribute("mess",phonePayForm.getMessage());
		 	return new ActionForward("/telcom/telcomPhonepay.jsp");
	    }
	}
	
	/**
	 * 君宝电信账单查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward queryAccountInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=gbk");
		response.setCharacterEncoding("gbk");
		PrintWriter out=response.getWriter();
		
		String tradeobject=request.getParameter("tradeobject");
		String numbertype=request.getParameter("numbertype");
		
		TelcomForm phonePayForm=new TelcomForm();
		phonePayForm.setNumType(numbertype);
		phonePayForm.setTradeObject(tradeobject);
		
		List<String[]> arryList=new TelcomPayBean().JunBaoAccountQueryBill(phonePayForm,"0");
		if(arryList==null || arryList.size()<=0){
			out.println(-1);
		}else{
			JSONArray json=JSONArray.fromObject(arryList); 
			out.print(json);
		}
		out.flush();
		out.close();
		return null;
	}
	
		
    /**
     * 电信充值
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward payBill(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		SysUserForm loginUser = (SysUserForm) request.getSession().getAttribute("userSession");
		if(null==loginUser){
			request.setAttribute("mess", "登录超时,请重新登录");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		if(!Tools.validateTime()){
			request.setAttribute("mess", "23:50到00:10不允许交易");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
		String numType=request.getParameter("numType");
		String phonePid="35";
		String phone=request.getParameter("tradeObject").replaceAll(" ", "");
		String payfee=request.getParameter("payFee");
		if(phone==null || "".equals(phone) ||
			payfee==null || "".equals(payfee))
		{
			Log.info("广东电信充值:系统异常   phone:"+phone+"     payfee:"+payfee);
			request.setAttribute("mess", "系统异常,请重新登录!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
		
		if(payfee.indexOf("-")!=-1)
		{
			request.setAttribute("mess", "充值金额必须是大于零!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
		
		String isdae=request.getParameter("isdae");//是否大额

		String userPid=loginUser.getUsersite();
		String parentID=loginUser.getParent_id();
		String userno=loginUser.getUserno();
		String seqNo=Tools.getStreamSerial(phone);
		double fee=(int)FloatArith.yun2li(payfee);
		if(fee<=0){
			request.setAttribute("mess", "充值金额必须是大于零!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
		String serialNo= "wh09015" + Tools.getNow3().substring(6)+ Tools.buildRandom(2) + Tools.buildRandom(3); //Tools.getNow3().substring(2)+"110800003243"+Tools.buildRandom(2);
		if("0".equals(isdae) && fee<2000000){
			//不允许
			request.setAttribute("mess", "充值金额小于2000不属于大额充值!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
		if(!"0".equals(isdae) && fee>=2000000){
			//不允许
			request.setAttribute("mess", "充值金额大于500请选中大额充值!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
		boolean flags=false;
		if("0".equals(isdae) && fee>=2000000){
			//大额充值
			flags=true;
		}
		
		//一分钟内不允许重复交易
		synchronized (lock) {
			if(MemcacheConfig.getInstance().get1(phone+fee+"repeat")){
				Log.info(phone+"-"+fee+" 一分钟内存在该交易");
				request.setAttribute("mess", "一分钟内不允许重复交易");
				return new ActionForward("/telcom/telcomPhonepay.jsp");
			}else{
				MemcacheConfig.getInstance().add(phone+fee+"repeat", phone);
			}
		}
		BiProd bp=new BiProd();
		float fundacctMoney=bp.getFundacctMoney(userno);
		if(fundacctMoney==-1){
			request.setAttribute("mess", "系统异常!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
		if((fundacctMoney-fee)<0){
			request.setAttribute("mess", "账户余额不足!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
		if(flags){//大额充值
			
			//启动现场，循环执行大面额充值
			Log.info("充值号码:"+phone+",大面额充值"+payfee+"元,启动线程!");
			new TelcomChargeThread(parentID,userno,phonePid,userPid,phone,loginUser.getSa_zone(),loginUser.getUsername(),numType,fee).start();
			
			request.setAttribute("mess", "请稍后查看交易明细!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
		
		//普通面额充值
		int n=bp.XYdxFill(parentID, userno, phonePid, userPid, "0",
				phone, seqNo, fee,loginUser.getSa_zone(),loginUser.getUsername(),numType,serialNo,null);
        // 0 成功 1 充值消息发送失败 -1 系统繁忙  -2 无对应充值接口  2 处理中 3失败
		//订单号  交易时间  代办户账户  商品名称  充值号码  付款金额   打印小票需要的数据
		String strA=seqNo+";"+Tools.getNewDate()+";"+loginUser.getUsername()+";电信缴费;"+phone+";"+payfee;
		if(n==0){
			request.setAttribute("strA",strA);
			request.setAttribute("mess", "充值成功");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}else if(n==1||n==3||n==-2||n==6){
			request.setAttribute("mess", "充值失败");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}else if(n==10){
			request.setAttribute("mess", "一分钟内不允许重复交易");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}else if(n==-5){
			request.setAttribute("mess", "充值失败,余额不足");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}else if(n==-1){
			request.setAttribute("mess", "系统繁忙,请稍后充值!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}else if(n==10){
			request.setAttribute("mess", "佣金比不存在请联系客服");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}else {
			request.setAttribute("mess", "处理中，请联系客服");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
	}

	 /**
	 * 获取用户接口
	 * @return 用户接口
	 * @throws Exception
	 */
	public String getInterfaceId(SysUserInterfaceForm sui) throws Exception {
        DBService db = new DBService();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select in_id")
                    .append(" from sys_user_interface ")
                    .append(" where user_id="+sui.getUser_id()+" and charge_type = "+sui.getCharge_type()+"  and province_code = "+sui.getProvince_code());
            System.out.println("slq:"+sql);
            return db.getString(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }

	
	/**省联通查询用户余额
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward queryUserMoney(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysUserForm userForm = (SysUserForm)request.getSession().getAttribute("userSession");
		if(userForm==null || "".equals(userForm)){
			request.setAttribute("mess", "登录超时,请重新登录");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		
		TelcomForm phoneInfo = (TelcomForm) form;
		
		String seqNo=Tools.getNow()+((int)(Math.random()*10000)+10000)+((char)(new Random().nextInt(26) + (int)'A'));
		String serialNo=Tools.getNow()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
		
		TradeMsg requestMsg=new TradeMsg();
		requestMsg.setSeqNo(seqNo);
		requestMsg.setServNo("00");
		requestMsg.setMsgFrom2("0103");
		requestMsg.setRsCode("000");
		requestMsg.setTradeNo("09023");
		requestMsg.setFlag("0");
		
		Map<String, String> content=new HashMap<String, String>();
		content.put("phone",phoneInfo.getTradeObject());
	    content.put("numType",phoneInfo.getNumType());
	    content.put("serialNo",serialNo);
	    
		requestMsg.setContent(content);
		if(!PortalSend.getInstance().sendmsg(requestMsg)){
			request.setAttribute("mess","查询失败!");
			return new ActionForward("/lt/payment.jsp");
		}
		int k=0;
		TradeMsg rsMsg=null;
		while(k<=180){
			k++;
			try {
				Thread.sleep(1000);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
			rsMsg=MemcacheConfig.getInstance().get(seqNo);
			if(null==rsMsg){
				continue;
			}else{
				MemcacheConfig.getInstance().delete(seqNo);
				break;
			}
		}
		if(null==rsMsg&&k>=180){
			request.setAttribute("mess","查询处理中!");
			return new ActionForward("/lt/payment.jsp");
		}
		String code=rsMsg.getRsCode();
		if("000".equals(code)){
			Map  rs=rsMsg.getContent();
			String ss=rs.get("fee").toString().replace(" ","");
			boolean bool=false;
			if(Float.parseFloat(ss)>0){
				bool=true;
				request.setAttribute("bool",bool);
			}
			phoneInfo.setTotalFee(ss);
			request.setAttribute("phonePayForm",phoneInfo);
			return new ActionForward("/lt/paymentInfo.jsp");
		}else {
			request.setAttribute("mess","查询失败!");
			return new ActionForward("/lt/payment.jsp");
		}
	}

	/**省联通缴费
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward UserPayMent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysUserForm loginUser = (SysUserForm) request.getSession().getAttribute("userSession");
		if(null==loginUser){
			request.setAttribute("mess", "登录超时,请重新登录");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		if(!Tools.validateTime()){
			request.setAttribute("mess", "23:50到00:10不允许交易");
			return new ActionForward("/lt/payment.jsp");
		}
		String numType=request.getParameter("numType");
		String phonePid="35";
		String phone=request.getParameter("tradeObject").replaceAll(" ", "");
		String payfee=request.getParameter("payFee");
		if(phone==null || "".equals(phone) ||
			payfee==null || "".equals(payfee))
		{
			Log.info("广东联通充值:系统异常   phone:"+phone+"     payfee:"+payfee);
			request.setAttribute("mess", "系统异常,请重新登录!");
			return new ActionForward("/lt/payment.jsp");
		}
		if(payfee.indexOf("-")!=-1)
		{
			request.setAttribute("mess", "充值金额必须是大于零!");
			return new ActionForward("/lt/payment.jsp");
		}
		String userPid=loginUser.getUsersite();
		String parentID=loginUser.getParent_id();
		String userno=loginUser.getUserno();
		String seqNo=Tools.getStreamSerial(phone);
		double fee=(int)FloatArith.yun2li(payfee);
		if(fee<=0){
			request.setAttribute("mess", "充值金额必须是大于零!");
			return new ActionForward("/lt/payment.jsp");
		}
		//一分钟内不允许重复交易
		synchronized (lock) {
			if(MemcacheConfig.getInstance().get1(phone+fee+"repeat")){
				Log.info(phone+"-"+fee+" 一分钟内存在该交易");
				request.setAttribute("mess", "一分钟内不允许重复交易");
				return new ActionForward("/lt/payment.jsp");
			}else{
				MemcacheConfig.getInstance().add(phone+fee+"repeat", phone);
			}
		}
		BiProd bp=new BiProd();
		float fundacctMoney=bp.getFundacctMoney(userno);
		if(fundacctMoney==-1){
			request.setAttribute("mess", "充值失败,系统异常!");
			return new ActionForward("/lt/payment.jsp");
		}
		if((fundacctMoney-fee)<0){
			request.setAttribute("mess", "充值失败,余额不足！");
			return new ActionForward("/lt/payment.jsp");
		}
		/**
		 * 测试用例 1000
		 
		for(int i=0;i<1000;i++){
			String serialNo=Tools.getNow()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
			Object[] strs=new Object[]{parentID, userno, phonePid, userPid, "2",
					phone, Tools.getStreamSerial(phone), fee,loginUser.getSa_zone(),loginUser.getUsername(),numType,serialNo};
			new TelcomChargeAction().new Cs(strs).start();
			try{
				Thread.sleep(1000);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		*/
		String serialNo=Tools.getNow()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
		//普通面额充值
		int n=bp.LianTongFill(parentID, userno, phonePid, userPid, "2",
				phone, seqNo, fee,loginUser.getSa_zone(),loginUser.getUsername(),numType,serialNo);
        // 0 成功 1 充值消息发送失败 -1 系统繁忙  -2 无对应充值接口  2 处理中 3失败
		//订单号  交易时间  代办户账户  商品名称  充值号码  付款金额   打印小票需要的数据
		String strA=seqNo+";"+Tools.getNewDate()+";"+loginUser.getUsername()+";联通缴费;"+phone+";"+payfee;
		if(n==0){
			request.setAttribute("strA",strA);
			request.setAttribute("mess", "充值成功");
			return new ActionForward("/lt/payment.jsp");
		}else if(n==1){
			request.setAttribute("mess", "充值失败");
			return new ActionForward("/lt/payment.jsp");
		}else if(n==3){
			request.setAttribute("mess", "充值异常,请联系客服");
			return new ActionForward("/lt/payment.jsp");
		}else if(n==4){
			request.setAttribute("mess", "三分钟重复充值");
			return new ActionForward("/lt/payment.jsp");
		}else if(n==5){
			request.setAttribute("mess", "充值失败,余额不足");
			return new ActionForward("/lt/payment.jsp");
		}else {
			request.setAttribute("mess", "处理中，请联系客服");
			return new ActionForward("/lt/payment.jsp");
		}
	}
	
	/**
	 * 省联通 账户余额查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward Query_Money(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/text;charset=GBK");  
		PrintWriter out=response.getWriter();
		SysUserForm loginUser = (SysUserForm) request.getSession().getAttribute("userSession");
		if(null==loginUser){
			out.println("请登录");
			out.flush();
			out.close();
			return null;
		}
		if(!"0".equals(loginUser.getRoleType())){
			out.println("没权限");
			out.flush();
			out.close();
			return null;
		}
		
		String serialNo=Tools.getNow()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
		
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(serialNo);
		msg.setFlag("0");// 有内容
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo("09035");
		Map<String, String> content = new HashMap<String, String>();
		content.put("serialNo", serialNo);
		msg.setContent(content);
		
		if (!PortalSend.getInstance().sendmsg(msg)) {
			out.println("异常");
			out.flush();
			out.close();
			return null;
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
			rsMsg = MemcacheConfig.getInstance().get(serialNo);
			if (null == rsMsg) {
				continue;
			} else {
				MemcacheConfig.getInstance().delete(serialNo);
				break;
			}
		}
		if(rsMsg==null || "".equals(rsMsg.getRsCode().trim())){
			out.println("异常");
			out.flush();
			out.close();
			return null;
		}
		if(!"000".equals(rsMsg.getRsCode())){
			out.println("异常");
			out.flush();
			out.close();
			return null;
		}
		Map<String,String> map=rsMsg.getContent();
		out.println(map.get("fee")+"元");
		out.flush();
		out.close();
		return null;
	}

	/**移动通缴费
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward MobileRecharge(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysUserForm loginUser = (SysUserForm) request.getSession().getAttribute("userSession");
		if(null==loginUser){
			request.setAttribute("mess", "登录超时,请重新登录");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		if(!Tools.validateTime()){
			request.setAttribute("mess", "23:50到00:10不允许交易");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}
		String phonePid="35";
		String phone=request.getParameter("tradeObject").replaceAll(" ", "");
		String payfee=request.getParameter("payFee");
		if(phone==null || "".equals(phone) ||
			payfee==null || "".equals(payfee) || phone.length()!=11){
			Log.info("广东移动充值:系统异常   phone:"+phone+"     payfee:"+payfee);
			request.setAttribute("mess", "系统异常,请重新登录!");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}
		if(payfee.indexOf("-")!=-1){
			request.setAttribute("mess", "充值金额必须是大于零!");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}
		String userPid=loginUser.getUsersite();
		String parentID=loginUser.getParent_id();
		String userno=loginUser.getUserno();
		String seqNo=Tools.getNow()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*1000)+1000);
		double fee=(int)FloatArith.yun2li(payfee);
		if(fee<=0){
			request.setAttribute("mess", "充值金额必须是大于零!");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}
		//一分钟内不允许重复交易
		synchronized (lock) {
			if(MemcacheConfig.getInstance().get1(phone+fee+"repeat")){
				Log.info(phone+"-"+fee+" 一分钟内存在该交易");
				request.setAttribute("mess", "一分钟内不允许重复交易");
				return new ActionForward("/Mobile/MobileRecharge.jsp");
			}else{
				MemcacheConfig.getInstance().add(phone+fee+"repeat", phone);
			}
		}
		BiProd bp=new BiProd();
		String[] st=bp.getPhoneInfo(phone.substring(0,7));
		if (null != st && st.length == 3) {
			if(!"35".equals(st[0]) || !"1".equals(st[1])){
				request.setAttribute("mess", "请充值广东移动号码!");
				return new ActionForward("/Mobile/MobileRecharge.jsp");
			}
		}else{
			request.setAttribute("mess", "请充值广东移动号码!");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}
		float fundacctMoney=bp.getFundacctMoney(userno);
		if(fundacctMoney==-1){
			request.setAttribute("mess", "充值失败,系统异常!");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}
		if((fundacctMoney-fee)<0){
			request.setAttribute("mess", "充值失败,余额不足！");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}
		//普通面额充值
		int n=1;//bp.MobileFill(parentID, userno, phonePid, userPid, "2",phone, seqNo, fee,loginUser.getSa_zone(),loginUser.getUsername(),"1");
        // 0 成功 1 充值消息发送失败 -1 系统繁忙  -2 无对应充值接口  2 处理中 3失败
		//订单号  交易时间  代办户账户  商品名称  充值号码  付款金额   打印小票需要的数据
		String strA=seqNo+";"+Tools.getNewDate()+";"+loginUser.getUsername()+";联通缴费;"+phone+";"+payfee;
		if(n==0){
			request.setAttribute("strA",strA);
			request.setAttribute("mess", "充值成功");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}else if(n==1||n==3||n==-2){
			request.setAttribute("mess", "充值失败");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}else if(n==-5){
			request.setAttribute("mess", "充值失败,余额不足");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}else {
			request.setAttribute("mess", "处理中，请联系客服");
			return new ActionForward("/Mobile/MobileRecharge.jsp");
		}
	}

	
	/**测试
	class Cs extends Thread{
		private Object[] strs;
		public Cs(Object[] strs){
			this.strs=strs;
		}
		public void run(){
			try {
				new BiProd().LianTongFill(strs[0].toString(), strs[1].toString(), 
						strs[2].toString(), strs[3].toString(), strs[4].toString(),
						strs[5].toString(), strs[6].toString(),Double.parseDouble(strs[7].toString()),
						strs[8].toString(),strs[9].toString(),strs[10].toString().toString(),strs[11].toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	*/
}
