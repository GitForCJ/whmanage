package com.wlt.webm.AccountInfo.bean;

import org.apache.struts.action.ActionForm;

public class AccountInfoBean  extends ActionForm {
	private int AccountInfoId;
	private String orderseq;//������ ϵͳ����
	private String custcode;//�ͻ�����
	private String externalid;//�ⲿID
	private String province;//ʡ����
	private String areacode;//���д���
	private String prtntype;//��ҵ����
	private String projet;//��Ŀ
	private String branchprop;//��������
	private String branchname;//�û�ȫ��
	private int bankarea;//������룬����
	private String varitype;//��֤��ʽ
	private String busitype;//���ո�����
	private String accname;//�����˻����� ����
	private String ankacct;//�����˺� ����
	private String bankinfo;//���������� ����
	private int bankcode;//���б��� ����
	private int privateflag;//�Թ���˽ 
	private int cardflag;//�������� ����
	private int certcode;//֤������ ����
	private String certno;// ֤������  ����
	private String contactphone;// ��ϵ�绰 ����
	private String contactaddr;// ��ϵ��ַ ����
	private String recvcorp;//�տλ���� ����
	private String sign_Id; //ǩԼid
	private int status;//��֤״̬ 0δ��֤ ��1��֤
	private String userno;//���û�id
	private String intime; //���ʱ��
	private String verifytime;//��֤ʱ��
	private int payType;//֧������,0��֧��
	private String remark; // �����ֶ�
	
	private String username; //�û�����
	private String usercat;//�û����֤��
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsercat() {
		return usercat;
	}
	public void setUsercat(String usercat) {
		this.usercat = usercat;
	}
	public int getAccountInfoId() {
		return AccountInfoId;
	}
	public void setAccountInfoId(int accountInfoId) {
		AccountInfoId = accountInfoId;
	}
	public String getOrderseq() {
		return orderseq;
	}
	public void setOrderseq(String orderseq) {
		this.orderseq = orderseq;
	}
	public String getCustcode() {
		return custcode;
	}
	public void setCustcode(String custcode) {
		this.custcode = custcode;
	}
	public String getExternalid() {
		return externalid;
	}
	public void setExternalid(String externalid) {
		this.externalid = externalid;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	public String getPrtntype() {
		return prtntype;
	}
	public void setPrtntype(String prtntype) {
		this.prtntype = prtntype;
	}
	public String getProjet() {
		return projet;
	}
	public void setProjet(String projet) {
		this.projet = projet;
	}
	public String getBranchprop() {
		return branchprop;
	}
	public void setBranchprop(String branchprop) {
		this.branchprop = branchprop;
	}
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	public int getBankarea() {
		return bankarea;
	}
	public void setBankarea(int bankarea) {
		this.bankarea = bankarea;
	}
	public String getVaritype() {
		return varitype;
	}
	public void setVaritype(String varitype) {
		this.varitype = varitype;
	}
	public String getBusitype() {
		return busitype;
	}
	public void setBusitype(String busitype) {
		this.busitype = busitype;
	}
	public String getAccname() {
		return accname;
	}
	public void setAccname(String accname) {
		this.accname = accname;
	}
	public String getAnkacct() {
		return ankacct;
	}
	public void setAnkacct(String ankacct) {
		this.ankacct = ankacct;
	}
	public String getBankinfo() {
		return bankinfo;
	}
	public void setBankinfo(String bankinfo) {
		this.bankinfo = bankinfo;
	}
	public int getBankcode() {
		return bankcode;
	}
	public void setBankcode(int bankcode) {
		this.bankcode = bankcode;
	}
	public int getPrivateflag() {
		return privateflag;
	}
	public void setPrivateflag(int privateflag) {
		this.privateflag = privateflag;
	}
	public int getCardflag() {
		return cardflag;
	}
	public void setCardflag(int cardflag) {
		this.cardflag = cardflag;
	}
	public int getCertcode() {
		return certcode;
	}
	public void setCertcode(int certcode) {
		this.certcode = certcode;
	}
	public String getCertno() {
		return certno;
	}
	public void setCertno(String certno) {
		this.certno = certno;
	}
	public String getContactphone() {
		return contactphone;
	}
	public void setContactphone(String contactphone) {
		this.contactphone = contactphone;
	}
	public String getContactaddr() {
		return contactaddr;
	}
	public void setContactaddr(String contactaddr) {
		this.contactaddr = contactaddr;
	}
	public String getRecvcorp() {
		return recvcorp;
	}
	public void setRecvcorp(String recvcorp) {
		this.recvcorp = recvcorp;
	}
	public String getSign_Id() {
		return sign_Id;
	}
	public void setSign_Id(String sign_Id) {
		this.sign_Id = sign_Id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public String getIntime() {
		return intime;
	}
	public void setIntime(String intime) {
		this.intime = intime;
	}
	public String getVerifytime() {
		return verifytime;
	}
	public void setVerifytime(String verifytime) {
		this.verifytime = verifytime;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
}
