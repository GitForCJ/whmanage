package com.wlt.webm.branchManage;

import java.util.List;

import com.wlt.webm.db.DBService;

/**
 * @author adminA
 *
 */
public class BranchManageServices {

	/**
	 * 获取上级 管理信息
	 * @param userno
	 * @return String
	 * @throws Exception
	 */
	public String getFatherUser(String userno) throws Exception
	{
	   DBService db = new DBService();
        try {
            String sql=" SELECT CONCAT(user_ename,'(',user_login,')') str FROM sys_user WHERE user_id=(SELECT user_pt FROM sys_user WHERE user_no='"+userno+"')";
            return db.getString(sql,null);
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
	}

	/**
	 * 获取代理商信息
	 * @return List<Object[]>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getAgentInfo() throws Exception
	{
	   DBService db = new DBService();
        try {
            String sql=" SELECT user_id,CONCAT(user_ename,'/',user_login) FROM sys_roletype t JOIN sys_role r ON t.sr_type=r.sr_type JOIN sys_user u ON r.sr_id=u.user_role WHERE t.sr_type in (0,1,2) ";
            return db.getList(sql,null);
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
	}

	/**
	 * 获取总行数
	 * @param inputName
	 * @param userno
	 * @return int
	 * @throws Exception
	 */
	public int getAgentInfoCount(String inputName,String userno,String sr_type,String inDate,String endDate) throws Exception
	{
		String sql=" SELECT count(*) con "+
		"  FROM sys_user a , wht_facct b,sys_role c WHERE a.user_no=b.user_no AND c.sr_id=a.user_role and ";
	if(inputName!=null && !"".equals(inputName))
	{
		sql=sql+" a.user_pt=(SELECT user_id FROM sys_user WHERE user_login='"+inputName+"') ";
		if(sr_type!=null && !"".equals(sr_type)){
			sql=sql+" and c.sr_type="+sr_type;
		}
	}
	else if((userno!=null && !"".equals(userno))){
		sql=sql+" a.user_pt='"+userno+"'  ";
		if(sr_type!=null && !"".equals(sr_type)){
			sql=sql+" and c.sr_type="+sr_type;
		}
	}
	else
	{
		sql=sql+" a.user_pt='"+userno+"'  ";
	}
	
	if(!"".equals(inDate) && inDate!=null)
	{
		sql=sql+" and a.user_createdate>='"+inDate.replace("-","")+"000000'";
	}
	if(!"".equals(endDate) && endDate!=null)
	{
		sql=sql+" and a.user_createdate<='"+endDate.replace("-","")+"235959'";
	}
	
		DBService db = new DBService();
        try {
           return db.getInt(sql);
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
	}
	
	/**
	 * 获取列表
	 * @param inputName
	 * @param userno
	 * @param index
	 * @param pagesize
	 * @return List<Object[]> 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> showAgentInfo(String inputName,String userno,int index,int pagesize,String sr_type,String inDate,String endDate) throws Exception
	{
		String sql=" SELECT a.user_login,a.user_no,a.user_ename , "+
			" CASE   "+
			" WHEN a.user_status = 0 THEN '正常' "+  
			" WHEN a.user_status =1 THEN  '注销'  "+
			" WHEN a.user_status =2 THEN '暂停服务'   "+  
			" WHEN a.user_status =3 THEN '清算'   END  AS state , "+
			"  b.fundacct,CONCAT(SUBSTRING(a.user_createdate,1,4),'-',SUBSTRING(a.user_createdate,5,2),'-',SUBSTRING(a.user_createdate,7,2))  "+
			"  FROM sys_user a , wht_facct b,sys_role c WHERE a.user_no=b.user_no AND c.sr_id=a.user_role and ";
		if(inputName!=null && !"".equals(inputName))
		{
			sql=sql+" a.user_pt=(SELECT user_id FROM sys_user WHERE user_login='"+inputName+"') ";
			if(sr_type!=null && !"".equals(sr_type)){
				sql=sql+" and c.sr_type="+sr_type;
			}
		}
		else if((userno!=null && !"".equals(userno))){
			sql=sql+" a.user_pt='"+userno+"'  ";
			if(sr_type!=null && !"".equals(sr_type)){
				sql=sql+" and c.sr_type="+sr_type;
			}
		}
		else
		{
			sql=sql+" a.user_pt='"+userno+"'  ";
		}
		
		if(!"".equals(inDate) && inDate!=null)
		{
			sql=sql+" and a.user_createdate>='"+inDate.replace("-","")+"000000'";
		}
		if(!"".equals(endDate) && endDate!=null)
		{
			sql=sql+" and a.user_createdate<='"+endDate.replace("-","")+"235959'";
		}
		
		sql=sql+"  ORDER BY a.user_no ASC  limit "+(index-1)*pagesize+" , "+pagesize;
		
		DBService db = new DBService();
        try {
           return db.getList(sql);
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
	}

}
