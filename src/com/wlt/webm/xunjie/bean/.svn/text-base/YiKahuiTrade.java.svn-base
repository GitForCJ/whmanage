package com.wlt.webm.xunjie.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.ten.service.MD5Util;

/**
 * 一卡惠接口交易处理
 * 
 * @author caiSJ
 * 
 */
public class YiKahuiTrade {
	private static String USERNO = "2013112700000067";
	private static String KEY = "4520B55D5A9D09E43DA50ED8F0768BFB";
	private static String FILL_URL = "http://api.ekhui.com/goodsTrans?";
	private static String QUERY_URL = "http://api.ekhui.com/orderQuery?";
//	private static String RETURN_BAK_URL = "http://localhost:80/wh/business/bank.do?method=xzReturn";
	private static String RETURN_BAK_URL="http://www.wanhuipay.com/business/bank.do?method=xzReturn";

	public Map<String, String> YikahuiFill(List lists) {
		Map<String, String> result = new HashMap<String, String>();
		StringBuilder sf = new StringBuilder();
		int k = lists.size();
		for (int i = 0; i < k;) {
			sf.append("&" + lists.get(i) + "=" + lists.get(i + 1));
			i += 2;
		}
		String orderID = (String) lists.get(5);

		String url = FILL_URL + sf.toString();
		Log.info(url);
		String rs = postXml(url);
		if (rs == null) {
			return YikahuiQuery(orderID); // 查询
		} else {
			String failedCode = rs.substring(rs.indexOf("<failedCode>")
					+ "<failedCode>".length(), rs.indexOf("</failedCode>"));
			Log.info("订单号:" + orderID + "  响应码:" + failedCode);
			String status = null;
			if (rs.indexOf("<status>") != -1) {
				status = rs.substring(rs.indexOf("<status>")
						+ "<status>".length(), rs.indexOf("</status>"));
			} else {
				result.put("code", "-1");// 充值失败
				result.put("supOrderNo", " ");
				return result;
			}
			if ("ORDER_FAILED".equals(status)) {
				String supOrderNo = rs.substring(rs.indexOf("<supOrderNo>")
						+ "<supOrderNo>".length(), rs.indexOf("</supOrderNo>"));
				result.put("code", "-1");// 充值失败
				result.put("supOrderNo", supOrderNo);
				return result;
			} else {
				return YikahuiQuery(orderID);
			}
		}
	}

	public Map<String, String> YikahuiQuery(String orderID) {
		Map<String, String> result = new HashMap<String, String>();
		String url = QUERY_URL + "&userNo=" + USERNO + "&ptOrderNo=" + orderID
				+ "&sign=" + MD5Util.MD5Encode(USERNO + orderID + KEY, "UTF-8");
		Log.info("查询："+url);
		String rs = null;
		String supOrderNo = null;
		int count = 0;
		while (true) {
			if (count > 20) {
				result.put("code", "-2");// 超时
				result.put("supOrderNo", supOrderNo);
				break;
			}
			count++;
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			rs = postXml(url);
			if (null == rs) {
				continue;
			} else {
				String failedCode = rs.substring(rs.indexOf("<failedCode>")
						+ "<failedCode>".length(), rs.indexOf("</failedCode>"));
				Log.info("failedCode:" + failedCode);
				if (rs.indexOf("<supOrderNo>") != -1) {
					supOrderNo = rs.substring(rs.indexOf("<supOrderNo>")
							+ "<supOrderNo>".length(), rs
							.indexOf("</supOrderNo>"));

				}
				String status = null;
				if (rs.indexOf("<status>") != -1) {
					status = rs.substring(rs.indexOf("<status>")
							+ "<status>".length(), rs.indexOf("</status>"));
				} else {
					if(count<=6){
						continue;
					}
					result.put("code", "-1");// 充值失败
					result.put("supOrderNo", supOrderNo);
					return result;
				}
				if ("ORDER_SUCCESS".equals(status) || "0000".equals(failedCode)) {
					result.put("code", "0");
					result.put("supOrderNo", supOrderNo);
					break;
				} else if ("ORDER_FAILED".equals(status)
						|| "501".equals(failedCode)
						|| "9999".equals(failedCode)) {
					if(count<=6){
						continue;
					}
					result.put("code", "-1");
					result.put("supOrderNo", supOrderNo);
					break;
				} else {
					continue;
				}
			}
		}
		Log.info("code：" + result.get("code") + "   supOrderNo："
				+ result.get("supOrderNo"));
		return result;
	}
	
	
	public int YikahuiQuery1(String orderID) {
		String url = QUERY_URL + "&userNo=" + USERNO + "&ptOrderNo=" + orderID
				+ "&sign=" + MD5Util.MD5Encode(USERNO + orderID + KEY, "UTF-8");
		Log.info("一卡惠手动查询："+url);
		String rs = null;
			rs = postXml(url);
			if (null == rs) {
				return -1;
			} else {
				String failedCode = rs.substring(rs.indexOf("<failedCode>")
						+ "<failedCode>".length(), rs.indexOf("</failedCode>"));
				Log.info("failedCode:" + failedCode);
				String status = null;
				if (rs.indexOf("<status>") != -1) {
					status = rs.substring(rs.indexOf("<status>")
							+ "<status>".length(), rs.indexOf("</status>"));
				} else {
					return 1;
				}
				if ("ORDER_SUCCESS".equals(status) || "0000".equals(failedCode)) {
					return 0;
				} else if ("ORDER_FAILED".equals(status)
						|| "501".equals(failedCode)
						|| "9999".equals(failedCode)) {
				return 1;
				} else {
					return -1;
				}
			}
		}
	
	

	public String convertStreamToString(InputStream is) throws IOException {
		StringBuilder sf = new StringBuilder();
		String line;
		BufferedReader reader =null;
		try {
			 reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"));
			while ((line = reader.readLine()) != null) {
				sf.append(line);
			}
		} finally {
			is.close();
			 if(null!=reader){
				 reader.close();
			 }
		}
		return sf.toString();
	}

	/**
	 * 获得http请求结果
	 * 
	 * @param url
	 * @return null失败或者异常
	 */
	public String postXml(String url) {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(url);
		String result = null;
		int status;
		try {
			status = client.executeMethod(post);
			if (status == 200) {
				result = convertStreamToString(post.getResponseBodyAsStream());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		finally
		{
			 post.releaseConnection();
			((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown(); 
			if(null!=client)
			{
				client=null;
			}
		}
		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		YiKahuiTrade yi = new YiKahuiTrade();
		// List lists=new ArrayList<String>();
		// lists.add("one");
		// lists.add("000");
		// lists.add("two");
		// lists.add("1111");
		// lists.add("sign");
		// lists.add("2334");
		// yi.YikahuiFill(lists);
		// yi.YikahuiQuery("13121709322500000000017");
String str="20131127000000671312271759071662572525584520B55D5A9D09E43DA50ED8F0768BFB";
   System.out.println(MD5Util.MD5Encode(str,"utf-8"));
	}

}
