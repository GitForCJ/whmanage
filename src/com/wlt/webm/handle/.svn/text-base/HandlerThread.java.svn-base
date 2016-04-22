package com.wlt.webm.handle;

/**
 * <p>Description: UIC接收消息服务线程类，实现线程接口</p>
 */

public class HandlerThread extends  Thread{

	//UIC业务服务类
	private Handler handler;
	
	
	public void run()
	{
		handler.start();
	}


	/**
	 * 获得业务服务类
	 * @return
	 */
	public Handler getHandler() {
		return handler;
	}


	/**
	 * 设置业务服务类
	 * @param handler 业务服务接口
	 */
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
}
