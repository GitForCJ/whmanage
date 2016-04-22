package com.wlt.webm.business.bean.zdhuawei;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

public class Test {

	public static void main(String[] args) {
/*查询手机号归属地及手机号所支持的产品列表*/	 String str1="{\"zdws_user_login\":\"uuuuuuu\",\"zdws_method\":\"winksi.topUpPhone.queryMessage\",\"timestamp\":\"2015-11-09 13:52:29\",\"platform\":\"zdws\",  \"token\":\"20151112\",\"version\":\"1.0\", \"format\":\"json\",\"zdws_params\": {\"phone\":\"13800138000\",\"card_type\":\"1\"},\"zdws_sign\":\"0B611E57C9938654FD09986CDA6272BA\"}";
/*充值流量费接口*/     String str2="{\"zdws_user_login\":\"uuuuuuu\",\"zdws_method\":\"winksi.topUpPhone.orderOnline\",\"timestamp\":\"2015-11-09 13:52:29\",\"platform\":\"zdws\",\"token\":\"20151112\",\"version\":\"1.0\",\"format\":\"json\",\"zdws_params\": {\"callback_url\":\"testcallbackurl\",\"phone\":\"13800138000\",\"area\":\"北京\",\"operator\":\"移动\",\"card_type\":\"1\",\"cardid\":\"30.00\",\"price\":\"29.00\",\"orderid\":\"123456789012\",\"productid\":\"\" },\"zdws_sign\":\"5ED130AC618D248E17A2E82323493147\"}";
/*查询订单接口*/	 String str3="{\"zdws_user_login\":\"uuuuuuu\",\"zdws_method\":\"winksi.topUpPhone.queryOrder\",\"timestamp\":\"2015-11-09 13:52:29\",\"platform\":\"zdws\",\"token\":\"20151112\",\"version\":\"1.0\",\"format\":\"json\",\"zdws_params\": {\"phone\":\"13800138000\",\"orderid\":\"123456789012\",\"card_type\":\"1\",\"starttime\":\"2015-11-12\",\"endtime\":\"2015-11-13\",\"status\":\"1023\"},\"zdws_sign\":\"0B611E57C9938654FD09986CDA6272BA\"}";

ZDSystemParam zdsystemparam=new ZDSystemParam();
     JSONObject json=JSONObject.fromObject(str3);
     zdsystemparam.setZdws_user_login(json.get("zdws_user_login").toString());
     zdsystemparam.setTimestamp(json.get("timestamp").toString());
     zdsystemparam.setZdws_sign(json.get("zdws_sign").toString());
     zdsystemparam.setZdws_method(json.get("zdws_method").toString());     
     System.out.println("user_login="+zdsystemparam.getZdws_user_login());
     System.out.println("timestamp="+zdsystemparam.getTimestamp());
     System.out.println("zdws_sign="+zdsystemparam.getZdws_sign());
     System.out.println("zdws_method="+zdsystemparam.getZdws_method());     
     if(zdsystemparam.getZdws_method().equals("winksi.topUpPhone.queryMessage")){    	 
    	 JSONObject params=JSONObject.fromObject(json.get("zdws_params"));
    	 QueryMsgEntryZD queryMsgEntry=new QueryMsgEntryZD();
    	 queryMsgEntry.setPhone(params.getString("phone"));
    	 queryMsgEntry.setCard_type(params.getString("card_type"));
    	 System.out.println("手机归属地及产品信息查询接口");
    	 System.out.println("phone="+queryMsgEntry.getPhone());
    	 System.out.println("card_type="+queryMsgEntry.getCard_type());
      }else if(zdsystemparam.getZdws_method().equals("winksi.topUpPhone.orderOnline")){
    	  OrderEntityZD orderEntityZD=new OrderEntityZD();
    	  JSONObject params=JSONObject.fromObject(json.get("zdws_params"));    	  
    	  orderEntityZD.setArea(params.getString("area"));
    	  orderEntityZD.setCallback_url(params.getString("callback_url"));
    	  orderEntityZD.setCard_type(params.getString("card_type"));
    	  orderEntityZD.setCardid(params.getString("cardid"));
    	  orderEntityZD.setOperator(params.getString("operator"));
    	  orderEntityZD.setOrderid(params.getString("orderid"));
    	  orderEntityZD.setPhone(params.getString("phone"));
    	  orderEntityZD.setPrice(params.getString("price"));
    	  orderEntityZD.setProductid(params.getString("productid"));
    	  System.out.println("充值流量费接口");
    	  System.out.println("area="+orderEntityZD.getArea());
    	  System.out.println("callback_url="+orderEntityZD.getCallback_url());
    	  System.out.println("card_type="+orderEntityZD.getCard_type());
    	  System.out.println("cardid="+orderEntityZD.getCardid());
    	  System.out.println("operator="+orderEntityZD.getOperator());
    	  System.out.println("orderid="+orderEntityZD.getOrderid());
    	  System.out.println("phone="+orderEntityZD.getPhone());
    	  System.out.println("price="+orderEntityZD.getPrice());
    	  System.out.println("productid="+orderEntityZD.getProductid());    	  
      }else if(zdsystemparam.getZdws_method().equals("winksi.topUpPhone.queryOrder")){
    	  QueryEntityZD queryEntityZD=new QueryEntityZD();
    	  JSONObject params=JSONObject.fromObject(json.get("zdws_params")); 
    	  queryEntityZD.setCard_type(params.getString("card_type"));
    	  queryEntityZD.setEndtime(params.getString("endtime"));
    	  queryEntityZD.setOrderid(params.getString("orderid"));
    	  queryEntityZD.setPhone(params.getString("phone"));
    	  queryEntityZD.setStarttime(params.getString("starttime"));
    	  queryEntityZD.setStatus(params.getString("status"));
    	  System.out.println("查询订单接口"); 
    	  System.out.println("card_type="+queryEntityZD.getCard_type());
    	  System.out.println("endtime="+queryEntityZD.getEndtime());
    	  System.out.println("orderid="+queryEntityZD.getOrderid());
    	  System.out.println("phone="+queryEntityZD.getPhone());
    	  System.out.println("starttime="+queryEntityZD.getStarttime());
    	  System.out.println("status="+queryEntityZD.getStatus());    	  
      }
	}
}
