package com.wlt.webm.business.TZ.tool;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;




			/**
			* MD5处理类
			* 创建日期：2011-11-25
			* Company：深圳市万恒科技有限公司
			* Copyright: Copyright (c) 2008
			* @author caiSJ 
			* @version 2.0.0.0
			*/
public class MD5 {
	
	public static String crypt(String str)
	{
		if (str == null || str.length() == 0) {
			throw new IllegalArgumentException("长度不能为空");
		}
		
		StringBuffer hexString = new StringBuffer();
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] hash = md.digest();
		
			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10) {
					hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
				}				
				else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}				
			}
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return hexString.toString();
	}
	
	public static void main(String[] args){
		
		String data = "9120110711356BASE101001090200000000999390889083   20:0:0:0:0:0:0:1  132888888881111111111";
		
		System.out.println(data.length());
	}
	
}
