package com.wlt.webm.business.bean;

/**
 * @author adminA
 *
 */
public class TyPortPayBean 
{
	private String KEY;//
	private String MERCHANTID; // �̻���
	private String SUBMERCHANTID; // ���̻���
	private String ORDERSEQ; // ������
	private String ORDERREQTRANSEQ; // ������������ˮ��
	private String ORDERDATE; // ��������
	private int ORDERAMOUNT; // �����ܽ��
	private int PRODUCTAMOUNT; // ��Ʒ���
	private int ATTACHAMOUNT; // ���ӽ��
	private String CURTYPE; // ����
	private int ENCODETYPE; // ���ܷ�ʽ
	private String MERCHANTURL; // ǰ̨���ص�ַ
	private String BACKMERCHANTURL; // ��̨���ص�ַ
	private String ATTACH; // ������Ϣ
	private String BUSICODE; // ҵ������
	private String PRODUCTID; // ҵ���ʶ
	private String TMNUM; // �ն˺���
	private String CUSTOMERID; // �ͻ���ʶ
	private String PRODUCTDESC; // ��Ʒ����
	private String MAC; // MACУ����
	private String DIVDETAILS; // ������ϸ
	private String PEDCNT; // ������
	private String GMTOVERTIME; // �����ر�ʱ��
	private String GOODPAYTYPE; // ��Ʒ��������
	private String GOODSCODE; // ��Ʒ����
	private String GOODSNAME; // ��Ʒ����
	private int GOODSNUM; // ��Ʒ����
	private String CLIENTIP; // �ͻ���IP
	
	
	private String BANKID;//���б���
	
	public String getBANKID() {
		return BANKID;
	}

	public void setBANKID(String bankid) {
		BANKID = bankid;
	}

	public TyPortPayBean()
	{
		this.setKEY("B684022912EF58A285D9B2BAD1001B39A78EEED47A2A12B0");
//		this.setCLIENTIP("220.231.192.42");
		this.setMERCHANTID("02440101040142000");//MERCHANTID	�̻���
		this.setMERCHANTURL("http://www.wanhuipay.com/AccountInfo/okInfo.jsp");//ǰ̨֪ͨ��ַ
		this.setBACKMERCHANTURL("http://www.wanhuipay.com/typortinfo.do");//��̨�����ַ
		//����
//		this.setBACKMERCHANTURL("http://shijianqiao321a.xicp.net:8888/wh/typortinfo.do");
//		this.setMERCHANTURL("http://shijianqiao321a.xicp.net:8888/wh/AccountInfo/okInfo.jsp");//ǰ̨֪ͨ��ַ
	}
	
	public String getMERCHANTID() {
		return MERCHANTID;
	}
	public void setMERCHANTID(String merchantid) {
		MERCHANTID = merchantid;
	}
	public String getSUBMERCHANTID() {
		return SUBMERCHANTID;
	}
	public void setSUBMERCHANTID(String submerchantid) {
		SUBMERCHANTID = submerchantid;
	}
	public String getORDERSEQ() {
		return ORDERSEQ;
	}
	public void setORDERSEQ(String orderseq) {
		ORDERSEQ = orderseq;
	}
	public String getORDERREQTRANSEQ() {
		return ORDERREQTRANSEQ;
	}
	public void setORDERREQTRANSEQ(String orderreqtranseq) {
		ORDERREQTRANSEQ = orderreqtranseq;
	}
	public String getORDERDATE() {
		return ORDERDATE;
	}
	public void setORDERDATE(String orderdate) {
		ORDERDATE = orderdate;
	}
	public int getORDERAMOUNT() {
		return ORDERAMOUNT;
	}
	public void setORDERAMOUNT(int orderamount) {
		ORDERAMOUNT = orderamount;
	}
	public int getPRODUCTAMOUNT() {
		return PRODUCTAMOUNT;
	}
	public void setPRODUCTAMOUNT(int productamount) {
		PRODUCTAMOUNT = productamount;
	}
	public int getATTACHAMOUNT() {
		return ATTACHAMOUNT;
	}
	public void setATTACHAMOUNT(int attachamount) {
		ATTACHAMOUNT = attachamount;
	}
	public String getCURTYPE() {
		return CURTYPE;
	}
	public void setCURTYPE(String curtype) {
		CURTYPE = curtype;
	}
	public int getENCODETYPE() {
		return ENCODETYPE;
	}
	public void setENCODETYPE(int encodetype) {
		ENCODETYPE = encodetype;
	}
	public String getMERCHANTURL() {
		return MERCHANTURL;
	}
	public void setMERCHANTURL(String merchanturl) {
		MERCHANTURL = merchanturl;
	}
	public String getBACKMERCHANTURL() {
		return BACKMERCHANTURL;
	}
	public void setBACKMERCHANTURL(String backmerchanturl) {
		BACKMERCHANTURL = backmerchanturl;
	}
	public String getATTACH() {
		return ATTACH;
	}
	public void setATTACH(String attach) {
		ATTACH = attach;
	}
	public String getBUSICODE() {
		return BUSICODE;
	}
	public void setBUSICODE(String busicode) {
		BUSICODE = busicode;
	}
	public String getPRODUCTID() {
		return PRODUCTID;
	}
	public void setPRODUCTID(String productid) {
		PRODUCTID = productid;
	}
	public String getTMNUM() {
		return TMNUM;
	}
	public void setTMNUM(String tmnum) {
		TMNUM = tmnum;
	}
	public String getCUSTOMERID() {
		return CUSTOMERID;
	}
	public void setCUSTOMERID(String customerid) {
		CUSTOMERID = customerid;
	}
	public String getPRODUCTDESC() {
		return PRODUCTDESC;
	}
	public void setPRODUCTDESC(String productdesc) {
		PRODUCTDESC = productdesc;
	}
	public String getMAC() {
		return MAC;
	}
	public void setMAC(String mac) {
		MAC = mac;
	}
	public String getDIVDETAILS() {
		return DIVDETAILS;
	}
	public void setDIVDETAILS(String divdetails) {
		DIVDETAILS = divdetails;
	}
	public String getPEDCNT() {
		return PEDCNT;
	}
	public void setPEDCNT(String pedcnt) {
		PEDCNT = pedcnt;
	}
	public String getGMTOVERTIME() {
		return GMTOVERTIME;
	}
	public void setGMTOVERTIME(String gmtovertime) {
		GMTOVERTIME = gmtovertime;
	}
	public String getGOODPAYTYPE() {
		return GOODPAYTYPE;
	}
	public void setGOODPAYTYPE(String goodpaytype) {
		GOODPAYTYPE = goodpaytype;
	}
	public String getGOODSCODE() {
		return GOODSCODE;
	}
	public void setGOODSCODE(String goodscode) {
		GOODSCODE = goodscode;
	}
	public String getGOODSNAME() {
		return GOODSNAME;
	}
	public void setGOODSNAME(String goodsname) {
		GOODSNAME = goodsname;
	}
	public int getGOODSNUM() {
		return GOODSNUM;
	}
	public void setGOODSNUM(int goodsnum) {
		GOODSNUM = goodsnum;
	}
	public String getCLIENTIP() {
		return CLIENTIP;
	}
	public void setCLIENTIP(String clientip) {
		CLIENTIP = clientip;
	}

	public String getKEY() {
		return KEY;
	}

	public void setKEY(String key) {
		KEY = key;
	}
}
