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
 * �Ƹ�ͨ��ʱ������������<br>
 */
public class QBRequestHandler {
	
	/** ����url��ַ */
	private String gateUrl;
	
	/** ��Կ */
	private String key;
	
	/** ����Ĳ��� */
	private SortedMap parameters;
	
	/** debug��Ϣ */
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
	 * ���캯��
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
	*��ʼ��������
	*/
	public void init() {
		//nothing to do
	}

	/**
	*��ȡ��ڵ�ַ,����������ֵ
	 * @return gateUrl
	*/
	public String getGateUrl() {
		return gateUrl;
	}

	/**
	*������ڵ�ַ,����������ֵ
	 * @param gateUrl 
	*/
	public void setGateUrl(String gateUrl) {
		this.gateUrl = gateUrl;
	}

	/**
	*��ȡ��Կ
	 * @return key
	*/
	public String getKey() {
		return key;
	}

	/**
	*������Կ
	 * @param key 
	*/
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * ��ȡ����ֵ
	 * @param parameter ��������
	 * @return String 
	 */
	public String getParameter(String parameter) {
		String s = (String)this.parameters.get(parameter); 
		return (null == s) ? "" : s;
	}
	
	/**
	 * ���ò���ֵ
	 * @param parameter ��������
	 * @param parameterValue ����ֵ
	 */
	public void setParameter(String parameter, String parameterValue) {
		String v = "";
		if(null != parameterValue) {
			v = parameterValue.trim();
		}
		this.parameters.put(parameter, v);
	}
	
	/**
	 * �������еĲ���
	 * @return SortedMap
	 */
	public SortedMap getAllParameters() {		
		return this.parameters;
	}

	/**
	*��ȡdebug��Ϣ
	 * @return debugInfo
	*/
	public String getDebugInfo() {
		return debugInfo;
	}
	
	/**
	 * ��ȡ������������URL
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
		
		//ȥ�����һ��&
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
	 * ����md5ժҪ,������:����������a-z����,������ֵ�Ĳ������μ�ǩ����
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
		//�����ˮ��
		String tradeNum=Tools.get16SerialNumber();
		//�ն˱��
		QBLogInfoBean  logInfo =new QBLogInfoBean(bargainor_id,tradeDate,tran_seq, in_acct,num,tradetime,tradeNum,resourceid,tol);
		return logInfo.Log(userForm,"Q0001");//��ʾQ��

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
		//�����ˮ��
		String tradeNum=Tools.get16SerialNumber();
		//�ն˱��
		QBLogInfoBean  logInfo =new QBLogInfoBean(bargainor_id,tradeDate,tran_seq, in_acct,num,tradetime,tradeNum,resourceid,tol);
		logInfo.Log(userForm,"Q0001");//��ʾQ��

	}
	
	/**
	*����debug��Ϣ
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
