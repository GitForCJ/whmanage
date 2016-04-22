package com.wlt.webm.business.bean.oufeiqb;

import com.alibaba.fastjson.JSON;

public class Test {
	
	public static void main(String[] args) {
//		String str="http://api2.ofpay.com/supply.do?partner=S015099&tplid=MB2014080611181047&sign=AD0155718306F3AB89F7704998E30D22&reqid=1234&format=json";
//	    System.out.println(MD5Util.MD5Encode("S015099MB2014080611181047x1i8jxjbhgplve3juxzj8du2c5vmpzcdu4slnukyl5", "UTF-8").toUpperCase());
//	    
//	    String confirm="http://api2.ofpay.com/confirmRecharge.do?orderid=15032411480866&id=375168927&format=json";
		
		
		String str="{'status':'0000','data':{'reqId':'1234','dataList':[{'id':'375168927','esup_uid':'S015099','product_id':'220612','order_time':'2015-03-24 11:08:27','product_company':'腾讯游戏','order_num':1,'product_name':'Q币任意充直充','userid':'S010916','order_ip':'183.15.59.155','product_par_value':'1','order_id':'15032411480866','recharge_account':'1032225044','tplid':'MB2014080611181047'}],'fields':'id,esup_uid,product_id,order_time,product_company,order_num,product_name,userid,order_ip,product_par_value,order_id,recharge_account,tplid'},'msg':null}";
		GetOrderRespone obj=(GetOrderRespone)JSON.parseObject(str,GetOrderRespone.class);
		System.out.println(111);

	}

}
