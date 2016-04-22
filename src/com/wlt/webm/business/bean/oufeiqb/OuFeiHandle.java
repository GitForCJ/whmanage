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
	
	public static final String QUERY_URL="http://api2.ofpay.com/supply.do";//��ѯ����
	public static final String CONFIRMATION_URL="http://api2.ofpay.com/confirmRecharge.do";//ȷ�϶����ӿ�
	public static final String REVERSE_URL="http://api2.ofpay.com/setOrders.do";//���س�ֵ����ӿ�
	
	public static final String FORMAT="json";
	public static final String VERSION="2.4";
	
	/**
	 * ������ȡ�ӿ�
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
		
		Log.info("Q�ҹ�������װ��ѯ��������,,url:"+buf.toString());
		String Jsonstr=SendMessageRequest(buf.toString());
		if(Jsonstr==null || "".equals(Jsonstr)){
			Log.info("Q�ҹ�������װ��ѯ��������,SendMessageRequest ��������ֵ:"+Jsonstr+",,url:"+buf.toString());
			return null;
		}
		try{
			GetOrderRespone QueryOrders=(GetOrderRespone)JSON.parseObject(Jsonstr,GetOrderRespone.class);
			return QueryOrders;
		}catch(Exception ex){
			Log.error("Q�ҹ�������װ��ѯ��������,,url:"+buf.toString()+",,json to bean Exception "+ex);
		}
		return null;
	}
	
	
	/**
	 * ������ֵǰȷ�Ͻӿ�
	 * @param orderid �������
	 * @param id ��ֵ���ţ��̵�ʱ���صĶ�����Ϣ��id�ڵ��ֵ��
	 * @return GetOrderRespone
	 */
	public static GetOrderRespone Confirmation(String orderid,String id){
		StringBuffer buf=new StringBuffer();
		buf.append(CONFIRMATION_URL);
		buf.append("?");
		buf.append("&orderid="+orderid);
		buf.append("&id="+id);
		buf.append("&format="+FORMAT);
		
		Log.info("Q�ҹ�������װ����ȷ������,,url:"+buf.toString());
		
		String Jsonstr=SendMessageRequest(buf.toString());
		
		if(Jsonstr==null || "".equals(Jsonstr)){
			Log.info("Q�ҹ�������װ����ȷ������,SendMessageRequest ��������ֵ:"+Jsonstr+",,url:"+buf.toString());
			return null;
		}
		try{
			GetOrderRespone QueryOrders=(GetOrderRespone)JSON.parseObject(Jsonstr,GetOrderRespone.class);
			return QueryOrders;
		}catch(Exception ex){
			Log.error("Q�ҹ�������װ����ȷ������,,url:"+buf.toString()+",,json to bean Exception "+ex);
		}
		return null;
	}
	
	/**
	 * ���س�ֵ����ӿ�
	 * @param orderid �������
	 * @param id ��ֵ���ţ��̵�ʱ���صĶ�����Ϣ��id�ڵ��ֵ��
	 * @param orderState ����״̬��4����ֵ�ɹ� 5����ֵʧ�� 6�����ɶ�����
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
		
		Log.info("Q�ҹ�������װ������ֵ�������,,url:"+buf.toString());
		
		String Jsonstr=SendMessageRequest(buf.toString());
		if(Jsonstr==null || "".equals(Jsonstr)){
			Log.info("Q�ҹ�������װ������ֵ�������,SendMessageRequest ��������ֵ:"+Jsonstr+",,url:"+buf.toString());
			return null;
		}
		try{
			GetOrderRespone QueryOrders=(GetOrderRespone)JSON.parseObject(Jsonstr,GetOrderRespone.class);
			return QueryOrders;
		}catch(Exception ex){
			Log.error("Q�ҹ�������װ������ֵ�������,,url:"+buf.toString()+",,json to bean Exception "+ex);
		}
		return null;
	}
	
	/**
	 * ������ 
	 * @return String 
	 */
	public static String getNumber(){
		return Tools.getNow()+((int)(Math.random()*10000)+10000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*10000)+10000);
	}
	
	
	/**
	 * ��������
	 * @param url
	 * @return String 
	 */
	public static String SendMessageRequest(String url){
		Log.info("Q�ҹ���,sendMessage info :"+url);
		HttpClient client=null;
		GetMethod post =null;
		try{
			client= new HttpClient();
			post=new GetMethod(url);
			int status = client.executeMethod(post);
			Log.info("Q�ҹ���,sendMessage info ,��Ӧstatus="+status+",,����url:"+url);
			if(status==200){
				String result = post.getResponseBodyAsString().trim();
				result=new String(result.getBytes("ISO-8859-1"),"utf-8");
				Log.info("Q�ҹ���,SendMessageRequest result Message :"+result+",,����url:"+url);
				return result;
			}
		}catch (Exception e) {
			Log.error("Q�ҹ���,sendMessage info :"+url+",Exception="+e);
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
