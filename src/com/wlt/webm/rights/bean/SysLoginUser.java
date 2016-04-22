package com.wlt.webm.rights.bean;


import java.sql.SQLException;
import java.util.List;

import com.wlt.webm.db.DBService;
import com.wlt.webm.db.DBToolSCP;
import com.wlt.webm.pccommon.Constants;
import com.wlt.webm.pccommon.TaskChecker;
import com.wlt.webm.rights.form.SysLoginUserForm;
import com.wlt.webm.util.MD5;

/**
 * 用户登陆账户管理
 */
public class SysLoginUser
{
	/**
     * 增加登陆账户
     * @param useForm 登陆账户信息
     * @return int
     * @throws Exception 
     */
    public int add(SysLoginUserForm useForm) throws Exception
    {
    	DBService db = new DBService();
		try {
        	db.setAutoCommit(false);
        	StringBuffer sql2 = new StringBuffer();
        	sql2.append("insert into sys_loginuser values( " +
        			""+useForm.getUser_id()+"," +
        			"'"+useForm.getUsername()+"'," +
        			"'"+MD5.encode(useForm.getUserpassword())+"'," +
        			"'"+useForm.getPassflag()+"'," +
        			"'"+useForm.getMacflag()+"'," +
        			"'"+useForm.getMac()+"'," +
        			"'"+useForm.getShortflag()+"'," +
        			"'"+useForm.getFeeshortflag()+"'," +
        			"'"+useForm.getPhone()+"'," +
        			"'"+useForm.getDealpass()+"'," +
        			"'"+useForm.getFeepass()+"'," +
        			"0"+
        			" )");
        	db.update(sql2.toString());
    		db.commit();
        } catch (Exception ex) {
        	db.rollback();
            throw ex;
        } finally {
            db.close();
        }
        return Integer.parseInt(useForm.getUser_id());
    }
    /**
	 * 获取登陆账户信息列表
	 * @return 登陆账户列表
	 * @throws Exception
	 */
	public List getSysLoginUserList(String userId) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
//		sql.append("select a.user_name,a.user_phone,a.user_macflag,a.user_mac,a.user_passflag,a.user_shortflag,a.user_feeshortflag,a.user_state");
		sql.append("select a.user_name,a.user_phone,a.user_state");
		sql.append(" from sys_loginuser a ");
		sql.append(" where user_id = "+userId);
		List list = dbService.getList(sql.toString());
		for(Object tmp : list){
			String[] temp = (String[])tmp;
//			if(null != temp[2] && !"".equals(temp[2])){
//				if("0".equals(temp[2])){
//					temp[2] = "验证";
//				}else if("1".equals(temp[2])){
//					temp[2] = "不验证";
//				}
//			}
//			if(null != temp[4] && !"".equals(temp[4])){
//				if("0".equals(temp[4])){
//					temp[4] = "验证";
//				}else if("1".equals(temp[4])){
//					temp[4] = "不验证";
//				}
//			}
//			if(null != temp[5] && !"".equals(temp[5])){
//				if("0".equals(temp[5])){
//					temp[5] = "验证";
//				}else if("1".equals(temp[5])){
//					temp[5] = "不验证";
//				}
//			}
//			if(null != temp[6] && !"".equals(temp[6])){
//				if("0".equals(temp[6])){
//					temp[6] = "验证";
//				}else if("1".equals(temp[6])){
//					temp[6] = "不验证";
//				}
//			}
			if(null != temp[2] && !"".equals(temp[2])){
				if("0".equals(temp[2])){
					temp[2] = "正常";
				}else if("1".equals(temp[2])){
					temp[2] = "禁用";
				}
			}
		}
		return list;
	}
	 /**
	 * 获取登陆账号信息(单个)
	 * @return 用户信息
	 * @throws Exception
	 */
	public SysLoginUserForm getLoginUserInfo(String userName) throws Exception {
        DBService db = new DBService(Constants.DBNAME_SCP);
        SysLoginUserForm userForm = new SysLoginUserForm();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select a.user_id,a.user_name,a.user_pass,a.user_phone,a.user_macflag,a.user_mac,a.user_passflag,a.user_shortflag,a.user_feeshortflag,a.user_dealpass,a.user_feepass,a.user_state")
                    .append(" from sys_loginuser a ")
                    .append(" where a.user_name=? ");

            String[] params = { userName };
            String[] fields = { "user_id","username", "userpassword" ,"phone","macflag","mac","passflag","shortflag","feeshortflag","dealpass","feepass","user_state"};
            
            db.populate(userForm, fields, sql.toString(), params);

            if(null != userForm.getUser_state()){
            	if("0".equals(userForm.getUserstatus())){
            		userForm.setUser_state("验证");
            	}else if("1".equals(userForm.getUserstatus())){
            		userForm.setUser_state("不验证");
            	}
            }
            return userForm;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
     * 更新用户
	 * @param a 
	 * @param b 
	 * @param c 
	 * @param d 
	 * @param userno 
	 * @param macaddress
	 * @param czflag 
     * @return int
	 * @throws SQLException 
     */
    public int update(int a,int b,int c,int d,String userno,String[] macaddress,String czflag) throws SQLException 
    {
    	String macStr="";
    	if(d==0)
    	{
    		for(int i=0;i<macaddress.length;i++)
    		{
    			if(!"".equals(macaddress[i]) && macaddress[i]!=null)
    			{
    				macStr=macStr+macaddress[i].trim()+"#";
    			}
    		}
    		if(!"".equals(macStr.trim()))
        	{
        		String updateSql=" update mac set macaddress='"+macStr+"' where userno="+userno;
        		try {
    				DBToolSCP.update(updateSql);
    			} catch (SQLException e) {
    				return -1;
    			}
        	}
    	}
    	TaskChecker.USEREXT3.put(userno, czflag);
    	String sql="update sys_user,mac set sys_user.user_shortflag="+c+",sys_user.user_feeshortflag="+b+",sys_user.exp3="+d+",mac.ext3="+czflag+" where sys_user.user_no=mac.userno and mac.userno="+userno;
    	return DBToolSCP.update(sql);
    }
    /**
     * 更新登陆密码
     * @param useForm 用户信息
     * @return int
     * @throws SQLException
     */
    public int updatePass(int id,String pwd) throws SQLException
    {   pwd=MD5.encode(pwd);
    	String sql="update sys_user set user_pass='"+pwd+"' where user_id="+id;
        return DBToolSCP.update(sql);
    }
    /**
     * 更新交易密码
     * @param useForm 用户信息
     * @return int
     * @throws SQLException
     */
    public int updatePassWord(int id,String pwd) throws SQLException
    {
    	 pwd=MD5.encode(pwd);
     	 String sql="update sys_user set trade_pass='"+pwd+"' where user_id="+id;
         return DBToolSCP.update(sql);
    }
    /**
     * 获取登陆密码
     * @param useForm 用户信息
     * @return int
     * @throws SQLException
     */
    public String getPass(int id) throws SQLException
    {
        StringBuffer sql = new StringBuffer();
        sql.append("select user_pass from sys_user");
        sql.append(" where");
        sql.append(" user_id ="+id);
        return DBToolSCP.getString(sql.toString());
    }
    /**
     * 获取交易密码
     * @param useForm 用户信息
     * @return int
     * @throws SQLException
     */
    public String getPassWord(int id,String pwd) throws SQLException
    {
        StringBuffer sql = new StringBuffer();
        sql.append("select trade_pass from sys_user");
        sql.append(" where");
        sql.append(" user_id ="+id);
        return DBToolSCP.getString(sql.toString());
    }
}

