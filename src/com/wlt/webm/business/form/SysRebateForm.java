package com.wlt.webm.business.form;

import org.apache.struts.action.ActionForm;

/**
 * 区域信息<br>
 */
public class SysRebateForm extends ActionForm
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

	private Integer srid;
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
	 * 业务名称
	 */
	private String sc_tradertype;
	
	/**
	 * 面值
	 */
	private String sc_tradernum;
	
	/**
	 * 佣金比例
	 */
	private String sc_traderpercent;
	
	public String getSc_tradertype() {
		return sc_tradertype;
	}

	public void setSc_tradertype(String sc_tradertype) {
		this.sc_tradertype = sc_tradertype;
	}

	public String getSc_tradernum() {
		return sc_tradernum;
	}

	public void setSc_tradernum(String sc_tradernum) {
		this.sc_tradernum = sc_tradernum;
	}

	public String getSc_traderpercent() {
		return sc_traderpercent;
	}

	public void setSc_traderpercent(String sc_traderpercent) {
		this.sc_traderpercent = sc_traderpercent;
	}

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

	public Integer getSrid() {
		return srid;
	}

	public void setSrid(Integer srid) {
		this.srid = srid;
	}


}
