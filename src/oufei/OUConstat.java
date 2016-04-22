package oufei;

import java.util.HashMap;
import java.util.TimerTask;

public class OUConstat {
	
	/**
	 * 话费充值供货 task
	 */
	public static HashMap<String, TimerTask> tasks=new HashMap<String, TimerTask>();
	
	/**
	 * Q币充值供货 task
	 */
	public static HashMap<String,TimerTask> Qbtask=new HashMap<String, TimerTask>();
	
	public static String OFPARTNER = "";  // 
	public static String OFTPLID = "";  //  
	public static String OFAPIKEY = "";
	public static String OFGETORDERURL = "" ;
	public static String OFCHECKORDERURL = "";
	public static String OFRETURNRESULTURL = "" ;
}
