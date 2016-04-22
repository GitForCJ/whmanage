package com.ejet.task;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	
	//日志正常
	public static final String  TASK_LOG_STATE_NORMAL = "0";
	//日志未完成
	public static final String  TASK_LOG_STATE_RUNNING = "1";
	//日志失败
	public static final String  TASK_LOG_STATE_FAIL = "2";
	
	
	//地市、代理商日交易统计
	public final static String  COUNT_TYPE_CITY_AGENT_DAY = "2";
	
	public static String formatCountType(String type)
	{
		  if (type.equals(COUNT_TYPE_CITY_AGENT_DAY)) {
	            return "地市、代理商日交易统计";
	        } else {
	        	return "";
	        }
	}

	/**
	 * 格式化任务日志状态
	 */
	public static String formatLogState(String state){
		if (state.equals(TASK_LOG_STATE_NORMAL)) {
            return "正常";
        } else if (state.equals(TASK_LOG_STATE_RUNNING)) {
            return "未完成";
        } else if(state.equals(TASK_LOG_STATE_FAIL)) {
        	return "失败";
        }else
        	return " ";
	}

}
