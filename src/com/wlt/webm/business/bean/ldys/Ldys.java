package com.wlt.webm.business.bean.ldys;

import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;

/**
 * 联动优势
 * 
 * @author 1989
 * 
 */
public class Ldys {

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
	private static final String notify_url = "http:///wh/business/bank.do?method=zhiXinB";
	/**
	 * 账号
	 */
	private static final String mrch_no = "AdminWangh";
	/**
	 * 密钥
	 */
	private static final String SIGN = "";

	/**
	 * http请求操作类
	 * 
	 * @param send_json
	 * @param clientOrderId
	 * @param type
	 *            0 查询 1 订购
	 * @return 服务端返回JSON
	 */
	public static String OP(String send_json, String clientOrderId, int type) {
		PostMethod postMt = new PostMethod(CZ_URL);
		try {
			byte[] bytedata = send_json.getBytes("UTF-8");
			RequestEntity en = new ByteArrayRequestEntity(bytedata);
			postMt.setRequestEntity(en);
			postMt.setRequestHeader("Content-Type",
					"application/json;charset=UTF-8");
			int st = client.executeMethod(postMt);
			Log.info("联动优势流量" + type + ",订单号:" + clientOrderId + ",,http响应状态:"
					+ st);
			if (st == 200) {
				String jg = postMt.getResponseBodyAsString();
				Log.info("联动优势流量" + type + ",订单号:" + clientOrderId
						+ ",,响应json内容:" + jg);
				if (jg == null) {
					Log.info("联动优势流量" + type + ",订单号:" + clientOrderId
							+ ",,解析json,内容为空");
					return null;
				} else {
					return jg;
				}
			}
		} catch (Exception e) {
			Log.error("联动优势流量" + type + ",订单号:" + clientOrderId + ",,系统异常,ex:"
					+ e);
		} finally {
			postMt.releaseConnection();
		}
		Log.info("联动优势流量订购,订单号:" + clientOrderId + ",,未知提交状态,return 2");
		return null;
	}

	public static int queryProducts(String type) {
		String value = "";
		if ("0".equals(type)) {
			value = "TELECOM";
		} else if ("1".equals(type)) {
			value = "MOBILE";
		} else {
			value = "UNICOM";
		}
		HashMap<String, String> req = new HashMap<String, String>();
		req.put("operator", value);
		req.put("province", "CHINA");
		req.put("type", "S");
		String res=OP(JSON.toJSONString(req), "query", 0);
		System.out.println(res);
		return 0;
	}

	public static void main(String[] args) throws InterruptedException {
		queryProducts("0");
	}
}
