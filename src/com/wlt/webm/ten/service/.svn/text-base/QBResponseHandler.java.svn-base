package com.wlt.webm.ten.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.util.TenpayUtil;

/**
 * 财付通即时到帐应答处理类<br>
 */
public class QBResponseHandler {

	/** 密钥 */
	private String key;

	/** 应答的参数 */
	private SortedMap parameters;

	/** debug信息 */
	private String debugInfo;

	/** request */
	private HttpServletRequest request;

	/** response */
	private HttpServletResponse response;

	/** uriEncoding */
	private String uriEncoding;

	/**
	 * 构造函数
	 * 
	 * @param request
	 * @param response
	 */
	public QBResponseHandler(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;

		this.key = "";
		this.parameters = new TreeMap();
		this.debugInfo = "";

		this.uriEncoding = "";

		Map m = this.request.getParameterMap();
		Iterator it = m.keySet().iterator();
		while (it.hasNext()) {
			String k = (String) it.next();
			String v = ((String[]) m.get(k))[0];
			this.setParameter(k, v);
		}

	}

	/**
	 *获取密钥
	 * 
	 * @return key
	 */
	public String getKey() {
		return key;
	}

	/**
	 *设置密钥
	 * 
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 获取参数值
	 * 
	 * @param parameter
	 *            参数名称
	 * @return String
	 */
	public String getParameter(String parameter) {
		String s = (String) this.parameters.get(parameter);
		return (null == s) ? "" : s;
	}

	/**
	 * 设置参数值
	 * 
	 * @param parameter
	 *            参数名称
	 * @param parameterValue
	 *            参数值
	 */
	public void setParameter(String parameter, String parameterValue) {
		String v = "";
		if (null != parameterValue) {
			v = parameterValue.trim();
		}
		this.parameters.put(parameter, v);
	}

	/**
	 * 返回所有的参数
	 * 
	 * @return SortedMap
	 */
	public SortedMap getAllParameters() {
		return this.parameters;
	}

	/**
	 * 是否财付通签名,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 * 
	 * @return boolean
	 */
	public boolean isTenpaySign() {
		StringBuffer sb = new StringBuffer();
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}

		sb.append("key=" + this.getKey());

		// 算出摘要
		String enc = TenpayUtil.getCharacterEncoding(this.request,
				this.response);
		String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();

		String tenpaySign = this.getParameter("sign").toLowerCase();

		// debug信息
		this.setDebugInfo(sb.toString() + " => sign:" + sign + " tenpaySign:"
				+ tenpaySign);

		return tenpaySign.equals(sign);
	}

	/**
	 * 显示处理结果。
	 * 
	 * @param show_url
	 *            显示处理结果的url地址,绝对url地址的形式(http://www.xxx.com/xxx.jsp)
	 * @throws IOException
	 */
	public void doShow(String show_url) throws IOException {
		Log.info("excute  doShow.....");
		String strHtml = "<html><head>\r\n"
				+ "<script language=\"javascript\">\r\n"
				+ "window.location.href='" + show_url + "';\r\n"
				+ "</script>\r\n" + "</head><body></body></html>";
		PrintWriter out = this.getHttpServletResponse().getWriter();
		out.println(strHtml);
		out.flush();
		out.close();
		Log.info("doShow。close");
	}

	/**
	 * 0 成功 1 数字签名错误（检查密钥是否正确、md5加密是否正确） 2 订单重复提交 3 用户帐号不存在 4
	 * 系统错误(指的是非在线卡支付逻辑的所有错误)。如果出现该错误，最多重复尝试充值3次，如果错误依旧，建议放弃充值或人工干预处理。
	 *  5 IP错误 6
	 * 用户key错误 7 参数错误 8 库存不足 9 用户状态异常 10 订单超时 101 此功能暂时不可用 102 该商业号权限不足 103
	 * 系统维护中
	 */
	public void doShowMessage(int type ) throws IOException {
		Log.info("excute  doShowMessage.....");
		String message = "";
		switch (type) {
		case 0:
			message = "充值成功";
			break;
		case 1:
			message = "数字签名错误";
			break;
		case 2:
			message = "订单重复提交";
			break;
		case 3:
			message = "用户帐号不存在";
			break;
		case 4:
			message = "系统错误";
			break;
		case 5:
			message = "IP错误";
			break;
		case 6:
			message = "用户key错误";
			break;
		case 7:
			message = "参数错误";
			break;
		case 8:
			message = "库存不足";
			break;
		case 9:
			message ="用户状态异常";
			break;
		case 10:
			message = "订单处理中";
			break;
		case 101:
			message = "此功能暂时不可用";
			break;
		case 102:
			message = "该商业号权限不足";
			break;
		case 103:
			message = "系统维护中";
			break;
		default:
			 message ="未知错误";
		     break;
		}
		request.setAttribute("mess", message);
		Log.info("doShowMessage finish");
	}

	/**
	 * 获取uri编码
	 * 
	 * @return String
	 */
	public String getUriEncoding() {
		return uriEncoding;
	}

	/**
	 * 设置uri编码
	 * 
	 * @param uriEncoding
	 * @throws UnsupportedEncodingException
	 */
	public void setUriEncoding(String uriEncoding)
			throws UnsupportedEncodingException {
		if (!"".equals(uriEncoding.trim())) {
			this.uriEncoding = uriEncoding;

			// 编码转换
			String enc = TenpayUtil.getCharacterEncoding(request, response);
			Iterator it = this.parameters.keySet().iterator();
			while (it.hasNext()) {
				String k = (String) it.next();
				String v = this.getParameter(k);
				v = new String(v.getBytes(uriEncoding.trim()), enc);
				this.setParameter(k, v);
			}
		}
	}

	/**
	 *获取debug信息
	 * 
	 * @return debugInfo
	 */
	public String getDebugInfo() {
		return debugInfo;
	}

	/**
	 *设置debug信息
	 * 
	 * @param debugInfo
	 */
	protected void setDebugInfo(String debugInfo) {
		this.debugInfo = debugInfo;
	}

	/**
	 * @return request
	 */
	protected HttpServletRequest getHttpServletRequest() {
		return this.request;
	}

	/**
	 * @return response
	 */
	protected HttpServletResponse getHttpServletResponse() {
		return this.response;
	}

	/**
	 * 是否财付通签名
	 * 
	 * @param signParameterArray
	 *            签名的参数数组
	 * @return boolean
	 */
	protected boolean isTenpaySign(String signParameterArray[]) {

		StringBuffer signPars = new StringBuffer();
		for (int index = 0; index < signParameterArray.length; index++) {
			String k = signParameterArray[index];
			String v = this.getParameter(k);
			if (null != v && !"".equals(v)) {
				signPars.append(k + "=" + v + "&");
			}
		}

		signPars.append("key=" + this.getKey());

		String enc = TenpayUtil.getCharacterEncoding(this
				.getHttpServletRequest(), this.getHttpServletResponse());
		// 算出摘要
		String sign = MD5Util.MD5Encode(signPars.toString(), enc).toLowerCase();

		String tenpaySign = this.getParameter("sign").toLowerCase();

		// debug信息
		this.setDebugInfo(signPars.toString() + " => sign:" + sign
				+ " tenpaySign:" + tenpaySign);

		return tenpaySign.equals(sign);
	}

}
