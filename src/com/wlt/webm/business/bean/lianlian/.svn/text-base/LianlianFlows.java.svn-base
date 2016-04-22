package com.wlt.webm.business.bean.lianlian;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.Three_DES;
import com.wlt.webm.util.TripleDESUtil;

public class LianlianFlows {

	static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	static HttpClient client = new HttpClient(connectionManager);
	static {
		client.getHttpConnectionManager().getParams().setConnectionTimeout(
				60 * 1000);// 链接时间 60秒，单位是毫秒
		client.getHttpConnectionManager().getParams().setSoTimeout(60 * 1000);// 读取时间
																				// 20秒，单位是毫秒
	}
	
	//测试
//	private static final String CZ_URL = "http://122.224.88.51:18080/trafic/charge.json";
//	private static final String Query_URL = "http://122.224.88.51:18080/common/query.json";
//	private static final String partner_id = "10440014";
//	private static final String orgcode = "KX0000";
//	private static final String agent_id = "sc0018";
//	public static final String key = "FD0762380D980D52";					
//	private static final String password = "";//TripleDESUtil.getInstance().encrypt("88888888", key);
//	private static final String sign_type = "MD5";
//	private static final String pay_password = "";//TripleDESUtil.getInstance().encrypt("88888888", key);
	
	//正式  http://a.aikaixin.com/
	/**
	 * 充值请求url
	 */
	private static final String CZ_URL = "http://183.129.169.172:8080/trafic/charge.json";
	/**
	 * 订单查询结果
	 */
	private static final String Query_URL = "http://183.129.169.172:8080/common/query.json";
	/**
	 * 商户编号
	 */
	private static final String partner_id = "10440014";
	/**
	 * 商户运营机构
	 */
	private static final String orgcode = "KX0000";
	/**
	 * 代理商编号
	 */
	private static final String agent_id = "sc0018";
	/**
	 * 签名秘钥
	 */
	public static final String key = "4585A4C1A7DF496D";
	/**
	 * 代理商密码
	 */						
	private static final String password = "70C32D64D8E01AE93FF556F18764DFBF";//TripleDESUtil.getInstance().encrypt("88888888", key);
	/**
	 * 签名方式
	 */
	private static final String sign_type = "MD5";
	/**
	 * 代理商支付密码
	 */
	private static final String pay_password = "70C32D64D8E01AE93FF556F18764DFBF";//TripleDESUtil.getInstance().encrypt("88888888", key);
	
	/**
	 * head 包头体
	 * 
	 * @return
	 */
	private static HeaderBean getHeader() {
		long timestamp = System.currentTimeMillis();
		HeaderBean bean = new HeaderBean();
		bean.setPartner_id(partner_id);
		bean.setOrgcode(orgcode);
		bean.setAgent_id(agent_id);
		bean.setPassword(password);
		bean.setTimestamp(timestamp + "");
		bean.setSign_type(sign_type);
		return bean;
	}

	/**
	 * 连连流量充值订单
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
		bodyBean.setPay_password(pay_password);
		bodyBean.setOrder_no(orderNum);
		bodyBean.setAccount_no(phoneNum);
		bodyBean.setData(mz);
		bodyBean.setData_type("0");

		HeaderBean headerBean = getHeader();

		StringBuffer buffer = new StringBuffer();
		buffer.append("account_no=" + bodyBean.getAccount_no());
		buffer.append("&agent_id=" + headerBean.getAgent_id());
		buffer.append("&data=" + bodyBean.getData());
		buffer.append("&data_type=" + bodyBean.getData_type());
		buffer.append("&order_no=" + bodyBean.getOrder_no());
		buffer.append("&orgcode=" + headerBean.getOrgcode());
		buffer.append("&partner_id=" + headerBean.getPartner_id());
		buffer.append("&password=" + headerBean.getPassword());
		buffer.append("&pay_password=" + bodyBean.getPay_password());
		buffer.append("&sign_type=" + headerBean.getSign_type());
		buffer.append("&timestamp=" + headerBean.getTimestamp());
		buffer.append(key);

		String sign_str = com.wlt.webm.util.MD5.encode(buffer.toString());

		headerBean.setSign(sign_str);

		JsonBean obj = new JsonBean();
		obj.setHeader(headerBean);
		obj.setBody(bodyBean);

		String send_json = JSON.toJSONString(obj);

		Log.info("连连流量订购,订单号:" + orderNum + ",,请求url:" + send_json);

		PostMethod postMt = new PostMethod(CZ_URL);
		try {
			byte[] bytedata = send_json.getBytes("UTF-8");
			RequestEntity en = new ByteArrayRequestEntity(bytedata);
			postMt.setRequestEntity(en);
			postMt.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
			int st = client.executeMethod(postMt);
			Log.info("连连流量订购,订单号:" + orderNum + ",,http响应状态:" + st);
			if (st == 200) {
				String rs = postMt.getResponseBodyAsString();
				Log.info("连连流量订购,订单号:" + orderNum + ",,响应json内容:" + rs);
				if (rs == null || "".equals(rs.toString())
						|| rs.indexOf("ret_code") == -1) {
					Log.info("连连流量订购,订单号:" + orderNum + ",,解析json,错误");
					return "2";
				}
				rs = rs.substring(rs.indexOf("ret_code"), rs.length());
				rs = rs.substring(0, (rs.indexOf(",") != -1 ? rs.indexOf(",")
						: rs.length()));
				rs = rs.replace("ret_code", "").replace("\"", "").replace(":",
						"").replace("{", "").replace("}", "").replace(",", "")
						.replace(" ", "").trim();

				if ("10000000".equals(rs)) {
					Log.info("连连流量订购,订单号:" + orderNum + ",,订单提交成功,return 0");
					return "0";
				} else {
					Log.info("连连流量订购,订单号:" + orderNum + ",,订单提交失败,return -1");
					return "-1";
				}
			}
		} catch (Exception e) {
			Log.error("连连流量订购,订单号:" + orderNum + ",,调用订单查询接口,,系统异常,ex:" + e);
			return QueryOrders(orderNum,null);
		} finally {
			postMt.releaseConnection();
		}
		Log.info("连连流量订购,订单号:" + orderNum + ",,未知提交状态,return 2");
		return "2";
	}

	/**
	 * 订单查询接口
	 * 
	 * @param orderNum
	 * @param orderTime
	 *            YYYYMMDD
	 * @return String 0 成功 -1失败 2处理中，异常
	 */
	public static String QueryOrders(String orderNum, String orderTime) {
		if(orderTime==null || "".equals(orderTime.trim())){
			orderTime=Tools.getNowDate();
		}
		QueryOrderBean bodyBean = new QueryOrderBean();
		bodyBean.setOrder_no(orderNum);
		bodyBean.setOrder_date((orderTime == null || ""
				.equals(orderTime.trim())) ? Tools.getNowDate() : orderTime);
		bodyBean.setOrder_type("02");

		HeaderBean headerBean = getHeader();

		StringBuffer buffer = new StringBuffer();
		buffer.append("agent_id=" + headerBean.getAgent_id());
		buffer.append("&order_date=" + bodyBean.getOrder_date());
		buffer.append("&order_no=" + bodyBean.getOrder_no());
		buffer.append("&order_type=" + bodyBean.getOrder_type());
		buffer.append("&orgcode=" + headerBean.getOrgcode());
		buffer.append("&partner_id=" + headerBean.getPartner_id());
		buffer.append("&password=" + headerBean.getPassword());
		buffer.append("&sign_type=" + headerBean.getSign_type());
		buffer.append("&timestamp=" + headerBean.getTimestamp());
		buffer.append(key);

		String sign_str = com.wlt.webm.util.MD5.encode(buffer.toString());

		headerBean.setSign(sign_str);

		JsonBean obj = new JsonBean();
		obj.setHeader(headerBean);
		obj.setBody(bodyBean);

		String send_json = JSON.toJSONString(obj);

		Log.info("连连流量订单查询,订单号:" + orderNum + ",,请求url:" + send_json);

		PostMethod postMt = new PostMethod(Query_URL);
		try {
			byte[] bytedata = send_json.getBytes("UTF-8");
			RequestEntity en = new ByteArrayRequestEntity(bytedata);
			postMt.setRequestEntity(en);
			postMt.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
			int st = client.executeMethod(postMt);
			Log.info("连连流量订单查询,订单号:" + orderNum + ",,http响应状态:" + st);
			if (st == 200) {
				String result = postMt.getResponseBodyAsString();
				Log.info("连连流量订单查询,订单号:" + orderNum + ",,响应json内容:" + result);
				if (result == null || "".equals(result.toString())
						|| result.indexOf("ret_code") == -1) {
					Log.info("连连流量订单查询,订单号:" + orderNum + ",,解析json,错误");
					return "2";
				}
				String rs = result;
				rs = rs.substring(rs.indexOf("ret_code"), rs.length());
				rs = rs.substring(0, (rs.indexOf(",") != -1 ? rs.indexOf(",")
						: rs.length()));
				rs = rs.replace("ret_code", "").replace("\"", "").replace(":",
						"").replace("{", "").replace("}", "").replace(",", "")
						.replace(" ", "").trim();
				if ("10000000".equals(rs)) {
					result = result.substring(result.indexOf("status"), result
							.length());
					result = result.substring(0,
							(result.indexOf(",") != -1 ? result.indexOf(",")
									: result.length()));
					result = result.replace("status", "").replace("\"", "")
							.replace(":", "").replace("{", "").replace("}", "")
							.replace(",", "").replace(" ", "").trim();
					if ("SUCCESS".equals(result)) {
						Log.info("连连流量订单查询,订单号:" + orderNum
								+ ",,订单状态:成功,return 0");
						return "0";
					} else if ("FAILED".equals(result)) {
						Log.info("连连流量订单查询,订单号:" + orderNum
								+ ",,订单状态:失败,return -1");
						return "-1";
					} else {
						Log.info("连连流量订单查询,订单号:" + orderNum
								+ ",,订单状态:处理中,return 2");
						return "2";
					}
				}else if("11310100".equals(rs)){
					Log.info("连连流量订单查询,订单号:" + orderNum
							+ ",,订单不存在,return -1");
					return "-1";
				}
			}
		} catch (Exception e) {
			Log.error("连连流量订单查询,订单号:" + orderNum + ",,系统异常,ex:" + e);
		} finally {
			postMt.releaseConnection();
		}
		Log.info("连连流量订单查询,订单号:" + orderNum + ",,未知订单状态,return 2");
		return "2";
	}

	public static void main(String[] args) throws InterruptedException {
//			String s="123456789"+(int)(Math.random()*10000+10000)+"";
//			CZ_Orders("18824588427","30",s);
//			System.out.println();
//			Thread.sleep(3*1000);
//			QueryOrders(s,Tools.getNowDate());

	}
}
