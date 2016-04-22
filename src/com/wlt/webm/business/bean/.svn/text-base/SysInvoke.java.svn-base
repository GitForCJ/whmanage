package com.wlt.webm.business.bean;

import com.wlt.webm.business.form.SysInvokeForm;
import com.wlt.webm.db.DBService;


/**
 * 接口日志管理
 */
public class SysInvoke
{
	
	/**
	 * 添加接口日志
	 * @param siForm 
	 * @return 记录数
	 * @throws Exception
	 */
	public int add(SysInvokeForm siForm) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("insert into sys_invokelog(user_id,ord_id,in_input,in_output,in_time,in_desc,in_code,in_otherput,se_code) " +
					"values('"+siForm.getUser_id()+"','"+siForm.getOrd_id()+"','"+siForm.getIn_input()+"','"+siForm.getIn_output()+"'," +
					"'"+siForm.getIn_time()+"','"+siForm.getIn_desc()+"','"+siForm.getIn_code()+"','"+siForm.getIn_otherput()+"','"+siForm.getSe_code()+"')");
			dbService.update(sql.toString());
			return 0;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	/**
	 * 更新接口日志
	 * @param siForm 
	 * @return 记录数
	 * @throws Exception
	 */
	public int update(String inOtherInput,String ordId,String dateStr) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("update sys_invokelog set in_time = '"+dateStr+"', in_otherput = '"+inOtherInput+"' where ord_id = '"+ordId+"'");
			dbService.update(sql.toString());
			return 0;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
}

