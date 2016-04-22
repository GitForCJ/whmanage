package com.wlt.webm.business.AppRequest.entity;

import java.util.List;

public class One {
	private String code;
	private String desc;
	private String phoneNum;
	private String phoneNumDesc;
	private String phoneType;
	private String areaId;
	private List<OneList> phoneProducts;
	public String getPhoneType() {
		return phoneType;
	}
	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getPhoneNumDesc() {
		return phoneNumDesc;
	}
	public void setPhoneNumDesc(String phoneNumDesc) {
		this.phoneNumDesc = phoneNumDesc;
	}
	public List<OneList> getPhoneProducts() {
		return phoneProducts;
	}
	public void setPhoneProducts(List<OneList> phoneProducts) {
		this.phoneProducts = phoneProducts;
	}
}
