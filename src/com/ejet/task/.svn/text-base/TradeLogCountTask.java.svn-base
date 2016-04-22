package com.ejet.task;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ejet.common.struts.bean.AgentCountVo;
import com.wlt.webm.db.DBService;
import com.wlt.webm.pccommon.Constants;
import com.wlt.webm.util.SQLUtil;
import com.wlt.webm.util.Tools;

public class TradeLogCountTask {
	
	private static Logger log = Logger.getLogger(TradeLogCountTask.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
	private String tasklog_table = "wlt_tasklog";
	
	public void excute() {
		DBService db = null;		
	try {
			log.info("地市、代理商任务开始: " + this.toString());
			insertTaskLog(Constant.COUNT_TYPE_CITY_AGENT_DAY);
	   		List<String[]> list = getExecuteData(Constant.COUNT_TYPE_CITY_AGENT_DAY);//
	   		if(list!=null || list.size()>0)
		   	{
	   			db = new DBService();
				db.setAutoCommit(false);
	   			for(String[]item :list)
	   			{
	   				String logId = item[0];
	   				String date = item[1];//统计时间
	   				String subTableName =  com.ejet.util.Tools.getPreviousDate(date, "yyyyMMdd").substring(2, 6);
	   				String acctbill_table = "wlt_acctbill_" + subTableName;
		   			String order_table = "wlt_orderform_" + subTableName;
		   			String trade_table = "wlt_trade_" + subTableName;
		   			String startTime = com.ejet.util.Tools.getPreviousDate(date, "yyyyMMdd")+"000000";
		   			String endTime = com.ejet.util.Tools.getPreviousDate(date, "yyyyMMdd")+"235959";
		   			
					StringBuffer sql = new StringBuffer();
					sql.append(" select u.user_ename, u.user_name,count(0)as order_num, ");
					sql.append(" CASE f.term_type when '0' then 'PC' when '1' then 'android'   when '2' then 'iphone' end as trade_type,  ");
					sql.append(" b.state, sum(b.tradefee)as sumfee, u.user_id, f.areacode, s.name as bus_type, f.buyid,f.phone_type,SUBSTR(b.tradetime,1,8)as trade_date  from ");
					sql.append( acctbill_table ).append(" b ");
					sql.append(" inner join ").append(order_table).append(" f on b.tradeserial =f.tradeserial ");
					sql.append(" inner join wlt_service s on f.service = s.`code` ");
					sql.append(" inner join sys_user u on f.userid = u.user_id ");
		            sql.append(" where 1=1 ");
		        	SQLUtil.addGreatEqual(sql, "b.tradetime", startTime);
					SQLUtil.addLessEqual(sql, "b.tradetime",endTime);
					sql.append(" group by s.`name`,CASE f.term_type when '0' then 'PC'");
					sql.append("   when '1' then 'android'");
					sql.append("   when '2' then 'iphone'");
					sql.append(" end,u.user_id,u.user_name,b.state,f.areacode,  ");
					sql.append(" SUBSTR(b.tradetime,1,8),f.buyid,f.phone_type ");
					sql.append(" order by SUBSTR(b.tradetime,1,8) asc ");
					System.out.println("★★★★★★★★" + sql.toString());
					List<AgentCountVo>agents = db.populate(AgentCountVo.class	,new String[]{"user_ename", "user_name","order_num","trade_type","state","sumfee","user_id","areacode","bus_type","buyid","phone_type","trade_date"},sql.toString());
					db.setAutoCommit(false);
					
					StringBuffer insertSql = new StringBuffer();
					insertSql.append("insert into " + trade_table + "(areacode,buyid,service,state,term_type,userid,user_name,phone_type,all_fee,count,trade_date, task_type, create_date, modify_date)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					for(AgentCountVo vo :agents)
					{ 
						db.update(insertSql.toString(), new Object[]{
							vo.getAreacode(),
							vo.getBuyid(),
							vo.getBus_type(),
							vo.getState(),
							vo.getTrade_type(),
							vo.getUser_id(),
							vo.getUser_name()+ "(" + vo.getUser_ename() + ")",
							vo.getPhone_type(),
							vo.getSumfee(),
							vo.getOrder_num(),
							vo.getTrade_date(),
							Constant.COUNT_TYPE_CITY_AGENT_DAY,
							date,
							Tools.getNowMiss()
						});
					}
					//更新日志表
					StringBuffer updateLog = new StringBuffer();
					updateLog.append("update " + tasklog_table + " set state=0, modifydate='" +Tools.getNow3()+ "', ")
					.append(" realtime='"+Tools.getNow3()+ "'")
					.append(" where id='").append(logId).append("'");
	   				db.update(updateLog.toString());
	   			}
		   	}	
	   		db.commit();
		    System.out.println("处理任务开始: " + this.toString() + ". 时间[" + new java.util.Date().toString()  + "]");
		    log.info("处理任务结束: " + this.toString());
		} catch (Exception e) {
		    log.error("处理任务异常: ", e);
		}finally
		{
			if(null!=db)
				db.close();
		}
	}
	
	private List<String[]> getExecuteData(String type)
	{
		List<String[]> list = new ArrayList();
		DBService db = null;
		try {
			//查询
			db = new DBService(Constants.DBNAME_SCP);
			StringBuffer sql = new StringBuffer();
			sql.append("select id, shoulddate from wlt_tasklog where state!='0'");
			if( type!=null )
				sql.append(" and type='").append(type).append("' ").append(" order by shoulddate desc ");
			list = db.getList(sql.toString());
		} catch (Exception e) {
		    log.error("处理任务异常: ", e);
		}finally{
			db.close();
		}
		return list;
	}
	
	//插入统计日志表
	private boolean insertTaskLog(String type)
	{
		boolean  ret= false;
		int count = 0;
		String date = Tools.getNow4();
		String id = Tools.getFormatNowTime("yyyyMMddhhmmsss")+ (count++);
		//查询
		DBService db = null;
		try {
			db = new DBService(Constants.DBNAME_SCP);
			StringBuffer querySql = new StringBuffer();
			querySql.append("select id from ").append(tasklog_table).append(" where type='").append(type).append("'")
			.append(" and shoulddate='").append(date).append("'");
			//System.out.println(querySql.toString());
			List list = db.getList(querySql.toString());
			if( list!=null && list.size()>0)
				return true;
			
			StringBuffer insertSql = new StringBuffer();
			insertSql.append("insert into ").append(tasklog_table).append(" ( id, taskname, type, shoulddate, realtime, state, modifydate) values (?,?,?,?,?,?,?)");
			db.update(insertSql.toString(), new Object[]{
				id,
				Constant.formatCountType(type),
				type,
				date,
				" ",
				Constant.TASK_LOG_STATE_RUNNING,
				Tools.getNow3(),
			});
			ret = true;
		} catch (Exception e) {
		    log.error("处理任务异常: ", e);
		}finally
		{
			db.close();
		}
		return ret;
	}
	
	
	public String toString()
	{
		return "[地市统计定时任务]";
	}
}
