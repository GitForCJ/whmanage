package com.wlt.webm.business.TZ.service;

import java.io.ByteArrayOutputStream;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.TZ.tool.HmacMD5;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.uictool.Tools;


public class TopUpTzMSg {
	
		/**
		 * 获得合作代理商余额查询的报文
		 * @param tradeTime  交易时间
		 * @param clientPh   客户号码
		 * @param cash       交易金额
		 * @param serial     
		 * @param frameID     
		 * @return
		 */
	public byte[] getTopUpMsg(String tradeTime,String clientPh,String cash,String serial,String frameID){

		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String headLength="0094";//报文体长度
		String mainKey=(String) MsgCache.tzSign.get("sign");
		//获得报文头
		String headMsg=MessageHead.getHeadMessage(headLength, mainKey, tradeTime, TianZuoParameters.TZ_TOPUP,frameID);
		//获得报文体
		String bodyMsg=getbodyMsg(clientPh,cash,serial);
		String signMessage=HmacMD5.crypt(headMsg+bodyMsg,mainKey);
		try{
			//包头信息
			bout.write(headMsg.getBytes());
			//报文体信息
			bout.write(bodyMsg.getBytes());
			//签名信息
			bout.write(signMessage.getBytes());
						
		}catch(Exception e){
			Log.error("充值组包异常"+e.toString());
		}
		Log.info("充值报文:" + new String(bout.toByteArray()));
		return bout.toByteArray();
	
	}
	
	
	/**
	 * 
	 * @param clientPh 客户号码
	 * @param cash     充值金额 单位分
	 * @param serial   发起方业务流水号
	 * @return         充值报文体
	 */
	public String getbodyMsg(String clientPh,String cash,String serial){
		if(clientPh.length()<50)
			clientPh=Tools.dealClientPh(clientPh);
		if(cash.length()<16)
			cash=Tools.dealCash(cash);
		if(serial.length()<20)
			serial =Tools.dealSerial(serial);
		StringBuffer sf=new StringBuffer();
		sf.append(TianZuoParameters.TZ_TOPUPSTYLE)
		  .append(TianZuoParameters.TZ_TOPUPSORT)
		  .append(clientPh).append(cash).append(serial);
		Log.info("充值流水号:"+serial);
		return sf.toString();
	}

}
