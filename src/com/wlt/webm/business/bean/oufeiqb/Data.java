package com.wlt.webm.business.bean.oufeiqb;

import java.util.List;

public class Data {
	private String reqid;
	private List<DateList> dataList;
	private String fields;
	
	private String canRechaege;
	
	private String isok;
	private String orderId;
	private String id;
	
	public String getReqid() {
		return reqid;
	}
	public void setReqid(String reqid) {
		this.reqid = reqid;
	}

	public List<DateList> getDataList() {
		return dataList;
	}
	public void setDataList(List<DateList> dataList) {
		this.dataList = dataList;
	}
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	public String getCanRechaege() {
		return canRechaege;
	}
	public void setCanRechaege(String canRechaege) {
		this.canRechaege = canRechaege;
	}
	public String getIsok() {
		return isok;
	}
	public void setIsok(String isok) {
		this.isok = isok;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
