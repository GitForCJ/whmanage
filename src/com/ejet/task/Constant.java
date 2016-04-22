package com.ejet.task;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	
	//��־����
	public static final String  TASK_LOG_STATE_NORMAL = "0";
	//��־δ���
	public static final String  TASK_LOG_STATE_RUNNING = "1";
	//��־ʧ��
	public static final String  TASK_LOG_STATE_FAIL = "2";
	
	
	//���С��������ս���ͳ��
	public final static String  COUNT_TYPE_CITY_AGENT_DAY = "2";
	
	public static String formatCountType(String type)
	{
		  if (type.equals(COUNT_TYPE_CITY_AGENT_DAY)) {
	            return "���С��������ս���ͳ��";
	        } else {
	        	return "";
	        }
	}

	/**
	 * ��ʽ��������־״̬
	 */
	public static String formatLogState(String state){
		if (state.equals(TASK_LOG_STATE_NORMAL)) {
            return "����";
        } else if (state.equals(TASK_LOG_STATE_RUNNING)) {
            return "δ���";
        } else if(state.equals(TASK_LOG_STATE_FAIL)) {
        	return "ʧ��";
        }else
        	return " ";
	}

}
