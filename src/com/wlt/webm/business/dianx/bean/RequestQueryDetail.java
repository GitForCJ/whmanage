package com.wlt.webm.business.dianx.bean;

import java.io.ByteArrayOutputStream;
import com.wlt.webm.uictool.Constant;
import com.wlt.webm.uictool.MD5;
import com.wlt.webm.uictool.Tools;
import com.commsoft.epay.util.logging.Log;

public class RequestQueryDetail {

	/**
	 * ��ˮ��
	 */
	private byte[] serialNo = new byte[10];
	
	/**
	 * ���ܳ���
	 */
	private byte[] bagLength = new byte[8];
	
	/**
	 * �����˺�
	 */
	private String accountNo = "";
	
	/**
	 * ����
	 */
	private String areaCode = "";
	
	/**
	 * ����
	 */
	private String feeType = "";

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
			bout.write(Constant.QUERYDETAIL_TYPE);
			bout.write("1".getBytes());//���Ͻṹ
			bout.write("0".getBytes());//��У��
			bout.write("03".getBytes());//��������
			bout.write(Constant.SEND_ID);
			bout.write(Constant.RECEIVE_ID);
			bout.write(Constant.BLANK_SPACE);
			bout.write(Constant.CHECK_INFO);
			//����Ϊ������Ϣ
			bout.write("708".getBytes());
			bout.write("001".getBytes());
			bout.write("02".getBytes());
			
			bout.write("7081".getBytes());
			bout.write("0004".getBytes());
			bout.write(Constant.AGENT_CODE.getBytes());
			
			bout.write("7082".getBytes());
			bout.write(Tools.add0(String.valueOf(Constant.AGENT_PASSWORD.length()), 4).getBytes());
			bout.write(Constant.AGENT_PASSWORD.getBytes());
			
			bout.write("A00".getBytes());
			bout.write("001".getBytes());
			bout.write("01".getBytes());
			
			bout.write("A005".getBytes());
			bout.write(Tools.add0(String.valueOf(areaCode.trim().length()), 4).getBytes());
			bout.write(areaCode.getBytes());
			
			bout.write("A30".getBytes());
			bout.write("001".getBytes());
			bout.write("03".getBytes());
			
			bout.write("A105".getBytes());
			bout.write(Tools.add0(String.valueOf(accountNo.trim().length()), 4).getBytes());
			bout.write(accountNo.trim().getBytes());
			
			bout.write("A300".getBytes());
			bout.write("0001".getBytes());
			bout.write("0".getBytes());
			
			bout.write("A301".getBytes());
			bout.write("0001".getBytes());
			bout.write(feeType.trim().getBytes());

		}catch(Exception e){
			Log.error("��ѯ����쳣");
			Log.error(e);
		}
//		Log.info("��ѯ����:" + new String(bout.toByteArray()));
		System.out.println("��ѯ����:"+ new String(bout.toByteArray()));
		System.out.println(new String(bout.toByteArray()).length());
		return bout.toByteArray();
	}
	
	/**
	 * �����ܳ���
	 * @param bagLength
	 */
	public void setBagLength(int length) {
		this.bagLength = Tools.add0(String.valueOf(Constant.BAG_LENGTH_MAC + length + 110),8).getBytes();
	}

	/**
	 * ��ˮ��
	 * @param serialNo
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = Tools.add0(serialNo,10).getBytes();
	}

	/**
	 * �����˺�
	 * @param accountNo
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * ���ú�������
	 * @param areaCode
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @param feeType 
	 */
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
}
