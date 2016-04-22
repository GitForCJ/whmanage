package com.wlt.webm.xunjie.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static void getdata(){
		String url="http://mgp.qq.com/flash/87592/";
		HttpClient client =new HttpClient();
		PostMethod post=new PostMethod(url);
		try {
			int a=client.executeMethod(post);
			if(200==a){
				System.out.println(convertStreamToString(post.getResponseBodyAsStream()));
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			post.releaseConnection();
   			((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown(); 
   			if(null!=client)
   			{
   				client=null;
   			}
		}
	}
	
	/**
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	  public static String convertStreamToString(InputStream is) throws IOException {
			StringBuilder sf = new StringBuilder();
			String line;
			try{
			 BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			 while ((line = reader.readLine()) != null){
			          sf.append(line);
			 }
			}
			finally {
			 is.close();
			}
			 return sf.toString();
			}


}
