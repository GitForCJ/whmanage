package com.wlt.webm.business.bean.trafficfines;

import java.util.List;

/**
 * Υ�²�ѯ��Ӧ����
 * 
 * @author 1989
 * 
 */
public class BreakRulesResp {
	/**
	 * ���ؽ����ʶ
	 */
	private int code;
	/**
	 * ���ؽ��˵��
	 */
	private String desc;
	/**
	 *Υ����Ϣ���������
	 */
	private int peccancyInfosTotal;
	/**
	 * Υ����Ϣ�����
	 */
	private List<PeccancyInfoModel> peccancyInfos;
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
	public int getPeccancyInfosTotal() {
		return peccancyInfosTotal;
	}
	public void setPeccancyInfosTotal(int peccancyInfosTotal) {
		this.peccancyInfosTotal = peccancyInfosTotal;
	}
	public List<PeccancyInfoModel> getPeccancyInfos() {
		return peccancyInfos;
	}
	public void setPeccancyInfos(List<PeccancyInfoModel> peccancyInfos) {
		this.peccancyInfos = peccancyInfos;
	}
	
	
}
