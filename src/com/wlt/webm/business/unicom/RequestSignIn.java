package com.wlt.webm.business.unicom;

import java.io.ByteArrayOutputStream;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.Constant;
import com.wlt.webm.tool.MD5;

public class RequestSignIn {
	
	/**
	 * 缴费消息组包
	 * @return 返回缴费消息组包
	 */
	public byte[] signInMsg(){
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try{
			String frameID=Unicom.getFrameID();
			String macStr = new String(Constant.FACTORYID)+new String(Constant.TERMINALID)+frameID+new String(Constant.UNIPASSWORD);
			byte [] macByte=MD5.encode(macStr).toUpperCase().getBytes();
			//包头信息
			bout.write(Constant.TAG);
			bout.write(Constant.CMD_SIGNIN_BAGLEN);
			bout.write(Constant.VERSION);
			bout.write("2124".getBytes());
			bout.write("20124007".getBytes());
			bout.write(Constant.CMD_SIGNIN_TYPE);
			bout.write(frameID.getBytes());
			bout.write(Constant.ERRSTATUS);
			bout.write(macByte);
			//包体信息
			bout.write(Constant.SIGNINID);
			bout.write(macByte);
			//System.out.println("===联通============签到报文:" + new String(bout.toByteArray()));			
		}catch(Exception e){
			Log.error("广东联通签到组包异常");
			Log.error(e);
		}
		Log.info("=联通======签到报文:" + new String(bout.toByteArray()));
		return bout.toByteArray();
	}
	
}
