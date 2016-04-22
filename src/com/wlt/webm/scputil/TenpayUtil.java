package com.wlt.webm.scputil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



/**
 * ���ߴ�����
 */
public class TenpayUtil {
	
	/**
	 * �Ѷ���ת�����ַ���
	 * @param obj
	 * @return String ת�����ַ���,������Ϊnull,�򷵻ؿ��ַ���.
	 */
	public static String toString(Object obj) {
		if(obj == null)
			return "";
		
		return obj.toString();
	}
	
	/**
	 * �Ѷ���ת��Ϊint��ֵ.
	 * 
	 * @param obj
	 *            �������ֵĶ���.
	 * @return int ת�������ֵ,�Բ���ת���Ķ��󷵻�0��
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
	 * ��ȡ��ǰʱ�� yyyyMMddHHmmss
	 * @return String
	 */ 
	public static String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}
	
	/**
	 * ��ȡ��ǰ���� yyyyMMdd
	 * @param date
	 * @return String
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String strDate = formatter.format(date);
		return strDate;
	}
	
	/**
	 * ȡ��һ��ָ�����ȴ�С�����������.
	 * 
	 * @param length
	 *            int �趨��ȡ��������ĳ��ȡ�lengthС��11
	 * @return int �������ɵ��������
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
	 * ��ȡunixʱ�䣬��1970-01-01 00:00:00��ʼ������
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
	 * ��������������
	 * @return
	 */
	public static  String getYesterdayTime(){
		Calendar cal =Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		DateFormat format =new SimpleDateFormat("yyyyMMdd");
		return format.format(cal.getTime());
	}
	
	/**
	 * ��������������
	 * @return
	 */
	public static  String getYesterday(){
		Calendar cal =Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		DateFormat format =new SimpleDateFormat("yyyy-MM-dd");
		return format.format(cal.getTime());
	}
	
	
	/**
	 * ��ȡ�ڶ����賿0ʱָ�����ӵ�ʱ��
	 * @param hour ָ����Сʱ��
	 * @param minute ָ���ķ�����
	 * @return �ڶ����賿��ʱ��
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
	 * ��õ�������� yyyy-MM-dd
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
