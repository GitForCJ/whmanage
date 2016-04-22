package com.wlt.webm.scputil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * 日期与时间解析与转换类
 */
public class DateParser {
	/**
	 * 静态方法,将一个Date对象格式化为"yyyyMMdd"格式的字符串
	 * @param date 要格式化的日期对象
	 * @return String字符串,格式为"yyyyMMdd"
	 */
    public static String formatDate(Date date) {
        if(date == null) {
            return "";
        } 
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String str = dateFormat.format(date);
        
        return str;
    }
    
    /**
	 * 格式化时间
	 * @param date 日期字符串，如"20040711235958"
	 * @return 返回字符串变量，如"2004-07-11 23:59:58"
	 */
	public static String formatStringToTime(String dateTime) {
		if(dateTime.trim().equals(""))
			return "";
		dateTime = dateTime.substring(0,14);

		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); 
			Date date1 = df.parse(dateTime);
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateTime = df.format(date1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dateTime;
	}
	
    /**
	 * 格式化时间
	 * @param date 日期字符串，如"2004-07-11 23:59:58"
	 * @return 返回字符串变量，如"20040711235958"
	 */
	public static String formatDateTimeToString(String dateTime) {
		if(dateTime.trim().equals(""))
			return "";
		dateTime = dateTime.substring(0,19);

		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = df.parse(dateTime);
			df = new SimpleDateFormat("yyyyMMddHHmmss");
			dateTime = df.format(date1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dateTime;
	}
	/**
	 * 格式化时间
	 * @param date 日期字符串，如"2006-12-02 00:00:00.000"  
	 * @return 返回字符串变量，如"20061202000000"
	 */
	public static String formatTimeToString(String dateTime) {
		if(dateTime.trim().equals(""))
			return "";

		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date date1 = df.parse(dateTime);
			df = new SimpleDateFormat("yyyyMMddHHmmss");
			dateTime = df.format(date1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dateTime; 
	}
	
    
	/**
     * 静态方法,获得当前系统时间,精确到秒.以字符串形式返回
     * @return String字符串,格式为"yyyyMMddHHmmss"
     */
    public static String getNowDateTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String str = dateFormat.format(cal.getTime());
        
        return str;
    }
    /**
     * 交通罚款时间
     * @return String字符串,格式为"yyyyMMddHHmmss"
     */
    public static String getJtfkDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = dateFormat.format(new Date());
        
        return str;
    }
    /**
     * 静态方法,获得当前系统日期,以字符串形式返回
     * @return String字符串,格式为"yyyyMMdd"
     */
    public static String getNowDateTable() {
    	return getNowDateTime().substring(2, 6);
    }
    
	/**
     * 静态方法,获得当前系统日期,以字符串形式返回
     * @return String字符串,格式为"yyyyMMdd"
     */
    public static String getNowDate() {
    	return getNowDateTime().substring(0, 8);
    }

    /**
     * 静态方法，获得昨天的日期,以字符串形式返回
     * @return String字符串，格式为"yyyyMMdd"
     */
    public static String getYesterdayDate() {
        String today = getNowDate();
        String yesterday = getPreviousDate(today, -1);
        
        return yesterday;
    }

	/**
     * 静态方法,获得当前月份,以字符串形式返回
     * @return String字符串,格式为"yyyyMM"
     */
    public static String getNowMonth() {
        return getNowDateTime().substring(0, 6);
    }
    
	/**
     * 静态方法,获得给定月份的下一月,以字符串形式返回
     * @param month 既定月份,以此月份为基准进行查询.格式为"yyyyMM"
     * @return String字符串,格式为"yyyyMM"
     */
    public static String getNextMonth(String month) {
        String str = "";
        
        if(month.length()!=6) {
        	return "Error Format";
        }
        
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
            dateFormat.parse(month);
            Calendar tempCal = dateFormat.getCalendar();
            tempCal.add(Calendar.MONTH, 1);
            str = dateFormat.format(tempCal.getTime());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        return str;
    }
   
	/**
     * 静态方法,获得某一月份前几月或后几月的月份,以字符串形式返回
     * @param month 既定月份,以此月份为基准进行查询.格式为"yyyyMM"
     * @param n 在给定月份上加或减的月数.当n为正值时,将获得将来的第n个月的月份,
     * 当n为负值时,将获得过去的n个月前的月份
     * @return String字符串,格式为"yyyyMM"
     */
    public static String getPreMonth(String month, int n) {
        String str = "";
        
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
            formatter.parse(month);
            Calendar tempCal = formatter.getCalendar();
            tempCal.add(Calendar.MONTH, n);
            str = formatter.format(tempCal.getTime());
        } 
        catch(Exception e) {
            e.printStackTrace();
        }

        return str;
    }
    
	/**
     * 静态方法,获得某一月份最后一天的日期，以字符串形式返回
     * @param month 既定月份,以此月份为基准进行查询.格式为"yyyyMM"
     * @return String字符串,格式为"yyyyMMdd"
     */
    public static String getEndDayOfMonth(String month) {
        String date = "";
        
        if(month.length()!=6) {
        	return "Error Format";
        }
        
        String nextMonth = getNextMonth(month);
        String nextMonthFirstDay = nextMonth + "01";
        
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            formatter.parse(nextMonthFirstDay);
            Calendar tempCal = formatter.getCalendar();
            tempCal.add(Calendar.DATE, -1);
            date = formatter.format(tempCal.getTime());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        return date;
    }	
    
    /**
     * 静态方法,获得某一日期的前n天的日期
     * @param date 既定日期,以此日期为基准进行查询.格式为"yyyyMMdd"
     * @param n 在给定日期上加或减的天数.当n为正值时,将获得将来第n天的日期,
     * 当n为负值时,将获得过去n天前的日期
     * @return String字符串,格式为"yyyyMMdd"
     */
    public static String getPreviousDate(String date, int n) {
        String str = "";
        
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            formatter.parse(date);
            Calendar tempCal = formatter.getCalendar();
            tempCal.add(Calendar.DATE, n);
            str = formatter.format(tempCal.getTime());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        return str;
    }
    
    /**
     * 静态方法,查询从开始月份到结束月份之间的所有月份,包括开始月份和结束月份.
     * @param startMonth 查询的开始月份.格式为"yyyyMM"
     * @param endMonth 查询的结束月份.格式为"yyyyMM"
     * @return List数组,每个月份都以"yyyyMM"格式存放
     */
    public static List getAllMonth(String startMonth, String endMonth) {
		List months = new ArrayList();
		
		int start = Integer.parseInt(startMonth);
		int end = Integer.parseInt(endMonth);
		if(start > end) {
			return months;
		}
		
		String temp = startMonth;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
			
			while (!temp.equals(endMonth)) {
				months.add(temp);
				formatter.parse(temp);
				Calendar tempCal = formatter.getCalendar();
				tempCal.add(Calendar.MONTH, 1);
				temp = formatter.format(tempCal.getTime());
			}

			months.add(endMonth);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return months;
    }
    
    /**
     * 静态方法,查询开始月份与结束月份之间有多少月,包括开始月份和结束月份.
     * @param startMonth 查询的开始月份.格式为"yyyyMM"
     * @param endMonth 查询的结束月份.格式为"yyyyMM"
     * @return 开始月份到结束月份间月份的数目
     */
    public static int getMonthNum(String startMonth, String endMonth) {
    	List months = getAllMonth(startMonth, endMonth);
    	int num = months.size();
    	
    	return num;
    }
    
    /**
     * 静态方法,查询给定月份包含的所有日期
     * @param month 既定月份,以此月份为条件进行查询.格式为"yyyyMM"
     * @return List数组,所有日期都以"yyyyMMdd"格式存放
     */
    public static List getDatesOfMonth(String month) {
    	List list = new ArrayList();
    	String firstDay = month + "01";
        String lastDay = getEndDayOfMonth(month);
        
    	if(month==null||month.length()<6) {
    		return list;
    	}
    	
        list = getDate(firstDay, lastDay);
        
        return list;
    }

	/**
     * 静态方法,获得某指定月份有多少天
     * @param month 既定月份,以此月份为条件进行查询.格式为"yyyyMM"
     * @return 指定月份所包含的日期数目
     */
    public static int getMonthLength(String month) {
    	List days = getDatesOfMonth(month);
    	int num = days.size();
    	
    	return num;
    }
    
	/**
	 * 静态方法,查询开始日期与结束日期之间的所有日期,包括开始日期和结束日期.
	 * @param startDate 查询的开始日期.格式为"yyyyMMdd"
	 * @param endDate 查询的结束日期.格式为"yyyyMMdd"
	 * @return 需查询的日期,每个日期都以"yyyyMMdd"格式存放在List数组中
	 */
	public static List getDate(String startDate, String endDate) {
		List list = new ArrayList();
		
		if (startDate==null||startDate.length()!=8||endDate==null||endDate.length()!=8) {
			return list;
		}

		try {
			SimpleDateFormat sDateFomat = new SimpleDateFormat("yyyyMMdd");
			sDateFomat.parse(startDate);
			Calendar startCal = sDateFomat.getCalendar();
			
			SimpleDateFormat eDateFomat = new SimpleDateFormat("yyyyMMdd");
			eDateFomat.parse(endDate);
			Calendar endCal = eDateFomat.getCalendar();
			
			if (startCal.after(endCal)) {
				return list;
			}
			
			while (startCal.before(endCal)) {
				list.add(formatDate(startCal.getTime()));
				startCal.add(Calendar.DATE, 1);
			}
			
			list.add(endDate);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 静态方法,查询开始日期与结束日期之间有多少日,包括开始日期和结束日期.
	 * @param startDate 查询的开始日期.格式为"yyyyMMdd"
	 * @param endDate 查询的结束日期.格式为"yyyyMMdd"
	 * @return 从开始日期到结束日期的天数
	 */
	public static int getNumberOfDays(String startDate, String endDate) {
		List days = getDate(startDate, endDate);
		int num = days.size();

		return num;
	}
	/**
     * 静态方法,获得某一日期的前n月或后N月的日期
     * @param date 既定日期,以此日期为基准进行查询.格式为"yyyyMMdd"
     * @param n 在给定日期上加或减的天数.当n为正值时,将获得将来第n天的日期,
     * 当n为负值时,将获得过去n天前的日期
     * @return String字符串,格式为"yyyyMMdd"
     */
	public static String getDayOfLastMonth(String date,int n)
	{
		String day = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

		try
		{
			formatter.parse(date);
			Calendar tempCal = formatter.getCalendar();
			tempCal.add(Calendar.MONTH,n);
			day = formatter.format(tempCal.getTime());
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return day;
	}
	
	public static int getNumHours(String sdate,String edate)
	{
		SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmss");
		int hour = 0;
		try
		{
			Date begin = dfs.parse(sdate);
			Date end = dfs.parse(edate);
			long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
			hour = (int)(between/ 3600);
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return hour;
	}
	
	public static void main(String[] args) {
		List rs = DateParser.getAllMonth("200608","200612");
		
		for(int i=0;i<rs.size();i++)
		{
			//String[] str = (String[])rs.get(i);
			//System.out.println(str[0]+" - "+str[1]);
			System.out.println(rs.get(i));
		}
		
	}
}