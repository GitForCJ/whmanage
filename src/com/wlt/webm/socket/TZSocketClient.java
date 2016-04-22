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
 * <p>Description: �����ͻ�����</p>
 */
public class TZSocketClient {

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
	private Socket mysocket = null;
	/**
	 * �����
	 */
	private OutputStream output = null;
	/**
	 * ������
	 */
	private InputStream input = null;
	 /**
     * ���ܻ�����
     */
    //private byte[] recvBuff  = new byte[6144];
    /**
	 * timeOut��ʱʱ��
	 */
	private int timeOut= 90 * 1000;
	/**
	 * ��ʼ���ɹ���־
	 */
	private boolean isConnected = false;
	

	private TZSocketClient(){
		
	}
	
	/**
	 * ���캯��
	 * @param remoteIp   Զ��IP
	 * @param port		 Զ�˶˿�
	 */
	public TZSocketClient(String remoteIp, int port) {
		this.remote = remoteIp;
		this.port = port;
	}

	/**
	 * ��ʼ�����ӷ���
	 * 
	 * @param ip
	 *            ���ӵ�IP
	 * @param port
	 *            ���ӵĶ˿�
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
            input =  new DataInputStream(mysocket.getInputStream());   //����������        
            output = new DataOutputStream(mysocket.getOutputStream());//���������
            Log.info("���ӳɹ� "+remote+":"+port);
            String mainKey=TianZuoParameters.TZ_MAINKEY;
            Log.info("TZSocketClient----mainKey:"+mainKey);
            //����ǩ����Ϣ
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
	 * ������Ϣ
	 * @param threadName 	�����߳�����
	 * @param data			��������
	 * @return			    �����ֽڸ���
	 * @throws IOException 
	 */
	public int send(byte[] data) throws IOException {
		Log.info("�������Ʒѽӿڷ��ͽ�����Ϣ");
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
	 * �ͻ��˹ر�
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
				Log.info( mysocket + "closing.....�ر�.");
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
    * ����������Ϣ
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
		byte[] endByte= new byte[Constant.MaxBuff+5];//���շ���
	    byte[] head = new byte[100] ;
	    int start=0;
	    int headln = input.read(head);
	    if(headln==-1){
	    	Log.info("û��ȡ����Ϣ......");
	    	return null;
	    }
	    System.arraycopy(head, 0, endByte, 0, headln);
	    start+=headln;
	    String headStr=new String(head);
	    Log.info("TZSocketClient���ܵı���ͷ�ֽ���["+headln+"]"+headStr);
	    int bodyLn=Integer.valueOf(headStr.substring(0, 4)).intValue();
	    byte[] body = new byte[bodyLn];
        int bodyln=input.read(body);
        System.arraycopy(body, 0, endByte, start, bodyln);
        String bodyStr=new String(body);
        Log.info("TZSocketClient���ܵı������ֽ���["+bodyln+"]"+bodyStr);
        start+=bodyln;
        byte[] sign=new byte[32];
        int signln=input.read(sign);
        System.arraycopy(sign, 0, endByte, start, signln); 
        String signStr=new String(sign);
        Log.info("TZSocketClient���ܵ���֤���ֽ���["+signln+"]"+signStr);       
        String result=new String(endByte).trim();
        
        //��֤ǩ����Ϣ
        String mainKey="";
        String msgType=result.substring(34,40);
        if("100001".equals(msgType)){
			Log.info("ǩ������......");
         mainKey=TianZuoParameters.TZ_MAINKEY;
		}
        else{
        	if(MsgCache.tzSign.size()==0){
        		Thread.sleep(3000);
        	}
    		mainKey=(String)MsgCache.tzSign.get("sign");
        	}
        Log.info("TZSocketClient ������Կ:"+mainKey);
        String clientSign=HmacMD5.crypt(result.substring(0,100+bodyLn), mainKey);
		if(!signStr.equals(HmacMD5.crypt(result.substring(0,100+bodyLn), mainKey))){
			Log.info("ǩ������   server:"+signStr+"  client:"+clientSign);
			Log.info("[TZ] SocketClient :the statue of socket,is not normal!");
			closeSocket();
			MsgCache.tzSign.clear();//����֮ǰ������ϴ�ǩ������Կ
			connect();
            return null;
		}
		Log.info("���յ���Ӧ��Ϣ����Ϊ:["+result.length()+"]"+result);
	    return result.getBytes();
	}
	
	/**
	 * �ж�Զ�̷�����Ƿ�Ͽ�������
	 * @return flag  
	 */
	public boolean isRemoteDisconection(){
		boolean flag =false;
		try {
			this.mysocket.sendUrgentData(0XFF);
		} catch (IOException e) {
			flag=true;
			Log.error("TZSocketClient:"+e);
			Log.error("Զ�̷�����Ѿ��Զ��Ͽ�������:"+this.mysocket);
		}
		return flag;
	}
	

	/**
	 * �����Ƿ�ɹ���־
	 * @return
	 */
	public boolean isConnected() {
		return isConnected;
	}
	

	/**
	 * ���socket
	 * @return socket�׽���
	 */
	public Socket getMysocket()
	{
		return this.mysocket;
	}

	/**
	 * ����socket�׽���
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
