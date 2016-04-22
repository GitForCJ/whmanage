package com.wlt.webm.business.dianx.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class TelcomForm extends ActionForm{
	/**
	 * ��������
	 */
	private String payType;
	/**
	 * �̻�����
	 */
	private String tradeObject;
	/**
	 * ��Ϣ
	 */
	private String message;
	/**
	 * �û�����
	 */
	private String userName;
	/**
	 * �û����
	 */
	private String totalFee;
	/**
	 * �Ʒ�����
	 */
	private String billList;
	/**
	 * ������ˮ��
	 */
	private String seqNo;
	/**
	 * ���׽��
	 */
	private String payFee;
	/**
	 * ���׷�����
	 */
	private String state;
	
	/**
	 * ��������
	 */
	private String numType;
	/**
	 * ����
	 */
	private String areaCode;
	/**
	 * ��ֵ��ˮ��
	 */
	private String serial;
	/**
	 * @return ���׽��
	 */ 
	public String getPayFee() {
		return payFee;
	}

	/**
	 * @param payFee ���׽��
	 */
	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}

	/**
	 * @return �̻�����
	 */
	public String getTradeObject() {
		return tradeObject;
	}
	
	/**
	 * @param tradeObject �̻�����
	 */
	public void setTradeObject(String tradeObject) {
		this.tradeObject = tradeObject;
	}

	/**
	 * @return ��Ϣ
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message ��Ϣ
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return �˵���Ϣ�б�
	 */
	public String getBillList() {
		return billList;
	}

	/**
	 * @param billList �˵���Ϣ�б�
	 */
	public void setBillList(String billList) {
		this.billList = billList;
	}

	/**
	 * @return ������ˮ��
	 */
	public String getSeqNo() {
		return seqNo;
	}

	/**
	 * @param seqNo ������ˮ��
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * @return �ۼƳ�ֵ���
	 */
	public String getTotalFee() {
		return totalFee;
	}

	/**
	 * @param totalFee �ۼƳ�ֵ���
	 */
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	/**
	 * @return �û�����
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName �û�����
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return state ���׷�����
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state ���׷�����
	 */
	public void setState(String state) {
		this.state = state;
	}

	public String getNumType() {
		return numType;
	}

	public void setNumType(String numType) {
		this.numType = numType;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	
}
