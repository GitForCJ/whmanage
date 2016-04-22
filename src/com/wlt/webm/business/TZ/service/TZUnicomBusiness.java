package com.wlt.webm.business.TZ.service;


import java.util.ArrayList;

import com.commsoft.epay.esb.EsbExecption;
import com.commsoft.epay.esb.EsbService;
import com.commsoft.epay.esb.Message;
import com.commsoft.epay.esb.ServiceFactory;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.Business;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.message.TelecomMsg;
import com.wlt.webm.uictool.Constant;


/**
 * <p>Description: 深圳TZ接口返回消息组包类</p>
 * 
 */
public class TZUnicomBusiness implements Business{

	/**
	 * 接收计费消息
	 */
	private byte[] rcvMsg = null;
	/**
	 * 包头流水号
	 */
	private String frameID ="";;
	/**
	 * 缴费号码
	 */
	private String phone="";
	/**
	 * 内部流水号
	 */
	private String seqNo="";
	/**
	 * 业务流程码
	 */
	private String servNo="";
	/**
	 * 业务流程码
	 */
	private String tradeNo="";
	/**
	 * 终端号码
	 */
	private String termNo="";
	/**
	 * 业务处理线程
	 */
	public void deal() {
		int messageType=-1;
		if(rcvMsg==null || rcvMsg.length<132)//消息包为空，或者长度不够
		{
			Log.error("TZ 返回消息包为空，或者长度不够:"+new String(rcvMsg));
			return;
		}
		String msg=null;
	try{
	    msg=new String(rcvMsg);
		int bodyLg=Integer.parseInt(msg.substring(0, 4));
		String signStr=msg.substring(100+bodyLg, msg.length());
		//获取消息类型(交易编码)
        String msgType=msg.substring(34,40);
		messageType=Integer.parseInt(msgType);
		//唯一性标示的作为key
		frameID=msg.substring(4,20);
	}catch(Exception e)
		{
		    Log.error("获取消息出错TZUnicomBusiness"+e);
		}
	switch(messageType)
	{
		case 100006://心跳消息
			Log.info("心跳消息");
			MsgCache.addTZHtbMsg("xinTiao".getBytes());
			break;
		case 100001://签到响应
		    Log.info("签到响应消息："+msg);
			ResponseSignIn responseSignIn = new ResponseSignIn(msg);
			String status=responseSignIn.getResCode();
			//对响应码进行判断
			if (!status.equals(Constant.TZRESPONSE_SUCCESS)) {
				Log.error("交易失败 >> 业务流水号：" + frameID + "; 响应码:"
						+ status );
				return;
			}
			MsgCache.tzSign.put("sign",responseSignIn.getWorkKey());
			MsgCache.tzStatue=true;
			Log.info("签到得到的工作密钥"+(String)MsgCache.tzSign.get("sign"));
			MsgCache.addTZHtbMsg(rcvMsg);
			break;
			
		case 100002://充值响应
		    Log.info("充值响应消息："+msg);
		    MsgCache.addTZHtbMsg("chongZhi".getBytes());
		    TopUpBack topUpback = new TopUpBack(msg);
			String status2=topUpback.getResCode();
			// 将计费返回码转换为SCP返回码
			String respScpCode2 = TelecomMsg.telcomToScpRecode(status2);
			//从缓存中获取数据
			ArrayList temp2 = new ArrayList();
			temp2 =  MsgCache.getScpTZMap(frameID);
			if(temp2==null || temp2.size()!=2)
			{
				Log.error("查询找不到对应缓冲信息！");
				return;
			}
			ArrayList list2 = (ArrayList)temp2.get(0);
			phone = (String)list2.get(0);
			String payFee = (String)list2.get(1);
			String serialNo = (String)list2.get(2);
			//对响应码进行判断
			if (!status2.equals(Constant.TZRESPONSE_SUCCESS)) {
				Log.error("业务流水号：" + seqNo + "; 响应码:"+ status2) ;
                //更新订单为失败
				return;
			}
			String topUPBody=topUpback.getBody();
			String time=topUPBody.substring(0,14);  //受理方交易时间
			String serial=topUPBody.substring(14,34); //受理方返回的业务流水号 
			String agentMon=topUPBody.substring(34,50).replaceAll("^0+", ""); //代理商余额
            Log.info(" 受理方交易时间"+time+" 受理方业务流水号"+serial+" 代理商余额"+agentMon);
			// 支付金额
			String billValue = String.valueOf(Integer.parseInt(payFee));
			// 对账所需数据
			String checkBill = phone + "|" + billValue + "|" + serial
					+ "|" + serialNo;
			// 冲正所需数据
			String rollBill =phone+ "#" +billValue+"#"+ serialNo+"#"+serial;
            //更新订单状态 更新对账所需数据

			Log.info("TZ联通充值返回的消息，流水号：" + seqNo + "; 计费返回码：" + respScpCode2);
            break;
			
		case 100003://冲正响应
		    Log.info("冲正响应消息："+msg);
		    MsgCache.addTZHtbMsg("chongZheng".getBytes());
		    ReverseBack reverseBack = new ReverseBack(msg);
			String status4=reverseBack.getResCode();
			//对响应码进行判断
			 if(status4.equals("10007")){//表示交易不存在 ，不用冲正
					status4=Constant.RESPONSE_SUCCESS;
					Log.info("交易不存在,不用冲正");
					return ;
			 }
				//将计费的响应码转换成SCP的响应码
				String respScpCode4 = TelecomMsg.telcomToScpRecode(status4);
				//从缓存中获取数据
				ArrayList temp4 = new ArrayList();
				temp4 =  MsgCache.getScpTZMap(frameID);
				if(temp4==null || temp4.size()!=2)
				{
					Log.error("查询找不到冲正对应缓冲信息！");
					return;
				}
				ArrayList list4 = (ArrayList)temp4.get(0);
				seqNo = (String)list4.get(0);
				//发起方业务流水号
				String serialNo4 = (String)list4.get(1);
				//原充值发起方业务流水号
				String oldSerialNo4=(String)list4.get(2);
			if (!status4.equals(Constant.TZRESPONSE_SUCCESS)) {
				Log.error("业务流水号：" + seqNo + "; 响应码:" + status4);
				//更新 订单表为失败
				return;
			}
			String revserseBody=reverseBack.getBody();
			String tradetime=revserseBody.substring(0,14);  //受理方交易时间
			String reverseSerial=revserseBody.substring(14,34); //受理方业务流水号 
			String cph=revserseBody.substring(34,84).trim(); //客户号码
			String reverseCash=revserseBody.substring(84,100).replaceAll("^0+", ""); //冲正金额
			String leftCount=revserseBody.substring(100,110).replaceAll("^0+", "").replaceAll(" ", "");//剩余冲正次数
            Log.info("像scp返回冲正消息  受理方交易时间:"+tradetime+" 受理方业务流水号:"+reverseSerial+"客户号码:"+cph+" 冲正金额:"+reverseCash+"剩余冲正次数:"+leftCount);
			//更新订单状态
            Log.info("");
            break;
			
		default:
			Log.error("消息包类别无法识别");
			break;
		}
	}
	
	
	/**
	 * 设置处理消息
	 */
	public void setMessage(Object msg) {
		rcvMsg =(byte[]) msg;
	}

}

