package com.wlt.webm.business.bean.zhixin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.ten.service.MD5Util;

/**
 * 福建智信
 * 
 * @author 1989
 * 
 */
public class ZhiXin {

	static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	static HttpClient client = new HttpClient(connectionManager);
	static {
		client.getHttpConnectionManager().getParams().setConnectionTimeout(
				60 * 1000);// 链接时间 60秒，单位是毫秒
		client.getHttpConnectionManager().getParams().setSoTimeout(60 * 1000);
	}
	/**
	 * 充值请求url
	 */
	private static final String CZ_URL = "http://api.julives.com:9080/zxpaycore/v2/recharge";
	private static final String notify_url="http:///wh/business/bank.do?method=zhiXinB";
	/**
	 * 账号
	 */
	private static final String mrch_no = "AdminWangh";
	/**
	 * 密钥
	 */
	private static final String SIGN = "";

	/**
	 * 订单预受理
	 * @param phoneNum  电话号码
	 * @param mz  充值面额 不带MB
	 * @param clientOrderId 订单号
	 * @param request_time   请求时间14位
	 * @param product_type   填4
	 * @return
	 */
	public static String ZxPreOrders(String phoneNum, String mz,
			String clientOrderId, String request_time,String product_type) {
		HashMap<String,String> rs=new HashMap<String,String>();
		rs.put("mrch_no", mrch_no);
		rs.put("request_time", request_time);
		rs.put("client_order_no", clientOrderId);
		rs.put("product_type", product_type);
		rs.put("phone_no", "phoneNum");
		rs.put("cp", "");
		rs.put("city_code", "");
		rs.put("recharge_amount", mz);
		rs.put("recharge_type", "0");
		rs.put("recharge_desc", "");
		rs.put("notify_url", notify_url);
		ArrayList<String> al=new ArrayList<String>(rs.keySet());
		Collections.sort(al);
		String md5sign = "";
		for(String key:al){
			md5sign+=key+rs.get(key);
		}
		String sign=MD5Util.MD5Encode(md5sign+SIGN, "UTF-8");
		rs.put("sign", sign);
        String send_json=JSON.toJSONString(rs);
		PostMethod postMt = new PostMethod(CZ_URL);
		try {
			byte[] bytedata = send_json.getBytes("UTF-8");
			RequestEntity en = new ByteArrayRequestEntity(bytedata);
			postMt.setRequestEntity(en);
			postMt.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
			int st = client.executeMethod(postMt);
			Log.info("智信流量订购,订单号:" + clientOrderId + ",,http响应状态:" + st);
			if (st == 200) {
				String jg = postMt.getResponseBodyAsString();
				Log.info("智信流量订购,订单号:" + clientOrderId + ",,响应json内容:" + jg);
				if (jg == null) {
					Log.info("智信流量订购,订单号:" + clientOrderId + ",,解析json,内容为空");
					return "2";
				}else {
					FillResponse res=JSON.parseObject(jg, FillResponse.class);
					String code=res.getCode();
					if("2".equals(code)){
						return "0";
					}else if("625".equals(code)){
						return "2";//存疑
					}else{
						return "-1";
					}
				}
			}
		} catch (Exception e) {
			Log.error("智信流量订购,订单号:" + clientOrderId + ",,系统异常,ex:" + e);
		} finally {
			postMt.releaseConnection();
		}
		Log.info("智信流量订购,订单号:" + clientOrderId + ",,未知提交状态,return 2");
		return "2";
	}

	public static void main(String[] args) throws InterruptedException {
		// String s="123456789"+(int)(Math.random()*10000)+"";
		// CZ_Orders("18824588427","10",s);
		// System.out.println();
		// // Thread.sleep(10*1000);
		// QueryOrders("1234567893058","18824588427");
		int n=0;
		int k=n>0 ? 1:1;
		System.out.println(k);

	}
}
