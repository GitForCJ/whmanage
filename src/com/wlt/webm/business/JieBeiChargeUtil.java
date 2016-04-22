package com.wlt.webm.business;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.wlt.webm.business.service.MD5;
import com.wlt.webm.scpcommon.Constant;

public class JieBeiChargeUtil {
	
	
	/**
	 * @param requestid 代理商系统订单号
	 * @param mobile 手机号
	 * @param amount 金额 元
	 * @return
	 * @throws IOException 
	 * @throws HttpException 
	 * @throws DocumentException 
	 */
	public static Map charge(String requestid,String mobile,String amount) throws HttpException, IOException, DocumentException{
		
		String userid=Constant.JIEBEI_USERID;
		String synctype=Constant.JIEBEI_SYNCTYPE;
		String chargetype=Constant.JIEBEI_CHARGETYPE;
		String secretkey=Constant.JIEBEI_SECRETKEY;
		String vstr=userid+"^"+requestid+"^"+mobile+"^"+amount+"^"+synctype+"^"+secretkey;
		MD5 d= new MD5();                                  
		byte [] r= d.getDigest(vstr.getBytes());        
		vstr= MD5.byteArrayToHexString(r).toLowerCase();
		
		String chargeUrl = Constant.JIEBEI_CHARGE_URL;
		String queryStr = "userid="+userid+"&requestid="+requestid+"&mobile="+mobile+"&amount="+amount+"&synctype="+synctype+"&vstr="+vstr;
		HttpClient client = new HttpClient();
	    GetMethod post = new GetMethod(chargeUrl+"?"+queryStr);
	    int status = client.executeMethod(post);
	    Map<String, String> resultMap = new HashMap<String, String>();
	    resultMap.put("input", chargeUrl+"?"+queryStr);
	    if(status==200){
			String result = post.getResponseBodyAsString();
			Document docResult=DocumentHelper.parseText(result);
	        Element rootResult = docResult.getRootElement();
	        List<Element> results=rootResult.elements();
	        for(Element item1:results){
	           resultMap.put(item1.getName(), item1.getText());
	        }
		 }
	    post.releaseConnection();
	    ((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown();
	    if(null!=client)
	    {
	    	client=null;
	    }
		return resultMap;
	}

	/**
	 * @param requestid 代理商系统订单号
	 * @return map
	 * @throws IOException 
	 * @throws HttpException 
	 * @throws DocumentException 
	 */
	public static Map query(String requestid) throws HttpException, IOException, DocumentException{
		
		String userid=Constant.JIEBEI_USERID;
		int history = 0;
		String queryUrl = Constant.JIEBEI_QUERY_URL;
		String queryStr = "userid="+userid+"&requestid="+requestid+"&history="+history;
		HttpClient client = new HttpClient();
	    GetMethod post = new GetMethod(queryUrl+"?"+queryStr);
	    int status = client.executeMethod(post);
	    Map<String, String> resultMap = new HashMap<String, String>();
	    if(status==200){
			String result = post.getResponseBodyAsString();
			Document docResult=DocumentHelper.parseText(result);
	        Element rootResult = docResult.getRootElement();
	        List<Element> results=rootResult.elements();
	        for(Element item1:results){
	           resultMap.put(item1.getName(), item1.getText());
	        }
		 }
	    post.releaseConnection();
	    ((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown();
	    if(null!=client)
	    {
	    	client=null;
	    }
		return resultMap;
	}
      public static void main(String[] args) throws Exception {
//    	  Map<String, String> resultMap = JieBeiChargeUtil.charge("10001018682033", "18682033916", "50");
//    	 Set it =resultMap.keySet();
//    	 for (Iterator iterator = it.iterator(); iterator.hasNext();) {
//			String  key = (String ) iterator.next();
//			System.out.println(key +" : "+resultMap.get(key));
//		}
    	 
    	 Map<String, String> resultMap1 =JieBeiChargeUtil.query("10001018682033");
    	 Set it1 =resultMap1.keySet();
    	 for (Iterator iterator1 = it1.iterator(); iterator1.hasNext();) {
			String  key1 = (String ) iterator1.next();
			System.out.println(key1 +" : "+resultMap1.get(key1));
		}
	}
}
