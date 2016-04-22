package com.wlt.webm.business.bean.liandong;

import com.informix.util.stringUtil;

public class BodyOrderBean {
	private String orderId;
	private String operator;
	private String province;
	private String mobile;
	private String productId;
	private String callbackUrl;
	private int proxOrder;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public int getProxOrder() {
		return proxOrder;
	}
	public void setProxOrder(int proxOrder) {
		this.proxOrder = proxOrder;
	}
	
	
	
	
	
}
