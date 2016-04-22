package com.wlt.webm.pccommon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.unicom3gquery.HttpFillOP;
import com.wlt.webm.db.DBService;
import com.wlt.webm.ten.service.MD5Util;
import com.wlt.webm.tool.Tools;

/**
 * 启动定时回调下游接口
 * @author 1989
 */
public class BackToAgent extends TimerTask {
	public static HttpFillOP  op=new HttpFillOP(); 
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		DBService db = null;
		try {
			String time = Tools.getNow3();
			String tablename="wht_orderform_"+time.substring(2,6);
			db = new DBService();
			int n = db.getInt("SELECT orderid FROM sys_returnrecord ORDER BY logtime DESC LIMIT 1");
			String sql = "SELECT a.userno,a.returnurl,a.keyvalue,b.tradeserial,b.state,b.id FROM sys_agentsign a LEFT JOIN "+tablename+" b"
					+" ON a.userno=b.userno WHERE a.state=0 AND b.id>"
					+n+" AND b.term_type=3 AND EXISTS(SELECT '0009','0010','0011') AND b.state=0 ORDER BY b.id";
			List lists=db.getList(sql);
			if(null!=lists&&lists.size()>1){//有数据则回调处理
				ArrayList<String[]> result=(ArrayList<String[]>)lists;
				int a=result.size();
				db.update("insert into sys_returnrecord(orderid,logtime) values("+
						result.get(a-1)[5]+","+time+")");
				//每十个订单启动一个线程进行处理
				if(a<=10){
					new DealUrlRetrun(result).start();
				}else{
					int a1=a/10;
					int a2=a%10;
				for(int k=0;k<a1;k++){
					new DealUrlRetrun(result.subList(k*10, (k+1)*10)).start();
				}
				if(a2!=0){
					new DealUrlRetrun(result.subList(a1*10, a)).start();
				}
				}
			}else{
				Log.info("时间:"+time+" ID:"+n+",暂无数据可供回调");
			}
			
		} catch (Exception e) {
			Log.error("回调定时任务出错"+e.toString());
			e.printStackTrace();
		} finally {
             if(null!=db){
            	 db.close();
             }
		}
	}
	
	 /**
	 * @author 1989
	 *回调处理线程
	 */
	class DealUrlRetrun extends Thread{
		private List<String[]> datas=null;
		/**
		 * @param list
		 */
		public DealUrlRetrun(List<String[]> list){
			this.datas= list;
		}
		@Override
		public void run(){
			for(String[] str:datas){
				HashMap<String, String> data=new HashMap<String, String>();
				data.put("orderid", str[3]);
				data.put("state", str[4]);
				data.put("sign", MD5Util.MD5Encode(str[3]+str[4]+str[2], "UTF-8"));
				try {
					op.OPReturn(str[1], data, "POST");
					Log.info(str[3]+"  回调成功");
				} catch (Exception e) {
					Log.error("流量回调:"+str[3]+" 异常"+e.toString());
				} 
			}
		}
	}
}
