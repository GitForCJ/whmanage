package com.wlt.webm.xunjie.bean;

import java.util.HashMap;

/**
 * ������ Company�����������Ƽ����޹�˾ Copyright: Copyright (c) 2013
 * 
 * @author caiSJ �������� 2013-5-28 version 1.0.0.0
 */
public class ResponseCodeSet {

	/**
	 * �����ַ�����
	 */
	public static HashMap responseCode = getCode();

	/**
	 * 
	 * @return
	 */
	public static HashMap getCode() {
		HashMap responseCode = new HashMap();
		responseCode.put("000", "�����ύ�ɹ�");
		responseCode.put("001", "�û�������");
		responseCode.put("002", "�����������");
		responseCode.put("003", "�û��ѽ���");
		responseCode.put("004", "����");
		responseCode.put("005", "�������Ѵ���");
		responseCode.put("006", "ϵͳ�쳣");
		responseCode.put("101", "���ݿ����");
		responseCode.put("201", "IP�ܾ�����");
		responseCode.put("202", "�����Ÿ�ʽ����");
		responseCode.put("203", "֪ͨURL����");
		responseCode.put("301", "���޴����ɷѲ�Ʒ");
		responseCode.put("303", "����ά��");
		responseCode.put("401", "ȱ�ٲ���");
		responseCode.put("402", "��������");
		responseCode.put("403", "��ֵ������");
		responseCode.put("404", "��ֵ�������");
		responseCode.put("405", "ǩ������");

		responseCode.put("501", "����ѯʧ��");
		responseCode.put("555", "����ѯ�ɹ�");
		responseCode.put("600", "�����Ų�����");
		responseCode.put("601", "��ֵ�ɹ�");
		responseCode.put("602", "��ֵʧ��");

		responseCode.put("603", "�������ڴ���");
		responseCode.put("610", "����״̬��ѯʧ��");
		return responseCode;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)  {
		
		System.out.println(ResponseCodeSet.responseCode.get("000"));
		System.out.println(ResponseCodeSet.responseCode.get("000"));

	}

}
