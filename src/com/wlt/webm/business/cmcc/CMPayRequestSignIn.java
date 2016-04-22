package com.wlt.webm.business.cmcc;

import java.io.ByteArrayOutputStream;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.tool.Constant;
import com.wlt.webm.tool.MD5;

public class CMPayRequestSignIn {
	byte[] sendMsg=null;
	/**
	 * ��½���
	 * @return ���ص�½���
	 */
	public byte[] signInMsg(){
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try{
			//��ͷ��Ϣ
			bout.write("0076".getBytes());//���� 4
			bout.write("1234567890".getBytes());//�������к� 10
			bout.write("YDCZ".getBytes());//ҵ������ 4
			bout.write("000001".getBytes());//��Ϣ�� 6
			//������Ϣ
			bout.write("00000000".getBytes());//��Ӫ�̴���  8
			bout.write("    hrdt".getBytes());//��ҵ���� 8
//			bout.write("00000000".getBytes());//��Ӫ�̴���  8
//			bout.write(" test123".getBytes());//��ҵ���� 8
			bout.write(MD5.encode(Constant.CMPaySignPWD).getBytes());//���ܺ���ҵ��¼���� 32
			bout.write("0010".getBytes());//Э��汾�� 4
		}catch(Exception e){
			Log.error("��½����쳣");
			Log.error(e);
		}
//		Log.info("�ɷ����:" + new String(bout.toByteArray()));
//		System.out.println("��½����:" + new String(bout.toByteArray()));
//		System.out.println(new String(bout.toByteArray()).trim().length());
		sendMsg= bout.toByteArray();
		MsgCache.sendMsgCache.add(sendMsg);//���浽���Ͷ���
//		System.out.println("�������ݣ�"+sendMsg);
		Log.info("��������:"+new String(sendMsg));
		return bout.toByteArray();
	}
	
}
