package com.wlt.webm.business.bean.zb;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.MD5;

public class Mobile_Zb_Inter {

	public static final String cz_url = "http://www.zbqnw.cn/thirddeposit.act?";

	public static final String query_url = "http://www.zbqnw.cn/thirdQuery.act?";

	public static final String sellerId = "17817734369";// ������루Ψһ��ʶ��

	public static final String password = "1213ffaraeb89WHzh";//

	public static final String bizType_hf = "E_CHARGE";

	public static final String bizType_flow = "E_FLOW";

	public static final String backUrl = "http://www.wanhuipay.com/business/bank.do?method=zb_back";
	


//	public static void main(String[] args) {
//		cz("18824588427", "1000", "111111" + (int) (Math.random() * 1000), "");
//		 query("18824588427","111111178",bizType_flow);
//		cz_flow("18824588427","10", "0000000000" + (int) (Math.random() * 1000));
//	}

	/**
	 * ����� ��ֵ
	 * 
	 * @param phone
	 * @param money
	 *            ��
	 * @param orderid
	 * @param intype
	 * @return 0�ɹ� -1ʧ�� 2������
	 */
	public static String cz(String phone, String money, String orderid,
			String intype) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("accountVal=" + phone);
		buffer.append("&bizType=" + bizType_hf);
		buffer.append("&notifyUrl=" + backUrl);
		buffer.append("&productNo=" + money);
		buffer.append("&sellerId=" + sellerId);
		buffer.append("&tbOrderNo=" + orderid);
		buffer.append("&timeStmp=" + System.currentTimeMillis());
		buffer.append("&unitPrice=" + money);
		buffer.append("&sign="
				+ MD5.encode(buffer.toString() + "||" + password));
		String sendurl = cz_url + buffer.toString();

		Log.info("����ѳ�ֵ,������:" + orderid + ",����:" + phone + ",,����url:"
				+ sendurl);

		HttpClient client = null;
		PostMethod post = null;
		try {
			client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(
					60 * 1000);
			client.getHttpConnectionManager().getParams().setSoTimeout(
					60 * 1000);
			post = new PostMethod(sendurl);
			int status = client.executeMethod(post);
			Log.info("����ѳ�ֵ,������:" + orderid + ",����:" + phone + ",,http state:"
					+ status);
			if (status == 200) {
				String result = inputStream2String(post.getResponseBodyAsStream(),"gbk");
				Log.info("����ѳ�ֵ,������:" + orderid + ",����:" + phone
						+ ",,http��Ӧ����:" + result);
				if (result != null && !"".equals(result.trim())
						&& result.indexOf("<coopOrderStatus>") > 0
						&& result.indexOf("</coopOrderStatus>") > 0) {
					result = result.substring(result
							.indexOf("<coopOrderStatus>")
							+ "<coopOrderStatus>".length(), result
							.indexOf("</coopOrderStatus>"));
					if ("SUCCESS".equals(result)) {
						if ("OF".equals(intype)) {
							for (int i = 1; i <= 36; i++) {
								String st = query(phone, orderid, bizType_hf);
								Log
										.info("����ѳ�ֵ,������:" + orderid + ",����:"
												+ phone + ",,��" + i
												+ "�β�ѯ,,��ֵ���:" + st);
								if ("0".equals(st)) {
									return "0";
								}
								if ("-1".equals(st)) {
									return "-1";
								}
								try {
									Thread.sleep(5 * 1000);
								} catch (Exception e) {
								}
							}
							Log.info("����ѳ�ֵ,������:" + orderid + ",����:" + phone
									+ ",,��ֵ���,δ֪,return 2");
							return "2";
						} else {
							Log.info("����ѳ�ֵ,������:" + orderid + ",����:" + phone
									+ ",,�ύ�ɹ�,return 0");
							return "0";
						}
					}
					if ("FAILED".equals(result)) {
						Log.info("����ѳ�ֵ,������:" + orderid + ",����:" + phone
								+ ",,�ύʧ��,return -1");
						return "-1";
					}
				}
			}
		} catch (Exception e) {
			Log.info("����ѳ�ֵ,������:" + orderid + ",����:" + phone
					+ ",,ϵͳ�쳣,,return 2,,ex:" + e);
			return "2";
		} finally {
			post.releaseConnection();
			((SimpleHttpConnectionManager) client.getHttpConnectionManager())
					.shutdown();
			if (null != client) {
				client = null;
			}
		}
		Log.info("����ѳ�ֵ,������:" + orderid + ",����:" + phone
				+ ",,�ύ���,δ֪,return 2");
		return "2";
	}

	/**
	 * ����� ��ѯ
	 * 
	 * @param phone
	 * @param orderid
	 * @return 0�ɹ� -1ʧ�� 2������
	 */
	public static String query(String phone, String orderid, String serviceType) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("accountVal=" + phone);
		buffer.append("&bizType=" + serviceType);
		buffer.append("&sellerId="+sellerId);
		buffer.append("&tbOrderNo=" + orderid);
		buffer.append("&timeStmp=" + Tools.getNowDate());
		buffer.append("&sign="
				+ MD5.encode(buffer.toString() + "||" + password));
		String sendurl = query_url + buffer.toString();

		Log.info("�����ֵ��ѯ,������:" + orderid + ",����:" + phone + ",,����url:"
				+ sendurl);

		HttpClient client = null;
		PostMethod post = null;
		try {
			client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(
					60 * 1000);
			client.getHttpConnectionManager().getParams().setSoTimeout(
					20 * 1000);
			post = new PostMethod(sendurl);
			int status = client.executeMethod(post);
			Log.info("�����ֵ��ѯ,������:" + orderid + ",����:" + phone + ",,http state:"
					+ status);
			if (status == 200) {
				String result = inputStream2String(post.getResponseBodyAsStream(),"gbk");
				Log.info("�����ֵ��ѯ,������:" + orderid + ",����:" + phone
						+ ",,http��Ӧ����:" + result);
				if (result != null && !"".equals(result.trim())
						&& result.indexOf("<coopOrderStatus>") > 0
						&& result.indexOf("</coopOrderStatus>") > 0) {
					result = result.substring(result
							.indexOf("<coopOrderStatus>")
							+ "<coopOrderStatus>".length(), result
							.indexOf("</coopOrderStatus>"));
					if ("SUCCESS".equals(result)) {
						Log.info("�����ֵ��ѯ,������:" + orderid + ",����:" + phone
								+ ",,��ֵ�ɹ�,return 0");
						return "0";
					}
					if ("FAILED".equals(result)) {
						Log.info("�����ֵ��ѯ,������:" + orderid + ",����:" + phone
								+ ",,��ֵʧ��,return -1");
						return "-1";
					}
				}
			}
		} catch (Exception e) {
			Log.info("�����ֵ��ѯ,������:" + orderid + ",����:" + phone
					+ ",,ϵͳ�쳣,,return 2,,ex:" + e);
			return "2";
		} finally {
			post.releaseConnection();
			((SimpleHttpConnectionManager) client.getHttpConnectionManager())
					.shutdown();
			if (null != client) {
				client = null;
			}
		}
		Log.info("����ѳ�ֵ,������:" + orderid + ",����:" + phone
				+ ",,�ύ���,δ֪,return 2");
		return "2";
	}

	public static HashMap<String, String> map = new HashMap<String, String>();
	static {
		map.put("10", "100010CMFA");
		map.put("30", "100030CMFA");
		map.put("70", "100070CMFA");
		map.put("150", "100150CMFA");
		map.put("500", "100500CMFA");
		map.put("1024", "101024CMFA");
		map.put("2048", "102048CMFA");
		map.put("3072", "103072CMFA");
		map.put("4096", "104096CMFA");
		map.put("6144", "106144CMFA");
		map.put("11264", "111264CMFA");
	}

	/**
	 * �����ƶ�������ֵ
	 * 
	 * @param phone
	 * @param name
	 *            50==>>50mb
	 * @param orderid
	 * @return 0�ɹ� -1ʧ�� 2������
	 */
	public static String cz_flow(String phone, String name, String orderid) {
		name = name.replace("mb", "").replace("MB", "");
		StringBuffer buffer = new StringBuffer();
		buffer.append("accountVal=" + phone);
		buffer.append("&bizType=" + bizType_flow);
		buffer.append("&notifyUrl=" + backUrl);
		buffer.append("&productNo=" + map.get(name));
		buffer.append("&sellerId=" + sellerId);
		buffer.append("&tbOrderNo=" + orderid);
		buffer.append("&timeStmp=" + System.currentTimeMillis());
		buffer.append("&unitPrice=0");
		buffer.append("&sign="
				+ MD5.encode(buffer.toString() + "||" + password));
		String sendurl = cz_url + buffer.toString();

		Log.info("�����ƶ�������ֵ,������:" + orderid + ",����:" + phone + ",,����url:"
				+ sendurl);

		HttpClient client = null;
		PostMethod post = null;
		try {
			client = new HttpClient();
			post = new PostMethod(sendurl);
			int status = client.executeMethod(post);
			Log.info("�����ƶ�������ֵ,������:" + orderid + ",����:" + phone
					+ ",,http state:" + status);
			if (status == 200) {
				
				String result = inputStream2String(post.getResponseBodyAsStream(),"gbk");
				Log.info("�����ƶ�������ֵ,������:" + orderid + ",����:" + phone
						+ ",,http��Ӧ����:" + result);
				if (result != null && !"".equals(result.trim())
						&& result.indexOf("<coopOrderStatus>") > 0
						&& result.indexOf("</coopOrderStatus>") > 0) {
					result = result.substring(result
							.indexOf("<coopOrderStatus>")
							+ "<coopOrderStatus>".length(), result
							.indexOf("</coopOrderStatus>"));
					if ("SUCCESS".equals(result)) {
						Log.info("�����ƶ�������ֵ,������:" + orderid + ",����:" + phone
								+ ",,�ύ�ɹ�,return 0");
						return "0";
					}
					if ("FAILED".equals(result)) {
						Log.info("�����ƶ�������ֵ,������:" + orderid + ",����:" + phone
								+ ",,�ύʧ��,return -1");
						return "-1";
					}
				}
			}
		} catch (Exception e) {
			Log.info("�����ƶ�������ֵ,������:" + orderid + ",����:" + phone
					+ ",,ϵͳ�쳣,,return 2,,ex:" + e);
			return "2";
		} finally {
			post.releaseConnection();
			((SimpleHttpConnectionManager) client.getHttpConnectionManager())
					.shutdown();
			if (null != client) {
				client = null;
			}
		}
		Log.info("�����ƶ�������ֵ,������:" + orderid + ",����:" + phone
				+ ",,�ύ���,δ֪,return 2");
		return "2";
	}
	
    /**http ���� InputStream  ת string
     * @param in
     * @param encoding
     * @return string
     * @throws IOException
     */
    public static String inputStream2String (InputStream in , String encoding) throws IOException {
	    StringBuffer out = new StringBuffer();
	    InputStreamReader inread = new InputStreamReader(in,encoding);
	    char[] b = new char[4096];
	    for (int n; (n = inread.read(b)) != -1;) {
	        out.append(new String(b, 0, n));
	    }
	    return out.toString();
    }
}
