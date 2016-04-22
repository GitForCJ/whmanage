package com.wlt.webm.xunjie.bean;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.ten.service.MD5Util;


		/**
		 * Ѹ�ݽӿ�
		 * @author caiSJ
		 *
		 */
public class XjDeal {
	
	public XjDeal(){
		
	}
	
	
	/**
	 * Ѹ���ύ����
	 * @param OrderNo   ������
	 * @param ArsValue  ��Ӫ�̵�ֵ
	 * @param phone     �绰����
	 * @param money     ���� ����λԪ��
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	 public int xjFill(String OrderNo,String ArsValue,String phone,String money) throws HttpException, IOException, InterruptedException{
		int flag=1;
		String signSource="ArsValue="+ArsValue+
        "&Phone="+ phone+
        "&Amount="+money+
        "&UserName="+XunJConstant.AGENT_ID+
        "&PayPwd="+XunJConstant.PWD+
        "&Key="+XunJConstant.KEY;
		
		String sign=MD5Util.MD5Encode(signSource, "UTF-8");
		
		String url=XunJConstant.FILL_URL+"OrderNo="+OrderNo+
		             "&ArsValue="+ArsValue+
		             "&Phone="+ phone+
		             "&Amount="+money+
		             "&UserName="+XunJConstant.AGENT_ID+
		             "&PayPwd="+XunJConstant.PWD+
		             "&MD5="+sign;
		System.out.println("xjֱ������:"+url);
		HttpClient client = new HttpClient();
		PostMethod post =new PostMethod(url);
	    int status = client.executeMethod(post);
	    if(status==200){
			String result = post.getResponseBodyAsString();
			System.out.println("xj��ֵ������:"+result);
			if("1".equals(result)||"9".equals(result)){
				int n=0;
				while(n<9){
					n++;
					int k=xjQuey(OrderNo);
					if(k==0){
						flag=0;//�ɹ�
						break;
					}else if(k==2){
						flag=2;  //ʧ��
						break;
					}else if(k==1){
						flag=1;//�쳣
						break;
					}
					Thread.sleep(20000);
				}
				if(n>=9){
					flag=3;//��ʱ
				}
			}
	    }else{
	    	flag=2;  //ʧ��
	    }
	    post.releaseConnection();
		((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown(); 
		if(null!=client)
		{
			client=null;
		}
	    System.out.println("��ֵ���ս��:"+flag);
		return flag;
		 
	 }
	 
	 /**
	  * Ѹ�ݶ�����ѯ
	  * @param OrderNo
	  * @return
	  * @throws HttpException
	  * @throws IOException
	  */
	 public int xjQuey(String OrderNo) throws HttpException, IOException{
			int flag=0;
			String signSource="OrderNo="+OrderNo+
	        "&UserName="+XunJConstant.AGENT_ID+
	        "&Key="+XunJConstant.KEY;
			
			String sign=MD5Util.MD5Encode(signSource, "UTF-8");
			
			String url=XunJConstant.QUERY_URL+"OrderNo="+OrderNo+
			             "&UserName="+XunJConstant.AGENT_ID+
			             "&MD5="+sign;
			System.out.println("xj��ѯ����:"+url);
			HttpClient client = new HttpClient();
			PostMethod post =new PostMethod(url);
		    int status = client.executeMethod(post);
		    if(status==200){
				String result = post.getResponseBodyAsString();
				System.out.println("xj��ѯ������:"+result);
				if("1".equals(result)){
					flag=0;//�ɹ�
				}else if("-1".equals(result)||"5".equals(result)||"9".equals(result)||"4".equals(result)||"8".equals(result)){
					flag=1;//�쳣
				}else if("3".equals(result)||"2".equals(result)){
					flag=2;//ʧ��
				}else {
					flag=3; //������ѯ
				}
				}
		    post.releaseConnection();
			((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown(); 
			if(null!=client)
			{
				client=null;
			}
			return flag;
			 
		 }
	 

	/**
	 * @param args
	 * @throws IOException 
	 * @throws HttpException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws HttpException, IOException, InterruptedException {
		XjDeal deal =new XjDeal();
		deal.xjFill("11111","1","18682033916","300000");
//		deal.xjQuey("11111");
	}

}
