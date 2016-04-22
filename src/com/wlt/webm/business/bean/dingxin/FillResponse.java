package com.wlt.webm.business.bean.dingxin;

/**
 * 山东鼎新流量充值返回数据
 * @author 1989
 */
public class FillResponse {
	String userid;
	String orderid;
	String Porderid;
	String account;
	String state;
	String starttime;
	String endtime;
	String error;
	String userprice;
	String gprs;
	String area;
	String effecttime;
	String validity;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getPorderid() {
		return Porderid;
	}
	public void setPorderid(String porderid) {
		Porderid = porderid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getUserprice() {
		return userprice;
	}
	public void setUserprice(String userprice) {
		this.userprice = userprice;
	}
	public String getGprs() {
		return gprs;
	}
	public void setGprs(String gprs) {
		this.gprs = gprs;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getEffecttime() {
		return effecttime;
	}
	public void setEffecttime(String effecttime) {
		this.effecttime = effecttime;
	}
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	
	@Override
	public String toString() {
		return account+"|订单号"+orderid+"|鼎信订单号"+Porderid+"|状态"+state+"|错误代码"+error;
	}
}
