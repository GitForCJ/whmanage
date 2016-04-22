/**
 * 
 */
package com.wlt.webm.scputil.bean;

/**
 * �ʽ��ʻ�������Ϣbean
 */
public class FacctManageBean {
	//�ͻ����
	private String custid = "";
	//�۷�ʵ����
	private String servid = "";
	//����
	private String areaCode = "";
	//��������
	private String compDate = "";
	//�ʽ��ʺ�
	private String fundAcct = "";
	//����
	private String password = "";
	//״̬�ı�ʱ��
	private String stateTime = "";
	//״̬
	private int state = 0;
	//�Ƿ�Ѻ���ʻ�
	private int isDeposit = 1;
	
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
	 * @return Returns the fundAcct.
	 */
	public String getFundAcct() {
		if(fundAcct.equals(""))
		{
			fundAcct = " ";
		}
		return fundAcct;
	}

	/**
	 * @param fundAcct The fundAcct to set.
	 */
	public void setFundAcct(String fundAcct) {
		this.fundAcct = fundAcct;
	}

	/**
	 * @return Returns the isDeposit.
	 */
	public int getIsDeposit() {
		return isDeposit;
	}

	/**
	 * @param isDeposit The isDeposit to set.
	 */
	public void setIsDeposit(int isDeposit) {
		this.isDeposit = isDeposit;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return Returns the stateTime.
	 */
	public String getStateTime() {
		return stateTime;
	}

	/**
	 * @param stateTime The stateTime to set.
	 */
	public void setStateTime(String stateTime) {
		this.stateTime = stateTime;
	}	
}
