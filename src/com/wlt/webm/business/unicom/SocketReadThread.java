package com.wlt.webm.business.unicom;

import java.net.SocketTimeoutException;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.message.MsgCache;
/**
 * 
 */
public class SocketReadThread implements Runnable {
	private ASynSocketClient as = null;
	SocketReadThread ()
	{}
	
	public SocketReadThread(ASynSocketClient as)
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
					MsgCache.addRecvQueMsg(back);
				}
				}catch(SocketTimeoutException ex)
				{
					//计费超时不打印
				}catch(Exception e)
				{
					Log.error("广东联通接收计费消息处理线程错误", e);
					//System.out.println("广东联通接收计费消息处理线程错误"+ e.toString());
				}
		
		}
	}	
	
}
