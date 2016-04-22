package com.wlt.webm.business.form;

import org.apache.struts.action.ActionForm;

/**
 * 第三方接口商类
 * 
 * @author caiSJ
 * 
 */
public class TPForm extends ActionForm{

	/**
	 * 代理商基本信息
	 */
	private String employID;
	private String user_login;
	private int user_id;
	private String user_no;
	private String user_pass;
	private String trade_pass;
	private int user_site;
	private int user_city;
	private int user_status;
	private String sa_zone;
	private String fundacct;
	private String interName;
	private String qbFlag;//--0不起用多通道  1启用多通道
	private String userPt;
	private String returnurl;
	
	private String qbCommission;//-- Q币佣金
	
	private String caidan;//-- 0拆单  1不拆单
	
	private String groups;// 佣金组，，对应sys_tpemploy groups 字段
	
	private String jfgroups;//交通费罚款佣金组，，对应sys_tpemploy_jf groups 字段
	
	private String flowgroups;//流量佣金组  对应 sys_tpemploy_Flow groups 字段
	
	/**
	 * 代理商签名秘钥
	 */
	private String keyvalue;
	/**
	 * 代理商校验码秘钥
	 */
	private String keyvalue1;
	/**
	 * ip或终端设备信息
	 */
	private String ip;

	private Integer state;
	
	private Integer curPage = 1;
	
	public Integer getCurPage() {
		return curPage;
	}
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}
	public Integer getState() {
		return state;
	}




	public void setState(Integer state) {
		this.state = state;
	}




	public String getUser_login() {
		return user_login;
	}




	public void setUser_login(String user_login) {
		this.user_login = user_login;
	}




	public int getUser_id() {
		return user_id;
	}




	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}




	public String getUser_no() {
		return user_no;
	}




	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}




	public String getUser_pass() {
		return user_pass;
	}




	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}




	public String getTrade_pass() {
		return trade_pass;
	}




	public void setTrade_pass(String trade_pass) {
		this.trade_pass = trade_pass;
	}




	public int getUser_site() {
		return user_site;
	}




	public void setUser_site(int user_site) {
		this.user_site = user_site;
	}




	public int getUser_city() {
		return user_city;
	}




	public void setUser_city(int user_city) {
		this.user_city = user_city;
	}




	public int getUser_status() {
		return user_status;
	}




	public void setUser_status(int user_status) {
		this.user_status = user_status;
	}




	public String getSa_zone() {
		return sa_zone;
	}




	public void setSa_zone(String sa_zone) {
		this.sa_zone = sa_zone;
	}




	public String getFundacct() {
		return fundacct;
	}




	public void setFundacct(String fundacct) {
		this.fundacct = fundacct;
	}




	public String getKeyvalue() {
		return keyvalue;
	}




	public void setKeyvalue(String keyvalue) {
		this.keyvalue = keyvalue;
	}




	public String getKeyvalue1() {
		return keyvalue1;
	}




	public void setKeyvalue1(String keyvalue1) {
		this.keyvalue1 = keyvalue1;
	}




	public String getIp() {
		return ip;
	}




	public void setIp(String ip) {
		this.ip = ip;
	}




	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	public String getInterName() {
		return interName;
	}
	public void setInterName(String interName) {
		this.interName = interName;
	}
	public String getEmployID() {
		return employID;
	}
	public void setEmployID(String employID) {
		this.employID = employID;
	}
	public String getQbFlag() {
		return qbFlag;
	}
	public void setQbFlag(String qbFlag) {
		this.qbFlag = qbFlag;
	}
	public String getUserPt() {
		return userPt;
	}
	public void setUserPt(String userPt) {
		this.userPt = userPt;
	}
	public String getQbCommission() {
		return qbCommission;
	}
	public void setQbCommission(String qbCommission) {
		this.qbCommission = qbCommission;
	}
	public String getCaidan() {
		return caidan;
	}
	public void setCaidan(String caidan) {
		this.caidan = caidan;
	}
	public String getGroups() {
		return groups;
	}
	public void setGroups(String groups) {
		this.groups = groups;
	}
	public String getJfgroups() {
		return jfgroups;
	}
	public void setJfgroups(String jfgroups) {
		this.jfgroups = jfgroups;
	}
	public String getFlowgroups() {
		return flowgroups;
	}
	public void setFlowgroups(String flowgroups) {
		this.flowgroups = flowgroups;
	}
	public String getReturnurl() {
		return returnurl;
	}
	public void setReturnurl(String returnurl) {
		this.returnurl = returnurl;
	}

}
