package com.wlt.webm.business.unicom;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Timer;


import com.commsoft.epay.util.logging.Log;

public class ASynSocketClient {
	/**
	 * 连接主机地址
	 */
	private String remote;
	/**
	 * 连接主机端口
	 */
	private int port;
	/**
	 * timeOut超时时间
	 */
	private int timeOut= 50;
	/**
	 * 监控时间间隔（秒）
	 */
	private int monitorInter = 30;
	
	/**
	 * socket服务
	 */
	private SocketClient socketclient = null;
	/**
	 * 是否连接
	 */
	private boolean isConnected;
	/**
	 * 构造函数
	 */
	private ASynSocketClient()
	{
		
	}
	/**
	 * 构造函数
	 * @param ip
	 * @param port
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public ASynSocketClient(String ip, int port) throws UnknownHostException, IOException
	{
		this.remote = ip;
		this.port = port;
		socketclient = new SocketClient(this.remote, this.port);
		socketclient.setTimeOut(timeOut * 1000);
		socketclient.connect();
		if(socketclient.isConnected())
		{
			this.isConnected = true;
			Log.info("---------------联通计费连接成功...");
		}else{
			Log.info("---------------联通计费连接失败...");
		}
		startMonitor();
	}
	
	/**
	 * 发送消息，如果异常
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
	 * 接收消息
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
	 * 等到连接是否成功标志
	 * @return
	 */
	public boolean isConnected() {
		return isConnected;
	}
	
	/**
	 * 启动状态监控线程
	 */
	private void startMonitor()
	{
		//启动状态监控
		MonitorASynSocketClient task = new MonitorASynSocketClient(this);
		Timer timer = new Timer();
		timer.schedule(task, 1000*10, 1000*monitorInter);
		
		//启动心跳监控
		MonitorHbtTask hbt = new MonitorHbtTask(this);
		Timer timer2 = new Timer();
		timer2.schedule(hbt, 3*1000*monitorInter, 3*1000*monitorInter);
		
		//启动接受消息线程
		SocketReadThread recv = new SocketReadThread(this);
		Thread t = new Thread(recv);
		t.start();
		//启动发送消息线程
		SocketSendThread send = new SocketSendThread(this);
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
	
	public SocketClient getSocketclient() {
		return socketclient;
	}
	
	public void setSocketclient(SocketClient socketclient) {
		this.socketclient = socketclient;
	}
	
	/**
	 * 获得启动监控socket状态是否正常任务间隔时间
	 * @param monitorInter	间隔时间（秒）
	 */
	public int getMonitorInter() {
		return monitorInter;
	}
	
	/**
	 * 设置启动监控socket状态是否正常任务间隔时间
	 * @param monitorInter	间隔时间（秒）
	 */
	public void setMonitorInter(int monitorInter) {
		this.monitorInter = monitorInter;
	}
	
	/**
	 * 获得Socket 关联的 InputStream 上调用 read() 将只阻塞此时间长度
	 * @return 超时时间(秒)
	 */
	public int getTimeOut() {
		return timeOut;
	}
	/**
	 * 设置Socket 关联的 InputStream 上调用 read() 将只阻塞此时间长度
	 * @param timeOut	超时时间(秒)
	 */
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	
}
