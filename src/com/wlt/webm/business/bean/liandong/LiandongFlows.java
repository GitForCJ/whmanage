package com.wlt.webm.business.bean.liandong;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.commsoft.epay.util.logging.Log;

public class LiandongFlows {

	static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	static HttpClient client = new HttpClient(connectionManager);
	static {
		client.getHttpConnectionManager().getParams().setConnectionTimeout(
				60 * 1000);// ����ʱ�� 60�룬��λ�Ǻ���
		client.getHttpConnectionManager().getParams().setSoTimeout(60 * 1000);// ��ȡʱ��
																				// 20�룬��λ�Ǻ���
	}
	
	/**
	 * ��ֵ����url
	 */
	private static final String CZ_URL = "http://114.113.159.224:9001/flow/std/order";
	/**
	 * ��Ʒ��ѯ���
	 */
	private static final String Query_URL = "http://114.113.159.224:9001/flow/std/queryproduct";
	/**
	 * ǩ��key
	 */
	private static final String key = "35150511B865466C";
	/**
	 * ǩ��IV
	 */
	private static final String iv = "B16FCC9B7C477F78";
	/**
	 * ������ʶ
	 */
	private static final String sign = "4359F5A50C4D4EB5900DAAEF9D576D99";
	
	/**
	 * �ص���ַ
	 */
	private static final String callbackUrl = "http://bigrookie.vicp.cc/whmanage/LiandongBack.do";
	
	/**
	 * head ��ͷ��
	 * 
	 * @return
	 */
	private static HeaderBean getHeader() {
		long timestamp = System.currentTimeMillis();
		HeaderBean bean = new HeaderBean();
		bean.setAdaptorId(sign);
		return bean;
	}

	/**
	 * ����������ֵ����
	 * 
	 * @param phoneNum
	 *            ��ֵ����
	 * @param mz
	 *            500M ���� 500
	 * @param orderNum
	 *            ������
	 * @return String 0 �ɹ� -1ʧ�� 2�����У��쳣
	 */
	public static String CZ_Orders(String phoneNum, String mz, String orderNum) {
		BodyOrderBean bodyBean = new BodyOrderBean();
		bodyBean.setOrderId(orderNum);
		bodyBean.setMobile(phoneNum);
		bodyBean.setProductId(mz);
		bodyBean.setOperator("MOBILE");
		bodyBean.setProvince("CHINA");
		bodyBean.setProxOrder(0);
		bodyBean.setCallbackUrl(callbackUrl);

		HeaderBean headerBean = getHeader();

		JsonBean obj = new JsonBean();
		obj.setHeader(headerBean);
		obj.setBody(bodyBean);
		System.out.println("<<<<<<<<<<<<<<<<<<<<<"+JSON.toJSONString(obj));
		PostMethod postMt = new PostMethod(CZ_URL);
		try {
			String send_json = Aes128CBCUtil.encrypt(JSON.toJSONString(obj), key, iv);
			System.out.println("send_json-----------------"+send_json);
			Log.info("������������,������:" + orderNum + ",,����url:" + send_json);
			byte[] bytedata = send_json.getBytes("UTF-8");
			RequestEntity en = new ByteArrayRequestEntity(bytedata);
			postMt.setRequestEntity(en);
			postMt.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
			int st = client.executeMethod(postMt);
			Log.info("������������,������:" + orderNum + ",,http��Ӧ״̬:" + st);
			if (st == 200) {
				String rs = postMt.getResponseBodyAsString();
				Log.info("������������,������:" + orderNum + ",,��Ӧjson����:" + rs);
				
				JSONObject json = JSON.parseObject(rs);
				if (rs == null || "".equals(rs.toString())
						|| rs.indexOf("ret_code") == -1) {
					Log.info("������������,������:" + orderNum + ",,����json,����");
					return "2";
				}

				if ("0000".equals(json.get("respCode"))) {
					Log.info("������������,������:" + orderNum + ",,�����ύ�ɹ�,return 0");
					return "0";
				} if ("E0020".equals(json.get("respCode"))) {
					Log.info("������������,������:" + orderNum + ",,�����ύʧ��,return -1");
					return "-1";
				} else {
					Log.info("������������,������:" + orderNum + ",,������ֵ�л��쳣,return 2");
					return "2";
				}
			}
		} catch (Exception e) {
			Log.error("������������,������:" + orderNum + ",,���ö�����ѯ�ӿ�,,ϵͳ�쳣,ex:" + e);
		} finally {
			postMt.releaseConnection();
		}
		Log.info("������������,������:" + orderNum + ",,δ֪�ύ״̬,return 2");
		return "2";
	}

	/**
	 * ��Ʒ��ѯ�ӿ�
	 * 
	 * @param orderNum
	 * @param orderTime
	 *            YYYYMMDD
	 * @return String 0 �ɹ� -1ʧ�� 2�����У��쳣
	 */
	public static void QueryProducts() {
		QueryProductBean proBean = new QueryProductBean();
		proBean.setOperator("MOBILE");
		proBean.setProvince("CHINA");
		proBean.setType("S");

		String send_json = JSON.toJSONString(proBean);

		PostMethod postMt = new PostMethod(Query_URL);
		try {
			byte[] bytedata = send_json.getBytes("UTF-8");
			RequestEntity en = new ByteArrayRequestEntity(bytedata);
			postMt.setRequestEntity(en);
			postMt.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
			int st = client.executeMethod(postMt);
			Log.info("����������Ʒ��ѯ,,http��Ӧ״̬:" + st);
			if (st == 200) {
				String result = postMt.getResponseBodyAsString();
				System.out.println("result:"+result);
				ResponseMessageBean bean = JSON.parseObject(result, ResponseMessageBean.class);
				Log.info("����������Ʒ��ѯ,,��Ӧjson����:" + result);
			}
		} catch (Exception e) {
			Log.error("����������Ʒ��ѯ,,ϵͳ�쳣,ex:" + e);
		} finally {
			postMt.releaseConnection();
		}
	}

	public static void main(String[] args) throws InterruptedException {
//			String s="123456789"+(int)(Math.random()*10000+10000)+"";
//			CZ_Orders("18824588427","30",s);
//			System.out.println();
//			Thread.sleep(3*1000);
//			QueryOrders(s,Tools.getNowDate());
		QueryProducts();
	}
}
