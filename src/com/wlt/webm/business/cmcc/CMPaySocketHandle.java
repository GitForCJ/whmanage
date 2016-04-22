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
	 * 	线程池
	 */
	private ThreadPool threadPool;
	/**
	 * 启动服务
	 */
	public void start() {
		
	}
	
	/**
	 * 启动线程服务
	 */
	public void run() {
		deal();
	}

	/**
	 * 启动业务处理
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
					//启动处理计费消息线程
					//Log.info("启动处理计费消息线程11111");
					CMPayBusiness  bus = new CMPayBusiness();
					CMPayBusinessProcess procbus = new CMPayBusinessProcess(bus);
					bus.setMessage(msg);
					new Thread(procbus).start();
					//Log.info("启动处理计费消息线程22222");
					
				
				}catch(Exception e)
				{
					Log.error("===接收计费消息处理线程错误", e);
				}
			
			
			
			
		}
		
		
		
		
		
	}
	/**
	 * 获得线程池
	 * @return ThreadPool 线程池
	 */
	public ThreadPool getThreadPool(){
		return threadPool;
	}

	/**
	 * 获得线程池
	 * @parm threadPool 线程池对象
	 */
	public void setThreadPool(ThreadPool threadPool){
		this.threadPool = threadPool;
	}

}
