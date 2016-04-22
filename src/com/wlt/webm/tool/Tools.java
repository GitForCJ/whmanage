package com.wlt.webm.tool;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import sun.misc.BASE64Decoder;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.action.Jd_Flows;
import com.wlt.webm.pccommon.TaskChecker;
import com.wlt.webm.ten.service.MD5Util;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * <p>
 * Description: 工具类
 * </p>
 */
public class Tools {
	/**
	 * 包序号
	 */
	private static long packageSerial = 0;
	public  static int  FLOW_SWITCH_FLAG=0;//不压单
	public  static int  DUANXIN_SWITCH_FLAG=1;//默认33得9
	public static String getFacct(int a) {
		return headFillStr("6222" + getNow3().substring(11) + a, 15);
	}

	/**
	 * 对于数字前补0
	 * 
	 * @param str
	 *            输入的字符串
	 * @param num
	 *            长度
	 * @return String 前补0后的字符串
	 */
	public static String headFillZero(String str, int num) {
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
	 * 函数功能：传入字符串和数字转化为相应的字符数组，后补空格
	 * 
	 * @param str
	 *            输入的字符串
	 * @param num
	 *            数组长度
	 * @return byte[] 返回的字符数组
	 */
	public static byte[] addRightZero(byte[] str, int num) {
		// 返回字符数组
		byte[] result = new byte[num];
		// 如果str为null或者str的长度为0,则传入空格,即32
		if (str == null || str.length == 0) {
			for (int i = 0; i < num; i++) {
				result[i] = 0x00;
			}
			return result;
		}
		// 传入字符串转换为字符数组后的长度
		int strNum = str.length;
		// 如果字符串的长度大于输入的长度，则截取
		if (strNum > num) {
			// 将输入的字符串copy到result中
			System.arraycopy(str, 0, result, 0, num);
			return result;
			// 字符串的长度小于输入的长度，则添加空格
		} else if (strNum < num) {
			System.arraycopy(str, 0, result, 0, strNum);
			for (int i = 0; i < num - strNum; i++) {
				result[strNum + i] = 0x00;
			}
			return result;
		} else {// 如果相等，则返回输入字符串的字符数组
			return str;
		}
	}

	/**
	 * 将序号格式化为字符串，不足位数在前面补0
	 * 
	 * @param sn
	 *            int 序号
	 * @param length
	 *            int 字符串位数
	 * @return String
	 */
	public static String formatSn(long sn, int length) {
		String str = sn + "";
		String strSn = str;
		for (int i = 0; i < length - str.length(); i++) {
			strSn = "0" + strSn;
		}
		return strSn;
	}

	/**
	 * 获得包流水号
	 * 
	 * @return
	 */
	public static synchronized byte[] getSequenceId() {
		byte[] serial = new byte[4];
		packageSerial++;
		long max = 4294967295l;
		if (packageSerial > max) {
			packageSerial = 0;
		}
		// serial =
		// Tools.addLeftZero(Long.toHexString(packageSerial).getBytes(), 4);
		serial = Tools.HexString2Bytes(Long.toHexString(packageSerial), 8, 4);

		return serial;
	}

	/**
	 * 函数功能：传入字符串和数字转化为相应的字符数组，后补空格
	 * 
	 * @param str
	 *            输入的字符串
	 * @param num
	 *            数组长度
	 * @return byte[] 返回的字符数组
	 */
	public static byte[] addRightZero2(byte[] str, int num) {
		// 返回字符数组
		byte[] result = new byte[num];
		// 如果str为null或者str的长度为0,则传入空格,即32
		if (str == null || str.length == 0) {
			for (int i = 0; i < num; i++) {
				result[i] = 0x00;
			}
			return result;
		}
		// 传入字符串转换为字符数组后的长度
		int strNum = str.length;
		// 如果字符串的长度大于输入的长度，则截取
		if (strNum > num) {
			// 将输入的字符串copy到result中
			System.arraycopy(str, 0, result, 0, num);
			return result;
			// 字符串的长度小于输入的长度，则添加空格
		} else if (strNum < num) {
			System.arraycopy(str, 0, result, 0, strNum);
			for (int i = 0; i < num - strNum; i++) {
				result[strNum + i] = 0x00;
			}
			return result;
		} else {// 如果相等，则返回输入字符串的字符数组
			return str;
		}
	}

	/**
	 * 将指定字符串src，以每两个字符分割转换为16进制形式 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF,
	 * 0xD9}
	 * 
	 * @param src
	 *            String
	 * @return byte[]
	 */
	public static byte[] HexString2Bytes(String src, int len, int size) {
		String newStr = "";
		if (src.length() <= len) {
			newStr = Tools.add0(src, len);
		}
		byte[] ret = new byte[size];
		byte[] tmp = newStr.getBytes();
		for (int i = 0; i < size; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	/**
	 * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
	 * 
	 * @param src0
	 *            byte
	 * @param src1
	 *            byte
	 * @return byte
	 */
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	/**
	 * 对于数字后面补空格
	 * 
	 * @param str
	 *            输入的字符串
	 * @param num
	 *            长度
	 * @return String 后面补空格后的字符串
	 */
	public static String endFillSpace(String str, int num) {
		StringBuffer buffer = new StringBuffer();
		// 如果长度小于指定长度，则前补0
		if (str.length() < num) {
			for (int i = 0; i < num - str.length(); i++) {
				buffer.append(" ");
			}
		}
		// 返回的字符串
		str = str + buffer.toString();
		return str;
	}

	/**
	 * 对于数字前补空格
	 * 
	 * @param str
	 *            输入的字符串
	 * @param num
	 *            长度
	 * @return String 后面补空格后的字符串
	 */
	public static String headFillSpace(String str, int num) {
		StringBuffer buffer = new StringBuffer();
		// 如果长度小于指定长度，则前补0
		if (str.length() < num) {
			for (int i = 0; i < num - str.length(); i++) {
				buffer.append(" ");
			}
		}
		// 返回的字符串
		str = buffer.toString() + str;
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
			SimpleDateFormat simpleFormat = new SimpleDateFormat(
					"yyyyMMddHHmmss");
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

	public static String getNewDate() {
		Date d = new Date();
		String str = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		str = formatter.format(d);
		return str;
	}

	public static String getNowDate1() {
		Date d = new Date();
		String str = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
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
		try {
			SimpleDateFormat simpleFormat = new SimpleDateFormat(
					"yyyyMMddHHmmss");
			t = simpleFormat.parse(seqFlag);
		} catch (Exception e) {
			Log.error(e);
		}
		return t;
	}

	/**
	 * byte数组相加
	 */
	public static byte[] ByteAdd(byte src[], byte src2[]) {
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
	 * 
	 * @param data
	 *            日志byte数据
	 * @return String 日志转换后的字符串
	 */
	public static String hexLog(byte[] data) {
		/**
		 * 记录日志
		 */
		StringBuffer logmes = new StringBuffer();

		for (int i = 0; i < data.length; i++) {

			String temp = Integer.toHexString(data[i] & 0xff);

			if (temp.length() == 1) {
				logmes.append("0");
			}
			logmes.append(temp).append(" ");
		}

		return logmes.toString();
	}

	/**
	 * 分转化为元
	 * 
	 * @param yuan
	 *            输入的字符串－分
	 * @return String 输出的字符串－元
	 */
	public static String fenToYuan(String fen) {
		// 转化后的元
		String yuan = "";
		// 字符串转化为整数
		long fenInt = Long.parseLong(fen.trim());
		// 赋值为元
		yuan = (fenInt / 100.00) + "";
		// 如果得到的元只包含尾数只包含一个数，则添加一个0
		if (yuan.lastIndexOf(".") == (yuan.length() - 2)) {
			yuan += "0";
		}

		return yuan;
	}

	/**
	 * 分转化为元
	 * 
	 * @param yuan
	 *            输入的字符串－分
	 * @return String 输出的字符串－元
	 */
	public static String fen2Yuan(String fen) {
		// 转化后的元
		String yuan = "";
		// 字符串转化为整数
		int fenInt = Integer.parseInt(fen.trim());
		// 赋值为元
		yuan = (fenInt / 100) + "";
		return yuan;
	}

	/**
	 * 将元转换为分
	 * 
	 * @param yuan
	 *            输入的字符串－元
	 * @return String 输出的字符串－分
	 */
	public static String yuanToFen(String yuan) {
		String fen = "";
		// 如果只包含点
		if (yuan.indexOf(".") == yuan.length() - 1) {
			yuan += "00";
		}
		// 如果输入的字符串不包含小数点
		if (yuan.indexOf(".") == -1) {
			fen = yuan + "00";
		} else {// 否则包含小数点
			// 去掉小数点
			String[] temp = yuan.split("\\.");
			// 如果分只有1位，则加上个0
			if (temp[1].length() == 1) {
				temp[1] += "0";
			}
			fen = temp[0] + temp[1];
		}
		return fen;
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
	public static String add0(String str, int num) {
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
	 * 函数功能：传入字符串和数字转化为相应的字符数组，后补空格
	 * 
	 * @param str
	 *            输入的字符串
	 * @param num
	 *            数组长度
	 * @return byte[] 返回的字符数组
	 */
	public static byte[] addBlank(String str, int num) {
		// 返回字符数组
		byte[] result = new byte[num];
		// 如果str为null或者str的长度为0,则传入空格,即32
		if (str == null || str.length() == 0) {
			for (int i = 0; i < num; i++) {
				result[i] = 32;
			}
			return result;
		}
		// 传入字符串转换为字符数组后的长度
		int strNum = str.getBytes().length;
		// 如果字符串的长度大于输入的长度，则截取
		if (strNum > num) {
			// 将输入的字符串copy到result中
			System.arraycopy(str.getBytes(), 0, result, 0, num);

			return result;
			// 字符串的长度小于输入的长度，则添加空格
		} else if (strNum < num) {
			// stringBuffer类加空格
			StringBuffer temp = new StringBuffer();
			// 加少的空格
			for (int i = 0; i < num - strNum; i++) {
				temp.append(" ");
			}
			str += temp.toString();
			return str.getBytes();
		} else {// 如果相等，则返回输入字符串的字符数组
			return str.getBytes();
		}
	}

	/**
	 * 比较当前日期和指定日期的天数间隔
	 * 
	 * @param pastDate
	 *            需要做比较的日期 格式：yyyyMMdd
	 * @param temp
	 *            间隔的天数
	 * @return 返回布尔值
	 */
	public static boolean compareDate(String pastDate, int temp) {
		boolean flag = false;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, temp);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String lastDate = formatter.format(cal.getTime());
		if (lastDate.equals(pastDate)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 记录日志（16进制）
	 * 
	 * @param data
	 *            日志byte数据
	 * @return String 日志转换后的字符串
	 */
	public static String hexlog(byte[] data) {
		/**
		 * 记录日志
		 */
		StringBuffer logmes = new StringBuffer();

		for (int i = 0; i < data.length; i++) {

			String temp = Integer.toHexString(data[i] & 0xff);

			if (temp.length() == 1) {
				logmes.append("0");
			}
			logmes.append(temp).append(" ");
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
	 * 
	 * @param str
	 *            所要计算的字节
	 * @param num
	 *            用num个数字表示长度（如num为4表示长度15为0015）
	 * @return
	 */
	public static byte[] getByteLength(byte[] str, int num) {
		int s = 0;
		if (str != null) {
			s = str.length;
		}
		int strLen = String.valueOf(s).length();
		int sub = num - strLen;
		String tmp = "";
		while (sub > 0) {
			tmp = tmp + "0";
			sub--;
		}
		return subString(tmp + String.valueOf(s), num).getBytes();
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
	 * 
	 * @param src
	 *            byte[] 数据员
	 * @param beg
	 *            int 开始位置
	 * @param end
	 *            int 结束位置
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
	 * 
	 * @return
	 */
	public static String getDate() {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		String date = simpleFormat.format(new Date());
		return date.substring(0, 8);
	}

	public static String getNowDateStr() {
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = simpleFormat.format(new Date());
		return date;
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
		buff.append(buildRandom(7));
		buff.append(headFillStr(getScpStreamAscNum() + "", 5));

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
	public static int streamAsc = 0;

	public static synchronized int getScpStreamAscNum() {
		streamAsc++;
		if (streamAsc > 99999) {
			streamAsc = 0;
		}
		return streamAsc;
	}

	/**
	 * 移动缴费流水号
	 */
	public static int BeginSeq = 0;

	public static synchronized String getCMPaySeq(String phone, int NO) {
		if (BeginSeq == 0) {
			BeginSeq = NO;
		} else if (BeginSeq == 9999999) {
			BeginSeq = 1;
		} else {
			BeginSeq++;
		}
		String a = "HR" + phone;
		String b = headFillStr(BeginSeq + "", 7);
		a += b;
		return a;
	}

	/**
	 * 移动报文序列号
	 */
	public static int Seq = 0;

	public static synchronized String getCMPayNO() {
		if (Seq == 9999999) {
			Seq = 1;
		} else {
			Seq++;
		}
		String b = headFillStr(Seq + "", 10);
		return b;
	}

	/**
	 * 得到16位的报文流水号
	 * 
	 * @param cooperate
	 * @return
	 */
	public static String get16SerialNumber() {
		// String str=TianZuoParameters.TZ_COOPERATEID;
		String str = Tools.getNow();
		int x = buildRandom(3);
		str = str + x + getNow3().substring(11);
		return str;
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
	 * 获得代理商号码 不足13位前补空格
	 * 
	 * @param agentID
	 * @return
	 */
	public static String getAgentID(String agentID) {
		String str = "";
		for (int i = 0; i < 13 - agentID.length(); i++) {
			str += " ";
		}
		return str + agentID;
	}

	/**
	 * 获得代理商密码 不足8位前补0
	 * 
	 * @param agentID
	 * @return
	 */
	public static String getAgentPWD(String pwd) {
		String str = "";
		for (int i = 0; i < 8 - pwd.length(); i++) {
			str += "0";
		}
		return str + pwd;
	}

	/**
	 * 不足50位后补空格
	 * 
	 * @param clientph
	 * @return
	 */
	public static String dealClientPh(String clientph) {
		int length = 50 - clientph.length();
		for (int i = 0; i < length; i++) {
			clientph += " ";
		}
		return clientph;

	}

	/**
	 * 不足16位前补0
	 * 
	 * @param cash
	 * @return
	 */
	public static String dealCash(String cash) {
		int length = 16 - cash.length();
		String supply = "";
		for (int i = 0; i < length; i++) {
			supply += "0";
		}
		return supply + cash;

	}

	/**
	 * 不足20位后补空格
	 * 
	 * @param cash
	 * @return
	 */
	public static String dealSerial(String serial) {
		int length = 20 - serial.length();
		for (int i = 0; i < length; i++) {
			serial += " ";
		}
		return serial;

	}

	public synchronized static String getSeqNo(String userNo) {
		return getNow3().substring(2) + userNo
				+ UniqueNo.getInstance().getNoTwo();
	}

	public synchronized static String getSeqNo2(String userNo) {
		return getNow3().substring(2) + userNo
				+ UniqueNo.getInstance().getNo3();
	}

	/**
	 * 获得代理商密码 不足8位前补0
	 * 
	 * @param agentID
	 * @return
	 */
	public static String getPho(String userNo) {
		String str = "";
		for (int i = 0; i < 11 - userNo.length(); i++) {
			str += "0";
		}
		return str + userNo;
	}

	public static String getSerialForJtfk() {
		SimpleDateFormat sdf = new SimpleDateFormat("ddHHmmss");
		String result = sdf.format(new Date());
		return result + new Random().nextInt(10) + new Random().nextInt(10)
				+ new Random().nextInt(10);
	}

	/**
	 * 获取上个月最后一天的时间点
	 * 
	 * @param t
	 * @return
	 */
	public static String getLastMonthDay(Date t) {
		Calendar cal = Calendar.getInstance();
		Date date = t;
		cal.setTime(date);
		int year = 0;
		int month = cal.get(Calendar.MONTH); // 上个月月份
		if (month == 0) {
			year = cal.get(Calendar.YEAR) - 1;
			month = 12;
		} else {
			year = cal.get(Calendar.YEAR);
		}
		// 设置天数
		String temp = year + "-" + month;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Date d = null;
		try {
			d = format.parse(temp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.setTime(d);
		int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		String endDay = null;
		if (month < 10) {
			endDay = year + "0" + month + "" + day + "235959";
		} else {
			endDay = year + "" + month + "" + day + "235959";
		}
		return endDay;
	}

	/**
	 * 获得当前时间前几天或者后几天
	 * 
	 * @param n
	 * @return
	 */
	public static String getOtherTime(int n) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, n);
		return format.format(cal.getTime());
	}

	/**
	 * 获得当前时间前几小时或者后几小时
	 * 
	 * @param n
	 * @return
	 */
	public static String getOtherHour(int n) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, n);
		return format.format(cal.getTime());
	}

	/**
	 * 判断是否为系统不允许交易时间
	 * 
	 * @return
	 */
	public static boolean validateTime() {
		int time = Integer.parseInt(Tools.getNow3().substring(8, 14));
		int num1 = 235000;
		int num2 = 002000;
		if (time >= num1 || time <= num2) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 针对佛山移动 接口 调用
	 * 判断是否为系统不允许交易时间
	 * @return boolean 
	 */
	public static boolean isTime() {
		int time = Integer.parseInt(Tools.getNow3().substring(8, 14));
		int num1 = 234500;
		int num2 = 002000;
		if (time >= num1 || time <= num2) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 用户真实ip
	 * 
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
		if (str != null && str.length > 1) {
			ip = str[0];
		}
		return ip;
	}

	public static String getMACAddress(String ip) {
		String str = "";
		String macAddress = "";
		try {
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(
								str.indexOf("MAC Address") + 14, str.length());
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return macAddress;
	}

	/**
	 * 获得账户日志流水号
	 * 
	 * @param time
	 * @param userNo
	 * @return
	 */
	public static String getAccountSerial(String time, String userNo) {
		return time.substring(12) + userNo + buildRandom(5) + buildRandom(3);
	}

	/**
	 * 获取指定月份的天数
	 * 
	 * @param yearMonth
	 * @return
	 * @throws ParseException
	 */
	public static int getMonthCount(String yearMonth) throws ParseException {
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMM"); // 如果写成年月日的形式的话
																		// ，
																		// 要写小d，
																		// 如：
																		// "yyyyMM"
		rightNow.setTime(simpleDate.parse(yearMonth)); // 要计算你想要的月份，改变这里即可
		int days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
		return days;
	}

	/**
	 * timestamp转成字符串
	 * 
	 * @param a
	 * @return
	 */
	public static String timestamp2String(long a) {
		Timestamp ts = new Timestamp(a);
		String tsStr = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		tsStr = sdf.format(ts);
		return tsStr;
	}

	public static long string2timestamp(String a) {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		ts = Timestamp.valueOf(a);
		return ts.getTime();
	}

	/**
	 * 分解大额充值
	 * @param n  待分解面额（厘）
	 * @return 面额数组
	 */
	public static float[] decomposeDE(float n ) {
		float[] rs = null;
		float maxlimit = 500000;
		float minlimit = 10000;
		float offset = 50000;
		if (n < minlimit) {
			System.out.println("不能充值");
		} else if (n >= minlimit && n <= maxlimit) {
			rs=new float[1];
			rs[0]=n;
			System.out.println("充值一次：" + n);
		} else {
			int m = (int) (n / maxlimit);
			float k = n % maxlimit;
			System.out.println("m==="+m+"  k==="+k);
			if (0 < k && k < minlimit) {
				int k1=0;
				rs=new float[m+1];
				for (int a = 0, b = m - 1; a < b; a++) {
					rs[a] = maxlimit;
					k1=a;
				}
				rs[k1+1]=k + offset;
				rs[k1+2]=maxlimit - offset;
				System.out.println("充值" + (m - 1) + "次：" + maxlimit
						+ " ,一次面额为:" + (k + offset) + "一次面额为:"
						+ (maxlimit - offset));
			} else if (k == 0) {
				rs=new float[m];
				for(int a=0;a<m;a++){
					rs[a]=maxlimit;
				}
				System.out.println("充值" + m + "次：" + maxlimit);
			} else {
				int k1=0;
				rs=new float[m+1];
				for(int a=0;a<m;a++){
					rs[a]=maxlimit;
					k1=a;
				}
				rs[k1+1]=k;
				System.out.println("充值" + m + "次：" + maxlimit + "  和一次面额为:" + k);
			}
		}
		return rs;
	}
	
	/**
	 * 根据系统编号获取用户是否开启充值密码
	 * @param userno
	 * @return
	 */
	public static String getPwdflag(String userno){
		return TaskChecker.USEREXT3.get(userno);
	}
	
	public static String PaiPaiSign(String url,HashMap<String, String> params,
			String type,String secretOAuthKey) throws Exception{
		//1、将请求的URI路径进行URL编码
		String enUrl=URLEncoder.encode(url);
//		System.out.println("encode后的url:"+enUrl);
		//2、将除“sign”外的所有参数按key进行字典升序排列,排序后的参数(key=value)用&拼接起来
		ArrayList<String> sort=new ArrayList<String>(params.keySet());
		Collections.sort(sort);
		String md5sign = "";
		for (String key : sort) {
			md5sign += (key +"="+ params.get(key))+"&";
		}
		md5sign=md5sign.substring(0,md5sign.length()-1);
//		System.out.println("排序后组合参数:"+md5sign);
		//3、将第2中的结果进行URL编码
		String enParams=URLEncoder.encode(md5sign).replace("+", "%20");
		//4、将HTTP请求方式(GER or POST)，第1步以及第3步中的到的字符串用&拼接起来
		String sourceString=type+"&"+enUrl+"&"+enParams;
//		System.out.println("源串:"+sourceString);
		//5、构造密钥
		secretOAuthKey=secretOAuthKey+"&";
		//6、使用HMAC-SHA1加密算法 将44中的到的源串以及5中得到的密钥进行加密,然后将加密后的字符串经过Base64编码
		String result=getBASE64(HmacSHA1Encrypt(sourceString,secretOAuthKey));
//		System.out.println("结果:"+result);
		return result;
	}
	
	   /**  
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名  
     * @param encryptText 被签名的字符串  
     * @param encryptKey  密钥  
     * @return  
     * @throws Exception  
     */    
    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception     
    {           
        byte[] data=encryptKey.getBytes();  
        //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称  
        SecretKey secretKey = new SecretKeySpec(data, "HmacSHA1");   
        //生成一个指定 Mac 算法 的 Mac 对象  
        Mac mac = Mac.getInstance("HmacSHA1");   
        //用给定密钥初始化 Mac 对象  
        mac.init(secretKey);    
        byte[] text = encryptText.getBytes();    
        //完成 Mac 操作   
        return mac.doFinal(text);    
    } 
	
	/**
	 * 将 s 进行 BASE64 编码 
	 * @param s
	 * @return
	 */
	public static String getBASE64(byte[] s) { 
	if (s == null) return null; 
	return (new BASE64Encoder()).encode(s); 
	} 
	 
	/**
	 * 将 BASE64 编码的字符串 s 进行解码 
	 * @param s
	 * @return
	 */
	public static String getFromBASE64(String s) { 
	if (s == null) return null; 
	BASE64Decoder decoder = new BASE64Decoder(); 
	try { 
	byte[] b = decoder.decodeBuffer(s); 
	return new String(b); 
	} catch (Exception e) { 
	return null; 
	} 
	}

	public static Date getDate(String seqFlag) {
		Date t = null;
		try {
			SimpleDateFormat simpleFormat = new SimpleDateFormat(
					"yyyyMMdd");
			t = simpleFormat.parse(seqFlag);
		} catch (Exception e) {
			Log.error(e);
		}
		return t;
	}
	
	/**
	 * 间隔日期
	 * @param startDate 开始日期
	 * @param endDate   结束日期
	 * @return 开始日期--结束日期之间的日期
	 */
	public static ArrayList<String> getIVdate(String startDate,String endDate){
	    Calendar start = Calendar.getInstance();  
	    start.setTime(Tools.getDate(startDate)) ;
	    start.add(Calendar.DATE, 1);
	    Long startTIme = start.getTimeInMillis();  
	    Calendar end = Calendar.getInstance();  
	    end.setTime(Tools.getDate(endDate));  
	    Long endTime = end.getTimeInMillis();  
	    Long oneDay = 1000 * 60 * 60 * 24l;  
	    Long time = startTIme;
	    ArrayList<String> dates=new ArrayList<String>();
	    while (time < endTime) {
	        Date d = new Date(time);  
	        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");  
	        dates.add(df.format(d));  
	        time += oneDay;  
	    }  
	    return dates;
	}
	
	/**
	 * jd MD5
	 * @param params
	 * @return
	 */
	public static String jdSign(HashMap<String,String> params){
		String source="";
		ArrayList<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		for(String key:keys){
			if(!"sign".equals(key)&&!"signType".equals(key)){
				source+=(key+params.get(key));
			}
		}
		return MD5Util.MD5Encode(source+Jd_Flows.PRIVATEKEY, "UTF-8");
	}
	
	/**
	 * 构造京东返回参数
	 * @param sign  md5值
	 * @param yewu  业务Map
	 * @return HashMap<String,String> rs
	 */
	public static HashMap<String,String> jDParams(HashMap<String,String> yewu){
		 HashMap<String,String> rs =new HashMap<String,String>();
		 rs.put("timestamp", getNow3());
	     rs.put("version","1.0");
	     rs.put("signType", "MD5");
	     rs.putAll(yewu);
	     rs.put("sign", jdSign(rs));
	     return rs;
	}
	
	public static void main(String[] args) throws Exception {
		
//		ArrayList<String> rs=getIVdate("20151122",Tools.getNowDate());
//		System.out.println((rs==null)+"  "  +rs.size());
//		for(String str:rs){
//			System.out.println(str);
//		}
		
		System.out.println(Math.abs(-1));
		
		/**
		String url="/deal/sellerSearchDealList.xhtml";
		String type="GET";
		String secretOAuthKey="hdUMwmU4P5jQtHpC";
		HashMap<String, String> params=new HashMap<String, String>();
		params.put("appOAuthID", "700042973");
		params.put("timeStamp", "1344568374452");
		params.put("accessToken", "ad39b7dbd59b87cda827223c0e520d6f");
		params.put("charset", "gbk");
		params.put("format", "xml");
		params.put("randomValue", "58095");
		params.put("uin", "1280863473");
		params.put("sellerUin", "1280863473");
		PaiPaiSign(url, params, type, secretOAuthKey);
		
		System.out.println(System.currentTimeMillis());//1439359865526
		System.out.println(timestamp2String(1439359865526L));
		
		System.out.println(URLEncoder.encode(" ").replace("+", "%20"));
		System.out.println(URLEncoder.encode("2015-08-25 09:57:47").replace("+", "%20"));
		//2009%3A23%3A39%
		
		//2015-08-25%2009
		 * */
	}

	/**
	 * 获取当期啊月 最后一天
	 * @return yyyy-MM-dd
	 */
	public static String getDateEnd(){
		// 获取Calendar
		Calendar calendar = Calendar.getInstance();
		// 设置时间,当前时间不用设置
		// calendar.setTime(new Date());
		// 设置日期为本月最大日期
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

		// 打印
		return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	}

}
