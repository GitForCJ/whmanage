package com.wlt.webm.business.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import sun.rmi.runtime.NewThreadAction;

import com.commsoft.epay.util.logging.Log;
import com.ejet.util.DataUtil;
import com.ejet.util.Excel;
import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.business.bean.OrderBean;
import com.wlt.webm.business.bean.SysUserInterface;
import com.wlt.webm.business.form.JtfkForm;
import com.wlt.webm.business.form.OrderForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.rights.bean.SysRole;
import com.wlt.webm.rights.bean.SysUser;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.scputil.DateParser;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.PageAttribute;
import com.wlt.webm.util.format.Formatter;

/**
 * 订单管理<br>
 */
public class OrderAction extends DispatchAction {

	/**
	 * 下级代理点交易信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward listuser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm orderForm = (OrderForm) form;
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		orderForm.setRoleFilter("select user_id from sys_user where user_pt = "
				+ userForm.getUser_id());
		OrderBean orderBean = new OrderBean();
		String userpt = userForm.getUser_id();
		PageAttribute page = new PageAttribute(orderForm.getCurPage(),
				Constant.PAGE_SIZE);
		page.setRsCount(orderBean.countUserOrder("wht_orderform_"
				+ DateParser.getNowDateTable(), orderForm, userpt));
		SysRole role = new SysRole();
		List list = orderBean.listUserOrder("wht_orderform_"
				+ DateParser.getNowDateTable(), orderForm, page, userpt);
		int curTotal = 0;
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			curTotal += Integer.parseInt(temp[6]);
		}
		request.setAttribute("curTotal", curTotal);
		String[] totalFee = orderBean.getuserTotalFee("wht_orderform_"
				+ DateParser.getNowDateTable(), orderForm, userpt);
		request.setAttribute("totalFee", totalFee);
		request.setAttribute("orderList", list);
		request.setAttribute("page", page);
		request.setAttribute("order", orderForm);
		request.setAttribute("rolelist", role.getRoleType(userForm
				.getRoleType()));
		request.setAttribute("serviceSel", getStingSel(orderBean.listSevice(),
				"业务类型"));
		return new ActionForward("/business/wltuserorderlist.jsp");
	}

	/**
	 * 下级代理点明细交易信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward listloweruser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm orderForm = (OrderForm) form;
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		String userpt = userForm.getUser_id();
		OrderBean orderBean = new OrderBean();
		if (orderForm.getDailishang() != null
				&& !orderForm.getDailishang().equals("")) {
			// orderForm.setRoleFilter(
			// "select user_id from sys_user where user_pt = "
			// +orderForm.getDailishang());
			userpt = orderForm.getDailishang();
		} else {
			List userPtlist = orderBean.getDaiLiUser(userForm.getUser_id()); // 获得下级编号
			for (int i = 0; i < userPtlist.size(); i++) {
				String str = (String) userPtlist.get(i);
				userpt += "," + str;
			}
		}

		PageAttribute page = new PageAttribute(orderForm.getCurPage(),
				Constant.PAGE_SIZE);
		page.setRsCount(orderBean.countUserOrder("wht_orderform_"
				+ DateParser.getNowDateTable(), orderForm, userpt));
		SysRole role = new SysRole();
		List list = orderBean.listUserOrder("wht_orderform_"
				+ DateParser.getNowDateTable(), orderForm, page, userpt);
		int curTotal = 0;
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			curTotal += Integer.parseInt(temp[6]);
		}
		request.setAttribute("curTotal", curTotal);
		String[] totalFee = orderBean.getuserTotalFee("wht_orderform_"
				+ DateParser.getNowDateTable(), orderForm, userpt);
		request.setAttribute("totalFee", totalFee);
		request.setAttribute("orderList", list);
		request.setAttribute("page", page);
		request.setAttribute("order", orderForm);
		request.setAttribute("rolelist", role.getRoleType(userForm
				.getRoleType()));
		request.setAttribute("serviceSel", getStingSel(orderBean.listSevice(),
				"业务类型"));
		request.setAttribute("dailishangSel", getStingSel(orderBean
				.getDaiLiShangUser(userForm.getUser_id()), "代理商"));
		return new ActionForward("/business/wltlowuserorderlist.jsp");
	}

	/**
	 * 中石化下级代理点交易信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward listzshuser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm orderForm = (OrderForm) form;
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");

		if (userForm.getRoleType().equals("1")) {
			orderForm
					.setRoleFilter("SELECT user_no FROM sys_user a,sys_area b,sys_role r WHERE b.sa_id=a.user_site AND a.user_site="
							+ userForm.getUsersite()
							+ " and a.user_role=r.sr_id and r.sr_type!=0 and r.sr_type!=1 and r.sr_type<>4");
		}
		if (userForm.getRoleType().equals("2")) {
			orderForm
					.setRoleFilter("select user_no from sys_user where user_pt = "
							+ userForm.getUser_id()
							+ " and user_site="
							+ userForm.getUsersite());
		}
		if (userForm.getRoleType().equals("3")) {
			orderForm.setRoleFilter(userForm.getUserno());
		}
		if (userForm.getRoleType().equals("4")) {
			orderForm.setRoleFilter(userForm.getUserno());
		}
		//orderForm.setRoleFilter("select user_id from sys_user where user_pt = "
		// +userForm.getUser_id());

		OrderBean orderBean = new OrderBean();
		int userpt = Integer.parseInt(userForm.getUser_id());
		PageAttribute page = new PageAttribute(orderForm.getCurPage(),
				Constant.PAGE_SIZE);
		String[] totalFee=orderBean.countZshUserOrder("wht_orderform_"+ DateParser.getNowDateTable(), orderForm, userpt);
		page.setRsCount(totalFee[0]==null?0:Integer.parseInt(totalFee[0]));
		SysRole role = new SysRole();
		List list = orderBean.listZshUserOrder("wht_orderform_"+ DateParser.getNowDateTable(), orderForm, page, userpt,99);
		int curTotal = 0;
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			curTotal += Integer.parseInt(temp[6]);
		}
		request.setAttribute("curTotal", curTotal);
//		String[] totalFee = orderBean.getuserTotalFee("wht_orderform_"
//				+ DateParser.getNowDateTable(), orderForm, userpt + "");
		request.setAttribute("totalFee", totalFee);
		request.setAttribute("orderList", list);
		request.setAttribute("page", page);
		request.setAttribute("order", orderForm);
		request.setAttribute("rolelist", role.getRoleType(userForm
				.getRoleType()));
		request.setAttribute("serviceSel", getStingSel(orderBean.listSevice(),
				"业务类型"));
		return new ActionForward("/business/wltzshuserorderlist.jsp");
	}

	/**
	 * 扣款统计
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward listout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm orderForm = (OrderForm) form;
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		OrderBean orderBean = new OrderBean();
		SysUserInterface sit = new SysUserInterface();
		SysRole role = new SysRole();
		List list = orderBean.listout("wht_acctout_"
				+ DateParser.getNowDate().substring(0, 4), orderForm);
		String str = orderBean.sumcount("wht_acctout_"
				+ DateParser.getNowDate().substring(0, 4), orderForm);
		request.setAttribute("str", str);
		request.setAttribute("orderList", list);
		request.setAttribute("order", orderForm);
		request.setAttribute("itypeSel", getStingSel(sit.listInterfaceType(),
				"接口商"));
		request.setAttribute("serviceSel", getStingSel(orderBean.listSevice(),
				"业务类型"));
		return new ActionForward("/business/accountoutlist.jsp");
	}

	/**
	 * 加款统计
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward listin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm orderForm = (OrderForm) form;
		OrderBean orderBean = new OrderBean();
		SysUserInterface sit = new SysUserInterface();
		List list = orderBean.listin("wht_acctin_"
				+ DateParser.getNowDate().substring(0, 4), orderForm);
		String[] str = orderBean.sumincount("wht_acctin_"
				+ DateParser.getNowDate().substring(0, 4), orderForm);
		String str2 = "";
		if (null != str) {
			str2 = "交易总量:" + str[0] + "   交易总额:" + str[1];
		}
		request.setAttribute("orderList", list);
		request.setAttribute("order", orderForm);
		request.setAttribute("str1", str2);
		// request.setAttribute("itypeSel",getStingSel(sit.listInterfaceType(),
		// "接口商"));
		request.setAttribute("serviceSel", getStingSel(orderBean
				.listtradeSevice(), "交易类型"));
		return new ActionForward("/business/accountinlist.jsp");
	}

	/**
	 * 中石化订单列表
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
		OrderForm orderForm = (OrderForm) form;
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		if (userForm.getRoleType().equals("1")) {
			orderForm
					.setRoleFilter("SELECT user_no FROM sys_user a,sys_area b,sys_role r WHERE b.sa_id=a.user_site AND a.user_site="
							+ userForm.getUsersite()
							+ " and a.user_role=r.sr_id and r.sr_type!=0 and r.sr_type!=1  and r.sr_type<>4");
		}
		if (userForm.getRoleType().equals("2")) {
			orderForm
					.setRoleFilter("select user_no from sys_user where user_pt = "
							+ userForm.getUser_id()
							+ " and user_site="
							+ userForm.getUsersite());
		}
		if (userForm.getRoleType().equals("3")) {
			orderForm.setRoleFilter(userForm.getUserno());
		}
		if (userForm.getRoleType().equals("4")) {
			orderForm.setRoleFilter(userForm.getUserno());
		}
		OrderBean orderBean = new OrderBean();
		PageAttribute page = new PageAttribute(orderForm.getCurPage(),
				Constant.PAGE_SIZE);
		String[] totalFee = orderBean.orderZshCount("wht_orderform_"
				+ DateParser.getNowDateTable(), orderForm);
		page.setRsCount(Integer.parseInt(totalFee[2]));// (orderBean.countOrder(
		// "wht_orderform_"
		// +DateParser
		// .getNowDateTable
		// (),orderForm));
		SysUserInterface sit = new SysUserInterface();
		SysRole role = new SysRole();
		List list = orderBean.listZshOrder("wht_orderform_"
				+ DateParser.getNowDateTable(), orderForm, page);
		int curTotal = 0;
		int valueTotal = 0;
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			valueTotal += Integer.parseInt(temp[5]);
			curTotal += Integer.parseInt(temp[6]);
		}
		request.setAttribute("valueTotal", valueTotal);
		request.setAttribute("curTotal", curTotal);
		request.setAttribute("totalFee", totalFee);
		request.setAttribute("orderList", list);
		request.setAttribute("page", page);
		request.setAttribute("areaSel", getStingSel(sit.listArea(), "区域"));
		request.setAttribute("itypeSel", getStingSel(sit.listInterfaceType(),
				"接口商"));
		request.setAttribute("order", orderForm);
		request.setAttribute("rolelist", role.getRoleType(userForm
				.getRoleType()));
		request.setAttribute("serviceSel", getStingSel(orderBean.listSevice(),
				"业务类型"));
		return mapping.findForward("zshsuccess");
	}

	/**
	 * 订单列表
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
		OrderForm orderForm = (OrderForm) form;
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		// if(userForm.getRoleType().equals("1")){
		// orderForm.setRoleFilter(
		// "SELECT user_no FROM sys_user a,sys_area b,sys_role r WHERE b.sa_id=a.user_site AND a.user_site="
		// +userForm.getUsersite()+
		// " and a.user_role=r.sr_id and r.sr_type!=0 and r.sr_type!=1 and r.sr_type<>4"
		// );
		// }
		String dailishang = orderForm.getDailishang();
		if(dailishang==null){
			dailishang="";
		}
		String exp1 = orderForm.getExp1();
		if(exp1==null){
			exp1="";
		}
		if (userForm.getRoleType().equals("0")
				|| userForm.getRoleType().equals("1")) {
			if ( !dailishang.equals("") && 
					exp1.equals("")) {
				orderForm
						.setRoleFilter("SELECT user_no FROM sys_user WHERE user_pt = (SELECT user_id FROM sys_user WHERE user_login = '"
								+ dailishang + "')");
			} else if ( !exp1.equals("") && 
					 dailishang.equals("")) {
				orderForm
						.setRoleFilter("SELECT user_no FROM sys_user WHERE user_pt in (SELECT user_id FROM sys_user WHERE exp1 = '"
								+ exp1 + "')");
			} else if (!exp1.equals("") && !dailishang.equals("")) {
				orderForm
						.setRoleFilter("SELECT user_no FROM sys_user WHERE user_pt = (SELECT user_id FROM sys_user WHERE user_login = '"
								+ dailishang + "' and exp1 = '" + exp1 + "') ");
			}

		}
		if (userForm.getRoleType().equals("2")) {
			orderForm
					.setRoleFilter("select user_no from sys_user where user_pt = "
							+ userForm.getUser_id()
							+ " and user_site="
							+ userForm.getUsersite());
		}
		if (userForm.getRoleType().equals("3")) {
			orderForm.setRoleFilter(userForm.getUserno());
		}
		if (userForm.getRoleType().equals("4")) {
			orderForm.setRoleFilter(userForm.getUserno());
		}
		OrderBean orderBean = new OrderBean();
		PageAttribute page = new PageAttribute(orderForm.getCurPage(),
				Constant.PAGE_SIZE);
		String[] totalFee = orderBean.orderCount("wht_orderform_"
				+ DateParser.getNowDateTable(), orderForm);
		page.setRsCount(Integer.parseInt(totalFee[2]));// (orderBean.countOrder(
		// "wht_orderform_"
		// +DateParser
		// .getNowDateTable
		// (),orderForm));
		SysUserInterface sit = new SysUserInterface();
		SysRole role = new SysRole();
		List list = orderBean.listOrder("wht_orderform_"
				+ DateParser.getNowDateTable(), orderForm, page);
		int curTotal = 0;
		int valueTotal = 0;
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			valueTotal += Integer.parseInt(temp[5]);
			curTotal += Integer.parseInt(temp[6]);
		}
		request.setAttribute("valueTotal", valueTotal);
		request.setAttribute("curTotal", curTotal);
		request.setAttribute("totalFee", totalFee);
		request.setAttribute("orderList", list);
		request.setAttribute("page", page);
		request.setAttribute("areaSel", getStingSel(sit.listArea(), "区域"));
		request.setAttribute("itypeSel", getStingSel(sit.listInterfaceType(),
				"接口商"));
		request.setAttribute("order", orderForm);
		request.setAttribute("rolelist", role.getRoleType(userForm
				.getRoleType()));
		request.setAttribute("serviceSel", getStingSel(orderBean.listSevice(),
				"业务类型"));
		return mapping.findForward("success");
	}

	/**
	 * 订单列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public int phoneList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm orderForm = (OrderForm) form;
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");

		if (userForm.getRoleType().equals("2")) {
			orderForm
					.setRoleFilter("select user_id from sys_user where user_pt = "
							+ userForm.getUser_id());
		}
		if (userForm.getRoleType().equals("3")) {
			orderForm.setRoleFilter(userForm.getUser_id());
		}
		if (userForm.getRoleType().equals("3")) {
			orderForm.setRoleFilter(userForm.getUser_id());
		}
		OrderBean orderBean = new OrderBean();
		PageAttribute page = new PageAttribute(orderForm.getCurPage(),
				Constant.PAGE_SIZE);
		page.setRsCount(orderBean.countOrder("wlt_orderform_"
				+ DateParser.getNowDateTable(), orderForm));
		SysUserInterface sit = new SysUserInterface();
		SysRole role = new SysRole();
		List list = orderBean.listOrder("wlt_orderform_"
				+ DateParser.getNowDateTable(), orderForm, page);
		int curTotal = 0;
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			curTotal += Integer.parseInt(temp[5]);
		}
		request.setAttribute("curTotal", curTotal);
		int totalFee = orderBean.getTotalFee(userForm.getUser_id());
		request.setAttribute("totalFee", totalFee);
		request.setAttribute("orderList", list);
		request.setAttribute("page", page);
		request.setAttribute("areaSel", getStingSel(sit.listArea(), "区域"));
		request.setAttribute("itypeSel", getStingSel(sit.listInterfaceType(),
				"接口商"));
		request.setAttribute("order", orderForm);
		request.setAttribute("rolelist", role.getRoleType(userForm
				.getRoleType()));
		request.setAttribute("serviceSel", getStingSel(orderBean.listSevice(),
				"业务类型"));
		return 0;
	}

	/**
	 * 导出订单明细
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward excelExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm orderForm = (OrderForm) form;
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		// if(userForm.getRoleType().equals("1")){
		// orderForm.setRoleFilter(
		// "SELECT user_no FROM sys_user a,sys_area b,sys_role r WHERE b.sa_id=a.user_site AND a.user_site="
		// +userForm.getUsersite()+
		// " and a.user_role=r.sr_id and r.sr_type!=0 and r.sr_type!=1  and r.sr_type<>4"
		// );
		// }
		String dailishang = orderForm.getDailishang();
		if(dailishang==null){
			dailishang="";
		}
		String exp1 = orderForm.getExp1();
		if(exp1==null){
			exp1="";
		}
		if (userForm.getRoleType().equals("0")
				|| userForm.getRoleType().equals("1")) {
			if ( !dailishang.equals("") && 
					exp1.equals("")) {
				orderForm
						.setRoleFilter("SELECT user_no FROM sys_user WHERE user_pt = (SELECT user_id FROM sys_user WHERE user_login = '"
								+ dailishang + "')");
			} else if ( !exp1.equals("") && 
					 dailishang.equals("")) {
				orderForm
						.setRoleFilter("SELECT user_no FROM sys_user WHERE user_pt in (SELECT user_id FROM sys_user WHERE exp1 = '"
								+ exp1 + "')");
			} else if (!exp1.equals("") && !dailishang.equals("")) {
				orderForm
						.setRoleFilter("SELECT user_no FROM sys_user WHERE user_pt = (SELECT user_id FROM sys_user WHERE user_login = '"
								+ dailishang + "' and exp1 = '" + exp1 + "') ");
			}

		}

		if (userForm.getRoleType().equals("2")) {
			orderForm
					.setRoleFilter("select user_no from sys_user where user_pt = "
							+ userForm.getUser_id()
							+ " and user_site="
							+ userForm.getUsersite());
		}
		if (userForm.getRoleType().equals("3")) {
			orderForm.setRoleFilter(userForm.getUserno());
		}
		if (userForm.getRoleType().equals("4")) {
			orderForm.setRoleFilter(userForm.getUserno());
		}
		OrderBean orderBean = new OrderBean();
		PageAttribute page = new PageAttribute(orderForm.getCurPage(),
				Constant.PAGE_SIZE);
		page.setRsCount(orderBean.countOrder("wht_orderform_"
				+ DateParser.getNowDateTable(), orderForm));
		List list = orderBean.listOrder_Excel("wht_orderform_"
				+ DateParser.getNowDateTable(), orderForm, page);
		// 导出excel
		String[][] colTitles = new String[][] { { "序号", "区域", "订单号", "交易号码",
				"订单状态", "业务类型", "交易金额(元)", "实际交易金额(元)", "提交时间", "修改时间", "登陆账号" } };
		int size = colTitles[0].length;
		List body = new ArrayList();
		// 只取其中某些字段
		int count = 0;
		int sum1 = 0;
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			String[] recode = new String[size];
			// 取其中某些字段
			int i = 0;
			recode[i++] = ++count + "";
			recode[i++] = temp[0];
			recode[i++] = temp[1];
			recode[i++] = temp[2];
			recode[i++] = temp[9];
			recode[i++] = temp[15];
			recode[i++] = Float.parseFloat(temp[5]) / 1000 + "";
			recode[i++] = Float.parseFloat(temp[6]) / 1000 + "";
			recode[i++] = temp[7];
			recode[i++] = temp[8];
			recode[i++] = temp[11];
			sum1 += Integer.parseInt(temp[6]);
			body.add(recode);
		}
		String[] sum = new String[size];
		sum[6] = "总计：";
		sum[7] = Formatter.format(sum1, Formatter.D1000F2);
		body.add(sum);
		Map rsMap = null;
		// 转换成excel格式数据
		if (body.size() > 0) {
			rsMap = DataUtil.toNestedStringsListMap(1, body);
		}

		Excel excel = new Excel();
		excel.setCols(colTitles[0].length);
		excel.createCaption("交易明细报表");
		excel.createColCaption(colTitles);
		if (rsMap != null) {
			excel.createBody(rsMap);
		}
		// excel.createRemarks("交易明细");
		String excelFileName = URLEncoder.encode("交易明细报表" + ".xls", "UTF-8");
		response.addHeader("Content-Disposition", "attachment; filename="
				+ excelFileName);
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			excel.createFile(out);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return mapping.findForward(null);
	}

	/**
	 * 中石化导出订单明细
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward zshExcelExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm orderForm = (OrderForm) form;
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		if (userForm.getRoleType().equals("1")) {
			orderForm
					.setRoleFilter("SELECT user_no FROM sys_user a,sys_area b,sys_role r WHERE b.sa_id=a.user_site AND a.user_site="
							+ userForm.getUsersite()
							+ " and a.user_role=r.sr_id and r.sr_type!=0 and r.sr_type!=1  and r.sr_type<>4");
		}
		if (userForm.getRoleType().equals("2")) {
			orderForm
					.setRoleFilter("select user_no from sys_user where user_pt = "
							+ userForm.getUser_id()
							+ " and user_site="
							+ userForm.getUsersite());
		}
		if (userForm.getRoleType().equals("3")) {
			orderForm.setRoleFilter(userForm.getUserno());
		}
		if (userForm.getRoleType().equals("4")) {
			orderForm.setRoleFilter(userForm.getUserno());
		}
		OrderBean orderBean = new OrderBean();
		PageAttribute page = new PageAttribute(orderForm.getCurPage(),
				Constant.PAGE_SIZE);
		String[] totalFee = orderBean.orderCount("wht_orderform_"
				+ DateParser.getNowDateTable(), orderForm);
		page.setRsCount(Integer.parseInt(totalFee[2]));// (orderBean.countOrder(
		// "wht_orderform_"
		// +DateParser
		// .getNowDateTable
		// (),orderForm));
		List list = orderBean.zshListOrder_Excel("wht_orderform_"
				+ DateParser.getNowDateTable(), orderForm, page);
		// 导出excel
		String[][] colTitles = new String[][] { { "序号", "区域", "条码类型", "订单号",
				"交易号码", "订单状态", "业务类型", "交易面值", "交易时间", "登陆账号" } };
		int size = colTitles[0].length;
		List body = new ArrayList();
		// 只取其中某些字段
		int count = 0;
		int sum1 = 0;
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			String[] recode = new String[size];
			// 取其中某些字段
			int i = 0;
			recode[i++] = ++count + "";
			recode[i++] = temp[0];
			recode[i++] = "条码" + temp[16];
			recode[i++] = temp[1];
			recode[i++] = temp[2];
			recode[i++] = temp[9];
			recode[i++] = temp[15];
			recode[i++] = Float.parseFloat(temp[5]) / 1000 + "";
			recode[i++] = temp[7];
			recode[i++] = temp[11];
			sum1 += Integer.parseInt(temp[5]);
			body.add(recode);
		}
		String[] sum = new String[size];
		sum[6] = "总计：";
		sum[7] = Formatter.format(sum1, Formatter.D1000F2);
		body.add(sum);
		Map rsMap = null;
		// 转换成excel格式数据
		if (body.size() > 0) {
			rsMap = DataUtil.toNestedStringsListMap(1, body);
		}

		Excel excel = new Excel();
		excel.setCols(colTitles[0].length);
		excel.createCaption("交易明细报表");
		excel.createColCaption(colTitles);
		if (rsMap != null) {
			excel.createBody(rsMap);
		}
		// excel.createRemarks("交易明细");
		String excelFileName = URLEncoder.encode("交易明细报表" + ".xls", "UTF-8");
		response.addHeader("Content-Disposition", "attachment; filename="
				+ excelFileName);
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			excel.createFile(out);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return mapping.findForward(null);
	}

	/**
	 * 导出订单明细
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward userExcelExport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderForm orderForm = (OrderForm) form;
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		if (userForm.getRoleType().equals("2")) {
			orderForm.setRoleFilter("select user_id from sys_user where user_pt = "
							+ userForm.getUser_id());
		}
		OrderBean orderBean = new OrderBean();
		PageAttribute page = new PageAttribute(orderForm.getCurPage(),
				Constant.PAGE_SIZE);
		page.setRsCount(orderBean.countUserOrder("wht_orderform_"
				+ DateParser.getNowDateTable(), orderForm, userForm
				.getUser_id()));
		SysUserInterface sit = new SysUserInterface();
		SysRole role = new SysRole();
		List list = orderBean.userOrder_Excel("wht_orderform_"
				+ DateParser.getNowDateTable(), orderForm, page, userForm
				.getUser_id());
		// 导出excel
		String[][] colTitles = new String[][] { { "序号", "区域", "订单号", "交易号码",
				"订单状态", "业务类型", "交易金额(元)", "实际交易金额(元)", "提交时间", "修改时间", "登陆账号" } };
		int size = colTitles[0].length;
		List body = new ArrayList();
		// 只取其中某些字段
		int count = 0;
		int sum1 = 0;
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			String[] recode = new String[size];
			// 取其中某些字段
			int i = 0;
			recode[i++] = ++count + "";
			recode[i++] = temp[0];
			recode[i++] = temp[1];
			recode[i++] = temp[2];
			recode[i++] = temp[9];
			recode[i++] = temp[15];
			recode[i++] = Float.parseFloat(temp[5]) / 1000 + "";
			recode[i++] = Float.parseFloat(temp[6]) / 1000 + "";
			recode[i++] = temp[7];
			recode[i++] = temp[8];
			recode[i++] = temp[11];
			sum1 += Integer.parseInt(temp[6]);
			body.add(recode);
		}
		String[] sum = new String[size];
		sum[6] = "总计：";
		sum[7] = Formatter.format(sum1, Formatter.D1000F2);
		body.add(sum);
		Map rsMap = null;
		// 转换成excel格式数据
		if (body.size() > 0) {
			rsMap = DataUtil.toNestedStringsListMap(1, body);
		}

		Excel excel = new Excel();
		excel.setCols(colTitles[0].length);
		excel.createCaption("交易明细报表");
		excel.createColCaption(colTitles);
		if (rsMap != null) {
			excel.createBody(rsMap);
		}
		// excel.createRemarks("交易明细");
		String excelFileName = URLEncoder.encode("交易明细报表" + ".xls", "UTF-8");
		response.addHeader("Content-Disposition", "attachment; filename="
				+ excelFileName);
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			excel.createFile(out);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return mapping.findForward(null);
	}

	/**
	 * 管理员导出下级用户订单明细
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loweruserExcelExport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderForm orderForm = (OrderForm) form;
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		if (userForm.getRoleType().equals("2")) {
			orderForm
					.setRoleFilter("select user_id from sys_user where user_pt = "
							+ userForm.getUser_id());
		}

		OrderBean orderBean = new OrderBean();
		String userpt = userForm.getUser_id();
		if (orderForm.getDailishang() != null
				&& !orderForm.getDailishang().equals("")) {
			// orderForm.setRoleFilter(
			// "select user_id from sys_user where user_pt = "
			// +orderForm.getDailishang());
			userpt = orderForm.getDailishang();
		} else {
			List userPtlist = orderBean.getDaiLiUser(userForm.getUser_id()); // 获得下级编号
			for (int i = 0; i < userPtlist.size(); i++) {
				String str = (String) userPtlist.get(i);
				userpt += "," + str;
			}
		}

		PageAttribute page = new PageAttribute(orderForm.getCurPage(),
				Constant.PAGE_SIZE);
		page.setRsCount(orderBean.countUserOrder("wht_orderform_"
				+ DateParser.getNowDateTable(), orderForm, userpt));
		SysUserInterface sit = new SysUserInterface();
		SysRole role = new SysRole();
		List list = orderBean.userOrder_Excel("wht_orderform_"
				+ DateParser.getNowDateTable(), orderForm, page, userpt);
		// 导出excel
		String[][] colTitles = new String[][] { { "序号", "区域", "订单号", "交易号码",
				"订单状态", "业务类型", "交易金额(元)", "实际交易金额(元)", "提交时间", "修改时间", "登陆账号" } };
		int size = colTitles[0].length;
		List body = new ArrayList();
		// 只取其中某些字段
		int count = 0;
		int sum1 = 0;
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			String[] recode = new String[size];
			// 取其中某些字段
			int i = 0;
			recode[i++] = ++count + "";
			recode[i++] = temp[0];
			recode[i++] = temp[1];
			recode[i++] = temp[2];
			recode[i++] = temp[9];
			recode[i++] = temp[15];
			recode[i++] = Float.parseFloat(temp[5]) / 1000 + "";
			recode[i++] = Float.parseFloat(temp[6]) / 1000 + "";
			recode[i++] = temp[7];
			recode[i++] = temp[8];
			recode[i++] = temp[11];
			sum1 += Integer.parseInt(temp[6]);
			body.add(recode);
		}
		String[] sum = new String[size];
		sum[6] = "总计：";
		sum[7] = Formatter.format(sum1, Formatter.D1000F2);
		body.add(sum);
		Map rsMap = null;
		// 转换成excel格式数据
		if (body.size() > 0) {
			rsMap = DataUtil.toNestedStringsListMap(1, body);
		}

		Excel excel = new Excel();
		excel.setCols(colTitles[0].length);
		excel.createCaption("交易明细报表");
		excel.createColCaption(colTitles);
		if (rsMap != null) {
			excel.createBody(rsMap);
		}
		// excel.createRemarks("交易明细");
		String excelFileName = URLEncoder.encode("交易明细报表" + ".xls", "UTF-8");
		response.addHeader("Content-Disposition", "attachment; filename="
				+ excelFileName);
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			excel.createFile(out);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return mapping.findForward(null);
	}

	/**
	 * 扣款统计报表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward outExcelExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm orderForm = (OrderForm) form;
		OrderBean orderBean = new OrderBean();
		//b.name,c.name,a.state,a.total,a.totalmoney,a.facctmoney,DATE_FORMAT(a.
		// date,'%Y-%m-%d')")
		List list = orderBean.listout("wht_acctout_"
				+ DateParser.getNowDate().substring(0, 4), orderForm);
		// 导出excel
		String[][] colTitles = new String[][] { { "业务类型", "接口商", "状态", "交易数量",
				"面值总额", "扣款总额", "交易日期" } };
		int size = colTitles[0].length;
		List body = new ArrayList();
		// 只取其中某些字段
		int count = 0;
		int sum1 = 0, sum2 = 0, sum3 = 0;
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			String[] recode = new String[size];
			// 取其中某些字段
			int i = 0;
			recode[i++] = temp[0];
			recode[i++] = temp[1];
			recode[i++] = temp[2];
			recode[i++] = temp[3];
			recode[i++] = Formatter.format(temp[4], Formatter.D1000F2);
			recode[i++] = Formatter.format(temp[5], Formatter.D1000F2);
			recode[i++] = temp[6];
			sum1 += Integer.parseInt(temp[3]);
			sum2 += Integer.parseInt(temp[4]);
			sum3 += Integer.parseInt(temp[5]);
			body.add(recode);
		}
		String[] sum = new String[size];
		sum[1] = "总计：";
		sum[3] = sum1 + "";
		sum[4] = Formatter.format(sum2, Formatter.D1000F2);
		sum[5] = Formatter.format(sum3, Formatter.D1000F2);
		body.add(sum);
		Map rsMap = null;
		// 转换成excel格式数据
		if (body.size() > 0) {
			rsMap = DataUtil.toNestedStringsListMap(1, body);
		}

		Excel excel = new Excel();
		excel.setCols(colTitles[0].length);
		excel.createCaption("扣款统计报表");
		excel.createColCaption(colTitles);
		if (rsMap != null) {
			excel.createBody(rsMap);
		}
		// excel.createRemarks("交易明细");
		String excelFileName = URLEncoder.encode("扣款统计报表" + ".xls", "UTF-8");
		response.addHeader("Content-Disposition", "attachment; filename="
				+ excelFileName);
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			excel.createFile(out);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return mapping.findForward(null);
	}

	/**
	 * 加款统计报表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward inExcelExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm orderForm = (OrderForm) form;
		OrderBean orderBean = new OrderBean();
		List list = orderBean.listin("wht_acctin_"
				+ DateParser.getNowDate().substring(0, 4), orderForm);
		// 导出excel
		String[][] colTitles = new String[][] { { "交易类型", "交易数量", "交易金额",
				"交易日期" } };
		int size = colTitles[0].length;
		List body = new ArrayList();
		// 只取其中某些字段
		int sum1 = 0, sum2 = 0;
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			String[] recode = new String[size];
			// 取其中某些字段
			int i = 0;
			recode[i++] = temp[0];
			recode[i++] = temp[1];
			recode[i++] = temp[2];
			recode[i++] = temp[3];
			recode[i++] = Formatter.format(temp[4], Formatter.D1000F2);
			recode[i++] = Formatter.format(temp[5], Formatter.D1000F2);
			recode[i++] = temp[6];
			sum1 += Integer.parseInt(temp[1]);
			sum2 += Integer.parseInt(temp[2]);
			body.add(recode);
		}
		String[] sum = new String[size];
		sum[0] = "总计：";
		sum[1] = sum1 + "";
		sum[2] = Formatter.format(sum2, Formatter.D1000F2);
		body.add(sum);
		Map rsMap = null;
		// 转换成excel格式数据
		if (body.size() > 0) {
			rsMap = DataUtil.toNestedStringsListMap(1, body);
		}

		Excel excel = new Excel();
		excel.setCols(colTitles[0].length);
		excel.createCaption("加款统计报表");
		excel.createColCaption(colTitles);
		if (rsMap != null) {
			excel.createBody(rsMap);
		}
		// excel.createRemarks("交易明细");
		String excelFileName = URLEncoder.encode("加款统计报表" + ".xls", "UTF-8");
		response.addHeader("Content-Disposition", "attachment; filename="
				+ excelFileName);
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			excel.createFile(out);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return mapping.findForward(null);
	}

	/**
	 * 订单列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward listReverse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm orderForm = (OrderForm) form;
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		if (!"0".equals(userForm.getRoleType())
				&& !"1".equals(userForm.getRoleType())) {
			orderForm.setUserId(userForm.getUserno());
		}
		// if(userForm.getRoleType().equals("1")){
		// orderForm.setRoleFilter(
		// "SELECT user_no FROM sys_user a,sys_area b,sys_role r WHERE b.sa_id=a.user_site AND a.user_site="
		// +userForm.getUsersite()+
		// " and a.user_role=r.sr_id and r.sr_type!=0 and r.sr_type!=1  and r.sr_type<>4"
		// );
		// }
		if (userForm.getRoleType().equals("2")) {
			orderForm
					.setRoleFilter("select user_no from sys_user where user_pt = "
							+ userForm.getUser_id()
							+ " and user_site="
							+ userForm.getUsersite());
		}
		if (userForm.getRoleType().equals("3")) {
			orderForm.setRoleFilter(userForm.getUserno());
		}
		if (userForm.getRoleType().equals("4")) {
			orderForm.setRoleFilter(userForm.getUserno());
		}
		OrderBean orderBean = new OrderBean();
		PageAttribute page = new PageAttribute(orderForm.getCurPage(),
				Constant.PAGE_SIZE);
		page.setRsCount(orderBean.countOrderReverse("wht_orderform_"
				+ DateParser.getNowDateTable(), orderForm));
		SysUserInterface sit = new SysUserInterface();
		List list = orderBean.listOrderReverse("wht_orderform_"
				+ DateParser.getNowDateTable(), orderForm, page, userForm
				.getRoleType());
		String tString = request.getParameter("msg");
		if (null != tString && "0".equals(tString)) {
			request.setAttribute("mess", "操作成功");
		} else if (null != tString && "1".equals(tString)) {
			request.setAttribute("mess", "操作失败");
		} else if (null != tString && "2".equals(tString)) {
			request.setAttribute("mess", "该月冲正数量已用完");
		} else if (null != tString && "3".equals(tString)) {
			request.setAttribute("mess", "系统繁忙请稍后再试");
		} else if (null != tString && "4".equals(tString)) {
			request.setAttribute("mess", "该种交易类型不能冲正");
		} else if (null != tString && "5".equals(tString)) {
			request.setAttribute("mess", "该交易已退费,不能重复退费");
		}if (null != tString && "6".equals(tString)) {
			request.setAttribute("mess", "请稍后发起冲正!");
		}
		request.setAttribute("orderList", list);
		request.setAttribute("page", page);
		request.setAttribute("areaSel", getStingSel(sit.listArea(), "区域"));
		request.setAttribute("order", orderForm);
		return mapping.findForward("reverseSuccess");
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
	 * 订单详细
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward orderDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OrderForm orderForm = (OrderForm) form;
		OrderBean orderBean = new OrderBean();
		if (null != orderForm.getService()
				&& "A9999".equals(orderForm.getService())) {// 交通罚款
			JtfkForm jtfk = orderBean.getOrderJtfk(orderForm.getTradeserial());
			request.setAttribute("jtfk", jtfk);
			return mapping.findForward("jtfkDetail");
		} else if (null != orderForm.getService()
				&& "P0001".equals(orderForm.getService())) {// 话费充值
			request.setAttribute("order", orderForm);
			return mapping.findForward("chargeDetail");
		} else if (null != orderForm.getService()
				&& "Q0001".equals(orderForm.getService())) {// Q币充值
			request.setAttribute("order", orderForm);
			return mapping.findForward("qbDetail");
		} else {
			SysUserInterface sit = new SysUserInterface();
			SysRole role = new SysRole();
			PageAttribute page = new PageAttribute(orderForm.getCurPage(),
					Constant.PAGE_SIZE);
			page.setRsCount(orderBean.countOrder("wlt_orderform_"
					+ DateParser.getNowDateTable(), orderForm));
			List list = orderBean.listOrder("wlt_orderform_"
					+ DateParser.getNowDateTable(), orderForm, page);
			request.setAttribute("orderList", list);
			request.setAttribute("areaList", sit.listArea());
			request.setAttribute("itypeList", sit.listInterfaceType());
			request.setAttribute("order", orderForm);
			request.setAttribute("rolelist", role.getRoleType(null));
			request.setAttribute("serviceList", orderBean.listSevice());
			return mapping.findForward("success");
		}
	}

	/**
	 * 返点
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward retpoint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MobileChargeService service = new MobileChargeService();
		String userId = request.getParameter("uid");
		String fee = request.getParameter("fee");
		String id = request.getParameter("id");
		String sepNo = Tools.getSeqNo("");
		String nowTime = Tools.getNow();
		String fundAcct02 = service.getUserFundAcct("02", userId);
		SysUser sysUser = new SysUser();
		sysUser.retpoint(id, fundAcct02, fee, sepNo, nowTime, "", "48", sepNo);
		return mapping.findForward("retpoint");
	}

	/**
	 * 导出中石化下级用户订单明细
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward zshuserExcelExport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderForm orderForm = (OrderForm) form;
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		if (userForm.getRoleType().equals("2")) {
			orderForm.setRoleFilter("select user_id from sys_user where user_pt = "
							+ userForm.getUser_id());
		}
		OrderBean orderBean = new OrderBean();
		PageAttribute page = new PageAttribute(orderForm.getCurPage(),
				Constant.PAGE_SIZE);
		 String[] str=orderBean.countZshUserOrder("wht_orderform_"+ DateParser.getNowDateTable(), orderForm, Integer.parseInt(userForm.getUser_id()));
		 int con =str[0]==null?0:Integer.parseInt(str[0]);
		page.setRsCount(con);
		List list = orderBean.listZshUserOrder("wht_orderform_"+ DateParser.getNowDateTable(), orderForm, page, Integer.parseInt(userForm.getUser_id()),-1);
		// 导出excel
		String[][] colTitles = new String[][] { { "序号", "区域", "条码类型", "订单号",
				"交易号码", "订单状态", "业务类型", "交易金额(元)", "提交时间", "修改时间", "登陆账号" } };
		int size = colTitles[0].length;
		List body = new ArrayList();
		// 只取其中某些字段
		int count = 0;
		int sum1 = 0;
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			String[] recode = new String[size];
			// 取其中某些字段
			int i = 0;
			recode[i++] = ++count + "";
			recode[i++] = temp[0];
			recode[i++] = "条码" + temp[16];
			recode[i++] = temp[1];
			recode[i++] = temp[2];
			recode[i++] = temp[9];
			recode[i++] = temp[15];
			recode[i++] = Float.parseFloat(temp[5]) / 1000 + "";
			recode[i++] = temp[7];
			recode[i++] = temp[8];
			recode[i++] = temp[11];
			sum1 += Integer.parseInt(temp[5]);
			body.add(recode);
		}
		String[] sum = new String[size];
		sum[6] = "总计：";
		sum[7] = Formatter.format(sum1, Formatter.D1000F2);
		body.add(sum);
		Map rsMap = null;
		// 转换成excel格式数据
		if (body.size() > 0) {
			rsMap = DataUtil.toNestedStringsListMap(1, body);
		}

		Excel excel = new Excel();
		excel.setCols(colTitles[0].length);
		excel.createCaption("交易明细报表");
		excel.createColCaption(colTitles);
		if (rsMap != null) {
			excel.createBody(rsMap);
		}
		// excel.createRemarks("交易明细");
		String excelFileName = URLEncoder.encode("交易明细报表" + ".xls", "UTF-8");
		response.addHeader("Content-Disposition", "attachment; filename="
				+ excelFileName);
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			excel.createFile(out);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return mapping.findForward(null);
	}

	/**
	 * Q币拆单列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward caidanList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userno=request.getParameter("userno");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String tradeobject=request.getParameter("tradeobject");
		String oldorderid=request.getParameter("oldorderid");
//		
//		if( "".equals(userno) &&
//			 "".equals(startDate) &&
//			 "".equals(endDate)&&
//			 "".equals(tradeobject) &&
//			 "".equals(oldorderid)){
//			return new ActionForward("/business/qbOrderAffiliated.jsp");
//		}
//		
		int index=1;
		int lastIndex=1;
	    int pagesize=15;
		
		if(request.getParameter("index")!=null && !"".equals(request.getParameter("index")))
		{
			index=Integer.parseInt(request.getParameter("index"));
		}
		if(index<=0)
			index=1;
		int count=OrderBean.caidanCount(userno, startDate.replace("-","")+"000000", endDate.replace("-","")+"235959", tradeobject, oldorderid);
		if(count>0)
			lastIndex=count%pagesize==0?count/pagesize:count/pagesize+1;
		
		if(index>=lastIndex)
			index=lastIndex;
		
		List<Object[]> arrList=OrderBean.caidanList(userno, startDate.replace("-","")+"000000", endDate.replace("-","")+"235959", tradeobject, oldorderid,index,pagesize);
		request.setAttribute("arrList",arrList);
		request.setAttribute("index",index);
		request.setAttribute("lastIndex",lastIndex);
		request.setAttribute("userno",userno);
		request.setAttribute("startDate",startDate);
		request.setAttribute("endDate",endDate);
		request.setAttribute("tradeobject",tradeobject);
		request.setAttribute("oldorderid",oldorderid);
		return new ActionForward("/business/qbOrderAffiliated.jsp");
	}
	
	/**
	 * 流量补充订单列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward flow2list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String flag=request.getParameter("flag");
		if(null==flag){
			flag="0";
		}
		OrderForm orderForm = (OrderForm) form;
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		if (!userForm.getRoleType().equals("0")
				&&!userForm.getRoleType().equals("1")) {
			request.setAttribute("mess", "权限不足");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		OrderBean orderBean = new OrderBean();
		PageAttribute page = new PageAttribute(orderForm.getCurPage(),
				Constant.PAGE_SIZE);
		SysUserInterface sit = new SysUserInterface();
		HashMap<String, Object> rs = orderBean.listflowOrder("wht_flow_Reissue",orderForm, page,flag);
		request.setAttribute("page", page);
		request.setAttribute("areaSel", getStingSel(sit.listArea(), "区域"));
		request.setAttribute("itypeSel", getStingSel(sit.listflowInterfaceType(),
				"渠道商"));
		request.setAttribute("order", orderForm);
		if("0".equals(flag)){//查询
		request.setAttribute("totalFee", rs.get("total"));
		List list=(List)rs.get("datas");
		request.setAttribute("orderList", list.size()>0?list:null);
		return new ActionForward("/business/wltfloworderlist.jsp");
		}else {//导出
			ArrayList list=new ArrayList();
	  		String[] headerNameList=new String[]{"说明","内部订单号","号码","渠道商","运营商","交易时间","结束时间","状态","面额","大客户订单号","渠道商名称"};
	  		list.add(headerNameList);
	  		request.setAttribute("count",headerNameList.length);//总列数
	  		request.setAttribute("excelName","流量补充数据"+Tools.getNowDate());//输出的 excel 文件名称
	  		request.setAttribute("HeaderName","流量补充数据"); //表格标题名称
	  		request.setAttribute("HeaderList",list); //表格标头 类别
	  		request.setAttribute("DataList", rs.get("datas")); //数据列表
	    	return new ActionForward("/rights/flowdownload.jsp");
		}
	}
	/**
	 * 修改流量补充表订单状态
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward changeflow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String orderid=request.getParameter("orderid");
		String st=request.getParameter("st");
		String paid=request.getParameter("otype");
		Log.info("内部订单号:"+orderid+"修改为:"+st+" 修改内部订单状态成功回调大客户告知结果开始...");
		int rs=5;
		if(null==orderid||null==st||null==paid||(!"0".equals(st)&&!"1".equals(st))){
			rs=1;//参数为空
		}else{
		    DBService db=null;
		    try{
            db=new DBService();
		    if(1!=db.update("update wht_flow_Reissue set o_state="+st
		    		+" WHERE o_tradeserial='"+orderid+"'")){
		    	rs=2;//修改失败
		    }else{//修改成功 调用 调用大客户回调
		    	rs=0;
		    	String[] str=db.getStringArray(" SELECT o_tradeserial,o_type,o_notityUrl,o_writeoff FROM wht_flow_Reissue WHERE o_writecheck='"+paid+"' AND r_dis=0");
		    	if("1000".equals(str[1])){//腾讯回调
		    		TencentOrdersBean obj=new TencentOrdersBean();
		    	    obj.setPaipai_dealid(paid);
		    	    obj.setSeqNo1(str[0]);
		    		Flows_Public_Method.Is_Back(null, st, "1000", obj, null);
		    	}else if("1001".equals(str[1])){//京东
		    		JdOrderBean obj=new JdOrderBean();
		    	    obj.setFillOrderNo(paid);
		    	    obj.setWh_order_num(str[0]);
		    	    obj.setFillAmount(str[3].replace("mb", ""));
		    	    obj.setNotifyUrl(str[2]);
		    		Flows_Public_Method.Is_Back(null, st, "1001", obj, null);
		    	}
		    	Log.info("内部订单号:"+orderid+"修改为:"+st+" 修改内部订单状态成功回调大客户告知结果完成");
		    }
		    }catch(SQLException ex){
				Log.error("内部订单号:"+orderid+"修改为:"+st+" 出错:"+ex.toString());
			}finally{
				if(null!=db)
				db.close();
			}
		} 
		PrintWriter pt=response.getWriter();
		pt.print(rs);
		pt.flush();
		pt.close();
		return null;
	}

}