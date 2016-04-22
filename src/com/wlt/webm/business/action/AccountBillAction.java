package com.wlt.webm.business.action;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commsoft.epay.util.logging.Log;
import com.ejet.util.DataUtil;
import com.ejet.util.Excel;
import com.wlt.webm.business.bean.AcctBillBean;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.business.bean.OrderBean;
import com.wlt.webm.business.form.AcctBillForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.rights.bean.SysRole;
import com.wlt.webm.rights.bean.SysUser;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.scputil.DateParser;
import com.wlt.webm.tool.FloatArith;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.PageAttribute;
import com.wlt.webm.util.format.Formatter;

/**
 * 账户明细管理<br>
 */
public class AccountBillAction extends DispatchAction {

	/**
	 * 账户明细列表
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
		AcctBillForm acctForm = (AcctBillForm) form;
		SysUserForm userSession = (SysUserForm) request.getSession()
				.getAttribute("userSession");

		// if(userSession.getRoleType().equals("1")){
		// acctForm.setRoleFilter(
		// "  and e.user_no in (SELECT user_no FROM sys_user a,sys_area b,sys_role r WHERE b.sa_id=a.user_site AND a.user_site="
		// +userSession.getUsersite()+
		// " and a.user_role=r.sr_id and r.sr_type!=0 and r.sr_type!=1 )");
		// }
		String dailishang = acctForm.getDailishang();
		if(dailishang==null){
			dailishang="";
		}
		String exp1 = acctForm.getExp1();
		if(exp1==null){
			exp1="";
		}
		if (userSession.getRoleType().equals("0")
				|| userSession.getRoleType().equals("1")) {
			if ( !dailishang.equals("") && 
					exp1.equals("")) {
				acctForm
						.setRoleFilter(" and e.user_no in ( SELECT user_no FROM sys_user WHERE user_pt = (SELECT user_id FROM sys_user WHERE user_login = '"
								+ dailishang + "'))");
			} else if ( !exp1.equals("") && 
					 dailishang.equals("")) {
				acctForm
						.setRoleFilter(" and e.user_no in ( SELECT user_no FROM sys_user WHERE user_pt in (SELECT user_id FROM sys_user WHERE exp1 = '"
								+ exp1 + "'))");
			} else if (!exp1.equals("") && !dailishang.equals("")) {
				acctForm
						.setRoleFilter(" and e.user_no in ( SELECT user_no FROM sys_user WHERE user_pt = (SELECT user_id FROM sys_user WHERE user_login = '"
								+ dailishang + "' and exp1 = '" + exp1 + "') )");
			}

		}
		if(userSession.getRoleType().equals("1") && BiProd.getdesc(userSession.getUserno()))
		{
			acctForm.setRoleFilter("  and e.user_no in (SELECT user_no FROM sys_user a WHERE   a.user_id="
					+ userSession.getUser_id() + ")");
		}
		if (userSession.getRoleType().equals("2")) {
			acctForm
					.setRoleFilter("  and e.user_no in (SELECT user_no FROM sys_user a WHERE   a.user_id="
							+ userSession.getUser_id() + ")");
		}
		if (userSession.getRoleType().equals("3")) {
			acctForm.setRoleFilter("  and e.user_no in ("
					+ userSession.getUserno() + ")");
		}
		if (userSession.getRoleType().equals("4")) {
			acctForm.setRoleFilter("  and e.user_no in ("
					+ userSession.getUserno() + ")");
		}
		AcctBillBean acctBean = new AcctBillBean();
		SysRole role = new SysRole();
		PageAttribute page = new PageAttribute(acctForm.getCurPage(),
				Constant.PAGE_SIZE);
		int totalCount = acctBean.countAcctBill("wht_acctbill_"
				+ DateParser.getNowDateTable(), acctForm, page);
		page.setRsCount(totalCount);
		List list = acctBean.listAcctBill("wht_acctbill_"
				+ DateParser.getNowDateTable(), acctForm, page);
		int curTotal = 0;
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			curTotal += Integer.parseInt(temp[5]);
		}
		request.setAttribute("curTotal", FloatArith.div(curTotal, 1000));
		double totalFee = acctBean.getFeeForTotal("wht_acctbill_"
				+ DateParser.getNowDateTable(), acctForm);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("totalFee", FloatArith.div(totalFee, 1000));
		request.setAttribute("orderList", list);
		request.setAttribute("tradeSel", getStingSel(acctBean.listTrade(),
				"交易类型"));
		request.setAttribute("acctbill", acctForm);
		request.setAttribute("areaSel", getStingSel(acctBean.listArea(), "区域"));
		request.setAttribute("page", page);
		return mapping.findForward("success");
	}

	/**
	 * 下级账户明细列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward loweruserlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AcctBillForm acctForm = (AcctBillForm) form;
		SysUserForm userSession = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		AcctBillBean acctBean = new AcctBillBean();
		if (userSession.getRoleType().equals("0")
				|| userSession.getRoleType().equals("1")) {
			if (acctForm.getDailishang() != null
					&& !acctForm.getDailishang().equals("")) {
				acctForm.setRoleFilter(" and e.user_id in ("
						+ acctForm.getDailishang() + ")");
			} else {
				if (userSession.getRoleType().equals("1")) {
					String userpt = userSession.getUser_id();
					List userPtlist = acctBean.getDaiLiUser(userSession
							.getUser_id()); // 获得下级编号
					for (int i = 0; i < userPtlist.size(); i++) {
						String str = (String) userPtlist.get(i);
						userpt += "," + str;
					}
					acctForm.setRoleFilter(" and e.user_pt in (" + userpt
							+ ") ");
				}
			}
		}
		if (userSession.getRoleType().equals("2")) {
			acctForm
					.setRoleFilter(" and e.user_no in (SELECT user_no FROM sys_user a WHERE   a.user_id="
							+ userSession.getUser_id() + ") ");
		}
		if (userSession.getRoleType().equals("3")) {
			acctForm.setRoleFilter(" and e.user_no in ( "
					+ userSession.getUserno() + ")");
		}
		if (userSession.getRoleType().equals("4")) {
			acctForm.setRoleFilter(" and e.user_no in ( "
					+ userSession.getUserno() + ")");
		}
		OrderBean orderBean = new OrderBean();
		SysRole role = new SysRole();
		PageAttribute page = new PageAttribute(acctForm.getCurPage(),
				Constant.PAGE_SIZE);
		int totalCount = acctBean.countAcctBill("wht_acctbill_"
				+ DateParser.getNowDateTable(), acctForm, page);
		page.setRsCount(totalCount);
		List list = acctBean.listAcctBill("wht_acctbill_"
				+ DateParser.getNowDateTable(), acctForm, page);
		int curTotal = 0;
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			curTotal += Integer.parseInt(temp[5]);
		}
		request.setAttribute("curTotal", FloatArith.div(curTotal, 1000));
		double totalFee = acctBean.getFeeForTotal("wht_acctbill_"
				+ DateParser.getNowDateTable(), acctForm);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("totalFee", FloatArith.div(totalFee, 1000));
		request.setAttribute("orderList", list);
		request.setAttribute("tradeSel", getStingSel(acctBean.listTrade(),
				"交易类型"));
		request.setAttribute("acctbill", acctForm);
		request.setAttribute("areaSel", getStingSel(acctBean.listArea(), "区域"));
		request.setAttribute("dailishangSel", getStingSel(orderBean
				.getDaiLiShangUser(userSession.getUser_id()), "代理商"));
		request.setAttribute("page", page);
		return new ActionForward("/business/wltloweruseracctbilllist.jsp");
	}

	/**
	 * 奖励明细
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward awardsList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AcctBillForm acctForm = (AcctBillForm) form;
		SysUserForm userSession = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		if (!"0".equals(userSession.getRoleType())
				&& !"1".equals(userSession.getRoleType())) {
			acctForm.setUserId(userSession.getUser_id());
		}
		AcctBillBean acctBean = new AcctBillBean();
		PageAttribute page = new PageAttribute(acctForm.getCurPage(),
				Constant.PAGE_SIZE);
		int totalCount = acctBean
				.countAwards("wht_monthawards", acctForm, page);
		page.setRsCount(totalCount);
		List list = acctBean.listAwards("wht_monthawards", acctForm, page);
		request.setAttribute("awardsList", list);
		request.setAttribute("acctbill", acctForm);
		request.setAttribute("page", page);
		return new ActionForward("/business/whtawardslist.jsp");
	}
	/**
	 * 奖励明细(一级代理)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward awardsListTwo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
		if(!"0".equals(userSession.getRoleType())){
			request.setAttribute("mess","您没有操作权限!");
			return new ActionForward("/business/whtawardslistTwo.jsp");
		}
		String login=request.getParameter("login");
		String facct=request.getParameter("facct");
		String mom=request.getParameter("money");
		String yearMoneth=request.getParameter("yearMoneth");
		String bs=request.getParameter("bs");
		if(login!=null && !"".equals(login)
				&& facct!=null && !"".equals(facct)
					&& mom!=null && !"".equals(mom)
						&& yearMoneth!=null && !"".equals(yearMoneth)
							&& bs!=null && !"".equals(bs))
		{
			if(!"01".equals(facct.substring(facct.length()-2,facct.length()))){
				facct=facct.substring(0,facct.length()-2)+"01";
			}
			//奖励
			DBService dbService = null;
			String time = Tools.getNow3();
			String tableName = "wht_acctbill_" + time.substring(2, 6);
			try {
				float money = Float.parseFloat(mom)*1000;
				String serial = Tools.getAccountSerial(time, login);
				dbService = new DBService();
				int left = dbService
						.getInt("select accountleft from wht_childfacct where childfacct='"
								+ facct + "'");
				float left1 = left + money;
				dbService.setAutoCommit(false);
				String sql0 = "update wht_monthawards_oneAgent SET state=0,operate='" + time
						+ "',operatewho='" + userSession.getUsername() + "',agentmoney="+money+" WHERE statisticdate='"+yearMoneth+"'  AND login='" + login + "' AND bs='"+bs+"'";
				String sql1 = "update wht_childfacct set accountleft=accountleft+"
						+ money + " where childfacct='" + facct + "'";
				Object[] params = { null, facct, serial, facct, time, money, money,
						18, "月度佣金奖励(一级)", 0, time, left1, serial, facct, 2 };
				dbService.update(sql0);
				dbService.update(sql1);
				dbService.logData(15, params, tableName);
				dbService.commit();
				request.setAttribute("mess","奖励成功!");
			} catch (Exception ex) {
				dbService.rollback();
				ex.printStackTrace();
				request.setAttribute("mess","奖励失败!系统异常");
			}finally {
				if(dbService!=null){
					dbService.close();
				}
			}
		}
			
		
		AcctBillForm acctForm = (AcctBillForm) form;
		List arryList=null;
		
		String str=" WHERE 1=1 ";
		if("".equals(acctForm.getState()) || acctForm.getState()==null || "-1".equals(acctForm.getState()))
		{
			acctForm.setState("-1");
		}
		else
		{
			str=str+" AND aa.state="+acctForm.getState();
		}
		if("".equals(acctForm.getStartDate()) || acctForm.getStartDate()==null)
		{
			acctForm.setStartDate(Tools.getNowDate1());
		}
		if("".equals(acctForm.getEndDate()) || acctForm.getEndDate()==null)
		{
			acctForm.setEndDate(Tools.getNowDate1());
		}
		str=str+" AND aa.logdate>='"+acctForm.getStartDate().replace("-","")+"000000"+"' AND aa.logdate<='"+acctForm.getEndDate().replace("-","")+"235959"+"' ";
		
		String sql="SELECT aa.login,aa.userno,  CASE WHEN aa.usertype=1 THEN '一级代理' END AS usertype,  "+
			 " CASE WHEN aa.phone_type=0 AND aa.areacodeType=1 THEN '本省电信'   "+
			 "  WHEN aa.phone_type=0 AND aa.areacodeType=2 THEN '外省电信'      "+
			 " WHEN aa.phone_type=1 AND aa.areacodeType=1 THEN '本省移动'     WHEN "+ 
			 " aa.phone_type=1 AND aa.areacodeType=2 THEN '外省移动'     WHEN  "+
			 " aa.phone_type=2 AND aa.areacodeType=1 THEN '本省联通'     WHEN  "+
			 " aa.phone_type=2 AND aa.areacodeType=2 THEN '外省联通'    WHEN  "+
			 " aa.phone_type=3 AND aa.areacodeType=3 THEN 'QQ币' END AS phonetype, "+  
			 " aa.money,  "+
			 " aa.phone_type ,  "+
			 " aa.areacodeType, "+
			 " 11, "+
			 " aa.statisticdate, "+
			 " aa.state, "+
			 "  aa.childfacct,aa.bs,FORMAT(aa.agentmoney/1000,3)  "+
			 " FROM  wht_monthawards_oneAgent AS aa ";
		sql=sql+str;
		
		sql=sql+"  ORDER BY  aa.logdate,aa.login,aa.phone_type,aa.areacodeType ASC";
		DBService db=null;
		try {
			db=new DBService();
			arryList=db.getList(sql, null);
			sql="";
			for(int i=0;i<arryList.size();i++)
			{
				Object[] ss=(Object[])arryList.get(i);
				if("1".equals(ss[5]) && "1".equals(ss[6]))//本省移动
				{
					sql="SELECT CASE WHEN ac.percent IS NULL THEN -1 ELSE FORMAT("+ss[4]+"*(ac.percent/100)/1000,3) END,CONCAT(FORMAT(ac.monbegin/1000,1),'-',FORMAT(ac.monend/1000,1)),CONCAT(ac.percent,'%') FROM " +
							" sys_oneagentmaps ab,sys_oneagent ac " +
							" WHERE ab.groupsID=ac.groupsID " +
							" AND  ab.userno='"+ss[1]+"' " +
									" AND ac.tradetype=1 AND ac.monbegin<="+ss[4]+" AND ac.monend>"+ss[4]+"";
				}
				if("1".equals(ss[5]) && "2".equals(ss[6]))//外省移动
				{
					sql="SELECT CASE WHEN ac.percent IS NULL THEN -1 ELSE FORMAT("+ss[4]+"*(ac.percent/100)/1000,3) END,CONCAT(FORMAT(ac.monbegin/1000,1),'-',FORMAT(ac.monend/1000,1)),CONCAT(ac.percent,'%') FROM " +
							" sys_oneagentmaps ab,sys_oneagent ac " +
							" WHERE ab.groupsID=ac.groupsID " +
							" AND  ab.userno='"+ss[1]+"' " +
									" AND ac.tradetype=2 AND ac.monbegin<="+ss[4]+" AND ac.monend>"+ss[4]+"";
				}
				
				if("0".equals(ss[5]) && "1".equals(ss[6]))//本省电信
				{
					sql="SELECT CASE WHEN ac.percent IS NULL THEN -1 ELSE FORMAT("+ss[4]+"*(ac.percent/100)/1000,3) END,CONCAT(FORMAT(ac.monbegin/1000,1),'-',FORMAT(ac.monend/1000,1)),CONCAT(ac.percent,'%') FROM " +
							" sys_oneagentmaps ab,sys_oneagent ac " +
							" WHERE ab.groupsID=ac.groupsID " +
							" AND  ab.userno='"+ss[1]+"' " +
									" AND ac.tradetype=5 AND ac.monbegin<="+ss[4]+" AND ac.monend>"+ss[4]+"";
				}
				if("0".equals(ss[5]) && "2".equals(ss[6]))//外省电信
				{
					sql="SELECT CASE WHEN ac.percent IS NULL THEN -1 ELSE FORMAT("+ss[4]+"*(ac.percent/100)/1000,3) END,CONCAT(FORMAT(ac.monbegin/1000,1),'-',FORMAT(ac.monend/1000,1)),CONCAT(ac.percent,'%') FROM " +
							" sys_oneagentmaps ab,sys_oneagent ac " +
							" WHERE ab.groupsID=ac.groupsID " +
							" AND  ab.userno='"+ss[1]+"' " +
									" AND ac.tradetype=6 AND ac.monbegin<="+ss[4]+" AND ac.monend>"+ss[4]+"";
				}
				
				if("2".equals(ss[5]) && "1".equals(ss[6]))//本省联通
				{
					sql="SELECT CASE WHEN ac.percent IS NULL THEN -1 ELSE FORMAT("+ss[4]+"*(ac.percent/100)/1000,3) END,CONCAT(FORMAT(ac.monbegin/1000,1),'-',FORMAT(ac.monend/1000,1)),CONCAT(ac.percent,'%') FROM " +
							" sys_oneagentmaps ab,sys_oneagent ac " +
							" WHERE ab.groupsID=ac.groupsID " +
							" AND  ab.userno='"+ss[1]+"' " +
									"  AND ac.tradetype=3 AND ac.monbegin<="+ss[4]+" AND ac.monend>"+ss[4]+"";
				}
				if("2".equals(ss[5]) && "2".equals(ss[6]))//外省联通
				{
					sql="SELECT CASE WHEN ac.percent IS NULL THEN -1 ELSE FORMAT("+ss[4]+"*(ac.percent/100)/1000,3) END,CONCAT(FORMAT(ac.monbegin/1000,1),'-',FORMAT(ac.monend/1000,1)),CONCAT(ac.percent,'%') FROM " +
							" sys_oneagentmaps ab,sys_oneagent ac " +
							" WHERE ab.groupsID=ac.groupsID " +
							" AND  ab.userno='"+ss[1]+"' " +
									"  AND ac.tradetype=4 AND ac.monbegin<="+ss[4]+" AND ac.monend>"+ss[4]+"";
				}
				if("3".equals(ss[5]) && "3".equals(ss[6]))//qq币
				{
					sql="SELECT CASE WHEN ac.percent IS NULL THEN -1 ELSE FORMAT("+ss[4]+"*(ac.percent/100)/1000,3) END,CONCAT(FORMAT(ac.monbegin/1000,1),'-',FORMAT(ac.monend/1000,1)),CONCAT(ac.percent,'%') FROM " +
							" sys_oneagentmaps ab,sys_oneagent ac " +
							" WHERE ab.groupsID=ac.groupsID " +
							" AND  ab.userno='"+ss[1]+"' " +
									"  AND ac.tradetype=7 AND ac.monbegin<="+ss[4]+" AND ac.monend>"+ss[4]+"";
				}
				String[] stris=db.getStringArray(sql,null);
				if(stris==null)
				{
					ss[5]="";
					ss[6]="";
					ss[7]="";
				}
				else
				{
					ss[5]=stris[0];
					ss[6]=stris[1];
					ss[7]=stris[2];
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(null!=db)
				db.close();
		}
		request.setAttribute("arryList", arryList);
		request.setAttribute("acctbill", acctForm);
		return new ActionForward("/business/whtawardslistTwo.jsp");
	}

	/**
	 * 账户明细
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward excelExportList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AcctBillForm acctForm = (AcctBillForm) form;
		SysUserForm userSession = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		// if(userSession.getRoleType().equals("1")){
		// acctForm.setRoleFilter(
		// "and e.user_no in (SELECT user_no FROM sys_user a,sys_area b,sys_role r WHERE b.sa_id=a.user_site AND a.user_site="
		// +userSession.getUsersite()+
		// " and a.user_role=r.sr_id and r.sr_type!=0 and r.sr_type!=1 )");
		// }
		String dailishang = acctForm.getDailishang();
		if(dailishang==null){
			dailishang="";
		}
		String exp1 = acctForm.getExp1();
		if(exp1==null){
			exp1="";
		}
		if (userSession.getRoleType().equals("0")
				|| userSession.getRoleType().equals("1")) {
			if ( !dailishang.equals("") && 
					exp1.equals("")) {
				acctForm
						.setRoleFilter(" and e.user_no in ( SELECT user_no FROM sys_user WHERE user_pt = (SELECT user_id FROM sys_user WHERE user_login = '"
								+ dailishang + "'))");
			} else if ( !exp1.equals("") && 
					 dailishang.equals("")) {
				acctForm
						.setRoleFilter(" and e.user_no in ( SELECT user_no FROM sys_user WHERE user_pt in (SELECT user_id FROM sys_user WHERE exp1 = '"
								+ exp1 + "'))");
			} else if (!exp1.equals("") && !dailishang.equals("")) {
				acctForm
						.setRoleFilter(" and e.user_no in ( SELECT user_no FROM sys_user WHERE user_pt = (SELECT user_id FROM sys_user WHERE user_login = '"
								+ dailishang + "' and exp1 = '" + exp1 + "') )");
			}

		}
		if (userSession.getRoleType().equals("2")) {
			acctForm
					.setRoleFilter("and e.user_no in (SELECT user_no FROM sys_user a WHERE   a.user_id="
							+ userSession.getUser_id() + ")");
		}
		if (userSession.getRoleType().equals("3")) {
			acctForm.setRoleFilter("and e.user_no in ( "
					+ userSession.getUserno() + ")");
		}
		if (userSession.getRoleType().equals("4")) {
			acctForm.setRoleFilter("and e.user_no in ( "
					+ userSession.getUserno() + ")");
		}
		AcctBillBean acctBean = new AcctBillBean();
		SysRole role = new SysRole();
		PageAttribute page = new PageAttribute(acctForm.getCurPage(),
				Constant.PAGE_SIZE);
		page.setRsCount(acctBean.countAcctBill("wht_acctbill_"
				+ DateParser.getNowDateTable(), acctForm));
		acctForm.setExcel(true);
		List list = acctBean.listAcctBill("wht_acctbill_"
				+ DateParser.getNowDateTable(), acctForm, page);
		// 导出excel
		String[][] colTitles = new String[][] { { "序号", "处理号", "订单号", "交易账号",
				"资金子账号", "交易描述", "交易时间", "预交易金额", "实际交易金额", "账号余额", "支出类别",
				"转出或转入账号", "状态" } };
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
			recode[i++] = temp[11];
			recode[i++] = temp[2];
			recode[i++] = temp[1];
			recode[i++] = temp[7];
			recode[i++] = temp[9];
			recode[i++] = FloatArith.div(Integer.parseInt(temp[4]), 1000) + "";
			recode[i++] = FloatArith.div(Integer.parseInt(temp[5]), 1000) + "";
			sum1 += Integer.parseInt(temp[5]);
			recode[i++] = FloatArith.div(Integer.parseInt(temp[10]), 1000) + "";
			recode[i++] = temp[13];
			recode[i++] = temp[12];
			recode[i++] = temp[8];
			body.add(recode);
		}
		String[] sum = new String[size];
		sum[7] = "总计：";
		sum[8] = Formatter.format(sum1, Formatter.D1000F2);
		body.add(sum);
		Map rsMap = null;
		// 转换成excel格式数据
		if (body.size() > 0) {
			rsMap = DataUtil.toNestedStringsListMap(1, body);
		}
		Excel excel = new Excel();
		excel.setCols(colTitles[0].length);
		excel.createCaption("账务查询报表");
		excel.createColCaption(colTitles);
		if (rsMap != null) {
			excel.createBody(rsMap);
		}
		// excel.createRemarks("交易明细");
		String excelFileName = URLEncoder.encode("账务查询报表" + ".xls", "UTF-8");
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
	 * 下级账户明细
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward lowerUserExcelExportList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AcctBillForm acctForm = (AcctBillForm) form;
		SysUserForm userSession = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		AcctBillBean acctBean = new AcctBillBean();
		if (userSession.getRoleType().equals("0")
				|| userSession.getRoleType().equals("1")) {
			if (acctForm.getDailishang() != null
					&& !acctForm.getDailishang().equals("")) {
				acctForm.setRoleFilter(" and e.user_no in ("
						+ acctForm.getDailishang() + ")");
			} else {
				if (userSession.getRoleType().equals("1")) {
					if (userSession.getRoleType().equals("1")) {
						String userpt = userSession.getUser_id();
						List userPtlist = acctBean.getDaiLiUser(userSession
								.getUser_id()); // 获得下级编号
						for (int i = 0; i < userPtlist.size(); i++) {
							String str = (String) userPtlist.get(i);
							userpt += "," + str;
						}
						acctForm.setRoleFilter("and e.user_pt in (" + userpt
								+ ")");
					}
				}
			}
		}
		if (userSession.getRoleType().equals("2")) {
			acctForm
					.setRoleFilter("and e.user_no in (SELECT user_no FROM sys_user a WHERE   a.user_id="
							+ userSession.getUser_id() + ") ");
		}
		if (userSession.getRoleType().equals("3")) {
			acctForm.setRoleFilter("and e.user_no in ( "
					+ userSession.getUserno() + ")");
		}
		if (userSession.getRoleType().equals("4")) {
			acctForm.setRoleFilter("and e.user_no in ( "
					+ userSession.getUserno() + ")");
		}
		SysRole role = new SysRole();
		PageAttribute page = new PageAttribute(acctForm.getCurPage(),
				Constant.PAGE_SIZE);
		page.setRsCount(acctBean.countAcctBill("wht_acctbill_"
				+ DateParser.getNowDateTable(), acctForm));
		acctForm.setExcel(true);
		List list = acctBean.listAcctBill("wht_acctbill_"
				+ DateParser.getNowDateTable(), acctForm, page);
		// 导出excel
		String[][] colTitles = new String[][] { { "序号", "处理号", "订单号", "交易账号",
				"资金子账号", "交易描述", "交易时间", "预交易金额", "实际交易金额", "账号余额", "支出类别",
				"转出或转入账号", "状态" } };
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
			recode[i++] = temp[11];
			recode[i++] = temp[2];
			recode[i++] = temp[1];
			recode[i++] = temp[7];
			recode[i++] = temp[9];
			recode[i++] = FloatArith.div(Integer.parseInt(temp[4]), 1000) + "";
			recode[i++] = FloatArith.div(Integer.parseInt(temp[5]), 1000) + "";
			sum1 += Integer.parseInt(temp[5]);
			recode[i++] = FloatArith.div(Integer.parseInt(temp[10]), 1000) + "";
			recode[i++] = temp[13];
			recode[i++] = temp[12];
			recode[i++] = temp[8];
			body.add(recode);
		}
		String[] sum = new String[size];
		sum[7] = "总计：";
		sum[8] = Formatter.format(sum1, Formatter.D1000F2);
		body.add(sum);
		Map rsMap = null;
		// 转换成excel格式数据
		if (body.size() > 0) {
			rsMap = DataUtil.toNestedStringsListMap(1, body);
		}
		Excel excel = new Excel();
		excel.setCols(colTitles[0].length);
		excel.createCaption("账务查询报表");
		excel.createColCaption(colTitles);
		if (rsMap != null) {
			excel.createBody(rsMap);
		}
		// excel.createRemarks("交易明细");
		String excelFileName = URLEncoder.encode("账务查询报表" + ".xls", "UTF-8");
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
	 * 明细列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward commList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AcctBillForm acctForm = (AcctBillForm) form;
		SysUserForm userSession = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		if (!"0".equals(userSession.getRoleType())
				&& !"1".equals(userSession.getRoleType())) {
			acctForm.setUserId(userSession.getUser_id());
		}
		acctForm.setAcctType("03");
		AcctBillBean acctBean = new AcctBillBean();
		SysRole role = new SysRole();
		PageAttribute page = new PageAttribute(acctForm.getCurPage(),
				Constant.PAGE_SIZE_20);
		page.setRsCount(acctBean.countAcctBill("wlt_acctbill_"
				+ DateParser.getNowDateTable(), acctForm));
		List list = acctBean.listAcctBill("wlt_acctbill_"
				+ DateParser.getNowDateTable(), acctForm, page);
		int curTotal = 0;
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			curTotal += Integer.parseInt(temp[4]);
		}
		request.setAttribute("curTotal", curTotal);
		double totalFee = acctBean.getFeeForTotal("wlt_acctbill_"
				+ DateParser.getNowDateTable(), acctForm);
		int totalCount = acctBean.countAcctBill("wlt_acctbill_"
				+ DateParser.getNowDateTable(), acctForm, page);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("totalFee", totalFee);
		request.setAttribute("orderList", list);
		request.setAttribute("tradeSel", getStingSel(acctBean.listTrade(),
				"交易类型"));
		request.setAttribute("acctbill", acctForm);
		request.setAttribute("areaSel", getStingSel(acctBean.listArea(), "省份"));
		if (null != acctForm.getAreacode()
				&& !"".equals(acctForm.getAreacode())) {
			request.setAttribute("citySel", getStingSel(acctBean
					.listArea(acctForm.getAreacode()), "地市"));
		}
		request.setAttribute("page", page);
		return mapping.findForward("commSuccess");
	}

	// 佣金明细excel报表
	public ActionForward excelExportCommList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AcctBillForm acctForm = (AcctBillForm) form;
		SysUserForm userSession = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		if (!"0".equals(userSession.getRoleType())
				&& !"1".equals(userSession.getRoleType())) {
			acctForm.setUserId(userSession.getUser_id());
		}
		acctForm.setAcctType("03");
		AcctBillBean acctBean = new AcctBillBean();
		SysRole role = new SysRole();
		PageAttribute page = new PageAttribute(acctForm.getCurPage(),
				Constant.PAGE_SIZE_20);
		page.setRsCount(acctBean.countAcctBill("wlt_acctbill_"
				+ DateParser.getNowDateTable(), acctForm));
		acctForm.setExcel(true);
		List list = acctBean.listAcctBill("wlt_acctbill_"
				+ DateParser.getNowDateTable(), acctForm, page);
		// 导出excel
		String[][] colTitles = new String[][] { { "序号", "支付账号", "代理姓名", "账户类型",
				"省份", "地市", "交易类型", "状态", "交易流水号", "交易账号", "交易时间", "交易金额(元)",
				"实际金额", "账户余额(元)", "交易说明" } };
		List body = new ArrayList();
		int size = colTitles[0].length;
		// 只取其中某些字段
		int count = 0;
		int sum1 = 0;
		int sum2 = 0;
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			String[] recode = new String[size];
			int i = 0;

			recode[i++] = ++count + "";
			recode[i++] = temp[2];
			recode[i++] = temp[15];
			recode[i++] = temp[13];
			recode[i++] = temp[14];
			recode[i++] = temp[16];
			recode[i++] = temp[5];
			recode[i++] = temp[7];
			recode[i++] = temp[1];
			recode[i++] = temp[2];
			recode[i++] = temp[3];
			recode[i++] = Formatter.format(temp[4], Formatter.D100F2);
			recode[i++] = Formatter.format(temp[4], Formatter.D100F2);
			;
			recode[i++] = Formatter.format(temp[9], Formatter.D100F2);
			;
			recode[i++] = temp[6];
			body.add(recode);
			sum1 += Integer.parseInt(temp[4]);
			sum2 += Integer.parseInt(temp[4]);
		}
		String[] sum = new String[size];
		sum[10] = "总计：";
		sum[11] = Formatter.format(sum1, Formatter.D100F2);
		sum[12] = Formatter.format(sum2, Formatter.D100F2);
		body.add(sum);
		Map rsMap = null;
		// 转换成excel格式数据
		if (body.size() > 0) {
			rsMap = DataUtil.toNestedStringsListMap(1, body);
		}
		Excel excel = new Excel();
		excel.setCols(colTitles[0].length);
		excel.createCaption("佣金明细报表");
		excel.createColCaption(colTitles);
		if (rsMap != null) {
			excel.createBody(rsMap);
		}
		// excel.createRemarks("交易明细");
		String excelFileName = URLEncoder.encode("佣金明细报表" + ".xls", "UTF-8");
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
	 * 明细列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward commyjList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AcctBillForm acctForm = (AcctBillForm) form;
		SysUserForm userSession = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		if (!"0".equals(userSession.getRoleType())
				&& !"1".equals(userSession.getRoleType())) {
			acctForm.setUserId(userSession.getUser_id());
		}
		acctForm.setAcctType("03");
		AcctBillBean acctBean = new AcctBillBean();
		SysRole role = new SysRole();
		PageAttribute page = new PageAttribute(acctForm.getCurPage(),
				Constant.PAGE_SIZE_20);
		page.setRsCount(acctBean.countyjBill("wlt_acctbill_"
				+ DateParser.getNowDateTable(), acctForm));
		List list = acctBean.listyjBill("wlt_acctbill_"
				+ DateParser.getNowDateTable(), acctForm, page);
		request.setAttribute("orderList", list);
		request.setAttribute("tradeSel", getStingSel(acctBean.listyjTrade(),
				"交易类型"));
		request.setAttribute("acctbill", acctForm);
		request.setAttribute("areaSel", getStingSel(acctBean.listArea(), "省份"));
		if (null != acctForm.getAreacode()
				&& !"".equals(acctForm.getAreacode())) {
			request.setAttribute("citySel", getStingSel(acctBean
					.listArea(acctForm.getAreacode()), "地市"));
		}
		request.setAttribute("page", page);
		return mapping.findForward("commSuccess");
	}

	/**
	 * 押金明细列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward yjList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AcctBillForm acctForm = (AcctBillForm) form;
		SysUserForm userSession = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		if (!"0".equals(userSession.getRoleType())
				&& !"1".equals(userSession.getRoleType())) {
			acctForm.setUserId(userSession.getUser_id());
		}
		acctForm.setAcctType("02");
		AcctBillBean acctBean = new AcctBillBean();
		SysRole role = new SysRole();
		PageAttribute page = new PageAttribute(acctForm.getCurPage(),
				Constant.PAGE_SIZE_20);
		page.setRsCount(acctBean.countAcctBill("wlt_acctbill_"
				+ DateParser.getNowDateTable(), acctForm));
		List list = acctBean.listAcctBill("wlt_acctbill_"
				+ DateParser.getNowDateTable(), acctForm, page);
		int curTotal = 0;
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			curTotal += Integer.parseInt(temp[4]);
		}
		request.setAttribute("curTotal", curTotal);
		double totalFee = acctBean.getFeeForTotal("wlt_acctbill_"
				+ DateParser.getNowDateTable(), acctForm);
		int totalCount = acctBean.countAcctBill("wlt_acctbill_"
				+ DateParser.getNowDateTable(), acctForm, page);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("totalFee", totalFee);
		request.setAttribute("orderList", list);
		request.setAttribute("tradeSel", getStingSel(acctBean.listTrade(),
				"交易类型"));
		request.setAttribute("acctbill", acctForm);
		request.setAttribute("areaSel", getStingSel(acctBean.listArea(), "省份"));
		if (null != acctForm.getAreacode()
				&& !"".equals(acctForm.getAreacode())) {
			request.setAttribute("citySel", getStingSel(acctBean
					.listArea(acctForm.getAreacode()), "地市"));
		}
		request.setAttribute("page", page);
		return mapping.findForward("yjSuccess");
	}

	public ActionForward excelExportYjList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AcctBillForm acctForm = (AcctBillForm) form;
		SysUserForm userSession = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		if (!"0".equals(userSession.getRoleType())
				&& !"1".equals(userSession.getRoleType())) {
			acctForm.setUserId(userSession.getUser_id());
		}
		acctForm.setAcctType("02");
		AcctBillBean acctBean = new AcctBillBean();
		SysRole role = new SysRole();
		PageAttribute page = new PageAttribute(acctForm.getCurPage(),
				Constant.PAGE_SIZE_20);
		page.setRsCount(acctBean.countAcctBill("wlt_acctbill_"
				+ DateParser.getNowDateTable(), acctForm));
		acctForm.setExcel(true);
		List list = acctBean.listAcctBill("wlt_acctbill_"
				+ DateParser.getNowDateTable(), acctForm, page);
		// 导出excel
		String[][] colTitles = new String[][] { { "序号", "支付账号", "代理姓名", "账户类型",
				"省份", "地市", "交易类型", "状态", "交易流水号", "交易账号", "交易时间", "交易金额(元)",
				"实际金额", "账户余额(元)", "交易说明" } };
		List body = new ArrayList();
		int size = colTitles[0].length;
		// 只取其中某些字段
		int count = 0;
		int sum1 = 0;
		int sum2 = 0;
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			String[] recode = new String[size];
			// 取其中某些字段
			int i = 0;
			recode[i++] = ++count + "";
			recode[i++] = temp[2];
			recode[i++] = temp[15];
			recode[i++] = temp[13];
			recode[i++] = temp[14];
			recode[i++] = temp[16];
			recode[i++] = temp[5];
			recode[i++] = temp[7];
			recode[i++] = temp[1];
			recode[i++] = temp[2];
			recode[i++] = temp[3];
			recode[i++] = Formatter.format(temp[4], Formatter.D100F2);
			recode[i++] = Formatter.format(temp[4], Formatter.D100F2);
			recode[i++] = Formatter.format(temp[9], Formatter.D100F2);
			recode[i++] = temp[6];
			sum1 += Integer.parseInt(temp[4]);
			sum2 += Integer.parseInt(temp[4]);
			body.add(recode);
		}
		String[] sum = new String[size];
		sum[10] = "总计：";
		sum[11] = Formatter.format(sum1, Formatter.D100F2);
		sum[12] = Formatter.format(sum2, Formatter.D100F2);
		body.add(sum);
		Map rsMap = null;
		// 转换成excel格式数据
		if (body.size() > 0) {
			rsMap = DataUtil.toNestedStringsListMap(1, body);
		}

		Excel excel = new Excel();
		excel.setCols(colTitles[0].length);
		excel.createCaption("押金明细");
		excel.createColCaption(colTitles);
		if (rsMap != null) {
			excel.createBody(rsMap);
		}
		// excel.createRemarks("交易明细");
		String excelFileName = URLEncoder.encode("押金明细" + ".xls", "UTF-8");
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
	 * 收益明细列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward incomeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AcctBillForm acctForm = (AcctBillForm) form;
		SysUserForm userSession = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		if (!"0".equals(userSession.getRoleType())
				&& !"1".equals(userSession.getRoleType())) {
			acctForm.setUserId(userSession.getUser_id());
		}
		acctForm.setPay_type("0");
		AcctBillBean acctBean = new AcctBillBean();
		SysRole role = new SysRole();
		PageAttribute page = new PageAttribute(acctForm.getCurPage(),
				Constant.PAGE_SIZE_20);
		page.setRsCount(acctBean.countAcctBill("wlt_acctbill_"
				+ DateParser.getNowDateTable(), acctForm));
		List list = acctBean.listAcctBill("wlt_acctbill_"
				+ DateParser.getNowDateTable(), acctForm, page);
		int curTotal = 0;
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			if (temp[12].equals("存入")) {
				temp[17] = temp[15];
			}
			if (temp[12].equals("一级存入")) {
				String account = temp[2];
				if (null != account && !"".equals(account)) {
					temp[17] = acctBean.getUserNameByTradeaccount(account);
				} else {
					temp[17] = "";
				}

			}
			curTotal += Integer.parseInt(temp[4]);
		}
		request.setAttribute("roleType", userSession.getRoleType());
		request.setAttribute("curTotal", curTotal);
		double totalFee = acctBean.getFeeForTotal("wlt_acctbill_"
				+ DateParser.getNowDateTable(), acctForm);
		int totalCount = acctBean.countAcctBill("wlt_acctbill_"
				+ DateParser.getNowDateTable(), acctForm, page);
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("totalFee", totalFee);
		request.setAttribute("orderList", list);
		request.setAttribute("tradeSel", getStingSel(acctBean.listTrade(),
				"交易类型"));
		request.setAttribute("acctbill", acctForm);
		if (userSession.getUser_id().equals("1")) {
			request.setAttribute("areaSel", getStingSel(acctBean.listArea(),
					"省份"));
		} else {
			request.setAttribute("areaSel", getStingSel(acctBean
					.listProvince((null == userSession.getUsersite()) ? "1"
							: userSession.getUsersite()), "省份"));
		}
		if (null != acctForm.getAreacode()
				&& !"".equals(acctForm.getAreacode())) {
			request.setAttribute("citySel", getStingSel(acctBean
					.listArea(acctForm.getAreacode()), "地市"));
		}
		request.setAttribute("page", page);
		return mapping.findForward("incomeSuccess");
	}

	/**
	 * 收益明细列表
	 */
	public ActionForward excelExportIncomeList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AcctBillForm acctForm = (AcctBillForm) form;
		SysUserForm userSession = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		if (!"0".equals(userSession.getRoleType())
				&& !"1".equals(userSession.getRoleType())) {
			acctForm.setUserId(userSession.getUser_id());
		}
		acctForm.setPay_type("0");
		AcctBillBean acctBean = new AcctBillBean();
		SysRole role = new SysRole();
		PageAttribute page = new PageAttribute(acctForm.getCurPage(),
				Constant.PAGE_SIZE_20);
		page.setRsCount(acctBean.countAcctBill("wlt_acctbill_"
				+ DateParser.getNowDateTable(), acctForm));
		acctForm.setExcel(true);
		List list = acctBean.listAcctBill("wlt_acctbill_"
				+ DateParser.getNowDateTable(), acctForm, page);
		// 导出excel
		String[][] colTitles = new String[][] { { "序号", "支付账号", "代理姓名", "账户类型",
				"省份", "地市", "交易类型", "状态", "交易流水号", "交易账号", "交易时间", "交易金额(元)",
				"收益金额", "受理平台" } };
		int size = colTitles[0].length;
		List body = new ArrayList();
		// 只取其中某些字段
		int count = 0;
		int sum1 = 0;
		int sum2 = 0;
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			String[] recode = new String[size];
			// 取其中某些字段
			int i = 0;
			recode[i++] = ++count + "";
			recode[i++] = temp[2];
			recode[i++] = temp[15];
			recode[i++] = temp[13];
			recode[i++] = temp[14];
			recode[i++] = temp[16];
			recode[i++] = temp[5];
			recode[i++] = temp[7];
			recode[i++] = temp[1];
			recode[i++] = temp[2];
			recode[i++] = temp[3];
			recode[i++] = Formatter.format(temp[4], Formatter.D100F2);
			recode[i++] = Formatter.format(temp[4], Formatter.D100F2);
			// recode[i++] = temp[18];
			sum1 += Integer.parseInt(temp[4]);
			sum2 += Integer.parseInt(temp[4]);
			body.add(recode);
		}
		String[] sum = new String[size];
		sum[10] = "总计：";
		sum[11] = Formatter.format(sum1, Formatter.D100F2);
		sum[12] = Formatter.format(sum2, Formatter.D100F2);
		body.add(sum);
		Map rsMap = null;
		// 转换成excel格式数据
		if (body.size() > 0) {
			rsMap = DataUtil.toNestedStringsListMap(1, body);
		}

		Excel excel = new Excel();
		excel.setCols(colTitles[0].length);
		excel.createCaption("收益明细报表");
		excel.createColCaption(colTitles);
		if (rsMap != null) {
			excel.createBody(rsMap);
		}
		// excel.createRemarks("交易明细");
		String excelFileName = URLEncoder.encode("收益明细报表" + ".xls", "UTF-8");
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
	 * 
	 * @param list
	 * @param fix
	 * @return
	 */
	public static String getStingProvince(List list, String fix) {
		StringBuffer sBuffer = new StringBuffer();
		for (Object tmp : list) {
			String[] temp = (String[]) tmp;
			sBuffer.append("|" + temp[1] + "[" + temp[0] + "]");
		}
		return sBuffer.toString();
	}

	/**
	 * 个人银行绑定审批信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward netpayList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AcctBillForm acctForm = (AcctBillForm) form;
		SysUserForm userSession = (SysUserForm) request.getSession()
				.getAttribute("userSession");
		if (!"0".equals(userSession.getRoleType())
				&& !"1".equals(userSession.getRoleType())) {
			acctForm.setUserId(userSession.getUser_id());
		}
		AcctBillBean acctBean = new AcctBillBean();
		PageAttribute page = new PageAttribute(acctForm.getCurPage(),
				Constant.PAGE_SIZE);
		int totalCount = acctBean.countUnionUser("wht_netpay", acctForm, page);
		page.setRsCount(totalCount);
		List list = acctBean.listUnionUser("wht_netpay", acctForm, page);
		request.setAttribute("awardsList", list);
		request.setAttribute("acctbill", acctForm);
		request.setAttribute("page", page);
		return new ActionForward("/bank/netpaybindinglist.jsp");
	}

	/**
	 * 用户 交易利润统计
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward UserPrice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String indate=request.getParameter("indate");
		String enddate=request.getParameter("enddate");
		
		if(indate==null || "".equals(indate)){
			indate=Tools.getNowDate1();
		}
		if(enddate==null || "".equals(enddate)){
			enddate=Tools.getNowDate1();
		}
		request.setAttribute("indate", indate);
		request.setAttribute("enddate", enddate);
		
		indate=indate.replace("-","").trim()+"000000";
		enddate=enddate.replace("-","").trim()+"235959";
		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
		
		String sqlString="SELECT ser.name,cmm.f,cmm.m,cmm.j FROM wht_service ser LEFT JOIN (SELECT service,SUM(ors.fee)/1000 AS f,SUM(ors.tradefee)/1000 AS m,SUM(ors.fee-ors.tradefee)/1000 AS j FROM wht_orderform_"+indate.replace("-","").trim().substring(2,6)+" ors WHERE ors.state=0 AND ors.tradetime>='"+indate+"' AND ors.tradetime<='"+enddate+"' AND ors.userno='"+userSession.getUserno()+"' GROUP BY service ) AS cmm ON ser.code=cmm.service WHERE ser.code IN ('0005','0001','0002','0003','0009','0010','0011')";
		
		DBService db=null;
		try {
			db=new DBService();
			List<String[]> arryList=(List<String[]>)db.getList(sqlString, null);
			float[] strs=null;
			if(arryList!=null && arryList.size()>0){
				strs=new float[]{0,0,0};
				for(String[] s:arryList){
					strs[0]=(strs[0]+((s[1]==null || "".equals(s[1].trim())?0:Float.parseFloat(s[1]))));
					strs[1]=(strs[1]+((s[2]==null || "".equals(s[2].trim())?0:Float.parseFloat(s[2]))));
					strs[2]=(strs[2]+((s[3]==null || "".equals(s[3].trim())?0:Float.parseFloat(s[3]))));
				}
			}
			request.setAttribute("strs",strs);
			request.setAttribute("arryList",arryList);
		}catch (Exception e) {
			Log.error("用户盈利统计异常，，，ex:"+e);
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		return new ActionForward("/business/UserPrice.jsp");
	}
	/**
	 * 充值统计 查询 ,普通代理商，普通代理点
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null 
	 * @throws Exception
	 */
	public ActionForward statisticsOrderList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
		
		String accountName=request.getParameter("accountName");
		String indate=request.getParameter("indate");
		String enddate=request.getParameter("enddate");
		String inmoney=request.getParameter("inmoney");
		String endmoney=request.getParameter("endmoney");
		String subtype=request.getParameter("subtype");
		String OrderStateType=request.getParameter("OrderStateType");
		
		if(indate==null || "".equals(indate))
		{
			indate=Tools.getNowDate1();
		}
		if(enddate==null || "".equals(enddate))
		{
			enddate=Tools.getNowDate1();
		}
		
		request.setAttribute("indate", indate);
		request.setAttribute("enddate", enddate);
		request.setAttribute("inmoney",inmoney );
		request.setAttribute("endmoney",endmoney);
		request.setAttribute("OrderStateType",OrderStateType);

		String str="";
		SysUser sys=new SysUser();
		String userType="";
		if(accountName==null || "".equals(accountName))
		{
			userType=sys.getUserLoginRole(userSession.getUsername());
			request.setAttribute("accountName", userSession.getUsername());
		}else
		{
			userType=sys.getUserLoginRole(accountName);
			request.setAttribute("accountName", accountName);
		}
		String[] strs=new String[22];
		List accountList=null;
		if("2".equals(userType)){
			//代理商
			if("".equals(accountName) || accountName==null)
			{
				str=" WHERE user_pt=(SELECT user_id FROM sys_user WHERE user_login='"+userSession.getUsername()+"') ";
			}
			else
			{
				str=" WHERE user_pt=(SELECT user_id FROM sys_user WHERE user_login='"+accountName+"') ";
			}
			String sql="SELECT users.user_ename,users.user_login, "+
			" SUM(CASE WHEN orders.phone_type = '1' AND phone_pid="+userSession.getUsersite()+" THEN 1 END) ydconb, "+
			" SUM(CASE WHEN orders.phone_type = '1' AND phone_pid="+userSession.getUsersite()+" THEN orders.fee END)/1000 ydsumb, "+
			" SUM(CASE WHEN orders.phone_type = '1' AND phone_pid<>"+userSession.getUsersite()+" THEN 1 END) ydcony, "+
			" SUM(CASE WHEN orders.phone_type = '1' AND phone_pid<>"+userSession.getUsersite()+" THEN orders.fee END)/1000 ydsumy, "+
			" SUM(CASE WHEN orders.phone_type = '2' AND phone_pid="+userSession.getUsersite()+" THEN 1 END) ltconb, "+
			" SUM(CASE WHEN orders.phone_type = '2' AND phone_pid="+userSession.getUsersite()+" THEN orders.fee END)/1000 ltsumb, "+
			" SUM(CASE WHEN orders.phone_type = '2' AND phone_pid<>"+userSession.getUsersite()+" THEN 1 END) ltcony, "+
			" SUM(CASE WHEN orders.phone_type = '2' AND phone_pid<>"+userSession.getUsersite()+" THEN orders.fee END)/1000 ltsumy, "+
			" SUM(CASE WHEN orders.phone_type = '0' AND phone_pid="+userSession.getUsersite()+" THEN 1 END) dxconb, "+
			" SUM(CASE WHEN orders.phone_type = '0' AND phone_pid="+userSession.getUsersite()+" THEN orders.fee END)/1000 dxsumb, "+
			" SUM(CASE WHEN orders.phone_type = '0' AND phone_pid<>"+userSession.getUsersite()+" THEN 1 END) dxcony, "+
			" SUM(CASE WHEN orders.phone_type = '0' AND phone_pid<>"+userSession.getUsersite()+" THEN orders.fee END)/1000 dxsumy, "+
			" SUM(CASE orders.phone_type WHEN '3' THEN 1 END) qbcon, "+
			" SUM(CASE orders.phone_type WHEN '3' THEN orders.fee END)/1000 qbsum, "+
			" SUM(CASE orders.phone_type WHEN '4' THEN 1 END) yxcon, "+
			" SUM(CASE orders.phone_type WHEN '4' THEN orders.fee END)/1000 yxsum, "+
			" SUM(CASE orders.phone_type WHEN '5' THEN 1 END) zfbcon, "+
			" SUM(CASE orders.phone_type WHEN '5' THEN orders.fee END)/1000 zfbsum, "+
			" COUNT(orders.phone_type) zcon, "+
			" SUM(orders.fee)/1000 zmoney,users.user_no "+
			" FROM wht_orderform_"+indate.replace("-","").trim().substring(2,6)+" orders RIGHT JOIN ( "+
			" SELECT user_ename,user_no,user_login FROM sys_user "+str+") users  "+
			" ON   "+
			" orders.userno=users.user_no  "+
			" AND orders.tradetime>='"+indate.replace("-","").trim()+"000000"+"' AND "+
			" orders.tradetime<='"+enddate.replace("-","").trim()+"235959"+"' ";
			if(inmoney!=null && !"".equals(inmoney))
			{
				sql=sql+" AND orders.fee>="+Float.parseFloat(inmoney)*1000;
			}
			if(endmoney!=null && !"".equals(endmoney))
			{
				sql=sql+" AND orders.fee<="+Float.parseFloat(endmoney)*1000;
			}
			if(!"-100".equals(OrderStateType))
			{
				sql=sql+" AND state='"+OrderStateType+"'";
			}
			sql=sql+" GROUP BY users.user_ename,users.user_no,users.user_login"; 
			
			float a2=0,a3=0,a4=0,a5=0,a6=0,a7=0,a8=0,a9=0,a10=0,a11=0,a12=0,a13=0,a14=0,a15=0,a16=0,a17=0,a18=0,a19=0,a20=0,a21=0;
			
			DBService db=null;
			try {
				db=new DBService();
				accountList=db.getList(sql, null);
				if(accountList!=null && !"".equals(accountList))
				{
					for(int i=0;i<accountList.size();i++)
					{
						String[] obj=(String[])accountList.get(i);
						a2=a2+("".equals(obj[2])?0:Float.parseFloat(obj[2]));
						a3=a3+("".equals(obj[3])?0:Float.parseFloat(obj[3]));
						a4=a4+("".equals(obj[4])?0:Float.parseFloat(obj[4]));
						a5=a5+("".equals(obj[5])?0:Float.parseFloat(obj[5]));
						
						a6=a6+("".equals(obj[6])?0:Float.parseFloat(obj[6]));
						a7=a7+("".equals(obj[7])?0:Float.parseFloat(obj[7]));
						a8=a8+("".equals(obj[8])?0:Float.parseFloat(obj[8]));
						a9=a9+("".equals(obj[9])?0:Float.parseFloat(obj[9]));
						
						a10=a10+("".equals(obj[10])?0:Float.parseFloat(obj[10]));
						a11=a11+("".equals(obj[11])?0:Float.parseFloat(obj[11]));
						a12=a12+("".equals(obj[12])?0:Float.parseFloat(obj[12]));
						a13=a13+("".equals(obj[13])?0:Float.parseFloat(obj[13]));
						
						a14=a14+("".equals(obj[14])?0:Float.parseFloat(obj[14]));
						a15=a15+("".equals(obj[15])?0:Float.parseFloat(obj[15]));
						
						a16=a16+("".equals(obj[16])?0:Float.parseFloat(obj[16]));
						a17=a17+("".equals(obj[17])?0:Float.parseFloat(obj[17]));
						a18=a18+("".equals(obj[18])?0:Float.parseFloat(obj[18]));
						a19=a19+("".equals(obj[19])?0:Float.parseFloat(obj[19]));
						a20=a20+("".equals(obj[20])?0:Float.parseFloat(obj[20]));
						a21=a21+("".equals(obj[21])?0:Float.parseFloat(obj[21]));
					}
					strs[0]="";
					strs[1]="统计";
					strs[2]=a2+"";
					strs[3]=a3+"";
					strs[4]=a4+"";
					strs[5]=a5+"";
					strs[6]=a6+"";
					strs[7]=a7+"";
					strs[8]=a8+"";
					strs[9]=a9+"";
					strs[10]=a10+"";
					strs[11]=a11+"";
					strs[12]=a12+"";
					strs[13]=a13+"";
					strs[14]=a14+"";
					strs[15]=a15+"";
					strs[16]=a16+"";
					strs[17]=a17+"";
					strs[18]=a18+"";
					strs[19]=a19+"";
					strs[20]=a20+"";
					strs[21]=a21+"";
				}
			} catch (Exception e) {
				Log.error("充值统计获取列表异常，，，"+e);
			}finally
			{
				if(null!=db)
					db.close();
			}
			request.setAttribute("strsList",strs);
			request.setAttribute("accountList",accountList);
		}else if("3".equals(userType) || "4".equals(userType)){
			//代理点 ，接口代理商
			if("".equals(accountName) || accountName==null)
			{
				str=" WHERE user_login='"+userSession.getUsername()+"' ";
			}
			else
			{
				str=" WHERE user_login='"+accountName+"' ";
			}
			int forIn=0;
			int forEnd=0;
			if(indate.replace("-","").substring(0,6).trim().equals(enddate.replace("-","").substring(0,6).trim()))//查询同一个月中的
			{
				forIn=Integer.parseInt(indate.replace("-","").trim()); 
				forEnd=Integer.parseInt(enddate.replace("-","").trim());
			}
			else
			{
				forIn=Integer.parseInt(indate.replace("-","").trim()); 
				int ss=Tools.getMonthCount(indate.replace("-","").substring(0,6).trim());
				forEnd =Integer.parseInt(indate.replace("-","").trim().substring(0,6)+ss);
			}
			float a2=0,a3=0,a4=0,a5=0,a6=0,a7=0,a8=0,a9=0,a10=0,a11=0,a12=0,a13=0,a14=0,a15=0,a16=0,a17=0,a18=0,a19=0,a20=0,a21=0;
			for(int k=forIn;k<=forEnd;k++)
			{
				String sql="SELECT DATE_FORMAT('"+k+"', '%Y-%m-%d'),'', "+
				" SUM(CASE WHEN orders.phone_type = '1' AND phone_pid="+userSession.getUsersite()+" THEN 1 END) ydconb, "+
				" SUM(CASE WHEN orders.phone_type = '1' AND phone_pid="+userSession.getUsersite()+" THEN orders.fee END)/1000 ydsumb, "+
				" SUM(CASE WHEN orders.phone_type = '1' AND phone_pid<>"+userSession.getUsersite()+" THEN 1 END) ydcony, "+
				" SUM(CASE WHEN orders.phone_type = '1' AND phone_pid<>"+userSession.getUsersite()+" THEN orders.fee END)/1000 ydsumy, "+
				" SUM(CASE WHEN orders.phone_type = '2' AND phone_pid="+userSession.getUsersite()+" THEN 1 END) ltconb, "+
				" SUM(CASE WHEN orders.phone_type = '2' AND phone_pid="+userSession.getUsersite()+" THEN orders.fee END)/1000 ltsumb, "+
				" SUM(CASE WHEN orders.phone_type = '2' AND phone_pid<>"+userSession.getUsersite()+" THEN 1 END) ltcony, "+
				" SUM(CASE WHEN orders.phone_type = '2' AND phone_pid<>"+userSession.getUsersite()+" THEN orders.fee END)/1000 ltsumy, "+
				" SUM(CASE WHEN orders.phone_type = '0' AND phone_pid="+userSession.getUsersite()+" THEN 1 END) dxconb, "+
				" SUM(CASE WHEN orders.phone_type = '0' AND phone_pid="+userSession.getUsersite()+" THEN orders.fee END)/1000 dxsumb, "+
				" SUM(CASE WHEN orders.phone_type = '0' AND phone_pid<>"+userSession.getUsersite()+" THEN 1 END) dxcony, "+
				" SUM(CASE WHEN orders.phone_type = '0' AND phone_pid<>"+userSession.getUsersite()+" THEN orders.fee END)/1000 dxsumy, "+
				" SUM(CASE orders.phone_type WHEN '3' THEN 1 END) qbcon, "+
				" SUM(CASE orders.phone_type WHEN '3' THEN orders.fee END)/1000 qbsum, "+
				" SUM(CASE orders.phone_type WHEN '4' THEN 1 END) yxcon, "+
				" SUM(CASE orders.phone_type WHEN '4' THEN orders.fee END)/1000 yxsum, "+
				" SUM(CASE orders.phone_type WHEN '5' THEN 1 END) zfbcon, "+
				" SUM(CASE orders.phone_type WHEN '5' THEN orders.fee END)/1000 zfbsum, "+
				" COUNT(orders.phone_type) zcon, "+
				" SUM(orders.fee)/1000 zmoney,users.user_no "+
				" FROM wht_orderform_"+indate.replace("-","").trim().substring(2,6)+" orders RIGHT JOIN ( "+
				" SELECT user_ename,user_no,user_login FROM sys_user "+str+") users  "+
				" ON   "+
				" orders.userno=users.user_no  "+
				" AND SUBSTRING(orders.tradetime,1,8)='"+k+"' ";
				if(inmoney!=null && !"".equals(inmoney))
				{
					sql=sql+" AND orders.fee>="+Float.parseFloat(inmoney)*1000;
				}
				if(endmoney!=null && !"".equals(endmoney))
				{
					sql=sql+" AND orders.fee<="+Float.parseFloat(endmoney)*1000;
				}
				if(!"-100".equals(OrderStateType))
				{
					sql=sql+" AND state='"+OrderStateType+"'";
				}
				
				DBService db=null;
				try {
					db=new DBService();
					List arrLists=db.getList(sql, null);
					if(arrLists!=null && !"".equals(arrLists))
					{
						String[] obj=(String[])arrLists.get(0);
						if(accountList==null)
							accountList=new ArrayList();
						accountList.add(obj);
						a2=a2+("".equals(obj[2])?0:Float.parseFloat(obj[2]));
						a3=a3+("".equals(obj[3])?0:Float.parseFloat(obj[3]));
						a4=a4+("".equals(obj[4])?0:Float.parseFloat(obj[4]));
						a5=a5+("".equals(obj[5])?0:Float.parseFloat(obj[5]));
						a6=a6+("".equals(obj[6])?0:Float.parseFloat(obj[6]));
						a7=a7+("".equals(obj[7])?0:Float.parseFloat(obj[7]));
						a8=a8+("".equals(obj[8])?0:Float.parseFloat(obj[8]));
						a9=a9+("".equals(obj[9])?0:Float.parseFloat(obj[9]));
						a10=a10+("".equals(obj[10])?0:Float.parseFloat(obj[10]));
						a11=a11+("".equals(obj[11])?0:Float.parseFloat(obj[11]));
						a12=a12+("".equals(obj[12])?0:Float.parseFloat(obj[12]));
						a13=a13+("".equals(obj[13])?0:Float.parseFloat(obj[13]));
						a14=a14+("".equals(obj[14])?0:Float.parseFloat(obj[14]));
						a15=a15+("".equals(obj[15])?0:Float.parseFloat(obj[15]));
						
						a16=a16+("".equals(obj[16])?0:Float.parseFloat(obj[16]));
						a17=a17+("".equals(obj[17])?0:Float.parseFloat(obj[17]));
						a18=a18+("".equals(obj[18])?0:Float.parseFloat(obj[18]));
						a19=a19+("".equals(obj[19])?0:Float.parseFloat(obj[19]));
						a20=a20+("".equals(obj[20])?0:Float.parseFloat(obj[20]));
						a21=a21+("".equals(obj[21])?0:Float.parseFloat(obj[21]));
					}
				} catch (Exception e) {
					e.printStackTrace();
					Log.error("充值统计获取列表异常，，，"+e);
				}finally
				{
					if(null!=db)
						db.close();
				}
			}
			strs[0]="";
			strs[1]="统计";
			strs[2]=a2+"";
			strs[3]=a3+"";
			strs[4]=a4+"";
			strs[5]=a5+"";
			strs[6]=a6+"";
			strs[7]=a7+"";
			strs[8]=a8+"";
			strs[9]=a9+"";
			strs[10]=a10+"";
			strs[11]=a11+"";
			strs[12]=a12+"";
			strs[13]=a13+"";
			strs[14]=a14+"";
			strs[15]=a15+"";
			strs[16]=a16+"";
			strs[17]=a17+"";
			strs[18]=a18+"";
			strs[19]=a19+"";
			strs[20]=a20+"";
			strs[21]=a21+"";
			request.setAttribute("strsList",strs);
			request.setAttribute("accountList",accountList);
			
		}else{
			request.setAttribute("mess","此功能仅限统计代理商,代理点充值交易!");
			return new ActionForward("/business/statisticsOrder.jsp");
		}
		
		if("1".equals(subtype))
		{
			return new ActionForward("/business/statisticsOrderExcel.jsp");
		}
		return new ActionForward("/business/statisticsOrder.jsp");
	}
	
	/**充值统计 查询,中石化 代理点功能
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward zshCensusList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
		String accountName=request.getParameter("accountName");//下级账号
		String indate=request.getParameter("indate");
		String enddate=request.getParameter("enddate");
		String inmoney=request.getParameter("inmoney");
		String endmoney=request.getParameter("endmoney");
		String subtype=request.getParameter("subtype");
		String OrderStateType=request.getParameter("OrderStateType");
		
		if(indate==null || "".equals(indate)){
			indate=Tools.getNowDate1();
		}
		if(enddate==null || "".equals(enddate)){
			enddate=Tools.getNowDate1();
		}
		request.setAttribute("indate", indate);
		request.setAttribute("enddate", enddate);
		request.setAttribute("inmoney",inmoney );
		request.setAttribute("endmoney",endmoney);
		request.setAttribute("OrderStateType",OrderStateType);
		
		String type="";
		String[] strs=new String[22];
		List accountList=null;
		DBService dd=null;
		try{
			dd=new DBService();
			if(accountName==null || "".equals(accountName) || accountName.equals(userSession.getUsername())){
				request.setAttribute("accountName", userSession.getUsername());
				String ss1=dd.getString("SELECT exp1 FROM sys_user WHERE exp1='"+userSession.getUsername()+"'");
				if(ss1==null || "".equals(ss1)){
					//没有子用户
					request.setAttribute("strsList",strs);
					request.setAttribute("accountList",accountList);
					request.setAttribute("mess","没有子级代理店!");
					return new ActionForward("/business/oilstatisticsOrder.jsp");
				}
				type="A";
			}else{
				request.setAttribute("accountName", accountName);
				String ss2=dd.getString("SELECT exp1 FROM sys_user WHERE user_login='"+accountName+"'");
				if(!userSession.getUsername().equals(ss2)){
					//不是当前的子账户
					request.setAttribute("strsList",strs);
					request.setAttribute("accountList",accountList);
					request.setAttribute("mess","账户："+accountName+",不是你的子账户!");
					return new ActionForward("/business/oilstatisticsOrder.jsp");
				}
				type="B";
			}
		}catch(Exception e){
			Log.error("中石化，代理店，充值统计，系统异常，，，"+e);
			request.setAttribute("strsList",strs);
			request.setAttribute("accountList",accountList);
			request.setAttribute("mess","系统异常!");
			return new ActionForward("/business/oilstatisticsOrder.jsp");
		}finally{
			if(dd!=null){
				dd.close();
			}
		}
		//总代理店
		if("A".equals(type)){
			String sql="SELECT users.user_ename,users.user_login, "+
			" SUM(CASE WHEN orders.phone_type = '1' AND phone_pid="+userSession.getUsersite()+" THEN 1 END) ydconb, "+
			" SUM(CASE WHEN orders.phone_type = '1' AND phone_pid="+userSession.getUsersite()+" THEN orders.fee END)/1000 ydsumb, "+
			" SUM(CASE WHEN orders.phone_type = '1' AND phone_pid<>"+userSession.getUsersite()+" THEN 1 END) ydcony, "+
			" SUM(CASE WHEN orders.phone_type = '1' AND phone_pid<>"+userSession.getUsersite()+" THEN orders.fee END)/1000 ydsumy, "+
			" SUM(CASE WHEN orders.phone_type = '2' AND phone_pid="+userSession.getUsersite()+" THEN 1 END) ltconb, "+
			" SUM(CASE WHEN orders.phone_type = '2' AND phone_pid="+userSession.getUsersite()+" THEN orders.fee END)/1000 ltsumb, "+
			" SUM(CASE WHEN orders.phone_type = '2' AND phone_pid<>"+userSession.getUsersite()+" THEN 1 END) ltcony, "+
			" SUM(CASE WHEN orders.phone_type = '2' AND phone_pid<>"+userSession.getUsersite()+" THEN orders.fee END)/1000 ltsumy, "+
			" SUM(CASE WHEN orders.phone_type = '0' AND phone_pid="+userSession.getUsersite()+" THEN 1 END) dxconb, "+
			" SUM(CASE WHEN orders.phone_type = '0' AND phone_pid="+userSession.getUsersite()+" THEN orders.fee END)/1000 dxsumb, "+
			" SUM(CASE WHEN orders.phone_type = '0' AND phone_pid<>"+userSession.getUsersite()+" THEN 1 END) dxcony, "+
			" SUM(CASE WHEN orders.phone_type = '0' AND phone_pid<>"+userSession.getUsersite()+" THEN orders.fee END)/1000 dxsumy, "+
			" SUM(CASE orders.phone_type WHEN '3' THEN 1 END) qbcon, "+
			" SUM(CASE orders.phone_type WHEN '3' THEN orders.fee END)/1000 qbsum, "+
			" SUM(CASE orders.phone_type WHEN '4' THEN 1 END) yxcon, "+
			" SUM(CASE orders.phone_type WHEN '4' THEN orders.fee END)/1000 yxsum, "+
			" SUM(CASE orders.phone_type WHEN '5' THEN 1 END) zfbcon, "+
			" SUM(CASE orders.phone_type WHEN '5' THEN orders.fee END)/1000 zfbsum, "+
			" COUNT(orders.phone_type) zcon, "+
			" SUM(orders.fee)/1000 zmoney,users.user_no "+
			" FROM wht_orderform_"+indate.replace("-","").trim().substring(2,6)+" orders RIGHT JOIN ( "+
			" SELECT user_ename,user_no,user_login FROM sys_user WHERE exp1='"+userSession.getUsername()+"') users  "+
			" ON   "+
			" orders.userno=users.user_no  "+
			" AND orders.tradetime>='"+indate.replace("-","").trim()+"000000"+"' AND "+
			" orders.tradetime<='"+enddate.replace("-","").trim()+"235959"+"' ";
			if(inmoney!=null && !"".equals(inmoney)){
				sql=sql+" AND orders.fee>="+Float.parseFloat(inmoney)*1000;
			}
			if(endmoney!=null && !"".equals(endmoney)){
				sql=sql+" AND orders.fee<="+Float.parseFloat(endmoney)*1000;
			}
			if(!"-100".equals(OrderStateType)){
				sql=sql+" AND state='"+OrderStateType+"'";
			}
			sql=sql+" GROUP BY users.user_ename,users.user_no,users.user_login"; 
			float a2=0,a3=0,a4=0,a5=0,a6=0,a7=0,a8=0,a9=0,a10=0,a11=0,a12=0,a13=0,a14=0,a15=0,a16=0,a17=0,a18=0,a19=0,a20=0,a21=0;
			DBService db=null;
			try {
				db=new DBService();
				accountList=db.getList(sql, null);
				if(accountList!=null && !"".equals(accountList))
				{
					for(int i=0;i<accountList.size();i++)
					{
						String[] obj=(String[])accountList.get(i);
						a2=a2+("".equals(obj[2])?0:Float.parseFloat(obj[2]));
						a3=a3+("".equals(obj[3])?0:Float.parseFloat(obj[3]));
						a4=a4+("".equals(obj[4])?0:Float.parseFloat(obj[4]));
						a5=a5+("".equals(obj[5])?0:Float.parseFloat(obj[5]));
						
						a6=a6+("".equals(obj[6])?0:Float.parseFloat(obj[6]));
						a7=a7+("".equals(obj[7])?0:Float.parseFloat(obj[7]));
						a8=a8+("".equals(obj[8])?0:Float.parseFloat(obj[8]));
						a9=a9+("".equals(obj[9])?0:Float.parseFloat(obj[9]));
						
						a10=a10+("".equals(obj[10])?0:Float.parseFloat(obj[10]));
						a11=a11+("".equals(obj[11])?0:Float.parseFloat(obj[11]));
						a12=a12+("".equals(obj[12])?0:Float.parseFloat(obj[12]));
						a13=a13+("".equals(obj[13])?0:Float.parseFloat(obj[13]));
						
						a14=a14+("".equals(obj[14])?0:Float.parseFloat(obj[14]));
						a15=a15+("".equals(obj[15])?0:Float.parseFloat(obj[15]));
						
						a16=a16+("".equals(obj[16])?0:Float.parseFloat(obj[16]));
						a17=a17+("".equals(obj[17])?0:Float.parseFloat(obj[17]));
						a18=a18+("".equals(obj[18])?0:Float.parseFloat(obj[18]));
						a19=a19+("".equals(obj[19])?0:Float.parseFloat(obj[19]));
						a20=a20+("".equals(obj[20])?0:Float.parseFloat(obj[20]));
						a21=a21+("".equals(obj[21])?0:Float.parseFloat(obj[21]));
					}
					strs[0]="";
					strs[1]="统计";
					strs[2]=a2+"";
					strs[3]=a3+"";
					strs[4]=a4+"";
					strs[5]=a5+"";
					strs[6]=a6+"";
					strs[7]=a7+"";
					strs[8]=a8+"";
					strs[9]=a9+"";
					strs[10]=a10+"";
					strs[11]=a11+"";
					strs[12]=a12+"";
					strs[13]=a13+"";
					strs[14]=a14+"";
					strs[15]=a15+"";
					strs[16]=a16+"";
					strs[17]=a17+"";
					strs[18]=a18+"";
					strs[19]=a19+"";
					strs[20]=a20+"";
					strs[21]=a21+"";
				}
			} catch (Exception e) {
				Log.error("充值统计获取列表异常，，，"+e);
			}finally{
				if(null!=db)
					db.close();
			}
			request.setAttribute("strsList",strs);
			request.setAttribute("accountList",accountList);
		}else{
			//子代理点
			int forIn=0;
			int forEnd=0;
			if(indate.replace("-","").substring(0,6).trim().equals(enddate.replace("-","").substring(0,6).trim())){//查询同一个月中的
				forIn=Integer.parseInt(indate.replace("-","").trim()); 
				forEnd=Integer.parseInt(enddate.replace("-","").trim());
			}else{
				forIn=Integer.parseInt(indate.replace("-","").trim()); 
				int ss=Tools.getMonthCount(indate.replace("-","").substring(0,6).trim());
				forEnd =Integer.parseInt(indate.replace("-","").trim().substring(0,6)+ss);
			}
			float a2=0,a3=0,a4=0,a5=0,a6=0,a7=0,a8=0,a9=0,a10=0,a11=0,a12=0,a13=0,a14=0,a15=0,a16=0,a17=0,a18=0,a19=0,a20=0,a21=0;
			for(int k=forIn;k<=forEnd;k++){
				String sql="SELECT DATE_FORMAT('"+k+"', '%Y-%m-%d'),'', "+
				" SUM(CASE WHEN orders.phone_type = '1' AND phone_pid="+userSession.getUsersite()+" THEN 1 END) ydconb, "+
				" SUM(CASE WHEN orders.phone_type = '1' AND phone_pid="+userSession.getUsersite()+" THEN orders.fee END)/1000 ydsumb, "+
				" SUM(CASE WHEN orders.phone_type = '1' AND phone_pid<>"+userSession.getUsersite()+" THEN 1 END) ydcony, "+
				" SUM(CASE WHEN orders.phone_type = '1' AND phone_pid<>"+userSession.getUsersite()+" THEN orders.fee END)/1000 ydsumy, "+
				" SUM(CASE WHEN orders.phone_type = '2' AND phone_pid="+userSession.getUsersite()+" THEN 1 END) ltconb, "+
				" SUM(CASE WHEN orders.phone_type = '2' AND phone_pid="+userSession.getUsersite()+" THEN orders.fee END)/1000 ltsumb, "+
				" SUM(CASE WHEN orders.phone_type = '2' AND phone_pid<>"+userSession.getUsersite()+" THEN 1 END) ltcony, "+
				" SUM(CASE WHEN orders.phone_type = '2' AND phone_pid<>"+userSession.getUsersite()+" THEN orders.fee END)/1000 ltsumy, "+
				" SUM(CASE WHEN orders.phone_type = '0' AND phone_pid="+userSession.getUsersite()+" THEN 1 END) dxconb, "+
				" SUM(CASE WHEN orders.phone_type = '0' AND phone_pid="+userSession.getUsersite()+" THEN orders.fee END)/1000 dxsumb, "+
				" SUM(CASE WHEN orders.phone_type = '0' AND phone_pid<>"+userSession.getUsersite()+" THEN 1 END) dxcony, "+
				" SUM(CASE WHEN orders.phone_type = '0' AND phone_pid<>"+userSession.getUsersite()+" THEN orders.fee END)/1000 dxsumy, "+
				" SUM(CASE orders.phone_type WHEN '3' THEN 1 END) qbcon, "+
				" SUM(CASE orders.phone_type WHEN '3' THEN orders.fee END)/1000 qbsum, "+
				" SUM(CASE orders.phone_type WHEN '4' THEN 1 END) yxcon, "+
				" SUM(CASE orders.phone_type WHEN '4' THEN orders.fee END)/1000 yxsum, "+
				" SUM(CASE orders.phone_type WHEN '5' THEN 1 END) zfbcon, "+
				" SUM(CASE orders.phone_type WHEN '5' THEN orders.fee END)/1000 zfbsum, "+
				" COUNT(orders.phone_type) zcon, "+
				" SUM(orders.fee)/1000 zmoney,users.user_no "+
				" FROM wht_orderform_"+indate.replace("-","").trim().substring(2,6)+" orders RIGHT JOIN ( "+
				" SELECT user_ename,user_no,user_login FROM sys_user  WHERE user_login='"+accountName+"') users  "+
				" ON   "+
				" orders.userno=users.user_no  "+
				" AND SUBSTRING(orders.tradetime,1,8)='"+k+"' ";
				if(inmoney!=null && !"".equals(inmoney)){
					sql=sql+" AND orders.fee>="+Float.parseFloat(inmoney)*1000;
				}
				if(endmoney!=null && !"".equals(endmoney)){
					sql=sql+" AND orders.fee<="+Float.parseFloat(endmoney)*1000;
				}
				if(!"-100".equals(OrderStateType)){
					sql=sql+" AND state='"+OrderStateType+"'";
				}
				
				DBService db=null;
				try {
					db=new DBService();
					List arrLists=db.getList(sql, null);
					if(arrLists!=null && !"".equals(arrLists))
					{
						String[] obj=(String[])arrLists.get(0);
						if(accountList==null)
							accountList=new ArrayList();
						accountList.add(obj);
						a2=a2+("".equals(obj[2])?0:Float.parseFloat(obj[2]));
						a3=a3+("".equals(obj[3])?0:Float.parseFloat(obj[3]));
						a4=a4+("".equals(obj[4])?0:Float.parseFloat(obj[4]));
						a5=a5+("".equals(obj[5])?0:Float.parseFloat(obj[5]));
						a6=a6+("".equals(obj[6])?0:Float.parseFloat(obj[6]));
						a7=a7+("".equals(obj[7])?0:Float.parseFloat(obj[7]));
						a8=a8+("".equals(obj[8])?0:Float.parseFloat(obj[8]));
						a9=a9+("".equals(obj[9])?0:Float.parseFloat(obj[9]));
						a10=a10+("".equals(obj[10])?0:Float.parseFloat(obj[10]));
						a11=a11+("".equals(obj[11])?0:Float.parseFloat(obj[11]));
						a12=a12+("".equals(obj[12])?0:Float.parseFloat(obj[12]));
						a13=a13+("".equals(obj[13])?0:Float.parseFloat(obj[13]));
						a14=a14+("".equals(obj[14])?0:Float.parseFloat(obj[14]));
						a15=a15+("".equals(obj[15])?0:Float.parseFloat(obj[15]));
						
						a16=a16+("".equals(obj[16])?0:Float.parseFloat(obj[16]));
						a17=a17+("".equals(obj[17])?0:Float.parseFloat(obj[17]));
						a18=a18+("".equals(obj[18])?0:Float.parseFloat(obj[18]));
						a19=a19+("".equals(obj[19])?0:Float.parseFloat(obj[19]));
						a20=a20+("".equals(obj[20])?0:Float.parseFloat(obj[20]));
						a21=a21+("".equals(obj[21])?0:Float.parseFloat(obj[21]));
					}
				} catch (Exception e) {
					Log.error("充值统计获取列表异常，，，"+e);
				}finally
				{
					if(null!=db)
						db.close();
				}
			}
			strs[0]="";
			strs[1]="统计";
			strs[2]=a2+"";
			strs[3]=a3+"";
			strs[4]=a4+"";
			strs[5]=a5+"";
			strs[6]=a6+"";
			strs[7]=a7+"";
			strs[8]=a8+"";
			strs[9]=a9+"";
			strs[10]=a10+"";
			strs[11]=a11+"";
			strs[12]=a12+"";
			strs[13]=a13+"";
			strs[14]=a14+"";
			strs[15]=a15+"";
			strs[16]=a16+"";
			strs[17]=a17+"";
			strs[18]=a18+"";
			strs[19]=a19+"";
			strs[20]=a20+"";
			strs[21]=a21+"";
			request.setAttribute("strsList",strs);
			request.setAttribute("accountList",accountList);
			
		}
		if("1".equals(subtype)){
			return new ActionForward("/business/oilstatisticsOrderExcel.jsp");
		}
		return new ActionForward("/business/oilstatisticsOrder.jsp");
	}
	
	/**一级代理统计 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward OneOrderList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
		
		String accountName=request.getParameter("accountName");
		String indate=request.getParameter("indate");
		String enddate=request.getParameter("enddate");
		String inmoney=request.getParameter("inmoney");
		String endmoney=request.getParameter("endmoney");
		String subtype=request.getParameter("subtype");
		String OrderStateType=request.getParameter("OrderStateType");
		
		if(indate==null || "".equals(indate))
		{
			indate=Tools.getNowDate1();
		}
		if(enddate==null || "".equals(enddate))
		{
			enddate=Tools.getNowDate1();
		}
		
		request.setAttribute("indate", indate);
		request.setAttribute("enddate", enddate);
		request.setAttribute("inmoney",inmoney );
		request.setAttribute("endmoney",endmoney);
		request.setAttribute("OrderStateType",OrderStateType);

		String str="";
		SysUser sys=new SysUser();
		String userType="";
		if(accountName==null || "".equals(accountName))
		{
			accountName=userSession.getUsername();
			userType=sys.getUserLoginRole(userSession.getUsername());
			request.setAttribute("accountName", userSession.getUsername());
		}else
		{
			userType=sys.getUserLoginRole(accountName);
			request.setAttribute("accountName", accountName);
		}
		String[] strs=new String[22];
		List accountList=null;
		if("2".equals(userType)){//代理商
			str="SELECT a.user_id AS userid,a.user_ename AS userName,a.user_login AS userNo FROM sys_user a WHERE a.user_login='"+accountName+"'";
		}else if("1".equals(userType)){ //一级代理
			str="SELECT a.user_id AS userid,a.user_ename AS userName,a.user_login AS userNo FROM sys_user a WHERE a.user_pt=(SELECT c.user_id AS uid FROM sys_user c WHERE c.user_login='"+accountName+"')";
		}else{
			request.setAttribute("mess","此功能仅限一级代理,代理商交易统计");
			return new ActionForward("/AccountInfo/payList.jsp");
		}
		
		String sql="SELECT users.userNo,users.userName,"+
		" SUM(CASE WHEN orders.phone_type = '1' AND phone_pid="+userSession.getUsersite()+" THEN 1 END) ydconb, "+
		" SUM(CASE WHEN orders.phone_type = '1' AND phone_pid="+userSession.getUsersite()+" THEN orders.fee END)/1000 ydsumb, "+
		" SUM(CASE WHEN orders.phone_type = '1' AND phone_pid<>"+userSession.getUsersite()+" THEN 1 END) ydcony, "+
		" SUM(CASE WHEN orders.phone_type = '1' AND phone_pid<>"+userSession.getUsersite()+" THEN orders.fee END)/1000 ydsumy, "+
		" SUM(CASE WHEN orders.phone_type = '2' AND phone_pid="+userSession.getUsersite()+" THEN 1 END) ltconb, "+
		" SUM(CASE WHEN orders.phone_type = '2' AND phone_pid="+userSession.getUsersite()+" THEN orders.fee END)/1000 ltsumb, "+
		" SUM(CASE WHEN orders.phone_type = '2' AND phone_pid<>"+userSession.getUsersite()+" THEN 1 END) ltcony, "+
		" SUM(CASE WHEN orders.phone_type = '2' AND phone_pid<>"+userSession.getUsersite()+" THEN orders.fee END)/1000 ltsumy, "+
		" SUM(CASE WHEN orders.phone_type = '0' AND phone_pid="+userSession.getUsersite()+" THEN 1 END) dxconb, "+
		" SUM(CASE WHEN orders.phone_type = '0' AND phone_pid="+userSession.getUsersite()+" THEN orders.fee END)/1000 dxsumb, "+
		" SUM(CASE WHEN orders.phone_type = '0' AND phone_pid<>"+userSession.getUsersite()+" THEN 1 END) dxcony, "+
		" SUM(CASE WHEN orders.phone_type = '0' AND phone_pid<>"+userSession.getUsersite()+" THEN orders.fee END)/1000 dxsumy, "+
		" SUM(CASE orders.phone_type WHEN '3' THEN 1 END) qbcon, "+
		" SUM(CASE orders.phone_type WHEN '3' THEN orders.fee END)/1000 qbsum, "+
		" SUM(CASE orders.phone_type WHEN '4' THEN 1 END) yxcon, "+
		" SUM(CASE orders.phone_type WHEN '4' THEN orders.fee END)/1000 yxsum, "+
		" SUM(CASE orders.phone_type WHEN '5' THEN 1 END) zfbcon, "+
		" SUM(CASE orders.phone_type WHEN '5' THEN orders.fee END)/1000 zfbsum, "+
		" COUNT(orders.phone_type) zcon, "+
		" SUM(orders.fee)/1000 zmoney  "+
		" FROM wht_orderform_"+indate.replace("-","").trim().substring(2,6)+" orders RIGHT JOIN "+
		" (SELECT u.user_no,n.userName,n.userNo FROM sys_user u RIGHT JOIN "+
		" 	("+str+") n ON u.user_pt=n.userid "+
		" ) AS users "+
		" ON orders.userno=users.user_no "+
		" AND orders.tradetime>='"+indate.replace("-","").trim()+"000000"+"' AND "+
		" orders.tradetime<='"+enddate.replace("-","").trim()+"235959"+"' ";
		if(inmoney!=null && !"".equals(inmoney))
		{
			sql=sql+" AND orders.fee>="+Float.parseFloat(inmoney)*1000;
		}
		if(endmoney!=null && !"".equals(endmoney))
		{
			sql=sql+" AND orders.fee<="+Float.parseFloat(endmoney)*1000;
		}
		if(!"-100".equals(OrderStateType))
		{
			sql=sql+" AND state='"+OrderStateType+"'";
		}
		sql=sql+" GROUP BY users.userNo,users.userName"; 
		
		float a2=0,a3=0,a4=0,a5=0,a6=0,a7=0,a8=0,a9=0,a10=0,a11=0,a12=0,a13=0,a14=0,a15=0,a16=0,a17=0,a18=0,a19=0,a20=0,a21=0;
		
		DBService db=null;
		try {
			db=new DBService();
			accountList=db.getList(sql, null);
			if(accountList!=null && !"".equals(accountList))
			{
				for(int i=0;i<accountList.size();i++)
				{
					String[] obj=(String[])accountList.get(i);
					a2=a2+("".equals(obj[2])?0:Float.parseFloat(obj[2]));
					a3=a3+("".equals(obj[3])?0:Float.parseFloat(obj[3]));
					a4=a4+("".equals(obj[4])?0:Float.parseFloat(obj[4]));
					a5=a5+("".equals(obj[5])?0:Float.parseFloat(obj[5]));
					
					a6=a6+("".equals(obj[6])?0:Float.parseFloat(obj[6]));
					a7=a7+("".equals(obj[7])?0:Float.parseFloat(obj[7]));
					a8=a8+("".equals(obj[8])?0:Float.parseFloat(obj[8]));
					a9=a9+("".equals(obj[9])?0:Float.parseFloat(obj[9]));
					
					a10=a10+("".equals(obj[10])?0:Float.parseFloat(obj[10]));
					a11=a11+("".equals(obj[11])?0:Float.parseFloat(obj[11]));
					a12=a12+("".equals(obj[12])?0:Float.parseFloat(obj[12]));
					a13=a13+("".equals(obj[13])?0:Float.parseFloat(obj[13]));
					
					a14=a14+("".equals(obj[14])?0:Float.parseFloat(obj[14]));
					a15=a15+("".equals(obj[15])?0:Float.parseFloat(obj[15]));
					
					a16=a16+("".equals(obj[16])?0:Float.parseFloat(obj[16]));
					a17=a17+("".equals(obj[17])?0:Float.parseFloat(obj[17]));
					a18=a18+("".equals(obj[18])?0:Float.parseFloat(obj[18]));
					a19=a19+("".equals(obj[19])?0:Float.parseFloat(obj[19]));
					a20=a20+("".equals(obj[20])?0:Float.parseFloat(obj[20]));
					a21=a21+("".equals(obj[21])?0:Float.parseFloat(obj[21]));
				}
				strs[0]="";
				strs[1]="统计";
				strs[2]=a2+"";
				strs[3]=a3+"";
				strs[4]=a4+"";
				strs[5]=a5+"";
				strs[6]=a6+"";
				strs[7]=a7+"";
				strs[8]=a8+"";
				strs[9]=a9+"";
				strs[10]=a10+"";
				strs[11]=a11+"";
				strs[12]=a12+"";
				strs[13]=a13+"";
				strs[14]=a14+"";
				strs[15]=a15+"";
				strs[16]=a16+"";
				strs[17]=a17+"";
				strs[18]=a18+"";
				strs[19]=a19+"";
				strs[20]=a20+"";
				strs[21]=a21+"";
			}
		} catch (Exception e) {
			Log.error("一级代理统计 获取列表异常，，，"+e);
		}finally
		{
			if(null!=db)
				db.close();
		}
		request.setAttribute("strsList",strs);
		request.setAttribute("accountList",accountList);
		
		if("1".equals(subtype))
		{
			return new ActionForward("/AccountInfo/payListExcel.jsp");
		}
		return new ActionForward("/AccountInfo/payList.jsp");
	}
}