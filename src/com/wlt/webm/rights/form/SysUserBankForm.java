package com.wlt.webm.rights.form;

import org.apache.struts.action.ActionForm;

/**
 * 用户银行卡信息<br>
 */
public class SysUserBankForm extends ActionForm
{

	private Integer user_id;
	  
	private String user_bankcard;
	  
	private String user_name;
	  
	private String user_icard_type;
	  
	private String user_icard;
	
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getUser_bankcard() {
		return user_bankcard;
	}

	public void setUser_bankcard(String user_bankcard) {
		this.user_bankcard = user_bankcard;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_icard_type() {
		return user_icard_type;
	}

	public void setUser_icard_type(String user_icard_type) {
		this.user_icard_type = user_icard_type;
	}

	public String getUser_icard() {
		return user_icard;
	}

	public void setUser_icard(String user_icard) {
		this.user_icard = user_icard;
	}
}
