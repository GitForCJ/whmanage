package com.wlt.webm.business.bean.lkal;

/**
 * 拉卡拉推送消息交易体
 * @author 1989
 *
 */
public class DealInfo {
	private String shopNo;
	private String dealType;
	private String dealTime;
	private String dealAmount;
	private String psam;
	private String channel;
	private String channelNo;
	private String dealResult;
	
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public String getDealTime() {
		return dealTime;
	}
	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}
	public String getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}
	public String getPsam() {
		return psam;
	}
	public void setPsam(String psam) {
		this.psam = psam;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	public String getDealResult() {
		return dealResult;
	}
	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}
	
}
