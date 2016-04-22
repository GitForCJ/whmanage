package com.wlt.webm.business.action;

import java.io.Serializable;
import java.util.Random;

import com.wlt.webm.tool.Tools;

public class TencentOrdersBean implements Serializable{
	
	private String paipai_dealid;// ��Ѷ��˾������
	private String cardid;// ��ֵ����Ʒ���
	private String num;// ��ֵ������
	private String customer;// ����ֵ�ʺŻ��ֻ�����
	private String pay;// ��λΪ�֣���200����2Ԫ
	private String price;// ��Ѷ��˾���ۼ۸�(�ɱ���)
	private String deal_time;// ��Ѷ��˾����ʱ��

	private String seqNo1;// ���ͨƽ̨������
	private String userno;// ���ͨϵͳ���
	private String operator;//��Ӫ������  0dx  1yd  2lt
	
	private String ordertimee;//����ʱ��
	
	private long last_time_query=0;//�����ϴβ�ѯʱ��
	private int query_count=0;//������ѯ�������
	
	private String code;
	
	private String interid;//�ӿ�id#���������
	
	private String beifen_reesult;//���ֽӿڳ�ֵ���ض�����
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
