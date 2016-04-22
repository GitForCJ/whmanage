package com.wlt.webm.scputil.bean;

/**
 * <p>@Title: 深圳康索特电子代办系统业务控制中心模块</p>
 */
public class TradeBean {
	
	/**
	 * 地市区号
	 */
	private String areaCode;
	
	/**
	 * 用户帐号
	 */
	private String useracct;
	
	/**
	 * 接入号
	 */
	private String accessNbr;
	
	/**
	 * 交易流水号
	 */
	private String tradeSerq;
	
	/**
	 * 终端ID
	 */
	private String posid;
	
	/**
	 * 交易对象
	 */
	private String tradeObject;
	
	/**
	 * 交易对方
	 */
	private String tradeOppos;
	
	/**
	 * 交易时间
	 */
	private String tradeTime;
	
	/**
	 * 支付帐号
	 */
	private String payAccount;
	
	/**
	 * 应支付金额
	 */
	private String duefee;
	
	/**
	 * 业务类型
	 */
	private String service;
	
	/**
	 * 实际支付金额
	 */
	private String fee;
	
	/**
	 * 交易说明
	 */
	private String explain;
	
	/**
	 * 支付方式
	 */
	private String payType;
	
	/**
	 * 交易发起平台
	 */
	private String source;
	
	/**
	 * 日志状态
	 */
	private String state;
	
	/**
	 * 状态变更时间
	 */
	private String disTime;
	
	/**
	 * 交易日志表信息集合
	 */
	private String[] tradeInf = new String[16];
	
	/**
	 * 构造方法
	 */
	public TradeBean() {
		super();
	}
	
	/**
	 * @param tradeInf The tradeInf to set.
	 */
	public void setTradeInf(String[] tradeInf) {
		this.tradeInf = tradeInf;
		
		if(tradeInf !=null && tradeInf.length==16)
		{
			this.setAcctInfo(tradeInf[0],tradeInf[1],tradeInf[2],tradeInf[3],tradeInf[4],tradeInf[5],tradeInf[6]);
			this.setTradeInf(tradeInf[7],tradeInf[8],tradeInf[9],tradeInf[10],tradeInf[11],tradeInf[12],tradeInf[13],tradeInf[14],tradeInf[15]);
		}
	}
	
	/**
	 * @return Returns the tradeInf.
	 */
	public String[] getTradeInf() {
		return this.tradeInf;
	}
	
	/**
	 * @param areaCode 地区码
	 * @param posid 终端编号
	 * @param accessNbr 接入号
	 * @param tradeSerq 交易流水号
	 * @param tradeObject 交易对象
	 * @param payAccount 支付账号
	 * @param tradeOppos 交易对方
	 */
	public void setAcctInfo(String areaCode,String posid,String accessNbr,String tradeSerq,
			String tradeObject,String payAccount,String tradeOppos)
	{
		this.setAreaCode(areaCode);
//		this.setUseracct(useracct);
		this.setPosid(posid);
		this.setAccessNbr(accessNbr);
		this.setTradeSerq(tradeSerq);
		this.setTradeObject(tradeObject);
		this.setTradeOppos(tradeOppos);
		this.setPayAccount(payAccount);
	}
	
	/**
	 * @param tradeTime 交易时间
	 * @param duefee 应支付金额
	 * @param fee 实际支付金额
	 * @param explain 备注
	 * @param payType 支付方式
	 * @param service 业务类型
	 * @param disTime 状态变更时间
	 * @param state 交易日志状态
	 * @param source 交易发起平台
	 */
	public void setTradeInf(String tradeTime,String duefee,String fee,
			String explain,String payType,String service,String disTime,String state,
			String source)
	{
		this.setTradeTime(tradeTime);
		this.setDuefee(duefee);
		this.setFee(fee);
		this.setExplain(explain);
		this.setPayType(payType);
		this.setService(service);
		this.setSource(source);
		this.setState(state);
		this.setDisTime(disTime);
	}
	
	/**
	 * @param accessNbr The accessNbr to set.
	 */
	public void setAccessNbr(String accessNbr) {
		this.accessNbr = accessNbr;
		tradeInf[2] = accessNbr;
	}
	/**
	 * @param areaCode The areaCode to set.
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
		tradeInf[0] = areaCode;
	}
	/**
	 * @param disTime The disTime to set.
	 */
	public void setDisTime(String disTime) {
		this.disTime = disTime;
		tradeInf[15] = disTime;
	}
	
	/**
	 * @return Returns the disTime.
	 */
	public String getDisTime()
	{
		return this.disTime;
	}
	/**
	 * @param duefee The duefee to set.
	 */
	public void setDuefee(String duefee) {
		this.duefee = duefee;
		tradeInf[8] = duefee;
	}
	/**
	 * @param explain The explain to set.
	 */
	public void setExplain(String explain) {
		this.explain = explain;
		tradeInf[10] = explain;
	}
	/**
	 * @param fee The fee to set.
	 */
	public void setFee(String fee) {
		this.fee = fee;
		tradeInf[9] = fee;
	}
	/**
	 * @param payAccount The payAccount to set.
	 */
	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
		tradeInf[6] = payAccount;
	}
	/**
	 * @param payType The payType to set.
	 */
	public void setPayType(String payType) {
		this.payType = payType;
		tradeInf[11] = payType;
	}
	/**
	 * @param service The service to set.
	 */
	public void setService(String service) {
		this.service = service;
		tradeInf[12] = service;
	}
	/**
	 * @param source The source to set.
	 */
	public void setSource(String source) {
		this.source = source;
		tradeInf[13] = source;
	}
	/**
	 * @param state The state to set.
	 */
	public void setState(String state) {
		this.state = state;
		tradeInf[14] = state;
	}
	
	/**
	 * @param tradeObject The tradeObject to set.
	 */
	public void setTradeObject(String tradeObject) {
		this.tradeObject = tradeObject;
		tradeInf[4] = tradeObject;
	}
	/**
	 * @param tradeOppos The tradeOppos to set.
	 */
	public void setTradeOppos(String tradeOppos) {
		this.tradeOppos = tradeOppos;
		tradeInf[5] = tradeOppos;
	}
	/**
	 * @param tradeSerq The tradeSerq to set.
	 */
	public void setTradeSerq(String tradeSerq) {
		this.tradeSerq = tradeSerq;
		tradeInf[3] = tradeSerq;
	}
	/**
	 * @param tradeTime The tradeTime to set.
	 */
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
		tradeInf[7] = tradeTime;
	}
	/**
	 * @param useracct The useracct to set.
	 */
	public void setUseracct(String useracct) {
		this.useracct = useracct;
		tradeInf[1] = useracct;
	}

	/**
	 * @return Returns the areaCode.
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @return Returns the duefee.
	 */
	public String getDuefee() {
		return duefee;
	}

	/**
	 * @return Returns the payAccount.
	 */
	public String getPayAccount() {
		return payAccount;
	}

	/**
	 * @return Returns the tradeObject.
	 */
	public String getTradeObject() {
		return tradeObject;
	}

	/**
	 * @return Returns the useracct.
	 */
	public String getUseracct() {
		return useracct;
	}

	/**
	 * @return Returns the posid.
	 */
	public String getPosid() {
		return posid;
	}

	/**
	 * @param posid The posid to set.
	 */
	public void setPosid(String posid) {
		this.posid = posid;
		tradeInf[1] = posid;
	}

	/**
	 * @return Returns the service.
	 */
	public String getService() {
		return service;
	}

	/**
	 * @return Returns the tradeSerq.
	 */
	public String getTradeSerq() {
		return tradeSerq;
	}

	/**
	 * @return Returns the accessNbr.
	 */
	public String getAccessNbr() {
		return accessNbr;
	}

	/**
	 * @return Returns the explain.
	 */
	public String getExplain() {
		return explain;
	}

	/**
	 * @return Returns the fee.
	 */
	public String getFee() {
		return fee;
	}

	/**
	 * @return Returns the payType.
	 */
	public String getPayType() {
		return payType;
	}

	/**
	 * @return Returns the source.
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @return Returns the state.
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return Returns the tradeOppos.
	 */
	public String getTradeOppos() {
		return tradeOppos;
	}

	/**
	 * @return Returns the tradeTime.
	 */
	public String getTradeTime() {
		return tradeTime;
	}

}
