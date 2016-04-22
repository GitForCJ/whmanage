package com.wlt.webm.business.unicom;

import java.util.TimerTask;

import com.commsoft.epay.util.logging.Log;

/**
 * 
 */
public class MonitorASynSocketClient extends TimerTask {

	private ASynSocketClient as = null;
	/**
	 * 构造函数
	 */
	public MonitorASynSocketClient()
	{
		
	}
	/**
	 * 构造函数
	 * @param as
	 */
	public MonitorASynSocketClient(ASynSocketClient as)
	{
		this.as = as;
	}
	
	/**
	 * 定时任务执行线程
	 */
	public void run() {
		try{
			//System.out.println("监控--------");
			//监控socket连接是否正常
			if (  as==null || !as.isConnected() || as.getSocketclient().getMysocket()==null 
					|| as.getSocketclient().getMysocket().isClosed())
			{
				//System.out.println("监控socket连接是否正常");
				Log.debug("[CSDP_SDK] Monitor task close error socket!");
				as.getSocketclient().closeSocket();
				as.reConnect();
				
			}
			else
			{
				//发送心跳消息
	            RequestPing ping = new RequestPing();
				byte[] sendMsg=ping.pingMsg();
				int len = as.getSocketclient().send(sendMsg);
				Log.info("广东联通发送链路维系包:" + len);
				//System.out.println("发送链路维系包:" + new String(sendMsg));
//				Log.debug("[CSDP_SDK] Monitor task running......");
			}
			}catch(Exception e)
			{
				Log.error("[CSDP_SDK]MonitorASynSocketClient|monitor error!",e);
				//System.out.println("发送链路维系包出错:");
			}
	}
}
