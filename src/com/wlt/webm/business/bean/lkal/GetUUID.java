package com.wlt.webm.business.bean.lkal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import org.apache.activemq.leveldb.DBManager;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import com.alibaba.fastjson.JSON;

/**
 * 初次获得UUID
 * @author 1989
 *
 */
public class GetUUID {
	
	public static String GET_UUID_URL="http://mp.lakala.com.cn:8660/msb/services/subscribeService";
		//"http://test1.lakala.com.cn:8660/msb/services/subscribeService";
	public static String APPID="d7e805720efe42be9137f936f5ad941f";
	//"2bf1af4ce9ad49b289ca69aa0c3af4c8";
	public static String SHOP_NO="822291254112658";
		//"310000000055310";//"310000000062501";
	public static String REDIRECTURL="http://www.wanhuipay.com/business/bank.do?method=mlkal";
		//"http://shijianqiao321a.xicp.net:8888/wh/business/bank.do?method=mlkal";
	
	/**
	 * 像服务端发送数据
	 * 
	 * @param url
	 * @param data
	 *            数据
	 *            数据类型
	 * @return
	 * @throws Exception
	 */
	public static String sendMsgRequest(String url, String data)
			throws Exception {
		HttpClient client = new HttpClient();
		PostMethod method=new PostMethod(url);
		String result = null;
		int status;
		// method.setRequestBody(data);
		byte b[] = data.getBytes("UTF-8");// 把字符串转换为二进制数据
		RequestEntity requestEntity = new ByteArrayRequestEntity(b);
		method.setRequestEntity(requestEntity);
		method.setRequestHeader("Content-Type", "text/json;charset=UTF-8");
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
				System.out.println("获取uuid:"+result);
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
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		UUIDRquestJson uuidJson=new UUIDRquestJson();
		uuidJson.setAppid(APPID);
		uuidJson.setShopNo(SHOP_NO);
		uuidJson.setRedirectURL(REDIRECTURL);
		System.out.println(JSON.toJSONString(uuidJson));
		String str=sendMsgRequest(GET_UUID_URL,JSON.toJSONString(uuidJson));
		UUIDResponseJson rs=JSON.parseObject(str,UUIDResponseJson.class);
		ArrayList<Key> keys=rs.getProperties().getKeys();
	    if(null!=keys&&keys.size()==20){
	    	Class.forName("com.mysql.jdbc.Driver");
	    	Connection conn=DriverManager.getConnection("jdbc:mysql://192.168.1.190:3306/wh?useUnicode=true&characterEncoding=UTF-8&useCursorFetch=true&defaultFetchSize=1000", 
	    			"wh","wh2013");
	    	conn.setAutoCommit(false);
	    	PreparedStatement pre=conn.prepareStatement("insert into wht_lakal_keys values(?,?)");
		for(Key key:keys){
			System.out.println("index:"+key.getIndex()+"   key:"+key.getKey());
			pre.setInt(1, key.getIndex());
			pre.setString(2, key.getKey());
			pre.addBatch();
		}
		pre.executeBatch();
		conn.commit();
		pre.close();
		conn.close();
	    }else{
	    	System.err.println("已经订阅");
	    }
	}

}
