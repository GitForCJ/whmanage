package com.wlt.webm.message;

import cn.emay.sdk.client.api.Client;


		/**
		 * 
		 * @author caiSJ
		 *
		 */
public class SingletonClient {
	private static Client client=null;
	//private static String softwareSerialNo="0SDK-EBB-0130-NEWML";//测试
	private static String softwareSerialNo="3SDK-EMY-0130-PGUUS";//正式
	private static String key="hrd1382025590";
	/**
	 * 构造函数
	 */
	private SingletonClient(){
	}
	 /**
	  * 
	  * @param softwareSerialNo
	  * @param key
	  * @return  client
	  */
	public synchronized static Client getClient(){
		if(client==null){
			try {
				client=new Client(softwareSerialNo,key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}

	public static void main(String str[]){
	}
}
