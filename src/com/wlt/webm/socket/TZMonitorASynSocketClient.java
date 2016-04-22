package com.wlt.webm.socket;

import java.util.TimerTask;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.TZ.service.RequestPing;
import com.wlt.webm.message.MsgCache;


/**
 * <p>Description: 线路维系类</p>
 */
public class TZMonitorASynSocketClient extends TimerTask {

	private TianZuoASynSocketClient as = null;
	/**
	 * 构造函数
	 */
	public TZMonitorASynSocketClient()
	{

	}
	/**
	 * 构造函数
	 * @param as
	 */
	public TZMonitorASynSocketClient(TianZuoASynSocketClient as)
	{
		this.as = as;
	}
	
	/**
	 * 定时任务执行线程
	 */
	public void run() {
		try{
			//监控socket连接是否正常 
			if (as==null || !as.isConnected() || as.getSocketclient().getMysocket()==null 
					|| as.getSocketclient().getMysocket().isClosed())
			{
				Log.info("[TZ] Monitor task:the statue of socket,is not normal!");
				as.getSocketclient().closeSocket();
				MsgCache.tzSign.clear();//重连之前先清空上次签到的密钥
				as.reConnect();
			}
			else
			{
				if(MsgCache.tzStatue){//如果已经签到则可以发心跳
	            RequestPing ping = new RequestPing();
	            String mainKey=(String) MsgCache.tzSign.get("sign");
				Log.info("TZ发送心跳消息使用的密钥"+mainKey);
	            byte[] sendMsg=ping.pingMsg(mainKey);
				int len = as.getSocketclient().send(sendMsg);
				Log.info("TZ发送链路维系包:"+len);
			}
			}
			}catch(Exception e)
			{
				Log.info("[CSDP_SDK]TZMonitorASynSocketClient|monitor error!"+e);
			}
	}
}
