package com.wlt.webm.handle;

/**
 * <p>Description: UIC������Ϣ�����߳��࣬ʵ���߳̽ӿ�</p>
 */

public class HandlerThread extends  Thread{

	//UICҵ�������
	private Handler handler;
	
	
	public void run()
	{
		handler.start();
	}


	/**
	 * ���ҵ�������
	 * @return
	 */
	public Handler getHandler() {
		return handler;
	}


	/**
	 * ����ҵ�������
	 * @param handler ҵ�����ӿ�
	 */
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
}
