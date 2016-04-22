package com.wlt.webm.business.bean;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List; 

import com.wlt.webm.business.form.CityReportForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.util.Tools;



public class ReportBean {

	
	
	
	public List queryCityTradeLog(CityReportForm form)
	{
		DBService service = null;
		try {
			service = new DBService();
			StringBuffer sql = new StringBuffer();
			String date = Tools.getNow4();
			String subTableName =  com.ejet.util.Tools.getPreviousDate(date, "yyyyMMdd").substring(2, 6);
			if(form.getStartDate()!=null && !"".equals(form.getStartDate()))
			{
				subTableName = form.getStartDate().substring(2, 6);
			}
			String tradeTable = "wlt_trade_" + subTableName;
			sql.append(" select (select are.sa_name from sys_area are where are.sa_id=s.sa_pid),s.sa_name,t.service,term_type, ");
			sql.append(" (case t.state  when 0 then '正常' ");
			sql.append(" when 1 then '返销' ");
			sql.append(" end) as trade_state , ");
			sql.append(" SUM(t.all_fee)as fee,sum(t.count)as count  ");
			sql.append(" ");
			sql.append(" from ").append(tradeTable).append(" t ");
			sql.append(" inner join sys_area s on t.areacode = s.sa_id   "); 
			sql.append(" where 1=1 ");
			if(form!=null)
			{
				if(form.getAreacode()!=null && !"".equals(form.getAreacode()))
				{
					sql.append(" and t.areacode='"+form.getAreacode()+"' ");
				}
				if(form.getUcity()!=null && !"".equals(form.getUcity()))
				{
					sql.append(" and s.sa_pid='"+form.getUcity()+"' ");
				}
				if(form.getStartDate()!=null && !"".equals(form.getStartDate()))
				{
					sql.append(" and t.trade_date>='"+form.getStartDate()+"' ");
				}
				if(form.getEndDate()!=null && !"".equals(form.getEndDate()))
				{
					sql.append(" and  t.trade_date<='"+form.getEndDate()+"' ");
				}
				if(form.getService()!=null && !"".equals(form.getService()))
				{
					sql.append(" and t.service='"+form.getService()+"' ");
				}
				if(form.getState()!=null && !"".equals(form.getState()))
				{
					sql.append(" and t.state="+form.getState()+" ");
				}
				if(form.getUser_id()!=null && !"".equals(form.getUser_id()))
				{
					sql.append(" and t.userid="+form.getUser_id()+" ");
				}
			}			
			sql.append(" group by  t.service,t.term_type,t.state, s.sa_name,s.sa_pid   ");
			sql.append(" ORDER BY s.sa_name");
			return service.getList(sql.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally
		{
			service.close();
		}
	}
	
	
	
	public List queryAgentTradeLog(CityReportForm form)
	{
		DBService service = null;
		try {
			String date = Tools.getNow4();
			String subTableName =  com.ejet.util.Tools.getPreviousDate(date, "yyyyMMdd").substring(2, 6);
			if(form.getStartDate()!=null && !"".equals(form.getStartDate()))
			{
				subTableName = form.getStartDate().replace("-", "").substring(2, 6);
			}
			String tradeTable = "wlt_trade_" + subTableName;
			service = new DBService();
			StringBuffer sql = new StringBuffer();
			sql.append(" select t.user_name,t.service,term_type, ");
			sql.append(" (case t.state  when 0 then '正常' ");
			sql.append(" when 1 then '返销' ");
			sql.append(" end) as trade_state , ");
			sql.append(" SUM(t.all_fee)as fee,sum(t.count)as count,s.sa_name  ");
			sql.append(" ");
			sql.append(" from ").append(tradeTable).append(" t ");
			sql.append(" inner join sys_area s on t.areacode = s.sa_id   "); 
			sql.append(" where 1=1 ");
			if(form!=null)
			{
				if(form.getAreacode()!=null && !"".equals(form.getAreacode()))
				{
					sql.append(" and t.areacode='"+form.getAreacode()+"' ");
				}
				if(form.getUcity()!=null && !"".equals(form.getUcity()))
				{
					sql.append(" and s.sa_pid='"+form.getUcity()+"' ");
				}
				if(form.getStartDate()!=null && !"".equals(form.getStartDate()))
				{
					
					sql.append(" and t.trade_date>='"+form.getStartDate().replace("-", "")+"' ");
				}
				if(form.getEndDate()!=null && !"".equals(form.getEndDate()))
				{
					sql.append(" and  t.trade_date<='"+form.getEndDate().replace("-", "")+"' ");
				}
				if(form.getService()!=null && !"".equals(form.getService()))
				{
					sql.append(" and t.service='"+form.getService()+"' ");
				}
				if(form.getState()!=null && !"".equals(form.getState()))
				{
					sql.append(" and t.state="+form.getState()+" ");
				}
				if(form.getUser_id()!=null && !"".equals(form.getUser_id()))
				{
					sql.append(" and t.userid="+form.getUser_id()+" ");
				}
			}
			sql.append(" group by  t.service,t.term_type,t.state,t.user_name ");
			sql.append(" ORDER BY t.user_name	  ");
			System.out.println("<><><><><><><><><><><><><><><>sql:    "+sql);
			return service.getList(sql.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally
		{
			service.close();
		}

	}
}
