package com.wlt.webm.business.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.dhst.CZResultJsonBean;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.PortalSend;
import com.wlt.webm.tool.Tools;

import edu.emory.mathcs.backport.java.util.Collections;

public class Jd_Flows extends DispatchAction {

	public static final String agentId = "35141";// ������ID

	public static final String PRIVATEKEY = "123456";// ��Կ

	public static final String userno = "0000000265";// ���� ���ϵͳ���
	
	public static final String fundacct = "";

	/**
	 * ������Ӫ�� ת �����Ӫ��
	 */
	public static HashMap<String, String> mutCode_map = new HashMap<String, String>();
	static {
		mutCode_map.put("2", "0");// ����
		mutCode_map.put("1", "1");// �ƶ�
		mutCode_map.put("0", "2");// ��ͨ
	}

	/**
	 * �������� ת ������
	 */
	public static HashMap<String, String> areaCode_map = new HashMap<String, String>();
	static {
		areaCode_map.put("1", "43");
		areaCode_map.put("2", "52");
		areaCode_map.put("3", "44");
		areaCode_map.put("4", "63");
		areaCode_map.put("5", "45");
		areaCode_map.put("6", "46");
		areaCode_map.put("7", "59");
		areaCode_map.put("8", "49");
		areaCode_map.put("9", "50");
		areaCode_map.put("10", "51");
		areaCode_map.put("11", "48");
		areaCode_map.put("12", "53");
		areaCode_map.put("13", "58");
		areaCode_map.put("14", "55");
		areaCode_map.put("15", "54");
		areaCode_map.put("16", "56");
		areaCode_map.put("17", "2");
		areaCode_map.put("18", "60");
		areaCode_map.put("19", "35");
		areaCode_map.put("20", "61");
		areaCode_map.put("21", "57");
		areaCode_map.put("22", "64");
		areaCode_map.put("23", "62");
		areaCode_map.put("24", "65");
		areaCode_map.put("25", "66");
		areaCode_map.put("26", "67");
		areaCode_map.put("27", "68");
		areaCode_map.put("28", "69");
		areaCode_map.put("29", "70");
		areaCode_map.put("30", "71");
		areaCode_map.put("31", "72");
	}

	static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	static HttpClient client = new HttpClient(connectionManager);
	static {
		client.getHttpConnectionManager().getParams().setConnectionTimeout(
				60 * 1000);// ����ʱ�� 60�룬��λ�Ǻ���
		client.getHttpConnectionManager().getParams().setSoTimeout(20 * 1000);// ��ȡʱ��
																				// 20�룬��λ�Ǻ���
	}

	/**
	 * ����������ֵ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		boolean bool = true;

		Map<String, String[]> params = request.getParameterMap();
		HashMap<String, String> prepare = new HashMap<String, String>();
		for (String key : params.keySet()) {
			String val = params.get(key)[0];
			if (val == null || "".equals(val.trim())) {
				bool = false;
			}
			prepare.put(key, val);
		}

		HashMap<String, String> yewu = new HashMap<String, String>();
		String jd_order_num = prepare.get("fillOrderNo");// ����������
		String wh_order_num = ""; // ��㶩����

		Log.info("��������,����������:" + jd_order_num + ",��㶩����:" + wh_order_num
				+ ",������Ϣ:" + prepare);

		if (!bool) {
			yewu.put("isSuccess", "F");
			yewu.put("errorCode", "JDI_00001");// �����������ȷ
			String rString = JSON.toJSONString(Tools.jDParams(yewu));
			Rs_Mess(jd_order_num, wh_order_num, rString, response);
			return null;
		}

		String sign = Tools.jdSign(prepare);
		if (!prepare.get("sign").equals(sign)) {// ǩ������
			yewu.put("isSuccess", "F");
			yewu.put("errorCode", "JDI_00002");// ǩ����֤����ȷ
			String rString = JSON.toJSONString(Tools.jDParams(yewu));
			Rs_Mess(jd_order_num, wh_order_num, rString, response);
			return null;
		}
		// ������Ӫ�̲����� || ����Ϊ ȫ��
		if ("3".equals(prepare.get("mutCode"))
				|| "99".equals(prepare.get("areaCode"))) {
			yewu.put("isSuccess", "F");
			yewu.put("errorCode", "JDI_00003");
			String rString = JSON.toJSONString(Tools.jDParams(yewu));
			Rs_Mess(jd_order_num, wh_order_num, rString, response);
			return null;
		}
		//�ж� ��Ӫ�����ͣ����� �Ƿ����
		if(!mutCode_map.containsKey(prepare.get("mutCode")) || !areaCode_map.containsKey(prepare.get("areaCode"))){
			yewu.put("isSuccess", "F");
			yewu.put("errorCode", "JDI_00003");
			String rString = JSON.toJSONString(Tools.jDParams(yewu));
			Rs_Mess(jd_order_num, wh_order_num, rString, response);
			return null;
		}

		// ��㶩����
		wh_order_num = Tools.getNow3()
				+ ((int) (Math.random() * 1000) + 1000)
				+ ((char) (new Random().nextInt(26) + (int) 'A'))
				+ ((int) (Math.random() * 1000) + 1000)
				+ ((char) (new Random().nextInt(26) + (int) 'a')) + "JD";
		
		// ��װ��Ϣ
		JdOrderBean bean = new JdOrderBean(prepare.get("fillOrderNo"), prepare
				.get("fillMobile"), prepare.get("fillAmount"), prepare
				.get("fillType"), prepare.get("finTime"), prepare
				.get("areaUsed"), prepare.get("notifyUrl"),
				Jd_Flows.mutCode_map.get(prepare.get("mutCode")),
				Jd_Flows.areaCode_map.get(prepare.get("areaCode")), prepare
				.get("costPrice"), wh_order_num, userno);
		if (!PortalSend.getInstance().Send_JD_Message(bean)) {// ϵͳ����
			Log.info("���վ���������ֵ������������Ϣ�ж�ʧ�ܣ����������ţ�"+prepare.get("fillOrderNo"));
			return null;
		}
		yewu.put("isSuccess", "T");
		yewu.put("errorCode", "");
		yewu.put("agentOrderNo", wh_order_num);
		yewu.put("fillOrderNo", jd_order_num);
		String rString = JSON.toJSONString(Tools.jDParams(yewu));
		Rs_Mess(jd_order_num, wh_order_num, rString, response);
		return null;
	}

	/**
	 *  �����ص�
	 * @param fileOrderNum ����������
	 * @param wh_order_num ��㶩��
	 * @param fillAmount ��ֵ
	 * @param updateOrderTime ���¶���ʱ��
	 * @param notityUrl �ص���ַ
	 * @param rs_code 1�ɹ� 2ʧ��
	 * @return boolean
	 */
	public static boolean Rs_break(String fileOrderNum,String wh_order_num,String fillAmount,String updateOrderTime,String notityUrl, String rs_code) {
		HashMap<String, String> yewu = new HashMap<String, String>();
		yewu.put("agentId", agentId);
		yewu.put("fillOrderNo", fileOrderNum);
		yewu.put("agentOrderNo",wh_order_num);
		yewu.put("fillStatus", rs_code);
		yewu.put("succAmount", ("1".equals(rs_code)) ? fillAmount
				: "0");
		yewu.put("fillTime", updateOrderTime);
		HashMap<String, String> map = Tools.jDParams(yewu);

		StringBuffer buffer = new StringBuffer(
				notityUrl.indexOf("?") == -1 ? (notityUrl + "?")
						: notityUrl);
		ArrayList<String> keys = new ArrayList<String>(map.keySet());
		Collections.sort(keys);
		for (String key : keys) {
			if ("?".equals(buffer.substring(buffer.length() - 1, buffer
					.length()))) {
				buffer.append(key + "=" + map.get(key));
			} else {
				buffer.append("&" + key + "=" + map.get(key));
			}
		}
		Log.info("���������ص���Ӧ,����������:" + fileOrderNum + ",��㶩����:"+ wh_order_num + ",http�ص�����url:" + buffer.toString());
		
		PostMethod postMt = new PostMethod(buffer.toString());
		try {
			postMt.setRequestHeader("Content-Type",
					"application/xml;charset=UTF-8");
			int st = client.executeMethod(postMt);
			if (st == 200) {
				String rs = postMt.getResponseBodyAsString();
				Log.info("���������ص���Ӧ,����������:" + fileOrderNum + ",��㶩����:"
						+ wh_order_num + ",��Ӧ����:" + rs);
			}
		} catch (Exception e) {
			Log.error("���������ص�,,ϵͳ�쳣,����������:" + fileOrderNum + ",��㶩����:"+ wh_order_num + ",,ex:"+e);
		} finally {
			postMt.releaseConnection();
		}
		return true;
	}
	
	/**
	 * ��Ӧ
	 * 
	 * @param jd_order_num
	 *            ����������
	 * @param wh_Order_num
	 *            ��㶩��
	 * @param str
	 * @param response
	 */
	private void Rs_Mess(String jd_order_num, String wh_Order_num, String str,
			HttpServletResponse response) {
		Log.info("����������ֵ,����������:" + jd_order_num + ",��㶩����:" + wh_Order_num
				+ ",,http��Ӧ����:" + str);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(str);
			out.flush();
			out.close();
		} catch (Exception e) {
			Log.error("����������ֵ,����������:" + jd_order_num + ",��㶩����:" + wh_Order_num
					+ ",,http��Ӧ�쳣:" + str + ",,,ex:" + e);
		}
	}
}
