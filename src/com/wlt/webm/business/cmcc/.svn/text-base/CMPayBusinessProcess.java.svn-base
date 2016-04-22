package com.wlt.webm.business.cmcc;

/**
 */
public class CMPayBusinessProcess implements Runnable{
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
	public CMPayBusinessProcess(Business business) {
		this.business = business;
	}
	/**
	 * 线程启动函数
	 */
	public void run() {
		business.deal();
	}

}
