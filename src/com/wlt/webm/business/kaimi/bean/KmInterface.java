package com.wlt.webm.business.kaimi.bean;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.wlt.webm.ten.service.MD5Util;


/**
 * 卡密接口
 * @author lenovo
 *
 */
public class KmInterface {
	
	private static String key="qPxp8Erj1FGgkXMV3qnlnX2BgXc0G1S0";
	private static String addr="http://58.63.225.48:9000/Api/CZ.ashx?";
	private static String addrCX="http://58.63.225.48:9000/Api/CX.ashx?";
//	private static String addr="http://132.121.249.42:9000/Api/CZ.ashx?";
//	private static String addrCX="http://132.121.249.42:9000/Api/CX.ashx?";
	
	/**
	 * 卡密查询接口
	 * @param oid
	 * @return
	 * @throws Exception
	 */
	public static int kmquery(String oid)throws Exception {
		String result=null;
		String msg="";
		String sign=MD5Util.MD5Encode("oid="+oid+key, "UTF-8").toUpperCase();
		msg=addrCX+"oid="+oid+"&sign="+sign;
		URL url = null;
			 url = new URL(msg);
		    HttpURLConnection connection = (HttpURLConnection)url.openConnection();   
			connection.connect();
			if (!(connection.getResponseCode() == HttpURLConnection.HTTP_OK)) {
				System.out.println("卡密查询链接异常");
				return 0;
			}
		     InputStream in = connection.getInputStream();       
		     byte b[] =new byte[512];
		     in.read(b);
		     result=new String(b,"utf-8").trim();
		     System.out.println("卡密查询返回:"+result); 
		     in.close();
		     connection.disconnect();
             connection=null;
             if(!(null!=result&&result.length()>3)){
            	 return 9;//无结果
             }else if(result.indexOf("NOT_FOUND")!=-1){
		    	 return 2;// 没有找到交易号
		     }else if(result.indexOf("DOING")!=-1){
		    	 return 3;// 充值中
		     }else if(result.indexOf("SUCCESS")!=-1){
		    	 return 4;// 充值成功
		     }else if(result.indexOf("PART_SUCCESS")!=-1){
		    	 return 5;// 部分成功
		     }else if(result.indexOf("FAIL")!=-1){
		    	 return 6;// 充值失败
		     }else if(result.indexOf("ERROR")!=-1){
		    	 return 7;// 参数错误
		     }else{
		    	 return 8;// 位置错误
		     }
	}
	
	
	/**
	 * 卡密充值接口
	 * @param oid  10位交易号
	 * @param account  手机号
	 * @param value    面额
	 * @param pid    产品id
	 * @param arg1    电信
	 * @param arg2    广东
	 * @param arg3    深圳
	 * @return  充值状态
	 * @throws Exception favicon
	 */
	public static int kmFill(String oid,String account,String value,String pid,String arg1,String arg2,String arg3) throws Exception{
		String result=null;   
		String msg0="account="+account+"&arg1="+arg1+"&oid="+oid+"&pid="+pid+"&value="+value+key;
		    System.out.println(msg0);
		    String msg=null;
			String sign= MD5Util.MD5Encode(msg0, "UTF-8").toUpperCase();
			System.out.println("==="+sign);
			//组装
			URL url;
			msg=addr+"account="+account+"&arg1="+URLEncoder.encode(arg1,"UTF-8").toLowerCase()
			//+"&arg2="+URLEncoder.encode(arg2,"UTF-8").toLowerCase()
			//+"&arg3="+URLEncoder.encode(arg3,"UTF-8").toLowerCase()
			+"&oid="+oid+"&pid="+pid+"&value="+value+"&sign="+sign;
			System.out.println("卡密请求url:"+msg);
			 url = new URL(msg);
		    HttpURLConnection connection = (HttpURLConnection)url.openConnection();   
			connection.connect();
			if (!(connection.getResponseCode() == HttpURLConnection.HTTP_OK)) {
				System.out.println("卡密充值链接异常");
				return 0;
			}
		     InputStream in = connection.getInputStream();       
		     byte b[] =new byte[512];
		     in.read(b);
		     result =new String(b,"utf-8").trim(); 
		     System.out.println("卡密充值返回:"+result);               
		     in.close();
		     connection.disconnect();
		     connection=null;
//             if(!(null!=result&&result.length()>3)){
//            	 return 9;//无结果
//             }else if(result.indexOf("EXISTS")!=-1){
//		    	 return 2;// 编号已经存在
//		     }else if(result.indexOf("FAIL")!=-1){
//		    	 return 3;// 失败
//		     }else if(result.indexOf("OK")!=-1){
//		    	 return 4;// 充值成功
//		     }else if(result.indexOf("ERROR")!=-1){
//		    	 return 7;// 参数错误
//		     }else{
//		    	 return 8;// 未知错误
//		     }
		     return 1;//提交成功
	}
	
	
	/**
	 * 随机shuzi
	 * @param length
	 * @return
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}
	
	
	public static void main(String[] args) throws Exception {
//		KmInterface.kmFill("10000","18682033916", "100", "1", "电信","广东","深圳");
		
		KmInterface.kmquery("10000");
	}
	
	
}
