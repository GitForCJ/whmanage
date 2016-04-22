package com.wlt.webm.business.TZ.service;

import com.commsoft.epay.util.logging.Log;
import com.commsoft.epay.util.tp.ThreadPool;
import com.wlt.webm.business.BusinessProcess;
import com.wlt.webm.message.MsgCache;


/**
 * <p>Title:电子代办平台</p>
 * <p>Description: 从接收计费缓冲区中取数据，并进行处理</p>
 * <p>create: 2011-11-22</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 深圳市万恒科技有限公司</p>
 * @author caiSJ
 * @version 3.0.0.0
 * 
 */
public class TZSocketHandle implements Runnable {
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
					int depth = MsgCache.getTZRecvQueLen();
					if(depth<1)
					{
						Thread.sleep(50);
						continue;
					}
					byte[] msg = MsgCache.getTZRecvMsg();
					Log.info("TZSocketHandle处理消息:"+new String(msg));
					System.out.println("TZSocketHandle处理接收到的天作消息:"+new String(msg));
					//启动处理计费消息线程
					TZUnicomBusiness  bus = new TZUnicomBusiness();
					bus.setMessage(msg);
					BusinessProcess procbus = new BusinessProcess(bus);
					threadPool.run(procbus);
				}catch(Exception e)
				{
					Log.error("TZSocketHandle计费消息处理线程错误"+ e);
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

