package com.wlt.webm.business.form;

import org.apache.struts.action.ActionForm;

/**
 * 
 */
public class BiSanForm extends ActionForm
{
	
	/**
	 * ���
	 */
	private Integer id ;
	/**
	 * ��Ʒid
	 */
	private String cm_face ;
	/**
	 * ��ֵ����
	 */
	private String cm_fee ;
	/**
	 * ����
	 */
	private String cm_one ;
	/**
	 * ҵ�����
	 */
	private String cm_type ;
	/**
	 * id
	 */
	private String[]  ids;
	
	//��Ӫ��
	private String yunyingshang;
	//���
	private String miane;
	//�ӿ�������
	private String interName;
	//Ӷ����
	private String groups;
	//��ҳʹ��
	private String paramUrl;
	
	private Integer curPage = 1;
	
	public Integer getCurPage() {
		return curPage;
	}
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCm_face() {
		return cm_face;
	}
	public void setCm_face(String cm_face) {
		this.cm_face = cm_face;
	}
	public String getCm_fee() {
		return cm_fee;
	}
	public void setCm_fee(String cm_fee) {
		this.cm_fee = cm_fee;
	}
	public String getCm_one() {
		return cm_one;
	}
	public void setCm_one(String cm_one) {
		this.cm_one = cm_one;
	}
	public String getCm_type() {
		return cm_type;
	}
	public void setCm_type(String cm_type) {
		this.cm_type = cm_type;
	}
	public String[] getIds() {
		return ids;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
	}
	public String getYunyingshang() {
		return yunyingshang;
	}
	public void setYunyingshang(String yunyingshang) {
		this.yunyingshang = yunyingshang;
	}
	public String getMiane() {
		return miane;
	}
	public void setMiane(String miane) {
		this.miane = miane;
	}
	public String getInterName() {
		return interName;
	}
	public void setInterName(String interName) {
		this.interName = interName;
	}
	public String getParamUrl() {
		return paramUrl;
	}
	public void setParamUrl(String paramUrl) {
		this.paramUrl = paramUrl;
	}
	
	
	/**
	 * ������ ����value
	 */
	private String zName;

	public String getzName() {
		return zName;
	}
	public void setzName(String zName) {
		this.zName = zName;
	}
	public String getGroups() {
		return groups;
	}
	public void setGroups(String groups) {
		this.groups = groups;
	}
	

}
