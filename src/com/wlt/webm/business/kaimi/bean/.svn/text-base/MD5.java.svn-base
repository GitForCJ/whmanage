package com.wlt.webm.business.kaimi.bean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;




public class MD5 {

	 /**
	  * 初始函数
	  *
	  */
	  public MD5() {
	    sourceStr = new byte[2048];
	    destStr = new byte[16];
	  }

	  /**
	   * 初始函数
	   * @param str md5加密数据源
	   */
	  public MD5(byte str[]) {
	    sourceStr = new byte[2048];
	    destStr = new byte[16];
	    sourceStr = str;
	  }

	  /**
	   * 返回源字符串
	   * @return byte[] 数据源
	   */
	  public byte[] getSourceStr() {
	    return sourceStr;
	  }

	  /**
	   * 设置源字符串
	   * @param sourceStr byte[] 数据源
	   */
	  public void setSourceStr(byte sourceStr[]) {
	    this.sourceStr = sourceStr;
	  }

	  /**
	   * 返回目的字符串
	   * @return byte[] 结果集
	   */
	  public byte[] getDestStr() {
	    return destStr;
	  }

	  /**
	   * 设置目的字符串
	   * @param destStr byte[] 结果集
	   */
	  public void setDestStr(byte destStr[]) {
	    this.destStr = destStr;
	  }

	  /**
	   * MD5加密开始
	   * @throws NoSuchAlgorithmException
	   * @return boolean 加密成功为真
	   */
	  public boolean md5Start() throws NoSuchAlgorithmException {
	    boolean over = true;
	    byte digest[] = new byte[128];
	    try {
	      MessageDigest alg = MessageDigest.getInstance("MD5");
	      alg.update(sourceStr);
	      digest = alg.digest();
	      destStr = digest;
	    }
	    catch (NoSuchAlgorithmException nsae) {
	      over = false;
	    }
	    return over;
	  }

	  /**
	   * 返回加密后的消息
	   * @param str String 加密前的信息
	   * @return byte[] 结果集
	   */
	  public byte[] getDigest(String str) {
	    sourceStr = str.getBytes();
	    try {
	      md5Start();
	    }
	    catch (Exception e) {}
	    return destStr;
	  }

	  /**
	   * 返回加密后的消息
	   * @param str byte[] 加密前的信息
	   * @return byte[] 结果集
	   */
	  public byte[] getDigest(byte str[]) {
	    sourceStr = str;
	    try {
	      md5Start();
	    }
	    catch (Exception e) {}
	    return destStr;
	  }

	  //数据源
	  private byte sourceStr[];
	  
	  //数据源
	  private byte destStr[];

	  public static void main(String args[])
	  {
		  
		
		  
		  
	   byte b[] = "wy@@@3-2011-08-29".getBytes();
		  //byte b[] ="1.02154325432h664562IVR12200909081712415106022735013016952733127.0.27.116Union20090514JS".getBytes();
		  MD5 d= new MD5();
	     byte [] r= d.getDigest(b);
	     System.out.println(new String(r));
		      
	    //System.out.println(getFromBASE64( "VkVSU0lPTj02NTUzOCZDSEFOTkVMSUQ9MjAwNzAxJkJVWUVSPTUxMDYwMjI3MyZDQVNIPTUwMDAmUEFZRVI9MTMwMTY5NTI3MzMmU1RBVFVTPTUwMDEmU0lHTj1mNWM4ZTg4MDA4ZjZhNTRkN2FmNjJkZDcwZjMyMDk0ZCZESUdJVEFMU0lHTj11TWtJVlVCZHVxY0lSNktvSk1kT3Q5ZG9ZRVZrMkdVODQwQVlzQS8zRFZ5M2t0UFpyZjBMbVE9PQ=="));
	   System.out.println( byteArrayToHexString(r).toLowerCase());
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
		
		/**
		 * 把单个字节转换为字符表示形式
		 * @param b 字节 
		 * @return 返回结果
		 */
		public static String byteToHexString(byte b) {
			int n = b;
			if (n < 0)
				n = 256 + n;
			int d1 = n / 16;
			int d2 = n % 16;
			return  hexDigits[d1] + hexDigits[d2];
		}
		
//		十六机制字符串表示
		private static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "A", "B", "C", "D", "E", "F" };


	}
