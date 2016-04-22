package com.wlt.webm.socket;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.message.MsgCache;

/**
 * <p>Description: 读计费消息线程，并将消息放置到消息缓冲队列中</p>
 */
public class TZSocketSendThread implements Runnable {
	private TianZuoASynSocketClient as = null;
	TZSocketSendThread ()
	{
		
	}
	
	public TZSocketSendThread(TianZuoASynSocketClient as)
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
				
				if(MsgCache.sendTZMsgCache.size()>0)
				{  
					Log.info("MsgCache.sendTZMsgCache.size()"+MsgCache.sendTZMsgCache.size());
					byte[] msg = (byte[])MsgCache.sendTZMsgCache.remove(0);
					as.getSocketclient().send(msg);
					continue;
				}
				else
				{
					Thread.sleep(100);
				}
				}catch(Exception e)
				{
					Log.info("发送计费消息处理线程错误"+ e);
				}
		
		}
	}	
	
}

