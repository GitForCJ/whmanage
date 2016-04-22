package com.wlt.webm.business.form;

import org.apache.struts.action.ActionForm;

/**
 * 交通罚款信息
 */
public class JtfkForm extends ActionForm
{
	
	/**
	 * 车牌号
	 */
	private String vehicle ;

	/**
	 * 车辆类型
	 */
	private String vehicleType;

	/**
	 * 车架号
	 */
	private String frameNo;

	/**
	 * 区域
	 */
	private String area;
	
	private String violationId	;//	代办商侧的违章单号
	private String violationTime;//违章时间，格式如：2011-12-01 11:03:05
	private String viloationLocation;//	200	违章地点
	private String totalFee	;//	该违章单除邮寄费之外的费用总和（商户需要计算好该金额，单位为分）
	private String fineFee	;//	罚款金额（单位为分）
	private String dealFee	;//	代办金额（单位为分）
	private String lateFee	;//	滞纳金（单位为分）
	private String dealTime	;//	代办周期（单位为天）
	private String otherWay	;//	其他处理方式
	private String policeContact	;//	当地交警联系方式
	private String policeAddress	;//	当地交警地址
	private String viloationDetail	;//	违章细节
	private String dealFlag	;//	能否处理：0，不能受理；1，可以受理
	private String dealMsg	;//	DealFlag为0，填充不能办理的原因；
	private String liveBill	;//	是否现场单：0，不是；1，是
	private String referFee	;//	参考报价（单位为分）
	private String wsh	;//	文书号
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
