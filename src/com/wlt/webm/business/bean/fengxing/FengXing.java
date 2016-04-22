package com.wlt.webm.business.bean.fengxing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;

import com.commsoft.epay.util.logging.Log;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.wlt.webm.ten.service.MD5Util;

/**
 * 鼎信流量
 * 
 * @author 1989
 * 
 */
public class FengXing {
	static final String userid = "dx-szwh";
	static final String pwd = "7689B590A51C9B0CD9A570F758D0469C";
	static final String sign = "CC60E946DEDA0F09B7803DBD121F6FB9";
	static final String fillURL = "http://api.ejiaofei.net:11140/gprsChongzhiAdvance.do?";
	static final String queryURL = "http://api.ejiaofei.net:11140/query_jkorders.do?";

	static MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
	static HttpClient client = new HttpClient(manager);
	static {
		client.getHttpConnectionManager().getParams().setConnectionTimeout(
				1000 * 60);
		client.getHttpConnectionManager().getParams().setSoTimeout(1000 * 60);
	}

	/**
	 * 山东鼎新充值流量
	 * 
	 * @param orderid
	 *            订单号
	 * @param account
	 *            手机号
	 * @param gprs
	 *            面值
	 * @param area
	 *            0 全国流量，1 省内流量
	 * @param effecttime
	 *            0 即时生效，1次日生效，2 次月生效
	 * @param validity
	 *            传入月数，0为当月有效
	 * @param times
	 *            yyyyMMddhhmmss
	 * @return
	 */
	public static int fillFlow(String orderid, String account, String gprs,
			String area, String effecttime, String validity, String times) {
		int n = 2;
		// （userid+“用户编号”+ pwd+“由鼎信商务提供”+ orderid+“用户提交的订单号”+
		// account+“手机号码”+gprs+”流量值”+
		// area+”流量范围”+effecttime+”生效日期”+validity+”流量有效期”+times+”时间戳”+”加密密钥”）
		String userkey = MD5Util.MD5Encode("userid" + userid + "pwd" + pwd
				+ "orderid" + orderid + "account" + account + "gprs" + gprs
				+ "area" + area + "effecttime" + effecttime + "validity"
				+ validity + "times" + times + sign, "UTF-8");
		String url = fillURL + "userid=" + userid + "&pwd=" + pwd + "&orderid="
				+ orderid + "&account=" + account + "&gprs=" + gprs + "&area="
				+ area + "&effecttime=" + effecttime + "&validity=" + validity
				+ "&times=" + times + "&userkey=" + userkey;
		String str = OP(url, orderid, "FILL");
		if (null != str) {
			XStream xx = new XStream(new DomDriver());
			xx.alias("response", FillResponse.class);
			FillResponse rs = null;
			rs = (FillResponse) xx.fromXML(str);
			System.out.println(rs.toString());
			if("2".equals(rs.getState())){
				n=-1;
			}else if("1".equals(rs.getState())||"3".equals(rs.getState())){
				n=0;
			}
		}
		return n;
	}

	/**
	 * 鼎信查询
	 * 
	 * @param orderid
	 * @return 0 成功 -1 失败 2 处理中
	 */
	public static int queryFlow(String orderid) {
		int n = 2;
		// （userid+“用户编号”+ pwd+“由鼎信商务提供”+ orderid+“用户提交的订单号” +“加密密钥”）
		String userkey = MD5Util.MD5Encode("userid" + userid + "pwd" + pwd
				+ "orderid" + orderid + sign, "UTF-8");
		String url = queryURL + "&userid=" + userid + "&pwd=" + pwd
				+ "&orderid=" + orderid + "&userkey=" + userkey;
		System.out.println("查询地址:" + url);
		String str = OP(url, orderid, "QUERY");
		if (null != str) {
			XStream xx = new XStream(new DomDriver());
			xx.alias("response", QueryResponse.class);
			QueryResponse rs = null;
			rs = (QueryResponse) xx.fromXML(str);
			System.out.println(rs.toString());
			if("2".equals(rs.getState())){
				n=-1;
			}else if("1".equals(rs.getState())||"3".equals(rs.getState())){
				n=0;
			}
		}
		return n;
	}

	/**
	 * 操作
	 * 
	 * @param url
	 * @param id
	 * @param ops
	 * @return
	 */
	public static String OP(String url, String id, String ops) {
		PostMethod postMt = null;
		try {
			postMt = new PostMethod(url);
			int status = client.executeMethod(postMt);
			Log.info(id + " 操作" + ops + "请求应答码:" + status);
			if (200 == status) {
				String result = convertStreamToString(postMt
						.getResponseBodyAsStream());
				return result;
			}
		} catch (Exception e) {
			Log.error(id + " 操作" + ops + " 系统异常" + e.toString());
		} finally {
			postMt.releaseConnection();
		}
		return null;
	}

	public static String convertStreamToString(InputStream is)
			throws IOException {
		StringBuilder sf = new StringBuilder();
		String line;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while ((line = reader.readLine()) != null) {
				sf.append(line);
			}
		} finally {
			is.close();
			if (null != reader) {
				reader.close();
			}
		}
		return sf.toString();
	}

	public static void main(String[] args) {
		System.out.println(fillFlow("123456", "15626509737", "20", "0", "0", "0", "20160129121212"));
//		System.out.println(queryFlow("WH12345678"));
	}
}
