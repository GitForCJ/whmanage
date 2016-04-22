package com.wlt.webm.business.cmcc;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.UnsupportedEncodingException;

import com.commsoft.epay.util.logging.Log;

public class CMPayResponseFill {

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
	 * 接受方流水号
	 */
	private byte[] revSeqNo = new byte[32];
	
	/**
	 * 充值交易时间
	 */
	private byte[] tradeTime = new byte[14];
	
	/**
	 * 代理商账户余额
	 */
	private byte[] agentFee = new byte[16];
	
	/**
	 * 客户手机余额
	 */
	private byte[] PhoneFee = new byte[16];
	
	/**
	 * 发起方流水号
	 */
	private byte[] SendSeqNo = new byte[20];
	/**
	 * 默认构造函数
	 *
	 */
	public CMPayResponseFill(){
		
	}
	
	/**
	 * 构造函数
	 */
	public CMPayResponseFill(byte[] receiveMsg){
		this.recvMsg = receiveMsg;
		if( recvMsg != null){
			input = new DataInputStream(new ByteArrayInputStream(recvMsg));
			deal();
		}else{
			respondType=null;
		}
	}
	
	/**
	 * 解析收到的缴费返回消息
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
			input.read(revSeqNo);//接收方流水号
			input.read(tradeTime);//充值交易时间
			input.read(agentFee);//代理商帐户余额
			input.read(PhoneFee);//客户手机余额
			input.read(SendSeqNo);//发起方流水号
			
			
		}catch(Exception e){
			Log.error("解析计费返回的查询消息出错！");
			Log.error(e);
		}
	}

	public String getAgentFee() throws UnsupportedEncodingException {
		return new String(agentFee,"iso-8859-1");
	}

	public String getBusinessType() throws UnsupportedEncodingException{
		return new String(businessType,"iso-8859-1");
	}

	public String getLen() throws UnsupportedEncodingException{
		return new String(len,"iso-8859-1");
	}

	public String getMessageType() throws UnsupportedEncodingException{
		return new String(messageType,"iso-8859-1");
	}

	public String getPhoneFee() throws UnsupportedEncodingException{
		return new String(PhoneFee,"iso-8859-1");
	}

	public String getRespondType() throws UnsupportedEncodingException{
		return new String(respondType,"iso-8859-1");
	}

	public String getRevSeqNo() throws UnsupportedEncodingException{
		return new String(revSeqNo,"iso-8859-1");
	}

	public String getSendSeqNo() throws UnsupportedEncodingException{
		return new String(SendSeqNo,"iso-8859-1");
	}

	public String getSerialNumber() throws UnsupportedEncodingException{
		return new String(SerialNumber,"iso-8859-1");
	}

	public String getTradeTime() throws UnsupportedEncodingException{
		return new String(tradeTime,"iso-8859-1");
	}
	
}
