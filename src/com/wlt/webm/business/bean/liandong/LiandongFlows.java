package com.wlt.webm.business.bean.liandong;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.commsoft.epay.util.logging.Log;

public class LiandongFlows {

	static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	static HttpClient client = new HttpClient(connectionManager);
	static {
		client.getHttpConnectionManager().getParams().setConnectionTimeout(
				60 * 1000);// 链接时间 60秒，单位是毫秒
		client.getHttpConnectionManager().getParams().setSoTimeout(60 * 1000);// 读取时间
																				// 20秒，单位是毫秒
	}
	
	/**
	 * 充值请求url
	 */
	private static final String CZ_URL = "http://114.113.159.224:9001/flow/std/order";
	/**
	 * 产品查询结果
	 */
	private static final String Query_URL = "http://114.113.159.224:9001/flow/std/queryproduct";
	/**
	 * 签名key
	 */
	private static final String key = "35150511B865466C";
	/**
	 * 签名IV
	 */
	private static final String iv = "B16FCC9B7C477F78";
	/**
	 * 渠道标识
	 */
	private static final String sign = "4359F5A50C4D4EB5900DAAEF9D576D99";
	
	/**
	 * 回调地址
	 */
	private static final String callbackUrl = "http://bigrookie.vicp.cc/whmanage/LiandongBack.do";
	
	/**
	 * head 包头体
	 * 
	 * @return
	 */
	private static HeaderBean getHeader() {
		long timestamp = System.currentTimeMillis();
		HeaderBean bean = new HeaderBean();
		bean.setAdaptorId(sign);
		return bean;
	}

	/**
	 * 联动流量充值订单
	 * 
	 * @param phoneNum
	 *            充值号码
	 * @param mz
	 *            500M 则填 500
	 * @param orderNum
	 *            订单号
	 * @return String 0 成功 -1失败 2处理中，异常
	 */
	public static String CZ_Orders(String phoneNum, String mz, String orderNum) {
		BodyOrderBean bodyBean = new BodyOrderBean();
		bodyBean.setOrderId(orderNum);
		bodyBean.setMobile(phoneNum);
		bodyBean.setProductId(mz);
		bodyBean.setOperator("MOBILE");
		bodyBean.setProvince("CHINA");
		bodyBean.setProxOrder(0);
		bodyBean.setCallbackUrl(callbackUrl);

		HeaderBean headerBean = getHeader();

		JsonBean obj = new JsonBean();
		obj.setHeader(headerBean);
		obj.setBody(bodyBean);
		System.out.println("<<<<<<<<<<<<<<<<<<<<<"+JSON.toJSONString(obj));
		PostMethod postMt = new PostMethod(CZ_URL);
		try {
			String send_json = Aes128CBCUtil.encrypt(JSON.toJSONString(obj), key, iv);
			System.out.println("send_json-----------------"+send_json);
			Log.info("联动流量订购,订单号:" + orderNum + ",,请求url:" + send_json);
			byte[] bytedata = send_json.getBytes("UTF-8");
			RequestEntity en = new ByteArrayRequestEntity(bytedata);
			postMt.setRequestEntity(en);
			postMt.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
			int st = client.executeMethod(postMt);
			Log.info("联动流量订购,订单号:" + orderNum + ",,http响应状态:" + st);
			if (st == 200) {
				String rs = postMt.getResponseBodyAsString();
				Log.info("联动流量订购,订单号:" + orderNum + ",,响应json内容:" + rs);
				
				JSONObject json = JSON.parseObject(rs);
				if (rs == null || "".equals(rs.toString())
						|| rs.indexOf("ret_code") == -1) {
					Log.info("联动流量订购,订单号:" + orderNum + ",,解析json,错误");
					return "2";
				}

				if ("0000".equals(json.get("respCode"))) {
					Log.info("联动流量订购,订单号:" + orderNum + ",,订单提交成功,return 0");
					return "0";
				} if ("E0020".equals(json.get("respCode"))) {
					Log.info("联动流量订购,订单号:" + orderNum + ",,订单提交失败,return -1");
					return "-1";
				} else {
					Log.info("联动流量订购,订单号:" + orderNum + ",,订单充值中或异常,return 2");
					return "2";
				}
			}
		} catch (Exception e) {
			Log.error("联动流量订购,订单号:" + orderNum + ",,调用订单查询接口,,系统异常,ex:" + e);
		} finally {
			postMt.releaseConnection();
		}
		Log.info("联动流量订购,订单号:" + orderNum + ",,未知提交状态,return 2");
		return "2";
	}

	/**
	 * 产品查询接口
	 * 
	 * @param orderNum
	 * @param orderTime
	 *            YYYYMMDD
	 * @return String 0 成功 -1失败 2处理中，异常
	 */
	public static void QueryProducts() {
		QueryProductBean proBean = new QueryProductBean();
		proBean.setOperator("MOBILE");
		proBean.setProvince("CHINA");
		proBean.setType("S");

		String send_json = JSON.toJSONString(proBean);

		PostMethod postMt = new PostMethod(Query_URL);
		try {
			byte[] bytedata = send_json.getBytes("UTF-8");
			RequestEntity en = new ByteArrayRequestEntity(bytedata);
			postMt.setRequestEntity(en);
			postMt.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
			int st = client.executeMethod(postMt);
			Log.info("联动流量产品查询,,http响应状态:" + st);
			if (st == 200) {
				String result = postMt.getResponseBodyAsString();
				System.out.println("result:"+result);
				ResponseMessageBean bean = JSON.parseObject(result, ResponseMessageBean.class);
				Log.info("联动流量产品查询,,响应json内容:" + result);
			}
		} catch (Exception e) {
			Log.error("联动流量产品查询,,系统异常,ex:" + e);
		} finally {
			postMt.releaseConnection();
		}
	}

	public static void main(String[] args) throws InterruptedException {
//			String s="123456789"+(int)(Math.random()*10000+10000)+"";
//			CZ_Orders("18824588427","30",s);
//			System.out.println();
//			Thread.sleep(3*1000);
//			QueryOrders(s,Tools.getNowDate());
		QueryProducts();
	}
}
