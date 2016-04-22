package com.wlt.webm.business.junbao.bean;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import com.wlt.webm.business.junbao.service.JBConstat;
import com.wlt.webm.util.MD5;

public class JBFill {
	
	
	public static int  fill(String prodid,String orderid,String mobilenum,
			String datetime){
		HttpURLConnection conn = null;
		DataInputStream inStream = null;
		//prodid=%s&agentid=%s&orderid=%s&mobilenum=%s&datetime=%s&mark=%s&merchantKey=%s
		String md5String ="prodid="+prodid+"&agentid="+JBConstat.JB_AGENTID+
		"&orderid="+orderid+
        "&mobilenum="+mobilenum+
        "&datetime="+datetime+
        "&mark=fill"+"&merchantKey="+JBConstat.JB_MAC;
		md5String = MD5.encode(md5String).toLowerCase();
		String urlString =JBConstat.URL+"?" +
		"prodid="+prodid+"&agentid="+JBConstat.JB_AGENTID+
		"&orderid="+orderid+
        "&mobilenum="+mobilenum+
        "&datetime="+datetime+
        "&mark=fill"+"&verifystring="+md5String;
		try {
			URL url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			inStream = new DataInputStream(conn.getInputStream());
			String str;
			URLDecoder decoder =new URLDecoder();
			while ((str = inStream.readLine()) != null) {
			System.out.println("Server response is: " + new String( decoder.decode(str, "utf-8")));
			System.out.println("");
			}
			inStream.close();
			conn.disconnect();
			}catch (Exception ioe) {
				System.out.println("From ServletCom CLIENT REQUEST:" + ioe);
				return -1;
			}
			return 0;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
