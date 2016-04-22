package com.wlt.webm.business.TZ.service;
import com.wlt.webm.business.TZ.tool.DESUtil;
import com.wlt.webm.uictool.Tools;


public class MessageHead {
	
	
	/**
	 * 根据报文体的长度创建报文头
	 * @param  length  报文体长度
	 * @param  key    主密钥
	 * @param  tradeTime   交易时间
	 * @param  tradeCode   交易编号
	 * @param  serial      报文流水号
	 * @return  报文头信息
	 */
	public static String getHeadMessage(String length,String key,String tradeTime,String tradeCode,String serial){
		String coorperatePwd=DESUtil.encrypt(TianZuoParameters.TZ_COOPERATEPASSWORD,key);
		String agentPWD=Tools.getAgentPWD(TianZuoParameters.TZ_AGENTPASSWORD);
		String agentPwd=DESUtil.encrypt(agentPWD,key);
		String agentNumber=TianZuoParameters.TZ_AGENTNUMBER.length()==13 ? TianZuoParameters.TZ_AGENTNUMBER : Tools.getAgentID(TianZuoParameters.TZ_AGENTNUMBER);
		StringBuffer headMsg=new StringBuffer();
		headMsg.append(length)
		.append(serial)
		.append(tradeTime)
		.append(tradeCode)
		.append(TianZuoParameters.TZ_COOPERATEID)
		.append(coorperatePwd)
		.append(agentNumber)
		.append(agentPwd)
		.append("     "); 
		
		return headMsg.toString();
	}
	

}
