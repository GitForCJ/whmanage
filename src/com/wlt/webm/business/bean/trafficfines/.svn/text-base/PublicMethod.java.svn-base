package com.wlt.webm.business.bean.trafficfines;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.fastjson.JSON;

/**
 * 基础方法
 * 
 * @author 1989
 * 
 */
public class PublicMethod {
	/**
	 * 测试url
 
	public final static String GET_TOKEN = "http://59.41.186.76:13278/api/sys/login";
	public final static String QUERY_BREAKRULES = "http://59.41.186.76:13278/api/PeccancyInfo/query";
	public final static String NEWQUERY_BREAKRULES = "http://59.41.186.76:13278/api/NPeccancyInfo/queryByGX";
	public final static String GET_PECCANCYINFOBYID = "http://59.41.186.76:13278/api/PeccancyInfo/getPeccancyInfoById";
	public final static String SUBMIT_BREAKRULES = "http://59.41.186.76:13278/api/PeccancyInfo/saveOrder";
	public final static String GET_INFO = "http://59.41.186.76:13278/api/PeccancyInfo/queryOrder";
	public final static String PASSWORD="api_wanheng";
	*/
	/**
	 * 正式地址
	*/
	public final static String GET_TOKEN = "http://c.gd118114.cn/api/sys/login";
	public final static String QUERY_BREAKRULES = "http://c.gd118114.cn/api/PeccancyInfo/query";
	public final static String NEWQUERY_BREAKRULES = "http://c.gd118114.cn/api/NPeccancyInfo/queryByGX";
	public final static String GET_PECCANCYINFOBYID = "http://c.gd118114.cn/api/PeccancyInfo/getPeccancyInfoById";
	public final static String SUBMIT_BREAKRULES = "http://c.gd118114.cn/api/PeccancyInfo/saveOrder";
	public final static String GET_INFO = "http://c.gd118114.cn/api/PeccancyInfo/queryOrder";
    public final static String PASSWORD="wanhengtech_755";
	public static Cookie[] cookies = null;
	
	public static String TOKEN_ID=getTokenID();

	/**
	 * 
	 * @return
	 */
	public static String getTokenID(){
		if(null==TOKEN_ID){
			TokenRequest tokenreq=new TokenRequest();
			tokenreq.setAccount("api_wanheng");
			tokenreq.setPassword(PASSWORD);
	        String res;
			try {
				res = PublicMethod.sendMsgRequest(GET_TOKEN, JSON.toJSONString(tokenreq));
		        TokenResponse  resp=JSON.parseObject(res, TokenResponse.class);
		        TOKEN_ID= resp.getTokenId();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return TOKEN_ID;
	}
	/**
	 * 像服务端发送数据
	 * 
	 * @param url
	 * @param data
	 *            数据 数据类型
	 * @return
	 * @throws Exception
	 */
	public static String sendMsgRequest(String url, String data)
			throws Exception {
		System.out.println("jf发送数据:"+data);
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				"UTF-8");
		client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		if(null!=cookies){
			client.getState().addCookies(cookies);	
		}
		PostMethod method = new PostMethod(url);
		String result = null;
		int status;
		// method.setRequestBody(data);
		byte b[] = data.getBytes("UTF-8");// 把字符串转换为二进制数据
		RequestEntity requestEntity = new ByteArrayRequestEntity(b);
		method.setRequestEntity(requestEntity);
		method.setRequestHeader("Content-Type",
				"application/json;charset=UTF-8");
		// 设置连接超时
		// client.getHttpConnectionManager().getParams().setConnectionTimeout(5
		// * 1000);
		// 设置读取超时
		// client.getHttpConnectionManager().getParams().setSoTimeout(20 *
		// 1000);
		try {
			status = client.executeMethod(method);
			if (status == 200) {
				result = convertStreamToString(method.getResponseBodyAsStream());
				if(null==cookies){
					cookies=client.getState().getCookies();
				}
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
		System.out.println("服务端返回数据:" + result);
		return result;
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

	/**
	 * 违章调用方法
	 * 
	 * @param response
	 *            返回的对象类型
	 * @param requestObj
	 *            请求的对象
	 * @param url
	 *            请求的url地址
	 * @return 违章服务端返回的对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Object breakrulesMethod(Class response, Object requestObj,
			String url) throws Exception {
		return JSON.parseObject(sendMsgRequest(url, JSON
				.toJSONString(requestObj)), response);
	}
}
