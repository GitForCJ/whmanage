package com.wlt.webm.business.bean.trafficfines;

/**
 * ��ȡtoken��Ϣ��Ϣ
 * @author 1989
 *
 */
public class TokenResponse {
	
	private int code ;
	private String desc;
	private String tokenId;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	
}
