/**
 * 
 */
package com.wlt.webm.scputil.bean;
public class CustErrorOrder {
	
	private String seqNo = "";
	//区号
	private String areaCode = "";
	
	private String servid = "";
	//客户编号
	private String custid = "";
	//客户名称
	private String custName = "";
	//动作编号
	private int action = 0;
	//产品编码
	private int prodId = 0;
	//竣工时间
	private String compDate = "";
	//状态时间
	private String stsDate = "";
	//异常产生平台
	private int source = -1;
	//处理次数
	private int disNum = -1;
	//处理状态
	private int disState = 0;
	//工单状态
	private int state = 0;
	//错误描述
	private String errorExp = "";
	
	/**
	 * 
	 */
	public CustErrorOrder() {
		super();

	}
	
	public String[] getCustErrorOrder()
	{
		String[] logs = {seqNo,areaCode,servid,custid,custName,""+action,""+prodId,compDate,stsDate,""+source,""+disNum,""+disState,""+state,errorExp};
		return logs;
	}
	
	/**
	 * @return Returns the action.
	 */
	public int getAction() {
		return action;
	}

	/**
	 * @param action The action to set.
	 */
	public void setAction(int action) {
		this.action = action;
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
		return custName;
	}

	/**
	 * @param custName The custName to set.
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * @return Returns the disState.
	 */
	public int getDisState() {
		return disState;
	}

	/**
	 * @param disState The disState to set.
	 */
	public void setDisState(int disState) {
		this.disState = disState;
	}

	/**
	 * @return Returns the errorExp.
	 */
	public String getErrorExp() {
		if(errorExp == null)
		{
			errorExp = " ";
		}
		return errorExp;
	}

	/**
	 * @param errorExp The errorExp to set.
	 */
	public void setErrorExp(String errorExp) {
		this.errorExp = errorExp;
	}

	/**
	 * @return Returns the prodId.
	 */
	public int getProdId() {
		return prodId;
	}

	/**
	 * @param prodId The prodId to set.
	 */
	public void setProdId(int prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return Returns the seqNo.
	 */
	public String getSeqNo() {
		return seqNo;
	}

	/**
	 * @param seqNo The seqNo to set.
	 */
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
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

	/**
	 * @return Returns the stsDate.
	 */
	public String getStsDate() {
		return stsDate;
	}

	/**
	 * @param stsDate The stsDate to set.
	 */
	public void setStsDate(String stsDate) {
		this.stsDate = stsDate;
	}

	/**
	 * @return Returns the disNum.
	 */
	public int getDisNum() {
		return disNum;
	}

	/**
	 * @param disNum The disNum to set.
	 */
	public void setDisNum(int disNum) {
		this.disNum = disNum;
	}

	/**
	 * @return Returns the source.
	 */
	public int getSource() {
		return source;
	}

	/**
	 * @param source The source to set.
	 */
	public void setSource(int source) {
		this.source = source;
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

}
