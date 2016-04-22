package com.wlt.webm.business.dianx.bean;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import com.commsoft.epay.util.logging.Log;

public class ResponseRoll {

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
	private byte[] responseCode = new byte[4];
	
	/**
	 * ������Ϣ˵��
	 */
	private byte[] responseInfo;
	
	/**
	 * �˺����
	 */
	private byte[] leftFee ;
	
	/**
	 * �����˺�
	 */
	private byte[] accountNo ;
	
	/**
	 * �˺�����
	 */
	private byte[] accountType ;
	
	/**
	 * ֧�����
	 */
	private byte[] payFee ;
	
	/**
	 * �������˺����
	 */
	private byte[] agentAccount ;
	
	/**
	 * Ĭ�Ϲ��캯��
	 *
	 */
	public ResponseRoll(){
		
	}
	
	/**
	 * ���캯��
	 */
	public ResponseRoll(byte[] receiveMsg){
		this.recvMsg = receiveMsg;
		if( recvMsg != null){
			input = new DataInputStream(new ByteArrayInputStream(recvMsg));
			deal();
		}
	}
	
	/**
	 * �����յ��ĳ���������Ϣ
	 *
	 */
	public void deal(){
		try{
			//������ͷ��Ϣ
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
			//����������Ϣ
			input.read(new byte[8]);//000
			
			input.read(new byte[8]);
			input.read(responseCode);//����������
			
			input.read(new byte[4]);
			byte[] temp1 = new byte[4];
			input.read(temp1);
			responseInfo = new byte[Integer.parseInt(new String(temp1).trim())];
			input.read(responseInfo);//������˵��
			
			if(Integer.parseInt(new String(paramsNum)) > 1){
				
				input.read(new byte[8]);//708
				
				input.read(new byte[4]);
				byte[] temp2 = new byte[4];
				input.read(temp2);
				accountNo = new byte[Integer.parseInt(new String(temp2).trim())];
				input.read(accountNo);//�����˺�
				
				input.read(new byte[4]);
				byte[] temp3 = new byte[4];
				input.read(temp3);
				payFee = new byte[Integer.parseInt(new String(temp3).trim())];
				input.read(payFee);//֧�����
				
//			input.read(new byte[4]);
//			byte[] temp5 = new byte[4];
//			input.read(temp5);
//			accountType = new byte[Integer.parseInt(new String(temp5).trim())];
//			input.read(accountType);//�˺�����
				
				input.read(new byte[4]);
				byte[] temp7 = new byte[4];
				input.read(temp7);
				agentAccount = new byte[Integer.parseInt(new String(temp7).trim())];
				input.read(agentAccount);//�������˺����
				
				input.read(new byte[4]);
				byte[] temp6 = new byte[4];
				input.read(temp6);
				leftFee = new byte[Integer.parseInt(new String(temp6).trim())];
				input.read(leftFee);//�˺Ž��׺����
			}
			
			
			
		}catch(Exception e){
			Log.error("�����Ʒѷ��صĲ�ѯ��Ϣ����");
			Log.error(e);
		}
	}

	/**
	 * �������˺����
	 * @return
	 */
	public String getAgentAccount() {
		return new String(agentAccount).trim();
	}

	/**
	 * ֧�����
	 * @return
	 */
	public String getPayFee() {
		return new String(payFee).trim();
	}

	/**
	 * �˺�����
	 * @return
	 */
	public String getAccountType() {
		return new String(accountType).trim();
	}

	/**
	 * �����˺�
	 * @return
	 */
	public String getAccountNo() {
		return new String(accountNo).trim();
	}

	/**
	 * �˺����
	 * @return
	 */
	public String getLeftFee() {
		return new String(leftFee).trim();
	}

	/**
	 * ������˵��
	 * @return
	 */
	public String getResponseInfo() {
		return new String(responseInfo).trim();
	}

	/**
	 * ����������
	 * @return
	 */
	public String getResponseCode() {
		return new String(responseCode).trim();
	}
}
