package com.wlt.webm.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.commsoft.epay.util.logging.Log;


/**
 * 北京烽火科技短信通道 （新）
 * 
 * @author 1989
 *
 */
public class FenghuoNew {

	/**
	 * 发送短信
	 * 
	 * @param phone
	 *            手机号码
	 * @param content
	 *            内容
	 * @return true 发送成功 false发送失败
	 */
	public static boolean sendMsg(String phone, String content) {
		String str = null;
		CloseableHttpClient client = HttpClients.createDefault();  
		CloseableHttpResponse res=null;
		HttpPost post = new HttpPost("http://223.6.255.39:7895/SendMT/SendMessage");
		JSONObject bjson = new JSONObject();
		JSONObject json = new JSONObject();
		try {
			json.put("content", URLEncoder.encode(content, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return false;
		}
		json.put("mobiles", phone);
		JSONArray b = new JSONArray();
		b.add(json);
		JSONObject json2 = new JSONObject();
		json2.put("clientID", "wanheng");
		json2.put("share_secret", "W0sTdo");
		json2.put("messageList", b);
		bjson.put("param", json2);
		try {
			StringEntity s = new StringEntity("param=" + json2.toString());
			post.setEntity(s);
			 res = client.execute(post);
			HttpEntity entity = res.getEntity();
			int statusCode = res.getStatusLine().getStatusCode();  
			if (statusCode == HttpStatus.SC_OK) 
			if (entity != null) {
				InputStream instreams = entity.getContent();
				str = convertStreamToString(instreams);				
				str = new String(str.getBytes(), "utf-8");
				JSONObject obj=JSONObject.fromObject(str);
				if(obj.getInt("code")==0)
		        	return true;				
			}
		}catch (Exception e) {
			Log.error(phone+"发送信息"+content+"异常:"+e.toString());
			return false;
		}finally{
			try {
				res.close();
			    post.abort();			
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
				Log.error("关闭发送信息连接异常"+e.toString());
			}
		}
		return false;
	}

	/**
	 * 从inputstream中获取字符串
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
