package com.wlt.webm.business.bean.oufeiqb;

import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.MD5;

public class OuFeiHandle {
	
	public static final String QUERY_URL="http://api2.ofpay.com/supply.do";//查询订单
	public static final String CONFIRMATION_URL="http://api2.ofpay.com/confirmRecharge.do";//确认订单接口
	public static final String REVERSE_URL="http://api2.ofpay.com/setOrders.do";//返回充值结果接口
	
	public static final String FORMAT="json";
	public static final String VERSION="2.4";
	
	/**
	 * 订单获取接口
	 * @param str 
	 * @return GetOrderRespone 
	 */
	public static GetOrderRespone QueryOrders(String[] str){
		StringBuffer buf=new StringBuffer();
		buf.append(QUERY_URL);
		buf.append("?");
		buf.append("partner="+str[5]);
		buf.append("&tplid="+str[6]);
		buf.append("&sign="+MD5.encode(str[5]+str[6]+str[7]).toUpperCase());
		buf.append("&reqid="+getNumber());
		buf.append("&format="+FORMAT);
		
		Log.info("Q币供货，组装查询订单请求,,url:"+buf.toString());
		String Jsonstr=SendMessageRequest(buf.toString());
		if(Jsonstr==null || "".equals(Jsonstr)){
			Log.info("Q币供货，组装查询订单请求,SendMessageRequest 方法返回值:"+Jsonstr+",,url:"+buf.toString());
			return null;
		}
		try{
			GetOrderRespone QueryOrders=(GetOrderRespone)JSON.parseObject(Jsonstr,GetOrderRespone.class);
			return QueryOrders;
		}catch(Exception ex){
			Log.error("Q币供货，组装查询订单请求,,url:"+buf.toString()+",,json to bean Exception "+ex);
		}
		return null;
	}
	
	
	/**
	 * 订单充值前确认接口
	 * @param orderid 订单编号
	 * @param id 充值单号（捞单时返回的订单信息中id节点的值）
	 * @return GetOrderRespone
	 */
	public static GetOrderRespone Confirmation(String orderid,String id){
		StringBuffer buf=new StringBuffer();
		buf.append(CONFIRMATION_URL);
		buf.append("?");
		buf.append("&orderid="+orderid);
		buf.append("&id="+id);
		buf.append("&format="+FORMAT);
		
		Log.info("Q币供货，组装订单确认请求,,url:"+buf.toString());
		
		String Jsonstr=SendMessageRequest(buf.toString());
		
		if(Jsonstr==null || "".equals(Jsonstr)){
			Log.info("Q币供货，组装订单确认请求,SendMessageRequest 方法返回值:"+Jsonstr+",,url:"+buf.toString());
			return null;
		}
		try{
			GetOrderRespone QueryOrders=(GetOrderRespone)JSON.parseObject(Jsonstr,GetOrderRespone.class);
			return QueryOrders;
		}catch(Exception ex){
			Log.error("Q币供货，组装订单确认请求,,url:"+buf.toString()+",,json to bean Exception "+ex);
		}
		return null;
	}
	
	/**
	 * 返回充值结果接口
	 * @param orderid 订单编号
	 * @param id 充值单号（捞单时返回的订单信息中id节点的值）
	 * @param orderState 订单状态（4：充值成功 5：冲值失败 6：可疑订单）
	 * @param str 
	 * @return GetOrderRespone
	 */
	public static GetOrderRespone ReverseStr(String orderid,String id,String orderState,String[] str){
		StringBuffer buf=new StringBuffer();
		buf.append(REVERSE_URL);
		buf.append("?");
		buf.append("orderid="+orderid);
		buf.append("&id="+id);
		buf.append("&sign="+MD5.encode(str[5]+id+orderid+orderState+str[7]).toUpperCase());
		buf.append("&orderstate="+orderState);
		buf.append("&partner="+str[5]);
		buf.append("&tplid="+str[6]);
		buf.append("&version="+VERSION);
		buf.append("&format="+FORMAT);
		
		Log.info("Q币供货，组装订单充值结果请求,,url:"+buf.toString());
		
		String Jsonstr=SendMessageRequest(buf.toString());
		if(Jsonstr==null || "".equals(Jsonstr)){
			Log.info("Q币供货，组装订单充值结果请求,SendMessageRequest 方法返回值:"+Jsonstr+",,url:"+buf.toString());
			return null;
		}
		try{
			GetOrderRespone QueryOrders=(GetOrderRespone)JSON.parseObject(Jsonstr,GetOrderRespone.class);
			return QueryOrders;
		}catch(Exception ex){
			Log.error("Q币供货，组装订单充值结果请求,,url:"+buf.toString()+",,json to bean Exception "+ex);
		}
		return null;
	}
	
	/**
	 * 请求编号 
	 * @return String 
	 */
	public static String getNumber(){
		return Tools.getNow()+((int)(Math.random()*10000)+10000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*10000)+10000);
	}
	
	
	/**
	 * 发送请求
	 * @param url
	 * @return String 
	 */
	public static String SendMessageRequest(String url){
		Log.info("Q币供货,sendMessage info :"+url);
		HttpClient client=null;
		GetMethod post =null;
		try{
			client= new HttpClient();
			post=new GetMethod(url);
			int status = client.executeMethod(post);
			Log.info("Q币供货,sendMessage info ,响应status="+status+",,请求url:"+url);
			if(status==200){
				String result = post.getResponseBodyAsString().trim();
				result=new String(result.getBytes("ISO-8859-1"),"utf-8");
				Log.info("Q币供货,SendMessageRequest result Message :"+result+",,请求url:"+url);
				return result;
			}
		}catch (Exception e) {
			Log.error("Q币供货,sendMessage info :"+url+",Exception="+e);
			return null;
		}finally{
			post.releaseConnection();
			((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown(); 
			if(null!=client){
				client=null;
			}
		}
		return null;
	}
	
	
	public static void main(String[] arg){
		System.out.println(getNumber());
	}
}
