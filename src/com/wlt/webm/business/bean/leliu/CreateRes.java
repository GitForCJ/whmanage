package com.wlt.webm.business.bean.leliu;

/**
 * 乐流流量创建订单返回实体
 * @author 1989
 *
 */
public class CreateRes {
	/**
	 * 请求编号
	 */
	private String request_no;
	/**
	 * 订单状态
	 */
	private String orderstatus;
	/**
	 * 结果编码
	 */
	private String result_code;
	/**
	 * 结果描述
	 */
	private String result_desc;
	public String getRequest_no() {
		return request_no;
	}
	public void setRequest_no(String request_no) {
		this.request_no = request_no;
	}
	public String getOrderstatus() {
		return orderstatus;
	}
	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getResult_desc() {
		return result_desc;
	}
	public void setResult_desc(String result_desc) {
		this.result_desc = result_desc;
	}
}
