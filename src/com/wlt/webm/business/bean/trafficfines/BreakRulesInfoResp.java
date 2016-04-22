package com.wlt.webm.business.bean.trafficfines;

import java.util.List;

/**
 * 违章代办详单查询返回对象
 * 
 * @author 1989
 * 
 */
public class BreakRulesInfoResp {
	private int code;
	private String desc;
	private List<PeccancyWOModel> orders;
	private long ordersTotal;
	private List<SpModel> sps;
	private List<AutoCarModel> cars;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<PeccancyWOModel> getOrders() {
		return orders;
	}

	public void setOrders(List<PeccancyWOModel> orders) {
		this.orders = orders;
	}

	public long getOrdersTotal() {
		return ordersTotal;
	}

	public void setOrdersTotal(long ordersTotal) {
		this.ordersTotal = ordersTotal;
	}

	public List<SpModel> getSps() {
		return sps;
	}

	public void setSps(List<SpModel> sps) {
		this.sps = sps;
	}

	public List<AutoCarModel> getCars() {
		return cars;
	}

	public void setCars(List<AutoCarModel> cars) {
		this.cars = cars;
	}

}
