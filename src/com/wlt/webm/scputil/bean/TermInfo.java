/**
 * 
 */
package com.wlt.webm.scputil.bean;

/**
 * ÖÕ¶Ë×ÊÔ´ĞÅÏ¢bean
 */
public class TermInfo {

	//pos±àºÅ
	private String posid = "";
	//ÖÕ¶ËÓÃ»§±àºÅ
	private String termid = "";
	//ÖÕ¶ËÀàĞÍ(pos,pc)
	private int posType = 0;
	//°²×°µØÖ·
	private String addr = "";
	//posĞÍºÅ
	private String posModel = "";
	//psam¿¨ºÅ
	private String psam1 = "";
	//psam¿¨ºÅ
	private String psam2 = "";
	//ÊÇ·ñÅäÖÃ´òÓ¡»ú
	private int isPrint = 0;
	//×´Ì¬
	private int state = 0;
	//°ó¶¨ºÅÂë
	private String bindnbr = "";
	
	/**
	 * 
	 */
	public TermInfo() {
		super();

	}

	/**
	 * @return Returns the addr.
	 */
	public String getAddr() {
		if(addr.equals(""))
		{
			addr = " ";
		}
		return addr;
	}

	/**
	 * @param addr The addr to set.
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * @return Returns the bindnbr.
	 */
	public String getBindnbr() {
		if(bindnbr.equals(""))
		{
			bindnbr = " ";
		}
		return bindnbr;
	}

	/**
	 * @param bindnbr The bindnbr to set.
	 */
	public void setBindnbr(String bindnbr) {
		this.bindnbr = bindnbr;
	}

	/**
	 * @return Returns the isPrint.
	 */
	public int getIsPrint() {
		return isPrint;
	}

	/**
	 * @param isPrint The isPrint to set.
	 */
	public void setIsPrint(int isPrint) {
		this.isPrint = isPrint;
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
	}

	/**
	 * @return Returns the posModel.
	 */
	public String getPosModel() {
		if(posModel.equals(""))
		{
			posModel = " ";
		}
		return posModel;
	}

	/**
	 * @param posModel The posModel to set.
	 */
	public void setPosModel(String posModel) {
		this.posModel = posModel;
	}

	/**
	 * @return Returns the posType.
	 */
	public int getPosType() {
		return posType;
	}

	/**
	 * @param posType The posType to set.
	 */
	public void setPosType(int posType) {
		this.posType = posType;
	}

	/**
	 * @return Returns the psam1.
	 */
	public String getPsam1() {
		if(psam1.length() > 16)
		{
			psam1 = psam1.substring(0,16);
		}
		return psam1;
	}

	/**
	 * @param psam1 The psam1 to set.
	 */
	public void setPsam1(String psam1) {
		this.psam1 = psam1;
	}

	/**
	 * @return Returns the psam2.
	 */
	public String getPsam2() {
		if(psam2.length() > 16)
		{
			psam2 = psam2.substring(0,16);
		}
		return psam2;
	}

	/**
	 * @param psam2 The psam2 to set.
	 */
	public void setPsam2(String psam2) {
		this.psam2 = psam2;
	}

	/**
	 * @return Returns the state.
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state The state to set.
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return Returns the termid.
	 */
	public String getTermid() {
		return termid;
	}

	/**
	 * @param termid The termid to set.
	 */
	public void setTermid(String termid) {
		this.termid = termid;
	}

}
