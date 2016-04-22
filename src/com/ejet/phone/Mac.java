package com.ejet.phone;


public class Mac {
	
	/**
	 * key密钥
	 * name 用户名
	 * src :json原始数据
	 * servertype：业务类型
	 * 对登陆信息进行加密 
	 * @return 业务类型|XXX1|XXX2
	 */
	/*public static String getLoginEncryt(String key,String name,String src,String servertype)
	{
		//1.用户名name用key密钥进行成密文XXX1
		String namepass= MD5.byteArrayToHexString(DES_CBC.encryptMode(Tools.getEightStr(key, " ") .getBytes(), Tools.getEightBeiStr(name," ").getBytes()));
		
		//2.用key进行md5加密产生16个字符串，对JSON进行加密为XXX2
        String  keyTmp=name+key.toString().trim();
		//首先对src进行md5计算
		byte b[] = keyTmp.getBytes();
		MD5 d= new MD5();
	    byte [] r= d.getDigest(b);
	     //获得md5结果后，转化为16进制字符串
	    String dataSec=Tools.getEightStr(MD5.byteArrayToHexString(r).toUpperCase(),"0");
		String jsonpass= MD5.byteArrayToHexString(DES_CBC.encryptMode(dataSec.getBytes(), Tools.getEightBeiStr(src," ").getBytes()));
		
		return servertype+"|"+namepass+"|"+jsonpass;
		
	}*/
	public static String getLoginEncryt(String key,String name,String src,String servertype)
	{
		//1.用户名name用key密钥进行成密文XXX1
		String namepass= MD5.byteArrayToHexString(DES_CBC.encryptMode(Tools.getEightStr("jiaofeib", " ") .getBytes(), Tools.getEightBeiStr(name," ").getBytes()));
		
		//2.用key进行md5加密产生16个字符串，对JSON进行加密为XXX2
        String  keyTmp=name+key.toString().trim();
		//首先对src进行md5计算
		byte b[] = keyTmp.getBytes();
		MD5 d= new MD5();
	    byte [] r= d.getDigest(b);
	     //获得md5结果后，转化为16进制字符串
	    String dataSec=Tools.getEightStr(MD5.byteArrayToHexString(r).toUpperCase(),"0");
		String jsonpass= MD5.byteArrayToHexString(DES_CBC.encryptMode(dataSec.getBytes(), Tools.getEightBeiStr(src," ").getBytes()));
		
		return servertype+"|"+namepass+"|"+jsonpass;
		
	}

	
	
	/**
	 * 对登陆json数进行解密
	 * @param key
	 * @param name
	 * @param src
	 * @param servertype
	 * @return
	 */
	public static String getLoginDecrypt(String key,String name,String src)
	{
		//用key进行md5加密产生16个字符串，对JSON进行解密为XXX2
        String  keyTmp=name+key.toString().trim() ;
		//首先对src进行md5计算
		byte b[] = keyTmp.getBytes();
		MD5 d= new MD5();
	    byte [] r= d.getDigest(b);
	     //获得md5结果后，转化为16进制字符串
	    String dataSec=Tools.getEightStr(MD5.byteArrayToHexString(r),"0").toUpperCase();
		String json= new String(DES_CBC.decryptMode(dataSec.getBytes(), Converts.hexStringToByte(src)));
		return json.trim();
	}
	
	public static String getNameDecrypt(String key,String namepass)
	{
		//1.用户名name用key密钥进行成解密XXX1
				String name= new String(DES_CBC.decryptMode(Tools.getEightStr(key, " ").getBytes(), Converts.hexStringToByte(namepass)));
				return name.trim();
	}

	
	
	public static void main(String args[])
	{
		String name="AAAA1111";
		String key="jiaofeib";
		String srcjson="json";
		String servertype="0001";
	
		//加密后结果
	    String jsonpass=	Mac.getLoginEncryt(key, name, srcjson, servertype);
	    System.out.println(jsonpass);
		
	    String [] jsonreslut=jsonpass.split("\\|");
	    String resultname=Mac.getNameDecrypt(key, jsonreslut[1]);
	    System.out.println(resultname);
	    
	    String rsultjson=Mac.getLoginDecrypt(key,resultname, jsonreslut[2]);
	    System.out.println(rsultjson);
	    
	}
	
}