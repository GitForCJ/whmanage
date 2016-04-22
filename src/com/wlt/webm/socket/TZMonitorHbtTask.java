package com.wlt.webm.socket;


import java.util.TimerTask;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.message.MsgCache;

/**
 * <p>Description: 监控测试连接是否返回线程</p>
 * 
 */
public class TZMonitorHbtTask extends TimerTask {
	
	
	private TianZuoASynSocketClient as = null;
	
	public TZMonitorHbtTask()
	{}
	
	public TZMonitorHbtTask(TianZuoASynSocketClient as )
	{
		this.as = as;
	}
	
	/**
	 * 监控心跳是否返回定时任务执行入口
	 */
	public void run() {
		
		if(!MsgCache.hasTZHbt())
		{
			try{
				Log.info("TZ心跳监控无返回，正关闭重连！");
				as.getSocketclient().closeSocket();
				as.reConnect();
			}catch(Exception e)
			{
				Log.info("TZ心跳监控线程错误！"+ e);
			}
		}
		
	}
	
	
	
	

}
