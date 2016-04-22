package com.wlt.webm.message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import whmessgae.TradeMsg;

import com.commsoft.epay.util.logging.Log;

public class Msg {// Collections.synchronizedMap(

	private static Map<String, TradeMsg> rcvB = new HashMap<String, TradeMsg>();
	private static Map<String, TradeMsg> rcvS = new HashMap<String, TradeMsg>();
	private static Msg msgCache=null;
	// public static WhQueue rcvB = new WhQueue();
	// public static WhQueue rcvS = new WhQueue();

	private Msg() {
     super();
	}
	
	 public static Msg getInstance(){
		 if(null==msgCache){
			 msgCache =new Msg();
		 }
		 return msgCache;
	 }
	 
	 /**
	  * 
	  * @param key
	  * @return
	  */
	 public synchronized TradeMsg removeCacheb(String key) {
		 Log.info("b remove key:" + key+"  "+rcvB.size());
		 TradeMsg a=null;
//	        return rcvB.remove(key);
		 Iterator<Map.Entry<String, TradeMsg>> it = rcvB.entrySet().iterator();
		 while(it.hasNext()){  
	            Map.Entry<String, TradeMsg> entry=it.next();  
	            String  key1=entry.getKey();
	            Log.info("==="+key1);
	            if(key.equals(key)){  
	                Log.info("delete this: "+key+" = "+key);  
	                //map.put(key, "ÆæÊý");   //ConcurrentModificationException  
	                //map.remove(key);      //ConcurrentModificationException  
	               a=rcvB.get(key);
	                it.remove();        //OK 
	            }  
		 }
		 Log.info(" === "+rcvB.size());
		 return a;
	    }
	 /**
	  * 
	  * @param key
	  * @return
	  */
	    private synchronized TradeMsg getCacheb(String key) {
	    	Log.info("b get key:" + key);
	        return rcvB.get(key);
	    }
	    /**
	     * 
	     * @param key
	     * @param object
	     */
	    public synchronized void putCacheb(String key, TradeMsg object) {
	    	Log.info("b put key:" + key);
	    	rcvB.put(key, object);
	    	Log.info("b put key:" + rcvB.size());
	    }
	 
	 
	 

	/**
	 * 
	 * @param seqno
	 * @param obj
	 */
	public static synchronized void addbMsg(String seqno, TradeMsg obj) {
		Log.info("b put key:" + seqno);
		rcvB.put(seqno, obj);
		Log.info("b put key:" + rcvB.size());
	}

	public static TradeMsg getbMsg(String key) {
		Log.info("b get key:" + key);
		TradeMsg v = rcvB.remove(key);
		return v;
	}

	public static synchronized void addsMsg(String seqno, TradeMsg obj) {
		Log.info("s put key:" + seqno);
		rcvS.put(seqno, obj);
	}

	public static TradeMsg getsMsg(String seqno) {
		Log.info("s get key:" + seqno);
		if (rcvS.get(seqno) == null) {
			return null;
		} else {
			return rcvS.remove(seqno);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		// String str=
		// "HRD0142220801200000043671108000032430211030001DED988E14003007649D62          000000000020140113144720101201-2010120114011314381420140113144714"
		// ;
		// System.out.println(str.substring(40,56)+"   "+str.length());
		System.out.println(Msg.rcvB.size());
		Msg msg = new Msg();
		A a = msg.new A();
		a.start();
		Thread.sleep(1000);
		B b = msg.new B();
		b.start();
	}

	class A extends Thread {
		@Override
		public void run() {
			Msg.addbMsg("1111", new TradeMsg());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Msg.addbMsg("111", new TradeMsg());
		}
	}

	class B extends Thread {
		@Override
		public void run() {
			Msg.addbMsg("22", new TradeMsg());
			System.out.println(Msg.rcvB.size());
			int k1 = 0;
			TradeMsg rsMsg = null;
			while (k1 < 360) {
				k1++;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				rsMsg = Msg.getbMsg("111");
				if (null == rsMsg) {
					continue;
				} else {
					System.out.println("OK");
					break;
				}
			}
			if (null == rsMsg && k1 >= 360) {
				Log.info("³åÕý³¬Ê±:");
			}
		}
	}
}
