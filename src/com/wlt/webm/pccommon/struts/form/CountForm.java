package com.wlt.webm.pccommon.struts.form;

import org.apache.struts.action.ActionForm;

/**
 * ͳ�Ʊ����������<br>
 * company ���������Ƽ����޹�˾<br>
 * copyright Copyright (c) 2008<br>
 * version 3.0.0.0
 * @author ¹��
 */
public class CountForm extends ActionForm
{
    /**
     * ʵ����Count�ӿڵ�ͳ�Ʊ���������
     */
    private String beanName;
    /**
     * ��ʼʱ��
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
     * ҳ��
     */
    private int pageIndex;
    /**
     * ���ݿ�����
     */
    private String dbName;
    /**
     * ����
     */
    private String type;
    /**
     * ��ʼ����
     */
    private String startDate;
    /**
     * ��������
     */
    private String endDate;
    /**
     * ���漶��
     */
    private String afficheGradeid;
    /**
     * ״̬
     */
    private String state;
    /**
     * �ؼ���
     */
    private String keyword;
    /**
     * ҵ������
     */
    private String service;
    /**
     * ������ˮ��
     */
    private String tradeSerial;
    /**
     * ���׶���
     */
    private String tradeObject;
    /**
     * ���׶Է�
     */
    private String tradeOppos;
    
    /**
     * �ն˱��
     */
    private String resourceId;
    /**
     * ���׶���
     */
    private String tradeobject;
    
    /**
     * ͳһ֧���˺�
     */
    private String fundacct;
    /**
     * ���׶Է�
     */
    private String tradeoppos;
    
    /**
     * ��ֵ����
     */
    private String accountnbr;
    /**
     * ��ֵ�����ֹ����
     */
    private String newenddate;
    /**
     * ��ֵ�����ֹʱ��
     */
    private String newenddatetime;
    /**
     * ��ֵ����ʼ����
     */
    private String newstartdate;
    /**
     * ��ֵ����ʼʱ��
     */
    private String newstartdatetime;
    /**
     * q�ҳ�ֵ������
     */
    private String tran_seq;
    
    /**
     *q�ҳ�ֵ ��ˮ��
     */
    private String  tradeNum;
    
    /**
     * q�ҳ�ֵƽ̨״̬
     */
    private String scpFlag;
    
    /**
     * q�ҳ�ֵ��Ѷ״̬
     */
    private String tencentFlag;
    /**
     * q�ҳ�ֵ��������
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
	 * @return ���׶Է�
	 */
	public String getTradeoppos() {
		return tradeoppos;
	}

	/**
	 * @param tradeoppos ���׶Է�
	 */
	public void setTradeoppos(String tradeoppos) {
		this.tradeoppos = tradeoppos;
	}
    
    /**
     * ����״̬
     */
    private String checkState;
    
    /**
	 * ��ʼ����
	 */
	private String startdate;
	
	/**
	 * ��������
	 */
	private String enddate;
    
    /**
     * ��ʼʱ��
     */
    private String startTime;
    /**
     * ����ʱ��
     */
    private String endTime;
    /**
     * ��ʼ��
     */
    private String startyear;
    /**
     * ��ʼ��
     */
    private String startmonth;
    /**
     * ������
     */
    private String endyear;
    /**
     * ������
     */
    private String endmonth;
    /**
     * ������
     */
    private String transaction_id;
    /**
     * �Ƹ�ͨ����״̬
     */
    private String tencentflag;
    /**
     * ƽ̨����״̬
     */
    private String scpflag;
	/**
	 * ��ע˵��
	 */
	private String explain;
	/**
	 * ҵ�����
	 */
	private String dealserial;
	/**
	 * ���׶���
	 */
	private String tradeaccount;
	/**
	 * ��������
	 */
	private String tradetype;
	/**
	 * @return ��ע˵��
	 */
	public String getExplain() {
		return explain;
	}



	 /**
	 * @param explain ��ע˵��
	 */
	public void setExplain(String explain) {
		this.explain = explain;
	}
    
    /**
     * ͳһ֧�����ʺ�
     */
    private String childfacct;
    /**
     * ֧���˺�
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
	 * @return ���׶���
	 */
	public String getTradeobject() {
		return tradeobject;
	}

	/**
	 * @param tradeobject ���׶���
	 */
	public void setTradeobject(String tradeobject) {
		this.tradeobject = tradeobject;
	}
    /**
     * ������ˮ��
     */
    private String tradeserial;
	/**
	 * @return ������ˮ��
	 */
	public String getTradeserial() {
		return tradeserial;
	}

	/**
	 * @param tradeserial ������ˮ��
	 */
	public void setTradeserial(String tradeserial) {
		this.tradeserial = tradeserial;
	}
	
    /**
     * �ն˱��
     */
    private String resourceid;
    /**
     * @return �ն˱��
     */
    public String getResourceid()
    {
        return resourceid;
    }

    /**
     * @param resourceid �ն˱��
     */
    public void setResourceid(String resourceid)
    {
        this.resourceid = resourceid;
    }

    
    /**
     * ���컧���
     */
    private String useracct;
	/**
	 * @return ���컧���
	 */
	public String getUseracct() {
		return useracct;
	}

	/**
	 * @param useracct ���컧���
	 */
	public void setUseracct(String useracct) {
		this.useracct = useracct;
	}
    /**
     * ����ʱ��
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
     * @return ������
     */
    public String getEndmonth()
    {
        return endmonth;
    }

    /**
     * @param endmonth ������
     */
    public void setEndmonth(String endmonth)
    {
        this.endmonth = endmonth;
    }

    /**
     * @return ������
     */
    public String getEndyear()
    {
        return endyear;
    }

    /**
     * @param endyear ������
     */
    public void setEndyear(String endyear)
    {
        this.endyear = endyear;
    }

    /**
     * @return ��ʼ��
     */
    public String getStartmonth()
    {
        return startmonth;
    }

    /**
     * @param startmonth ��ʼ��
     */
    public void setStartmonth(String startmonth)
    {
        this.startmonth = startmonth;
    }

    /**
     * @return ��ʼ��
     */
    public String getStartyear()
    {
        return startyear;
    }

    /**
     * @param startyear ��ʼ��
     */
    public void setStartyear(String startyear)
    {
        this.startyear = startyear;
    }

    /**
	 * @return ���ʵ����Count�ӿڵ�ͳ�Ʊ���������
	 */
	public String getBeanName() {
		return beanName;
	}

	/**
	 * @param beanName ����ʵ����Count�ӿڵ�ͳ�Ʊ���������
	 */
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	/**
	 * @return ҳ��
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex ҳ��
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * @return ���ݿ�����
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * @param dbName ���ݿ�����
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * @return ���漶��
	 */
	public String getAfficheGradeid() {
		return afficheGradeid;
	}

	/**
	 * @param afficheGradeid ���漶��
	 */
	public void setAfficheGradeid(String afficheGradeid) {
		this.afficheGradeid = afficheGradeid;
	}

	/**
	 * @return ״̬
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state ״̬
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return ��������
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type ��������
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return ��������
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate ��������
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return ��ʼ����
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate ��ʼ����
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return �ؼ���
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword �ؼ���
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return ҵ������
	 */
	public String getService() {
		return service;
	}

	/**
	 * @param service ҵ������
	 */
	public void setService(String service) {
		this.service = service;
	}

	/**
	 * @return ����ʱ��
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime ����ʱ��
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return ��ʼʱ��
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime ��ʼʱ��
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return ���׶���
	 */
	public String getTradeObject() {
		return tradeObject;
	}

	/**
	 * @param tradeObject ���׶���
	 */
	public void setTradeObject(String tradeObject) {
		this.tradeObject = tradeObject;
	}

	/**
	 * @return ���׶Է�
	 */
	public String getTradeOppos() {
		return tradeOppos;
	}

	/**
	 * @param tradeOppos ���׶Է�
	 */
	public void setTradeOppos(String tradeOppos) {
		this.tradeOppos = tradeOppos;
	}

	/**
	 * @return ������ˮ��
	 */
	public String getTradeSerial() {
		return tradeSerial;
	}

	/**
	 * @param tradeSerial ������ˮ��
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
