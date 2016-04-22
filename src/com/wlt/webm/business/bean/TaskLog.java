package com.wlt.webm.business.bean;

import java.sql.SQLException;
import java.util.List;

import org.springframework.util.StringUtils;

import com.ejet.task.Constant;
import com.ejet.util.Tools;
import com.ejet.util.format.Formatter;
import com.wlt.webm.business.form.TaskForm;
import com.wlt.webm.db.DBToolSCP;
import com.wlt.webm.scpdb.DBService;
import com.wlt.webm.util.Pagination;
import com.wlt.webm.util.SQLUtil;
import com.wlt.webm.util.StringUtil;

/**
 * 后台任务管理
 * Company：深圳市###EJET###有限公司
 * Copyright: Copyright (c) 2009
 * version 2.0.0.0
 * @author Ejet.Shen
 */
public class TaskLog {
	/**
	 * 分页信息
	 */
    private Pagination page;

	/**
	 * @return 分页信息
	 */
	public Pagination getPage() {
		return page;
	}

    /**
     * 查询后台任务日志信息表
     * @param taskForm 表单
     * @param userForm 用户信息
     * @return List(String[])信息列表
     * @throws SQLException
     */
	public List listTaskLog(TaskForm taskForm) throws SQLException{
		StringBuffer sql =new StringBuffer(); //Formatter.format(taskForm.getStartdate(), Formatter.YMD)
		String startDate = Tools.getNow4();
		String endDate = Tools.getNow4();
		sql.append("SELECT A.taskname, A.type, A.shoulddate, A.realtime, A.state, A.modifydate, A.explain, A.id  FROM wlt_tasklog A WHERE 1=1 ");
		if ( taskForm!=null && taskForm.getStartdate()!=null && !taskForm.getStartdate().isEmpty())
			startDate = taskForm.getStartdate();
		if ( taskForm!=null && taskForm.getEnddate()!=null && !taskForm.getEnddate().isEmpty())
			 endDate = taskForm.getEnddate();
		if ( taskForm!=null && taskForm.getType()!=null && !taskForm.getType().isEmpty())
			sql.append(" AND A.type='").append(taskForm.getType()).append("' ");
		SQLUtil.addGreatEqual(sql, "A.shoulddate", startDate);
		SQLUtil.addLessEqual(sql, "A.shoulddate",endDate);
		sql.append(" order by A.shoulddate desc ");
		List rslist = DBToolSCP.getList(sql.toString());
		//List rslist=DBToolSCP.getPageList(sql.toString(), page);
		for(int i=0;i<rslist.size();i++){
			String str[]=(String[])rslist.get(i);
			str[1]= Constant.formatCountType(str[1]);
			str[2]=Tools.formatDate5(str[2]);
			str[3]=Tools.formatDate3(str[3]);
			str[4]=Constant.formatLogState(str[4]);
			str[5]=Tools.formatDate3(str[5]);
		}
		return rslist;
	}
	/**
	 * 任务重新执行
	 * @param taskForm 任务信息表单
	 * @param user 用户信息
	 * @return 1、成功；
	 * @throws Exception
	 */
    public int reExecute(TaskForm taskForm) throws Exception
    {
        DBService db = new DBService();
        StringBuffer sql = new StringBuffer();
        String[] ids = taskForm.getIds();
        String id   = ids[0];
        String date = "";
        String table = "";
        String type="";
        try
        {
            db.setAutoCommit(false);
            sql.append("update wlt_tasklog set state=1, modifydate='").append( Tools.getNow3()) .append("'")
            		.append(" where id='").append(id).append("'");
            int count = db.update(sql.toString());          
            if( count==1 )//删除统计日志
            {
            	  StringBuffer querySql = new StringBuffer();
                  querySql.append("select shoulddate, type from wlt_tasklog ").append(" where id='").append(id).append("'");;
                  List list = db.getList(querySql.toString());
                  if(list!=null && list.size()>0)
                  {
                	  date = ((String[])list.get(0))[0];
                	  type = ((String[])list.get(0))[1];
                	  if( date!=null && date.length()==8)
                	  {
                    	  table = "wlt_trade_" + date.substring(2,6);
	                      StringBuffer delSql = new StringBuffer();
	                      delSql.append("DELETE FROM ").append(table).append(" WHERE create_date='").append(date)
	                      .append("' AND task_type='").append(type).append("'");
	                      db.update(delSql.toString());
                	  }
                  }
            }
            db.commit();
        }
        catch (Exception ex)
        {
            db.rollback();
            throw ex;
        }
        finally
        {
            db.close();
        }
        return 1;
    }

}
