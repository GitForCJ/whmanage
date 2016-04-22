package com.wlt.webm.business.bean.lkal;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;

/**
 * 消息处理后返回数据
 * @author 1989
 *
 */
public class DealResponse {
	
	private boolean success;
	private String resultCode;
    private String message;
    private Obj properties =new Obj();
    
    
	public void setSuccess(boolean success) {
		this.success = success;
	}


	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public void setProperties(Obj properties) {
		this.properties = properties;
	}
	
	


	public boolean isSuccess() {
		return success;
	}


	public String getResultCode() {
		return resultCode;
	}


	public String getMessage() {
		return message;
	}


	public Obj getProperties() {
		return properties;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DealResponse rs=new DealResponse();
		rs.setSuccess(true);
		rs.setResultCode("SUCCESSS");
		rs.setMessage("处理成功");
//		rs.setProperties(new Obj());
		
		System.out.println(JSON.toJSON(rs));
	}

}
