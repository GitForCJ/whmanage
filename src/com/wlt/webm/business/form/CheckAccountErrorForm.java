package com.wlt.webm.business.form;

import org.apache.struts.action.ActionForm;

/**
 * ����ʵ����
 * @author ̷����
 * @Company ���������Ƽ����޹�˾
* @date 2014-4-10 ����09:36:12
 */
public class CheckAccountErrorForm extends ActionForm{
	private Integer id;
	private String tradeserial;  //�ڲ���ˮ���׺�
	private String externalserial; //�ⲿ���׺�
	private String tradeobject;  //���׺���
	private String buyid; //�ӿ���
	private Integer tradefee;  //ʵ�ʽ��׽��
	private String tradetime;//����ʱ��
	private Integer StateOne; // ���ڵ�ϵͳ����״̬
	private Integer StateTwo;  //�Է�ϵͳ״̬
	private Integer Contrast_state; //�Ա�״̬
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
