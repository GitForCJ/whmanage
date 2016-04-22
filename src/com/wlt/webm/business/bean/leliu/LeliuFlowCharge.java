package com.wlt.webm.business.bean.leliu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import sun.misc.BASE64Encoder;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;

/**
 * @author 1989
 */
public class LeliuFlowCharge {
	public static String partner_no="100088";//合作方编号
    public static String contract_id="100088";//合同ID
    private static String api_secret="YeHNKyn9AsIvUudV";
    private static String api_iv="1OUKlp84Ar1U2Ynr";
    private static String createUrL="http://llcz.net/trafficweb/api/order/index.php";//"http://120.24.157.105/trafficweb/api/order/index.php";
    private static String queryUrL="http://llcz.net/trafficweb/api/query/index.php";//"http://120.24.157.105/trafficweb/api/query/index.php";
    private static String backUrL="";
	static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	static HttpClient client = new HttpClient(connectionManager);
	static {
		client.getHttpConnectionManager().getParams().setConnectionTimeout(
				60 * 1000);// 链接时间 60秒，单位是毫秒
		client.getHttpConnectionManager().getParams().setSoTimeout(60 * 1000);//读取时间 ,单位是毫秒
	}

	/**
	 * 
	 * @param url
	 * @param data
	 * @param id 订单号
	 * @param ops 操作说明
	 * @return
	 */
	public static String OP(String url, String data, String id,String ops) {
		PostMethod postMt = null;
		try {
			postMt = new PostMethod(url);
			byte[] bytedata=data.getBytes("UTF-8");
			RequestEntity en=new ByteArrayRequestEntity(bytedata);
			postMt.setRequestEntity(en);
			postMt.setRequestHeader("Content-Type","text/json;charset=UTF-8");
			int status = client.executeMethod(postMt);
			Log.info(id+" 操作"+ops+"请求应答码:"+status);
			if (200 == status) {
				String result = convertStreamToString(postMt
						.getResponseBodyAsStream());
				return result;
			}
		} catch (Exception e) {
			Log.error(id+" 操作"+ops+" 系统异常"+ e.toString());
		} finally {
			postMt.releaseConnection();
		}
		return null;
	}

	
	public static String convertStreamToString(InputStream is)
			throws IOException {
		StringBuilder sf = new StringBuilder();
		String line;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while ((line = reader.readLine()) != null) {
				sf.append(line);
			}
		} finally {
			is.close();
			if (null != reader) {
				reader.close();
			}
		}
		return sf.toString();
	}
	
	/**
	 * AES CBC 加密
	 * @param input
	 * @param key
	 * @param vi
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String input, String key, String vi) {
		try {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE,
		new SecretKeySpec(key.getBytes(), "AES"),
		new IvParameterSpec(vi.getBytes()));
		byte[] encrypted = cipher.doFinal(input.getBytes("utf-8"));
		// 此处使用BASE64做转码
		return new BASE64Encoder().encode(encrypted);
//		return DatatypeConverter.printBase64Binary(encrypted);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		return null;
		}
		}
	
	/**
	 * 充值
	 * @param request_no 订单号
	 * @param phone_id    号码
	 * @param face        面额
	 * @param type        运营商类型
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static int leliuFill(String request_no,String phone_id,String face,String type){
		CreateReq req=new CreateReq();
		req.setRequest_no(request_no);
		req.setPhone_id(phone_id);
		req.setOrder_id(request_no);
		if("0".equals(type)){
		req.setPlat_offer_id("DX."+face);
		}else if("2".equals(type)){
			req.setPlat_offer_id("LT"+face);
		}else{
			int liuliang=Integer.parseInt(face);
			if(liuliang>=1024){
				face=liuliang/1024+"GB";
			}
			req.setPlat_offer_id("YD."+face);
		}
		req.setTimestamp(System.currentTimeMillis());
		String data=JSON.toJSONString(req);
		Log.info("乐流充值code原始数据:"+data);
		String aesCBC=encrypt(data,api_secret,api_iv);
		CreateReqFacct fact=new CreateReqFacct();
		fact.setCode(aesCBC);
		String factdata=JSON.toJSONString(fact);
		Log.info("乐流充值最终数据："+factdata);
		String rs=OP(createUrL,factdata,request_no,"FILL");
		Log.info("乐流充值返回结果:"+rs);
		if(null==rs){
			return queryflow(request_no);
		}else{
			CreateRes res=JSON.parseObject(rs, CreateRes.class);
			String code=res.getOrderstatus();
			if("finish".equals(code)){
				return 0;//成功
			}else if("fail".equals(code)){
				return -1;//失败
			}else {
				return 2;//处理中 或者其他
			}
		}
	}
	
	/**
	 * 订单查询
	 * @param request_no
	 * @return 0 成功  -1失败  2处理中，异常
	 */
	public static int queryflow(String request_no){
		QueryReq req=new QueryReq();
		req.setRequest_no(request_no);
		String data=JSON.toJSONString(req);
		Log.info("乐流查询请求数据:"+data);
		String rs=OP(queryUrL,data,request_no,"QUERY");
		Log.info("乐流查询结果:"+URLDecoder.decode(rs));
		if(null!=rs){
			QueryRes res=JSON.parseObject(rs,QueryRes.class);
			String code=res.getOrderstatus();
			if("finish".equals(code)){
				return 0;//成功
			}else if("fail".equals(code)){
				return -1;//失败
			}else {
				return 2;//处理中 或者其他
			}
		}
		return 2;
	}
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
//		System.out.println(leliuFill("0000000000005","18824588427","0","1"));
		
//		queryflow("0000000000005");  
//		CreateReq req=new CreateReq();
//		req.setRequest_no("Q20141117143738");
//		req.setPhone_id("18905172668");
//		req.setOrder_id("Q20141117143738");
//		req.setPlat_offer_id("LXC00000000T");

////		String data="{\"plat_offer_id\":\"LXC00000000T\",\"phone_id\":\"18905172668\",\"facevalue\":\"0\",\"order_id\":\"Q20141117143738\",\"request_no\":\"Q20141117143738\",\"contract_id\":\"100001\"}";
//		String data="{\"plat_offer_id\":\"LXC00000000T\",\"phone_id\":\"18905172668\",\"facevalue\":\"0\",\"order_id\":\"Q20141117143738\",\"request_no\":\"Q20141117143738\",\"contract_id\":\"100001\"}";
//		System.out.println(data);
//		String aesCBC=encrypt(data,"E2RKLiLSOsc4kkSN","1292939640503927");
//		System.out.println(aesCBC);
//		String rs="{\"request_no\":\"\",\"orderstatus\":\"fail\",\"result_code\":\"99999\",\"result_desc\":\"\u4f59\u989d\u4e3a0,\u50a8\u503c\u4e0d\u591f\uff0c\u8bf7\u8054\u7cfb\u5546\u52a1\u5145\u503c\uff01\"}";
//		System.out.println("结果\n"+URLDecoder.decode(rs));
//		HashMap<String,String> maps=JSON.parseObject(rs, HashMap.class);
//		System.out.println("=="+maps.get("orderstatus"));
		String str="\u8ba2\u5355\u672a\u53d7\u7406\u6216\u4e0d\u5b58\u5728";
		System.out.println("结果\n"+URLDecoder.decode(str));
		
		
	}

}
