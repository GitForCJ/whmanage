package com.wlt.webm.business.bean.zy.entity.ec001.back;

import java.util.List;
/**
 * EC001�ӷ��ص���Ϣ��Ӧʵ����
 * @author fw
 *
 */
public class EC001BackBody {
	private String ECCode;
	private String PrdOrdNum;
	private EC001BackMember backMembers;

	public String getECCode() {
		return ECCode;
	}

	public void setECCode(String eCCode) {
		ECCode = eCCode;
	}

	public String getPrdOrdNum() {
		return PrdOrdNum;
	}

	public void setPrdOrdNum(String prdOrdNum) {
		PrdOrdNum = prdOrdNum;
	}

	public EC001BackMember getBackMembers() {
		return backMembers;
	}

	public void setBackMembers(EC001BackMember backMembers) {
		this.backMembers = backMembers;
	}

	
}
