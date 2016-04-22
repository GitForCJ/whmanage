package com.wlt.webm.business.bean.baibang;

import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.thoughtworks.xstream.XStream;
import com.wlt.webm.scputil.MD5Util;
import com.wlt.webm.util.MD5;

/**
 * 
 * @author 1989
 * 
 */
public class Md5AndBase64 {

	//测试
//	public final static String KEY = "#s^un2ye31<cn%|aoXpR,+vh";
	//正式
	public final static String KEY = "#s^un2ye31<cn%|aoXpR,+vhwhkj";
	
	/**
	 * MD5
	 * 
	 * @param source
	 * @param charsetname
	 * @return
	 */
	public static String md5Method(String source, String charsetname) {
		return MD5Util.MD5Encode(source, charsetname);
	}

	/**
	 * base64 加密
	 * 
	 * @param source
	 * @param charsetname
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String base64EN(String source, String charsetname)
			throws UnsupportedEncodingException {
		return Base64.encode(source.getBytes(charsetname));
	}

	/**
	 * 解码
	 * 
	 * @param source
	 * @param charsetname
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String base64DE(String source, String charsetname)
			throws UnsupportedEncodingException {
		return new String(Base64.decode(source), charsetname);
	}

	public static String obj2Xml(Object obj) {
		XStream xstream = new XStream();
		xstream.alias("root", obj.getClass());
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ xstream.toXML(obj);
	}

	/**
	 * 将QueryText应包含的字段组成源串A（不必区分排列顺序），字段之间以&分隔，参数名字和参数值之间用=分隔
	 * 在A后加上md5的key（key由财付通提供）得到串B，对B做md5得到Md5Sign（大写）
	 * 将Md5Sign缀到A后面得到串C，
	 * 对C串做BASE64加密得到D（使用GB2312）QueryText=D
	 * @param A
	 * @return D
	 * @throws UnsupportedEncodingException
	 */
	public static String encryptionQuerytext(String A)
			throws UnsupportedEncodingException {
		String B = A + "&Md5Key=" + KEY;
		String C = A + "&Md5Sign=" + md5Method(B, "GBK").toUpperCase();
		String D = base64EN(C, "gb2312");
		return D;
	}

	/**
	 * @param args
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public static void main(String[] args) throws UnsupportedEncodingException,
			JAXBException {
		// URLDecoder decoder =new URLDecoder();
		String key = "#s^un2ye31<cn%|aoXpR,+vh";
		String source = "VmVoaWNsZT3UwVk2Vzk4MyZWZWhpY2xlVHlwZT0yJkZyYW1lTm89MjU0Mzk5JkVuZ2luZU5vPSZPd25lck5hbWU9Jk1kNVNpZ249RTQwNzg4OEVCRjFCMkUwRDAxOUREREQyM0FDNTNEN0E=";
		System.out.println("base64 解码:" + base64DE(source, "gb2312"));
		// String md5sign=
		// "Vehicle=粤Y6W983&VehicleType=2&FrameNo=254399&EngineNo=&OwnerName=&Md5Key="
		// +key;
		// String md5sign=
		// "Attach=abcd&CftMerId=58&Command=2&MerchantId=1002&ResFormat=xml&UserId=EN1_A61E447C7FF746A960DAA70E609577A0&Vehicle=粤Y6W983&Version=1&Md5Key="
		// +key;
		String md5sign = "Attach=abcd&CftMerId=58&Command=2&MerchantId=1002&QueryText=VmVoaWNsZT3UwVk2Vzk4MyZWZWhpY2xlVHlwZT0yJkZyYW1lTm89MjU0Mzk5JkVuZ2luZU5vPSZPd25lck5hbWU9Jk1kNVNpZ249RTQwNzg4OEVCRjFCMkUwRDAxOUREREQyM0FDNTNEN0E=&ResFormat=xml&UserId=EN1_A61E447C7FF746A960DAA70E609577A0&Version=1&Md5Key=#s^un2ye31<cn%|aoXpR,+vh";
		System.out.println(md5Method(md5sign, "GBK"));

		XmlHead xml = new XmlHead("", "");
		XStream xstream = new XStream();
		xstream.alias("root", XmlHead.class);
		System.out.println(xstream.toXML(xml));
		String test = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><root><Command>2</Command><UserId></UserId><CftMerId></CftMerId><MerchantId></MerchantId><Version>1</Version><ResFormat>xml</ResFormat><Attach></Attach><Sign></Sign></root>";
		XmlHead xml1 = (XmlHead) xstream.fromXML(test);
		System.out.println("==" + xml1.getMerchantId().length());

	}

}
