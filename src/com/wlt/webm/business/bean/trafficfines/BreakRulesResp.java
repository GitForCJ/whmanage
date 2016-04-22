package com.wlt.webm.business.bean.trafficfines;

import java.util.List;

/**
 * 违章查询响应对象
 * 
 * @author 1989
 * 
 */
public class BreakRulesResp {
	/**
	 * 返回结果标识
	 */
	private int code;
	/**
	 * 返回结果说明
	 */
	private String desc;
	/**
	 *违章信息结果集总数
	 */
	private int peccancyInfosTotal;
	/**
	 * 违章信息结果集
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
