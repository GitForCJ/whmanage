package com.wlt.webm.business.unicom;

import com.wlt.webm.business.Business;

/**
 */
public class BusinessProcess implements Runnable{
	/**
	 * 业务处理对象
	 */
	private UnicomBusiness business;

	/**
	 * 构造方法
	 * 
	 * @param business
	 *            业务处理对象
	 */
	public BusinessProcess(UnicomBusiness business) {
		this.business = business;
	}
	/**
	 * 线程启动函数
	 */
	public void run() {
		business.deal();
	}

}
