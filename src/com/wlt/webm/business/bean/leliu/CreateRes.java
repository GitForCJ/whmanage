package com.wlt.webm.business.bean.leliu;

/**
 * ��������������������ʵ��
 * @author 1989
 *
 */
public class CreateRes {
	/**
	 * ������
	 */
	private String request_no;
	/**
	 * ����״̬
	 */
	private String orderstatus;
	/**
	 * �������
	 */
	private String result_code;
	/**
	 * �������
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
