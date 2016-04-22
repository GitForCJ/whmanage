package com.wlt.webm.business.bean.zdhuawei;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.action.Flow3g;
import com.wlt.webm.business.bean.zdhuawei.fact.CallbackRequest;
import com.wlt.webm.business.bean.zdhuawei.fact.MD5Util;
import com.wlt.webm.business.bean.zdhuawei.fact.OrderResponse;
import com.wlt.webm.business.form.TPForm;
import com.wlt.webm.message.MemcacheConfig;

public class ZDHuaweiOrderflow extends DispatchAction{
	
	static Logger log=Logger.getLogger(ZDHuaweiOrderflow.class);
	/**
	 * ��ֵ�����ѽӿ�
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
		request.setCharacterEncoding("utf-8");
		String str2 =null;// "{\"zdws_user_login\":\"0000000265\",\"zdws_method\":\"winksi.topUpPhone.orderOnline\",\"timestamp\":\"2015-11-09 13:52:29\",\"platform\":\"zdws\",\"token\":\"20151112\",\"version\":\"1.0\",\"format\":\"json\",\"zdws_params\": {\"callback_url\":\"testcallbackurl\",\"phone\":\"18682033916\",\"area\":\"�㶫\",\"operator\":\"��ͨ\",\"card_type\":\"2\",\"cardid\":\"100\",\"price\":\"15.00\",\"orderid\":\"1458876789015\",\"productid\":\"51_001000\" },\"zdws_sign\":\"37A2F8DDD741E49BF67F0A195C951046\"}";
		Object result = null; 
		String ip=null;
		StringBuffer strBu=new  StringBuffer(); 
		try{
			BufferedReader reader=new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
			String s=null;
			  while((s=reader.readLine())!=null){
			    strBu.append(s); 
			 }
		  }catch(Exception e){
			 log.error("��ȡ����json����");
		 }	
		  str2=strBu.toString();		
		  log.info("����json����="+str2);
		 //System.out.println("2����:"+str2);
		 JSONObject params=null;
		 JSONObject json=null;
		 OrderResponse orderResponse = new OrderResponse();
		try{
			 json = JSONObject.fromObject(str2);
			 ip=json.getString("ip");
			 json=json.getJSONObject("data");
			 params = json.getJSONObject("zdws_params");			
		     ZDSystemParam zdsystemparam = new ZDSystemParam();
		
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
				System.out.println("sign:"+MD5Util.MD5Encode(temp, "UTF-8")
						.toUpperCase());
				orderResponse.setCode("201");
			    orderResponse.setMsg("ǩ��У��ʧ��");
			} else 
			{
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
				orderEntityZD.setOrderid("ZW"+params.getString("orderid"));
				orderEntityZD.setPhone(params.getString("phone"));
				orderEntityZD.setPrice(params.getString("price"));
				orderEntityZD.setProductid(params.getString("productid"));
				try{
				TPForm tp = MemcacheConfig.getInstance().getTP(
						json.getString("zdws_user_login"));
				if (tp == null) {
					orderResponse.setCode("201");
				    orderResponse.setMsg("�ӿ��̲�����");
				} else {
					log.info("IP="+ip);
					 if (tp.getIp().indexOf(ip) == -1) {
					// result = "{\"desc\":\"IP���Ϸ�\",\"status\":\"19\"}";
					orderResponse.setCode("201");
				    orderResponse.setMsg("IP���Ϸ�");
					 } else					
					 {	// result=Flows_Order_Inter(phoneType, areaId, orderid,
						// productId, phone, realPrice, name, tp);
						result = new Flow3g().Flows_Order_Inter(
								ZDHuaweiUtil.getOperatorCode(params.getString("operator")),
								ZDHuaweiUtil.priviceName.get(params.getString("area")),
								orderEntityZD.getOrderid(),
								orderEntityZD.getProductid(),
								orderEntityZD.getPhone(),
								orderEntityZD.getPrice(),
								orderEntityZD.getCardid(), tp);
						// result="{\"code\":\"200\",\"msg\":\"�ɹ�\",\"orderid\"��\"1231321\"}";
						log.info("result="+result);
						JSONObject j = JSONObject.fromObject(result);									
					    //��ת��0�ɹ���1�����У�������Ϊʧ��					   
						//����ص�					
					    if(j.getString("status").equals("0")){
					    	 orderResponse.setCode("200");// ��Ϊ�����ɹ�
							 orderResponse.setMsg("success");
							 ////////////////////////////////�ص��ɹ���ַ
							 System.out.println("�ص������ɹ��ӿ�");
							    CallbackRequest callback = new CallbackRequest();
								callback.setZdws_method("cpCallBack");
								SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								callback.setTimestamp(dateformat.format(new Date()));
								CallbackEntityZD callbackzd = new CallbackEntityZD();
								callbackzd.setCard_type("2");
								callbackzd.setOrderid(orderEntityZD.getOrderid().substring(2));
								callbackzd.setPhone(orderEntityZD.getPhone());
								callbackzd.setPrice(orderEntityZD.getPrice());
								callbackzd.setStatus("1021");//1021�ɹ���1022ʧ�ܣ�200�ɹ���201ʧ��
								callback.setZdws_params(callbackzd);
								callback.setZdws_sign(null);//����ǩ��
								System.out.println("�ص�����ӿ�:" + JSON.toJSONString(callback));
								//String response1=excuteCallback(callback,orderEntityZD.getCallback_url());
								//System.out.println("response1="+response1);
					    }else if(!j.getString("status").equals("1")){//���˶����У�����ȫ����ʧ��result={"desc":"����ʧ��","status":"2"}
					    	//{"desc":"��Ʒ��Ӧ�ӿڲ�����","status":"20"}
					    	 orderResponse.setCode("201");// ��Ϊ�����ɹ�
							 orderResponse.setMsg(j.getString("desc"));
							 //////////////////////�ص�ʧ�ܵ�ַ
							 System.out.println("�ص�����ʧ�ܽӿ�");
							 CallbackRequest callback = new CallbackRequest();
								callback.setZdws_method("cpCallBack");
								SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								callback.setTimestamp(dateformat.format(new Date()));
								CallbackEntityZD callbackzd = new CallbackEntityZD();
								callbackzd.setCard_type("2");
								callbackzd.setOrderid(orderEntityZD.getOrderid().substring(2));
								callbackzd.setPhone(orderEntityZD.getPhone());
								callbackzd.setPrice(orderEntityZD.getPrice());
								callbackzd.setStatus("1021");
								callback.setZdws_params(callbackzd);
								callback.setZdws_sign(null);
								System.out.println("�ص�����ӿ�:" + JSON.toJSONString(callback));
								//String response1=excuteCallback(callback,orderEntityZD.getCallback_url());
								//System.out.println("response1="+response1);
					    }else if(j.getString("status").equals("1")){
					    	 orderResponse.setCode("200");// ��Ϊ�����ɹ�,�����в�����
							 orderResponse.setMsg("success");
							 System.out.println("�����ж��������ص����� ");
					    }else {
					    	orderResponse.setCode("201");// ��Ϊ�����ɹ�
							 orderResponse.setMsg("δ֪ϵͳ�쳣");
							 System.out.println("δ֪ϵͳ�쳣");
					    }
					   // System.out.println("�����ţ�"+orderEntityZD.getOrderid());
						orderResponse.setOrderid(orderEntityZD.getOrderid().substring(2));//������						
					}
				}
				}catch(Exception e){
					e.printStackTrace();
				    orderResponse.setCode("201");// ��ת��0�ɹ���1�����У�������Ϊʧ��
					orderResponse.setMsg("�ӿ��쳣");
					orderResponse.setOrderid(orderEntityZD.getOrderid().substring(2));//������
				}				
			}
		}
		}catch(Exception e){
			e.printStackTrace();
			orderResponse.setCode("201");
			orderResponse.setMsg("��������");
		}
		// ////////////////����
		// System.out.println("�µ��ӿ�Ӧ��: " + JSON.toJSONString(res));
		response.setCharacterEncoding("UTF-8");
		PrintWriter wr = response.getWriter();
		log.info(JSONObject.fromObject(orderResponse).toString());
		wr.write(JSONObject.fromObject(orderResponse).toString());
		wr.flush();
		wr.close();
		return null;
	}
	/**
	 * ����ص�
	 */
	public String excuteCallback(CallbackRequest callbackrequest,String url) {
		//String url = "${�д��ַ}/cpCallBack";
		String callbackStr=null;		
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse res = null;
		HttpPost post = new HttpPost(url);
		try {
			StringEntity s = new StringEntity(JSON.toJSONString(callbackrequest));
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
			e.printStackTrace();			
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
	
		return callbackStr;
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
}
