package com.wlt.webm.business.bean.zy.entity.ec001;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class EC001 {
	/**
	 * ECÆóÒµ´úÂë
	 */
 private String ECCode;
 private String PrdOrdNum;
 private  EC001Member Member;// 

public  String getBody(String rootElement,String ECCode,String PrdOrdNum){
	Document document=DocumentHelper.createDocument();
	Element MemberShipRequest= DocumentHelper.createElement(rootElement);
	Element BODY=MemberShipRequest.addElement("BODY");
	document.add(MemberShipRequest);
	BODY.addElement("ECCode").setText(ECCode);
	BODY.addElement("PrdOrdNum").setText(PrdOrdNum);
	
	{Element memberE=BODY.addElement("Member");	
	memberE.addElement("OptType").setText(Member.getOptType());
	memberE.addElement("PayFlag").setText(Member.getPayFlag());
	memberE.addElement("UsecyCle").setText(Member.getUsecyCle());
	memberE.addElement("Mobile").setText(Member.getMobile());
	memberE.addElement("UserName").setText(Member.getUserName());
	memberE.addElement("EffType").setText(Member.getEffType());
	List<EC001PrdList> PrdList=Member.getPrdList();
	if(PrdList!=null)
	for(EC001PrdList prdlist:PrdList)	
	{   Element PrdList1=memberE.addElement("PrdList");
		PrdList1.addElement("PrdCode").setText(prdlist.getPrdCode());
		PrdList1.addElement("OptType").setText(prdlist.getOptType());
		//System.out.println(PrdList1.asXML());
		List<EC001Service> Service=prdlist.getService();
		if(Service!=null)
		for(EC001Service sevice:Service)				
		{
		Element Service1=PrdList1.addElement("Service");
		Service1.addElement("ServiceCode").setText(sevice.getServiceCode());
		List<EC001USERINFOMAP> USERINFOMAP=sevice.getUSERINFOMAP();
		//System.out.println("\t"+Service1.asXML());
		if(USERINFOMAP!=null)
		for(EC001USERINFOMAP use:USERINFOMAP)
		if(USERINFOMAP!=null){
		  Element USERINFOMAP1=Service1.addElement("USERINFOMAP");
		  USERINFOMAP1.addElement("OptType").setText(use.getOptType());
		  USERINFOMAP1.addElement("ItemName").setText(use.getItemName());
		  USERINFOMAP1.addElement("ItemValue").setText(use.getItemValue());
		 // System.out.println("\t\t"+USERINFOMAP1.asXML());
		}
	  }
	}
	}
	return document.getRootElement().asXML();
}
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
	public EC001Member getMember() {
		return Member;
	}
	public void setMember(EC001Member member) {
		Member = member;
	}
	
}
