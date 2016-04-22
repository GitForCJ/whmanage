package com.wlt.webm.business.dianx.bean;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.wlt.webm.uictool.Constant;
import com.wlt.webm.uictool.Tools;
import com.commsoft.epay.util.logging.Log;

public class Telcom {
	/**
	 * socket连接
	 */
	private Socket sockChannel=null;
	
	/**
	 * 是否连接
	 */
	private boolean isConnected;
	/**
     * 数据输入流
     */
    private DataInputStream input;
    /**
     * 数据输出流
     */
    private DataOutputStream output;
    /**
     * 主机地址
     */
    private String remote = "";
    /**
     * 端口号
     */
    private int port = 17700;
	
	/**
	 * 默认构造函数
	 */
	public Telcom(){}
	/**
	 * 构造函数
	 */
	public Telcom(String ip, int port){
		this.remote = ip;
		this.port = port;
	}
    /**
     * 连接主机
     * @return
     */
	public boolean connect(){
		boolean connected = false;
		try {
//			sockChannel = new Socket(remote, port);
			SocketAddress socketAddress = new InetSocketAddress(remote, port);
			sockChannel = new Socket();
			sockChannel.connect(socketAddress, 8*1000);
			sockChannel.setReuseAddress(true);
			if(sockChannel.isConnected()){
				this.isConnected = true;
				connected = true;
				//数据输入流
                input = new DataInputStream(sockChannel.getInputStream());                
                //数据输出流
                output = new DataOutputStream(sockChannel.getOutputStream());
			}else{
				if(sockChannel!=null){
					sockChannel.close();
					sockChannel=null;
					this.isConnected = false;
				}
				Log.error("创建GSM计费连接失败:"+remote+":"+port);
			}
			
			} catch (UnknownHostException e) {
				Log.error("连接GSM计费主机失败:	"+remote+":"+port);
				Log.error(e);
				
			} catch (IOException e) {
				Log.error("连接GSM计费主机失败、创建输入输出流错误！");
				Log.error(e);
			} catch (Exception e) {
				Log.error(e);
			}
		
		return connected;
	}
	
	/**
	 * 设置socket超时时间，单位（秒）
	 * @param time
	 */
	public void setTimeOut(int time){
		if(sockChannel==null) return;
		try {
			
				sockChannel.setSoTimeout(time*1000);
				
			} catch (SocketException e) {
				Log.error("GSMTelecom设置超时失败！");
				Log.error(e);
		}
		
	}
	/**
	 * 关闭输入、输出流、socket连接
	 */
	public void socketClose(){
		try{   
				Log.info("关闭socket连接");
	            if(input!=null)
	            {
	                input.close();
	            }
	            if(output!=null)
	            {
	                output.close();
	            }
	            if(sockChannel!=null)
	            {
	            	sockChannel.close();
	            }
	        }catch(Exception ex){
	        	
	        	Log.error("关闭socket连接失败");
	        	Log.error(ex);
	        	
	        }
	}
	
	/**
     * 发送数据信息
     */
   public void send(byte[] send){
	  try{
		  Log.info("向电信计费发送消息:" + new String(send) + "|");
		   output.write(send);
		   output.flush();
		  }catch(Exception e){
			 Log.error("向电信计费发送数据错误！"); 
			 Log.error(e);
		  }
   }
   
   	/**
    * 接收数据信息
    */
	public byte[] receive_orenginal(){
		//读取消息报文头信息
		byte [] head = new byte[4];
		//读取消息包流水号
		byte[] serialNo = new byte[10];
		//读取消息包长度
		byte[] length = new byte[8];
		//临时接收缓冲区
		byte [] recvBuff = new byte[1024];
		//最后返回数组
		byte [] destBuff = new byte[Constant.MaxBuff+5];
		//接受所有数据的长度
		int len=0;
		//接收次数
		int recvTime=0;
		//接受数据放置到destBuff中，起始位置
		int start=0;
		//每次实际接受的数据长度
		int rn=0;
		try {
				//读取消息头，包含消息长度
				rn = input.read(head);
//				if( rn == 4 && head[0]==0x02 ){	
				if( rn == 4 ){	
					//读取流水号
					rn = input.read(serialNo);
					//读取包长度
					rn = input.read(length);
					//解析接收消息，解析消息长度
					String msgLen = new String(length);
					//拷贝到目标数组中
					System.arraycopy(head, 0, destBuff, start, rn);
					start = start + rn;
					len = rn;
					
					//读取消息中指定长度的数据,加上结束标志及MAC校验位
					int messLen = Integer.parseInt(msgLen);
					byte[] temp = new byte[messLen+2];
					rn=input.read(temp);
					Log.info("messageLen:"+messLen+"|实际读取长度:"+rn);
					if(len>0){
						if(rn>Constant.MaxBuff)
						rn = Constant.MaxBuff;
						System.arraycopy(temp, 0, destBuff, start, rn);
						start = start + rn;
						len = len + rn;
					}
					//如果实际接受数据少于报文中长度，则继续接收，最多接收3次，直到超时返回
					while(len<messLen && recvTime<3){
						//Log.log("-------while-----");
						rn = input.read(recvBuff);
						if(rn>Constant.MaxBuff)
						rn = Constant.MaxBuff;
						System.arraycopy(recvBuff, 0, destBuff, start, rn);
						start = start + rn;
						len = len + rn;
						//Log.log("while接受收据:"+ rn +"|"+ start+"|"+new String(destBuff));
						recvTime++;
					}
				}else{
					Log.info("接受数据不以0x02开头，或者长度少于5"+new String(recvBuff));
					if( rn>0 ){
						recvBuff = new byte[rn];
						if(rn>Constant.MaxBuff)
						System.arraycopy(head, 0, destBuff, 0, rn);
					}
				}
				
			Log.info("接收电信计费的消息：" + new String(destBuff) + "|");	
			System.out.println("接收电信计费的消息：" + new String(destBuff));
			if(len>Constant.MaxBuff)
				destBuff = Tools.ByteToByte(destBuff, 0, destBuff.length-1);
			else
				destBuff = Tools.ByteToByte(destBuff, 0, len-1);	
				
			} catch (IOException e) {
				
				Log.error("电信计费接收数据异常！");
				
				Log.error(e);
			}finally
			{
				this.socketClose();
			}
		
		return destBuff;
	}
	
	/**
	 * 接收数据信息
	 */
	public byte[] receive(){
		//读取消息报文头信息
		byte [] head = new byte[4];
		//读取消息包流水号
		byte[] serialNo = new byte[10];
		//读取消息包长度
		byte[] length = new byte[8];
		//临时接收缓冲区
		byte [] recvBuff = new byte[1024];
		//最后返回数组
		byte [] destBuff = new byte[Constant.MaxBuff+5];
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
		try {
			//读取消息包头
			rn = input.read(head);
			System.arraycopy(head, 0, destBuff, 0, rn);
			start += rn;
			if( new String(head).equals("FFFF") ){
				//读取流水号
				rn = input.read(serialNo);
				System.arraycopy(serialNo, 0, destBuff, start, rn);
				start += rn;
				//读取包长度
				rn = input.read(length);
				System.arraycopy(length, 0, destBuff, start, rn);
				start += rn;
				//读取消息中指定长度的数据
				messLen = Integer.parseInt(new String(length));
				//除去前3个字段，包剩余的部分
				byte[] temp = new byte[messLen - 22];
				rn = input.read(temp);
				len = rn;
				Log.info("messageLen:"+messLen+"|第一次读取长度:"+rn);
				if(len>0){
					if(rn>Constant.MaxBuff)
						rn = Constant.MaxBuff;
					System.arraycopy(temp, 0, destBuff, start, rn);
					start = start + rn;
					len = len + 22;
				}
				//如果实际接受数据少于报文中长度，则继续接收，最多接收3次，直到超时返回
				while(len<messLen && recvTime<8){
					Log.info("-------while-----:"+recvTime);
					rn = input.read(recvBuff);
					if(rn>Constant.MaxBuff)
						rn = Constant.MaxBuff;
					System.arraycopy(recvBuff, 0, destBuff, start, rn);
					start = start + rn;
					len = len + rn;
					//Log.info("while接受收据:"+ rn +"|"+ start+"|"+new String(destBuff));
					recvTime++;
				}
				Log.info("messageLen:"+messLen+"|实际读取长度:"+len);
			}else{
				Log.info("接受数据不以0x02开头，或者长度少于5"+new String(recvBuff));
				if( rn>0 ){
					recvBuff = new byte[rn];
					if(rn>Constant.MaxBuff)
						System.arraycopy(head, 0, destBuff, 0, rn);
				}
			}
			
			Log.info("接收电信计费的消息：" + new String(destBuff).trim());	
			if(len>Constant.MaxBuff)
				destBuff = Tools.ByteToByte(destBuff, 0, destBuff.length-1);
			else
				destBuff = Tools.ByteToByte(destBuff, 0, len-1);	
			//判断最终读取的数据长度是否跟计费返回的相符
			if(new String(destBuff).trim().getBytes().length < messLen){
				destBuff = "-1".getBytes();
				Log.info("读取的消息长度不够，返回-1");
			}
			
		} catch (IOException e) {
			
			Log.error("电信计费接收数据异常！");
			
			Log.error(e);
		}finally
		{
			this.socketClose();
		}
		
		return destBuff;
	}
	
	/**
	 * 返回是否连接标识
	 * @return
	 */
	public boolean isConnected()
	{
		return this.isConnected;
	}
    
}
