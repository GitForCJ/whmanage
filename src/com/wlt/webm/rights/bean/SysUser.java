package com.wlt.webm.rights.bean;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oufei.OUConstat;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.TZ.tool.DESUtil;
import com.wlt.webm.business.bean.AcctBillBean;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.db.DBService;
import com.wlt.webm.db.DBToolSCP;
import com.wlt.webm.message.YMmessage;
import com.wlt.webm.pccommon.Constants;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scpdb.DBLog;
import com.wlt.webm.scputil.MD5Util;
import com.wlt.webm.scputil.bean.FacctBillBean;
import com.wlt.webm.tool.FloatArith;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.DesUtil;
import com.wlt.webm.util.MD5;
import com.wlt.webm.util.PageAttribute;

public class SysUser {

	/**
	 * 登陆验证
	 * 
	 * @param userForm
	 *            用户信息表单
	 * @param request
	 * @return 0 系统繁忙 1、成功；-10、用户不存在；-20、状态不可用 -30密码错误 -40 密码输错五次账号冻结24小时
	 * @throws Exception
	 */
	public int login(SysUserForm userForm, HttpServletRequest request,
			String str, String mac) throws Exception {
		String login = userForm.getUsername();
		DesUtil desObj = new DesUtil();
        String key = "ac3401b"+MD5Util.MD5Encode("whtech_WH#TE@CH!0755", "UTF-8").substring(8)+"bcaedf";
        String data = userForm.getUserpassword();
        String dec = desObj.strDec(data, key, null, null);
		String password = MD5.encode(dec);
		DBService db = null;
		try {
			db = new DBService(Constants.DBNAME_SCP);
			int n = db
					.getInt("SELECT errornum-total FROM sys_userloginLimit WHERE login='"
							+ login + "'");
			if (n == -1) {
				return -40;
			}

			StringBuffer sql = new StringBuffer();
			sql
					.append(
							"select a.user_no,a.user_activate,a.user_id,a.user_login,a.user_pass,a.user_role,a.user_site,a.user_tel,a.user_shortflag,a.user_feeshortflag,a.trade_pass, a.user_city, a.user_mail, a.user_adress, a.user_ename,a.user_status,a.user_pt,b.sa_zone,c.sr_type,a.exp3,a.exp2,c.sr_desc,a.exp1 ")
					.append(" from sys_user a, sys_area b ,sys_role c")
					.append(
							" where a.user_login=? and b.sa_id=a.user_city and c.sr_id=a.user_role");

			String[] params = { login };
			String[] fields = { "userno", "user_activate", "user_id",
					"username", "userpassword", "user_role", "usersite",
					"phone", "shortflag", "feeshortflag", "dealpass",
					"user_site_city", "usermail", "address", "userename",
					"userstatus", "parent_id", "sa_zone", "roleType", "exp3","exp2","srdesc","exp1" };

			db.populate(userForm, fields, sql.toString(), params);

			// 判断角色是否存在
			if (userForm.getUser_role() == null) {
				return -10;
			}
			// 判断角色是否销户
			if (!"0".equals(userForm.getUserstatus())) {
				return -20;
			}
			if ((null != str)
					&& (userForm.getRoleType().equals("1") || userForm.getRoleType().equals("0"))) {
				if(!"1".equals(userForm.getSrdesc()))
					return -50;
			}
			// mac地址验证
			if ("0".equals(userForm.getExp3())) {
				if("undefined".equals((mac+"").trim()) || "".equals((mac+"").trim()))
				{
					return -61;
				}
				else
				{
					String macArry=db.getString("SELECT macaddress FROM mac WHERE userno='"+userForm.getUserno()+"'",null);
					if("".equals(macArry) || macArry==null)
					{
						return -60;
					}
					else
					{
						if(macArry.indexOf(mac)==-1)
						{
							return -60;
						}
					}
				}
			}
			// 判断密码是否正确
			if (!userForm.getUserpassword().equals(password)) {
				db
						.update("UPDATE sys_userloginLimit SET errornum=errornum+1 ,TIME='"
								+ Tools.getOtherHour(6)
								+ "' WHERE login='"
								+ login + "'");
				return db
						.getInt("SELECT errornum-total FROM sys_userloginLimit WHERE login='"
								+ login + "'");
			}

			return 1;
		} catch (Exception ex) {
			Log.error("用户登录:" + ex.toString());
			return -100;
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}

	/**
	 * 验证用户名和密码
	 * 
	 * @param userForm
	 *            用户信息表单
	 * @param request
	 * @return 1、成功；-1、用户名不存在；-2、密码错误；
	 * @throws Exception
	 */
	public int login1(SysUserForm userForm, HttpServletRequest request)
			throws Exception {
		String login = userForm.getUsername();
		// String password = MD5.encode(userForm.getPassword());
		DBService db = new DBService(Constants.DBNAME_SCP);
		try {
			db.setAutoCommit(false);
			StringBuffer sql = new StringBuffer();
			sql
					.append(
							"select a.user_id,b.user_name,b.user_pass,a.user_role,a.user_site,a.user_tel,b.user_mac,b.user_macflag,b.user_shortflag,b.user_phone,c.sa_zone,d.sr_type,b.user_feeshortflag,b.user_dealpass, c.sa_id, a.user_mail, a.user_adress, a.user_ename, c.sa_name")
					.append(
							" from sys_user a left join sys_loginuser b on b.user_id=a.user_id")
					.append(
							" left join sys_area c on a.user_site_city = c.sa_id")
					.append(" left join sys_role d on a.user_role = d.sr_id")
					.append(" where b.user_name=?");

			String[] params = { login };
			String[] fields = { "user_id", "login", "password", "user_role",
					"usersite", "usertel", "mac", "macflag", "shortflag",
					"phone", "sa_zone", "roleType", "feeshortflag", "dealpass",
					"user_site_city", "usermail", "address", "userename",
					"wltarea" };

			db.populate(userForm, fields, sql.toString(), params);

			// 判断角色是否存在
			if (userForm.getUser_role() == null) {
				return -1;
			}
			int state = 0;
			try {
				String sql2 = "select user_status from sys_user where user_name='"
						+ login + "'";
				state = db.getInt(sql2.toString());
			} catch (Exception ex) {
				throw ex;
			}
			// 判断角色是否销户
			if (state == 2) {
				return -8;
			}
			/*
			 * // 判断密码是否正确 if (!userForm.getPassword().equals(password)) {
			 * return -2; }
			 */
			return 1;
		} catch (Exception ex) {
			db.rollback();
			throw ex;
		} finally {
			db.close();
		}
	}

	/**
	 * 根据用户名获取资金账号
	 * 
	 * @throws Exception
	 */
	public String getFundacctByUserName(String username) throws Exception {
		DBService db = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql
					.append("select b.fundacct")
					.append(
							" from sys_user a left join wlt_facct b on a.user_id=b.termid")
					.append(
							" left join sys_loginuser c on a.user_id = c.user_id")
					.append(" where c.user_name='" + username + "'");

			return db.getString(sql.toString());
		} catch (Exception ex) {
			throw ex;
		} finally {
			db.close();
		}
	}

	/**
	 * 获得PC用户的功能权限列表
	 * 
	 * @param userole
	 *            角色编号
	 * @return List(String[])功能权限列表
	 * @throws SQLException
	 */
	public List getMenuList(String userole) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append("select b.sm_id,b.sm_name,b.sm_link,b.sm_pid").append(
				" from sys_sm_power a,sys_menu b").append(
				" where a.sm_id=b.sm_id and a.sr_id=").append(userole).append(
				" order by b.sm_order");
		return DBToolSCP.getList(sql.toString());
	}

	/**
	 * 获取角色类型
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getUserRole(String userId) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql
				.append("select b.sr_type from sys_user a left join sys_role b on a.user_role = b.sr_id where a.user_id = '"
						+ userId + "'");
		String result = dbService.getString(sql.toString());
			if (null != dbService) {
				dbService.close();
			}
			return result;
	}
	
	/**
	 * 根据用户号码获取角色类型
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getUserLoginRole(String userlogin) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql
				.append("select b.sr_type from sys_user a left join sys_role b on a.user_role = b.sr_id where a.user_login = '"
						+ userlogin + "'");
		String result = dbService.getString(sql.toString());
			if (null != dbService) {
				dbService.close();
			}
			return result;
	}

	/**
	 * 注册用户
	 * 
	 * @param useForm
	 *            用户信息
	 * @return int 0 1
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	public int add(SysUserForm useForm) {
		DBService db = null;
		String[] authinfo = null;
		try {
//			authinfo = AuthenticatorServer.genSecret(useForm.getUsername(),
//					"whthost");
			authinfo = AuthenticatorServer.genSecret();
			if (null == authinfo) {
				return 2;
			}
			db = new DBService();
			db.setAutoCommit(false);
			String time = Tools.getNow3();
			int a = db.getInt("select MAX(user_id) FROM sys_user") + 1;
			String userno = Tools.add0(Tools.buildRandom(3)+""+a + "", 10);
			Object[] obj0 = { null, userno, useForm.getUsername(),
					MD5.encode(useForm.getUserpassword()),
					MD5.encode(useForm.getDealpass()), useForm.getUser_role(),
					useForm.getUserename(), useForm.getParent_id(), 0,
					useForm.getWltarea(), useForm.getUser_site_city(), time, 1,
					useForm.getPhone(), useForm.getAddress(),
					useForm.getUsermail(), 0, 0, useForm.getOperatename(),
					useForm.getUsersf(), null };
			db.logData(21, obj0, "sys_user");
			String account = Tools.getFacct(a);
			Object[] obj1 = { account, 0, 0, 1, userno };
			db.logData(5, obj1, "wht_facct");
			Object[] obj2 = { account + "01", account, "01", 0, 0 };
			Object[] obj3 = { account + "02", account, "02", 0, 0 };
			Object[] obj4 = { account + "03", account, "03", 0, 0 };
			db.logData(5, obj2, "wht_childfacct");
			db.logData(5, obj3, "wht_childfacct");
			db.logData(5, obj4, "wht_childfacct");
			String sql1 = "INSERT INTO sys_userloginLimit(login,userno,errornum,total) VALUES(?,?,?,?)";
			Object[] params = new Object[] { useForm.getUsername(), userno, 1,
					12 };
			db.update(sql1, params);
			String sql2 = "INSERT INTO mac(userno,googlesign,twodimensionalcode,ext1)  VALUES(?,?,?,?)";
//			db.update(sql2, new Object[] { userno, authinfo[0], com.wlt.webm.util.Tools.getImageStr(authinfo[1]),authinfo[1]});
			db.update(sql2, new Object[] { userno, authinfo[0], null,authinfo[1]});
			db.commit();
		} catch (SQLException e) {
			db.rollback();
			e.printStackTrace();
			Log.error("注册异常:" + e.toString());
			return 1;
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return 0;
	}

	/**
	 * 获得子功能权限列表
	 * 
	 * @param menuList
	 *            全部功能权限
	 * @param parentMenuId
	 *            父菜单编号
	 * @return List(String[])子功能权限列表
	 */
	@SuppressWarnings("unchecked")
	public List getChildMenuList(List menuList, String parentMenuId) {
		List childMenuList = new ArrayList();

		for (int i = 0, ii = menuList.size(); i < ii; i++) {
			String[] menu = (String[]) menuList.get(i);
			if (menu[3].equals(parentMenuId)) {
				childMenuList.add(menu);
			}
		}

		return childMenuList;
	}

	/**
	 * 获取用户密码
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getPassWord(String userId) throws Exception {
		DBService dbService = new DBService();
		String result = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql
					.append("SELECT user_dealpass FROM sys_loginuser WHERE user_id = '"
							+ userId + "'");
			result = dbService.getString(sql.toString());

		} finally {
			if (null != dbService) {
				dbService.close();
			}
		}
		return result;
	}

	/**
	 * 获取用户信息列表====
	 * 
	 * @return 用户列表
	 * @throws Exception
	 */
	public List getSysUserList(String srType, HttpSession session,
			PageAttribute page, SysUserForm uForm) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql
					.append("select a.user_login,a.user_no,b.sr_typename,a.user_status,c.fundacct,a.exp1,e.phone");
			sql
					.append(" from sys_user a left join wht_facct c on c.user_no = a.user_no Left JOIN sys_hfk e on e.resourceid=a.user_no LEFT JOIN  sys_role d on a.user_role = d.sr_id left join sys_roletype b on d.sr_type = b.sr_type");
			sql.append(" where 1=1 ");
			if (null != uForm.getUser_id() && !"".equals(uForm.getUser_id())) {
				sql.append(" and a.user_no = '" + uForm.getUser_id()+"'");
				paraBuffer.append("&user_no=" + uForm.getUser_id());
			}
			if (null != uForm.getUsername() && !"".equals(uForm.getUsername())) {
				sql.append(" and a.user_login = '" + uForm.getUsername() + "'");
				paraBuffer.append("&user_login=" + uForm.getUsername());
			}
			if (null != uForm.getRoleType() && !"".equals(uForm.getRoleType())) {
				sql.append(" and b.sr_type = " + uForm.getRoleType());
				paraBuffer.append("&roleType=" + uForm.getRoleType());
			}
			if (null != uForm.getUserstatus()
					&& !"".equals(uForm.getUserstatus())) {
				sql.append(" and a.user_status = " + uForm.getUserstatus());
				paraBuffer.append("&user_status=" + uForm.getUserstatus());
			}
			if (null != uForm.getStartDate()
					&& !"".equals(uForm.getStartDate())) {
				sql.append(" and a.user_createdate > '"
						+ uForm.getStartDate().replaceAll("-", "") + "000000'");
				paraBuffer.append("&startDate=" + uForm.getStartDate());
			}
			if (null != uForm.getEndDate() && !"".equals(uForm.getEndDate())) {
				sql.append(" and a.user_createdate < '"
						+ uForm.getEndDate().replaceAll("-", "") + "235959'");
				paraBuffer.append("&endDate=" + uForm.getEndDate());
			}
			if (null != uForm.getExp1() && !"".equals(uForm.getExp1())) {
				sql.append(" and a.exp1 = '" + uForm.getExp1() + "'");
				paraBuffer.append("&exp1=" + uForm.getExp1());
			}
			uForm.setParamUrl(paraBuffer.toString());
			// 查询当前登录用户角色类型
			SysUserForm userForm = (SysUserForm) session
					.getAttribute("userSession");
			String sqlRole = "select sr_type from sys_role where sr_id="
					+ userForm.getUser_role();
			int userRoleType = dbService.getInt(sqlRole);
			if (userRoleType != 0 && userRoleType != 1) {
				sql.append(" and a.user_pt = " + userForm.getUser_id()
						+ " and a.user_site=" + userForm.getUsersite());
			}
			if (userRoleType == 1) {
				sql
						.append(" and a.user_site="
								+ userForm.getUsersite()
								+ " and d.sr_type<>0 and d.sr_type<>4  and d.sr_type<>1");
			}
			sql.append(" limit " + (page.getCurPage() - 1) * page.getPageSize()
					+ "," + page.getPageSize());
			List list = dbService.getList(sql.toString());
			for (Object tmp : list) {
				String[] temp = (String[]) tmp;
				// String userid = temp[1];
				// //查询
				// String fcctSql =
				// "select sum(accountleft) from wht_childfacct where fundacct =(SELECT fundacct from wht_facct where user_no = "
				// +userid+")";
				// temp[5] = dbService.getString(fcctSql);
				if (null != temp[3] && !"".equals(temp[3])) {
					if ("0".equals(temp[3])) {
						temp[3] = "正常";
					} else if ("1".equals(temp[3])) {
						temp[3] = "注销";
					} else if ("2".equals(temp[3])) {
						temp[3] = "暂停";
					}
				}
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (null != dbService) {
				dbService.close();
			}
		}

	}

	/**
	 * 获取用户信息列表
	 * 
	 * @return 用户列表
	 * @throws Exception
	 */
	public List getSysUserList(String srType, HttpSession session)
			throws Exception {
		DBService dbService = new DBService();

		StringBuffer sql = new StringBuffer();
		sql
				.append("select a.user_name,a.user_no,b.sr_typename,a.user_status,c.accountleft");
		sql
				.append(" from sys_user a  left join wlt_facct c on c.termid = a.user_id LEFT JOIN  sys_role d on a.user_role = d.sr_id left join sys_roletype b on d.sr_type = b.sr_type");
		if (null != srType && !"".equals(srType)) {
			sql.append("  where d.sr_type = " + srType);
		}
		// 查询当前登录用户角色类型
		SysUserForm userForm = (SysUserForm) session
				.getAttribute("userSession");
		String sqlRole = "select sr_type from sys_role where sr_id="
				+ userForm.getUser_role();
		int userRoleType = dbService.getInt(sqlRole);
		if (0 != userRoleType && 1 != userRoleType) {
			sql.append(" and a.user_pt = " + userForm.getUser_id());
		}
		List list = dbService.getList(sql.toString());
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			String userid = temp[0];
			// 查询
			String fcctSql = "select sum(usableleft) from wlt_childfacct where fundacct = (SELECT fundacct from wlt_facct where termid = "
					+ userid + ")";
			temp[9] = dbService.getString(fcctSql);
			if (null != temp[3] && !"".equals(temp[3])) {
				if ("0".equals(temp[3])) {
					temp[3] = "正常";
				} else if ("1".equals(temp[3])) {
					temp[3] = "禁用";
				} else if ("2".equals(temp[3])) {
					temp[3] = "销户";
				}
			}
		}
		return list;
	}
	/**
	 * 获取用户信息列表总数
	 * 
	 * @return 用户列表
	 * @throws Exception
	 */
	public int SumCountSysUser(String srType, HttpSession session) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) ");
			sql.append(" from sys_user a left join wht_facct c on c.user_no = a.user_no LEFT JOIN  sys_role d on a.user_role = d.sr_id left join sys_roletype b on d.sr_type = b.sr_type");
			sql.append(" where 1=1 ");
			
			// 查询当前登录用户角色类型
			SysUserForm userForm = (SysUserForm) session
					.getAttribute("userSession");
			String sqlRole = "select sr_type from sys_role where sr_id="
					+ userForm.getUser_role();
			int userRoleType = dbService.getInt(sqlRole);
			if (userRoleType != 0 && userRoleType != 1) {
				sql.append(" and a.user_pt = " + userForm.getUser_id()
						+ " and a.user_site=" + userForm.getUsersite());
			}
			if (userRoleType == 1) {
				sql.append(" and a.user_site="
								+ userForm.getUsersite()
								+ " and d.sr_type<>0 and d.sr_type<>4 and d.sr_type<>1");
			}
			int n = dbService.getInt(sql.toString());
			return n;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (null != dbService) {
				dbService.close();
			}
		}

	}
	/**
	 * 获取用户信息列表==========
	 * 
	 * @return 用户列表
	 * @throws Exception
	 */
	public int countSysUser(String srType, HttpSession session,
			SysUserForm uForm) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) ");
			sql
					.append(" from sys_user a left join wht_facct c on c.user_no = a.user_no LEFT JOIN  sys_role d on a.user_role = d.sr_id left join sys_roletype b on d.sr_type = b.sr_type LEFT JOIN wht_childfacct f ON (f.fundacct = c.fundacct AND f.type='01')");
			sql.append(" where 1=1 ");

			if (null != uForm.getUser_id() && !"".equals(uForm.getUser_id())) {
				sql.append(" and a.user_id = " + uForm.getUser_id());
				paraBuffer.append("&user_id=" + uForm.getUser_id());
			}
			if (null != uForm.getUsername() && !"".equals(uForm.getUsername())) {
				sql.append(" and a.user_login = '" + uForm.getUsername() + "'");
				paraBuffer.append("&user_login=" + uForm.getUsername());
			}
			if (null != uForm.getRoleType() && !"".equals(uForm.getRoleType())) {
				sql.append(" and d.sr_type = " + uForm.getRoleType());
				paraBuffer.append("&roleType=" + uForm.getRoleType());
			}
			if (null != uForm.getUserstatus()
					&& !"".equals(uForm.getUserstatus())) {
				sql.append(" and a.user_status = " + uForm.getUserstatus());
				paraBuffer.append("&user_status=" + uForm.getUserstatus());
			}
			if (null != uForm.getStartDate()
					&& !"".equals(uForm.getStartDate())) {
				sql.append(" and a.user_createdate > '"
						+ uForm.getStartDate().replaceAll("-", "") + "000000'");
				paraBuffer.append("&startDate=" + uForm.getStartDate());
			}
			if (null != uForm.getEndDate() && !"".equals(uForm.getEndDate())) {
				sql.append(" and a.user_createdate < '"
						+ uForm.getEndDate().replaceAll("-", "") + "235959'");
				paraBuffer.append("&endDate=" + uForm.getEndDate());
			}
			if (null != uForm.getExp1() && !"".equals(uForm.getExp1())) {
				sql.append(" and a.exp1 = '" + uForm.getExp1() + "'");
				paraBuffer.append("&exp1=" + uForm.getExp1());
			}
			if(null!=uForm.getName() && !"".equals(uForm.getName()))
			{
				sql.append(" and a.user_ename = '" + uForm.getName() + "'");
				paraBuffer.append("&user_ename=" + uForm.getName());
			}
			
			uForm.setParamUrl(paraBuffer.toString());
			// 查询当前登录用户角色类型
			SysUserForm userForm = (SysUserForm) session
					.getAttribute("userSession");
			String sqlRole = "select sr_type from sys_role where sr_id="
					+ userForm.getUser_role();
			int userRoleType = dbService.getInt(sqlRole);
			if (userRoleType != 0 && userRoleType != 1) {
				sql.append(" and a.user_pt = " + userForm.getUser_id()
						+ " and a.user_site=" + userForm.getUsersite());
			}
			if (userRoleType == 1) {
				sql
						.append(" and a.user_site="
								+ userForm.getUsersite()
								+ " and d.sr_type<>0 and d.sr_type<>4 and d.sr_type<>1");
			}
			int n = dbService.getInt(sql.toString());
			return n;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (null != dbService) {
				dbService.close();
			}
		}

	}

	/**
	 * 获取用户信息列表(bank)
	 * 
	 * @return 用户列表
	 * @throws Exception
	 */
	public List getSysUserBankList(String srType, HttpSession session)
			throws Exception {
		DBService dbService = new DBService();

		StringBuffer sql = new StringBuffer();
		sql
				.append("select a.user_id,a.user_name,b.sr_typename,d.sr_name,a.user_mail,a.user_tel,a.user_status,a.user_createdate");
		sql
				.append(" from sys_user a LEFT JOIN  sys_role d on a.user_role = d.sr_id left join sys_roletype b on d.sr_type = b.sr_type");
		if (null != srType && !"".equals(srType)) {
			sql.append("  where d.sr_type = " + srType);
			// 查询当前登录用户角色类型
			SysUserForm userForm = (SysUserForm) session
					.getAttribute("userSession");
			String sqlRole = "select sr_type from sys_role where sr_id="
					+ userForm.getUser_role();
			int userRoleType = dbService.getInt(sqlRole);
			if (0 != userRoleType && 1 != userRoleType) {
				sql.append(" and a.user_pt = " + userForm.getUser_id());
			}
		}
		List list = dbService.getList(sql.toString());
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			if (null != temp[6] && !"".equals(temp[6])) {
				if ("0".equals(temp[6])) {
					temp[6] = "正常";
				} else if ("1".equals(temp[6])) {
					temp[6] = "禁用";
				} else if ("2".equals(temp[6])) {
					temp[6] = "销户";
				}
			}
		}
		return list;
	}

	/**
	 * 获取用户信息列表(userinterface)
	 * 
	 * @return 用户列表
	 * @throws Exception
	 */
	public List getSysUserInterfaceList(String srType, HttpSession session)
			throws Exception {
		DBService dbService = new DBService();

		StringBuffer sql = new StringBuffer();
		sql
				.append("select a.user_id,a.user_name,b.sr_typename,d.sr_name,a.user_status");
		sql
				.append(" from sys_user a LEFT JOIN  sys_role d on a.user_role = d.sr_id left join sys_roletype b on d.sr_type = b.sr_type");
		if (null != srType && !"".equals(srType)) {
			sql.append("  where d.sr_type = " + srType);
			// 查询当前登录用户角色类型
			SysUserForm userForm = (SysUserForm) session
					.getAttribute("userSession");
			String sqlRole = "select sr_type from sys_role where sr_id="
					+ userForm.getUser_role();
			int userRoleType = dbService.getInt(sqlRole);
			if (0 != userRoleType && 1 != userRoleType) {
				sql.append(" and a.user_pt = " + userForm.getUser_id());
			}
		}
		List list = dbService.getList(sql.toString());
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			if (null != temp[4] && !"".equals(temp[4])) {
				if ("0".equals(temp[4])) {
					temp[4] = "正常";
				} else if ("1".equals(temp[4])) {
					temp[4] = "禁用";
				} else if ("2".equals(temp[4])) {
					temp[4] = "销户";
				}
			}
		}
		return list;
	}

	/**
	 * 获取用户信息(单个)
	 * 
	 * @return 用户信息
	 * @throws Exception
	 */
	public SysUserForm getUserInfo(String userid) throws Exception {
		DBService db = new DBService(Constants.DBNAME_SCP);
		SysUserForm userForm = new SysUserForm();
		try {
			StringBuffer sql = new StringBuffer();
			sql
					.append(
							"select a.user_id, a.user_login,a.user_no,a.user_ename,a.user_role,b.sr_name,a.user_mail,a.user_tel,a.user_status,a.user_site,a.user_createdate,c.fundacct,f.hfkid,f.phone,a.exp2,a.exp3,user_adress,convert(h.accountleft/1000,decimal(18,3)),a.user_shortflag,a.user_feeshortflag")
					.append(
							" from sys_user a left join sys_role b on a.user_role = b.sr_id "
									+ " left join wht_facct c on c.user_no = a.user_no" 
									+ " LEFT JOIN sys_hfk f on a.user_no = f.resourceid "
									+" left join wht_childfacct h on h.fundacct=c.fundacct and h.type='01'")
					.append(" where a.user_no=? ");

			String[] params = { userid };
			String[] fields = { "user_id", "username", "userno", "userename",
					"user_role", "role_name", "usermail", "phone",
					"userstatus", "usersite", "usercreatedate", "fundacct",
					"hfkID", "hfkphone", "usersf","exp3","address","left" ,"shortflag","feeshortflag"};

			db.populate(userForm, fields, sql.toString(), params);

			// if(null != userForm.getUserstatus()){
			// if("0".equals(userForm.getUserstatus())){
			// userForm.setUserstatus("正常");
			// }else if("1".equals(userForm.getUserstatus())){
			// userForm.setUserstatus("禁用");
			// }else if("2".equals(userForm.getUserstatus())){
			// userForm.setUserstatus("销户");
			// }
			// }
			return userForm;
		} catch (Exception ex) {
			throw ex;
		} finally {
			db.close();
		}
	}

	/**
	 * 更新用户
	 * 
	 * @param useForm
	 *            用户信息
	 * @return int
	 * @throws Exception
	 * @throws SQLException
	 */
	public int update(SysUserForm useForm) throws Exception {
		DBService db = null;
		try {
			db = new DBService(Constants.DBNAME_SCP);
			db.setAutoCommit(false);
			StringBuffer sql = new StringBuffer();
			sql.append("update sys_user set");
			// sql.append(" user_no='"+useForm.getUserno()+"'");
			if (null != useForm.getUserename()
					&& !"".equals(useForm.getUserename())) {
				sql.append(" user_ename='" + useForm.getUserename() + "'");
			}
			if (null != useForm.getUsermail()
					&& !"".equals(useForm.getUsermail())) {
				sql.append(" ,user_mail='" + useForm.getUsermail() + "'");
			}
			if (null != useForm.getPhone() && !"".equals(useForm.getPhone())) {
				sql.append(" ,user_tel='" + useForm.getPhone() + "'");
			}
			if (null != useForm.getUserstatus()
					&& !"".equals(useForm.getUserstatus())) {
				sql.append(" ,user_status='" + useForm.getUserstatus() + "'");
			}
			// sql.append(" ,user_site='"+useForm.getUsersite()+"'");
			if (null != useForm.getUserdesc()
					&& !"".equals(useForm.getUserdesc())) {
				sql.append(" ,user_desc='" + useForm.getUserdesc() + "'");
			}
			if(null!=useForm.getExp3() && !"".equals(useForm.getExp3()))
			{
				sql.append(" ,exp3="+useForm.getExp3());
			}
			if(null!=useForm.getShortflag()&&!"".equals(useForm.getShortflag())){
				sql.append(" ,user_shortflag="+useForm.getShortflag());
			}else{
				sql.append(" ,user_shortflag=1");
			}
			if(null!=useForm.getFeeshortflag()&&!"".equals(useForm.getFeeshortflag())){
				sql.append(" ,user_feeshortflag="+useForm.getFeeshortflag());
			}else{
				sql.append(" ,user_feeshortflag=1");
			}
			sql.append(" where");
			sql.append(" user_no='" + useForm.getUserno() + "'");

			System.out.println("++++++++++++" + sql.toString());
			db.update(sql.toString());

			if (null != useForm.getDealpass() || null != useForm.getFeepass()) {
				StringBuffer sBuffer = new StringBuffer();
				sBuffer.append("update sys_loginuser set");
				if (null != useForm.getDealpass()
						&& !"".equals(useForm.getDealpass())) {
					sBuffer.append(" user_dealpass='" + useForm.getDealpass()
							+ "'");
					if (null != useForm.getFeepass()
							&& !"".equals(useForm.getFeepass())) {
						sBuffer.append(" ,");
					}
				}
				if (null != useForm.getFeepass()
						&& !"".equals(useForm.getFeepass())) {
					sBuffer.append(" user_feepass='" + useForm.getFeepass()
							+ "'");
				}
				sBuffer.append(" where");
				sBuffer.append(" user_id='" + useForm.getUser_id() + "'");
				db.update(sBuffer.toString());
			}
			if ((null != useForm.getHfkID() && !"".equals(useForm.getHfkID()))
					|| (null != useForm.getHfkphone() && !"".equals(useForm
							.getHfkphone()))) {
				StringBuffer sBuffer = new StringBuffer();
				sBuffer.append("update sys_hfk set");
				if (null != useForm.getHfkphone()
						&& !"".equals(useForm.getHfkphone())) {
					sBuffer.append(" phone='" + useForm.getHfkphone() + "'");
					if (null != useForm.getHfkID()
							&& !"".equals(useForm.getHfkID())) {
						sBuffer.append(" ,");
					}
				}
				if (null != useForm.getHfkID()
						&& !"".equals(useForm.getHfkID())) {
					sBuffer.append(" hfkid='" + useForm.getHfkID() + "'");
				}
				sBuffer.append(" where");
				sBuffer.append(" resourceid='" + useForm.getUserno() + "'");
				System.out.println(sBuffer.toString());
				db.update(sBuffer.toString());
			}

			db.commit();
		} catch (Exception ex) {
			db.rollback();
			return 0;
		} finally {
			db.close();
		}
		return 1;
	}

	/**
	 * 添加慧付款用户信息
	 * 
	 * @param form
	 *            表单
	 * @param user
	 *            登录用户信息
	 * @return 1、成功；-1、手机重复；-2、终端不存在 -3,统一支付账户不存在 -4，终端和账户不匹配 -5 系统繁忙
	 * @throws Exception
	 */
	public int addHfkUser(SysUserForm form) throws Exception {
		String now = Tools.getNow3();
		StringBuffer sql = new StringBuffer();
		DBService db = new DBService();
		try {
			sql.append("select 1 from sys_hfk where phone=?");
			if (db.hasData(sql.toString(), new String[] { form.getHfkphone()
					.trim() })) {
				return -1;
			}
			sql.delete(0, sql.length());
			sql.append("select user_no from sys_user ").append(
					" where user_no='").append(form.getUserno()).append("'");
			String resourceid = db.getString(sql.toString());
			if (resourceid == null) {
				return -2;
			}
			sql.delete(0, sql.length());
			sql.append("select fundacct from wht_facct ").append(
					" where fundacct='").append(form.getFundacct()).append("'");
			String fundacct = db.getString(sql.toString());
			if (fundacct == null) {
				return -3;
			}
			sql.delete(0, sql.length());
			sql.append("select a.user_id from sys_user a,wht_facct b ").append(
					" where a.user_no=b.user_no and a.user_no='").append(
					resourceid).append("'").append(" and b.fundacct='").append(
					fundacct).append("'");

			String posid = db.getString(sql.toString());
			if (posid == null) {
				return -4;
			}
			sql.delete(0, sql.length());
			sql.append("insert into sys_hfk values(?,?,?,?,?,?,?)");
			String[] params = { form.getHfkphone().trim(), resourceid,
					fundacct, form.getHfkID(), now, form.getSfz(),
					form.getHfkgz() };
			db.update(sql.toString(), params);
		} catch (Exception e) {
			db.rollback();
			e.printStackTrace();
			return -5;
		} finally {
			db.close();
		}
		return 1;
	}

	/**
	 * 获取资金账户(单个)
	 * 
	 * @return
	 * @throws Exception
	 */
	public SysUserForm getUserAccount(String userid) {
		DBService db = null;
		SysUserForm userForm = new SysUserForm();
		try {
			db = new DBService();
			StringBuffer sql = new StringBuffer();
			sql.append("select a.type,a.accountleft").append(
					" from wht_childfacct a ").append(
					" where a.fundacct=(select fundacct from wht_facct where user_no ='"
							+ userid + "') ");

			List list = db.getList(sql.toString());

			for (Object object : list) {
				String[] temp = (String[]) object;
				if ("01".equals(temp[0])) {
					userForm.setAccountAmount(temp[1]);
				} else if ("02".equals(temp[0])) {
					userForm.setCommissionAmount(temp[1]);
				} else if ("03".equals(temp[0])) {
					userForm.setFrozenAmount(temp[1]);
				}
			}
			String flag = db
					.getString("select user_feeshortflag from sys_user where user_no='"
							+ userid + "'");
			userForm.setFeeshortflag(flag);
			return userForm;
		} catch (Exception ex) {
			return null;
		} finally {
			db.close();
		}
	}

	/**
	 * 获取资金账号子账号
	 * 
	 * @return 资金账号子账号
	 * @throws Exception
	 */
	public String[] getUserFundAcctBylogin(String fundAcct) {
		DBService db = null;
		try {
			db = new DBService();
			StringBuffer sql = new StringBuffer();
			sql.append("select b.state,b.fundacct").append(
					" from sys_user a,wht_facct b ").append(
					" where a.user_login = '" + fundAcct
							+ "' and a.user_no=b.user_no");
			return db.getStringArray(sql.toString());
		} catch (Exception ex) {
			return null;
		} finally {
			if(null!=db)
			db.close();
		}
	}

	/**
	 * 获取资金账号子账号
	 * 
	 * @return 资金账号子账号
	 * @throws Exception
	 */
	public String getUserFundAcct(String fundAcct) throws Exception {
		DBService db = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select state").append(" from wht_facct ").append(
					" where fundacct = '" + fundAcct + "'");
			return db.getString(sql.toString());
		} catch (Exception ex) {
			throw ex;
		} finally {
			db.close();
		}
	}

	/**
	 * 更新用户
	 * 
	 * @param useForm
	 *            用户信息
	 * @return int
	 * @throws Exception
	 */
	public int updateAccount(SysUserForm useForm) throws Exception {
		DBService db = new DBService();
		int result = 0;
		try {
			db.setAutoCommit(false);
			// StringBuffer sql = new StringBuffer();
			// sql.append("update wlt_childfacct set");
			// sql.append(" usableleft='"+useForm.getAccountAmount()+"'");
			// sql.append(" where");
			// sql.append(
			// " accttypeid = '02' and fundacct = (select fundacct from wlt_facct where termid = "
			// +useForm.getUser_id()+" )");
			// result += db.update(sql.toString());
			//	        
			// StringBuffer sql1 = new StringBuffer();
			// sql1.append("update wlt_childfacct set");
			// sql1.append(" usableleft='"+useForm.getCommissionAmount()+"'");
			// sql1.append(" where");
			// sql1.append(
			// " accttypeid = '03' and fundacct = (select fundacct from wlt_facct where termid = "
			// +useForm.getUser_id()+" )");
			// result += db.update(sql1.toString());
			//	        
			// StringBuffer sql2 = new StringBuffer();
			// sql2.append("update wlt_childfacct set");
			// sql2.append(" usableleft='"+useForm.getFrozenAmount()+"'");
			// sql2.append(" where");
			// sql2.append(
			// " accttypeid = '04' and fundacct = (select fundacct from wlt_facct where termid = "
			// +useForm.getUser_id()+" )");
			// result += db.update(sql2.toString());

			StringBuffer sql3 = new StringBuffer();
			sql3.append("update wlt_facct set");
			sql3.append(" state='" + useForm.getState() + "'");
			sql3.append(" where");
			sql3.append(" termid = " + useForm.getUser_id());
			result += db.update(sql3.toString());
			db.commit();
		} catch (Exception ex) {
			db.rollback();
			throw ex;
		} finally {
			db.close();
		}
		return result;
	}

	/**
	 * 资金账户充值或者提现
	 * 
	 * @param userno
	 * @param mon
	 * @param sepNo
	 * @param nowTime
	 * @param op
	 * @param orderid 
	 * @param optenation_userno 操作用户系统编号
	 * @return 0成功 1 异常系统繁忙 2 未查询到相应账户 3 余额不足
	 */
	public int charge(String userno, String mon, String sepNo, String nowTime,
			String op,String orderid,String optenation_userno) {
		DBService db = null;
		int result = 0;
		try {
			String exp = (null != op && op.equals("0")) ? "管理员提现" : "管理员充值";
			int n = (null != op && op.equals("0")) ? 9 : 10;
			float money = (null != op && op.equals("0")) ? -Float.parseFloat(mon)
					: Float.parseFloat(mon);
			int m = (null != op && op.equals("0")) ? 1 : 2;
			db = new DBService();
			String[] str = db
					.getStringArray("select childfacct,accountleft from wht_childfacct where type='01' and "
							+ "fundacct=("
							+ "select fundacct from wht_facct where user_no='"
							+ userno + "')");
			if (null == str || str.length != 2) {
				return 2;
			}
			String account = str[0];
			float left = Float.parseFloat(str[1]);
			if (n == 9) {
				if (left + money < 0) {
					return 3;
				}
			}
			db.setAutoCommit(false);
			if(null!=orderid&&orderid.length()>14){
				sepNo=orderid;
				if(db.hasData("select id from wht_transactionRecord  where recordStatus=0 and orderNumber='"+orderid+"'"+" and userNumber='"+userno+"'")){
					db.update("update wht_transactionRecord set recordStatus=1 where orderNumber='"+orderid+"'");
				}else{
					return 4;
				}
			}
			
			/**
			 * 充值,提现 审核功能
			 */
			db.update("INSERT INTO wht_Account_operation_admin(openation_userno,openation_type,openation_fee," +
					"openation_time,openation_state,be_openation_userno,be_openation_childfacct) " +
					"VALUES('"+optenation_userno+"',"+n+","+Float.parseFloat(mon)+",'"+Tools.getNow()+"','-1','"+userno+"','"+str[0]+"')");
			
//			String tablename = "wht_acctbill_" + nowTime.substring(2, 6);
//			Object[] obj1 = { null, account, sepNo, account, nowTime,
//					Float.parseFloat(mon), Float.parseFloat(mon), n, exp, 0,
//					nowTime, left + money, sepNo, "", m };
//			db.logData(15, obj1, tablename);
//			db.update("update wht_childfacct set accountleft=accountleft+"
//					+ money + " where childfacct='" + str[0] + "'");
//			db.update("update wht_facct set accountleft=accountleft+" + money
//					+ " where fundacct='" + str[0].substring(0, 15) + "'");
			db.commit();
		} catch (Exception ex) {
			db.rollback();
			Log.error("管理员充值,提现异常,,ex："+ex.toString());
			return 1;
		} finally {
			if(db!=null){
				db.close();
				db=null;
			}
		}
		return result;
	}

	/**
	 * 获取用户信息列表
	 * 
	 * @return 用户列表
	 * @throws Exception
	 */
	public List getUserAccountList() throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append("select a.fundacct,b.user_name");
		sql.append(" from sys_user b left join wlt_facct a ");
		sql.append("  on a.termid = b.user_id ");
		List list = dbService.getList(sql.toString());
		return list;
	}

	/**
	 * 额度转移
	 * 
	 * @param fundAcct01
	 * @param toAccount
	 * @param fee
	 * @param seqNo
	 * @param time
	 * @param remark
	 * @param tradeCode
	 * @param ownLogin
	 * @param otherLogin
	 * @return
	 */
	public int trans(String fundAcct01, String toAccount, double fee,
			String seqNo, String time, String remark, String tradeCode,
			String ownLogin,String otherLogin) {
		String sepNo1 = Tools.getSeqNo(toAccount.substring(5));
		DBService db = null;
		try {
			db = new DBService();
			String a0 = "select accountleft from wht_childfacct where childfacct='"
					+ fundAcct01 + "'";
			String a1 = "select accountleft from wht_childfacct where childfacct='"
					+ toAccount + "01'";
			double oneleft = db.getDouble(a0);
			double toleft = db.getDouble(a1);
			db.setAutoCommit(false);
			int money = (int) fee;
			String b0 = "update wht_childfacct set accountleft=accountleft-"
					+ money + " where childfacct='" + fundAcct01 + "'";
			String b1 = "update wht_childfacct set accountleft=accountleft+"
					+ money + " where childfacct='" + toAccount + "01'";
			Object[] obj1 = { null, fundAcct01, seqNo, otherLogin, time, money,
					money, tradeCode, remark, 0, time, oneleft - money, sepNo1,
					toAccount, 1 };
			Object[] obj2 = { null, toAccount + "01", sepNo1, ownLogin,
					time, money, money, tradeCode, remark, 0, time,
					toleft + money, seqNo, fundAcct01.substring(0, 15), 2 };
			String tablename = "wht_acctbill_" + time.substring(2, 6);
			db.update(b0);
			db.update(b1);
			db.logData(15, obj1, tablename);
			db.logData(15, obj2, tablename);
			db.commit();
			return 0;
		} catch (Exception ex) {
			db.rollback();
			return 1;
		} finally {
			db.close();
		}
	}

	/**
	 * 佣金转押金
	 * 
	 * @param fromAccount
	 * @param toAccount
	 * @param fee
	 * @param seqNo
	 * @param time
	 * @param remark
	 * @param tradeCode
	 * @param tradeserial
	 * @return
	 * @throws Exception
	 */
	public int transChild(String fromAccount, String toAccount, double fee,
			String seqNo, String time, String remark, int tradeCode,
			String tradeserial) {
		DBService db = null;
		int result = 0;
		try {
			db = new DBService();
			String a0 = "select accountleft from wht_childfacct where childfacct='"
					+ fromAccount + "'";
			String a1 = "select accountleft from wht_childfacct where childfacct='"
					+ toAccount + "'";
			int oneleft = db.getInt(a0);
			int toleft = db.getInt(a1);
			db.setAutoCommit(false);
			String b0 = "update wht_childfacct set accountleft=accountleft-"
					+ fee + " where childfacct='" + fromAccount + "'";
			String b1 = "update wht_childfacct set accountleft=accountleft+"
					+ fee + " where childfacct='" + toAccount + "'";
			Object[] obj1 = { null, fromAccount, seqNo, fromAccount, time, fee,
					fee, tradeCode, remark, 0, time, oneleft - fee,
					tradeserial, toAccount, 1 };
			Object[] obj2 = { null, toAccount, tradeserial, toAccount, time,
					fee, fee, tradeCode, remark, 0, time, toleft + fee, seqNo,
					fromAccount, 2 };
			String tablename = "wht_acctbill_" + time.substring(2, 6);
			db.update(b0);
			db.update(b1);
			db.logData(15, obj1, tablename);
			db.logData(15, obj2, tablename);
			db.commit();
		} catch (Exception ex) {
			db.rollback();
		} finally {
			db.close();
		}
		return result;
	}

	public int retpoint(String id, String account, String fee, String seqNo,
			String time, String remark, String tradeCode, String tradeserial)
			throws Exception {
		com.wlt.webm.scpdb.DBService db = new com.wlt.webm.scpdb.DBService();
		int result = 0;
		try {
			db.setAutoCommit(false);

			StringBuffer sql1 = new StringBuffer();
			sql1.append("update wlt_childfacct set");
			sql1.append(" usableleft=usableleft+" + fee);
			sql1.append(" where");
			sql1.append(" childfacct = '" + account + "'");
			result += db.update(sql1.toString());

			StringBuffer sql4 = new StringBuffer();
			sql4.append("update wlt_facct set accountleft = accountleft+" + fee
					+ " where fundacct = '"
					+ account.substring(0, account.length() - 2) + "'");
			result += db.update(sql4.toString());

			StringBuffer sql6 = new StringBuffer();
			sql6
					.append("select usableleft from wlt_childfacct where childfacct = '"
							+ account + "'");
			String toAcctLeft = db.getString(sql6.toString());

			FacctBillBean facct = new FacctBillBean();
			facct.setFacctTrade(account, seqNo, account, time, String
					.valueOf(fee), tradeCode, remark, "0", time, toAcctLeft,
					tradeserial, "", "" + 2);
			DBLog dbLog = new DBLog(db);
			// 记录账户日志
			dbLog.insertAcctLog(facct);
			// 修改记录状态为 已返还
			StringBuffer sql5 = new StringBuffer();
			sql5.append("update total_jieti_account set state = 4 where id = "
					+ id);
			db.update(sql5.toString());
			db.commit();
		} catch (Exception ex) {
			db.rollback();
			throw ex;
		} finally {
			db.close();
		}
		return result;
	}

	/**
	 * 获取余额
	 */
	public String getUseLeft(String posid) {
		float usableleft = 0;
		DBService db = null;
		try {
			db = new DBService(Constants.DBNAME_SCP);
			String child = db
					.getString("select fundacct from wht_facct where user_no='"
							+ posid + "'")
					+ "01";
			String sql = "select accountleft from wht_childfacct where childfacct='"
					+ child + "'";
			usableleft = db.getFloat(sql);
		} catch (SQLException e) {
			return "0";
		} finally {
			if (null != db) {
				db.close();
			}
		}
		String str = FloatArith.li2yuan(usableleft + "") + "";
		return str;
	}

	/**
	 * 根据开户个数
	 * 
	 * @throws Exception
	 */
	public int getCountForUser(String userid) throws Exception {
		DBService db = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from sys_user where user_pt=" + userid);
			return db.getInt(sql.toString());
		} catch (Exception ex) {
			throw ex;
		} finally {
			db.close();
		}
	}

	/**
	 * 根据当天开户个数
	 * 
	 * @throws Exception
	 */
	public int getCountForUserToday(String userid, String nowDate)
			throws Exception {
		DBService db = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from sys_user where user_pt=" + userid
					+ " and user_createdate like '" + nowDate + "%'");
			return db.getInt(sql.toString());
		} catch (Exception ex) {
			throw ex;
		} finally {
			db.close();
		}
	}

	/**
	 * 用户是否激活
	 * 
	 * @param username
	 * @return true激活
	 */
	public boolean activate(String username) {
		boolean flag = false;
		String sql = "select isActivate from sys_user where user_name='"
				+ username + "'";
		DBService db = null;
		try {
			db = new DBService(Constants.DBNAME_SCP);
			int n = db.getInt(sql);
			if (2 == n) {
				flag = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return flag;
	}

	/**
	 * 获取用户信息列表(bank)
	 * 
	 * @return 用户列表
	 * @throws Exception
	 */
	public List getSysUserBankList1(HttpSession session, String name)
			throws Exception {
		DBService dbService = new DBService();

		StringBuffer sql = new StringBuffer();
		sql
				.append("select a.user_id,a.user_name,b.sr_typename,d.sr_name,a.user_mail,a.user_tel,a.user_status,a.user_createdate");
		sql
				.append(" from sys_user a LEFT JOIN  sys_role d on a.user_role = d.sr_id left join sys_roletype b on d.sr_type = b.sr_type");
		// if(null != srType && !"".equals(srType)){
		// sql.append("  where d.sr_type = "+srType);
		// //查询当前登录用户角色类型
		// SysUserForm userForm =
		// (SysUserForm)session.getAttribute("userSession");
		// String sqlRole =
		// "select sr_type from sys_role where sr_id="+userForm.getUser_role();
		// int userRoleType = dbService.getInt(sqlRole);
		// if(0 != userRoleType && 1 != userRoleType){
		// sql.append(" and a.user_pt = "+userForm.getUser_id());
		// }
		// }
		if (null != name && !name.equals("")) {
			sql.append("  where a.user_name='").append(name).append("'");
		}
		System.out.println("----" + sql.toString());
		List list = dbService.getList(sql.toString());
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			if (null != temp[6] && !"".equals(temp[6])) {
				if ("0".equals(temp[6])) {
					temp[6] = "正常";
				} else if ("1".equals(temp[6])) {
					temp[6] = "禁用";
				} else if ("2".equals(temp[6])) {
					temp[6] = "销户";
				}
			}
		}
		return list;
	}

	/**
	 * 提现
	 * 
	 * @param toAccount
	 * @param fee
	 * @param seqNo
	 * @param time
	 * @param remark
	 * @param tradeCode
	 * @param tradeserial
	 * @return
	 */
	public int tixian(String toAccount, String fee, String seqNo, String time,
			String remark, String tradeCode, String tradeserial) {
		com.wlt.webm.scpdb.DBService db = null;
		int result = 0;
		try {
			db = new com.wlt.webm.scpdb.DBService();
			db.setAutoCommit(false);
			StringBuffer sql = new StringBuffer();
			sql.append("update wlt_childfacct set");
			sql.append(" usableleft=usableleft-" + fee + "");
			sql.append(" where");
			sql.append(" accttypeid = '02' and fundacct = '" + toAccount + "'");
			result += db.update(sql.toString());

			StringBuffer sql3 = new StringBuffer();
			sql3.append("update wlt_facct set accountleft = accountleft-" + fee
					+ " where fundacct = '" + toAccount + "'");
			result += db.update(sql3.toString());

			StringBuffer sql6 = new StringBuffer();
			sql6
					.append("select usableleft from wlt_childfacct where childfacct = '"
							+ toAccount + "02'");
			String toAcctLeft = db.getString(sql6.toString());

			FacctBillBean facct1 = new FacctBillBean();
			facct1.setFacctTrade(toAccount + "02", seqNo, toAccount, time,
					String.valueOf(fee), tradeCode, remark, "0", time,
					toAcctLeft, tradeserial, "", "" + 1);
			DBLog dbLog = new DBLog(db);
			dbLog.insertAcctLog(facct1);
			db.commit();
		} catch (Exception ex) {
			db.rollback();
			result = 0;
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return result;
	}

	/**
	 * 注册用户
	 * 
	 * @param useForm
	 *            用户信息
	 * @return int 0 1
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	public String addUser(SysUserForm useForm) {
		DBService db = null;
		List<Integer> arrayPH = null;
		List<Integer> arrayZH = null;
		String userParent = useForm.getParent_id();
		String usertime = Tools.getNow3().substring(0, 8);
		String[] authinfo = null;
		try {
//			authinfo = AuthenticatorServer.genSecret(useForm.getUsername(),
//					"whthost");
			authinfo = AuthenticatorServer.genSecret();
			if (null == authinfo) {
				return null;
			}
			db = new DBService();
			if (!"1".equals(userParent)) {
				// 判断当天是否大于20 总数是否大于300
				String sql0 = "select count(*) from wht_agentNum where agentnum="
						+ userParent + " and date='" + usertime + "'";
				String sql1 = "select count(*) from wht_agentNum where agentnum="
						+ userParent;
				int a1 = db.getInt(sql0);
				int b1 = db.getInt(sql1);
				if (a1 >= 50) {
					return "-1";
				}
				if (b1 >= 1000) {
					return "-2";
				}
			}
			arrayPH = db.getArray("SELECT id FROM sys_employ0 WHERE pid="
					+ useForm.getWltarea());
			arrayZH = db.getArray("SELECT id FROM sys_employ3 WHERE flag=0");
			if (null == arrayPH || null == arrayZH) {
				return null;
			}
			db.setAutoCommit(false);
			String time = Tools.getNow3();
			synchronized (time) {
				
			}
			int a = db.getInt("select MAX(user_id) FROM sys_user") + 1;
			String userno = Tools.add0(Tools.buildRandom(3)+""+a + "", 10);
			Object[] obj0 = { null, userno, useForm.getUsername(),
					MD5.encode(useForm.getUserpassword()),
					MD5.encode(useForm.getDealpass()), useForm.getUser_role(),
					useForm.getUserename(), useForm.getParent_id(), 0,
					useForm.getWltarea(), useForm.getUser_site_city(), time, 1,
					useForm.getPhone(), useForm.getAddress(),
					useForm.getUsermail(), 0, 0, useForm.getExp1(), useForm.getUsersf(),
					null };
			db.logData(21, obj0, "sys_user");
			String account = Tools.getFacct(a);
			Object[] obj1 = { account, 0, 0, 1, userno };
			db.logData(5, obj1, "wht_facct");
			Object[] obj2 = { account + "01", account, "01", 0, 0 };
			Object[] obj3 = { account + "02", account, "02", 0, 0 };
			Object[] obj4 = { account + "03", account, "03", 0, 0 };
			db.logData(5, obj2, "wht_childfacct");
			db.logData(5, obj3, "wht_childfacct");
			db.logData(5, obj4, "wht_childfacct");
			String sql2 = "INSERT INTO mac(userno,googlesign,twodimensionalcode,ext1)  VALUES(?,?,?,?)";
			//db.update(sql2, new Object[] { userno, authinfo[0], com.wlt.webm.util.Tools.getImageStr(authinfo[1]),authinfo[1]});
			db.update(sql2, new Object[] { userno, authinfo[0], null,authinfo[1]});
			if (!"1".equals(userParent)) {
				Object[] obj5 = { null, userParent, usertime };
				db.logData(3, obj5, "wht_agentNum");
			}
			// 注册默认佣金
			for (int i = 0; i < arrayPH.size(); i++) {
				if (Float.valueOf(arrayPH.get(i)) < 0) {
					Object[] obj5 = { userno, userParent, arrayPH.get(i), null,
							150.0 + "", 0 };
					db.logData(6, obj5, "wht_agentAnduser");
				} else {
					Object[] obj5 = { userno, userParent, arrayPH.get(i), null,
							92.0 + "", 0 };
					db.logData(6, obj5, "wht_agentAnduser");
				}
			}
			for (int i = 0; i < arrayZH.size(); i++) {
				Object[] obj6 = { userno, userParent, null, arrayZH.get(i),
						(arrayZH.get(i)==14?81.0:80.0) + "", 1 };
				db.logData(6, obj6, "wht_agentAnduser");
			}
			String sql1 = "INSERT INTO sys_userloginLimit(login,userno,errornum,total) VALUES(?,?,?,?)";
			Object[] params = new Object[] { useForm.getUsername(), userno, 1,
					12 };
			db.update(sql1, params);
			//
			db.commit();
			return userno;
		} catch (SQLException e) {
			db.rollback();
			e.printStackTrace();
			Log.error("代理商开代办点异常:" + e.toString());
			return null;
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}

	/**
	 * 获取代理商下属用户信息列表====
	 * 
	 * @return 用户列表
	 * @throws Exception
	 */
	public List getAgentuserList(String srType, HttpSession session,
			PageAttribute page, SysUserForm uForm) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql
					.append("select a.user_login,a.user_no,a.user_ename,b.sa_name,a.user_status,e.phone,f.accountleft");
			sql
					.append(" from sys_user a left join wht_facct c on c.user_no = a.user_no Left JOIN sys_hfk e on e.resourceid=a.user_no LEFT JOIN  sys_role d on a.user_role = d.sr_id left join sys_area b on a.user_city = b.sa_id LEFT JOIN wht_childfacct f ON (f.fundacct = c.fundacct AND f.type='01')");
			sql.append(" where 1=1 ");
			if (null != uForm.getUser_id() && !"".equals(uForm.getUser_id())) {
				sql.append(" and a.user_id = " + uForm.getUser_id());
				paraBuffer.append("&user_id=" + uForm.getUser_id());
			}
			if (null != uForm.getUsername() && !"".equals(uForm.getUsername())) {
				sql.append(" and a.user_login = '" + uForm.getUsername() + "'");
				paraBuffer.append("&user_login=" + uForm.getUsername());
			}
			if (null != uForm.getRoleType() && !"".equals(uForm.getRoleType())) {
				sql.append(" and d.sr_type = " + uForm.getRoleType());
				paraBuffer.append("&roleType=" + uForm.getRoleType());
			}
			if (null != uForm.getUserstatus()
					&& !"".equals(uForm.getUserstatus())) {
				sql.append(" and a.user_status = " + uForm.getUserstatus());
				paraBuffer.append("&user_status=" + uForm.getUserstatus());
			}
			if (null != uForm.getStartDate()
					&& !"".equals(uForm.getStartDate())) {
				sql.append(" and a.user_createdate > '"
						+ uForm.getStartDate().replaceAll("-", "") + "000000'");
				paraBuffer.append("&startDate=" + uForm.getStartDate());
			}
			if (null != uForm.getEndDate() && !"".equals(uForm.getEndDate())) {
				sql.append(" and a.user_createdate < '"
						+ uForm.getEndDate().replaceAll("-", "") + "235959'");
				paraBuffer.append("&endDate=" + uForm.getEndDate());
			}
			if(null!=uForm.getName() && !"".equals(uForm.getName()))
			{
				sql.append(" and a.user_ename = '" + uForm.getName() + "'");
				paraBuffer.append("&user_ename=" + uForm.getName());
			}
			uForm.setParamUrl(paraBuffer.toString());
			// 查询当前登录用户角色类型
			SysUserForm userForm = (SysUserForm) session
					.getAttribute("userSession");
			String sqlRole = "select sr_type from sys_role where sr_id="
					+ userForm.getUser_role();
			int userRoleType = dbService.getInt(sqlRole);
			if (userRoleType != 0 && userRoleType != 1) {
				sql.append(" and a.user_pt = " + userForm.getUser_id()
						+ " and a.user_site=" + userForm.getUsersite());
			}
			if (userRoleType == 1) {
				sql
						.append(" and a.user_site="
								+ userForm.getUsersite()
								+ " and d.sr_type<>0 and d.sr_type<>4  and d.sr_type<>1");
			}
			sql.append(" limit " + (page.getCurPage() - 1) * page.getPageSize()
					+ "," + page.getPageSize());
			List list = dbService.getList(sql.toString());
			for (Object tmp : list) {
				String[] temp = (String[]) tmp;
				if (null != temp[4] && !"".equals(temp[4])) {
					if ("0".equals(temp[4])) {
						temp[4] = "正常";
					} else if ("1".equals(temp[4])) {
						temp[4] = "注销";
					} else if ("2".equals(temp[4])) {
						temp[4] = "暂停";
					}
				}
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (null != dbService) {
				dbService.close();
			}
		}

	}

	/**
	 * 获取资金账户余额
	 * 
	 * @return
	 * @throws Exception
	 */
	public SysUserForm getCountLeft(String userid) {
		DBService db = null;
		SysUserForm userForm = new SysUserForm();
		try {
			db = new DBService();
			StringBuffer sql = new StringBuffer();
			sql.append("select a.type,a.accountleft").append(
					" from wht_childfacct a ").append(
					" where a.fundacct=(select fundacct from wht_facct where user_no = "
							+ userid + ") ");

			List list = db.getList(sql.toString());

			for (Object object : list) {
				String[] temp = (String[]) object;
				if ("01".equals(temp[0])) {
					userForm.setAccountAmount(temp[1]);
				} else if ("02".equals(temp[0])) {
					userForm.setCommissionAmount(temp[1]);
				} else if ("03".equals(temp[0])) {
					userForm.setFrozenAmount(temp[1]);
				}
			}
			return userForm;
		} catch (Exception ex) {
			return null;
		} finally {
			db.close();
		}
	}

	/**
	 * 获取当前用户可以提取的佣金
	 * 
	 * @param userno
	 * @param comm
	 * @return
	 */
	public float getableMoney(String userno, String comm) {
		boolean flag = false;
		DBService db = null;
		try {
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			int n = date.getDate();
			SimpleDateFormat simpleFormat = new SimpleDateFormat(
					"yyyyMMddHHmmss");
			String time = simpleFormat.format(date);
			cal.add(Calendar.DAY_OF_MONTH, -2);
			String time1 = simpleFormat.format(cal.getTime());
			db = new DBService(Constants.DBNAME_SCP);
			String employAccount = "select fundacct from wht_facct where user_no='"
					+ userno + "'";
			String childFacct = db.getString(employAccount);
			if (n < 3) {
				String lasttime = Tools.getLastMonthDay(new Date());
				String sql = "select sum(infacctfee) from wht_acctbill_"
						+ lasttime.substring(2, 6) + " where tradetime>='"
						+ time1 + "' and tradetime<='" + lasttime
						+ "' and tradetype=15 and pay_type=2 and state=0 and childfacct='"
						+ childFacct + "02'";
				int k = db.getInt(sql);
				String sql2 = "select sum(infacctfee) from wht_acctbill_"
						+ time.substring(2, 6) + " where tradetime>'"
						+ lasttime + "' and tradetime<='" + time
						+ "' and tradetype=15 and pay_type=2 and state=0 and childfacct='"
						+ childFacct + "02'";
				int k1 = db.getInt(sql2);
				return (Integer.parseInt(comm) - k1 - k) / 1000;
			} else {
				String sql3 = "select sum(a) from (select sum(infacctfee) as a from wht_acctbill_"
						+ time.substring(2, 6) + " where tradetime>='" + time1
						+ "' and tradetime<='" + time
						+ "' and tradetype!=12 and state=0 and childfacct='"
						+ childFacct + "02' GROUP BY tradeserial HAVING COUNT(tradeserial)=1) as temptable";
				int m = db.getInt(sql3);
				return (Float.parseFloat(comm) - m) / 1000;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}

	/**
	 * 添加欧非供货信息
	 * 
	 * @param form
	 *            表单
	 * @param user
	 *            登录用户信息
	 * @return 1、成功；-1、用户系统编号重复；-2、用户系统编号不存在 -3,统一支付账户不存在 -4，终端和账户不匹配 -5 系统繁忙
	 * @throws Exception
	 */
	public int addOufeiSwitch(SysUserForm form) throws Exception {
		String now = Tools.getNow3();
		StringBuffer sql = new StringBuffer();
		DBService db = null;
		try {
			db = new DBService();
			sql.append("select 1 from sys_oufeiswitch where userno=? and oufeitype=?");
			if (db.hasData(sql.toString(), new String[] { form.getUserno().trim(),form.getBtype() })) {
				return -1;
			}
			sql.delete(0, sql.length());
			sql.append("select user_no from sys_user ").append(
					" where user_no='").append(form.getUserno()).append("'");
			String resourceid = db.getString(sql.toString());
			if (resourceid == null) {
				return -2;
			}
			sql.delete(0, sql.length());
			sql.append("select fundacct from wht_facct ").append(
					" where fundacct='").append(form.getFundacct()).append("'");
			String fundacct = db.getString(sql.toString());
			if (fundacct == null) {
				return -3;
			}
			sql.delete(0, sql.length());
			sql.append("insert into sys_oufeiswitch(userno,fundacct,oufeitype) values(?,?,?)");
			Object[] params = { form.getUserno().trim(),form.getFundacct().trim(),form.getBtype() };
			db.update(sql.toString(), params);
		} catch (Exception e) {
			db.rollback();
			e.printStackTrace();
			return -5;
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return 1;
	}

	/**
	 * 添加欧非供货信息
	 * 
	 * @param form
	 *            表单
	 * @param user
	 *            登录用户信息
	 * @return 1、成功；-1、用户系统编号重复；-2、用户系统编号不存在 -3,统一支付账户不存在 -4，终端和账户不匹配 -5 系统繁忙
	 * @throws Exception
	 */
	public int addOufeiUser(SysUserForm form, String parentid) throws Exception {
		String now = Tools.getNow3();
		StringBuffer sql = new StringBuffer();
		DBService db = null;
		try {
			db = new DBService();
			if("0".equals(form.getBtype())){
				sql.append("select 1 from sys_oufei where parentid=?");
				if (db.hasData(sql.toString(), new String[] { parentid})) {
					return -1;
				}
			}
			if("1".equals(form.getBtype())){
				sql.delete(0, sql.length());
				sql.append("select 1 from sys_oufei_qb where parentid=?");
				if (db.hasData(sql.toString(), new String[] { parentid})) {
					return -1;
				}
			}
			String password = MD5.encode(form.getUserpassword());
			sql.delete(0, sql.length());
			sql.append("select user_no from sys_user ").append(
					" where user_no='").append(form.getUserno()).append(
					"' and user_pass='").append(password).append("'");
			String resourceid = db.getString(sql.toString());
			if (resourceid == null) {
				return -2;
			}
			sql.delete(0, sql.length());
			sql.append("select fundacct from wht_facct ").append(
					" where fundacct='").append(form.getFundacct()).append("'");
			String fundacct = db.getString(sql.toString());
			if (fundacct == null) {
				return -3;
			}
			sql.delete(0, sql.length());
			sql.append("select a.user_login,c.sa_zone,a.user_site from sys_user a,wht_facct b,sys_area c ")
					.append(" where a.user_no=b.user_no and a.user_city=c.sa_id and a.user_no='")
					.append(resourceid).append("'").append(" and b.fundacct='")
					.append(fundacct).append("'");

			String[] posid = db.getStringArray(sql.toString());
			if (posid == null) {
				return -4;
			}
			if("0".equals(form.getBtype())){
				sql.delete(0, sql.length());
				sql.append("insert into sys_oufei(userno,fundacct,oufeiID,oufeimoban,oufeisign,TIME,state,isstart,userpid,areacode,login,parentid) " +
						"values(?,?,?,?,?,?,?,?,?,?,?,?)");
				Object[] params = { form.getUserno().trim(),
						form.getFundacct().trim(), form.getOufeiID(),
						form.getOufeiMoban(), form.getOufeiSign(), now, 0, 1,
						posid[2], posid[1], posid[0], parentid};
				db.update(sql.toString(), params);
			}else if("1".equals(form.getBtype())){
				sql.delete(0, sql.length());
				sql.append("insert into sys_oufei_qb(userno,fundacct,oufeiID,oufeimoban,oufeisign,TIME,state,isstart,userpid,areacode,login,parentid,oufeitype) " +
						" values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
				Object[] params = { form.getUserno().trim(),
						form.getFundacct().trim(), form.getOufeiID(),
						form.getOufeiMoban(), form.getOufeiSign(), now, 0, 1,
						posid[2], posid[1], posid[0], parentid,"1"};
				db.update(sql.toString(), params);
			}else{
				return -5;
			}
		} catch (Exception e) {
			db.rollback();
			e.printStackTrace();
			return -5;
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return 1;
	}

	/**
	 * 查询殴飞信息
	 * 
	 * @param parentid
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	public List queryOufei(String parentid, int flag,String type) throws Exception {
		String sql = "";
		if (flag == 0) {
			String tableName="";
			if("0".equals(type)){
				tableName = "sys_oufei";
			}else{
				tableName = "sys_oufei_qb";
			}
			sql = "select userno,userpid,areacode,login,parentid,oufeiID,oufeimoban,oufeisign from "+tableName+" where state =0 and parentid='"
					+ parentid + "'";
		} else {
			sql = "select isstart,0 as type,'话费供货' as aa from sys_oufei where state =0 and parentid='" + parentid + "' "+
			" UNION "+
			" select isstart,1 as type,'Q币供货' as aa from sys_oufei_qb where state =0 and parentid='" + parentid + "'";
		}
		DBService db = null;
		try {
			db = new DBService();
			return  db.getList(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return null;
	}

	/**
	 * 修改殴飞供货状态
	 * 
	 * @param userid
	 * @param flag
	 * @param flag2
	 * @return
	 * @throws Exception
	 */
	public int upOufei(String userid, int flag, int flag2,String type) throws Exception {
		String sql = "";
		if (flag2 == 0) {
			if("0".equals(type)){
				sql = "update sys_oufei set isstart=" + flag + " where parentid='"
					+ userid + "'";
			}else{
				sql = "update sys_oufei_qb set isstart=" + flag + " where parentid='"
				+ userid + "'";
			}
		} else {
			if("0".equals(type)){
				sql = "delete from  sys_oufei where userno='" + userid + "'";
			}else{
				sql = "delete from  sys_oufei_qb where userno='" + userid + "'";
			}
		}
		DBService db = null;
		try {
			db = new DBService();
			return db.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}
	
	/**
	 * 修改殴飞供货用户功能状态
	 * 
	 * @param userid
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	public int upOufeiuser(String userid, int flag,String type) throws Exception {
		DBService db = null;
		String qbnum="";
		String hfnum="";
		try {
			db = new DBService();
			String sql="";
			if("0".equals(type)){
				if(flag==1){//关闭
					hfnum=db.getString("SELECT parentid FROM sys_oufei where userno='"+ userid + "'");
					TimerTask task = OUConstat.tasks.get(hfnum);
					if (null != task) {
						task.cancel();
						OUConstat.tasks.remove(hfnum);
						Log.info("殴飞话费供货已关闭:" + hfnum);
					}
					}
				sql = "update sys_oufei set isstart=1,state=" + flag + " where userno='"
					+ userid + "'";
			}else{
				if(flag==1){
					qbnum=db.getString("SELECT parentid FROM sys_oufei_qb where userno='"+ userid + "'");
					TimerTask qbtask = OUConstat.Qbtask.get(qbnum+"_qb");
					if (null != qbtask) {
						qbtask.cancel();
						OUConstat.Qbtask.remove(qbnum+"_qb");
						Log.info("殴飞话费供货已关闭:" + qbnum+"_qb");
					}
					}
				 sql = "update sys_oufei_qb set isstart=1 ,state=" + flag + " where userno='"
				+ userid + "'";
			}
			return db.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}

	public List oufeiList() {
		String sql = "select userno,fundacct,oufeiID,oufeimoban,oufeisign,TIME,state,isstart,userpid,areacode,login,parentid,'话费充值' as aa,0 as bb from sys_oufei "+
		" UNION "+
		" select userno,fundacct,oufeiID,oufeimoban,oufeisign,TIME,state,isstart,userpid,areacode,login,parentid,'Q币充值' as aa,1 as bb from sys_oufei_qb order by userno asc";
		DBService db = null;
		try {
			db = new DBService();
			return db.getList(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}

	public List oufeiList(String userid) {
//		String sql = "select userno,fundacct,oufeiID,oufeimoban,oufeisign,DATE_FORMAT(TIME,'%Y-%m-%d %k:%i:%s'),state,isstart,userpid,areacode,login,parentid from sys_oufei where parentid='"
//				+ userid + "'";
		String sql="SELECT userno,fundacct,oufeiID,oufeimoban,oufeisign,DATE_FORMAT(TIME,'%Y-%m-%d %k:%i:%s'),state,isstart,userpid,areacode,login,parentid,'话费充值' AS aa FROM sys_oufei WHERE parentid='"+ userid +"'"+
					" UNION "+
					" SELECT userno,fundacct,oufeiID,oufeimoban,oufeisign,DATE_FORMAT(TIME,'%Y-%m-%d %k:%i:%s'),state,isstart,userpid,areacode,login,parentid,'Q币充值'  AS aa FROM sys_oufei_qb WHERE parentid='"+ userid +"' order by userno asc";
		DBService db = null;
		try {
			db = new DBService();
			return db.getList(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}

	public boolean oufeiswitch(String userno) {
		String sql = "select 1 from sys_oufeiswitch where userno='" + userno
				+ "'";
		DBService db = null;
		try {
			db = new DBService();
			if (db.hasData(sql)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}

	/**
	 * 欧飞供货 绑定 获取权限  类型(话费，Q币)
	 * @param userno
	 * @return list
	 */
	public List OuFeiTypeList(String userno) {
		String sql = "select oufeitype from sys_oufeiswitch where userno='" + userno + "' order by oufeitype asc";
		DBService db = null;
		try {
			db = new DBService();
			return db.getStringList(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != db) {
				db.close();
			}
		}
		return null;
	}
	/**
	 * 初始化用户登录、交易密码
	 * 
	 * @param login
	 * @return -1表示异常 0表示不存在 1表示成功
	 */
	public int initPwd(String login) {
		String sql = "UPDATE sys_user SET user_pass='de99411448999cb9d00dc45c1266fed1',trade_pass='de99411448999cb9d00dc45c1266fed1' WHERE user_login='"
				+ login + "'";
		DBService db = null;
		try {
			db = new DBService();
			return db.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}

	/**
	 * 获取中石化下级用户列表
	 * 
	 * @param srType
	 * @param session
	 * @param userForm
	 * @return Date:2014-04-26 autor:谭万龙
	 */
	public List getZshAgentuserList(String srType, HttpSession session,
			PageAttribute page, SysUserForm uForm) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append("select a.user_login,a.user_no,a.user_ename,b.sa_name,a.user_status,e.phone,f.accountleft,(SELECT 100 FROM sys_user s WHERE s.user_login=a.exp1) AS ss  ");
			sql.append(" from sys_user a left join wht_facct c on c.user_no = a.user_no Left JOIN sys_hfk e on e.resourceid=a.user_no LEFT JOIN  sys_role d on a.user_role = d.sr_id left join sys_area b on a.user_city = b.sa_id  LEFT JOIN wht_childfacct f ON (f.fundacct = c.fundacct AND f.type='01')");
			sql.append(" where 1=1 ");
			if (null != uForm.getUser_id() && !"".equals(uForm.getUser_id())) {
				sql.append(" and a.user_id = " + uForm.getUser_id());
				paraBuffer.append("&user_id=" + uForm.getUser_id());
			}
			if (null != uForm.getUsername() && !"".equals(uForm.getUsername())) {
				sql.append(" and a.user_login = '" + uForm.getUsername() + "'");
				paraBuffer.append("&user_login=" + uForm.getUsername());
			}
			if (null != uForm.getRoleType() && !"".equals(uForm.getRoleType())) {
				sql.append(" and d.sr_type = " + uForm.getRoleType());
				paraBuffer.append("&roleType=" + uForm.getRoleType());
			}
			if (null != uForm.getUserstatus()
					&& !"".equals(uForm.getUserstatus())) {
				sql.append(" and a.user_status = " + uForm.getUserstatus());
				paraBuffer.append("&user_status=" + uForm.getUserstatus());
			}
			if (null != uForm.getStartDate()
					&& !"".equals(uForm.getStartDate())) {
				sql.append(" and a.user_createdate > '"
						+ uForm.getStartDate().replaceAll("-", "") + "'");
				paraBuffer.append("&startDate=" + uForm.getStartDate());
			}
			if (null != uForm.getEndDate() && !"".equals(uForm.getEndDate())) {
				sql.append(" and a.user_createdate < '"
						+ uForm.getEndDate().replaceAll("-", "") + "'");
				paraBuffer.append("&endDate=" + uForm.getEndDate());
			}
			uForm.setParamUrl(paraBuffer.toString());
			// 查询当前登录用户角色类型
			SysUserForm userForm = (SysUserForm) session
					.getAttribute("userSession");
			String sqlRole = "select sr_type from sys_role where sr_id="
					+ userForm.getUser_role();
			int userRoleType = dbService.getInt(sqlRole);
			if (userRoleType != 0 && userRoleType != 1) {
				sql.append(" and a.user_pt = " + userForm.getUser_id()
						+ " and a.user_site=" + userForm.getUsersite());
			}
			if (userRoleType == 1) {
				sql
						.append(" and a.user_site="
								+ userForm.getUsersite()
								+ " and d.sr_type<>0 and d.sr_type<>4  and d.sr_type<>1");
			}
			sql.append(" limit " + (page.getCurPage() - 1) * page.getPageSize()
					+ "," + page.getPageSize());
			List list = dbService.getList(sql.toString());
			for (Object tmp : list) {
				String[] temp = (String[]) tmp;
				if (null != temp[4] && !"".equals(temp[4])) {
					if ("0".equals(temp[4])) {
						temp[4] = "正常";
					} else if ("1".equals(temp[4])) {
						temp[4] = "注销";
					} else if ("2".equals(temp[4])) {
						temp[4] = "暂停";
					}
				}
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (null != dbService) {
				dbService.close();
			}
		}

	}

	/**
	 * 获取中石化下级用户条数
	 * 
	 * @param srType
	 * @param session
	 * @param userForm
	 * @return Date:2014-04-26 autor:谭万龙
	 */
	public int countZshSysUser(String srType, HttpSession session,
			SysUserForm uForm) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) ");
			sql
					.append(" from sys_user a left join wht_facct c on c.user_no = a.user_no LEFT JOIN  sys_role d on a.user_role = d.sr_id left join sys_roletype b on d.sr_type = b.sr_type  LEFT JOIN wht_childfacct f ON (f.fundacct = c.fundacct AND f.type='01')");
			sql.append(" where 1=1 ");

			if (null != uForm.getUser_id() && !"".equals(uForm.getUser_id())) {
				sql.append(" and a.user_id = " + uForm.getUser_id());
				paraBuffer.append("&user_id=" + uForm.getUser_id());
			}
			if (null != uForm.getUsername() && !"".equals(uForm.getUsername())) {
				sql.append(" and a.user_login = '" + uForm.getUsername() + "'");
				paraBuffer.append("&user_login=" + uForm.getUsername());
			}
			if (null != uForm.getRoleType() && !"".equals(uForm.getRoleType())) {
				sql.append(" and d.sr_type = " + uForm.getRoleType());
				paraBuffer.append("&roleType=" + uForm.getRoleType());
			}
			if (null != uForm.getUserstatus()
					&& !"".equals(uForm.getUserstatus())) {
				sql.append(" and a.user_status = " + uForm.getUserstatus());
				paraBuffer.append("&user_status=" + uForm.getUserstatus());
			}
			if (null != uForm.getStartDate()
					&& !"".equals(uForm.getStartDate())) {
				sql.append(" and a.user_createdate > '"
						+ uForm.getStartDate().replaceAll("-", "") + "'");
				paraBuffer.append("&startDate=" + uForm.getStartDate());
			}
			if (null != uForm.getEndDate() && !"".equals(uForm.getEndDate())) {
				sql.append(" and a.user_createdate < '"
						+ uForm.getEndDate().replaceAll("-", "") + "'");
				paraBuffer.append("&endDate=" + uForm.getEndDate());
			}
			uForm.setParamUrl(paraBuffer.toString());
			// 查询当前登录用户角色类型
			SysUserForm userForm = (SysUserForm) session
					.getAttribute("userSession");
			String sqlRole = "select sr_type from sys_role where sr_id="
					+ userForm.getUser_role();
			int userRoleType = dbService.getInt(sqlRole);
			if (userRoleType != 0 && userRoleType != 1) {
				sql.append(" and a.user_pt = " + userForm.getUser_id()
						+ " and a.user_site=" + userForm.getUsersite());
			}
			if (userRoleType == 1) {
				sql
						.append(" and a.user_site="
								+ userForm.getUsersite()
								+ " and d.sr_type<>0 and d.sr_type<>4  and d.sr_type<>1");
			}
			int n = dbService.getInt(sql.toString());
			return n;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (null != dbService) {
				dbService.close();
			}
		}
	}

	/**
	 * 中石化提取金额
	 * 
	 * @param userNo
	 * @param userSNo
	 * @param chargeFee
	 * @param fundAcct02
	 * @param fundAcct01
	 * @return
	 */
	public int zshextact(String userNo, String userSNo, double chargeFee,
			String fundAcct01, String fundAcct02) {
		DBService db = null;
		try {

			db = new DBService();
			String a0 = "select a.accountleft from wht_childfacct a left join wht_facct b on a.fundacct=b.fundacct where b.user_no='"
					+ userNo + "' and a.type='01'";
			String a1 = "select a.accountleft from wht_childfacct a left join wht_facct b on a.fundacct=b.fundacct where b.user_no='"
					+ userSNo + "' and a.type='01'";
			int oneleft = db.getInt(a0);
			int toleft = db.getInt(a1);
			int money = (int) chargeFee;
			String sql1 = "UPDATE wht_childfacct SET accountleft=accountleft-"
					+ money
					+ " WHERE fundacct=(SELECT fundacct FROM wht_facct WHERE user_no='"
					+ userSNo + "') and type='01'";
			String sql2 = "UPDATE wht_childfacct SET accountleft=accountleft+"
					+ money
					+ " WHERE fundacct=(SELECT fundacct FROM wht_facct WHERE user_no='"
					+ userNo + "') and type='01'";
			db.setAutoCommit(false);
			String sepNo = Tools.getSeqNo(userNo);
			String sepNo1 = Tools.getSeqNo(userSNo);
			String nowTime = Tools.getNow();
			Object[] obj1 = { null, fundAcct02, sepNo, fundAcct02, nowTime,
					money, money, 21, "代理商提现", 0, nowTime, toleft - money,
					sepNo1, fundAcct01.substring(0, 15), 1 };
			Object[] obj2 = { null, fundAcct01, sepNo1, fundAcct01, nowTime,
					money, money, 21, "代理商提现", 0, nowTime, oneleft + money,
					sepNo, fundAcct02.substring(0, 15), 2 };
			String tablename = "wht_acctbill_" + nowTime.substring(2, 6);
			db.update(sql1);
			db.update(sql2);
			db.logData(15, obj1, tablename);
			db.logData(15, obj2, tablename);
			db.commit();
			return 0;
		} catch (Exception ex) {
			ex.printStackTrace();
			db.rollback();
			return 1;
		} finally {
			db.close();
		}
	}

	/**
	 * 中石化获取资金账号余额
	 * 
	 * @param userSNo
	 * @return
	 */
	public String getZshUseLeft(String userSNo) {
		int usableleft = 0;
		DBService db = null;
		try {
			db = new DBService(Constants.DBNAME_SCP);
			String child = db
					.getString("select fundacct from wht_facct where user_no='"
							+ userSNo + "'")
					+ "01";
			String sql = "select accountleft from wht_childfacct where childfacct='"
					+ child + "' and type='01'";
			usableleft = db.getInt(sql);
		} catch (SQLException e) {
			return "0";
		} finally {
			if (null != db) {
				db.close();
			}
		}
		String str = FloatArith.li2yuan(usableleft + "") + "";
		return str;
	}

	/**
	 * 根据用户账号获取用户信息
	 * 
	 * @param user_login
	 * @return author 谭万龙 date:2014-04-29
	 * @throws Exception
	 */
	public SysUserForm getUserNoForm(String user_login) throws Exception {
		DBService db = new DBService(Constants.DBNAME_SCP);
		SysUserForm userForm = new SysUserForm();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select user_login,user_no from sys_user").append(
					" where user_login=? ");

			String[] params = { user_login };
			String[] fields = { "username", "userno" };
			db.populate(userForm, fields, sql.toString(), params);
			return userForm;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (db != null)
				db.close();
		}
	}

	/**
	 * 根据角色获取市级地区信息
	 * 
	 * @param userRole
	 * @return
	 * @throws Exception
	 */
	// public String findRoleCity(String userRole) throws Exception {
	// DBService db = new DBService(Constants.DBNAME_SCP);
	// SysUserForm userForm = new SysUserForm();
	// try {
	// String sql ="select sa_id from sys_sa_power where sr_id="+userRole+" ";
	// return db.getString(sql);
	// } catch (Exception ex) {
	// throw ex;
	// } finally {
	// db.close();
	// }
	// }
	
	/**
	 * 验证令牌
	 */
	public boolean validateAuth(String code, String userno) {
		String savedSecret = null;
		DBService db = null;
		try {
			db = new DBService();
			savedSecret = db
					.getString("SELECT googlesign FROM mac WHERE userno='"
							+ userno + "'");
			if (null == savedSecret) {
               return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null)
				db.close();
		}
		return AuthenticatorServer.auth(code, savedSecret);
	}
	/**
	 * 查询下级用户条数
	 * @param sr_id
	 * @param session
	 * @param userForm
	 * @return
	 */
	public int countLowerUserUser(String sr_id, HttpSession session,
			SysUserForm uForm) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) ");
			sql.append(" from sys_user a left join wht_facct c on c.user_no = a.user_no LEFT JOIN  sys_role d on a.user_role = d.sr_id left join sys_roletype b on d.sr_type = b.sr_type");
			sql.append(" where 1=1 ");

			if (null != uForm.getUser_id() && !"".equals(uForm.getUser_id())) {
				sql.append(" and a.user_id = " + uForm.getUser_id());
				paraBuffer.append("&user_id=" + uForm.getUser_id());
			}
			if (null != uForm.getUsername() && !"".equals(uForm.getUsername())) {
				sql.append(" and a.user_login = '" + uForm.getUsername() + "'");
				paraBuffer.append("&user_login=" + uForm.getUsername());
			}
			if (null != uForm.getRoleType() && !"".equals(uForm.getRoleType())) {
				sql.append(" and d.sr_type = " + uForm.getRoleType());
				paraBuffer.append("&roleType=" + uForm.getRoleType());
			}
			if (null != uForm.getUserstatus()
					&& !"".equals(uForm.getUserstatus())) {
				sql.append(" and a.user_status = " + uForm.getUserstatus());
				paraBuffer.append("&user_status=" + uForm.getUserstatus());
			}
			if (null != uForm.getStartDate()
					&& !"".equals(uForm.getStartDate())) {
				sql.append(" and a.user_createdate > '"
						+ uForm.getStartDate().replaceAll("-", "") + "000000'");
				paraBuffer.append("&startDate=" + uForm.getStartDate());
			}
			if (null != uForm.getEndDate() && !"".equals(uForm.getEndDate())) {
				sql.append(" and a.user_createdate < '"
						+ uForm.getEndDate().replaceAll("-", "") + "235959'");
				paraBuffer.append("&endDate=" + uForm.getEndDate());
			}
			if (null != uForm.getExp1() && !"".equals(uForm.getExp1())) {
				sql.append(" and a.exp1 = '" + uForm.getExp1() + "'");
				paraBuffer.append("&exp1=" + uForm.getExp1());
			}
			uForm.setParamUrl(paraBuffer.toString());
			// 查询当前登录用户角色类型
			SysUserForm userForm = (SysUserForm) session
					.getAttribute("userSession");
			String sqlRole = "select sr_type from sys_role where sr_id="
					+ userForm.getUser_role();
			int userRoleType = dbService.getInt(sqlRole);
			if (userRoleType != 0 && userRoleType != 1) {
				sql.append(" and a.user_pt = " + userForm.getUser_id()
						+ " and a.user_site=" + userForm.getUsersite());
			}
			if (userRoleType == 1) {
				sql.append(" and a.user_pt = " + userForm.getUser_id()
						+" and a.user_site="
								+ userForm.getUsersite()
								+ " and d.sr_type<>0 and d.sr_type<>4 and d.sr_type<>1");
			}
			int n = dbService.getInt(sql.toString());
			return n;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (null != dbService) {
				dbService.close();
			}
		}
	}
	/**
	 * 查询下级用户列表
	 * @param sr_id
	 * @param session
	 * @param page
	 * @param userForm
	 * @return
	 * @throws SQLException 
	 */
	public List getLowerUserList(String srType, HttpSession session,
			PageAttribute page, SysUserForm uForm) throws SQLException {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append("select a.user_login,a.user_no,a.user_ename,b.sa_name,a.user_status,e.phone,d.sr_type,a.user_id ");
			sql.append(" from sys_user a left join wht_facct c on c.user_no = a.user_no Left JOIN sys_hfk e on e.resourceid=a.user_no LEFT JOIN  sys_role d on a.user_role = d.sr_id left join sys_area b on a.user_city = b.sa_id");
			sql.append(" where 1=1 ");
			if (null != uForm.getUser_id() && !"".equals(uForm.getUser_id())) {
				sql.append(" and a.user_id = " + uForm.getUser_id());
				paraBuffer.append("&user_id=" + uForm.getUser_id());
			}
			if (null != uForm.getUsername() && !"".equals(uForm.getUsername())) {
				sql.append(" and a.user_login = '" + uForm.getUsername() + "'");
				paraBuffer.append("&user_login=" + uForm.getUsername());
			}
			if (null != uForm.getRoleType() && !"".equals(uForm.getRoleType())) {
				sql.append(" and d.sr_type = " + uForm.getRoleType());
				paraBuffer.append("&roleType=" + uForm.getRoleType());
			}
			if (null != uForm.getUserstatus()
					&& !"".equals(uForm.getUserstatus())) {
				sql.append(" and a.user_status = " + uForm.getUserstatus());
				paraBuffer.append("&user_status=" + uForm.getUserstatus());
			}
			if (null != uForm.getStartDate()
					&& !"".equals(uForm.getStartDate())) {
				sql.append(" and a.user_createdate > '"
						+ uForm.getStartDate().replaceAll("-", "") + "'");
				paraBuffer.append("&startDate=" + uForm.getStartDate());
			}
			if (null != uForm.getEndDate() && !"".equals(uForm.getEndDate())) {
				sql.append(" and a.user_createdate < '"
						+ uForm.getEndDate().replaceAll("-", "") + "'");
				paraBuffer.append("&endDate=" + uForm.getEndDate());
			}
			uForm.setParamUrl(paraBuffer.toString());
			// 查询当前登录用户角色类型
			SysUserForm userForm = (SysUserForm) session
					.getAttribute("userSession");
			String sqlRole = "select sr_type from sys_role where sr_id="
					+ userForm.getUser_role();
			int userRoleType = dbService.getInt(sqlRole);
			if (userRoleType != 0 && userRoleType != 1) {
				sql.append(" and a.user_pt = " + userForm.getUser_id()
						+ " and a.user_site=" + userForm.getUsersite());
			}
			if (userRoleType == 1) {
				sql.append(" and a.user_pt = " + userForm.getUser_id()
						+" and a.user_site="
								+ userForm.getUsersite()
								+ " and d.sr_type<>0 and d.sr_type<>4  and d.sr_type<>1");
			}
			sql.append(" limit " + (page.getCurPage() - 1) * page.getPageSize()
					+ "," + page.getPageSize());
			List list = dbService.getList(sql.toString());
			for (Object tmp : list) {
				String[] temp = (String[]) tmp;
				if (null != temp[4] && !"".equals(temp[4])) {
					if ("0".equals(temp[4])) {
						temp[4] = "正常";
					} else if ("1".equals(temp[4])) {
						temp[4] = "注销";
					} else if ("2".equals(temp[4])) {
						temp[4] = "暂停";
					}
				}
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (null != dbService) {
				dbService.close();
			}
		}
	}
	/**
	 * 代理商下级用户条数
	 * @param parent_id
	 * @param session
	 * @param userForm
	 * @return
	 */
	public int countDailishangLowerUser(String parent_id,
			SysUserForm uForm) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) ");
			sql.append(" from sys_user a left join wht_facct c on c.user_no = a.user_no LEFT JOIN  sys_role d on a.user_role = d.sr_id left join sys_roletype b on d.sr_type = b.sr_type");
			sql.append(" where 1=1 ");

			if (null != uForm.getUser_id() && !"".equals(uForm.getUser_id())) {
				sql.append(" and a.user_id = " + uForm.getUser_id());
				paraBuffer.append("&user_id=" + uForm.getUser_id());
			}
			if (null != uForm.getUsername() && !"".equals(uForm.getUsername())) {
				sql.append(" and a.user_login = '" + uForm.getUsername() + "'");
				paraBuffer.append("&user_login=" + uForm.getUsername());
			}
			if (null != uForm.getRoleType() && !"".equals(uForm.getRoleType())) {
				sql.append(" and d.sr_type = " + uForm.getRoleType());
				paraBuffer.append("&roleType=" + uForm.getRoleType());
			}
			if (null != uForm.getUserstatus()
					&& !"".equals(uForm.getUserstatus())) {
				sql.append(" and a.user_status = " + uForm.getUserstatus());
				paraBuffer.append("&user_status=" + uForm.getUserstatus());
			}
			if (null != uForm.getStartDate()
					&& !"".equals(uForm.getStartDate())) {
				sql.append(" and a.user_createdate > '"
						+ uForm.getStartDate().replaceAll("-", "") + "000000'");
				paraBuffer.append("&startDate=" + uForm.getStartDate());
			}
			if (null != uForm.getEndDate() && !"".equals(uForm.getEndDate())) {
				sql.append(" and a.user_createdate < '"
						+ uForm.getEndDate().replaceAll("-", "") + "235959'");
				paraBuffer.append("&endDate=" + uForm.getEndDate());
			}
			if (null != uForm.getExp1() && !"".equals(uForm.getExp1())) {
				sql.append(" and a.exp1 = '" + uForm.getExp1() + "'");
				paraBuffer.append("&exp1=" + uForm.getExp1());
			}
			uForm.setParamUrl(paraBuffer.toString());
			sql.append(" and a.user_pt = " + parent_id);
			int n = dbService.getInt(sql.toString());
			return n;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (null != dbService) {
				dbService.close();
			}
		}
	}
	/**
	 * 代理商下级用户列表
	 * @param parent_id
	 * @param session
	 * @param page
	 * @param userForm
	 * @return
	 */
	public List getDailishangLowerUserList(String parent_id,
			 PageAttribute page, SysUserForm uForm) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append("select a.user_login,a.user_no,a.user_ename,b.sa_name,a.user_status,e.phone,d.sr_type,a.user_id ");
			sql.append(" from sys_user a left join wht_facct c on c.user_no = a.user_no Left JOIN sys_hfk e on e.resourceid=a.user_no LEFT JOIN  sys_role d on a.user_role = d.sr_id left join sys_area b on a.user_city = b.sa_id");
			sql.append(" where 1=1 ");
			if (null != uForm.getUser_id() && !"".equals(uForm.getUser_id())) {
				sql.append(" and a.user_id = " + uForm.getUser_id());
				paraBuffer.append("&user_id=" + uForm.getUser_id());
			}
			if (null != uForm.getUsername() && !"".equals(uForm.getUsername())) {
				sql.append(" and a.user_login = '" + uForm.getUsername() + "'");
				paraBuffer.append("&user_login=" + uForm.getUsername());
			}
			if (null != uForm.getRoleType() && !"".equals(uForm.getRoleType())) {
				sql.append(" and d.sr_type = " + uForm.getRoleType());
				paraBuffer.append("&roleType=" + uForm.getRoleType());
			}
			if (null != uForm.getUserstatus()
					&& !"".equals(uForm.getUserstatus())) {
				sql.append(" and a.user_status = " + uForm.getUserstatus());
				paraBuffer.append("&user_status=" + uForm.getUserstatus());
			}
			if (null != uForm.getStartDate()
					&& !"".equals(uForm.getStartDate())) {
				sql.append(" and a.user_createdate > '"
						+ uForm.getStartDate().replaceAll("-", "") + "'");
				paraBuffer.append("&startDate=" + uForm.getStartDate());
			}
			if (null != uForm.getEndDate() && !"".equals(uForm.getEndDate())) {
				sql.append(" and a.user_createdate < '"
						+ uForm.getEndDate().replaceAll("-", "") + "'");
				paraBuffer.append("&endDate=" + uForm.getEndDate());
			}
			uForm.setParamUrl(paraBuffer.toString());
			sql.append(" and a.user_pt = " + parent_id);
			sql.append(" limit " + (page.getCurPage() - 1) * page.getPageSize()
					+ "," + page.getPageSize());
			List list = dbService.getList(sql.toString());
			for (Object tmp : list) {
				String[] temp = (String[]) tmp;
				if (null != temp[4] && !"".equals(temp[4])) {
					if ("0".equals(temp[4])) {
						temp[4] = "正常";
					} else if ("1".equals(temp[4])) {
						temp[4] = "注销";
					} else if ("2".equals(temp[4])) {
						temp[4] = "暂停";
					}
				}
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (null != dbService) {
				dbService.close();
			}
		}
	}

	public int initMessagePwd(String login,String phone) {
		DBService db = null;
		try {
			db = new DBService();
		String checkUserSql="select user_tel from sys_user where user_login='"+login+"'";
		String usertel=db.getString(checkUserSql);
		if(usertel==null || "".equals(usertel)){
			return 0;
		}
		String pwd="";
		for(int i=0;i<8;i++){
			pwd=pwd+new Random().nextInt(10);
		}
		String md5Pwd=MD5Util.MD5Encode(pwd, "UTF-8");
		String sql = "UPDATE sys_user SET user_pass='"+md5Pwd+"',trade_pass='"+md5Pwd+"' WHERE user_login='"
			+ login + "'";
		int num=db.update(sql);
		//发送短信 phone//手机号码
		boolean b=false;
		if(phone!=null&&!phone.trim().equals("")){
			 b=YMmessage.qxtSendSMS(phone,YMmessage.mi(pwd));
		}else{
			b=YMmessage.qxtSendSMS(usertel.trim(),YMmessage.mi(pwd));
		}
		if(b){
			num=1;
		}else {
			num=2;
		}
		return num;
	} catch (Exception e) {
		e.printStackTrace();
		return -1;
	} finally {
		if (null != db) {
			db.close();
		}
	}
	}
	
	/**
	 * 根据用户系统编号获取角色类型
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getRTbyUserno(String userno) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql
				.append("select b.sr_type from sys_user a left join sys_role b on a.user_role = b.sr_id where a.user_no = '"
						+ userno + "'");
		String result = dbService.getString(sql.toString());
			if (null != dbService) {
				dbService.close();
				dbService=null;
			}
			return result;
	}
	
     
	/**
	 * 根据文件 代理商账号开代办点
	 * @param userlogin
	 * @param userList String{登录账号,登录密码，交易密码，姓名，市区编码、地址、邮件、身份证}
	 * @return
	 */
   public String autoAddUser(String userlogin,ArrayList<String[]> userList){
	   SysUserForm useForm=new SysUserForm();
	   String i="-1";
	   DBService db =null;
	   try {
		db=new DBService();
		String[] str=db.getStringArray("SELECT user_id,user_no,user_site FROM sys_user WHERE user_status=0 AND user_login='"+userlogin+"'");
	    if(null!=str){
	    	i="开户失败的用户:";
		    String sql="SELECT b.sr_id from sys_sa_power a,sys_role b " +
    		"WHERE a.sr_id = b.sr_id and  b.sr_type = 3 and a.sa_id in(SELECT sa_id from sys_area where sa_id ="+str[2]
            +" or sa_pid ="+str[2]+") AND a.sg_default='0'";
		    String roleId=db.getString(sql);
		    for(String[] user : userList){
		    	useForm.setParent_id(str[0]);
		    	useForm.setUsersite(str[2]);
		    	useForm.setUser_site_city(user[4]);
		    	useForm.setUsername(user[0]);
		    	useForm.setUserpassword(user[1]);
		    	useForm.setDealpass(user[2]);
		    	useForm.setUserename(user[3]);
		    	useForm.setAddress(user[5]);
		    	useForm.setUsermail(user[6]);
		    	useForm.setUsersf(user[7]);
		    	useForm.setUser_role(roleId);
		    	int a=autoAddUsers(useForm);
		    	Log.info(useForm.getUsername()+"  状态 "+a);
		    	if(a!=0){
		    		i+=useForm.getUsername()+",";
		    	}
		    }
	    }else{
	    	i="无此代理商";//无此代理商
	    }
	   } catch (SQLException e) {
		i="系统异常请稍后再试";//异常
		e.printStackTrace();
		Log.error("自动开户异常："+e.toString());
	}finally{
		if(null!=db){
			db.close();
		}
	}
	   return i;
   }
   
   
        /**
         * 自动注册用户
         * @param useForm
         * @return
         */
	public int autoAddUsers(SysUserForm useForm) {
		DBService db = null;
		List<Integer> arrayPH = null;
		List<Integer> arrayZH = null;
		String userParent = useForm.getParent_id();
		String usertime = Tools.getNow3().substring(0, 8);
		String[] authinfo = null;
		try {
//			authinfo = AuthenticatorServer.genSecret(useForm.getUsername(),
//					"whthost");
			authinfo = AuthenticatorServer.genSecret();
			if (null == authinfo) {
				return -1;
			}
			db = new DBService();
			arrayPH = db.getArray("SELECT id FROM sys_employ0 WHERE pid="
					+ useForm.getWltarea());
			arrayZH = db.getArray("SELECT id FROM sys_employ3 WHERE flag=0");
			if (null == arrayPH || null == arrayZH) {
				return -2;
			}
			db.setAutoCommit(false);
			String time = Tools.getNow3();
			synchronized (time) {
				
			}
			int a = db.getInt("select MAX(user_id) FROM sys_user") + 1;
			String userno = Tools.add0(Tools.buildRandom(3)+""+a + "", 10);
			Object[] obj0 = { null, userno, useForm.getUsername(),
					MD5.encode(useForm.getUserpassword()),
					MD5.encode(useForm.getDealpass()), useForm.getUser_role(),
					useForm.getUserename(), useForm.getParent_id(), 0,
					useForm.getUsersite(), useForm.getUser_site_city(), time, 0,
					useForm.getPhone(), useForm.getAddress(),
					useForm.getUsermail(), 0, 0, null, useForm.getUsersf(),
					null };
			db.logData(21, obj0, "sys_user");
			String account = Tools.getFacct(a);
			Object[] obj1 = { account, 0, 0, 1, userno };
			db.logData(5, obj1, "wht_facct");
			Object[] obj2 = { account + "01", account, "01", 0, 0 };
			Object[] obj3 = { account + "02", account, "02", 0, 0 };
			Object[] obj4 = { account + "03", account, "03", 0, 0 };
			db.logData(5, obj2, "wht_childfacct");
			db.logData(5, obj3, "wht_childfacct");
			db.logData(5, obj4, "wht_childfacct");
			String sql2 = "INSERT INTO mac(userno,googlesign,twodimensionalcode,ext1)  VALUES(?,?,?,?)";
			//db.update(sql2, new Object[] { userno, authinfo[0], com.wlt.webm.util.Tools.getImageStr(authinfo[1]),authinfo[1]});
			db.update(sql2, new Object[] { userno, authinfo[0], null,authinfo[1]});
//			if (!"1".equals(userParent)) {
//				Object[] obj5 = { null, userParent, usertime };
//				db.logData(3, obj5, "wht_agentNum");
//			}
			// 注册默认佣金
			for (int i = 0; i < arrayPH.size(); i++) {
				if (Float.valueOf(arrayPH.get(i)) < 0) {
					Object[] obj5 = { userno, userParent, arrayPH.get(i), null,
							150.0 + "", 0 };
					db.logData(6, obj5, "wht_agentAnduser");
				} else {
					Object[] obj5 = { userno, userParent, arrayPH.get(i), null,
							82.0 + "", 0 };
					db.logData(6, obj5, "wht_agentAnduser");
				}
			}
			for (int i = 0; i < arrayZH.size(); i++) {
				Object[] obj6 = { userno, userParent, null, arrayZH.get(i),
						80.0 + "", 1 };
				db.logData(6, obj6, "wht_agentAnduser");
			}
			String sql1 = "INSERT INTO sys_userloginLimit(login,userno,errornum,total) VALUES(?,?,?,?)";
			Object[] params = new Object[] { useForm.getUsername(), userno, 1,
					12 };
			db.update(sql1, params);
			//
			db.commit();
			return 0;
		} catch (SQLException e) {
			db.rollback();
			e.printStackTrace();
			Log.error("自动开代办点"+useForm.getUsername()+" 异常:" + e.toString());
			return -3;
		} finally {
			if (null != db) {
				db.close();
			}
		}
	}
	
	/**
	 * 获取中石化下级用户条数
	 * 
	 * @param login
	 * @param uForm
	 */
	public int countoilstation(String login,SysUserForm uForm) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) ");
			sql.append(" from sys_user a left join wht_facct c on c.user_no = a.user_no LEFT JOIN wht_childfacct f ON (f.fundacct = c.fundacct AND f.type='01')");
			sql.append(" where a.exp1='"+login+"' ");
			if (null != uForm.getUsername() && !"".equals(uForm.getUsername())) {
				sql.append(" and a.user_login = '" + uForm.getUsername() + "'");
				paraBuffer.append("&user_login=" + uForm.getUsername());
			}
			if (null != uForm.getStartDate()
					&& !"".equals(uForm.getStartDate())) {
				sql.append(" and a.user_createdate > '"
						+ uForm.getStartDate().replaceAll("-", "") + "000000'");
				paraBuffer.append("&startDate=" + uForm.getStartDate());
			}
			if (null != uForm.getEndDate() && !"".equals(uForm.getEndDate())) {
				sql.append(" and a.user_createdate < '"
						+ uForm.getEndDate().replaceAll("-", "") + "235959'");
				paraBuffer.append("&endDate=" + uForm.getEndDate());
			}
			uForm.setParamUrl(paraBuffer.toString());
			int n = dbService.getInt(sql.toString());
			return n;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (null != dbService) {
				dbService.close();
			}
		}
	}
	
	/**
	 * 获取油站列表
	 * @param login
	 * @param page
	 * @param userForm
	 */
	public List getOilstationList(String login,
			PageAttribute page, SysUserForm uForm) {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append("select a.user_login,a.user_no,a.user_ename,b.sa_name,a.user_status,e.phone,f.accountleft");
			sql.append(" from sys_user a left join wht_facct c on c.user_no = a.user_no Left JOIN sys_hfk e on e.resourceid=a.user_no LEFT JOIN  sys_role d on a.user_role = d.sr_id left join sys_area b on a.user_city = b.sa_id  LEFT JOIN wht_childfacct f ON (f.fundacct = c.fundacct AND f.type='01')");
			sql.append(" where a.exp1='"+login+"' ");
			if (null != uForm.getUsername() && !"".equals(uForm.getUsername())) {
				sql.append(" and a.user_login = '" + uForm.getUsername() + "'");
				paraBuffer.append("&user_login=" + uForm.getUsername());
			}
			if (null != uForm.getStartDate()
					&& !"".equals(uForm.getStartDate())) {
				sql.append(" and a.user_createdate > '"
						+ uForm.getStartDate().replaceAll("-", "") + "000000'");
				paraBuffer.append("&startDate=" + uForm.getStartDate());
			}
			if (null != uForm.getEndDate() && !"".equals(uForm.getEndDate())) {
				sql.append(" and a.user_createdate < '"
						+ uForm.getEndDate().replaceAll("-", "") + "235959'");
				paraBuffer.append("&endDate=" + uForm.getEndDate());
			}
			uForm.setParamUrl(paraBuffer.toString());
			sql.append(" limit " + (page.getCurPage() - 1) * page.getPageSize()
					+ "," + page.getPageSize());
			List list = dbService.getList(sql.toString());
			for (Object tmp : list) {
				String[] temp = (String[]) tmp;
				if (null != temp[4] && !"".equals(temp[4])) {
					if ("0".equals(temp[4])) {
						temp[4] = "正常";
					} else if ("1".equals(temp[4])) {
						temp[4] = "注销";
					} else if ("2".equals(temp[4])) {
						temp[4] = "暂停";
					}
				}
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (null != dbService) {
				dbService.close();
			}
		}

	}
	
	/**
	 * 中石化判断用户是否存在
	 * @param login
	 * @return
	 * @throws Exception
	 */
	public boolean  getUser(String login) throws Exception{
		DBService db=null;
		boolean flag=false;
		try{
			db=new DBService();
			if(null!=db.getString("SELECT user_role  FROM sys_user WHERE user_login='"+login+"' AND user_site=382")){
				flag=true;
			}
		}catch (Exception e) {
			throw e;
		}finally{
			if(null!=db){
				db.close();
			}
		}
		return flag;
	}
	/**
	 * 判断用户是否存在
	 * @param userlogin
	 * @return 0 存在，，1 不存在，，-1异常
	 * @throws Exception
	 */
	public int isUser(String userlogin){
		DBService db=null;
		try{
			db=new DBService();
			if(null!=db.getString("SELECT user_no FROM sys_user WHERE user_login='"+userlogin+"'")){
				return 0;
			}else{
				return 1;
			}
		}catch (Exception e) {
			e.printStackTrace();
			Log.error("isUser,方法异常,判断用户:"+userlogin);
			return -1;
		}finally{
			if(null!=db){
				db.close();
			}
		}
	}
	
	public static void main(String[] args) {
		//de99411448999cb9d00dc45c1266fed1
		System.out.println(MD5Util.MD5Encode("123456whtech","UTF-8"));
	}

	/**
	 * 根据系统编号获取用户默认区号
	 * @param userno
	 * @param n 0表示获取 1表示修改
	 * @param area 区号 
	 * @param parentid 父节点
	 * @return
	 */
	public String[] getdefultAR(String userno,int n,String area,String parentid) {
		String[] ar = null;
		DBService db = null;
		try {
			db = new DBService();
			if(n==0){
				ar=new String[2];
				ar[0] = db.getString("SELECT ext2 FROM mac WHERE userno='"
						+ userno + "'");
				String flag = AcctBillBean.getStatus(parentid + "") == true ? "1" : "0";
				String[] employFee=new BiProd().getZZEmploy(db, "xyjb01", flag,
						userno, Integer.parseInt(parentid));
				if ("1".equals(flag)) {//直营
					ar[1]=FloatArith.div(Double.valueOf(employFee[0]), 100)+"";
				} else {
					double a = FloatArith.div(Double.valueOf(employFee[0]), 100);// 总佣金比
					double c = FloatArith.mul(a, FloatArith.div(Double
							.valueOf(employFee[1]), 100));// 代办点应得佣金
					ar[1]=c+"";
				}
				
			}else {
				ar=new String[1];
				ar[0]=db.update("update mac set ext2='"+area+"' WHERE userno='"
						+ userno + "'" )+"";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null)
				db.close();
		}
		return ar;
	}
	/**
	*判断代理商 有没有佣金配置权限
	*/
	public String isbool(String agent_userno){
		DBService db=null;
		try {
			db=new DBService();
			return db.getString("SELECT open_close FROM wht_class_a_commission_allocation WHERE agent_userno='"+agent_userno+"'");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (db != null)
				db.close();
		}
		return null;
	}
}
