package com.wlt.webm.business;

import com.commsoft.epay.util.logging.Log;

/**
 * 
 * @author Administrator
 *
 */
public class Processer extends Thread {

	/**
	 * 业务处理对象
	 */
	private Business business;

	/**
	 * 线程主函数
	 */
	public void run() {
		Log.info("启动业务处理线程");
		try {
			ing();
		} catch (Exception ex) {
		}
	}

	/**
	 * 构造方法
	 * @param business 业务处理对象
	 */
	public Processer(Business business) {
		this.business = business;
	}

	/**
	 * 处理具体业务
	 */
	private void ing() {
		try {
			business.deal();
		} catch (Exception exp) {
			Log.error("处理业务出错:",exp);
		}
	}
}
