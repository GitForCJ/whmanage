package com.wlt.webm.rights.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wlt.webm.db.DBService;
import com.wlt.webm.rights.form.SysRoleForm;
import com.wlt.webm.rights.form.SysUserForm;


/**
 * 区域管理
 */
public class SysRole
{
	/**
	 * 根据节点编号获取所有在此节点下的节点信息,且为此节点所有子节点  
	 * @return 节点信息｛sa_id,sa_name,sa_pid,sa_zone｝
	 * @throws Exception
	 */
	public List getSyslevel() throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append("select sm_id,sm_name,sm_pid,Sm_level from sys_menu");
		List list = dbService.getList(sql.toString());
		return list;
	}
	
	/**
	 * 根据区域信息列表与父节点编号获得子区域信息列表 
	 * @param areaList  区域列表
	 * @param parentid  父节点编号
	 * @return List(String)区域信息列表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
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
	 * @param userForm 
	 * @return 节点信息｛sa_id,sa_name,sa_pid,sa_zone｝
	 * @throws Exception
	 */
	public List listRole(SysUserForm userForm) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
			
			sql.append("  SELECT a.sr_id,a.sr_name,b.sr_typename,d.sa_name,c.sg_default,a.sr_desc FROM sys_role a LEFT JOIN sys_roletype b ON a.sr_type=b.sr_type "+
				 " LEFT JOIN sys_sa_power c ON c.sr_id=a.sr_id "+
				 " LEFT JOIN sys_area d ON c.sa_id=d.sa_id"+
				 " WHERE a.sr_parentid="+userForm.getUser_role());
		List list = dbService.getList(sql.toString());
		for(Object tmp : list){
			String[] temp = (String[])tmp;
			if(null != temp[4] && !"".equals(temp[4])){
				if("0".equals(temp[4])){
					temp[4] = "默认";
				}else if("1".equals(temp[4])){
					temp[4] = "非默认";
				}
			}else {
				temp[4] = "非默认";
			}
		}
		for(Object tmp : list){
			String[] temp = (String[])tmp;
			if(null != temp[5] && !"".equals(temp[5])){
				if("1".equals(temp[5])){
					temp[5] = "一级代理";
				}
			}
		}
		return list;
	}
	
	/**
	 * 根据节点编号获取下级区域
	 * @param sr_id 
	 * @return 节点信息｛sa_id,sa_name,sa_pid,sa_zone｝
	 * @throws Exception
	 */
	public List getRoleArea(String sr_id) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		if(!sr_id.equals("1")){
			sql.append("select c.sa_id,c.sa_name from sys_sa_power a,sys_area b,sys_area c where a.sr_id='"+sr_id+"' and a.sa_id=b.sa_id and c.sa_id=b.sa_id;");
		}else{
			sql.append("select c.sa_id,c.sa_name from sys_area c where c.sa_pid=1;");
		}
		List list = dbService.getList(sql.toString());
		return list;
	}
	
	/**
	 * 获取角色类型
	 * @param roleid 
	 * @return list
	 * @throws Exception
	 */
	public List getRoleType(String roleid) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append("select b.sr_type,b.sr_typename from sys_roletype b ");
		if(null!=roleid&&!"".equals(roleid)){
			if("3".equals(roleid)){
				sql.append("where b.sr_type=3 ");
			}else if("2".equals(roleid)){
				sql.append("where b.sr_type in(2,3) ");
			}else if("0".equals(roleid)){
				sql.append("where b.sr_type in(1,2,3,4) ");
			}else if("1".equals(roleid)){
				sql.append("where b.sr_type in(2,3) ");
			}else if("4".equals(roleid)){
				sql.append("where b.sr_type =4");
			}
		}
		List list = dbService.getList(sql.toString());
		return list;
	}
	
	/**
	 * 获取所有角色
	 * @param roleid 
	 * @return list
	 * @throws Exception
	 */
	public List getRole(String roleid) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append("select sr_id,sr_name from sys_role a where sr_id <> 1");
		List list = dbService.getList(sql.toString());
		return list;
	}
	/**
	 * 根据角色类型获取所有角色
	 * @param rtype 
	 * @return list
	 * @throws Exception
	 */
	public List getRoleByType(String rtype) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append("select sr_id,sr_name from sys_role a where sr_id <> 1 and sr_type ="+rtype);
		List list = dbService.getList(sql.toString());
		return list;
	}
	
	/**
	 * 
	 * @param rtype
	 * @param provice
	 * @return
	 * @throws Exception
	 */
	public List getRoleByTypeAndArea(String rtype,String provice) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append("select a.sr_id,a.sr_name from sys_role a,sys_sa_power b where sr_type ="+rtype
				+" AND a.sr_id=b.sr_id AND b.sa_id='"+provice+"'");
		List list = dbService.getList(sql.toString());
		return list;
	}
	/**
	 * 根据区域获取所有角色
	 * @param rtype 
	 * @return list
	 * @throws Exception
	 */
	public List getRoleByArea(String aid,String srType) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT b.sr_id,b.sr_name from sys_sa_power a,sys_role b " +
				"WHERE a.sr_id = b.sr_id and  "+srType+" a.sa_id in(SELECT sa_id from sys_area where sa_id = "+aid+" or sa_pid = "+aid+")");
		List list = dbService.getList(sql.toString());
		return list;
	}
	/**
	 * 根据区域获取默认角色
	 * @param rtype 
	 * @return list
	 * @throws Exception
	 */
	public List getRoleByAreaDefault(String aid,String srType) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT b.sr_id,b.sr_name from sys_sa_power a,sys_role b " +
				"WHERE a.sr_id = b.sr_id and a.sg_default = 0 and "+srType+" a.sa_id in(SELECT sa_id from sys_area where sa_id = "+aid+" or sa_pid = "+aid+")");
		List list = dbService.getList(sql.toString());
		return list;
	}
	/**
	 * 根据角色获取菜单
	 * @param roleId 
	 * @return list
	 * @throws Exception
	 */
	public List getMenuByRole(String roleId) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append("select b.sm_link from sys_sm_power a,sys_menu b where a.sm_id = b.sm_id and b.sm_link <> '' and a.sr_id = "+roleId );
		List list = dbService.getList(sql.toString());
		return list;
	}
	/**
	 * 根据角色获取权限
	 * @param roleId 
	 * @return list
	 * @throws Exception
	 */
	public List getMenuIdByRole(String roleId) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append("select a.sm_id from sys_sm_power a where a.sr_id = "+roleId );
		List list = dbService.getList(sql.toString());
		return list;
	}
	/**
	 * 根据节点编号获取下级区域
	 * @param roleForm 
	 * @return 节点信息
	 * @throws Exception
	 */
	public int add(SysRoleForm roleForm) throws Exception {
		DBService dbService = new DBService();
		try {
			dbService.setAutoCommit(false);
			if("0".equals(roleForm.getSg_default())){
				StringBuffer sql4 = new StringBuffer();	
				sql4.append("update sys_sa_power set sg_default = 1 where sa_id = "+roleForm.getWltarea()+" and sr_id in (select sr_id from sys_role where sr_type ="+roleForm.getRoleType()+" )");
				dbService.update(sql4.toString());
			}
			StringBuffer sql1 = new StringBuffer();	
			sql1.append("select sr_id from sys_role where sr_name='"+roleForm.getRolename()+"';");
			boolean flag = dbService.hasData(sql1.toString());
			if(flag){
				return 1;
			}
			StringBuffer sql = new StringBuffer();
			sql.append("insert into sys_role(sr_parentid,sr_name,sr_status,sr_type,sr_desc) values('"+roleForm.getProle()+"','"+roleForm.getRolename()+"','0','"+roleForm.getRoleType()+"','"+roleForm.getOneAgents()+"')");
			dbService.update(sql.toString());
			StringBuffer sql2 = new StringBuffer();	
			sql2.append("select sr_id from sys_role where sr_name='"+roleForm.getRolename()+"';");
			String sr_id = dbService.getString(sql2.toString());
			StringBuffer sql3 = new StringBuffer();	
			if(null == roleForm.getAreaid() || "".equals(roleForm.getAreaid())){
				sql3.append("insert into sys_sa_power(sr_id,sa_id,sg_default) values('"+sr_id+"','"+roleForm.getWltarea()+"',"+roleForm.getSg_default()+")");
			}else{
				sql3.append("insert into sys_sa_power(sr_id,sa_id,sg_default) values('"+sr_id+"','"+roleForm.getAreaid()+"',"+roleForm.getSg_default()+")");
			}
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
	public int modifyMenu(SysRoleForm roleForm) throws Exception {
		DBService dbService = new DBService();
		Connection cn = dbService.getConn();
		String[] modules = roleForm.getModules();
		dbService.setAutoCommit(false);
		StringBuffer sql1 = new StringBuffer();	
		sql1.append("delete from sys_sm_power where sp_type=0 and sr_id='"+roleForm.getRoleid()+"';");
		dbService.update(sql1.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("insert into sys_sm_power(sr_id,sm_id,sp_type) values(?,?,0)");
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
	 * 根据节点编号获取下级区域
	 * @param roleForm 
	 * @return 节点信息
	 * @throws Exception
	 */
	public int modifyMenuFunc(SysRoleForm roleForm) throws Exception {
		DBService dbService = new DBService();
		Connection cn = dbService.getConn();
		String[] modules = roleForm.getModules();
		dbService.setAutoCommit(false);
		StringBuffer sql1 = new StringBuffer();	
		sql1.append("delete from sys_sm_power where sp_type=1 and sr_id='"+roleForm.getRoleid()+"';");
		dbService.update(sql1.toString());
		StringBuffer sql = new StringBuffer();
		sql.append("insert into sys_sm_power(sr_id,sm_id,sp_type) values(?,?,1)");
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
	 * 根据节点编号获取下级区域
	 * @param roleForm 
	 * @return 节点信息
	 * @throws Exception
	 */
	public boolean del(SysRoleForm roleForm) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql1 = new StringBuffer();	
		sql1.append("delete from sys_role where sr_id='"+roleForm.getIds()[0]+"';");
		dbService.update(sql1.toString());
		StringBuffer sql2 = new StringBuffer();	
		sql2.append("delete from sys_sa_power where sr_id='"+roleForm.getIds()[0]+"';");
		dbService.update(sql2.toString());
		return true;
	}
	 /**
	 * 获取角色信息(单个)
	 * @return 角色信息
	 * @throws Exception
	 */
	public SysRoleForm getSysRoleInfo(String roleId) throws Exception {
        DBService db = new DBService();
        SysRoleForm roleForm = new SysRoleForm();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select a.sr_name,b.sg_default,b.sa_id,c.sa_pid")
                    .append(" from sys_role a,sys_sa_power b,sys_area c ")
                    .append(" where a.sr_id = b.sr_id and b.sa_id = c.sa_id and a.sr_id = ?");

            String[] params = { roleId };
            String[] fields = { "rolename","sg_default","wltarea","prole"};
            
            db.populate(roleForm, fields, sql.toString(), params);

            return roleForm;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
	 * 更新角色
	 * @param roleForm 
	 * @return 节点信息
	 * @throws Exception
	 */
	public int update(SysRoleForm roleForm) throws Exception {
		DBService dbService = new DBService();
		try {
			dbService.setAutoCommit(false);
			if("0".equals(roleForm.getSg_default())){
				StringBuffer sql2 = new StringBuffer();	
				sql2.append("select sa_pid from sys_area where sa_id="+roleForm.getWltarea());
				String pid = dbService.getString(sql2.toString());
				String areaId = "";
				if("1".equals(pid)){
					areaId = roleForm.getWltarea();
				}else {
					areaId = pid;
				}
				StringBuffer sql4 = new StringBuffer();	
				sql4.append("update sys_sa_power set sg_default = 1 where sa_id = "+areaId+" and sr_id in (select sr_id from sys_role where sr_type ="+roleForm.getRoleType()+" )");
				dbService.update(sql4.toString());
			}
			StringBuffer sql = new StringBuffer();	
			sql.append("update sys_role set sr_name='"+roleForm.getRolename()+"' where sr_id="+roleForm.getRoleid());
			dbService.update(sql.toString());
			
			StringBuffer sql1 = new StringBuffer();	
			sql1.append("update sys_sa_power set sg_default="+roleForm.getSg_default()+" where sr_id="+roleForm.getRoleid());
			dbService.update(sql1.toString());
			
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
	 * 获取区域列表 
	 * @return 区域列表 
	 * @throws Exception
	 */
	public List getAreaList() throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append(" select sa_id,sa_name ")
		.append(" from sys_area")
		.append(" where sa_pid = 1");
		List list = dbService.getList(sql.toString());
		return list;
	}
	/**
	 * 根据当前登陆人员类型及所属省获取区域列表 
	 * @return 区域列表 
	 * @throws Exception
	 */
	public List getAreaList(String roleType,String site) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		if(roleType.equals("0")){
			sql.append(" select sa_id,sa_name from sys_area where sa_pid = 1");
		}else{
			sql.append(" select sa_id,sa_name from sys_area where sa_id ="+site);
		}
		List list = dbService.getList(sql.toString());
		return list;
	}
	  /**
	   * 获取面额区间信息
	   * @return
	   * @throws Exception
	   */
	public List getValueList() throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
			sql.append("select cm_id ,concat(min,'--',max) from sys_valueRange");
		List list = dbService.getList(sql.toString());
		return list;
	}
	/**
	 * 获取下级区域列表 
	 * @return 区域列表 
	 * @throws Exception
	 */
	public List getDownAreaList(String n) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append(" select sa_id,sa_name ")
		.append(" from sys_area")
		.append(" where sa_pid ="+n);
		List list = dbService.getList(sql.toString());
		return list;
	}
	
	public String getAreaName(String n) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append(" select sa_name ")
		.append(" from sys_area")
		.append(" where sa_id ="+n);
		return  dbService.getString(sql.toString());
	}
	
	/**
	 * 根据省份获取角色编号
	 * @param n
	 * @return
	 * @throws Exception
	 */
	public String getRoleID(String n) throws Exception {
		DBService dbService = new DBService();
	    String sql="SELECT b.sr_id from sys_sa_power a,sys_role b " +
	    		"WHERE a.sr_id = b.sr_id and  b.sr_type = 3"+ 
" and a.sa_id in(SELECT sa_id from sys_area where sa_id ="+n+" or sa_pid ="+n+") AND a.sg_default='0'";
		return  dbService.getString(sql.toString());
	}
	
	/**
	 * 获取mac  google 密钥
	 * @param userno
	 * @return
	 * @throws SQLException 
	 */
	public List getMac(String userno) throws SQLException
	{
		DBService dbService=new DBService();
	    String sql="SELECT m.macaddress,m.googlesign,u.exp3,CASE WHEN m.twodimensionalcode IS NULL THEN 0 ELSE 1 END ab FROM mac m LEFT JOIN sys_user u ON u.user_no=m.userno WHERE m.userno='"+userno+"'";
		return  dbService.getList(sql);
	}
}

