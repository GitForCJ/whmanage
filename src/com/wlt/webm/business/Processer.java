package com.wlt.webm.business;

import com.commsoft.epay.util.logging.Log;

/**
 * 
 * @author Administrator
 *
 */
public class Processer extends Thread {

	/**
	 * ҵ�������
	 */
	private Business business;

	/**
	 * �߳�������
	 */
	public void run() {
		Log.info("����ҵ�����߳�");
		try {
			ing();
		} catch (Exception ex) {
		}
	}

	/**
	 * ���췽��
	 * @param business ҵ�������
	 */
	public Processer(Business business) {
		this.business = business;
	}

	/**
	 * �������ҵ��
	 */
	private void ing() {
		try {
			business.deal();
		} catch (Exception exp) {
			Log.error("����ҵ�����:",exp);
		}
	}
}
