package com.wlt.webm.business.action;

import java.io.Serializable;

public class JdOrderBean implements Serializable{
	private String fillOrderNo;// 京东订单号
	private String fillMobile;// 手机号
	private String fillAmount;// 充值流量 50
	private String fillType;// 流量类型 默认传0
	private String finTime;// 清算时间
	private String areaUsed; // 使用范围 全国 省内
	private String notifyUrl;// 后台通知地址
	private String mutCode;// 运营商
	private String areaCode; // 地区
	private String costPrice;// 成本价

	private String userno;//万恒系统编号
	private String wh_order_num;// 万恒订单号
	private String ordertime;//订单时间
	private String interid;//接口id#号码归属地
	private String wh_mutCode;//万恒运营商
	
	private String code;
	
	private long last_time_query=0;//订单上次查询时间
	private int query_count=0;//订单查询请求次数
	
	private String beifen_reesult;//北分接口充值返回订单号
	public String getBeifen_reesult() {
		return beifen_reesult;
	}

	public void setBeifen_reesult(String beifen_reesult) {
		this.beifen_reesult = beifen_reesult;
	}
	
	public JdOrderBean(){}
	
	

	public JdOrderBean(String fillOrderNo, String fillMobile,
			String fillAmount, String fillType, String finTime,
			String areaUsed, String notifyUrl, String mutCode, String areaCode,
			String costPrice, String wh_order_num,String userno) {
		this.fillAmount=fillAmount;
		this.fillMobile=fillMobile;
		this.fillAmount=fillAmount;
		this.fillType=fillType;
		this.finTime=finTime;
		this.areaUsed=areaUsed;
		this.notifyUrl=notifyUrl;
		this.mutCode=mutCode;
		this.areaCode=areaCode;
		this.costPrice=costPrice;
		
		this.wh_order_num=wh_order_num;
		this.userno=userno;
	}

	public String getWh_mutCode() {
		return wh_mutCode;
	}

	public void setWh_mutCode(String wh_mutCode) {
		this.wh_mutCode = wh_mutCode;
	}
	public String getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public String getWh_order_num() {
		return wh_order_num;
	}

	public void setWh_order_num(String wh_order_num) {
		this.wh_order_num = wh_order_num;
	}

	public String getFillOrderNo() {
		return fillOrderNo;
	}

	public void setFillOrderNo(String fillOrderNo) {
		this.fillOrderNo = fillOrderNo;
	}

	public String getFillMobile() {
		return fillMobile;
	}

	public void setFillMobile(String fillMobile) {
		this.fillMobile = fillMobile;
	}

	public String getFillAmount() {
		return fillAmount;
	}

	public void setFillAmount(String fillAmount) {
		this.fillAmount = fillAmount;
	}

	public String getFillType() {
		return fillType;
	}

	public void setFillType(String fillType) {
		this.fillType = fillType;
	}

	public String getFinTime() {
		return finTime;
	}

	public void setFinTime(String finTime) {
		this.finTime = finTime;
	}

	public String getAreaUsed() {
		return areaUsed;
	}

	public void setAreaUsed(String areaUsed) {
		this.areaUsed = areaUsed;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getMutCode() {
		return mutCode;
	}

	public void setMutCode(String mutCode) {
		this.mutCode = mutCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public long getLast_time_query() {
		return last_time_query;
	}

	public void setLast_time_query(long last_time_query) {
		this.last_time_query = last_time_query;
	}

	public int getQuery_count() {
		return query_count;
	}

	public void setQuery_count(int query_count) {
		this.query_count = query_count;
	}

	public String getInterid() {
		return interid;
	}

	public void setInterid(String interid) {
		this.interid = interid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
