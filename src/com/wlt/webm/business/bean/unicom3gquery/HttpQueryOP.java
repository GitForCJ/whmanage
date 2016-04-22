package com.wlt.webm.business.bean.unicom3gquery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import com.commsoft.epay.util.logging.Log;

/**
 * 联通流量查询
 * @author 1989
 *
 */
public class HttpQueryOP {
	
	public static String APPIDSIGN="%flow1_%";
	public static String PHONESIGN="aiwmMobile";
	public static String APPID="flow1";
	public static String QUERYURL="http://smtp.wo.cn:2090/aiwmServPortal/rest/client/queryFlow";
	//public static String QUERYURL="http://114.247.0.106:8451/aiwmServPortal/rest/client/queryFlow";
    static MultiThreadedHttpConnectionManager connectionManager =
        new MultiThreadedHttpConnectionManager();
    static HttpClient client=new HttpClient(connectionManager);
	/**
	 * 
	 * @param url
	 * @param data
	 * @return 查询请求处理方法 
	 */
	public static String OP(String url,String data){
		PostMethod postMt=new PostMethod(url);
		byte[] bytedata=data.getBytes();
		RequestEntity en=new ByteArrayRequestEntity(bytedata);
		postMt.setRequestEntity(en);
		postMt.setRequestHeader("Content-Type",
				"text/plain;charset=UTF-8");
//		client.getHttpConnectionManager().getParams().setConnectionTimeout(20*1000);
//		client.getHttpConnectionManager().getParams().setSoTimeout(60*1000);
		try {
			int status=client.executeMethod(postMt);
			if(200==status){
				String result=convertStreamToString(postMt.getResponseBodyAsStream());
			    if(null!=result){
			    Log.info("联通返回:"+result);
                return result;
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("联通流量查询:"+e.toString());
		}
		return null;
	}
	
	/**
	 * 输入流转成字符串
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
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

}

