package com.wlt.webm.business.unicom;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.Constant;

public class ResponseSignIn {

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
	 * 充值/缴费类型
	 */
	private byte[] messageID = new byte[4]; 
	
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
	 * e票通平台受理时间
	 */
	private byte[] srvDateTime = new byte[14];
	
	/**
	 * 请求流水号
	 */
	private byte[] status = new byte[4];
	
	/**
	 * 请求信息说明
	 */
	private byte[] reserve = new byte[8];
	
	/**
	 * 默认构造函数
	 *
	 */
	public ResponseSignIn(){
		
	}
	
	/**
	 * 构造函数
	 */
	public ResponseSignIn(byte[] receiveMsg){
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
			input.read(messageID);
			input.read(frameID);
			input.read(errStatus);
			input.read(macStr);
			//解析包体信息
			
			input.read(status);//请求结果码
			input.read(srvDateTime);//请求结果时间
			input.read(reserve);//保留字段
		}catch(Exception e){
			Log.error("解析计费返回的查询消息出错！");
			Log.error(e);
		}
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
	public String getStatus() {
		return new String(status).trim();
	}
}
