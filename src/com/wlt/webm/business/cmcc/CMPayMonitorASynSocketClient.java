package com.wlt.webm.business.cmcc;

import java.util.TimerTask;

import com.commsoft.epay.util.logging.Log;

/**
 * 
 */
public class CMPayMonitorASynSocketClient extends TimerTask {

	private CMPayASynSocketClient as = null;
	/**
	 * 构造函数
	 */
	public CMPayMonitorASynSocketClient()
	{
		
	}
	/**
	 * 构造函数
	 * @param as
	 */
	public CMPayMonitorASynSocketClient(CMPayASynSocketClient as)
	{
		this.as = as;
	}
	
	/**
	 * 定时任务执行线程
	 */
	public void run() {
		try{
			//监控socket连接是否正常
			if (  as==null || !as.isConnected() || as.getSocketclient().getMysocket()==null 
					|| as.getSocketclient().getMysocket().isClosed())
			{
				Log.debug("[CSDP_SDK] Monitor task close error socket!");
				as.getSocketclient().closeSocket();
				Log.info("发送链路维系包:12121212sxsxsx");
				as.reConnect();
				
			}
			else
			{
				//发送心跳消息
				CMPayRequestPing ping = new CMPayRequestPing();
				byte[] sendMsg=ping.pingMsg();
				int len = as.getSocketclient().send(sendMsg);
				Log.info("发送链路维系包:" + len);
//				Log.debug("[CSDP_SDK] Monitor task running......");
			}
			}catch(Exception e)
			{
				Log.info("广东移动发送链路维系包出错");
			}
	}
}
