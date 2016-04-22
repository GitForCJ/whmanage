package com.wlt.webm.business.bean.baibang;

/**
 * 
 * @author 1989
 *
 */
public class XmlRequest extends XmlHead{

	public XmlRequest(String Attach, String Sign) {
		super(Attach, Sign);
	}

	private String QueryText;

	public String getQueryText() {
		return QueryText;
	}

	public void setQueryText(String queryText) {
		QueryText = queryText;
	}
	
	
}
