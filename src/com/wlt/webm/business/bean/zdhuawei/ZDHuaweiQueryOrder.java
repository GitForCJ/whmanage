package com.wlt.webm.business.bean.zdhuawei;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.alibaba.fastjson.JSON;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.business.bean.unicom3gquery.HttpFillOP;
import com.wlt.webm.business.bean.zdhuawei.fact.MD5Util;
import com.wlt.webm.business.bean.zdhuawei.fact.QueryMsgBack;
import com.wlt.webm.business.bean.zdhuawei.fact.QueryResponse;
import com.wlt.webm.business.form.TPForm;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.tool.Tools;
/**
 * 中大订单查询
 * @author fw
 *
 */
public class ZDHuaweiQueryOrder extends DispatchAction{
 static HttpFillOP fillOp = new HttpFillOP();
 static	Logger log=Logger.getLogger(ZDHuaweiQueryOrder.class);
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
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String str3 =null;// "{\"zdws_user_login\":\"0000000265\",\"zdws_method\":\"winksi.topUpPhone.queryOrder\",\"timestamp\":\"2015-11-09 13:52:29\",\"platform\":\"zdws\",\"token\":\"20151112\",\"version\":\"1.0\",\"format\":\"json\",\"zdws_params\": {\"phone\":\"18682033916\",\"orderid\":\"1458876789015\",\"card_type\":\"2\",\"starttime\":\"2015-11-12\",\"endtime\":\"2016-11-13\",\"status\":\"1023\"},\"zdws_sign\":\"AB04CC9EFF0BE08C6CBA46141FCA8DC7\"}";
		Object result = null;
		String ip=null;
		 StringBuffer strBu=new  StringBuffer(); 
		try{
		BufferedReader reader=request.getReader();		 
		  String s=null;
		  while((s=reader.readLine())!=null){
		    strBu.append(s); 
		 } }catch(Exception e){
			 log.error("读取请求数据异常");
		 }
		   str3=strBu.toString();		
		    JSONObject params=null;
			JSONObject json=null;
			QueryResponse queryResponse = new QueryResponse();
			try{
			 json = JSONObject.fromObject(str3);
			 ip=json.getString("ip");
			 json=json.getJSONObject("data");
			 params =json.getJSONObject("zdws_params");
			}catch(Exception e){
				e.printStackTrace();
				queryResponse.setCode("201");
				queryResponse.setMsg("参数错误");
			}
		ZDSystemParam zdsystemparam = new ZDSystemParam();				
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
				TPForm tp = MemcacheConfig.getInstance().getTP(
						json.getString("zdws_user_login"));
				if (tp == null) {
					//result = "{\"desc\":\"接口商不存在\",\"status\":\"18\"}";
					queryResponse.setCode("201");
					queryResponse.setMsg("接口商不存在");
				} else {				
					log.info("IP="+ip);
					 if (tp.getIp().indexOf(ip) == -1) {
					// result = "{\"desc\":\"IP不合法\",\"status\":\"19\"}";
					queryResponse.setCode("201");
					queryResponse.setMsg("IP不合法");
					 } else{
					try{
						result = fillOp.queryResultZDJSON(params.getString("orderid"));
						log.info("查询结果:" + result);
						if (null == result) {
							//result = "{\"desc\":\"接口调用异常\",\"status\":\"8\"}";// wht_orderform_1603
							queryResponse.setCode("201");
							queryResponse.setMsg("接口调用异常");
						} else {
							// "{\"desc\":\"100mb流量充值\",\"status\":\"0\",\"writeoff\":\"100mb\",\"tradetime\":\"20160303161249\",\"chgtime\":\"20160303161249\",\"tradefree\":\"14716\"}"
							JSONObject j = JSONObject.fromObject(result);							
							queryResponse.setCode(ZDHuaweiUtil.getQuerySetatus(j.getString("status")));
							queryResponse.setMsg(j.getString("desc"));
							ArrayList<QueryResponeEntityZD> list = new ArrayList<QueryResponeEntityZD>();
							QueryResponeEntityZD queryResponeEntity = new QueryResponeEntityZD();
							BiProd biprod = new BiProd();
							String[] info = biprod.getPhoneInfo(queryEntityZD.getPhone().substring(0, 7));// 0 是电信 1移动// 2联通
							queryResponeEntity.setArea(ZDHuaweiUtil.priviceCodeName.get(fillOp.phoneAreas1.get(info[0])));
							queryResponeEntity.setCallBackStatus((j.getString("status").equals("0")?"200":"201"));//通知回调成功失败（200，201）//
							queryResponeEntity.setCallBackTime(ZDHuaweiUtil.getFormateTime(j.getString("chgtime")));
							queryResponeEntity.setCard_type("2");
							queryResponeEntity.setCardid(j.getString("writeoff"));
							queryResponeEntity.setFinishTime(ZDHuaweiUtil.getFormateTime(j.getString("chgtime")));
							queryResponeEntity.setOperator(ZDHuaweiUtil.getOperator(info[1]));
							queryResponeEntity.setOrderid(queryEntityZD.getOrderid());
							queryResponeEntity.setPhone(queryEntityZD.getPhone());
							queryResponeEntity.setPrice(String.format("%.2f", Float.parseFloat(j.getString("tradefree")) / 1000));
							queryResponeEntity.setStatus(ZDHuaweiUtil.getChgStatus(j.getString("status")));// 1020充值中,1021成功,1022失败
							queryResponeEntity.setTime(ZDHuaweiUtil.getFormateTime(j.getString("tradetime")));
							list.add(queryResponeEntity);
							queryResponse.setOrder(list);
						}
					
					}catch(Exception  e){
						e.printStackTrace();
						queryResponse.setCode("201");
						queryResponse.setMsg("接口异常");
					}
				 }
				}
			}
		}
		// //////////////////返回
		System.out.println("查询应答接口:" + JSON.toJSONString(result));
		response.setCharacterEncoding("UTF-8");
		PrintWriter wr = response.getWriter();
		log.info(JSONObject.fromObject(queryResponse).toString());
		wr.write(JSONObject.fromObject(queryResponse).toString());
		wr.flush();
		wr.close();
		return null;
	}
}
