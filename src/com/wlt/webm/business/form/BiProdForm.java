package com.wlt.webm.business.form;

import org.apache.struts.action.ActionForm;

/**
 * 面额信息
 */
public class BiProdForm extends ActionForm
{
	
	/**
	 * 编号
	 */
	private Integer cm_id ;
	/**
	 * 产品id
	 */
	private String cm_prod ;
	/**
	 * 充值费用
	 */
	private Integer cm_fee ;
	/**
	 * 区域
	 */
	private String cm_area ;
	/**
	 * 业务类别
	 */
	private String cm_type ;
	/**
	 * id
	 */
	private String[]  ids;
	
	public String[] getIds() {
		return ids;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
	}
	public Integer getCm_id() {
		return cm_id;
	}
	public void setCm_id(Integer cm_id) {
		this.cm_id = cm_id;
	}
	public String getCm_prod() {
		return cm_prod;
	}
	public void setCm_prod(String cm_prod) {
		this.cm_prod = cm_prod;
	}
	public Integer getCm_fee() {
		return cm_fee;
	}
	public void setCm_fee(Integer cm_fee) {
		this.cm_fee = cm_fee;
	}
	public String getCm_area() {
		return cm_area;
	}
	public void setCm_area(String cm_area) {
		this.cm_area = cm_area;
	}
	public String getCm_type() {
		return cm_type;
	}
	public void setCm_type(String cm_type) {
		this.cm_type = cm_type;
	}

}
