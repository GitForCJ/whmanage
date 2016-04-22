package com.wlt.webm.business.bean;

import org.apache.commons.lang.ArrayUtils;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBService;
import com.wlt.webm.tool.FloatArith;
import com.wlt.webm.tool.Tools;

/**
 * 拉卡拉通知记录
 * 
 * @author 1989
 * 
 */
public class LkalBusiness extends Thread {

	private Object[] obj = null;

	/**
	 * @param obj
	 */
	public LkalBusiness(Object[] obj) {
		this.obj = obj;
	}

	@Override
	public void run() {
//			Txncod,RequestId,MercId,TermId,RefNumber,OrgRefNum,
//			OrderID,Amount,TransTime,OrderSta,PayType,ExtData
		 DBService db=null;
		 Object[] obj2=null;
		 try{
			 if(null!=obj&&obj[10].equals("07")&&"01".equals(obj[9])){
				 Log.info("lakala接收到冲正通知系统参考号:"+obj[4]+" 原系统参考号:"+obj[5]);
				 db=new DBService();
				 db.update("update wht_lakal_record set state=4 where refNumber='"+obj[5]+"'");
			 }else{
			 if("00".equals(obj[9])){
			 Log.info("lakala接收到加款通知..."+obj[4]);
			 db=new DBService();
			 if(0!=(db.getInt("select id from wht_lakal_record where refNumber='"+obj[4]+"'"))){
				 Log.info("lakala接收到重复加款通知..."+obj[4]);
				 return ;
			 }
			 double  n=FloatArith.mul(Double.valueOf(obj[7]+""), 10) ;//Integer.parseInt(obj[7]+"");
			 double  fee=FloatArith.sub(n, FloatArith.mul(n, 0.0038));
			 obj[7]=n;
			 String[] str=db.getStringArray("select userno,fundacct,userlogin from sys_lkal where type=0 and termid='"+obj[3]+"'");
		     if(null==str||str.length==0){
		    	obj2=new Object[]{0,Tools.getNow(),null,null,null,fee,0,null,null,null,null}; 
		     }else{
		    	 obj2=new Object[]{1,Tools.getNow(),str[0],str[1],str[2],fee,0,null,null,null,null};  
		     }
		     Object[] obj3=ArrayUtils.addAll(obj, obj2);
		     db.logData(24, ArrayUtils.addAll(new Object[]{null},obj3), "wht_lakal_record");
			 }else{
				 Log.info("接收到lakala加款失败通知:"+obj[4]+"  交易类型:"+obj[10]);
			 }
			 }
			 }
		     catch (Exception e) {
			 Log.error("异步记录lakala通知异常,终端号:"+obj[3]+"  系统参考号:"+obj[4]+"交易类型"+obj[10]+"  exception:"+e.toString());
		}finally{
			if(null!=db){
				db.close();
			}
		}
	}
}
