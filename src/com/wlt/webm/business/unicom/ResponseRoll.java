package com.wlt.webm.business.unicom;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.Constant;

public class ResponseRoll {

	/**
	 * 返回的数据信息
	 */
	private byte[] recvMsg;
	
	/**
	 * 数据输入流
	 */
	private DataInputStream input;
	
	/**
	 * 请求信息说明
	 */
	private byte[] responseInfo;
	
	/**
	 * 包头流水号
	 */
	private byte[] frameID = new byte[16];
	
	/**
	 * MD5验证字符串
	 */
	private byte[] macStr = new byte[32];
	
	/**
	 * e票通平台受理时间
	 */
	private byte[] srvDateTime = new byte[14];
	/**
	 * e票通返回的流水号
	 */
	private byte[] svrTraceID = new byte[16];
	
	/**
	 * 请求流水号
	 */
	private byte[] reqTraceID = new byte[16];
	
	private byte[] oldTraceID = new byte[16];
	
	private byte[] userNumber = new byte[20];
	
	private byte[] billValue = new byte[10];
	
	private byte[] status = new byte[4];
	
	/**
	 * 默认构造函数
	 *
	 */
	public ResponseRoll(){
		
	}
	
	/**
	 * 构造函数
	 */
	public ResponseRoll(byte[] receiveMsg){
		this.recvMsg = receiveMsg;
		if( recvMsg != null){
			input = new DataInputStream(new ByteArrayInputStream(recvMsg));
			deal();
		}
	}
	
	/**
	 * 解析收到的冲正返回消息
	 *
	 */
	public void deal(){
		try{
//			解析包头信息
			input.read(Constant.TAG);
			input.read(Constant.RSP_ROLL_BAGLEN);
			input.read(Constant.VERSION);
			input.read(Constant.FACTORYID);
			input.read(Constant.TERMINALID);
			input.read(Constant.RSP_QUERY_TYPE);
			input.read(frameID);
			input.read(Constant.ERRSTATUS);
			input.read(macStr);
			//解析包体信息
			
			input.read(srvDateTime);//请求结果时间
			input.read(svrTraceID);//请求结果e票通流水号
			input.read(reqTraceID);//请求交易流水号
			input.read(oldTraceID);//返销流水号
			input.read(Constant.UNI_NUMBER_TYPE);
			input.read(userNumber);
			input.read(billValue);//缴费金额
			input.read(status);//请求结果码
			
		}catch(Exception e){
			Log.error("解析计费返回的查询消息出错！");
			Log.error(e);
		}
	}

	
	/**
	 * 请求结果说明
	 * @return
	 */
	public String getResponseInfo() {
		return new String(responseInfo).trim();
	}

	/**
	 * 请求结果编码
	 * @return
	 */
	public String getResponseCode() {
		return new String(status).trim();
	}

	/**
	 * 返销金额
	 * @param billValue
	 */
	public void setBillValue(byte[] billValue) {
		this.billValue = billValue;
	}
	
	/**
	 * 内部流水号
	 * @param frameID
	 */
	public void setFrameID(byte[] frameID) {
		this.frameID = frameID;
	}
	
	/**
	 * MD5验证码
	 * @param macStr
	 */
	public void setMacStr(byte[] macStr) {
		this.macStr = macStr;
	}
	
	/**
	 * 返销的流水号
	 * @param oldTraceID
	 */
	public void setOldTraceID(byte[] oldTraceID) {
		this.oldTraceID = oldTraceID;
	}
	
	/**
	 * 流水号
	 * @param reqTraceID
	 */
	public void setReqTraceID(byte[] reqTraceID) {
		this.reqTraceID = reqTraceID;
	}
	
	public String getReqTraceID() {
		return new String(reqTraceID).trim();
	}
	/**
	 * 计费时间
	 * @param srvDateTime
	 */
	public void setSrvDateTime(byte[] srvDateTime) {
		this.srvDateTime = srvDateTime;
	}
	
	/**
	 * 响应码
	 * @param status
	 */
	public void setStatus(byte[] status) {
		this.status = status;
	}
	
	/**
	 * 计费流水号
	 * @param svrTraceID
	 */
	public void setSvrTraceID(byte[] svrTraceID) {
		this.svrTraceID = svrTraceID;
	}
	
	/**
	 * 返销的用户号码
	 * @param userNumber
	 */
	public void setUserNumber(byte[] userNumber) {
		this.userNumber = userNumber;
	}
	
}
