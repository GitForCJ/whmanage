package com.wlt.webm.AccountInfo;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.util.Digest;

public class JingFengInter {
	
	/*
	 * 正式
	 */
	public static final String key = "da68d15a62a345ed5c9d67490fc7ea47";//key
	public static final String agentUserno = "SZWH201411141830";//代理商编号
	public static final String getBackUrl = "http://www.wanhuipay.com/Callback.do";//回调地址
	public static final String exp = "wanheng";
	
	public static final String RechargeUrl = "http://202.85.213.156:8080/youpintongAgentByInterfaceServlet";//充值地址
	public static final String QueryUrl = "http://202.85.213.156/youpintong/youpintongAgentQueryInterfaceServlet";//查询地址
	/*
	 * 测试
	public static final String key = "62ee6d764238c09ce8327e93886db1d5";
	public static final String agentUserno = "SWDZ201212101700";
	public static final String getBackUrl = "http://shijianqiao321a.xicp.net:9009/wh/Callback.do";//
	public static final String exp = "wanheng";

	public static final String RechargeUrl = "http://123.121.42.172:8081/youpintong/youpintongAgentByInterfaceServlet";
	public static final String QueryUrl = "http://123.121.42.172:8081/youpintong/youpintongAgentQueryInterfaceServlet";
	*/	
	/**
	 * 劲峰全国三网 充值
	 * 
	 * @param phoneNumber
	 *            手机号码
	 * @param mm
	 *            充值金额
	 * @param orderNo
	 *            订单号
	 * @param phoneType
	 *            手机号码类型
	 * @param interType
	 *            是否是OF供货
	 * @return int 状态码
	 */
	@SuppressWarnings("static-access")
	public static int RechargeFile(String phoneNumber, String mm,String orderNo, String phoneType, String interType) {
		Log.info("劲峰全国三网 充值方法");
		if (phoneNumber == null || "".equals(phoneNumber)
				|| phoneNumber.trim().length() != 11 || mm == null
				|| "".equals(mm) || orderNo == null || "".equals(orderNo)
				|| phoneType == null || "".equals(phoneType)) {
			Log.info("劲峰全国三网 充值方法 参数不够 手机号=" + phoneNumber + "   充值金额=" + mm
					+ "    订单号=" + orderNo + "  充值类型:" + phoneType);
			return -1;
		}

		// 充值金额单位转成 元
		String money = Integer.parseInt(mm) / 1000 + "".trim();

		if ("0".equals(phoneType)) {// 0 电信
			phoneType = "SHKC_CT";
		} else if ("1".equals(phoneType)) {// 1移动
			phoneType = "SHKC";
		} else if ("2".equals(phoneType)) {// 2联通
			phoneType = "SHKC_CU";
		} else {
			// 未知充值类型
			Log.info("劲峰全国三网 充值方法,未知充值类型，，return -1");
			return -1;
		}
		String P0_biztype, P1_agentcode, P2_mobile, P3_parvalue, P4_productcode, P5_requestid, P6_callbackurl, P7_extendinfo;
		P0_biztype = "mobiletopup";
		P1_agentcode = agentUserno;
		P2_mobile = phoneNumber.trim();
		P3_parvalue = money;
		P4_productcode = phoneType;
		P5_requestid = orderNo;
		P6_callbackurl = getBackUrl;
		P7_extendinfo = exp;
		String aValue = P0_biztype + P1_agentcode + P2_mobile + P3_parvalue
				+ P4_productcode + P5_requestid + P6_callbackurl
				+ P7_extendinfo;
		String hmac = Digest.hmacSign(aValue, key);

		// 请求url
		StringBuffer buf = new StringBuffer();
		buf.append(RechargeUrl);
		buf.append("?");
		buf.append("P0_biztype=" + P0_biztype);
		buf.append("&P1_agentcode=" + P1_agentcode);
		buf.append("&P2_mobile=" + P2_mobile);
		buf.append("&P3_parvalue=" + P3_parvalue);
		buf.append("&P4_productcode=" + P4_productcode);
		buf.append("&P5_requestid=" + P5_requestid);
		buf.append("&P6_callbackurl=" + P6_callbackurl);
		buf.append("&P7_extendinfo=" + P7_extendinfo);
		buf.append("&hmac=" + hmac);

		Log.info("劲峰全国三网 请求上行充值" + "   请求类型:" + interType + "  url : "
				+ buf.toString());
		try {
			URL u = new URL(buf.toString());
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("GET");
			InputStream in = conn.getInputStream();
			byte b[] = new byte[1024];
			in.read(b);
			String result = new String(b, "utf-8").trim();
			in.close();
			conn.disconnect();
			Log.info("劲峰 充值请求返回字符串 ： " + result + "   请求类型:" + interType);
			if ("000000".equals(result) || "100032".equals(result) || "100029".equals(result) || result.length()!=6) {
				Log.info("劲峰 充值 订单提交成功,提交状态:"+result+",交易订单号 ：" + orderNo + "   请求类型:" + interType);
				// //订单请求成功, 每20秒去查询
				if ("OF".equals(interType)) {
					int con = 0;
					while (true) {
						if (con >=36) {
							break;
						}
						Thread.sleep(5000);
						Log.info("劲峰 OF 充值 查询订单,,,,订单号：" + orderNo);
						String a1 = JingFengInter.getOrderState(orderNo);
						if ("0".equals(a1)) {
							Log.info("劲峰充值OF订单状态:充值成功,,,,订单号：" + orderNo+ ",订单状态:" + a1);
							return 0;
						}
						if ("1".equals(a1)) {
							Log.info("劲峰充值OF订单状态:充值成失败,,,,订单号：" + orderNo+ ",订单状态:" + a1);
							return -1;
						}
						con++;
					}
					return -2;
				} else {
					Log.info("劲峰 充值 订单上行请求成功,,,,,订单号：" + orderNo);
					return 0;
				}
			}
		} catch (Exception e) {
			Log.error("劲峰  发起充值请求 异常,,,订单号：" + orderNo + "   请求类型:" + interType
					+ "  异常：" + e);
			return -2;
		}
		Log.info("劲峰 充值请求验证失败,,,,,订单号：" + orderNo + "   请求类型:" + interType);
		// 订单请求失败
		return -1;
	}

	/**
	 * 订单查询
	 * 
	 * @param orderNo
	 * @return 订单状态
	 */
	public static String getOrderState(String orderNo) {
		Log.info("劲峰 订单查询请求 ，，，订单号:" + orderNo);
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(QueryUrl);
		buffer.append("?");
		buffer.append("P1_agentcode=" + agentUserno);
		buffer.append("&P5_requestid=" + orderNo);
		buffer.append("&hmac="+Digest.hmacSign((agentUserno+orderNo), key));
		
		Log.info("劲峰 订单查询请求 url=" + buffer.toString());
		try {
			URL u = new URL(buffer.toString());
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("GET");
			InputStream in = conn.getInputStream();
			byte b[] = new byte[1024];
			in.read(b);
			String result = new String(b, "utf-8").trim();
			in.close();
			conn.disconnect();
			
			Log.info("劲峰 订单号:" + orderNo + "查询请求返回结果 " + result);
			if (result != null && !"".equals(result)) {
				String[] strs=result.split("\\|");
				if ("000000".equals(strs[0])) {
					if("2".equals(strs[5])){
						return "0";//成功
					}else if("6".equals(strs[5])){
						return "1";//退款成功
					}else{
						return "-2";//处理中
					}
				}
			}
			return "-2";
		} catch (Exception e) {
			Log.error("劲峰 查询订单异常,,,,," + e);
			return "-2";
		}
	}
	public static void main(String[] arg){
//		RechargeFile("18824588427","1000","12345678","1","OF");
		getOrderState("12345678");
//		System.out.println("000000|SWDZ201212101700|1234567|18824588427||0|488e21985e0ec00fdba1f00cd98a45eb".split("\\|").length);
	}
}
