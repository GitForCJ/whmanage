package com.wlt.webm.business.bean.trafficfines;

/**
 * ����Υ��id��ȡΥ�¼�¼���������
 * 
 * @author 1989
 * 
 */
public class BreakRulesByIdReq {
	private long peccId;
	private String tokenId;
	
	/**
	 * ���캯��
	 * @param peccId
	 * @param tokenId
	 */
	public BreakRulesByIdReq(long peccId,String tokenId){
		this.peccId=peccId;
		this.tokenId=tokenId;
	}

	public long getPeccId() {
		return peccId;
	}

	public void setPeccId(long peccId) {
		this.peccId = peccId;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	
}
