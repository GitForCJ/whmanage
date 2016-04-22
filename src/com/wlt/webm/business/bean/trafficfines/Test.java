package com.wlt.webm.business.bean.trafficfines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;

import com.alibaba.fastjson.JSON;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		
		//测试获取token
//		String url="http://59.41.186.76:13278/api/sys/login";
//		TokenRequest tokenreq=new TokenRequest();
//		tokenreq.setAccount("api_wanheng");
//		tokenreq.setPassword("api_wanheng");
//        String res=PublicMethod.sendMsgRequest(url, JSON.toJSONString(tokenreq));
//        TokenResponse  resp=JSON.parseObject(res, TokenResponse.class);
//        System.out.println("获取的tokenid:"+resp.getTokenId());
//        BreakRulesReq requestObj=new BreakRulesReq("粤BF447U","1","003932","4304",PublicMethod.getTokenID());
//        BreakRulesResp obj=(BreakRulesResp)PublicMethod.breakrulesMethod(BreakRulesResp.class, requestObj, PublicMethod.QUERY_BREAKRULES);
//        System.out.println(obj.getCode());
//        obj.getPeccancyInfos();
		//036137	188011
		HashMap<String,String> querys=new HashMap<String,String>();
		querys.put("plateNumber", "粤B5SU98");
		querys.put("plateNumberType", "2");
		querys.put("carFrameNum", "036137");
		querys.put("engineNumber", "188011");
		querys.put("tokenId", PublicMethod.getTokenID());
		NewQueryRes queryRes=(NewQueryRes)PublicMethod.breakrulesMethod(NewQueryRes.class, querys, PublicMethod.NEWQUERY_BREAKRULES);
		System.out.println("返回代码："+queryRes.getCode());
		System.out.println("返回描述："+queryRes.getDesc() );
		System.out.println("返回数量："+queryRes.getPeccancyInfosTotal());
		List<PeccancyInfoModel> peccancyInfos =queryRes.getPeccancyInfos();
		if(null!=peccancyInfos&&peccancyInfos.size()>0){
			for(PeccancyInfoModel model:peccancyInfos){
				System.out.println("文书号："+model.getPecNum()+"  车牌号："+model.getPlateNumber()
						+"   违章代码："+model.getPecCode()+"  违章描述:"+model.getPecDesc()
						+"  违章地点："+model.getPecAddr()+"   本金："+model.getCorpus()
						+"  滞纳金："+model.getLateFee()+"  代办费："+model.getReplaceMoney()
						+"  是否可以代办:"+model.getAgent()+"  状态："+model.getWoState()
						+"  违章区域编码："+model.getAreaCode());
				System.out.println();
			}
		}
		
	}
	   static void func1(List s) {
	        s.add("dfdsa");
	        System.out.println(s.size());
	        s=null;
	    }
	   static void test() {
	        List<String> list = new ArrayList<String>();
	        list.add("abc");
	        func1(list);
	        System.out.println(list.size()); // 此处结果为2 
	    }
	   
	   static void func1(String s) {
		    s += "tail";
		    System.out.println(s);
		    }
	   static    void test1() {
		        String a = "abc";
		        func1(a);
		        System.out.println(a); // 此处结果为abc  
		    }
	   /**
	    * 
	    * @param agent  是否可代办  代办单状态
	    * @param woState
	    * @return 1 可代办 2 已经下单 3 代办成功 4 不可代办
	    */
	   public int getstate(String agent,String woState){
		   if(agent=="1"){
			   if(woState==null||woState=="5"||woState=="7"||woState=="-99"){
				 return 1;  
			   }else if(woState=="1"||woState=="2"||woState=="3"||woState=="6"){
				  return 2; 
			   }else if(woState=="4"){
				   return 3;
			   }else{
				   return 4;
			   }
		   }else{
			   return 4;
		   }
	   }
}
