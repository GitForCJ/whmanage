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
	 * �����ļ�
	 */
	ConfigInfo config = ConfigInfo.getInstance();
	
	/**
	 * ��ǰ�����һ��ʵ��
	 */
	private static TaskShedule task = null;

	/**
	 * ��ǰ��ĳ�ʼ������
	 */
	public static void init() {
		if (task != null) {
			return;
		}
		task = new TaskShedule();
	}
	/**
	 * ��ǰ�����˽�й��캯�����û�������һʵ��
	 */
	private TaskShedule() {
		this.setDaemon(true);
		this.setName("Task Schedule Thread");
		startTask();
	}

	/**
	 * �������ܣ���ȡ�����ĵ�һʵ��
	 * 
	 * @return ��ǰ��ĵ�һʵ��
	 */
	public static TaskShedule getTaskShedule() {
		return task;
	}

	/**
	 * ������������߳����к�����������߳������������������̣߳����� ����̲߳�����ֹ����Ϊ�������߳�һ����ֹ���ӷ����߳�Ҳ����ֹ��
	 * ��������������л����Զ���ȥ���ݿ��ж�ȡ����������Ϣ������Ŀǰ��� �汾��û��ʵ�ֶ��ڶ�ȡ��ʱ������Ϣ�Ĺ���
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
	 * �������ܣ���������ʼ������
	 */
	private void startTask() {
		
		//������������߳�
//		Timer hbtTm = new Timer(true);
//		MonitorHbtTask hbt = new MonitorHbtTask();
//		hbtTm.schedule(hbt, 1*60*1000, config.getHbt()*2*1000L);
		
		//����socket״̬����߳�
//		Timer sockTm = new Timer(true);
//		MonitorSocketTask sock = new MonitorSocketTask();
//		sockTm.schedule(sock, 20*1000, 10*1000L);

		
	}
}
