package com.wlt.webm.business.bean.leliu;

/**
 * 乐流流量创建订单请求实体
 * @author 1989
 *
 */
public class CreateReqFacct {
	/**
	 * 合作方编号
	 */
	private String partner_no=LeliuFlowCharge.partner_no;
	/**
	 * code
	 */
	private String code;
	
	public String getPartner_no() {
		return partner_no;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
