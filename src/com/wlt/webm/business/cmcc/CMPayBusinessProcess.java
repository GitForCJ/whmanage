package com.wlt.webm.business.cmcc;

/**
 */
public class CMPayBusinessProcess implements Runnable{
	/**
	 * ҵ�������
	 */
	private Business business;

	/**
	 * ���췽��
	 * 
	 * @param business
	 *            ҵ�������
	 */
	public CMPayBusinessProcess(Business business) {
		this.business = business;
	}
	/**
	 * �߳���������
	 */
	public void run() {
		business.deal();
	}

}
