package com.wlt.webm.business.bean.trafficfines;

/**
 * ����Υ��id��ȡΥ�¼�¼���صĶ���
 * 
 * @author 1989
 * 
 */
public class BreakRulesByIdResp {
	private int code;
	private String desc;
	private PeccancyInfoModel peccancyInfo;
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
	public PeccancyInfoModel getPeccancyInfo() {
		return peccancyInfo;
	}
	public void setPeccancyInfo(PeccancyInfoModel peccancyInfo) {
		this.peccancyInfo = peccancyInfo;
	}
}
