package com.wlt.webm.business.form;

import org.apache.struts.action.ActionForm;

/**
 * ��ͨ������Ϣ
 */
public class JtfkForm extends ActionForm
{
	
	/**
	 * ���ƺ�
	 */
	private String vehicle ;

	/**
	 * ��������
	 */
	private String vehicleType;

	/**
	 * ���ܺ�
	 */
	private String frameNo;

	/**
	 * ����
	 */
	private String area;
	
	private String violationId	;//	�����̲��Υ�µ���
	private String violationTime;//Υ��ʱ�䣬��ʽ�磺2011-12-01 11:03:05
	private String viloationLocation;//	200	Υ�µص�
	private String totalFee	;//	��Υ�µ����ʼķ�֮��ķ����ܺͣ��̻���Ҫ����øý���λΪ�֣�
	private String fineFee	;//	�������λΪ�֣�
	private String dealFee	;//	�������λΪ�֣�
	private String lateFee	;//	���ɽ𣨵�λΪ�֣�
	private String dealTime	;//	�������ڣ���λΪ�죩
	private String otherWay	;//	����������ʽ
	private String policeContact	;//	���ؽ�����ϵ��ʽ
	private String policeAddress	;//	���ؽ�����ַ
	private String viloationDetail	;//	Υ��ϸ��
	private String dealFlag	;//	�ܷ�����0������������1����������
	private String dealMsg	;//	DealFlagΪ0����䲻�ܰ�����ԭ��
	private String liveBill	;//	�Ƿ��ֳ�����0�����ǣ�1����
	private String referFee	;//	�ο����ۣ���λΪ�֣�
	private String wsh	;//	�����
	private String phone;
	private String detailUrl ;
	
	public String getDetailUrl() {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("violationId="+violationId);
		sBuffer.append("&violationTime="+violationTime);
		sBuffer.append("&viloationLocation="+viloationLocation);
		sBuffer.append("&totalFee="+totalFee);
		sBuffer.append("&fineFee="+fineFee);
		sBuffer.append("&dealFee="+dealFee);
		sBuffer.append("&lateFee="+lateFee);
		sBuffer.append("&dealTime="+dealTime);
		sBuffer.append("&otherWay="+otherWay);
		sBuffer.append("&policeContact="+policeContact);
		sBuffer.append("&policeAddress="+policeAddress);
		sBuffer.append("&viloationDetail="+viloationDetail);
		sBuffer.append("&dealFlag="+dealFlag);
		sBuffer.append("&dealMsg="+dealMsg);
		sBuffer.append("&dealFlag="+liveBill);
		sBuffer.append("&dealMsg="+referFee);
		sBuffer.append("&dealFlag="+wsh);
		return sBuffer.toString();
	}
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getFrameNo() {
		return frameNo;
	}

	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getViolationId() {
		return violationId;
	}

	public void setViolationId(String violationId) {
		this.violationId = violationId;
	}

	public String getViolationTime() {
		return violationTime;
	}

	public void setViolationTime(String violationTime) {
		this.violationTime = violationTime;
	}

	public String getViloationLocation() {
		return viloationLocation;
	}

	public void setViloationLocation(String viloationLocation) {
		this.viloationLocation = viloationLocation;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getFineFee() {
		return fineFee;
	}

	public void setFineFee(String fineFee) {
		this.fineFee = fineFee;
	}

	public String getDealFee() {
		return dealFee;
	}

	public void setDealFee(String dealFee) {
		this.dealFee = dealFee;
	}

	public String getLateFee() {
		return lateFee;
	}

	public void setLateFee(String lateFee) {
		this.lateFee = lateFee;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public String getOtherWay() {
		return otherWay;
	}

	public void setOtherWay(String otherWay) {
		this.otherWay = otherWay;
	}

	public String getPoliceContact() {
		return policeContact;
	}

	public void setPoliceContact(String policeContact) {
		this.policeContact = policeContact;
	}

	public String getPoliceAddress() {
		return policeAddress;
	}

	public void setPoliceAddress(String policeAddress) {
		this.policeAddress = policeAddress;
	}

	public String getViloationDetail() {
		return viloationDetail;
	}

	public void setViloationDetail(String viloationDetail) {
		this.viloationDetail = viloationDetail;
	}

	public String getDealFlag() {
		return dealFlag;
	}

	public void setDealFlag(String dealFlag) {
		this.dealFlag = dealFlag;
	}

	public String getDealMsg() {
		return dealMsg;
	}

	public void setDealMsg(String dealMsg) {
		this.dealMsg = dealMsg;
	}

	public String getLiveBill() {
		return liveBill;
	}

	public void setLiveBill(String liveBill) {
		this.liveBill = liveBill;
	}

	public String getReferFee() {
		return referFee;
	}

	public void setReferFee(String referFee) {
		this.referFee = referFee;
	}

	public String getWsh() {
		return wsh;
	}

	public void setWsh(String wsh) {
		this.wsh = wsh;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

}