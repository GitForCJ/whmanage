package com.wlt.webm.ten.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.ten.bean.LogInfoBean;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.TenpayUtil;

/**
 * 财付通即时到帐请求处理类<br>
 */
public class RequestHandler {
	
	/** 网关url地址 */
	private String gateUrl;
	
	/** 密钥 */
	private String key;
	
	/** 请求的参数 */
	private SortedMap parameters;
	
	/** debug信息 */
	private String debugInfo;
	
	/** request */
	private HttpServletRequest request;
	/** response */
	private HttpServletResponse response;
	
	/**
	 * 
	 */
	public RequestHandler(){
		
	}
	/**
	 * 构造函数
	 * @param request
	 * @param response
	 */
	public RequestHandler(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		
		this.gateUrl = "https://www.tenpay.com/cgi-bin/v1.0/pay_gate.cgi";
		this.key = "";
		this.parameters = new TreeMap();
		this.debugInfo = "";
	}
	
	/**
	*初始化函数。
	*/
	public void init() {
		//nothing to do
	}

	/**
	*获取入口地址,不包含参数值
	 * @return gateUrl
	*/
	public String getGateUrl() {
		return gateUrl;
	}

	/**
	*设置入口地址,不包含参数值
	 * @param gateUrl 
	*/
	public void setGateUrl(String gateUrl) {
		this.gateUrl = gateUrl;
	}

	/**
	*获取密钥
	 * @return key
	*/
	public String getKey() {
		return key;
	}

	/**
	*设置密钥
	 * @param key 
	*/
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * 获取参数值
	 * @param parameter 参数名称
	 * @return String 
	 */
	public String getParameter(String parameter) {
		String s = (String)this.parameters.get(parameter); 
		return (null == s) ? "" : s;
	}
	
	/**
	 * 设置参数值
	 * @param parameter 参数名称
	 * @param parameterValue 参数值
	 */
	public void setParameter(String parameter, String parameterValue) {
		String v = "";
		if(null != parameterValue) {
			v = parameterValue.trim();
		}
		this.parameters.put(parameter, v);
	}
	
	/**
	 * 返回所有的参数
	 * @return SortedMap
	 */
	public SortedMap getAllParameters() {		
		return this.parameters;
	}

	/**
	*获取debug信息
	 * @return debugInfo
	*/
	public String getDebugInfo() {
		return debugInfo;
	}
	
	/**
	 * 获取带参数的请求URL
	 * @return String
	 * @throws UnsupportedEncodingException 
	 */
	public String getRequestURL() throws UnsupportedEncodingException {
		
		this.createSign();
		
		StringBuffer sb = new StringBuffer();
		String enc = TenpayUtil.getCharacterEncoding(this.request, this.response);
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			sb.append(k + "=" + URLEncoder.encode(v, enc) + "&");
		}
		
		//去掉最后一个&
		String reqPars = sb.substring(0, sb.lastIndexOf("&"));
		
		return this.getGateUrl() + "?" + reqPars;
		
	}
	
	/**
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public void doSend() throws UnsupportedEncodingException, IOException {
		this.response.sendRedirect(this.getRequestURL());
	}
	
	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 * 
	 */
	protected void createSign() {
		StringBuffer sb = new StringBuffer();
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if(null != v && !"".equals(v) 
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + this.getKey());
		
		String enc = TenpayUtil.getCharacterEncoding(this.request, this.response);
		String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();
		
		this.setParameter("sign", sign);
		
		//debug信息
		this.setDebugInfo(sb.toString() + " => sign:" + sign);
		
	}
	
	/**
	 * @param bargainor_id
	 * @param transaction_id
	 * @param clientIP
	 * @param pay_fee
	 * @param currentTime
	 */
		public void logTransferToDB(String bargainor_id, String transaction_id,
	    String clientIP, String pay_fee, String currentTime){
			SysUserForm userForm = (SysUserForm)this.request.getSession().getAttribute("userSession");
		//获得流水号
		String sp_bill=Tools.get16SerialNumber();
		this.setParameter("sp_billno", sp_bill);
		LogInfoBean  logInfo =new LogInfoBean(bargainor_id,transaction_id,sp_bill, clientIP,pay_fee,currentTime);
		logInfo.Log(userForm,16);

	}
	
	
	/**
	*设置debug信息
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

	 
}
