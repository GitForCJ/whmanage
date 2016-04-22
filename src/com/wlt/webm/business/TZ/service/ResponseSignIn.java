package com.wlt.webm.business.TZ.service;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.TZ.tool.DESUtil;
import com.wlt.webm.message.MsgCache;




public class ResponseSignIn {
	
	public String msg=null;
	public String resCode=null;
	public String workKey=null;
	public int bodyLength=0;
	public String body=null;
	
	public ResponseSignIn(){
		
	}
	
	public ResponseSignIn(String msg){
		this.msg=msg;
		this.bodyLength=Integer.parseInt(this.msg.substring(0, 4));
		this.resCode=this.msg.substring(95, 100);
		if("00000".equals(resCode)){
			this.body=msg.substring(100,100+this.bodyLength);
			String mainKey=TianZuoParameters.TZ_MAINKEY;
			this.workKey=DESUtil.decrypt(this.body.substring(0, 16),mainKey);
		}
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getWorkKey() {
		return workKey;
	}

	public void setWorkKey(String workKey) {
		this.workKey = workKey;
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

		public static void main(String[] args){
			String msg="0030HUARUIDA0185883920120222160839100001HUARUIDA01BBB50516FEC0BE5C  131284553885A65C5C47F3DF4B6000005F050940BD64F5B320120223160900476CE4788512C32EBE003084CA6D60EC";
			MsgCache.tzSign.put("sign","09532092");
			MsgCache.tzSign.clear();
			System.out.println(MsgCache.tzSign.size());
//			ResponseSignIn responseSignIn = new ResponseSignIn(msg);
//			MsgCache.tzSign.put("sign",responseSignIn.getWorkKey());
//			System.out.println("签到得到的工作密钥"+(String)MsgCache.tzSign.get("sign"));
		}

	
	

}
