package com.wlt.webm.business.bean;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBService;
import com.wlt.webm.tool.Tools;

/**
 * 流量比例统计
 * @author 1989
 */
public class FlowProportionCount extends TimerTask{

	@Override
	public void run() {
		proportion();
	}
	
	public void proportion(){
		DBService db=null;
		try{
			String nowdate=Tools.getNowDate();//yyyyMMdd
			db=new DBService();
			String riqi=db.getString("SELECT riqi FROM sys_flowcount WHERE state=0 ORDER BY id DESC  LIMIT 1");
			if(null!=riqi){
				ArrayList<String> dates=Tools.getIVdate(riqi,nowdate);
				if(null!=dates&&dates.size()>0){
					for(String date:dates){
						dealTradetime(date, db);
					}
				}
			}else{
				dealTradetime(Tools.getOtherTime(-1).substring(0,8), db);
			}
		}catch(Exception ex){
			
		}finally{
			if(null==db){
				db.close();
			}
		}
	}
	
	/**
	 * 执行统计方法
	 * @param date
	 * @param db
	 */
	public void dealcount(String date,DBService db){
		Log.info(date+" 流量统计任务开始");
		String sql="INSERT INTO sys_flowcountRecord(riqi,bishu,state,buyid,buyname,sa_id,sa_name)"+ 
        " SELECT '"+date+"',COUNT(a.tradeserial) AS bishu,a.state,b.id,b.name,c.sa_id,c.sa_name"+
        " FROM wht_orderform_"+date.substring(2, 6)+" a LEFT JOIN  sys_interface b"+
        " ON a.buyid=b.id LEFT JOIN sys_area c ON a.phone_pid=c.sa_id"+
        " WHERE a.tradetime>'"+date+"000000' AND a.tradetime<'"+date+"235959' and b.flag=2 GROUP BY a.state,a.buyid,a.phone_pid  ORDER BY a.buyid";
		String sql1="INSERT INTO sys_flowcount(riqi,counttime,state) VALUE('"+
		date+"','"+Tools.getNow()+"',0)";
		try {
			db.setAutoCommit(false);
			db.update(sql);
			db.update(sql1);
			db.commit();
			Log.info(date+" 流量统计任务结束");
		} catch (SQLException e) {
			Log.error(date+" 流量统计任务出错:"+e.toString());
			db.rollback();
		}
	}
	
	
	/**
	 * 执行统计交易时间方法
	 * @param date
	 * @param db
	 */
	public void dealTradetime(String date,DBService db){
		Log.info(date+" 统计流量交易时间任务开始");
		String sql="INSERT INTO sys_flowtimerecord(riqi,userno,timerange,num)"+ 
		" SELECT '"+date+"',a.userno, ELT(INTERVAL(a.difftime,0,180,600),'三分钟内','三到十分钟','十分钟以上') AS timerange,COUNT(a.id)"+
		" FROM (SELECT id,userno,buyid,TIMESTAMPDIFF(SECOND,DATE_FORMAT(tradetime,'%Y%m%d%H%i%s'),DATE_FORMAT(chgtime,'%Y%m%d%H%i%s')) AS difftime"+
		" FROM wht_orderform_"+date.substring(2, 6)+
		" WHERE tradetime>='"+date+"000000' AND tradetime<='"+date+"235959' AND service IN('0009','0010','0011') ) a"+
		" GROUP BY userno,ELT(INTERVAL(a.difftime,0,180,600),'三分钟内','三到十分钟','十分钟以上')";
		String sql1="INSERT INTO sys_flowcount(riqi,counttime,state) VALUE('"+
		date+"','"+Tools.getNow()+"',0)";
		try {
			db.setAutoCommit(false);
			db.update(sql);
			db.update(sql1);
			db.commit();
			Log.info(date+" 统计流量交易时间任务结束");
		} catch (SQLException e) {
			Log.error(date+" 统计流量交易时间任务出错:"+e.toString());
			db.rollback();
		}
	}
	
	public static void main(String[] args) {
		Calendar cal=Calendar.getInstance();
		System.out.println(cal.get(Calendar.DAY_OF_MONTH));
		System.out.println(cal.getMaximum(Calendar.DAY_OF_MONTH));
		
	    System.out.println(Tools.compareDate("20151110", -1));
	    //----------
	    Calendar start = Calendar.getInstance();  
	    start.setTime(Tools.getDate("20151110")) ;
	    start.add(Calendar.DATE, 1);
	    Long startTIme = start.getTimeInMillis();  
	    Calendar end = Calendar.getInstance();  
	    end.setTime(Tools.getDate("20151111"));  
	    Long endTime = end.getTimeInMillis();  
	    Long oneDay = 1000 * 60 * 60 * 24l;  
	  
	    Long time = startTIme;
	    ArrayList<String> dates=new ArrayList<String>();
	    while (time < endTime) {
	        Date d = new Date(time);  
	        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");  
	        dates.add(df.format(d));  
	        System.out.println(df.format(d));
	        time += oneDay;  
	    }  
	    System.out.println(dates.size());
	}
}
