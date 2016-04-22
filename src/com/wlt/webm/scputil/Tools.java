package com.wlt.webm.scputil;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 工具类
 */
public class Tools {
	/**
	 * 获得业务控制中心系统目录
	 */
	public static final String SCP_HOME = System.getProperty("user.dir");
	/**
	 * 计费同步流水号
	 */
	public static int BillSerialNbrAsc = 0;
	 
	/**
	 * 对字符串进行编码转换，从ISO编码转换为GBK编码
	 * 
	 * @param output
	 *            String
	 * @return String
	 */
	public static String decode(String output) {
		try {
			return new String(output.getBytes("ISO-8859-1"), "GBK");
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * 对字符串进行编码转换，从GBK编码转换为ISO编码
	 * 
	 * @param output
	 *            String
	 * @return String
	 */
	public static String encode(String input) {
		try {
			return new String(input.getBytes("GBK"), "ISO-8859-1");
		} catch (Exception ex) {
			return "";
		}
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
	 * 删除目录以及目录下所有文件
	 * 
	 * @param dir
	 *            String
	 * @return String
	 */
	public static void delDir(File file) {
		// 保存中间结果
		//boolean rslt = true;
		// 先尝试直接删除
		if (!(file.delete())) {
			// 若文件夹非空。枚举、递归删除里面内容
			File subs[] = file.listFiles();
			for (int i = 0; i <= subs.length - 1; i++) {
				if (subs[i].isDirectory()) {
					// 递归删除子文件夹内容
					Tools.delDir(subs[i]);
				}
				// 删除子文件夹本身
				subs[i].delete();
			}
			// 删除此文件夹本身
			file.delete();
		}
	}
	
	
	/**
	 * 返回交易发起平台
	 * @param sendID 系统定义发送方编码
	 * @return 返回发起方平台ID
	 */
	public static String getTradeSource(String sendID)
	{
		int flag = Integer.parseInt(sendID);
		String source = null;
		switch(flag)
		{
			case 2: //smp
			{
				source = "7";
				break;
			}
			case 3: //pc
			{
				source = "3";
				break;
			}
			case 4: //ipc,即pos
			{
				source = "2";
				break;
			}
			case 5: //ipc,即pos
			{
				source = "3";
				break;
			}
			case 10: //portal
			{
				source = "1";
				break;
			}
			case 11: //空充终端
			{
				source = "8";
				break;
			}
			case 12: //FEP外部电信接口接入
			{
				source = "9";
				break;
			}
			
		}
		
		return source;
	}

	
	/**
     * 根据List中的数据获得一个Map（有序Map）。
     * @param list List中元素为String[]，且元素个数必须大于等于2。
     * @return Map(key：String；value：String)
     */
    public static Map getStringMap(List list)
    {
        Map map = new LinkedHashMap();

        for (int i = 0; i < list.size(); i++)
        {
            String[] info = (String[]) list.get(i);
            map.put(info[0], info[1]);
        }

        return map;
    }

    
    /**
     * 返回计费本地网营业区ID
     * @param areaCode  区号
     * @return 返回计费本地网营业区ID
     */
    public static String getBillLoaclNetId(String areaCode)
    {
    	int net = Integer.parseInt(areaCode.substring(1));
    	String netId = "";
    	switch(net)
    	{
	    	case 310: //邯郸
	    	{
	    		netId = "1304";
	    		break;
	    	}
	    	case 311: //石家庄
	    	{
	    		netId = "1301";
	    		break;
	    	}
	    	
	    	case 312: //保定
	    	{
	    		netId = "1306";
	    		break;
	    	}
	    	case 313: //张家口
	    	{
	    		netId = "1307";
	    		break;
	    	}
	    	case 314:  //承德
	    	{
	    		netId = "1308";
	    		break;
	    	}
	    	case 315: //唐山
	    	{
	    		netId = "1302";
	    		break;
	    	}
	    	case 316:  //廊坊
	    	{
	    		netId = "1310";
	    		break;
	    	}
	    	case 317:  //沧州
	    	{
	    		netId = "1309";
	    		break;
	    	}
	    	case 318:  //衡水
	    	{
	    		netId = "1311";
	    		break;
	    	}
	    	case 319: //邢台
	    	{
	    		netId = "1305";
	    		break;
	    	}
	    	case 335:  //秦皇岛
	    	{
	    		netId = "1303";
	    		break;
	    	}
    	}
    	
    	return netId;
    }
    
    /**
     * 返回移动G网缴费城市代码
     * @param areaCode  区号
     * @return 返回城市代码
     */
    public static String getMobileCityFlag(String areaCode)
    {
    	int net = Integer.parseInt(areaCode.substring(1));
    	String netId = "";
    	switch(net)
    	{
	    	case 310: //邯郸
	    	{
	    		netId = "F";
	    		break;
	    	}
	    	case 311: //石家庄
	    	{
	    		netId = "H";
	    		break;
	    	}
	    	
	    	case 312: //保定
	    	{
	    		netId = "G";
	    		break;
	    	}
	    	case 313: //张家口
	    	{
	    		netId = "D";
	    		break;
	    	}
	    	case 314:  //承德
	    	{
	    		netId = "J";
	    		break;
	    	}
	    	case 315: //唐山
	    	{
	    		netId = "A";
	    		break;
	    	}
	    	case 316:  //廊坊
	    	{
	    		netId = "C";
	    		break;
	    	}
	    	case 317:  //沧州
	    	{
	    		netId = "W";
	    		break;
	    	}
	    	case 318:  //衡水
	    	{
	    		netId = "I";
	    		break;
	    	}
	    	case 319: //邢台
	    	{
	    		netId = "E";
	    		break;
	    	}
	    	case 335:  //秦皇岛
	    	{
	    		netId = "B";
	    		break;
	    	}
    	}
    	
    	return netId;
    }
    
    /**
	 * 获得发给计费同步流水号
	 * @param synSerial 输入计费的同步流水号
	 * @return
	 */
	public static String getBillSerialNbr() {
		StringBuffer buff = new StringBuffer();
		buff.append(DateParser.getNowDateTime().substring(8));
		buff.append(Tools.headFillStr(String.valueOf(getBillSerialNbrAsc()), 6));
		return buff.toString();
	}
	
	/**
	 * 获得G网缴话,发给计费同步流水号
	 * @param synSerial 输入计费的同步流水号
	 * @return
	 */
	public static String getMobileSerialNbr()
	{
		StringBuffer buff = new StringBuffer();
		buff.append(DateParser.getNowDateTime());
		buff.append(Tools.headFillStr(String.valueOf(getBillSerialNbrAsc()), 6));
		return buff.toString();
	}
	
	/**
	 * 同步流水序号
	 * @return int
	 */
	public static synchronized int getBillSerialNbrAsc() {
		BillSerialNbrAsc++;
		if (BillSerialNbrAsc > 999999) {
			BillSerialNbrAsc = 0;
		}
		return BillSerialNbrAsc;
	}

	/**
	 * 字符串前添加0
	 * @param src  源数据
	 * @param number 需要的长度
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
	public static void main(String args[]) {
		System.out.println(Tools.getMobileSerialNbr());
		
//		File nul= new File("D:/测试/edwe");
//		Tools tools =new Tools();
//		tools.delDir(nul);
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
	
		public static String getNowDate() {
			Date d = new Date();
			String str = "";
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			str = formatter.format(d);
			return str;
		}
}
