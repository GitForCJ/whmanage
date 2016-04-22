package com.wlt.webm.business.junbao.bean;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.junbao.service.JBConstat;
import com.wlt.webm.util.MD5;

public class OrderQuery {
	
	public static String  query(String orderID){
		HttpURLConnection conn = null;
		DataInputStream inStream = null;
		String flag="";
		String md5="agentid="+JBConstat.JB_AGENTID+
		"&orderid="+orderID+
		"&merchantKey="+JBConstat.JB_MAC;
		MD5 md=new MD5();
		String urlStr="http://220.189.210.142:28088/esales/order/orderQuery.do?agentid="+JBConstat.JB_AGENTID+
		"&orderid="+orderID+"&verifystring="+md.encode(md5);
		
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");

			} catch (MalformedURLException ex) {
			System.out.println("From ServletCom CLIENT REQUEST:" + ex);
			} catch (IOException ioe) {
			System.out.println("From ServletCom CLIENT REQUEST:" + ioe);
			}
			try {
			inStream = new DataInputStream(conn.getInputStream());
			Map result=xmlDeal(inStream);
			if(result.size()>0){
				flag=(String) result.get("resultno");
			}
			inStream.close();
			conn.disconnect();
			} catch (IOException ioex) {
			ioex.printStackTrace();
			}
		System.out.println(flag);
		return flag;
		
	}
	
	public static Map xmlDeal(InputStream ios){
		Map result=new HashMap();
		DocumentBuilderFactory bfactory =DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder;
		Document doc=null;
		try {
			builder = bfactory.newDocumentBuilder();
			doc=builder.parse(ios);
			int n=doc.getElementsByTagName("item").getLength();
			for(int i=0;i<n;i++){
			Element nodeTmp = (Element) doc.getElementsByTagName("item").item(i);
			result.put(nodeTmp.getAttribute("name"), nodeTmp.getAttribute("value"));
			}
		} catch (ParserConfigurationException e) {
			Log.error("君宝查询返回数据出错"+e.toString());
			e.printStackTrace();
		}catch (SAXException e) {
			Log.error("君宝解析查询返回数据出错"+e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			Log.error("君宝解析查询返回数据出错"+e.toString());	
			e.printStackTrace();
		}catch (Exception e) {
			Log.error("君宝解析查询返回数据出错"+e.toString());
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
