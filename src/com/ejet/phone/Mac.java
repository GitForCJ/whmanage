package com.ejet.phone;


public class Mac {
	
	/**
	 * key��Կ
	 * name �û���
	 * src :jsonԭʼ����
	 * servertype��ҵ������
	 * �Ե�½��Ϣ���м��� 
	 * @return ҵ������|XXX1|XXX2
	 */
	/*public static String getLoginEncryt(String key,String name,String src,String servertype)
	{
		//1.�û���name��key��Կ���г�����XXX1
		String namepass= MD5.byteArrayToHexString(DES_CBC.encryptMode(Tools.getEightStr(key, " ") .getBytes(), Tools.getEightBeiStr(name," ").getBytes()));
		
		//2.��key����md5���ܲ���16���ַ�������JSON���м���ΪXXX2
        String  keyTmp=name+key.toString().trim();
		//���ȶ�src����md5����
		byte b[] = keyTmp.getBytes();
		MD5 d= new MD5();
	    byte [] r= d.getDigest(b);
	     //���md5�����ת��Ϊ16�����ַ���
	    String dataSec=Tools.getEightStr(MD5.byteArrayToHexString(r).toUpperCase(),"0");
		String jsonpass= MD5.byteArrayToHexString(DES_CBC.encryptMode(dataSec.getBytes(), Tools.getEightBeiStr(src," ").getBytes()));
		
		return servertype+"|"+namepass+"|"+jsonpass;
		
	}*/
	public static String getLoginEncryt(String key,String name,String src,String servertype)
	{
		//1.�û���name��key��Կ���г�����XXX1
		String namepass= MD5.byteArrayToHexString(DES_CBC.encryptMode(Tools.getEightStr("jiaofeib", " ") .getBytes(), Tools.getEightBeiStr(name," ").getBytes()));
		
		//2.��key����md5���ܲ���16���ַ�������JSON���м���ΪXXX2
        String  keyTmp=name+key.toString().trim();
		//���ȶ�src����md5����
		byte b[] = keyTmp.getBytes();
		MD5 d= new MD5();
	    byte [] r= d.getDigest(b);
	     //���md5�����ת��Ϊ16�����ַ���
	    String dataSec=Tools.getEightStr(MD5.byteArrayToHexString(r).toUpperCase(),"0");
		String jsonpass= MD5.byteArrayToHexString(DES_CBC.encryptMode(dataSec.getBytes(), Tools.getEightBeiStr(src," ").getBytes()));
		
		return servertype+"|"+namepass+"|"+jsonpass;
		
	}

	
	
	/**
	 * �Ե�½json�����н���
	 * @param key
	 * @param name
	 * @param src
	 * @param servertype
	 * @return
	 */
	public static String getLoginDecrypt(String key,String name,String src)
	{
		//��key����md5���ܲ���16���ַ�������JSON���н���ΪXXX2
        String  keyTmp=name+key.toString().trim() ;
		//���ȶ�src����md5����
		byte b[] = keyTmp.getBytes();
		MD5 d= new MD5();
	    byte [] r= d.getDigest(b);
	     //���md5�����ת��Ϊ16�����ַ���
	    String dataSec=Tools.getEightStr(MD5.byteArrayToHexString(r),"0").toUpperCase();
		String json= new String(DES_CBC.decryptMode(dataSec.getBytes(), Converts.hexStringToByte(src)));
		return json.trim();
	}
	
	public static String getNameDecrypt(String key,String namepass)
	{
		//1.�û���name��key��Կ���гɽ���XXX1
				String name= new String(DES_CBC.decryptMode(Tools.getEightStr(key, " ").getBytes(), Converts.hexStringToByte(namepass)));
				return name.trim();
	}

	
	
	public static void main(String args[])
	{
		String name="AAAA1111";
		String key="jiaofeib";
		String srcjson="json";
		String servertype="0001";
	
		//���ܺ���
	    String jsonpass=	Mac.getLoginEncryt(key, name, srcjson, servertype);
	    System.out.println(jsonpass);
		
	    String [] jsonreslut=jsonpass.split("\\|");
	    String resultname=Mac.getNameDecrypt(key, jsonreslut[1]);
	    System.out.println(resultname);
	    
	    String rsultjson=Mac.getLoginDecrypt(key,resultname, jsonreslut[2]);
	    System.out.println(rsultjson);
	    
	}
	
}