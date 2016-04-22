package com.wlt.webm.business.TZ.tool;


import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;



public class HmacMD5 {

	private static final String KEY_MAC = "HmacMD5";
	
	
	
	public static String crypt(String str, String key)
	{
		StringBuffer hexString = new StringBuffer();
		
		try{
			//此类提供“消息验证码”（Message Authentication Code，MAC）算法的功能。
			Mac mac = Mac.getInstance(KEY_MAC);
			//SecretKeySpec以与 provider 无关的方式指定一个密钥
			mac.init(new SecretKeySpec(key.getBytes("ISO-8859-1"), KEY_MAC));
			byte[] hash = mac.doFinal(str.getBytes("ISO-8859-1"));
			
			for (int i = 0; i < hash.length; i++) {
				
				if ((0xff & hash[i]) < 0x10) {
					hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
				}				
				else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}				
			}
			
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return hexString.toString().toUpperCase();
	}
	
	public static void main(String[] args) {
		
//		 String key1 = "aswdff45678jncVVFDD1122xxeeew22234444ffffffffffffffff";
//		 String key2 = "ffffffffffffaswdff45678jncVVFDD1122xxeeew22234444ffffffffffffffffddddddddddddss22222222222222";
//		 String str = "12345678jhgfvbkll;..mngfssaxcvhuioll99--';;aaaaaaaaaaaaaxxxs";
		 //676447ED3E430D160D4347791E9E60A3
		
		 String str2 = "11112222222222222221111";
		 String key3 = "27AE799ACFBC5D7B531DECAFCE552E94";
		
		 String result3 = HmacMD5.crypt("0000HRD000000124055220111117163552100006HRD000000138133BFE21EC3F8B  13011117777C85FEFF078DB1CA200000", "06238424");
		 System.out.println(result3);
	}

}
