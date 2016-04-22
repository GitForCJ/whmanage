package com.wlt.webm.AccountInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;

import com.commsoft.epay.util.logging.Log;
import com.hisun.iposm.HiThreeDes;
import com.hisun.iposm.HiiposmUtil;
import com.wlt.webm.util.Tools;

public class HeBao {
	
	//正式环境地址
	private static final String CZ_URL="https://ipos.10086.cn/ips/phoneChargeService";
	//正式查询url
	private static final String Query_URL="https://ipos.10086.cn/ips/phoneChargeService";
	//正式签名秘钥
	private static final String MD5_SIGN_KEY="whtech123";
	//正式商户编号
	private static final String merchantId="888009948145719";

//	//测试充值url
//	private static final String CZ_URL="http://211.138.236.210/ips/phoneChargeService";
//	//测试查询url
//	private static final String Query_URL="http://211.138.236.210/ips/phoneChargeService";
//	//测试签名秘钥
//	private static final String MD5_SIGN_KEY="whtech123";
//	//测试商户编号
//	private static final String merchantId="888009941110018";
	
	//回调地址
	private static final String notifyUrl="http://www.wanhuipay.com/HeBaoBack.do";
	//签名方式
	private static final String signType="MD5";
	//接口类型
	private static final String type="PhoneChargesOffline";
	//查询接口类型
	private static final String type2="PhoneChargesQuery";
	//密码更新接口
	private static final String type3="TransKeyChange";
	//版本号
	private static final String version="2.0.0";
	//字符集
	private static final String characterSet="00";
	//币种
	private static final String currency="00";
	//短信标志
	private static final String smsFlag="1";
	//短信魔板
	private static final String smsCd="PES045";
	
	/**
	 * 话费充值  
	 * @param requestId
	 * @param mobileNo 手机号
	 * @param amount 分
	 * @param inType OF 
	 * @return null
	 */
	public static String Order_Recharge(String requestId,String mobileNo,String amount,String inType){
		String rsCode="2";
		StringBuffer buf=new StringBuffer(CZ_URL);
		buf.append("?");
		buf.append("characterSet="+characterSet);
		buf.append("&notifyUrl="+notifyUrl);
		buf.append("&merchantId="+merchantId);
		buf.append("&requestId="+requestId);
		buf.append("&signType="+signType);
		buf.append("&type="+type);
		buf.append("&version="+version);
		buf.append("&hmac=#@@@#@@#");
		buf.append("&amount="+amount);
		buf.append("&mobileNo="+mobileNo);
		buf.append("&currency="+currency);
		buf.append("&smsFlag="+smsFlag);
		buf.append("&smsCd="+smsCd);
		
		String yc=characterSet+notifyUrl+merchantId+requestId+signType+type+version+amount+mobileNo+currency+smsFlag+smsCd;
		HiiposmUtil util = new HiiposmUtil();
		String hmac1 = util.MD5Sign(yc, MD5_SIGN_KEY);
		
		String http_send=buf.toString().replace("#@@@#@@#",hmac1);
		
		Log.info("和包话费充值,订单号："+requestId+",,,请求url:"+http_send);
		
		HttpClient client = null;
		GetMethod get = null;
		try {
			client = new HttpClient();
			get = new GetMethod(http_send);
			client.getHttpConnectionManager().getParams().setConnectionTimeout(60*1000);
			client.getHttpConnectionManager().getParams().setSoTimeout(60*1000);
			int status = client.executeMethod(get);
			Log.info("和包话费充值,订单号："+requestId+",,,http响应状态码:"+status);
			if (status == 200) {
				String rs=get.getResponseBodyAsString();
				Log.info("和包话费充值,订单号："+requestId+",,,http响应内容:"+rs);
				if(rs.indexOf("returnCode")!=-1){
					String end_str=rs.substring(rs.indexOf("returnCode=")+"returnCode=".length(),rs.length());
					if(end_str.indexOf("&")!=-1){
						end_str=end_str.substring(0,end_str.indexOf("&"));
					}
					if(end_str.endsWith("00000")){
						//提交成功
						Log.info("和包话费充值,订单号："+requestId+",,,http提交成功");
						rsCode = "0";
					}else{
						//提交失败
						Log.info("和包话费充值,订单号："+requestId+",,,http提交失败");
						rsCode = "-1";
					}
				}
			}
		} catch (Exception ex) {
			Log.error("和包话费充值,订单号："+requestId+",,,http请求异常,,ex:"+ex);
			rsCode = "2";
		} finally {
			get.releaseConnection();
			((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
			if (null != client) {
				client = null;
			}
		}
		if("OF".equals(inType) && !"-1".equals(rsCode)){
			int con=0;
			while(con<20){
				con++;
				if(con>0 && con<=3){
					try { Thread.sleep(3*1000); } catch (Exception e) { }
				}
				if(con>3 && con<=10){
					try { Thread.sleep(10*1000); } catch (Exception e) { }
				}
				if(con>10){
					try { Thread.sleep(20*1000); } catch (Exception e) { }
				}
				
				String rs=Order_Query(requestId);
				Log.info("和包话费充值,OF供货,订单号："+requestId+",,,http订单提交成功,,循环第："+con+"次查询,,充值充值结果(0成功 -1失败 2处理中或异常):"+rs);
				if("0".equals(rs) || "-1".equals(rs)){
					return rs;
				}
			}
		}
		Log.info("和包话费充值,订单号："+requestId+",,,订单提交结果(0成功  -1失败  2处理中，异常),return:"+rsCode+",,,是否OF供货:"+("OF".equals(inType)?"true":"false"));
		return rsCode;
	}
	
	
	/**
	 * 订单查询
	 * @param queryRequestId 充值订单号
	 * @return String  0成功  -1失败  2处理中，异常
	 */
	public static String Order_Query(String queryRequestId){
		
		String requestId=Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((char)(new Random().nextInt(26) + (int)'a'))+((int)(Math.random()*1000)+1000);
		
		StringBuffer buf=new StringBuffer(Query_URL);
		buf.append("?");
		buf.append("&merchantId="+merchantId);
		buf.append("&requestId="+requestId);
		buf.append("&signType="+signType);
		buf.append("&type="+type2);
		buf.append("&version="+version);
		buf.append("&queryRequestId="+queryRequestId);
		buf.append("&hmac=#@@@#@@#");
		HiiposmUtil util = new HiiposmUtil();
		String hmac1 = util.MD5Sign(merchantId+requestId+signType+type2+version+queryRequestId, MD5_SIGN_KEY);
		
		String http_send=buf.toString().replace("#@@@#@@#",hmac1);
		
		Log.info("和包订单查询,查询订单号："+queryRequestId+",,,请求url:"+http_send);
		
		HttpClient client = null;
		GetMethod get = null;
		try {
			client = new HttpClient();
			get = new GetMethod(http_send);
			client.getHttpConnectionManager().getParams().setConnectionTimeout(30*1000);
			client.getHttpConnectionManager().getParams().setSoTimeout(10*1000);
			int status = client.executeMethod(get);
			if (status == 200) {
				String rs=get.getResponseBodyAsString();
				Log.info("和包订单查询,查询订单号："+queryRequestId+",,,http查询响应内容:"+rs);
				if(rs.indexOf("returnCode=")!=-1 && rs.indexOf("status=")!=-1){
					String end_str=rs.substring(rs.indexOf("returnCode=")+"returnCode=".length(),rs.length());
					if(end_str.indexOf("&")!=-1){
						end_str=end_str.substring(0,end_str.indexOf("&"));
					}
					if(end_str.endsWith("00000")){
						//提交成功
						Log.info("和包订单查询,查询订单号："+queryRequestId+",,,http查询提交成功");
						String sta=rs.substring(rs.indexOf("status=")+"status=".length(),rs.length());
						if(sta.indexOf("&")!=-1){
							sta=sta.substring(0,sta.indexOf("&")).trim();
						}
						if("SS".equals(sta)){
							Log.info("和包订单查询,查询订单号："+queryRequestId+",,,http查询提交成功,,,订单充值结果:成功");
							return "0";
						}else if("SF".equals(sta)){
							Log.info("和包订单查询,查询订单号："+queryRequestId+",,,http查询提交成功,,,订单充值结果:失败");
							return "-1";
						}
					}
				}
			}
		} catch (Exception ex) {
			Log.error("和包订单查询,查询订单号："+queryRequestId+",,,http查询请求异常,,ex:"+ex);
		} finally {
			get.releaseConnection();
			((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
			if (null != client) {
				client = null;
			}
		}
		Log.info("和包订单查询,查询订单号："+queryRequestId+",,,没有明确结果,,");
		return "2";
	}
	
	
	/**
	 * 秘钥更新
	 * @param old_password 
	 * @param new_password  新签名秘钥
	 */
	public static void Update_Password(String old_password,String new_password){
		String requestId=Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((char)(new Random().nextInt(26) + (int)'a'))+((int)(Math.random()*1000)+1000);
		
		StringBuffer buf=new StringBuffer(CZ_URL);
		buf.append("?");
		buf.append("&merchantId="+merchantId);
		buf.append("&requestId="+requestId);
		buf.append("&signType="+signType);
		buf.append("&type="+type3);
		buf.append("&version=2.0.1");
		buf.append("&signKeyType=0");
		buf.append("&signKey="+URLEncoder.encode(new_password));
		buf.append("&hmac=#@@@#@@#");
		
		String yc=merchantId+requestId+signType+type3+"2.0.1"+"0"+new_password;
		HiiposmUtil util = new HiiposmUtil();
		String hmac1 = util.MD5Sign(yc, old_password);//"dhp5c07vs2kw"
		
		String http_send=buf.toString().replace("#@@@#@@#",hmac1);
		
		Log.info("和包秘钥更新,订单号："+requestId+",,,请求url:"+http_send);
		
		HttpClient client = null;
		GetMethod get = null;
		try {
			client = new HttpClient();
			get = new GetMethod(http_send);
			client.getHttpConnectionManager().getParams().setConnectionTimeout(30*1000);
			client.getHttpConnectionManager().getParams().setSoTimeout(10*1000);
			int status = client.executeMethod(get);
			Log.info("和包秘钥更新,订单号："+requestId+",,,http响应状态码:"+status);
			if (status == 200) {
				String rs=get.getResponseBodyAsString();
				Log.info("和包秘钥更新,订单号："+requestId+",,,http响应内容:"+rs);
				if(rs.indexOf("returnCode")!=-1){
					String end_str=rs.substring(rs.indexOf("returnCode=")+"returnCode=".length(),rs.length());
					if(end_str.indexOf("&")!=-1){
						end_str=end_str.substring(0,end_str.indexOf("&"));
					}
					if(end_str.endsWith("00000")){
						//提交成功
						Log.info("和包秘钥更新,订单号："+requestId+",,,更新成功");
					}else{
						//提交失败
						Log.info("和包秘钥更新,订单号："+requestId+",,,更新失败");
					}
				}
			}
		} catch (Exception ex) {
			Log.error("和包秘钥更新,订单号："+requestId+",,,http请求异常,,ex:"+ex);
		} finally {
			get.releaseConnection();
			((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
			if (null != client) {
				client = null;
			}
		}
		return ;
	}
	
	
	public static void main(String[] args) {
//		更新秘钥									原秘钥，右补0,32位					新秘钥，字符数组组合
//		String newsignKey = HiThreeDes.encryptMode("88800994814571900000000000000000", "whtech123"); 
//		Update_Password("888009948145719",newsignKey);
//		try { Thread.sleep(80*1000); } catch (InterruptedException e1) {}
		
//		String newsignKey = HiThreeDes.encryptMode("dhp5c07vs2kw00000000000000000000", "whtech123"); 
//		System.out.println(newsignKey);
//		try {
//			System.out.println(URLEncoder.encode(newsignKey,"gbk"));
//		} catch (UnsupportedEncodingException e) {}
//		System.out.println(HiThreeDes.decryptMode("dhp5c07vs2kw00000000000000000000",newsignKey));
		
//		HeBao.Order_Recharge("2222222222","18824588427","1000","");
		
//		Order_Query("201510291414411157qH");
	}
}
