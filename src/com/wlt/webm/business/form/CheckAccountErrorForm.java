package com.wlt.webm.business.form;

import org.apache.struts.action.ActionForm;

/**
 * 对账实体类
 * @author 谭万龙
 * @Company 深圳市万恒科技有限公司
* @date 2014-4-10 上午09:36:12
 */
public class CheckAccountErrorForm extends ActionForm{
	private Integer id;
	private String tradeserial;  //内部流水交易号
	private String externalserial; //外部交易号
	private String tradeobject;  //交易号码
	private String buyid; //接口商
	private Integer tradefee;  //实际交易金额
	private String tradetime;//交易时间
	private Integer StateOne; // 所在的系统交易状态
	private Integer StateTwo;  //对方系统状态
	private Integer Contrast_state; //对比状态
	private Integer curPage = 1;
	private String paramUrl;
	public CheckAccountErrorForm() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTradeserial() {
		return tradeserial;
	}
	public void setTradeserial(String tradeserial) {
		this.tradeserial = tradeserial;
	}
	public String getExternalserial() {
		return externalserial;
	}
	public void setExternalserial(String externalserial) {
		this.externalserial = externalserial;
	}
	public String getTradeobject() {
		return tradeobject;
	}
	public void setTradeobject(String tradeobject) {
		this.tradeobject = tradeobject;
	}
	public String getBuyid() {
		return buyid;
	}
	public void setBuyid(String buyid) {
		this.buyid = buyid;
	}
	public Integer getTradefee() {
		return tradefee;
	}
	public void setTradefee(Integer tradefee) {
		this.tradefee = tradefee;
	}
	public String getTradetime() {
		return tradetime;
	}
	public void setTradetime(String tradetime) {
		this.tradetime = tradetime;
	}
	public Integer getStateOne() {
		return StateOne;
	}
	public void setStateOne(Integer stateOne) {
		StateOne = stateOne;
	}
	public Integer getStateTwo() {
		return StateTwo;
	}
	public void setStateTwo(Integer stateTwo) {
		StateTwo = stateTwo;
	}
	public Integer getContrast_state() {
		return Contrast_state;
	}
	public void setContrast_state(Integer contrast_state) {
		Contrast_state = contrast_state;
	}
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
	
	
}
