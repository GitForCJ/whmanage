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
	 * socket����
	 */
	private Socket sockChannel=null;
	
	/**
	 * �Ƿ�����
	 */
	private boolean isConnected;
	/**
     * ����������
     */
    private DataInputStream input;
    /**
     * ���������
     */
    private DataOutputStream output;
    /**
     * ������ַ
     */
    private String remote = "";
    /**
     * �˿ں�
     */
    private int port = 17700;
	
	/**
	 * Ĭ�Ϲ��캯��
	 */
	public Telcom(){}
	/**
	 * ���캯��
	 */
	public Telcom(String ip, int port){
		this.remote = ip;
		this.port = port;
	}
    /**
     * ��������
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
				//����������
                input = new DataInputStream(sockChannel.getInputStream());                
                //���������
                output = new DataOutputStream(sockChannel.getOutputStream());
			}else{
				if(sockChannel!=null){
					sockChannel.close();
					sockChannel=null;
					this.isConnected = false;
				}
				Log.error("����GSM�Ʒ�����ʧ��:"+remote+":"+port);
			}
			
			} catch (UnknownHostException e) {
				Log.error("����GSM�Ʒ�����ʧ��:	"+remote+":"+port);
				Log.error(e);
				
			} catch (IOException e) {
				Log.error("����GSM�Ʒ�����ʧ�ܡ������������������");
				Log.error(e);
			} catch (Exception e) {
				Log.error(e);
			}
		
		return connected;
	}
	
	/**
	 * ����socket��ʱʱ�䣬��λ���룩
	 * @param time
	 */
	public void setTimeOut(int time){
		if(sockChannel==null) return;
		try {
			
				sockChannel.setSoTimeout(time*1000);
				
			} catch (SocketException e) {
				Log.error("GSMTelecom���ó�ʱʧ�ܣ�");
				Log.error(e);
		}
		
	}
	/**
	 * �ر����롢�������socket����
	 */
	public void socketClose(){
		try{   
				Log.info("�ر�socket����");
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
	        	
	        	Log.error("�ر�socket����ʧ��");
	        	Log.error(ex);
	        	
	        }
	}
	
	/**
     * ����������Ϣ
     */
   public void send(byte[] send){
	  try{
		  Log.info("����żƷѷ�����Ϣ:" + new String(send) + "|");
		   output.write(send);
		   output.flush();
		  }catch(Exception e){
			 Log.error("����żƷѷ������ݴ���"); 
			 Log.error(e);
		  }
   }
   
   	/**
    * ����������Ϣ
    */
	public byte[] receive_orenginal(){
		//��ȡ��Ϣ����ͷ��Ϣ
		byte [] head = new byte[4];
		//��ȡ��Ϣ����ˮ��
		byte[] serialNo = new byte[10];
		//��ȡ��Ϣ������
		byte[] length = new byte[8];
		//��ʱ���ջ�����
		byte [] recvBuff = new byte[1024];
		//��󷵻�����
		byte [] destBuff = new byte[Constant.MaxBuff+5];
		//�����������ݵĳ���
		int len=0;
		//���մ���
		int recvTime=0;
		//�������ݷ��õ�destBuff�У���ʼλ��
		int start=0;
		//ÿ��ʵ�ʽ��ܵ����ݳ���
		int rn=0;
		try {
				//��ȡ��Ϣͷ��������Ϣ����
				rn = input.read(head);
//				if( rn == 4 && head[0]==0x02 ){	
				if( rn == 4 ){	
					//��ȡ��ˮ��
					rn = input.read(serialNo);
					//��ȡ������
					rn = input.read(length);
					//����������Ϣ��������Ϣ����
					String msgLen = new String(length);
					//������Ŀ��������
					System.arraycopy(head, 0, destBuff, start, rn);
					start = start + rn;
					len = rn;
					
					//��ȡ��Ϣ��ָ�����ȵ�����,���Ͻ�����־��MACУ��λ
					int messLen = Integer.parseInt(msgLen);
					byte[] temp = new byte[messLen+2];
					rn=input.read(temp);
					Log.info("messageLen:"+messLen+"|ʵ�ʶ�ȡ����:"+rn);
					if(len>0){
						if(rn>Constant.MaxBuff)
						rn = Constant.MaxBuff;
						System.arraycopy(temp, 0, destBuff, start, rn);
						start = start + rn;
						len = len + rn;
					}
					//���ʵ�ʽ����������ڱ����г��ȣ���������գ�������3�Σ�ֱ����ʱ����
					while(len<messLen && recvTime<3){
						//Log.log("-------while-----");
						rn = input.read(recvBuff);
						if(rn>Constant.MaxBuff)
						rn = Constant.MaxBuff;
						System.arraycopy(recvBuff, 0, destBuff, start, rn);
						start = start + rn;
						len = len + rn;
						//Log.log("while�����վ�:"+ rn +"|"+ start+"|"+new String(destBuff));
						recvTime++;
					}
				}else{
					Log.info("�������ݲ���0x02��ͷ�����߳�������5"+new String(recvBuff));
					if( rn>0 ){
						recvBuff = new byte[rn];
						if(rn>Constant.MaxBuff)
						System.arraycopy(head, 0, destBuff, 0, rn);
					}
				}
				
			Log.info("���յ��żƷѵ���Ϣ��" + new String(destBuff) + "|");	
			System.out.println("���յ��żƷѵ���Ϣ��" + new String(destBuff));
			if(len>Constant.MaxBuff)
				destBuff = Tools.ByteToByte(destBuff, 0, destBuff.length-1);
			else
				destBuff = Tools.ByteToByte(destBuff, 0, len-1);	
				
			} catch (IOException e) {
				
				Log.error("���żƷѽ��������쳣��");
				
				Log.error(e);
			}finally
			{
				this.socketClose();
			}
		
		return destBuff;
	}
	
	/**
	 * ����������Ϣ
	 */
	public byte[] receive(){
		//��ȡ��Ϣ����ͷ��Ϣ
		byte [] head = new byte[4];
		//��ȡ��Ϣ����ˮ��
		byte[] serialNo = new byte[10];
		//��ȡ��Ϣ������
		byte[] length = new byte[8];
		//��ʱ���ջ�����
		byte [] recvBuff = new byte[1024];
		//��󷵻�����
		byte [] destBuff = new byte[Constant.MaxBuff+5];
		//�����������ݵĳ���
		int len = 0;
		//���մ���
		int recvTime = 0;
		//�������ݷ��õ�destBuff�У���ʼλ��
		int start = 0;
		//ÿ��ʵ�ʽ��ܵ����ݳ���
		int rn = 0;
		//��Ϣ���ܳ���
		int messLen = 0;
		try {
			//��ȡ��Ϣ��ͷ
			rn = input.read(head);
			System.arraycopy(head, 0, destBuff, 0, rn);
			start += rn;
			if( new String(head).equals("FFFF") ){
				//��ȡ��ˮ��
				rn = input.read(serialNo);
				System.arraycopy(serialNo, 0, destBuff, start, rn);
				start += rn;
				//��ȡ������
				rn = input.read(length);
				System.arraycopy(length, 0, destBuff, start, rn);
				start += rn;
				//��ȡ��Ϣ��ָ�����ȵ�����
				messLen = Integer.parseInt(new String(length));
				//��ȥǰ3���ֶΣ���ʣ��Ĳ���
				byte[] temp = new byte[messLen - 22];
				rn = input.read(temp);
				len = rn;
				Log.info("messageLen:"+messLen+"|��һ�ζ�ȡ����:"+rn);
				if(len>0){
					if(rn>Constant.MaxBuff)
						rn = Constant.MaxBuff;
					System.arraycopy(temp, 0, destBuff, start, rn);
					start = start + rn;
					len = len + 22;
				}
				//���ʵ�ʽ����������ڱ����г��ȣ���������գ�������3�Σ�ֱ����ʱ����
				while(len<messLen && recvTime<8){
					Log.info("-------while-----:"+recvTime);
					rn = input.read(recvBuff);
					if(rn>Constant.MaxBuff)
						rn = Constant.MaxBuff;
					System.arraycopy(recvBuff, 0, destBuff, start, rn);
					start = start + rn;
					len = len + rn;
					//Log.info("while�����վ�:"+ rn +"|"+ start+"|"+new String(destBuff));
					recvTime++;
				}
				Log.info("messageLen:"+messLen+"|ʵ�ʶ�ȡ����:"+len);
			}else{
				Log.info("�������ݲ���0x02��ͷ�����߳�������5"+new String(recvBuff));
				if( rn>0 ){
					recvBuff = new byte[rn];
					if(rn>Constant.MaxBuff)
						System.arraycopy(head, 0, destBuff, 0, rn);
				}
			}
			
			Log.info("���յ��żƷѵ���Ϣ��" + new String(destBuff).trim());	
			if(len>Constant.MaxBuff)
				destBuff = Tools.ByteToByte(destBuff, 0, destBuff.length-1);
			else
				destBuff = Tools.ByteToByte(destBuff, 0, len-1);	
			//�ж����ն�ȡ�����ݳ����Ƿ���Ʒѷ��ص����
			if(new String(destBuff).trim().getBytes().length < messLen){
				destBuff = "-1".getBytes();
				Log.info("��ȡ����Ϣ���Ȳ���������-1");
			}
			
		} catch (IOException e) {
			
			Log.error("���żƷѽ��������쳣��");
			
			Log.error(e);
		}finally
		{
			this.socketClose();
		}
		
		return destBuff;
	}
	
	/**
	 * �����Ƿ����ӱ�ʶ
	 * @return
	 */
	public boolean isConnected()
	{
		return this.isConnected;
	}
    
}
