package com.wlt.webm.business.unicom;

import java.io.ByteArrayOutputStream;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.Constant;
import com.wlt.webm.tool.MD5;

public class RequestPing {
	
	/**
	 * ������Ϣ���
	 * @return ���ؽɷ���Ϣ���
	 */
	public byte[] pingMsg(){
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try{
			String frameID=Unicom.getFrameID();
			String macStr = new String(Constant.FACTORYID)+new String(Constant.TERMINALID)+frameID+new String(Constant.UNIPASSWORD);
			byte [] macByte=MD5.encode(macStr).toUpperCase().getBytes();
			//��ͷ��Ϣ
			bout.write(Constant.TAG);
			bout.write(Constant.CMD_PING_BAGLEN);
			bout.write(Constant.VERSION);
			bout.write("2124".getBytes());
			bout.write("20124007".getBytes());
			bout.write(Constant.CMD_PING_TYPE);
			bout.write(frameID.getBytes());
			bout.write(Constant.ERRSTATUS);
			bout.write(macByte);
		}catch(Exception e){
			Log.error("ǩ������쳣");
			Log.error(e);
		}
		//Log.info("��������:" + new String(bout.toByteArray()));
		return bout.toByteArray();
	}
	
}
