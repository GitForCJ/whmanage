package com.wlt.webm.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

	 public ThreadPoolExecutor excute= new ThreadPoolExecutor(10, 50, 2,  
             TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(1000),  
             new ThreadPoolExecutor.DiscardOldestPolicy()); 
		
	
	 private static class SingletonClassInstance { 
		    private static final ThreadPool instance = new ThreadPool(); 
		  } 

		  public static ThreadPool getInstance() { 
		    return SingletonClassInstance.instance; 
		  } 

		  private ThreadPool() { 

		  }


		public ThreadPoolExecutor getExcute() {
			return excute;
		}

		public void setExcute(ThreadPoolExecutor excute) {
			this.excute = excute;
		}
		  
		public void  run(Runnable command){
			excute.execute(command);
		}  
}
