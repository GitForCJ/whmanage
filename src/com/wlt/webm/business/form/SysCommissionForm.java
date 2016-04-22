package com.wlt.webm.business.form;

import org.apache.struts.action.ActionForm;

/**
 * 区域信息<br>
 */
public class SysCommissionForm extends ActionForm
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
	private String sg_id;
	
	private String sc_traderbegin;
	
	private String sc_traderend;
	
	private String sc_name;
	
	private String sc_type;
	
	private String sc_other;
	
	public String getSc_type() {
		return sc_type;
	}

	public void setSc_type(String sc_type) {
		this.sc_type = sc_type;
	}

	public String getSc_level() {
		return sc_level;
	}

	public void setSc_level(String sc_level) {
		this.sc_level = sc_level;
	}

	private String sc_level;
	
	public String getSc_traderbegin() {
		return sc_traderbegin;
	}

	public void setSc_traderbegin(String sc_traderbegin) {
		this.sc_traderbegin = sc_traderbegin;
	}

	public String getSc_traderend() {
		return sc_traderend;
	}

	public void setSc_traderend(String sc_traderend) {
		this.sc_traderend = sc_traderend;
	}

	public String getSg_defaut() {
		return sg_defaut;
	}

	public void setSg_defaut(String sg_defaut) {
		this.sg_defaut = sg_defaut;
	}

	private String sg_defaut;
	
	public String getSg_id() {
		return sg_id;
	}

	public void setSg_id(String sg_id) {
		this.sg_id = sg_id;
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
	 * 业务名称
	 */
	private String sc_tradertype;
	
	/**
	 * 面值
	 */
	private String sc_tradernum;
	
	/**
	 * 角色类型
	 */
	private String wltroletype;
	
	public String getWltroletype() {
		return wltroletype;
	}

	public void setWltroletype(String wltroletype) {
		this.wltroletype = wltroletype;
	}

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

	public String getSc_name() {
		return sc_name;
	}

	public void setSc_name(String sc_name) {
		this.sc_name = sc_name;
	}

	public String getSc_other() {
		return sc_other;
	}

	public void setSc_other(String sc_other) {
		this.sc_other = sc_other;
	}


}
