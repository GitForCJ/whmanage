package com.wlt.webm.message;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;

import com.wlt.webm.tool.Tools;

/**
 * 亿美短信网关服务类
 * 
 * @author caiSJ
 * 
 */
public class YMmessage {

	/**
	 * 短信注册
	 * 
	 * @param pwd
	 */
	public static void ymRegistEx() {
		// int i = SingletonClient.getClient().registEx("333951");//测试
		int i = SingletonClient.getClient().registEx("448429");// 正式
		System.out.println("ymRegistEx:" + i);
	}

	/**
	 * 短信发送
	 * 
	 * @param pwd
	 */
	public static boolean ymSendSMS(String[] mobiles, String smsContent) {
		int i = SingletonClient.getClient().sendSMS(mobiles, smsContent, 5);
		System.out.println("ymSendSMS:" + i);
		if (0 == i) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * http短信费发送
	 * 
	 * @param mobile
	 * @param smsContent
	 * @return
	 */
	public static boolean ymSendSMS(String mobile, String code) {
//		String httpPath = "http://58.68.247.137:9053/communication/sendSms.ashx";
//		StringBuffer params = new StringBuffer();
//		// 客户端ID
//		String cid = "8116";
//		// 客户端密码
//		String pwd = "123456wh";
//		// 通道组id
//		String productid = "20692101";
//		// 手号机
//		// 短信内容
//		// 子号码
//		String content=zu(code);
//		String lcode = "123";
//		String sign="";
//		// 短信唯一标识,用于匹配状态报告
//		String ssid =mobile+Tools.getNow3().substring(8)+Tools.buildRandom(4);
//		// 短信类型:15普通短信,32长短信
//		String format = "15";
//		// 客户自定义内容,目前没有用到,不用填写
//		String custom = "";
//		try {
//			params.append("cid=").append(CodingUtils.encodeBase64URL(cid))
//					.append("&").append("pwd=").append(
//							CodingUtils.encodeBase64URL(pwd)).append("&")
//					.append("productid=").append(
//							CodingUtils.encodeURL(productid)).append("&")
//					.append("mobile=").append(
//							CodingUtils.encodeBase64URL(mobile)).append("&")
//					.append("content=").append(
//							CodingUtils.encodeBase64URL(content)).append("&")
//					.append("lcode=").append(lcode).append("&").append("ssid=")
//					.append(ssid).append("&").append("format=").append(format)
//					.append("&").append("sign=").append(
//							CodingUtils.encodeBase64URL(sign)).append("&")
//					.append("custom=").append(CodingUtils.encodeURL(custom));
//			System.out.println(params.toString());
//			String result = HttpUtil.sendPostRequestByParam(httpPath, params
//					.toString());
//
//			System.out.println("结果集:" + result);
//
//			JSONObject dataJson = new JSONObject(result);
//			String province = dataJson.getString("msgid");
//			String city = dataJson.getString("mobile");
//			String status = dataJson.getString("status");
//			if (status.equals("0")) {
//				return true;
//			} else {
//				return false;
//			}
//
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			return false;
//		}
		
		return qxtSendSMS(mobile,zu(code));
	}
	
	public static String zu(String code){
		return "您本次操作验证码:"+code+"。打死都不能告诉别人哦！如非本人操作，请及时修改帐号密码。唯一热线：400-9923-988";
	}
	
	public static String mi(String code){
		return "您的密码已修改为 :"+code+"。请妥善保管，打死都不能告诉别人哦！唯一热线：400-9923-988";
	}

	public static boolean ymSendStatus(){
		//http服务端url
		String httpPath = "http://58.68.247.137:9053/communication/fetchReports.ashx";
		StringBuffer params = new StringBuffer();
		//客户端ID
		String cid="8116";
		//客户端密码
		String pwd="123456wh";
		//一次获取状态报告的数量
		int cnt=1;
		try
		{
			params.append("cid=").append(CodingUtils.encodeBase64URL(cid))
			.append("&").append("pwd=").append(CodingUtils.encodeBase64URL(pwd))
			.append("&").append("cnt=").append(cnt);
			String result=HttpUtil.sendPostRequestByParam(httpPath,params.toString());
			System.out.println(result);
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		
	}
	/**
	 * 获取余额
	 */
	public static void ymGetBundle() {
		String i = null;
		try {
			i = SingletonClient.getClient().getBalance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("ymGetBundle:" + i);
	}
	
	/**
	 * 短信发送通道
	 * @param phone  手机号码
	 * @param content 内容 （验证码  或者密码）
	 * @return
	 */
	public static boolean qxtSendSMS(String phone,String content){
		switch (Tools.DUANXIN_SWITCH_FLAG){
		case 0:
			return qxtSendSMS1(phone,content);//烽火科技旧
		case 1:
			return YDMessage.sendMsg(phone, content);//三三得九
		case 2:
			return FenghuoNew.sendMsg(phone, content);//烽火科技新
		default :
			return false;
		}
	}
	
	/**
	 * 北京企信通短信服务  烽火科技
	 * @param phone  手机号码
	 * @param content 内容 （验证码  或者密码）
	 * @return
	 */
	public static boolean qxtSendSMS1(String phone,String content){
		HttpClient client=null;
		GetMethod get=null;
		boolean flag=false;
		try {
			client =new HttpClient();
			String url="http://118.244.212.86:89/sendsms.asp?name=wanheng&password=wheng637&mobile="+phone
			+"&message="+URLEncoder.encode(content, "GBK");
			get=new GetMethod(url);
			int a=client.executeMethod(get);
			if(200==a){
				String rs=get.getResponseBodyAsString();
				System.out.println(rs);
				if(rs.indexOf("succ")!=-1){
					flag= true;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null!=client&&null!=get){
				get.releaseConnection();
				((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown(); 
                get=null;
				client=null;
			}
		}
		return flag;
	}

	public static boolean getMsgStatus(){
		HttpClient client=null;
		GetMethod get=null;
		boolean flag=false;
		try {
			client =new HttpClient();
			String url="http://118.244.212.86:89/report.asp?name=wanheng&password=wheng637";
			get=new GetMethod(url);
			int a=client.executeMethod(get);
			if(200==a){
				String rs=get.getResponseBodyAsString();
				System.out.println(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null!=client&&null!=get){
				get.releaseConnection();
				((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown(); 
                get=null;
				client=null;
			}
		}
		return flag;
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//手机充值卡业务是指可以为中国移动“全球通”、“动感地带”和“神州行”客户进行充值/交费的充值卡，使用手机充值卡可以轻松实现异地交费。客户购买手机充值卡后，在国内（不含港澳台）均可拨打业务接入码13800138000进行交费。
		
		// YMmessage.ymRegistEx();
		// YMmessage.ymSendSMS(new
		// String[]{"13510223879","13798147934","13760356687"},
		// "您好,截止目前你的基本账户余额为500.897万人民币[中国工商银行]");
		// YMmessage.ymGetBundle(); 13642361000
//		YMmessage.ymSendSMS("15012880537", "12345678");
//		YMmessage.ymSendSMS("18682033916", "11111111");
//		  YMmessage.ymSendSMS("18033402247", "欢迎使用万汇通业务!您本次操作的验证码为:123456请勿将该验证码告诉他人,如有疑问,致电4006 220 216垂询.");
//	       YMmessage.ymSendStatus();
//	System.out.println("欢迎使用万汇通！您本次操作的验证码为：11111111。请勿将该验证码告诉他人，如有疑问，致电4006-220-216垂询。【深圳万恒科技】".length());
//	System.out.println(qxtSendSMS("18682033916",zu("888888")));
//	System.out.println(qxtSendSMS("18682033916",zu("856520")));
//		System.out.println(getMsgStatus());
		
		/**
	String phone="18682033916";
	String content=zu("186820");
	
	InputStream inputStream = null;
    InputStreamReader inputStreamReader = null;
    BufferedReader reader = null;
    StringBuffer resultBuffer = new StringBuffer();
    String tempLine = null;
	try {
		URL url =new URL("http://118.244.212.86:89/sendsms.asp?name=wanheng&password=wheng637&mobile="+phone
				+"&message="+URLEncoder.encode(content, "GBK"));
		URLConnection connection=url.openConnection();
		HttpURLConnection httpURLConnection=(HttpURLConnection)connection;
        if(httpURLConnection.getResponseCode()==200){
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
        }
            System.out.println(resultBuffer.toString());
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
        if (reader != null) {
            reader.close();
        }
        if (inputStreamReader != null) {
            inputStreamReader.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        
    }*/
		
		System.out.println( FenghuoNew.sendMsg("18682033916", "hello万恒科技"));
	}

}
