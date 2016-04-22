package com.wlt.webm.business.unicom;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.Constant;

public class ResponseHead {

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
	 * ��ͷ
	 */
	private byte[] len = new byte[4];
	
	/**
	 * ��ͷ��ˮ��
	 */
	private byte[] frameID = new byte[16];
	/**
	 * ��Ϣ���ͺ�
	 */
	private byte[] msgType = new byte[4];
	
	/**
	 * ����ͷ��Ӧ״̬
	 */
	private byte[] errStatus = new byte[4];
	
	/**
	 * MD5��֤�ַ���
	 */
	private byte[] macStr = new byte[32];
	/**
	 * Ĭ�Ϲ��캯��
	 *
	 */
	public ResponseHead(){
		
	}
	
	/**
	 * ���캯��
	 */
	public ResponseHead(byte[] receiveMsg){
		this.recvMsg = receiveMsg;
		if( recvMsg != null){
			input = new DataInputStream(new ByteArrayInputStream(recvMsg));
			deal();
		}else{
			Log.error("������ϢΪ��....");
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
			input.read(len);
			input.read(Constant.VERSION);
			input.read(Constant.FACTORYID);
			input.read(Constant.TERMINALID);
			input.read(msgType);
			input.read(frameID);
			input.read(errStatus);
			input.read(macStr);
		}catch(Exception e){
			Log.error("�����Ʒѷ��صĲ�ѯ��Ϣ����");
			Log.error(e);
		}
	}

	/**
	 * ��ͷ��ˮ��
	 * @return
	 */
	public String getFrameID() {
		return new String(frameID).trim();
	}

	/**
	 * ֧������
	 * @return
	 */
	public String getPayType() {
		return new String(Constant.UNI_NUMBER_TYPE);
	}

	/**
	 * @return ��Ϣ���ͺ�
	 */
	public String getMsgType() {
		return  new String(msgType).trim();
	}

	/**
	 * @return ��ͷ 
	 */
	public String getTag() {
		return  new String(tag).trim();
	}

	/**
	 * @return ���Ļ�Ӧ״̬
	 */
	public String getErrStatus() {
		return new String(errStatus).trim();
	}
}
