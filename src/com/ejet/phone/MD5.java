package com.ejet.phone;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;




public class MD5 {

	 /**
	  * ????��?
	  *
	  */
	  public MD5() {
	    sourceStr = new byte[2048];
	    destStr = new byte[16];
	  }

	  /**
	   * ????��?
	   * @param str md5????��?�?
	   */
	  public MD5(byte str[]) {
	    sourceStr = new byte[2048];
	    destStr = new byte[16];
	    sourceStr = str;
	  }

	  /**
	   * �??�??�?��
	   * @return byte[] ?��?�?
	   */
	  public byte[] getSourceStr() {
	    return sourceStr;
	  }

	  /**
	   * 设置�??�?��
	   * @param sourceStr byte[] ?��?�?
	   */
	  public void setSourceStr(byte sourceStr[]) {
	    this.sourceStr = sourceStr;
	  }

	  /**
	   * �?????�??�?
	   * @return byte[] �????
	   */
	  public byte[] getDestStr() {
	    return destStr;
	  }

	  /**
	   * 设置???�??�?
	   * @param destStr byte[] �????
	   */
	  public void setDestStr(byte destStr[]) {
	    this.destStr = destStr;
	  }

	  /**
	   * MD5???�??
	   * @throws NoSuchAlgorithmException
	   * @return boolean ??????为�?
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
	   * �????????�??
	   * @param str String ??????信�?
	   * @return byte[] �????
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
	   * �????????�??
	   * @param str byte[] ??????信�?
	   * @return byte[] �????
	   */
	  public byte[] getDigest(byte str[]) {
	    sourceStr = str;
	    try {
	      md5Start();
	    }
	    catch (Exception e) {}
	    return destStr;
	  }

	  //?��?�?
	  private byte sourceStr[];
	  
	  //?��?�?
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
		 * ???�?��??��16�??�??串表示模�?
		 * @param b �???��?
		 * @return �??�????
		 */
		public static String byteArrayToHexString(byte[] b) {
			String result = "";
			for (int i = 0; i < b.length; i++)
				result += byteToHexString(b[i]);
			return result;
		}
		
		/**
		 * ???�????��??���??表示形�?
		 * @param b �?? 
		 * @return �??�??
		 */
		public static String byteToHexString(byte b) {
			int n = b;
			if (n < 0)
				n = 256 + n;
			int d1 = n / 16;
			int d2 = n % 16;
			return  hexDigits[d1] + hexDigits[d2];
		}
		
//		????��?�??串表�?
		private static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "A", "B", "C", "D", "E", "F" };


	}
