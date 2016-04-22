package com.wlt.webm.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBConfig;
import com.wlt.webm.db.DBService;
import com.wlt.webm.scputil.TenpayUtil;

public class Tools {   
	
	/**
	 * 获得账户日志流水号
	 * @param time
	 * @param userNo
	 * @return
	 */
	public static String getAccountSerial(String time,String userNo) {
		return time.substring(12)+userNo+TenpayUtil.buildRandom(5)+TenpayUtil.buildRandom(3);
	}
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
   * 分转化为元
   * @param fen 输入的字符串－分	
   * @return	String 输出的字符串－元
   */
  public static String fenToYuan(String fen){
  	//转化后的元
  	String yuan = "";
  	//字符串转化为整数
  	long fenInt = Long.parseLong(fen.trim());
  	//赋值为元
  	yuan = (fenInt / 100.00) + "";
  	//如果得到的元只包含尾数只包含一个数，则添加一个0
  	if(yuan.lastIndexOf(".") == (yuan.length() - 2)){
  		yuan += "0";
  	}
	
  	return yuan;
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
   * 获得当天日期，格式为yyyyMMdd
   * @return String
   */
  public static String getYesterd() {
		Date date = new Date();
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.DAY_OF_MONTH, -1);// 日期减1天
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		return sd.format(rightNow.getTime());
  }
	/**
	 * 获得昨日的日期
	 * 
	 * @return
	 */
	public static String calDate() {
		Date date = new Date();
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.DAY_OF_MONTH, -1);// 日期减1天
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		return sd.format(rightNow.getTime());
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

  
	public static String getFormatNowTime(String matter) {
		Date d = new Date();
		String str = "";
		SimpleDateFormat formatter = new SimpleDateFormat(matter);
		str = formatter.format(d);
		return str;
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
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
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
   * 获得下一天的日期
   * @return   返回前一天日期的字符串变量，如"20030711"
   */
  public static String getNextDate() {
	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, 1);
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
		hm.put("17", "总帐不平！");
		hm.put("18", "MAC校验错误，系统故障！");
		hm.put("99", "其他错误！");
		hm.put("91", "卡已经售完！");
		hm.put("81", "帐单无欠费！");
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
	 * 系统提示错误编码和含义对应
	 * @param code	错误编码
	 * @return	含义
	 */
  public static String checkErrors(String code){
		HashMap scprecode=new HashMap();
		scprecode.put("000", "交易成功");
		scprecode.put("001", "交易包格式错误");
		scprecode.put("002", "其他错误");
		scprecode.put("003", "处理中,交易被取消");
		scprecode.put("004", "该账户正在交费");
		scprecode.put("005", "MAC校验错误");
		scprecode.put("006", "系统处理失败");
		scprecode.put("007", "统计日期跨度必须小于两个月");
		scprecode.put("008", "系统繁忙，请稍后连接");
		scprecode.put("009", "加密机处理失败");
		scprecode.put("010", "系统锁定,无法交易");
		scprecode.put("011", "未定义的交易码");		
		scprecode.put("012", "加密机计算银联MAC错误");
		scprecode.put("013", "加密机转换PIN错误");
		scprecode.put("014", "无法找到MAC密钥");
		scprecode.put("015", "无法找到PIN密钥");
		scprecode.put("016", "计算PSAM卡MAC错误");
		scprecode.put("017", "解密二三磁道错误");
		scprecode.put("018", "验证银联PINKEY失败");
		scprecode.put("019", "验证银联MACKEY失败");		
		scprecode.put("020", "资金帐户不存在");
		scprecode.put("021", "资金帐户密码错误");
		scprecode.put("022", "资金帐户已经销户");
		scprecode.put("023", "资金帐户余额不足");
		scprecode.put("024", "此笔交易金额超过资金账户单笔消费最高金额");
		scprecode.put("025", "此次交易金额已超过资金账户日消费额度");
		scprecode.put("026", "资金帐户尚未设置额度限制");
		scprecode.put("027", "您的余额已不多，请及时充值");
		scprecode.put("030", "终端信息验证失败");
		scprecode.put("031", "终端已经销户");
		scprecode.put("032", "呼入号码不是绑定电话");		
		scprecode.put("033", "终端接入号码错误");
		scprecode.put("034", "PSAM卡号不存在");	
		scprecode.put("035", "向计费发送消息失败");	
		scprecode.put("036", "此类卡已售完");
		scprecode.put("037", "pos终端不存在");
		scprecode.put("038", "平台无卡数据");
		scprecode.put("039", "无重打印数据");
		scprecode.put("040", "订单已失效");  
		scprecode.put("041", "订单不存在");
		scprecode.put("042","该笔订单已支付");
		scprecode.put("043", "平台无代办信息");
		scprecode.put("044", "查无此流水号");
		scprecode.put("045", "缴费过于频繁,请稍后再缴");	
		scprecode.put("046", "查无数据");
		scprecode.put("047", "正在处理中，请勿重复缴费");	
		scprecode.put("050","保存PINKEY失败");
		scprecode.put("051","保存MACKEY失败");
		scprecode.put("070", "无可用记录");	
		scprecode.put("071", "非本地用户");	
		scprecode.put("072", "异常金额");	
		scprecode.put("073", "该用户帐单超过7个月,在银行无法正常缴费,请通知用户到联通营业厅缴费");	
		scprecode.put("074", "此银行未签订代收费协议,不允许代收联通话费");	
		scprecode.put("075", "该月发票已打印");	
		scprecode.put("076", "总帐不平");			
		scprecode.put("077", "总帐平");			
		scprecode.put("102", "没有欠费");
		scprecode.put("110", "输入号码不存在");
		scprecode.put("111", "用户密码错误");
		scprecode.put("112", "不能返销");
		scprecode.put("113", "不是指定的客户端");
		scprecode.put("114", "有多局编账号");
		scprecode.put("115", "返销流水不存在");
		scprecode.put("116", "没有要打印的单据");
		scprecode.put("117", "该笔流水已返销");
		scprecode.put("118", "该笔流水已销账");
		scprecode.put("200", "对账时总额核对不匹配");
		scprecode.put("222", "金额错误");
		scprecode.put("301", "交易失败,请联系发卡行");
		scprecode.put("302", "交易失败,请联系发卡行");
		scprecode.put("303", "商户未登记");
		scprecode.put("304", "没收卡,请联系收单行");
		scprecode.put("305", "交易失败,请联系发卡行");
		scprecode.put("306", "交易失败,请联系发卡行");
		scprecode.put("307", "没收卡,请联系收单行");
		scprecode.put("309", "交易失败,请重试");
		scprecode.put("312", "交易失败,请重试");
		scprecode.put("313", "交易金额超限请重试");
		scprecode.put("314", "无效卡号,请联系发卡行");
		scprecode.put("315", "此卡不能受理");
		scprecode.put("319", "交易失败,请联系发卡行");
		scprecode.put("320", "交易失败,请联系发卡行");
		scprecode.put("321", "交易失败,请联系发卡行");
		scprecode.put("322", "操作有误,请重试");
		scprecode.put("323", "交易失败,请联系发卡行");
		scprecode.put("325", "交易失败,请联系发卡行");
		scprecode.put("330", "交易失败,请重试");
		scprecode.put("331", "此卡不能受理");
		scprecode.put("333", "过期卡,请联系发卡行");
		scprecode.put("334", "没收卡,请联系收单行");
		scprecode.put("335", "没收卡,请联系收单行");
		scprecode.put("336", "此卡有误,请换卡重试");
		scprecode.put("337", "没收卡,请联系收单行");
		scprecode.put("338", "密码错次数超限");
		scprecode.put("339", "交易失败,请联系发卡行");
		scprecode.put("340", "交易失败,请联系发卡行");
		scprecode.put("341", "没收卡,请联系收单行");
		scprecode.put("342", "交易失败,请联系发卡行");
		scprecode.put("343", "没收卡,请联系收单行");
		scprecode.put("344", "交易失败,请联系发卡行");
		scprecode.put("351", "余额不足,请查询");
		scprecode.put("352", "交易失败,请联系发卡行");
		scprecode.put("353", "交易失败,请联系发卡行");
		scprecode.put("354", "过期卡,请联系发卡行");
		scprecode.put("355", "密码错,请重试");
		scprecode.put("356", "交易失败,请联系发卡行");
		scprecode.put("357", "交易失败,请联系发卡行");
		scprecode.put("358", "终端无效,请联系收单行或银联");
		scprecode.put("359", "交易失败,请联系发卡行");
		scprecode.put("360", "交易失败,请联系发卡行");
		scprecode.put("361", "金额太大");
		scprecode.put("362", "交易失败,请联系发卡行");
		scprecode.put("363", "交易失败,请联系发卡行");
		scprecode.put("364", "交易失败,请联系发卡行");
		scprecode.put("365", "超出取款次数限制");
		scprecode.put("366", "交易失败,请联系收单行或银联");
		scprecode.put("367", "没收卡");
		scprecode.put("368", "交易处理中,请重试");
		scprecode.put("375", "密码错次数超限");
		scprecode.put("377", "请向网络中心签到");
		scprecode.put("379", "POS终端重传脱机数据");
		scprecode.put("390", "交易失败,请稍后重试");
		scprecode.put("391", "交易失败,请稍后重试");
		scprecode.put("392", "交易失败,请稍后重试");
		scprecode.put("393", "交易失败,请联系发卡行");
		scprecode.put("394", "交易失败,请稍后重试");
		scprecode.put("395", "交易失败,请稍后重试");
		scprecode.put("396", "交易失败,请稍后重试");
		scprecode.put("397", "终端未登记,请联系收单行或银联");
		scprecode.put("398", "交易处理中,请重试");
		scprecode.put("399", "校验错,请重新签到");
		scprecode.put("3A0", "校验错,请重新签到");
		scprecode.put("666", "计费系统错误");
		scprecode.put("777", "要求对账的明细太多");
		if(scprecode.containsKey(code.trim())){
			return scprecode.get(code.trim()).toString();
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
     * 根据编码名称从SCP库表中获得对应的增长序列值，并用0在前面补全为固定长度的字符串。
     * @param code 序列名称
     * @param length 长度
     * @return 增长序列值
     * @throws SQLException
     */
    public static String getCodeSequence(String code, int length) throws SQLException
    {
        int sequence = 0;
        DBService db = null;
        try
        {
            db = new DBService();
            db.setAutoCommit(false);

            StringBuffer sql = new StringBuffer(100);
            sql.append("update ec_code_sequence set sequence=sequence+1 where code='").append(code).append("'");
            db.update(sql.toString());

            sql.delete(0, sql.length());
            sql.append("select sequence from ec_code_sequence where code='").append(code).append("'");
            sequence = db.getInt(sql.toString());

            db.commit();
        }
        catch (SQLException e)
        {
            db.rollback();
            throw e;
        }
        finally
        {
            db.close();
        }

        DecimalFormat df = new DecimalFormat(repeat("0", length));
        return df.format(sequence);
    }
    /**
     * 获得当天时间，格式为yyyyMMddHHmmss
     * @return String
     */
    public static String getNowMiss() {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
      String strDate = dateFormat.format(new Date());
      return strDate;
    }
    
    /**
     * 获得前一天的时间
     * @return String
     */
    public static String getPrevious2Date() {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DATE, -2);
      String str = "";
      SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
      str = formatter.format(cal.getTime());
      return str;
    }
    
	/**
	 * 用户真实ip
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getRemoteAddr();
		}
		String[] str = ip.split(",");
		if(str!=null && str.length>1){
		ip = str[0];
		}
		return ip;
	}
	
	public static  int buildRandom(int length) {
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

	public static Date getDate(Date date,int minute){
		if(date==null){
			date=new Date();
		}
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minute);
		return cal.getTime();
	}
	
	/**
	 * 判断是否为系统不允许交易时间
	 * @return
	 */
	public static boolean validateTime(){
		int time=Integer.parseInt(Tools.getNow3().substring(8, 14));
		int num1=235000;
		int num2=001000;
		if(time>=num1||time<=num2){
			return false;
		}else {
			return true;
		}
	}
	

	/**
	 * 图片转字符串
	 * urlStr 路径
	 *  将图片文件转化为字节数组字符串，并对其进行Base64编码处理
	 * @throws IOException 
	 */
	public static String getImageStr(String urlStr)
	{
		URL url;
		HttpURLConnection con=null;
		try {
			url = new URL(urlStr);
			con= (HttpURLConnection)url.openConnection();
			con.setConnectTimeout(100000);
			con.setReadTimeout(100000);
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.connect();//建立连接
			if(con.getResponseCode()!=HttpURLConnection.HTTP_OK){
				return null;
			}
		} catch (Exception e1) {
			Log.error("链接超时，，，，"+e1);
			return null;
		}
		
		InputStream in = null;
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();  
		byte[] data = null;
		try {
			in =con.getInputStream();
			int ch;  
			while ((ch = in.read()) != -1) 
			{  
				bytestream.write(ch);  
			}  
			data = bytestream.toByteArray();  
			bytestream.close();  
			in.close();
		} catch (IOException e) {
			Log.error("图片转字符异常"+e);
			return null;
		}
		finally{
			if(null!=con){
				con=null;
			}
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}
	/**
	* 将字符串转为图片
	* @param imgStr  图片字符串
	* @return null
	 * @throws IOException 
	*/
	public static void generateImage(String imgStr,HttpServletResponse response) throws IOException
	{// 对字节数组字符串进行Base64解码并生成图片
		BASE64Decoder decoder = new BASE64Decoder();
		InputStream in=null;
		BufferedOutputStream bout=null;
		try {
			// Base64解码
//			System.out.println(Tools.getImageStr(imgStr));
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) 
			{
				if (b[i] < 0) 
				{// 调整异常数据
					b[i] += 256;	
				}
			}
			 in= new ByteArrayInputStream(b);  
			 bout = new BufferedOutputStream(response.getOutputStream());  
		    byte c[] = new byte[1024];  
	        int len = in.read(c);  
	        while (len > 0) {  
	            bout.write(c, 0, len);  
	            len = in.read(c); 
	        }
		} 
		catch (Exception e)
		{
			Log.error("字符串转图片异常"+e);
		}
		finally
		{
			if(bout!=null)
				bout.close();  
			if(in!=null)
				in.close(); 
		}
	}
	
	/**
	 * 确认号码是否为揭阳潮汕的
	 * @param phone  手机号码
	 * @return true 是  false 否
	 */
	public static boolean isJC(String phone){
		String str=DBConfig.getInstance().getJcZone();
		if(str.indexOf(phone.substring(0,7))!=-1){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 万恒Q币 流水号生成
	 * @return
	 */
	public static String getGuangXinSerialNo(){
		return getNow3()+"0117"+((int)(Math.random()*100000)+100000);
	}
	
	/**
	 * 拆分非 1 5 10 20 30的数量  最多拆分成两笔
	 * @param qbNum
	 * @return
	 */
	public static int[] decomposeQB1(int qbNum){
         int[] rs=new int[2];
         int[] n={1,5,10,20,30};
         for(int k=0;k<5;k++){
        	 if(qbNum-n[k]<0){
        		 rs[0]=n[k-1];
        		 rs[1]=qbNum-n[k-1];
        		 break;
        	 }else{
        		 rs[0]=30;
        		 rs[1]=qbNum-30;
        	 }
         }
         return rs;
	}
	/**
	 * 拆分非 1 5 10 20 30的数量  最多拆分成两笔
	 * @param qbNum
	 * @return
	 */
	public static int[] decomposeQB(int qbNum){
         int[] rs=new int[2];
         if(qbNum>30){
        	 rs[0]=30;
        	 rs[1]=qbNum-30;
         }else if(qbNum<5){
           	 rs[0]=1;
        	 rs[1]=qbNum-1;
         }else{
        	 if(qbNum>=15&&qbNum<20){
        		 rs[0]=(qbNum/5-1)*5;
            	 rs[1]=qbNum-rs[0];
        	 }else if(qbNum>20){
        		 rs[0]=20;
            	 rs[1]=qbNum-rs[0];
        	 }else{
        		 rs[0]=qbNum/5*5;
        		 rs[1]=qbNum%5;
        	 }
         }
         return rs;
	}
	
	public static ArrayList<Integer> decomposeQB3(int qbNum){
        ArrayList<Integer> rs=new ArrayList<Integer>();
        int[] qbFee={1,5,10,20,30};
        for(int k=0;k<2;k++){
        for(int i=0;i<5;i++){
         if(qbNum-qbFee[i]>=0){
        	 rs.add(qbFee[i]);
        	 qbNum=qbNum-qbFee[i];
         }
        }
        }
        return rs;
	}
	public static void main(String args[]){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String str1 =formatter.format(cal.getTime());

		cal.add(Calendar.DATE, -1);
		String str2 = formatter.format(cal.getTime());

		cal.add(Calendar.DATE, -1);
		String str3 =formatter.format(cal.getTime());

		System.out.println(str1+"  "+str2+"   "+str3);
	}
}
