package com.wlt.webm.business.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.business.bean.beijingFlow.BeanFlow;
import com.wlt.webm.business.bean.unicom3gquery.EncodeUtils;
import com.wlt.webm.business.bean.unicom3gquery.FlowOrder;
import com.wlt.webm.business.bean.unicom3gquery.FlowOrderDetail;
import com.wlt.webm.business.bean.unicom3gquery.HttpFillOP;
import com.wlt.webm.business.bean.unicom3gquery.HttpQueryOP;
import com.wlt.webm.business.bean.unicom3gquery.QueryDesc;
import com.wlt.webm.business.bean.unicom3gquery.QueryRes;
import com.wlt.webm.business.bean.unicom3gquery.xml.FlowRequest;
import com.wlt.webm.business.bean.unicom3gquery.xml.JavaOpXml;
import com.wlt.webm.business.bean.unicom3gquery.xml.RequestMsgHeader;
import com.wlt.webm.business.bean.unicom3gquery.xml.TencentFlowOrderReq;
import com.wlt.webm.business.form.TPForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.ten.service.MD5Util;
import com.wlt.webm.tool.Tools;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * ��ͨ������ѯ
 * @author 1989
 * 
 */
public class Flow3g extends DispatchAction {

	static HttpQueryOP queryOp = new HttpQueryOP();
	static JavaOpXml javaOpxml = new JavaOpXml();
	static HttpFillOP fillOp = new HttpFillOP();

	/**
	 * ��ͨ��������ѯJSON
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public ActionForward qflow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// http://localhost:8888/wh/flow?method=qFlow
		String phone = request.getParameter("phone");
		String userno = request.getParameter("userno");
		String signSource = request.getParameter("sign");
		String result = null;
		// У�����
		if (null == phone || phone.length() != 11 || null == userno
				|| null == signSource) {
			// rs=new HashMap<String, String>();
			// rs.put("res_code", "6000");
			// rs.put("res_desc", "������ʽ����ȷ");
			// result=JSON.toJSONString(rs);
			result = constructorJSON("res_code", "res_desc", "error", "1002",
					"ȱ�ٱ����ֶ�");
		} else {
			TPForm tp = MemcacheConfig.getInstance().getTP(userno);
			if (tp == null) {
				result = constructorJSON("res_code", "res_desc", "error",
						"6001", "�ӿڴ����̲�����");
			} else {
				String ip = Tools.getIpAddr(request);
				if (tp.getIp().indexOf(ip) == -1) {
					result = constructorJSON("res_code", "res_desc", "error",
							"6002", "�ǰ�IP");
				} else {
					if (!signSource.equals(MD5Util.MD5Encode(phone + userno
							+ tp.getKeyvalue(), "UTF-8"))) {
						result = constructorJSON("res_code", "res_desc",
								"error", "6003", "У�����");
					} else {
						result = queryOp.OP(HttpQueryOP.QUERYURL,
								constructorJSON("phoneNum", "appid",
										EncodeUtils.encode(
												HttpQueryOP.PHONESIGN, phone),
										EncodeUtils.encode(
												HttpQueryOP.APPIDSIGN,
												HttpQueryOP.APPID)));
						if (null == result) {
							result = constructorJSON("res_code", "res_desc",
									"error", "6000", "δ��ѯ�������Ϣ");
						}
					}
				}
			}
		}
		response.setCharacterEncoding("UTF-8");
		PrintWriter wr = response.getWriter();
		wr.write(result);
		wr.flush();
		wr.close();
		wr = null;
		return null;
	}

	/**
	 * ��������son
	 * 
	 * @param param1
	 * @param param2
	 * @param param3
	 * @param value1
	 * @param value3
	 * @return
	 */
	public static String constructorJSON(String param1, String param2,
			String param3, String value1, String value3) {
		return "{\"" + param1 + "\":\"" + value1 + "\",\"" + param2 + "\":{\""
				+ param3 + "\":\"" + value3 + "\"}}";
	}

	/**
	 * ���췵��json
	 * 
	 * @param param1
	 * @param param2
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static String constructorJSON(String param1, String param2,
			String value1, String value2) {
		return "{\"" + param1 + "\":\"" + value1 + "\",\"" + param2 + "\":\""
				+ value2 + "\"}";
	}

	/**
	 * ��ͨ��������ѯ xml��ʽ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public ActionForward flowxml(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String code = "", Retmsg = "", PackageId = "", PackageName = "", PackageSubname = "", UnitName = "3", CumulationTotal = "", CumulationLeft = "", StartTime = "", EndTime = "", phone = "";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					request.getInputStream()));
			String lines;
			StringBuffer xml = new StringBuffer();
			while ((lines = reader.readLine()) != null) {
				xml.append(lines);
			}
			Log.info("�ӵ���ѶԴ����ѯ����:" + xml);
			if (xml.length() < 10) {
				return null;
			}
			FlowRequest re = (FlowRequest) javaOpxml.xml2Java(xml.toString(),
					FlowRequest.class, "Request");
			HashMap<String, String> maps = new HashMap<String, String>();
			phone = re.getMsgBody().getMobile();
			RequestMsgHeader header = re.getMsgHeader();
			maps.put("Version", header.getVersion());
			maps.put("TimeStamp", header.getTimeStamp());
			maps.put("TransId", header.getTransId());
			maps.put("RandomValue", header.getRandomValue());
			maps.put("AppId", header.getAppId());
			maps.put("ApiName", header.getApiName());
			maps.put("Format", header.getFormat());
			maps.put("Mobile", phone);
			TPForm tp = MemcacheConfig.getInstance().getTP(header.getAppId());
			if (null == tp || null == phone || phone.length() != 11) {
				code = "00005";
				Retmsg = "��������";
			} else {
				ArrayList<String> list = new ArrayList<String>(maps.keySet());
				Collections.sort(list);
				String md5sign = "";
				for (String key : list) {
					md5sign += (key + maps.get(key));
				}
				md5sign += tp.getKeyvalue();
				if (!MD5Util.MD5Encode(md5sign, "UTF-8").equals(
						header.getSign())) {
					code = "00001";
					Retmsg = "ǩ������";
				} else {
					QueryRes flow = JSON.parseObject(queryOp.OP(
							HttpQueryOP.QUERYURL, constructorJSON("phoneNum",
									"appid", EncodeUtils.encode(
											HttpQueryOP.PHONESIGN, phone),
									EncodeUtils.encode(HttpQueryOP.APPIDSIGN,
											HttpQueryOP.APPID))),
							QueryRes.class);
					QueryDesc desc = flow.getRes_desc();
					code = "0" + flow.getRes_code();
					Retmsg = null == desc.getError() ? "SUCCESS" : "";
					PackageName = desc.getProductname();
					Pattern pattern = Pattern
							.compile("^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
					CumulationTotal = (null != desc.getTotalflow() && pattern
							.matcher(desc.getTotalflow()).matches()) ? (Double
							.parseDouble(desc.getTotalflow()) * 1024 + "") : "";
					CumulationLeft = (null != desc.getTotalflow()
							&& null != desc.getUsedflow()
							&& pattern.matcher(desc.getTotalflow()).matches() && pattern
							.matcher(desc.getUsedflow()).matches()) ? (Double
							.parseDouble(desc.getTotalflow()) - Double
							.parseDouble(desc.getUsedflow()))
							* 1024 + "" : "";
					StartTime = desc.getDetailstartdate();
					EndTime = desc.getDetailenddate();
				}
			}
		} catch (Exception e) {
			code = "00002";
			Retmsg = "ϵͳ����";
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml;charset=UTF-8");
		PrintWriter wr;
		try {
			wr = response.getWriter();
			wr.write(javaOpxml.string2xml(phone, code, Retmsg, PackageId,
					PackageName, PackageSubname, UnitName, CumulationTotal,
					CumulationLeft, StartTime, EndTime));
			wr.flush();
			wr.close();
			wr = null;
		} catch (IOException e) {
			Log.error("������ѯ����:" + e.toString());
			e.printStackTrace();
		}
		return null;
	}

	/**ȫ��������Ʒ��ѯ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward proflow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//http://localhost:8080/wh/flow.do?method=proflow&phoneNum=18682033916&activityId=3366&areaId=51&phoneType=2&userNo=0000755868
		String phone = request.getParameter("phoneNum");
		String areaid = request.getParameter("areaId");
		String type = request.getParameter("phoneType");
		String userno = request.getParameter("userNo");
		Object result = null;
		// У�����
		if (null == phone || phone.length() != 11 || null == userno
				  || null == areaid || null == type) {
			result = "{\"desc\":\"��������\",\"status\":\"6\"}";
		} else {
			TPForm tp = MemcacheConfig.getInstance().getTP(userno);
			if (tp == null) {
				result = "{\"desc\":\"�ӿ��̲�����\",\"status\":\"18\"}";
			} else {
				String ip = Tools.getIpAddr(request);
				if (tp.getIp().indexOf(ip) == -1) {
					result = "{\"desc\":\"IP���Ϸ�\",\"status\":\"19\"}";
				} else {
					result = fillOp.filterProductJSON(phone,areaid,type,"json",tp.getFlowgroups());
					if (null == result) {
						result = "{\"desc\":\"�޿ɶ�����Ʒ\",\"status\":\"-1\"}";
					}
				}
			}
		}
		Log.info("������Ʒ��ѯ,��Ӧ�������ӿ�json�ַ���,"+result);
		response.setCharacterEncoding("UTF-8");
		PrintWriter wr = response.getWriter();
		wr.write(result+"");
		wr.flush();
		wr.close();
		wr = null;
		return null;
	}

	/**ȫ��������ֵ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public ActionForward orderflow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// http://localhost:8080/wh/flow.do?method=orderflow&orderNo=20150513095022610001&productId=G00200
		//&activityId=3366&name=200&phoneNum=18682033916&realPrice=1600&userNo=0000755868&areaId=51&token=fdd5a5a02bfdee7509f6393d42401f82
		String orderid = request.getParameter("orderNo");
		String productId = request.getParameter("productId");
		String phone = request.getParameter("phoneNum");
		String realPrice = request.getParameter("realPrice");//��
		String phoneType=request.getParameter("phoneType");
		String name=request.getParameter("name");
		String userno = request.getParameter("userNo");
		String areaId=request.getParameter("areaId");
		String signSource = request.getParameter("token");
		
		String result = null;
		// У�����
		if (null == phone || phone.length() != 11 || null == userno
				|| null == signSource || null == orderid || null == phoneType
				|| null == realPrice || null == productId||null==areaId||null==name) {
			result = "{\"desc\":\"��������\",\"status\":\"6\"}";
		} else {
			TPForm tp = MemcacheConfig.getInstance().getTP(userno);
			if (tp == null) {
				result = "{\"desc\":\"�ӿ��̲�����\",\"status\":\"18\"}";
			} else {
				String ip = Tools.getIpAddr(request);
				if (tp.getIp().indexOf(ip) == -1) {
					result = "{\"desc\":\"IP���Ϸ�\",\"status\":\"19\"}";
				} else {
					if (!signSource.equals(MD5Util.MD5Encode(tp.getKeyvalue()+orderid+productId+phone+realPrice, "UTF-8"))) {
						result = "{\"desc\":\"У�����\",\"status\":\"7\"}";
					} else {
						result=Flows_Order_Inter(phoneType, areaId, orderid, productId, phone, realPrice, name, tp);
					}
				}
			}
		}
		response.setCharacterEncoding("UTF-8");
		PrintWriter wr = response.getWriter();
		System.out.println("back:"+result);
		wr.write(result);
		wr.flush();
		wr.close();
		wr = null;
		return null;
	}
	
	/**
	 * ������ֵ ���ýӿ� ��������
	 * @param phoneType
	 * @param areaId
	 * @param orderid
	 * @param productId
	 * @param phone
	 * @param realPrice
	 * @param name
	 * @param tp
	 * @return String 
	 */
	@SuppressWarnings("static-access")
	public String Flows_Order_Inter(String phoneType,String areaId,String orderid,String productId,String phone,String realPrice,String name,TPForm tp){
		realPrice=(int)(Float.parseFloat(realPrice))+"";
		name=name.replace("MB","").replace("mb","");
		String userno=tp.getUser_no();
		String interId=BiProd.getFlowInterface(phoneType,HttpFillOP.phoneAreas.get(areaId))+"".trim();
		HashMap<String,String> map=null;
		if(HttpFillOP.FLOw1.equals(interId)){//ʡ����
		map=fillOp.fillFlow(orderid, productId, phone,
				Integer.parseInt(realPrice), HttpFillOP.ACTIVITYID, userno, tp.getUser_login(),tp.getSa_zone(),
				HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"", 
				tp.getUserPt(),tp.getFlowgroups(),interId,"1",name);
		}else if(HttpFillOP.FLOw2.equals(interId)){//������ͨ����
			map=BeanFlow.fillFlow(orderid, productId, phone,
					Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"", 
					tp.getUserPt(),tp.getFlowgroups(),interId,"1",name);
		}else if(HttpFillOP.FLOW3.equals(interId)){//˼���ƶ�����
			map=BeanFlow.sikong_flow(orderid, productId, phone,
					Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"", 
					tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"1");//�ƶ�
		}else if(HttpFillOP.FLOW4.equals(interId)){//˼����ͨ����
			map=BeanFlow.sikong_flow(orderid, productId, phone,
					Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"", 
					tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"2");//��ͨ
		}else if(HttpFillOP.FLOW5.equals(interId)){//˼�յ�������
			map=BeanFlow.sikong_flow(orderid, productId, phone,
					Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"",
					tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"0");//����
		}else if(HttpFillOP.FLOW6.equals(interId)){//�����ƶ�����
			map=BeanFlow.WebServices_flow(orderid, productId, phone,
					Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"", tp.getUserPt(),
					tp.getFlowgroups(),interId,"1",name);
		}else if(HttpFillOP.FLOW8.equals(interId)){//����ͨ�ƶ�����
			map=BeanFlow.Dhst_flow(orderid, productId, phone,
					Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"", 
					tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"1");//�ƶ�
		}else if(HttpFillOP.FLOW9.equals(interId)){//����ͨ��ͨ����
			map=BeanFlow.Dhst_flow(orderid, productId, phone,
					Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"", 
					tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"2");//��ͨ
		}else if(HttpFillOP.FLOW7.equals(interId)){//����ͨ��������
			map=BeanFlow.Dhst_flow(orderid, productId, phone,
					Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"",
					tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"0");//����
		}else if(HttpFillOP.FLOW10.equals(interId)){//������������
			map=BeanFlow.LeLiu_flow(orderid, productId, phone,
					Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"",
					tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"0");//����
		}else if(HttpFillOP.FLOW11.equals(interId)){//������ͨ����
			map=BeanFlow.LeLiu_flow(orderid, productId, phone,
					Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"",
					tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"1");//�ƶ�
		}else if(HttpFillOP.FLOW12.equals(interId)){//�����ƶ�����
			map=BeanFlow.LeLiu_flow(orderid, productId, phone,
					Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"",
					tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"2");//��ͨ
		}else if(HttpFillOP.FLOW14.equals(interId)){//�����ƶ�����
			map=BeanFlow.Lianlian_flow(orderid, productId, phone,
					Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"", 
					tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"1");//�ƶ�
		}else if(HttpFillOP.FLOW15.equals(interId)){//������ͨ����
			map=BeanFlow.Lianlian_flow(orderid, productId, phone,
					Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"", 
					tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"2");//��ͨ
		}else if(HttpFillOP.FLOW13.equals(interId)){//������������
			map=BeanFlow.Lianlian_flow(orderid, productId, phone,
					Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"",
					tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"0");//����
		}else if(HttpFillOP.flow16.equals(interId)){//�����ƶ�����
			map=BeanFlow.zb_flow(orderid, productId, phone,
					Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"", tp.getUserPt(),
					tp.getFlowgroups(),interId,"1",name);
		}else if(HttpFillOP.flow17.equals(interId)){//�����ƶ�����
			map=BeanFlow.zy_flow(orderid, productId, phone,
					Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"", tp.getUserPt(),
					tp.getFlowgroups(),interId,"1",name);
		}else if(HttpFillOP.flow18.equals(interId)){//껷���ͨ
			map=BeanFlow.LiuLiangFan(orderid, productId, phone,
					Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"", tp.getUserPt(),
					tp.getFlowgroups(),interId,"1",name,"2");
		}else if(HttpFillOP.FLOW25.equals(interId)){//껷��ƶ�
			map=BeanFlow.LiuLiangFan(orderid, productId, phone,
					Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"", tp.getUserPt(),
					tp.getFlowgroups(),interId,"1",name,"1");
		}else if(HttpFillOP.FLOW24.equals(interId)){//껷����
			map=BeanFlow.LiuLiangFan(orderid, productId, phone,
					Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"", tp.getUserPt(),
					tp.getFlowgroups(),interId,"1",name,"0");
		}else if(HttpFillOP.FLOW21.equals(interId)){//������ͨ����
			map=BeanFlow.DingXin_flow(orderid, productId, phone,
					Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"",
					tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"2");//��ͨ
		}else if(HttpFillOP.FLOW20.equals(interId)){//�����ƶ�����
			map=BeanFlow.DingXin_flow(orderid, productId, phone,
					Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"",
					tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"1");//�ƶ�
		}else if(HttpFillOP.FLOW19.equals(interId)){//���ŵ�������
			map=BeanFlow.DingXin_flow(orderid, productId, phone,
					Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"",
					tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"0");//����
		}else if(HttpFillOP.FLOW22.equals(interId)){//������ͨ����
			map=BeanFlow.ZhiXin_flow(orderid, productId, phone,
					Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"",
					tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"2");//��ͨ
		}else if(HttpFillOP.FLOW23.equals(interId)){//���ŵ�������
			map=BeanFlow.ZhiXin_flow(orderid, productId, phone,
					Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
					HttpFillOP.phoneAreas.get(areaId), tp.getUser_site()+"",
					tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"0");//����
		}else{
			map=new HashMap<String, String>();
			map.put("result", "{\"desc\":\"��Ʒ��Ӧ�ӿڲ�����\",\"status\":\"20\"}");
		}
		String result;
		if(null!=map){
			result =map.get("result");
		}else{
			result = "{\"desc\":\"�ӿڵ����쳣\",\"status\":\"8\"}";
		}
		return result;
	}
	
	/**
	 * ��ͨ������ֵ�����ѯ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public ActionForward conflow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// http://localhost:8080/wh/flow.do?method=conflow&orderNo=20150513095022610003&userNo=0000755868&token=201505131037080661990000000201
		String orderid = request.getParameter("orderNo");
		String userno = request.getParameter("userNo");
		String signSource=request.getParameter("token");
		String result = null;
		// У�����
		if ( null == userno|| null == signSource ) {
			result = "{\"desc\":\"��������\",\"status\":\"6\"}";
		} else {
			TPForm tp = MemcacheConfig.getInstance().getTP(userno);
			if (tp == null) {
				result = "{\"desc\":\"�ӿ��̲�����\",\"status\":\"18\"}";
			} else {
				String ip = Tools.getIpAddr(request);
				if (tp.getIp().indexOf(ip) == -1) {
					result = "{\"desc\":\"IP���Ϸ�\",\"status\":\"19\"}";
				} else {
					if (!signSource.equals(MD5Util.MD5Encode(orderid + userno
							+ tp.getKeyvalue(), "UTF-8"))) {
						result = "{\"desc\":\"У�����\",\"status\":\"7\"}";
					} else {
						result = fillOp.queryResultJSON(orderid);
						if (null == result) {
							result = "{\"desc\":\"�ӿڵ����쳣\",\"status\":\"8\"}";
						}
					}
				}
			}
		}
		response.setCharacterEncoding("UTF-8");
		PrintWriter wr = response.getWriter();
		wr.write(result);
		wr.flush();
		wr.close();
		wr = null;
		return null;
	}
	
	/**
	 * ��ͨ������ֵ������ѯ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws InterruptedException 
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public ActionForward queryOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
			{
		// http://localhost:8080/wh/flow.do?method=queryOrder&username=18501370432
		String username = request.getParameter("username");
		String result = null;
		if ( null == username) {
			result = "{\"desc\":\"��������\",\"status\":\"6\"}";
		} else {
			ArrayList<String[]> lists=BiProd.getorders(username);
			if(null==lists||lists.size()<1){
				result = "{\"desc\":\"δ��ѯ����ض���\",\"status\":\"5\"}";
			}else{
				FlowOrder orders=new FlowOrder();
				orders.setDesc("��ѯ�ɹ�");
				orders.setStatus(0);
				FlowOrderDetail detail=null;
				List<FlowOrderDetail> order=new ArrayList<FlowOrderDetail>();
				for(String[] str:lists){
					detail=new FlowOrderDetail();
					detail.setPh(str[0]);
					detail.setFee(str[1]);
					detail.setTradefee(str[2]);
					detail.setTradetime(str[3]);
					detail.setSt(str[4]);
					order.add(detail);
				}
				orders.setOrders(order);
				result=JSON.toJSONString(orders,true);
			}
		}
		response.setCharacterEncoding("UTF-8");
		PrintWriter wr;
		try {
			wr = response.getWriter();
			wr.write(result);
			wr.flush();
			wr.close();
			wr = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ��ȡ�˻����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAccountMoney(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String mon="-1";
		String result ="";
		DBService db=null;
		try {
			db=new DBService();
			mon=db.getString("SELECT accountleft FROM wht_childfacct WHERE fundacct='000006222318201' AND TYPE='01'");
			result ="{\"money\":\""+Math.round(Float.parseFloat(mon))/1000+"\",\"status\":\"0\"}";
		} catch (Exception e) {
			Log.error("ϵͳ�쳣����");
			result ="{\"money\":\0\",\"status\":\"0\"}";
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		response.setCharacterEncoding("UTF-8");
		PrintWriter wr = response.getWriter();
		wr.write(result);
		wr.flush();
		wr.close();
		wr = null;
		return null;
	}
	
	/**
	 * ��Ѷ�������� xml��ʽ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public ActionForward tencentFloworder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String code = "", Retmsg = "", PackageId = "",transId="",phone = "";
		try {
			StringBuffer xml = new StringBuffer();
			Enumeration el=request.getParameterNames();
			while(el.hasMoreElements()){
				String str=(String)el.nextElement();
				if(!"method".equals(str)){
					xml.append(str);
					break;
				}
			}
			Log.info("�ӵ���ѶԴ����������:" + xml);
			if (xml.length() < 10) {
				return null;
			}
			TencentFlowOrderReq re = (TencentFlowOrderReq) javaOpxml.xml2Java(xml.toString(),
					TencentFlowOrderReq.class, "Request");
			HashMap<String, String> maps = new HashMap<String, String>();
			phone = re.getMsgBody().getMobile();
			PackageId=re.getMsgBody().getPackageId();
			RequestMsgHeader header = re.getMsgHeader();
			maps.put("Version", header.getVersion());
			maps.put("TimeStamp", header.getTimeStamp());
			maps.put("TransId", header.getTransId());
			maps.put("RandomValue", header.getRandomValue());
			maps.put("AppId", header.getAppId());
			maps.put("ApiName", header.getApiName());
			maps.put("Format", header.getFormat());
			maps.put("Mobile", phone);
			maps.put("PackageId", PackageId);
			maps.put("ActivceMod", re.getMsgBody().getActivceMod());
			TPForm tp = MemcacheConfig.getInstance().getTP(header.getAppId());
			if (null == tp || null == phone || phone.length() != 11||PackageId=="") {
				code = "00005";
				Retmsg = "��������";
			} else {
				ArrayList<String> list = new ArrayList<String>(maps.keySet());
				Collections.sort(list);
				String md5sign = "";
				for (String key : list) {
					md5sign += (key + maps.get(key));
				}
				md5sign += tp.getKeyvalue();
				if (!MD5Util.MD5Encode(md5sign, "UTF-8").equals(
						header.getSign())) {
					code = "00001";
					Retmsg = "ǩ������";
				} else {
					//������������
				}
			}
		} catch (Exception e) {
			code = "00002";
			Retmsg = "ϵͳ����";
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-www-form-urlencoded");
		PrintWriter wr;
		try {
			wr = response.getWriter();
			wr.write(javaOpxml.flowOrder2xml(phone, code, Retmsg, transId));
			wr.flush();
			wr.close();
			wr = null;
		} catch (IOException e) {
			Log.error("������ѯ����:" + e.toString());
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		HashMap<String,String> a=new HashMap<String, String>();
		a.put("a", "12334");
		a.put("b", "12334");
		a.put("c", "12334");
		System.out.println(JSON.toJSONString(a));
	}
}
