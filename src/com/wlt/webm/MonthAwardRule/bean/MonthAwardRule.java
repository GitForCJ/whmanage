package com.wlt.webm.MonthAwardRule.bean;

import org.apache.struts.action.ActionForm;

public class MonthAwardRule extends ActionForm {
	private int awardId;//id ����
	private String userno;//�û����
	private int minmoney;//��С���
	private int maxmoney;//�����
	private Float rate;//�����ٷֱ�
	private int facevalue  ;//��ʶ ��������
	private String intime;
	public int getAwardId() {
		return awardId;
	}
	public void setAwardId(int awardId) {
		this.awardId = awardId;
	}
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public int getMinmoney() {
		return minmoney;
	}
	public void setMinmoney(int minmoney) {
		this.minmoney = minmoney;
	}
	public int getMaxmoney() {
		return maxmoney;
	}
	public void setMaxmoney(int maxmoney) {
		this.maxmoney = maxmoney;
	}
	public Float getRate() {
		return rate;
	}
	public void setRate(Float rate) {
		this.rate = rate;
	}
	public String getIntime() {
		return intime;
	}
	public void setIntime(String intime) {
		this.intime = intime;
	}//����ʱ��
	public int getFacevalue() {
		return facevalue;
	}
	public void setFacevalue(int facevalue) {
		this.facevalue = facevalue;
	}
	
}
