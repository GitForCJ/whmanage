package com.wlt.webm.business.TZ.bean;

import java.util.ArrayList;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.Business;
import com.wlt.webm.business.TZ.service.ReverseTzMSg;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.uictool.Tools;

/**
 * <p>Description: 冲正业务流程处理类</p>
 */
public class RevertBusiness implements Business{
	
	/**
	 *  消息流水号
	 */
	private String seqNo=null;
	/**
	 * 报文流水号
	 */
	private String frameID = null;
	/**
	 * 发起方业务流水号
	 */
	private String serialNo=null;
	/**
	 * 原充值交易发起方业务流水号
	 */
	private String oldSerialNo=null;
	/**
	 * 交易时间
	 */
	private String tradeTime=null;
	/**
	 * 电话号码
	 */
	private String phone=null;
	/**
	 * 冲正金额
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
	 * 业务流程
	 */
	public int deal() {
		ReverseTzMSg roll = new ReverseTzMSg();
		try{
			//自增流水号
			frameID =Tools.getSerialNumber();
			byte[] sendMsg = roll.getReverseMsg(tradeTime, phone, payfee, serialNo,oldSerialNo,frameID);
			MsgCache.sendTZMsgCache.add(sendMsg);//保存到发送队列
			//保存缴费返回信息
			ArrayList list  = new ArrayList();
			list.add(seqNo.trim());
			list.add(serialNo.trim());
			list.add(oldSerialNo.trim());
			MsgCache.addScpTZCache(frameID, list);
		}catch(Exception e){
			Log.error("TZ冲正,解析包错误", e);
			return -1;
		}
		return 0;
	}


}
