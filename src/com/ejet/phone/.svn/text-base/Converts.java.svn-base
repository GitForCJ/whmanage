package com.ejet.phone;


import java.io.ByteArrayInputStream; 
import java.io.ByteArrayOutputStream; 
import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.io.Serializable; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 
public  class Converts { 
//æµ??1234567890ABCDEF1234567890ABCDEF
//æ­£å?257F3A7A19A5A2A6257F3A7A19A5A2A6
public  static char[] BToA = "1234567890ABCDEF1234567890ABCDEF".toCharArray() ; 

/** 
  * ??6è¿??å­??ä¸²è½¬?¢æ?å­???°ç? 
  * @param hex 
  * @return 
  */ 
public static byte[] hexStringToByte(String hex) { 
  int len = (hex.length() / 2); 
  byte[] result = new byte[len]; 
  char[] achar = hex.toCharArray(); 
  for (int i = 0; i < len; i++) { 
   int pos = i * 2; 
   result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1])); 
  } 
  return result; 
} 

private static byte toByte(char c) { 
  byte b = (byte) "0123456789ABCDEF".indexOf(c); 
  return b; 
} 

/** 
  * ??????ç»?½¬?¢æ?16è¿??å­??ä¸?
  * @param bArray 
  * @return 
  */ 
public static final String bytesToHexString(byte[] bArray) { 
  StringBuffer sb = new StringBuffer(bArray.length); 
  String sTemp; 
  for (int i = 0; i < bArray.length; i++) { 
   sTemp = Integer.toHexString(0xFF & bArray[i]); 
   if (sTemp.length() < 2) 
    sb.append(0); 
   sb.append(sTemp.toUpperCase()); 
  } 
  return sb.toString(); 
} 
/**
 * ??????ç»?????è¿??è¾??
 */
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

/** 
  * ??????ç»?½¬??¸ºå¯¹è±¡ 
  * @param bytes 
  * @return 
  * @throws IOException 
  * @throws ClassNotFoundException 
  */ 
public static final Object bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException { 
  ByteArrayInputStream in = new ByteArrayInputStream(bytes); 
  ObjectInputStream oi = new ObjectInputStream(in); 
  Object o = oi.readObject(); 
  oi.close(); 
  return o; 
} 

/** 
  * ???åº?????è±¡è½¬?¢æ?å­???°ç? 
  * @param s 
  * @return 
  * @throws IOException 
  */ 
public static final byte[] objectToBytes(Serializable s) throws IOException { 
  ByteArrayOutputStream out = new ByteArrayOutputStream(); 
  ObjectOutputStream ot = new ObjectOutputStream(out); 
  ot.writeObject(s); 
  ot.flush(); 
  ot.close(); 
  return out.toByteArray(); 
} 

public static final String objectToHexString(Serializable s) throws IOException{ 
  return bytesToHexString(objectToBytes(s)); 
} 

public static final Object hexStringToObject(String hex) throws IOException, ClassNotFoundException{ 
  return bytesToObject(hexStringToByte(hex)); 
} 

/** 
  * @?½æ????: BCD??½¬ä¸?0è¿??ä¸??¿æ?ä¼???? 
  * @è¾?????: BCD??
  * @è¾??ç»??: 10è¿??ä¸?
  */ 
public static String bcd2Str(byte[] bytes){ 
  StringBuffer temp=new StringBuffer(bytes.length*2); 

  for(int i=0;i<bytes.length;i++){ 
   temp.append((byte)((bytes[i]& 0xf0)>>>4)); 
   temp.append((byte)(bytes[i]& 0x0f)); 
  } 
  return temp.toString().substring(0,1).equalsIgnoreCase("0")?temp.toString().substring(1):temp.toString(); 
} 

/** 
  * @?½æ????: 10è¿??ä¸²è½¬ä¸?CD??
  * @è¾?????: 10è¿??ä¸?
  * @è¾??ç»??: BCD??
  */ 
public static byte[] str2Bcd(String asc) { 
  int len = asc.length(); 
  int mod = len % 2; 

  if (mod != 0) { 
   asc = "0" + asc; 
   len = asc.length(); 
  } 

  byte abt[] = new byte[len]; 
  if (len >= 2) { 
   len = len / 2; 
  } 

  byte bbt[] = new byte[len]; 
  abt = asc.getBytes(); 
  int j, k; 

  for (int p = 0; p < asc.length()/2; p++) { 
   if ( (abt[2 * p] >= '0') && (abt[2 * p] <= '9')) { 
    j = abt[2 * p] - '0'; 
   } else if ( (abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) { 
    j = abt[2 * p] - 'a' + 0x0a; 
   } else { 
    j = abt[2 * p] - 'A' + 0x0a; 
   } 

   if ( (abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) { 
    k = abt[2 * p + 1] - '0'; 
   } else if ( (abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) { 
    k = abt[2 * p + 1] - 'a' + 0x0a; 
   }else { 
    k = abt[2 * p + 1] - 'A' + 0x0a; 
   } 

   int a = (j << 4) + k; 
   byte b = (byte) a; 
   bbt[p] = b; 
  } 
  return bbt; 
} 

public static String BCD2ASC(byte[] bytes) { 
  StringBuffer temp = new StringBuffer(bytes.length * 2); 

  for (int i = 0; i < bytes.length; i++) { 
   int h = ((bytes[i] & 0xf0) >>> 4); 
   int l = (bytes[i] & 0x0f);   
   temp.append(BToA[h]).append( BToA[l]); 
  } 
  return temp.toString() ; 
} 

/** 
  * MD5???å­??ä¸²ï?è¿????????16è¿??å­??ä¸?
  * @param origin 
  * @return 
  */ 
public static String MD5EncodeToHex(String origin) { 
     return bytesToHexString(MD5Encode(origin)); 
   } 

/** 
  * MD5???å­??ä¸²ï?è¿????????å­???°ç? 
  * @param origin 
  * @return 
  */ 
public static byte[] MD5Encode(String origin){ 
  return MD5Encode(origin.getBytes()); 
} 

/** 
  * MD5???å­???°ç?ï¼?????å¯????????ç»?
  * @param bytes 
  * @return 
  */ 
public static byte[] MD5Encode(byte[] bytes){ 
  MessageDigest md=null; 
  try { 
   md = MessageDigest.getInstance("MD5"); 
   return md.digest(bytes); 
  } catch (NoSuchAlgorithmException e) { 
   e.printStackTrace(); 
   return new byte[0]; 
  } 
  
} 
} 
