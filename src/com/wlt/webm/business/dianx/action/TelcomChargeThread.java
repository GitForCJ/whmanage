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
		Log.info("��ֵ����:"+phone+",������ֵ"+(fee/1000)+"Ԫ,��ʼѭ����ֵ��");
		BiProd bp=new BiProd();
		for(int i=0;i<rs.length;i++){
			String seqNo=Tools.getStreamSerial(phone);
			String serialNo= "wh09015" + Tools.getNow3().substring(6)+ Tools.buildRandom(2) + Tools.buildRandom(3);
			
			try{
				int ss=bp.XYdxFill(parentID, userno, phonePid, userPid, "0",
						phone, seqNo, rs[i],sa_zone,username,numType,serialNo,"OF");
				Log.info("��ֵ����:"+phone+",ѭ����ֵ,��"+(i+1)+"��,��ֵ���:"+(rs[i]/1000)+"Ԫ,��ֵ��ֵ�����ύ���:"+ss);
				System.out.println("��ֵ����:"+phone+",ѭ����ֵ,��"+(i+1)+"��,��ֵ���:"+(rs[i]/1000)+"Ԫ,��ֵ��ֵ�����ύ���:"+ss);
			}catch(Exception e){
				Log.error("����ֵ����ֵ����:"+phone+",ѭ����ֵ��"+(i+1)+"�γ�ֵ����ֵ��"+rs[i]+"�ϵͳ�쳣����"+e);
			}
		}
		Log.info("��ֵ����:"+phone+",������ֵ"+(fee/1000)+"Ԫ,ѭ����ֵ��ϡ�ѭ������:"+rs.length);
	}
}
