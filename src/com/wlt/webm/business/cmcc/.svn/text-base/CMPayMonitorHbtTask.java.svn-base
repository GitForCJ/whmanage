package com.wlt.webm.business.cmcc;

import java.util.TimerTask;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.message.MsgCache;
/**
 * 
 */
public class CMPayMonitorHbtTask extends TimerTask {
	
	
	private CMPayASynSocketClient as = null;
	
	public CMPayMonitorHbtTask()
	{}
	
	public CMPayMonitorHbtTask(CMPayASynSocketClient as )
	{
		this.as = as;
	}
	
	/**
	 * 监控心跳是否返回定时任务执行入口
	 */
	public void run() {
		
		if(!MsgCache.hasMoveHbt())
		{
			try{
				Log.info("心跳监控无返回，正关闭重连！");	
				//System.out.println("心跳监控无返回，正关闭重连！");
				as.getSocketclient().closeSocket();
				as.reConnect();
			}catch(Exception e)
			{
				Log.error("心跳监控线程错误！", e);
				//System.out.println("心跳监控线程错误！"+e.toString());
			}
		}
		
	}
	
	
	
	

}
