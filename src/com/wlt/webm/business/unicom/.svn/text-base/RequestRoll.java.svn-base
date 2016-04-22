package com.wlt.webm.business.unicom;

import java.io.ByteArrayOutputStream;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.Constant;
import com.wlt.webm.tool.Tools;

public class RequestRoll {

	/**
	 * 终端流水号
	 */
	private byte[] frameID = new byte[16];
	
	/**
	 * 终端电话号码
	 */
	private byte[] userNumber = new byte[20];
	
	/**
	 * 支付金额
	 */
	private byte[] billValue = new byte[10];
	
	/**
	 * 流水号
	 */
	private byte[] reqTraceID = new byte[16];
	
	/**
	 * 冲正的交易流水号
	 */
	private byte[] oldReqTraceID = new byte[16];
	
	/**
	 * MD5验证字符串
	 */
	private byte[] macStr = new byte[32];
	/**
	 * 计费交易类型
	 */
	private byte[] type = new byte[4];
	/**
	 * 返销组包
	 * @return 返回返销组包信息
	 */
	public byte[] rollMsg(){
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try{
			//包头信息
			bout.write(Constant.TAG);
			bout.write(Constant.CMD_ROLL_BAGLEN);
			bout.write(Constant.VERSION);
			bout.write("2124".getBytes());
			bout.write("20124007".getBytes());
			bout.write(type);
			bout.write(frameID);
			bout.write(Constant.ERRSTATUS);
			bout.write(macStr);
			//包体信息
			bout.write(Tools.getNow().getBytes());
			bout.write(reqTraceID);
			bout.write(oldReqTraceID);
			bout.write(Constant.UNI_NUMBER_TYPE);			
			bout.write(userNumber);
			bout.write(billValue);
			
		}catch(Exception e){
			Log.error("返销组包异常");
			Log.error(e);
		}
		Log.info("返销组包:" + new String(bout.toByteArray()));
		return bout.toByteArray();
	
	}

	/**
	 *缴费金额
	 * @param billValue
	 */
	public void setBillValue(byte[] billValue) {
		this.billValue = billValue;
	}

	/**
	 * 终端流水号ID
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
	 * 需要冲正的流水号
	 * @param oldReqTraceID
	 */
	public void setOldReqTraceID(byte[] oldReqTraceID) {
		this.oldReqTraceID = oldReqTraceID;
	}
	
	/**
	 * 流水号ID
	 * @param reqTraceID
	 */
	public void setReqTraceID(byte[] reqTraceID) {
		this.reqTraceID = reqTraceID;
	}
	
	/**
	 * 终端号码
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
