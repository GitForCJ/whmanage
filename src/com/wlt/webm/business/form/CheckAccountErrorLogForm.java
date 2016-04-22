package com.wlt.webm.business.form;

import org.apache.struts.action.ActionForm;

/**
 * 对账实体日志类
 * @author 谭万龙
 * @Company 深圳市万恒科技有限公司
* @date 2014-4-10 上午09:36:12
 */
public class CheckAccountErrorLogForm extends ActionForm{
	private Integer id;
	private String buyid; //接口商
	private String checktime;  //所对账目日期
	private String checkRunTime;//对账任务完成日期
	private Integer State; // 对账结果
	private Integer curPage = 1;
	private String paramUrl;
	
	public Integer getCurPage() {
		return curPage;
	}
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}
	public String getParamUrl() {
		return paramUrl;
	}
	public void setParamUrl(String paramUrl) {
		this.paramUrl = paramUrl;
	}
	public CheckAccountErrorLogForm() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBuyid() {
		return buyid;
	}
	public void setBuyid(String buyid) {
		this.buyid = buyid;
	}
	public String getChecktime() {
		return checktime;
	}
	public void setChecktime(String checktime) {
		this.checktime = checktime;
	}
	public String getCheckRunTime() {
		return checkRunTime;
	}
	public void setCheckRunTime(String checkRunTime) {
		this.checkRunTime = checkRunTime;
	}
	public Integer getState() {
		return State;
	}
	public void setState(Integer state) {
		State = state;
	}
	
	
	
}
