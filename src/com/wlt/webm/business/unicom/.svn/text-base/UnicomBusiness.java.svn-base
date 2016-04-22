package com.wlt.webm.business.unicom;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.tool.Constant;

/**
 * 
 */
public class UnicomBusiness{

	/**
	 * 接收计费消息
	 */
	private byte[] rcvMsg = null;
	/**
	 * 消息类型
	 */
	private String msgType="";
	/**
	 * 包头流水号
	 */
	private String frameID ="";;
	/**
	 * 业务处理线程
	 */
	public void deal() {
		try{
			
			if(rcvMsg==null || rcvMsg.length<5)//消息包为空，或者长度不够
			{
				Log.error("消息包为空，或者长度不够2:"+new String(rcvMsg));
				return;
			}
			ResponseHead responseHead = new ResponseHead(rcvMsg);
			String tag =responseHead.getTag() ;//是否包含报头WT
			if(!tag.equals("WT"))
			{
				Log.info("验证计费消息格式错误:"+new String(rcvMsg));
				return;
			}
			//唯一性标示的作为key
			frameID=responseHead.getFrameID(); 	//流水号
			msgType =responseHead.getMsgType();  //交易码
			int msgtype = Integer.parseInt( msgType);
			Log.info("消息体"+new String(rcvMsg)+"----消息类型:" + msgType);
			switch(msgtype)
			{
				case 1202://心跳消息
					MsgCache.addHtbMsg(rcvMsg);
					break;
					
				case 1201://签到响应
				{
				    Log.info("签到响应消息："+new String(rcvMsg));
					ResponseSignIn responseSignIn = new ResponseSignIn(rcvMsg);
					String status=responseSignIn.getStatus();
					//对响应码进行判断
					if (!status.equals(Constant.RESPONSE_SUCCESS)) {
						Log.error("交易失败 >> 业务流水号：" + frameID + "; 响应码:"
								+ status );
						return;
					}
					MsgCache.signCache.put("signFlag", "success");
					MsgCache.addHtbMsg(rcvMsg);
					break;
				}
				case 1203://充值响应
				{
					Log.info("充值响应消息："+new String(rcvMsg));
					ResponseFill response = new ResponseFill(rcvMsg);
					String status=response.getResponseCode();
					String SendSeqNo=response.getReqTraceID();
					Log.info("广东联通充值成功--返回码"+status+"========="+SendSeqNo);
					 if(status.equals("0000")){
						 MsgCache.unicom.put("unicomPay"+SendSeqNo,"0");
							
						}else{
							Log.info("广东联通充值消息返回响应不正确---"+status+"unicom"+SendSeqNo);
							 MsgCache.unicom.put("unicomPay"+SendSeqNo,"1");
							return;
						}			
					break;
				}
				case 1204://冲正响应
				{
					Log.info("冲正响应消息："+new String(rcvMsg));
					ResponseRoll response = new ResponseRoll(rcvMsg);
					String status=response.getResponseCode();
					String SendSeqNo=response.getReqTraceID();
					 if(status.equals("0000")){//表示交易没有成功，不用冲正\
						 Log.info("广东联通冲正成功"+SendSeqNo);
						 MsgCache.unicom.put("unicomRePay"+SendSeqNo,"0");
					 }else{
						 Log.info("广东联通冲正失败"+SendSeqNo);
						 MsgCache.unicom.put("unicomRePay"+SendSeqNo,"1");
					 }
					
					break;
				}
				default:
				{
					Log.error("计费消息包类别无法识别:" + new String(rcvMsg));
					break;
				}
			}
		}catch(Exception e)
		{
			//Log.error("解析计费消息，业务处理线程错误", e);
		}
		
	}
	/**
	 * 设置处理消息
	 */
	public void setMessage(Object msg) {
		
		rcvMsg =(byte[]) msg;
	}
	
	

}
