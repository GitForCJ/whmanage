package com.wlt.webm.business.unicom;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.Constant;

public class ResponseHead {

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
	 * 包头
	 */
	private byte[] len = new byte[4];
	
	/**
	 * 包头流水号
	 */
	private byte[] frameID = new byte[16];
	/**
	 * 消息类型号
	 */
	private byte[] msgType = new byte[4];
	
	/**
	 * 报文头回应状态
	 */
	private byte[] errStatus = new byte[4];
	
	/**
	 * MD5验证字符串
	 */
	private byte[] macStr = new byte[32];
	/**
	 * 默认构造函数
	 *
	 */
	public ResponseHead(){
		
	}
	
	/**
	 * 构造函数
	 */
	public ResponseHead(byte[] receiveMsg){
		this.recvMsg = receiveMsg;
		if( recvMsg != null){
			input = new DataInputStream(new ByteArrayInputStream(recvMsg));
			deal();
		}else{
			Log.error("返回消息为空....");
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
			input.read(len);
			input.read(Constant.VERSION);
			input.read(Constant.FACTORYID);
			input.read(Constant.TERMINALID);
			input.read(msgType);
			input.read(frameID);
			input.read(errStatus);
			input.read(macStr);
		}catch(Exception e){
			Log.error("解析计费返回的查询消息出错！");
			Log.error(e);
		}
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
	 * @return 消息类型号
	 */
	public String getMsgType() {
		return  new String(msgType).trim();
	}

	/**
	 * @return 包头 
	 */
	public String getTag() {
		return  new String(tag).trim();
	}

	/**
	 * @return 报文回应状态
	 */
	public String getErrStatus() {
		return new String(errStatus).trim();
	}
}
