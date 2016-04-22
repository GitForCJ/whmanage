package com.wlt.webm.business.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.tool.Tools;

/**
 * @author ʩ����
 *
 */
public class JdOrderQuery extends DispatchAction {

	/**
	 * ��������������ѯ
	 * @param mapping 
	 * @param form 
	 * @param request 
	 * @param response 
	 * @return null
	 */
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, String[]> params = request.getParameterMap();
		HashMap<String, String> prepare = new HashMap<String, String>();
		for (String key : params.keySet()) {
			String val = params.get(key)[0];
			prepare.put(key, val);
		}

		HashMap<String, String> yewu = new HashMap<String, String>();
		String jd_order_num = prepare.get("fillOrderNo");// ����������
		if (jd_order_num == null || "".equals(jd_order_num.trim())) {
			yewu.put("isSuccess", "F");
			yewu.put("errorCode", "JDI_00001");// �����������ȷ
			String rString = JSON.toJSONString(Tools.jDParams(yewu));
			Rs_Mess(jd_order_num, "", rString, response);
			return null;
		}
		String sign = Tools.jdSign(prepare);
		if (!prepare.get("sign").equals(sign)) {// ǩ������
			yewu.put("isSuccess", "F");
			yewu.put("errorCode", "JDI_00002");// ǩ����֤����ȷ
			String rString = JSON.toJSONString(Tools.jDParams(yewu));
			Rs_Mess(jd_order_num, "", rString, response);
			return null;
		}

		if (MemcacheConfig.getInstance().get1("jd" + jd_order_num)) {
			Log.info("����������ѯ,һ�����ڲ�ѯ�����Ч,���������ţ�" + jd_order_num);
			return null;
		} else {
			MemcacheConfig.getInstance().add("jd" + jd_order_num, "123");
		}

		DBService db = null;
		try {
			db = new DBService();
			String[] strs = db.getStringArray("SELECT tradeserial,state,tradetime,writeoff FROM wht_orderform_" + Tools.getNow3().substring(2, 6)
							+ " WHERE writecheck='" + jd_order_num + "'");
			if (strs == null || strs.length <= 0) {
				yewu.put("isSuccess", "F");
				yewu.put("errorCode", "JDI_00007");//����������
				String rString = JSON.toJSONString(Tools.jDParams(yewu));
				Rs_Mess(jd_order_num, "", rString, response);
				return null;
			}
			if ("0".equals(strs[1])) {//��ֵ�ɹ�
				yewu.put("isSuccess", "T");
				yewu.put("errorCode", "");
				yewu.put("fillOrderNo",jd_order_num);
				yewu.put("agentOrderNo",strs[0]);
				yewu.put("fillTime",strs[2]);
				yewu.put("fillStatus","1");//��ֵ�ɹ�
				yewu.put("succAmount",strs[3].replace("mb","").replace("MB",""));
				String rString = JSON.toJSONString(Tools.jDParams(yewu));
				Rs_Mess(jd_order_num, strs[0], rString, response);
				return null;
			} else if ("1".equals(strs[1])) {//��ֵʧ��
				yewu.put("isSuccess", "T");
				yewu.put("errorCode", "");
				yewu.put("fillOrderNo",jd_order_num);
				yewu.put("agentOrderNo",strs[0]);
				yewu.put("fillTime",strs[2]);
				yewu.put("fillStatus","2");//��ֵʧ��
				yewu.put("succAmount","0");
				String rString = JSON.toJSONString(Tools.jDParams(yewu));
				Rs_Mess(jd_order_num, strs[0], rString, response);
				return null;
			} else {//��ֵ��
				yewu.put("isSuccess", "T");
				yewu.put("errorCode", "");
				yewu.put("fillOrderNo",jd_order_num);
				yewu.put("agentOrderNo",strs[0]);
				yewu.put("fillTime","");
				yewu.put("fillStatus","3");//��ֵ��
				yewu.put("succAmount",strs[3].replace("mb","").replace("MB",""));
				String rString = JSON.toJSONString(Tools.jDParams(yewu));
				Rs_Mess(jd_order_num, strs[0], rString, response);
				return null;
			}
		} catch (Exception e) {
			yewu.put("isSuccess", "F");
			yewu.put("errorCode", "JDI_00010");//ϵͳ�쳣
			String rString = JSON.toJSONString(Tools.jDParams(yewu));
			Rs_Mess(jd_order_num, "", rString, response);
			return null;
		} finally {
			if (db != null) {
				db.close();
				db = null;
			}
		}
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
		Log.info("����������ѯ,����������:" + jd_order_num + ",,��㶩����:"+wh_Order_num+",,http��Ӧ����:" + str);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(str);
			out.flush();
			out.close();
		} catch (Exception e) {
			Log.error("����������ֵ,����������:" + jd_order_num + ",,��㶩����:"+wh_Order_num+",,http��Ӧ�쳣:" + str
					+ ",,,ex:" + e);
		}
	}

}
