package com.wlt.webm.business.cmcc;

import java.io.ByteArrayOutputStream;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.tool.Constant;
import com.wlt.webm.tool.MD5;

public class CMPayRequestSignIn {
	byte[] sendMsg=null;
	/**
	 * 登陆组包
	 * @return 返回登陆组包
	 */
	public byte[] signInMsg(){
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try{
			//包头信息
			bout.write("0076".getBytes());//长度 4
			bout.write("1234567890".getBytes());//报文序列号 10
			bout.write("YDCZ".getBytes());//业务类型 4
			bout.write("000001".getBytes());//消息码 6
			//包体信息
			bout.write("00000000".getBytes());//运营商代码  8
			bout.write("    hrdt".getBytes());//企业代码 8
//			bout.write("00000000".getBytes());//运营商代码  8
//			bout.write(" test123".getBytes());//企业代码 8
			bout.write(MD5.encode(Constant.CMPaySignPWD).getBytes());//加密后企业登录密码 32
			bout.write("0010".getBytes());//协议版本号 4
		}catch(Exception e){
			Log.error("登陆组包异常");
			Log.error(e);
		}
//		Log.info("缴费组包:" + new String(bout.toByteArray()));
//		System.out.println("登陆报文:" + new String(bout.toByteArray()));
//		System.out.println(new String(bout.toByteArray()).trim().length());
		sendMsg= bout.toByteArray();
		MsgCache.sendMsgCache.add(sendMsg);//保存到发送队列
//		System.out.println("连接内容："+sendMsg);
		Log.info("连接内容:"+new String(sendMsg));
		return bout.toByteArray();
	}
	
}
