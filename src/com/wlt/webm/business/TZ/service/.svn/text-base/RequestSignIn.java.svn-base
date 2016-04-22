package com.wlt.webm.business.TZ.service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.TZ.tool.HmacMD5;
import com.wlt.webm.uictool.Tools;


/**
 * <p>Title:电子代办平台</p>
 * <p>Description: 天作签到交易类</p>
 * <p>create: 2011-11-22</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: 深圳市万恒科技有限公司</p>
 * @author caiSJ
 * @version 3.0.0.0
 */
public class RequestSignIn {
	
	/**
	 * 签到组包
	 * @return 
	 */
	public byte[] signInMsg(String mainKey){
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String headLength="0000";//报文体长度
		String tradeTime=Tools.getNow();//交易时间
		String headMsg=MessageHead.getHeadMessage(headLength, mainKey, tradeTime, TianZuoParameters.TZ_SIGN,Tools.getSerialNumber());
		String signMessage=HmacMD5.crypt(headMsg,mainKey);
		Log.info("签到加密"+signMessage);
		try{
			//包头信息
			bout.write(headMsg.getBytes());
			//签名信息
			bout.write(signMessage.getBytes());
						
		}catch(Exception e){
			Log.error("签到组包异常"+e.toString());
		}
		Log.info("签到报文:" + new String(bout.toByteArray()));
		return bout.toByteArray();
	}
	
	
	/**
	 * test
	 * @param args
	 */
	public static void main(String[] args){
//		String headLength="0000";//报文体长度
//		String tradeTime=Tools.getNow();//交易时间
//		String headMsg=MessageHead.getHeadMessage(headLength, TianZuoParameters.TZ_MAINKEY, tradeTime, TianZuoParameters.TZ_SIGN);
//		String signMessage=HmacMD5.crypt(headMsg,TianZuoParameters.TZ_MAINKEY);
//		System.out.println(headMsg.length());
//		System.out.println(signMessage);
		RequestSignIn ss=new RequestSignIn();
		ss.signInMsg("00404738");
//		Map maps=new HashMap();
//        System.out.println(maps.size());
		
	}
	
}
