package com.wlt.webm.business.bean;

import java.sql.SQLException;
import java.util.List;

import com.wlt.webm.business.form.SysInterfaceTypeForm;
import com.wlt.webm.business.form.SysUserInterfaceForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.db.DBToolSCP;
import com.wlt.webm.pccommon.Constants;
import com.wlt.webm.util.Tools;


/**
 * �û���ֵ�ӿڹ���
 */
public class SysUserInterface
{
	/**
	 * ��ȡ�û���ֵ�ӿ��б� 
	 * @return �û���ֵ�ӿ��б� 
	 * @throws Exception
	 */
	public List listUserInterface(String userId) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select a.id,c.user_name,d.sa_name,a.charge_type,b.in_name,a.is_return ")
			.append(" from sys_user_interface a, sys_interface_type b,sys_user c,sys_area d")
			.append(" where a.user_id = c.user_id and a.in_id = b.in_id and a.province_code = d.sa_id")
			.append(" and a.user_id = "+userId);
			List list = dbService.getList(sql.toString());
			
			for(Object tmp : list){
				String[] temp = (String[])tmp;
				if(null != temp[3] && !"".equals(temp[3])){
					if("0".equals(temp[3])){
						temp[3] = "����ҵ��";
					}else if("1".equals(temp[3])){
						temp[3] = "�ƶ�";
					}else if("2".equals(temp[3])){
						temp[3] = "��ͨ";
					}else if("3".equals(temp[3])){
						temp[3] = "����";
					}
				}
				if(null != temp[5] && !"".equals(temp[5])){
					if("0".equals(temp[5])){
						temp[5] = "��";
					}else if("1".equals(temp[5])){
						temp[5] = "��";
					}
				}
			}
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	/**
	 * ��ȡ�����б� 
	 * @return �����б� 
	 * @throws Exception
	 */
	public List listArea() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select sa_id,sa_name ")
			.append(" from sys_area")
			.append(" where sa_pid = 1");
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	/**
	 * ��ȡ�㶫ʡ�����б� 
	 * @return �����б� 
	 * @throws Exception
	 */
	public List listArea2() throws Exception {
		DBService dbService = new DBService();
		try {
			String sql = "SELECT sa_id,sa_name FROM sys_area WHERE sa_pid=35 ";
			List list = dbService.getList(sql);
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	/**
	 * ��ȡ�ӿ������б� 
	 * @return �ӿ������б� 
	 * @throws Exception
	 */
	public List listInterfaceType() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select id,name ")
			.append(" from sys_interface");
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	public List listflowInterfaceType() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select id,name ")
			.append(" from sys_interface where flag=2");
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	/**
	 * ����û���ֵ�ӿ�
	 * @param bpForm 
	 * @return ��¼��
	 * @throws Exception
	 */
	public int add(SysUserInterfaceForm suiForm) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql3 = new StringBuffer();	
			sql3.append("insert into sys_user_interface(user_id,in_id,charge_type,province_code,is_return) values(" +
					"'"+suiForm.getUser_id()+"','"+suiForm.getIn_id()+"','"+suiForm.getCharge_type()+"','"+suiForm.getProvince_code()+"','"+suiForm.getIs_return()+"')");
			dbService.update(sql3.toString());
			return 0;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	/**
	 * ɾ����ֵ�ӿ�
	 * @param bpForm 
	 * @return bool
	 * @throws Exception
	 */
	public int del(SysUserInterfaceForm sitForm) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select user_id from sys_user_interface where id="+sitForm.getIds()[0]);
			int result = dbService.getInt(sql.toString());
			
			StringBuffer sql1 = new StringBuffer();	
			sql1.append("delete from sys_user_interface where id='"+sitForm.getIds()[0]+"';");
			dbService.update(sql1.toString());
			return result;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	 /**
	 * ��ȡ�û���ֵ�ӿ�(����)
	 * @return �����Ϣ
	 * @throws Exception
	 */
	public SysUserInterfaceForm getUserInterfaceInfo(String siId) throws Exception {
        DBService db = new DBService(Constants.DBNAME_SCP);
        SysUserInterfaceForm sitForm = new SysUserInterfaceForm();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select user_id,in_id,charge_type,province_code,is_return ")
                    .append(" from sys_user_interface ")
                    .append(" where id=? ");

            String[] params = { siId };
            String[] fields = { "user_id","in_id", "charge_type","province_code","is_return"};
            
            db.populate(sitForm, fields, sql.toString(), params);

            return sitForm;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
     * �����û���ֵ�ӿ�
     * @param sitForm �û���ֵ�ӿ�
     * @return int
     * @throws SQLException
     */
    public int update(SysUserInterfaceForm sitForm) throws SQLException
    {
        StringBuffer sql = new StringBuffer();
        sql.append("update sys_user_interface set");
        sql.append(" in_id='"+sitForm.getIn_id()+"'");
        sql.append(" ,charge_type='"+sitForm.getCharge_type()+"'");
        sql.append(" ,province_code='"+sitForm.getProvince_code()+"'");
        sql.append(" ,is_return='"+sitForm.getIs_return()+"'");
        sql.append(" where");
        sql.append(" id='"+sitForm.getId()+"'");
        return DBToolSCP.update(sql.toString());
    }
    

		/**
		 * �жϳ�������
		 * @param userid
		 * @return
		 */
    public static int  revertCount(String userid)
    {
    	int count=0;
    	String selectStr="select count(*) from wlt_revertlimit where userno='"+userid+"'  and date="+Tools.getMonth2();
    	try {
			 count=DBToolSCP.getInt(selectStr);
		} catch (SQLException e) {
			e.printStackTrace();
			return 3;
		}
		if(count>=10)
			return 2;
		else
        return 0;
    }
    
	/**
	 * ��������
	 * @param userid
	 * @return
	 * @throws SQLException 
	 */
public static void  revertCountAdd(String userid) throws SQLException
{
	int count=0;
	String selectStr="insert into wlt_revertlimit(userid,date) values('"+userid+"','"+Tools.getMonth2()+"')";
		 count=DBToolSCP.update(selectStr);
}
    
    
    
}

