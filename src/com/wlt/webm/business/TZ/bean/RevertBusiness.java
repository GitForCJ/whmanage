package com.wlt.webm.business.TZ.bean;

import java.util.ArrayList;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.Business;
import com.wlt.webm.business.TZ.service.ReverseTzMSg;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.uictool.Tools;

/**
 * <p>Description: ����ҵ�����̴�����</p>
 */
public class RevertBusiness implements Business{
	
	/**
	 *  ��Ϣ��ˮ��
	 */
	private String seqNo=null;
	/**
	 * ������ˮ��
	 */
	private String frameID = null;
	/**
	 * ����ҵ����ˮ��
	 */
	private String serialNo=null;
	/**
	 * ԭ��ֵ���׷���ҵ����ˮ��
	 */
	private String oldSerialNo=null;
	/**
	 * ����ʱ��
	 */
	private String tradeTime=null;
	/**
	 * �绰����
	 */
	private String phone=null;
	/**
	 * �������
	 */
	private String payfee=null;
	
	public RevertBusiness(String seqNo,String phone,String tradeTime,String serialNo,
			String oldSerialNo,String oldAgentSerialNo,String payfee){
		this.seqNo=seqNo;
		this.phone=phone;
		this.tradeTime=tradeTime;
		this.serialNo=serialNo;
		this.oldSerialNo=oldSerialNo;
		this.payfee=payfee;
	}
	
	/**
	 * ҵ������
	 */
	public int deal() {
		ReverseTzMSg roll = new ReverseTzMSg();
		try{
			//������ˮ��
			frameID =Tools.getSerialNumber();
			byte[] sendMsg = roll.getReverseMsg(tradeTime, phone, payfee, serialNo,oldSerialNo,frameID);
			MsgCache.sendTZMsgCache.add(sendMsg);//���浽���Ͷ���
			//����ɷѷ�����Ϣ
			ArrayList list  = new ArrayList();
			list.add(seqNo.trim());
			list.add(serialNo.trim());
			list.add(oldSerialNo.trim());
			MsgCache.addScpTZCache(frameID, list);
		}catch(Exception e){
			Log.error("TZ����,����������", e);
			return -1;
		}
		return 0;
	}


}
