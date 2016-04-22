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
 * 设置页面post表单值的字符编码<br>
 */
public class CharacterEncodingFilter implements Filter
{
    /**
     * 字符集
     */
    private String encoding;

    /**
     * 是否忽略该过滤器
     */
    private boolean ignore = false;
    
    /**
	 * 被忽略的url
	 */
	private List urlList = new ArrayList();

    /**
     * 初始化过滤器
     * @param filterConfig 过滤器配置对象
     * @throws ServletException
     */
    public void init(FilterConfig filterConfig) throws ServletException
    {
        this.encoding = filterConfig.getInitParameter("encoding");
        String ignoreValue = filterConfig.getInitParameter("ignore");
        //只有当 ignoreValue 为"true"时，设置 ignore = true，为其他时 ignore = false;
        this.ignore = ignoreValue != null && ignoreValue.equalsIgnoreCase("true");
        
        urlList.add("/AlipayBack.do");//支付宝回调
    }

    /**
     * 执行过滤器主体，设置post提交表单的字符集
     * @param request 用户请求对象
     * @param response 请求的响应对象
     * @param chain 过滤器链
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
				chain.doFilter(request, response);// 执行过滤器链的下一个过滤器
				return;
			}
            request.setCharacterEncoding(encoding);
        }
        chain.doFilter(request, response);// 执行过滤器链的下一个过滤器
    }

    /**
     * 销毁过滤器
     */
    public void destroy()
    {
        encoding = null;
    }

    /**
	 * 验证请求的URL是否被忽略验证Session
	 * 
	 * @param url
	 *            请求的URL
	 * @return true，忽略验证Session。
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
