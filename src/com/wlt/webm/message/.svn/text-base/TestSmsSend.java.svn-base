package com.wlt.webm.message;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * http接口短信发送示例
 * @author Administrator
 *
 */
public class TestSmsSend {

	
	public static void main(String[] args)
	{
		//http服务端url
//		String httpPath = "http://58.68.247.139:1721/sendSms.ashx";
		String httpPath="http://58.68.247.137:9053/communication/sendSms.ashx";
		StringBuffer params = new StringBuffer();
		//客户端ID
		String cid="8116";
		//客户端密码
		String pwd="whkj/123456wh";
		//通道组id
		String productid="20692101";
		//手号机
		String mobile="18682033916";
		//短信内容
		String content="万恒短信发送测试";
		//子号码
		String lcode="123";
		//短信唯一标识,用于匹配状态报告
		String ssid="3333";
		//短信类型:15普通短信,32长短信
		String format="15";
		//客户自定义签名,可以不填
		String sign="[深圳万恒科技]";
		//客户自定义内容,目前没有用到,不用填写
		String custom="";
		try
		{
			params.append("cid=").append(CodingUtils.encodeBase64URL(cid))
			.append("&").append("pwd=").append(CodingUtils.encodeBase64URL(pwd))
			.append("&").append("productid=").append(CodingUtils.encodeURL(productid))
			.append("&").append("mobile=").append(CodingUtils.encodeBase64URL(mobile))
			.append("&").append("content=").append(CodingUtils.encodeBase64URL(content))
			.append("&").append("lcode=").append(lcode)
			.append("&").append("ssid=").append(ssid)
			.append("&").append("format=").append(format)
			.append("&").append("sign=").append(CodingUtils.encodeBase64URL(sign))
			.append("&").append("custom=").append(CodingUtils.encodeURL(custom));
			System.out.println(params.toString());
			String result=HttpUtil.sendPostRequestByParam(httpPath,params.toString());
			System.out.println(result);
			
			JSONObject  dataJson=new JSONObject(result);
//					JSONObject  response=dataJson.getJSONObject("response");
//					JSONArray data=response.getJSONArray("data");
//					JSONObject info=data.getJSONObject(0);
					String province=dataJson.getString("msgid");
					String city=dataJson.getString("mobile");
					String district=dataJson.getString("status");
					 System.out.println(province+" "+city+"  "+district);
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	
	
	
	
	
}
