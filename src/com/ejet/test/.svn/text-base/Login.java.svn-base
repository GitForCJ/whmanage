package com.ejet.test;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.wlt.webm.business.dianx.form.TelcomForm;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.tool.Tools;

public class Login {

	public static void main(String[] args) throws Exception {
		
			String method = "cmccFill";
			String tradeobject    = "13510223879";
			String payfee = "10";
			String seqno = Tools.getNow()+Tools.getSeqNo("android0001");
	        String sendUrl = "http://localhost:8080/oms/AdPhoneServlet?method="+method+"&tradeobject="+tradeobject+"&seqno="+seqno+"&payfee="+payfee;  
	        System.out.println(sendUrl);  
	        
	        URL url = new URL(sendUrl);  
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
	        connection.setDoInput(true);  
	        connection.setDoOutput(true);  
	        connection.setRequestMethod("POST"); 
	        connection.connect();  
	        String result = "";  
	        InputStreamReader  bis = new InputStreamReader(connection.getInputStream(),"gb2312");  
	        int c = 0;  
	        while((c = bis.read()) != -1){  
	            result = result + (char)c;     
	        }  
	        bis.close();
	        connection.disconnect();
		
/*		
		
		com.wlt.webm.mobile.CmccFill t = new com.wlt.webm.mobile.CmccFill();
		
		SysUserForm user = new SysUserForm();
		TelcomForm form = new TelcomForm();
		form.setTradeObject("13510223879");
		form.setSeqNo(seq);
		form.setPayFee("10");
		
		int ret  = t.telMobile(form, user);
		System.out.println(ret);
*/		
		
		
		
		

	}

}
