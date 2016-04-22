package com.wlt.webm.business.unicom;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.message.MsgCache;
/**
 * @version 3.0.0.0
 * 
 */
public class SocketSendThread implements Runnable {
	private ASynSocketClient as = null;
	SocketSendThread ()
	{}
	
	public SocketSendThread(ASynSocketClient as)
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
				
				if(MsgCache.sendMsgCache.size()>0)
				{
					byte[] msg = (byte[])MsgCache.sendMsgCache.remove(0);
					as.getSocketclient().send(msg);
					continue;
				}
				else
				{
					Thread.sleep(100);
				}
				}catch(Exception e)
				{
					Log.error("接收计费消息处理线程错误", e);
				//	System.out.println("联通接收计费消息处理线程错误"+ e);
				}
		
		}
	}	
	
}
