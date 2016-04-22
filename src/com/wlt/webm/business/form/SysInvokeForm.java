package com.wlt.webm.business.form;

import org.apache.struts.action.ActionForm;

/**
 * 接口日志信息
 */
public class SysInvokeForm extends ActionForm
{

	private String id;
	 
	private String user_id;
	 
	private String ord_id;
	 
	private String in_input;
	 
	private String in_output;
	 
	private String in_time;
	 
	private String in_desc;
	 
	private String in_code;
	 
	private String in_otherput;
	 
	private String se_code;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getOrd_id() {
		return ord_id;
	}

	public void setOrd_id(String ord_id) {
		this.ord_id = ord_id;
	}

	public String getIn_input() {
		return in_input;
	}

	public void setIn_input(String in_input) {
		this.in_input = in_input;
	}

	public String getIn_output() {
		return in_output;
	}

	public void setIn_output(String in_output) {
		this.in_output = in_output;
	}

	public String getIn_time() {
		return in_time;
	}

	public void setIn_time(String in_time) {
		this.in_time = in_time;
	}

	public String getIn_desc() {
		return in_desc;
	}

	public void setIn_desc(String in_desc) {
		this.in_desc = in_desc;
	}

	public String getIn_code() {
		return in_code;
	}

	public void setIn_code(String in_code) {
		this.in_code = in_code;
	}

	public String getIn_otherput() {
		return in_otherput;
	}

	public void setIn_otherput(String in_otherput) {
		this.in_otherput = in_otherput;
	}

	public String getSe_code() {
		return se_code;
	}

	public void setSe_code(String se_code) {
		this.se_code = se_code;
	}
}
