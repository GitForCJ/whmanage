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
 * @author 施建桥
 *
 */
public class JdOrderQuery extends DispatchAction {

	/**
	 * 京东流量订单查询
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
		String jd_order_num = prepare.get("fillOrderNo");// 京东订单号
		if (jd_order_num == null || "".equals(jd_order_num.trim())) {
			yewu.put("isSuccess", "F");
			yewu.put("errorCode", "JDI_00001");// 传入参数不正确
			String rString = JSON.toJSONString(Tools.jDParams(yewu));
			Rs_Mess(jd_order_num, "", rString, response);
			return null;
		}
		String sign = Tools.jdSign(prepare);
		if (!prepare.get("sign").equals(sign)) {// 签名错误
			yewu.put("isSuccess", "F");
			yewu.put("errorCode", "JDI_00002");// 签名验证不正确
			String rString = JSON.toJSONString(Tools.jDParams(yewu));
			Rs_Mess(jd_order_num, "", rString, response);
			return null;
		}

		if (MemcacheConfig.getInstance().get1("jd" + jd_order_num)) {
			Log.info("京东订单查询,一分钟内查询多次无效,京东订单号：" + jd_order_num);
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
				yewu.put("errorCode", "JDI_00007");//订单不存在
				String rString = JSON.toJSONString(Tools.jDParams(yewu));
				Rs_Mess(jd_order_num, "", rString, response);
				return null;
			}
			if ("0".equals(strs[1])) {//充值成功
				yewu.put("isSuccess", "T");
				yewu.put("errorCode", "");
				yewu.put("fillOrderNo",jd_order_num);
				yewu.put("agentOrderNo",strs[0]);
				yewu.put("fillTime",strs[2]);
				yewu.put("fillStatus","1");//充值成功
				yewu.put("succAmount",strs[3].replace("mb","").replace("MB",""));
				String rString = JSON.toJSONString(Tools.jDParams(yewu));
				Rs_Mess(jd_order_num, strs[0], rString, response);
				return null;
			} else if ("1".equals(strs[1])) {//充值失败
				yewu.put("isSuccess", "T");
				yewu.put("errorCode", "");
				yewu.put("fillOrderNo",jd_order_num);
				yewu.put("agentOrderNo",strs[0]);
				yewu.put("fillTime",strs[2]);
				yewu.put("fillStatus","2");//充值失败
				yewu.put("succAmount","0");
				String rString = JSON.toJSONString(Tools.jDParams(yewu));
				Rs_Mess(jd_order_num, strs[0], rString, response);
				return null;
			} else {//充值中
				yewu.put("isSuccess", "T");
				yewu.put("errorCode", "");
				yewu.put("fillOrderNo",jd_order_num);
				yewu.put("agentOrderNo",strs[0]);
				yewu.put("fillTime","");
				yewu.put("fillStatus","3");//充值中
				yewu.put("succAmount",strs[3].replace("mb","").replace("MB",""));
				String rString = JSON.toJSONString(Tools.jDParams(yewu));
				Rs_Mess(jd_order_num, strs[0], rString, response);
				return null;
			}
		} catch (Exception e) {
			yewu.put("isSuccess", "F");
			yewu.put("errorCode", "JDI_00010");//系统异常
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
	 * 响应
	 * 
	 * @param jd_order_num
	 *            京东订单号
	 * @param wh_Order_num
	 *            万恒订单
	 * @param str
	 * @param response
	 */
	private void Rs_Mess(String jd_order_num, String wh_Order_num, String str,
			HttpServletResponse response) {
		Log.info("京东订单查询,京东订单号:" + jd_order_num + ",,万恒订单号:"+wh_Order_num+",,http响应内容:" + str);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(str);
			out.flush();
			out.close();
		} catch (Exception e) {
			Log.error("京东流量充值,京东订单号:" + jd_order_num + ",,万恒订单号:"+wh_Order_num+",,http响应异常:" + str
					+ ",,,ex:" + e);
		}
	}

}
