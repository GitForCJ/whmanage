package com.ejet.task;

import org.apache.log4j.Logger;

public class CityCountTaskReport implements TaskReportBase {

	public static Logger log = Logger.getLogger(CityCountTaskReport.class);
	
	
	public void excute() {
		
		try {
			
			
			
		    log.info("处理任务开始: " + this.toString());
		    System.out.println("处理任务开始: " + this.toString() + ". 时间[" + new java.util.Date().toString()  + "]");
		    log.info("处理任务结束: " + this.toString());
		} catch (Exception e) {
		    log.error("处理任务异常: ", e);
		}
	}
	
	

	
	
	
	public String toString()
	{
		return "[地市统计定时任务]";
	}
}
