package com.wlt.webm.socket;

import java.net.SocketTimeoutException;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.message.MsgCache;

/**
 * <p>Description: 读计费消息线程，并将消息放置到消息缓冲队列中</p>
 * 
 */
public class TZSocketReadThread implements Runnable {
	private TianZuoASynSocketClient as = null;
	
	/**
	 * default constructor
	 */
	TZSocketReadThread ()
	{
		
	}
	
	public TZSocketReadThread(TianZuoASynSocketClient as)
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
						Thread.sleep(3*1000);
						continue;
				}
				byte[] back = as.getSocketclient().receive();
				if(back!=null)
				{
					MsgCache.addTZRecvQueMsg(back);
				}
				}catch(SocketTimeoutException ex)
				{
					Log.error("TZSocketReadThread"+ ex);
				}catch(Exception e)
				{
					Log.error("TZSocketReadThread"+ e);
				}
		
		}
	}	
	
}

