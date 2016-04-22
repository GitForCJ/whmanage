package com.wlt.webm.rights.form;

import org.apache.struts.action.ActionForm;

/**
 * 公告信息<br>
 */
public class SysNoticeForm extends ActionForm
{
	/**
	 * 编号
	 */
	public String an_id;
	/**
	 * 公告标题
	 */
	public String an_title;
	/**
	 * 公告内容
	 */
	public String an_content;
	/**
	 * 公告类型 00 所有 01 地市 02 个人
	 */
	public String an_type;
	/**
	 * 面向的地市编号 0全部地市可见
	 */
	public String an_faceid;
	/**
	 * 公告创建时间
	 */
	public String an_createtime;
	/**
	 * 公告生效时间
	 */
	public String an_activedate;
	/**
	 * 公告截止时间
	 */
	public String an_deaddate;
	/**
	 * 是否有效
	 */
	public String if_active;
	/**
	 * 公告类型
	 */
	public String contentType;
	public String getAn_id() {
		return an_id;
	}
	public void setAn_id(String an_id) {
		this.an_id = an_id;
	}
	public String getAn_title() {
		return an_title;
	}
	public void setAn_title(String an_title) {
		this.an_title = an_title;
	}
	public String getAn_content() {
		return an_content;
	}
	public void setAn_content(String an_content) {
		this.an_content = an_content;
	}
	public String getAn_type() {
		return an_type;
	}
	public void setAn_type(String an_type) {
		this.an_type = an_type;
	}
	public String getAn_faceid() {
		return an_faceid;
	}
	public void setAn_faceid(String an_faceid) {
		this.an_faceid = an_faceid;
	}
	public String getAn_createtime() {
		return an_createtime;
	}
	public void setAn_createtime(String an_createtime) {
		this.an_createtime = an_createtime;
	}
	public String getAn_activedate() {
		return an_activedate;
	}
	public void setAn_activedate(String an_activedate) {
		this.an_activedate = an_activedate;
	}
	public String getAn_deaddate() {
		return an_deaddate;
	}
	public void setAn_deaddate(String an_deaddate) {
		this.an_deaddate = an_deaddate;
	}
	public String getIf_active() {
		return if_active;
	}
	public void setIf_active(String if_active) {
		this.if_active = if_active;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
