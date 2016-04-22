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
	public static String userName="szwh"; //用户登录名
	public static String goodsCode="wy0001"; //商品编码
	public static String customerID="wh0001";	//客户标识
	public static String goodsName="123"; //商品名称
	public static String productDesc="123"; //产品描述
	public static String sign="0e1c94d4-a2f2-410b-9d99-b5183ea852df";//密钥
	public static String phoneOraccount="18501370438"; //终端号码
	
	//返回路径
	public static String returnUrl="http://shijianqiao321a.xicp.net:8888/wh/AccountInfo/okInfo.jsp";
	public static String notifyUrl="http://shijianqiao321a.xicp.net:8888/wh/AccountPay.do";
	
	
	/**
	 * 九七惠 账单充值
	 * @param orderNum
	 * @param money 分
	 * @param phoneOraccount
	 * @return 账单提交结果 0成功，-1失败
	 * @throws UnsupportedEncodingException 
	 */
	public static String account(String orderNum,int money) throws UnsupportedEncodingException
	{
		money=money*100;
		Log.info("九七惠 账单支付 account 方法");
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
		Log.info("九七惠 电信账单支付，请求url="+requestUrl);
		return requestUrl.toString();
	}

	public static void main(String[] arg)
	{
		System.out.println(MD5.encode("szwh201408131446071617s0e1c94d4-a2f2-410b-9d99-b5183ea852df"));
	}
}
