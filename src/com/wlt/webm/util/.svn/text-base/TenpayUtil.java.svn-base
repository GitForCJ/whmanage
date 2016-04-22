package com.wlt.webm.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wlt.webm.ten.service.TenpayXmlPath;

public class TenpayUtil {
	
	/**
	 * 商户号
	 */
	TenpayXmlPath test = new TenpayXmlPath();
	static String bargainor_id = "1217952001";	
	static String XINSHENG_ID = "wetetetwyt646567525256";	
	static String QB_ID="25689poug789000";
	
	/**
	 * 生成腾讯订单号
	 * @param currTime 
	 * @return transaction_id
	 */
	public static String get28Chars(String currTime){
		//8位日期
		String strDate = currTime.substring(0, 8);
		//6位时间
		String strTime = currTime.substring(8, currTime.length());
		//四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		//10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		//财付通交易单号，规则为：10位商户号+8位时间（YYYYmmdd)+10位流水号
		String transaction_id = bargainor_id + strDate + strReq;
		return transaction_id;
	}

	/**
	 * 内部订单号
	 * @return sp_billno
	 */
    public static String getSpbillion(){
    	String currTime = TenpayUtil.getCurrTime();
    	//六位随机数
		String strRandom = TenpayUtil.buildRandom(6) + "";
    	return currTime+strRandom;
    }
	
	
	/**
	 * 把对象转换成字符串
	 * @param obj
	 * @return String 转换成字符串,若对象为null,则返回空字符串.
	 */
	public static String toString(Object obj) {
		if(obj == null)
			return "";
		
		return obj.toString();
	}
	
	/**
	 * 把对象转换为int数值.
	 * 
	 * @param obj
	 *            包含数字的对象.
	 * @return int 转换后的数值,对不能转换的对象返回0。
	 */
	public static int toInt(Object obj) {
		int a = 0;
		try {
			if (obj != null)
				a = Integer.parseInt(obj.toString());
		} catch (Exception e) {

		}
		return a;
	}
	
	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 * @return String
	 */ 
	public static String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}
	
	/**
	 * 获取当前日期 yyyyMMdd
	 * @param date
	 * @return String
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String strDate = formatter.format(date);
		return strDate;
	}
	
	/**
	 * 取出一个指定长度大小的随机正整数.
	 * 
	 * @param length
	 *            int 设定所取出随机数的长度。length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}
	
	/**
	 * 获取编码字符集
	 * @param request
	 * @param response
	 * @return String
	 */
	public static String getCharacterEncoding(HttpServletRequest request,
			HttpServletResponse response) {
		
		if(null == request || null == response) {
			return "GBK";
		}
		
		String enc = request.getCharacterEncoding();
		if(null == enc || "".equals(enc)) {
			enc = response.getCharacterEncoding();
		}
		
		if(null == enc || "".equals(enc)) {
			enc = "GBK";
		}
		
		return enc;
	}
	
	/**
	 * 获取unix时间，从1970-01-01 00:00:00开始的秒数
	 * @param date
	 * @return long
	 */
	public static long getUnixTime(Date date) {
		if( null == date ) {
			return 0;
		}
		
		return date.getTime()/1000;
	}
	
	/**
	 * 获取第二年的时间
	 * @return
	 */
	public static String getNextYearTime(){
	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.YEAR, 1);
		Date now = cal.getTime();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}
	
	/**
	 * 元转成分
	 * @param str
	 * @return
	 */
	public static int yuanToFen(String str){
		int   i ;
		float   sum   =   Float.parseFloat(str)*100;
	    i   =   (int)sum;
		System.out.println(i);
		return i;
	}
	
	
	/**
	 * 生成新生支付订单号
	 * @param currTime 
	 * @return transaction_id
	 */
	public static String getXinsChars(String currTime){
		//7位日期
		String strDate = currTime.substring(1, 8);
		//6位时间
		String strTime = currTime.substring(8, currTime.length());
		//四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		//10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		//财付通交易单号，规则为：11位商户号+7位时间（YYYYmmdd)+10位流水号
		String transaction_id = XINSHENG_ID + strDate + strReq;
		return transaction_id;
	}
	
	/**
	 * 生成Q币充值订单号
	 * @param currTime 
	 * @return transaction_id
	 */
	public static String getQBChars(String currTime){
		//7位日期
		String strDate = currTime.substring(2);
		//四位随机数
		String strRandom = TenpayUtil.buildRandom(5) + "";
		String transaction_id=strDate+strRandom+TenpayUtil.buildRandom(5)+TenpayUtil.buildRandom(2);
		return transaction_id;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args){
		System.out.println(getUnixTime(new Date()));
	}

}
