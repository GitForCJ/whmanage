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
 * ��������
 * 
 * @author 1989
 * 
 */
public class BaiBean {
	/**
	 * ����url
	 */
	public final static String QUERY = "http://www.gdwz.net/open_cx/ser_wzcx.aspx?";//��ѯ
	public final static String PREQUERY = "http://www.gdwz.net/open_cx/ser_wzdb.aspx?";//Ԥ��ѯ
	public final static String SUBMIT = "http://www.gdwz.net/open_cx/ser_wzdb2.aspx?";//�µ�

	/**
	 * �����˷���xml����
	 * 
	 * @param url
	 * @param data
	 *            ���� ��������
	 * @return
	 * @throws Exception
	 */
	public  InputStream sendMsgRequest(String url, String data)
			throws Exception {
		System.out.println("���°﷢������:"+data);
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		InputStream result = null;
		int status;
		byte b[] = data.getBytes("UTF-8");// ���ַ���ת��Ϊ����������
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
	 * �����˷�������
	 * 
	 * @param url
	 * @return  String
	 * @throws Exception
	 */
	public  String  sendMsgRequest(String url)
			throws Exception {
		System.out.println("���°﷢������:"+url);
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
	 * ������ת���ַ���
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
