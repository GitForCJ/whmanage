package com.wlt.webm.business.action;

import java.io.Serializable;
import java.util.Random;

import com.wlt.webm.tool.Tools;

public class TencentOrdersBean implements Serializable{
	
	private String paipai_dealid;// 腾讯公司订单号
	private String cardid;// 充值卡商品编号
	private String num;// 充值卡数量
	private String customer;// 被充值帐号或手机号码
	private String pay;// 单位为分，如200代表2元
	private String price;// 腾讯公司出售价格(成本价)
	private String deal_time;// 腾讯公司订单时间

	private String seqNo1;// 万汇通平台订单号
	private String userno;// 万汇通系统编号
	private String operator;//运营商类型  0dx  1yd  2lt
	
	private String ordertimee;//订单时间
	
	private long last_time_query=0;//订单上次查询时间
	private int query_count=0;//订单查询请求次数
	
	private String code;
	
	private String interid;//接口id#号码归属地
	
	private String beifen_reesult;//北分接口充值返回订单号
	public String getBeifen_reesult() {
		return beifen_reesult;
	}

	public void setBeifen_reesult(String beifen_reesult) {
		this.beifen_reesult = beifen_reesult;
	}

	public TencentOrdersBean(){}
	
	public TencentOrdersBean(String paipai_dealid, String cardid, String num,
			String customer, String pay, String price, String deal_time,
			String seqNo1, String userno,String operator) {
		this.paipai_dealid = paipai_dealid;
		this.cardid = cardid;
		this.num = num;
		this.customer = customer;
		this.pay = pay;
		this.price = price;
		this.deal_time = deal_time;

		this.seqNo1 = seqNo1;
		this.userno = userno;
		this.operator=operator;
	}
	
	public int getQuery_count() {
		return query_count;
	}

	public void setQuery_count(int query_count) {
		this.query_count = query_count;
	}
	
	public long getLast_time_query() {
		return last_time_query;
	}

	public void setLast_time_query(long last_time_query) {
		this.last_time_query = last_time_query;
	}

	public String getOrdertimee() {
		return ordertimee;
	}

	public void setOrdertimee(String ordertimee) {
		this.ordertimee = ordertimee;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getSeqNo1() {
		return seqNo1;
	}

	public void setSeqNo1(String seqNo1) {
		this.seqNo1 = seqNo1;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}
	
	public String getPaipai_dealid() {
		return paipai_dealid;
	}

	public void setPaipai_dealid(String paipai_dealid) {
		this.paipai_dealid = paipai_dealid;
	}

	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDeal_time() {
		return deal_time;
	}

	public void setDeal_time(String deal_time) {
		this.deal_time = deal_time;
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
