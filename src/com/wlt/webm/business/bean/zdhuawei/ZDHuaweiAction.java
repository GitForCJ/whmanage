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
	 * ��ѯ�ֻ��Ź����ؼ��ֻ�����֧�ֵĲ�Ʒ�б�
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
		 * strBu.append(s); } System.out.println("����:"+strBu.toString());
		 */
		ZDSystemParam zdsystemparam = new ZDSystemParam();
		JSONObject json = JSONObject.fromObject(str1);
		JSONObject params = JSONObject.fromObject(json.get("zdws_params"));
		QueryMsgBack queryres = new QueryMsgBack();
		// md5(zdws_key+zdws_user_login+timestamp+platform+token+version+format+phone)��д
		if (json.getString("zdws_user_login") == null
				|| json.getString("timestamp") == null
				|| json.getString("platform") == null
				|| json.getString("token") == null
				|| json.getString("version") == null
				|| json.getString("format") == null
				|| params.getString("phone") == null
				|| json.getString("zdws_sign") == null) {
			//result = "{\"desc\":\"��������\",\"status\":\"6\"}";
			queryres.setCode("201");
			queryres.setMsg("��������");
		} else {
			String temp = MD5Util.zdwz_key + json.getString("zdws_user_login")
					+ json.getString("timestamp") + json.getString("platform")
					+ json.getString("token") + json.getString("version")
					+ json.getString("format") + params.getString("phone");
			if (!MD5Util.MD5Encode(temp, "UTF-8").equalsIgnoreCase(
					json.getString("zdws_sign"))) {
				System.out.println(MD5Util.MD5Encode(temp, "UTF-8")
						.toUpperCase());
				//result = "{\"desc\":\"ǩ��У��ʧ��\",\"status\":\"7\"}";
				queryres.setCode("201");
				queryres.setMsg("ǩ��У��ʧ��");
			} else {
				zdsystemparam.setZdws_user_login(json
						.getString("zdws_user_login"));
				zdsystemparam.setTimestamp(json.getString("timestamp"));
				zdsystemparam.setZdws_sign(json.getString("zdws_sign"));
				zdsystemparam.setZdws_method(json.getString("zdws_method"));
				QueryMsgEntryZD queryMsgEntry = new QueryMsgEntryZD();
				queryMsgEntry.setPhone(params.getString("phone"));
				queryMsgEntry.setCard_type(params.getString("card_type"));
				// System.out.println("�ֻ������ؼ���Ʒ��Ϣ��ѯ�ӿ�");
				BiProd biprod = new BiProd();
				System.out.println("here");
				queryMsgEntry.setPhone("18682033916");
				String[] info = biprod.getPhoneInfo(queryMsgEntry.getPhone()
						.substring(0, 7));// 0 �ǵ��� 1�ƶ� 2��ͨ
				if (info != null) {
					System.out.println("������ʡ��=" + info[0] + "   �������ͣ�"
							+ info[1] + "    /"
							+ queryMsgEntry.getPhone().substring(0, 7));
				} else {
					System.out.println("��������ʡ�ݳ���"
							+ queryMsgEntry.getPhone().substring(0, 7));
				}
				System.out.println("here2");
				TPForm tp = MemcacheConfig.getInstance().getTP(
						zdsystemparam.getZdws_user_login());
				System.out.println("sdfsdfsdf");
				if (tp == null) {
					///result = "{\"desc\":\"�ӿ��̲�����\",\"status\":\"18\"}";
					queryres.setCode("201");
					queryres.setMsg("�ӿ��̲�����");
				} else {
					String ip = Tools.getIpAddr(request);
					System.out.println("ip=" + ip);
					// if (tp.getIp().indexOf(ip) == -1) {
					// result = "{\"desc\":\"IP���Ϸ�\",\"status\":\"19\"}";
					//queryres.setCode("201");
					//queryres.setMsg("IP���Ϸ�");
					// } else
					{
						if (info != null) {
							result = fillOp.filterProductJSON(
									queryMsgEntry.getPhone(),
									fillOp.phoneAreas1.get(info[0]), info[1],
									"json", tp.getFlowgroups());
							if (null == result) {
								//result = "{\"desc\":\"�޿ɶ�����Ʒ\",\"status\":\"-1\"}";
								queryres.setCode("201");
								queryres.setMsg("�޿ɶ�����Ʒ");
							} else {
								JSONObject jsontemp = JSONObject
										.fromObject(result.toString());
								System.out.println("desc="
										+ jsontemp.getString("desc"));							
								queryres.setCode("200");
								queryres.setMsg("success");
								queryres.setOperator(getOperator(info[1]));// ��Ӫ��
								queryres.setArea(ZDHuaweiUtil.priviceCodeName
										.get(fillOp.phoneAreas1.get(info[0])));// �ֻ��Ź�����
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
													.length() - 2));// ���,ȥ������ĵ�λmb
									querymsg.setCardname(b
											.getString("product_name"));// ��Ʒ����
									querymsg.setIntroduce(b
											.getString("product_name"));// ��Ʒ����
									querymsg.setPrice(b
											.getString("product_price"));// ��ֵ����֧����ʵ�ʼ۸�
									querymsg.setProductid(b
											.getString("product_id"));// ��������Ʒid��û�п�Ϊ��
									querymsg.setType("2");// Ϊ1�ǳ仰�ѣ�Ϊ2�ǳ�����
									order.add(querymsg);
								}
								queryres.setData(order);
								result = JSON.toJSONString(queryres);
							}
						} else {
							result = "{\"desc\":\"��ȡ����������쳣\",\"status\":\"����\"}";							
						}
					}
				}
			}
		}
		// ////////////////////////////����
		response.setCharacterEncoding("UTF-8");
		PrintWriter wr = response.getWriter();
		wr.write(result.toString());
		wr.flush();
		wr.close();
		return null;
	}

	/**
	 * ��ֵ�����ѽӿ�
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
		String str2 = "{\"zdws_user_login\":\"0000000265\",\"zdws_method\":\"winksi.topUpPhone.orderOnline\",\"timestamp\":\"2015-11-09 13:52:29\",\"platform\":\"zdws\",\"token\":\"20151112\",\"version\":\"1.0\",\"format\":\"json\",\"zdws_params\": {\"callback_url\":\"testcallbackurl\",\"phone\":\"18682033916\",\"area\":\"�㶫\",\"operator\":\"��ͨ\",\"card_type\":\"2\",\"cardid\":\"100\",\"price\":\"15.00\",\"orderid\":\"1458876789012\",\"productid\":\"51_001000\" },\"zdws_sign\":\"37A2F8DDD741E49BF67F0A195C951046\"}";
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
		    orderResponse.setMsg("��������");
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
			    orderResponse.setMsg("ǩ��У��ʧ��");
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
				    orderResponse.setMsg("�ӿ��̲�����");
				} else {
					// /String ip = Tools.getIpAddr(request);
					// if (tp.getIp().indexOf(ip) == -1) {
					// result = "{\"desc\":\"IP���Ϸ�\",\"status\":\"19\"}";
					//orderResponse.setCode("201");
				    //orderResponse.setMsg("IP���Ϸ�");
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
						// result="{\"code\":\"200\",\"msg\":\"�ɹ�\",\"orderid\"��\"1231321\"}";
						JSONObject j = JSONObject.fromObject(result);									
					    orderResponse.setCode("200");// ��ת��0�ɹ���1�����У�������Ϊʧ��
					    orderResponse.setMsg("success");
						//����ص�					
					    if(j.getString("code").equals("0")){
					    	
					    }else if(!j.getString("code").equals("1")){//���˶����У�����ȫ����ʧ��
					    	
					    }
						orderResponse.setOrderid(orderEntityZD.getOrderid());//������						
					}
				}				
			}
		}
		// ////////////////����
		// System.out.println("�µ��ӿ�Ӧ��: " + JSON.toJSONString(res));
		System.out.println("��ֵ�����ѽӿ�");
		response.setCharacterEncoding("UTF-8");
		PrintWriter wr = response.getWriter();
		wr.write(JSONObject.fromObject(orderResponse).toString());
		wr.flush();
		wr.close();
		return null;
	}

	/**
	 * ��ѯ�����ӿ�
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
		
		// md5(zdws_key+zdws_user_login+timestamp+platform+token+version+format+phone+card_type+orderid+starttime+endtime)��д
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
			//result = "{\"desc\":\"��������\",\"status\":\"6\"}";
			queryResponse.setCode("201");
			queryResponse.setMsg("��������");
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
				//result = "{\"desc\":\"ǩ��У��ʧ��\",\"status\":\"7\"}";
				queryResponse.setCode("201");
				queryResponse.setMsg("ǩ��У��ʧ��");
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
				System.out.println("��ѯ�����ӿ�");
				TPForm tp = MemcacheConfig.getInstance().getTP(
						json.getString("zdws_user_login"));
				if (tp == null) {
					//result = "{\"desc\":\"�ӿ��̲�����\",\"status\":\"18\"}";
					queryResponse.setCode("201");
					queryResponse.setMsg("�ӿ��̲�����");
				} else {
					String ip = Tools.getIpAddr(request);
					// if (tp.getIp().indexOf(ip) == -1) {
					// result = "{\"desc\":\"IP���Ϸ�\",\"status\":\"19\"}";
					//queryResponse.setCode("201");
					//queryResponse.setMsg("IP���Ϸ�");
					// } else
					{
						result = fillOp.queryResultZDJSON(params
								.getString("orderid"));
						System.out.println("��ѯ���:" + result);
						if (null == result) {
							//result = "{\"desc\":\"�ӿڵ����쳣\",\"status\":\"8\"}";// wht_orderform_1603
							queryResponse.setCode("201");
							queryResponse.setMsg("�ӿڵ����쳣");
						} else {
							// "{\"desc\":\"100mb������ֵ\",\"status\":\"0\",\"writeoff\":\"100mb\",\"tradetime\":\"20160303161249\",\"chgtime\":\"20160303161249\",\"tradefree\":\"14716\"}"
							JSONObject j = JSONObject.fromObject(result);							
							queryResponse.setCode(getQuerySetatus(j
									.getString("status")));
							queryResponse.setMsg(j.getString("desc"));
							ArrayList<QueryResponeEntityZD> list = new ArrayList<QueryResponeEntityZD>();
							QueryResponeEntityZD queryResponeEntity = new QueryResponeEntityZD();
							BiProd biprod = new BiProd();
							String[] info = biprod.getPhoneInfo(queryEntityZD
									.getPhone().substring(0, 7));// 0 �ǵ��� 1�ƶ�
																	// 2��ͨ
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
									.getString("status")));// 1020��ֵ��,1021�ɹ�,1022ʧ��
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
		// //////////////////����
		System.out.println("��ѯӦ��ӿ�:" + JSON.toJSONString(result));
		response.setCharacterEncoding("UTF-8");
		PrintWriter wr = response.getWriter();
		wr.write(result.toString());
		wr.flush();
		wr.close();
		return null;
	}

	/**
	 * ����ص�
	 */
	public void excuteCallback() {
		String url = "${�д��ַ}/cpCallBack";
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
		System.out.println("�ص�����ӿ�:" + JSON.toJSONString(callback));
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
					//{"code":"200","msg":"�ɹ�"}
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
				Log.error("�رջص������쳣" + e.toString());
			}
		}
		////////////////////////����
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
	 * 7ά�����ͽӿ�
	 */
	public void test() {

	}

	/**
	 * ��inputstream�л�ȡ�ַ���
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
	 * ������Ӫ�����ʹ����ȡ��Ӧ����
	 */
	private static String getOperator(String operatorCode) {
		if (operatorCode != null) {// 0 �ǵ��� 1�ƶ� 2��ͨ
			if (operatorCode.equals("0"))
				return "����";
			else if (operatorCode.equals("1"))
				return "�ƶ�";
			else if (operatorCode.equals("2"))
				return "��ͨ";
			else
				return null;
		} else {
			return null;
		}
	}

	/**
	 * ������Ӫ�����ʹ����ȡ��Ӧ����
	 */
	private static String getOperatorCode(String operator) {
		if (operator != null) {// 0 �ǵ��� 1�ƶ� 2��ͨ
			if (operator.equals("����"))
				return "0";
			else if (operator.equals("�ƶ�"))
				return "1";
			else if (operator.equals("��ͨ"))
				return "2";
			else
				return null;
		} else {
			return null;
		}
	}

	/**
	 * ����ʱ�䣬20160303161249
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
			System.err.println("ת�����ڸ�ʽ�쳣");
		}
		return str;
	}

	/**
	 * ת������������ �������(200�ɹ���201ʧ��, 202�޴˶�����, 203�޳�ֵ��¼)
	 * ��Ӧ0/2/other
	 * @param status
	 * @return
	 */
	private String getQuerySetatus(String status) {
		if (status.equals("0")) {// ���ٳɹ�
			return "200";
		} else if (status.equals("2")) {// �޴˶���
			return "202";
		} else {// ʧ��
			return "201";
		}
	}

	/**
	 * ��ֵ״̬��ѯת�� 1020��ֵ��,1021�ɹ�,1022ʧ�� �ֱ��Ӧ0/1/other
	 * 
	 * @param status
	 * @return
	 */
	private String getChgStatus(String status) {
		if (status.equals("0")) {// �ɹ�
			return "1021";
		} else if (status.equals("1")) {// ʧ��
			return "1022";
		} else {// ��ֵ��
			return "1020";
		}

	}

}
