package com.wlt.webm.business.bean.unicom3gquery.xml;

/**
 * 腾讯流量订购请求
 * @author 1989
 */
public class TencentFlowOrderReq {
	private RequestMsgHeader MsgHeader;
    private TencentFlowOrderReqBody MsgBody;
    
	public RequestMsgHeader getMsgHeader() {
		return MsgHeader;
	}
	public void setMsgHeader(RequestMsgHeader msgHeader) {
		MsgHeader = msgHeader;
	}
	public TencentFlowOrderReqBody getMsgBody() {
		return MsgBody;
	}
	public void setMsgBody(TencentFlowOrderReqBody msgBody) {
		MsgBody = msgBody;
	}
	
}
