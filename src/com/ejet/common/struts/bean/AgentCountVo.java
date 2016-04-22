package com.ejet.common.struts.bean;

public class AgentCountVo implements java.io.Serializable {

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getOrder_num() {
		return order_num;
	}

	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}

	public String getBus_type() {
		return bus_type;
	}

	public void setBus_type(String bus_type) {
		this.bus_type = bus_type;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public double getSumfee() {
		return sumfee;
	}

	public void setSumfee(double sumfee) {
		this.sumfee = sumfee;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	private String user_name;
	
	private int order_num ;
	
	private String bus_type ;
	
	private int state;
	
	private double sumfee;
	
	private String user_id;
	
	private  String areacode;
	
	private String trade_type;
	
	private String buyid;
	
	private String phone_type;

	private String trade_date;
	
	private String user_ename;
	
	
	
	public String getUser_ename() {
		return user_ename;
	}

	public void setUser_ename(String user_ename) {
		this.user_ename = user_ename;
	}

	public String getTrade_date() {
		return trade_date;
	}

	public void setTrade_date(String trade_date) {
		this.trade_date = trade_date;
	}

	public String getBuyid() {
		return buyid;
	}

	public void setBuyid(String buyid) {
		this.buyid = buyid;
	}

	public String getPhone_type() {
		return phone_type;
	}

	public void setPhone_type(String phone_type) {
		this.phone_type = phone_type;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	 
}
