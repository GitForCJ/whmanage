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
	 * ��ȡsocket����Ϣ�߳�ִ�����
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
					//�Ʒѳ�ʱ����ӡ
				}catch(Exception e)
				{
					Log.error("�㶫��ͨ���ռƷ���Ϣ�����̴߳���", e);
					//System.out.println("�㶫��ͨ���ռƷ���Ϣ�����̴߳���"+ e.toString());
				}
		
		}
	}	
	
}
