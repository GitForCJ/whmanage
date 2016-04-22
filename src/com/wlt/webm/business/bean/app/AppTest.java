package com.wlt.webm.business.bean.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;

public class AppTest {
	
    static MultiThreadedHttpConnectionManager connectionManager =
        new MultiThreadedHttpConnectionManager();
    static HttpClient client=new HttpClient(connectionManager);
	
	public static String OP(String url,String data){
		PostMethod postMt=new PostMethod(url);
		byte[] bytedata=data.getBytes();
		RequestEntity en=new ByteArrayRequestEntity(bytedata);
		postMt.setRequestEntity(en);
		postMt.setRequestHeader("Content-Type",
				"text/plain;charset=UTF-8");
		try {
			int status=client.executeMethod(postMt);
			if(200==status){
				String result=convertStreamToString(postMt.getResponseBodyAsStream());
			    if(null!=result){
			    Log.info("测试返回:"+result);
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
			reader = new BufferedReader(new InputStreamReader(is));
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
		//登陆
//		String dl="{\"account\":\"18682033916\",\"password\":\"12345\"}";
//		String dlurl="http://192.168.1.117:9009/wh/app.do?method=AppLogin";
//		System.out.println(OP(dlurl,dl));
		
		//注册
//		String zc="{\"account\":\"18682033916\",\"password\":\"12345\",\"url\":\"http://192.168.1.117:9009/wh/business/QR.jsp?id=1\"}";
//		String zcurl="http://192.168.1.117:9009/wh/app.do?method=Registered";
//		System.out.println(OP(zcurl,zc));
//		
//		//编辑请求
//		String bj="{\"account\":\"18682033916\"}";
//		String bjurl="http://192.168.1.117:9009/wh/app.do?method=AppEdit";
//		System.out.println(OP(bjurl,bj));
//		//编辑提交
		String bjtjurl="http://192.168.1.117:9009/wh/app.do?method=AppEditSubmit";
		HashMap<String, String> bjtj=new HashMap<String, String>();
		bjtj.put("account", "18682033916");
		bjtj.put("name", "蔡守军");
		bjtj.put("company", "万恒");
		bjtj.put("position", "开发");
		bjtj.put("qq", "31256");
		bjtj.put("address", "龙华");
		bjtj.put("email", "163@qq.com");
		bjtj.put("note", "哈哈");
		bjtj.put("phone", "18682033916");
		bjtj.put("telphone", "123456");
		bjtj.put("fax", "123456");
		bjtj.put("website", "www.wanhuipay.com");
		bjtj.put("msn", "11");
		System.out.println(OP(bjtjurl,JSON.toJSONString(bjtj)));
	}
}
