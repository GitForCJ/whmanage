package com.wlt.webm.message;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * http�ӿڶ��ŷ���ʾ��
 * @author Administrator
 *
 */
public class TestSmsSend {

	
	public static void main(String[] args)
	{
		//http�����url
//		String httpPath = "http://58.68.247.139:1721/sendSms.ashx";
		String httpPath="http://58.68.247.137:9053/communication/sendSms.ashx";
		StringBuffer params = new StringBuffer();
		//�ͻ���ID
		String cid="8116";
		//�ͻ�������
		String pwd="whkj/123456wh";
		//ͨ����id
		String productid="20692101";
		//�ֺŻ�
		String mobile="18682033916";
		//��������
		String content="�����ŷ��Ͳ���";
		//�Ӻ���
		String lcode="123";
		//����Ψһ��ʶ,����ƥ��״̬����
		String ssid="3333";
		//��������:15��ͨ����,32������
		String format="15";
		//�ͻ��Զ���ǩ��,���Բ���
		String sign="[�������Ƽ�]";
		//�ͻ��Զ�������,Ŀǰû���õ�,������д
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
