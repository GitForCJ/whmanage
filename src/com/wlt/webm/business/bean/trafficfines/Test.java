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
		
		
		//���Ի�ȡtoken
//		String url="http://59.41.186.76:13278/api/sys/login";
//		TokenRequest tokenreq=new TokenRequest();
//		tokenreq.setAccount("api_wanheng");
//		tokenreq.setPassword("api_wanheng");
//        String res=PublicMethod.sendMsgRequest(url, JSON.toJSONString(tokenreq));
//        TokenResponse  resp=JSON.parseObject(res, TokenResponse.class);
//        System.out.println("��ȡ��tokenid:"+resp.getTokenId());
//        BreakRulesReq requestObj=new BreakRulesReq("��BF447U","1","003932","4304",PublicMethod.getTokenID());
//        BreakRulesResp obj=(BreakRulesResp)PublicMethod.breakrulesMethod(BreakRulesResp.class, requestObj, PublicMethod.QUERY_BREAKRULES);
//        System.out.println(obj.getCode());
//        obj.getPeccancyInfos();
		//036137	188011
		HashMap<String,String> querys=new HashMap<String,String>();
		querys.put("plateNumber", "��B5SU98");
		querys.put("plateNumberType", "2");
		querys.put("carFrameNum", "036137");
		querys.put("engineNumber", "188011");
		querys.put("tokenId", PublicMethod.getTokenID());
		NewQueryRes queryRes=(NewQueryRes)PublicMethod.breakrulesMethod(NewQueryRes.class, querys, PublicMethod.NEWQUERY_BREAKRULES);
		System.out.println("���ش��룺"+queryRes.getCode());
		System.out.println("����������"+queryRes.getDesc() );
		System.out.println("����������"+queryRes.getPeccancyInfosTotal());
		List<PeccancyInfoModel> peccancyInfos =queryRes.getPeccancyInfos();
		if(null!=peccancyInfos&&peccancyInfos.size()>0){
			for(PeccancyInfoModel model:peccancyInfos){
				System.out.println("����ţ�"+model.getPecNum()+"  ���ƺţ�"+model.getPlateNumber()
						+"   Υ�´��룺"+model.getPecCode()+"  Υ������:"+model.getPecDesc()
						+"  Υ�µص㣺"+model.getPecAddr()+"   ����"+model.getCorpus()
						+"  ���ɽ�"+model.getLateFee()+"  ����ѣ�"+model.getReplaceMoney()
						+"  �Ƿ���Դ���:"+model.getAgent()+"  ״̬��"+model.getWoState()
						+"  Υ��������룺"+model.getAreaCode());
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
	        System.out.println(list.size()); // �˴����Ϊ2 
	    }
	   
	   static void func1(String s) {
		    s += "tail";
		    System.out.println(s);
		    }
	   static    void test1() {
		        String a = "abc";
		        func1(a);
		        System.out.println(a); // �˴����Ϊabc  
		    }
	   /**
	    * 
	    * @param agent  �Ƿ�ɴ���  ���쵥״̬
	    * @param woState
	    * @return 1 �ɴ��� 2 �Ѿ��µ� 3 ����ɹ� 4 ���ɴ���
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
