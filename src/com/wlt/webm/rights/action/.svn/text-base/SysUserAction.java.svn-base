package com.wlt.webm.rights.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import oufei.OUConstat;
import oufei.OufeiTask;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.AccountInfo.SessionControl;
import com.wlt.webm.business.MessageUtil;
import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.business.NetPayUtil;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.business.bean.SysBankLog;
import com.wlt.webm.business.bean.SysCommission;
import com.wlt.webm.business.bean.oufeiqb.OuFeiThread;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.YMmessage;
import com.wlt.webm.rights.bean.SysArea;
import com.wlt.webm.rights.bean.SysRole;
import com.wlt.webm.rights.bean.SysUser;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.tool.FloatArith;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.MD5;
import com.wlt.webm.util.PageAttribute;

/**
 * 用户管理<br>
 */
public class SysUserAction extends DispatchAction {
	/**
	 * 用户列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userForm = (SysUserForm) form;
		SysUser user = new SysUser();
		SysUserForm user1 = (SysUserForm) request.getSession().getAttribute(
		"userSession");
		PageAttribute page = new PageAttribute(userForm.getCurPage(),
				Constant.PAGE_SIZE);
		String srType = request.getParameter("st");
		if (null != srType && !"".equals(srType)) {
			userForm.setRoleType(srType);
		}
		page.setRsCount(user.countSysUser(srType, request.getSession(),
				userForm));
		List list = user.getSysUserList(srType, request.getSession(), page,
				userForm);
		SysRole role = new SysRole();
		request.setAttribute("roleSel", getStingSel(role.getRoleType(user1.getRoleType()),
				"角色类型"));
		request.setAttribute("userList", list);
		request.setAttribute("page", page);
		request.setAttribute("uform", userForm);
		request.setAttribute("st", srType);
		return mapping.findForward("listsuccess");
	}

	/**
	 * 用户列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward list1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userForm = (SysUserForm) form;
//		if(userForm.getStartDate()==null || "".equals(userForm.getStartDate()))
//		{
//			userForm.setStartDate(Tools.getNowDate1());
//		}
//		if(userForm.getEndDate()==null || "".equals(userForm.getEndDate()))
//		{
//			userForm.setEndDate(Tools.getNowDate1());
//		}
		
		SysUser user = new SysUser();
		PageAttribute page = new PageAttribute(userForm.getCurPage(),
				Constant.PAGE_SIZE);
		SysUserForm user1 = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		String sr_id = user1.getUser_id();
		String srType = request.getParameter("st");
		if (null != srType && !"".equals(srType)) {
			userForm.setRoleType(srType);
		}
		page.setRsCount(user.countSysUser(sr_id, request.getSession(),
				userForm));
		List list = user.getAgentuserList(sr_id, request.getSession(), page,
				userForm);
		SysRole role = new SysRole();
		request.setAttribute("roleSel", getStingSel(role.getRoleType(user1.getRoleType()),
				"角色类型"));
		request.setAttribute("userList", list);
		request.setAttribute("page", page);
		request.setAttribute("uform", userForm);
		request.setAttribute("st", srType);
		
		request.setAttribute("accountCount",user.SumCountSysUser(sr_id,request.getSession()));//当前代理商下级总代办点数量
		return new ActionForward("/rights/whtagentuserlist.jsp");
	}
	 /** 下级用户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward lowerUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userForm = (SysUserForm) form;
		SysUser user = new SysUser();
		PageAttribute page = new PageAttribute(userForm.getCurPage(),
				Constant.PAGE_SIZE);
		SysUserForm user1 = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		String sr_id = user1.getUser_id();
		String srType = request.getParameter("st");
		if (null != srType && !"".equals(srType)) {
			userForm.setRoleType(srType);
		}
		page.setRsCount(user.countLowerUserUser(sr_id, request.getSession(),
				userForm));
		List list = user.getLowerUserList(sr_id, request.getSession(), page,
				userForm);
		SysRole role = new SysRole();
		request.setAttribute("roleSel", getStingSel(role.getRoleType(user1.getRoleType()),
				"角色类型"));
		request.setAttribute("userList", list);
		request.setAttribute("page", page);
		request.setAttribute("uform", userForm);
		request.setAttribute("st", srType);
		return new ActionForward("/rights/whtloweruserlist.jsp");
	}
	
	 /** 代理商下级代办点
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward dailishangLowerUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userForm = (SysUserForm) form;
		SysUser user = new SysUser();
		PageAttribute page = new PageAttribute(userForm.getCurPage(),
				Constant.PAGE_SIZE);
		SysUserForm user1 = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		String sr_id = user1.getUser_id();
		String parent_id=request.getParameter("parent_id");
		userForm.setRoleType("3");
		page.setRsCount(user.countDailishangLowerUser(parent_id,
				userForm));
		List list = user.getDailishangLowerUserList(parent_id,page,
				userForm);
		request.setAttribute("userList", list);
		request.setAttribute("page", page);
		request.setAttribute("uform", userForm);
		request.setAttribute("parent_id", parent_id);
		return new ActionForward("/rights/whtdailishangloweruserlist.jsp");
	}
	/**
	 * 中石化下级用户列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward zshlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userForm = (SysUserForm) form;
		SysUser user = new SysUser();
		PageAttribute page = new PageAttribute(userForm.getCurPage(),
				Constant.PAGE_SIZE);
		SysUserForm user1 = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		String sr_id = user1.getUser_id();
		String srType = request.getParameter("st");
		if (null != srType && !"".equals(srType)) {
			userForm.setRoleType(srType);
		}
		page.setRsCount(user.countZshSysUser(sr_id, request.getSession(),
				userForm));
		List list = user.getZshAgentuserList(sr_id, request.getSession(), page,
				userForm);
		SysRole role = new SysRole();
		request.setAttribute("roleSel", getStingSel(role.getRoleType(user1.getRoleType()),
				"角色类型"));
		request.setAttribute("userList", list);
		request.setAttribute("page", page);
		request.setAttribute("uform", userForm);
		request.setAttribute("st", srType);
		return new ActionForward("/rights/zshagentuserlist.jsp");
	}
	public static String getStingSel(List list, String fix) {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("请选择" + fix + "[]");
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			sBuffer.append("|" + temp[1] + "[" + temp[0] + "]");
		}
		return sBuffer.toString();
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward sendMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			String username = request.getParameter("username");
			SysUserForm userForm = new SysUserForm();
			userForm.setUsername(username);
			SysUser user = new SysUser();
			user.login(userForm, request,null,null);
			if (null != userForm.getUsername()
					&& !"".equals(userForm.getUsername())) {
				String msgCode = NetPayUtil.getRandEight();
				String phone = userForm.getUsername();
				if (null != phone && !"".equals(phone)) {
					String content = "短信验证码:" + msgCode;
					if (YMmessage.ymSendSMS(new String[] { phone }, content)) {// MessageUtil
																				// .
																				// send
																				// (
																				// phone
																				// ,
																				// content
																				// )
																				// ;
						request.getSession().setAttribute(
								userForm.getUsername() + "=dl", msgCode);
						printWriter.print(0);
						printWriter.flush();
						printWriter.close();
					} else {
						printWriter.print(1);
						printWriter.flush();
						printWriter.close();
					}
				} else {
					printWriter.print(1);
					printWriter.flush();
					printWriter.close();
				}
			} else {
				printWriter.print(1);
				printWriter.flush();
				printWriter.close();
			}
		} catch (IOException e) {
			printWriter.print(1);
			printWriter.flush();
			printWriter.close();
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward checkName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			String username = request.getParameter("name");
			String userpassword=request.getParameter("userpassword");
			SysUserForm userForm = new SysUserForm();
			userForm.setUsername(username);
			userForm.setUserpassword(userpassword);
			SysUser user = new SysUser();
			int flag=user.isUser(username+"".trim());//判断用户是否存在
			if (flag==0) {
				printWriter.print(0);// 存在
				printWriter.flush();
				printWriter.close();
			} else if(flag==1) {
				printWriter.print(1);//不存在
				printWriter.flush();
				printWriter.close();
			}else{
				printWriter.print(-1);//异常
				printWriter.flush();
				printWriter.close();
			}
		} catch (Exception e) {
			printWriter.print(-1);//异常
			printWriter.flush();
			printWriter.close();
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用户登录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String mac=request.getParameter("mac");
		SysUserForm userForm = (SysUserForm) form;
		HttpSession session = request.getSession();
		String vercode = request.getParameter("vercode");
		String serverCode=null;
		serverCode = (String) session.getAttribute("rand");
		String str=request.getParameter("adminflag");
		if (null == serverCode || null == vercode || serverCode.equals("")
				|| vercode.equals("")) {
			request.setAttribute("mess", "验证码输入错误");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		if (!serverCode.equalsIgnoreCase(vercode)) {
			request.setAttribute("mess", "验证码输入错误");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		session.removeAttribute("rand");
//		session.setAttribute("rand", null);
		SysUser user = new SysUser();
		int state = user.login(userForm, request,str,mac);
		if (state == -10) {
			request.setAttribute("mess", "登录名不存在");
			return mapping.findForward("login_fail");
		}
		if (state == -100||state==0) {
			request.setAttribute("mess", "系统繁忙请稍后在试");
			return mapping.findForward("login_fail");
		}
		if (state == -20) {
			request.setAttribute("mess", "该账户已销户或被暂停使用,请联系管理员");
			return mapping.findForward("login_fail");
		}
		if (state == -50) {
			request.setAttribute("mess", "管理员请在管理平台进行登录");
			return mapping.findForward("login_fail");
		}
		if (state == -61) {
			request.setAttribute("mess", "获取不到 mac 地址,请使用ie浏览器登陆");
			return mapping.findForward("login_fail");
		}
		if (state == -60) {
			request.setAttribute("mess", "mac 地址错误");
			return mapping.findForward("login_fail");
		}
		if (state == -40) {
			request.setAttribute("mess", "输错密码次数达到10次,账户将被冻结6小时");
			return mapping.findForward("login_fail");
		}
		if (state >= -10 && state <= -1) {
			request.setAttribute("mess", "密码错误,6小时内剩余尝试次数" + ((-state) - 1));
			return mapping.findForward("login_fail");
		}
		session.setAttribute("userSession", userForm);
//		ServletContext application=session.getServletContext();
//		application.setAttribute(userForm.getUser_id()+"-"+session.getId(), session);
		
		//不是管理员，超级管理员，，同一个账号，加入限制多处同时登陆
		if(session!=null && userForm.getUsername()!=null && !"".equals(userForm.getUsername()) && !"1".equals(userForm.getRoleType()) && !"0".equals(userForm.getRoleType()))
		{
			if(SessionControl.getControl().maps.containsKey(userForm.getUsername()))
			{
				HttpSession upSession=(HttpSession)SessionControl.getControl().maps.get(userForm.getUsername());
				if(upSession!=null && upSession.getId()!=session.getId())
				{
					try{
						upSession.invalidate();
					}catch(Exception e){
						Log.error("系统异常!");
					}
					upSession=null;
				}
				SessionControl.getControl().maps.remove(userForm.getUsername());
			}
			SessionControl.getControl().maps.put(userForm.getUsername(), session);
		}
		
		// 查询用户权限
		SysRole sRole = new SysRole();
		List menuList = sRole.getMenuByRole(userForm.getUser_role());
		String menuStr = "";
		if (null != menuList && menuList.size() > 0) {
			for (int i = 0; i < menuList.size(); i++) {
				menuStr += ((String[]) menuList.get(i))[0] + "|";
			}
		}
		if (!"".equals(menuStr)) {
			String[] powerArr = menuStr.substring(0, menuStr.length() - 1)
					.split("\\|");
			session.setAttribute("powerArr", powerArr);
		}
		// String posid=userForm.getUser_id();
		// request.setAttribute("useleft", user.getUseLeft(posid));
		// 激活
		if (!"0".equals(userForm.getUser_activate())) {
			request.setAttribute("phonenum", userForm.getPhone().substring(
					0, 3)
					+ "***" + (userForm.getPhone().length()>8?userForm.getPhone().substring(8):""));
			return new ActionForward("/business/phoneValidata.jsp");
		}
		if (null != userForm.getShortflag()
				&& !"".equals(userForm.getShortflag())
				&& Integer.parseInt(userForm.getShortflag()) == 2) {//手机令牌
			
			request.setAttribute("phonenum", userForm.getPhone().substring(
					0, 3)+ "***" + (userForm.getPhone().length()>8?userForm.getPhone().substring(8):""));
			return new ActionForward("/business/deluphoneValidata.jsp");
		}
		
		if (null != userForm.getShortflag()
				&& !"".equals(userForm.getShortflag())
				&& Integer.parseInt(userForm.getShortflag()) == 1) {//短信验证
			
			request.setAttribute("phonenum", userForm.getPhone().substring(
					0, 3)+ "***" + (userForm.getPhone().length()>8?userForm.getPhone().substring(8):""));
			return new ActionForward("/business/phonemsgValidata.jsp");
		}
		//
		return mapping.findForward("login_success");
	}

	/**
	 * 用户退出
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		SysUserForm loginUser = (SysUserForm) request.getSession().getAttribute("userSession");
		if (null == loginUser) {
			return new ActionForward("/rights/wltlogin.jsp");
		}
		String userName=loginUser.getUsername();
		session.removeAttribute("userSession");
		session.removeAttribute("powerArr");
		session.invalidate();
		session=null;
		if(SessionControl.getControl().maps.containsKey(userName)){
			SessionControl.getControl().maps.remove(userName);
		}
		return mapping.findForward("login_out");
	}

	/**
	 * 用户添加
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userForm = (SysUserForm) form;
		if (null == userForm.getUsername()
				|| null == userForm.getUserpassword()
				|| null == userForm.getDealpass()
				|| null == userForm.getWltarea()
				|| null == userForm.getUser_site_city()
				|| null == userForm.getUser_role()
				|| null==userForm.getPhone()) {
			request.setAttribute("mess", "所填信息不完整");
			return new ActionForward("/rights/wltuseradd.jsp");
		}
		SysUser user = new SysUser();
//		//说明添加的是管理员
//		if(userForm.getUser_site_city().trim().equals("")){
//			String userRole=userForm.getUser_role();
//			String cityId=user.findRoleCity(userRole);// 根据角色查询所属市级ID
//			userForm.setUser_site_city(cityId);
//		}
		
		SysUserForm userSession = (SysUserForm) request.getSession()
				.getAttribute("userSession");
//		if(userSession.getRoleType().equals("1")){
//			userForm.setUser_site_city(userSession.getUser_site_city());
//			userForm.setUsersite(userSession.getUsersite());
//		}
		userForm.setParent_id(userSession.getUser_id());
		request.setAttribute("mess", user.add(userForm) == 0 ? "添加成功" : "添加失败");
		return new ActionForward("/rights/wltuseradd.jsp");
	}

	/**
	 * 一级代理添加用户
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward addOneAgentUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userForm = (SysUserForm) form;
		if (null == userForm.getUsername()
				|| null == userForm.getUserpassword()
				|| null == userForm.getDealpass()
				|| null == userForm.getWltarea()
				|| null == userForm.getUser_site_city()
				|| null == userForm.getUser_role()) {
			request.setAttribute("mess", "所填信息不完整");
			return new ActionForward("/rights/wltuseraddoneagent.jsp");
		}
		SysUser user = new SysUser();
//		//说明添加的是管理员
//		if(userForm.getUser_site_city().trim().equals("")){
//			String userRole=userForm.getUser_role();
//			String cityId=user.findRoleCity(userRole);// 根据角色查询所属市级ID
//			userForm.setUser_site_city(cityId);
//		}
		
		SysUserForm userSession = (SysUserForm) request.getSession()
				.getAttribute("userSession");
//		if(userSession.getRoleType().equals("1")){
//			userForm.setUser_site_city(userSession.getUser_site_city());
//			userForm.setUsersite(userSession.getUsersite());
//		}
		userForm.setParent_id(userSession.getUser_id());
		request.setAttribute("mess", user.add(userForm) == 0 ? "添加成功" : "添加失败");
		return new ActionForward("/rights/wltuseraddoneagent.jsp");
	}
	/**
	 * 用户注册
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward regist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userForm = (SysUserForm) form;
		if (null == userForm.getUser_role()
				|| "".equals(userForm.getUser_role())) {
			request.setAttribute("mess", "该省份暂未开放");
			return new ActionForward("/rights/wltregist.jsp");
		}
		userForm.setUser_id("0");
		SysUser user = new SysUser();
		int uid = user.add(userForm);
		if (100 == uid) {
			request.setAttribute("mess", "系统繁忙请稍后注册");
			return new ActionForward("/rights/wltregist.jsp");
		}
		return new ActionForward("/rights/wltuserupdate_2.jsp?uid=" + uid);
	}

	/**
	 * 更新用户信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm sysUserLogin = (SysUserForm) request.getSession().getAttribute("userSession");
		String pwd=request.getParameter("tradepassword");
    	if(null==pwd||!MD5.encode(pwd).equals(sysUserLogin.getDealpass())){
    		request.setAttribute("mess", "交易密码错误!");
			return new ActionForward("/rights/wltusermodify.jsp");
    	}
		SysUserForm userForm = (SysUserForm) form;
		userForm.setUserno(request.getParameter("userno"));
		String redirectUrl = request.getParameter("rdurl");
		SysUser user = new SysUser();
		user.update(userForm);
		request.setAttribute("mess", "操作成功");
		return new ActionForward("/rights/wltusermodify.jsp");
//		return new ActionForward("/rights/wltuserlist.jsp");
		// return new ActionForward("/rights/wltuseradd.jsp");
	}

	/**
	 * 添加慧付款信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward addhfk(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userForm = (SysUserForm) form;
		SysUser user = new SysUser();
		int state = user.addHfkUser(userForm);
		if (state == -1) {
			request.setAttribute("msg", "添加失败，该手机号码已录入，请重新添加！");
			return new ActionForward("/rights/wlthfkbangding.jsp");
		} else if (state == -2) {
			request.setAttribute("msg", "用户编号不存在，请重新添加！");
			return new ActionForward("/rights/wlthfkbangding.jsp");
		} else if (state == -3) {
			request.setAttribute("msg", "资金账号不存在，请重新添加！");
			return new ActionForward("/rights/wlthfkbangding.jsp");
		} else if (state == -4) {
			request.setAttribute("msg", "用户编号和资金账号不匹配，请重新添加！");
			return new ActionForward("/rights/wlthfkbangding.jsp");
		} else if (state == -5) {
			request.setAttribute("msg", "系统繁忙稍后再试！");
			return new ActionForward("/rights/wlthfkbangding.jsp");
		}
		request.setAttribute("msg", "操作成功");
		return new ActionForward("/rights/wlthfkbangding.jsp");
	}

	/**
	 * 绑定卡拉卡 万汇通平台账号
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addLakala(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String lakalaNum=request.getParameter("lakalaNum");
		String userno=request.getParameter("userno");
		String userfee=request.getParameter("userfee");
		String userLogin=request.getParameter("userlogin");
		if(userno==null || "".equals(userno) ||
				userfee==null || "".equals(userfee) ||
					lakalaNum==null || "".equals(lakalaNum) ||
						userLogin==null || "".equals(userLogin))
		{
			request.setAttribute("mess", "缺少参数!");
			return new ActionForward("/rights/lakala.jsp");
		}
		DBService db = null;
		try {
			db = new DBService();
			String bool=db.getString("SELECT 1 FROM sys_lkal WHERE termid='"+lakalaNum+"'",null);
			if(bool!=null && !"".equals(bool))
			{
				request.setAttribute("mess", "拉卡拉终端编号已绑定，不能重复绑定!");
				return new ActionForward("/rights/lakala.jsp");
			}
			String flag=db.getString("SELECT 1 FROM sys_user u,wht_facct f  WHERE u.user_no=f.user_no AND f.fundacct='"+userfee+"' AND u.user_login='"+userLogin+"' AND u.user_no='"+userno+"' ",null);
			if(flag==null || "".equals(flag))
			{
				request.setAttribute("mess", "请输入正确的万汇通系统编号,资金账号,登录账号！");
				return new ActionForward("/rights/lakala.jsp");
			}
			
			String sql="INSERT INTO sys_lkal(termid,userno,fundacct,userlogin,TIME,TYPE)  VALUES('"+lakalaNum+"','"+userno+"','"+userfee+"','"+userLogin+"','"+Tools.getNow3()+"',0)";
			if(db.update(sql,null)>0)
			{
				request.setAttribute("mess", "绑定成功！");
				return new ActionForward("/rights/lakala.jsp");
			}
			else
			{
				request.setAttribute("mess", "绑定失败！");
				return new ActionForward("/rights/lakala.jsp");
			}
		} catch (Exception e) {
			Log.error("拉卡拉绑定异常，，，，"+e);
			request.setAttribute("mess", "系统异常");
			return new ActionForward("/rights/lakala.jsp");
		} finally {
			if (null != db)
				db.close();
		}
		
	}
	/**
	 * 更新用户信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward registNext(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userForm = (SysUserForm) form;
		SysUser user = new SysUser();
		user.update(userForm);
		request.setAttribute("msg", "注册成功");
		return new ActionForward("/rights/wltlogin.jsp");
	}

	/**
	 * 更新资金账户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward updateAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userForm = (SysUserForm) form;
		SysUser user = new SysUser();
		user.updateAccount(userForm);
		return mapping.findForward("updatesuccess");
	}

	/**
	 * 资金账户充值
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward charge(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fee = request.getParameter("chargeFee");
		String orderid=request.getParameter("orderid");
		String userno = request.getParameter("user_id");
		
		/**
		 * 操作人信息
		 */
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute("userSession");
		String optenation_userno=userForm.getUserno();
		
		SysUser user = new SysUser();
		String sepNo = Tools.getSeqNo("");
		String nowTime = Tools.getNow();
		String mon = FloatArith.yun2li(fee) + "";
		String op = request.getParameter("op");
		int rs = user.charge(userno, mon, sepNo, nowTime, op,orderid,optenation_userno);
		// 0成功 1 异常系统繁忙 2 未查询到相应账户 3 余额不足
		String msg = "失败:";
		if (rs == 0) {
			msg = "操作成功,等待系统审批";
		} else if (rs == 1) {
			msg += "系统繁忙,请稍后再试";
		} else if (rs == 2) {
			msg += "未查询到相应账户";
		} else if (rs == 3) {
			msg += "余额不足";
		}else if(rs==4){
			msg+="订单号不存在或已自动处理";
		}
		String redUrl = request.getParameter("redUrl");
		request.setAttribute("mess", msg);
		return new ActionForward(redUrl);
	}
	
	/**
	 * 审核结果
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	public ActionForward Audit_operation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute("userSession");
		if(!"0".equals(userForm.getRoleType())){
			request.setAttribute("mess","您没有操作权限!");
			return new ActionForward("/business/operation_account_audit.jsp");
		}
		String ids=request.getParameter("ids");
		String op=request.getParameter("op");
		if(ids==null || "".equals(ids.trim()) || (!"0".equals(op) && !"-1".equals(op))){
			request.setAttribute("mess","系统错误,参数异常1!");
			return Account_operation(mapping,form,request,response);
		}
		DBService db=null;
		try {
			db=new DBService();
			String[] strs=db.getStringArray("SELECT openation_userno,openation_type,openation_fee,be_openation_userno,be_openation_childfacct,openation_state FROM wht_Account_operation_admin WHERE id="+ids);
			if(strs==null){
				request.setAttribute("mess","系统错误,参数异常2!");
				return Account_operation(mapping,form,request,response);
			}
			if(!"-1".equals(strs[5])){
				request.setAttribute("mess","此条已经处理过审批,请注意操作!");
				return Account_operation(mapping,form,request,response);
			}
			if("-1".equals(op)){
				//撤销
				db.update("UPDATE wht_Account_operation_admin SET openation_state=1,audit_person='"+userForm.getUserno()+"' ,autit_time='"+Tools.getNow()+"' WHERE id="+ids);
				request.setAttribute("mess","撤销操作成功!");
				return Account_operation(mapping,form,request,response);
			}
			
			String[] str = db.getStringArray("select childfacct,accountleft from wht_childfacct where type='01' and "
					+ "fundacct=("
					+ "select fundacct from wht_facct where user_no='"
					+ strs[3] + "')");
			
			String exp="9".equals(strs[1])?"管理员提现":"管理员充值";
			int m = "9".equals(strs[1])?1:2;
			float left = Float.parseFloat(str[1]);
			String sepNo = Tools.getSeqNo("");
			String nowTime=Tools.getNow();
			float money="9".equals(strs[1])? -Float.parseFloat(strs[2]):Float.parseFloat(strs[2]);
			String tablename = "wht_acctbill_" + nowTime.substring(2,6);
			
			db.setAutoCommit(false);
				db.update("UPDATE wht_Account_operation_admin SET openation_state=0,audit_person='"+userForm.getUserno()+"' ,autit_time='"+Tools.getNow()+"' WHERE id="+ids);
			
				Object[] obj1 = { null, str[0], sepNo, str[0], nowTime, Float.parseFloat(strs[2]), Float.parseFloat(strs[2]), strs[1], exp, 0, nowTime, left + money, sepNo, "", m };
				db.logData(15, obj1, tablename);
				db.update("update wht_childfacct set accountleft=accountleft+" + money + " where childfacct='" + str[0] + "'");
				db.update("update wht_facct set accountleft=accountleft+" + money + " where fundacct='" + str[0].substring(0, 15) + "'");
			db.commit();
			request.setAttribute("mess","审核操作成功!");
			return Account_operation(mapping,form,request,response);
		}catch (Exception e) {
			db.rollback();
			Log.error("审核结果异常,,,ex："+e);
			request.setAttribute("mess","系统异常!");
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		return Account_operation(mapping,form,request,response);
	}
	/**
	 * 管理员加款,提款 审批
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward Account_operation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute("userSession");
		if(!"0".equals(userForm.getRoleType())){
			request.setAttribute("mess","您没有操作权限!");
			return new ActionForward("/business/operation_account_audit.jsp");
		}
		
		String openation_userno=request.getParameter("openation_userno");
		String audit_person_userno=request.getParameter("audit_person_userno");
		String indate=request.getParameter("indate");
		String enddate=request.getParameter("enddate");
		StringBuffer buffer=new StringBuffer(" where 1=1");
		
		if(openation_userno!=null && !"".equals(openation_userno)){
			buffer.append(" and openation_userno='"+openation_userno+"'");
		}
		if(audit_person_userno!=null && !"".equals(audit_person_userno)){
			buffer.append(" and audit_person='"+audit_person_userno+"'");
		}
		if(indate!=null && !"".equals(indate)){
			buffer.append(" and openation_time>='"+indate.replace("-","")+"000000"+"'");
		}
		if(enddate!=null && !"".equals(enddate)){
			buffer.append(" and openation_time<='"+indate.replace("-","")+"235959"+"'");
		}
		buffer.append(" order by id desc");
		DBService db=null;
		int index=1;
		int lastIndex=1;
	    int pagesize=15;
	    int count=0;
		try {
			db=new DBService();
			if(request.getParameter("index")!=null && !"".equals(request.getParameter("index"))){
				index=Integer.parseInt(request.getParameter("index"));
			}
			if(index<=0){
				index=1;
			}
			count=db.getInt("SELECT COUNT(1) FROM wht_Account_operation_admin "+buffer.toString());
			if(count>0){
				lastIndex=count%pagesize==0?count/pagesize:count/pagesize+1;
			}
			if(index>=lastIndex){
				index=lastIndex;
			}
			List<String[]> arrList=db.getList("SELECT (SELECT user_ename FROM sys_user WHERE user_no=openation_userno),openation_userno,(SELECT NAME FROM wht_acctradetype WHERE CODE=openation_type),openation_fee/1000,DATE_FORMAT(openation_time,'%Y-%m-%d %k:%i:%s'),CASE openation_state WHEN -1 THEN '等待审批' WHEN 0 THEN '审批成功' WHEN 1 THEN '审批失败' END  ,(SELECT user_ename FROM sys_user WHERE user_no=be_openation_userno),be_openation_userno,(SELECT user_ename FROM sys_user WHERE user_no=audit_person),audit_person,DATE_FORMAT(autit_time,'%Y-%m-%d %k:%i:%s'),id,openation_state FROM wht_Account_operation_admin "+buffer.toString()+" limit "+(index-1)*pagesize+" , "+pagesize);
			request.setAttribute("arrList",arrList);
		} catch (Exception e) {
			Log.error("审批列表查询异常,,,ex："+e);
			request.setAttribute("mess","系统异常!");
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		request.setAttribute("openation_userno",openation_userno);
		request.setAttribute("audit_person_userno", audit_person_userno);
		request.setAttribute("indate", indate);
		request.setAttribute("enddate", enddate);
		request.setAttribute("index",index);
		request.setAttribute("lastIndex",lastIndex);
		request.setAttribute("count",count);
		return new ActionForward("/business/operation_account_audit.jsp");
	}
	
	/**
	 * 地市交易统计  代理店交易统计  代理店开户统计
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null;
	 */
	@SuppressWarnings("unchecked")
	public ActionForward Statistics_info(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String requestType=request.getParameter("reType"); //0用户开户统计  1代理店交易统计  2地市交易统计
		String openationType=request.getParameter("opType");//0查询 1导出
		
		String indate=request.getParameter("indate");
		String enddate=request.getParameter("enddate");
		
		request.setAttribute("indate", indate);
		request.setAttribute("enddate", enddate);
		
		String backUrl="";
		if("0".equals(requestType)){
			backUrl="/business/Proxy_User_Statistics.jsp";
		}else if("1".equals(requestType)){
			backUrl="/business/Proxy_Orders_Statistics.jsp";
		}else if("2".equals(requestType)){
			backUrl="/business/City_Orders_Statistics.jsp";
		}else{
			backUrl="/rights/wltlogin.jsp";
		}
		
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute("userSession");
		if(!"0".equals(userForm.getRoleType())){
			request.setAttribute("mess","您没有操作权限!");
			return new ActionForward(backUrl);
		}
		
		if(indate==null || "".equals(indate) || enddate==null || "".equals(enddate)){
			request.setAttribute("mess","参数不足!");
			return new ActionForward(backUrl);
		}
		String in=indate.replace("-","")+"000000";
		String en=enddate.replace("-","")+"235959";
		
		int columnCount=0;
		String excelName="";
		String HeaderName="";
		List HeaderList=null;
		DBService db=null;
		try {
			db=new DBService();
			String sql="";
			String tableName="wht_orderform_"+in.substring(2,6);
			List<String[]> arrs=null;
			if("0".equals(requestType)){
				sql="SELECT u.user_login ,u.user_ename,u.user_no,(SELECT CONCAT(f.fundacct,'01') FROM wht_facct f WHERE f.user_no=u.user_no) cc,u.exp1,DATE_FORMAT(u.user_createdate,'%Y-%m-%d %k:%i:%s'),"+
					" CASE u.user_status WHEN 0 THEN '正常' WHEN 1 THEN '注销' WHEN 2 THEN '暂停服务' WHEN 3 THEN '清算' ELSE '未知' END  AS state,"+
					" (SELECT sa_name FROM sys_area WHERE sa_id=u.user_city) city,u.exp2,u.user_tel,u.user_adress"+
					" FROM sys_user u WHERE u.user_createdate>='"+in+"' AND u.user_createdate<='"+en+"' ORDER BY u.user_id ASC";
				arrs=db.getList(sql);
				if("1".equals(openationType)){
					columnCount=11;
					excelName="用户信息统计";
					HeaderName="用户信息统计";
					HeaderList=new ArrayList();
					String[] strs=new String[]{"用户账号;colspan=1;rowspan=1;",
					"用户姓名;colspan=1;rowspan=1;",
					"系统编号;colspan=1;rowspan=1;",
					"资金帐户;colspan=1;rowspan=1;",
					"开户人;colspan=1;rowspan=1;",
					"开户时间;colspan=1;rowspan=1;",
					"状态;colspan=1;rowspan=1;",
					"所属地市;colspan=1;rowspan=1;",
					"证件号码;colspan=1;rowspan=1;",
					"用户电话;colspan=1;rowspan=1;",
					"用户地址;colspan=1;rowspan=1;"};
					HeaderList.add(strs);
				}
			}else if("1".equals(requestType)){
				sql="SELECT CASE u.sr_type WHEN 3 THEN '代理店' WHEN 4 THEN '接口商' END AS aaa, u.user_login,u.user_ename,o.ff FROM " +
						"(SELECT aa.user_no,aa.user_login,aa.user_ename,r.sr_type FROM sys_user aa ,"+
					"(SELECT sr_id,sr_type FROM sys_role WHERE sr_type=3 OR sr_type=4) r WHERE aa.user_role=r.sr_id) u "+
					" LEFT JOIN (SELECT userno,SUM(fee)/1000 ff FROM "+tableName+
					" WHERE tradetime>='"+in+"' AND tradetime<='"+en+"' AND state=0  GROUP BY userno) o"+ 
					" ON u.user_no=o.userno ORDER BY u.sr_type,o.ff DESC";
				arrs=db.getList(sql);
				if("1".equals(openationType)){
					columnCount=4;
					excelName="网点交易统计";
					HeaderName="网点交易统计";
					HeaderList=new ArrayList();
					String[] strs=new String[]{"用户类型;colspan=1;rowspan=1;",
					"用户账号;colspan=1;rowspan=1;",
					"用户姓名;colspan=1;rowspan=1;",
					"交易总量;colspan=1;rowspan=1;"};
					HeaderList.add(strs);
				}
			}else if("2".equals(requestType)){
				sql="SELECT b.sa_name,a.cc,a.summoney FROM ("+
					" SELECT CASE areacode WHEN 381 THEN '0000' ELSE areacode END AS areacode ,COUNT(areacode) AS cc,SUM(fee/1000) AS summoney"+ 
					" FROM "+tableName+" WHERE state=0 AND tradetime>='"+in+"' AND tradetime<='"+en+"' GROUP BY areacode ) a "+
					" LEFT JOIN sys_area b ON a.areacode=b.sa_zone ORDER BY a.summoney ASC";
				arrs=db.getList(sql);
				if("1".equals(openationType)){
					columnCount=3;
					excelName="地市交易统计";
					HeaderName="地市交易统计";
					HeaderList=new ArrayList();
					String[] strs=new String[]{"地市名称;colspan=1;rowspan=1;",
					"交易笔数;colspan=1;rowspan=1;",
					"交易总额;colspan=1;rowspan=1;"};
					HeaderList.add(strs);
				}
			}else {
				return new ActionForward("/rights/wltlogin.jsp"); 
			}
			if("0".equals(openationType)){
				request.setAttribute("arrList", arrs);
				return new ActionForward(backUrl);
			}else{
				request.setAttribute("count",columnCount);//总列数
				request.setAttribute("excelName",excelName);//输出的 excel 文件名称
				request.setAttribute("HeaderName",HeaderName); // 表格标题名称
				request.setAttribute("HeaderList",HeaderList); // 表格标头 类别
				request.setAttribute("DataList",arrs); // 数据列表
				return new ActionForward("/AccountInfo/download.jsp"); // 返回的输出页面
			}
		} catch (Exception e) {
			request.setAttribute("mess","系统异常!");
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		return new ActionForward(backUrl);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward sendMsgAdminTrans(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			String userId = request.getParameter("user_id");
			SysUser user = new SysUser();
			SysUserForm userForm = user.getUserInfo(userId);
			String msgCode = NetPayUtil.getRandEight();
			String phone = userForm.getPhone();
			if (null != phone && !"".equals(phone)) {
				String content = "短信验证码:" + msgCode;
				boolean bool = MessageUtil.send(phone, content);
				request.getSession().setAttribute(
						userForm.getUsername() + "-userTrans", msgCode);
				printWriter.print(0);
				printWriter.flush();
				printWriter.close();
			} else {
				printWriter.print(1);
				printWriter.flush();
				printWriter.close();
			}
		} catch (Exception e) {
			printWriter.print(1);
			printWriter.flush();
			printWriter.close();
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 用户管理账户转款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward transUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MobileChargeService service = new MobileChargeService();
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		String userno = userForm.getUserno();
		String fee = request.getParameter("chargeFee");
		
		if(fee==null||fee.equals("")||fee.indexOf("-")!=-1||Float.valueOf(fee)<=0){
			request.setAttribute("mess", "转款金额不正确");
			return new ActionForward("/rights/wltuserTrans.jsp");	
		}
		
		BiProd bp = new BiProd();
		String[] strss=bp.getPlaceMoney("3", userForm.getUserno());
		if(strss!=null &&"true".equals(strss[0]))
		{
			request.setAttribute("mess", "管理员已禁用额度转移功能,如需开通,请联系管理员!");
			return new ActionForward("/rights/wltuserTrans.jsp");	
		}
		
		SysUser user = new SysUser();
		String userf = (String)request.getParameter("fee_flag");
		if(userf.equals("2")){
			String code = request.getParameter("inputMsgCode");
			if(!user.validateAuth(code, userno)){
				request.setAttribute("mess", "令牌验证码输入错误");
				return new ActionForward("/rights/wltuserTrans.jsp");
			}
		}
		
		if(userf.equals("1")){
			String code = request.getParameter("inputMsgCode");
			String msg=(String) request.getSession().getAttribute(
					userForm.getUsername() + "=at");
			if(!msg.equals(code)){
				request.setAttribute("mess", "短信验证码输入错误");
				return new ActionForward("/rights/wltuserTrans.jsp");
			}
		}
		
		double mon = FloatArith.yun2li(fee);

		String sepNo = Tools.getSeqNo(userForm.getUserno());
		String nowTime = Tools.getNow();
		
		String fcct = request.getParameter("username");
		String[] state = user.getUserFundAcctBylogin(fcct);
		if (null == state || state.length != 2) {
			request.setAttribute("mess", "资金账号不存在");
			return new ActionForward("/rights/wltuserTrans.jsp");
		}
		if (!"1".equals(state[0])) {
			request.setAttribute("mess", "资金账号不可用");
			return new ActionForward("/rights/wltuserTrans.jsp");
		}
		String fundAcct01 = service.getUserFundAcct("01", userno);
		double acctLeft = Double.parseDouble(service
				.getFundAcctLeft(fundAcct01));
		if (0 == acctLeft || FloatArith.sub(acctLeft, mon) < 0) {
			request.setAttribute("mess", "资金账号余额不足");
			return new ActionForward("/rights/wltuserTrans.jsp");
		}
		/**
		if (null != userForm.getFeeshortflag()
				&& !"".equals(userForm.getFeeshortflag())
				&& Integer.parseInt(userForm.getFeeshortflag()) == 0) {
			String msgCode = (String) request.getSession().getAttribute(
					userForm.getUsername() + "-accountTrans");
			if (!inputMsgCode.equals(msgCode)) {
				request.setAttribute("mess", "短信验证码输入错误");
				return new ActionForward("/rights/wltuserTrans.jsp");
			}
		}
		*/
		if (null != userForm.getDealpass()
				&& !"".equals(userForm.getDealpass())) {
			String tradePass = request.getParameter("tradePass");
			if (!userForm.getDealpass().equals(MD5.encode(tradePass))) {
				request.setAttribute("mess", "交易密码输入错误");
				return new ActionForward("/rights/wltuserTrans.jsp");
			}
		} else {
			request.setAttribute("mess", "交易密码输入错误");
			return new ActionForward("/rights/wltuserTrans.jsp");
		}
		int tk = user.trans(fundAcct01, state[1], mon, sepNo, nowTime, "额度转移",
				"11", userForm.getUsername(),fcct);
		if (tk == 0) {
			request.setAttribute("mess", "交易成功");
		} else {
			request.setAttribute("mess", "交易失败");
		}
		return new ActionForward("/rights/wltuserTrans.jsp");
	}
	/**
	 * 代理商列表 额度转移
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward transUserListHref(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MobileChargeService service = new MobileChargeService();
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		String returnUrls=request.getParameter("returnUrls");
		request.setAttribute("returnUrls",returnUrls);
		
		String userno = userForm.getUserno();
		String fee = request.getParameter("chargeFee");
		
		if(fee==null||fee.equals("")||fee.indexOf("-")!=-1||Float.valueOf(fee)<=0){
			request.setAttribute("mess", "转款金额不正确");
			return new ActionForward("/rights/wltuserTransHref.jsp");	
		}
		BiProd bp = new BiProd();
		String[] strss=bp.getPlaceMoney("3", userForm.getUserno());
		if(strss!=null &&"true".equals(strss[0]))
		{
			request.setAttribute("mess", "管理员已禁用额度转移功能,如需开通,请联系管理员!");
			return new ActionForward("/rights/wltuserTransHref.jsp");	
		}
		SysUser user = new SysUser();
		String userf = (String)request.getParameter("fee_flag");
		if(userf.equals("2")){
			String code = request.getParameter("inputMsgCode");
			if(!user.validateAuth(code, userno)){
				request.setAttribute("mess", "令牌验证码输入错误");
				return new ActionForward("/rights/wltuserTransHref.jsp");
			}
		}
		
		if(userf.equals("1")){
			String code = request.getParameter("inputMsgCode");
			String msg=(String) request.getSession().getAttribute(
					userForm.getUsername() + "=at");
			if(!msg.equals(code)){
				request.setAttribute("mess", "短信验证码输入错误");
				return new ActionForward("/rights/wltuserTransHref.jsp");
			}
		}
		
		double mon = FloatArith.yun2li(fee);

		String sepNo = Tools.getSeqNo(userForm.getUserno());
		String nowTime = Tools.getNow();
		
		String fcct = request.getParameter("username");
		String[] state = user.getUserFundAcctBylogin(fcct);
		if (null == state || state.length != 2) {
			request.setAttribute("mess", "资金账号不存在");
			return new ActionForward("/rights/wltuserTransHref.jsp");
		}
		if (!"1".equals(state[0])) {
			request.setAttribute("mess", "资金账号不可用");
			return new ActionForward("/rights/wltuserTransHref.jsp");
		}
		String fundAcct01 = service.getUserFundAcct("01", userno);
		double acctLeft = Double.parseDouble(service
				.getFundAcctLeft(fundAcct01));
		if (0 == acctLeft || FloatArith.sub(acctLeft, mon) < 0) {
			request.setAttribute("mess", "资金账号余额不足");
			return new ActionForward("/rights/wltuserTransHref.jsp");
		}
		/**
		if (null != userForm.getFeeshortflag()
				&& !"".equals(userForm.getFeeshortflag())
				&& Integer.parseInt(userForm.getFeeshortflag()) == 0) {
			String msgCode = (String) request.getSession().getAttribute(
					userForm.getUsername() + "-accountTrans");
			if (!inputMsgCode.equals(msgCode)) {
				request.setAttribute("mess", "短信验证码输入错误");
				return new ActionForward("/rights/wltuserTrans.jsp");
			}
		}
		*/
		if (null != userForm.getDealpass()
				&& !"".equals(userForm.getDealpass())) {
			String tradePass = request.getParameter("tradePass");
			if (!userForm.getDealpass().equals(MD5.encode(tradePass))) {
				request.setAttribute("mess", "交易密码输入错误");
				return new ActionForward("/rights/wltuserTransHref.jsp");
			}
		} else {
			request.setAttribute("mess", "交易密码输入错误");
			return new ActionForward("/rights/wltuserTransHref.jsp");
		}
		int tk = user.trans(fundAcct01, state[1], mon, sepNo, nowTime, "额度转移",
				"11", userForm.getUsername(),fcct);
		if (tk == 0) {
			request.setAttribute("mess", "交易成功");
		} else {
			request.setAttribute("mess", "交易失败");
		}
		return new ActionForward("/rights/wltuserTransHref.jsp");
	}
	/**
	 * 中石化账户转款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward transZshUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MobileChargeService service = new MobileChargeService();
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		String userno = userForm.getUserno();
		String username = userForm.getUsername();
		String fee = request.getParameter("chargeFee");
		double mon = FloatArith.yun2li(fee);
		if(mon<=0){
			request.setAttribute("mess", "转账金额不能小于0");
			return new ActionForward("/rights/wltzshuserTrans.jsp");
		}
		String sepNo = Tools.getSeqNo(userForm.getUserno());
		String nowTime = Tools.getNow();
		SysUser user = new SysUser();
		String fcct = request.getParameter("username");
		String[] state = user.getUserFundAcctBylogin(fcct);
		if (null == state || state.length != 2) {
			request.setAttribute("mess", "资金账号不存在");
			return new ActionForward("/rights/wltzshuserTrans.jsp");
		}
		if (!"1".equals(state[0])) {
			request.setAttribute("mess", "资金账号不可用");
			return new ActionForward("/rights/wltzshuserTrans.jsp");
		}
		String inputMsgCode = request.getParameter("inputMsgCode");
		String fundAcct01 = service.getUserFundAcct("01", userno);
		double acctLeft = Double.parseDouble(service
				.getFundAcctLeft(fundAcct01));
		if (0 == acctLeft || FloatArith.sub(acctLeft, mon) < 0) {
			request.setAttribute("mess", "资金账号余额不足");
			return new ActionForward("/rights/wltzshuserTrans.jsp");
		}
		BiProd biProd = new BiProd();
		String str1 = biProd.compareUser(username);// 获取转账账号角色类型
		String str2 = biProd.findUserType(username, fcct);// 获取子账号角色类型
		if (str2 != null) {
			boolean b = false;
			if (Integer.parseInt(str2) > Integer.parseInt(str1)
					&& Integer.parseInt(str2) == 3
					&& Integer.parseInt(str1) == 2) {
				b = true;
			}
			if (!b) {
				request.setAttribute("mess", "不支持类型转账");
				return new ActionForward("/rights/wltzshuserTrans.jsp");
			}
		}else{
			request.setAttribute("mess", "不支持类型转账");
			return new ActionForward("/rights/wltzshuserTrans.jsp");
		}
	
		String userf = (String)request.getParameter("fee_flag");
		if(userf.equals("2")){
			String code = request.getParameter("inputMsgCode");
			if(!user.validateAuth(code, userno)){
				request.setAttribute("mess", "令牌验证码输入错误");
				return new ActionForward("/rights/wltzshuserTrans.jsp");
			}
		}
		
		if(userf.equals("1")){
			String code = request.getParameter("inputMsgCode");
			String msg=(String) request.getSession().getAttribute(
					userForm.getUsername() + "=at");
			if(!msg.equals(code)){
				request.setAttribute("mess", "短信验证码输入错误");
				return new ActionForward("/rights/wltzshuserTrans.jsp");
			}
		}
		if (null != userForm.getDealpass()
				&& !"".equals(userForm.getDealpass())) {
			String tradePass = request.getParameter("tradePass");
			if (!userForm.getDealpass().equals(MD5.encode(tradePass))) {
				request.setAttribute("mess", "交易密码输入错误");
				return new ActionForward("/rights/wltzshuserTrans.jsp");
			}
		} else {
			request.setAttribute("mess", "交易密码输入错误");
			return new ActionForward("/rights/wltzshuserTrans.jsp");
		}
		int tk = user.trans(fundAcct01, state[1], mon, sepNo, nowTime, "额度转移",
				"11", username,fcct);
		if (tk == 0) {
			request.setAttribute("mess", "交易成功");
		} else {
			request.setAttribute("mess", "交易失败");
		}
		return new ActionForward("/rights/wltzshuserTrans.jsp");
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward sendMsgAcctTrans(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			SysUserForm userForm = (SysUserForm) request.getSession()
					.getAttribute("userSession");
			String msgCode = NetPayUtil.getRandEight();
			String phone=userForm.getPhone();
			if (null != phone && !"".equals(phone)) {
				if (YMmessage.ymSendSMS(phone, msgCode)) {
					request.getSession().setAttribute(
							userForm.getUsername() + "=at", msgCode);
					printWriter.print(0);
					printWriter.flush();
					printWriter.close();
				} else {
					printWriter.print(1);
					printWriter.flush();
					printWriter.close();
				}
			} else {
				printWriter.print(1);
				printWriter.flush();
				printWriter.close();
			}
		} catch (IOException e) {
			printWriter.print(1);
			printWriter.flush();
			printWriter.close();
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 资金子账户转款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward transChild(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String num=request.getParameter("num");
		String fee = num.equals("1")?request.getParameter("transFee"):request.getParameter("transFee3");
		double mon = FloatArith.yun2li(fee);
		String nowTime = Tools.getNow();
		MobileChargeService service = new MobileChargeService();
		if("3".equals(num)){
			if(service.isDateLimit(nowTime)){
				request.setAttribute("mess", "限制日期内不允许从冻结账户转出金额");
				return mapping.findForward("transchildsuccess");
			}
		}
		
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		String sepNo = Tools.getSeqNo(userForm.getUserno());
		String fromAccount = service
				.getUserFundAcct(num.equals("1")?"02":"03", userForm.getUserno());
		String str=num.equals("1")?"佣金转押金":"冻结转押金";
		int tradetype=num.equals("1")? 12:24;
		String toAccount = fromAccount.substring(0, 15) + "01";
		String tradeserial = Tools.getSeqNo(toAccount);
		String acctLeft = service.getFundAcctLeft(fromAccount);
		if (null != acctLeft && !"".equals(acctLeft)) {
			int tmp = Integer.parseInt(acctLeft);
			if (tmp - mon < 0) {
				request.setAttribute("mess", "余额不足");
				return mapping.findForward("transchildsuccess");
			}
			SysUser user = new SysUser();
			user.transChild(fromAccount, toAccount, mon, sepNo, nowTime,
					str, tradetype, tradeserial);
			request.setAttribute("mess", "转移成功");
			return mapping.findForward("transchildsuccess");
		} else {
			request.setAttribute("mess", "资金账户不存在");
			return mapping.findForward("transchildsuccess");
		}
	}

	/**
	 * 根据角色类型查询角色
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getUserRoleByType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pWriter = response.getWriter();
		String roleType = request.getParameter("rtype");
		String provice =request.getParameter("provice");
		SysRole sRole = new SysRole();
		List list = sRole.getRoleByTypeAndArea(roleType,provice);
		StringBuffer sBuffer = new StringBuffer();
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			sBuffer.append("|" + temp[1] + "[" + temp[0] + "]");
		}
		System.out.println(sBuffer.toString());
		pWriter.print(sBuffer.toString());
		pWriter.flush();
		pWriter.close();
		return null;
	}

	/**
	 * 根据区域查询角色
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getUserRoleByArea(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pWriter = response.getWriter();
		String aid = request.getParameter("areaid");
		String sType = request.getParameter("sType");
		if (null == sType || "".equals(sType)) {
			sType = "";
		} else {
			sType = "b.sr_type = " + sType + " and";
		}
		SysRole sRole = new SysRole();
		// List list = sRole.getRoleByAreaDefault(aid,sType);
		// String result = "";
		// if(null != list && list.size() > 0){
		// String[] temp = (String[])list.get(0);
		// result = temp[0];
		// }
		List list = sRole.getRoleByArea(aid, sType);
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("请选择[]");
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			sBuffer.append("|" + temp[1] + "[" + temp[0] + "]");
		}
		pWriter.print(sBuffer.toString());
		pWriter.flush();
		pWriter.close();
		return null;
	}

	/**
	 * 根据区域查询角色
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getUserRoleDefaultByArea(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pWriter = response.getWriter();
		String aid = request.getParameter("areaid");
		String sType = request.getParameter("sType");
		if (null == sType || "".equals(sType)) {
			sType = "";
		} else {
			sType = "b.sr_type = " + sType + " and";
		}
		SysRole sRole = new SysRole();
		List list = sRole.getRoleByAreaDefault(aid, sType);
		String result = "";
		if (null != list && list.size() > 0) {
			String[] temp = (String[]) list.get(0);
			result = temp[0];
		}
		pWriter.print(result);
		pWriter.flush();
		pWriter.close();
		return null;
	}

	/**
	 * 根据区域查询角色
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getUserRoleByAreaRegist(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pWriter = response.getWriter();
		String aid = request.getParameter("areaid");
		String sType = request.getParameter("sType");
		if (null == sType || "".equals(sType)) {
			sType = "";
		} else {
			sType = "b.sr_type = " + sType + " and";
		}
		SysRole sRole = new SysRole();
		List list = sRole.getRoleByAreaDefault(aid, sType);
		StringBuffer sBuffer = new StringBuffer();
		// sBuffer.append("请选择[]");
		if (null != list && list.size() > 0) {
			String[] temp = (String[]) list.get(0);
			sBuffer.append(temp[1] + "[" + temp[0] + "]");
		}
		pWriter.print(sBuffer.toString());
		pWriter.flush();
		pWriter.close();
		return null;
	}

	/**
	 * 根据区域查询角色
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getUserRoleByAreaDefault(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pWriter = response.getWriter();
		String aid = request.getParameter("areaid");
		String sType = request.getParameter("sType");
		if (null == sType || "".equals(sType)) {
			sType = "";
		} else {
			sType = "b.sr_type = " + sType + " and";
		}
		SysRole sRole = new SysRole();
		List list = sRole.getRoleByAreaDefault(aid, sType);
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("请选择[]");
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			sBuffer.append("|" + temp[1] + "[" + temp[0] + "]");
		}
		pWriter.print(sBuffer.toString());
		pWriter.flush();
		pWriter.close();
		return null;
	}

	/**
	 * 根据省查询市
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pWriter = response.getWriter();
		String aid = request.getParameter("areaid");
		SysArea sa = new SysArea();
		List list = sa.getNextList(aid);
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("请选择[]");
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			sBuffer.append("|" + temp[1] + "[" + temp[0] + "]");
		}
		pWriter.print(sBuffer.toString());
		pWriter.flush();
		pWriter.close();
		return null;
	}

	/**
	 * 根据省查询佣金组
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getCommission(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pWriter = response.getWriter();
		String aid = request.getParameter("areaid");
		String sType = request.getParameter("sType");
		if ("2".equals(sType)) {
			// 代理商
			sType = "3";
		} else if ("3".equals(sType)) {
			// 代办点
			sType = "1";
		} else if ("3".equals(sType)) {
			// 接口代理商
			sType = "4";
		}
		SysCommission sc = new SysCommission();
		// List list = sc.listDefaultCommission(aid,sType);
		// String result = "";
		// if(null != list && list.size() > 0){
		// String[] temp = (String[])list.get(0);
		// result = temp[0];
		// }
		List list = sc.listCommission(aid, sType);
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("请选择[]");
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			sBuffer.append("|" + temp[1] + "[" + temp[0] + "]");
		}
		pWriter.print(sBuffer.toString());
		pWriter.flush();
		pWriter.close();
		return null;
	}

	/**
	 * 根据省查询默认佣金组
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getCommissionDefault(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pWriter = response.getWriter();
		String aid = request.getParameter("areaid");
		String sType = request.getParameter("sType");
		if ("2".equals(sType)) {
			// 代理商
			sType = "3";
		} else if ("3".equals(sType)) {
			// 代办点
			sType = "1";
		} else if ("3".equals(sType)) {
			// 接口代理商
			sType = "4";
		}
		SysCommission sc = new SysCommission();
		List list = sc.listDefaultCommission(aid, sType);
		String result = "";
		if (null != list && list.size() > 0) {
			String[] temp = (String[]) list.get(0);
			result = temp[0];
		}
		pWriter.print(result);
		pWriter.flush();
		pWriter.close();
		return null;
	}

	public ActionForward getDefaultCommission(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter pWriter = response.getWriter();
		String aid = request.getParameter("areaid");
		String sType = request.getParameter("sType");
		if ("2".equals(sType)) {
			// 代理商
			sType = "3";
		} else if ("3".equals(sType)) {
			// 代办点
			sType = "1";
		} else if ("3".equals(sType)) {
			// 接口代理商
			sType = "4";
		}
		SysCommission sc = new SysCommission();
		List list = sc.listDefaultCommission(aid, sType);
		StringBuffer sBuffer = new StringBuffer();
		// sBuffer.append("请选择[]");
		if (null != list && list.size() > 0) {
			String[] temp = (String[]) list.get(0);
			sBuffer.append(temp[1] + "[" + temp[0] + "]");
		}
		pWriter.print(sBuffer.toString());
		pWriter.flush();
		pWriter.close();
		return null;
	}

	/**
	 * 忘记密码
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updatePwd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String phone = request.getParameter("phone");
		String account = request.getParameter("username");
		if (null == phone || null == account) {
			request.setAttribute("mess", "输入信息有误");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		String password = NetPayUtil.getRandEight1() + "hrd";
		String pwd = MD5.encode(password);
		int n = SysBankLog.updPwd(account, pwd);
		if (n == 1) {
			request.setAttribute("mess", "用户不存在");
			return new ActionForward("/rights/chongzhimima.jsp");
		} else if (n == 2) {
			request.setAttribute("mess", "系统繁忙请稍后再试");
			return new ActionForward("/rights/chongzhimima.jsp");
		}
		String content = "万汇通账户重置后的密码:" + password + ",请勿告诉他人<深圳万恒科技有限公司>";
		if (YMmessage.ymSendSMS(new String[] { phone }, content)) {
			request.setAttribute("mess", "密码重置成功");
			return new ActionForward("/rights/wltlogin.jsp");
		} else {
			request.setAttribute("mess", "短信发送失败");
			return new ActionForward("/rights/chongzhimima.jsp");
		}
	}

	/**
	 * 提现
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward adminTransfer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		String roleType = userForm.getRoleType();
		if (!("0".equals(roleType) || "1".equals(roleType))) {
			request.setAttribute("mess", "权限不足");
			return new ActionForward("/rights/admintransfer.jsp");
		}
		MobileChargeService service = new MobileChargeService();
		String fee = request.getParameter("chargeFee");
		String fcct = request.getParameter("username");
		String mon = Tools.yuanToFen(fee);
		String sepNo = Tools.getSeqNo("");
		String nowTime = Tools.getNow();
		// 根据用户名获取资金账号
		SysUser user = new SysUser();
		String state = user.getUserFundAcct(fcct);
		if (null == state || "".equals(state)) {
			request.setAttribute("mess", "资金账号不存在");
			return new ActionForward("/rights/admintransfer.jsp");
		}
		if (!"1".equals(state)) {
			request.setAttribute("mess", "资金账号不可用");
			return new ActionForward("/rights/admintransfer.jsp");
		}
		int acctLeft = Integer.parseInt(service.getFundAcctLeft(fcct + "02"));
		if (0 == acctLeft || acctLeft - Integer.parseInt(mon) < 0) {
			request.setAttribute("mess", "资金账号余额不足无法提现");
			return new ActionForward("/rights/admintransfer.jsp");
		}
		if (user.tixian(fcct, mon, sepNo, nowTime, "管理员提现", "53", sepNo) == 0) {
			request.setAttribute("mess", "提现失败");
			return new ActionForward("/rights/admintransfer.jsp");
		} else {
			request.setAttribute("mess", "提现成功");
			return new ActionForward("/rights/admintransfer.jsp");
		}
	}

	/**
	 * 代理商添加用户
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward addUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userForm = (SysUserForm) form;
		if (null == userForm.getUsername()
				|| null == userForm.getUserpassword()
				|| null == userForm.getDealpass()
				|| null == userForm.getWltarea()
				|| null == userForm.getUser_site_city()) {
			request.setAttribute("mess", "所填信息不完整");
			return new ActionForward("/rights/wltuseradd2.jsp");
		}
		SysUserForm userSession = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		SysRole role = new SysRole();
		String roleId=role.getRoleID(userSession.getUsersite());
		if(roleId==null||roleId.length()==0){
			request.setAttribute("mess", "添加代办户失败,未找到默认代办户角色,请联系管理员");
			return new ActionForward("/rights/wltuseradd2.jsp");
		}
		userForm.setUser_role(roleId);
		userForm.setParent_id(userSession.getUser_id());
		userForm.setUsersite(userSession.getUsersite());
		SysUser user = new SysUser();
		String k = user.addUser(userForm);
		if (null != k && !"-1".equals(k) && !"-2".equals(k)) {
			request.setAttribute("downnum", k);
			request.setAttribute("parentid", userSession.getUser_id());
			request.setAttribute("mess", "开户成功,该代办点佣金比例已按默认值分配");
			// return new ActionForward("/employ/whtuserandparentemploy.jsp");
			return new ActionForward("/rights/wltuseradd2.jsp");
		} else if ("-1".equals(k)) {
			request.setAttribute("mess", "每天最多只能添加50个下级代理点");
			return new ActionForward("/rights/wltuseradd2.jsp");
		} else if ("-2".equals(k)) {
			request.setAttribute("mess", "最多只能添加1000个下级代理点");
			return new ActionForward("/rights/wltuseradd2.jsp");
		} else {
			request.setAttribute("mess", "添加代办户失败");
			return new ActionForward("/rights/wltuseradd2.jsp");
		}
	}

	/**
	 * 添加欧非信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward addOufei(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (null == request.getSession().getAttribute("userSession")) {

			request.setAttribute("msg", "未登录,请重新登录");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		SysUserForm userSession = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		SysUser user = new SysUser();
        if(!"2".equals(userSession.getRoleType())){
    		request.setAttribute("msg", "非代理商不能使用绑定功能！");
			return new ActionForward("/rights/whtagentOufeibangding.jsp");
        }
		SysUserForm userForm = (SysUserForm) form;
		if(!user.getRTbyUserno(userForm.getUserno()).equals("3")){
			request.setAttribute("msg", "添加失败，只允许绑定代办点,请确认输入信息是否为代办点!");
			return new ActionForward("/rights/whtagentOufeibangding.jsp");
		}
		int state = user.addOufeiUser(userForm, userSession.getUser_id());
		if (state == -1) {
			request.setAttribute("msg", "添加失败，该代理商已经绑定过供货信息!");
			return new ActionForward("/rights/whtagentOufeibangding.jsp");
		} else if (state == -2) {
			request.setAttribute("msg", "用户编号不存在，请仔细检查重新添加！");
			return new ActionForward("/rights/whtagentOufeibangding.jsp");
		} else if (state == -3) {
			request.setAttribute("msg", "资金账号不存在，请仔细检查重新添加！");
			return new ActionForward("/rights/whtagentOufeibangding.jsp");
		} else if (state == -4) {
			request.setAttribute("msg", "用户编号和登录密码、不匹配，请重新添加！");
			return new ActionForward("/rights/whtagentOufeibangding.jsp");
		} else if (state == -5) {
			request.setAttribute("msg", "系统繁忙稍后再试！");
			return new ActionForward("/rights/whtagentOufeibangding.jsp");
		}
		request.setAttribute("msg", "操作成功");
		return new ActionForward("/rights/whtagentOufeibangding.jsp");
	}

	/**
	 * 添加欧非信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward oufeiSwitch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (null == request.getSession().getAttribute("userSession")) {

			request.setAttribute("msg", "未登录,请重新登录");
			return new ActionForward("/rights/wltlogin.jsp");
		}

		SysUserForm userForm = (SysUserForm) form;
		SysUser user = new SysUser();
		int state = user.addOufeiSwitch(userForm);
		if (state == -1) {
			request.setAttribute("msg", "添加失败，已存在相同数据!");
			return new ActionForward("/rights/whtoufeiswitch.jsp");
		} else if (state == -2) {
			request.setAttribute("msg", "用户编号不存在，请重新添加！");
			return new ActionForward("/rights/whtoufeiswitch.jsp");
		} else if (state == -3) {
			request.setAttribute("msg", "资金账号不存在，请重新添加！");
			return new ActionForward("/rights/whtoufeiswitch.jsp");
		} else if (state == -4) {
			request.setAttribute("msg", "用户编号和资金账号不匹配，请重新添加！");
			return new ActionForward("/rights/whtoufeiswitch.jsp");
		} else if (state == -5) {
			request.setAttribute("msg", "系统繁忙稍后再试！");
			return new ActionForward("/rights/whtoufeiswitch.jsp");
		}
		request.setAttribute("msg", "操作成功");
		return new ActionForward("/rights/whtoufeiswitch.jsp");
	}

	/**
	 * 代理商 查看供货状态
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward gh(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm loginUser = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		if (null == loginUser) {
			request.setAttribute("mess", "登录超时,请重新登录");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		String userid = loginUser.getUser_id();

		SysUser user = new SysUser();
		List array = user.queryOufei(userid, 1,"");

		if (null == array||array.size()==0) {
			return new ActionForward("/rights/oufeifail.jsp");
		} else {
			request.setAttribute("infos", array);
			return new ActionForward("/rights/agentoufeiinfo.jsp");
		}
	}

	/**
	 * 代理商 修改供货状态
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward ghUp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm loginUser = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		if (null == loginUser) {
			request.setAttribute("mess", "登录超时,请重新登录");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		int flag = Integer.parseInt(request.getParameter("flag"));
		String type=request.getParameter("type");
		SysUser user = new SysUser();

		List arryList = user.queryOufei(loginUser.getUser_id(), 0,type);
		String[] str=(String[])arryList.get(0);
		if (null == str) {
			request.setAttribute("mess", "系统繁忙或者供货信息未绑定");
			return new ActionForward("/rights/whtagentOufeibangding.jsp");
		}
		int rs = user.upOufei(loginUser.getUser_id(), flag, 0,type);
		if (rs == -1) {
			request.setAttribute("mess", "操作失败,请稍后再试");
			return new ActionForward("/rights/sysuser.do?method=gh");
		} else {
			if (flag == 0) {
				if("0".equals(type)){//开启话费充值
					Log.info("开启殴飞话费供货--" + loginUser.getUserno());
					OufeiTask task = new OufeiTask(str);
					new Timer().scheduleAtFixedRate(task, 0, 1 * 30 * 1000);
					OUConstat.tasks.put(loginUser.getUser_id(), task);
				}else{//开启Q币充值
					Log.info("开启殴飞Q币供货--" + loginUser.getUser_id()+"_qb");
					OuFeiThread qbtask = new OuFeiThread(str);
					new Timer().scheduleAtFixedRate(qbtask, 0, 1 * 30 * 1000);
					OUConstat.Qbtask.put(loginUser.getUser_id()+"_qb", qbtask);
				}
			} else {
				if("0".equals(type)){//关闭话费充值
					Log.info("关闭殴飞话费供货--" + loginUser.getUser_id());
					TimerTask task = OUConstat.tasks.get(loginUser.getUser_id());
					if (null != task) {
						task.cancel();
						OUConstat.tasks.remove(loginUser.getUser_id());
						Log.info("殴飞话费供货已关闭:" + loginUser.getUser_id());
					}
				}else{//关闭Q币充值
					Log.info("关闭殴飞Q币供货--" + loginUser.getUser_id()+"_qb");
					TimerTask qbtask = OUConstat.Qbtask.get(loginUser.getUser_id()+"_qb");
					if (null != qbtask) {
						qbtask.cancel();
						OUConstat.Qbtask.remove(loginUser.getUser_id()+"_qb");
						Log.info("殴飞话费供货已关闭:" + loginUser.getUser_id()+"_qb");
					}
				}
			}
			request.setAttribute("mess", "操作成功");
			request.setAttribute("info", new String[] { flag + "" });
			return new ActionForward("/rights/sysuser.do?method=gh");
		}
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward ghdel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm loginUser = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		if (null == loginUser) {
			request.setAttribute("mess", "登录超时,请重新登录");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		String id = request.getParameter("ids").split("#")[0];
		String type=request.getParameter("ids").split("#")[2];
		SysUser user = new SysUser();
		int rs = user.upOufei(id, 0, 1,type);
		if (rs == -1) {
			request.setAttribute("mess", "操作失败,请稍后再试");
			return new ActionForward("/rights/oufeiUserlist.jsp");
		} else {
			request.setAttribute("mess", "操作成功");
			return new ActionForward("/rights/oufeiUserlist.jsp");
		}
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward ghupstate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm loginUser = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		if (null == loginUser) {
			request.setAttribute("mess", "登录超时,请重新登录");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		String id = request.getParameter("ids").split("#")[0];
		String type=request.getParameter("ids").split("#")[2];
		SysUser user = new SysUser();
		int rs = user.upOufeiuser(id,request.getParameter("ids").split("#")[1].equals("0")?1:0,type);
		if (rs == -1) {
			request.setAttribute("mess", "操作失败,请稍后再试");
			return new ActionForward("/rights/oufeiUserlist.jsp");
		} else {
			request.setAttribute("mess", "操作成功");
			return new ActionForward("/rights/oufeiUserlist.jsp");
		}
	}

	/**
	 * 代理商供货开关
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward ghswitch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm loginUser = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		String flag = request.getParameter("flag");
		if (null == loginUser) {
			request.setAttribute("mess", "登录超时,请重新登录");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		SysUser user = new SysUser();
		boolean rs = user.oufeiswitch(loginUser.getUserno());
		if (rs == false) {
			return new ActionForward("/rights/oufeifail.jsp");
		} else {
			if ("0".equals(flag)) {
				return new ActionForward("/rights/whtagentOufeibangding.jsp");
			} else if ("1".equals(flag)) {
				return new ActionForward("/rights/oufeipersional.jsp");
			} else if ("2".equals(flag)) {
				return new ActionForward("/rights/sysuser.do?method=gh");
			} else {
				return new ActionForward("/rights/oufeifail.jsp");
			}
		}
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward phoneValidata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String inputMsgCode = request.getParameter("msgcode");
		String newpwd=MD5.encode(request.getParameter("newpwd"));
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		String msgCode = (String) request.getSession().getAttribute(
				userForm.getUsername() + "=jh");
		if (null == msgCode || "".equals(msgCode)
				|| !inputMsgCode.equals(msgCode)) {
			request.setAttribute("mess", "短信验证码不正确");
			return new ActionForward("/business/phoneValidata.jsp");
		} else {
			String sql = "update sys_user set user_activate=0,user_pass='"+newpwd+"' where user_login='"
					+ userForm.getUsername() + "'";
			DBService db = null;
			try {
				db = new DBService();
				
				db.setAutoCommit(false);
				db.update(sql);
				db.commit();
			} catch (Exception e) {
				e.printStackTrace();
				db.rollback();
				request.setAttribute("mess", "激活账户失败,请稍后重试");
				return new ActionForward("/rights/wltlogin.jsp");
			} finally {
				if (null != db)
					db.close();
			}
			return new ActionForward("/rights/wltframe.jsp");
		}
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward dengluValidata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String inputMsgCode = request.getParameter("msgcode");
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		if(null==userForm||null==inputMsgCode){
			request.setAttribute("mess", "令牌验证码为空或需要重新登录万汇通平台");
			return new ActionForward("/business/deluphoneValidata.jsp");
		}
		//令牌
		SysUser user =new SysUser();
		if(!user.validateAuth(inputMsgCode,userForm.getUserno())){
			request.setAttribute("mess", "令牌验证码不正确");
			return new ActionForward("/business/deluphoneValidata.jsp");	
		}else{
			return new ActionForward("/rights/wltframe.jsp");
		}
		//令牌结束
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward dengluMsgValidata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String inputMsgCode = request.getParameter("msgcode");
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		if(null==userForm||null==inputMsgCode){
			request.setAttribute("mess", "短信验证码为空或需要重新登录万汇通平台");
			return new ActionForward("/business/phonemsgValidata.jsp");
		}
		String msgCode = (String) request.getSession().getAttribute(
				userForm.getUsername() + "=dl");
		if (null == msgCode || "".equals(msgCode)
				|| !inputMsgCode.equals(msgCode)) {
			request.setAttribute("mess", "短信验证码不正确");
			return new ActionForward("/business/phonemsgValidata.jsp");
		} else {
			return new ActionForward("/rights/wltframe.jsp");
		}
	}

	/**
	 * 初始化用户密码
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPwd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String login = request.getParameter("cm_value");
		if (null == login) {
			request.setAttribute("mess", "所填登录账号有误");
			return new ActionForward("/rights/initializationpwd.jsp");
		}
		SysUser user = new SysUser();
		int n = user.initPwd(login);
		if (n == 0) {
			request.setAttribute("mess", "用户不存在");
		} else if (n == 1) {
			request.setAttribute("mess", "初始化成功");
		} else if (n == -1) {
			request.setAttribute("mess", "系统异常请稍后再试");
		}

		return new ActionForward("/rights/initializationpwd.jsp");
	}
	/**
	 * 初始化用户密码并短信发送给对方
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initMessagePwd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String login = request.getParameter("cm_value");
		String phone = request.getParameter("re_value");
		if (null == login) {
			request.setAttribute("mess", "所填登录账号有误");
			return new ActionForward("/rights/initializationmessagepwd.jsp");
		}
		if (null != phone && phone.length()>0&&phone.length() != 11) {
			request.setAttribute("mess", "所填接收手机有误");
			return new ActionForward("/rights/initializationmessagepwd.jsp");
		}
		SysUser user = new SysUser();
		int n = user.initMessagePwd(login,phone);
		if (n == 0) {
			request.setAttribute("mess", "用户不存在");
		} else if (n == 1) {
			request.setAttribute("mess", "初始化成功,已发送短信到用户");
		} else if (n == -1) {
			request.setAttribute("mess", "系统异常请稍后再试");
		} else if (n == 2) {
			request.setAttribute("mess", "短信发送失败");
		}

		return new ActionForward("/rights/initializationmessagepwd.jsp");
	}
	/**
	 * 获取中石化下级用户信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * autor:谭万龙
	 * date:2014-04-26
	 */
	public ActionForward getZshUserInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String uid = request.getParameter("uid");
		if(uid!=null){
			SysUser user = new SysUser();
			String accLeft=user.getUseLeft(uid);
			if(accLeft!=null){
				request.setAttribute("accLeft", accLeft);
				 BigDecimal a = new BigDecimal(accLeft);
				 BigDecimal b = new BigDecimal(100);
				request.setAttribute("accYLeft",a.subtract(b));
				request.setAttribute("userNo", uid);
				return new ActionForward("/rights/zshuserextract.jsp");
			}else{
				return new ActionForward("/rights/zshagentuserlist.jsp");
			}
		}else{
			return new ActionForward("/rights/zshagentuserlist.jsp");
		}
		
	} 
	/**
	 * 取中石化下级用户金额
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * autor:谭万龙
	 * date:2014-04-26
	 */
	public ActionForward zshuserextact(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MobileChargeService service = new MobileChargeService();
		String userNo = request.getParameter("userNo");
		String userSNo = request.getParameter("userSNo");
		String chargeFee = request.getParameter("chargeFee");
		double mon = FloatArith.yun2li(chargeFee);
		if(mon<=0){
			request.setAttribute("mess", "提取金额不能小于0");
			return new ActionForward("/rights/sysuser.do?method=getZshUserInfo&uid="+userSNo);
		}
		String tradePass = request.getParameter("tradePass");
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
		"userSession");
		SysUser user = new SysUser();
		String fundAcct01 = service.getUserFundAcct("01", userNo);
		String fundAcct02 = service.getUserFundAcct("01", userSNo);
		if (null != tradePass
				&& !"".equals(tradePass)) {
			if (!userForm.getDealpass().equals(MD5.encode(tradePass))) {
				request.setAttribute("mess", "交易密码输入错误");
				return new ActionForward("/rights/sysuser.do?method=getZshUserInfo&uid="+userSNo);
			}
		} else {
			request.setAttribute("mess", "交易密码输入错误");
			return new ActionForward("/rights/sysuser.do?method=getZshUserInfo&uid="+userSNo);
		}
		
		String accLeft=user.getZshUseLeft(userSNo);
		if(accLeft!=null){
			BigDecimal a = new BigDecimal(accLeft);
			BigDecimal b = new BigDecimal(chargeFee);
			if(a.subtract(b).floatValue()<100){
				request.setAttribute("mess", "提取金额超过最大提取金额");
				return new ActionForward("/rights/sysuser.do?method=getZshUserInfo&uid="+userSNo);
			}
		}else{
			request.setAttribute("mess", "资金账余额不存在");
			return new ActionForward("/rights/sysuser.do?method=getZshUserInfo&uid="+userSNo);
		}
		
		int tk = user.zshextact(userNo,userSNo,mon,fundAcct01,fundAcct02);
		if (tk == 0) {
			request.setAttribute("mess", "交易成功");
		} else {
			request.setAttribute("mess", "交易失败");
		}
		return new ActionForward("/rights/sysuser.do?method=getZshUserInfo&uid="+userSNo);
	} 
	
	/**
	 * 验证用户输入的交易密码
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validatePWD(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
		"userSession");
		String flag="0";
		String pwd=request.getParameter("userpwd");
		if(null==pwd||pwd==""){
			flag="1";//密码错误
		}else if(!MD5.encode(pwd).equals(userForm.getDealpass())){
			flag="1";
		}
		PrintWriter pWriter = response.getWriter();
		pWriter.print(flag);
		pWriter.flush();
		pWriter.close();
		return null;
	}
	
	/**
	 * 中石化代办点添加附属油站
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward addOilStation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userForm = (SysUserForm) form;
		if (null == userForm.getUsername()
				|| null == userForm.getUserpassword()
				|| null == userForm.getDealpass()
				|| null == userForm.getWltarea()
				|| null == userForm.getUser_site_city()) {
			request.setAttribute("mess", "所填信息不完整");
			return new ActionForward("/rights/oilstationadd.jsp");
		}
		SysUserForm userSession = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		SysRole role = new SysRole();
		String roleId=role.getRoleID(userSession.getUsersite());
		if(roleId==null||roleId.length()==0){
			request.setAttribute("mess", "添加附属油站失败,未找到默认角色,请联系管理员");
			return new ActionForward("/rights/oilstationadd.jsp");
		}
		userForm.setExp1(userSession.getUsername());
		userForm.setUser_role(roleId);
		userForm.setParent_id(userSession.getParent_id());
		userForm.setUsersite(userSession.getUsersite());
		SysUser user = new SysUser();
		String k = user.addUser(userForm);
		if (null != k && !"-1".equals(k) && !"-2".equals(k)) {
			request.setAttribute("downnum", k);
			request.setAttribute("parentid", userSession.getUser_id());
			request.setAttribute("mess", "开户成功");
			// return new ActionForward("/employ/whtuserandparentemploy.jsp");
			return new ActionForward("/rights/oilstationadd.jsp");
		} else if ("-1".equals(k)) {
			request.setAttribute("mess", "每天最多只能添加50个下级");
			return new ActionForward("/rights/oilstationadd.jsp");
		} else if ("-2".equals(k)) {
			request.setAttribute("mess", "最多只能添加1000个下级代理点");
			return new ActionForward("/rights/oilstationadd.jsp");
		} else {
			request.setAttribute("mess", "开户失败");
			return new ActionForward("/rights/oilstationadd.jsp");
		}
	}
	
	/**
	 * 附属油站列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward oilstationlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userForm = (SysUserForm) form;
		SysUser user = new SysUser();
		PageAttribute page = new PageAttribute(userForm.getCurPage(),
				Constant.PAGE_SIZE);
		SysUserForm user1 = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		page.setRsCount(user.countoilstation(user1.getUsername(),userForm));
		List list = user.getOilstationList(user1.getUsername(), page,
				userForm);
		request.setAttribute("userList", list);
		request.setAttribute("page", page);
		request.setAttribute("uform", userForm);
		return new ActionForward("/rights/oilstationlist.jsp");
	}
	
	/**
	 * 修改默认区号
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateArea(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Object obj =request.getSession().getAttribute("userSession");
		if(obj==null){
    		request.setAttribute("mes", "请重新重新登陆!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
		}
		SysUserForm user1 = (SysUserForm) obj;
		String area=request.getParameter("tradepassword");
    	if(null==area||area.length()>4||area.length()<3){
    		request.setAttribute("mes", "区号填写有误!");
			return new ActionForward("/telcom/telcomPhonepay.jsp");
    	}
		SysUser user = new SysUser();
		user.getdefultAR(user1.getUserno(),1, area,null);
		request.setAttribute("mes", "操作成功");
		return new ActionForward("/telcom/telcomPhonepay.jsp");
	}
	
}