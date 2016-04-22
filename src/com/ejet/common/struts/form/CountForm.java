package com.ejet.common.struts.form;

import org.apache.struts.action.ActionForm;

/**
 * 统计报表的条件表单
 * Company：深圳市###EJET###有限公司
 * Copyright: Copyright (c) 2009
 * @author ###ejet###
 * 修改人：胡俊
 * 修改日期：2009-9-28
 * @version V2.1.2.0
 */
public class CountForm extends ActionForm
{
    /**
     * 实现了Count接口的统计报表类名称
     */
    private String beanName;
    /**
     * 页数
     */
    private int pageIndex;
    /**
     * 数据库名称
     */
    private String dbName;
    /**
     * 终端业务编号 
     */
    private String code;
    /**
     * 代理点编号
     */
    private String terminal;
    /**
     * 代理点名称
     */
    private String termName;
    /**
     * 终端业务名称
     */
    private String name;
    /**
     * 终端业务操作步骤
     */
    private String steps;
    /**
     * 终端组
     */
    private String group;
    /**
     * 客户名称
     */
    private String custname;
    /**
     * 终端类型
     */
    private String termtype;
    /**
     * 使用类型
     */
    private String usetype;
    /**
     * 备注
     */
    private String remark;
    /**
     * 地市
     */
    private String city;
    /**
     * 片区
     */
    private String district;
    /**
     * 代理商
     */
    private String agent;   
    /**
     * 代理点编号
     */
    private String useracct;
    /**
     * 终端编号
     */
    private String resourceid;
    /**
     * 交易流水号
     */
    private String tradeserial;
    /**
     * 交易对象
     */
    private String tradeobject;
    /**
     * 交易对方
     */
    private String tradeoppos;
    /**
     * 支付账号
     */
    private String payaccount;
    /**
     * 起始日期
     */
    private String startdate;
    /**
     * 起始日期2
     */
    private String startdate2;
    /**
     * 起始时间
     */
    private String starttime;
    /**
     * 起始时间2
     */
    private String starttime2;
    /**
     * 终止日期
     */
    private String enddate;
    /**
     * 终止日期2
     */
    private String enddate2;
    /**
     * 结束时间
     */
    private String endtime;
    /**
     * 结束时间2
     */
    private String endtime2;
    /**
     * 交易方式
     */
    private String tradetype;
    /**
     * 业务类型
     */
    private String service;
    /**
     * 交易发起平台
     */
    private String tradesource;
    /**
     * 状态
     */
    private String state;
    /**
     * 起始年份
     */
    private String startyear;
    /**
     * 终止年份
     */
    private String endyear;
    /**
     * 起始月份
     */
    private String startmonth;
    /**
     * 终止月份
     */
    private String endmonth;
    /**
     * 操作类型（计费统一支付账户对账查询）
     */
    private String opertype;
    /**
     * 明细处理状态（计费统一支付账户对账查询）
     */
    private String disstate;
    /**
     * 统一支付账户类型
     */
    private String faccttype;
    /**
     * 统一支付账号
     */
    private String fundacct;
    /**
     * 卡类别
     */
    private String cardtype;
    /**
     * 卡面值
     */
    private String cardfee;
    /**
     * 卡号
     */
    private String cardno;
    /**
     * 卡批次
     */
    private String cardbatch;
    /**
     * 卡志号
     */
    private String cardsign;
    /**
     * 流水号（综服异常工单查询）
     */
    private String seqno;
    /**
     * 服务实例号（综服异常工单查询）
     */
    private String servid;
    /**
     * 操作类型（综服异常工单查询）
     */
    private String actionid;
    /**
     * 产品类型（综服异常工单查询）
     */
    private String prodid;
    /**
     * 错误信息关键字（综服异常工单查询）
     */
    private String errorkey;
    /**
     * 年份
     */
    private String year;
    /**
     * 月份
     */
    private String month;
    /**
     * 交易场所 00 portal 01 pos 02 pc 03 计费系统 04 168系统 
     */
    private String tradeaddr;
    /**
     * 交易账号
     */
    private String tradeaccount;
    /**
     * 交易流水号
     */
    private String dealserial;
    /**
     * 交易平台（代理商佣金统计）
     */
    private String tradeplat;
    /**
     * 支付方式（代理商佣金统计）
     */
    private String paytype;
    
    /**
     * 支付流水号(与订单推送业务中的订单同步流水号相同)
     */
    private String payNo;
    /**
     * 商户号
     */
    private String merId;
    
    /**
     * 商户私有域
     */
    private String priv;
    
    /**
     * 本地流水号/网银订单号
     */
    private String localNo;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * POS机编号
     */
    private String posid;
    /**
     * 订单支付有效期
     */
    private String limittime;
    /**
     * 订单支付状态
     */
    private String paystate;
    /**
     * 订单推送状态
     */
    private String pushstate;
    /**
     * 订单支付响应状态
     */
    private String restate;
    
    /**
     * 交易类型
     */
    private String transtype;
    
    /**
     * 最小交易金额
     */
    private String mintransamt;
    
    /**
     * 最高交易金额
     */
    private String maxtransamt;
    
    /**
     * 交易结果
     */
    private String transresult;
    
    /**
     * 同步状态
     */
    private String syncstate;
	/**
	 * 交易账号
	 */
	private String tradeacct;
	/**
	 * 终端标识码
	 */
	private String banktermid;
	/**
	 * 商户标识
	 */
	private String bankmerch;
	/**
	 * 备注说明
	 */
	private String explain;
	
     /**
      * 导报表标志
      */	
	private String excelstate;
	
    /**
     * 证件号码
     */	
	private String idcard;
	
	/**
	 * 卡入库起始日期startintime
	 */
	private String startintime;
	
	/**
	 * 卡入库终止日期
	 */
	private String endtintime;
	/**
	 * @return 商户标识
	 */
	public String getBankmerch() {
		return bankmerch;
	}
    
    
    
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
	 * @param bankmerch 商户标识
	 */
	public void setBankmerch(String bankmerch) {
		this.bankmerch = bankmerch;
	}

	/**
	 * @return 终端标识码
	 */
	public String getBanktermid() {
		return banktermid;
	}

	/**
	 * @param banktermid 终端标识码
	 */
	public void setBanktermid(String banktermid) {
		this.banktermid = banktermid;
	}

	/**
	 * @return 交易账号
	 */
	public String getTradeacct() {
		return tradeacct;
	}

	/**
	 * @param tradeacct 交易账号
	 */
	public void setTradeacct(String tradeacct) {
		this.tradeacct = tradeacct;
	}

	/**
	 * @return 同步状态
	 */
	public String getSyncstate() {
		return syncstate;
	}

	/**
	 * @param syncstate 同步状态
	 */
	public void setSyncstate(String syncstate) {
		this.syncstate = syncstate;
	}

	/**
	 * @return 最小交易金额
	 */
	public String getMintransamt() {
		return mintransamt;
	}

	/**
	 * @param mintransamt 最小交易金额
	 */
	public void setMintransamt(String mintransamt) {
		this.mintransamt = mintransamt;
	}
	
	/**
	 * @return 最高交易金额
	 */
	public String getMaxtransamt() {
		return maxtransamt;
	}

	/**
	 * @param maxtransamt 最高交易金额
	 */
	public void setMaxtransamt(String maxtransamt) {
		this.maxtransamt = maxtransamt;
	}

	/**
	 * @return 交易结果
	 */
	public String getTransresult() {
		return transresult;
	}

	/**
	 * @param transresult 交易结果
	 */
	public void setTransresult(String transresult) {
		this.transresult = transresult;
	}

	/**
	 * @return 交易类型
	 */
	public String getTranstype() {
		return transtype;
	}

	/**
	 * @param transtype 交易类型
	 */
	public void setTranstype(String transtype) {
		this.transtype = transtype;
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
	 * 获取终端业务编号
	 * @return 终端业务编号
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置终端业务编号
	 * @param code 终端业务编号
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取终端业务名称
	 * @return 终端业务名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置终端业务名称
	 * @param name 终端业务名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取终端业务操作步骤
	 * @return 终端业务操作步骤
	 */
	public String getSteps() {
		return steps;
	}

	/**
	 * 设置终端业务操作步骤
	 * @param steps 终端业务操作步骤
	 */
	public void setSteps(String steps) {
		this.steps = steps;
	}

	/**
	 * @return 代理商
	 */
	public String getAgent() {
		return agent;
	}

	/**
	 * @param agent 代理商
	 */
	public void setAgent(String agent) {
		this.agent = agent;
	}

	/**
	 * @return 地市
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city 地市
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return 片区
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param district 片区
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return 终止日期
	 */
	public String getEnddate() {
		return enddate;
	}

	/**
	 * @param enddate 终止日期
	 */
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	/**
	 * @return 终止日期2
	 */
	public String getEnddate2() {
		return enddate2;
	}
	
	/**
	 * @param enddate2 终止日期2
	 */
	public void setEnddate2(String enddate2) {
		this.enddate2 = enddate2;
	}

	/**
	 * @return 结束时间
	 */
	public String getEndtime() {
		return endtime;
	}

	/**
	 * @param endtime 结束时间
	 */
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	/**
	 * @return 结束时间2
	 */
	public String getEndtime2() {
		return endtime2;
	}
	
	/**
	 * @param endtime2 结束时间2
	 */
	public void setEndtime2(String endtime2) {
		this.endtime2 = endtime2;
	}

	/**
	 * @return 支付账号
	 */
	public String getPayaccount() {
		return payaccount;
	}

	/**
	 * @param payaccount 支付账号
	 */
	public void setPayaccount(String payaccount) {
		this.payaccount = payaccount;
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
	 * @return 起始日期
	 */
	public String getStartdate() {
		return startdate;
	}

	/**
	 * @param startdate 起始日期
	 */
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	/**
	 * @return 起始日期2
	 */
	public String getStartdate2() {
		return startdate2;
	}
	
	/**
	 * @param startdate2 起始日期2
	 */
	public void setStartdate2(String startdate2) {
		this.startdate2 = startdate2;
	}

	/**
	 * @return 起始时间
	 */
	public String getStarttime() {
		return starttime;
	}

	/**
	 * @param starttime 起始时间
	 */
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	/**
	 * @return 起始时间2
	 */
	public String getStarttime2() {
		return starttime2;
	}
	
	/**
	 * @param starttime2 起始时间2
	 */
	public void setStarttime2(String starttime2) {
		this.starttime2 = starttime2;
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
	 * @return 交易方式
	 */
	public String getTradetype() {
		return tradetype;
	}

	/**
	 * @param tradetype 交易方式
	 */
	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}

	/**
	 * @return 代理点编号
	 */
	public String getUseracct() {
		return useracct;
	}

	/**
	 * @param useracct 代理点编号
	 */
	public void setUseracct(String useracct) {
		this.useracct = useracct;
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
     * @return 交易发起平台
     */
    public String getTradesource()
    {
        return tradesource;
    }

    /**
     * @param tradesource 交易发起平台
     */
    public void setTradesource(String tradesource)
    {
        this.tradesource = tradesource;
    }

    /**
     * @return 代理点编号
     */
    public String getTerminal()
    {
        return terminal;
    }

    /**
     * @param terminal 代理点编号
     */
    public void setTerminal(String terminal)
    {
        this.terminal = terminal;
    }

    /**
     * @return 客户名称
     */
    public String getCustname()
    {
        return custname;
    }

    /**
     * @param custname 客户名称
     */
    public void setCustname(String custname)
    {
        this.custname = custname;
    }

    /**
     * @return 终端组
     */
    public String getGroup()
    {
        return group;
    }

    /**
     * @param group 终端组
     */
    public void setGroup(String group)
    {
        this.group = group;
    }

    /**
     * @return 备注
     */
    public String getRemark()
    {
        return remark;
    }

    /**
     * @param remark 备注
     */
    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    /**
     * @return 终端类型
     */
    public String getTermtype()
    {
        return termtype;
    }

    /**
     * @param termtype 终端类型
     */
    public void setTermtype(String termtype)
    {
        this.termtype = termtype;
    }

    /**
     * @return 使用类型
     */
    public String getUsetype()
    {
        return usetype;
    }

    /**
     * @param usetype 使用类型
     */
    public void setUsetype(String usetype)
    {
        this.usetype = usetype;
    }
	/**
	 * @return 终止月份
	 */
	public String getEndmonth() {
		return endmonth;
	}

	/**
	 * @param endmonth 终止月份
	 */
	public void setEndmonth(String endmonth) {
		this.endmonth = endmonth;
	}

	/**
	 * @return 终止年份
	 */
	public String getEndyear() {
		return endyear;
	}

	/**
	 * @param endyear 终止年份
	 */
	public void setEndyear(String endyear) {
		this.endyear = endyear;
	}

	/**
	 * @return 起始月份
	 */
	public String getStartmonth() {
		return startmonth;
	}

	/**
	 * @param startmonth 起始月份
	 */
	public void setStartmonth(String startmonth) {
		this.startmonth = startmonth;
	}

	/**
	 * @return 起始年份
	 */
	public String getStartyear() {
		return startyear;
	}

	/**
	 * @param startyear 起始年份
	 */
	public void setStartyear(String startyear) {
		this.startyear = startyear;
	}

	/**
	 * @return 明细处理状态
	 */
	public String getDisstate() {
		return disstate;
	}

	/**
	 * @param disstate 明细处理状态
	 */
	public void setDisstate(String disstate) {
		this.disstate = disstate;
	}

	/**
	 * @return 操作类型
	 */
	public String getOpertype() {
		return opertype;
	}

	/**
	 * @param opertype 操作类型
	 */
	public void setOpertype(String opertype) {
		this.opertype = opertype;
	}
	/**
	 * @return  统一支付账户类型
	 */ 
	public String getFaccttype() {
		return faccttype;
	}
	/**
	 * @param faccttype 统一支付账户类型
	 */
	public void setFaccttype(String faccttype) {
		this.faccttype = faccttype;
	}
	/**
	 * @return 统一支付账号
	 */
	public String getFundacct() {
		return fundacct;
	}

	/**
	 * @param fundacct 统一支付账号
	 */
	public void setFundacct(String fundacct) {
		this.fundacct = fundacct;
	}

	/**
	 * @return 卡批次
	 */
	public String getCardbatch() {
		return cardbatch;
	}

	/**
	 * @param cardbatch 卡批次
	 */
	public void setCardbatch(String cardbatch) {
		this.cardbatch = cardbatch;
	}

	/**
	 * @return 卡面值
	 */ 
	public String getCardfee() {
		return cardfee;
	}

	/**
	 * @param cardfee 卡面值
	 */
	public void setCardfee(String cardfee) {
		this.cardfee = cardfee;
	}

	/**
	 * @return 卡号
	 */
	public String getCardno() {
		return cardno;
	}

	/**
	 * @param cardno 卡号
	 */
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	/**
	 * @return 卡类别
	 */
	public String getCardtype() {
		return cardtype;
	}

	/**
	 * @param cardtype 卡类别
	 */
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	/**
	 * @return 操作类型
	 */
	public String getActionid() {
		return actionid;
	}

	/**
	 * @param actionid 操作类型
	 */
	public void setActionid(String actionid) {
		this.actionid = actionid;
	}

	/**
	 * @return 错误信息关键字
	 */
	public String getErrorkey() {
		return errorkey;
	}

	/**
	 * @param errorkey 错误信息关键字
	 */
	public void setErrorkey(String errorkey) {
		this.errorkey = errorkey;
	}

	/**
	 * @return 产品类型
	 */
	public String getProdid() {
		return prodid;
	}

	/**
	 * @param prodid 产品类型
	 */
	public void setProdid(String prodid) {
		this.prodid = prodid;
	}
	
	/**
	 * @return 流水号
	 */
	public String getSeqno() {
		return seqno;
	}

	/**
	 * @param seqno 流水号
	 */
	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}

	/**
	 * @return 服务实例号
	 */
	public String getServid() {
		return servid;
	}

	/**
	 * @param servid 服务实例号
	 */
	public void setServid(String servid) {
		this.servid = servid;
	}

	/**
	 * @return 月份
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month 月份
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return 年份
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year 年份
	 */
	public void setYear(String year) {
		this.year = year;
	}
	
	/**
	 * @return 交易场所
	 */
	public String getTradeaddr() {
		return tradeaddr;
	}

	/**
	 * @param tradeaddr 交易场所
	 */
	public void setTradeaddr(String tradeaddr) {
		this.tradeaddr = tradeaddr;
	}

	/**
	 * @return 交易流水号
	 */
	public String getDealserial() {
		return dealserial;
	}

	/**
	 * @param dealserial 交易流水号
	 */
	public void setDealserial(String dealserial) {
		this.dealserial = dealserial;
	}

	/**
	 * @return 交易账号
	 */
	public String getTradeaccount() {
		return tradeaccount;
	}

	/**
	 * @param tradeaccount 交易账号
	 */
	public void setTradeaccount(String tradeaccount) {
		this.tradeaccount = tradeaccount;
	}

	/**
	 * @return 支付方式
	 */
	public String getPaytype() {
		return paytype;
	}

	/**
	 * @param paytype 支付方式
	 */
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	/**
	 * @return 交易平台
	 */
	public String getTradeplat() {
		return tradeplat;
	}

	/**
	 * @param tradeplat 交易平台
	 */
	public void setTradeplat(String tradeplat) {
		this.tradeplat = tradeplat;
	}

	/**
	 * @return 支付有效期
	 */
	public String getLimittime() {
		return limittime;
	}

	/**
	 * @param limittime 支付有效期
	 */
	public void setLimittime(String limittime) {
		this.limittime = limittime;
	}

	/**
	 * @return 商户号
	 */
	public String getMerId() {
		return merId;
	}

	/**
	 * @param merId 商户号
	 */
	public void setMerId(String merId) {
		this.merId = merId;
	}

	/**
	 * @return 订单号
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId 订单号
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return 订单支付状态
	 */
	public String getPaystate() {
		return paystate;
	}

	/**
	 * @param paystate 订单支付状态
	 */
	public void setPaystate(String paystate) {
		this.paystate = paystate;
	}

	/**
	 * @return POS机编号
	 */
	public String getPosid() {
		return posid;
	}

	/**
	 * @param posid POS机编号
	 */
	public void setPosid(String posid) {
		this.posid = posid;
	}

	/**
	 * @return 订单推送状态
	 */
	public String getPushstate() {
		return pushstate;
	}

	/**
	 * @param pushstate 订单推送状态
	 */
	public void setPushstate(String pushstate) {
		this.pushstate = pushstate;
	}

	/**
	 * @return 订单支付响应状态
	 */
	public String getRestate() {
		return restate;
	}

	/**
	 * @param restate 订单支付响应状态
	 */
	public void setRestate(String restate) {
		this.restate = restate;
	}

	/**
	 * @return 支付流水号
	 */
	public String getPayNo() {
		return payNo;
	}

	/**
	 * @param payNo 支付流水号 
	 */
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	/**
	 * @return 卡志号
	 */
	public String getCardsign() {
		return cardsign;
	}

	/**
	 * @param cardsign 卡志号
	 */
	public void setCardsign(String cardsign) {
		this.cardsign = cardsign;
	}
	
	/**
	 * @return 本地流水号/网银订单号
	 */
	public String getLocalNo() {
		return localNo;
	}

	/**
	 * @param localNo 本地流水号/网银订单号
	 */
	public void setLocalNo(String localNo) {
		this.localNo = localNo;
	}
	
	/**
	 * @return 商户私有域
	 */
	public String getPriv() {
		return priv;
	}

	/**
	 * @param priv 商户私有域
	 */
	public void setPriv(String priv) {
		this.priv = priv;
	}

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
     * @return 代理点名称
     */
    public String getTermName()
    {
        return termName;
    }

    /**
     * @param termName 代理点名称
     */
    public void setTermName(String termName)
    {
        this.termName = termName;
    }

    /**
     * @return 导报表标志
     */
	public String getExcelstate() {
		return excelstate;
	}
	
    /**
     * @param excelstate 导报表标志
     */
	public void setExcelstate(String excelstate) {
		this.excelstate = excelstate;
	}
	
    /**
     * @return 客户证件号码
     */
	public String getIdcard() {
		return idcard;
	}


    /**
     * @param idcard 客户证件号码
     */
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}



	/**
	 * @return 卡入库终止日期
	 */
	public String getEndtintime() {
		return endtintime;
	}

	/**
	 * @param endtintime 卡入库终止日期
	 */
	public void setEndtintime(String endtintime) {
		this.endtintime = endtintime;
	}



	/**
	 * @return 卡入库起始日期
	 */
	public String getStartintime() {
		return startintime;
	}

	/**
	 * @param startintime 卡入库起始日期
	 */
	public void setStartintime(String startintime) {
		this.startintime = startintime;
	}

}
