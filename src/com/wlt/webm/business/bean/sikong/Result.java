package com.wlt.webm.business.bean.sikong;

public class Result {
	private String mobile;
	private String userId;
	private String msgId;
	private String err;
	private String fail_describe;
	private String report_time;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getErr() {
		return err;
	}
	public void setErr(String err) {
		this.err = err;
	}
	public String getFail_describe() {
		return fail_describe;
	}
	public void setFail_describe(String fail_describe) {
		this.fail_describe = fail_describe;
	}
	public String getReport_time() {
		return report_time;
	}
	public void setReport_time(String report_time) {
		this.report_time = report_time;
	}
}
