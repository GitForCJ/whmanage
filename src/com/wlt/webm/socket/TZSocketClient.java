package com.wlt.webm.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.TZ.service.RequestSignIn;
import com.wlt.webm.business.TZ.service.TianZuoParameters;
import com.wlt.webm.business.TZ.tool.HmacMD5;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.tool.Constant;


/**
 * <p>Description: 天作客户端类</p>
 */
public class TZSocketClient {

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
	 * timeOut超时时间
	 */
	private int timeOut= 90 * 1000;
	/**
	 * 初始化成功标志
	 */
	private boolean isConnected = false;
	

	private TZSocketClient(){
		
	}
	
	/**
	 * 构造函数
	 * @param remoteIp   远端IP
	 * @param port		 远端端口
	 */
	public TZSocketClient(String remoteIp, int port) {
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
	 * @throws InterruptedException 
	 */
	public void connect() throws UnknownHostException,InterruptedException,IOException
	{
		closeSocket();
		Log.info("TZ connect remote host "+remote+":"+port);
		mysocket = new Socket(remote, port);
		mysocket.setReuseAddress(true);
		mysocket.setSoTimeout(timeOut);
		if(mysocket.isConnected())
		{
			this.isConnected = true;
            input =  new DataInputStream(mysocket.getInputStream());   //数据输入流        
            output = new DataOutputStream(mysocket.getOutputStream());//数据输出流
            Log.info("连接成功 "+remote+":"+port);
            String mainKey=TianZuoParameters.TZ_MAINKEY;
            Log.info("TZSocketClient----mainKey:"+mainKey);
            //发送签到消息
            RequestSignIn signIn = new RequestSignIn();
			byte[] sendMsg=signIn.signInMsg(mainKey);
			send(sendMsg);
		}
		else
		{
			if(mysocket!=null)
			{
				mysocket.close();
				mysocket=null;
				this.isConnected = false;
			}
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
		Log.info("向天作计费接口发送交易信息");
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
		if(sendLen>0){
			Log.info(mysocket+ " send ["+sendLen+"]"+new String(data));
		}
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
				Log.info( mysocket + "closing.....关闭.");
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
			Log.error("TZSocketClient close error"+ e);
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
	 * @throws InterruptedException 
    */
	public byte[] receive() throws IOException, InterruptedException
	{
		try {
			Thread.sleep(2*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		byte[] endByte= new byte[Constant.MaxBuff+5];//最终返回
	    byte[] head = new byte[100] ;
	    int start=0;
	    int headln = input.read(head);
	    if(headln==-1){
	    	Log.info("没读取到信息......");
	    	return null;
	    }
	    System.arraycopy(head, 0, endByte, 0, headln);
	    start+=headln;
	    String headStr=new String(head);
	    Log.info("TZSocketClient接受的报文头字节数["+headln+"]"+headStr);
	    int bodyLn=Integer.valueOf(headStr.substring(0, 4)).intValue();
	    byte[] body = new byte[bodyLn];
        int bodyln=input.read(body);
        System.arraycopy(body, 0, endByte, start, bodyln);
        String bodyStr=new String(body);
        Log.info("TZSocketClient接受的报文体字节数["+bodyln+"]"+bodyStr);
        start+=bodyln;
        byte[] sign=new byte[32];
        int signln=input.read(sign);
        System.arraycopy(sign, 0, endByte, start, signln); 
        String signStr=new String(sign);
        Log.info("TZSocketClient接受的验证码字节数["+signln+"]"+signStr);       
        String result=new String(endByte).trim();
        
        //验证签名信息
        String mainKey="";
        String msgType=result.substring(34,40);
        if("100001".equals(msgType)){
			Log.info("签到返回......");
         mainKey=TianZuoParameters.TZ_MAINKEY;
		}
        else{
        	if(MsgCache.tzSign.size()==0){
        		Thread.sleep(3000);
        	}
    		mainKey=(String)MsgCache.tzSign.get("sign");
        	}
        Log.info("TZSocketClient 工作密钥:"+mainKey);
        String clientSign=HmacMD5.crypt(result.substring(0,100+bodyLn), mainKey);
		if(!signStr.equals(HmacMD5.crypt(result.substring(0,100+bodyLn), mainKey))){
			Log.info("签名错误   server:"+signStr+"  client:"+clientSign);
			Log.info("[TZ] SocketClient :the statue of socket,is not normal!");
			closeSocket();
			MsgCache.tzSign.clear();//重连之前先清空上次签到的密钥
			connect();
            return null;
		}
		Log.info("接收到响应消息长度为:["+result.length()+"]"+result);
	    return result.getBytes();
	}
	
	/**
	 * 判断远程服务端是否断开了连接
	 * @return flag  
	 */
	public boolean isRemoteDisconection(){
		boolean flag =false;
		try {
			this.mysocket.sendUrgentData(0XFF);
		} catch (IOException e) {
			flag=true;
			Log.error("TZSocketClient:"+e);
			Log.error("远程服务端已经自动断开了连接:"+this.mysocket);
		}
		return flag;
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
