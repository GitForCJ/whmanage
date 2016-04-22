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
 * ��������
 * 
 * @author 1989
 * 
 */
public class ZhiXin {

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
	private static final String notify_url="http:///wh/business/bank.do?method=zhiXinB";
	/**
	 * �˺�
	 */
	private static final String mrch_no = "AdminWangh";
	/**
	 * ��Կ
	 */
	private static final String SIGN = "";

	/**
	 * ����Ԥ����
	 * @param phoneNum  �绰����
	 * @param mz  ��ֵ��� ����MB
	 * @param clientOrderId ������
	 * @param request_time   ����ʱ��14λ
	 * @param product_type   ��4
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
			Log.info("������������,������:" + clientOrderId + ",,http��Ӧ״̬:" + st);
			if (st == 200) {
				String jg = postMt.getResponseBodyAsString();
				Log.info("������������,������:" + clientOrderId + ",,��Ӧjson����:" + jg);
				if (jg == null) {
					Log.info("������������,������:" + clientOrderId + ",,����json,����Ϊ��");
					return "2";
				}else {
					FillResponse res=JSON.parseObject(jg, FillResponse.class);
					String code=res.getCode();
					if("2".equals(code)){
						return "0";
					}else if("625".equals(code)){
						return "2";//����
					}else{
						return "-1";
					}
				}
			}
		} catch (Exception e) {
			Log.error("������������,������:" + clientOrderId + ",,ϵͳ�쳣,ex:" + e);
		} finally {
			postMt.releaseConnection();
		}
		Log.info("������������,������:" + clientOrderId + ",,δ֪�ύ״̬,return 2");
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
