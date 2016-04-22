package com.wlt.webm.business.bean.zdhuawei;

/**
 * 中大系统级参数
 * 
 * @author 1989
 * 
 */
public class ZDSystemParam {
	public String zdws_user_login="huaweizd";
	public String zdws_sign;
	public String zdws_method;
	public String timestamp;
	public String platform = "zdws";
	public String token = "中大分配";
	public String version = "1.0";
	public String format = "json";

	public String getZdws_user_login() {
		return zdws_user_login;
	}

	public void setZdws_user_login(String zdws_user_login) {
		this.zdws_user_login = zdws_user_login;
	}

	public String getZdws_sign() {
		return zdws_sign;
	}

	public void setZdws_sign(String zdws_sign) {
		this.zdws_sign = zdws_sign;
	}

	public String getZdws_method() {
		return zdws_method;
	}

	public void setZdws_method(String zdws_method) {
		this.zdws_method = zdws_method;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getPlatform() {
		return platform;
	}

	public String getToken() {
		return token;
	}

	public String getVersion() {
		return version;
	}

	public String getFormat() {
		return format;
	}
	
}
