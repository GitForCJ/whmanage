package com.wlt.webm.business;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.service.MD5;
import com.wlt.webm.scpcommon.Constant;

public class OuFeiChargeUtil {
	
	
	/**
	 * @param cardnum ��� Ԫ
	 * @param sporder_id ������
	 * @param sporder_time ����ʱ��
	 * @param game_userid �ֻ���
	 * @return
	 * @throws IOException 
	 * @throws HttpException 
	 * @throws DocumentException 
	 */
	public static Map charge(String cardnum,String sporder_id,String sporder_time,String game_userid) throws HttpException, IOException, DocumentException{
		
			String userid=Constant.OUFEI_USERID;//�û����
			String userpws=Constant.OUFEI_USERPWS;//�û�����
			MD5 d= new MD5();
			byte [] r= d.getDigest(userpws.getBytes());
		    userpws= MD5.byteArrayToHexString(r).toLowerCase();//md5��
	
		    String cardid=Constant.OUFEI_CARDID; //�̶���������ʾ�������
			String key=Constant.OUFEI_KEY;
			String md5_str=userid+userpws+cardid+cardnum+sporder_id+sporder_time+ game_userid+key;
			r= d.getDigest(md5_str.getBytes());
			md5_str= MD5.byteArrayToHexString(r).toUpperCase();
			
			String chargeUrl = Constant.OUFEI_CHARGE_URL;
			String ret_url = Constant.OUFEI_RETURN_URL;
			String queryStr = "userid="+userid+"&userpws="+userpws+"&cardid="+cardid+"&cardnum="+cardnum+"&sporder_id="+sporder_id+"&sporder_time="+sporder_time+"&game_userid="+game_userid+"&md5_str="+md5_str+"&ret_url="+ret_url+"&version=6.0";
			HttpClient client = new HttpClient();
			Log.info("Ź�ɽӿ�:"+chargeUrl+"?"+queryStr);
		    GetMethod post = new GetMethod(chargeUrl+"?"+queryStr);
		    int status = client.executeMethod(post);
		    Log.info("Ź�ɽӿڷ��أ�"+status);
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
	 * @param spbillid ������
	 * @return int
	 * @throws IOException 
	 * @throws HttpException 
	 * @throws DocumentException 
	 */
	public static int query(String spbillid) throws HttpException, IOException{
			String userid=Constant.OUFEI_USERID;//�û����
			String queryUrl = Constant.OUFEI_QUERY_URL;
			String queryStr = "userid="+userid+"&spbillid="+spbillid;
			HttpClient client = new HttpClient();
		    GetMethod post = new GetMethod(queryUrl+"?"+queryStr);
		    int status = client.executeMethod(post);
		    String resultStr = "";
		    if(status==200){
		    	resultStr = post.getResponseBodyAsString();
			 }
		    post.releaseConnection();
		    ((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown();
		    if(null!=client)
		    {
		    	client=null;
		    }
		    return Integer.parseInt(resultStr);
	}
}
