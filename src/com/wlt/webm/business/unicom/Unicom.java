package com.wlt.webm.business.unicom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.Constant;
import com.wlt.webm.tool.MD5;
import com.wlt.webm.tool.Tools;

public class Unicom {
	/**
	 * socket����
	 */
	private Socket sockChannel = null;

	/**
	 * �Ƿ�����
	 */
	private boolean isConnected;

	/**
	 * ����������
	 */
	private DataInputStream input;

	/**
	 * ���������
	 */
	private DataOutputStream output;

	/**
	 * ������ַ
	 */
	private String remote = "";

	/**
	 * �˿ں�
	 */
	private int port = 24903;
	
	/**
	 * ��ͨ������ˮ��
	 */
	private static long seq = 0;
	
	/**
	 * ��ͷ��ˮ��
	 */
	private static int frameID = 0;
	/**
	 * Ĭ�Ϲ��캯��
	 */
	public Unicom() {
	}

	/**
	 * ���캯��
	 */
	public Unicom(String ip, int port) {
		this.remote = ip;
		this.port = port;
	}

	/**
	 * ��������
	 * @return
	 */
	public boolean connect() {
		boolean connected = false;
		try {
			//			Configuration config = ConfigParser.getConfig();
			//			HashMap configsMap = config.getConfig();
			sockChannel = new Socket(remote, port);
			sockChannel.setReuseAddress(true);
			if (sockChannel.isConnected()) {
				this.isConnected = true;
				connected = true;
				//����������
				input = new DataInputStream(sockChannel.getInputStream());
				//���������
				output = new DataOutputStream(sockChannel.getOutputStream());
			} else {
				if (sockChannel != null) {
					sockChannel.close();
					sockChannel = null;
					this.isConnected = false;
				}
				Log.error("������ͨ����ʧ��:" + remote + ":" + port);
				//System.out.println("������ͨʧ��:	" + remote + ":" + port);
			}

		} catch (UnknownHostException e) {
			Log.error("������ͨʧ��:	" + remote + ":" + port);
			//System.out.println("������ͨʧ��:	" + remote + ":" + port);
			//System.out.println(e);
			Log.error(e);

		} catch (IOException e) {

			Log.error("������ͨ����ʧ�ܡ������������������");
			//System.out.println(e);
			Log.error(e);
		} catch (Exception e) {
			//System.out.println(e);
		}

		return connected;
	}

	/**
	 * ����socket��ʱʱ�䣬��λ���룩
	 * @param time
	 */
	public void setTimeOut(int time) {
		if (sockChannel == null)
			return;
		try {

			sockChannel.setSoTimeout(time * 1000);

		} catch (SocketException e) {
			Log.error("��ͨ���ó�ʱʧ�ܣ�");
			Log.error(e);
		}

	}

	/**
	 * �ر����롢�������socket����
	 */
	public void socketClose() {
		try {
			Log.info("�ر�socket����");
			if (input != null) {
				input.close();
			}
			if (output != null) {
				output.close();
			}
			if (sockChannel != null) {
				sockChannel.close();
			}
		} catch (Exception ex) {
			Log.info("�ر�socket����ʧ��");
			ex.printStackTrace();
			//	        	Log.error("�ر�socket����ʧ��");
			//	        	Log.error(ex);

		}
	}

	/**
	 * ����������Ϣ
	 */
	public void send(byte[] send) {
		try {
			Log.info("����ͨ������Ϣ:" + new String(send) + "|");
			output.write(send);
			output.flush();
		} catch (Exception e) {
			Log.info("����ͨ�������ݴ���");
			e.printStackTrace();
			//			 Log.error(e);
		}
	}

	/**
	 * ����������Ϣ
	 */
	public byte[] receive() {
		//��ȡ��Ϣ����ͷ��Ϣ
		byte[] tag = new byte[2];
		//��ȡ��Ϣ������
		byte[] length = new byte[4];
		//��ʱ���ջ�����
		byte[] recvBuff = new byte[1024];
		//��󷵻�����
		byte[] destBuff = new byte[Constant.MaxBuff + 5];
		//�����������ݵĳ���
		int len = 0;
		//���մ���
		int recvTime = 0;
		//�������ݷ��õ�destBuff�У���ʼλ��
		int start = 0;
		//ÿ��ʵ�ʽ��ܵ����ݳ���
		int rn = 0;
		//��Ϣ���ܳ���
		int messLen = 0;
		try {
			//��ȡ��Ϣ��ͷ
			rn = input.read(tag);
			System.arraycopy(tag, 0, destBuff, 0, rn);
			start += rn;
			if (new String(tag).equals("WT")) {
				//��ȡ������
				rn = input.read(length);
				System.arraycopy(length, 0, destBuff, start, rn);
				start += rn;
				//��ȡ��Ϣ��ָ�����ȵ�����
				messLen = Integer.parseInt(new String(length));
				//��ȥǰ2���ֶΣ���ʣ��Ĳ���
				byte[] temp = new byte[messLen - 6];
				rn = input.read(temp);
				len = rn;
				Log.info("messageLen:" + messLen + "|��һ�ζ�ȡ����:" + rn);
				if (len > 0) {
					if (rn > Constant.MaxBuff)
						rn = Constant.MaxBuff;
					System.arraycopy(temp, 0, destBuff, start, rn);
					start = start + rn;
					len = len + 6;
				}
				//���ʵ�ʽ����������ڱ����г��ȣ���������գ�������3�Σ�ֱ����ʱ����
				while (len < messLen && recvTime < 3) {
					Log.info("-------while-----:" + recvTime);
					rn = input.read(recvBuff);
					if (rn > Constant.MaxBuff)
						rn = Constant.MaxBuff;
					System.arraycopy(recvBuff, 0, destBuff, start, rn);
					start = start + rn;
					len = len + rn;
					recvTime++;
				}
				Log.info("messageLen:" + messLen + "|ʵ�ʶ�ȡ����:" + len);
			} else {
				Log.info("�������ݲ���WT��ͷ1"
						+ new String(recvBuff));
				if (rn > 0) {
					recvBuff = new byte[rn];
					if (rn > Constant.MaxBuff)
						System.arraycopy(tag, 0, destBuff, 0, rn);
				}
			}
            String mess=new String(destBuff).trim();
			Log.info("������ͨ����Ϣ��" +mess);
			if (len > Constant.MaxBuff)
				destBuff = Tools.ByteToByte(destBuff, 0, destBuff.length - 1);
			else
				destBuff = Tools.ByteToByte(destBuff, 0, len - 1);
			//�ж����ն�ȡ�����ݳ����Ƿ���Ʒѷ��ص����
			if (new String(destBuff).getBytes().length < messLen) {
				destBuff = "-1".getBytes();
				Log.info("��ȡ����Ϣ���Ȳ���������-1");
			}

		} catch (IOException e) {
			Log.info("��ͨ���������쳣!"+e);
			Log.error(e);
		} finally {
			//this.socketClose();
		}

		return destBuff;
	}

	/**
	 * �����Ƿ����ӱ�ʶ
	 * @return
	 */
	public boolean isConnected() {
		return this.isConnected;
	}
	
	/**
	 * ��ȡ������ˮ��
	 * 
	 * @return string ��ˮ�� 12λ
	 */
	public static synchronized String getSerial() {
		String date = Tools.getNow().substring(4);		
		// ��ˮ������
		seq++;
		// �������99�����ʼ��Ϊ0
		if (seq > 99) {
			seq = 1;
		}
		String seqNo = Tools.add0(String.valueOf(seq), 2);
		date= date +seqNo;
		return date;
	}
	
	
	public static synchronized String getFrameID(){
		String date = Tools.getNow().substring(2);
		frameID ++;
		if(frameID>9999)
			frameID=1;
		String id = date+Tools.add0(String.valueOf(frameID), 4);
		return id;
	}
	/**
	 * MD5�����ַ���
	 * @param FactoryID+TerminalID+FrameID+UserKey
	 * @return
	 */
	public static byte[] getMacStr(String str){
		return MD5.encode(str).toUpperCase().getBytes();
	}
}
