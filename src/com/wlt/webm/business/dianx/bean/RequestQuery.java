package com.wlt.webm.business.dianx.bean;

import java.io.ByteArrayOutputStream;
import com.wlt.webm.uictool.Constant;
import com.wlt.webm.uictool.Tools;
import com.commsoft.epay.util.logging.Log;

public class RequestQuery {

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
	 * ������
	 */
	private String operCode = "";

	/**
	 * ��ѯ��Ϣ���
	 * @return ���ز�ѯ��Ϣ��
	 */
	public byte[] queryMsg(){
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try{
			//��ͷ��Ϣ
			bout.write(Constant.BAG_HEAD);
			bout.write(serialNo);
			bout.write(bagLength);
			bout.write(Constant.QUERY_TYPE);
			bout.write("1".getBytes());//���Ͻṹ
			bout.write("0".getBytes());//��У��
			bout.write("01".getBytes());//��������
			bout.write(Constant.SEND_ID);
			bout.write(Constant.RECEIVE_ID);
			bout.write(Constant.BLANK_SPACE);
			bout.write(Constant.CHECK_INFO);
			//����Ϊ������Ϣ
			bout.write("708".getBytes());
			bout.write("001".getBytes());
			bout.write("06".getBytes());
			
			bout.write("7081".getBytes());
			bout.write("0004".getBytes());
			bout.write(Constant.AGENT_CODE.getBytes());
			
			bout.write("7082".getBytes());
			bout.write(Tools.add0(String.valueOf(Constant.AGENT_PASSWORD.length()), 4).getBytes());
			bout.write(Constant.AGENT_PASSWORD.getBytes());
			
			bout.write("7083".getBytes());
			bout.write(Tools.add0(String.valueOf(termPhoneNum.trim().length()),4).getBytes());
			bout.write(termPhoneNum.trim().getBytes());
			
			bout.write("7084".getBytes());
			bout.write("0006".getBytes());
			bout.write(operCode.getBytes());//6λ������--��ͬ�ɷ�
			
			bout.write("7085".getBytes());
			bout.write(Tools.add0(String.valueOf(accountNo.trim().length()), 4).getBytes());
			bout.write(accountNo.trim().getBytes());
			
//			bout.write("7092".getBytes());
//			bout.write("0000".getBytes());
//			bout.write(Constant.NUMBER_TYPE_SJ.getBytes());
			
			bout.write("7093".getBytes());
			bout.write(Tools.add0(String.valueOf(Constant.AGENT_IP.length()),4).getBytes());
			bout.write(Constant.AGENT_IP.getBytes());
			
		}catch(Exception e){
			Log.error("��ѯ����쳣");
			Log.error(e);
		}
//		System.out.println("��ѯ����:"+ new String(bout.toByteArray()));
//		System.out.println(new String(bout.toByteArray()).length());
		return bout.toByteArray();
	}
	
	/**
	 * �����ܳ���
	 * @param bagLength
	 */
	public void setBagLength(int length) {
		this.bagLength = Tools.add0(String.valueOf(Constant.BAG_LENGTH_MAC + length + 98 + Constant.AGENT_IP.length()),8).getBytes();
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
	 * �����˺�
	 * @param accountNo
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * ������
	 * @param operCode
	 */
	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}
}
