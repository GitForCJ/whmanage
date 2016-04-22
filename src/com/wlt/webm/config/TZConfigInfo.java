package com.wlt.webm.config;

/**
 * <p>Description: 配置文件信息保存类</p>
 */
public class TZConfigInfo {
	
	/**
	 * 配置文件信息
	 */
	public static TZConfigInfo conInfo=null;
	/**
	 * 协议
	 */
	private String version = "1";

	/**
	 * 缴费标准代号
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

	

	public String getVersion() {
		return version;
	}



	public void setVersion(String version) {
		this.version = version;
	}



	public String getFillSerNo() {
		return fillSerNo;
	}



	public void setFillSerNo(String fillSerNo) {
		this.fillSerNo = fillSerNo;
	}



	public String getRevertSerNo() {
		return revertSerNo;
	}



	public void setRevertSerNo(String revertSerNo) {
		this.revertSerNo = revertSerNo;
	}



	public String getCheckSerNo() {
		return checkSerNo;
	}



	public void setCheckSerNo(String checkSerNo) {
		this.checkSerNo = checkSerNo;
	}

	/**
	 * 获得配置文件单一实例
	 * @return 配置文件单一实例
	 */
	public static TZConfigInfo getInstance() {
		
		if(conInfo == null){
			conInfo = new TZConfigInfo();
		}
		return conInfo;
	}

}
