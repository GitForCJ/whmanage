package com.wlt.webm.business.bean.baibang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

/**
 * 基础方法
 * 
 * @author 1989
 * 
 */
public class BaiBean {
	/**
	 * 测试url
	 */
	public final static String QUERY = "http://www.gdwz.net/open_cx/ser_wzcx.aspx?";//查询
	public final static String PREQUERY = "http://www.gdwz.net/open_cx/ser_wzdb.aspx?";//预查询
	public final static String SUBMIT = "http://www.gdwz.net/open_cx/ser_wzdb2.aspx?";//下单

	/**
	 * 像服务端发送xml数据
	 * 
	 * @param url
	 * @param data
	 *            数据 数据类型
	 * @return
	 * @throws Exception
	 */
	public  InputStream sendMsgRequest(String url, String data)
			throws Exception {
		System.out.println("百事帮发送数据:"+data);
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		InputStream result = null;
		int status;
		byte b[] = data.getBytes("UTF-8");// 把字符串转换为二进制数据
		RequestEntity requestEntity = new ByteArrayRequestEntity(b);
		method.setRequestEntity(requestEntity);
		method.setRequestHeader("Content-Type",
				"application/xml;charset=UTF-8");
		try {
			status = client.executeMethod(method);
			if (status == 200) {
				result = method.getResponseBodyAsStream();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			method.releaseConnection();
			((SimpleHttpConnectionManager) client.getHttpConnectionManager())
					.shutdown();
			if (null != client) {
				client = null;
			}
		}
		return result;
	}
	
	/**
	 * 像服务端发送请求
	 * 
	 * @param url
	 * @return  String
	 * @throws Exception
	 */
	public  String  sendMsgRequest(String url)
			throws Exception {
		System.out.println("百事帮发送请求:"+url);
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		String result = null;
		int status;
		try {
			status = client.executeMethod(method);
			if (status == 200) {
				result = convertStreamToString(method.getResponseBodyAsStream());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			method.releaseConnection();
			((SimpleHttpConnectionManager) client.getHttpConnectionManager())
					.shutdown();
			if (null != client) {
				client = null;
			}
		}
		return result;
	}

	/**
	 * 输入流转成字符串
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public  String convertStreamToString(InputStream is)
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

}
