package com.wlt.webm.business.TZ.service;
import com.wlt.webm.business.TZ.tool.DESUtil;
import com.wlt.webm.uictool.Tools;


public class MessageHead {
	
	
	/**
	 * ���ݱ�����ĳ��ȴ�������ͷ
	 * @param  length  �����峤��
	 * @param  key    ����Կ
	 * @param  tradeTime   ����ʱ��
	 * @param  tradeCode   ���ױ��
	 * @param  serial      ������ˮ��
	 * @return  ����ͷ��Ϣ
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
