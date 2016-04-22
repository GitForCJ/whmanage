package com.wlt.webm.rights.form;

import org.apache.struts.action.ActionForm;

/**
 * 充值次数信息<br>
 */
public class ReversalForm extends ActionForm
{	
	private Integer id;
	private String user_login;//登陆账号
	private String user_no;//用户编号
	private String tradeTypes;//类型
	private String reversalcount;//冲正次数
	/**
	 * id
	 */
	private String[]  ids;
	private Integer curPage = 1;
	
	public Integer getCurPage() {
		return curPage;
	}
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}
	
	
	public ReversalForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReversalForm(Integer id,String reversalcount, String user_login, String user_no) {
		super();
		this.id = id;
		this.reversalcount = reversalcount;
		this.user_login = user_login;
		this.user_no = user_no;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUser_login() {
		return user_login;
	}
	public void setUser_login(String user_login) {
		this.user_login = user_login;
	}
	public String getUser_no() {
		return user_no;
	}
	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}
	public String getReversalcount() {
		return reversalcount;
	}
	public void setReversalcount(String reversalcount) {
		this.reversalcount = reversalcount;
	}
	public String[] getIds() {
		return ids;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
	}
	public String getTradeTypes() {
		return tradeTypes;
	}
	public void setTradeTypes(String tradeTypes) {
		this.tradeTypes = tradeTypes;
	}
	

}
