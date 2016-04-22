package com.wlt.webm.business.AppRequest.entity;

import java.util.List;

public class FlowProductsBean {
	private String code;
	private String desc;
	private String mobileTypeCode;
	private String mobileAreaCode;
	private String mobileArea;
	private List<FlowProductsBeanList> products;
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
	public String getMobileTypeCode() {
		return mobileTypeCode;
	}
	public void setMobileTypeCode(String mobileTypeCode) {
		this.mobileTypeCode = mobileTypeCode;
	}
	public String getMobileAreaCode() {
		return mobileAreaCode;
	}
	public void setMobileAreaCode(String mobileAreaCode) {
		this.mobileAreaCode = mobileAreaCode;
	}
	public String getMobileArea() {
		return mobileArea;
	}
	public void setMobileArea(String mobileArea) {
		this.mobileArea = mobileArea;
	}
	public List<FlowProductsBeanList> getProducts() {
		return products;
	}
	public void setProducts(List<FlowProductsBeanList> products) {
		this.products = products;
	}
}
