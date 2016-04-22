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
 * �Ƹ�ͨ��ʱ����Ӧ������<br>
 */
public class ResponseHandler { 
	
	/** ��Կ */
	private String key;
	
	/** Ӧ��Ĳ��� */
	private SortedMap parameters; 
	
	/** debug��Ϣ */
	private String debugInfo;
	
	/** request */
	private HttpServletRequest request;
	
	/**	response */
	private HttpServletResponse response;
	
	/** uriEncoding */
	private String uriEncoding;
	
	/**
	 * ���캯��
	 * 
	 * @param request
	 * @param response
	 */
	public ResponseHandler(HttpServletRequest request,
			HttpServletResponse response)  {
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
	 * �Ƿ�Ƹ�ͨǩ��,������:����������a-z����,������ֵ�Ĳ������μ�ǩ����
	 * @return boolean
	 */
	public boolean isTenpaySign() {
		StringBuffer sb = new StringBuffer();
		Set es = this.parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if(!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}
		
		sb.append("key=" + this.getKey());
		
		//���ժҪ
		String enc = TenpayUtil.getCharacterEncoding(this.request, this.response);
		String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();
		
		String tenpaySign = this.getParameter("sign").toLowerCase();
		
		//debug��Ϣ
		this.setDebugInfo(sb.toString() + " => sign:" + sign +
				" tenpaySign:" + tenpaySign);
		
		return tenpaySign.equals(sign);
	}
	
	/**
	 * ��ʾ��������
	 * @param show_url ��ʾ��������url��ַ,����url��ַ����ʽ(http://www.xxx.com/xxx.jsp)
	 * @throws IOException 
	 */
	public void doShow(String show_url) throws IOException {
		Log.info("excute  doShow.....");
		String strHtml = "<html><head>\r\n" +
				"<meta name=\"TENCENT_ONLINE_PAYMENT\" content=\"China TENCENT\">\r\n" +
				"<script language=\"javascript\">\r\n" +
					"window.location.href='" + show_url + "';\r\n" +
				"</script>\r\n" +
				"</head><body></body></html>";
		PrintWriter out = this.getHttpServletResponse().getWriter();
		out.println(strHtml);
		out.flush();
		out.close();
		Log.info("doShow��close");
	}
	
	/**
	 * ��ȡuri����
	 * @return String
	 */
	public String getUriEncoding() {
		return uriEncoding;
	}

	/**
	 * ����uri����
	 * @param uriEncoding
	 * @throws UnsupportedEncodingException
	 */
	public void setUriEncoding(String uriEncoding)
			throws UnsupportedEncodingException {
		if (!"".equals(uriEncoding.trim())) {
			this.uriEncoding = uriEncoding;

			// ����ת��
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
	*��ȡdebug��Ϣ
	 * @return debugInfo
	*/
	public String getDebugInfo() {
		return debugInfo;
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
	 * �Ƿ�Ƹ�ͨǩ��
	 * @param signParameterArray ǩ���Ĳ�������
	 * @return boolean
	 */
	protected boolean isTenpaySign(String signParameterArray[]) {

		StringBuffer signPars = new StringBuffer();
		for(int index = 0; index < signParameterArray.length; index++) {
			String k = signParameterArray[index];
			String v = this.getParameter(k);
			if(null != v && !"".equals(v)) {
				signPars.append(k + "=" + v + "&");
			}
		}
		
		signPars.append("key=" + this.getKey());
				
		String enc = TenpayUtil.getCharacterEncoding(
				this.getHttpServletRequest(), this.getHttpServletResponse());
		//���ժҪ
		String sign = MD5Util.MD5Encode(signPars.toString(), enc).toLowerCase();
		
		String tenpaySign = this.getParameter("sign").toLowerCase();
		
		//debug��Ϣ
		this.setDebugInfo(signPars.toString() + " => sign:" + sign +
				" tenpaySign:" + tenpaySign);
		
		return tenpaySign.equals(sign);
	}
	
}
