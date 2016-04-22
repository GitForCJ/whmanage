package com.wlt.webm.business;

/**
 * <p>Description: 业务处理线程</p>
 */
public class BusinessProcess implements Runnable{
	/**
	 * 业务处理对象
	 */
	private Business business;

	/**
	 * 构造方法
	 * 
	 * @param business
	 *            业务处理对象
	 */
	public BusinessProcess(Business business) {
		this.business = business;
	}
	/**
	 * 线程启动函数
	 */
	public void run() {
		business.deal();
	}

}
