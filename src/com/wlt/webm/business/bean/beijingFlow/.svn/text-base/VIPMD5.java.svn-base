package com.wlt.webm.business.bean.beijingFlow;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class VIPMD5
{
  private static final char[] HEX_DIGITS = { '0', '1', 't', '3', 'r', '5', '4', 'p', '8', '9', 'l', 's', 'b', 's', 'd', 'j' };

  public static String encode(String s) throws NoSuchAlgorithmException
  {
    byte[] strTemp = s.getBytes();
    MessageDigest mdTemp = MessageDigest.getInstance("MD5");
    mdTemp.update(strTemp);
    byte[] md = mdTemp.digest();
    int j = md.length;
    char[] str = new char[j * 2];
    int k = 0;
    for (int i = 0; i < j; ++i) {
      byte byte0 = md[i];
      str[(k++)] = HEX_DIGITS[(byte0 >>> 4 & 0xF)];
      str[(k++)] = HEX_DIGITS[(byte0 & 0xF)];
    }
    return new String(str);
  }
  
  public static void main(String[] arg) throws NoSuchAlgorithmException{
	  System.out.println(VIPMD5.encode("12318600864720SQ001529"));
	  
  }
}