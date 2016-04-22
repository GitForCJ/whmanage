package com.wlt.webm.business.service;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.commsoft.epay.util.logging.Log;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.wlt.webm.business.bean.JtfkBean;
import com.wlt.webm.business.form.JtfkForm;
import com.wlt.webm.business.form.OrderForm;
import com.wlt.webm.pccommon.Constants;
import com.wlt.webm.scpdb.DBService;
import com.wlt.webm.scpdb.FundAcctDao;

public class JtfkService {

	public static List jtfkQuery(JtfkForm form) throws HttpException, IOException, DocumentException{
		 String Command="2";
	     String UserId="EN1_A61E447C7FF746A960DAA70E609577A0";
	     String CftMerId="27";
	     String MerchantId="1002";
	     String Version="1";
	     String ResFormat="xml";
	     String Attach="abcd";
	     String Sign;
	     String QueryText="Vehicle="+form.getVehicle()+
	     				  "&VehicleType="+form.getVehicleType()+
	     				  "&FrameNo="+form.getFrameNo()+"&EngineNo=&OwnerName=";
	     
	     String msg="";
	     String key="#s^un2ye31<cn%|aoXpR,+hrd2013";
	     
	     
	     MD5 md5 = new MD5();
	     String tmp1= byteArrayToHexString(md5.getDigest(QueryText+"&Md5Key="+key));
	     String tmp2=QueryText+"&Md5Sign="+tmp1;
	     String tmp3= Base64.encode(tmp2.getBytes("gb2312"));
	     QueryText=tmp3;
	    
	     String SignTmp="Attach="+Attach+"&CftMerId="+CftMerId+"&Command="+Command+"&MerchantId="+MerchantId+"&QueryText="+QueryText+"&ResFormat="+ResFormat+"&UserId="+UserId+"&Version="+Version+"&Md5Key="+key;
	     Sign=byteArrayToHexString(md5.getDigest(SignTmp));
	     
	     msg="http://www.gdwz.net/open_cx/ser_wzcx.aspx?Command="+Command+"&MerchantId="+MerchantId+"&Attach="+Attach+"&CftMerId="+CftMerId+"&ResFormat="+ResFormat+"&UserId="+UserId+"&Version="+Version+"&Sign="+Sign+"&QueryText="+QueryText;

	     HttpClient client = new HttpClient();
	     GetMethod post = new GetMethod(msg);
//	     GetMethod post = new GetMethod("http://localhost:9090/oms");
//	     post.setQueryString(queryString);
	     int status = client.executeMethod(post);
	     List<JtfkBean> jtfkList=new ArrayList<JtfkBean>();
	     if(status==200){ 
	    	 	String recordResult = "";
	    	 	String resultCode = "";
				String result = post.getResponseBodyAsString();
				
				Document docResult=DocumentHelper.parseText(result);
	            Element rootResult = docResult.getRootElement();
	            List<Element> results=rootResult.elements();
	            for(Element item1:results){
	            	if("RespText".equals(item1.getName())){
	            		recordResult = item1.getText();
    	    		}
	            	if("SpRetCode".equals(item1.getName())){
	            		resultCode = item1.getText();
    	    		}
	            }
	            if(!"".equals(resultCode)){
	            	System.out.println("-------------交通罚款返回-----"+resultCode);
	            }
				URLDecoder decoder =new URLDecoder();
//				String newRecord = decoder.decode(new String( Base64.decode("UmV0TnVtPTYmVG90YWxOdW09NiZSZWNvcmRzPSUzY1JlY29yZHMlM2UlM2NSZWNvcmQlM2UlM2NWaW9sYXRpb25JZCUzZTQ5OTU3NjAlM2MlMmZWaW9sYXRpb25JZCUzZSUzY1Zpb2xhdGlvblRpbWUlM2UyMDEyLTExLTA5KzExJTNhMTMlM2EwMCUzYyUyZlZpb2xhdGlvblRpbWUlM2UlM2NWaWxvYXRpb25Mb2NhdGlvbiUzZSU1YiViNyVmMCVjOSViZCU1ZCViNyVmMCVjOSViZCViNCVmMyViNSVjMCVkNiVkMC0lYmMlYmUlYmIlYWElY2IlYzQlYzIlYjclYzIlYjclYmYlZGElM2MlMmZWaWxvYXRpb25Mb2NhdGlvbiUzZSUzY1RvdGFsRmVlJTNlMjE1MDAlM2MlMmZUb3RhbEZlZSUzZSUzY0ZpbmVGZWUlM2UyMDAwMCUzYyUyZkZpbmVGZWUlM2UlM2NEZWFsRmVlJTNlMTUwMCUzYyUyZkRlYWxGZWUlM2UlM2NMYXRlRmVlJTNlMCUzYyUyZkxhdGVGZWUlM2UlM2NEZWFsVGltZSUzZTclM2MlMmZEZWFsVGltZSUzZSUzY090aGVyV2F5JTNlJWM3JWViJWI0JWY4JWM5JWNmJWJjJWRkJWNhJWJiJWQ2JWE0JWQwJWQwJWNhJWJiJWQ2JWE0JWQ2JWMxJWNlJWE1JWI3JWE4JWI1JWQ4JWJkJWJiJWJlJWFmJWI0JWYzJWI2JWQzJWI0JWE2JWMwJWVkJTNjJTJmT3RoZXJXYXklM2UlM2NQb2xpY2VDb250YWN0JTNlJTNjJTJmUG9saWNlQ29udGFjdCUzZSUzY1BvbGljZUFkZHJlc3MlM2UlM2MlMmZQb2xpY2VBZGRyZXNzJTNlJTNjVmlsb2F0aW9uRGV0YWlsJTNlJWJiJWZhJWI2JWFmJWIzJWI1JWNlJWE1JWI3JWI0JWJkJWZiJWQ2JWI5JWIxJWVhJWNmJWRmJWQ2JWI4JWNhJWJlJWI1JWM0JTNjJTJmVmlsb2F0aW9uRGV0YWlsJTNlJTNjRGVhbEZsYWclM2UxJTNjJTJmRGVhbEZsYWclM2UlM2NEZWFsTXNnJTNlJWJmJWM5JWQyJWQ0JWIwJWVjJWMwJWVkJTNjJTJmRGVhbE1zZyUzZSUzY0xpdmVCaWxsJTNlMCUzYyUyZkxpdmVCaWxsJTNlJTNjUmVmZXJGZWUlM2UyODAwJTNjJTJmUmVmZXJGZWUlM2UlM2NXc2glM2U0NDA2MTQ3OTAwNTMxOTczJTNjJTJmV3NoJTNlJTNjJTJmUmVjb3JkJTNlJTNjUmVjb3JkJTNlJTNjVmlvbGF0aW9uSWQlM2U0OTk1NzU5JTNjJTJmVmlvbGF0aW9uSWQlM2UlM2NWaW9sYXRpb25UaW1lJTNlMjAxMS0wNy0yOCsxMiUzYTAxJTNhMDAlM2MlMmZWaW9sYXRpb25UaW1lJTNlJTNjVmlsb2F0aW9uTG9jYXRpb24lM2UlNWIlYzklYzclY2UlYjIlNWQlYzklZWUlYzklYzclYjglZGYlY2IlZDklYjklYWIlYzIlYjclYzklYzclY2UlYjIlYmQlYmIlYmUlYWYlYzYlZDIlYjElZGYlYjQlZjMlYjYlZDMlY2YlYmQlYjYlY2UlM2MlMmZWaWxvYXRpb25Mb2NhdGlvbiUzZSUzY1RvdGFsRmVlJTNlMjIwMDAlM2MlMmZUb3RhbEZlZSUzZSUzY0ZpbmVGZWUlM2UyMDAwMCUzYyUyZkZpbmVGZWUlM2UlM2NEZWFsRmVlJTNlMjAwMCUzYyUyZkRlYWxGZWUlM2UlM2NMYXRlRmVlJTNlMCUzYyUyZkxhdGVGZWUlM2UlM2NEZWFsVGltZSUzZTclM2MlMmZEZWFsVGltZSUzZSUzY090aGVyV2F5JTNlJWM3JWViJWI0JWY4JWM5JWNmJWJjJWRkJWNhJWJiJWQ2JWE0JWQwJWQwJWNhJWJiJWQ2JWE0JWQ2JWMxJWNlJWE1JWI3JWE4JWI1JWQ4JWJkJWJiJWJlJWFmJWI0JWYzJWI2JWQzJWI0JWE2JWMwJWVkJTNjJTJmT3RoZXJXYXklM2UlM2NQb2xpY2VDb250YWN0JTNlJTNjJTJmUG9saWNlQ29udGFjdCUzZSUzY1BvbGljZUFkZHJlc3MlM2UlM2MlMmZQb2xpY2VBZGRyZXNzJTNlJTNjVmlsb2F0aW9uRGV0YWlsJTNlJWQ0JWRhJWI4JWRmJWNiJWQ5JWI5JWFiJWMyJWI3JWM5JWNmJWIzJWFjJWNiJWQ5JWIyJWJiJWQ3JWUzNTAlMjUlYjUlYzQlM2MlMmZWaWxvYXRpb25EZXRhaWwlM2UlM2NEZWFsRmxhZyUzZTElM2MlMmZEZWFsRmxhZyUzZSUzY0RlYWxNc2clM2UlYmYlYzklZDIlZDQlYjAlZWMlYzAlZWQlM2MlMmZEZWFsTXNnJTNlJTNjTGl2ZUJpbGwlM2UwJTNjJTJmTGl2ZUJpbGwlM2UlM2NSZWZlckZlZSUzZTI4MDAlM2MlMmZSZWZlckZlZSUzZSUzY1dzaCUzZTQ0MTU5MTc5MDAwMTM2ODMlM2MlMmZXc2glM2UlM2MlMmZSZWNvcmQlM2UlM2NSZWNvcmQlM2UlM2NWaW9sYXRpb25JZCUzZTQ5OTU3NjYlM2MlMmZWaW9sYXRpb25JZCUzZSUzY1Zpb2xhdGlvblRpbWUlM2UyMDExLTA2LTAxKzEzJTNhMzUlM2E0MiUzYyUyZlZpb2xhdGlvblRpbWUlM2UlM2NWaWxvYXRpb25Mb2NhdGlvbiUzZSU1YiVkNCVjNiViOCVhMSU1ZCViOSVlMyVjMCVhNSViOCVkZiVjYiVkOUs4OCsyMDAlM2MlMmZWaWxvYXRpb25Mb2NhdGlvbiUzZSUzY1RvdGFsRmVlJTNlMjE1MDAlM2MlMmZUb3RhbEZlZSUzZSUzY0ZpbmVGZWUlM2UyMDAwMCUzYyUyZkZpbmVGZWUlM2UlM2NEZWFsRmVlJTNlMTUwMCUzYyUyZkRlYWxGZWUlM2UlM2NMYXRlRmVlJTNlMCUzYyUyZkxhdGVGZWUlM2UlM2NEZWFsVGltZSUzZTclM2MlMmZEZWFsVGltZSUzZSUzY090aGVyV2F5JTNlJWM3JWViJWI0JWY4JWM5JWNmJWJjJWRkJWNhJWJiJWQ2JWE0JWQwJWQwJWNhJWJiJWQ2JWE0JWQ2JWMxJWNlJWE1JWI3JWE4JWI1JWQ4JWJkJWJiJWJlJWFmJWI0JWYzJWI2JWQzJWI0JWE2JWMwJWVkJTNjJTJmT3RoZXJXYXklM2UlM2NQb2xpY2VDb250YWN0JTNlJTNjJTJmUG9saWNlQ29udGFjdCUzZSUzY1BvbGljZUFkZHJlc3MlM2UlM2MlMmZQb2xpY2VBZGRyZXNzJTNlJTNjVmlsb2F0aW9uRGV0YWlsJTNlJWQ0JWRhJWI4JWRmJWNiJWQ5JWI5JWFiJWMyJWI3JWM5JWNmJWIzJWFjJWNiJWQ5JWIyJWJiJWQ3JWUzNTAlMjUlYjUlYzQlM2MlMmZWaWxvYXRpb25EZXRhaWwlM2UlM2NEZWFsRmxhZyUzZTElM2MlMmZEZWFsRmxhZyUzZSUzY0RlYWxNc2clM2UlYmYlYzklZDIlZDQlYjAlZWMlYzAlZWQlM2MlMmZEZWFsTXNnJTNlJTNjTGl2ZUJpbGwlM2UwJTNjJTJmTGl2ZUJpbGwlM2UlM2NSZWZlckZlZSUzZTI4MDAlM2MlMmZSZWZlckZlZSUzZSUzY1dzaCUzZTQ0NTMwMzAwMDA4Mjc0OTUlM2MlMmZXc2glM2UlM2MlMmZSZWNvcmQlM2UlM2NSZWNvcmQlM2UlM2NWaW9sYXRpb25JZCUzZTQ5OTU3NjQlM2MlMmZWaW9sYXRpb25JZCUzZSUzY1Zpb2xhdGlvblRpbWUlM2UyMDExLTAzLTI3KzE0JTNhMjQlM2EyMCUzYyUyZlZpb2xhdGlvblRpbWUlM2UlM2NWaWxvYXRpb25Mb2NhdGlvbiUzZSU1YiVjOSVjNyVjZCViNyU1ZCVjOSVlZSVjOSVjNyViOCVkZiVjYiVkOSVjYyVlZiVkMCVjNCVkNiVjMSViYSVhMyVjMyVjNSViNiVjZSUzYyUyZlZpbG9hdGlvbkxvY2F0aW9uJTNlJTNjVG90YWxGZWUlM2UyMTUwMCUzYyUyZlRvdGFsRmVlJTNlJTNjRmluZUZlZSUzZTIwMDAwJTNjJTJmRmluZUZlZSUzZSUzY0RlYWxGZWUlM2UxNTAwJTNjJTJmRGVhbEZlZSUzZSUzY0xhdGVGZWUlM2UwJTNjJTJmTGF0ZUZlZSUzZSUzY0RlYWxUaW1lJTNlNyUzYyUyZkRlYWxUaW1lJTNlJTNjT3RoZXJXYXklM2UlYzclZWIlYjQlZjglYzklY2YlYmMlZGQlY2ElYmIlZDYlYTQlZDAlZDAlY2ElYmIlZDYlYTQlZDYlYzElY2UlYTUlYjclYTglYjUlZDglYmQlYmIlYmUlYWYlYjQlZjMlYjYlZDMlYjQlYTYlYzAlZWQlM2MlMmZPdGhlcldheSUzZSUzY1BvbGljZUNvbnRhY3QlM2UlM2MlMmZQb2xpY2VDb250YWN0JTNlJTNjUG9saWNlQWRkcmVzcyUzZSUzYyUyZlBvbGljZUFkZHJlc3MlM2UlM2NWaWxvYXRpb25EZXRhaWwlM2UlZDQlZGElYjglZGYlY2IlZDklYjklYWIlYzIlYjclYzklY2YlYjMlYWMlY2IlZDklYjIlYmIlZDclZTM1MCUyNSViNSVjNCUzYyUyZlZpbG9hdGlvbkRldGFpbCUzZSUzY0RlYWxGbGFnJTNlMSUzYyUyZkRlYWxGbGFnJTNlJTNjRGVhbE1zZyUzZSViZiVjOSVkMiVkNCViMCVlYyVjMCVlZCUzYyUyZkRlYWxNc2clM2UlM2NMaXZlQmlsbCUzZTAlM2MlMmZMaXZlQmlsbCUzZSUzY1JlZmVyRmVlJTNlMjgwMCUzYyUyZlJlZmVyRmVlJTNlJTNjV3NoJTNlNDQwNTkyMDAwMjkxNTc2MSUzYyUyZldzaCUzZSUzYyUyZlJlY29yZCUzZSUzY1JlY29yZCUzZSUzY1Zpb2xhdGlvbklkJTNlNDk5NTc2MiUzYyUyZlZpb2xhdGlvbklkJTNlJTNjVmlvbGF0aW9uVGltZSUzZTIwMTEtMDMtMjcrMTElM2ExOSUzYTAwJTNjJTJmVmlvbGF0aW9uVGltZSUzZSUzY1ZpbG9hdGlvbkxvY2F0aW9uJTNlJTViJWJiJWRkJWQ2JWRkJTVkJWI5JWUzJWI2JWFiJWNhJWExJWJiJWRkJWQ2JWRkJWNhJWQwJWJjJWMzJWI5JWUzJWI4JWRmJWNiJWQ5JWI5JWFiJWMyJWI3JWEzJWE4RzM1JWEzJWE5JWI5JWUzJWJiJWRkJWJiJWRkJWQ2JWRkJWI2JWNlJTNjJTJmVmlsb2F0aW9uTG9jYXRpb24lM2UlM2NUb3RhbEZlZSUzZTIxMDAwJTNjJTJmVG90YWxGZWUlM2UlM2NGaW5lRmVlJTNlMjAwMDAlM2MlMmZGaW5lRmVlJTNlJTNjRGVhbEZlZSUzZTEwMDAlM2MlMmZEZWFsRmVlJTNlJTNjTGF0ZUZlZSUzZTAlM2MlMmZMYXRlRmVlJTNlJTNjRGVhbFRpbWUlM2U3JTNjJTJmRGVhbFRpbWUlM2UlM2NPdGhlcldheSUzZSVjNyVlYiViNCVmOCVjOSVjZiViYyVkZCVjYSViYiVkNiVhNCVkMCVkMCVjYSViYiVkNiVhNCVkNiVjMSVjZSVhNSViNyVhOCViNSVkOCViZCViYiViZSVhZiViNCVmMyViNiVkMyViNCVhNiVjMCVlZCUzYyUyZk90aGVyV2F5JTNlJTNjUG9saWNlQ29udGFjdCUzZSUzYyUyZlBvbGljZUNvbnRhY3QlM2UlM2NQb2xpY2VBZGRyZXNzJTNlJTNjJTJmUG9saWNlQWRkcmVzcyUzZSUzY1ZpbG9hdGlvbkRldGFpbCUzZSVkNCVkYSViOCVkZiVjYiVkOSViOSVhYiVjMiViNyVjOSVjZiViMyVhYyVjYiVkOSViMiViYiVkNyVlMzUwJTI1JWI1JWM0JTNjJTJmVmlsb2F0aW9uRGV0YWlsJTNlJTNjRGVhbEZsYWclM2UxJTNjJTJmRGVhbEZsYWclM2UlM2NEZWFsTXNnJTNlJWJmJWM5JWQyJWQ0JWIwJWVjJWMwJWVkJTNjJTJmRGVhbE1zZyUzZSUzY0xpdmVCaWxsJTNlMCUzYyUyZkxpdmVCaWxsJTNlJTNjUmVmZXJGZWUlM2UxOTAwJTNjJTJmUmVmZXJGZWUlM2UlM2NXc2glM2U0NDEzOTMwMDA2Mjg0MjcwJTNjJTJmV3NoJTNlJTNjJTJmUmVjb3JkJTNlJTNjUmVjb3JkJTNlJTNjVmlvbGF0aW9uSWQlM2U0OTk1NzYxJTNjJTJmVmlvbGF0aW9uSWQlM2UlM2NWaW9sYXRpb25UaW1lJTNlMjAxMS0wMi0wNSsxNSUzYTMyJTNhNDQlM2MlMmZWaW9sYXRpb25UaW1lJTNlJTNjVmlsb2F0aW9uTG9jYXRpb24lM2UlNWIlYmQlZDIlZDElZjQlNWQlYjklZTMlYjYlYWIlY2ElYTElYmQlZDIlZDElZjQlY2ElZDAlYzklZWUlYzklYzclYjglZGYlY2IlZDklYjYlYWIlYjYlY2UxODkuN0tNJWI0JWE2JWEzJWE4JWM5JWM3LSVjOSVlZSVhMyVhOSUzYyUyZlZpbG9hdGlvbkxvY2F0aW9uJTNlJTNjVG90YWxGZWUlM2UyMDgwMCUzYyUyZlRvdGFsRmVlJTNlJTNjRmluZUZlZSUzZTIwMDAwJTNjJTJmRmluZUZlZSUzZSUzY0RlYWxGZWUlM2U4MDAlM2MlMmZEZWFsRmVlJTNlJTNjTGF0ZUZlZSUzZTAlM2MlMmZMYXRlRmVlJTNlJTNjRGVhbFRpbWUlM2U3JTNjJTJmRGVhbFRpbWUlM2UlM2NPdGhlcldheSUzZSVjNyVlYiViNCVmOCVjOSVjZiViYyVkZCVjYSViYiVkNiVhNCVkMCVkMCVjYSViYiVkNiVhNCVkNiVjMSVjZSVhNSViNyVhOCViNSVkOCViZCViYiViZSVhZiViNCVmMyViNiVkMyViNCVhNiVjMCVlZCUzYyUyZk90aGVyV2F5JTNlJTNjUG9saWNlQ29udGFjdCUzZSUzYyUyZlBvbGljZUNvbnRhY3QlM2UlM2NQb2xpY2VBZGRyZXNzJTNlJTNjJTJmUG9saWNlQWRkcmVzcyUzZSUzY1ZpbG9hdGlvbkRldGFpbCUzZSVkNCVkYSViOCVkZiVjYiVkOSViOSVhYiVjMiViNyVjOSVjZiViMyVhYyVjYiVkOSViMiViYiVkNyVlMzUwJTI1JWI1JWM0JTNjJTJmVmlsb2F0aW9uRGV0YWlsJTNlJTNjRGVhbEZsYWclM2UxJTNjJTJmRGVhbEZsYWclM2UlM2NEZWFsTXNnJTNlJWJmJWM5JWQyJWQ0JWIwJWVjJWMwJWVkJTNjJTJmRGVhbE1zZyUzZSUzY0xpdmVCaWxsJTNlMCUzYyUyZkxpdmVCaWxsJTNlJTNjUmVmZXJGZWUlM2UxOTAwJTNjJTJmUmVmZXJGZWUlM2UlM2NXc2glM2U0NDUyMDYwMDAzOTI0ODI1JTNjJTJmV3NoJTNlJTNjJTJmUmVjb3JkJTNlJTNjJTJmUmVjb3JkcyUzZSZNZDVTaWduPTkxMTAwRkIzQTk5NzA3MjNCODAyRTc5NDM2NUY5QkRF")));
				String newRecord = decoder.decode(new String( Base64.decode(recordResult)));
				String xmlRecord = newRecord.substring(newRecord.indexOf("<Records>"), newRecord.indexOf("</Records>"))+"</Records>";
				Document doc=DocumentHelper.parseText(xmlRecord);
		        Element root = doc.getRootElement();
			    List<Element> records=root.elements("Record");
//			    if(items==null||items.size()!=1){
//			    }
			    for(Element ele:records){
			    	List<Element> item=ele.elements();
			    	JtfkBean jtfk = new JtfkBean();
			    	for(Element item1:item){
			    		if("ViolationId".equals(item1.getName())){
			    			jtfk.setViolationId(item1.getText());
			    		}else if("ViolationTime".equals(item1.getName())){
			    			jtfk.setViolationTime(item1.getText());
			    		}else if("ViloationLocation".equals(item1.getName())){
			    			jtfk.setViloationLocation(item1.getText());
			    		}else if("TotalFee".equals(item1.getName())){
			    			jtfk.setTotalFee(item1.getText());
			    		}else if("FineFee".equals(item1.getName())){
			    			jtfk.setFineFee(item1.getText());
			    		}else if("DealFee".equals(item1.getName())){
			    			jtfk.setDealFee(item1.getText());
			    		}else if("LateFee".equals(item1.getName())){
			    			jtfk.setLateFee(item1.getText());
			    		}else if("DealTime".equals(item1.getName())){
			    			jtfk.setDealTime(item1.getText());
			    		}else if("OtherWay".equals(item1.getName())){
			    			jtfk.setOtherWay(item1.getText());
			    		}else if("PoliceContact".equals(item1.getName())){
			    			jtfk.setPoliceContact(item1.getText());
			    		}else if("PoliceAddress".equals(item1.getName())){
			    			jtfk.setPoliceAddress(item1.getText());
			    		}else if("ViloationDetail".equals(item1.getName())){
			    			jtfk.setViloationDetail(item1.getText());
			    		}else if("DealFlag".equals(item1.getName())){
			    			jtfk.setDealFlag(item1.getText());
			    		}else if("DealMsg".equals(item1.getName())){
			    			jtfk.setDealMsg(item1.getText());
			    		}else if("LiveBill".equals(item1.getName())){
			    			jtfk.setLiveBill(item1.getText());
			    		}else if("ReferFee".equals(item1.getName())){
			    			jtfk.setReferFee(item1.getText());
			    		}else if("Wsh".equals(item1.getName())){
			    			jtfk.setWsh(item1.getText());
			    		}
			    	}
			    	jtfkList.add(jtfk);
			    }
			}
	     post.releaseConnection();
	     ((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown();
	     if(null!=client)
	     {
	    	 client=null;
	     }
	     return jtfkList;
	}
	
	/**
	 * @param cftTransId
	 * @param datetime
	 * @param violationId
	 * @param mailReceipt
	 * @param mailAddr
	 * @param mailFee
	 * @param feeType
	 * @param totalFee
	 * @return map
	 * @throws HttpException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> createOrder(String cftTransId,String datetime,String violationId,String mailReceipt,String mailAddr,String mailFee,String feeType,String totalFee) throws HttpException, IOException, DocumentException{
		String Command="2";
	     String UserId="EN1_A61E447C7FF746A960DAA70E609577A0";
	     String CftMerId="27";
	     String MerchantId="1002";
	     String Version="1";
	     String ResFormat="xml";
	     String Attach="abcd";
	     String Sign;
	     String QueryText="CftTransId="+cftTransId+"&" +
	     		"Datetime="+datetime+"&" +
	     		"ViolationId="+violationId+"&" +
	     		"MailReceipt="+mailReceipt+"&" +
	     		"MailAddr="+mailAddr+"&" +
	     		"MailFee="+mailFee+"&" +
	     		"FeeType="+feeType+"&" +
	     		"TotalFee="+totalFee+"&" +
	     		"MobileNo=18600000001";
	     
	     String msg="";
	     String key="#s^un2ye31<cn%|aoXpR,+hrd2013";
	     
	     
	     MD5 md5 = new MD5();
	     String tmp1= byteArrayToHexString(md5.getDigest(QueryText+"&Md5Key="+key));
	     String tmp2=QueryText+"&Md5Sign="+tmp1;
	     String tmp3= Base64.encode(tmp2.getBytes("gb2312"));
	     QueryText=tmp3;
	    
	     String SignTmp="Attach="+Attach+"&CftMerId="+CftMerId+"&Command="+Command+"&MerchantId="+MerchantId+"&QueryText="+QueryText+"&ResFormat="+ResFormat+"&UserId="+UserId+"&Version="+Version+"&Md5Key="+key;
	     Sign=byteArrayToHexString(md5.getDigest(SignTmp));
	     
	     msg="http://www.gdwz.net/open_cx/ser_wzdb.aspx?Command="+Command+"&MerchantId="+MerchantId+"&Attach="+Attach+"&CftMerId="+CftMerId+"&ResFormat="+ResFormat+"&UserId="+UserId+"&Version="+Version+"&Sign="+Sign+"&QueryText="+QueryText;

	     
	     HttpClient client = new HttpClient();
	     GetMethod post = new GetMethod(msg);
//	     GetMethod post = new GetMethod("http://localhost:9090/oms");
//	     post.setQueryString(queryString);
	     int status = client.executeMethod(post);
	     Map<String, String> resultMap = new HashMap<String, String>();
	     if(status==200){
				String result = post.getResponseBodyAsString();
				
				Document docResult=DocumentHelper.parseText(result);
	            Element rootResult = docResult.getRootElement();
	            List<Element> results=rootResult.elements();
	            for(Element item1:results){
	            	resultMap.put(item1.getName(), item1.getText());
	            }
		 }
	     post.releaseConnection();
	     ((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown();
	     if(null!=client)
	     {
	    	 client=null;
	     }
	     return resultMap;
	}
	/**
	 * @param cftTransId
	 * @param spTransId
	 * @param datetime
	 * @param violationId
	 * @param mailReceipt
	 * @param mailAddr
	 * @param mailFee
	 * @param feeType
	 * @param totalFee
	 * @return map
	 * @throws HttpException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> payOrder(String cftTransId,String spTransId,String datetime,String violationId,String mailReceipt,String mailAddr,String mailFee,String feeType,String totalFee) throws HttpException, IOException, DocumentException{
		String Command="2";
	     String UserId="EN1_A61E447C7FF746A960DAA70E609577A0";
	     String CftMerId="27";
	     String MerchantId="1002";
	     String Version="1";
	     String ResFormat="xml";
	     String Attach="abcd";
	     String Sign;
	     String QueryText="CftTransId="+cftTransId+"&" +
	     		"SpTransId="+spTransId+"&" +
	     		"Datetime="+datetime+"&" +
	     		"ViolationId="+violationId+"&" +
	     		"MailReceipt="+mailReceipt+"&" +
	     		"MailAddr="+mailAddr+"&" +
	     		"MailFee="+mailFee+"&" +
	     		"FeeType="+feeType+"&" +
	     		"TotalFee="+totalFee;
	     
	     String msg="";
	     String key="#s^un2ye31<cn%|aoXpR,+hrd2013";
	     
	     
	     MD5 md5 = new MD5();
	     String tmp1= byteArrayToHexString(md5.getDigest(QueryText+"&Md5Key="+key));
	     String tmp2=QueryText+"&Md5Sign="+tmp1;
	     String tmp3= Base64.encode(tmp2.getBytes("gb2312"));
	     QueryText=tmp3;
	    
	     String SignTmp="Attach="+Attach+"&CftMerId="+CftMerId+"&Command="+Command+"&MerchantId="+MerchantId+"&QueryText="+QueryText+"&ResFormat="+ResFormat+"&UserId="+UserId+"&Version="+Version+"&Md5Key="+key;
	     Sign=byteArrayToHexString(md5.getDigest(SignTmp));
	     
	     msg="http://www.gdwz.net/open_cx/ser_wzdb2.aspx?Command="+Command+"&MerchantId="+MerchantId+"&Attach="+Attach+"&CftMerId="+CftMerId+"&ResFormat="+ResFormat+"&UserId="+UserId+"&Version="+Version+"&Sign="+Sign+"&QueryText="+QueryText;

	     HttpClient client = new HttpClient();
	     GetMethod post = new GetMethod(msg);
//	     GetMethod post = new GetMethod("http://localhost:9090/oms");
	     int status = client.executeMethod(post);
	     Map<String, String> resultMap = new HashMap<String, String>();
	     if(status==200){
				String result = post.getResponseBodyAsString();
				
				Document docResult=DocumentHelper.parseText(result);
	            Element rootResult = docResult.getRootElement();
	            List<Element> results=rootResult.elements();
	            for(Element item1:results){
	            	resultMap.put(item1.getName(), item1.getText());
	            }
		 }
	     post.releaseConnection();
	     ((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown();
	     if(null!=client)
	     {
	    	 client=null;
	     }
	     return resultMap;
	}
	public static void updateAccountAndStatus(String rebatePercent,String userId,int fee,OrderForm orderForm,JtfkForm jtBean,Map queryMap,String tDate) throws Exception{
		DBService db = new DBService(Constants.DBNAME_SCP);
		try {
        	db.setAutoCommit(false);
        	String fcct = db.getString("select fundacct from wlt_facct where termid = "+userId)+"02";
        	FundAcctDao facctDB = new FundAcctDao(db);
        	facctDB.updateChildLeft(fcct,-(fee*(Integer.parseInt(rebatePercent))/100),null);
//        	StringBuffer sql = new StringBuffer();
//            sql.append("update wlt_childfacct set");
//            sql.append(" usableleft=usableleft-"+(fee-fee*(Integer.parseInt(rebatePercent))/100));
//            sql.append(" where");
//            sql.append(" accttypeid = '02' and fundacct = (select fundacct from wlt_facct where termid = "+userId+" )");
            //更新资金账户
//            db.update(sql.toString());
            //修改订单状态
            StringBuffer sql1 = new StringBuffer();
            sql1.append("update "+orderForm.getTableName()+" set");
            sql1.append(" state=4");
            sql1.append(" where id="+orderForm.getId());
            db.update(sql1.toString());
            //记录账号明细
            StringBuffer sql2 = new StringBuffer();	
            sql2.append("insert into wlt_acctbill_"+tDate+"(childfacct,dealserial,tradeaccount,tradetime,tradefee,tradetype,`explain`,state,distime,accountleft,tradeserial,other_childfacct,pay_type)" +
            		" values(");
            sql2.append("'"+orderForm.getFundacct()+"',");
            sql2.append("'"+orderForm.getTradeserial()+"',");
            sql2.append("'"+jtBean.getViolationId()+"',");
            sql2.append("'"+orderForm.getTradetime()+"',");
            sql2.append("'"+orderForm.getFee()+"',");
            sql2.append("'A999',");
            sql2.append("'交通罚款',");
            sql2.append("0,");
            sql2.append("'"+orderForm.getTradetime()+"',");
            String sqlFuncLeft = "select usableleft from wlt_childfacct where childfacct = "+orderForm.getFundacct();
            sql2.append("'"+db.getString(sqlFuncLeft)+"',");
            sql2.append("'"+orderForm.getTradeserial()+"',");
            sql2.append("'',");
            sql2.append("1");
            sql2.append(")");
            db.update(sql2.toString());
            //记录交通罚款明细记录
            StringBuffer sql3 = new StringBuffer();	
    		sql3.append("insert into wlt_jtfklog"+
    				"(orderno,tableName,violationid,viloationlocation,violationtime,totalfee,finefee,dealfee,latefee," +
    				"dealtime,viloationdetail,dealflag,wsh,cfttransid,datetime,mailreceipt,mailaddr,mailfee,feetype,totalfeepay,sptransid,state,phone) " +
    				"values(");
    		sql3.append("'"+orderForm.getTradeserial()+"',");
    		sql3.append("'"+orderForm.getTableName()+"',");
    		sql3.append("'"+jtBean.getViolationId()+"',");
    		sql3.append("'"+jtBean.getViloationLocation()+"',");
    		sql3.append("'"+jtBean.getViolationTime()+"',");
    		sql3.append("'"+jtBean.getTotalFee()+"',");
    		sql3.append("'"+jtBean.getFineFee()+"',");
    		sql3.append("'"+jtBean.getDealFee()+"',");
    		sql3.append("'"+jtBean.getLateFee()+"',");
    		sql3.append("'"+jtBean.getDealTime()+"',");
    		sql3.append("'"+jtBean.getViloationDetail()+"',");
    		sql3.append("'"+jtBean.getDealFlag()+"',");
    		sql3.append("'"+jtBean.getWsh()+"',");
    		sql3.append("'"+queryMap.get("cfttransid")+"',");
    		sql3.append("'"+queryMap.get("datetime")+"',");
    		sql3.append("'"+queryMap.get("mailreceipt")+"',");
    		sql3.append("'"+queryMap.get("mailaddr")+"',");
    		sql3.append("'"+queryMap.get("mailfee")+"',");
    		sql3.append("'"+queryMap.get("feetype")+"',");
    		sql3.append("'"+queryMap.get("totalfee")+"',");
    		sql3.append("'"+queryMap.get("sptransid")+"',");
    		sql3.append("4,");
    		sql3.append("'"+jtBean.getPhone()+"'");
    		sql3.append(")");
    		db.update(sql3.toString());
    		db.commit();
        } catch (Exception ex) {
        	db.rollback();
            throw ex;
        } finally {
            db.close();
        }
	}
	/**
	 * @param tradeNo
	 * @param tableName
	 * @param ordStatus
	 * @throws Exception
	 */
	public static void updateOrderStatus(String tradeNo,String tableName,String status) throws Exception{
		DBService db = new DBService(Constants.DBNAME_SCP);
		String ordStatus = "";
		try {
        	db.setAutoCommit(false);
        	if("1".equals(status)){
        		ordStatus = "0";
        		//更新订单状态
        		StringBuffer sql = new StringBuffer();
        		sql.append("update "+tableName+" set");
        		sql.append(" state="+ordStatus);
        		sql.append(" where");
        		sql.append(" tradeserial = '"+tradeNo+"'");
        		db.update(sql.toString());
        		//更新交通明细的状态
        		StringBuffer sql1 = new StringBuffer();
        		sql1.append("update wlt_jtfklog set");
        		sql1.append(" state="+ordStatus);
        		sql1.append(" where");
        		sql1.append(" orderno = '"+tradeNo+"'");
        		db.update(sql1.toString());
        	}else if("2".equals(status)){
        		ordStatus = "1";
        		//更新订单状态
        		StringBuffer sql = new StringBuffer();
        		sql.append("update "+tableName+" set");
        		sql.append(" state="+ordStatus);
        		sql.append(" where");
        		sql.append(" tradeserial = '"+tradeNo+"'");
        		db.update(sql.toString());
        		//更新交通明细的状态
        		StringBuffer sql1 = new StringBuffer();
        		sql1.append("update wlt_jtfklog set");
        		sql1.append(" state="+ordStatus);
        		sql1.append(" where");
        		sql1.append(" orderno = '"+tradeNo+"'");
        		db.update(sql1.toString());
        		//更新账号明细的状态
        		StringBuffer sql2 = new StringBuffer();
        		sql2.append("update wlt_acctbill_"+tableName.split("\\_")[2]+" set");
        		sql2.append(" state=1");
        		sql2.append(" where");
        		sql2.append(" dealserial = '"+tradeNo+"'");
        		db.update(sql2.toString());
			}
    		db.commit();
        } catch (Exception ex) {
        	Log.info("处理交通罚款回调出错"+tradeNo);
        	db.rollback();
            throw ex;
        } finally {
            db.close();
        }
	}
	/**
	 * 把数组转换为16进制字符串表示模式
	 * @param b 字节数组
	 * @return 返回结果集
	 */
	public static String byteArrayToHexString(byte[] b) {
		String result = "";
		for (int i = 0; i < b.length; i++)
			result += byteToHexString(b[i]);
		return result;
	}
	
	/**
	 * 把单个字节转换为字符表示形式
	 * @param b 字节 
	 * @return 返回结果
	 */
	public static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return  hexDigits[d1] + hexDigits[d2];
	}
	
//	十六机制字符串表示
	private static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "A", "B", "C", "D", "E", "F" };
	
	public static void main(String[] args) throws DocumentException {
		URLDecoder decoder =new URLDecoder();  
		String newRecord = decoder.decode(new String( Base64.decode("UmV0TnVtPTYmVG90YWxOdW09NiZSZWNvcmRzPSUzY1JlY29yZHMlM2UlM2NSZWNvcmQlM2UlM2NWaW9sYXRpb25JZCUzZTQ5OTU3NjAlM2MlMmZWaW9sYXRpb25JZCUzZSUzY1Zpb2xhdGlvblRpbWUlM2UyMDEyLTExLTA5KzExJTNhMTMlM2EwMCUzYyUyZlZpb2xhdGlvblRpbWUlM2UlM2NWaWxvYXRpb25Mb2NhdGlvbiUzZSU1YiViNyVmMCVjOSViZCU1ZCViNyVmMCVjOSViZCViNCVmMyViNSVjMCVkNiVkMC0lYmMlYmUlYmIlYWElY2IlYzQlYzIlYjclYzIlYjclYmYlZGElM2MlMmZWaWxvYXRpb25Mb2NhdGlvbiUzZSUzY1RvdGFsRmVlJTNlMjE1MDAlM2MlMmZUb3RhbEZlZSUzZSUzY0ZpbmVGZWUlM2UyMDAwMCUzYyUyZkZpbmVGZWUlM2UlM2NEZWFsRmVlJTNlMTUwMCUzYyUyZkRlYWxGZWUlM2UlM2NMYXRlRmVlJTNlMCUzYyUyZkxhdGVGZWUlM2UlM2NEZWFsVGltZSUzZTclM2MlMmZEZWFsVGltZSUzZSUzY090aGVyV2F5JTNlJWM3JWViJWI0JWY4JWM5JWNmJWJjJWRkJWNhJWJiJWQ2JWE0JWQwJWQwJWNhJWJiJWQ2JWE0JWQ2JWMxJWNlJWE1JWI3JWE4JWI1JWQ4JWJkJWJiJWJlJWFmJWI0JWYzJWI2JWQzJWI0JWE2JWMwJWVkJTNjJTJmT3RoZXJXYXklM2UlM2NQb2xpY2VDb250YWN0JTNlJTNjJTJmUG9saWNlQ29udGFjdCUzZSUzY1BvbGljZUFkZHJlc3MlM2UlM2MlMmZQb2xpY2VBZGRyZXNzJTNlJTNjVmlsb2F0aW9uRGV0YWlsJTNlJWJiJWZhJWI2JWFmJWIzJWI1JWNlJWE1JWI3JWI0JWJkJWZiJWQ2JWI5JWIxJWVhJWNmJWRmJWQ2JWI4JWNhJWJlJWI1JWM0JTNjJTJmVmlsb2F0aW9uRGV0YWlsJTNlJTNjRGVhbEZsYWclM2UxJTNjJTJmRGVhbEZsYWclM2UlM2NEZWFsTXNnJTNlJWJmJWM5JWQyJWQ0JWIwJWVjJWMwJWVkJTNjJTJmRGVhbE1zZyUzZSUzY0xpdmVCaWxsJTNlMCUzYyUyZkxpdmVCaWxsJTNlJTNjUmVmZXJGZWUlM2UyODAwJTNjJTJmUmVmZXJGZWUlM2UlM2NXc2glM2U0NDA2MTQ3OTAwNTMxOTczJTNjJTJmV3NoJTNlJTNjJTJmUmVjb3JkJTNlJTNjUmVjb3JkJTNlJTNjVmlvbGF0aW9uSWQlM2U0OTk1NzU5JTNjJTJmVmlvbGF0aW9uSWQlM2UlM2NWaW9sYXRpb25UaW1lJTNlMjAxMS0wNy0yOCsxMiUzYTAxJTNhMDAlM2MlMmZWaW9sYXRpb25UaW1lJTNlJTNjVmlsb2F0aW9uTG9jYXRpb24lM2UlNWIlYzklYzclY2UlYjIlNWQlYzklZWUlYzklYzclYjglZGYlY2IlZDklYjklYWIlYzIlYjclYzklYzclY2UlYjIlYmQlYmIlYmUlYWYlYzYlZDIlYjElZGYlYjQlZjMlYjYlZDMlY2YlYmQlYjYlY2UlM2MlMmZWaWxvYXRpb25Mb2NhdGlvbiUzZSUzY1RvdGFsRmVlJTNlMjIwMDAlM2MlMmZUb3RhbEZlZSUzZSUzY0ZpbmVGZWUlM2UyMDAwMCUzYyUyZkZpbmVGZWUlM2UlM2NEZWFsRmVlJTNlMjAwMCUzYyUyZkRlYWxGZWUlM2UlM2NMYXRlRmVlJTNlMCUzYyUyZkxhdGVGZWUlM2UlM2NEZWFsVGltZSUzZTclM2MlMmZEZWFsVGltZSUzZSUzY090aGVyV2F5JTNlJWM3JWViJWI0JWY4JWM5JWNmJWJjJWRkJWNhJWJiJWQ2JWE0JWQwJWQwJWNhJWJiJWQ2JWE0JWQ2JWMxJWNlJWE1JWI3JWE4JWI1JWQ4JWJkJWJiJWJlJWFmJWI0JWYzJWI2JWQzJWI0JWE2JWMwJWVkJTNjJTJmT3RoZXJXYXklM2UlM2NQb2xpY2VDb250YWN0JTNlJTNjJTJmUG9saWNlQ29udGFjdCUzZSUzY1BvbGljZUFkZHJlc3MlM2UlM2MlMmZQb2xpY2VBZGRyZXNzJTNlJTNjVmlsb2F0aW9uRGV0YWlsJTNlJWQ0JWRhJWI4JWRmJWNiJWQ5JWI5JWFiJWMyJWI3JWM5JWNmJWIzJWFjJWNiJWQ5JWIyJWJiJWQ3JWUzNTAlMjUlYjUlYzQlM2MlMmZWaWxvYXRpb25EZXRhaWwlM2UlM2NEZWFsRmxhZyUzZTElM2MlMmZEZWFsRmxhZyUzZSUzY0RlYWxNc2clM2UlYmYlYzklZDIlZDQlYjAlZWMlYzAlZWQlM2MlMmZEZWFsTXNnJTNlJTNjTGl2ZUJpbGwlM2UwJTNjJTJmTGl2ZUJpbGwlM2UlM2NSZWZlckZlZSUzZTI4MDAlM2MlMmZSZWZlckZlZSUzZSUzY1dzaCUzZTQ0MTU5MTc5MDAwMTM2ODMlM2MlMmZXc2glM2UlM2MlMmZSZWNvcmQlM2UlM2NSZWNvcmQlM2UlM2NWaW9sYXRpb25JZCUzZTQ5OTU3NjYlM2MlMmZWaW9sYXRpb25JZCUzZSUzY1Zpb2xhdGlvblRpbWUlM2UyMDExLTA2LTAxKzEzJTNhMzUlM2E0MiUzYyUyZlZpb2xhdGlvblRpbWUlM2UlM2NWaWxvYXRpb25Mb2NhdGlvbiUzZSU1YiVkNCVjNiViOCVhMSU1ZCViOSVlMyVjMCVhNSViOCVkZiVjYiVkOUs4OCsyMDAlM2MlMmZWaWxvYXRpb25Mb2NhdGlvbiUzZSUzY1RvdGFsRmVlJTNlMjE1MDAlM2MlMmZUb3RhbEZlZSUzZSUzY0ZpbmVGZWUlM2UyMDAwMCUzYyUyZkZpbmVGZWUlM2UlM2NEZWFsRmVlJTNlMTUwMCUzYyUyZkRlYWxGZWUlM2UlM2NMYXRlRmVlJTNlMCUzYyUyZkxhdGVGZWUlM2UlM2NEZWFsVGltZSUzZTclM2MlMmZEZWFsVGltZSUzZSUzY090aGVyV2F5JTNlJWM3JWViJWI0JWY4JWM5JWNmJWJjJWRkJWNhJWJiJWQ2JWE0JWQwJWQwJWNhJWJiJWQ2JWE0JWQ2JWMxJWNlJWE1JWI3JWE4JWI1JWQ4JWJkJWJiJWJlJWFmJWI0JWYzJWI2JWQzJWI0JWE2JWMwJWVkJTNjJTJmT3RoZXJXYXklM2UlM2NQb2xpY2VDb250YWN0JTNlJTNjJTJmUG9saWNlQ29udGFjdCUzZSUzY1BvbGljZUFkZHJlc3MlM2UlM2MlMmZQb2xpY2VBZGRyZXNzJTNlJTNjVmlsb2F0aW9uRGV0YWlsJTNlJWQ0JWRhJWI4JWRmJWNiJWQ5JWI5JWFiJWMyJWI3JWM5JWNmJWIzJWFjJWNiJWQ5JWIyJWJiJWQ3JWUzNTAlMjUlYjUlYzQlM2MlMmZWaWxvYXRpb25EZXRhaWwlM2UlM2NEZWFsRmxhZyUzZTElM2MlMmZEZWFsRmxhZyUzZSUzY0RlYWxNc2clM2UlYmYlYzklZDIlZDQlYjAlZWMlYzAlZWQlM2MlMmZEZWFsTXNnJTNlJTNjTGl2ZUJpbGwlM2UwJTNjJTJmTGl2ZUJpbGwlM2UlM2NSZWZlckZlZSUzZTI4MDAlM2MlMmZSZWZlckZlZSUzZSUzY1dzaCUzZTQ0NTMwMzAwMDA4Mjc0OTUlM2MlMmZXc2glM2UlM2MlMmZSZWNvcmQlM2UlM2NSZWNvcmQlM2UlM2NWaW9sYXRpb25JZCUzZTQ5OTU3NjQlM2MlMmZWaW9sYXRpb25JZCUzZSUzY1Zpb2xhdGlvblRpbWUlM2UyMDExLTAzLTI3KzE0JTNhMjQlM2EyMCUzYyUyZlZpb2xhdGlvblRpbWUlM2UlM2NWaWxvYXRpb25Mb2NhdGlvbiUzZSU1YiVjOSVjNyVjZCViNyU1ZCVjOSVlZSVjOSVjNyViOCVkZiVjYiVkOSVjYyVlZiVkMCVjNCVkNiVjMSViYSVhMyVjMyVjNSViNiVjZSUzYyUyZlZpbG9hdGlvbkxvY2F0aW9uJTNlJTNjVG90YWxGZWUlM2UyMTUwMCUzYyUyZlRvdGFsRmVlJTNlJTNjRmluZUZlZSUzZTIwMDAwJTNjJTJmRmluZUZlZSUzZSUzY0RlYWxGZWUlM2UxNTAwJTNjJTJmRGVhbEZlZSUzZSUzY0xhdGVGZWUlM2UwJTNjJTJmTGF0ZUZlZSUzZSUzY0RlYWxUaW1lJTNlNyUzYyUyZkRlYWxUaW1lJTNlJTNjT3RoZXJXYXklM2UlYzclZWIlYjQlZjglYzklY2YlYmMlZGQlY2ElYmIlZDYlYTQlZDAlZDAlY2ElYmIlZDYlYTQlZDYlYzElY2UlYTUlYjclYTglYjUlZDglYmQlYmIlYmUlYWYlYjQlZjMlYjYlZDMlYjQlYTYlYzAlZWQlM2MlMmZPdGhlcldheSUzZSUzY1BvbGljZUNvbnRhY3QlM2UlM2MlMmZQb2xpY2VDb250YWN0JTNlJTNjUG9saWNlQWRkcmVzcyUzZSUzYyUyZlBvbGljZUFkZHJlc3MlM2UlM2NWaWxvYXRpb25EZXRhaWwlM2UlZDQlZGElYjglZGYlY2IlZDklYjklYWIlYzIlYjclYzklY2YlYjMlYWMlY2IlZDklYjIlYmIlZDclZTM1MCUyNSViNSVjNCUzYyUyZlZpbG9hdGlvbkRldGFpbCUzZSUzY0RlYWxGbGFnJTNlMSUzYyUyZkRlYWxGbGFnJTNlJTNjRGVhbE1zZyUzZSViZiVjOSVkMiVkNCViMCVlYyVjMCVlZCUzYyUyZkRlYWxNc2clM2UlM2NMaXZlQmlsbCUzZTAlM2MlMmZMaXZlQmlsbCUzZSUzY1JlZmVyRmVlJTNlMjgwMCUzYyUyZlJlZmVyRmVlJTNlJTNjV3NoJTNlNDQwNTkyMDAwMjkxNTc2MSUzYyUyZldzaCUzZSUzYyUyZlJlY29yZCUzZSUzY1JlY29yZCUzZSUzY1Zpb2xhdGlvbklkJTNlNDk5NTc2MiUzYyUyZlZpb2xhdGlvbklkJTNlJTNjVmlvbGF0aW9uVGltZSUzZTIwMTEtMDMtMjcrMTElM2ExOSUzYTAwJTNjJTJmVmlvbGF0aW9uVGltZSUzZSUzY1ZpbG9hdGlvbkxvY2F0aW9uJTNlJTViJWJiJWRkJWQ2JWRkJTVkJWI5JWUzJWI2JWFiJWNhJWExJWJiJWRkJWQ2JWRkJWNhJWQwJWJjJWMzJWI5JWUzJWI4JWRmJWNiJWQ5JWI5JWFiJWMyJWI3JWEzJWE4RzM1JWEzJWE5JWI5JWUzJWJiJWRkJWJiJWRkJWQ2JWRkJWI2JWNlJTNjJTJmVmlsb2F0aW9uTG9jYXRpb24lM2UlM2NUb3RhbEZlZSUzZTIxMDAwJTNjJTJmVG90YWxGZWUlM2UlM2NGaW5lRmVlJTNlMjAwMDAlM2MlMmZGaW5lRmVlJTNlJTNjRGVhbEZlZSUzZTEwMDAlM2MlMmZEZWFsRmVlJTNlJTNjTGF0ZUZlZSUzZTAlM2MlMmZMYXRlRmVlJTNlJTNjRGVhbFRpbWUlM2U3JTNjJTJmRGVhbFRpbWUlM2UlM2NPdGhlcldheSUzZSVjNyVlYiViNCVmOCVjOSVjZiViYyVkZCVjYSViYiVkNiVhNCVkMCVkMCVjYSViYiVkNiVhNCVkNiVjMSVjZSVhNSViNyVhOCViNSVkOCViZCViYiViZSVhZiViNCVmMyViNiVkMyViNCVhNiVjMCVlZCUzYyUyZk90aGVyV2F5JTNlJTNjUG9saWNlQ29udGFjdCUzZSUzYyUyZlBvbGljZUNvbnRhY3QlM2UlM2NQb2xpY2VBZGRyZXNzJTNlJTNjJTJmUG9saWNlQWRkcmVzcyUzZSUzY1ZpbG9hdGlvbkRldGFpbCUzZSVkNCVkYSViOCVkZiVjYiVkOSViOSVhYiVjMiViNyVjOSVjZiViMyVhYyVjYiVkOSViMiViYiVkNyVlMzUwJTI1JWI1JWM0JTNjJTJmVmlsb2F0aW9uRGV0YWlsJTNlJTNjRGVhbEZsYWclM2UxJTNjJTJmRGVhbEZsYWclM2UlM2NEZWFsTXNnJTNlJWJmJWM5JWQyJWQ0JWIwJWVjJWMwJWVkJTNjJTJmRGVhbE1zZyUzZSUzY0xpdmVCaWxsJTNlMCUzYyUyZkxpdmVCaWxsJTNlJTNjUmVmZXJGZWUlM2UxOTAwJTNjJTJmUmVmZXJGZWUlM2UlM2NXc2glM2U0NDEzOTMwMDA2Mjg0MjcwJTNjJTJmV3NoJTNlJTNjJTJmUmVjb3JkJTNlJTNjUmVjb3JkJTNlJTNjVmlvbGF0aW9uSWQlM2U0OTk1NzYxJTNjJTJmVmlvbGF0aW9uSWQlM2UlM2NWaW9sYXRpb25UaW1lJTNlMjAxMS0wMi0wNSsxNSUzYTMyJTNhNDQlM2MlMmZWaW9sYXRpb25UaW1lJTNlJTNjVmlsb2F0aW9uTG9jYXRpb24lM2UlNWIlYmQlZDIlZDElZjQlNWQlYjklZTMlYjYlYWIlY2ElYTElYmQlZDIlZDElZjQlY2ElZDAlYzklZWUlYzklYzclYjglZGYlY2IlZDklYjYlYWIlYjYlY2UxODkuN0tNJWI0JWE2JWEzJWE4JWM5JWM3LSVjOSVlZSVhMyVhOSUzYyUyZlZpbG9hdGlvbkxvY2F0aW9uJTNlJTNjVG90YWxGZWUlM2UyMDgwMCUzYyUyZlRvdGFsRmVlJTNlJTNjRmluZUZlZSUzZTIwMDAwJTNjJTJmRmluZUZlZSUzZSUzY0RlYWxGZWUlM2U4MDAlM2MlMmZEZWFsRmVlJTNlJTNjTGF0ZUZlZSUzZTAlM2MlMmZMYXRlRmVlJTNlJTNjRGVhbFRpbWUlM2U3JTNjJTJmRGVhbFRpbWUlM2UlM2NPdGhlcldheSUzZSVjNyVlYiViNCVmOCVjOSVjZiViYyVkZCVjYSViYiVkNiVhNCVkMCVkMCVjYSViYiVkNiVhNCVkNiVjMSVjZSVhNSViNyVhOCViNSVkOCViZCViYiViZSVhZiViNCVmMyViNiVkMyViNCVhNiVjMCVlZCUzYyUyZk90aGVyV2F5JTNlJTNjUG9saWNlQ29udGFjdCUzZSUzYyUyZlBvbGljZUNvbnRhY3QlM2UlM2NQb2xpY2VBZGRyZXNzJTNlJTNjJTJmUG9saWNlQWRkcmVzcyUzZSUzY1ZpbG9hdGlvbkRldGFpbCUzZSVkNCVkYSViOCVkZiVjYiVkOSViOSVhYiVjMiViNyVjOSVjZiViMyVhYyVjYiVkOSViMiViYiVkNyVlMzUwJTI1JWI1JWM0JTNjJTJmVmlsb2F0aW9uRGV0YWlsJTNlJTNjRGVhbEZsYWclM2UxJTNjJTJmRGVhbEZsYWclM2UlM2NEZWFsTXNnJTNlJWJmJWM5JWQyJWQ0JWIwJWVjJWMwJWVkJTNjJTJmRGVhbE1zZyUzZSUzY0xpdmVCaWxsJTNlMCUzYyUyZkxpdmVCaWxsJTNlJTNjUmVmZXJGZWUlM2UxOTAwJTNjJTJmUmVmZXJGZWUlM2UlM2NXc2glM2U0NDUyMDYwMDAzOTI0ODI1JTNjJTJmV3NoJTNlJTNjJTJmUmVjb3JkJTNlJTNjJTJmUmVjb3JkcyUzZSZNZDVTaWduPTkxMTAwRkIzQTk5NzA3MjNCODAyRTc5NDM2NUY5QkRF")));
//		String msg = "RetNum=6&TotalNum=6&Records=<Records><Record><ViolationId>4995760</ViolationId><ViolationTime>2012-11-09 11:13:00</ViolationTime><ViloationLocation>[佛山]佛山大道中-季华四路路口</ViloationLocation><TotalFee>21500</TotalFee><FineFee>20000</FineFee><DealFee>1500</DealFee><LateFee>0</LateFee><DealTime>7</DealTime><OtherWay>请带上驾驶证行驶证至违法地交警大队处理</OtherWay><PoliceContact></PoliceContact><PoliceAddress></PoliceAddress><ViloationDetail>机动车违反禁止标线指示的</ViloationDetail><DealFlag>1</DealFlag><DealMsg>可以办理</DealMsg><LiveBill>0</LiveBill><ReferFee>2800</ReferFee><Wsh>4406147900531973</Wsh></Record><Record><ViolationId>4995759</ViolationId><ViolationTime>2011-07-28 12:01:00</ViolationTime><ViloationLocation>[汕尾]深汕高速公路汕尾交警埔边大队辖段</ViloationLocation><TotalFee>22000</TotalFee><FineFee>20000</FineFee><DealFee>2000</DealFee><LateFee>0</LateFee><DealTime>7</DealTime><OtherWay>请带上驾驶证行驶证至违法地交警大队处理</OtherWay><PoliceContact></PoliceContact><PoliceAddress></PoliceAddress><ViloationDetail>在高速公路上超速不足50%的</ViloationDetail><DealFlag>1</DealFlag><DealMsg>可以办理</DealMsg><LiveBill>0</LiveBill><ReferFee>2800</ReferFee><Wsh>4415917900013683</Wsh></Record><Record><ViolationId>4995766</ViolationId><ViolationTime>2011-06-01 13:35:42</ViolationTime><ViloationLocation>[云浮]广昆高速K88 200</ViloationLocation><TotalFee>21500</TotalFee><FineFee>20000</FineFee><DealFee>1500</DealFee><LateFee>0</LateFee><DealTime>7</DealTime><OtherWay>请带上驾驶证行驶证至违法地交警大队处理</OtherWay><PoliceContact></PoliceContact><PoliceAddress></PoliceAddress><ViloationDetail>在高速公路上超速不足50%的</ViloationDetail><DealFlag>1</DealFlag><DealMsg>可以办理</DealMsg><LiveBill>0</LiveBill><ReferFee>2800</ReferFee><Wsh>4453030000827495</Wsh></Record><Record><ViolationId>4995764</ViolationId><ViolationTime>2011-03-27 14:24:20</ViolationTime><ViloationLocation>[汕头]深汕高速田心至海门段</ViloationLocation><TotalFee>21500</TotalFee><FineFee>20000</FineFee><DealFee>1500</DealFee><LateFee>0</LateFee><DealTime>7</DealTime><OtherWay>请带上驾驶证行驶证至违法地交警大队处理</OtherWay><PoliceContact></PoliceContact><PoliceAddress></PoliceAddress><ViloationDetail>在高速公路上超速不足50%的</ViloationDetail><DealFlag>1</DealFlag><DealMsg>可以办理</DealMsg><LiveBill>0</LiveBill><ReferFee>2800</ReferFee><Wsh>4405920002915761</Wsh></Record><Record><ViolationId>4995762</ViolationId><ViolationTime>2011-03-27 11:19:00</ViolationTime><ViloationLocation>[惠州]广东省惠州市济广高速公路（G35）广惠惠州段</ViloationLocation><TotalFee>21000</TotalFee><FineFee>20000</FineFee><DealFee>1000</DealFee><LateFee>0</LateFee><DealTime>7</DealTime><OtherWay>请带上驾驶证行驶证至违法地交警大队处理</OtherWay><PoliceContact></PoliceContact><PoliceAddress></PoliceAddress><ViloationDetail>在高速公路上超速不足50%的</ViloationDetail><DealFlag>1</DealFlag><DealMsg>可以办理</DealMsg><LiveBill>0</LiveBill><ReferFee>1900</ReferFee><Wsh>4413930006284270</Wsh></Record><Record><ViolationId>4995761</ViolationId><ViolationTime>2011-02-05 15:32:44</ViolationTime><ViloationLocation>[揭阳]广东省揭阳市深汕高速东段189.7KM处（汕-深）</ViloationLocation><TotalFee>20800</TotalFee><FineFee>20000</FineFee><DealFee>800</DealFee><LateFee>0</LateFee><DealTime>7</DealTime><OtherWay>请带上驾驶证行驶证至违法地交警大队处理</OtherWay><PoliceContact></PoliceContact><PoliceAddress></PoliceAddress><ViloationDetail>在高速公路上超速不足50%的</ViloationDetail><DealFlag>1</DealFlag><DealMsg>可以办理</DealMsg><LiveBill>0</LiveBill><ReferFee>1900</ReferFee><Wsh>4452060003924825</Wsh></Record></Records>&Md5Sign=91100FB3A9970723B802E794365F9BDE";
//		System.out.println(msg.substring(msg.indexOf("<Records>"), msg.indexOf("</Records>"))+"</Records>");
	}
}
