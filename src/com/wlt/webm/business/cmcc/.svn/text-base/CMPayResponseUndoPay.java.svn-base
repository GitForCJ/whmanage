package com.wlt.webm.business.cmcc;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.UnsupportedEncodingException;

import com.commsoft.epay.util.logging.Log;

/**
 * 
 */
public class CMPayResponseUndoPay {

	/**
	 * 返回的数据信息
	 */
	private byte[] recvMsg;
	
	/**
	 * 数据输入流
	 */
	private DataInputStream input;
	
	/**
	 * 报文长度
	 */
	private byte[] len = new byte[4];
	/**
	 * 报文序列号
	 */
	private byte[] SerialNumber = new byte[10];
	
	/**
	 * 业务类型
	 */
	private byte[] businessType = new byte[4];
	/**
	 * 消息码
	 */
	private byte[] messageType = new byte[6];
	/**
	 * 交易响应码
	 */
	private byte[] respondType = new byte[4];
	/**
	 * 接收方冲正流水号
	 */
	private byte[] SeqNumOne = new byte[32];
	/**
	 * 被冲正的充值流水号
	 */
	private byte[] SeqNumTwo = new byte[32];
	/**
	 * 冲正交易时间
	 */
	private byte[] PayTime = new byte[14];
	/**
	 * 发起方流水号
	 */
	private byte[] SepNo = new byte[20];
	/**
	 * 默认构造函数
	 *
	 */
	public CMPayResponseUndoPay(){
		
	}
	
	public CMPayResponseUndoPay(byte[] receiveMsg){
		this.recvMsg = receiveMsg;
		if( recvMsg != null){
			input = new DataInputStream(new ByteArrayInputStream(recvMsg));
			deal();
		}
	}
	
	/**
	 * 解析接收到的计费消息
	 *
	 */
	public void deal(){
		try{
			//解析包头信息
			input.read(len);//报文长度
			input.read(SerialNumber);//报文序列号
			input.read(businessType);//业务类型
			input.read(messageType);//消息码
			input.read(respondType);//交易响应码
			//解析包体信息
			input.read(SeqNumOne);//接收方冲正流水号
			input.read(SeqNumTwo);//被冲正的充值流水号
			input.read(PayTime);//冲正交易时间
			input.read(SepNo);//发起方流水号
		}catch(Exception e){
			Log.error("解析计费返回的查询消息出错！");
			Log.error(e);
		}
	}

	public String getBusinessType() throws UnsupportedEncodingException {
		return new String(businessType,"iso-8859-1");
	}

	public String getLen() throws UnsupportedEncodingException {
		return new String(len,"iso-8859-1");
	}

	public String getMessageType() throws UnsupportedEncodingException {
		return new String(messageType,"iso-8859-1");
	}

	public String getPayTime() throws UnsupportedEncodingException {
		return new String(PayTime,"iso-8859-1");
	}

	public String getRecvMsg() throws UnsupportedEncodingException {
		return new String(recvMsg,"iso-8859-1");
	}

	public String getRespondType() throws UnsupportedEncodingException {
		return new String(respondType,"iso-8859-1");
	}

	public String getSepNo() throws UnsupportedEncodingException {
		return new String(SepNo,"iso-8859-1");
	}

	public String getSeqNumOne() throws UnsupportedEncodingException {
		return new String(SeqNumOne,"iso-8859-1");
	}

	public String getSeqNumTwo() throws UnsupportedEncodingException {
		return new String(SeqNumTwo,"iso-8859-1");
	}

	public String getSerialNumber() throws UnsupportedEncodingException {
		return new String(SerialNumber,"iso-8859-1");
	}


}
