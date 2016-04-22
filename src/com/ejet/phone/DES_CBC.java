package com.ejet.phone;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DES_CBC {
	
	private static final String Algorithm = "DES/ECB/NoPadding";//���� �����㷨,���� DES,DESede,Blowfish
	public static void main(String[] args) {
		//����Կ����Ϊ��1234567890ABCDEF1234567890ABCDEF
		byte[] ImacKey=hexStringToByte("1234567890ABCDEF1234567890ABCDEF");
		byte[] leftKey=hexStringToByte("1234567890ABCDEF");
		byte[] rightKey=hexStringToByte("1234567890ABCDEF");
		//�յ�����Կ��Ϣ����������?���?5DC386FC624184D6846EE8B������MAC��Կ75DC386FC624184D��������У���룺6846EE8B��
		byte[] MACMsg=hexStringToByte("75DC386FC624184D");
		byte[] MACCheck=hexStringToByte("6846EE8B");
		byte[] key=decryptMode(leftKey,MACMsg);//ͨ������Կ��MAC��Կ����3DES���ܿ��Եõ�������Կ
		 key=encryptMode(rightKey,key);		   //ͨ������Կ��MAC��Կ����3DES���ܿ��Եõ�������Կ
		 key=decryptMode(leftKey,key);         //ͨ������Կ��MAC��Կ����3DES���ܿ��Եõ�������Կ
		System.out.println("3DES���ܺ󣬹�����ԿΪ"+toHex(key));//����3DES����֮����Ի�ù�����ԿA878F1F435BBEB10
		byte[] Check=OneZero(key);//ȡǰ����뵥����У����Ƚ�
		boolean isRight=IsCheck(Check,MACCheck);
		System.out.println("check:"+toHex(Check));
		System.out.println(isRight);//У�����Կ�Ƿ���ȷ����ȷ�򷵻�?rue
		//ǩ�������Կ�ɹ��?���?Ϳ���ʹ�øù�����Կ���������ҵ���ġ�ע�������·�Ͽ�����һ���?�����ǩ����ȡ��Կ��?
		
		//-------------------------�����Ǳ���MACУ������㷽��?
		//�������?ac����ı���У�����Ӧ��Ϣ��ȡ������ɱ���?07661100000001480766610019960101201104261021198975DC386FC624184D54AFC1F0
	    String block="207661100000001480766610019960101201104261021198975DC386FC624184D54AFC1F0";
		byte[] mac=getMAC(key,block.getBytes());//�������㱨�ĺ͹�����Կ������getMAC�������Ի��?acУ����
		System.out.println("�����õ�MACΪ��"+toHex(mac));
		String namepass= MD5.byteArrayToHexString(DES_CBC.encryptMode(Tools.getEightStr("jiaofeib", " ") .getBytes(), Tools.getEightBeiStr("12345678900"," ").getBytes()));
		System.out.println(namepass);
	}
	/** 
	  * ��16�����ַ�ת�����ֽ����� 
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
	 * DES���ܷ���
	 * @param keybyte
	 * @param src
	 * @return
	 */
	   public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		      try {
		        //������?
		        SecretKey deskey = new SecretKeySpec(keybyte, "DES");
		        //����
		        Cipher c1 = Cipher.getInstance(Algorithm);
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
	   /**
	    * DES���ܷ���
	    * @param keybyte
	    * @param src
	    * @return
	    */
	   public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		      try {
		        //������?
		        SecretKey deskey = new SecretKeySpec(keybyte, "DES");
		        //����
		        Cipher c1 = Cipher.getInstance(Algorithm);
		        c1.init(Cipher.ENCRYPT_MODE, deskey);
		        return c1.doFinal(src);
		        //���ܳɹ��󷵻�
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
		      //ʧ�ܷ���null
		    }
	   /**
	    * ���ֽ����鰴ʮ��������
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
		 * ���е�������Կ�㷨
		 */
		public static byte[] OneZero(byte[] des){
			byte[] pink = hexStringToByte("0000000000000000");
		     byte[] Cvalue = encryptMode(des, pink);
		    // System.out.println("��������Կ������õ�ֵ�?���?+toHex(Cvalue));
		     byte[] ch=new byte[pink.length/2];
			  for(int i=0;i<ch.length;i++){
				  ch[i]=Cvalue[i];
			     }
		     return ch;
		}
		public static boolean IsCheck(byte[] value, byte[] checkvalue){
	     boolean he=true;
	     for(int i=0;i<value.length;i++){
	    	 if(value[i]!=checkvalue[i]){
	    		 he=false;
	    	 }
	     }
	     return he;
		}
		public static byte[] getMAC(byte[] workKey,byte[] block){
			/**
			 * DES CBC����ο�����?
			 */
			byte[] xor = hexStringToByte("0000000000000000");
		    	//DESvalue ΪDES���ܺ�õ������
		    	byte[] DESvalue = hexStringToByte("0000000000000000");
		    	for(int i=0;i<block.length;){
		    		//ÿ��ȡ64bit����ݽ������
		    		for(int j=0;j<xor.length;i++,j++){
		    			//���һ�������64bit��0x00
		    			if(i>=block.length){
		    				xor[j]=(byte)(0x00^xor[j]);}		
		    			else{
		    				xor[j]=(byte)(block[i]^xor[j]);}
		    			}
		    		//���õ����ݽ���DES����
		    		 DESvalue = encryptMode(workKey, xor);
		    		 xor = DESvalue;  
		    		}
		    	 byte[] temp=new byte[DESvalue.length/2];
				 for(int k=0;k<DESvalue.length/2;k++){
					 temp[k]=DESvalue[k];
				 }
				return temp;
		}
		
}