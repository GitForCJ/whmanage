package com.wlt.webm.business.bean.zdhuawei;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.action.Flow3g;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.business.bean.unicom3gquery.HttpFillOP;
import com.wlt.webm.business.bean.zdhuawei.fact.CallbackRequest;
import com.wlt.webm.business.bean.zdhuawei.fact.MD5Util;
import com.wlt.webm.business.bean.zdhuawei.fact.OrderResponse;
import com.wlt.webm.business.bean.zdhuawei.fact.QueryMsgBack;
import com.wlt.webm.business.bean.zdhuawei.fact.QueryResponse;
import com.wlt.webm.business.form.TPForm;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.tool.Tools;

/**
 * @author sysuser.do
 *
 */
public class ZDHuaweiAction extends DispatchAction {
	static HttpFillOP fillOp = new HttpFillOP();

	/**
	 * 查询手机号归属地及手机号所支持的产品列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request queryMessage
	 * @param response
	 * @return
	 * @throws Exception  cp_token
	 */
	public ActionForward proflow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String str1 = "{\"zdws_user_login\":\"0000000265\",\"zdws_method\":\"winksi.topUpPhone.queryMessage\",\"timestamp\":\"2015-11-09 13:52:29\",\"platform\":\"zdws\",  \"token\":\"20151112\",\"version\":\"1.0\", \"format\":\"json\",\"zdws_params\": {\"phone\":\"15899876879\",\"card_type\":\"1\"},\"zdws_sign\":\"4E6BC9272EDB21FF9F597213C82824B1\"}";
		System.out.println("yes,it is!");
		Object result = null;
		/*
		 * String param = convertStreamToString(request.getInputStream());
		 * BufferedReader reader=request.getReader(); StringBuffer strBu=new
		 * StringBuffer(); String s=null; while((s=reader.readLine())!=null){
		 * strBu.append(s); } System.out.println("输入:"+strBu.toString());
		 */
		ZDSystemParam zdsystemparam = new ZDSystemParam();
		JSONObject json = JSONObject.fromObject(str1);
		JSONObject params = JSONObject.fromObject(json.get("zdws_params"));
		QueryMsgBack queryres = new QueryMsgBack();
		// md5(zdws_key+zdws_user_login+timestamp+platform+token+version+format+phone)大写
		if (json.getString("zdws_user_login") == null
				|| json.getString("timestamp") == null
				|| json.getString("platform") == null
				|| json.getString("token") == null
				|| json.getString("version") == null
				|| json.getString("format") == null
				|| params.getString("phone") == null
				|| json.getString("zdws_sign") == null) {
			//result = "{\"desc\":\"参数错误\",\"status\":\"6\"}";
			queryres.setCode("201");
			queryres.setMsg("参数错误");
		} else {
			String temp = MD5Util.zdwz_key + json.getString("zdws_user_login")
					+ json.getString("timestamp") + json.getString("platform")
					+ json.getString("token") + json.getString("version")
					+ json.getString("format") + params.getString("phone");
			if (!MD5Util.MD5Encode(temp, "UTF-8").equalsIgnoreCase(
					json.getString("zdws_sign"))) {
				System.out.println(MD5Util.MD5Encode(temp, "UTF-8")
						.toUpperCase());
				//result = "{\"desc\":\"签名校检失败\",\"status\":\"7\"}";
				queryres.setCode("201");
				queryres.setMsg("签名校检失败");
			} else {
				zdsystemparam.setZdws_user_login(json
						.getString("zdws_user_login"));
				zdsystemparam.setTimestamp(json.getString("timestamp"));
				zdsystemparam.setZdws_sign(json.getString("zdws_sign"));
				zdsystemparam.setZdws_method(json.getString("zdws_method"));
				QueryMsgEntryZD queryMsgEntry = new QueryMsgEntryZD();
				queryMsgEntry.setPhone(params.getString("phone"));
				queryMsgEntry.setCard_type(params.getString("card_type"));
				// System.out.println("手机归属地及产品信息查询接口");
				BiProd biprod = new BiProd();
				System.out.println("here");
				queryMsgEntry.setPhone("18682033916");
				String[] info = biprod.getPhoneInfo(queryMsgEntry.getPhone()
						.substring(0, 7));// 0 是电信 1移动 2联通
				if (info != null) {
					System.out.println("号码属省份=" + info[0] + "   所属类型＝"
							+ info[1] + "    /"
							+ queryMsgEntry.getPhone().substring(0, 7));
				} else {
					System.out.println("加载所属省份出错"
							+ queryMsgEntry.getPhone().substring(0, 7));
				}
				System.out.println("here2");
				TPForm tp = MemcacheConfig.getInstance().getTP(
						zdsystemparam.getZdws_user_login());
				System.out.println("sdfsdfsdf");
				if (tp == null) {
					///result = "{\"desc\":\"接口商不存在\",\"status\":\"18\"}";
					queryres.setCode("201");
					queryres.setMsg("接口商不存在");
				} else {
					String ip = Tools.getIpAddr(request);
					System.out.println("ip=" + ip);
					// if (tp.getIp().indexOf(ip) == -1) {
					// result = "{\"desc\":\"IP不合法\",\"status\":\"19\"}";
					//queryres.setCode("201");
					//queryres.setMsg("IP不合法");
					// } else
					{
						if (info != null) {
							result = fillOp.filterProductJSON(
									queryMsgEntry.getPhone(),
									fillOp.phoneAreas1.get(info[0]), info[1],
									"json", tp.getFlowgroups());
							if (null == result) {
								//result = "{\"desc\":\"无可订购产品\",\"status\":\"-1\"}";
								queryres.setCode("201");
								queryres.setMsg("无可订购产品");
							} else {
								JSONObject jsontemp = JSONObject
										.fromObject(result.toString());
								System.out.println("desc="
										+ jsontemp.getString("desc"));							
								queryres.setCode("200");
								queryres.setMsg("success");
								queryres.setOperator(getOperator(info[1]));// 运营商
								queryres.setArea(ZDHuaweiUtil.priviceCodeName
										.get(fillOp.phoneAreas1.get(info[0])));// 手机号归属地
								System.out.println("list="
										+ jsontemp.getJSONObject("data")
												.getString("list"));
								JSONArray js = jsontemp.getJSONObject("data")
										.getJSONArray("list");
								System.out.println("size=" + js.size());
								ArrayList<QueryMsgBackEntityZD> order = new ArrayList<QueryMsgBackEntityZD>();
								for (int i = 0; i < js.size(); i++) {
									JSONObject b = js.getJSONObject(i);
									QueryMsgBackEntityZD querymsg = new QueryMsgBackEntityZD();
									querymsg.setCardid(b.getString(
											"product_name").substring(
											0,
											b.getString("product_name")
													.length() - 2));// 面额,去掉后面的单位mb
									querymsg.setCardname(b
											.getString("product_name"));// 产品名称
									querymsg.setIntroduce(b
											.getString("product_name"));// 产品介绍
									querymsg.setPrice(b
											.getString("product_price"));// 充值所需支付的实际价格
									querymsg.setProductid(b
											.getString("product_id"));// 第三方产品id，没有可为空
									querymsg.setType("2");// 为1是充话费，为2是充流量
									order.add(querymsg);
								}
								queryres.setData(order);
								result = JSON.toJSONString(queryres);
							}
						} else {
							result = "{\"desc\":\"获取号码归属地异常\",\"status\":\"待定\"}";							
						}
					}
				}
			}
		}
		// ////////////////////////////返回
		response.setCharacterEncoding("UTF-8");
		PrintWriter wr = response.getWriter();
		wr.write(result.toString());
		wr.flush();
		wr.close();
		return null;
	}

	/**
	 * 充值流量费接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward orderflow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String str2 = "{\"zdws_user_login\":\"0000000265\",\"zdws_method\":\"winksi.topUpPhone.orderOnline\",\"timestamp\":\"2015-11-09 13:52:29\",\"platform\":\"zdws\",\"token\":\"20151112\",\"version\":\"1.0\",\"format\":\"json\",\"zdws_params\": {\"callback_url\":\"testcallbackurl\",\"phone\":\"18682033916\",\"area\":\"广东\",\"operator\":\"联通\",\"card_type\":\"2\",\"cardid\":\"100\",\"price\":\"15.00\",\"orderid\":\"1458876789012\",\"productid\":\"51_001000\" },\"zdws_sign\":\"37A2F8DDD741E49BF67F0A195C951046\"}";
		System.out.println("yes,it is!");
		Object result = null;
		String param = request.getParameter("param");
		System.out.println("param=" + param);
		ZDSystemParam zdsystemparam = new ZDSystemParam();
		JSONObject json = JSONObject.fromObject(str2);
		JSONObject params = JSONObject.fromObject(json.get("zdws_params"));
		OrderResponse orderResponse = new OrderResponse();
		if (json.get("zdws_user_login").toString() == null
				|| json.getString("timestamp") == null
				|| json.getString("platform") == null
				|| json.getString("token") == null
				|| json.getString("version") == null
				|| json.getString("format") == null
				|| params.getString("phone") == null
				|| params.getString("area") == null
				|| params.getString("operator") == null
				|| params.getString("card_type") == null
				|| params.getString("cardid") == null
				|| params.getString("price") == null
				|| params.getString("orderid") == null
				|| json.getString("zdws_sign") == null) {
			orderResponse.setCode("201");
		    orderResponse.setMsg("参数错误");
		} else {
			String temp = MD5Util.zdwz_key + json.getString("zdws_user_login")
					+ json.getString("timestamp") + json.getString("platform")
					+ json.getString("token") + json.getString("version")
					+ json.getString("format") + params.getString("phone")
					+ params.getString("area") + params.getString("operator")
					+ params.getString("card_type")
					+ params.getString("cardid") + params.getString("price")
					+ params.getString("orderid");
			if (!MD5Util.MD5Encode(temp, "UTF-8").equalsIgnoreCase(
					json.getString("zdws_sign"))) {
				System.out.println(MD5Util.MD5Encode(temp, "UTF-8")
						.toUpperCase());
				orderResponse.setCode("201");
			    orderResponse.setMsg("签名校检失败");
			} else {
				zdsystemparam.setZdws_user_login(json
						.getString("zdws_user_login"));
				zdsystemparam.setTimestamp(json.getString("timestamp"));
				zdsystemparam.setZdws_sign(json.getString("zdws_sign"));
				zdsystemparam.setZdws_method(json.getString("zdws_method"));
				OrderEntityZD orderEntityZD = new OrderEntityZD();
				orderEntityZD.setArea(params.getString("area"));
				orderEntityZD.setCallback_url(params.getString("callback_url"));
				orderEntityZD.setCard_type(params.getString("card_type"));
				orderEntityZD.setCardid(params.getString("cardid"));
				orderEntityZD.setOperator(params.getString("operator"));
				orderEntityZD.setOrderid(params.getString("orderid"));
				orderEntityZD.setPhone(params.getString("phone"));
				orderEntityZD.setPrice(params.getString("price"));
				orderEntityZD.setProductid(params.getString("productid"));
				TPForm tp = MemcacheConfig.getInstance().getTP(
						json.getString("zdws_user_login"));
				if (tp == null) {
					orderResponse.setCode("201");
				    orderResponse.setMsg("接口商不存在");
				} else {
					// /String ip = Tools.getIpAddr(request);
					// if (tp.getIp().indexOf(ip) == -1) {
					// result = "{\"desc\":\"IP不合法\",\"status\":\"19\"}";
					//orderResponse.setCode("201");
				    //orderResponse.setMsg("IP不合法");
					// } else
					{
						// result=Flows_Order_Inter(phoneType, areaId, orderid,
						// productId, phone, realPrice, name, tp);
						result = new Flow3g().Flows_Order_Inter(
								getOperatorCode(params.getString("operator")),
								ZDHuaweiUtil.priviceName.get(params.getString("area")),
								orderEntityZD.getOrderid(),
								orderEntityZD.getProductid(),
								orderEntityZD.getPhone(),
								orderEntityZD.getPrice(),
								orderEntityZD.getCardid(), tp);
						// result="{\"code\":\"200\",\"msg\":\"成功\",\"orderid\"：\"1231321\"}";
						JSONObject j = JSONObject.fromObject(result);									
					    orderResponse.setCode("200");// 待转化0成功，1订购中，其他的为失败
					    orderResponse.setMsg("success");
						//处理回调					
					    if(j.getString("code").equals("0")){
					    	
					    }else if(!j.getString("code").equals("1")){//除了订购中，其余全返回失败
					    	
					    }
						orderResponse.setOrderid(orderEntityZD.getOrderid());//订单号						
					}
				}				
			}
		}
		// ////////////////返回
		// System.out.println("下单接口应答: " + JSON.toJSONString(res));
		System.out.println("充值流量费接口");
		response.setCharacterEncoding("UTF-8");
		PrintWriter wr = response.getWriter();
		wr.write(JSONObject.fromObject(orderResponse).toString());
		wr.flush();
		wr.close();
		return null;
	}

	/**
	 * 查询订单接口
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String str3 = "{\"zdws_user_login\":\"0000000265\",\"zdws_method\":\"winksi.topUpPhone.queryOrder\",\"timestamp\":\"2015-11-09 13:52:29\",\"platform\":\"zdws\",\"token\":\"20151112\",\"version\":\"1.0\",\"format\":\"json\",\"zdws_params\": {\"phone\":\"18682033916\",\"orderid\":\"1458876789012\",\"card_type\":\"2\",\"starttime\":\"2015-11-12\",\"endtime\":\"2016-11-13\",\"status\":\"1023\"},\"zdws_sign\":\"AB04CC9EFF0BE08C6CBA46141FCA8DC7\"}";
		System.out.println("yes,it is!");
		Object result = null;
		ZDSystemParam zdsystemparam = new ZDSystemParam();
		JSONObject json = JSONObject.fromObject(str3);
		JSONObject params = JSONObject.fromObject(json.get("zdws_params"));
		QueryResponse queryResponse = new QueryResponse();
		
		// md5(zdws_key+zdws_user_login+timestamp+platform+token+version+format+phone+card_type+orderid+starttime+endtime)大写
		if (json.getString("zdws_user_login") == null
				|| json.getString("timestamp") == null
				|| json.getString("platform") == null
				|| json.getString("token") == null
				|| json.getString("version") == null
				|| json.getString("format") == null
				|| params.getString("phone") == null
				|| params.getString("card_type") == null
				|| params.getString("orderid") == null
				|| params.getString("starttime") == null
				|| params.getString("endtime") == null
				|| json.getString("zdws_sign") == null) {
			//result = "{\"desc\":\"参数错误\",\"status\":\"6\"}";
			queryResponse.setCode("201");
			queryResponse.setMsg("参数错误");
		} else {
			String temp = MD5Util.zdwz_key + json.getString("zdws_user_login")
					+ json.getString("timestamp") + json.getString("platform")
					+ json.getString("token") + json.getString("version")
					+ json.getString("format") + params.getString("phone")
					+ params.getString("card_type")
					+ params.getString("orderid")
					+ params.getString("starttime")
					+ params.getString("endtime");
			if (!MD5Util.MD5Encode(temp, "UTF-8").equalsIgnoreCase(
					json.getString("zdws_sign"))) {
				System.out.println(MD5Util.MD5Encode(temp, "UTF-8")
						.toUpperCase());
				//result = "{\"desc\":\"签名校检失败\",\"status\":\"7\"}";
				queryResponse.setCode("201");
				queryResponse.setMsg("签名校检失败");
			} else {
				zdsystemparam.setZdws_user_login(json
						.getString("zdws_user_login"));
				zdsystemparam.setTimestamp(json.getString("timestamp"));
				zdsystemparam.setZdws_sign(json.getString("zdws_sign"));
				zdsystemparam.setZdws_method(json.getString("zdws_method"));
				QueryEntityZD queryEntityZD = new QueryEntityZD();
				queryEntityZD.setCard_type(params.getString("card_type"));
				queryEntityZD.setEndtime(params.getString("endtime"));
				queryEntityZD.setOrderid(params.getString("orderid"));
				queryEntityZD.setPhone(params.getString("phone"));
				queryEntityZD.setStarttime(params.getString("starttime"));
				queryEntityZD.setStatus(params.getString("status"));
				System.out.println("查询订单接口");
				TPForm tp = MemcacheConfig.getInstance().getTP(
						json.getString("zdws_user_login"));
				if (tp == null) {
					//result = "{\"desc\":\"接口商不存在\",\"status\":\"18\"}";
					queryResponse.setCode("201");
					queryResponse.setMsg("接口商不存在");
				} else {
					String ip = Tools.getIpAddr(request);
					// if (tp.getIp().indexOf(ip) == -1) {
					// result = "{\"desc\":\"IP不合法\",\"status\":\"19\"}";
					//queryResponse.setCode("201");
					//queryResponse.setMsg("IP不合法");
					// } else
					{
						result = fillOp.queryResultZDJSON(params
								.getString("orderid"));
						System.out.println("查询结果:" + result);
						if (null == result) {
							//result = "{\"desc\":\"接口调用异常\",\"status\":\"8\"}";// wht_orderform_1603
							queryResponse.setCode("201");
							queryResponse.setMsg("接口调用异常");
						} else {
							// "{\"desc\":\"100mb流量充值\",\"status\":\"0\",\"writeoff\":\"100mb\",\"tradetime\":\"20160303161249\",\"chgtime\":\"20160303161249\",\"tradefree\":\"14716\"}"
							JSONObject j = JSONObject.fromObject(result);							
							queryResponse.setCode(getQuerySetatus(j
									.getString("status")));
							queryResponse.setMsg(j.getString("desc"));
							ArrayList<QueryResponeEntityZD> list = new ArrayList<QueryResponeEntityZD>();
							QueryResponeEntityZD queryResponeEntity = new QueryResponeEntityZD();
							BiProd biprod = new BiProd();
							String[] info = biprod.getPhoneInfo(queryEntityZD
									.getPhone().substring(0, 7));// 0 是电信 1移动
																	// 2联通
							queryResponeEntity.setArea(ZDHuaweiUtil.priviceCodeName
									.get(fillOp.phoneAreas1.get(info[0])));
							// queryResponeEntity.setCallBackStatus();
							// queryResponeEntity.setCallBackTime();
							queryResponeEntity.setCard_type("2");
							queryResponeEntity.setCardid(j
									.getString("writeoff"));
							queryResponeEntity.setFinishTime(getFormateTime(j
									.getString("chgtime")));
							queryResponeEntity
									.setOperator(getOperator(info[1]));
							queryResponeEntity.setOrderid(queryEntityZD
									.getOrderid());
							queryResponeEntity.setPhone(queryEntityZD
									.getPhone());
							queryResponeEntity.setPrice(String
									.format("%.2f", Float.parseFloat(j
											.getString("tradefree")) / 1000));
							queryResponeEntity.setStatus(getChgStatus(j
									.getString("status")));// 1020充值中,1021成功,1022失败
							queryResponeEntity.setTime(getFormateTime(j
									.getString("tradetime")));
							list.add(queryResponeEntity);
							queryResponse.setOrder(list);
							result = JSONObject.fromObject(queryResponse);
						}
					}
				}
			}
		}
		// //////////////////返回
		System.out.println("查询应答接口:" + JSON.toJSONString(result));
		response.setCharacterEncoding("UTF-8");
		PrintWriter wr = response.getWriter();
		wr.write(result.toString());
		wr.flush();
		wr.close();
		return null;
	}

	/**
	 * 处理回调
	 */
	public void excuteCallback() {
		String url = "${中大地址}/cpCallBack";
		String callbackStr;
		CallbackRequest callback = new CallbackRequest();
		callback.setZdws_method("cpCallBack");
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		callback.setTimestamp("2015-11-09 13:52:29");
		CallbackEntityZD callbackzd = new CallbackEntityZD();
		callbackzd.setCard_type("2");
		callbackzd.setOrderid("12345678");
		callbackzd.setPhone("18682033916");
		callbackzd.setPrice("29.00");
		callbackzd.setStatus("200");
		callback.setZdws_params(callbackzd);
		callback.setZdws_sign(null);
		System.out.println("回调请求接口:" + JSON.toJSONString(callback));
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse res = null;
		HttpPost post = new HttpPost(url);
		try {
			StringEntity s = new StringEntity(JSON.toJSONString(callback));
			post.setEntity(s);
			res = client.execute(post);
			HttpEntity entity = res.getEntity();
			int statusCode = res.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK)
				if (entity != null) {
					InputStream instreams = entity.getContent();
					callbackStr = convertStreamToString(instreams);
					callbackStr = new String(callbackStr.getBytes(), "utf-8");
					//{"code":"200","msg":"成功"}
					// JSONObject obj=JSONObject.fromObject(str);
				}
		} catch (Exception e) {
			return;
		} finally {
			try {
				res.close();
				post.abort();
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
				Log.error("关闭回调连接异常" + e.toString());
			}
		}
		////////////////////////返回
		String str = "{\"zdws_user_login\":\"0000000265\",\"zdws_method\":\"cpCallBack\",\"timestamp\":\"2015-11-09 13:52:29\",\"platform\":\"zdws\",\"token\":\"20151112\",\"version\":\"1.0\",\"format\":\"json\",\"zdws_params\": {\"phone\":\"13800138000\",\"card_type\":\"1\",\"status\":\"200\",\"price\":\"29.00\",\"orderid\":\"123456789012\"},\"zdws_sign\":\"58F38CEC5CFB27F136983CD4FBE34BC8\"}";
		ZDSystemParam zdsystemparam = new ZDSystemParam();
		JSONObject json = JSONObject.fromObject(str);
		zdsystemparam
				.setZdws_user_login(json.get("zdws_user_login").toString());
		zdsystemparam.setTimestamp(json.get("timestamp").toString());
		zdsystemparam.setZdws_sign(json.get("zdws_sign").toString());
		zdsystemparam.setZdws_method(json.get("zdws_method").toString());
		System.out.println("user_login=" + zdsystemparam.getZdws_user_login());
		System.out.println("timestamp=" + zdsystemparam.getTimestamp());
		System.out.println("zdws_sign=" + zdsystemparam.getZdws_sign());
		System.out.println("zdws_method=" + zdsystemparam.getZdws_method());
		if (zdsystemparam.getZdws_method().equals("cpCallBack")) {
			JSONObject params = JSONObject.fromObject(json.get("zdws_params"));
			CallbackEntityZD callbackEntityZD = new CallbackEntityZD();
			callbackEntityZD.setCard_type(params.getString("card_type"));
			callbackEntityZD.setOrderid(params.getString("orderid"));
			callbackEntityZD.setPhone(params.getString("phone"));
			callbackEntityZD.setPrice(params.getString("price"));
			callbackEntityZD.setStatus(params.getString("status"));
			System.out.println("card_type=" + callbackEntityZD.getCard_type());
			System.out.println("orderid=" + callbackEntityZD.getOrderid());
			System.out.println("phone=" + callbackEntityZD.getPhone());
			System.out.println("price=" + callbackEntityZD.getPrice());
			System.out.println("status=" + callbackEntityZD.getStatus());
		}
	}

	/**
	 * 7维护推送接口
	 */
	public void test() {

	}

	/**
	 * 从inputstream中获取字符串
	 * 
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 根根运营商类型代码获取对应名称
	 */
	private static String getOperator(String operatorCode) {
		if (operatorCode != null) {// 0 是电信 1移动 2联通
			if (operatorCode.equals("0"))
				return "电信";
			else if (operatorCode.equals("1"))
				return "移动";
			else if (operatorCode.equals("2"))
				return "联通";
			else
				return null;
		} else {
			return null;
		}
	}

	/**
	 * 根根运营商类型代码获取对应名称
	 */
	private static String getOperatorCode(String operator) {
		if (operator != null) {// 0 是电信 1移动 2联通
			if (operator.equals("电信"))
				return "0";
			else if (operator.equals("移动"))
				return "1";
			else if (operator.equals("联通"))
				return "2";
			else
				return null;
		} else {
			return null;
		}
	}

	/**
	 * 处理时间，20160303161249
	 * 
	 * @return
	 */
	private String getFormateTime(String time) {
		String str = null;
		try {
			str = time.substring(0, 4) + "-" + time.substring(4, 6) + "-"
					+ time.substring(6, 8) + " " + time.substring(8, 10) + ":"
					+ time.substring(10, 12) + ":" + time.substring(12, 14);
		} catch (Exception e) {
			System.err.println("转换日期格式异常");
		}
		return str;
	}

	/**
	 * 转换操作代码结果 操作结果(200成功，201失败, 202无此订单号, 203无充值记录)
	 * 对应0/2/other
	 * @param status
	 * @return
	 */
	private String getQuerySetatus(String status) {
		if (status.equals("0")) {// 作操成功
			return "200";
		} else if (status.equals("2")) {// 无此订单
			return "202";
		} else {// 失败
			return "201";
		}
	}

	/**
	 * 充值状态查询转换 1020充值中,1021成功,1022失败 分别对应0/1/other
	 * 
	 * @param status
	 * @return
	 */
	private String getChgStatus(String status) {
		if (status.equals("0")) {// 成功
			return "1021";
		} else if (status.equals("1")) {// 失败
			return "1022";
		} else {// 充值中
			return "1020";
		}

	}

}
