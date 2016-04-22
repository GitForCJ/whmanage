package com.wlt.webm.business.kaimi.bean;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class KMQuery {
	
	private static String key="qPxp8Erj1FGgkXMV3qnlnX2BgXc0G1S0";
	private static String addr="http://58.63.225.48:9000/Api/CX.ashx?";
	
	public static String kmquery(String oid)throws Exception {
		String msg="";
		MD5 md5= new MD5();
		String sign= byteArrayToHexString(md5.getDigest("oid="+oid+key));
		msg=addr+"oid="+oid+"&sign="+sign;
		URL url = new URL(msg);
		 long l=System.currentTimeMillis(); 
	     URLConnection uc = url.openConnection();       
	     InputStream in = uc.getInputStream();       
	     int c;     
	     byte b[] =new byte[1024];
	     in.read(b);
	     String result=new String(b,"utf-8");
	     System.out.println();
	     System.out.println(System.currentTimeMillis()-l);               
	     in.close();
	     return result;
	}
	
	public static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return  hexDigits[d1] + hexDigits[d2];
	}
	
	 /**
	 * 把数组转换为16进制字符串表示模式
	 * @param b 字节数组
	 * @return 返回结果集
	 */
	public static String byteArrayToHexString(byte[] b) {
		String result = "";
		for (int i = 0; i < b.length; i++)
			result += byteToHexString(b[i]);
		return result;
	}
	
//	十六机制字符串表示
	private static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "A", "B", "C", "D", "E", "F" };

}
