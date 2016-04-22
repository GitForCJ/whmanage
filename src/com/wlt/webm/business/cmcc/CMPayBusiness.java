package com.wlt.webm.business.cmcc;


import javax.servlet.http.HttpSession;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.tool.Constant;

/**
 */
public class CMPayBusiness implements Business{
	

	/**
	 * 接收计费消息
	 */
	private byte[] rcvMsg = null;
	/**
	 * 消息类型
	 */
	private String messageType="";																                                                                                       
	/**
	 * 
	 */
	String PhoneFee  = "";
	/**
	 * 
	 */
	String SendSeqNo = "";
	/**
	 * 
	 */
	String userid="";
	/**
	 * 
	 */
	String sepNo="";
	/**
	 * 
	 */
	String nowTime="";
	/**
	 * 
	 */
	String empFee="";
	/**
	 * 
	 */
	String empAcctLevlOne="";
	/**
	 * 
	 */
	String empFeeLevlOne="";
	/**
	 * 
	 */
	String empAcctLevlTwo="";
	/**
	 * 
	 */
	String empFeeLevlTwo="";
	/**
	 * 
	 */
	String payphone="";
	/**
	 * 
	 */
	String payfee="";
	/**
	 * 
	 */
	String revSeqNo  ="";//接受方流水号
	/**
	 * 
	 */
	String tradeTime = "";//充值交易时间
	/**
	 * 
	 */
	String agentFee  = "";//代理商账户余额
	/**
	 * 
	 */
	String rollback = "";
	/**
	 * 
	 */
	String fundAcct02 = "";
	/**
	 * 业务处理线程
	 */
	public void deal() {
		try{
			if(rcvMsg.length<24)//消息包为空，或者长度不够
			{
				Log.error("消息包为空，或者长度不够1:"+rcvMsg.length);
				return;
			}
			CMPayResponseHead CMPayresponseHead = new CMPayResponseHead(rcvMsg);//解析接受到的报文
			String len =CMPayresponseHead.getLen() ;//获取报文长度
			messageType=CMPayresponseHead.getMessageType();//获取报文类型
			MobileChargeService service = new MobileChargeService();
			//唯一性标示的作为key
			int msgtype = Integer.parseInt(messageType);
			Log.info("消息类型:" + messageType);
			switch(msgtype)//报文类型选择
			{
				case 800119://心跳消息
					Log.info("收到移动心跳消息。");
					//System.out.println("收到移动心跳消息。");
					MsgCache.addMoveHtbMsg(rcvMsg);
					break;
					
				case 800001://登陆响应
				{
					MsgCache.addMoveHtbMsg(rcvMsg);
				    Log.info("签到响应消息："+new String(rcvMsg));
					String status=CMPayresponseHead.getRespondType();
					//对响应码进行判断
					if (!status.equals(Constant.RESPONSE_SUCCESS)) {
						Log.error("登陆失败 >> 响应码:"+ status );
						return;
					}
					MsgCache.signCache.put("signFlag", "success");
					
					break;
				}
				case 800002://注销响应
				{
					MsgCache.addMoveHtbMsg(rcvMsg);
					Log.info("签到响应消息："+new String(rcvMsg));
					String status=CMPayresponseHead.getRespondType();
					//对响应码进行判断
					if (!status.equals(Constant.RESPONSE_SUCCESS)) {
						Log.error("注销失败 >> 响应码:"+ status );
						return;
					}
					break;
				}
				case 800003://充值响应
				{
					MsgCache.addMoveHtbMsg(rcvMsg);
					Log.info("广东瑞通移动充值响应消息："+new String(rcvMsg));
					CMPayResponseFill response = new CMPayResponseFill(rcvMsg);
					String status=response.getRespondType();//获取得到的报文响应码，判断发送的报文是否响应成功
//					ArrayList list=new ArrayList();
//					ArrayList listuserid=new ArrayList();
//					revSeqNo  = response.getRevSeqNo();//接受方流水号
//					tradeTime = response.getTradeTime();//充值交易时间
//					agentFee  = response.getAgentFee();//代理商账户余额
//					PhoneFee  = response.getPhoneFee();//客户手机余额
					SendSeqNo = response.getSendSeqNo();//发起方流水号
					Log.info("准备取出"+SendSeqNo+"==="+status+"----");
					    if(status.trim().equals("0000")){
					    	Log.info("准备放入----------------"+SendSeqNo+status);
					    	//MsgCache.cmcc.put("cmccpay"+SendSeqNo,"0");
					    	service.insertState("cmccpay"+SendSeqNo,"0");
								
						}else{
							//MsgCache.cmcc.put("cmccpay"+SendSeqNo,"2");
							service.insertState("cmccpay"+SendSeqNo,"2");
							return;
							
						}							
					break;
				}
				case 800004://冲正响应
				{
					MsgCache.addMoveHtbMsg(rcvMsg);
					Log.info("广东瑞通移动冲正响应消息："+new String(rcvMsg));
					CMPayResponseUndoPay response = new CMPayResponseUndoPay(rcvMsg);
					String status=response.getRespondType();
					SendSeqNo = response.getSepNo();
					 if(status.equals("0000")){
							//MsgCache.cmcc.put("cmccRepay"+SendSeqNo,"0");
							service.insertState("cmccRepay"+SendSeqNo,"0");
						}else{
							//MsgCache.cmcc.put("cmccRepay"+SendSeqNo,"1");
							service.insertState("cmccRepay"+SendSeqNo,"1");
							return;
						}				
					break;
				}
				default:
				{
					MsgCache.addMoveHtbMsg(rcvMsg);
					Log.error("移动接受消息类别无法识别:" + new String(rcvMsg));
					
					break;
				}
			}
		}catch(Exception e)
		{
			Log.error("解析移动缴费消息，业务处理线程错误", e);
		}
	}
	
	/**
	 * 设置处理消息
	 */
	public void setMessage(Object msg) {
		
		rcvMsg =(byte[]) msg;
	}
	
	

}
