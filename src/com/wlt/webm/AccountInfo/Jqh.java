package com.wlt.webm.AccountInfo;
import com.commsoft.epay.util.logging.Log;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.wlt.webm.scputil.MD5Util;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.MD5;
public class Jqh 
{
	public static String url="http://www.97hui.com/payyzfservice.shtml";
	public static String userName="szwh"; //�û���¼��
	public static String goodsCode="wy0001"; //��Ʒ����
	public static String customerID="wh0001";	//�ͻ���ʶ
	public static String goodsName="123"; //��Ʒ����
	public static String productDesc="123"; //��Ʒ����
	public static String sign="0e1c94d4-a2f2-410b-9d99-b5183ea852df";//��Կ
	public static String phoneOraccount="18501370438"; //�ն˺���
	
	//����·��
	public static String returnUrl="http://shijianqiao321a.xicp.net:8888/wh/AccountInfo/okInfo.jsp";
	public static String notifyUrl="http://shijianqiao321a.xicp.net:8888/wh/AccountPay.do";
	
	
	/**
	 * ���߻� �˵���ֵ
	 * @param orderNum
	 * @param money ��
	 * @param phoneOraccount
	 * @return �˵��ύ��� 0�ɹ���-1ʧ��
	 * @throws UnsupportedEncodingException 
	 */
	public static String account(String orderNum,int money) throws UnsupportedEncodingException
	{
		money=money*100;
		Log.info("���߻� �˵�֧�� account ����");
		String key=MD5.encode(userName+orderNum+money+goodsCode+goodsName+productDesc+customerID+phoneOraccount+sign);
		StringBuffer requestUrl=new StringBuffer();
		requestUrl.append(url);
		requestUrl.append("?");
		requestUrl.append("userName="+userName);
		requestUrl.append("&payOrder="+orderNum);
		requestUrl.append("&orderMoney="+money);
		requestUrl.append("&goodsCode="+goodsCode);
		requestUrl.append("&goodsName="+goodsName);
		requestUrl.append("&productDesc="+productDesc);
		requestUrl.append("&customerID="+customerID);
		requestUrl.append("&tmnum="+phoneOraccount);
		requestUrl.append("&key="+key);
		requestUrl.append("&returnUrl="+returnUrl);
		requestUrl.append("&notifyUrl="+notifyUrl);
		Log.info("���߻� �����˵�֧��������url="+requestUrl);
		return requestUrl.toString();
	}

	public static void main(String[] arg)
	{
		System.out.println(MD5.encode("szwh201408131446071617s0e1c94d4-a2f2-410b-9d99-b5183ea852df"));
	}
}
