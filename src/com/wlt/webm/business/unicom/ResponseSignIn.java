package com.wlt.webm.business.unicom;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.Constant;

public class ResponseSignIn {

	/**
	 * ���ص�������Ϣ
	 */
	private byte[] recvMsg;
	
	/**
	 * ����������
	 */
	private DataInputStream input;
	
	/**
	 * ��ͷ
	 */
	private byte[] tag = new byte[2];
	
	/**
	 * ��ֵ/�ɷ�����
	 */
	private byte[] messageID = new byte[4]; 
	
	/**
	 * ��ͷ��ˮ��
	 */
	private byte[] frameID = new byte[16];
	
	/**
	 * ���Ļ�Ӧ״̬
	 */
	private byte[] errStatus = new byte[4];
	
	/**
	 * MD5��֤�ַ���
	 */
	private byte[] macStr = new byte[32];
	
	/**
	 * eƱͨƽ̨����ʱ��
	 */
	private byte[] srvDateTime = new byte[14];
	
	/**
	 * ������ˮ��
	 */
	private byte[] status = new byte[4];
	
	/**
	 * ������Ϣ˵��
	 */
	private byte[] reserve = new byte[8];
	
	/**
	 * Ĭ�Ϲ��캯��
	 *
	 */
	public ResponseSignIn(){
		
	}
	
	/**
	 * ���캯��
	 */
	public ResponseSignIn(byte[] receiveMsg){
		this.recvMsg = receiveMsg;
		if( recvMsg != null){
			input = new DataInputStream(new ByteArrayInputStream(recvMsg));
			deal();
		}else{
			status=null;
		}
	}
	
	/**
	 * �����յ��Ľɷѷ�����Ϣ
	 *
	 */
	public void deal(){
		try{
			//������ͷ��Ϣ
			input.read(tag);
			input.read(Constant.RSP_FILL_BAGLEN);
			input.read(Constant.VERSION);
			input.read(Constant.FACTORYID);
			input.read(Constant.TERMINALID);
			input.read(messageID);
			input.read(frameID);
			input.read(errStatus);
			input.read(macStr);
			//����������Ϣ
			
			input.read(status);//��������
			input.read(srvDateTime);//������ʱ��
			input.read(reserve);//�����ֶ�
		}catch(Exception e){
			Log.error("�����Ʒѷ��صĲ�ѯ��Ϣ����");
			Log.error(e);
		}
	}
	
	/**
	 * ������˵��
	 * @return
	 */
	public String getRreserve() {
		return new String(reserve).trim();
	}

	/**
	 * ����������
	 * @return
	 */
	public String getStatus() {
		return new String(status).trim();
	}
}
