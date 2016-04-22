package com.wlt.webm.config;

/**
 * <p>Description: �����ļ���Ϣ������</p>
 */
public class ConfigInfo {
	
	/**
	 * �����ļ���Ϣ
	 */
	public static ConfigInfo conInfo=null;
	/**
	 * Զ��IP
	 */
	private String remoteIp = "133.96.63.11";
	
	/**
	 * Զ�˶˿�
	 */
	private int remotePort = 9001;
	
	/**
	 * ��ʱʱ��
	 */
	private int timeOut = 2*60;
	
	/**
	 * ���д���
	 */
	private String bankNo="58";
	
	/**
	 * Э��
	 */
	private String version = "1";
	
	/**
	 * �������ʱ��
	 */
	private int hbt=30*1000;
	
	/**
	 * ��ѯ��־����
	 */
	private String qrySerNo="";
	
	/**
	 * ���Žɷѱ�׼����
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
	/**
	 * ��Ϣ����ҵ����
	 */
	private String controlTradeNo="";
	/**
	 * ��Ϣ����ҵ��������
	 */
	private String controlTradeNum="";
	/**
	 * ��Ϣ����ʱ��
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
	 * ������д���
	 * @return ���д���
	 */
	public String getBankNo() {
		return bankNo;
	}

	/**
	 * �������д���
	 * @param bankNo ���д���
	 */
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	/**
	 * ��ýɷѱ�ʶ
	 * @return �ɷѱ�ʶ
	 */
	public String getFillSerNo() {
		return fillSerNo;
	}

	/**
	 * ���ýɷѱ�ʶ
	 * @param fillSerNo �ɷѱ�ʶ
	 */
	public void setFillSerNo(String fillSerNo) {
		this.fillSerNo = fillSerNo;
	}

	/**
	 * ��ò�ѯ��ʶ
	 * @return ��ѯ��ʶ
	 */
	public String getQrySerNo() {
		return qrySerNo;
	}

	/**
	 * ���ò�ѯ��ʶ
	 * @param qrySerNo ��ѯ��ʶ
	 */
	public void setQrySerNo(String qrySerNo) {
		this.qrySerNo = qrySerNo;
	}

	/**
	 * �Ʒ�Զ��Ip
	 * @return ��üƷ�IP��ַ
	 */
	public String getRemoteIp() {
		return remoteIp;
	}

	/**
	 * ���üƷ�Զ��IP 
	 * @param remoteIp �Ʒ�Զ��IP
	 */
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	/**
	 * ��üƷѶ˿�
	 * @return �ƷѶ˿�
	 */
	public int getRemotePort() {
		return remotePort;
	}

	/**
	 * ���üƷѶ˿�
	 * @param remotePort �ƷѶ˿�
	 */
	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	/**
	 * ��÷���ҵ�����
	 * @return ����ҵ�����
	 */
	public String getRevertSerNo() {
		return revertSerNo;
	}

	/**
	 * ���÷���ҵ�����
	 * @param revertSerNo ����ҵ�����
	 */
	public void setRevertSerNo(String revertSerNo) {
		this.revertSerNo = revertSerNo;
	}

	/**
	 * ��ó�ʱʱ��
	 * @return ��ʱʱ��
	 */
	public int getTimeOut() {
		return timeOut;
	}

	/**
	 * ���ó�ʱʱ��
	 * @param timeOut ��ʱʱ��
	 */
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	/**
	 * ���Э��汾
	 * @return Э��汾
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * ����Э��汾
	 * @param version Э��汾
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * ���ö��˱�ʶ
	 * @param checkSerNo ���˱�ʶ
	 */
	public String getCheckSerNo() {
		return checkSerNo;
	}

	/**
	 * ��ö��˱�ʶ
	 * @param checkSerNo���˱�ʶ
	 */
	public void setCheckSerNo(String checkSerNo) {
		this.checkSerNo = checkSerNo;
	}
	
	/**
	 * ����������ʱ��
	 * @return �������ʱ��
	 */
	public int getHbt() {
		return hbt;
	}

	/**
	 * �����������ʱ��
	 * @param hbt �������ʱ��
	 */
	public void setHbt(int hbt) {
		this.hbt = hbt;
	}

	/**
	 * ��������ļ���һʵ��
	 * @return �����ļ���һʵ��
	 */
	public static ConfigInfo getInstance() {
		
		if(conInfo == null){
			conInfo = new ConfigInfo();
		}
		return conInfo;
	}

}
