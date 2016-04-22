package com.wlt.webm.business.unicom;

import java.io.ByteArrayOutputStream;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.Constant;
import com.wlt.webm.tool.Tools;

public class RequestFill {
	
	/**
	 * 流水号
	 */
	private byte[] frameID = new byte[16];
	
	/**
	 * MD5验证字符串
	 */
	private byte[] macStr = new byte[32];
	
	/**
	 * 终端电话号码
	 */
	private byte[] userNumber = new byte[20];
		
	/**
	 * 支付金额
	 */
	private byte[] billValue = new byte[10];
		
	/**
	 * 消息流水号
	 */
	private byte[] reqTraceID = new byte[16];
	/**
	 * 计费交易类型
	 */
	private byte[] type = new byte[4];
	
	/**
	 * 缴费消息组包
	 * @return 返回缴费消息组包
	 */
	public byte[] fillMsg(){
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try{
			//包头信息
			bout.write(Constant.TAG);
			bout.write(Constant.CMD_FILL_BAGLEN);
			bout.write(Constant.VERSION);
			bout.write("2124".getBytes());
			bout.write("20124007".getBytes());
			Log.info("11111111111111"+Constant.FACTORYID);
			Log.info("222222222222222222222"+Constant.TERMINALID);
			bout.write(type);
			bout.write(frameID);
			bout.write(Constant.ERRSTATUS);
			bout.write(macStr);
			//包体信息
			bout.write(Tools.getNow().getBytes());
			bout.write(reqTraceID);
			bout.write(Constant.UNI_NUMBER_TYPE);			
			bout.write(userNumber);
			bout.write(billValue);
						
		}catch(Exception e){
			Log.error("缴费组包异常");
			Log.error(e);
		}
		Log.info("广东联通缴费组包--------:" + new String(bout.toByteArray()));
//		System.out.println("缴费报文:" + new String(bout.toByteArray()));
//		System.out.println(new String(bout.toByteArray()).trim().length());
		return bout.toByteArray();
	}
	
	
	/**
	 * 充值金额
	 * @param billValue
	 */
	public void setBillValue(byte[] billValue) {
		this.billValue = billValue;
	}
	
	/**
	 * 流水号ID
	 * @param frameID
	 */
	public void setFrameID(byte[] frameID) {
		this.frameID = frameID;
	}

	/**
	 * MD5验证字符串
	 * @param macStr
	 */
	public void setMacStr(byte[] macStr) {
		this.macStr = macStr;
	}

	/**
	 * 消息流水号
	 * @param reqTraceID
	 */
	public void setReqTraceID(byte[] reqTraceID) {
		this.reqTraceID = reqTraceID;
	}
	
	/**
	 * 终端电话号码
	 * @param userNumber
	 */
	public void setUserNumber(byte[] userNumber) {
		this.userNumber = userNumber;
	}

	/**
	 * @param type 计费交易类型 
	 */
	public void setType(byte[] type) {
		this.type = type;
	}
	
	
}
