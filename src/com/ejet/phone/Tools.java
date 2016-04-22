package com.ejet.phone;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Tools {

	
	public static String getEightStr(String src,String pad)
	{
		if(src.length()>8)
		{
			return src.substring(0, 8);
		}else
		{
			int len=src.length();
			for(int i=len;i<8;i++)
			{
				src+=pad;
			}
			return src;
		}
	}
	
	public static String getEightBeiStr(String src,String pad)
	{
		if(src.getBytes().length%8==0)
		{
			return src;
		}else
		{
			int len=src.getBytes().length%8;
			for(int i=len;i<8;i++)
			{
				src+=pad;
			}
			return src;
		}
	}
	
	
	public static int distanceMax(int[] tmp)
	{
		
		   int a[]=tmp;
	        Arrays.sort(a);
	       // System.out.println("?????¸º: ");
	        for(int i=0;i<a.length;i++)
	        {
	 
	        }
	     return (int)((a[a.length-1]-a[0]));
	}
	
	public static int distanceMid(int[] tmp)
	{
		int a[]=tmp;
        Arrays.sort(a);
       // System.out.println("?????¸º: ");
        for(int i=0;i<a.length;i++)
        {
        
        }
     return (int)((a[a.length-1]+a[0]))/2;
	}
	
	
		    
		    public static double GetDistance(double lng1, double lat1, double lng2, double lat2)
		    {
		       double radLat1 = rad(lat1);
		       double radLat2 = rad(lat2);
		       double a = radLat1 - radLat2;
		       double b = rad(lng1) - rad(lng2);
		       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
		        Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
		       s = s * EARTH_RADIUS;
		       s = Math.round(s * 10000) / 10000;
		       return s;
		    }
		    
		    private static double rad(double d)
		    {
		       return d * Math.PI / 180.0;
		    }
		    
		    private static final double EARTH_RADIUS = 6378137;
		    
		    public static String getNow3() {
				Date d = new Date();
				String str = "";
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
				str = formatter.format(d);
				return str;
			}
		    
		
		    public static void main(String args[])
		    {
		    	System.out.println(Tools.getEightStr("123", "0"));
		    }
		
		    public static boolean isEmail(String strEmail) {
		    	String strPattern = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";

				Pattern p = Pattern.compile(strPattern);
				Matcher m = p.matcher(strEmail);
				return m.matches();  
			}
		    
		    
		    public static String addZERO(String src,int len)
		    {
		    	int size=src.length();
		    	for(int i=size;i<len;i++)
		    	{
		    		src="0"+src;
		    	}
		    	return src;
		    }
		    
		    public static String[] getNowTime()
		    {
		    	String str[]=new String[3];
		    	Date date= new Date();
				DateFormat df = new SimpleDateFormat("yyyyMMdd");  
				
				String tmp= df.format(date);
				str[0]=tmp.substring(0,4);
				str[1]=tmp.substring(4,6);
				str[2]=tmp.substring(6,8);
				return str;
		    }
}
