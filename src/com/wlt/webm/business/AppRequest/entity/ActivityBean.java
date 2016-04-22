package com.wlt.webm.business.AppRequest.entity;

public class ActivityBean {
	private String code;
	private String desc;
	private String startTime;
	private String endTime;
	private String outlineImgUrl;
	private String contentHtmlUrl;
	private String actionType;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getOutlineImgUrl() {
		return outlineImgUrl;
	}
	public void setOutlineImgUrl(String outlineImgUrl) {
		this.outlineImgUrl = outlineImgUrl;
	}
	public String getContentHtmlUrl() {
		return contentHtmlUrl;
	}
	public void setContentHtmlUrl(String contentHtmlUrl) {
		this.contentHtmlUrl = contentHtmlUrl;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
}
