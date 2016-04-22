package com.wlt.webm.business.cmcc;

import com.commsoft.epay.util.logging.Log;
import com.commsoft.epay.util.tp.ThreadPool;
import com.wlt.webm.handle.Handler;
import com.wlt.webm.message.MsgCache;
/**
 * 
 */
public class CMPaySocketHandle implements Handler, Runnable {

	/**
	 * 	�̳߳�
	 */
	private ThreadPool threadPool;
	/**
	 * ��������
	 */
	public void start() {
		
	}
	
	/**
	 * �����̷߳���
	 */
	public void run() {
		deal();
	}

	/**
	 * ����ҵ����
	 */
	public void deal() {
		
		while(true)
		{
			try{
					int depth = MsgCache.getCMPayRecvQueLen();
					if(depth<1)
					{
						Thread.sleep(100);
						continue;
					}
					byte[] msg = MsgCache.getCMPayRecvMsg();
					//��������Ʒ���Ϣ�߳�
					//Log.info("��������Ʒ���Ϣ�߳�11111");
					CMPayBusiness  bus = new CMPayBusiness();
					CMPayBusinessProcess procbus = new CMPayBusinessProcess(bus);
					bus.setMessage(msg);
					new Thread(procbus).start();
					//Log.info("��������Ʒ���Ϣ�߳�22222");
					
				
				}catch(Exception e)
				{
					Log.error("===���ռƷ���Ϣ�����̴߳���", e);
				}
			
			
			
			
		}
		
		
		
		
		
	}
	/**
	 * ����̳߳�
	 * @return ThreadPool �̳߳�
	 */
	public ThreadPool getThreadPool(){
		return threadPool;
	}

	/**
	 * ����̳߳�
	 * @parm threadPool �̳߳ض���
	 */
	public void setThreadPool(ThreadPool threadPool){
		this.threadPool = threadPool;
	}

}
