package com.wlt.webm.business.TZ.service;


public class QueryBack {
	
	public String msg=null;
	public String resCode=null;
	public int bodyLength=0;
	public String body=null;
	
	public QueryBack(){
		
	}
	
	public QueryBack(String msg){
		this.msg=msg;
		this.bodyLength=Integer.parseInt(this.msg.substring(0, 4));
		this.resCode=this.msg.substring(95, 100);
		if("00000".equals(resCode)){
			this.body=msg.substring(100,100+this.bodyLength);
		}
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public int getBodyLength() {
		return bodyLength;
	}

	public void setBodyLength(int bodyLength) {
		this.bodyLength = bodyLength;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	

}
