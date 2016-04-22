package com.wlt.webm.business.TZ.bean;

import java.util.ArrayList;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.Business;
import com.wlt.webm.business.TZ.service.TopUpTzMSg;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.uictool.Tools;

public class TopUpBusiness implements Business {

	/**
	 * 消息流水号
	 */
	private String seqNo = null;
	/**
	 * 报文流水号
	 */
	private String frameID = null;
	/**
	 * 缴费金额
	 */
	private String payFee = null;
	/**
	 * 客户号码
	 */
	private String phone  = null;
	/**
	 * 交易时间
	 */
	private String tradeTime=null;
	/**
	 * 发起方业务流水号
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
		Log.info("开始处理TZ充值业务。。。");
		try {
			// 报文流水号
			frameID = Tools.getSerialNumber();
			//缴费组包
			TopUpTzMSg toUp =new TopUpTzMSg();
			byte[] sendMsg = toUp.getTopUpMsg(tradeTime, phone, payFee,serialNo, frameID);
			MsgCache.sendTZMsgCache.add(sendMsg);//保存到发送队列
			//保存缴费返回信息
			ArrayList list  = new ArrayList();
			list.add(phone.trim());
			list.add(payFee.trim());
			list.add(serialNo.trim());
			list.add(seqNo.trim());
			Log.info("发送报文流水号frameID："+frameID);
			MsgCache.addScpTZCache(frameID, list);
		} catch (Exception e) {
			Log.error("TZ费错误", e);
			return -1;
		}
	return 0;

	}



}
