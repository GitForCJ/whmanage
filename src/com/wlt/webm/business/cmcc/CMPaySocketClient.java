package com.wlt.webm.business.cmcc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBConfig;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.tool.Constant;
import com.wlt.webm.tool.Tools;


public class CMPaySocketClient {

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
	private Socket mysocket = null;
	/**
	 * 输出流
	 */
	private OutputStream output = null;
	/**
	 * 输入流
	 */
	private InputStream input = null;
	 /**
     * 接受缓冲区
     */
    //private byte[] recvBuff  = new byte[6144];
    /**
	 * timeOut处理中时间
	 */
	private int timeOut= 50 * 1000;
	/**
	 * 初始化成功标志
	 */
	private boolean isConnected = false;
	/**
	 * 
	 */
	private CMPaySocketClient(){}
	
	/**
	 * 构造函数
	 * @param remoteIp   远端IP
	 * @param port		 远端端口
	 */
	public CMPaySocketClient(String remoteIp, int port) {
		this.remote = remoteIp;
		this.port = port;
	}

	/**
	 * 初始化连接方法
	 * 
	 * @param ip
	 *            连接的IP
	 * @param port
	 *            连接的端口
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public void connect() 
	{
		try {
			closeSocket();
			//Log.info("connect re---==================---=mote host "+remote+":"+port);
			mysocket = new Socket(remote, port);
			mysocket.setReuseAddress(true);
			mysocket.setSoTimeout(timeOut);
			if(mysocket.isConnected())
			{
				//Log.info("签到===========");
				this.isConnected = true;
	            input = new DataInputStream(mysocket.getInputStream());   //数据输入流        
	            output = new DataOutputStream(mysocket.getOutputStream());//数据输出流
	            Log.info("连接成功 "+remote+":"+port);
	            //发送签到消息
	            CMPayRequestSignIn signIn = new CMPayRequestSignIn();
				byte[] sendMsg=signIn.signInMsg();
				Log.info(" 签到报文："+new String(sendMsg));
				send(sendMsg);
			}
			else
			{
				//Log.info("签到=22222==========");
				if(mysocket!=null)
				{
					mysocket.close();
					mysocket=null;
					this.isConnected = false;
				}
			}
			
		} catch (Exception e) {
			//Log.info("签到报错==========="+e.toString());
			//e.printStackTrace();
		}
		
	}
	/**
	 * 发送信息
	 * @param threadName 	发送线程名称
	 * @param data			发送数据
	 * @return			    发送字节个数
	 * @throws IOException 
	 */
	public int send(byte[] data) throws IOException {
		int sendLen = -1;
		if(mysocket==null || mysocket.isClosed() || data==null || output==null){
			closeSocket();
			return -1;
		}
		if(data==null){
			return 0;
		}
		output.write(data);
		output.flush();
		sendLen = data.length;
		Log.info("RT send success:"+new String(data));
		return sendLen;
	}
	
	/**
	 * 客户端关闭 
	 */
	public boolean closeSocket() {
		boolean flag = false;
		try {
				this.isConnected = false;
				if(mysocket==null || mysocket.isClosed())
				{
					if (output != null) {
						output.close();
						output = null;
					}
					if (input != null) {
						input.close();
						input =  null;
					}
					flag = true;
					return true;
				}
				Log.debug( mysocket + "closing......");
				if (mysocket != null) {
					mysocket.close();
					mysocket =  null;
				}
				if (output != null) {
					output.close();
					output = null;
				}
				if (input != null) {
					input.close();
					input =  null;
				}
				
				flag = true;
		} catch (Exception e) {
			Log.error( mysocket + "close error", e);
			flag = false;
		}
		return flag;
	}
	
	
	/**
    * 接收数据信息
	 * @throws IOException 
	 * @throws IOException 
	 * @throws IOException 
	 * @throws InterruptedException 
    */
	public byte[] receive() throws IOException
	{
		//读取消息报文头信息
		byte[] tag = new byte[0];
		//读取消息包长度
		byte[] length = new byte[4];
		//临时接收缓冲区
		byte[] recvBuff = new byte[1024];
		//最后返回数组
		byte[] destBuff = new byte[Constant.MaxBuff + 5];
		//接受所有数据的长度
		int len = 0;
		//接收次数
		int recvTime = 0;
		//接受数据放置到destBuff中，起始位置
		int start = 0;
		//每次实际接受的数据长度
		int rn = 0;
		//消息包总长度
		int messLen = 0;
			//读取消息包头
		rn = input.read(tag);
		if(rn==-1){//如果没有消息，则返回
			return null;
		}
		System.arraycopy(tag, 0, destBuff, 0, rn);
		start += rn;
		if (true) {
			//读取包长度
			rn = input.read(length);
			System.arraycopy(length, 0, destBuff, start, rn);
			start += rn;
			//读取消息中指定长度的数据
			messLen = Integer.parseInt(new String(length));
			//除去前2个字段，包剩余的部分
			byte[] temp = new byte[messLen - 4];
			rn = input.read(temp);
			MsgCache.addMoveHtbMsg(temp);
			len = rn;
			//Log.info("messageLen:" + messLen + "|第一次读取长度:" + rn);
			if (len > 0) {
				if (rn > Constant.MaxBuff)
					rn = Constant.MaxBuff;
				System.arraycopy(temp, 0, destBuff, start, rn);
				start = start + rn;
				len = len + 4;
			}
			//如果实际接受数据少于报文中长度，则继续接收，最多接收3次，直到处理中返回
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
			//Log.info("messageLen:" + messLen + "|实际读取长度:" + len);
		} else {
			Log.info("接受数据不以HRD开头"
					+ new String(recvBuff));
			if (rn > 0) {
				recvBuff = new byte[rn];
				if (rn > Constant.MaxBuff)
					System.arraycopy(tag, 0, destBuff, 0, rn);
			}
		}
        String mess=new String(destBuff).trim();
		Log.info("接收到的消息：" +mess);
		if (len > Constant.MaxBuff)
			destBuff = Tools.ByteToByte(destBuff, 0, destBuff.length - 1);
		else
			destBuff = Tools.ByteToByte(destBuff, 0, len - 1);
		//判断最终读取的数据长度是否跟计费返回的相符
		if (new String(destBuff).getBytes().length < messLen) {
			destBuff = "-1".getBytes();
			Log.info("读取的消息长度不够，返回-1");
		}
	return destBuff;
	}
	

	/**
	 * 连接是否成功标志
	 * @return
	 */
	public boolean isConnected() {
		return isConnected;
	}
	

	/**
	 * 获得socket
	 * @return socket套接字
	 */
	public Socket getMysocket()
	{
		return this.mysocket;
	}

	/**
	 * 设置socket套接字
	 * @param mysocket
	 */
	public void setMysocket(Socket mysocket) {
		this.mysocket = mysocket;
	}

	/**
	 * 
	 * @param timeOut
	 */
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	
	
	
	
}
