package com.wlt.webm.rights.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wlt.webm.db.DBService;
import com.wlt.webm.pccommon.Constants;
import com.wlt.webm.rights.form.ReversalForm;
import com.wlt.webm.rights.form.SysLoginUserForm;
import com.wlt.webm.rights.form.SysRoleForm;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.util.PageAttribute;


/**
 * ��������
 */
public class Reversal
{
	
	/**
	 * ���������б�����
	 * @param reversalForm 
	 * @return
	 * @throws Exception 
	 */
	public int ReversalSum(ReversalForm reversalForm) throws Exception {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String name="sys_reversalcount";
			String sql="select count(*) from "+name+" where 1=1 ";
			if(reversalForm.getTradeTypes()!=null && !"".equals(reversalForm.getTradeTypes()))
			{
				sql=sql+" AND tradetype='"+reversalForm.getTradeTypes()+"'";
			}
			return dbService.getInt(sql);
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	/**
	 * ���������б�
	 * @param page
	 * @return
	 */
	public List getReversalinfo(ReversalForm reversalForm,PageAttribute page) {
		String name="sys_reversalcount";
		String sql = "select user_login,user_no,reversalcount,tradetype,id from "+name+" where 1=1 ";
		if(reversalForm.getTradeTypes()!=null && !"".equals(reversalForm.getTradeTypes()))
		{
			sql=sql+" AND tradetype='"+reversalForm.getTradeTypes()+"'";
		}
		sql=sql+" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize();
		ArrayList<String[]> list=new ArrayList<String[]>();
		DBService dbService = null;
		try {
			dbService = new DBService();
			list = (ArrayList<String[]>)dbService.getList(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dbService.close();
		}
		return list;
	}
	public int getReversalCountByUserLogin(String userno, String username,String tradetypes) throws Exception {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String name="sys_reversalcount";
			String sql="select count(*) from "+name+" where user_login='"+username+ "' and user_no='"+userno+"' and tradetype='"+tradetypes+"'";
			return dbService.getInt(sql);
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	/**
	 * ��ӳ�������
	 * @param user_login
	 * @param reversalcount
	 * @param reversalcount 
	 * @return
	 * @throws SQLException 
	 */
	public boolean saveReverSal(String user_login, String user_no, String reversalcount,String tradeTypes) throws SQLException {
		DBService dbService = new DBService();
		try {
			String table = "sys_reversalcount";
			Object[] params = { null, user_login,user_no, reversalcount,tradeTypes };
			dbService.logData(5, params, table);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			dbService.close();
		}
	}
	public boolean updateReverSal(String user_login, String reversalcount,String id) {
		DBService dbService = null;
		String tablename = "sys_reversalcount";
		try {
			dbService = new DBService();
			String sql = "update " + tablename + " set reversalcount=" + reversalcount
					+ " where user_login='" + user_login+"' AND id='"+id+"'";
			dbService.update(sql.toString());
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			if(null!=dbService)
				dbService.close();
		}
	}
	
	/**
	 * ��ȡ��ͨ����������Ϣ
	 * @return
	 * @throws Exception
	 */
	public List getjtfkList() throws Exception {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql="SELECT id,cattype,operationType,interface,(CASE WHEN cattype=1 THEN 'С�ͳ�'  ELSE '��С�ͳ�' END) AS car,(CASE WHEN operationType=1 THEN '��ѯ'  ELSE '�µ�' END) AS op,(CASE WHEN interface=1 THEN '��������' WHEN interface=2 THEN '���°�����' WHEN interface=0 THEN '���°�����' END) AS infer FROM wht_carInterface";
			return dbService.getList(sql);
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	

    /**
     * ����������Ϣ
     * @param car
     * @param op
     * @param qd
     * @return
     * @throws SQLException
     */
	public boolean addJtfk(String car, String op, String qd) throws SQLException {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql="select id from wht_carInterface where cattype=" + car+" and operationType="+op;
			if(dbService.getInt(sql)!=0){
				return false;
			}
			String sql1="select interface from wht_carInterface where cattype=" + car+" and operationType!="+op;
		    if(dbService.hasData(sql1)){
		    	int a=dbService.getInt(sql1);
		    	if(a==0||a==Integer.parseInt(qd)){
		    	}else{
		    		return false;
		    	}
		    }
			
			String table = "wht_carInterface";
			Object[] params = { null, car, qd,op,null,null };
			dbService.logData(6, params, table);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			dbService.close();
		}
	}
	
	/**
	 * �޸�������Ϣ
	 * @param car
	 * @param op
	 * @param qd
	 * @return
	 */
	public boolean updateJtfk(String car, String op,String qd) {
		DBService dbService = null;
		String tablename = "wht_carInterface";
		try {
			dbService = new DBService();
//			String sql1="select interface from wht_carInterface where cattype=" + car+" and operationType!="+op;
//		    if(dbService.hasData(sql1)){
//		    	int a=dbService.getInt(sql1);
//		    	if(a==0||a==Integer.parseInt(qd)){
//		    	}else{
//		    		return false;
//		    	}
//		    }
			String sql = "update " + tablename + " set interface=" + qd
					+ " where cattype=" + car+" AND operationType="+op;
			dbService.update(sql.toString());
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			if(null!=dbService)
				dbService.close();
		}
	}
	
	/**
	 * ������Ʒ��
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getFlowinfo(ReversalForm reversalForm,PageAttribute page) {
		String sql="SELECT id,NAME,price,TYPE FROM wht_flowprice WHERE 1=1";
		if(reversalForm.getTradeTypes()!=null && !"".equals(reversalForm.getTradeTypes()))
		{
			sql=sql+" and type='"+reversalForm.getTradeTypes()+"'";
		}
		sql=sql+" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize();
		ArrayList<String[]> list=new ArrayList<String[]>();
		DBService dbService = null;
		try {
			dbService = new DBService();
			list = (ArrayList<String[]>)dbService.getList(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dbService.close();
		}
		return list;
	}
	
	/**
	 * ���������Ʒ�ͼ۸�
	 * @param type
	 * @param price
	 * @param name
	 * @return
	 */
	public boolean saveFlowprice(String type,String price,String name){
		DBService dbService =null;
		try {
			dbService= new DBService();
			String table = "wht_flowprice";
			Object[] params = { null, name,price,type,null,null };
			dbService.logData(6, params, table);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			dbService.close();
		}
	}
	
	/**
	 * ɾ��������Ʒ
	 * @param id
	 * @return true false
	 */
	public boolean saveFlowprice(String id){
		DBService dbService =null;
		try {
			dbService= new DBService();
			dbService.update("DELETE FROM wht_flowprice WHERE id="+id);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			dbService.close();
		}
	}
}

