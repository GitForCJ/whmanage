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
 * Description: ������
 * </p>
 */
public class Tools {
	/**
	 * �����
	 */
	private static long packageSerial = 0;
	public  static int  FLOW_SWITCH_FLAG=0;//��ѹ��
	public  static int  DUANXIN_SWITCH_FLAG=1;//Ĭ��33��9
	public static String getFacct(int a) {
		return headFillStr("6222" + getNow3().substring(11) + a, 15);
	}

	/**
	 * ��������ǰ��0
	 * 
	 * @param str
	 *            ������ַ���
	 * @param num
	 *            ����
	 * @return String ǰ��0����ַ���
	 */
	public static String headFillZero(String str, int num) {
		// stringbuffer���0
		StringBuffer buffer = new StringBuffer();
		// �������С��ָ�����ȣ���ǰ��0
		if (str.length() < num) {
			for (int i = 0; i < num - str.length(); i++) {
				buffer.append("0");
			}
		}
		// ���ص��ַ���
		str = buffer.toString() + str;

		return str;
	}

	/**
	 * �������ܣ������ַ���������ת��Ϊ��Ӧ���ַ����飬�󲹿ո�
	 * 
	 * @param str
	 *            ������ַ���
	 * @param num
	 *            ���鳤��
	 * @return byte[] ���ص��ַ�����
	 */
	public static byte[] addRightZero(byte[] str, int num) {
		// �����ַ�����
		byte[] result = new byte[num];
		// ���strΪnull����str�ĳ���Ϊ0,����ո�,��32
		if (str == null || str.length == 0) {
			for (int i = 0; i < num; i++) {
				result[i] = 0x00;
			}
			return result;
		}
		// �����ַ���ת��Ϊ�ַ������ĳ���
		int strNum = str.length;
		// ����ַ����ĳ��ȴ�������ĳ��ȣ����ȡ
		if (strNum > num) {
			// ��������ַ���copy��result��
			System.arraycopy(str, 0, result, 0, num);
			return result;
			// �ַ����ĳ���С������ĳ��ȣ�����ӿո�
		} else if (strNum < num) {
			System.arraycopy(str, 0, result, 0, strNum);
			for (int i = 0; i < num - strNum; i++) {
				result[strNum + i] = 0x00;
			}
			return result;
		} else {// �����ȣ��򷵻������ַ������ַ�����
			return str;
		}
	}

	/**
	 * ����Ÿ�ʽ��Ϊ�ַ���������λ����ǰ�油0
	 * 
	 * @param sn
	 *            int ���
	 * @param length
	 *            int �ַ���λ��
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
	 * ��ð���ˮ��
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
	 * �������ܣ������ַ���������ת��Ϊ��Ӧ���ַ����飬�󲹿ո�
	 * 
	 * @param str
	 *            ������ַ���
	 * @param num
	 *            ���鳤��
	 * @return byte[] ���ص��ַ�����
	 */
	public static byte[] addRightZero2(byte[] str, int num) {
		// �����ַ�����
		byte[] result = new byte[num];
		// ���strΪnull����str�ĳ���Ϊ0,����ո�,��32
		if (str == null || str.length == 0) {
			for (int i = 0; i < num; i++) {
				result[i] = 0x00;
			}
			return result;
		}
		// �����ַ���ת��Ϊ�ַ������ĳ���
		int strNum = str.length;
		// ����ַ����ĳ��ȴ�������ĳ��ȣ����ȡ
		if (strNum > num) {
			// ��������ַ���copy��result��
			System.arraycopy(str, 0, result, 0, num);
			return result;
			// �ַ����ĳ���С������ĳ��ȣ�����ӿո�
		} else if (strNum < num) {
			System.arraycopy(str, 0, result, 0, strNum);
			for (int i = 0; i < num - strNum; i++) {
				result[strNum + i] = 0x00;
			}
			return result;
		} else {// �����ȣ��򷵻������ַ������ַ�����
			return str;
		}
	}

	/**
	 * ��ָ���ַ���src����ÿ�����ַ��ָ�ת��Ϊ16������ʽ �磺"2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF,
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
	 * ������ASCII�ַ��ϳ�һ���ֽڣ� �磺"EF"--> 0xEF
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
	 * �������ֺ��油�ո�
	 * 
	 * @param str
	 *            ������ַ���
	 * @param num
	 *            ����
	 * @return String ���油�ո����ַ���
	 */
	public static String endFillSpace(String str, int num) {
		StringBuffer buffer = new StringBuffer();
		// �������С��ָ�����ȣ���ǰ��0
		if (str.length() < num) {
			for (int i = 0; i < num - str.length(); i++) {
				buffer.append(" ");
			}
		}
		// ���ص��ַ���
		str = str + buffer.toString();
		return str;
	}

	/**
	 * ��������ǰ���ո�
	 * 
	 * @param str
	 *            ������ַ���
	 * @param num
	 *            ����
	 * @return String ���油�ո����ַ���
	 */
	public static String headFillSpace(String str, int num) {
		StringBuffer buffer = new StringBuffer();
		// �������С��ָ�����ȣ���ǰ��0
		if (str.length() < num) {
			for (int i = 0; i < num - str.length(); i++) {
				buffer.append(" ");
			}
		}
		// ���ص��ַ���
		str = buffer.toString() + str;
		return str;
	}

	/**
	 * ��ȡ�ֽ�����
	 * 
	 * @param src
	 *            byte[] ���ص�����
	 * @param beg
	 *            int ��ʼλ��
	 * @param end
	 *            int ����λ��
	 * @return byte[] ��ȡ�������
	 */
	public static byte[] ByteToByte(byte src[], int beg, int end) {
		byte bytes[] = new byte[end - beg + 1];
		for (int i = beg; i <= end; i++) {
			bytes[i - beg] = src[i];
		}
		return bytes;
	}

	/**
	 * ���ַ���ת��ΪDATE
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
			Log.error("��ʽ��Ϣ��ʽ����", e);
		}
		return t;
	}

	/**
	 * �������ܣ���õ�ǰ����
	 * 
	 * @return String ���ص�ǰ���ڵ��ַ���������"20070625"
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
	 * ��õ�ǰtime
	 * 
	 * @return String ��õ�ǰʱ����ַ�����������"235959"
	 */
	public static String getTime() {
		Date d = new Date();
		String str = "";
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
		str = formatter.format(d);
		return str;
	}

	/**
	 * ��õ�ǰtime
	 * 
	 * @return String ��õ�ǰʱ����ַ�����������"dd101211"
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
	 * byte�������
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
	 * ��õ�ǰʱ��
	 * 
	 * @return String ��õ�ǰʱ����ַ�����������"20030114101211"
	 */
	public static String getNow() {
		Date d = new Date();
		String str = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		str = formatter.format(d);
		return str;
	}

	/**
	 * ��¼��־��16���ƣ�
	 * 
	 * @param data
	 *            ��־byte����
	 * @return String ��־ת������ַ���
	 */
	public static String hexLog(byte[] data) {
		/**
		 * ��¼��־
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
	 * ��ת��ΪԪ
	 * 
	 * @param yuan
	 *            ������ַ�������
	 * @return String ������ַ�����Ԫ
	 */
	public static String fenToYuan(String fen) {
		// ת�����Ԫ
		String yuan = "";
		// �ַ���ת��Ϊ����
		long fenInt = Long.parseLong(fen.trim());
		// ��ֵΪԪ
		yuan = (fenInt / 100.00) + "";
		// ����õ���Ԫֻ����β��ֻ����һ�����������һ��0
		if (yuan.lastIndexOf(".") == (yuan.length() - 2)) {
			yuan += "0";
		}

		return yuan;
	}

	/**
	 * ��ת��ΪԪ
	 * 
	 * @param yuan
	 *            ������ַ�������
	 * @return String ������ַ�����Ԫ
	 */
	public static String fen2Yuan(String fen) {
		// ת�����Ԫ
		String yuan = "";
		// �ַ���ת��Ϊ����
		int fenInt = Integer.parseInt(fen.trim());
		// ��ֵΪԪ
		yuan = (fenInt / 100) + "";
		return yuan;
	}

	/**
	 * ��Ԫת��Ϊ��
	 * 
	 * @param yuan
	 *            ������ַ�����Ԫ
	 * @return String ������ַ�������
	 */
	public static String yuanToFen(String yuan) {
		String fen = "";
		// ���ֻ������
		if (yuan.indexOf(".") == yuan.length() - 1) {
			yuan += "00";
		}
		// ���������ַ���������С����
		if (yuan.indexOf(".") == -1) {
			fen = yuan + "00";
		} else {// �������С����
			// ȥ��С����
			String[] temp = yuan.split("\\.");
			// �����ֻ��1λ������ϸ�0
			if (temp[1].length() == 1) {
				temp[1] += "0";
			}
			fen = temp[0] + temp[1];
		}
		return fen;
	}

	/**
	 * ��������ǰ��0
	 * 
	 * @param str
	 *            ������ַ���
	 * @param num
	 *            ����
	 * @return ǰ��0����ַ���
	 */
	public static String add0(String str, int num) {
		// stringbuffer���0
		StringBuffer buffer = new StringBuffer();
		// �������С��ָ�����ȣ���ǰ��0
		if (str.length() < num) {
			for (int i = 0; i < num - str.length(); i++) {
				buffer.append("0");
			}
		}
		// ���ص��ַ���
		str = buffer.toString() + str;

		return str;
	}

	/**
	 * �������ܣ������ַ���������ת��Ϊ��Ӧ���ַ����飬�󲹿ո�
	 * 
	 * @param str
	 *            ������ַ���
	 * @param num
	 *            ���鳤��
	 * @return byte[] ���ص��ַ�����
	 */
	public static byte[] addBlank(String str, int num) {
		// �����ַ�����
		byte[] result = new byte[num];
		// ���strΪnull����str�ĳ���Ϊ0,����ո�,��32
		if (str == null || str.length() == 0) {
			for (int i = 0; i < num; i++) {
				result[i] = 32;
			}
			return result;
		}
		// �����ַ���ת��Ϊ�ַ������ĳ���
		int strNum = str.getBytes().length;
		// ����ַ����ĳ��ȴ�������ĳ��ȣ����ȡ
		if (strNum > num) {
			// ��������ַ���copy��result��
			System.arraycopy(str.getBytes(), 0, result, 0, num);

			return result;
			// �ַ����ĳ���С������ĳ��ȣ�����ӿո�
		} else if (strNum < num) {
			// stringBuffer��ӿո�
			StringBuffer temp = new StringBuffer();
			// ���ٵĿո�
			for (int i = 0; i < num - strNum; i++) {
				temp.append(" ");
			}
			str += temp.toString();
			return str.getBytes();
		} else {// �����ȣ��򷵻������ַ������ַ�����
			return str.getBytes();
		}
	}

	/**
	 * �Ƚϵ�ǰ���ں�ָ�����ڵ��������
	 * 
	 * @param pastDate
	 *            ��Ҫ���Ƚϵ����� ��ʽ��yyyyMMdd
	 * @param temp
	 *            ���������
	 * @return ���ز���ֵ
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
	 * ��¼��־��16���ƣ�
	 * 
	 * @param data
	 *            ��־byte����
	 * @return String ��־ת������ַ���
	 */
	public static String hexlog(byte[] data) {
		/**
		 * ��¼��־
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
	 * byte�������
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
	 * �����ֽڳ���
	 * 
	 * @param str
	 *            ��Ҫ������ֽ�
	 * @param num
	 *            ��num�����ֱ�ʾ���ȣ���numΪ4��ʾ����15Ϊ0015��
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
	 * ��ȡ�ֽ�����
	 * 
	 * @param src
	 *            byte[] ���ص�����
	 * @param beg
	 *            int ��ʼλ��
	 * @param end
	 *            int ����λ��
	 * @return byte[] ��ȡ�������
	 */
	public static byte[] subByte(byte src[], int beg, int end) {
		byte bytes[] = new byte[end - beg + 1];
		for (int i = beg; i <= end; i++) {
			bytes[i - beg] = src[i];
		}
		return bytes;
	}

	/**
	 * ���ֽ�����ת��Ϊ�ַ���
	 * 
	 * @param src
	 *            byte[] ����Ա
	 * @param beg
	 *            int ��ʼλ��
	 * @param end
	 *            int ����λ��
	 * @return String �����ַ���
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
	 * ��õ�ǰʱ��
	 * 
	 * @return ��õ�ǰʱ����ַ�����������"20030114101211"
	 */
	public static String getNow3() {
		Date d = new Date();
		String str = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		str = formatter.format(d);
		return str;
	}

	/**
	 * ��������ǰ��0
	 * 
	 * @param str
	 *            ������ַ���
	 * @param num
	 *            ����
	 * @return ǰ��0����ַ���
	 */
	public static String addLeftZero(String str, int num) {
		// stringbuffer���0
		StringBuffer buffer = new StringBuffer();
		// �������С��ָ�����ȣ���ǰ��0
		if (str.length() < num) {
			for (int i = 0; i < num - str.length(); i++) {
				buffer.append("0");
			}
		}
		// ���ص��ַ���
		str = buffer.toString() + str;
		return str;
	}

	/**
	 * ����ʱ���ǰ8λ
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
	 * ��÷���scp����Ϣ��ˮ��
	 * 
	 * @param terminal
	 *            �ն˺���
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
	 * �����ˮ����������
	 * 
	 * @return ��õ�ǰʱ����ַ�����������"080114101211"
	 */
	public static String getStreamDate() {
		Date d = new Date();
		String str = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
		str = formatter.format(d);
		return str;
	}

	/**
	 * �ַ���ǰ���0
	 * 
	 * @param src
	 *            Դ����
	 * @param number
	 *            ��Ҫ�ĳ���
	 * @return des Ŀ���ַ���
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
	 * SCP��ˮ���
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
	 * �ƶ��ɷ���ˮ��
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
	 * �ƶ��������к�
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
	 * �õ�16λ�ı�����ˮ��
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
	 * ��ô����̺��� ����13λǰ���ո�
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
	 * ��ô��������� ����8λǰ��0
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
	 * ����50λ�󲹿ո�
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
	 * ����16λǰ��0
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
	 * ����20λ�󲹿ո�
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
	 * ��ô��������� ����8λǰ��0
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
	 * ��ȡ�ϸ������һ���ʱ���
	 * 
	 * @param t
	 * @return
	 */
	public static String getLastMonthDay(Date t) {
		Calendar cal = Calendar.getInstance();
		Date date = t;
		cal.setTime(date);
		int year = 0;
		int month = cal.get(Calendar.MONTH); // �ϸ����·�
		if (month == 0) {
			year = cal.get(Calendar.YEAR) - 1;
			month = 12;
		} else {
			year = cal.get(Calendar.YEAR);
		}
		// ��������
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
	 * ��õ�ǰʱ��ǰ������ߺ���
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
	 * ��õ�ǰʱ��ǰ��Сʱ���ߺ�Сʱ
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
	 * �ж��Ƿ�Ϊϵͳ��������ʱ��
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
	 * ��Է�ɽ�ƶ� �ӿ� ����
	 * �ж��Ƿ�Ϊϵͳ��������ʱ��
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
	 * �û���ʵip
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
	 * ����˻���־��ˮ��
	 * 
	 * @param time
	 * @param userNo
	 * @return
	 */
	public static String getAccountSerial(String time, String userNo) {
		return time.substring(12) + userNo + buildRandom(5) + buildRandom(3);
	}

	/**
	 * ��ȡָ���·ݵ�����
	 * 
	 * @param yearMonth
	 * @return
	 * @throws ParseException
	 */
	public static int getMonthCount(String yearMonth) throws ParseException {
		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMM"); // ���д�������յ���ʽ�Ļ�
																		// ��
																		// ҪдСd��
																		// �磺
																		// "yyyyMM"
		rightNow.setTime(simpleDate.parse(yearMonth)); // Ҫ��������Ҫ���·ݣ��ı����Ｔ��
		int days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
		return days;
	}

	/**
	 * timestampת���ַ���
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
	 * �ֽ����ֵ
	 * @param n  ���ֽ����壩
	 * @return �������
	 */
	public static float[] decomposeDE(float n ) {
		float[] rs = null;
		float maxlimit = 500000;
		float minlimit = 10000;
		float offset = 50000;
		if (n < minlimit) {
			System.out.println("���ܳ�ֵ");
		} else if (n >= minlimit && n <= maxlimit) {
			rs=new float[1];
			rs[0]=n;
			System.out.println("��ֵһ�Σ�" + n);
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
				System.out.println("��ֵ" + (m - 1) + "�Σ�" + maxlimit
						+ " ,һ�����Ϊ:" + (k + offset) + "һ�����Ϊ:"
						+ (maxlimit - offset));
			} else if (k == 0) {
				rs=new float[m];
				for(int a=0;a<m;a++){
					rs[a]=maxlimit;
				}
				System.out.println("��ֵ" + m + "�Σ�" + maxlimit);
			} else {
				int k1=0;
				rs=new float[m+1];
				for(int a=0;a<m;a++){
					rs[a]=maxlimit;
					k1=a;
				}
				rs[k1+1]=k;
				System.out.println("��ֵ" + m + "�Σ�" + maxlimit + "  ��һ�����Ϊ:" + k);
			}
		}
		return rs;
	}
	
	/**
	 * ����ϵͳ��Ż�ȡ�û��Ƿ�����ֵ����
	 * @param userno
	 * @return
	 */
	public static String getPwdflag(String userno){
		return TaskChecker.USEREXT3.get(userno);
	}
	
	public static String PaiPaiSign(String url,HashMap<String, String> params,
			String type,String secretOAuthKey) throws Exception{
		//1���������URI·������URL����
		String enUrl=URLEncoder.encode(url);
//		System.out.println("encode���url:"+enUrl);
		//2��������sign��������в�����key�����ֵ���������,�����Ĳ���(key=value)��&ƴ������
		ArrayList<String> sort=new ArrayList<String>(params.keySet());
		Collections.sort(sort);
		String md5sign = "";
		for (String key : sort) {
			md5sign += (key +"="+ params.get(key))+"&";
		}
		md5sign=md5sign.substring(0,md5sign.length()-1);
//		System.out.println("�������ϲ���:"+md5sign);
		//3������2�еĽ������URL����
		String enParams=URLEncoder.encode(md5sign).replace("+", "%20");
		//4����HTTP����ʽ(GER or POST)����1���Լ���3���еĵ����ַ�����&ƴ������
		String sourceString=type+"&"+enUrl+"&"+enParams;
//		System.out.println("Դ��:"+sourceString);
		//5��������Կ
		secretOAuthKey=secretOAuthKey+"&";
		//6��ʹ��HMAC-SHA1�����㷨 ��44�еĵ���Դ���Լ�5�еõ�����Կ���м���,Ȼ�󽫼��ܺ���ַ�������Base64����
		String result=getBASE64(HmacSHA1Encrypt(sourceString,secretOAuthKey));
//		System.out.println("���:"+result);
		return result;
	}
	
	   /**  
     * ʹ�� HMAC-SHA1 ǩ�������Զ�encryptText����ǩ��  
     * @param encryptText ��ǩ�����ַ���  
     * @param encryptKey  ��Կ  
     * @return  
     * @throws Exception  
     */    
    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception     
    {           
        byte[] data=encryptKey.getBytes();  
        //���ݸ������ֽ����鹹��һ����Կ,�ڶ�����ָ��һ����Կ�㷨������  
        SecretKey secretKey = new SecretKeySpec(data, "HmacSHA1");   
        //����һ��ָ�� Mac �㷨 �� Mac ����  
        Mac mac = Mac.getInstance("HmacSHA1");   
        //�ø�����Կ��ʼ�� Mac ����  
        mac.init(secretKey);    
        byte[] text = encryptText.getBytes();    
        //��� Mac ����   
        return mac.doFinal(text);    
    } 
	
	/**
	 * �� s ���� BASE64 ���� 
	 * @param s
	 * @return
	 */
	public static String getBASE64(byte[] s) { 
	if (s == null) return null; 
	return (new BASE64Encoder()).encode(s); 
	} 
	 
	/**
	 * �� BASE64 ������ַ��� s ���н��� 
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
	 * �������
	 * @param startDate ��ʼ����
	 * @param endDate   ��������
	 * @return ��ʼ����--��������֮�������
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
	 * ���쾩�����ز���
	 * @param sign  md5ֵ
	 * @param yewu  ҵ��Map
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
	 * ��ȡ���ڰ��� ���һ��
	 * @return yyyy-MM-dd
	 */
	public static String getDateEnd(){
		// ��ȡCalendar
		Calendar calendar = Calendar.getInstance();
		// ����ʱ��,��ǰʱ�䲻������
		// calendar.setTime(new Date());
		// ��������Ϊ�����������
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

		// ��ӡ
		return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	}

}
