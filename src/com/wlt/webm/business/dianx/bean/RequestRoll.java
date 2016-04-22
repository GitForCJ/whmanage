package com.wlt.webm.business.dianx.bean;

import java.io.ByteArrayOutputStream;

import com.wlt.webm.uictool.Constant;
import com.wlt.webm.uictool.MD5;
import com.wlt.webm.uictool.Tools;
import com.commsoft.epay.util.logging.Log;

public class RequestRoll {

	/**
	 * ��ˮ��
	 */
	private byte[] serialNo = new byte[10];
	
	/**
	 * ���ܳ���
	 */
	private byte[] bagLength = new byte[8];
	
	/**
	 * �ն˵绰����
	 */
	private String termPhoneNum = "";
	
	/**
	 * �����˺�
	 */
	private String accountNo = "";
	
	/**
	 * ֧�����
	 */
	private String payFee = "";
	
	/**
	 * �ն���ˮ��
	 */
	private String messageSerialNo = "";
	
	/**
	 * �����Ľ�����ˮ��
	 */
	private String rollSerialNo = "";

	/**
	 * �������
	 * @return ���ط��������Ϣ
	 */
	public byte[] rollMsg(){
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try{
			//��ͷ��Ϣ
			bout.write(Constant.BAG_HEAD);
			bout.write(serialNo);
			bout.write(bagLength);
			bout.write(Constant.ROLL_TYPE);
			bout.write("1".getBytes());//���Ͻṹ
			bout.write("0".getBytes());//��У��
			bout.write("01".getBytes());//��������
			bout.write(Constant.SEND_ID);
			bout.write(Constant.RECEIVE_ID);
			bout.write(Constant.BLANK_SPACE);
			bout.write(Constant.CHECK_INFO);
			//������Ϣ
			bout.write("708".getBytes());
			bout.write("001".getBytes());
			bout.write("08".getBytes());
			
			bout.write("7081".getBytes());
			bout.write("0004".getBytes());
			bout.write(Constant.AGENT_CODE.getBytes());
			
			bout.write("7082".getBytes());
			bout.write(Tools.add0(String.valueOf(Constant.AGENT_PASSWORD.length()), 4).getBytes());
			bout.write(Constant.AGENT_PASSWORD.getBytes());
			
			bout.write("7083".getBytes());//����ʡ��ƽ̨��Э���޸�
			bout.write("0008".getBytes());
			bout.write("99990007".getBytes());
//			bout.write(Tools.add0(String.valueOf(termPhoneNum.trim().length()),4).getBytes());
//			bout.write(termPhoneNum.trim().getBytes());
			
			bout.write("7085".getBytes());
			bout.write(Tools.add0(String.valueOf(accountNo.trim().length()), 4).getBytes());
			bout.write(accountNo.trim().getBytes());
			
			bout.write("7086".getBytes());
			bout.write(Tools.add0(String.valueOf(payFee.trim().length()), 4).getBytes());
			bout.write(payFee.getBytes());
			
			bout.write("7087".getBytes());
			bout.write(Tools.add0(String.valueOf(rollSerialNo.trim().length()), 4).getBytes());
			bout.write(rollSerialNo.trim().getBytes());
			
			bout.write("7092".getBytes());
			bout.write("0000".getBytes());
			
//			bout.write("7093".getBytes());
//			bout.write("0000".getBytes());
//			bout.write(Tools.add0(String.valueOf(Constant.AGENT_IP.length()),4).getBytes());
//			bout.write(Constant.AGENT_IP.getBytes());
			
			bout.write("7095".getBytes());
			bout.write(Tools.add0(String.valueOf(messageSerialNo.length()), 4).getBytes());
			bout.write(messageSerialNo.getBytes());
			
		}catch(Exception e){
			Log.error("��������쳣");
			Log.error(e);
		}
//		Log.info("�������:" + new String(bout.toByteArray()));
		System.out.println("��������:" + new String(bout.toByteArray()));
		System.out.println(new String(bout.toByteArray()).length());
		return bout.toByteArray();
	
	}
	
	/**
	 * �����˺�
	 * @param accountNo
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * ������
	 * @param length
	 */
	public void setBagLength(int length) {
		System.out.println(length);
		this.bagLength = Tools.add0(String.valueOf(Constant.BAG_LENGTH_MAC + length + 164),8).getBytes();
	}

	/**
	 * ֧�����
	 * @param payFee
	 */
	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}

	/**
	 * ����������ˮ��
	 * @param rollSerialNo
	 */
	public void setRollSerialNo(String rollSerialNo) {
		this.rollSerialNo = rollSerialNo;
	}

	/**
	 * ��ˮ��
	 * @param serialNo
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = Tools.add0(serialNo,10).getBytes();
	}

	/**
	 * �ն˵绰����
	 * @param termPhoneNum
	 */
	public void setTermPhoneNum(String termPhoneNum) {
		this.termPhoneNum = termPhoneNum;
	}

	/**
	 * ��Ϣ��ˮ��
	 * @param messageSerialNo
	 */
	public void setMessageSerialNo(String messageSerialNo) {
		this.messageSerialNo = messageSerialNo;
	}
	
	
}
