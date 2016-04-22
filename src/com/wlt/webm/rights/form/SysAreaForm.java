package com.wlt.webm.rights.form;

import org.apache.struts.action.ActionForm;

/**
 * 区域信息<br>
 */
public class SysAreaForm extends ActionForm
{
	/**
	 * 区域编号
	 */
	public String areacope;
	
	/**
	 * 父区域编号
	 */
	public String pareacope;
	
	/**
	 * 区域名称
	 */
	public String areaname;
	
	/**
	 * 区域区号
	 */
	public String areacode;
	
	/**
	 * 是否虚拟
	 */
	public String flag;

	/**
	 * @return 区域编号
	 */
	public String getAreacope() {
		return areacope;
	}

	/**
	 * @param areacope
	 */
	public void setAreacope(String areacope) {
		this.areacope = areacope;
	}

	/**
	 * @return 区域名称
	 */
	public String getAreaname() {
		return areaname;
	}

	/**
	 * @param areaname
	 */
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	/**
	 * @return 区域区号
	 */
	public String getAreacode() {
		return areacode;
	}

	/**
	 * @param areacode
	 */
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	/**
	 * @return 父区域编号
	 */
	public String getPareacope() {
		return pareacope;
	}

	/**
	 * @param pareacope
	 */
	public void setPareacope(String pareacope) {
		this.pareacope = pareacope;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
