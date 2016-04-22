package com.wlt.webm.business.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.form.BiSanForm;
import com.wlt.webm.business.form.SysCommissionForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.pccommon.Constants;
import com.wlt.webm.rights.form.SysRoleForm;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.util.PageAttribute;


/**
 * 区域管理
 */
public class SysCommission
{
	/**
	 * 根据节点编号获取所有在此节点下的节点信息,且为此节点所有子节点  
	 * @return 节点信息｛sa_id,sa_name,sa_pid,sa_zone｝
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
	 * 根据区域信息列表与父节点编号获得子区域信息列表 
	 * @param areaList  区域列表
	 * @param parentid  父节点编号
	 * @return List(String)区域信息列表
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
	 * 根据节点编号获取所有在此节点下的节点信息,且为此节点所有子节点  
	 * @return 节点信息｛sa_id,sa_name,sa_pid,sa_zone｝
	 * @throws Exception
	 */
	public List listCommissiongroup() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select a.sg_id,a.sc_name,b.sa_name, case when a.sg_defaut=1 then '默认' else '非默认' end  ")
			.append("from sys_commissiongroup a,sys_area b ")
			.append("where a.sa_id=b.sa_id and srt_id=1 ")
			.append("order by a.sg_id");
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	/**
	 * 根据节点编号获取所有在此节点下的节点信息,且为此节点所有子节点  
	 * @return 节点信息｛sa_id,sa_name,sa_pid,sa_zone｝
	 * @throws Exception
	 */
	public List listSc(String sc_name) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select * ")
			.append("from sys_commissiongroup ")
			.append("where sc_name = '"+sc_name+"'");
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	/**
	 * 根据节点编号获取所有在此节点下的节点信息,且为此节点所有子节点  
	 * @return 节点信息｛sa_id,sa_name,sa_pid,sa_zone｝
	 * @throws Exception
	 */
	public List listCommissionjkgroup() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select a.sg_id,a.sc_name,b.sa_name, case when a.sg_defaut=1 then '默认' else '非默认' end ")
			.append("from sys_commissiongroup a,sys_area b ")
			.append("where a.sa_id=b.sa_id and srt_id=4 ")
			.append("order by a.sg_id");
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	/**
	 * 根据节点编号获取所有在此节点下的节点信息,且为此节点所有子节点  
	 * @return 节点信息｛sa_id,sa_name,sa_pid,sa_zone｝
	 * @throws Exception
	 */
	public List listCommissionjtgroup() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select a.sg_id,a.sc_name,b.sa_name, case when a.sg_defaut=1 then '默认' else '非默认' end ")
			.append("from sys_commissiongroup a,sys_area b ")
			.append("where a.sa_id=b.sa_id and srt_id=5 ")
			.append("order by a.sg_id");
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	/**
	 * 根据节点编号获取所有在此节点下的节点信息,且为此节点所有子节点  
	 * @return 节点信息｛sa_id,sa_name,sa_pid,sa_zone｝
	 * @throws Exception
	 */
	public List listCommissiontcgroup() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select a.sg_id,a.sc_name,b.sa_name, case when a.sg_defaut=1 then '默认' else '非默认' end ")
			.append("from sys_commissiongroup a,sys_area b ")
			.append("where a.sa_id=b.sa_id and srt_id=2 ")
			.append("order by a.sg_id");
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	/**
	 * 根据节点编号获取所有在此节点下的节点信息,且为此节点所有子节点  
	 * @return 节点信息｛sa_id,sa_name,sa_pid,sa_zone｝
	 * @throws Exception
	 */
	public List listCommissionsgroup() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select a.sg_id,a.sc_name,b.sa_name, case when a.sg_defaut=1 then '默认' else '非默认' end ")
			.append("from sys_commissiongroup a,sys_area b ")
			.append("where a.sa_id=b.sa_id and srt_id=3 ")
			.append("order by a.sg_id");
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	/**
	 * 根据区域查询佣金组  
	 * @param saId
	 * @return List
	 * @throws Exception
	 */
	public List listCommission(String saId,String sType) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select sg_id,sc_name from sys_commissiongroup where srt_id="+sType+" and sa_id="+saId);
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	/**
	 * 根据区域查询默认佣金组  
	 * @param saId
	 * @return List
	 * @throws Exception
	 */
	public List listDefaultCommission(String saId,String sType) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select sg_id,sc_name from sys_commissiongroup where sg_defaut=1 and srt_id="+sType+" and sa_id="+saId);
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	/**
	 * 根据节点编号获取所有在此节点下的节点信息,且为此节点所有子节点  
	 * @param sg_id 
	 * @return 节点信息｛sa_id,sa_name,sa_pid,sa_zone｝
	 * @throws Exception
	 */
	public List listCommissionmx(String sg_id) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select a.sc_id, case a.sc_tradertype when 1 then '移动' when 2 then '联通' when 3 then '电信' else '所有' end, ")
			.append("concat(concat(a.sc_traderbegin,'-'),a.sc_traderend),concat(a.sc_traderpercent,'%') from sys_commission a ")
			.append("where sg_id='"+sg_id+"'");
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	/**
	 * 根据节点编号获取所有在此节点下的节点信息,且为此节点所有子节点  
	 * @param sg_id 
	 * @return 节点信息｛sa_id,sa_name,sa_pid,sa_zone｝
	 * @throws Exception
	 */
	public List listCommissionsmx(String sg_id) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select a.sc_id, case a.sc_tradertype when 1 then '移动' when 2 then '联通' when 3 then '电信'  else '所有' end, ")
			.append(" concat(a.sc_traderpercent,'%') from sys_commission a ")
			.append("where sg_id='"+sg_id+"'");
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	/**
	 * 根据节点编号获取所有在此节点下的节点信息,且为此节点所有子节点  
	 * @param sg_id 
	 * @return 节点信息｛sa_id,sa_name,sa_pid,sa_zone｝
	 * @throws Exception
	 */
	public List listCommissiontcmx(String sg_id) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select a.sc_id,case a.sc_level when 2 then '第一级' when 3 then '第二级' else '默认级别' end, ")
			.append("concat(concat(a.sc_traderbegin,'-'),a.sc_traderend),concat(a.sc_traderpercent,'%') from sys_commission a ")
			.append("where sg_id='"+sg_id+"'");
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	/**
	 * 根据节点编号获取下级区域
	 * @param sr_id 
	 * @return 节点信息｛sa_id,sa_name,sa_pid,sa_zone｝
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
	 * 获取角色类型
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
	 * 获取所有角色
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
	 * 根据节点编号获取下级区域
	 * @param scForm 
	 * @return 节点信息
	 * @throws Exception
	 */
	public int add(SysCommissionForm scForm) throws Exception {
		DBService dbService = new DBService();
		try {
			dbService.setAutoCommit(false);
			if(null != scForm.getSg_defaut() && "1".equals(scForm.getSg_defaut())){
				String sql = "update sys_commissiongroup set sg_defaut = 2 where srt_id='"+scForm.getSc_type()+"' and sa_id = "+scForm.getWltarea();
				dbService.update(sql);
			}
			StringBuffer sql3 = new StringBuffer();	
			sql3.append("insert into sys_commissiongroup(sa_id,srt_id,sg_defaut,sc_name) values('"+scForm.getWltarea()+"','"+scForm.getSc_type()+"','"+scForm.getSg_defaut()+"','"+scForm.getSc_name()+"')");		
			dbService.update(sql3.toString());
			dbService.commit();
        } catch (Exception ex) {
        	dbService.rollback();
            throw ex;
        } finally {
        	dbService.close();
        }
		return 0;
	}
	
	
	/**
	 * 根据节点编号获取下级区域
	 * @param scForm 
	 * @return 节点信息
	 * @throws Exception
	 */
	public int addmxa(SysCommissionForm scForm) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql3 = new StringBuffer();	
			sql3.append("insert into sys_commission(sg_id,sc_tradertype,sc_traderbegin,sc_traderend,sc_traderpercent,sc_other) values('"+scForm.getSg_id()+"','"+scForm.getSc_tradertype()+"','"+scForm.getSc_traderbegin()+"','"+scForm.getSc_traderend()+"','"+scForm.getSc_traderpercent()+"',"+scForm.getSc_other()+")");		
			System.out.println(sql3.toString());
			dbService.update(sql3.toString());
			return 0;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	/**
	 * 根据节点编号获取下级区域
	 * @param scForm 
	 * @return 节点信息
	 * @throws Exception
	 */
	public int addtcmx(SysCommissionForm scForm) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql3 = new StringBuffer();	
			sql3.append("insert into sys_commission(sg_id,sc_level,sc_traderbegin,sc_traderend,sc_traderpercent) values('"+scForm.getSg_id()+"','"+scForm.getSc_level()+"','"+scForm.getSc_traderbegin()+"','"+scForm.getSc_traderend()+"','"+scForm.getSc_traderpercent()+"')");		
			System.out.println(sql3.toString());
			dbService.update(sql3.toString());
			return 0;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	/**
	 * 根据节点编号获取下级区域
	 * @param scForm 
	 * @return 节点信息
	 * @throws Exception
	 */
	public int addsmx(SysCommissionForm scForm) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql3 = new StringBuffer();	
			sql3.append("insert into sys_commission(sg_id,sc_tradertype,sc_traderpercent) values('"+scForm.getSg_id()+"','"+scForm.getSc_tradertype()+"','"+scForm.getSc_traderpercent()+"')");		
			System.out.println(sql3.toString());
			dbService.update(sql3.toString());
			return 0;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	/**
	 * 根据节点编号获取下级区域
	 * @param scForm 
	 * @return 节点信息
	 * @throws Exception
	 */
	public int addjtmx(SysCommissionForm scForm) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql3 = new StringBuffer();	
			sql3.append("insert into sys_commission(sg_id,sc_tradertype,sc_traderbegin,sc_traderend,sc_traderpercent) values('"+scForm.getSg_id()+"','"+scForm.getSc_tradertype()+"','"+scForm.getSc_traderbegin()+"','"+scForm.getSc_traderend()+"','"+scForm.getSc_traderpercent()+"')");		
			System.out.println(sql3.toString());
			dbService.update(sql3.toString());
			return 0;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	/**
	 * 根据节点编号获取下级区域
	 * @param scForm 
	 * @return 节点信息
	 * @throws Exception
	 */
	public int modify(SysCommissionForm scForm) throws Exception {
		DBService dbService = new DBService();
		try {
			dbService.setAutoCommit(false);
			if(null != scForm.getSg_defaut() && "1".equals(scForm.getSg_defaut())){
				String sql1 = "select sa_id from sys_commissiongroup where sg_id="+scForm.getSg_id();
				int saId = dbService.getInt(sql1);
				String sql = "update sys_commissiongroup set sg_defaut = 2 where srt_id='"+scForm.getSc_type()+"' and sa_id = "+saId;
				dbService.update(sql);
			}
			StringBuffer sql3 = new StringBuffer();	
			sql3.append("update sys_commissiongroup set sc_name='"+scForm.getSc_name()+"',sg_defaut='"+scForm.getSg_defaut()+"' where sg_id='"+scForm.getSg_id()+"'");
			dbService.update(sql3.toString());
			dbService.commit();
        } catch (Exception ex) {
        	dbService.rollback();
            throw ex;
        } finally {
        	dbService.close();
        }
		return 0;
	}
	
	/**
	 * 根据节点编号获取下级区域
	 * @param roleForm 
	 * @return 节点信息
	 * @throws Exception
	 */
	public boolean del(SysCommissionForm scForm) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql1 = new StringBuffer();	
			sql1.append("delete from sys_commissiongroup where sg_id='"+scForm.getIds()[0]+"';");
			dbService.update(sql1.toString());
			return true;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	/**
	 * 根据节点编号获取下级区域
	 * @param roleForm 
	 * @return 节点信息
	 * @throws Exception
	 */
	public boolean delmx(SysCommissionForm scForm) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql1 = new StringBuffer();	
			sql1.append("delete from sys_commission where sc_id='"+scForm.getIds()[0]+"';");
			dbService.update(sql1.toString());
			return true;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	 /**
	 * 获取佣金组信息(单个)
	 * @return 佣金组信息
	 * @throws Exception
	 */
	public SysCommissionForm getCommissionInfo(String scId) throws Exception {
        DBService db = new DBService(Constants.DBNAME_SCP);
        SysCommissionForm scForm = new SysCommissionForm();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select sg_defaut,sc_name")
                    .append(" from sys_commissiongroup ")
                    .append(" where sg_id=? ");

            String[] params = { scId };
            String[] fields = { "sg_defaut","sc_name"};
            
            db.populate(scForm, fields, sql.toString(), params);

            return scForm;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	
	/**
	 * 获得总数
	 * @param roleForm 
	 * @return 节点信息
	 * @throws Exception 
	 */
	public int employSum(String st,BiSanForm userForm) throws Exception {
		String name="sys_employ"+st;
		String sql="select count(*) from "+name +" where 1=1 ";
		if(userForm.getCm_one()!=null && !"".equals(userForm.getCm_one()))
		{
			sql=sql+" and pid='"+userForm.getCm_one()+"' ";
		}
		if(userForm.getYunyingshang()!=null && !"".equals(userForm.getYunyingshang()))
		{
			sql=sql+" and type='"+userForm.getYunyingshang()+"' ";
		}
		if(userForm.getMiane()!=null && !"".equals(userForm.getMiane()))
		{
			sql=sql+" and cm_id='"+userForm.getMiane()+"' ";
		}

		DBService dbService = null;
		try {
			dbService = new DBService();
			return dbService.getInt(sql);
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	/**阶梯列表总行数
	 * @param userForm
	 * @return
	 */
	public int getOneAgentCount(BiSanForm userForm)
	{
		String sql="SELECT COUNT(*) con  FROM sys_oneagent a ,sys_setupgroups b WHERE a.groupsID=b.groupsID ";
		if(userForm.getzName()!=null && !"".equals(userForm.getzName()) && !"null".equals(userForm.getzName()))
		{
			sql=sql+" AND a.groupsID="+userForm.getzName();
		}
		DBService dbService = null;
		try {
			dbService = new DBService();
			return dbService.getInt(sql);
        } catch (Exception ex) {
            Log.error("获取阶梯总行数异常，，，"+ex);
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
       return -1;
	}
	
	/**获取阶梯列表
	 * @param page
	 * @param st
	 * @param userForm
	 * @return null
	 */
	public List getOneAgentList(PageAttribute page,BiSanForm userForm){
		String sql = "SELECT a.groupsID,b.groupname,a.tradetype,CONCAT(format(a.monbegin/1000,1),' ~ ',format(a.monend/1000,1)) AS fee,a.percent,a.setupID FROM sys_oneagent a ,sys_setupgroups b WHERE a.groupsID=b.groupsID  ";
		if(userForm.getzName()!=null && !"".equals(userForm.getzName()) && !"null".equals(userForm.getzName()))
		{
			sql=sql+" AND a.groupsID="+userForm.getzName();
		}
		sql=sql+" ORDER BY a.groupsID,a.setupID ASC  limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize();
		List list=new ArrayList();
		DBService dbService = null;
		try {
			dbService = new DBService();
			list = dbService.getList(sql,null);
		} catch (Exception e) {
			 Log.error("获取阶梯总列表数据异常，，，"+e);
		}finally{
			dbService.close();
		}
		return list;
	}
	
	/**阶梯配置总行数
	 * @param userForm
	 * @return
	 */
	public int getconfigureCount(BiSanForm userForm)
	{
		String sql="SELECT count(*) con FROM sys_oneagentmaps m,sys_user u,sys_setupgroups ss WHERE m.groupsID=ss.groupsID AND m.userno=u.user_no ";
		if(userForm.getzName()!=null && !"".equals(userForm.getzName()) && !"null".equals(userForm.getzName()))
		{
			sql=sql+" AND m.groupsID="+userForm.getzName();
		}
		DBService dbService = null;
		try {
			dbService = new DBService();
			return dbService.getInt(sql);
        } catch (Exception ex) {
            Log.error("阶梯配置总行数异常，，，"+ex);
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
       return -1;
	}
	
	/**阶梯配置列表
	 * @param page
	 * @param st
	 * @param userForm
	 * @return null
	 */
	public List getconfigureList(PageAttribute page,BiSanForm userForm){
		String sql = "SELECT ss.groupname,u.user_no,u.user_ename,u.user_login,m.groupsID FROM sys_oneagentmaps m,sys_user u,sys_setupgroups ss WHERE m.groupsID=ss.groupsID AND m.userno=u.user_no ";
		if(userForm.getzName()!=null && !"".equals(userForm.getzName()) && !"null".equals(userForm.getzName()))
		{
			sql=sql+" AND m.groupsID="+userForm.getzName();
		}
		sql=sql+" ORDER BY m.groupsID ASC  limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize();
		List list=new ArrayList();
		DBService dbService = null;
		try {
			dbService = new DBService();
			list = dbService.getList(sql,null);
		} catch (Exception e) {
			 Log.error("阶梯配置列表数据异常，，，"+e);
		}finally{
			dbService.close();
		}
		return list;
	}
	/**
	 * 获得总数
	 * @param roleForm 
	 * @return 节点信息
	 * @throws Exception
	 */
	public int tpemploySum(BiSanForm userForm) throws Exception {
		String sql="SELECT COUNT(*) FROM sys_tpemploy a,sys_valueRange b,sys_area c WHERE a.cm_id=b.cm_id AND a.pid=c.sa_id  ";
		if(userForm.getCm_one()!=null && !"".equals(userForm.getCm_one()))
		{
			sql=sql+" and a.pid='"+userForm.getCm_one()+"' ";
		}
		if(userForm.getYunyingshang()!=null && !"".equals(userForm.getYunyingshang()))
		{
			sql=sql+" and a.type='"+userForm.getYunyingshang()+"' ";
		}
		if(userForm.getMiane()!=null && !"".equals(userForm.getMiane()))
		{
			sql=sql+" and a.cm_id='"+userForm.getMiane()+"' ";
		}
		if(userForm.getGroups()!=null && !"".equals(userForm.getGroups()))
		{
			sql=sql+" and a.groups='"+userForm.getGroups()+"' ";
		}
		DBService dbService = null;
		try {
			dbService = new DBService();
			return dbService.getInt(sql);
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	/**
	 * 获得总数
	 * @param roleForm 
	 * @return 节点信息
	 * @throws Exception
	 */
	public int minemploySum() throws Exception {
		DBService dbService = null;
		try {
			dbService = new DBService();
			String name="sys_employ5";
			String sql="select count(*) from "+name;
			return dbService.getInt(sql);
        } catch (Exception ex) {
            throw ex;
        } finally {
        	dbService.close();
        }
	}
	
	/**
	 * 获得佣金明细
	 * @param page
	 * @param st
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEmployinfo(PageAttribute page,String st,BiSanForm userForm){
		String name="sys_employ"+st;
		String sql = "select a.id,c.sa_name,a.type,concat(b.min,'--',b.max),a.value from "+name+" a,sys_valueRange b,sys_area c where " +
				" a.cm_id=b.cm_id and a.pid=c.sa_id ";
		if(userForm.getCm_one()!=null && !"".equals(userForm.getCm_one()))
		{
			sql=sql+" and a.pid='"+userForm.getCm_one()+"' ";
			userForm.setParamUrl(userForm.getParamUrl()+"&cm_one="+userForm.getCm_one());
		}
		if(userForm.getYunyingshang()!=null && !"".equals(userForm.getYunyingshang()))
		{
			sql=sql+" and a.type='"+userForm.getYunyingshang()+"' ";
			userForm.setParamUrl(userForm.getParamUrl()+"&yunyingshang="+userForm.getYunyingshang());
		}
		if(userForm.getMiane()!=null && !"".equals(userForm.getMiane()))
		{
			sql=sql+" and a.cm_id='"+userForm.getMiane()+"' ";
			userForm.setParamUrl(userForm.getParamUrl()+"&miane="+userForm.getMiane());
		}
		sql=sql+"ORDER BY a.id ASC limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize();
		
		ArrayList<String[]> list=new ArrayList<String[]>();
		DBService dbService = null;
		try {
			dbService = new DBService();
			list = (ArrayList<String[]>)dbService.getList(sql);
			for(Object tmp : list){
				String[] temp = (String[])tmp;
				if(null!=temp[2]&&!"".equals(temp[2])){
					if(temp[2].equals("0")){
						temp[2]="电信";
					}else if(temp[2].equals("1")){
						temp[2]="移动";
					}else if(temp[2].equals("2")){
						temp[2]="联通";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dbService.close();
		}
		return list;
	}
	
	/**
	 * 获得佣金明细
	 * @param page
	 * @param st
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List tpgetEmployinfo(PageAttribute page,BiSanForm userForm){
		String name="sys_tpemploy";
		String sql = "select a.id,c.sa_name,a.type,concat(b.min,'--',b.max),a.value,a.groups from "+name+" a,sys_valueRange b,sys_area c where " +
				" a.cm_id=b.cm_id and a.pid=c.sa_id ";
		if(userForm.getCm_one()!=null && !"".equals(userForm.getCm_one()))
		{
			sql=sql+" and a.pid='"+userForm.getCm_one()+"' ";
			userForm.setParamUrl(userForm.getParamUrl()+"&cm_one="+userForm.getCm_one());
		}
		if(userForm.getYunyingshang()!=null && !"".equals(userForm.getYunyingshang()))
		{
			sql=sql+" and a.type='"+userForm.getYunyingshang()+"' ";
			userForm.setParamUrl(userForm.getParamUrl()+"&yunyingshang="+userForm.getYunyingshang());
		}
		if(userForm.getMiane()!=null && !"".equals(userForm.getMiane()))
		{
			sql=sql+" and a.cm_id='"+userForm.getMiane()+"' ";
			userForm.setParamUrl(userForm.getParamUrl()+"&miane="+userForm.getMiane());
		}
		if(userForm.getGroups()!=null && !"".equals(userForm.getGroups()))
		{
			sql=sql+" and a.groups='"+userForm.getGroups()+"' ";
			userForm.setParamUrl(userForm.getParamUrl()+"&groups="+userForm.getGroups());
		}
		sql=sql+" ORDER BY a.groups,a.type ASC limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize();
		ArrayList<String[]> list=new ArrayList<String[]>();
		DBService dbService = null;
		try {
			dbService = new DBService();
			list = (ArrayList<String[]>)dbService.getList(sql);
			for(Object tmp : list){
				String[] temp = (String[])tmp;
				if(null!=temp[2]&&!"".equals(temp[2])){
					if(temp[2].equals("0")){
						temp[2]="电信";
					}else if(temp[2].equals("1")){
						temp[2]="移动";
					}else if(temp[2].equals("2")){
						temp[2]="联通";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dbService.close();
		}
		return list;
	}
	
	/**
	 * 获得佣金明细
	 * @param page
	 * @param st
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getminEmployinfo(PageAttribute page){
		String name="sys_employ5";
		String sql = "select a.id,c.sa_name,a.type,concat(b.min,'--',b.max),a.value,a.value1 from "+name+" a,sys_valueRange b,sys_area c where " +
				" a.cm_id=b.cm_id and a.pid=c.sa_id ORDER BY a.id ASC limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize();
		ArrayList<String[]> list=new ArrayList<String[]>();
		DBService dbService = null;
		try {
			dbService = new DBService();
			list = (ArrayList<String[]>)dbService.getList(sql);
			for(Object tmp : list){
				String[] temp = (String[])tmp;
				if(null!=temp[2]&&!"".equals(temp[2])){
					if(temp[2].equals("0")){
						temp[2]="电信";
					}else if(temp[2].equals("1")){
						temp[2]="移动";
					}else if(temp[2].equals("2")){
						temp[2]="联通";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			dbService.close();
		}
		return list;
	}
	
	/**
	 * 获得增值业务佣金明细
	 * @return
	 */
	public List getRebatinfo(){
	    String sf="select a.id,b.name,a.value,a.flag from sys_employ3 a,wht_service b where a.code=b.code and b.flag=1";
		ArrayList<String[]> list=new ArrayList<String[]>();
		DBService dbService = null;
		try {
			dbService = new DBService();
			list = (ArrayList<String[]>)dbService.getList(sf);
			for(Object tmp : list){
				String[] temp = (String[])tmp;
				if(null!=temp[3]&&!"".equals(temp[3])){
					if(temp[3].equals("0")){
						temp[3]="非直营";
					}else{
						temp[3]="直营";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			dbService.close();
		}
		return list;
	}
	
	
	
	/**
	 * 获得增值业务佣金明细
	 * @return
	 */
	public List getMinEmploy(){
	    String sf="select a.id,b.name,a.value,a.flag from sys_employ3 a,wht_service b where a.code=b.code and b.flag=1";
		ArrayList<String[]> list=new ArrayList<String[]>();
		DBService dbService = null;
		try {
			dbService = new DBService();
			list = (ArrayList<String[]>)dbService.getList(sf);
			for(Object tmp : list){
				String[] temp = (String[])tmp;
				if(null!=temp[3]&&!"".equals(temp[3])){
					if(temp[3].equals("0")){
						temp[3]="非直营";
					}else{
						temp[3]="直营";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			dbService.close();
		}
		return list;
	}
	
	/**
	 * 获得佣金组总数
	 * @param st 
	 * @param userForm 
	 * @return 节点信息
	 * @throws Exception 
	 */
	public int setupGpSum(String st,BiSanForm userForm) throws Exception {
		String name="sys_setupgroups";
		String sql="select count(*) from "+name +" where 1=1 ";
		DBService dbService = null;
		try {
			dbService = new DBService();
			return dbService.getInt(sql);
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=dbService){
        	dbService.close();
        	}
        }
	}
	
	/**
	 * 获得阶梯组明细
	 * @param page
	 * @param st
	 * @param userForm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSetupGpinfo(PageAttribute page,String st,BiSanForm userForm){
		String sql = "SELECT groupsID,typenum, (CASE WHEN typenum=0 THEN '一级阶梯组' ELSE '未知' END) AS NAME,groupname,isdefaut,(CASE WHEN isdefaut=0 THEN '否' ELSE '是' END) AS NAME1,TIME FROM sys_setupgroups WHERE 1=1 ";
		sql=sql+" ORDER BY groupsID ASC limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize();
		ArrayList<String[]> list=new ArrayList<String[]>();
		DBService dbService = null;
		try {
			dbService = new DBService();
			list = (ArrayList<String[]>)dbService.getList(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null!=dbService){
				dbService.close();
			}
		}
		return list;
	}

	/**
	 * 接口商 交通罚款佣金组 
	 * @param pid
	 * @param cartype
	 * @param groups
	 * @return int
	 */
	public int JFCount(String pid,String cartype,String groups){
		String sql="SELECT COUNT(jf.id) con FROM sys_tpemploy_jf jf,sys_area ar,sys_car_type car WHERE jf.pid=ar.sa_id AND jf.carid=car.id";
		if(pid!=null && !"".equals(pid)){
			sql=sql+" and jf.pid="+pid;
		}
		if(cartype!=null && !"".equals(cartype)){
			sql=sql+" and jf.carid="+cartype;
		}
		if(groups!=null && !"".equals(groups)){
			sql=sql+" and jf.groups='"+groups+"'";
		}
		
		DBService dbService = null;
		try {
			dbService = new DBService();
			return dbService.getInt(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}finally{
			if(dbService!=null){
				dbService.close();
				dbService=null;
			}
		}
	}
	/**
	 * 接口商 交通罚款佣金组 
	 * @param pid
	 * @param cartype
	 * @param groups
	 * @param index
	 * @param pagesize
	 * @return list
	 */
	public List JFList(String pid,String cartype,String groups,int index,int pagesize,int bool){
		String sql="SELECT ar.sa_name,car.carName,jf.val,jf.groups,jf.id FROM sys_tpemploy_jf jf,sys_area ar,sys_car_type car WHERE jf.pid=ar.sa_id AND jf.carid=car.id";
		if(pid!=null && !"".equals(pid)){
			sql=sql+" and jf.pid="+pid;
		}
		if(cartype!=null && !"".equals(cartype)){
			sql=sql+" and jf.carid="+cartype;
		}
		if(groups!=null && !"".equals(groups)){
			sql=sql+" and jf.groups='"+groups+"'";
		}
		sql=sql+"  order by jf.id asc ";
		if(bool==1){
			sql=sql+"  limit "+(index-1)*pagesize+" , "+pagesize;
		}
		DBService dbService = null;
		try {
			dbService = new DBService();
			return dbService.getList(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(dbService!=null){
				dbService.close();
				dbService=null;
			}
		}
	}
	/**
	 * 交通罚款根据id 查询记录
	 * @param id
	 * @return list
	 */
	public List JFUpdateID(String id){
		String sql="SELECT ar.sa_name,jf.pid,car.carName,jf.carid,jf.val,jf.groups,jf.id FROM sys_tpemploy_jf jf,sys_area ar,sys_car_type car WHERE jf.pid=ar.sa_id AND jf.carid=car.id  AND jf.id="+id;
		DBService dbService = null;
		try {
			dbService = new DBService();
			return dbService.getList(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(dbService!=null){
				dbService.close();
				dbService=null;
			}
		}
	}
	/**
	 * 根据id 修改 佣金
	 * @param val
	 * @param id
	 * @return
	 */
	public boolean JFUpdate(String val,String id){
		String sql="UPDATE sys_tpemploy_jf SET val='"+val+"' WHERE id="+id;
		DBService dbService = null;
		try {
			dbService = new DBService();
			return dbService.update(sql)>0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			if(dbService!=null){
				dbService.close();
				dbService=null;
			}
		}
	}
}

