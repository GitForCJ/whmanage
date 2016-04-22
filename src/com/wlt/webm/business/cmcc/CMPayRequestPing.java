package com.wlt.webm.business.cmcc;

import java.io.ByteArrayOutputStream;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.Constant;
import com.wlt.webm.tool.Tools;

public class CMPayRequestPing {
	
	/**
	 * 心跳消息组包
	 * @return 返回缴费消息组包
	 */
	public byte[] pingMsg(){
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try{
			//包头信息
			bout.write("0024".getBytes());//报文长度	
			bout.write(Tools.getCMPayNO().getBytes());//报文序列号
			bout.write(Constant.CMPayBusinessType.getBytes());//业务类型
			bout.write("000119".getBytes());//消息码
			Log.info("签到组包");
			//System.out.println("签到组包"+bout.toByteArray());
		}catch(Exception e){
			Log.error("签到组包异常");
			Log.info("签到组包异常");
			Log.error(e);
		}
		return bout.toByteArray();
	}
	
}
