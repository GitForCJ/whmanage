package com.wlt.webm.AccountInfo;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.AccountInfo.bean.SiChuanDianXinBean;
import com.wlt.webm.db.DBService;
import com.wlt.webm.scputil.DateParser;
import com.wlt.webm.scputil.MD5Util;

/**
 * @author adminA
 *
 */
public class scdx {
	/**
	 *  商户id			
	 */
	public static String merchNo="2692";
	/**
	 * 密钥
	 */
	public static String key="PDNxP6fTgl9NrTqA";
	/**
	 * 四川电信 充值
	 * @param orderNo 订单号
	 * @param phoneNumber 电话号码
	 * @param time 日期
	 * @param moneyStr 元为单位
	 * @param interType 订单类型
	 * @param phoneType 号码类型
	 * @param Parameter 号码类型|区域号码
	 * @return int
	 */
	@SuppressWarnings("static-access")
	public static int sjytczFile(String orderNo,String time,String moneyStr,String phoneNumber,String interType,String phoneType,String Parameter)
	{
		String money=Float.parseFloat(moneyStr)/1000+"".trim();
		Log.info("四川电信 充值方法");
		if(orderNo==null || "".equals(orderNo) || time==null || "".equals(time) || money==null || "".equals(money) || phoneNumber==null || "".equals(phoneNumber))
		{
			Log.info("四川电信 充值方法 参数不够 orderNO="+orderNo+"   time="+time+"    money="+money+"   phoneNumber="+phoneNumber);
			return -1;
		}
		//0 电信 1移动2联通
		if("0".equals(phoneType))
			phoneType="11";
		if("1".equals(phoneType))
			phoneType="9";
		if("2".equals(phoneType))
			phoneType="10";
		SiChuanDianXinBean sc=new SiChuanDianXinBean();
		sc.setRequestUrl("http://sup.314pay.com/orderReceive.aspx");
		sc.setMerchNo(merchNo);
		sc.setAccount(phoneNumber);
		sc.setProductId(phoneType);
		sc.setAmt(Float.parseFloat(money));
		sc.setParameter(Parameter);
		sc.setOrderNo(orderNo);//订单号
//		sc.setMerchBackUrl("http://shijianqiao321a.xicp.net:8888/wh/business/bank.do?method=SCCallBack");//测试回调url
		sc.setMerchBackUrl("http://www.wanhuipay.com/business/bank.do?method=SCCallBack");//正式回调url
		
		String str="merchNo="+sc.getMerchNo()+
				"&account="+sc.getAccount() +
						"&productId="+sc.getProductId() +
								"&amt="+sc.getAmt() +
										"&parameter="+sc.getParameter() +
												"&orderNo="+sc.getOrderNo()+
														"&merchBackUrl="+sc.getMerchBackUrl();
		str=str+key;
		String mm=MD5Util.MD5Encode(str,"GB2312");
		sc.setSignMsg(mm);//MAC校验域
		
	    //请求url
	    StringBuffer buf=new StringBuffer();
	    buf.append(sc.getRequestUrl());
		buf.append("?");
		buf.append("merchNo="+sc.getMerchNo());
		buf.append("&account="+sc.getAccount());
		buf.append("&productId="+sc.getProductId());
		buf.append("&amt="+sc.getAmt());
		buf.append("&parameter="+sc.getParameter());
		buf.append("&orderNo="+sc.getOrderNo());
		buf.append("&merchBackUrl="+sc.getMerchBackUrl());
		buf.append("&signMsg="+sc.getSignMsg());
		Log.info("四川电信 请求上行充值"+"   请求类型:"+interType+"  url : "+buf.toString());
		try {
			URL u = new URL(buf.toString());
			HttpURLConnection conn=(HttpURLConnection)u.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("GET");
			InputStream in = conn.getInputStream();       
		    byte b[] =new byte[512];
		    in.read(b);
		    String result=new String(b,"utf-8").trim();
		    in.close();
		    conn.disconnect();
		    Log.info("四川 充值请求返回字符串 ： "+result+"   请求类型:"+interType);
		    if("0".equals(result.trim()))
		    {
		    	Log.info("四川 充值 订单提交成功 ："+orderNo+"   请求类型:"+interType);
//		    	//订单请求成功, 每20秒去查询
		    	if("OF".equals(interType))
		    	{
			    	boolean flag=false;
			    	int con=0;
			    	while(true)
			    	{
			    		if(con>=36)
			    		{
			    			break;
			    		}
			    		Thread.sleep(5000);
			    		 Log.info("四川 OF 充值 查询订单,,,,订单号："+orderNo);
			    		 String a1=scdx.getOrderState(orderNo);
			    		if("0".equals(a1))
			    		{
			    			Log.info("四川充值OF订单状态:充值成功,,,,订单号："+orderNo+",订单状态:"+a1);
			    			return 0;
			    		}
			    		if("-1".equals(a1) || "-2".equals(a1) || "-3".equals(a1) || "-9".equals(a1))
			    		{
			    			Log.info("四川充值OF订单状态:充值成失败,,,,订单号："+orderNo+",订单状态:"+a1);
			    			return -1;
			    		}
			    		con++;
			    	}
			    	return -2;
		    	}
		    	else
		    	{
			    	Log.info("四川 充值 订单上行请求成功,,,,,订单号："+orderNo);
			    	return 0;
		    	}
		    }
		} catch (Exception e) {
			Log.error("四川  发起充值请求 异常,,,订单号："+orderNo+"   请求类型:"+interType+"  异常："+e);
			return -2;
		}
		Log.info("四川 充值请求验证失败,,,,,订单号："+orderNo+"   请求类型:"+interType);
    	//订单请求失败
        return -1;
		
	}
	
	/**订单查询
	 * @param orderNo
	 * @return  订单状态
	 */
	public static String getOrderState(String orderNo)
	{
		Log.info("四川 订单查询请求 ，，，订单号:"+orderNo);
		StringBuffer buffer=new StringBuffer();
		buffer.append("http://sup.314pay.com/orderReceiveSearch.aspx");
		buffer.append("?");
		buffer.append("merchNo="+merchNo);
		buffer.append("&orderNo="+orderNo);
		buffer.append("&signMsg="+MD5Util.MD5Encode("merchNo="+merchNo+"&orderNo="+orderNo+key,"GB2312"));
		Log.info("四川 订单查询请求 url="+buffer.toString());
		try
		{
			URL u = new URL(buffer.toString());
			HttpURLConnection conn=(HttpURLConnection)u.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("GET");
			InputStream in = conn.getInputStream();
		    byte b[] =new byte[1024];
		    in.read(b);
		    String result=new String(b,"utf-8").trim();
		    in.close();
		    conn.disconnect();
		    Log.info("四川 订单号:"+orderNo+"查询请求返回结果 "+result);
  		    if(result!=null && !"".equals(result))
		    {
		    	if(result.indexOf("orderStatus=")!=-1)
		    	{
		    		String str=result.substring(result.indexOf("orderStatus=")+"orderStatus=".length());
		    		if(str.indexOf("&")!=-1)
		    		{
		    			str=str.substring(0,str.indexOf("&"));
		    		}
		    		return str;
		    	}
		    }
		    return "-2";
		}catch(Exception e)
		{
			Log.error("四川 查询订单异常,,,,,"+e);
			return "-2";
		}
	}
	
	
//	/**
//	 * @param arg
//	 */
//	public static void main(String[] arg)
//	{
//		//13350852605
//		System.out.println("return jieguo:"+scdx.sjytczFile("2014061716380012314","20140617163800","10","18926512055","","0"));
//		//201405261700001000
//		//201405261700001001
//		//201405261700001002
//		//201405261700001003
//		//201405261700001004
//		//201405261700001005
//		//201405261700001006
//		//201405271038001007
//		//System.out.println("查询："+getOrderState("201405271044001008"));
//	}
}
