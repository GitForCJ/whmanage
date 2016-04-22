package com.wlt.webm.rights.bean;


import com.wlt.webm.db.DBService;
import com.wlt.webm.rights.form.SysUserBankForm;

/**
 * 用户银行卡管理
 */
public class SysUserBank
{
	/**
	 * 获取用户银行卡信息(单个)
	 * @return 用户银行卡信息
	 * @throws Exception
	 */
	public SysUserBankForm getUserBankInfo(String userid) throws Exception {
        DBService db = new DBService();
        SysUserBankForm userBankForm = new SysUserBankForm();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select a.user_id,a.user_bankcard,a.user_name,a.user_icard_type,a.user_icard,b.user_name as username")
                    .append(" from sys_userbank a left join sys_user b on a.user_id = b.user_id ")
                    .append(" where a.user_id=? ");

            String[] params = { userid };
            String[] fields = { "user_id","user_bankcard", "user_name" ,"user_icard_type","user_icard","username"};
            
            db.populate(userBankForm, fields, sql.toString(), params);

            return userBankForm;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
	 * 添加用户银行卡信息
	 * @return 用户银行卡信息
	 * @throws Exception
	 */
	public int addUserBank(SysUserBankForm userBankForm) throws Exception {
        DBService db = new DBService();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("insert into sys_userbank (user_id,user_bankcard,user_name,user_icard_type,user_icard)")
               .append(" values("+userBankForm.getUser_id()+",'"+userBankForm.getUser_bankcard()+"','"+userBankForm.getUser_name()+"','"+userBankForm.getUser_icard_type()+"','"+userBankForm.getUser_icard()+"') ");
            return db.update(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
	 * 更新用户银行卡信息
	 * @return 用户银行卡信息
	 * @throws Exception
	 */
	public int updateUserBank(SysUserBankForm userBankForm) throws Exception {
        DBService db = new DBService();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("update sys_userbank set ")
            	.append("user_bankcard = "+userBankForm.getUser_bankcard())
            	.append(",user_name = '"+userBankForm.getUser_name()+"'")
            	.append(",user_icard_type = '"+userBankForm.getUser_icard_type()+"'")
            	.append(",user_icard = '"+userBankForm.getUser_icard()+"'")
            	.append(" where user_id="+userBankForm.getUser_id());
            return db.update(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
}

