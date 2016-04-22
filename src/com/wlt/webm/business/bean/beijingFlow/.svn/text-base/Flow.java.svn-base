package com.wlt.webm.business.bean.beijingFlow;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.message.YDMessage;
import com.wlt.webm.tool.Tools;

public class Flow {
	/**
	 * 正式环境
	 */
	private static final String URL = "http://61.50.254.200:8001/ua/proOrder";

	private static final String query_url="http://61.50.254.200:8001/ua/result";
	
	private static final String MD5_KEYString = "77354924";
	private static final String p = "G"; // 用户类型（G-GSM,
	private static final String a = "0";// 生效类型:0 当月生效
	private static final String u = "72656";// 渠道工号

	private static final HashMap<String, String> maps = new HashMap<String, String>();

	static {
		maps.put("50", "G00050");
		maps.put("200", "G00200");
		maps.put("20", "G00020");
		maps.put("100", "G00100");
		maps.put("500", "G00500");
	}

	/**
	 * 北京流量充值
	 * 
	 * @param phone
	 *            电话号码
	 * @param pro_code
	 *            产品代码
	 * @return String 0成功 -1失败 其他处理中
	 */
	public static String BeiJin_Flow(String phone, String pro_code) {
		String mzString=pro_code;
		pro_code = maps.get(pro_code);
		if (null == pro_code) {
			Log.info("phone:" + phone + ",,,北京流量充值,没有对应的流量产品档位,产品档位:" + pro_code);
			return "-1#";// 没有对应的流量产品档位
		}
		String md5_String = MD5_KEYString + phone.trim() + p + pro_code.trim() + a + u;
		String m = "";
		try {
			m = VIPMD5.encode(md5_String);
		} catch (NoSuchAlgorithmException e1) {
			Log.error("phone:" + phone + ",,,北京流量充值,,组装加密字符串异常,,订单充值失败,return -1,,ex:" + e1);
			return "-1#";// 组装加密字符串错误
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(URL);
		buffer.append("/t/" + phone.trim());
		buffer.append("/p/" + p);
		buffer.append("/b/" + pro_code.trim());
		buffer.append("/a/" + a);
		buffer.append("/u/" + u);
		buffer.append("/m/" + m);
		Log.info("phone:" + phone + ",,,北京流量充值请求,,,url:" + buffer.toString());
		HttpClient client = new HttpClient();
		GetMethod get = new GetMethod(buffer.toString());
		client.getHttpConnectionManager().getParams().setConnectionTimeout(60 * 1000);
		client.getHttpConnectionManager().getParams().setSoTimeout(60 * 1000);
		int status = -1;
		try {
			status = client.executeMethod(get);
		} catch (Exception e1) {
			Log.error("phone:" + phone + ",,,北京流量充值,,http链接异常,,订单充值失败,return -1,,ex:" + e1);
			return "2#";
		}
		Log.info("phone:" + phone + ",,,北京流量充值,httpclient响应状态:" + status);
		if (status == 200) {
			String result = "";
			try {
				result = get.getResponseBodyAsString();
			} catch (IOException e) {
				get.releaseConnection();
				((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
				if (null != client) { client = null; }
				Log.error("phone:" + phone + ",,,北京流量充值,http请求成功,响应http状态200,读取响应内容异常,return 2,处理中,,ex:" + e);
				return "2#";
			}
			Log.info("phone:" + phone + ",,,北京流量充值,响应字符串,,,result:" + result);
			if (result == null || "".equals(result) || result.indexOf("code") == -1) {
				get.releaseConnection();
				((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
				if (null != client) { client = null; }
				Log.info("phone:" + phone + ",,,北京流量充值,解析响应内容错误,return  2 处理中");
				return "2#";
			} else {
				Result bean = (Result) JSON.parseObject(result, Result.class);
				if ("00000".equals(bean.getResult().getCode())) {
					get.releaseConnection();
					((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
					if (null != client) { client = null; }
					Log.info("phone:" + phone + ",,,北京流量充值,充值成功,return 0 成功,返回订单号:" + bean.getResult().getKey());
					//调用发短信接口
					YDMessage.HeandMsg(phone,mzString);
					return "0#" + bean.getResult().getKey();
				} else {
					get.releaseConnection();
					((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
					if (null != client) { client = null; }
					Log.info("phone:" + phone + ",,,北京流量充值,充值失败,return -1 失败");
					return "-1#";
				}
			}
		} else {
			get.releaseConnection();
			((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
			if (null != client) { client = null; }
			Log.info("phone:" + phone + ",,,北京流量充值,http请求失败,响应http状态不为200,return -1 失败");
			return "2#";
		}
	}
	
	/**
	 * 北分订单查询接口
	 * @param wh_order_num 
	 * @param beifen_orderid 北分充值接口 返回的  订单号 
	 * @return String 0成功 -1失败  其他处理中，异常
	 */
	public static String BeiFen_Query_Order(String wh_order_num,String beifen_orderid){
		if(wh_order_num==null || "".equals(wh_order_num) || beifen_orderid==null || "".equals(beifen_orderid.trim())){
			Log.info("北分流量查询接口,万恒订单号:"+wh_order_num+",北分订单号:"+beifen_orderid+",,,,缺少必要参数,,return 2 处理中");
			return "2";
		}
		HttpClient client=null;
		GetMethod get=null;
		try{
			String sign=MD5_KEYString + beifen_orderid + u;
			StringBuffer buf=new StringBuffer(query_url);
			buf.append("/k/"+beifen_orderid);
			buf.append("/u/"+u);
			buf.append("/m/"+VIPMD5.encode(sign));
			client= new HttpClient();
			get = new GetMethod(buf.toString());
			client.getHttpConnectionManager().getParams().setConnectionTimeout(30 * 1000);
			client.getHttpConnectionManager().getParams().setSoTimeout(20 * 1000);
			int status = client.executeMethod(get);
			if (status == 200) {
				String result = get.getResponseBodyAsString();
				Log.info("北分流量查询接口,万恒订单号:"+wh_order_num+",北分订单号:"+beifen_orderid+",订单查询响应内容:"+result);
				if (result == null || "".equals(result.trim()) || result.indexOf("code") == -1) {
					Log.info("北分流量查询接口,万恒订单号:"+wh_order_num+",北分订单号:"+beifen_orderid+",return 2,处理中");
					return "2";
				} else {
					Result bean = (Result) JSON.parseObject(result, Result.class);
					if ("00000".equals(bean.getResult().getCode())) {
						Log.info("北分流量查询接口,万恒订单号:"+wh_order_num+",北分订单号:"+beifen_orderid+",return 0,成功");
						return "0";
					} else {
						Log.info("北分流量查询接口,万恒订单号:"+wh_order_num+",北分订单号:"+beifen_orderid+",return -1,失败");
						return "-1";
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			get.releaseConnection();
			((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
		}
		Log.info("北分流量查询接口,万恒订单号:"+wh_order_num+",北分订单号:"+beifen_orderid+",return 2,处理中");
		return "2";
	}
	public static void main(String[] args) {
		System.out.println(BeiFen_Query_Order("11111111","33333333333"));
	}
}
