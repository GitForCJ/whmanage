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

	public static final String agentId = "35141";// 代理商ID

	public static final String PRIVATEKEY = "123456";// 秘钥

	public static final String userno = "0000000265";// 京东 万恒系统编号
	
	public static final String fundacct = "";

	/**
	 * 京东运营商 转 万恒运营商
	 */
	public static HashMap<String, String> mutCode_map = new HashMap<String, String>();
	static {
		mutCode_map.put("2", "0");// 电信
		mutCode_map.put("1", "1");// 移动
		mutCode_map.put("0", "2");// 联通
	}

	/**
	 * 京东地市 转 万恒地市
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
				60 * 1000);// 链接时间 60秒，单位是毫秒
		client.getHttpConnectionManager().getParams().setSoTimeout(20 * 1000);// 读取时间
																				// 20秒，单位是毫秒
	}

	/**
	 * 京东流量充值
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
		String jd_order_num = prepare.get("fillOrderNo");// 京东订单号
		String wh_order_num = ""; // 万恒订单号

		Log.info("京东流量,京东订单号:" + jd_order_num + ",万恒订单号:" + wh_order_num
				+ ",订单信息:" + prepare);

		if (!bool) {
			yewu.put("isSuccess", "F");
			yewu.put("errorCode", "JDI_00001");// 传入参数不正确
			String rString = JSON.toJSONString(Tools.jDParams(yewu));
			Rs_Mess(jd_order_num, wh_order_num, rString, response);
			return null;
		}

		String sign = Tools.jdSign(prepare);
		if (!prepare.get("sign").equals(sign)) {// 签名错误
			yewu.put("isSuccess", "F");
			yewu.put("errorCode", "JDI_00002");// 签名验证不正确
			String rString = JSON.toJSONString(Tools.jDParams(yewu));
			Rs_Mess(jd_order_num, wh_order_num, rString, response);
			return null;
		}
		// 京东运营商不处理 || 地市为 全国
		if ("3".equals(prepare.get("mutCode"))
				|| "99".equals(prepare.get("areaCode"))) {
			yewu.put("isSuccess", "F");
			yewu.put("errorCode", "JDI_00003");
			String rString = JSON.toJSONString(Tools.jDParams(yewu));
			Rs_Mess(jd_order_num, wh_order_num, rString, response);
			return null;
		}
		//判断 运营商类型，地市 是否存在
		if(!mutCode_map.containsKey(prepare.get("mutCode")) || !areaCode_map.containsKey(prepare.get("areaCode"))){
			yewu.put("isSuccess", "F");
			yewu.put("errorCode", "JDI_00003");
			String rString = JSON.toJSONString(Tools.jDParams(yewu));
			Rs_Mess(jd_order_num, wh_order_num, rString, response);
			return null;
		}

		// 万恒订单号
		wh_order_num = Tools.getNow3()
				+ ((int) (Math.random() * 1000) + 1000)
				+ ((char) (new Random().nextInt(26) + (int) 'A'))
				+ ((int) (Math.random() * 1000) + 1000)
				+ ((char) (new Random().nextInt(26) + (int) 'a')) + "JD";
		
		// 组装消息
		JdOrderBean bean = new JdOrderBean(prepare.get("fillOrderNo"), prepare
				.get("fillMobile"), prepare.get("fillAmount"), prepare
				.get("fillType"), prepare.get("finTime"), prepare
				.get("areaUsed"), prepare.get("notifyUrl"),
				Jd_Flows.mutCode_map.get(prepare.get("mutCode")),
				Jd_Flows.areaCode_map.get(prepare.get("areaCode")), prepare
				.get("costPrice"), wh_order_num, userno);
		if (!PortalSend.getInstance().Send_JD_Message(bean)) {// 系统错误
			Log.info("接收京东流量充值订单，放入消息列队失败，京东订单号："+prepare.get("fillOrderNo"));
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
	 *  京东回调
	 * @param fileOrderNum 京东订单号
	 * @param wh_order_num 万恒订单
	 * @param fillAmount 面值
	 * @param updateOrderTime 更新订单时间
	 * @param notityUrl 回调地址
	 * @param rs_code 1成功 2失败
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
		Log.info("京东订单回调响应,京东订单号:" + fileOrderNum + ",万恒订单号:"+ wh_order_num + ",http回调请求url:" + buffer.toString());
		
		PostMethod postMt = new PostMethod(buffer.toString());
		try {
			postMt.setRequestHeader("Content-Type",
					"application/xml;charset=UTF-8");
			int st = client.executeMethod(postMt);
			if (st == 200) {
				String rs = postMt.getResponseBodyAsString();
				Log.info("京东订单回调响应,京东订单号:" + fileOrderNum + ",万恒订单号:"
						+ wh_order_num + ",响应内容:" + rs);
			}
		} catch (Exception e) {
			Log.error("京东订单回调,,系统异常,京东订单号:" + fileOrderNum + ",万恒订单号:"+ wh_order_num + ",,ex:"+e);
		} finally {
			postMt.releaseConnection();
		}
		return true;
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
		Log.info("京东流量充值,京东订单号:" + jd_order_num + ",万恒订单号:" + wh_Order_num
				+ ",,http响应内容:" + str);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(str);
			out.flush();
			out.close();
		} catch (Exception e) {
			Log.error("京东流量充值,京东订单号:" + jd_order_num + ",万恒订单号:" + wh_Order_num
					+ ",,http响应异常:" + str + ",,,ex:" + e);
		}
	}
}
