package com.wlt.webm.business.TZ.bean;

import java.util.ArrayList;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.Business;
import com.wlt.webm.business.TZ.service.TopUpTzMSg;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.uictool.Tools;

public class TopUpBusiness implements Business {

	/**
	 * ��Ϣ��ˮ��
	 */
	private String seqNo = null;
	/**
	 * ������ˮ��
	 */
	private String frameID = null;
	/**
	 * �ɷѽ��
	 */
	private String payFee = null;
	/**
	 * �ͻ�����
	 */
	private String phone  = null;
	/**
	 * ����ʱ��
	 */
	private String tradeTime=null;
	/**
	 * ����ҵ����ˮ��
	 */
	private String serialNo=null;
	
	public TopUpBusiness(String seqNo,String phone,String payFee,String tradeTime,
			String serialNo){
		this.seqNo=seqNo;
		this.phone=phone;
		this.payFee=payFee;
		this.tradeTime=tradeTime;
		this.serialNo=serialNo;
	}

	public int deal() {
		Log.info("��ʼ����TZ��ֵҵ�񡣡���");
		try {
			// ������ˮ��
			frameID = Tools.getSerialNumber();
			//�ɷ����
			TopUpTzMSg toUp =new TopUpTzMSg();
			byte[] sendMsg = toUp.getTopUpMsg(tradeTime, phone, payFee,serialNo, frameID);
			MsgCache.sendTZMsgCache.add(sendMsg);//���浽���Ͷ���
			//����ɷѷ�����Ϣ
			ArrayList list  = new ArrayList();
			list.add(phone.trim());
			list.add(payFee.trim());
			list.add(serialNo.trim());
			list.add(seqNo.trim());
			Log.info("���ͱ�����ˮ��frameID��"+frameID);
			MsgCache.addScpTZCache(frameID, list);
		} catch (Exception e) {
			Log.error("TZ�Ѵ���", e);
			return -1;
		}
	return 0;

	}



}
