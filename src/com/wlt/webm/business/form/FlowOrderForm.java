package com.wlt.webm.business.form;

import org.apache.struts.action.ActionForm;

/**
 * 订单信息
 */
public class FlowOrderForm extends ActionForm
{
	/**
	 * 序号
	 */
	private Integer id;
	/**
	 * 地市区号
	 */
	private String areacode;
	/**
	 * 内部交易流水号
	 */
	private String tradeserial;
	/**
	 * 交易号码
	 */
	private String tradeobject;
	/**
	 * 接口商
	 */
	private String buyid;
	/**
	 * 业务类型
	 */
	private String service;
	/**
	 * 代理商
	 */
	private String dailishang;
	/**
	 * 交易金额
	 */
	private String fee;
	/**
	 * 扣款账号
	 */
	private String fundacct;
	/**
	 * 订单提交时间时间
	 */
	private String tradetime;
	/**
	 * 状态修改时间
	 */
	private String chgtime;
	/**
	 * 订单状态
	 */
	private String state;
	/**
	 * 返销所需数据
	 */
	private String writeoff	;
	/**
	 * 对账所需数据
	 */
	private String writecheck;
	/**
	 * 终端类别
	 */
	private String term_type;
	/**
	 * 条码类型
	 */
	private String barcode_type;
	/**
	 * 客户经理
	 */
	private String exp1;
	
	/**
	 * 表名
	 */
	private String tableName;
	
	private String startDate;
	
	private String endDate;
	
	private String userId;
	
	private String accountleft;
	
	private String username;
	
	private String roletype;
	
	private String logaccount;
	
	private String phone_type;
	
	private Integer curPage = 1;
	
	private String filterinfo;
	
	private String roleFilter;
	
	private String acctState;
	
	private String paramUrl;
	
	private String filterState;
	
	public String getFilterState() {
		return filterState;
	}

	public void setFilterState(String filterState) {
		this.filterState = filterState;
	}

	public String getParamUrl() {
		return paramUrl;
	}

	public void setParamUrl(String paramUrl) {
		this.paramUrl = paramUrl;
	}
	public String getRoleFilter() {
		return roleFilter;
	}
	public void setRoleFilter(String roleFilter) {
		this.roleFilter = roleFilter;
	}
	public String getFilterinfo() {
		return filterinfo;
	}
	public void setFilterinfo(String filterinfo) {
		this.filterinfo = filterinfo;
	}
	public Integer getCurPage() {
		return curPage;
	}
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}
	public String getLogaccount() {
		return logaccount;
	}
	public void setLogaccount(String logaccount) {
		this.logaccount = logaccount;
	}
	public String getRoletype() {
		return roletype;
	}
	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAccountleft() {
		return accountleft;
	}
	public void setAccountleft(String accountleft) {
		this.accountleft = accountleft;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	public String getTradeserial() {
		return tradeserial;
	}
	public void setTradeserial(String tradeserial) {
		this.tradeserial = tradeserial;
	}
	public String getTradeobject() {
		return tradeobject;
	}
	public void setTradeobject(String tradeobject) {
		this.tradeobject = tradeobject;
	}
	public String getBuyid() {
		return buyid;
	}
	public void setBuyid(String buyid) {
		this.buyid = buyid;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getFundacct() {
		return fundacct;
	}
	public void setFundacct(String fundacct) {
		this.fundacct = fundacct;
	}
	public String getTradetime() {
		return tradetime;
	}
	public void setTradetime(String tradetime) {
		this.tradetime = tradetime;
	}
	public String getChgtime() {
		return chgtime;
	}
	public void setChgtime(String chgtime) {
		this.chgtime = chgtime;
	}
	public String getWriteoff() {
		return writeoff;
	}
	public void setWriteoff(String writeoff) {
		this.writeoff = writeoff;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getWritecheck() {
		return writecheck;
	}
	public void setWritecheck(String writecheck) {
		this.writecheck = writecheck;
	}
	public String getTerm_type() {
		return term_type;
	}
	public void setTerm_type(String term_type) {
		this.term_type = term_type;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone_type() {
		return phone_type;
	}
	public void setPhone_type(String phone_type) {
		this.phone_type = phone_type;
	}

	public String getAcctState() {
		return acctState;
	}

	public void setAcctState(String acctState) {
		this.acctState = acctState;
	}

	public String getBarcode_type() {
		return barcode_type;
	}

	public void setBarcode_type(String barcode_type) {
		this.barcode_type = barcode_type;
	}

	public String getDailishang() {
		return dailishang;
	}

	public void setDailishang(String dailishang) {
		this.dailishang = dailishang;
	}

	public String getExp1() {
		return exp1;
	}

	public void setExp1(String exp1) {
		this.exp1 = exp1;
	}

}
