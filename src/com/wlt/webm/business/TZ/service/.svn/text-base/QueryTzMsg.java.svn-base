package com.wlt.webm.business.TZ.service;

import java.io.ByteArrayOutputStream;

import com.wlt.webm.business.TZ.tool.HmacMD5;
import com.wlt.webm.message.MsgCache;





public class QueryTzMsg {
	
	/**
	 * 获得合作代理商余额查询的报文
	 * @param tradeTime  交易时间
	 * @param frameID    报文流水号
	 * @return
	 */
	public byte[] getQueryMsg(String tradeTime,String frameID){
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String headLength="0000";//报文体长度
		String mainKey=(String) MsgCache.tzSign.get("sign");
		String headMsg=MessageHead.getHeadMessage(headLength, mainKey, tradeTime, TianZuoParameters.TZ_QUERY,frameID);
		String signMessage=HmacMD5.crypt(headMsg,mainKey);
		try{
			//包头信息
			bout.write(headMsg.getBytes());
			//签名信息
			bout.write(signMessage.getBytes());
						
		}catch(Exception e){
			System.out.println("TZ余额查询组包异常");
			System.out.println(e);
		}
		System.out.println("TZ余额查询报文:" + new String(bout.toByteArray()));
		return bout.toByteArray();
	
	}

}
