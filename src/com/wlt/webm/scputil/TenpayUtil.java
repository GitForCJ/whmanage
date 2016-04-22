package com.wlt.webm.scputil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



/**
 * 工具处理类
 */
public class TenpayUtil {
	
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
	 * 获得昨天具体日期
	 * @return
	 */
	public static  String getYesterdayTime(){
		Calendar cal =Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		DateFormat format =new SimpleDateFormat("yyyyMMdd");
		return format.format(cal.getTime());
	}
	
	/**
	 * 获得昨天具体日期
	 * @return
	 */
	public static  String getYesterday(){
		Calendar cal =Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		DateFormat format =new SimpleDateFormat("yyyy-MM-dd");
		return format.format(cal.getTime());
	}
	
	
	/**
	 * 获取第二天凌晨0时指定分钟的时间
	 * @param hour 指定的小时数
	 * @param minute 指定的分钟数
	 * @return 第二天凌晨的时间
	 */
	public static Date getNextCheckDate(int hour, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, 0);
		Date date = cal.getTime();
		return date;
	}
	
	/**
	 * 获得当天的日期 yyyy-MM-dd
	 * @return
	 */
	public static String getDayTime(){
		DateFormat format =new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date());
	}
	
	
	/**
	 * use test
	 * @param args
	 */
    public static void main(String[] args){
    	System.out.println(TenpayUtil.getDayTime());
    	List list =new ArrayList();
    	String selectSql ="select date from ec_tenpaycheckinglog where filestate='1' and date<'"+TenpayUtil.getDayTime()+"'";
    	System.out.println(selectSql);
    }
}
