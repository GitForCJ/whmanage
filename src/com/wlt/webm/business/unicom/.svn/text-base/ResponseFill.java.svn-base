package com.wlt.webm.business.unicom;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.Constant;
import com.wlt.webm.tool.Tools;

public class ResponseFill {

	/**
	 * 返回的数据信息
	 */
	private byte[] recvMsg;
	
	/**
	 * 数据输入流
	 */
	private DataInputStream input;
	
	/**
	 * 包头
	 */
	private byte[] tag = new byte[2];
	
	/**
	 * 包头流水号
	 */
	private byte[] frameID = new byte[16];
	
	/**
	 * 报文回应状态
	 */
	private byte[] errStatus = new byte[4];
	
	/**
	 * MD5验证字符串
	 */
	private byte[] macStr = new byte[32];
	
	/**
	 * 联通平台受理时间
	 */
	private byte[] srvDateTime = new byte[14];
	/**
	 * 联通返回的流水号
	 */
	private byte[] svrTraceID = new byte[16];
	
	/**
	 * 请求流水号
	 */
	private byte[] reqTraceID = new byte[16];
	
	private byte[] userNumber = new byte[20];
	
	private byte[] billValue = new byte[10];
	
	private byte[] status = new byte[4];
	
	private byte[] reserve = new byte[50];
	
	/**
	 * 默认构造函数
	 *
	 */
	public ResponseFill(){
		
	}
	
	/**
	 * 构造函数
	 */
	public ResponseFill(byte[] receiveMsg){
		this.recvMsg = receiveMsg;
		if( recvMsg != null){
			input = new DataInputStream(new ByteArrayInputStream(recvMsg));
			deal();
		}else{
			status=null;
		}
	}
	
	/**
	 * 解析收到的缴费返回消息
	 *
	 */
	public void deal(){
		try{
			//解析包头信息
			input.read(tag);
			input.read(Constant.RSP_FILL_BAGLEN);
			input.read(Constant.VERSION);
			input.read(Constant.FACTORYID);
			input.read(Constant.TERMINALID);
			input.read(Constant.RSP_FILL_TYPE);
			input.read(frameID);
			input.read(errStatus);
			input.read(macStr);
			//解析包体信息
			
			input.read(srvDateTime);//请求结果时间
			input.read(svrTraceID);//请求结果联通流水号
			input.read(reqTraceID);//请求结果联通流水号
			input.read(Constant.UNI_NUMBER_TYPE);//请求结果联通流水号
			input.read(userNumber);//被充值电话号码
			input.read(billValue);//缴费金额
			input.read(status);//请求结果码
			input.read(reserve);//保留字段
			
		}catch(Exception e){
			Log.error("解析计费返回的查询消息出错！");
			Log.error(e);
		}
	}
	
	public String getReqTraceID() {
		return new String(reqTraceID).trim();
	}

	public void setReqTraceID(byte[] reqTraceID) {
		this.reqTraceID = reqTraceID;
	}

	/**
	 * 支付金额
	 * @return
	 */
	public String getBillValue() {
		return Tools.yuanToFen(new String(billValue).trim());
	}

	/**
	 * 包头流水号
	 * @return
	 */
	public String getFrameID() {
		return new String(frameID).trim();
	}

	/**
	 * 支付类型
	 * @return
	 */
	public String getPayType() {
		return new String(Constant.UNI_NUMBER_TYPE);
	}
	
	/**
	 * 请求结果说明
	 * @return
	 */
	public String getRreserve() {
		return new String(reserve).trim();
	}

	/**
	 * 请求结果编码
	 * @return
	 */
	public String getResponseCode() {
//		return new String(responseCode).trim();
//		String code = new String(responseCode).trim();
//		if(code.equals("6750") || code.equals("6751")){
//			code = new String(status);
//		}
		return new String(status);
	}

	/**
	 * 联通返回的流水号
	 * @return
	 */
	public String getSvrTraceID() {
		return new String(svrTraceID).trim();
	}

	/**
	 * @return 
	 */
	public String getErrStatus() {
		return new String(errStatus).trim();
	}
}
