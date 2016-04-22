package com.wlt.webm.rights.form;

import org.apache.struts.action.ActionForm;

/**
 * ������Ϣ<br>
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
	 * �Ƿ�Ĭ��
	 */
	private String sg_default;
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

	public String getSg_default() {
		return sg_default;
	}

	public void setSg_default(String sg_default) {
		this.sg_default = sg_default;
	}

	/**
	 * һ������
	 */
	private String oneAgents;

	public String getOneAgents() {
		return oneAgents;
	}

	public void setOneAgents(String oneAgents) {
		this.oneAgents = oneAgents;
	}

}
