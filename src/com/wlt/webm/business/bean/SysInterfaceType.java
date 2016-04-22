package com.wlt.webm.business.bean;

import java.sql.SQLException;
import java.util.List;

import com.wlt.webm.business.form.SysInterfaceTypeForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.db.DBToolSCP;
import com.wlt.webm.pccommon.Constants;


/**
 * 充值接口管理
 */
public class SysInterfaceType
{
	/**
	 * 获取充值接口列表 
	 * @return 充值接口列表 
	 * @throws Exception
	 */
	public List listSiType() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select a.in_id,a.in_name,a.in_explain ")
			.append(" from sys_interface_type a")
			.append(" order by a.in_id");
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	
	/**
	 * 添加充值接口
	 * @param bpForm 
	 * @return 记录数
	 * @throws Exception
	 */
	public int add(SysInterfaceTypeForm sitForm) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql3 = new StringBuffer();	
			sql3.append("insert into sys_interface_type(in_id,in_name,in_explain) values('"+sitForm.getIn_id()+"','"+sitForm.getIn_name()+"','"+sitForm.getIn_explain()+"')");
			dbService.update(sql3.toString());
			return 0;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	
	/**
	 * 删除充值接口
	 * @param bpForm 
	 * @return bool
	 * @throws Exception
	 */
	public boolean del(SysInterfaceTypeForm sitForm) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql1 = new StringBuffer();	
			sql1.append("delete from sys_interface_type where in_id='"+sitForm.getIds()[0]+"';");
			dbService.update(sql1.toString());
			return true;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	 /**
	 * 获取充值接口(单个)
	 * @return 面额信息
	 * @throws Exception
	 */
	public SysInterfaceTypeForm getSiTypeInfo(String siId) throws Exception {
        DBService db = new DBService(Constants.DBNAME_SCP);
        SysInterfaceTypeForm sitForm = new SysInterfaceTypeForm();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select  a.in_id,a.in_name,a.in_explain ")
                    .append(" from sys_interface_type a ")
                    .append(" where a.in_id=? ");

            String[] params = { siId };
            String[] fields = { "in_id","in_name", "in_explain"};
            
            db.populate(sitForm, fields, sql.toString(), params);

            return sitForm;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
     * 更新充值接口
     * @param prodForm 充值接口
     * @return int
     * @throws SQLException
     */
    public int update(SysInterfaceTypeForm sitForm) throws SQLException
    {
        StringBuffer sql = new StringBuffer();
        sql.append("update sys_interface_type set");
        sql.append(" in_name='"+sitForm.getIn_name()+"'");
        sql.append(" ,in_explain='"+sitForm.getIn_explain()+"'");
        sql.append(" where");
        sql.append(" in_id='"+sitForm.getIn_id()+"'");
        return DBToolSCP.update(sql.toString());
    }
}

