package com.wlt.webm.business.bean.zdhuawei;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
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
import com.wlt.webm.business.form.TPForm;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.tool.Tools;

public class ZDhuaweiProflow extends DispatchAction {
	
static Logger log=Logger.getLogger(ZDhuaweiProflow.class);
static HttpFillOP fillOp = new HttpFillOP();

public ActionForward execute(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {	
	
	
	System.out.println("�д����----------------------------"); 
	  String str1 = null;
	  Object result = null;	
	  StringBuffer strBu=new  StringBuffer(); 
	 try{
	  BufferedReader reader=request.getReader();	 
	  String s=null;
	  while((s=reader.readLine())!=null){
	      strBu.append(s); 
	   }
	  }catch(Exception e){
		 log.equals("��ȡ�����쳣");
	 }
	str1=strBu.toString();	
	System.out.println("getdata="+str1);
	JSONObject params=null;
	JSONObject json=null;
	String ip=null;
	QueryMsgBack queryres = new QueryMsgBack();
	try{
		json = JSONObject.fromObject(str1);
		ip=json.getString("ip");
		json=json.getJSONObject("data");
		params = json.getJSONObject("zdws_params");
		System.out.println("IP:"+ip);
	}catch(Exception e){
		e.printStackTrace();
		queryres.setCode("201");
		queryres.setMsg("��������");
	}
	ZDSystemParam zdsystemparam = new ZDSystemParam();	
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
		System.out.println("signstr:"+temp);
		System.out.println(MD5Util.MD5Encode(temp, "UTF-8"));
		if (!MD5Util.MD5Encode(temp, "UTF-8").equalsIgnoreCase(
				json.getString("zdws_sign"))) {
			System.out.println(MD5Util.MD5Encode(temp, "UTF-8").toUpperCase());
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
			queryMsgEntry.setPhone(params.getString("phone"));
			String[] info = biprod.getPhoneInfo(queryMsgEntry.getPhone()
					.substring(0, 7));// 0 �ǵ��� 1�ƶ� 2��ͨ
			if (info != null) {
				log.debug("������ʡ��=" + info[0] + "   �������ͣ�"+ info[1] + "    /"+ queryMsgEntry.getPhone().substring(0, 7));
				System.out.println("������ʡ��=" + info[0] + "   �������ͣ�"+ info[1] + "    /"+ queryMsgEntry.getPhone().substring(0, 7));
				TPForm tp = MemcacheConfig.getInstance().getTP(
						zdsystemparam.getZdws_user_login());
				if (tp == null) {
					///result = "{\"desc\":\"�ӿ��̲�����\",\"status\":\"18\"}";
					queryres.setCode("201");
					queryres.setMsg("�ӿ��̲�����");
				} else {
					System.out.println("IP2:"+tp.getIp());
					log.info("ip=" + ip);
					 if (tp.getIp().indexOf(ip) == -1) {
					// result = "{\"desc\":\"IP���Ϸ�\",\"status\":\"19\"}";
					queryres.setCode("201");
					queryres.setMsg("IP���Ϸ�");
					 } else
					try{
						if (info != null) {
							result = fillOp.filterProductJSON(
									queryMsgEntry.getPhone(),
									fillOp.phoneAreas1.get(info[0]), info[1],
									"json", tp.getFlowgroups());
							//��ͨ������ֵ,��Ʒ��ѯ����json,����:18682033916,,,resutl:{"desc":"ʡ������æ","status":"8"}
							System.out.println("result="+result.toString());
							if (null == result) {
								queryres.setCode("201");
								queryres.setMsg("�޿ɶ�����Ʒ");
							} else {
								JSONObject jsontemp = JSONObject.fromObject(result.toString());								
								if(jsontemp.getString("status").equals("0")){
									queryres.setCode("200");
									queryres.setMsg("success");
									queryres.setOperator(getOperator(info[1]));// ��Ӫ��
									queryres.setArea(ZDHuaweiUtil.priviceCodeName.get(fillOp.phoneAreas1.get(info[0])));// �ֻ��Ź�����
									JSONArray js = jsontemp.getJSONObject("data").getJSONArray("list");
									ArrayList<QueryMsgBackEntityZD> order = new ArrayList<QueryMsgBackEntityZD>();
									for (int i = 0; i < js.size(); i++) {
										JSONObject b = js.getJSONObject(i);
										QueryMsgBackEntityZD querymsg = new QueryMsgBackEntityZD();
										querymsg.setCardid(b.getString("product_name").substring(0,b.getString("product_name").length() - 2));// ���,ȥ������ĵ�λmb
										querymsg.setCardname(b.getString("product_name"));// ��Ʒ����
										querymsg.setIntroduce(b.getString("product_name"));// ��Ʒ����
										querymsg.setPrice(b.getString("product_price"));// ��ֵ����֧����ʵ�ʼ۸�
										querymsg.setProductid(b.getString("product_id"));// ��������Ʒid��û�п�Ϊ��
										querymsg.setType("2");// Ϊ1�ǳ仰�ѣ�Ϊ2�ǳ�����
										order.add(querymsg);
									}
									queryres.setData(order);
								}else{			
									queryres.setCode("201");
									queryres.setMsg(jsontemp.getString("desc"));
								}							
							}						
						} else {
							queryres.setCode("201");
							queryres.setMsg("�޿ɶ�����Ʒ");
							//result = "{\"desc\":\"��ȡ����������쳣\",\"status\":\"����\"}";							
						}
					}catch(Exception e){
						e.printStackTrace();
						queryres.setCode("201");
						queryres.setMsg("�ӿ��쳣");
					}
				}
			} else {
				log.error("��������ʡ�ݳ���"
						+ queryMsgEntry.getPhone().substring(0, 7));
				queryres.setCode("201");
				queryres.setMsg("ƥ��ʡ�ݳ���");
			}			
		}
	}
	// ////////////////////////////����
	response.setCharacterEncoding("UTF-8");
	PrintWriter wr = response.getWriter();
	log.info(JSON.toJSONString(queryres));
	wr.write(JSON.toJSONString(queryres));
	wr.flush();
	wr.close();
	return null;
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

public static void main(String[] args) {
	fillOp.filterProductJSON("18682033916", "51", "2", "json", "30002");
}

}
