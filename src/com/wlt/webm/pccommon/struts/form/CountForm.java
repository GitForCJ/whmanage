package com.wlt.webm.pccommon.struts.form;

import org.apache.struts.action.ActionForm;

/**
 * 统计报表的条件表单<br>
 * company 深圳市万恒科技有限公司<br>
 * copyright Copyright (c) 2008<br>
 * version 3.0.0.0
 * @author 鹿振
 */
public class CountForm extends ActionForm
{
    /**
     * 实现了Count接口的统计报表类名称
     */
    private String beanName;
    /**
     * 起始时间
     */
    private String starttime;
    /**
     * @return starttime
     */
    public String getStarttime() {
		return starttime;
	}

	/**
	 * @param starttime
	 */
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	/**
     * 页数
     */
    private int pageIndex;
    /**
     * 数据库名称
     */
    private String dbName;
    /**
     * 类型
     */
    private String type;
    /**
     * 开始日期
     */
    private String startDate;
    /**
     * 结束日期
     */
    private String endDate;
    /**
     * 公告级别
     */
    private String afficheGradeid;
    /**
     * 状态
     */
    private String state;
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 业务类型
     */
    private String service;
    /**
     * 交易流水号
     */
    private String tradeSerial;
    /**
     * 交易对象
     */
    private String tradeObject;
    /**
     * 交易对方
     */
    private String tradeOppos;
    
    /**
     * 终端编号
     */
    private String resourceId;
    /**
     * 交易对象
     */
    private String tradeobject;
    
    /**
     * 统一支付账号
     */
    private String fundacct;
    /**
     * 交易对方
     */
    private String tradeoppos;
    
    /**
     * 充值号码
     */
    private String accountnbr;
    /**
     * 充值请求截止日期
     */
    private String newenddate;
    /**
     * 充值请求截止时间
     */
    private String newenddatetime;
    /**
     * 充值请求开始日期
     */
    private String newstartdate;
    /**
     * 充值请求开始时间
     */
    private String newstartdatetime;
    /**
     * q币充值订单号
     */
    private String tran_seq;
    
    /**
     *q币充值 流水号
     */
    private String  tradeNum;
    
    /**
     * q币充值平台状态
     */
    private String scpFlag;
    
    /**
     * q币充值腾讯状态
     */
    private String tencentFlag;
    /**
     * q币充值交易类型
     */
    private String tradeType; 
    
	/**
	 * @return tradeType
	 */
	public String getTradeType() {
		return tradeType;
	}

	/**
	 * @param tradeType
	 */
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	/**
	 * @return tencentFlag
	 */
	public String getTencentFlag() {
		return tencentFlag;
	}

	/**
	 * @param tencentFlag
	 */
	public void setTencentFlag(String tencentFlag) {
		this.tencentFlag = tencentFlag;
	}

	/**
	 * @return tradeNum
	 */
	public String getTradeNum() {
		return tradeNum;
	}

	/**
	 * @param tradeNum
	 */
	public void setTradeNum(String tradeNum) {
		this.tradeNum = tradeNum;
	}

	/**
	 * @return tran_seq
	 */
	public String getTran_seq() {
		return tran_seq;
	}

	/**
	 * @param tran_seq
	 */
	public void setTran_seq(String tran_seq) {
		this.tran_seq = tran_seq;
	}

	/**
	 * @return newstartdate
	 */
	public String getNewstartdate() {
		return newstartdate;
	}

	/**
	 * @param newstartdate
	 */
	public void setNewstartdate(String newstartdate) {
		this.newstartdate = newstartdate;
	}

	/**
	 * @return newstartdatetime
	 */
	public String getNewstartdatetime() {
		return newstartdatetime;
	}

	/**
	 * @param newstartdatetime
	 */
	public void setNewstartdatetime(String newstartdatetime) {
		this.newstartdatetime = newstartdatetime;
	}

	/**
	 * @return accountnbr
	 */
	public String getAccountnbr() {
		return accountnbr;
	}

	/**
	 * @param accountnbr
	 */
	public void setAccountnbr(String accountnbr) {
		this.accountnbr = accountnbr;
	}

	/**
	 * @return newenddate
	 */
	public String getNewenddate() {
		return newenddate;
	}

	/**
	 * @param newenddate
	 */
	public void setNewenddate(String newenddate) {
		this.newenddate = newenddate;
	}

	/**
	 * @return newenddatetime
	 */
	public String getNewenddatetime() {
		return newenddatetime;
	}

	/**
	 * @param newenddatetime
	 */
	public void setNewenddatetime(String newenddatetime) {
		this.newenddatetime = newenddatetime;
	}

	/**
	 * @return 交易对方
	 */
	public String getTradeoppos() {
		return tradeoppos;
	}

	/**
	 * @param tradeoppos 交易对方
	 */
	public void setTradeoppos(String tradeoppos) {
		this.tradeoppos = tradeoppos;
	}
    
    /**
     * 对账状态
     */
    private String checkState;
    
    /**
	 * 开始日期
	 */
	private String startdate;
	
	/**
	 * 结束日期
	 */
	private String enddate;
    
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 起始年
     */
    private String startyear;
    /**
     * 起始月
     */
    private String startmonth;
    /**
     * 结束年
     */
    private String endyear;
    /**
     * 结束月
     */
    private String endmonth;
    /**
     * 订单号
     */
    private String transaction_id;
    /**
     * 财付通交易状态
     */
    private String tencentflag;
    /**
     * 平台划账状态
     */
    private String scpflag;
	/**
	 * 备注说明
	 */
	private String explain;
	/**
	 * 业务处理号
	 */
	private String dealserial;
	/**
	 * 交易对象
	 */
	private String tradeaccount;
	/**
	 * 交易类型
	 */
	private String tradetype;
	/**
	 * @return 备注说明
	 */
	public String getExplain() {
		return explain;
	}



	 /**
	 * @param explain 备注说明
	 */
	public void setExplain(String explain) {
		this.explain = explain;
	}
    
    /**
     * 统一支付子帐号
     */
    private String childfacct;
    /**
     * 支付账号
     */
    private String payaccount;
	/**
	 * @return payaccount
	 */
	public String getPayaccount() {
		return payaccount;
	}

	/**
	 * @param payaccount
	 */
	public void setPayaccount(String payaccount) {
		this.payaccount = payaccount;
	}

	/**
	 * @return 交易对象
	 */
	public String getTradeobject() {
		return tradeobject;
	}

	/**
	 * @param tradeobject 交易对象
	 */
	public void setTradeobject(String tradeobject) {
		this.tradeobject = tradeobject;
	}
    /**
     * 交易流水号
     */
    private String tradeserial;
	/**
	 * @return 交易流水号
	 */
	public String getTradeserial() {
		return tradeserial;
	}

	/**
	 * @param tradeserial 交易流水号
	 */
	public void setTradeserial(String tradeserial) {
		this.tradeserial = tradeserial;
	}
	
    /**
     * 终端编号
     */
    private String resourceid;
    /**
     * @return 终端编号
     */
    public String getResourceid()
    {
        return resourceid;
    }

    /**
     * @param resourceid 终端编号
     */
    public void setResourceid(String resourceid)
    {
        this.resourceid = resourceid;
    }

    
    /**
     * 代办户编号
     */
    private String useracct;
	/**
	 * @return 代办户编号
	 */
	public String getUseracct() {
		return useracct;
	}

	/**
	 * @param useracct 代办户编号
	 */
	public void setUseracct(String useracct) {
		this.useracct = useracct;
	}
    /**
     * 结束时间
     */
    private String endtime;
	
    /**
     * @return endtime
     */
    public String getEndtime() {
		return endtime;
	}

	/**
	 * @param endtime
	 */
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	/**
     * @return 结束月
     */
    public String getEndmonth()
    {
        return endmonth;
    }

    /**
     * @param endmonth 结束月
     */
    public void setEndmonth(String endmonth)
    {
        this.endmonth = endmonth;
    }

    /**
     * @return 结束年
     */
    public String getEndyear()
    {
        return endyear;
    }

    /**
     * @param endyear 结束年
     */
    public void setEndyear(String endyear)
    {
        this.endyear = endyear;
    }

    /**
     * @return 起始月
     */
    public String getStartmonth()
    {
        return startmonth;
    }

    /**
     * @param startmonth 起始月
     */
    public void setStartmonth(String startmonth)
    {
        this.startmonth = startmonth;
    }

    /**
     * @return 起始年
     */
    public String getStartyear()
    {
        return startyear;
    }

    /**
     * @param startyear 起始年
     */
    public void setStartyear(String startyear)
    {
        this.startyear = startyear;
    }

    /**
	 * @return 获得实现了Count接口的统计报表类名称
	 */
	public String getBeanName() {
		return beanName;
	}

	/**
	 * @param beanName 设置实现了Count接口的统计报表类名称
	 */
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	/**
	 * @return 页数
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex 页数
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * @return 数据库名称
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * @param dbName 数据库名称
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * @return 公告级别
	 */
	public String getAfficheGradeid() {
		return afficheGradeid;
	}

	/**
	 * @param afficheGradeid 公告级别
	 */
	public void setAfficheGradeid(String afficheGradeid) {
		this.afficheGradeid = afficheGradeid;
	}

	/**
	 * @return 状态
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state 状态
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return 公告类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type 公告类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return 结束日期
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate 结束日期
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return 开始日期
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate 开始日期
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return 关键字
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword 关键字
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return 业务类型
	 */
	public String getService() {
		return service;
	}

	/**
	 * @param service 业务类型
	 */
	public void setService(String service) {
		this.service = service;
	}

	/**
	 * @return 结束时间
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime 结束时间
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return 开始时间
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime 开始时间
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return 交易对象
	 */
	public String getTradeObject() {
		return tradeObject;
	}

	/**
	 * @param tradeObject 交易对象
	 */
	public void setTradeObject(String tradeObject) {
		this.tradeObject = tradeObject;
	}

	/**
	 * @return 交易对方
	 */
	public String getTradeOppos() {
		return tradeOppos;
	}

	/**
	 * @param tradeOppos 交易对方
	 */
	public void setTradeOppos(String tradeOppos) {
		this.tradeOppos = tradeOppos;
	}

	/**
	 * @return 交易流水号
	 */
	public String getTradeSerial() {
		return tradeSerial;
	}

	/**
	 * @param tradeSerial 交易流水号
	 */
	public void setTradeSerial(String tradeSerial) {
		this.tradeSerial = tradeSerial;
	}

	/**
	 * @return the enddate
	 */
	public String getEnddate() {
		return enddate;
	}

	/**
	 * @param enddate the enddate to set
	 */
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	/**
	 * @return the startdate
	 */
	public String getStartdate() {
		return startdate;
	}

	/**
	 * @param startdate the startdate to set
	 */
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	/**
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return the fundacct
	 */
	public String getFundacct() {
		return fundacct;
	}

	/**
	 * @param fundacct the fundacct to set
	 */
	public void setFundacct(String fundacct) {
		this.fundacct = fundacct;
	}

	/**
	 * @return the checkState
	 */
	public String getCheckState() {
		return checkState;
	}

	/**
	 * @param checkState the checkState to set
	 */
	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}

	/**
	 * @return transaction_id
	 */
	public String getTransaction_id() {
		return transaction_id;
	}

	/**
	 * @param transactionId
	 */
	public void setTransaction_id(String transactionId) {
		transaction_id = transactionId;
	}

	/**
	 * @return tencentflag
	 */
	public String getTencentflag() {
		return tencentflag;
	}

	/**
	 * @param tencentflag 
	 */
	public void setTencentflag(String tencentflag) {
		this.tencentflag = tencentflag;
	}

	/**
	 * @param childfacct
	 */
	public void setChildfacct(String childfacct) {
		this.childfacct = childfacct;
	}

	/**
	 * @return childfacct
	 */
	public String getChildfacct() {
		return childfacct;
	}

	/**
	 * @param scpflag
	 */
	public void setScpflag(String scpflag) {
		this.scpflag = scpflag;
	}

	/**
	 * @return scpflag
	 */
	public String getScpflag() {
		return scpflag;
	}

	/**
	 * @return dealserial
	 */
	public String getDealserial() {
		return dealserial;
	}

	/**
	 * @param dealserial
	 */
	public void setDealserial(String dealserial) {
		this.dealserial = dealserial;
	}

	/**
	 * @return tradeaccount
	 */
	public String getTradeaccount() {
		return tradeaccount;
	}

	/**
	 * @param tradeaccount
	 */
	public void setTradeaccount(String tradeaccount) {
		this.tradeaccount = tradeaccount;
	}

	/**
	 * @return tradetype
	 */
	public String getTradetype() {
		return tradetype;
	}

	/**
	 * @param tradetype
	 */
	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}

	/**
	 * @param scpFlag
	 */
	public void setScpFlag(String scpFlag) {
		this.scpFlag = scpFlag;
	}

	/**
	 * @return scpFlag
	 */
	public String getScpFlag() {
		return scpFlag;
	}
}
