package com.wlt.webm.business.form;

import org.apache.struts.action.ActionForm;

/**
 * 银行转款日志信息
 */
public class BankLogForm extends ActionForm
{
	private String id;
	
	private String dealserial;
	
	private String tradeaccount;
	
	private String tradetime;
	
	private String tradefee;
	
	private String tradetype;
	
	private String explain;
	
	private String state;
	
	private String distime;
	
	private String returnmsg;
	
	private String checkmsg;
	
	private String qrymsg;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDealserial() {
		return dealserial;
	}

	public void setDealserial(String dealserial) {
		this.dealserial = dealserial;
	}

	public String getTradeaccount() {
		return tradeaccount;
	}

	public void setTradeaccount(String tradeaccount) {
		this.tradeaccount = tradeaccount;
	}

	public String getTradetime() {
		return tradetime;
	}

	public void setTradetime(String tradetime) {
		this.tradetime = tradetime;
	}

	public String getTradefee() {
		return tradefee;
	}

	public void setTradefee(String tradefee) {
		this.tradefee = tradefee;
	}

	public String getTradetype() {
		return tradetype;
	}

	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistime() {
		return distime;
	}

	public void setDistime(String distime) {
		this.distime = distime;
	}

	public String getReturnmsg() {
		return returnmsg;
	}

	public void setReturnmsg(String returnmsg) {
		this.returnmsg = returnmsg;
	}

	public String getCheckmsg() {
		return checkmsg;
	}

	public void setCheckmsg(String checkmsg) {
		this.checkmsg = checkmsg;
	}

	public String getQrymsg() {
		return qrymsg;
	}

	public void setQrymsg(String qrymsg) {
		this.qrymsg = qrymsg;
	}
}
