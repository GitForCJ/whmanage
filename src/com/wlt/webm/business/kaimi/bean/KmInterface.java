package com.wlt.webm.business.kaimi.bean;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.wlt.webm.ten.service.MD5Util;


/**
 * ���ܽӿ�
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
	 * ���ܲ�ѯ�ӿ�
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
				System.out.println("���ܲ�ѯ�����쳣");
				return 0;
			}
		     InputStream in = connection.getInputStream();       
		     byte b[] =new byte[512];
		     in.read(b);
		     result=new String(b,"utf-8").trim();
		     System.out.println("���ܲ�ѯ����:"+result); 
		     in.close();
		     connection.disconnect();
             connection=null;
             if(!(null!=result&&result.length()>3)){
            	 return 9;//�޽��
             }else if(result.indexOf("NOT_FOUND")!=-1){
		    	 return 2;// û���ҵ����׺�
		     }else if(result.indexOf("DOING")!=-1){
		    	 return 3;// ��ֵ��
		     }else if(result.indexOf("SUCCESS")!=-1){
		    	 return 4;// ��ֵ�ɹ�
		     }else if(result.indexOf("PART_SUCCESS")!=-1){
		    	 return 5;// ���ֳɹ�
		     }else if(result.indexOf("FAIL")!=-1){
		    	 return 6;// ��ֵʧ��
		     }else if(result.indexOf("ERROR")!=-1){
		    	 return 7;// ��������
		     }else{
		    	 return 8;// λ�ô���
		     }
	}
	
	
	/**
	 * ���ܳ�ֵ�ӿ�
	 * @param oid  10λ���׺�
	 * @param account  �ֻ���
	 * @param value    ���
	 * @param pid    ��Ʒid
	 * @param arg1    ����
	 * @param arg2    �㶫
	 * @param arg3    ����
	 * @return  ��ֵ״̬
	 * @throws Exception favicon
	 */
	public static int kmFill(String oid,String account,String value,String pid,String arg1,String arg2,String arg3) throws Exception{
		String result=null;   
		String msg0="account="+account+"&arg1="+arg1+"&oid="+oid+"&pid="+pid+"&value="+value+key;
		    System.out.println(msg0);
		    String msg=null;
			String sign= MD5Util.MD5Encode(msg0, "UTF-8").toUpperCase();
			System.out.println("==="+sign);
			//��װ
			URL url;
			msg=addr+"account="+account+"&arg1="+URLEncoder.encode(arg1,"UTF-8").toLowerCase()
			//+"&arg2="+URLEncoder.encode(arg2,"UTF-8").toLowerCase()
			//+"&arg3="+URLEncoder.encode(arg3,"UTF-8").toLowerCase()
			+"&oid="+oid+"&pid="+pid+"&value="+value+"&sign="+sign;
			System.out.println("��������url:"+msg);
			 url = new URL(msg);
		    HttpURLConnection connection = (HttpURLConnection)url.openConnection();   
			connection.connect();
			if (!(connection.getResponseCode() == HttpURLConnection.HTTP_OK)) {
				System.out.println("���ܳ�ֵ�����쳣");
				return 0;
			}
		     InputStream in = connection.getInputStream();       
		     byte b[] =new byte[512];
		     in.read(b);
		     result =new String(b,"utf-8").trim(); 
		     System.out.println("���ܳ�ֵ����:"+result);               
		     in.close();
		     connection.disconnect();
		     connection=null;
//             if(!(null!=result&&result.length()>3)){
//            	 return 9;//�޽��
//             }else if(result.indexOf("EXISTS")!=-1){
//		    	 return 2;// ����Ѿ�����
//		     }else if(result.indexOf("FAIL")!=-1){
//		    	 return 3;// ʧ��
//		     }else if(result.indexOf("OK")!=-1){
//		    	 return 4;// ��ֵ�ɹ�
//		     }else if(result.indexOf("ERROR")!=-1){
//		    	 return 7;// ��������
//		     }else{
//		    	 return 8;// δ֪����
//		     }
		     return 1;//�ύ�ɹ�
	}
	
	
	/**
	 * ���shuzi
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
//		KmInterface.kmFill("10000","18682033916", "100", "1", "����","�㶫","����");
		
		KmInterface.kmquery("10000");
	}
	
	
}
