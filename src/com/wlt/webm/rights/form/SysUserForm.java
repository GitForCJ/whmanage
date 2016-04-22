package com.wlt.webm.rights.form;

import org.apache.struts.action.ActionForm;

/**
 * 用户信息<br>
 */
public class SysUserForm extends ActionForm
{
	/**
	 * 用户姓名
	 */
	private String name;
    /**
     * 用户编号
     */
    private String user_id;
    /**
     * 用户系统编号
     */
    private String userno;
    /**
     * 登录名
     */
    private String username;
    /**
     * 登陆密码
     */
    private String userpassword;
    /**
     * 角色编号
     */
    private String user_role;
    /**
     * 用户归属区域 省
     */
    private String usersite;
    /**
     * 用户归属市
     */
	private String user_site_city;
	/**
	 * 登陆短信验证标示
	 */
    private String shortflag;
	/**
	 * 用户地市区号
	 */
	private String sa_zone;
	/**
	 * 用户类型
	 */
	private String roleType;
	/**
	 * 账户操作是否需要短信标示
	 */
    private String feeshortflag;
    /**
     * 交易密码
     */
    private String dealpass;
    /**
     * 用户邮箱
     */
    private String usermail;
    /**
     * 用户地址
     */
    private String address;
    /**
     * 用户名字
     */
    private String userename;
    
    /**
	 * 区域名称
	 */
	private String wltarea;
	/**
	 * 短信验证码
	 */
	private String mmccode;
    
    /**
     * 联系电话
     */
    private String phone;
    /**
     *  用户状态  
     */
    private String userstatus;
    /**
     * 激活状态
     */
    private String user_activate;
    /**
     * 殴飞绑定类型 0代表话费 1支付宝 2 Q币
     */
    private String btype;
    /**
     * 父节点编号
     */
    private String parent_id;  

    private String exp1;//开户人
    private String exp2;
    private String exp3;//mac 地址
	private String role_name;
    
    private String rolecheck;
    
    private String usersf;//身份证
    
    private String left;//用户余额
    
    private String srdesc;//是否一级代理

    
    

    

    

    

    
    public String getSrdesc() {
		return srdesc;
	}

	public void setSrdesc(String srdesc) {
		this.srdesc = srdesc;
	}

	public String getExp2() {
		return exp2;
	}

	public void setExp2(String exp2) {
		this.exp2 = exp2;
	}

	public String getExp3() {
		return exp3;
	}

	public void setExp3(String exp3) {
		this.exp3 = exp3;
	}

	public String getExp1() {
		return exp1;
	}

	public void setExp1(String exp1) {
		this.exp1 = exp1;
	}

	public String getUsersf() {
		return usersf;
	}

	public void setUsersf(String usersf) {
		this.usersf = usersf;
	}

	private String userdesc;
    
    private String usercreatedate;
    
    /**
     * 资金账户
     */
    private String accountAmount;
    /**
     * 佣金账户
     */
    private String commissionAmount;
    /**
     * 冻结账户
     */
    
    private String frozenAmount;
    
    private String state;
    
    private String passflag;
    
    private String feepass;

    private String parentRoleType;
    
	public String getParentRoleType() {
		return parentRoleType;
	}

	public void setParentRoleType(String parentRoleType) {
		this.parentRoleType = parentRoleType;
	}

	public String getMmccode() {
		return mmccode;
	}

	public void setMmccode(String mmccode) {
		this.mmccode = mmccode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 区域编号
	 */
	private String areaid;
	
	



	
	private Integer curPage = 1;

	private String startDate;
	
	private String endDate;
	
	private String fundacct;
	
	private String paramUrl;
	


	private String hfkID;
	private String sfz;
	private String hfkgz;
	private String hfkphone;
	
	private String oufeiID;
	private String oufeiMoban;
	private String oufeiSign;
	
	
	private String operatename;
	
	public String getOperatename() {
		return operatename;
	}

	public void setOperatename(String operatename) {
		this.operatename = operatename;
	}

	public String getHfkphone() {
		return hfkphone;
	}

	public void setHfkphone(String hfkphone) {
		this.hfkphone = hfkphone;
	}

	public String getHfkgz() {
		return hfkgz;
	}

	public void setHfkgz(String hfkgz) {
		this.hfkgz = hfkgz;
	}

	public String getSfz() {
		return sfz;
	}

	public void setSfz(String sfz) {
		this.sfz = sfz;
	}
	public String getHfkID() {
		return hfkID;
	}

	public void setHfkID(String hfkID) {
		this.hfkID = hfkID;
	}

	public String getParamUrl() {
		return paramUrl;
	}

	public void setParamUrl(String paramUrl) {
		this.paramUrl = paramUrl;
	}
	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}
	public String getSa_zone() {
		return sa_zone;
	}

	public void setSa_zone(String sa_zone) {
		this.sa_zone = sa_zone;
	}

	public String getUser_site_city() {
		return user_site_city;
	}

	public void setUser_site_city(String user_site_city) {
		this.user_site_city = user_site_city;
	}

	public String getWltarea() {
		return wltarea;
	}

	public void setWltarea(String wltarea) {
		this.wltarea = wltarea;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(String accountAmount) {
		this.accountAmount = accountAmount;
	}

	public String getCommissionAmount() {
		return commissionAmount;
	}

	public void setCommissionAmount(String commissionAmount) {
		this.commissionAmount = commissionAmount;
	}

	public String getFrozenAmount() {
		return frozenAmount;
	}

	public void setFrozenAmount(String frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public String getUserename() {
		return userename;
	}

	public void setUserename(String userename) {
		this.userename = userename;
	}

	public String getUsermail() {
		return usermail;
	}

	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}


	public String getUserstatus() {
		return userstatus;
	}

	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}

	public String getUsersite() {
		return usersite;
	}

	public void setUsersite(String usersite) {
		this.usersite = usersite;
	}

	public String getUserdesc() {
		return userdesc;
	}

	public void setUserdesc(String userdesc) {
		this.userdesc = userdesc;
	}

	public String getUsercreatedate() {
		return usercreatedate;
	}

	public void setUsercreatedate(String usercreatedate) {
		this.usercreatedate = usercreatedate;
	}


	public String getRolecheck() {
		return rolecheck;
	}

	public void setRolecheck(String rolecheck) {
		this.rolecheck = rolecheck;
	}


	/**
	 * @return 角色编号
	 */
	public String getUser_role() {
		return user_role;
	}

	/**
	 * @param user_role
	 */
	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPassflag() {
		return passflag;
	}

	public void setPassflag(String passflag) {
		this.passflag = passflag;
	}



	public String getShortflag() {
		return shortflag;
	}

	public void setShortflag(String shortflag) {
		this.shortflag = shortflag;
	}

	public String getFeeshortflag() {
		return feeshortflag;
	}

	public void setFeeshortflag(String feeshortflag) {
		this.feeshortflag = feeshortflag;
	}


	public String getDealpass() {
		return dealpass;
	}

	public void setDealpass(String dealpass) {
		this.dealpass = dealpass;
	}

	public String getFeepass() {
		return feepass;
	}

	public void setFeepass(String feepass) {
		this.feepass = feepass;
	}


	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getFundacct() {
		return fundacct;
	}

	public void setFundacct(String fundacct) {
		this.fundacct = fundacct;
	}
    public String getUser_activate() {
		return user_activate;
	}

	public void setUser_activate(String user_activate) {
		this.user_activate = user_activate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getOufeiID() {
		return oufeiID;
	}

	public void setOufeiID(String oufeiID) {
		this.oufeiID = oufeiID;
	}

	public String getOufeiMoban() {
		return oufeiMoban;
	}

	public void setOufeiMoban(String oufeiMoban) {
		this.oufeiMoban = oufeiMoban;
	}

	public String getOufeiSign() {
		return oufeiSign;
	}

	public void setOufeiSign(String oufeiSign) {
		this.oufeiSign = oufeiSign;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getBtype() {
		return btype;
	}

	public void setBtype(String btype) {
		this.btype = btype;
	}

	
}
