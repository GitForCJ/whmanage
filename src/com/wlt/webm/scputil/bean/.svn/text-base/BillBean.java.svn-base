/**
 * 
 */
package com.wlt.webm.scputil.bean;

/**
 * 计费销帐bean
 */
public class BillBean {
	
	private String areaCode;
	
	//用户帐号
	/*private String useracct;
	
	//业务类型
	private String service;
	
	//交易流水号
	private String tradeSerq;*/
	
	//交易对象
	private String tradeObject;
	
	private String billDate;
	//交易时间
	/*private String tradeTime;
	
	//金额
	private String fee;
	
	//返销数据
	private String writeOff;
	
	//对帐数据
	private String checkData;
	
	//交易说明
	private String explain;
	
	//日志类型
	private String state;*/
	
	//状态变更时间
	private String disTime;
	
	private String[] payBill = new String[15];
	/**
	 * 
	 */
	public BillBean() {
		super();

	}
	/**
	 * @param areaCode The areaCode to set.
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
		payBill[0] = areaCode;
	}
	/**
	 * @param checkData The checkData to set.
	 */
	public void setCheckData(String checkData) {
		payBill[9] = checkData;
	}
	/**
	 * @param disTime The disTime to set.
	 */
	public void setDisTime(String disTime) {
		this.disTime = disTime;
		payBill[13] = disTime;
	}
	
	public String getDisTime()
	{
		return this.disTime;
	}
	/**
	 * @param explain The explain to set.
	 */
	public void setExplain(String explain) {
		payBill[11] = explain;
	}
	/**
	 * @param fee The fee to set.
	 */
	public void setFee(String fee) {
		payBill[7] = fee;
	}

	/**
	 * 计费流水号(返销及对账都需要此字段)
	 */
	public void setSerialNo(String writeoffseq){
		payBill[5] = writeoffseq;
	}
	
	/**
	 * @param service The service to set.
	 */
	public void setService(String service) {
		payBill[2] = service;
	}
	/**
	 * @param state The state to set.
	 */
	public void setState(String state) {
		payBill[12] = state;
	}
	/**
	 * @param tradeObject The tradeObject to set.
	 */
	public void setTradeObject(String tradeObject) {
		this.tradeObject = tradeObject;
		payBill[4] = tradeObject;
	}
	/**
	 * @param tradeSerq The tradeSerq to set.
	 */
	public void setTradeSerq(String tradeSerq) {
		payBill[3] = tradeSerq;
	}
	/**
	 * @param tradeTime The tradeTime to set.
	 */
	public void setTradeTime(String tradeTime) {
		payBill[6] = tradeTime;
	}
	/**
	 * @param useracct The useracct to set.
	 */
	public void setUseracct(String useracct) {
		payBill[1] = useracct;
	}
	/**
	 * @param writeOff The writeOff to set.
	 */
	public void setWriteOff(String writeOff) {
		payBill[8] = writeOff;
	}
	
	public void setAcctInf(String areaCode,String useracct,String service,String tradeSerq,String tradeObject,String writeOffSeq,String tradeTime)
	{
		this.setAreaCode(areaCode);
		payBill[1] = useracct;
		payBill[2] = service;
		payBill[3] = tradeSerq;
		this.setTradeObject(tradeObject);
		payBill[5] = writeOffSeq;
		payBill[6] = tradeTime;
	}
	
	public void setTradeInf(String fee,String writeOff,String checkData,String billDate,String explain,String state,String disTime,String checkState)
	{
		payBill[7] = fee;
		payBill[8] = writeOff;
		payBill[9] = checkData;
		this.setBillDate(billDate);
		payBill[11] = explain;
		payBill[12] = state;
		this.setDisTime(disTime);
		payBill[14] = checkState;
	}
	
	public String[] getPayBillInf()
	{
		return payBill;
	}
	/**
	 * @return Returns the areaCode.
	 */
	public String getAreaCode() {
		return areaCode;
	}
	/**
	 * @return Returns the tradeObject.
	 */
	public String getTradeObject() {
		return tradeObject;
	}
	/**
	 * @return Returns the billDate.
	 */
	public String getBillDate() {
		return billDate;
	}
	/**
	 * @param billDate The billDate to set.
	 */
	public void setBillDate(String billDate) {
		payBill[10] = billDate;
		this.billDate = billDate;
	}

}
