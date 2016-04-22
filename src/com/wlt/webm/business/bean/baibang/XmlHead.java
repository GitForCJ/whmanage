package com.wlt.webm.business.bean.baibang;

import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author 1989
 * 
 */
//@XmlRootElement(name = "root")
public class XmlHead implements Serializable{
	private String Command="2";
	private String UserId="EN1_A61E447C7FF746A960DAA70E609577A0";
//	private String CftMerId="58";//≤‚ ‘
	private String CftMerId="323";//’˝ Ω
	private String MerchantId="1002";
	private String Version="1";
	private String ResFormat="xml";
	private String Attach="abcd";
	private String Sign;
	
	
	public XmlHead(String Attach,String Sign){
	} 
	
	public String getCommand() {
		return Command;
	}
	public void setCommand(String command) {
		Command = command;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getCftMerId() {
		return CftMerId;
	}
	public void setCftMerId(String cftMerId) {
		CftMerId = cftMerId;
	}
	public String getMerchantId() {
		return MerchantId;
	}
	public void setMerchantId(String merchantId) {
		MerchantId = merchantId;
	}
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	public String getResFormat() {
		return ResFormat;
	}
	public void setResFormat(String resFormat) {
		ResFormat = resFormat;
	}
	public String getAttach() {
		return Attach;
	}
	public void setAttach(String attach) {
		Attach = attach;
	}
	public String getSign() {
		return Sign;
	}
	public void setSign(String sign) {
		Sign = sign;
	}
	
}
