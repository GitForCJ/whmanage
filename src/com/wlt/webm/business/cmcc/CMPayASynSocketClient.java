package com.wlt.webm.business.cmcc;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Timer;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBConfig;

public class CMPayASynSocketClient {
	/**
	 * ����������ַ
	 */
	private String remote;
	/**
	 * ���������˿�
	 */
	private int port;
	/**
	 * timeOut������ʱ��
	 */
	private int timeOut= 50;
	/**
	 * ���ʱ�������룩
	 */
	private int monitorInter = 30;
	
	/**
	 * socket����
	 */
	private CMPaySocketClient socketclient = null;
	/**
	 * �Ƿ�����
	 */
	private boolean isConnected;
	/**
	 * ���캯��
	 */
	private CMPayASynSocketClient()
	{
		
	}
	/**
	 * ���캯��
	 * @param ip
	 * @param port
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public CMPayASynSocketClient(String ip, int port) throws UnknownHostException, IOException
	{
		this.remote = ip;
		this.port = port;
		socketclient = new CMPaySocketClient(this.remote, this.port);
		socketclient.setTimeOut(timeOut * 1000);
		socketclient.connect();
		//Log.info(DBConfig.getInstance().getIP());
		if(socketclient.isConnected())
		{
			this.isConnected = true;
			Log.info("��ͨ�Ʒ����ӳɹ�...");
		}else{
			Log.info("��ͨ�Ʒ�����ʧ��...");
		}
		startMonitor();
	}
	
	/**
	 * ������Ϣ������쳣
	 * @param msg
	 * @return
	 * @throws IOException 
	 * @throws IOException
	 */
	public synchronized int send(byte[] msg)
	{
		int  sendLen = -1;
		try {
				if(socketclient==null)
				{
					return -1;
				}
				sendLen  = socketclient.send(msg);
			} catch (IOException e) {
					socketclient.closeSocket();
					Log.error("ASynSocketClient| send message error!", e);
				}
		return sendLen;
	}

	/**
	 * ������Ϣ
	 * @return
	 */
	public synchronized byte[] receive()
	{
		byte[] recvMsg = null;
		try {
				if(socketclient==null)
				{
					return null;
				}
				recvMsg = socketclient.receive();
			} catch (SocketException e) {
				Log.error("ASynSocketClient| receive message error!", e);
				socketclient.closeSocket();
			} catch (SocketTimeoutException  e) {
				Log.error("ASynSocketClient| receive message error!", e);
			} catch (IOException e) {
				Log.error("ASynSocketClient| receive message error!", e);
			} 
			/*catch (InterruptedException e) {
				Log.error("ASynSocketClient| receive message error!", e);
			}*/
		return  recvMsg;
	}
	
	/**
	 * �ȵ������Ƿ�ɹ���־
	 * @return
	 */
	public boolean isConnected() {
		return isConnected;
	}
	
	/**
	 * ����״̬����߳�
	 */
	private void startMonitor()
	{
		//����״̬���
		CMPayMonitorASynSocketClient task = new CMPayMonitorASynSocketClient(this);
		Timer timer = new Timer();
		timer.schedule(task, 1000*10, 1000*monitorInter);
		//�����������
		CMPayMonitorHbtTask hbt = new CMPayMonitorHbtTask(this);
		Timer timer2 = new Timer();
		timer2.schedule(hbt, 3*1000*monitorInter, 3*1000*monitorInter);
		//����������Ϣ�߳�
		CMPaySocketReadThread recv = new CMPaySocketReadThread(this);
		Thread t = new Thread(recv);
		t.start();
		//����������Ϣ�߳�
		CMPaySocketSendThread send = new CMPaySocketSendThread(this);
		Thread t2 = new Thread(send);
		t2.start();
		
		
		
		
		
	}
	/**
	 * @throws IOException 
	 * @throws UnknownHostException 
	 *
	 */
	public void reConnect() throws UnknownHostException, IOException
	{
		socketclient.closeSocket();
		socketclient.connect();
	}
	
	public CMPaySocketClient getSocketclient() {
		return socketclient;
	}
	
	public void setSocketclient(CMPaySocketClient socketclient) {
		this.socketclient = socketclient;
	}
	
	/**
	 * ����������socket״̬�Ƿ�����������ʱ��
	 * @param monitorInter	���ʱ�䣨�룩
	 */
	public int getMonitorInter() {
		return monitorInter;
	}
	
	/**
	 * �����������socket״̬�Ƿ�����������ʱ��
	 * @param monitorInter	���ʱ�䣨�룩
	 */
	public void setMonitorInter(int monitorInter) {
		this.monitorInter = monitorInter;
	}
	
	/**
	 * ���Socket ������ InputStream �ϵ��� read() ��ֻ������ʱ�䳤��
	 * @return ������ʱ��(��)
	 */
	public int getTimeOut() {
		return timeOut;
	}
	/**
	 * ����Socket ������ InputStream �ϵ��� read() ��ֻ������ʱ�䳤��
	 * @param timeOut	������ʱ��(��)
	 */
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	
}
