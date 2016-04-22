/**
 * 
 */
package com.wlt.webm.scputil.bean;

import com.wlt.webm.scpcommon.Constant;


/**
 * 资金帐户信息bean
 */
public class FacctBillBean {
	
	private String fundAcct;
	
	/*private String dealSerial;
	
	private String tradeAcct;*/
	
	private String tradeTime;
	
	/*private String tradeFee;
	
	private String tradeType;
	
	private String explain;*/
	
	private String[] facct = new String[13];
	/**
	 * 
	 */
	public FacctBillBean() {
		facct[8] = Constant.FACCT_RIGHT_LOGSTATE;
	}
	/**
	 * @param dealSerial The dealSerial to set.
	 */
	public void setDealSerial(String dealSerial) {
		facct[1] = dealSerial;
	}
	/**
	 * @param accountleft The dealSerial to set.
	 */
	public void setAccountleft(String accountleft) {
		facct[10] = accountleft;
	}
	/**
	 * @param explain The explain to set.
	 */
	public void setExplain(String explain) {
		facct[7] = explain;
	}
	/**
	 * @param explain The explain to set.
	 */
	public void setState(String statte) {
		facct[8] = statte;
	}
	/**
	 * @param fundAcct The fundAcct to set.
	 */
	public void setFundAcct(String fundAcct) {
		this.fundAcct = fundAcct;
		facct[0] = fundAcct;
	}
	/**
	 * @param tradeAcct The tradeAcct to set.
	 */
	public void setTradeAcct(String tradeAcct) {
		facct[2] = tradeAcct;
	}
	/**
	 * @param tradeFee The tradeFee to set.
	 */
	public void setTradeFee(String tradeFee) {
		facct[4] = tradeFee;
	}
	
	/**
	 * @param tradeTime The tradeTime to set.
	 */
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
		facct[3] = tradeTime;
	}
	
	public String getTradeTime()
	{
		return this.tradeTime;
	}
	/**
	 * @param tradeType The tradeType to set.
	 */
	public void setTradeType(String tradeType) {
		facct[6] = tradeType;
	}
//	childfacct,dealserial,tradeaccount,tradetime,tradefee,tradetype,explain,state,distime,accountleft,tradeserial,other_childfacct,pay_type
	public void setFacctTrade(String v1,String v2,String v3,String v4,String v5,String v6,String v7,
			String v8,String v9,String v10,String v11,String v12,String v13)
	{
		this.setFundAcct(v1);
		facct[0] = v1;
		facct[1] = v2;
		facct[2] = v3;
		facct[3] = v4;
		facct[4] = v5;
		facct[5] = v6;
		facct[6] = v7;
		facct[7] = v8;
		facct[8] = v9;
		facct[9] = v10;
		facct[10] = v11;
		facct[11]= v12;
		facct[12]=v13;
	}
	
	public int getTradeFee()
	{
		return Integer.parseInt(facct[4]);
	}
	
	public String[] getFacctBill()
	{
		return this.facct;
	}
	/**
	 * @return Returns the facct.
	 */
	public String[] getFacct() {
		return facct;
	}
	/**
	 * @return Returns the fundAcct.
	 */
	public String getFundAcct() {
		return fundAcct;
	}
}
