package com.wlt.webm.business.dianx.action;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.tool.Tools;

/**
 * @author adminA
 *
 */
public class TelcomChargeThread extends Thread{
	private String parentID="";
	private String userno="";
	private String phonePid="";
	private String userPid="";
	private String phone="";
	private String sa_zone="";
	private String username="";
	private String numType="";
	private double fee=0.0d;
	public TelcomChargeThread(String parentID,String userno,String phonePid,String userPid,String phone,String sa_zone,String username,String numType,double fee){
		this.parentID=parentID;
		this.userno=userno;
		this.phonePid=phonePid;
		this.userPid=userPid;
		this.phone=phone;
		this.sa_zone=sa_zone;
		this.username=username;
		this.numType=numType;
		this.fee=fee;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		float[] rs=Tools.decomposeDE((float)fee);
		Log.info("充值号码:"+phone+",大面额充值"+(fee/1000)+"元,开始循环充值。");
		BiProd bp=new BiProd();
		for(int i=0;i<rs.length;i++){
			String seqNo=Tools.getStreamSerial(phone);
			String serialNo= "wh09015" + Tools.getNow3().substring(6)+ Tools.buildRandom(2) + Tools.buildRandom(3);
			
			try{
				int ss=bp.XYdxFill(parentID, userno, phonePid, userPid, "0",
						phone, seqNo, rs[i],sa_zone,username,numType,serialNo,"OF");
				Log.info("充值号码:"+phone+",循环充值,第"+(i+1)+"次,充值金额:"+(rs[i]/1000)+"元,充值充值订单提交结果:"+ss);
				System.out.println("充值号码:"+phone+",循环充值,第"+(i+1)+"次,充值金额:"+(rs[i]/1000)+"元,充值充值订单提交结果:"+ss);
			}catch(Exception e){
				Log.error("大额充值，充值号码:"+phone+",循环充值第"+(i+1)+"次充值，充值金额："+rs[i]+"里，系统异常，，"+e);
			}
		}
		Log.info("充值号码:"+phone+",大面额充值"+(fee/1000)+"元,循环充值完毕。循环次数:"+rs.length);
	}
}
