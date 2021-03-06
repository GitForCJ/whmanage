package com.wlt.webm.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wlt.webm.rights.form.SysUserForm;

/**
 * 验证用户Session的过滤器<br>
 * company 深圳市万恒科技有限公司<br>
 * copyright Copyright (c) 2008<br>
 * version 3.0.0.0
 * 
 * @author 鹿振
 */
public class UserSessionValidateFilter implements Filter {
	/**
	 * 是否忽略该过滤器
	 */
	private boolean ignore = false;

	/**
	 * 被忽略的url
	 */
	private List urlList = new ArrayList();
	
	/**
	 * 忽略 widow.showModalDialog 页面
	 */
	private String arryStr="";

	/**
	 * 初始化过滤器
	 * 
	 * @param filterConfig
	 *            过滤器配置对象
	 * @throws ServletException
	 */
	@SuppressWarnings("unchecked")
	public void init(FilterConfig filterConfig) throws ServletException {
		String ignoreValue = filterConfig.getInitParameter("ignore");
		// 只有当 ignoreValue 为"true"时，设置 ignore = true，为其他时 ignore = false;
		this.ignore = ignoreValue != null
				&& ignoreValue.equalsIgnoreCase("true");

		urlList.add("/index.jsp");
		urlList.add("/rights/password.jsp");
		urlList.add("/rights/passwordTwo.jsp");
		urlList.add("/AccountInfo/showAccountInfo.do");
		urlList.add("/AccountPay.do");
		urlList.add("/rights/wltlogin.jsp");
		urlList.add("/rights/sysuser.do");
		urlList.add("/oufei/rcharge.do");
		urlList.add("/jiebei/rcharge.do");
		urlList.add("errmsg.jsp");
		urlList.add("blank.jsp");
		urlList.add("/rights/wltregist.jsp");
		urlList.add("/rights/wltwelcome.jsp");
		urlList.add("/qb/qbResult.do");
		urlList.add("/ten/tenpay.do");
		urlList.add("/qb/fill.do");
		urlList.add("/qb/query.do");
		urlList.add("/business/jtfk.do");
		urlList.add("/wlttencent/qbpay.jsp");
		urlList.add("/wlttencent/tenpaytransfersuccess.jsp");
		urlList.add("/wlttencent/qbtransfertimeout.jsp");
		urlList.add("/wlttencent/qbtransfersuccess.jsp");
		urlList.add("/wlttencent/qqtransfer1.jsp");
		urlList.add("/wlttencent/qqtransfer2.jsp");
		urlList.add("/dianxin/telecom.do");
		urlList.add("/business/bank.do");
		urlList.add("/rights/adminlogin.jsp");
		urlList.add("/rights/chongzhimima.jsp");
		urlList.add("/rights/wltadminlogin.jsp");
		//新增
		urlList.add("/tpcharge.do");
		urlList.add("/OrderQueryInter/OrderQueryInter.do");
		urlList.add("/typortinfo.do");
		urlList.add("/car.do");
		urlList.add("/Callback.do");
		urlList.add("/flow.do");
		urlList.add("/AccountChecking/AccountChecking.do");
		urlList.add("/cars.do");
		urlList.add("/flowback.do");//思空流量回调
		urlList.add("/PPOrderPay.do");//腾讯流量充值
		urlList.add("/PPOrdercheck.do");//腾讯流量查询
		urlList.add("/HeBaoBack.do");//和包移动回调
		urlList.add("/LiuLiangFanBack.do");//流量饭回调
		urlList.add("/LiandongBack.do");//流量饭回调
		
		urlList.add("/trafficPrepareSearch.do");//京东预查询
		urlList.add("/trafficBeginFill.do");//京东流量充值
		urlList.add("/trafficFindFill.do");//京东订单查询
		urlList.add("/winksi.topUpPhone.queryMessage.do");//中大查询
		urlList.add("/winksi.topUpPhone.orderOnline.do");//中大查询
		urlList.add("/winksi.topUpPhone.queryOrder.do");//中大查询
		urlList.add("/winksi.topUpPhone.serverDown.do");//中大查询
		
		//web app services 
		urlList.add("/app.do");
		urlList.add("/apps.do");
		urlList.add("/AlipayBack.do");//支付宝回调
		urlList.add("/WxBack.do");//微信回调
		urlList.add("/BaiduBack.do");//百度回调
		urlList.add("/business/QR.jsp");
		urlList.add("/business/QR1.jsp");
		
		urlList.add("AccountInfo/okInfo.jsp");
		urlList.add("/druid/");
		urlList.add("/LiuLiangFanBack.do");
		
		//忽略的  弹出页面 window.showModalDialog
		arryStr=arryStr+"/AccountInfo/AccountDetails.jsp#";
		arryStr=arryStr+"/task/showinfo.jsp#";
		arryStr=arryStr+"/druid/index.html#";
		arryStr=arryStr+"/business/Receipt.jsp#";
		arryStr=arryStr+"/business/ReceiptTwo.jsp#";
	}

	/**
	 * 验证session过期和用户权限的过滤器
	 * 
	 * @param request
	 *            用户请求对象
	 * @param response
	 *            请求的响应对象
	 * @param chain
	 *            过滤器链
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (!ignore) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			
			@SuppressWarnings("unused")
			HttpSession session = httpRequest.getSession();
			//session.setMaxInactiveInterval(7200 * 2);
			String url = httpRequest.getRequestURI();
			if (httpRequest.getQueryString() != null) {
				url += "?" + httpRequest.getQueryString();
			}
			if(httpRequest.getSession().getAttribute("userSession")!=null)
			{
//				String conString = httpRequest.getHeader("REFERER");
//				if("".equals(conString) || conString==null )
//				{
//					if(this.arryStr.indexOf(httpRequest.getRequestURI())!=-1)
//					{
//						chain.doFilter(request, response);// 执行过滤器链的下一个过滤器
//						return;
//					}
//					if(validateUrl(url))
//					{
//						chain.doFilter(request, response);// 执行过滤器链的下一个过滤器
//						return;
//					}
//					if(httpRequest.getRequestURI()!=null && httpRequest.getRequestURI().indexOf("/index.jsp")!=-1)
//					{
//						chain.doFilter(request, response);// 执行过滤器链的下一个过滤器
//						return;
//					}
//					else
//					{
//						httpResponse.sendRedirect(httpRequest.getContextPath()+"/index.jsp");
//						return;
//					}
//				}
				chain.doFilter(request, response);// 执行过滤器链的下一个过滤器
				return;
			}
			else
			{
				if(validateUrl(url))
				{
					chain.doFilter(request, response);// 执行过滤器链的下一个过滤器
					return;
				}
				if(url.equals("/index.jsp"))
				{
					chain.doFilter(request, response);// 执行过滤器链的下一个过滤器
					return;
				}
				httpResponse.sendRedirect(httpRequest.getContextPath()+"/index.jsp");
				return;
			}
			
//			String flag = "V";
//			if (null != httpRequest.getSession().getAttribute("userSession")) {
//				SysUserForm userSession = (SysUserForm) httpRequest
//						.getSession().getAttribute("userSession");
//				flag = userSession.getRoleType();
//			}
			
//			if (!validateUrl(url)
//					|| url.contains("/rights/sysuser.do?method=list")
//					|| url.contains("/rights/sysuser.do?method=list1")) 
//			{
//				if (session.getAttribute("userSession") == null) {
//					httpResponse.sendRedirect(httpRequest.getContextPath()
//							+ "/index.jsp");
//					return;
//				}
//				String[] powerArr = (String[]) session.getAttribute("powerArr");
//				if (url.contains(".jsp")) {// ||(url.contains(
//											// "/rights/sysuser.do?method=list"
//											// )&&
//											// !"0".equals(flag)&&!"1".equals(
//											// flag))
//					if (!validatePower(url, powerArr)) {
//						httpResponse.sendRedirect(httpRequest.getContextPath()
//								+ "/error/errmsg.jsp");
//						return;
//					}
//				}
//			}
		}
//		chain.doFilter(request, response);// 执行过滤器链的下一个过滤器
	}

	/**
	 * 销毁过滤器
	 */
	public void destroy() {

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

	private boolean validatePower(String url, String[] powerArr) {
		if (null != powerArr && powerArr.length > 0) {
			for (int i = 0; i < powerArr.length; i++) {
				String tmp = powerArr[i];
				if (url.contains(tmp)) {
					return true;
				}
			}
		}
		return false;
	}
}
