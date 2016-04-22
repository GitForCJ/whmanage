package com.wlt.webm.business.bean.zdhuawei.fact;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;


/**
 * @author caiSJ
 * MD5加密<br>
 */
public class MD5Util {

    public static String zdwz_key="123456789012"; //#HUAWEI201601ALL56455ZD%3211TT10
	/**
	 * @param b
	 * @return string
	 */
	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	/**
	 * @param b
	 * @return string
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * @param origin
	 * @param charsetname
	 * @return resultString
	 */
	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	/**
	 * MD5字符串字符定义范围
	 */
	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

		public static void main(String[] args){
//			String sign="201505130950226100030000755868012345678901";
//			System.out.println("源串:"+sign);
//			String str=MD5Encode(sign, "UTF-8");
//			System.out.println("MD5后："+str);
//			System.out.println("236TWX778T8MV9F937GTQVKBB87VT2FY".length());
			HashMap<String, String> maps=new HashMap<String, String>();
			maps.put("appid","wxd930ea5d5a258f4f" );
			maps.put("mch_id","10000100" );
			maps.put("device_info", "1000");
			maps.put("body", "test");
			maps.put("nonce_str", "ibuaiVcKdpRxkhJA");
			String signsource="";
			Set<String> keys=maps.keySet();
			ArrayList<String> keysort=new ArrayList<String>(keys);
			Collections.sort(keysort);
			for(String str1:keysort){
				System.out.println(str1+":"+maps.get(str1));
				signsource+=str1+"="+maps.get(str1)+"&";
			}
			signsource+="key=192006250b4c09247ec02edce69f6a2d";
			System.out.println(signsource);
			String str=MD5Encode("appid=wxddb078438190b668&nonceStr=b3592b0702998592368d3b4d4c45873a&package=prepay_id=wx20151014092957dde955ee8e0893694140&signType=MD5&timeStamp=1444786200&key=86LyGv5u2LTrz240i2nM9duDRrMja4y4", "GBK");
			System.out.println(str.toUpperCase());
			
			System.out.println("====================");
			String miyao="123456789456";
			String yuan="00000002652015122114262384220151221142623";
			System.out.println("Md5源串:"+yuan+miyao);
			System.out.println("MD5结果:"+MD5Encode(yuan+miyao, "utf-8"));
			System.out.println();
			System.out.println("#HUAWEI201601ALL56455ZD%3211TT10".length());
		}//B39B0591E56C669A1C035C91D375DDDC
		
}