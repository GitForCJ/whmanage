package com.wlt.webm.business.unicom;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.config.ConfigInfo;
import com.wlt.webm.message.TelecomMsg;
/**
 * 
 */
public class SocketImplt {

	/**
	 * ����������ַ
	 */
	private String remote;
	/**
	 * ���������˿�
	 */
	private int port;
	/**
	 * socket
	 */
	private Socket socket = null;
	/**
	 * ��ȡ���ݻ�����
	 */
	private byte[] message  = new byte[2048];
	/**
	 * �����
	 */
	private OutputStream out = null;
	/**
	 * ������
	 */
	private InputStream ins = null;
	
	/**
	 * ��ͷ��ʶ
	 */
	private String bagHead = new String(TelecomMsg.head);
	/**
	 * ��ʼ���ɹ���־
	 */
	private boolean initFlag = false;
	
	/**
	 * �����ļ���Ϣ
	 */
	ConfigInfo config = ConfigInfo.getInstance();
	
	/**
	 * ���캯��
	 */
	public SocketImplt() {
		
		if(socket==null)
		{
			initSocket();
		}
		else
		{
			if(socket.isClosed())
			{
				initSocket();
			}
			else
			{
				Log.info("Socket������������������������");
			}
		}
	}

	/**
	 * ��ʼ������
	 * 
	 * @param ip
	 *            ���ӵ�IP
	 * @param port
	 *            ���ӵĶ˿�
	 */
	public void initSocket() {
		
		try {
			closeSocket();
			Log.info("��������Զ�̷���......");
			this.remote = config.getRemoteIp();
			this.port = config.getRemotePort();
			socket = new Socket();
	        //��������ʱ������
	        socket.connect(new InetSocketAddress(remote, port),10 * 1000);
			out = socket.getOutputStream();
			ins = socket.getInputStream();
			initFlag = true;
			Log.debug("���ӷ���ɹ�[" + remote + ":" + port + "]");
		} catch (Exception e) {
			Log.error("���ӷ�����쳣[" + remote + ":" + port + "]", e);
		}
	}

	/**
	 * ������Ϣ
	 * @param data
	 *            ���͵�����
	 */
	public int sendMess(byte[] data) {
		int sendLen = -1;
		try {
				if(socket==null || socket.isClosed() || data==null)
				{
					return -1;
				}
				out.write(data);
				out.flush();
				sendLen = data.length;
				Log.info("���ͼƷ���Ϣ["+sendLen+"]:" + new String(data));
		} catch (Exception e) {
			
			Log.error("���ͼƷ������쳣...", e);
		
		}
		return sendLen;
	}

	/**
	 * ������Ϣ
	 * @return	���ؽ�������
	 */
	public ArrayList recvMsg()
	{
		ArrayList reList= new ArrayList();
		byte [] recMsg = null;
		
		try {
				int length = ins.read(message);
				if(length<=0) 
				{
					return reList;
				}
				recMsg = new byte[length];
				System.arraycopy(message, 0, recMsg, 0, length);
				Log.info("���ռƷ���Ϣ[" + length + "]:" + new String(recMsg));
				int startIndex = new String(recMsg).indexOf(bagHead);
			    if(startIndex != -1)//����а�ͷ��ʶ  	
			    {
			    	if(startIndex > 0)//����ʼ�Ĳ��ǰ���ʶ�����ȡ
			    	{
			    		recMsg = new String(recMsg).substring(startIndex).getBytes();
				    }   	
			    	int lastFlag = new String(recMsg).lastIndexOf(bagHead);
			    	if(lastFlag != 0)//������ְ�ͷ�����һ��λ�ò����ڿ�ͷ������зֽ�
			    	{	 
				    	String[] tempstr = new String(recMsg).split("\\"+bagHead,-1);   	 
				    	for(int i = 1; i < tempstr.length; i ++)
				    	{  
				    		Log.info("Mutil MSG:" + tempstr[i]);
				    		reList.add(tempstr[i].getBytes());
				    	}
				    }
			    	else
			    	{
			    		Log.info("Singal MSG:" + new String(recMsg));
			    		reList.add(recMsg);
			    	}
			    }	
		} catch (IOException e) {
			//Log.error("���ռƷ������쳣", e);
			closeSocket();
		}
		return reList;
	}
	
	/**
	 * ��ȡ��ʼ����־
	 * 
	 * @return  ��ʼ����־
	 */
	public boolean getInit() {
		
		return this.initFlag;
	}

	/**
	 * ���socket
	 * @return
	 */
	public Socket getSock()
	{
		return this.socket;
	}
	
	/**
	 * �ͻ��˹ر�
	 */
	public boolean closeSocket() {
		boolean flag = false;
		try {
				if(socket==null || socket.isClosed())
				{
					if (out != null) {
						out.close();
						out = null;
					}
					if (ins != null) {
						ins.close();
						ins =  null;
					}
					flag = true;
					return true;
				}
				if (out != null) {
					out.close();
					out = null;
				}
				if (ins != null) {
					ins.close();
					ins =  null;
				}
				if (socket != null) {
					socket.close();
					socket =  null;
				}
				flag = true;
		} catch (Exception e) {
			Log.error("�رտͻ����쳣...", e);
			flag = false;
		}
		return flag;
	}
}
