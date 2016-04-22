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
 * ��������
 * 
 * @author 1989
 * 
 */
public class Ldys {

	static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	static HttpClient client = new HttpClient(connectionManager);
	static {
		client.getHttpConnectionManager().getParams().setConnectionTimeout(
				60 * 1000);// ����ʱ�� 60�룬��λ�Ǻ���
		client.getHttpConnectionManager().getParams().setSoTimeout(60 * 1000);
	}
	/**
	 * ��ֵ����url
	 */
	private static final String CZ_URL = "http://api.julives.com:9080/zxpaycore/v2/recharge";
	private static final String notify_url = "http:///wh/business/bank.do?method=zhiXinB";
	/**
	 * �˺�
	 */
	private static final String mrch_no = "AdminWangh";
	/**
	 * ��Կ
	 */
	private static final String SIGN = "";

	/**
	 * http���������
	 * 
	 * @param send_json
	 * @param clientOrderId
	 * @param type
	 *            0 ��ѯ 1 ����
	 * @return ����˷���JSON
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
			Log.info("������������" + type + ",������:" + clientOrderId + ",,http��Ӧ״̬:"
					+ st);
			if (st == 200) {
				String jg = postMt.getResponseBodyAsString();
				Log.info("������������" + type + ",������:" + clientOrderId
						+ ",,��Ӧjson����:" + jg);
				if (jg == null) {
					Log.info("������������" + type + ",������:" + clientOrderId
							+ ",,����json,����Ϊ��");
					return null;
				} else {
					return jg;
				}
			}
		} catch (Exception e) {
			Log.error("������������" + type + ",������:" + clientOrderId + ",,ϵͳ�쳣,ex:"
					+ e);
		} finally {
			postMt.releaseConnection();
		}
		Log.info("����������������,������:" + clientOrderId + ",,δ֪�ύ״̬,return 2");
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
