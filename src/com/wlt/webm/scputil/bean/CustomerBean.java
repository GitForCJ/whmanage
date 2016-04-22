/**
 * 
 */
package com.wlt.webm.scputil.bean;


/**
 * 客户信息bean
 */
public class CustomerBean {
	
	//客户编号
	private String custid = "";
	
	private String servid = "";
	//客户名称
	private String custName = "";
	//区号
	private String areaCode = "";
	//证件类型
	private String idType = "";
	//证件号
	private String idCard = "";
	//银行名
	private String bank = "";
	//银行卡号
	private String bankNo ="";
	//峻工日期
	private String compDate = "";
	//开户地址
	private String servAddr = "";
	//联系地址
	private String linkAddr = "";
	
	private long agentid = 0;
	
	private int state = 0;
	/**
	 * @return Returns the agentid.
	 */
	public long getAgentid() {
		return agentid;
	}

	/**
	 * @param agentid The agentid to set.
	 */
	public void setAgentid(long agentid) {
		this.agentid = agentid;
	}

	/**
	 * @return Returns the state.
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state The state to set.
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * 
	 */
	public CustomerBean() {
		super();

	}

	/**
	 * @return Returns the areaCode.
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode The areaCode to set.
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return Returns the bank.
	 */
	public String getBank() {
		if(bank.equals(""))
		{
			bank = " ";
		}
		return bank;
	}

	/**
	 * @param bank The bank to set.
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * @return Returns the bankNo.
	 */
	public String getBankNo() {
		if(bankNo.equals(""))
		{
			bankNo = " ";
		}
		return bankNo;
	}

	/**
	 * @param bankNo The bankNo to set.
	 */
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	/**
	 * @return Returns the compDate.
	 */
	public String getCompDate() {
		return compDate;
	}

	/**
	 * @param compDate The compDate to set.
	 */
	public void setCompDate(String compDate) {
		this.compDate = compDate;
	}

	/**
	 * @return Returns the custid.
	 */
	public String getCustid() {
		return custid;
	}

	/**
	 * @param custid The custid to set.
	 */
	public void setCustid(String custid) {
		this.custid = custid;
	}

	/**
	 * @return Returns the custName.
	 */
	public String getCustName() {
		if(custName.equals(""))
		{
			custName = " ";
		}
		return custName;
	}

	/**
	 * @param custName The custName to set.
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * @return Returns the idCard.
	 */
	public String getIdCard() {
		if(idCard.equals(""))
		{
			idCard = " ";
		}
		return idCard;
	}

	/**
	 * @param idCard The idCard to set.
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	/**
	 * @return Returns the idType.
	 */
	public String getIdType() {
		if(idType.equals(""))
		{
			idType = " ";
		}
		return idType;
	}

	/**
	 * @param idType The idType to set.
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}

	/**
	 * @return Returns the linkAddr.
	 */
	public String getLinkAddr() {
		if(linkAddr.equals(""))
		{
			linkAddr = " ";
		}
		return linkAddr;
	}

	/**
	 * @param linkAddr The linkAddr to set.
	 */
	public void setLinkAddr(String linkAddr) {
		this.linkAddr = linkAddr;
	}

	/**
	 * @return Returns the servAddr.
	 */
	public String getServAddr() {
		if(servAddr.equals(""))
		{
			servAddr = " ";
		}
		return servAddr;
	}

	/**
	 * @param servAddr The servAddr to set.
	 */
	public void setServAddr(String servAddr) {
		this.servAddr = servAddr;
	}

	/**
	 * @return Returns the servid.
	 */
	public String getServid() {
		return servid;
	}

	/**
	 * @param servid The servid to set.
	 */
	public void setServid(String servid) {
		this.servid = servid;
	}

}
