package com.wlt.webm.business.bean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author admin
 * 移动接口，组装返回消息 实体类
 */
@XmlRootElement(name = "response")
public class InterfaceResponseBean {
	/**
	 *  resMsg	响应消息
		rsCode	返回码
		ptOrderNo	合作方订单编号
		whtOrderNo	万汇通订单编号
		orderSuccessTime	交易返回时间
		responseDescription 订单描述
		responseFormat 响应格式 xml or json
	 */
	private String resMsg;
	private String rsCode;
	private String ptOrderNo;
	private String whtOrderNo;
	private String orderSuccessTime;
	private String ordersDescription;
	private String responseFormat;
	public String getResMsg() {
		return resMsg;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	public String getRsCode() {
		return rsCode;
	}
	public void setRsCode(String rsCode) {
		this.rsCode = rsCode;
	}
	public String getPtOrderNo() {
		return ptOrderNo;
	}
	public void setPtOrderNo(String ptOrderNo) {
		this.ptOrderNo = ptOrderNo;
	}
	public String getWhtOrderNo() {
		return whtOrderNo;
	}
	public void setWhtOrderNo(String whtOrderNo) {
		this.whtOrderNo = whtOrderNo;
	}
	public String getOrderSuccessTime() {
		return orderSuccessTime;
	}
	public void setOrderSuccessTime(String orderSuccessTime) {
		this.orderSuccessTime = orderSuccessTime;
	}
	public String getOrdersDescription() {
		return ordersDescription;
	}
	public void setOrdersDescription(String ordersDescription) {
		this.ordersDescription = ordersDescription;
	}
	public String getResponseFormat() {
		return responseFormat;
	}
	public void setResponseFormat(String responseFormat) {
		this.responseFormat = responseFormat;
	}
}
