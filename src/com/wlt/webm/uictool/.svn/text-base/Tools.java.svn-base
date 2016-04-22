package com.wlt.webm.uictool;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.rpc.ServiceException;

import cn.com.WebXml.Single;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.scputil.TenpayUtil;
/**
 * <p>Description: 工具类</p>
 */
public class Tools {
	
	  /**
     * 对于数字前补0
     * @param str 输入的字符串
     * @param num 长度
     * @return String 前补0后的字符串
     */
    public static String headFillZero(String str,int num){
    	//stringbuffer添加0
    	StringBuffer buffer = new StringBuffer();
    	//如果长度小于指定长度，则前补0
    	if(str.length() < num){
    		for(int i = 0 ; i < num - str.length(); i ++){
    			buffer.append("0");
    		}
    	}
    	//返回的字符串
    	str = buffer.toString() + str;
    	
    	return str;
    }
    
    /**
     * 对于数字后面补空格
     * @param str 输入的字符串
     * @param num 长度
     * @return String 后面补空格后的字符串
     */
    public static String endFillSpace(String str,int num){
    	StringBuffer buffer = new StringBuffer();
    	//如果长度小于指定长度，则前补0
    	if(str.length() < num){
    		for(int i = 0 ; i < num - str.length(); i ++){
    			buffer.append(" ");
    		}
    	}
    	//返回的字符串
    	str = str + buffer.toString();    	
    	return str;
    }
    /**
     * 对于数字前补空格
     * @param str 输入的字符串
     * @param num 长度
     * @return String 后面补空格后的字符串
     */
    public static String headFillSpace(String str,int num){
    	StringBuffer buffer = new StringBuffer();
    	//如果长度小于指定长度，则前补0
    	if(str.length() < num){
    		for(int i = 0 ; i < num - str.length(); i ++){
    			buffer.append(" ");
    		}
    	}
    	//返回的字符串
    	str = buffer.toString()+str;    	
    	return str;
    }
    
    /**
	 * 截取字节数组
	 * 
	 * @param src
	 *            byte[] 被截的数组
	 * @param beg
	 *            int 开始位置
	 * @param end
	 *            int 结束位置
	 * @return byte[] 截取后的数组
	 */
	public static byte[] ByteToByte(byte src[], int beg, int end) {
		byte bytes[] = new byte[end - beg + 1];
		for (int i = beg; i <= end; i++) {
			bytes[i - beg] = src[i];
		}
		return bytes;
	}
	/**
	 * 将字符串转换为DATE
	 * 
	 * @return Date
	 */
	public static Date fomatStrToDate(String seqFlag) {
		Date t = null;
		try {
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			t = simpleFormat.parse(seqFlag);
		} catch (Exception e) {
			Log.error("格式消息格式错误", e);
		}
		return t;
	}
	/**
	 * 函数功能：获得当前日期
	 * 
	 * @return String 返回当前日期的字符变量，如"20070625"
	 */
	public static String getNowDate() {
		Date d = new Date();
		String str = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		str = formatter.format(d);
		return str;
	}
	/**
	 * 获得当前time
	 * 
	 * @return String 获得当前时间的字符串变量，如"235959"
	 */
	public static String getTime() {
		Date d = new Date();
		String str = "";
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
		str = formatter.format(d);
		return str;
	}
	/**
	 * 获得当前time
	 * 
	 * @return String 获得当前时间的字符串变量，如"dd101211"
	 */
	public static String getOptTime() {
		Date d = new Date();
		String str = "";
		SimpleDateFormat formatter = new SimpleDateFormat("ddHHmmss");
		str = formatter.format(d);
		return str;
	}
	  public static Date getDateTime(String seqFlag) {
		  Date t = null;
		  try{
			  SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			  t = simpleFormat.parse(seqFlag);
		  }catch(Exception e){
			 Log.error(e);
		  }
		  return t;
	  }
	/**
	 * 获得当前时间
	 * 
	 * @return String 获得当前时间的字符串变量，如"20030114101211"
	 */
	public static String getNow() {
		Date d = new Date();
		String str = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		str = formatter.format(d);
		return str;
	}
	/**
     * 记录日志（16进制）
     * @param data 日志byte数据
     * @return String 日志转换后的字符串
     */
    public static String hexLog(byte[] data){
        /**
         * 记录日志
         */
    	StringBuffer logmes = new StringBuffer();		
    	
    	for(int i = 0;i < data.length; i ++){
    		
    		String temp = Integer.toHexString(data[i] & 0xff);
    		
    		if(temp.length() == 1){
    			logmes.append("0");
    		}
    		logmes.append(temp)
    			  .append(" ");
    	}
    	
    	return logmes.toString();
    }
    
    /**
     * 分转化为元
     * @param yuan 输入的字符串－分	
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
     * 将元转换为分
     * @param yuan 输入的字符串－元	
     * @return	String 输出的字符串－分
     */
    public static String yuanToFen(String yuan){
    	String fen = "";
    	//如果只包含点
    	if(yuan.indexOf(".") == yuan.length() - 1){
    		yuan += "00";
    	}
    	//如果输入的字符串不包含小数点
    	if(yuan.indexOf(".") == -1){
    		fen = yuan + "00";
    	}else{//否则包含小数点
    		//去掉小数点
    		String[] temp = yuan.split("\\.");
    		//如果分只有1位，则加上个0
    		if(temp[1].length() == 1){
    			temp[1] += "0";
    		}
    		fen = temp[0] + temp[1];
    	}
    	return fen;
    }

    /**
     * 对于数字前补0
     * @param str 输入的字符串
     * @param num 长度
     * @return 前补0后的字符串
     */
    public static String add0(String str,int num){
    	//stringbuffer添加0
    	StringBuffer buffer = new StringBuffer();
    	//如果长度小于指定长度，则前补0
    	if(str.length() < num){
    		for(int i = 0 ; i < num - str.length(); i ++){
    			buffer.append("0");
    		}
    	}
    	//返回的字符串
    	str = buffer.toString() + str;
    	
    	return str;
    }
    
    /**
     * 函数功能：传入字符串和数字转化为相应的字符数组，后补空格
     * @param str 输入的字符串 
     * @param num 数组长度
     * @return byte[] 返回的字符数组
     */
    public static byte[] addBlank(String str,int num){
    	//返回字符数组
    	byte[] result = new byte[num];
    	//如果str为null或者str的长度为0,则传入空格,即32
    	if(str == null || str.length() == 0){
    		for(int i = 0; i < num; i ++){
    			result[i] = 32;
    		}
    		return result;
    	}
    	//传入字符串转换为字符数组后的长度
    	int strNum = str.getBytes().length;
    	//如果字符串的长度大于输入的长度，则截取
    	if(strNum > num){
    		//将输入的字符串copy到result中
    		System.arraycopy(str.getBytes(),0,result,0,num);
    		
    		return result;
    		//字符串的长度小于输入的长度，则添加空格
    	}else if(strNum < num){
    		//stringBuffer类加空格
    		StringBuffer temp = new StringBuffer();
    		//加少的空格
    		for(int i = 0; i < num - strNum; i ++){
    			temp.append(" ");
    		}
    		str += temp.toString();   		
    		return str.getBytes();
    	}else{//如果相等，则返回输入字符串的字符数组
    		return str.getBytes();
    	}  
    }
    
    /**
     * 比较当前日期和指定日期的天数间隔
     * @param pastDate  需要做比较的日期 格式：yyyyMMdd
     * @param temp  间隔的天数
     * @return  返回布尔值
     */
    public static boolean compareDate(String pastDate, int temp){
    	boolean flag = false;
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DATE, temp);
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    	String lastDate = formatter.format(cal.getTime());
    	if(lastDate.equals(pastDate)){
    		flag = true;
    	}
    	return flag;
    }
    /**
     * 记录日志（16进制）
     * @param data 日志byte数据
     * @return String 日志转换后的字符串
     */
    public static String hexlog(byte[] data){
        /**
         * 记录日志
         */
    	StringBuffer logmes = new StringBuffer();		
    	
    	for(int i = 0;i < data.length; i ++){
    		
    		String temp = Integer.toHexString(data[i] & 0xff);
    		
    		if(temp.length() == 1){
    			logmes.append("0");
    		}
    		logmes.append(temp)
    			  .append(" ");
    	}
    	
    	return logmes.toString();
    }
    
    
	/**
	 * byte数组相加
	 */
	public static byte[] byteAappend(byte src[], byte src2[]) {
		if (src == null)
			return src2;
		if (src2 == null)
			return src;
		int srcLen = src.length;
		int destLen = src2.length;
		byte dest[] = new byte[srcLen + destLen];
		int i = 0;
		for (i = 0; i < srcLen; i++) {
			dest[i] = src[i];
		}
		for (int n = i; n < srcLen + destLen; n++) {
			dest[n] = src2[n - i];
		}
		return dest;
	}
	
	
	/**
	 * 计算字节长度
	 * @param str 所要计算的字节
	 * @param num 用num个数字表示长度（如num为4表示长度15为0015）
	 * @return
	 */
	public static byte[] getByteLength(byte[] str, int num) {
		int s = 0;
		if(str!=null) 
		{
			s=str.length;
		}
		int strLen = String.valueOf(s).length();
		int sub = num - strLen;
		String tmp = "";
		while(sub>0)
		{
			tmp= tmp+"0";
			sub--;
		}
		return  subString(tmp+String.valueOf(s), num).getBytes();
	}
	public static String subString(String str, int num) {
		String substring = str;
		if (str != null && str.length() > num) {
			substring = str.substring(0, num);
		}
		return substring;
	}

	/**
	 * 截取字节数组
	 * 
	 * @param src
	 *            byte[] 被截的数组
	 * @param beg
	 *            int 开始位置
	 * @param end
	 *            int 结束位置
	 * @return byte[] 截取后的数组
	 */
	public static byte[] subByte(byte src[], int beg, int end) {
		byte bytes[] = new byte[end - beg + 1];
		for (int i = beg; i <= end; i++) {
			bytes[i - beg] = src[i];
		}
		return bytes;
	}
    
	/**
	   * 把字节数组转化为字符串
	   * @param src byte[] 数据员
	   * @param beg int 开始位置
	   * @param end int 结束位置
	   * @return String 返回字符串
	   */
	  public static String bytesToStr(byte src[], int beg, int end) {
	    String hs = "";
	    char ch = '\0';
	    for (int n = beg; n <= end; n++) {
	      ch = (char) src[n];
	      hs = hs + ch;
	    }
	    return hs;
	  }
	  
	  
		/**
		 * 获得当前时间
		 * 
		 * @return 获得当前时间的字符串变量，如"20030114101211"
		 */
		public static String getNow3() {
			Date d = new Date();
			String str = "";
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			str = formatter.format(d);
			return str;
		}
		
		/**
		 * 对于数字前补0
		 * 
		 * @param str
		 *            输入的字符串
		 * @param num
		 *            长度
		 * @return 前补0后的字符串
		 */
		public static String addLeftZero(String str, int num) {
			// stringbuffer添加0
			StringBuffer buffer = new StringBuffer();
			// 如果长度小于指定长度，则前补0
			if (str.length() < num) {
				for (int i = 0; i < num - str.length(); i++) {
					buffer.append("0");
				}
			}
			// 返回的字符串
			str = buffer.toString() + str;
			return str;
		}
		
		/**
		 * 返回时间的前8位
		 * @return
		 */
		public static String getDate() {
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddhhmmss");
			String date = simpleFormat.format(new Date());
			return date.substring(0, 8);
		}
		
		
		

		/**
			 * 获得发给scp的消息流水号
			 * 
			 * @param terminal
			 *            终端号码
			 * @return
			 */
			public static String getStreamSerial(String terminal) {
				StringBuffer buff = new StringBuffer();
				buff.append(getStreamDate());
				buff.append(headFillStr(terminal, 12));
				int no = getScpStreamAscNum();
				if (no <= 9) {
					buff.append("0");
					buff.append(no);
				} else {
					buff.append(no);
				}
				return buff.toString();
			}


			/**
			 * 获得流水号生成日期
			 * 
			 * @return 获得当前时间的字符串变量，如"080114101211"
			 */
			public static String getStreamDate() {
				Date d = new Date();
				String str = "";
				SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
				str = formatter.format(d);
				return str;
			}

			/**
			 * 字符串前添加0
			 * 
			 * @param src
			 *            源数据
			 * @param number
			 *            需要的长度
			 * @return des 目的字符串
			 */
			public static String headFillStr(String src, int number) {
				if (src.length() < number) {
					String temp = "";
					for (int i = 0; i < number - src.length(); i++) {
						temp += "0";
					}
					src = temp + src;
				} else if (src.length() > number) {
					src = src.substring(src.length() - number, src.length());
				}
				return src;
			}

			/**
			 * SCP流水序号
			 * 
			 * @return int
			 */
			public static int streamAsc=0;
			public static synchronized int getScpStreamAscNum() {
				streamAsc++;
				if (streamAsc > 99) {
					streamAsc = 0;
				}
				return streamAsc;
			}
			/**
			 * 移动缴费流水号
			 */
			public static int BeginSeq=0;
			public static synchronized String getCMPaySeq(String phone,int NO){
				if(BeginSeq==0){
					BeginSeq=NO;
				}else if(BeginSeq==9999999){
					BeginSeq=1;
				}else{
					BeginSeq++;
				}
				String a="HR"+phone;
				String b=headFillStr(BeginSeq+"",7);
				a+=b;
				return a;
			}
			/**
			 *  移动报文序列号
			 */
			public static int Seq=0;
			public static synchronized String getCMPayNO(){
				if(Seq==9999999){
					Seq=1;
				}else{
					Seq++;
				}
				String b=headFillStr(Seq+"",10);
				return b;
			}
			
			/**
			 * 取出一个指定长度大小的随机正整数.
			 * 
			 * @param length
			 *            int 设定所取出随机数的长度。length小于11
			 * @return int 返回生成的随机数。
			 */
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
			
			
			/**
			 * 获得代理商号码 不足13位前补空格
			 * @param agentID
			 * @return
			 */
			public static String getAgentID(String agentID){
				String str="";
                for(int i=0;i<13-agentID.length();i++){
                	str+=" ";
                }
				return str+agentID;
			}
			
			/**
			 * 获得代理商密码 不足8位前补0
			 * @param agentID
			 * @return
			 */
			public static String getAgentPWD(String pwd){
				String str="";
                for(int i=0;i<8-pwd.length();i++){
                	str+="0";
                }
				return str+pwd;
			}
			
			/**
			 * 不足50位后补空格
			 * @param clientph
			 * @return
			 */
			public static String dealClientPh(String clientph){
				int length=50-clientph.length();
				for(int i=0;i<length;i++){
					clientph+=" ";
				}
				return clientph;
				
			}

			/**
			 * 不足16位前补0
			 * @param cash
			 * @return
			 */
			public static String dealCash(String cash){
				int length=16-cash.length();
				String supply="";
				for(int i=0;i<length;i++){
					supply+="0";
				}
				return supply+cash;
				
			}
			
			/**
			 * 不足20位后补空格
			 * @param cash
			 * @return
			 */
			public static String dealSerial(String serial){
				int length=20-serial.length();
				for(int i=0;i<length;i++){
					serial+=" ";
				}
				return serial;
				
			}
			
			/**
			 * 得到20位的交易流水号
			 * @param cooperate
			 * @return
			 */
			public static String getSerialNumber(){
				String x=buildRandom(4)+"";
				String y=buildRandom(5)+"";
				String z=buildRandom(3)+"";
				String str=z+y+x+getNow3().substring(8);
				return str;
			}


			/**
			 * 判断ip是否是广东省
			 * @param ip
			 * @return
			 * @throws RemoteException
			 * @throws ServiceException
			 * @throws UnsupportedEncodingException 
			 */
			public static boolean validateIp(String ip) throws RemoteException, ServiceException, UnsupportedEncodingException{
				boolean flag=false;
				String[] str1=Single.getSrvInstance().getIpAddressSearchWebServiceSoap().getCountryCityByIp(ip);
				if(null!=str1&&str1.length>1&&((new String(str1[1].getBytes("GBK"))).indexOf("广东")!=-1)){
					flag=true;
				}
				return flag;
			}
			
}
