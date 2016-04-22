package com.wlt.webm.rights.bean;

import java.util.ArrayList;
import java.util.List;

import com.wlt.webm.db.DBService;



/**
 * 区域管理
 */
public class SysArea
{
	/**
	 * 根据节点编号获取虚拟字段
	 * @param sa_id 
	 * @return flag
	 * @throws Exception
	 */
	public String getAreaFlag(String sa_id) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append("select sa_flag from sys_area where sa_id='"+sa_id+"';");
		return dbService.getString(sql.toString());
	}
	/**
	 * 根据节点编号获取所有在此节点下的节点信息,且为此节点所有子节点  
	 * @return 节点信息｛sa_id,sa_name,sa_pid,sa_zone｝
	 * @throws Exception
	 */
	public List getSyslevel() throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append("select sa_id,sa_name,sa_pid,sa_zone,sa_flag from sys_area");
		List list = dbService.getList(sql.toString());
		return list;
	}
	
	/**
	 * 根据节点编号获取下级区域
	 * @param sa_pid 
	 * @return 节点信息｛sa_id,sa_name,sa_pid,sa_zone｝
	 * @throws Exception
	 */
	public List getNextList(String sa_pid) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append("select b.sa_id,b.sa_name from sys_area a,sys_area b where a.sa_pid='"+sa_pid+"' and a.sa_id=b.sa_id;");
		List list = dbService.getList(sql.toString());
		return list;
	}
	
	/**
	 * 根据节点编号获取上级节点  
	 * @param sa_id 
	 * @return 节点信息｛sa_id,sa_name,sa_pid,sa_zone｝
	 * @throws Exception
	 */
	public String getPareacode(String sa_id) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append("select sa_pid from sys_area where sa_id='"+sa_id+"';");
		String sa_pid = dbService.getString(sql.toString());
		return sa_pid;
	}
	
	
	/**
	 * 根据节点编号删除节点  
	 * @param sa_id 
	 * @throws Exception
	 */
	public void delcode(String sa_id) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append("delete from sys_area where sa_id='"+sa_id+"';");
		dbService.update(sql.toString());
	}
	
	/**
	 * 根据节点编号修改节点内容  
	 * @param sa_pid 
	 * @param sa_name 
	 * @param sa_zone 
	 * @throws Exception
	 */
	public void add(String sa_pid,String sa_name,String sa_zone,String sa_flag) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into sys_area(sa_name,sa_zone,sa_pid,sa_flag) values('"+sa_name+"','"+sa_zone+"','"+sa_pid+"','"+sa_flag+"');");
		dbService.update(sql.toString());
	}
	
	/**
	 * 根据节点编号修改节点内容  
	 * @param sa_id 
	 * @param sa_name 
	 * @param sa_zone 
	 * @throws Exception
	 */
	public void modifycode(String sa_id,String sa_name,String sa_zone) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append("update sys_area set sa_name='"+sa_name+"',sa_zone='"+sa_zone+"' where sa_id='"+sa_id+"';");
		dbService.update(sql.toString());
	}
	
	/**
	 * 根据区域信息列表与父节点编号获得子区域信息列表 
	 * @param areaList  区域列表
	 * @param parentid  父节点编号
	 * @return List(String)区域信息列表
	 * @throws Exception
	 */
	public List getChildareaList(List areaList, String parentid)
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
	
}

