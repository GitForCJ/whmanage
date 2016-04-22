package com.wlt.webm.business.unicom;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.Constant;
import com.wlt.webm.tool.Tools;

public class ResponseFill {

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
	 * ��ͨƽ̨����ʱ��
	 */
	private byte[] srvDateTime = new byte[14];
	/**
	 * ��ͨ���ص���ˮ��
	 */
	private byte[] svrTraceID = new byte[16];
	
	/**
	 * ������ˮ��
	 */
	private byte[] reqTraceID = new byte[16];
	
	private byte[] userNumber = new byte[20];
	
	private byte[] billValue = new byte[10];
	
	private byte[] status = new byte[4];
	
	private byte[] reserve = new byte[50];
	
	/**
	 * Ĭ�Ϲ��캯��
	 *
	 */
	public ResponseFill(){
		
	}
	
	/**
	 * ���캯��
	 */
	public ResponseFill(byte[] receiveMsg){
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
			input.read(Constant.RSP_FILL_TYPE);
			input.read(frameID);
			input.read(errStatus);
			input.read(macStr);
			//����������Ϣ
			
			input.read(srvDateTime);//������ʱ��
			input.read(svrTraceID);//��������ͨ��ˮ��
			input.read(reqTraceID);//��������ͨ��ˮ��
			input.read(Constant.UNI_NUMBER_TYPE);//��������ͨ��ˮ��
			input.read(userNumber);//����ֵ�绰����
			input.read(billValue);//�ɷѽ��
			input.read(status);//��������
			input.read(reserve);//�����ֶ�
			
		}catch(Exception e){
			Log.error("�����Ʒѷ��صĲ�ѯ��Ϣ����");
			Log.error(e);
		}
	}
	
	public String getReqTraceID() {
		return new String(reqTraceID).trim();
	}

	public void setReqTraceID(byte[] reqTraceID) {
		this.reqTraceID = reqTraceID;
	}

	/**
	 * ֧�����
	 * @return
	 */
	public String getBillValue() {
		return Tools.yuanToFen(new String(billValue).trim());
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
	public String getResponseCode() {
//		return new String(responseCode).trim();
//		String code = new String(responseCode).trim();
//		if(code.equals("6750") || code.equals("6751")){
//			code = new String(status);
//		}
		return new String(status);
	}

	/**
	 * ��ͨ���ص���ˮ��
	 * @return
	 */
	public String getSvrTraceID() {
		return new String(svrTraceID).trim();
	}

	/**
	 * @return 
	 */
	public String getErrStatus() {
		return new String(errStatus).trim();
	}
}
