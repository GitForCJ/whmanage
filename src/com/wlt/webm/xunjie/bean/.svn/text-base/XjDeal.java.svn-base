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
		 * 迅捷接口
		 * @author caiSJ
		 *
		 */
public class XjDeal {
	
	public XjDeal(){
		
	}
	
	
	/**
	 * 迅捷提交订单
	 * @param OrderNo   订单号
	 * @param ArsValue  运营商的值
	 * @param phone     电话号码
	 * @param money     费用 （单位元）
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
		System.out.println("xj直冲请求:"+url);
		HttpClient client = new HttpClient();
		PostMethod post =new PostMethod(url);
	    int status = client.executeMethod(post);
	    if(status==200){
			String result = post.getResponseBodyAsString();
			System.out.println("xj充值请求结果:"+result);
			if("1".equals(result)||"9".equals(result)){
				int n=0;
				while(n<9){
					n++;
					int k=xjQuey(OrderNo);
					if(k==0){
						flag=0;//成功
						break;
					}else if(k==2){
						flag=2;  //失败
						break;
					}else if(k==1){
						flag=1;//异常
						break;
					}
					Thread.sleep(20000);
				}
				if(n>=9){
					flag=3;//超时
				}
			}
	    }else{
	    	flag=2;  //失败
	    }
	    post.releaseConnection();
		((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown(); 
		if(null!=client)
		{
			client=null;
		}
	    System.out.println("充值最终结果:"+flag);
		return flag;
		 
	 }
	 
	 /**
	  * 迅捷订单查询
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
			System.out.println("xj查询链接:"+url);
			HttpClient client = new HttpClient();
			PostMethod post =new PostMethod(url);
		    int status = client.executeMethod(post);
		    if(status==200){
				String result = post.getResponseBodyAsString();
				System.out.println("xj查询请求结果:"+result);
				if("1".equals(result)){
					flag=0;//成功
				}else if("-1".equals(result)||"5".equals(result)||"9".equals(result)||"4".equals(result)||"8".equals(result)){
					flag=1;//异常
				}else if("3".equals(result)||"2".equals(result)){
					flag=2;//失败
				}else {
					flag=3; //继续查询
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
