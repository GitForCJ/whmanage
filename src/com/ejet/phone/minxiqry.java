package com.ejet.phone;

import org.json.JSONObject;


//������ϸ��ѯ

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
			
			
			//�ֻ��˷��͵���Ϣ
			/***************************/
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("service_type", servertype);// ҵ������
			String time = Tools.getNow3();
			jsonObj.put("time_stamp", time);// ʱ��
			jsonObj.put("user_type", "1");//
			jsonObj.put("send_flag", "2");
			jsonObj.put("user_name", name);// �û���½��

			jsonObj.put("version", "1.0");
			jsonObj.put("clientv", "1.0");
			jsonObj.put("ip", "");
			jsonObj.put("passsec", pass);// �û�����

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

			// ��½��json
			String sendJson = jsonObj2.toString();
			System.out.println(sendJson);

			// ���ܺ���
			String jsonpass = Mac
					.getLoginEncryt(key, name, sendJson, servertype);

			System.out.println(jsonpass);
			/***************************/
		
			
			
			//����˽�����Ϣ��������Ϣ������
			/***************************/
			  String [] jsonreslut=jsonpass.split("\\|");
			  String resultname=Mac.getNameDecrypt(key, jsonreslut[1]).trim();
			  
			  System.out.println(resultname.trim());//��ȡ�û���
			    
			    String rsultjson=Mac.getLoginDecrypt(key,resultname, jsonreslut[2]);//��ȡjson����
			    System.out.println(rsultjson.trim());
			
			    //��ʼ����json����
			    
			    JSONObject jsonResult = new JSONObject(rsultjson);
			    
			    JSONObject jsonServerResult= jsonResult.getJSONObject("server");
			    
			    System.out.println(jsonServerResult);
			    System.out.println(jsonServerResult.getString("user_name"));//����û���
			    System.out.println(jsonServerResult.getString("passsec"));//����û�����
			    
			    
			    /***************************/
			    
			    
			    
			    //����˷��ص���Ϣ
			    
			    
		} catch (Exception e) {
		}

	}
	
}
