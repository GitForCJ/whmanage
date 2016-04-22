package com.wlt.webm.AccountInfo;

import java.util.List;

import com.wlt.webm.db.DBService;

/**
 * @author adminA
 *
 */
public class LakalaAccountServices {
	
	/**
	 * 获取拉卡拉绑定信息 总行数
	 * @param userlogin 
	 * @param userno 
	 * @return int
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int getAccountVerifyCount(String userlogin,String userno) throws Exception {
        DBService db = new DBService();
        try {
            String sql="SELECT count(*) com FROM sys_lkal where 1=1 ";
            if(userlogin!=null && !"".equals(userlogin))
            {
            	sql=sql+" AND userlogin='"+userlogin+"' ";
            }
            if(userno!=null && !"".equals(userno))
            {
            	sql=sql+" AND userno='"+userno+"'";
            }
            int count=db.getInt(sql,null);
            return count;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=db)
        		db.close();
        }
    }
	
	/**
	 * 获取拉卡拉绑定信息 lsit
	 * @param userlogin 
	 * @param userno 
	 * @param index 
	 * @param pagesize 
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> showAccountVerify(String userlogin,String userno,int index,int pagesize) throws Exception {
        DBService db = new DBService();
        List<Object[]> arrList=null;
        try {
        	 String sql="SELECT termid,userno,fundacct,userlogin,TIME FROM sys_lkal where 1=1 ";
             if(userlogin!=null && !"".equals(userlogin))
             {
             	sql=sql+" AND userlogin='"+userlogin+"' ";
             }
             if(userno!=null && !"".equals(userno))
             {
             	sql=sql+" AND userno='"+userno+"'";
             }
             sql=sql+" order by TIME asc limit "+(index-1)*pagesize+" , "+pagesize;
            arrList=db.getList(sql,null);
            return arrList;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
}
