package com.wlt.webm.business.dianx.bean;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.commsoft.epay.util.logging.Log;

/**
 * <p>
 * Title:���Ӵ���ƽ̨
 * </p>
 * <p>
 * Description: ��ѯ�ӿ�ҵ�����̴�����
 * </p>
 * <p>
 * create: 2009-9-25
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: ���������Ƽ����޹�˾
 * </p>
 * 
 * @author shenyijie
 * @version 3.0.0.0
 * 
 */
public class ResponseQueryDetail {

	/**
	 * ���ص�������Ϣ
	 */
	private byte[] recvMsg;

	/**
	 * ����������
	 */
	private DataInputStream input;

	/**
	 * ��ͷ
	 */
	private byte[] head = new byte[4];

	/**
	 * ���ı�ʶ��
	 */
	private byte[] bagFlag = new byte[10];

	/**
	 * ���ܳ���
	 */
	private byte[] bagLength = new byte[8];

	/**
	 * ��������
	 */
	private byte[] bagType = new byte[8];

	/**
	 * �ṹ��ʶ
	 */
	private byte[] structFlag = new byte[1];

	/**
	 * У���ʶ
	 */
	private byte[] checkFlag = new byte[1];

	/**
	 * ��������
	 */
	private byte[] paramsNum = new byte[2];

	/**
	 * ����ϵͳID
	 */
	private byte[] sendID = new byte[2];

	/**
	 * ����ϵͳID
	 */
	private byte[] receiveID = new byte[2];

	/**
	 * Ԥ���ռ�
	 */
	private byte[] blankArea = new byte[26];

	/**
	 * ��Ϣ������
	 */
	private byte[] checkCode = new byte[8];

	/**
	 * ����������
	 */
	private byte[] responseCode;

	/**
	 * ������Ϣ˵��
	 */
	private byte[] responseInfo;

	/**
	 * ����
	 */
	private String fee;

	/**
	 * ���ڱ�ʶ
	 */
	private String accountFlagAll;

	/**
	 * �������ڱ�ʶ
	 */
	private byte[] accountFlag;

	/**
	 * �û���
	 */
	private String userName;

	/**
	 * �Ƿ�ɱ�����ͷ
	 */
	private String flag;

	/**
	 * ��Ʊ�����
	 */
	private byte[] invoiceCode;

	/**
	 * ��Ʊ������
	 */
	private byte[] invoiceName;

	/**
	 * ����
	 */
	private byte[] number;

	/**
	 * ��Ʊ�����
	 */
	private byte[] invoiceFee;

	/**
	 * ��Ӫ�̴���
	 */
	private byte[] agentCode;

	/**
	 * ��Ʊ��ϸ��Ϣ
	 */
	private HashMap detailMap;

	/**
	 * ����
	 */
	private ArrayList date;

	/**
	 * ������ʼʱ��
	 */
	private String accountBegin = "99999999";

	/**
	 * ���ڽ���ʱ��
	 */
	private String accountEnd = "00000000";

	/**
	 * ����
	 */
	private String areaCode;

	/**
	 * MBOSS����
	 */
	// private static ArrayList listAreaCode;
	/**
	 * Ĭ�Ϲ��캯��
	 * 
	 */
	public ResponseQueryDetail() {

	}

	public ResponseQueryDetail(byte[] receiveMsg, String areaCode) {
		this.areaCode = areaCode;
		this.recvMsg = receiveMsg;
		if (recvMsg != null) {
			input = new DataInputStream(new ByteArrayInputStream(recvMsg));
			deal();
		}
	}

	/**
	 * �������յ��ļƷ���Ϣ
	 * 
	 */
	public void deal() {
		try {
			// ������ͷ��Ϣ
			input.read(head);
			input.read(bagFlag);
			input.read(bagLength);
			input.read(bagType);
			input.read(structFlag);
			input.read(checkFlag);
			input.read(paramsNum);
			input.read(sendID);
			input.read(receiveID);
			input.read(blankArea);
			input.read(checkCode);
			// ����������Ϣ
			input.read(new byte[8]);// 000

			input.read(new byte[4]);
			byte[] tem = new byte[4];
			input.read(tem);
			responseCode = new byte[Integer.parseInt(new String(tem).trim())];
			input.read(responseCode);// ����������
			Log.info("-----���������룺" + new String(responseCode));

			input.read(new byte[4]);
			byte[] temp1 = new byte[4];
			input.read(temp1);
			responseInfo = new byte[Integer.parseInt(new String(temp1).trim())];
			input.read(responseInfo);// ������˵��
			Log.info("----������˵����" + new String(responseInfo));

			if (Integer.parseInt(new String(paramsNum)) > 1) {

				String[] temp = new String[8];
				int index = 0;
				input.read(new byte[8]);// A31

				byte[] b1 = new byte[4];
				input.read(b1);
				temp[index++] = new String(b1).trim();
				byte[] temp2 = new byte[4];
				input.read(temp2);
				byte[] fee1 = new byte[Integer.parseInt(new String(temp2)
						.trim())];
				input.read(fee1);// ��������Ƿ���ܺ�
				temp[index++] = new String(fee1).trim();

				// if (!listAreaCode.contains(areaCode.trim())) {
				byte[] b2 = new byte[4];
				input.read(b2);
				temp[index++] = new String(b2).trim();
				byte[] temp3 = new byte[4];
				input.read(temp3);
				byte[] accountFlagAll1 = new byte[Integer.parseInt(new String(
						temp3).trim())];
				input.read(accountFlagAll1);// ���ڱ�ʶ
				temp[index++] = new String(accountFlagAll1).trim();
				// }
				byte[] b3 = new byte[4];
				input.read(b3);
				temp[index++] = new String(b3).trim();
				byte[] temp4 = new byte[4];
				input.read(temp4);
				byte[] userName1 = new byte[Integer.parseInt(new String(temp4)
						.trim())];
				input.read(userName1);// �û���
				temp[index++] = new String(userName1).trim();

				byte[] b4 = new byte[4];
				input.read(b4);
				temp[index++] = new String(b4).trim();
				byte[] temp5 = new byte[4];
				input.read(temp5);
				byte[] flag1 = new byte[Integer.parseInt(new String(temp5)
						.trim())];
				input.read(flag1);// �Ƿ�ɱ�����ͷ
				temp[index] = new String(flag1).trim();

				for (int i = 0; i < temp.length; i += 2) {
					if (temp[i].equals("A310")) {
						fee = temp[i + 1];
					} else if (temp[i].equals("A311")) {
						accountFlagAll = temp[i + 1];
					} else if (temp[i].equals("A312")) {
						userName = temp[i + 1];
					} else if (temp[i].equals("A313")) {
						flag = temp[i + 1];
					}
				}
			}

			// ͨ������������ж�A32���Ƿ���ڣ������Ƿ�ѣ��ƷѲ�����A32��
			if (Integer.parseInt(new String(paramsNum)) == 3) {
				input.read(new byte[3]);
				byte[] temp6 = new byte[3];
				input.read(temp6);
				input.read(new byte[2]);
				int column = Integer.parseInt(new String(temp6).trim());

				detailMap = new HashMap();
				date = new ArrayList();
				String name = "";
				String value = "";
				byte[] accountDate;

				// ��������������ѭ��
				for (int i = 0; i < column; i++) {

					input.read(new byte[4]);
					byte[] temp7 = new byte[4];
					input.read(temp7);
					invoiceCode = new byte[Integer.parseInt(new String(temp7)
							.trim())];
					input.read(invoiceCode);

					if (!new String(invoiceCode).equals("0")) {// �����Ʊ����벻Ϊ0��
																// ���ʾ��ϸ��Ϣ
						input.read(new byte[4]);
						byte[] temp8 = new byte[4];
						input.read(temp8);
						invoiceName = new byte[Integer.parseInt(new String(
								temp8).trim())];
						input.read(invoiceName);
						name = new String(invoiceName);// ��Ʊ������

						input.read(new byte[4]);
						byte[] temp9 = new byte[4];
						input.read(temp9);
						number = new byte[Integer.parseInt(new String(temp9)
								.trim())];
						input.read(number);// ����

						input.read(new byte[4]);
						byte[] temp10 = new byte[4];
						input.read(temp10);
						invoiceFee = new byte[Integer.parseInt(new String(
								temp10).trim())];
						input.read(invoiceFee);
						value = new String(invoiceFee);// ��Ʊ�����

						if (detailMap.containsKey(name)) { // �������ͬ����ϸ���ƣ�����ֵ�ϲ�
							String v = (String) detailMap.get(name);
							value = String.valueOf(Integer.parseInt(value)
									+ Integer.parseInt(v));
						}
						detailMap.put(name, value);

						input.read(new byte[4]);
						byte[] temp11 = new byte[4];
						input.read(temp11);
						accountFlag = new byte[Integer.parseInt(new String(
								temp11).trim())];
						input.read(accountFlag);// ���ڱ�ʶ

						String[] str = new String(accountFlag).split(":");
						if (str.length == 1) {
							if (accountBegin.compareTo(str[0]) <= 0) {

							} else {
								accountBegin = str[0];
							}
						} else if (str.length == 2) {
							if (accountBegin.compareTo(str[0]) <= 0) {

							} else {
								accountBegin = str[0];
							}
							if (accountEnd.compareTo(str[1]) >= 0) {

							} else {
								accountEnd = str[1];
							}
						}

						input.read(new byte[4]);
						byte[] temp12 = new byte[4];
						input.read(temp12);
						agentCode = new byte[Integer
								.parseInt(new String(temp12).trim())];
						input.read(agentCode);// ��Ӫ�̴���

					} else {// �����Ʊ�����Ϊ0�����ʾ���⺬�壺���ڵ���ֹʱ��

						input.read(new byte[4]);
						byte[] temp13 = new byte[4];
						input.read(temp13);
						input.read(new byte[Integer
								.parseInt(new String(temp13))]);// ��Ʊ������

						input.read(new byte[4]);
						byte[] temp14 = new byte[4];
						input.read(temp14);
						accountDate = new byte[Integer.parseInt(new String(
								temp14))];
						input.read(accountDate);// ����--��ʼʱ��
						// date.add(new String(accountDate));

						if (accountBegin.compareTo(new String(accountDate)) <= 0) {

						} else {
							accountBegin = new String(accountDate);
						}

						input.read(new byte[4]);
						byte[] temp15 = new byte[4];
						input.read(temp15);
						accountDate = new byte[Integer.parseInt(new String(
								temp15))];
						input.read(accountDate);// ��Ʊ�����--����ʱ��
						// date.add(new String(accountDate));

						if (accountEnd.compareTo(new String(accountDate)) >= 0) {

						} else {
							accountEnd = new String(accountDate);
						}

						input.read(new byte[4]);
						byte[] temp16 = new byte[4];
						input.read(temp16);
						input.read(new byte[Integer
								.parseInt(new String(temp16))]);

						input.read(new byte[4]);
						byte[] temp17 = new byte[4];
						input.read(temp17);
						input.read(new byte[Integer
								.parseInt(new String(temp17))]);
					}
				}
			}

		} catch (Exception e) {
			Log.error("�����Ʒѷ��صĲ�ѯ��Ϣ����");
			Log.error(e);
		}
	}

	public static void main(String[] args) {
		System.out.println("FFFF000731045800001555INF0033110030080000000000000000000000000001111111100000102".length());
		String mes = "FFFF000731045800001555INF00331100300800000000000000000000000000011111111000"
				+ "0010200010004000000020004�ɹ�A3100104A31000048269A31100010A3120005*����A3130000A32014"
				+ "06A321000810000001A3220008�ײͷ���A105001118929208898A32300044000A311001720120501:20120531A32"
				+ "400010A321000810000102A3220020�»�����"
				+ "(�ײ������)A105001118929208898A3230003500A311001720120501:20120531A32400010A321000810"
				+ "000103A3220018ͨ�ŷ�(�ײ������)A105001118929208898A32300047375A311001720120501:20120531"
				+ "A32400010A321000810000009A3220008�Ż�����A105001118929208898A3230005-4105A3110017"
				+ "20120501:20120531A32400010A321000810000012A3220008�Ѹ�����"
				+ "A105001118929208898A3230005-4000A311001720120501:20120531A32400010A321000810000011"
				+ "A3220008���շ���A105001118929208898A32300042100A311001720120501:20120531A32400010A321"
				+ "000510905A3220006Ԥ����A105001118929208898A3230005-1590A311001720120501:20120531A32400010A"
				+ "321000510902A3220006ΥԼ��A105001118929208898A323000212A311001720120501:20120531A32400010" +
						"A321000810000001A3220008�ײͷ���A105001118929208898A32300044000A311001720120601:20120630" +
						"A32400010A321000810000102A3220020�»�����(�ײ������)A105001118929208898A3230003500A31100172" +
						"0120601:20120630A32400010A321000810000103A3220018ͨ�ŷ�(�ײ������)A105001118929208898A3230004348" +
						"3A311001720120601:20120630A32400010A321000810000009A3220008�Ż�����A105001118929208898A3230005-" +
						"2506A311001720120601:20120630A32400010A321000810000012A3220008�Ѹ�����A105001118929208898A3230005-30" +
						"00A311001720120601:20120630A32400010A321000810000011A3220008���շ���A105001118929208898A32300041500A" +
						"311001720120601:20120630A32400010";
		ResponseQueryDetail del=new ResponseQueryDetail(mes.getBytes(),"0755");
		
	}

	/**
	 * ����������
	 * 
	 * @return
	 */
	public String getResponseCode() {
		return new String(responseCode).trim();
	}

	/**
	 * ������˵��
	 * 
	 * @return
	 */
	public String getResponseInfo() {
		return new String(responseInfo).trim();
	}

	/**
	 * �����̱��
	 * 
	 * @return
	 */
	public String getAgentCode() {
		return new String(agentCode).trim();
	}

	/**
	 * ��Ʊ����
	 * 
	 * @return
	 */
	public String getInvoiceFee() {
		return new String(invoiceFee).trim();
	}

	/**
	 * ����
	 * 
	 * @return
	 */
	public String getNumber() {
		return new String(number).trim();
	}

	/**
	 * ��Ʊ����
	 * 
	 * @return
	 */
	public String getInvoiceName() {
		return new String(invoiceName).trim();
	}

	/**
	 * ��Ʊ���
	 * 
	 * @return
	 */
	public String getInvoiceCode() {
		return new String(invoiceCode).trim();
	}

	/**
	 * �Ƿ�ɱ�����ͷ
	 * 
	 * @return
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * �û���
	 * 
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * ���ڱ�ʶ
	 * 
	 * @return
	 */
	public String getAccountFlag() {
		return new String(accountFlag).trim();
	}

	/**
	 * ����
	 * 
	 * @return
	 */
	public String getFee() {
		return fee;
	}

	/**
	 * ��ȡ��Ʊ��ϸ��Ϣ
	 * 
	 * @return
	 */
	public HashMap getDetailMap() {
		return detailMap;
	}

	/**
	 * ��ȡ������Ϣ
	 * 
	 * @return
	 */
	public ArrayList getDate() {
		return date;
	}

	/**
	 * ��ȡ���������
	 * 
	 * @return
	 */
	public int getParamsNum() {
		return Integer.parseInt(new String(paramsNum).trim());
	}

	/**
	 * ��ȡ���ڿ�ʼʱ��
	 * 
	 * @return
	 */
	public String getAccountBegin() {
		return accountBegin;
	}

	/**
	 * ��ȡ���ڽ���ʱ��
	 * 
	 * @return
	 */
	public String getAccountEnd() {
		return accountEnd;
	}

	/**
	 * ��ȡ����
	 * 
	 * @return
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * MBoss����
	 */
	/*
	 * static{ // ���ݡ����ڡ���ݸ����ɽ�����š��麣����Զ��÷�ݡ��عء��Ƹ� listAreaCode.add("020");//����
	 * listAreaCode.add("0755");//���� listAreaCode.add("0769");//��ݸ
	 * listAreaCode.add("0757");//��ɽ listAreaCode.add("0750");//����
	 * listAreaCode.add("0756");//�麣 listAreaCode.add("0763");//��Զ
	 * listAreaCode.add("0753");//÷�� listAreaCode.add("0751");//�ع�
	 * listAreaCode.add("0766");//�Ƹ� }
	 */
}
