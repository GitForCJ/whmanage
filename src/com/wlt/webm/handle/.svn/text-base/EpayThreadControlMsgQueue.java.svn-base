package com.wlt.webm.handle;

import java.util.LinkedList;
import java.util.Vector;

/**
 * <p>Description: 清除线程池缓存消息服务类</p>
 */

public class EpayThreadControlMsgQueue {

	// 清除时间限制,默认为10秒钟
	//private static long clearTime = 10000;

	// 存放消息流水号
	private static LinkedList queue = new LinkedList();

	// 存放消息流水号KEY，时间值对
	// private static LinkedHashMap map = new LinkedHashMap();
	// 消息限制数
	private static int listSize = 50000;

	
	public static void init()
	{
		queue = new LinkedList();
		listSize = 50000;
	}
	
	
	/**
	 * 保存消息流水号到消息缓存中
	 * 
	 * @param str
	 *            消息流水号+业务类型
	 * @return 返回保存是否成功
	 */
	public static boolean setQueue(Vector v) {

		// 消息是否超过限制
		if (queue.size() >= listSize) {
			return false;
		}

		queue.addFirst(v);
		// listSize++;
		return true;

	}

	/**
	 * 删除消息
	 * 
	 * @param str
	 *            时间
	 */
	public static Object getQueue() {

		
		
		if (queue.size() < 1) {
			return null;
		}

		// 获得队列最后一个流水号
		Object tmpObj = queue.removeLast();
		if (tmpObj == null) {
			return null;
		}
		
		return tmpObj;

	}
}
