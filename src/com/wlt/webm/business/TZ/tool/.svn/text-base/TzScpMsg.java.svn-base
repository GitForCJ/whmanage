package com.wlt.webm.business.TZ.tool;

import java.util.HashMap;
import java.util.Map;

import com.commsoft.epay.esb.Message;
import com.commsoft.epay.util.logging.Log;

/**
* 组装内部通信消息的类
* 创建日期：2011-11-25
* Company：深圳市万恒科技有限公司
* Copyright: Copyright (c) 2008
* @author caiSJ 
* @version 2.0.0.0
*/
public class TzScpMsg {
	/**
	 * 交易查询返回组包消息
	 * @param seqNo			内部流水号
	 * @param servNo		业务流程码
	 * @param ensureMoney	保证金余额
	 * @param agentMoney    佣金余额 
	 * @param respCode		返回码
	 * @param contextFlag	是否包含消息体标志
	 * @param flag	        具体交易类型
	 * @param money	        冻结金额
	 * @return	返销组包消息
	 */
	public static Message queryResponse(String sid,String messType,String tradeNo,String seqNo,String servNo,String ensureMoney,String agentMoney,String respCode, String contextFlag,String flag,String money)
	{
		Message msg = new Message();
		try{
			//设置消息发送这标识
			msg.setSid(sid); 
			//设置消息流水号
			msg.setSeqNo(seqNo);
			//设置消息交易类型
			msg.setTradeNo(tradeNo);
			//设置消息类型
			msg.setType(messType);
			//设置业务流程
			msg.setServNo(servNo);
			//设置mac码
			msg.setMac("");
			//设置响应码
			msg.setResponseCode(respCode);
			//设置内容标识
			msg.setContextFlag(contextFlag);
			if(contextFlag.equals("0"))
			{	
				//消息内容
				Map map = new HashMap();
				if("Q".equals(flag)){
					map.put("EnsureMoney", ensureMoney);
					map.put("AgentMoney", agentMoney);	
					map.put("IceMoney", money);
				}else if("C".equals(flag)){
					map.put("EnsureLeft", ensureMoney);
					map.put("Commision", agentMoney);	
				}
				msg.setContext(map);
			}
			
		}catch(Exception e)
		{
			Log.error("TZ返回消息组包xml错误", e);
			msg=null;
		}
		return msg;
	}
	
	
	/**
	 * 充值返回消息组包
	 * @param sid
	 * @param messType
	 * @param seqNo		内部流水号
	 * @param tradeNo	交易编码
	 * @param servNo    业务流程码
	 * @param respCode	返回码
	 * @param payfee	缴费金额
	 * @param payno	    缴费号码
	 * @param rst	    账单明细
	 * @param checkbill	对账信息
	 * @param rollmess		冲正信息
	 * @param contextFlag 	是否包含消息体
	 * @param time	    受理方交易时间
	 * @param agentMO 	代理商余额
	 * @param serialNo  充值发起方流水号
	 * @param serial    受理方返回流水号
	 * @return 组包消息报文
	 */
	public static Message fillResponse(String sid,String messType,String seqNo,String tradeNo,String servNo,String respCode,
			String payfee, String payno, String rst, String checkbill,String rollmess, String contextFlag,String time,String agentMO,String serialNo,String serial)
	{
		Message msg = new Message();
		try{
			//设置消息发送这标识
			msg.setSid(sid); 
			//设置消息流水号
			msg.setSeqNo(seqNo);
			//设置消息交易类型
			msg.setTradeNo(tradeNo);
			//设置消息类型
			msg.setType(messType);
			//设置业务流程
			msg.setServNo(servNo);
			//设置mac码
			msg.setMac("");
			//设置响应码
			msg.setResponseCode(respCode);
			//设置内容标识
			msg.setContextFlag(contextFlag);
			if(contextFlag.equals("0"))
			{	
				//消息内容
				Map map = new HashMap();
				//充值金额
				map.put("PayFee", payfee);
				//电话号码
				map.put("PayNo", payno);
				//记录数
				map.put("Num", "1");
				//明细
				map.put("Rst1", rst);
				//对账需要保存的信息
				map.put("CheckBill", checkbill);
				//冲正需要保存的信息
				map.put("RollBack", rollmess);
				//受理方交易时间
				map.put("time", time);
				//代理商余额
				map.put("agentMO", agentMO);
				//原充值发起方流水号
				map.put("serialNo", serialNo);
				//受理方返回流水号
				map.put("serial", serial);
				msg.setContext(map);
			}
			
		}catch(Exception e)
		{
			Log.error("电信固网查询返回消息组包xml错误", e);
			msg=null;
		}
		return msg;
	}
	
	/**
	 * 	返销返回组包消息
	 * @return	返销组包消息
	 */
	public static Message revertResponse(String sid,String messType,String tradeNo,String seqNo,String servNo,String reverseSerial,String respCode, String contextFlag,String time,String phone,String pay,String count,String rever,String oldSerialNo4)
	{
		Message msg = new Message();
		try{
			//设置消息发送这标识
			msg.setSid(sid); 
			//设置消息流水号
			msg.setSeqNo(seqNo);
			//设置消息交易类型
			msg.setTradeNo(tradeNo);
			//设置消息类型
			msg.setType(messType);
			//设置业务流程
			msg.setServNo(servNo);
			//设置mac码
			msg.setMac("");
			//设置响应码
			msg.setResponseCode(respCode);
			//设置内容标识
			msg.setContextFlag(contextFlag);
			if(contextFlag.equals("0"))
			{	
				//消息内容
				Map map = new HashMap();
				//受理方业务流水号
				map.put("serialNo", reverseSerial);
				//受理方交易时间
				map.put("tradeTime", time);
				//客户号码
				map.put("phone", phone);
				//冲正金额
				map.put("payFee", pay);
				//冲正次数
				map.put("leftCount", count);
				//冲正记录
				map.put("rever", rever);
				//原充值业务流水号
				map.put("TopUpSerialNo", oldSerialNo4);
				msg.setContext(map);
			}
			
		}catch(Exception e)
		{
			Log.error("电信固网返销返回消息组包xml错误", e);
			msg=null;
		}
		return msg;
	}
}
