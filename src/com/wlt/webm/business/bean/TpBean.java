package com.wlt.webm.business.bean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wlt.webm.business.form.TPForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.db.DBToolSCP;
import com.wlt.webm.util.PageAttribute;

/**
 * 
* @ClassName: TpBean 
* @Description: 接口商管理 
* @author majian
* @date 2014-5-7 下午03:56:03
 */
public class TpBean {
	/**
	 * 接口商秘钥列表总数
	 * @return
	 * @throws Exception 
	 */
	public int agentsignSum() throws Exception {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql="select count(id) from sys_agentsign";
			return dbService.getInt(sql);
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(dbService!=null)
        		dbService.close();
        }
	}
	
	/**
	 * 加载所有的接口商
	 * @return List;
	 * @throws Exception 
	 */
	public List getInterList(String type) throws Exception {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql="";
			if("1".equals(type))
			{
				 sql="SELECT user_no,user_login,user_ename FROM sys_user u,sys_role r WHERE  u.user_role=r.sr_id AND sr_type=4 ";//AND u.user_no NOT IN (SELECT userno FROM sys_agentsign)
			}
			else
			{
				sql="SELECT user_no,user_login,user_ename FROM sys_user u,sys_role r WHERE  u.user_role=r.sr_id AND sr_type=4 AND u.user_no NOT IN (SELECT userno FROM sys_agentsign)";//AND u.user_no NOT IN (SELECT userno FROM sys_agentsign)
			}
			
			return dbService.getList(sql);
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(dbService!=null)
        		dbService.close();
        }
	}
	/**
	 * 接口商秘钥列表
	 * @param page
	 * @return
	 */
	public List agentsignList(PageAttribute page) {
		String sql = "select s.userno,s.keyvalue,s.keyvalue1,s.ip,s.state,s.interName,u.user_login,s.qbChannel,s.qbCommission,s.caidan,s.groups,s.jfgroups,s.flowgroups,s.returnurl from sys_agentsign s,sys_user u where s.userno=u.user_no limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize();
		ArrayList<String[]> list=new ArrayList<String[]>();
		DBService dbService = null;
		try {
			dbService = new DBService();
			list = (ArrayList<String[]>)dbService.getList(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(dbService!=null)
        		dbService.close();
		}
		return list;
	}
	public int agentsignadd(TPForm tpfrom) throws Exception {
			DBService db = new DBService();
		try {
			String querySql="select userno from sys_agentsign where userno=?";
			if (db.hasData(querySql.toString(), new String[] { tpfrom.getUser_no()})) {
				return -1;
			}
			String sql="insert into sys_agentsign(userno,keyvalue,keyvalue1,ip,state,interName,qbChannel,qbCommission,caidan,groups,jfgroups,flowgroups,returnurl) " +
					"values('"+tpfrom.getUser_no()+"','"+tpfrom.getKeyvalue()+"'" +
					",'"+tpfrom.getKeyvalue1()+"','"+tpfrom.getIp()+"',"+tpfrom.getState()+",'"+tpfrom.getInterName()+"','"+tpfrom.getQbFlag()+"','"+tpfrom.getQbCommission()+"','"+tpfrom.getCaidan()+"','"+tpfrom.getGroups()+"','"+tpfrom.getJfgroups()+"','"+tpfrom.getFlowgroups()+"','"+tpfrom.getReturnurl()+"')";
			db.update(sql.toString());
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(null!=db)
				db.close();
		}
		return 0;
	}
	public int agentsignupdate(TPForm tpfrom) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("update sys_agentsign set");
		sql.append(" keyvalue='" + tpfrom.getKeyvalue()+ "'");
		sql.append(" ,keyvalue1='" + tpfrom.getKeyvalue1() + "'");
		sql.append(" ,ip='" + tpfrom.getIp()+ "'");
		sql.append(" ,state='" + tpfrom.getState() + "'");
		sql.append(" ,interName='"+tpfrom.getInterName()+"'");
		sql.append(" ,qbChannel='"+tpfrom.getQbFlag()+"'");
		sql.append(" ,qbCommission='"+tpfrom.getQbCommission()+"'");
		sql.append(" ,caidan='"+tpfrom.getCaidan()+"'");
		sql.append(" ,groups='"+tpfrom.getGroups()+"'");
		sql.append(" ,jfgroups='"+tpfrom.getJfgroups()+"'");
		sql.append(" ,flowgroups='"+tpfrom.getFlowgroups()+"'");
		sql.append(" ,returnurl='"+tpfrom.getReturnurl()+"'");
		sql.append(" where ");
		sql.append(" userno='" + tpfrom.getUser_no() + "'");
		return DBToolSCP.update(sql.toString());
	}
	
	/**
	 * 获取话费佣金组名称
	 * @return List
	 */
	public List getGroups(){
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql="SELECT groupsName,groupsName FROM interGroups where exp1=1 ";
			return dbService.getList(sql);
        } catch (Exception ex) {
        	ex.printStackTrace();
        } finally {
        	if(dbService!=null)
        		dbService.close();
        }
        return null;
	}
	/**
	 * 获取交通罚款佣金组名称
	 * @return List
	 */
	public List getJTFKGroups(){
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql="SELECT groupsName,groupsName FROM interGroups where exp1=2 ";
			return dbService.getList(sql);
        } catch (Exception ex) {
        	ex.printStackTrace();
        } finally {
        	if(dbService!=null)
        		dbService.close();
        }
        return null;
	}
	/**
	 * 流量佣金组
	 * @return List
	 */
	public List getFlowGroups(){
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql="SELECT groupsName,groupsName FROM interGroups where exp1=3 ";
			return dbService.getList(sql);
        } catch (Exception ex) {
        	ex.printStackTrace();
        } finally {
        	if(dbService!=null)
        		dbService.close();
        }
        return null;
	}
	/**
	 * 获取交通罚款佣金组名称
	 * @return List
	 */
	public List getCarList(){
		DBService dbService = null;
		try {
			dbService = new DBService();
			String sql="SELECT id,carName FROM sys_car_type";
			return dbService.getList(sql);
        } catch (Exception ex) {
        	ex.printStackTrace();
        } finally {
        	if(dbService!=null)
        		dbService.close();
        }
        return null;
	}
	
	/**
	 * 获取一级代理下的 所有代理商
	 * @param userno
	 * @return null
	 */
	public List<String[]> getAgent(String userno){
		String sql="SELECT u.user_id,u.user_no,u.user_login,u.user_ename,cl.open_close FROM (SELECT user_id,user_no,user_login,user_ename FROM sys_user WHERE user_pt=(SELECT user_id FROM sys_user WHERE user_no='"+userno+"')) AS u LEFT JOIN wht_class_a_commission_allocation cl ON u.user_no=cl.agent_userno";
		DBService dbService = null;
		try {
			dbService = new DBService();
			return dbService.getList(sql);
        } catch (Exception ex) {
        	ex.printStackTrace();
        } finally {
        	if(dbService!=null)
        		dbService.close();
        }
        return null;
	}
	/**
	 * 一级代理商关闭开启代理商拥挤配置
	 * @param userno
	 * @param s
	 * @return
	 */
	public boolean OperationAgent(String userno,String s){
		DBService dbService = null;
		try {
			dbService = new DBService();
			dbService.update("DELETE FROM wht_class_a_commission_allocation WHERE agent_userno='"+userno+"'");
			
			if(dbService.update("INSERT INTO wht_class_a_commission_allocation(agent_userno,open_close) VALUES('"+userno+"',"+s+")")>0){
				return true;
			}else{
				return false;
			}
        } catch (Exception ex) {
        	ex.printStackTrace();
        } finally {
        	if(dbService!=null)
        		dbService.close();
        }
        return false;
	}
}
