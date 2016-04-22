package com.ejet.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.FileInputStream;


/**
 * 工具类
 * Company：深圳市###EJET###有限公司
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 */
public class Tools {
  /**
   * 将ISO编码转换为GBK编码
   * @param output String  输入字符串
   * @return String  输出字符串
   */
  public static String decode(String output) {
    try {
      return new String(output.getBytes("ISO-8859-1"), "GBK");
    }
    catch (Exception ex) {
      return "";
    }
  }
  /**
   * 将ISO编码转换为GBK编码
   * @param output String  输入字符串
   * @return String  输出字符串
   */
  public static String decode(String output, String orgEncoding, String destEncoding) {
    try {
	    	if(destEncoding==null)
	    	{
	    		 return new String(output.getBytes(orgEncoding));
	    	}
    	
      return new String(output.getBytes(orgEncoding), destEncoding);
    }
    catch (Exception ex) {
      return "";
    }
  }

  /**
   * 将GBK编码转换为ISO编码
   * @param input String  输入字符串
   * @return String  输出字符串
   */
  public static String encode(String input) {
    try {
      return new String(input.getBytes("GBK"), "ISO-8859-1");
    }
    catch (Exception ex) {
      return "";
    }
  }

  /**
   * 格式化日期，格式如2006-07-09
   * @param date Date  日期
   * @return String  输出字符串
   */
  public static String formatDate(Date date) {
    if (date == null) {
      return "";
    }
    else {
      String disp = "";
      SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      disp = bartDateFormat.format(date);
      return disp;
    }
  }

  /**
   * 格式化日期，格式如2006-07-09 23:00:00
   * @param date Date  日期
   * @return String  输出字符串
   */
  public static String formatDate2(Date date) {
    if (date == null) {
      return "";
    }
    else {
      String disp = "";
      SimpleDateFormat bartDateFormat = new SimpleDateFormat(
          "yyyy-MM-dd HH:mm:ss");
      disp = bartDateFormat.format(date);
      return disp;
    }
  }

  /**
   * 格式化日期，将日期从yyyyMMddHHmmss转换为yyyy-MM-dd HH:mm:ss
   * @param datetime String
   * @return String
   */
  public static String formatDate3(String datetime) {
    try {
      SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
      Date date1 = df.parse(datetime);
      df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      datetime = df.format(date1);
    }
    catch (Exception ex) {
      datetime = "";
    }
    return datetime;
  }
  /**
   * 格式化日期，将日期从yyyyMMddHHmm转换为yyyy-MM-dd HH:mm
   * @param datetime String
   * @return String
   */
  public static String formatDate_3(String datetime) {
    try {
      SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
      Date date1 = df.parse(datetime);
      df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      datetime = df.format(date1);
    }
    catch (Exception ex) {
      datetime = "";
    }
    return datetime;
  }
  /**
   * 格式化日期，将日期从yyyyMMddHHmm转换为yyyy-MM-dd HH:mm
   * @param datetime String
   * @return String
   */
  public static String getPreviousDate(String datetime,String formatter) {
	  String str = "";
	  try {
    	 SimpleDateFormat sdf = new SimpleDateFormat(formatter);
         sdf.parse(datetime);
         Calendar cal = sdf.getCalendar();
         cal.add(cal.DATE, -1);
         str = sdf.format(cal.getTime());
    }
    catch (Exception ex) {
    }
    return str;
  }
  /**
   * 格式化日期，将日期从yyyy-MM-dd转换为yyyyMMdd
   * @param datetime String
   * @return String
   */
  public static String formatDate4(String datetime) {
    try {
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      Date date1 = df.parse(datetime);
      df = new SimpleDateFormat("yyyyMMdd");
      datetime = df.format(date1);
    }
    catch (Exception ex) {
      datetime = "";
    }
    return datetime;
  }

  /**
   * 格式化日期，将日期从yyyyMMdd转换为yyyy-MM-dd
   * @param datetime String
   * @return String
   */
  public static String formatDate5(String datetime) {
    try {
      SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
      Date date1 = df.parse(datetime);
      df = new SimpleDateFormat("yyyy-MM-dd");
      datetime = df.format(date1);
    }
    catch (Exception ex) {
      datetime = "";
    }
    return datetime;
  }

  /**
   * 格式化日期，将日期从yyyy-MM-dd HH:mm:ss转换为yyyyMMddHHmmss
   * @param datetime String
   * @return String
   */

  public static String formatDate6(String datetime) {
    try {
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      Date date1 = df.parse(datetime);
      df = new SimpleDateFormat("yyyyMMddHHmmss");
      datetime = df.format(date1);
    }
    catch (Exception ex) {
      datetime = "";
    }
    return datetime;
  }
  /**
   * 格式化日期，将日期从yyyy-MM-dd HH:mm转换为yyyyMMddHHmm
   * @param datetime String
   * @return String
   */

  public static String formatDate_6(String datetime) {
    try {
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      Date date1 = df.parse(datetime);
      df = new SimpleDateFormat("yyyyMMddHHmm");
      datetime = df.format(date1);
    }
    catch (Exception ex) {
      datetime = "";
    }
    return datetime;
  }

  /**
   * 格式化日期为yyyyMMddHHmmss格式
   * @param date Date
   * @return String
   */
  public static String formatDate7(Date date) {
    if (date == null) {
      return "";
    }
    else {
      String disp = "";
      SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
      disp = bartDateFormat.format(date);
      return disp;
    }
  }

  /**
   * 格式化日期，将日期从yyyyMMdd转换为yyyy-MM-dd
   * @param datetime String
   * @return String
   */
  public static String formatDate8(String datetime) {
    try {
      SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
      Date date1 = df.parse(datetime);
      df = new SimpleDateFormat("yyyy/MM/dd");
      datetime = df.format(date1);
    }
    catch (Exception ex) {
      datetime = "";
    }
    return datetime;
  }

  /**
   * 格式化double变量为字符串，格式为##################0.00
   * @param b double
   * @return String
   */
  public static String formatE(double b) {
    try {
      DecimalFormat df1 = new DecimalFormat("##################0.00");
      return df1.format(b);
    }
    catch (Exception ex) {
      return "";
    }
  }

  /**
   * 格式化float变量为字符串，格式为##################0.00
   * @param b double
   * @return String
   */
  public static String formatE(float b) {
    try {
      DecimalFormat df1 = new DecimalFormat("##################0.00");
      return df1.format(b);
    }
    catch (Exception ex) {
      return "";
    }
  }
  /**
   * 格式化float变量为字符串，格式为##################0.00
   * @param b double
   * @return String
   */
  public static String formatE2(float b) {
	  String result="";
    try {
      DecimalFormat df1 = new DecimalFormat("##################0.000");
      result= df1.format(b);
      String ss[]=result.split("\\.");
      String ss1=ss[0];
      String ss2=ss[1];
      String s21=ss2.substring(0, 2);
      String s22=ss2.substring(2, 3);
      if(Integer.parseInt(s22)>4){
    	  s21=(Integer.parseInt(s21)+1)+"";
    	  
    	  if(Integer.parseInt(s21)==100){
    		  s21="00";
    		  ss1=(Integer.parseInt(ss1)+1)+"";
    		  result=ss1+"."+s21;
    	  }else{
    		  if(Integer.parseInt(s21)<10){
    			  s21="0"+s21;
    		  }
    		  result=ss1+"."+s21;
    	  }
      }else{
    	  result=ss1+"."+s21;
      }
      return result;
    }catch (Exception ex) {
      return "";
    }
  }


  /**
   * 将字符串根据分隔符分解成字符数组
   * @param value String  字符串
   * @param compart String  分隔符
   * @return Vector
   */
  public static Vector formatString(String value, String compart) {
    int index = 0;
    int i = 0;
    Vector vc = new Vector();
    while ( (index = value.indexOf(compart)) != -1) {
      vc.add(value.substring(0, index));
      i++;
      value = value.substring(index + 1);
    }
    return vc;
  }
  /**
   * 算出百分比
   * @param str1 String  字符串
   * @param str2 String  分隔符
   * @return String
   */
  public static String formatPercent(String str1, String str2) {
	  if(str1.equals("0")){
			return "0％";
		}
	    double t1 = Double.parseDouble(str1);
	    double t2 = Double.parseDouble(str2);
	    String tt="0";
	    tt=100*(t1/t2)+"0000.00000";
	    String []comm=tt.split("\\.");
	    String comm1=comm[0];
	    String comm2=comm[1].substring(0, 1);
		String comm3=comm[1].substring(1, 2);
		String comm4=comm[1].substring(2, 3);
	    if(Integer.parseInt(comm4)>=5){
			comm3=(Integer.parseInt(comm3)+1)+"";
			if(Integer.parseInt(comm3)==10){
				comm3="0";
				comm2=(Integer.parseInt(comm2)+1)+"";
				if(Integer.parseInt(comm2)==10){
					comm2="0";
					comm1=(Integer.parseInt(comm1)+1)+"";
				}
			}
		}
	    if(comm3.equals("0")){
	    	if(comm2.equals("0")){
	    		if(comm1.equals("0")){
	    			tt="0.01％";
	    		}else{
	    			tt=comm1+"％";
	    		}
	    	}else{
	    			tt=comm1+"."+comm2+"％";
	    	}
	    }else{
	    	tt=comm1+"."+comm2+comm3+"％";
	    }
	    return tt;
  }
  /**
   * 算出百分比
   * @param str1 String  数字字符串
   * @param str21 String  数字字符串
   * @param str22 String  数字字符串
   * @return String
   */
  public static String formatPercent(String str1, String str21,String str22) {
	if(str1.equals("0")){
		return "0％";
	}
    double t1 = Double.parseDouble(str1);
    double t2 = Double.parseDouble(str21)+Double.parseDouble(str22);
    String tt="0";
    tt=100*(t1/t2)+"0000.00000";
    String []comm=tt.split("\\.");
    String comm1=comm[0];
    String comm2=comm[1].substring(0, 1);
	String comm3=comm[1].substring(1, 2);
	String comm4=comm[1].substring(2, 3);
    if(Integer.parseInt(comm4)>=5){
		comm3=(Integer.parseInt(comm3)+1)+"";
		if(Integer.parseInt(comm3)==10){
			comm3="0";
			comm2=(Integer.parseInt(comm2)+1)+"";
			if(Integer.parseInt(comm2)==10){
				comm2="0";
				comm1=(Integer.parseInt(comm1)+1)+"";
			}
		}
	}

    if(comm3.equals("0")){
    	if(comm2.equals("0")){
    		if(comm1.equals("0")){
    			tt="0.01％";
    		}else{
    			tt=comm1+"％";
    		}
    	}else{
    			tt=comm1+"."+comm2+"％";
    	}
    }else{
    	tt=comm1+"."+comm2+comm3+"％";
    }
    return tt;
  }
  /**
   * 获得备份路径
   * @return String
   */
  public static String getBackup() {
    return File.separator + "app" + File.separator + "iservice_server" +
        File.separator + "backup";
  }

  /**
   * 获得EXCEL文件生成路径
   * @return String
   */
  public static String getExcelServer() {
    return System.getProperty("catalina.home") + File.separator + "webapps" +
        File.separator + "iservice_smp_client";
  }

  /**
   * 获得按照起始和终止时间，获得所有的日期
   * @param startTime String
   * @param endTime String
   * @return Vector
   */
  public static Vector getMonth(String startTime, String endTime) {
    Vector vcMonth = new Vector();
    try {
      SimpleDateFormat startDF = new SimpleDateFormat("yyyyMMddHHmmss");
      startDF.parse(startTime);
      Calendar startCal = startDF.getCalendar();
      SimpleDateFormat endDF = new SimpleDateFormat("yyyyMMddHHmmss");
      endDF.parse(endTime);
      Calendar endCal = endDF.getCalendar();
      vcMonth.add(startTime);
      startCal.set(Calendar.DAY_OF_MONTH, 1);
      startCal.set(Calendar.HOUR_OF_DAY, 0);
      startCal.set(Calendar.MINUTE, 0);
      startCal.set(Calendar.SECOND, 0);
      startCal.add(Calendar.MONTH, 1);
      startCal.add(Calendar.SECOND, -1);
      while (endCal.after(startCal)) {
        vcMonth.add(formatDate7(startCal.getTime()));
        vcMonth.add(Integer.toString(startCal.get(2) + 1));
        startCal.add(Calendar.SECOND, 1);
        vcMonth.add(formatDate7(startCal.getTime()));
        startCal.add(Calendar.MONTH, 1);
        startCal.add(Calendar.SECOND, -1);
      }
      vcMonth.add(endTime);
      vcMonth.add(Integer.toString(endCal.get(2) + 1));
    }
    catch (Exception ex) {
      vcMonth = new Vector();
    }
    return vcMonth;
  }

  /**
   * 获得当天时间，格式为yyyy-MM-dd HH:mm:ss
   * @return String
   */
  public static String getNow() {
    Date d = new Date();
    String str = "";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    str = formatter.format(d);
    return str;
  }

  /**
   * 获得当天日期，格式为yyyy-MM-dd
   * @return String
   */
  public static String getNow2() {
    Date d = new Date();
    String str = "";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    str = formatter.format(d);
    return str;
  }

  /**
   * 获得当天时间，格式为yyyyMMddHHmmss
   * @return String
   */
  public static String getNow3() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String strDate = dateFormat.format(new Date());
    return strDate;
  }

  /**
   * 获得当天日期，格式为yyyyMMdd
   * @return String
   */
  public static String getNow4() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    String strDate = dateFormat.format(new Date());
    return strDate;
  }
  /**
   * 获得当天日期，格式为yyMM
   * @return String
   */
  public static String getNowMonth() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
    String strDate = dateFormat.format(new Date());
    return strDate;
  }

  /**
   * 获得前一天的时间
   * @return String
   */
  public static String getPreviousDate() {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1);
    String str = "";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    str = formatter.format(cal.getTime());
    return str;
  }
  /**
   * 获得前一天的时间
   * @return String
   */
  public static String getPreviousDate(String matter) {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1);
    String str = "";
    SimpleDateFormat formatter = new SimpleDateFormat(matter);
    str = formatter.format(cal.getTime());
    return str;
  }

  /**
   * 格式化金额
   * @param acc String
   * @return String
   */
  public static String formatAcc(String acc) {
    //如果是0，直接返回
    if (Float.parseFloat(acc) == 0) {
      return "零元";
    }

    int length = acc.length();
    String upper = "";
    double fee = Double.parseDouble(acc);
    if (fee % 10000D == 0.0D) {
      for (int i = length - 1; i >= 0; i--) {
        if (i <= 0) {
          char array[] = new char[1];
          array[0] = acc.charAt(i);
          if (Integer.parseInt(new String(array)) != 0) {
            upper = formatNum(Integer.parseInt(new String(array))) +
                formatIndex(length - i) + upper;
          }
        }
      }
      upper = upper + "元整";
    }
    else
    if (fee % 1000D == 0.0D) {
      for (int i = length - 1; i >= 0; i--) {
        if (i <= 1) {
          char array[] = new char[1];
          array[0] = acc.charAt(i);
          if (Integer.parseInt(new String(array)) != 0) {
            upper = formatNum(Integer.parseInt(new String(array))) +
                formatIndex(length - i) + upper;
          }
        }
      }
      upper = upper + "元整";
    }
    else
    if (fee % 100D == 0.0D) {
      for (int i = length - 1; i >= 0; i--) {
        if (i <= 2) {
          char array[] = new char[1];
          array[0] = acc.charAt(i);
          if (Integer.parseInt(new String(array)) != 0) {
            upper = formatNum(Integer.parseInt(new String(array))) +
                formatIndex(length - i) + upper;
          }
        }
      }
      upper = upper + "元整";
    }
    else
    if (fee % 10D == 0.0D) {
      for (int i = length - 1; i >= 0; i--) {
        if (i <= 3) {
          if (i != length - 3) {
            char array[] = new char[1];
            array[0] = acc.charAt(i);
            if (Integer.parseInt(new String(array)) != 0) {
              upper = formatNum(Integer.parseInt(new String(array))) +
                  formatIndex(length - i) + upper;
            }
          }
        }
      }
      upper = upper + "元整";
    }
    else
    if (fee % 1.0D == 0.0D) {
      for (int i = length - 1; i >= 0; i--) {
        if (i <= 4) {
          if (i != length - 3) {
            char array[] = new char[1];
            array[0] = acc.charAt(i);
            if (Integer.parseInt(new String(array)) != 0) {
              upper = formatNum(Integer.parseInt(new String(array))) +
                  formatIndex(length - i) + upper;
            }
          }
        }
      }
      upper = upper + "整";
    }
    else {
      for (int i = length - 1; i >= 0; i--) {
        if (i != length - 3) {
          char array[] = new char[1];
          array[0] = acc.charAt(i);
          upper = formatNum(Integer.parseInt(new String(array))) +
              formatIndex(length - i) +
              upper;
        }
      }
    }
    return upper;
  }

  /**
   * 格式化序号
   * @param index int
   * @return String
   */
  private static String formatIndex(int index) {
    switch (index) {
      case 1: // '\001'
        return "分";

      case 2: // '\002'
        return "角";

      case 3: // '\003'
        return "";

      case 4: // '\004'
        return "元";

      case 5: // '\005'
        return "拾";

      case 6: // '\006'
        return "佰";

      case 7: // '\007'
        return "仟";

      case 8: // '\b'
        return "万";
    }
    return "";
  }

  /**
   * 格式化数字，获得大写
   * @param num int  数字
   * @return String
   */
  private static String formatNum(int num) {
    switch (num) {
      case 0:
        return "零";

      case 1:
        return "壹";

      case 2:
        return "贰";

      case 3:
        return "叁";

      case 4:
        return "肆";

      case 5:
        return "伍";

      case 6:
        return "陆";

      case 7:
        return "柒";

      case 8:
        return "捌";

      case 9:
        return "玖";
    }
    return "";
  }
  /**
	 * 函数功能：获得当前日期
	 * 
	 * @return 返回当前日期的字符变量，如"120000"
	 */
	public static String getNowTime() {
		Date d = new Date();
		String str = "";
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
		str = formatter.format(d);
		return str;
	}
  /**
   * 获得当前月份
   * @return String 格式：200705
   */
  public static String getMonth() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
    String strDate = dateFormat.format(new Date());
    return strDate;
  }

  /**
   * 获得当前月份
   * @return String 格式：2007-05
   */
  public static String getMonth2() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
    String strDate = dateFormat.format(new Date());
    return strDate;
  }

  /**
   * 获得该月的第一天日期和最后一天日期
   * @param year int 年份
   * @param month int  月份
   * @return String[]
   */
  public static String[] getMonth(int year, int month) {
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    String[] dates = new String[2];
    Calendar startCal = Calendar.getInstance();
    startCal.set(year, month - 1, 1);
    dates[0] = format.format(startCal.getTime());

    Calendar endCal = Calendar.getInstance();
    endCal.set(year, month, 1);
    endCal.add(Calendar.DATE, -1);
    dates[1] = format.format(endCal.getTime());
    return dates;
  }
  
  /**
   * 由日期获取日期所在月份
   * @param date    日期变量，如2003-3-3
   * @param type  (1表示当前日期的字符串格式,如"200612" ,2表示当前日期的日期格式,如"2006-12")
   * @return 月份
   */
  public static String formatMonth(Date date,int type)
  {
    if (date == null)
    {
      return "";
    }
    SimpleDateFormat bartDateFormat = null;
    if(type == 1){
      bartDateFormat = new SimpleDateFormat("yyyyMM");
    }
    if(type == 2){
      bartDateFormat = new SimpleDateFormat("yyyy-MM");
    }
    String disp = bartDateFormat.format(date);
    return disp;
  }
  
  /**
   * 获得两个日期之间的所有月份；
   * @param startdate  起始日期
   * @param enddate  截止日期
   * @return  两个月份之间的所有月份
   */
  public static List getAllMonth(String startdate, String enddate)
  {
	 String startMon = startdate.substring(0, 6);
     String endMon= enddate.substring(0, 6);
    List dateList = new ArrayList();
    if (startMon == null || startMon.length() == 0 ||
        endMon == null || endMon.length() == 0)
    {
      return dateList;
    }

    try
    {
      SimpleDateFormat startDF = new SimpleDateFormat("yyyyMM");
      startDF.parse(startMon);
      Calendar startCal = startDF.getCalendar();
      SimpleDateFormat endDF = new SimpleDateFormat("yyyyMM");
      endDF.parse(endMon);
      Calendar endCal = endDF.getCalendar();

      while (startCal.before(endCal))
      {
        dateList.add(formatMonth(startCal.getTime(),1));
        startCal.add(Calendar.MONTH, 1);
      }
      dateList.add(endMon);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return dateList;
  }
  
  /**
   * 获得两个月之间的所有月份；
   * @param startMon  起始月份
   * @param endMon  截止月份
   * @return  两个月份之间的所有月份
   */
  public static List getAllMonths(String startMon, String endMon)
  {
    List dateList = new ArrayList();
    if (startMon == null || startMon.length() == 0 ||
        endMon == null || endMon.length() == 0)
    {
      return dateList;
    }

    try
    {
      SimpleDateFormat startDF = new SimpleDateFormat("yyyyMM");
      startDF.parse(startMon);
      Calendar startCal = startDF.getCalendar();
      SimpleDateFormat endDF = new SimpleDateFormat("yyyyMM");
      endDF.parse(endMon);
      Calendar endCal = endDF.getCalendar();

      while (startCal.before(endCal))
      {
        dateList.add(formatMonth(startCal.getTime(),1));
        startCal.add(Calendar.MONTH, 1);
      }
      dateList.add(endMon);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return dateList;
  }
  
  /**
   * 根据起止日期获得各个需要统计的计费月度
   * @param startdate 起始日期
   * @param enddate  终止日期
   * @return 月份列表
   */
  public static List getMonths(String startdate, String enddate)
  {
    List monthList = new ArrayList();
    try
    {
      startdate = formatDate4(startdate);
      enddate =  formatDate4(enddate);

      SimpleDateFormat startDF = new SimpleDateFormat("yyyyMMdd");
      startDF.parse(startdate);
      Calendar startCal = startDF.getCalendar();

      SimpleDateFormat endDF = new SimpleDateFormat("yyyyMMdd");
      endDF.parse(enddate);
      Calendar endCal = endDF.getCalendar();

      //判断起始日期的所属财务月度
      startCal.set(Calendar.DAY_OF_MONTH, 1);
      startCal.add(Calendar.MONTH, 1);

      //加入所有的财务月度
      while (endCal.after(startCal)) {
        monthList.add(Integer.toString(startCal.get(Calendar.MONTH) + 1));
        startCal.add(Calendar.MONTH, 1);
      }
      monthList.add(Integer.toString(endCal.get(Calendar.MONTH) + 1));
    }
    catch (Exception ex)
    {
      monthList = null;
    }
    return monthList;
  }
  /**
   * 获得从2007年至当前年的前或后N（根据参数定制）年的所有年份
   * @param num 距离当年的一个数，例：1表示当年的下年，-2表示前年。
   * @return 年份数组
   */
  private static String[] getToCurYearsWithNum(int num)
  {
      int startYear = 2007;
      Calendar c = Calendar.getInstance();
      int curYear = c.get(Calendar.YEAR) + num;
      String[] years = new String[curYear - startYear + 1];
      for (int i = startYear, j = 0; i <= curYear; i++, j++)
      {
          years[j] = "" + i;
      }
      return years;
  }
  
  /**
   * 获得从2007至当前年的所有年份
   * @return String[] 2007至当前年的所有年份
   */
  public static String[] getToCurYears()
  {
      return getToCurYearsWithNum(0);
  }
  
  /**
   * 获得所有的两位和正常数字对应的12个月份。
   * @return 所有月份，例如：String[]{ { "01", "1" }, { "02", "2" }, ... { "12", "12" } }
   */
  public static String[][] getMonths()
  {
      String[][] months = new String[12][2];

      for (int i = 1, j = 0; i <= 12; i++, j++)
      {
          months[j][0] = i < 10 ? "0" + i : "" + i;
          months[j][1] = "" + i;
      }

      return months;
  }
  
  /**
   * 获得上个计费月度
   * @return 上个计费月度
   */
  public static String getPreFeeMonth()
  {
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.DATE,1);
    cal.add(Calendar.MONTH,-1);
    return formatMonth(cal.getTime(),1);
  }

  /**
   * 根据输入年月获取其上一个月份(YYYYMM)
   * @param yearMonth 年月
   * @return 日期(YYYYMM)
   */
  public static String getPreMonth(String yearMonth)
  {
    int year = Integer.parseInt(yearMonth.substring(0, 4));
    int month = Integer.parseInt(yearMonth.substring(4, 6));
    Calendar c = Calendar.getInstance();
    c.set(year, month-1,1);
    c.add(Calendar.DATE, -1);
    String mon;
    if(c.get(Calendar.MONTH)+1<10){
    	mon="0"+String.valueOf(c.get(Calendar.MONTH)+1);
    }
    else
    	mon=String.valueOf(c.get(Calendar.MONTH)+1);
    
    return  String.valueOf(c.get(Calendar.YEAR))+mon;
    
  }
  /**
   * 获得上月的最后一天和该月最后一天日期
   * @param year int 年份
   * @param month int  月份
   * @return String[]
   */
  public static String[] getMonth2(int year, int month) {
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    String[] dates = new String[2];
    Calendar startCal = Calendar.getInstance();
    startCal.set(year, month - 1, 1);
    startCal.add(Calendar.DATE, -1);
    dates[0] = format.format(startCal.getTime());

    Calendar endCal = Calendar.getInstance();
    endCal.set(year, month, 1);
    endCal.add(Calendar.DATE, -1);
    dates[1] = format.format(endCal.getTime());
    return dates;
  }
  /**
   * 获得前一天的日期
   * @return   返回前一天日期的字符串变量，如"20030711"
   */
  public static String getlastDate() {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -1);
    String str = "";
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    str = formatter.format(cal.getTime());
    return str;
  }

  /**
   * 获得系统前一个礼拜的起始日期和终止日期
   * @return   数组第一个分素是起始日期，第二个是终止日期
   */
  public static String[] getWeek() {
    String[] dates = new String[2];
    Calendar cal = Calendar.getInstance();
    int day = cal.get(Calendar.DAY_OF_WEEK);
    if (day == 1) {
      cal.add(Calendar.DATE, -7);
    }
    else {
      cal.add(Calendar.DATE, - (day - 1));
    }
    dates[1] = formatDate(cal.getTime());
    cal.add(Calendar.DATE, -6);
    dates[0] = formatDate(cal.getTime());
    return dates;
  }
  /**
   * 获得两个日期之间的天数日期格式为yyyy-MM-dd
   * @param startTime 起始日期
   * @param endTime 结束日期
   * @return  int
   */
  public static int getDateFromTime1ToTime2(String startTime,String endTime){ 
      SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
      Date dt1 = null;
      Date dt2 = null;
      int checkDays=0;
	try {
		dt1 = formatter.parse(endTime);
		dt2=formatter.parse(startTime);
		long l = dt1.getTime() - dt2.getTime();
	      l = (long)(l / (1000 * 60 * 60 *24) + 0.5); 
	       checkDays = Integer.parseInt(String.valueOf(l));
	} catch (ParseException e) {
        return checkDays;
	}
    return checkDays;
  }

  /**
   * 获得上一个自然月的起始日期和终止日期
   * @return  数组第一个分素是起始日期，第二个是终止日期
   */
  public static String[] getlastMonth() {
    String[] dates = new String[2];
    Calendar cal = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();
    cal.set(Calendar.DATE, 1);
    cal.add(Calendar.MONTH, -1);
    cal2.set(Calendar.DATE, 1);
    cal2.add(Calendar.DATE, -1);
    dates[0] = formatDate(cal.getTime());
    dates[1] = formatDate(cal2.getTime());
    return dates;
  }
  /**
   * 获得收费日期
   * @return String
   */
  public static String getOptTime() {
    Calendar cal = Calendar.getInstance();
    String year = Integer.toString(cal.get(Calendar.YEAR));
    String month = Integer.toString(cal.get(Calendar.MONTH) + 1);
    String day = Integer.toString(cal.get(Calendar.DATE));
    if (month.length() == 1) {
      month = "0" + month;
    }
    if (day.length() == 1) {
      day = "0" + day;
    }
    return year + "年" + month + "月" + day + "日";
  }

  /**
   * 获得自动停复机系统IP
   * @return String
   */
  public static String getAutoIP() {
    String autoIP = "";
    try {
      Properties a = new Properties();
      FileInputStream fis = new FileInputStream(System.getProperty(
          "catalina.home") + File.separator + "iservice.properties");
      a.load(fis);
      autoIP = a.getProperty("AutoIP");
      fis.close();
    }
    catch (Exception e) {
    }
    return autoIP;
  }

  /**
   * 获得自动停复机系统端口号
   * @return int
   */
  public static int getAutoPort() {
    String autoPort = "0";
    try {
      Properties a = new Properties();
      FileInputStream fis = new FileInputStream(System.getProperty(
          "catalina.home") + File.separator + "iservice.properties");
      a.load(fis);
      autoPort = a.getProperty("AutoPort");
      fis.close();
    }
    catch (Exception e) {
      autoPort = "0";
    }
    return Integer.parseInt(autoPort);
  }
  /**
   * 日期加减
   * @param offset 日期间隔天数
   * @return 日期（yyyy-MM-dd）
   */
  public static String changeDay( int offset) {
	  Calendar calendar = Calendar.getInstance();
	  Date date=calendar.getTime();
      calendar.setTime(date);
      calendar.set(Calendar.DAY_OF_YEAR,
                   (calendar.get(Calendar.DAY_OF_YEAR) + offset));
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      String day = formatter.format(calendar.getTime());
      return day;
  }
  /**
	 * 系统提示错误编码和含义对应
	 * @param code	错误编码
	 * @return	含义
	 */
	public static String checkError(String code){
		HashMap hm=new HashMap();
		hm.put("00", "交易成功!");
		hm.put("01", "交易失败,联系发卡方!");
		hm.put("02", "无效卡,卡未登记!");
		hm.put("03", "没收卡!");
		hm.put("04", "出错!");
		hm.put("05", "无效交易,请重试！");
		hm.put("06", "无效金额，请重试！");
		hm.put("07", "无效卡号！");
		hm.put("08", "无效物理卡号！");
		hm.put("09", "格式错误，交易失败，请重试！");
		hm.put("10", "过期的卡！");
		hm.put("11", "黑名单内卡");
		hm.put("12", "余额不足，请查询！");
		hm.put("13", "积分不足，请重试！");
		hm.put("14", "卡金额和账户不符！");
		hm.put("15", "需要重新签到！");
		hm.put("16", "终端未登记！");
		hm.put("17", "总账不平！");
		hm.put("18", "MAC校验错误，系统故障！");
		hm.put("99", "其他错误！");
		hm.put("91", "卡已经售完！");
		hm.put("81", "账单无欠费！");
		hm.put("61", "无交易！");
		hm.put("62", "交易取消！");
		hm.put("73", "数据库查询失败！");
		hm.put("75", "终端押金不足！");
		hm.put("53", "客户效验失败！");
		hm.put("63", "计费中心查询话费失败！");
		hm.put("64", "计费中心无此用户！");
		hm.put("65", "计费中心缴费失败！");
		hm.put("66","计费中心返销失败！");
		hm.put("74", "代理商押金不足！");
		hm.put("76", "终端无押金！");
		hm.put("83", "终端已经销户！");
		hm.put("84", "代理商已经销户！");
		hm.put("85", "该呼入号码不是绑定电话！");
		hm.put("86", "密码错误！");
		hm.put("87", "密码设置失败！");
		hm.put("88", "没有重新打印数据！");
		hm.put("92", "服务忙");
		hm.put("89", "计费中心连接错误！");
		hm.put("90", "处理中，处理失败！");
		hm.put("97", "供电尚未开工！");
		hm.put("21", "该户是欠费停电用户，缴费后须到供电办理复电手续");
		if(hm.containsKey(code.trim())){
			return hm.get(code.trim()).toString();
		}else{
			return "其他错误！";
		}
	}

    /**
     * 根据SCP的SQL语句获得SMP的SQL语句
     * @param scpSql SCP的SQL语句
     * @return SMP的SQL语句
     */
    public static String getSmpSql(CharSequence scpSql)
    {
        return scpSql.toString().replaceAll("ec_", "em_");
    }

    /**
     * 根据SMP的SQL语句获得SCP的SQL语句
     * @param smpSql SMP的SQL语句
     * @return SCP的SQL语句
     */
    public static String getScpSql(CharSequence smpSql)
    {
        return smpSql.toString().replaceAll("em_", "ec_");
    }

    /**
     * 生成字符串的MAC校验码
     * @param src 字符串 
     * @return MAC校验码
     */
    public static String getMac(String src){
        byte[] data = src.getBytes();
        ByteArrayOutputStream bout  = new ByteArrayOutputStream(); 
        ByteArrayInputStream input;
        byte[] mac = new byte[8];
        try{
            bout.write(data);

            int cover = data.length % 8;

            if(cover != 0){//如果不为8的倍数，则补位
                for(int i = 0; i <  (8 - cover); i ++){
                    bout.write(0xff);
                }
            }
            data = bout.toByteArray();
            input = new ByteArrayInputStream(data);
            bout = new ByteArrayOutputStream(); 
            int ennumber = data.length / 8;
            byte[] temp ;
            for(int i = 0; i <  ennumber; i ++){
                temp = new byte[8];
                input.read(temp);
                for(int j = 0; j <  8; j ++){
                    mac[j] = (byte)(mac[j] ^ temp[j]);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return new String(mac);
    }

    /**
     * 获得字符串的MAC校验码的ASCII码
     * @param src 字符串
     * @return ASCII码
     */
    public static String getMacAscii(String src)
    {
        String mac = getMac(src);
        StringBuffer ascii = new StringBuffer();
        for (int i = 0, ii = mac.length(); i < ii; i++)
        {
            ascii.append((int) mac.charAt(i));
        }
        return ascii.toString();
    }

    /**
     * 重复字符串规定的次数
     * @param s 要重复的字符串
     * @param num 重复的次数，必须大于0，为0时返回""。
     * @return 返回重复后的字符串
     */
    public static String repeat(String s, int num)
    {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < num; i++)
        {
            str.append(s);
        }
        return str.toString();
    }

  
    /**
     * 将序号格式化为字符串，不足位数在前面补0
     * @param sn int  序号
     * @param length int  字符串位数
     * @return String
     */
    public static String formatSn(long sn, int length) {
  	  String str = sn+"";
  	  String strSn = str;
  	  for (int i = 0; i < length - str.length(); i++) {
  		  strSn = "0" + strSn;
  	  }
  	  return strSn;
    }

    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
//    	String time = Tools.formatDate3("20081230165823");
//    	
//    	System.out.println(time);
//        System.out.println(time.substring(0, 10));
//        System.out.println(time.substring(11, time.length()));
//    	List monthList = getMonths("2008-01-01","2008-07-04");
    	List monthList = getAllMonth("2008-01-01","2008-07-04");
    	for(int i=0; i<monthList.size(); i++){
    		String str = (String) monthList.get(i);
    		System.out.println(str);
    	}
    	
    }
}
