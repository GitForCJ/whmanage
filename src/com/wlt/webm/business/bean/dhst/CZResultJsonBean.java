package com.wlt.webm.business.bean.dhst;

public class CZResultJsonBean {
	
	private String resultCode;
	private String resultMsg;
	private String failPhones;
	private String clientOrderId;
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getFailPhones() {
		return failPhones;
	}
	public void setFailPhones(String failPhones) {
		this.failPhones = failPhones;
	}
	public String getClientOrderId() {
		return clientOrderId;
	}
	public void setClientOrderId(String clientOrderId) {
		this.clientOrderId = clientOrderId;
	}

}
