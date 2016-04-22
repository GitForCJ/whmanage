package com.wlt.webm.business.bean.lianlian;

import com.informix.util.stringUtil;

public class BodyOrderBean {
	private String pay_password;
	private String order_no;
	private String account_no;
	private String data;
	private String data_type;
	public String getPay_password() {
		return pay_password;
	}
	public void setPay_password(String pay_password) {
		this.pay_password = pay_password;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getAccount_no() {
		return account_no;
	}
	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
}
