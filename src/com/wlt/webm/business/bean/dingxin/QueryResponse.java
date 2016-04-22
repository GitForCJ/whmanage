package com.wlt.webm.business.bean.dingxin;

/**
 * 山东鼎新流量查询返回数据
 * @author 1989
 */
public class QueryResponse {
	String userid;
	String orderid;
	String Porderid;
	String account;
	String state;
	String starttime;
	String endtime;
	String amount;
	String face;
	String error;
	
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

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return account+"|"+orderid+"|"+Porderid+"|"+state+"|"+error;
	}
}
