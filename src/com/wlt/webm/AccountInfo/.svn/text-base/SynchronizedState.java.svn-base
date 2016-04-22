package com.wlt.webm.AccountInfo;

import com.wlt.webm.message.MemcacheConfig;


public class SynchronizedState {
	
	private static Object look=new Object();

	/**回调判断
	 * @param seqNo
	 * @return true 表示存在，处理中，，      false 表示不存在，没处理
	 */
	public static boolean getState(String seqNo){
		boolean bool=false;
		synchronized(look){
			if(MemcacheConfig.getInstance().get1(seqNo)){
				bool=true;
			}else{
				MemcacheConfig.getInstance().add(seqNo, 1);
				bool=false;
			}
		}
		return bool;
	}
}
