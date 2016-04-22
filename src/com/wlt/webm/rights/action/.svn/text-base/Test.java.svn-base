package com.wlt.webm.rights.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;

import com.wlt.webm.util.MD5;
import com.commsoft.epay.util.logging.Log;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public static void main(String[] args) throws HttpException, IOException {
		
           String url="http://supply.api.17sup.com/checkOrder.do?";
           String partner="S010916",tplid="MB2013110912162219",orderids="",
           reqid="S01091620140210100539";
           String sign = MD5.encode(partner+tplid+reqid+"devkprj9l13k32xy5juxe9d6k5bg72t6178rnrjli4").toUpperCase();
           String parameter="partner="+partner +
			"&tplid="+tplid+
			"&sign="+sign+
			"&reqid="+reqid;
           
          HttpClient client = new HttpClient();
   		PostMethod post = new PostMethod(url+parameter);
   		int status;
   		String result = "" ;
   			status = client.executeMethod(post);
   			if(status==200){
   				result =convertStreamToString(post.getResponseBodyAsStream());
   				System.out.println("漏单检查结果:"+result);
   			}
   			post.releaseConnection();
   			((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown(); 
   			if(null!=client)
   			{
   				client=null;
   			}
	}
   			/**
   			 * 字节流转换成字符串
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
