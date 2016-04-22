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
	 * socket连接
	 */
	private Socket sockChannel = null;

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
	private int port = 24903;
	
	/**
	 * 联通自增流水号
	 */
	private static long seq = 0;
	
	/**
	 * 包头流水号
	 */
	private static int frameID = 0;
	/**
	 * 默认构造函数
	 */
	public Unicom() {
	}

	/**
	 * 构造函数
	 */
	public Unicom(String ip, int port) {
		this.remote = ip;
		this.port = port;
	}

	/**
	 * 连接主机
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
				//数据输入流
				input = new DataInputStream(sockChannel.getInputStream());
				//数据输出流
				output = new DataOutputStream(sockChannel.getOutputStream());
			} else {
				if (sockChannel != null) {
					sockChannel.close();
					sockChannel = null;
					this.isConnected = false;
				}
				Log.error("创建联通连接失败:" + remote + ":" + port);
				//System.out.println("连接联通失败:	" + remote + ":" + port);
			}

		} catch (UnknownHostException e) {
			Log.error("连接联通失败:	" + remote + ":" + port);
			//System.out.println("连接联通失败:	" + remote + ":" + port);
			//System.out.println(e);
			Log.error(e);

		} catch (IOException e) {

			Log.error("连接联通主机失败、创建输入输出流错误！");
			//System.out.println(e);
			Log.error(e);
		} catch (Exception e) {
			//System.out.println(e);
		}

		return connected;
	}

	/**
	 * 设置socket超时时间，单位（秒）
	 * @param time
	 */
	public void setTimeOut(int time) {
		if (sockChannel == null)
			return;
		try {

			sockChannel.setSoTimeout(time * 1000);

		} catch (SocketException e) {
			Log.error("联通设置超时失败！");
			Log.error(e);
		}

	}

	/**
	 * 关闭输入、输出流、socket连接
	 */
	public void socketClose() {
		try {
			Log.info("关闭socket连接");
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
			Log.info("关闭socket连接失败");
			ex.printStackTrace();
			//	        	Log.error("关闭socket连接失败");
			//	        	Log.error(ex);

		}
	}

	/**
	 * 发送数据信息
	 */
	public void send(byte[] send) {
		try {
			Log.info("向联通发送消息:" + new String(send) + "|");
			output.write(send);
			output.flush();
		} catch (Exception e) {
			Log.info("向联通发送数据错误！");
			e.printStackTrace();
			//			 Log.error(e);
		}
	}

	/**
	 * 接收数据信息
	 */
	public byte[] receive() {
		//读取消息报文头信息
		byte[] tag = new byte[2];
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
		try {
			//读取消息包头
			rn = input.read(tag);
			System.arraycopy(tag, 0, destBuff, 0, rn);
			start += rn;
			if (new String(tag).equals("WT")) {
				//读取包长度
				rn = input.read(length);
				System.arraycopy(length, 0, destBuff, start, rn);
				start += rn;
				//读取消息中指定长度的数据
				messLen = Integer.parseInt(new String(length));
				//除去前2个字段，包剩余的部分
				byte[] temp = new byte[messLen - 6];
				rn = input.read(temp);
				len = rn;
				Log.info("messageLen:" + messLen + "|第一次读取长度:" + rn);
				if (len > 0) {
					if (rn > Constant.MaxBuff)
						rn = Constant.MaxBuff;
					System.arraycopy(temp, 0, destBuff, start, rn);
					start = start + rn;
					len = len + 6;
				}
				//如果实际接受数据少于报文中长度，则继续接收，最多接收3次，直到超时返回
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
				Log.info("messageLen:" + messLen + "|实际读取长度:" + len);
			} else {
				Log.info("接受数据不以WT开头1"
						+ new String(recvBuff));
				if (rn > 0) {
					recvBuff = new byte[rn];
					if (rn > Constant.MaxBuff)
						System.arraycopy(tag, 0, destBuff, 0, rn);
				}
			}
            String mess=new String(destBuff).trim();
			Log.info("接收联通的消息：" +mess);
			if (len > Constant.MaxBuff)
				destBuff = Tools.ByteToByte(destBuff, 0, destBuff.length - 1);
			else
				destBuff = Tools.ByteToByte(destBuff, 0, len - 1);
			//判断最终读取的数据长度是否跟计费返回的相符
			if (new String(destBuff).getBytes().length < messLen) {
				destBuff = "-1".getBytes();
				Log.info("读取的消息长度不够，返回-1");
			}

		} catch (IOException e) {
			Log.info("联通接收数据异常!"+e);
			Log.error(e);
		} finally {
			//this.socketClose();
		}

		return destBuff;
	}

	/**
	 * 返回是否连接标识
	 * @return
	 */
	public boolean isConnected() {
		return this.isConnected;
	}
	
	/**
	 * 获取自增流水号
	 * 
	 * @return string 流水号 12位
	 */
	public static synchronized String getSerial() {
		String date = Tools.getNow().substring(4);		
		// 流水号自增
		seq++;
		// 如果大于99，则初始化为0
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
	 * MD5加密字符串
	 * @param FactoryID+TerminalID+FrameID+UserKey
	 * @return
	 */
	public static byte[] getMacStr(String str){
		return MD5.encode(str).toUpperCase().getBytes();
	}
}
