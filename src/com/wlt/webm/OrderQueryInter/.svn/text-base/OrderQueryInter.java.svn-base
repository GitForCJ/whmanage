package com.wlt.webm.OrderQueryInter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.form.TPForm;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.ten.service.MD5Util;
import com.wlt.webm.tool.Tools;

/**
 * @author adminA
 *
 */
public class OrderQueryInter  extends DispatchAction {
	
	/**
	 *单笔订单查询接口
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null 
	 * @throws Exception
	 */
	public ActionForward getOrderQueryInters(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("application/json;charset=UTF-8");
		 response.setCharacterEncoding("UTF-8");
		
		Log.info("单笔订单查询接口...");
		String userSysNo = request.getParameter("userSysNo");
		String QueryNum = request.getParameter("QueryNum");
		String ptOrderNo = request.getParameter("ptOrderNo");
		String sign = request.getParameter("sign");

		String orderNo=getStr();
		//从缓存中获取接口商信息
		TPForm tp=MemcacheConfig.getInstance().getTP(userSysNo);
		if(tp==null){
			Log.info("代理商不存在或者状态不正常...");
			send2TP(fillResponseXML("FAILED", "108", "代理商不存在或者状态不正常", ptOrderNo, orderNo, null,
					null),response);
			return null;
		}
		//验证ip或者终端号是否合法
		String ip=Tools.getIpAddr(request);
		if(tp.getIp().indexOf(ip)<0){
			Log.info("绑定的IP或终端编号地址不合法...");
			send2TP(fillResponseXML("FAILED", "105", "绑定的IP或终端编号地址不合法", ptOrderNo, orderNo, null,
					null),response);
			return null;	
		}
		if(null == userSysNo || null == QueryNum || null == ptOrderNo
				|| null == sign ) {
			Log.info("参数不完整...");
			send2TP(fillResponseXML("FAILED", "101", "参数不完整", ptOrderNo, orderNo, null,
					null),response);
			return null;
		}

        String signString=userSysNo+ptOrderNo+tp.getKeyvalue();
		if(!MD5Util.MD5Encode(signString,"UTF-8").equals(sign)){
			Log.info("签名验证失败...");
			send2TP(fillResponseXML("FAILED", "102", "签名验证失败", ptOrderNo, orderNo, null,
					null),response);
			return null;	
		}
		
		OrderInfo info =new OrderInfo();
    	int stat=info.getOrderInfo(ptOrderNo,getTableName());
		if(stat==-1){
			Log.info("系统异常(需要查询确认交易)...-1");
			send2TP(fillResponseXML("FAILED", "106", "系统异常(需要查询确认交易)", ptOrderNo, orderNo, null,
					null),response);
			return null;	
		} 
		
		if(stat==-2){
			Log.info("订单不存在 -2");
			send2TP(fillResponseXML("FAILED", "501", "订单不存在", ptOrderNo, orderNo, null,
					null),response);
			return null;	
		} 
		//0、成功，1、失败，2、未响应（计费没有响应），3 未处理（未扣款） 4 处理中（已扣款）5 冲正 6异常订单7已退费 
		
		if(stat==0)
		{
			Log.info("请求成功....订单状态:0");
			send2TP(fillResponseXML("SUCCESS", "000", "成功", ptOrderNo, orderNo, null,
					null),response);
			return null;
		}
		if(stat==1)
		{
			Log.info("请求成功....订单状态:1");
			send2TP(fillResponseXML("SUCCESS", "208", "失败", ptOrderNo, orderNo, null,
					null),response);
			return null;
		}
		if(stat==2)
		{
			Log.info("请求成功....订单状态:2");
			send2TP(fillResponseXML("SUCCESS", "903", "未响应（计费没有响应）", ptOrderNo, orderNo, null,
					null),response);
			return null;
		}
		if(stat==3)
		{
			Log.info("请求成功....订单状态:3");
			send2TP(fillResponseXML("SUCCESS", "904", "未处理（未扣款）", ptOrderNo, orderNo, null,
					null),response);
			return null;
		}
		if(stat==4)
		{
			Log.info("请求成功....订单状态:4");
			send2TP(fillResponseXML("SUCCESS", "905", "处理中（已扣款）", ptOrderNo, orderNo, null,
					null),response);
			return null;
		}
		if(stat==5)
		{
			Log.info("请求成功....订单状态:5");
			send2TP(fillResponseXML("SUCCESS", "907", "冲正", ptOrderNo, orderNo, null,
					null),response);
			return null;
		}
		if(stat==6)
		{
			Log.info("请求成功....订单状态:6");
			send2TP(fillResponseXML("SUCCESS", "210", "订单存疑", ptOrderNo, orderNo, null,
					null),response);
			return null;
		}
		if(stat==7)
		{
			Log.info("请求成功....订单状态:7");
			send2TP(fillResponseXML("SUCCESS", "906", "已退费", ptOrderNo, orderNo, null,
					null),response);
			return null;
		}
		return null;

	}
	
	/**
	 * 对外接口充值返回信息
	 * 
	 * @param resMsg
	 * @param rsCode
	 * @param failedReason
	 * @param ptOrderNo
	 * @param whtOrderNo
	 * @param orderSuccessTime
	 * @param result
	 * @return xml
	 */
	public String fillResponseXML(String resMsg, String rsCode,
			String failedReason, String ptOrderNo, String whtOrderNo,
			String orderSuccessTime,
			List<HashMap<String, String>> result) {
		StringBuilder sf = new StringBuilder();
		sf.append(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><resMsg>")
				.append(resMsg).append("</resMsg>").append("<rsCode>").append(
						rsCode).append("</rsCode>").append("<failedReason>")
				.append(failedReason).append("</failedReason>").append(
						"<ptOrderNo>").append(ptOrderNo).append("</ptOrderNo>")
				.append("<whtOrderNo>").append(whtOrderNo).append(
						"</whtOrderNo>");
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
	 * 向第三方发送结果
	 * 
	 * @param rs
	 * @param response
	 */
	public void send2TP(String rs, HttpServletResponse response) {
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(rs);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			Log.error("向第三方发送结果失败：" + rs + "  再次发送...");
			send2TP(rs, response);
		}
	}

	/**
	 * 生成 订单号，年月日时分秒+六位随机数  组成
	 * @return String  订单编号
	 */
	public synchronized static String getStr()
	{
		return Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
	}
	/**
	 * 获取当前月对应的表
	 * @return String  表名
	 */
	public  static String getTableName()
	{
		 Calendar cal=Calendar.getInstance();//使用日历类
		  String year=cal.get(Calendar.YEAR)+"";//得到年
		  int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
		  
		  String tableName=year.substring(year.length()-2,year.length())+(month<10?("0"+month):month);
		  return tableName;
	}
}
