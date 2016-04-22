package com.wlt.webm.business.form;

import org.apache.struts.action.ActionForm;

/**
 * ������Ϣ<br>
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
     * ����ģ������
     */
    private String[] modules;
	
	/**
	 * @return ����ģ������
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
	 * ����
	 */
	private String wltarea;
	
	/**
	 * ������
	 */
	private String areaid;
	
	/**
	 * ��ɫ���
	 */
	private String roleid;
	
	/**
	 * ҵ������
	 */
	private String sc_tradertype;
	
	/**
	 * ��ֵ
	 */
	private String sc_tradernum;
	
	/**
	 * Ӷ�����
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
	 * @return ��ɫid
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
	 * ��ɫ����
	 */
	private String roleType;
	
	/**
	 * ��ɫ��
	 */
	private String rolename;
	
	/**
	 * ����ɫ
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
	 * @return ��ɫ����
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
	 * @return ��ɫ����
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
	 * @return ����
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
	 * @return ����id
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
