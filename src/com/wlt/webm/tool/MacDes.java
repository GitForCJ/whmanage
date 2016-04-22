package com.wlt.webm.tool;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


	/**
	 * 3DES加密解密，加密源可以为中文。
	 */
	public class MacDes
	{
	    /**
	     * 3DES加密算法
	     */
	    private static final String algorithm = "DES/ECB/NoPadding";

	    /**
	     * 密钥字符集
	     */
	    private static final String keyCharsetName = "ISO-8859-1";

	    /**
	     * 明文字符集
	     */
	    private static final String srcCharsetName = "UTF-8";

	    /**
	     * 密文字符集
	     */
	    private static final String destCharsetName = "ISO-8859-1";

	    ////
	    private static final String Algorithm = "DES/ECB/NoPadding"; //定义 加密算法,可用 DES,DESede,Blowfish
	    //keybyte为加密密钥，长度为24字节
	    //src为被加密的数据缓冲区（源）
	    public static byte[] encryptMode(byte[] keybyte, byte[] src) {
	      try {
	        //生成密钥
	        SecretKey deskey = new SecretKeySpec(keybyte, "DES");
	        //加密
	        Cipher c1 = Cipher.getInstance(Algorithm);
	        //    javax.crypto.spec.IvParameterSpec ips = new javax.crypto.spec.IvParameterSpec(keybyte);
	        c1.init(Cipher.ENCRYPT_MODE, deskey);
	        return c1.doFinal(src);
	        //加密成功后返回
	      }
	      catch (java.security.NoSuchAlgorithmException e1) {
	        e1.printStackTrace();
	      }
	      catch (javax.crypto.NoSuchPaddingException e2) {
	        e2.printStackTrace();
	      }
	      catch (java.lang.Exception e3) {
	        e3.printStackTrace();
	      }
	      return null;
	      //失败返回null
	    }
	    //keybyte为加密密钥，长度为24字节
	    //src为加密后的缓冲区
	    public static byte[] decryptMode(byte[] keybyte, byte[] src) {
	      try {
	        //生成密钥
	        SecretKey deskey = new SecretKeySpec(keybyte, "DES");
	        //解密
	        Cipher c1 = Cipher.getInstance(Algorithm);
	        //    byte[] iv = new sun.misc.BASE64Decoder().decodeBuffer(new String( keybyte));
	        //    System.out.println("iv:"+iv.length);
	        //     javax.crypto.spec.IvParameterSpec ips = new javax.crypto.spec.IvParameterSpec(keybyte);
	        c1.init(Cipher.DECRYPT_MODE, deskey);
	        return c1.doFinal(src);
	      }
	      catch (java.security.NoSuchAlgorithmException e1) {
	        e1.printStackTrace();
	      }
	      catch (javax.crypto.NoSuchPaddingException e2) {
	        e2.printStackTrace();
	      }
	      catch (java.lang.Exception e3) {
	        e3.printStackTrace();
	      }
	      return null;
	    }
	    ///
	    /**
	     * 加密
	     * @param key 密钥，必须为24字节
	     * @param src 明文
	     * @return 8字节倍数的密文，失败则返回null
	     */
	    public static byte[] encrypt(byte[] key, byte[] src)
	    {
	        SecretKey secretkey = new SecretKeySpec(key, algorithm);
	        try
	        {
	            Cipher cipher = Cipher.getInstance(algorithm);
	            cipher.init(Cipher.ENCRYPT_MODE, secretkey);
	            return cipher.doFinal(src);
	        }
	        catch (Exception e)
	        {
	        	e.printStackTrace();
	            return null;
	        }
	    }

	    /**
	     * 加密
	     * @param key 密钥，必须为24字节
	     * @param src 明文
	     * @return 8字节倍数的密文，失败则返回null
	     */
	    public static String encrypt(String key, String src)
	    {
	        try
	        {
	            return new String(encrypt(key.getBytes(keyCharsetName), src.getBytes(srcCharsetName)), destCharsetName);
	        }
	        catch (UnsupportedEncodingException e)
	        {
	        	e.printStackTrace();
	            return null;
	        }
	    }

	    /**
	     * 解密
	     * @param key 密钥，必须为24字节
	     * @param src 密文
	     * @return 明文，失败则返回null
	     */
	    public static byte[] decrypt(byte[] key, byte[] src)
	    {
	        SecretKey secretkey = new SecretKeySpec(key, algorithm);
	        try
	        {
	            Cipher cipher = Cipher.getInstance(algorithm);
	            cipher.init(Cipher.DECRYPT_MODE, secretkey);
	            return cipher.doFinal(src);
	        }
	        catch (Exception e)
	        {
	        	e.printStackTrace();
	            return null;
	        }
	    }

	    /**
	     * 解密
	     * @param key 密钥，必须为24字节
	     * @param dest 密文
	     * @return 明文，失败则返回null
	     */
	    public static String decrypt(String key, String dest)
	    {
	        try
	        {
	            return new String(decrypt(key.getBytes(keyCharsetName), dest.getBytes(destCharsetName)), srcCharsetName);
	        }
	        catch (UnsupportedEncodingException e)
	        {
	        	e.printStackTrace();
	            return null;
	        }
	    }

	    /**
	     * 用指定的密钥对明文加密，并对加密结果转换成ASCII码，最后用制定的分隔字符串连接ASCII码形成最终结果。
	     * @param key 密钥，必须为24字节
	     * @param src 明文
	     * @param splitString 分割字符串
	     * @return 加密结果，失败则返回null
	     */
	    public static String encryptAscii(String key, String src, String splitString)
	    {
	        try
	        {
	            byte[] bytes = encrypt(key.getBytes(keyCharsetName), src.getBytes(srcCharsetName));
	            StringBuffer result = new StringBuffer(100);
	            if (bytes.length > 0)
	            {
	                result.append(bytes[0]);

	                for (int i = 1; i < bytes.length; i++)
	                {
	                    result.append(splitString).append(bytes[i]);
	                }
	            }
	            return result.toString();
	        }
	        catch (Exception e)
	        {
	        	e.printStackTrace();
	            return null;
	        }
	    }

	    /**
	     * 用指定的密钥对ASCII密文解密。
	     * @param key 密钥，必须为24字节
	     * @param dest 密文
	     * @param splitString 分割字符串
	     * @return 解密结果，失败则返回null
	     */
	    public static String decryptAscii(String key, String dest, String splitString)
	    {
	        try
	        {
	            String[] asciis = dest.split(splitString);
	            byte[] bytes = new byte[asciis.length];
	            for (int i = 0; i < asciis.length; i++)
	            {
	                bytes[i] = Byte.parseByte(asciis[i]);
	            }

	            return new String(decrypt(key.getBytes(keyCharsetName), bytes), srcCharsetName);
	        }
	        catch (Exception e)
	        {
	            return null;
	        }
	    }

	    
	    
	    /*
	     * 3DES解密
	     * @param key 密钥，16字节（32位16进制）
	     * @param dest mac密文
	     * @param checkvalue 对方传的校验值
	     * @return 解密结果，失败则返回null
	     * @author 何宁
	     */
	    public static byte[] ThreeDesDecrypt(byte[] key,byte[] dest,byte[] checkvalue){
	    	 //将密钥分为前后两组
	    	 byte[] key1 = new byte[key.length/2];
	    	 byte[] key2 = new byte[key.length/2];
	    	  for(int i=0;i<key.length;i++){
	    		 if(i<key.length/2){
	    			 key1[i]=key[i];}
	    		 else{
	    			 key2[i-key.length/2]=key[i];
	    		 }
	    	  }
	    	 //3DES解密
	    	 byte[] a = MacDes.decryptMode(key1, dest);
		     byte[] b = MacDes.encryptMode(key2, a);
		     byte[] c = MacDes.decryptMode(key1, b);    	
	    	 //校验是否正确
		     //解出Mac后，对8个数值0做单倍长密钥算法，取结果的前四位与checkvalue 的值比较应该是一致的
		     byte[] pink = Converts.hexStringToByte("0000000000000000");
		     byte[] Cvalue = MacDes.encryptMode(c, pink);
		     byte[] value = new byte[4];
		     for(int i=0;i<value.length;i++){
		    	 value[i]=Cvalue[i];
		     }
		     //标量he 为ture代表与checkvalue相等，为false代表不相等
		     boolean he=true;
		     for(int i=0;i<value.length;i++){
		    	 if(value[i]!=checkvalue[i]){
		    		 he=false;
		    	 }
		     }
		     //校验真确返回Mac，不正确返回null
		     if(he)
		     return c;
		     else 
		     return null;
	    }
	    
	    /*
	     * 3DES解密
	     * @param key 密钥，16字节（32位16进制）
	     * @param dest mac密文
	     * @return 解密结果，失败则返回null
	     * @author 何宁
	     */
	    public static byte[] ThreeDesDecrypt(byte[] key,byte[] dest){
	    	 //将密钥分为前后两组
	    	 byte[] key1 = new byte[key.length/2];
	    	 byte[] key2 = new byte[key.length/2];
	    	  for(int i=0;i<key.length;i++){
	    		 if(i<key.length/2){
	    			 key1[i]=key[i];}
	    		 else{
	    			 key2[i-key.length/2]=key[i];
	    		 }
	    	  }
	    	 //3DES解密
	    	 byte[] a = MacDes.decryptMode(key1, dest);
		     byte[] b = MacDes.encryptMode(key2, a);
		     byte[] c = MacDes.decryptMode(key1, b);    	
		     return c;
	    }
	    
	    /**
	     * @param args
	     * @throws UnsupportedEncodingException 
	     */
	    public static void main(String[] args) throws UnsupportedEncodingException
	    {
	    	Converts tool=new Converts();
	    	 byte[] key = tool.hexStringToByte("1234567890ABCDEF");
	    	 byte[] testvalue = tool.hexStringToByte("75DC386FC624184D");
	         byte[] pink = tool.hexStringToByte("A878F1F435BBEB10");   
	         byte[] checkvalue = tool.hexStringToByte("54AFC1F0");  
	         byte[] shiyan = tool.hexStringToByte("0000000000000000");
	         byte[] shiyan2 = tool.hexStringToByte("00000000");
	         String tmp = new String(testvalue,"iso-8859-1");
	    	//decryptMode为DES解密
	    	//encryptMode为DES加密
	    	byte[] _d = MacDes.decryptMode(key, testvalue);
	    	byte[] _e = MacDes.encryptMode(key, _d);
	    	byte[] _f = MacDes.decryptMode(key, _e);
//	    	 byte[] Cvalue = MacDes.encryptMode(tool.hexStringToByte("75DC386FC624184D"), tool.hexStringToByte("0000000000000000"));
	    	System.out.println(toHex(_d));
	    	byte[] Cvalue = MacDes.encryptMode(tool.hexStringToByte("A878F1F435BBEB10"), tool.hexStringToByte("0000000000000000"));
	    	MacDes.encryptMode(_d, pink);
	    	System.out.println("3des解密第一步后为："+toHex(_d));
	    	System.out.println("3des解密第二步后为："+toHex(_e));
	    	System.out.println("3des解密第三步后为："+toHex(_f));
	    	System.out.println("3des解密第三步后为："+toHex(tmp.getBytes("iso-8859-1")));
	    	System.out.println("3des解密第三步后为："+new String(pink,"iso-8859-1"));
	    	System.out.println("单倍长："+toHex(Cvalue));
	 /*   	byte[] _a = MacDes.encryptMode(_f, shiyan);
	    	byte[] _b = MacDes.decryptMode(key, _a);
	    	byte[] _c = MacDes.encryptMode(key, _b);
	    	System.out.println("3des加密第一步后为："+toHex(_a));
	    	System.out.println("3des加密第二步后为："+toHex(_b));
	    	System.out.println("3des加密第三步后为："+toHex(_c));
	    	
	    	byte[] mac= MacDes.ThreeDesDecrypt(pink, testvalue, checkvalue);
	    	System.out.println("mac："+toHex(mac));*/
	    	//value 为mac block
	    	
	  	    	//E48B8D57
	    	
//	    	String text0="03.0.0.0trim();
//	    	String text2="166225889941255749".trim();
//	    	String text3="300000".trim();
//	    	String text7="1129172936".trim();
//	    	String text11="172936".trim();
//	    	String text25="00".trim();
//	    	String text32="0800090000".trim();
//	    	String text33="0800090000".trim();
//	    	String text39="A0".trim();
//	    	String text41="02023792".trim();
//	    	String text42="99944014813.0.0.0.trim();    	
//	    	String text00="20";    	
//	    	String valueStr= text0+text00+text2+text00+text3+text00+text7+text00+text11+text00+text25+text00+text32+text00+text33+text00+text39+text00+text41+text00+text42;
//	    	byte[] value = new String(tool.hexStringToByte(valueStr)).replaceAll("(20)+", "20").getBytes();
	 /*   	byte[] value = tool.hexStringToByte("03.0.0.0.0.08000900003.0.0.0");
	    	System.out.println("========>"+new String(value)+"<====");
	    	//xor为初始数据,后接受异或运算的数据
	    	byte[] xor = tool.hexStringToByte("0000000000000000");
	    	byte[] tt = tool.hexStringToByte("20");
	    	System.out.println("====20====>"+new String(tt)+"<====");
	    	//mackey为DES密匙51EABF5BD540D0B0
	    	byte[] mackey =tool.hexStringToByte("1C1C9EECE5EFC19E");
	    	//DESvalue 为DES加密后得到的数据
	    	byte[] DESvalue = tool.hexStringToByte("0000000000000000");
	    	System.out.println("value:"+toHex(value));
	    	int m=0;
	    	for(int i=0;i<value.length;){
	    		//每次取64bit的数据进行异或
	    		
	    		for(int j=0;j<xor.length;i++,j++){
	    			//最后一组如果不足64bit补0x00
	    			if(i>=value.length){
	    				xor[j]=(byte)(0x00^xor[j]);}		
	    			else{
	    				xor[j]=(byte)(value[i]^xor[j]);}
	    			}
	    			System.out.println("异或运行之后："+toHex(xor));
	    		//异或得到内容进行DES加密
	    		 DESvalue = MacDes.encryptMode(mackey, xor);
	    		 xor = DESvalue;
	    		System.out.println("DES加密之后："+toHex(DESvalue));
	    		}   	*/
	    }
	    
//将字节按十六进制格式输出
	    public static final String toHex(byte b[])  
	    {  
	        char buf[] = new char[b.length * 2];  
	        int j;  
	        for(int i = j = 0; i < b.length; i++)  
	        {  
	            int k = b[i];  
	            buf[j++] = hex[k >>> 4 & 0xf];  
	            buf[j++] = hex[k & 0xf];  
	        }  

	        return new String(buf);  
	    }  

	    private static final char hex[] = {  
	        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',   
	        'A', 'B', 'C', 'D', 'E', 'F'  
	    }; 
	}
