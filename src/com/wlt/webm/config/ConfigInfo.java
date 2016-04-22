package com.wlt.webm.config;

/**
 * <p>Description: 配置文件信息保存类</p>
 */
public class ConfigInfo {
	
	/**
	 * 配置文件信息
	 */
	public static ConfigInfo conInfo=null;
	/**
	 * 远端IP
	 */
	private String remoteIp = "133.96.63.11";
	
	/**
	 * 远端端口
	 */
	private int remotePort = 9001;
	
	/**
	 * 超时时间
	 */
	private int timeOut = 2*60;
	
	/**
	 * 银行代号
	 */
	private String bankNo="58";
	
	/**
	 * 协议
	 */
	private String version = "1";
	
	/**
	 * 心跳间隔时间
	 */
	private int hbt=30*1000;
	
	/**
	 * 查询标志代号
	 */
	private String qrySerNo="";
	
	/**
	 * 电信缴费标准代号
	 */
	private String fillSerNo="";
	
	/**
	 * 返销标志
	 */
	private String revertSerNo="";
	
	/**
	 * 对账标志
	 */
	private String checkSerNo="";
	/**
	 * 消息控制业务编号
	 */
	private String controlTradeNo="";
	/**
	 * 消息控制业务限制数
	 */
	private String controlTradeNum="";
	/**
	 * 消息控制时间
	 */
	private String controlTradeTime="";

	
	/**
	 * @return 
	 */
	public String getControlTradeTime() {
		return controlTradeTime;
	}

	/**
	 * @param controlTradeTime 
	 */
	public void setControlTradeTime(String controlTradeTime) {
		this.controlTradeTime = controlTradeTime;
	}

	/**
	 * @return 
	 */
	public String getControlTradeNo() {
		return controlTradeNo;
	}

	/**
	 * @param controlTradeNo 
	 */
	public void setControlTradeNo(String controlTradeNo) {
		this.controlTradeNo = controlTradeNo;
	}

	/**
	 * @return 
	 */
	public String getControlTradeNum() {
		return controlTradeNum;
	}

	/**
	 * @param controlTradeNum 
	 */
	public void setControlTradeNum(String controlTradeNum) {
		this.controlTradeNum = controlTradeNum;
	}

	/**
	 * 获得银行代号
	 * @return 银行代号
	 */
	public String getBankNo() {
		return bankNo;
	}

	/**
	 * 设置银行代号
	 * @param bankNo 银行代号
	 */
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	/**
	 * 获得缴费标识
	 * @return 缴费标识
	 */
	public String getFillSerNo() {
		return fillSerNo;
	}

	/**
	 * 设置缴费标识
	 * @param fillSerNo 缴费标识
	 */
	public void setFillSerNo(String fillSerNo) {
		this.fillSerNo = fillSerNo;
	}

	/**
	 * 获得查询标识
	 * @return 查询标识
	 */
	public String getQrySerNo() {
		return qrySerNo;
	}

	/**
	 * 设置查询标识
	 * @param qrySerNo 查询标识
	 */
	public void setQrySerNo(String qrySerNo) {
		this.qrySerNo = qrySerNo;
	}

	/**
	 * 计费远端Ip
	 * @return 获得计费IP地址
	 */
	public String getRemoteIp() {
		return remoteIp;
	}

	/**
	 * 设置计费远端IP 
	 * @param remoteIp 计费远端IP
	 */
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	/**
	 * 获得计费端口
	 * @return 计费端口
	 */
	public int getRemotePort() {
		return remotePort;
	}

	/**
	 * 设置计费端口
	 * @param remotePort 计费端口
	 */
	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	/**
	 * 获得返销业务代码
	 * @return 返销业务代码
	 */
	public String getRevertSerNo() {
		return revertSerNo;
	}

	/**
	 * 设置返销业务代码
	 * @param revertSerNo 返销业务代码
	 */
	public void setRevertSerNo(String revertSerNo) {
		this.revertSerNo = revertSerNo;
	}

	/**
	 * 获得超时时间
	 * @return 超时时间
	 */
	public int getTimeOut() {
		return timeOut;
	}

	/**
	 * 设置超时时间
	 * @param timeOut 超时时间
	 */
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	/**
	 * 获得协议版本
	 * @return 协议版本
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * 设置协议版本
	 * @param version 协议版本
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * 设置对账标识
	 * @param checkSerNo 对账标识
	 */
	public String getCheckSerNo() {
		return checkSerNo;
	}

	/**
	 * 获得对账标识
	 * @param checkSerNo对账标识
	 */
	public void setCheckSerNo(String checkSerNo) {
		this.checkSerNo = checkSerNo;
	}
	
	/**
	 * 获得心跳间隔时间
	 * @return 心跳间隔时间
	 */
	public int getHbt() {
		return hbt;
	}

	/**
	 * 设置心跳间隔时间
	 * @param hbt 心跳间隔时间
	 */
	public void setHbt(int hbt) {
		this.hbt = hbt;
	}

	/**
	 * 获得配置文件单一实例
	 * @return 配置文件单一实例
	 */
	public static ConfigInfo getInstance() {
		
		if(conInfo == null){
			conInfo = new ConfigInfo();
		}
		return conInfo;
	}

}
