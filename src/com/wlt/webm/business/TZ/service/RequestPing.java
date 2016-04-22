package com.wlt.webm.business.TZ.service;


import java.io.ByteArrayOutputStream;

import com.wlt.webm.business.TZ.tool.HmacMD5;
import com.wlt.webm.uictool.Tools;


		
	/**
	 * <p>Title:电子代办平台</p>
	 * <p>Description: 心跳消息组包</p>
	 * <p>create: 2011-11-22</p>
	 * <p>Copyright: Copyright (c) 2011</p>
	 * <p>Company: 深圳市万恒科技有限公司</p>
	 * @author caiSJ
	 * @version 3.0.0.0
	 * 
	 */
public class RequestPing {
	
	/**
	 * 心跳消息组包
	 * @return 心跳消息包
	 */
	public byte[] pingMsg(String mainKey){
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String headLength="0000";//报文体长度
		String tradeTime=Tools.getNow();//交易时间
		String headMsg=MessageHead.getHeadMessage(headLength, mainKey, tradeTime, TianZuoParameters.TZ_PING,Tools.getSerialNumber());
		String signMessage=HmacMD5.crypt(headMsg,mainKey);
		try{
			//包头信息
			bout.write(headMsg.getBytes());
			//签名信息
			bout.write(signMessage.getBytes());
		}catch(Exception e){
			System.out.println("心跳组包异常");
			System.out.println(e);
		}
		System.out.println("心跳报文:" + new String(bout.toByteArray()));
		return bout.toByteArray();
	}
	
}
