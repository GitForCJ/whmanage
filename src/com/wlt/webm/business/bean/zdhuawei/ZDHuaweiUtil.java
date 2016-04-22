package com.wlt.webm.business.bean.zdhuawei;

import java.util.HashMap;
import java.util.Map;

public class ZDHuaweiUtil {

	public static Map<String, String> priviceName = new HashMap<String, String>();
	public static Map<String, String> priviceCodeName = new HashMap<String, String>();
	public static Map<String, String> priviceCodeZD = new HashMap<String, String>();

	/**
	 * ������Ӫ�����ʹ����ȡ��Ӧ����
	 */
	public static String getOperatorCode(String operator) {
		if (operator != null) {// 0 �ǵ��� 1�ƶ� 2��ͨ
			if (operator.equals("����"))
				return "0";
			else if (operator.equals("�ƶ�"))
				return "1";
			else if (operator.equals("��ͨ"))
				return "2";
			else
				return null;
		} else {
			return null;
		}
	}
	/**
	 * ת������������ �������(200�ɹ���201ʧ��, 202�޴˶�����, 203�޳�ֵ��¼)
	 * ��Ӧ0/2/other
	 * @param status
	 * @return
	 */
	public static  String getQuerySetatus(String status) {
		if (status.equals("0")) {// ���ٳɹ�
			return "200";
		} else if (status.equals("2")) {// �޴˶���
			return "202";
		} else {// ʧ��
			return "201";
		}
	}
	/**
	 * ����ʱ�䣬20160303161249
	 * 
	 * @return
	 */
	public static String getFormateTime(String time) {
		String str = null;
		try {
			str = time.substring(0, 4) + "-" + time.substring(4, 6) + "-"
					+ time.substring(6, 8) + " " + time.substring(8, 10) + ":"
					+ time.substring(10, 12) + ":" + time.substring(12, 14);
		} catch (Exception e) {
			System.err.println("ת�����ڸ�ʽ�쳣");
		}
		return str;
	}
	/**
	 * ������Ӫ�����ʹ����ȡ��Ӧ����
	 */
	public static String getOperator(String operatorCode) {
		if (operatorCode != null) {// 0 �ǵ��� 1�ƶ� 2��ͨ
			if (operatorCode.equals("0"))
				return "����";
			else if (operatorCode.equals("1"))
				return "�ƶ�";
			else if (operatorCode.equals("2"))
				return "��ͨ";
			else
				return null;
		} else {
			return null;
		}
	}
	/**
	 * ��ֵ״̬��ѯת�� 1020��ֵ��,1021�ɹ�,1022ʧ�� �ֱ��Ӧ0/1/other
	 * 
	 * @param status
	 * @return
	 */
	public static String getChgStatus(String status) {
		if (status.equals("0")) {// �ɹ�
			return "1021";
		} else if (status.equals("1")) {// ʧ��
			return "1022";
		} else {// ��ֵ��
			return "1020";
		}

	}
	static {
		priviceName.put("���ɹ�", "10");
		priviceName.put("����", "11");
		priviceName.put("���", "13");
		priviceName.put("ɽ��", "17");
		priviceName.put("�ӱ�", "18");
		priviceName.put("ɽ��", "19");
		priviceName.put("����", "22");
		priviceName.put("����", "30");
		priviceName.put("�Ϻ�", "31");
		priviceName.put("����", "34");
		priviceName.put("�㽭", "36");
		priviceName.put("����", "38");
		priviceName.put("����", "50");
		priviceName.put("�㶫", "51");
		priviceName.put("����", "59");
		priviceName.put("�ຣ", "70");
		priviceName.put("����", "71");
		priviceName.put("����", "74");
		priviceName.put("����", "75");
		priviceName.put("����", "76");
		priviceName.put("����", "79");
		priviceName.put("�Ĵ�", "81");
		priviceName.put("����", "83");
		priviceName.put("����", "84");
		priviceName.put("����", "85");
		priviceName.put("����", "86");
		priviceName.put("����", "87");
		priviceName.put("����", "88");
		priviceName.put("�½�", "89");
		priviceName.put("����", "90");
		priviceName.put("����", "91");
		priviceName.put("������", "97");
		// ////////////////////////////////////
		priviceCodeName.put("10", "���ɹ�");
		priviceCodeName.put("11", "����");
		priviceCodeName.put("13", "���");
		priviceCodeName.put("17", "ɽ��");
		priviceCodeName.put("18", "�ӱ�");
		priviceCodeName.put("19", "ɽ��");
		priviceCodeName.put("22", "����");
		priviceCodeName.put("30", "����");
		priviceCodeName.put("31", "�Ϻ�");
		priviceCodeName.put("34", "����");
		priviceCodeName.put("36", "�㽭");
		priviceCodeName.put("38", "����");
		priviceCodeName.put("50", "����");
		priviceCodeName.put("51", "�㶫");
		priviceCodeName.put("59", "����");
		priviceCodeName.put("70", "�ຣ");
		priviceCodeName.put("71", "����");
		priviceCodeName.put("74", "����");
		priviceCodeName.put("75", "����");
		priviceCodeName.put("76", "����");
		priviceCodeName.put("79", "����");
		priviceCodeName.put("81", "�Ĵ�");
		priviceCodeName.put("83", "����");
		priviceCodeName.put("84", "����");
		priviceCodeName.put("85", "����");
		priviceCodeName.put("86", "����");
		priviceCodeName.put("87", "����");
		priviceCodeName.put("88", "����");
		priviceCodeName.put("89", "�½�");
		priviceCodeName.put("90", "����");
		priviceCodeName.put("91", "����");
		priviceCodeName.put("97", "������");
		// ////////////////////////////////////
		priviceCodeZD.put("����", "1");
		priviceCodeZD.put("�Ϻ�", "2");
		priviceCodeZD.put("���", "3");
		priviceCodeZD.put("����", "4");
		priviceCodeZD.put("�ӱ�", "130000");
		priviceCodeZD.put("ɽ��", "140000");
		priviceCodeZD.put("���ɹ�", "150000");
		priviceCodeZD.put("����", "210000");
		priviceCodeZD.put("����", "220000");
		priviceCodeZD.put("������", "230000");
		priviceCodeZD.put("����", "320000");
		priviceCodeZD.put("�㽭", "330000");
		priviceCodeZD.put("����", "340000");
		priviceCodeZD.put("����", "350000");
		priviceCodeZD.put("����", "360000");
		priviceCodeZD.put("ɽ��", "370000");
		priviceCodeZD.put("����", "410000");
		priviceCodeZD.put("����", "420000");
		priviceCodeZD.put("����", "430000");
		priviceCodeZD.put("�㶫", "440000");
		priviceCodeZD.put("����", "450000");
		priviceCodeZD.put("����", "460000");
		priviceCodeZD.put("�Ĵ�", "510000");
		priviceCodeZD.put("����", "520000");
		priviceCodeZD.put("����", "530000");
		priviceCodeZD.put("����", "540000");
		priviceCodeZD.put("����", "610000");
		priviceCodeZD.put("����", "620000");
		priviceCodeZD.put("�ຣ", "630000");
		priviceCodeZD.put("����", "640000");
		priviceCodeZD.put("�½�", "650000");
		priviceCodeZD.put("̨��", "710000");
		priviceCodeZD.put("���", "810000");
		priviceCodeZD.put("����", "820000");
		priviceCodeZD.put("ȫ��", "5");
	}
}
