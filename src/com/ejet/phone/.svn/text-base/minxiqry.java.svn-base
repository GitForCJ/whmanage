package com.ejet.phone;

import org.json.JSONObject;


//交易明细查询

public class minxiqry {

	
	public static void main(String args[]) {
		try {
			String key = "sessionkey";
			String name = "AAAA";
			String servertype = "0014";
			String pass="CCCCCC";
			
			String starttime="20130512";
			String endtime="20130612";
			String type="1";
			String fee="100";
			String order_no="";
			String state="";
			String phone="";
			
			
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

			JSONObject contentsend = new JSONObject();
			contentsend.put("starttime", starttime);
			contentsend.put("endtime", endtime);
			contentsend.put("pagenum", 0);
			contentsend.put("itemnum", 20);
			contentsend.put("type", type);
			
			contentsend.put("fee", fee);
			 contentsend.put("order_no", order_no);
			 contentsend.put("state", state);
			  contentsend.put("phone", phone);
			
			
			jsonObj.put("content", contentsend);
			
			JSONObject jsonObj2 = new JSONObject();
			jsonObj2.put("server", jsonObj);

			// 登陆的json
			String sendJson = jsonObj2.toString();
			System.out.println(sendJson);

			// 加密后结果
			String jsonpass = Mac
					.getLoginEncryt(key, name, sendJson, servertype);

			System.out.println(jsonpass);
			/***************************/
		
			
			
			//服务端接收信息，进行信息解析；
			/***************************/
			  String [] jsonreslut=jsonpass.split("\\|");
			  String resultname=Mac.getNameDecrypt(key, jsonreslut[1]).trim();
			  
			  System.out.println(resultname.trim());//获取用户名
			    
			    String rsultjson=Mac.getLoginDecrypt(key,resultname, jsonreslut[2]);//获取json报文
			    System.out.println(rsultjson.trim());
			
			    //开始解析json报文
			    
			    JSONObject jsonResult = new JSONObject(rsultjson);
			    
			    JSONObject jsonServerResult= jsonResult.getJSONObject("server");
			    
			    System.out.println(jsonServerResult);
			    System.out.println(jsonServerResult.getString("user_name"));//获得用户名
			    System.out.println(jsonServerResult.getString("passsec"));//获得用户密码
			    
			    
			    /***************************/
			    
			    
			    
			    //服务端返回的信息
			    
			    
		} catch (Exception e) {
		}

	}
	
}
