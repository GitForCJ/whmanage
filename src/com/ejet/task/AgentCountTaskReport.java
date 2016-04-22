package com.ejet.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.commsoft.epay.util.logging.Log;
import com.ejet.common.struts.bean.AgentCountVo;
import com.wlt.webm.db.DBService;
import com.wlt.webm.pccommon.Constants;

//@Component
public class AgentCountTaskReport implements TaskReportBase{

	public static Logger log = Logger.getLogger(AgentCountTaskReport.class);

	//@Scheduled(cron="0 0 0 * * ?  ")
	public void excute() { 
		DBService db=null;
		try { 
			
			
			Log.info("处理任务开始: " + this.toString());
			
			
			//查询
			db = new DBService(Constants.DBNAME_SCP);
			//sdf.format(new Date());
			String tableName = sdf.format(new Date());
			Date date= new Date();
			date.setDate(date.getDate()-1);
			String timeStr = new SimpleDateFormat("yyyyMMdd").format(date);
			StringBuffer sql = new StringBuffer();
			sql.append(" select u.user_name,count(0)as order_num, ");
			sql.append(" CASE f.term_type when '0' then 'PC' ");
			sql.append(" when '1' then 'android' ");
			sql.append(" when '2' then 'iphone' ");
			sql.append(" end as trade_type,b.state,sum(b.tradefee)as sumfee,u.user_id,f.areacode,s.name as bus_type,f.buyid,f.phone_type,SUBSTR(b.tradetime,1,8)as trade_date  from ");
			sql.append(" wlt.wlt_acctbill_").append(tableName).append(" b ");
			sql.append(" inner join wlt.wlt_orderform_").append(tableName).append(" f on b.tradeserial =f.tradeserial ");
			sql.append(" inner join wlt.wlt_service s on f.service = s.`code` ");
			sql.append(" inner join wlt.sys_user u on f.userid = u.user_id ");
            sql.append(" where SUBSTR(b.tradetime,1,8)>=").append(timeStr);
			/*where f.userid=40 wlt.wlt_acctbill_1304 b */ 
			sql.append(" group by s.`name`,CASE f.term_type when '0' then 'PC'");
			sql.append("   when '1' then 'android'");
			sql.append("   when '2' then 'iphone'");
			sql.append(" end,u.user_id,u.user_name,b.state,f.areacode,  ");
			sql.append(" SUBSTR(b.tradetime,1,8),f.buyid,f.phone_type ");
			sql.append(" order by SUBSTR(b.tradetime,1,8) asc ");
			
			StringBuffer insertSql = new StringBuffer();
			List<AgentCountVo>agents = db.populate(AgentCountVo.class	,new String[]{"user_name","order_num","trade_type","state","sumfee","user_id","areacode","bus_type","buyid","phone_type","trade_date"},sql.toString());
			if(agents!=null && !agents.isEmpty())
			{
				db.setAutoCommit(false);
				insertSql.append("insert into wlt.wlt_trade_"+tableName + "(areacode,buyid,service,state,term_type,userid,user_name,phone_type,all_fee,count,trade_date)values(?,?,?,?,?,?,?,?,?,?,?)");
				/*for(AgentCountVo vo :agents)
				{ 
					db.update(insertSql.toString(), new Object[]{
						vo.getAreacode(),
						vo.getBuyid(),
						vo.getBus_type(),
						vo.getState(),
						vo.getTrade_type(),
						vo.getUser_id(),
						vo.getUser_name(),
						vo.getPhone_type(),
						vo.getSumfee(),
						vo.getOrder_num(),
						vo.getTrade_date()
					});
				}*/
				
				//更新日志表
				StringBuffer insertLog = new StringBuffer();
				insertLog.append("insert into wlt.wlt_tasklog (taskname, type, shoulddate, realtime, state, grade, explain)values(?,?,?, ?,?,?,?)");
				
				
				
				db.commit();
			}
			Log.info("处理任务结束: " + this.toString());
		} catch (Exception e) {
			Log.error("处理任务异常: ", e);
		}
		finally{
			if(null!=db)
				db.close();
		}
	}



	public String toString()
	{
		return "[代理商统计定时任务]";
	}
}
