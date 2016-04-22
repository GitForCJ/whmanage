package com.wlt.webm.handle;

import com.commsoft.epay.util.tp.ThreadPool;
/**
 * <p>Description: 深圳电信计费接口消息组包类</p>
 */
public interface Handler{
			
	/**
	 * 设置线程池
	 * @param pool 线程池对象
	 */
	public void setThreadPool(ThreadPool pool);
	
	/**
	 *  业务服务类
	 */
	public void deal();
	
	/**
	 * 线程启动类
	 */
	public void start();
	
}
