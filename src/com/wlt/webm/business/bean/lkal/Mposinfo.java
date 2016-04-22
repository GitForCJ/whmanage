package com.wlt.webm.business.bean.lkal;

/**
 * 拉卡拉完整推送消息
 * @author 1989
 *
 */
public class Mposinfo {
	
	private String index;
	private String mac;
	private DealInfo dealInfo;
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public DealInfo getDealInfo() {
		return dealInfo;
	}
	public void setDealInfo(DealInfo dealInfo) {
		this.dealInfo = dealInfo;
	}
	
	
}
