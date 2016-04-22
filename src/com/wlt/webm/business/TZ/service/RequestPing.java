package com.wlt.webm.business.TZ.service;


import java.io.ByteArrayOutputStream;

import com.wlt.webm.business.TZ.tool.HmacMD5;
import com.wlt.webm.uictool.Tools;


		
	/**
	 * <p>Title:���Ӵ���ƽ̨</p>
	 * <p>Description: ������Ϣ���</p>
	 * <p>create: 2011-11-22</p>
	 * <p>Copyright: Copyright (c) 2011</p>
	 * <p>Company: ���������Ƽ����޹�˾</p>
	 * @author caiSJ
	 * @version 3.0.0.0
	 * 
	 */
public class RequestPing {
	
	/**
	 * ������Ϣ���
	 * @return ������Ϣ��
	 */
	public byte[] pingMsg(String mainKey){
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String headLength="0000";//�����峤��
		String tradeTime=Tools.getNow();//����ʱ��
		String headMsg=MessageHead.getHeadMessage(headLength, mainKey, tradeTime, TianZuoParameters.TZ_PING,Tools.getSerialNumber());
		String signMessage=HmacMD5.crypt(headMsg,mainKey);
		try{
			//��ͷ��Ϣ
			bout.write(headMsg.getBytes());
			//ǩ����Ϣ
			bout.write(signMessage.getBytes());
		}catch(Exception e){
			System.out.println("��������쳣");
			System.out.println(e);
		}
		System.out.println("��������:" + new String(bout.toByteArray()));
		return bout.toByteArray();
	}
	
}
