package com.ejet.test;


import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.ejet.phone.Mac;
import com.wlt.webm.business.dianx.form.TelcomForm;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.tool.Tools;

public class Cmcc {

	
	
	public static void login() throws JSONException
	{
		String key = "jiaofei";
		String name = "AAAA";
		String servertype = "0001";
		String pass="CCCCCC";
		
		
		//手机端发送的信息
		/***************************/
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("service_type", servertype);// 业务类型
		String time = Tools.getNow3();
		jsonObj.put("time_stamp", time);// 时间
		jsonObj.put("user_type", "1");//
		jsonObj.put("send_flag", "2");
		jsonObj.put("user_name", name);// 用户登陆名

		jsonObj.put("version", "1.0");
		jsonObj.put("clientv", "1.0");
		jsonObj.put("ip", "");
		jsonObj.put("passsec", pass);// 用户密码

		JSONObject jsonObj2 = new JSONObject();
		jsonObj2.put("server", jsonObj);

		// 登陆的json
		String sendJson = jsonObj2.toString();
		System.out.println(sendJson);

		// 加密后结果
		String jsonpass = Mac.getLoginEncryt(key, name, sendJson, servertype);
		System.out.println("JSON_DATA: " + jsonpass);
		
		
	}
	
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		
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
