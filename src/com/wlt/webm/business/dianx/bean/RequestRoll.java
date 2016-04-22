package com.wlt.webm.business.dianx.bean;

import java.io.ByteArrayOutputStream;

import com.wlt.webm.uictool.Constant;
import com.wlt.webm.uictool.MD5;
import com.wlt.webm.uictool.Tools;
import com.commsoft.epay.util.logging.Log;

public class RequestRoll {

	/**
	 * 流水号
	 */
	private byte[] serialNo = new byte[10];
	
	/**
	 * 包总长度
	 */
	private byte[] bagLength = new byte[8];
	
	/**
	 * 终端电话号码
	 */
	private String termPhoneNum = "";
	
	/**
	 * 交易账号
	 */
	private String accountNo = "";
	
	/**
	 * 支付金额
	 */
	private String payFee = "";
	
	/**
	 * 终端流水号
	 */
	private String messageSerialNo = "";
	
	/**
	 * 冲正的交易流水号
	 */
	private String rollSerialNo = "";

	/**
	 * 返销组包
	 * @return 返回返销组包信息
	 */
	public byte[] rollMsg(){
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try{
			//包头信息
			bout.write(Constant.BAG_HEAD);
			bout.write(serialNo);
			bout.write(bagLength);
			bout.write(Constant.ROLL_TYPE);
			bout.write("1".getBytes());//复合结构
			bout.write("0".getBytes());//不校验
			bout.write("01".getBytes());//参数个数
			bout.write(Constant.SEND_ID);
			bout.write(Constant.RECEIVE_ID);
			bout.write(Constant.BLANK_SPACE);
			bout.write(Constant.CHECK_INFO);
			//包体信息
			bout.write("708".getBytes());
			bout.write("001".getBytes());
			bout.write("08".getBytes());
			
			bout.write("7081".getBytes());
			bout.write("0004".getBytes());
			bout.write(Constant.AGENT_CODE.getBytes());
			
			bout.write("7082".getBytes());
			bout.write(Tools.add0(String.valueOf(Constant.AGENT_PASSWORD.length()), 4).getBytes());
			bout.write(Constant.AGENT_PASSWORD.getBytes());
			
			bout.write("7083".getBytes());//根据省代平台新协议修改
			bout.write("0008".getBytes());
			bout.write("99990007".getBytes());
//			bout.write(Tools.add0(String.valueOf(termPhoneNum.trim().length()),4).getBytes());
//			bout.write(termPhoneNum.trim().getBytes());
			
			bout.write("7085".getBytes());
			bout.write(Tools.add0(String.valueOf(accountNo.trim().length()), 4).getBytes());
			bout.write(accountNo.trim().getBytes());
			
			bout.write("7086".getBytes());
			bout.write(Tools.add0(String.valueOf(payFee.trim().length()), 4).getBytes());
			bout.write(payFee.getBytes());
			
			bout.write("7087".getBytes());
			bout.write(Tools.add0(String.valueOf(rollSerialNo.trim().length()), 4).getBytes());
			bout.write(rollSerialNo.trim().getBytes());
			
			bout.write("7092".getBytes());
			bout.write("0000".getBytes());
			
//			bout.write("7093".getBytes());
//			bout.write("0000".getBytes());
//			bout.write(Tools.add0(String.valueOf(Constant.AGENT_IP.length()),4).getBytes());
//			bout.write(Constant.AGENT_IP.getBytes());
			
			bout.write("7095".getBytes());
			bout.write(Tools.add0(String.valueOf(messageSerialNo.length()), 4).getBytes());
			bout.write(messageSerialNo.getBytes());
			
		}catch(Exception e){
			Log.error("返销组包异常");
			Log.error(e);
		}
//		Log.info("返销组包:" + new String(bout.toByteArray()));
		System.out.println("返销报文:" + new String(bout.toByteArray()));
		System.out.println(new String(bout.toByteArray()).length());
		return bout.toByteArray();
	
	}
	
	/**
	 * 交易账号
	 * @param accountNo
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * 包长度
	 * @param length
	 */
	public void setBagLength(int length) {
		System.out.println(length);
		this.bagLength = Tools.add0(String.valueOf(Constant.BAG_LENGTH_MAC + length + 164),8).getBytes();
	}

	/**
	 * 支付金额
	 * @param payFee
	 */
	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}

	/**
	 * 冲正交易流水号
	 * @param rollSerialNo
	 */
	public void setRollSerialNo(String rollSerialNo) {
		this.rollSerialNo = rollSerialNo;
	}

	/**
	 * 流水号
	 * @param serialNo
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = Tools.add0(serialNo,10).getBytes();
	}

	/**
	 * 终端电话号码
	 * @param termPhoneNum
	 */
	public void setTermPhoneNum(String termPhoneNum) {
		this.termPhoneNum = termPhoneNum;
	}

	/**
	 * 消息流水号
	 * @param messageSerialNo
	 */
	public void setMessageSerialNo(String messageSerialNo) {
		this.messageSerialNo = messageSerialNo;
	}
	
	
}
