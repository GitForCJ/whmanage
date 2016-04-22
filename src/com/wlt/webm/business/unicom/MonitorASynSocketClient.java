package com.wlt.webm.business.unicom;

import java.util.TimerTask;

import com.commsoft.epay.util.logging.Log;

/**
 * 
 */
public class MonitorASynSocketClient extends TimerTask {

	private ASynSocketClient as = null;
	/**
	 * ���캯��
	 */
	public MonitorASynSocketClient()
	{
		
	}
	/**
	 * ���캯��
	 * @param as
	 */
	public MonitorASynSocketClient(ASynSocketClient as)
	{
		this.as = as;
	}
	
	/**
	 * ��ʱ����ִ���߳�
	 */
	public void run() {
		try{
			//System.out.println("���--------");
			//���socket�����Ƿ�����
			if (  as==null || !as.isConnected() || as.getSocketclient().getMysocket()==null 
					|| as.getSocketclient().getMysocket().isClosed())
			{
				//System.out.println("���socket�����Ƿ�����");
				Log.debug("[CSDP_SDK] Monitor task close error socket!");
				as.getSocketclient().closeSocket();
				as.reConnect();
				
			}
			else
			{
				//����������Ϣ
	            RequestPing ping = new RequestPing();
				byte[] sendMsg=ping.pingMsg();
				int len = as.getSocketclient().send(sendMsg);
				Log.info("�㶫��ͨ������·άϵ��:" + len);
				//System.out.println("������·άϵ��:" + new String(sendMsg));
//				Log.debug("[CSDP_SDK] Monitor task running......");
			}
			}catch(Exception e)
			{
				Log.error("[CSDP_SDK]MonitorASynSocketClient|monitor error!",e);
				//System.out.println("������·άϵ������:");
			}
	}
}
