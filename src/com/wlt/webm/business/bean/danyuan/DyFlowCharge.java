package com.wlt.webm.business.bean.danyuan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.ten.service.MD5Util;

/**
 * @author 1989
 */
public class DyFlowCharge {
    public static String action="charge";
    public static String v="1.1";
    public static String range="0";
    public static String account="wenheng2";
    public static String key="f9065b9dba22416ea28704222868d176";
    public static String url="http://120.25.130.197:8080/api.aspx?";
	static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	static HttpClient client = new HttpClient(connectionManager);
	static {
		client.getHttpConnectionManager().getParams().setConnectionTimeout(
				60 * 1000);// 链接时间 60秒，单位是毫秒
		client.getHttpConnectionManager().getParams().setSoTimeout(20 * 1000);//读取时间 ,单位是毫秒
	}

	
	public static String OP(String url, HashMap data, String method) {
		String rs="{\"desc\":\"单元服务繁忙\",\"status\":\"8\"}";
		Set<String> set = data.keySet();
		for (String key : set) {
			url += (key + "=" + data.get(key) + "&");
		}
		url = url.substring(0, url.length() - 1);
		Log.info("单元科技请求url:" + url);
		HttpMethod obj = null;
			obj = new PostMethod(url);
		try {
			int status = client.executeMethod(obj);
			if (200 == status) {
				String result = convertStreamToString(obj
						.getResponseBodyAsStream());
				if (null == result) {
					Log.info("请求联通号码:" + data.get("mobile") + "响应内容为空");
				} else {
					rs=result;
				}
			}else{
				Log.info("请求联通号码:" + data.get("mobile") + "响应不正常status:"+status);
			}
		} catch (Exception e) {
			Log.error("请求联通号码:" + data.get("mobile") + ",,,系统异常"
					+ e.toString());
		} finally {
			obj.releaseConnection();
		}
		return rs;
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
	
	public static int dyFillFlow(String package2,String mobile){
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("Action", action);
		map.put("V", v);
		map.put("Range", range);
		map.put("Account", account);
		map.put("Mobile", mobile);
		map.put("PACKAGE", package2);
		String signsource="account="+account+"&mobile="+mobile+
	    "&package="+package2+"&key="+key;
		System.out.println("signsource:"+signsource);
		String sign=MD5Util.MD5Encode(signsource, "UTF-8");
		System.out.println("sign:"+sign);
		map.put("Sign", sign);
        String rs=OP(url, map, "POST");
        System.out.println(rs);
        return 0;
	}
	
	public static void main(String[] args) {
		dyFillFlow("10","18682033916");
	}

}
