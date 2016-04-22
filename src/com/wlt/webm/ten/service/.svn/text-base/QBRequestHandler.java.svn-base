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
import com.wlt.webm.ten.bean.QBLogInfoBean;
import com.wlt.webm.ten.bean.SendMsgScpBean;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.TenpayUtil;

/**
 * 财付通即时到帐请求处理类<br>
 */
public class QBRequestHandler {
	
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
	public QBRequestHandler(){
		
	}
	/**
	 * 构造函数
	 * @param request
	 * @param response
	 */
	public QBRequestHandler(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		
		this.gateUrl = "http://esales.qq.com/cgi-bin/esales_sell_dir.cgi";
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
		
	}
	
		 /**
		  * 
		  * @param bargainor_id
		  * @param tradeDate
		  * @param tran_seq
		  * @param in_acct
		  * @param num
		  * @param tradetime
		  * @param tol
		  * @param resourceid
		  * @param userForm
		  * @return  0 cg
		  */
		public int logTransferToDB(String bargainor_id, String tradeDate,String tran_seq,
	    String in_acct, int num, String tradetime ,String tol,String resourceid,SysUserForm userForm){
		//获得流水号
		String tradeNum=Tools.get16SerialNumber();
		//终端编号
		QBLogInfoBean  logInfo =new QBLogInfoBean(bargainor_id,tradeDate,tran_seq, in_acct,num,tradetime,tradeNum,resourceid,tol);
		return logInfo.Log(userForm,"Q0001");//表示Q币

	}
	
		/**
		 * 
		 * @param bargainor_id
		 * @param tradeDate
		 * @param tran_seq
		 * @param in_acct
		 * @param num
		 * @param tradetime
		 */
		public void logTransferToDBMb(String bargainor_id, String tradeDate,String tran_seq,
	    String in_acct, int num, String tradetime ,String tol,String resourceid,SysUserForm userForm){
		//获得流水号
		String tradeNum=Tools.get16SerialNumber();
		//终端编号
		QBLogInfoBean  logInfo =new QBLogInfoBean(bargainor_id,tradeDate,tran_seq, in_acct,num,tradetime,tradeNum,resourceid,tol);
		logInfo.Log(userForm,"Q0001");//表示Q币

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
	/**
	 * @return
	 */
	public SortedMap getParameters() {
		return parameters;
	}
	/**
	 * @param parameters
	 */
	public void setParameters(SortedMap parameters) {
		this.parameters = parameters;
	}

	
	 
}
