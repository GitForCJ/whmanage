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
 * �д󶩵���ѯ
 * @author fw
 *
 */
public class ZDHuaweiQueryOrder extends DispatchAction{
 static HttpFillOP fillOp = new HttpFillOP();
 static	Logger log=Logger.getLogger(ZDHuaweiQueryOrder.class);
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
			 log.error("��ȡ���������쳣");
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
				queryResponse.setMsg("��������");
			}
		ZDSystemParam zdsystemparam = new ZDSystemParam();				
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
				TPForm tp = MemcacheConfig.getInstance().getTP(
						json.getString("zdws_user_login"));
				if (tp == null) {
					//result = "{\"desc\":\"�ӿ��̲�����\",\"status\":\"18\"}";
					queryResponse.setCode("201");
					queryResponse.setMsg("�ӿ��̲�����");
				} else {				
					log.info("IP="+ip);
					 if (tp.getIp().indexOf(ip) == -1) {
					// result = "{\"desc\":\"IP���Ϸ�\",\"status\":\"19\"}";
					queryResponse.setCode("201");
					queryResponse.setMsg("IP���Ϸ�");
					 } else{
					try{
						result = fillOp.queryResultZDJSON(params.getString("orderid"));
						log.info("��ѯ���:" + result);
						if (null == result) {
							//result = "{\"desc\":\"�ӿڵ����쳣\",\"status\":\"8\"}";// wht_orderform_1603
							queryResponse.setCode("201");
							queryResponse.setMsg("�ӿڵ����쳣");
						} else {
							// "{\"desc\":\"100mb������ֵ\",\"status\":\"0\",\"writeoff\":\"100mb\",\"tradetime\":\"20160303161249\",\"chgtime\":\"20160303161249\",\"tradefree\":\"14716\"}"
							JSONObject j = JSONObject.fromObject(result);							
							queryResponse.setCode(ZDHuaweiUtil.getQuerySetatus(j.getString("status")));
							queryResponse.setMsg(j.getString("desc"));
							ArrayList<QueryResponeEntityZD> list = new ArrayList<QueryResponeEntityZD>();
							QueryResponeEntityZD queryResponeEntity = new QueryResponeEntityZD();
							BiProd biprod = new BiProd();
							String[] info = biprod.getPhoneInfo(queryEntityZD.getPhone().substring(0, 7));// 0 �ǵ��� 1�ƶ�// 2��ͨ
							queryResponeEntity.setArea(ZDHuaweiUtil.priviceCodeName.get(fillOp.phoneAreas1.get(info[0])));
							queryResponeEntity.setCallBackStatus((j.getString("status").equals("0")?"200":"201"));//֪ͨ�ص��ɹ�ʧ�ܣ�200��201��//
							queryResponeEntity.setCallBackTime(ZDHuaweiUtil.getFormateTime(j.getString("chgtime")));
							queryResponeEntity.setCard_type("2");
							queryResponeEntity.setCardid(j.getString("writeoff"));
							queryResponeEntity.setFinishTime(ZDHuaweiUtil.getFormateTime(j.getString("chgtime")));
							queryResponeEntity.setOperator(ZDHuaweiUtil.getOperator(info[1]));
							queryResponeEntity.setOrderid(queryEntityZD.getOrderid());
							queryResponeEntity.setPhone(queryEntityZD.getPhone());
							queryResponeEntity.setPrice(String.format("%.2f", Float.parseFloat(j.getString("tradefree")) / 1000));
							queryResponeEntity.setStatus(ZDHuaweiUtil.getChgStatus(j.getString("status")));// 1020��ֵ��,1021�ɹ�,1022ʧ��
							queryResponeEntity.setTime(ZDHuaweiUtil.getFormateTime(j.getString("tradetime")));
							list.add(queryResponeEntity);
							queryResponse.setOrder(list);
						}
					
					}catch(Exception  e){
						e.printStackTrace();
						queryResponse.setCode("201");
						queryResponse.setMsg("�ӿ��쳣");
					}
				 }
				}
			}
		}
		// //////////////////����
		System.out.println("��ѯӦ��ӿ�:" + JSON.toJSONString(result));
		response.setCharacterEncoding("UTF-8");
		PrintWriter wr = response.getWriter();
		log.info(JSONObject.fromObject(queryResponse).toString());
		wr.write(JSONObject.fromObject(queryResponse).toString());
		wr.flush();
		wr.close();
		return null;
	}
}
