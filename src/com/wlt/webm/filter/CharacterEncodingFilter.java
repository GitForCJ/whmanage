package com.wlt.webm.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ����ҳ��post��ֵ���ַ�����<br>
 */
public class CharacterEncodingFilter implements Filter
{
    /**
     * �ַ���
     */
    private String encoding;

    /**
     * �Ƿ���Ըù�����
     */
    private boolean ignore = false;
    
    /**
	 * �����Ե�url
	 */
	private List urlList = new ArrayList();

    /**
     * ��ʼ��������
     * @param filterConfig ���������ö���
     * @throws ServletException
     */
    public void init(FilterConfig filterConfig) throws ServletException
    {
        this.encoding = filterConfig.getInitParameter("encoding");
        String ignoreValue = filterConfig.getInitParameter("ignore");
        //ֻ�е� ignoreValue Ϊ"true"ʱ������ ignore = true��Ϊ����ʱ ignore = false;
        this.ignore = ignoreValue != null && ignoreValue.equalsIgnoreCase("true");
        
        urlList.add("/AlipayBack.do");//֧�����ص�
    }

    /**
     * ִ�й��������壬����post�ύ�����ַ���
     * @param request �û��������
     * @param response �������Ӧ����
     * @param chain ��������
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
    	HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
    	HttpSession session = httpRequest.getSession();
		String url = httpRequest.getRequestURI();
		if (httpRequest.getQueryString() != null) {
			url += "?" + httpRequest.getQueryString();
		}
        if (!ignore)
        {
        	if(validateUrl(url)){
				chain.doFilter(request, response);// ִ�й�����������һ��������
				return;
			}
            request.setCharacterEncoding(encoding);
        }
        chain.doFilter(request, response);// ִ�й�����������һ��������
    }

    /**
     * ���ٹ�����
     */
    public void destroy()
    {
        encoding = null;
    }

    /**
	 * ��֤�����URL�Ƿ񱻺�����֤Session
	 * 
	 * @param url
	 *            �����URL
	 * @return true��������֤Session��
	 */
	private boolean validateUrl(String url) {
		if (url.charAt(url.length() - 1) == '/') {
			return true;
		}

		for (int i = 0, ii = urlList.size(); i < ii; i++) {
			String ignoreUrl = (String) urlList.get(i);
			if (url.lastIndexOf(ignoreUrl) != -1) {
				return true;
			}
		}
		return false;
	}
}
