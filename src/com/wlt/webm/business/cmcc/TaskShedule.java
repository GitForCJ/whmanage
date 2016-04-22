package com.wlt.webm.business.cmcc;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;

import com.wlt.webm.tool.Constant;
import com.wlt.webm.config.ConfigInfo;


/**
 * 
 */
public class TaskShedule extends Thread {

	/**
	 * 配置文件
	 */
	ConfigInfo config = ConfigInfo.getInstance();
	
	/**
	 * 当前对象的一个实例
	 */
	private static TaskShedule task = null;

	/**
	 * 当前类的初始化函数
	 */
	public static void init() {
		if (task != null) {
			return;
		}
		task = new TaskShedule();
	}
	/**
	 * 当前对象的私有构造函数，用户创建单一实例
	 */
	private TaskShedule() {
		this.setDaemon(true);
		this.setName("Task Schedule Thread");
		startTask();
	}

	/**
	 * 函数功能：获取任务表的单一实例
	 * 
	 * @return 当前类的单一实例
	 */
	public static TaskShedule getTaskShedule() {
		return task;
	}

	/**
	 * 任务管理器的线程运行函数，在这个线程中启动了其它服务线程，所以 这个线程不能终止，因为父服务线程一旦终止，子服务线程也会终止，
	 * 另外在这个函数中还可以定期去数据库中读取定期任务信息，但是目前这个 版本中没有实现定期读取定时任务信息的功能
	 */
	public void run() {
		while (true) {
			try {
				sleep(50);
			} catch (InterruptedException ex) {
			}
		}
	}

	/**
	 * 函数功能：创建、初始化任务
	 */
	private void startTask() {
		
		//启动心跳监控线程
//		Timer hbtTm = new Timer(true);
//		MonitorHbtTask hbt = new MonitorHbtTask();
//		hbtTm.schedule(hbt, 1*60*1000, config.getHbt()*2*1000L);
		
		//启动socket状态监控线程
//		Timer sockTm = new Timer(true);
//		MonitorSocketTask sock = new MonitorSocketTask();
//		sockTm.schedule(sock, 20*1000, 10*1000L);

		
	}
}
