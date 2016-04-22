package com.wlt.webm.business.TZ.service;

import java.io.ByteArrayOutputStream;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.TZ.tool.HmacMD5;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.uictool.Tools;


public class ReverseTzMSg {
	
    /**
     *  获得话费冲正的报文
     * @param tradeTime
     * @param clientPh
     * @param cash
     * @param serial
     * @param oldSerial
     * @param frameID
     * @return
     */
	public byte[] getReverseMsg(String tradeTime,String clientPh,String cash,String serial,String oldSerial,String frameID){
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String headLength="0114";//报文体长度
		String mainKey=(String) MsgCache.tzSign.get("sign");
		//获得报文头
		String headMsg=MessageHead.getHeadMessage(headLength, mainKey, tradeTime, TianZuoParameters.TZ_REVERSE,frameID);
		//获得报文体
		String bodyMsg=getbodyMsg(clientPh,cash,serial,oldSerial);
		String signMessage=HmacMD5.crypt(headMsg+bodyMsg,mainKey);
		try{
			//包头信息
			bout.write(headMsg.getBytes());
			//报文体信息
			bout.write(bodyMsg.getBytes());
			//签名信息
			bout.write(signMessage.getBytes());
						
		}catch(Exception e){
			Log.error("冲正组包异常"+e.toString());
		}
		Log.info("冲正报文:" + new String(bout.toByteArray()));
		return bout.toByteArray();
	
	}
	
	
	/**
	 * 
	 * @param clientPh 客户号码
	 * @param cash     充值金额 单位分
	 * @param oldSerial  原充值业务流水号
	 * @param serial   发起方业务流水号
	 * @return         充值报文体
	 */
	public String getbodyMsg(String clientPh,String cash,String serial,String oldSerial){
		if(clientPh.length()<50)
			clientPh=Tools.dealClientPh(clientPh);
		if(cash.length()<16)
			cash=Tools.dealCash(cash);
		if(serial.length()<20)
			serial =Tools.dealSerial(serial);
		StringBuffer sf=new StringBuffer();
		sf.append(TianZuoParameters.TZ_TOPUPSTYLE)
		  .append(TianZuoParameters.TZ_TOPUPSORT)
		  .append(clientPh).append(cash).append(serial)
		  .append(oldSerial);
		System.err.println("冲正业务流水号:"+oldSerial);
		return sf.toString();
	}

}
