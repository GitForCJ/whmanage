package com.wlt.webm.rights.form;

import org.apache.struts.action.ActionForm;

/**
 * 区域信息<br>
 */
public class SysRoleForm extends ActionForm
{
	
	/**
	 * @return prole
	 */
	public String getProle() {
		return prole;
	}

	/**
	 * @param prole
	 */
	public void setProle(String prole) {
		this.prole = prole;
	}

	   /**
     * 功能模块编号组
     */
    private String[] modules;
	
	/**
	 * @return 功能模块编号组
	 */
	public String[] getModules() {
		return modules;
	}

	/**
	 * @param modules
	 */
	public void setModules(String[] modules) {
		this.modules = modules;
	}

	/**
	 * 区域
	 */
	private String wltarea;
	
	/**
	 * 区域编号
	 */
	private String areaid;
	
	/**
	 * 角色编号
	 */
	private String roleid;
	
	/**
	 * 是否默认
	 */
	private String sg_default;
	/**
	 * @return 角色id
	 */
	public String getRoleid() {
		return roleid;
	}

	/**
	 * @param roleid
	 */
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	/**
	 * 角色类型
	 */
	private String roleType;
	
	/**
	 * 角色名
	 */
	private String rolename;
	
	/**
	 * 父角色
	 */
	private String  prole;
	
	/**
	 * id
	 */
	private String[]  ids;
	
	/**
	 * @return id
	 */
	public String[] getIds() {
		return ids;
	}

	/**
	 * @param ids
	 */
	public void setIds(String[] ids) {
		this.ids = ids;
	}

	/**
	 * @return 角色类型
	 */
	public String getRoleType() {
		return roleType;
	}

	/**
	 * @param roleType
	 */
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	/**
	 * @return 角色名称
	 */
	public String getRolename() {
		return rolename;
	}

	/**
	 * @param rolename
	 */
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	/**
	 * @return 区域
	 */
	public String getWltarea() {
		return wltarea;
	}

	/**
	 * @param wltarea
	 */
	public void setWltarea(String wltarea) {
		this.wltarea = wltarea;
	}

	/**
	 * @return 区域id
	 */
	public String getAreaid() {
		return areaid;
	}

	/**
	 * @param areaid
	 */
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getSg_default() {
		return sg_default;
	}

	public void setSg_default(String sg_default) {
		this.sg_default = sg_default;
	}

	/**
	 * 一级代理
	 */
	private String oneAgents;

	public String getOneAgents() {
		return oneAgents;
	}

	public void setOneAgents(String oneAgents) {
		this.oneAgents = oneAgents;
	}

}
