package com.wlt.webm.socket;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Timer;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.message.MsgCache;


public class TianZuoASynSocketClient {
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
	private int timeOut= 90;
	/**
	 * 监控时间间隔（秒）
	 */
	private int monitorInter = 70;
	
	/**
	 * socket服务
	 */
	private TZSocketClient socketclient = null;
	/**
	 * 是否连接
	 */
	private boolean isConnected;
	
	
	/**
	 * 构造函数
	 */
	private TianZuoASynSocketClient()
	{
		
	}
	/**
	 * 构造函数
	 * @param ip
	 * @param port
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws InterruptedException 
	 */
	public TianZuoASynSocketClient(String ip, int port) throws UnknownHostException, InterruptedException, IOException
	{
		this.remote = ip;
		this.port = port;
		socketclient = new TZSocketClient(this.remote, this.port);
		socketclient.setTimeOut(timeOut * 1000);
		socketclient.connect();
		if(socketclient.isConnected())
		{
			this.isConnected = true;
			Log.info("天作计费连接成功...");
		}else{
			Log.info("天作计费连接失败...");
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
					Log.error("TianZuoASynSocketClient| send message error!"+ e);
				}
		return sendLen;
	}

	/**
	 * 接收消息
	 * @return
	 * @throws InterruptedException 
	 */
	public synchronized byte[] receive() throws InterruptedException
	{
		byte[] recvMsg = null;
		try {
				if(socketclient==null)
				{
					return null;
				}
				recvMsg = socketclient.receive();
			} catch (SocketException e) {
				Log.error("TianZuoASynSocketClient| receive message error!"+ e);
				socketclient.closeSocket();
			} catch (SocketTimeoutException  e) {
				Log.error("TianZuoASynSocketClient| receive message error!"+ e);
			} catch (IOException e) {
				Log.error("TianZuoASynSocketClient| receive message error!"+ e);
			} 
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
		Log.info("开始TZ监控");
		//启动状态监控
		TZMonitorASynSocketClient task = new TZMonitorASynSocketClient(this);
		Timer timer = new Timer();
		timer.schedule(task, 1000*40, 1000*30);
		//启动心跳监控
		TZMonitorHbtTask hbt = new TZMonitorHbtTask(this);
		Timer timer2 = new Timer();
		timer2.schedule(hbt, 1000*50, 1000*monitorInter);		
		//启动接受消息线程
		TZSocketReadThread recv = new TZSocketReadThread(this);
		Thread t = new Thread(recv);
		t.start();
		//启动发送消息线程
		TZSocketSendThread send = new TZSocketSendThread(this);
		Thread t2 = new Thread(send);
		t2.start();
		
		
		
		
	}
	
	/**
	 * @return true服务端自动断开客户端
	 */
	public boolean getFlag(){
		return socketclient.isRemoteDisconection();
	}
	/**
	 * @throws IOException 
	 * @throws UnknownHostException 
	 *
	 */
	public void reConnect() throws UnknownHostException, InterruptedException,IOException
	{
		Log.info("TZ开始重连");
		MsgCache.tzStatue=false;
		socketclient.connect();
	}
	
	public TZSocketClient getSocketclient() {
		return socketclient;
	}
	
	public void setSocketclient(TZSocketClient socketclient) {
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
