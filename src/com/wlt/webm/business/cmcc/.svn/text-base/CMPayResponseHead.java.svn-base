package com.wlt.webm.business.cmcc;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.UnsupportedEncodingException;

import com.commsoft.epay.util.logging.Log;

public class CMPayResponseHead {

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
	 * 默认构造函数
	 *
	 */
	public CMPayResponseHead(){
		
	}
	
	/**
	 * 构造函数
	 */
	public CMPayResponseHead(byte[] receiveMsg){
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
			input.read(len);
			input.read(SerialNumber);
			input.read(businessType);
			input.read(messageType);
			input.read(respondType);
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

	public String getRespondType() throws UnsupportedEncodingException {
		return new String(respondType,"iso-8859-1");
	}

	public String getSerialNumber() throws UnsupportedEncodingException {
		return new String(SerialNumber,"iso-8859-1");
	}

	}
