package com.wlt.webm.business.cmcc;

import java.net.SocketTimeoutException;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.message.MsgCache;
/**
 * 
 */
public class CMPaySocketReadThread implements Runnable {
	private CMPayASynSocketClient as = null;
	CMPaySocketReadThread ()
	{}
	
	public CMPaySocketReadThread(CMPayASynSocketClient as)
	{
		this.as = as;
	}
	
	/**
	 * 读取socket中消息线程执行入口
	 */
	public void run() {
		
		while(true)
		{
			try{
				if(	as==null || !as.isConnected() || as.getSocketclient().getMysocket()==null 
						|| as.getSocketclient().getMysocket().isClosed())
				{
						Thread.sleep(2*1000);
						continue;
				}
				byte[] back = as.getSocketclient().receive();
				if(back!=null)
				{
					MsgCache.addCMPayRecvQueMsg(back);
				}
				}catch(SocketTimeoutException ex)
				{
					//计费处理中不打印
				}catch(Exception e)
				{
					//Log.info("接收计费消息处理线程错误.1.");
					//Log.error("接收计费消息处理线程错误.1.", e);
				}
		
		}
	}	
	
}
