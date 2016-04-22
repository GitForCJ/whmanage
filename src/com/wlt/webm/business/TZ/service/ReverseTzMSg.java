package com.wlt.webm.business.TZ.service;

import java.io.ByteArrayOutputStream;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.TZ.tool.HmacMD5;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.uictool.Tools;


public class ReverseTzMSg {
	
    /**
     *  ��û��ѳ����ı���
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
		String headLength="0114";//�����峤��
		String mainKey=(String) MsgCache.tzSign.get("sign");
		//��ñ���ͷ
		String headMsg=MessageHead.getHeadMessage(headLength, mainKey, tradeTime, TianZuoParameters.TZ_REVERSE,frameID);
		//��ñ�����
		String bodyMsg=getbodyMsg(clientPh,cash,serial,oldSerial);
		String signMessage=HmacMD5.crypt(headMsg+bodyMsg,mainKey);
		try{
			//��ͷ��Ϣ
			bout.write(headMsg.getBytes());
			//��������Ϣ
			bout.write(bodyMsg.getBytes());
			//ǩ����Ϣ
			bout.write(signMessage.getBytes());
						
		}catch(Exception e){
			Log.error("��������쳣"+e.toString());
		}
		Log.info("��������:" + new String(bout.toByteArray()));
		return bout.toByteArray();
	
	}
	
	
	/**
	 * 
	 * @param clientPh �ͻ�����
	 * @param cash     ��ֵ��� ��λ��
	 * @param oldSerial  ԭ��ֵҵ����ˮ��
	 * @param serial   ����ҵ����ˮ��
	 * @return         ��ֵ������
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
		System.err.println("����ҵ����ˮ��:"+oldSerial);
		return sf.toString();
	}

}
