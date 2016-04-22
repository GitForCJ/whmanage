package com.wlt.webm.business.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wlt.webm.business.form.SysCommissionForm;
import com.wlt.webm.business.form.SysRebateForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.db.DBToolSCP;
import com.wlt.webm.pccommon.Constants;
import com.wlt.webm.rights.form.SysRoleForm;
import com.wlt.webm.rights.form.SysUserForm;


/**
 * �������
 */
public class SysRebate
{
	/**
	 * ���ݽڵ��Ż�ȡ�����ڴ˽ڵ��µĽڵ���Ϣ,��Ϊ�˽ڵ������ӽڵ�  
	 * @return �ڵ���Ϣ��sa_id,sa_name,sa_pid,sa_zone��
	 * @throws Exception
	 */
	public List getSyslevel() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select sm_id,sm_name,sm_pid,Sm_level from sys_menu");
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	/**
	 * ����������Ϣ�б��븸�ڵ��Ż����������Ϣ�б� 
	 * @param areaList  �����б�
	 * @param parentid  ���ڵ���
	 * @return List(String)������Ϣ�б�
	 * @throws Exception
	 */
	public List getChildroleList(List areaList, String parentid)
			throws Exception {
		List rsList = new ArrayList();
		for (int i = 0; i < areaList.size(); i++) {
			String[] areainfo = (String[]) areaList.get(i);
			if (areainfo[2].trim().equals(parentid)) {
				rsList.add(areainfo);
			}
		}
		return rsList;
	}
	/**
	 * ���ݽڵ��Ż�ȡ�����ڴ˽ڵ��µĽڵ���Ϣ,��Ϊ�˽ڵ������ӽڵ�  
	 * @return �ڵ���Ϣ��sa_id,sa_name,sa_pid,sa_zone��
	 * @throws Exception
	 */
	public List listRebate() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select a.srb_id,b.sr_name,a.sc_tradertype,concat(a.sc_traderpercent,'%') ")
			.append(" from sys_rebate a,sys_role b")
			.append(" where a.srt_id=b.sr_id")
			.append(" order by a.srb_id");
			List list = dbService.getList(sql.toString());
			for(Object tmp : list){
				String[] temp = (String[])tmp;
				if(null != temp[2] && !"".equals(temp[2])){
					if("1".equals(temp[2])){
						temp[2] = "Q��";
					}else if("2".equals(temp[2])){
						temp[2] = "��Ϸ��";
					}else if("3".equals(temp[2])){
						temp[2] = "��ͨ����";
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
	 * ���ݽڵ��Ż�ȡ�¼�����
	 * @param sr_id 
	 * @return �ڵ���Ϣ��sa_id,sa_name,sa_pid,sa_zone��
	 * @throws Exception
	 */
	public List getRoleArea(String sr_id) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			if(!sr_id.equals("1")){
				sql.append("select c.sa_id,c.sa_name from sys_sa_power a,sys_area b,sys_area c where a.sr_id='"+sr_id+"' and a.sa_id=b.sa_id and c.sa_id=b.sa_id;");
			}else{
				sql.append("select c.sa_id,c.sa_name from sys_area c where c.sa_pid=1;");
			}
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	/**
	 * ��ȡ��ɫ����
	 * @param roleid 
	 * @return list
	 * @throws Exception
	 */
	public List getRoleType(String roleid) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select b.sr_type,b.sr_typename from sys_role a,sys_roletype b where a.sr_id="+roleid+" and b.sr_type>a.sr_type;");
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	/**
	 * ��ȡ���н�ɫ
	 * @param roleid 
	 * @return list
	 * @throws Exception
	 */
	public List getRole(String roleid) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select sr_type,sr_typename from sys_role a,sys_roletype b where b.;");
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	/**
	 * ���ݽڵ��Ż�ȡ�¼�����
	 * @param srForm 
	 * @return �ڵ���Ϣ
	 * @throws Exception
	 */
	public int add(SysRebateForm srForm) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql3 = new StringBuffer();	
			
			sql3.append("insert into sys_rebate(srt_id,sc_tradertype,sc_traderpercent) values('"+srForm.getRoleid()+"','"+srForm.getSc_tradertype()+"','"+srForm.getSc_traderpercent()+"')");
			dbService.update(sql3.toString());
			return 0;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	/**
	 * ���ݽڵ��Ż�ȡ�¼�����
	 * @param roleForm 
	 * @return �ڵ���Ϣ
	 * @throws Exception
	 */
	public int modifyMenu(SysRoleForm roleForm) throws Exception {
		DBService dbService = new DBService();
		Connection cn = dbService.getConn();
		String[] modules = roleForm.getModules();
		dbService.setAutoCommit(false);
		StringBuffer sql1 = new StringBuffer();	
		sql1.append("delete from sys_sm_power where sr_id='"+roleForm.getRoleid()+"';");
		dbService.update(sql1.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("insert into sys_sm_power(sr_id,sm_id) values(?,?)");
		PreparedStatement ps = cn.prepareStatement(sql.toString());
		for (int i = 0; i < modules.length; i++) {
			ps.setString(1, roleForm.getRoleid());
			ps.setString(2, modules[i]);
			ps.addBatch();
		}
		ps.executeBatch();
		dbService.commit();
		return 0;
	}
	
	/**
	 * ���ݽڵ��Ż�ȡ�¼�����
	 * @param srForm 
	 * @return �ڵ���Ϣ
	 * @throws Exception
	 */
	public boolean del(SysRebateForm srForm) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql1 = new StringBuffer();	
			sql1.append("delete from sys_rebate where srb_id='"+srForm.getIds()[0]+"';");
			dbService.update(sql1.toString());
			return true;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	 /**
	 * ��ȡ�ۿ�����Ϣ(����)
	 * @return �ۿ�����Ϣ
	 * @throws Exception
	 */
	public SysRebateForm getRebateInfo(String srid) throws Exception {
        DBService db = new DBService(Constants.DBNAME_SCP);
        SysRebateForm rebateForm = new SysRebateForm();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select a.srb_id, a.srt_id,a.sc_tradertype,a.sc_traderpercent")
                    .append(" from sys_rebate a ")
                    .append(" where a.srb_id=? ");

            String[] params = { srid };
            String[] fields = { "srid","roleid", "sc_tradertype" ,"sc_traderpercent"};
            
            db.populate(rebateForm, fields, sql.toString(), params);

            return rebateForm;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
	 * ��ȡ�ۿ�����Ϣ(����)
	 * @return �ۿ�����Ϣ
	 * @throws Exception
	 */
	public SysRebateForm getRebateByRole(String roleid,String tradeType) throws Exception {
        DBService db = new DBService(Constants.DBNAME_SCP);
        SysRebateForm rebateForm = new SysRebateForm();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select a.srb_id, a.srt_id,a.sc_tradertype,a.sc_traderpercent")
                    .append(" from sys_rebate a ")
                    .append(" where sc_tradertype = ?");

            String[] params = { tradeType };
            String[] fields = { "srid","roleid", "sc_tradertype" ,"sc_traderpercent"};
            
            db.populate(rebateForm, fields, sql.toString(), params);

            return rebateForm;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
     * �����ۿ���
     * @param rebateForm �ۿ�����Ϣ
     * @return int
     * @throws SQLException
     */
    public int update(SysRebateForm rebateForm) throws SQLException
    {
        StringBuffer sql = new StringBuffer();
        sql.append("update sys_rebate set");
        sql.append(" srt_id='"+rebateForm.getRoleid()+"'");
        sql.append(" ,sc_tradertype='"+rebateForm.getSc_tradertype()+"'");
        sql.append(" ,sc_traderpercent='"+rebateForm.getSc_traderpercent()+"'");
        sql.append(" where");
        sql.append(" srb_id='"+rebateForm.getSrid()+"'");
        return DBToolSCP.update(sql.toString());
    }
	
}

