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
	 * 连接主机地址
	 */
	private String remote;
	/**
	 * 连接主机端口
	 */
	private int port;
	/**
	 * socket
	 */
	private Socket socket = null;
	/**
	 * 读取数据缓冲区
	 */
	private byte[] message  = new byte[2048];
	/**
	 * 输出流
	 */
	private OutputStream out = null;
	/**
	 * 输入流
	 */
	private InputStream ins = null;
	
	/**
	 * 包头标识
	 */
	private String bagHead = new String(TelecomMsg.head);
	/**
	 * 初始化成功标志
	 */
	private boolean initFlag = false;
	
	/**
	 * 配置文件信息
	 */
	ConfigInfo config = ConfigInfo.getInstance();
	
	/**
	 * 构造函数
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
				Log.info("Socket连接正常，无需重连。。。");
			}
		}
	}

	/**
	 * 初始化方法
	 * 
	 * @param ip
	 *            连接的IP
	 * @param port
	 *            连接的端口
	 */
	public void initSocket() {
		
		try {
			closeSocket();
			Log.info("正在连接远程服务......");
			this.remote = config.getRemoteIp();
			this.port = config.getRemotePort();
			socket = new Socket();
	        //进行连接时间设置
	        socket.connect(new InetSocketAddress(remote, port),10 * 1000);
			out = socket.getOutputStream();
			ins = socket.getInputStream();
			initFlag = true;
			Log.debug("连接服务成功[" + remote + ":" + port + "]");
		} catch (Exception e) {
			Log.error("连接服务端异常[" + remote + ":" + port + "]", e);
		}
	}

	/**
	 * 发送信息
	 * @param data
	 *            发送的数据
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
				Log.info("发送计费消息["+sendLen+"]:" + new String(data));
		} catch (Exception e) {
			
			Log.error("发送计费数据异常...", e);
		
		}
		return sendLen;
	}

	/**
	 * 接收消息
	 * @return	返回接收数据
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
				Log.info("接收计费消息[" + length + "]:" + new String(recMsg));
				int startIndex = new String(recMsg).indexOf(bagHead);
			    if(startIndex != -1)//如果有包头标识  	
			    {
			    	if(startIndex > 0)//如果最开始的不是包标识，则截取
			    	{
			    		recMsg = new String(recMsg).substring(startIndex).getBytes();
				    }   	
			    	int lastFlag = new String(recMsg).lastIndexOf(bagHead);
			    	if(lastFlag != 0)//如果出现包头的最后一个位置不是在开头，则进行分解
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
			//Log.error("接收计费数据异常", e);
			closeSocket();
		}
		return reList;
	}
	
	/**
	 * 获取初始化标志
	 * 
	 * @return  初始化标志
	 */
	public boolean getInit() {
		
		return this.initFlag;
	}

	/**
	 * 获得socket
	 * @return
	 */
	public Socket getSock()
	{
		return this.socket;
	}
	
	/**
	 * 客户端关闭
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
			Log.error("关闭客户端异常...", e);
			flag = false;
		}
		return flag;
	}
}
