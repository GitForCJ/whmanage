package com.wlt.webm.config;

/**
 * <p>Description: �����ļ���Ϣ������</p>
 */
public class TZConfigInfo {
	
	/**
	 * �����ļ���Ϣ
	 */
	public static TZConfigInfo conInfo=null;
	/**
	 * Э��
	 */
	private String version = "1";

	/**
	 * �ɷѱ�׼����
	 */
	private String fillSerNo="";
	
	/**
	 * ������־
	 */
	private String revertSerNo="";
	
	/**
	 * ���˱�־
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
	 * ��������ļ���һʵ��
	 * @return �����ļ���һʵ��
	 */
	public static TZConfigInfo getInstance() {
		
		if(conInfo == null){
			conInfo = new TZConfigInfo();
		}
		return conInfo;
	}

}
