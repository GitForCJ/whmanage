package com.ejet.phone.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wlt.webm.business.bean.AcctBillBean;
import com.wlt.webm.business.bean.ReportBean;
import com.wlt.webm.business.form.AcctBillForm;
import com.wlt.webm.business.form.CityReportForm;
import com.wlt.webm.rights.bean.SysRole;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.scputil.DateParser;
import com.wlt.webm.util.PageAttribute;

// 统计业务

public class PhoneCountDao {
	
	
	/**
	 * 账户明细列表
	 */
	public static int countAcctBill( AcctBillForm acctForm, SysUserForm userSession, 
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		AcctBillForm acctForm = (AcctBillForm) form;
		//SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
		if(!"0".equals(userSession.getRoleType()) && !"1".equals(userSession.getRoleType())){
			acctForm.setUserId(userSession.getUser_id());
		}
		AcctBillBean acctBean= new AcctBillBean();
		SysRole role = new SysRole();
		PageAttribute page = new PageAttribute(acctForm.getCurPage(), Integer.parseInt((String)request.getAttribute("itemnum")));
		page.setRsCount(acctBean.countAcctBill("wlt_acctbill_"+DateParser.getNowDateTable(),acctForm));
		List list = acctBean.listAcctBill("wlt_acctbill_"+DateParser.getNowDateTable(),acctForm,page);
		int curTotal = 0;
		for(Object tmp : list){
			String[] temp = (String[])tmp;
			curTotal += Integer.parseInt(temp[4]);
		}
		request.setAttribute("curTotal", curTotal);
		int totalFee = acctBean.getTotalFee(userSession.getUser_id(),null,null);
		request.setAttribute("totalFee", totalFee);
		request.setAttribute("orderList",list); 
		request.setAttribute("acctbill",acctForm); 
		request.setAttribute("page",page); 
		return 0;
	}
	
	/**
	 * 佣金明细
	 */
	public static int commList(AcctBillForm acctForm , SysUserForm userSession,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		AcctBillForm acctForm = (AcctBillForm) form;
//		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
		if(!"0".equals(userSession.getRoleType()) && !"1".equals(userSession.getRoleType())){
			acctForm.setUserId(userSession.getUser_id());
		}
		acctForm.setAcctType("03");
		AcctBillBean acctBean= new AcctBillBean();
		SysRole role = new SysRole();
		PageAttribute page = new PageAttribute(acctForm.getCurPage(),Constant.PAGE_SIZE_20);
		page.setRsCount(acctBean.countAcctBill("wlt_acctbill_"+DateParser.getNowDateTable(),acctForm));
		List list = acctBean.listAcctBill("wlt_acctbill_"+DateParser.getNowDateTable(),acctForm,page);
		int curTotal = 0;
		for(Object tmp : list){
			String[] temp = (String[])tmp;
			curTotal += Integer.parseInt(temp[4]);
		}
		request.setAttribute("curTotal", curTotal);
		int totalFee = acctBean.getFeeForTotal("wlt_acctbill_"+DateParser.getNowDateTable(),acctForm);
		request.setAttribute("totalFee", totalFee);
		request.setAttribute("orderList",list); 
//		request.setAttribute("tradeSel",getStingSel(acctBean.listTrade(),"交易类型")); 
		request.setAttribute("acctbill",acctForm); 
//		request.setAttribute("areaSel",getStingSel(acctBean.listArea(),"省份"));
		if(null != acctForm.getAreacode() && !"".equals(acctForm.getAreacode())){
//			request.setAttribute("citySel",getStingSel(acctBean.listArea(acctForm.getAreacode()),"地市"));
		}
		request.setAttribute("page",page); 
		return 0;
	}
	
	//押金明细
	public static int commyjList(AcctBillForm acctForm, SysUserForm userSession, 
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		AcctBillForm acctForm = (AcctBillForm) form;
//		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
		if(!"0".equals(userSession.getRoleType()) && !"1".equals(userSession.getRoleType())){
			acctForm.setUserId(userSession.getUser_id());
		}
		acctForm.setAcctType("02");
		AcctBillBean acctBean= new AcctBillBean();
		SysRole role = new SysRole();
		PageAttribute page = new PageAttribute(acctForm.getCurPage(),Constant.PAGE_SIZE_20);
		page.setRsCount(acctBean.countyjBill("wlt_acctbill_"+DateParser.getNowDateTable(),acctForm));
		List list = acctBean.listyjBill("wlt_acctbill_"+DateParser.getNowDateTable(),acctForm,page);
		request.setAttribute("orderList",list); 
//		request.setAttribute("tradeSel",getStingSel(acctBean.listyjTrade(),"交易类型")); 
		request.setAttribute("acctbill",acctForm); 
//		request.setAttribute("areaSel",getStingSel(acctBean.listArea(),"省份"));
		if(null != acctForm.getAreacode() && !"".equals(acctForm.getAreacode())){
//			request.setAttribute("citySel",getStingSel(acctBean.listArea(acctForm.getAreacode()),"地市"));
		}
		request.setAttribute("page",page); 
//		return mapping.findForward("commSuccess");
		return 0;
	}
	
	//收益统计
	public static int incomeList( AcctBillForm acctForm , SysUserForm userSession,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		AcctBillForm acctForm = (AcctBillForm) form;
//		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
		if(!"0".equals(userSession.getRoleType()) && !"1".equals(userSession.getRoleType())){
			acctForm.setUserId(userSession.getUser_id());
		}
		acctForm.setPay_type("0");
		AcctBillBean acctBean= new AcctBillBean();
		SysRole role = new SysRole();
		PageAttribute page = new PageAttribute(acctForm.getCurPage(),Constant.PAGE_SIZE_20);
		page.setRsCount(acctBean.countAcctBill("wlt_acctbill_"+DateParser.getNowDateTable(),acctForm));
		List list = acctBean.listAcctBill("wlt_acctbill_"+DateParser.getNowDateTable(),acctForm,page);
		int curTotal = 0;
		for(Object tmp : list){
			String[] temp = (String[])tmp;
			if(temp[12].equals("存入")){
				temp[17] = temp[15];
			}
			if(temp[12].equals("一级存入")){
				String account = temp[2];
				if(null != account && !"".equals(account)){
					temp[17] = acctBean.getUserNameByTradeaccount(account);
				}else{
					temp[17] = "";
				}
					
			}
			curTotal += Integer.parseInt(temp[4]);
		}
		request.setAttribute("roleType", userSession.getRoleType());
		request.setAttribute("curTotal", curTotal);
		int totalFee = acctBean.getFeeForTotal("wlt_acctbill_"+DateParser.getNowDateTable(),acctForm);
		request.setAttribute("totalFee", totalFee);
		request.setAttribute("orderList",list); 
//		request.setAttribute("tradeSel",getStingSel(acctBean.listTrade(),"交易类型")); 
		request.setAttribute("acctbill",acctForm); 
		if(userSession.getUser_id().equals("1")){
//			request.setAttribute("areaSel",getStingSel(acctBean.listArea(),"省份")); 
			}else{
//				request.setAttribute("areaSel",getStingSel(acctBean.listProvince((null==userSession.getUsersite())?"1":userSession.getUsersite()),"省份")); 
			}
		if(null != acctForm.getAreacode() && !"".equals(acctForm.getAreacode())){
//			request.setAttribute("citySel",getStingSel(acctBean.listArea(acctForm.getAreacode()),"地市"));
		}
		request.setAttribute("page",page); 
//		return mapping.findForward("incomeSuccess");
		return 0;
		
	}
	
	public static int query(ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		ReportBean bean = new ReportBean();
		CityReportForm f = (CityReportForm) form;
//		CityReportForm f= new CityReportForm();
//		f.setAreacode(request.getParameter("areacode"));
//		f.setUcity(request.getParameter("user_site_city"));
//		f.setStartDate(request.getParameter("startDate"));
//		f.setEndDate(request.getParameter("endDate"));
		List<String[]>array = new ArrayList<String[]>();
		List<String[]> result= bean.queryAgentTradeLog(f);
		String[]arr = null;
		if(result!=null&&!result.isEmpty())
		{
			for(String[]temp :result)
			{
				arr = new String[temp.length+5];
				System.arraycopy(temp, 0, arr, 0, temp.length); 
				/*arr[temp.length+2]=countRowSpan(result,temp[2],2,arr)+"";// 业务类型 
				 arr[temp.length+3]=countRowSpan(result,temp[3],3,arr)+"";//交易状态
				 arr[temp.length+3]=countRowSpan(result,temp[3],4,arr)+"";//交易终端
				 arr[temp.length+1]=countCityRowSpan(result,temp[1])+"";// 地市 跨行数目
				 */
				arr[temp.length]=countAgentRowSpan(result,temp[0])+"";// 省份跨行数目
				

				array.add(arr);
			}
		}

		request.setAttribute("cityCount",array); 
		return 0;
	}
	/***
	 * 
	 * @param result
	 * @param key
	 * @return
	 */
	private static int countAgentRowSpan(List<String[]> result ,String key)
	{
		int count=0;
		if(result!=null && !result.isEmpty())
		{
			for(String[] arr : result)
			{
				if(arr[0].equals(key))
				{
					count++; 
				}
			}
		}
		return ++count;
	}
	/***
	 * 
	 * @param result
	 * @param key
	 * @return
	 */
	private static int countAreaRowSpan(List<String[]> result ,String key)
	{
		int count=0;
		HashMap<String, String>counter = new HashMap<String, String>();
		if(result!=null && !result.isEmpty())
		{
			for(String[] arr : result)
			{
				if(arr[0].equals(key))
				{
					count++; 
					counter.put(arr[1], arr[1]);
				}
			}
		}
		return count+counter.size();
	}
	/***
	 * 
	 * @param result
	 * @param key
	 * @param position
	 * @return
	 */
	private static int countCityRowSpan(List<String[]> result ,String key)
	{
		int count=0;
		if(result!=null && !result.isEmpty())
		{
			for(String[] arr : result)
			{
				if(arr[1].equals(key))
				{
					count++; 
				}
			}
			if(count<=result.size())// 合计功能
			{
				count++;
			}
		}
		
		return count;
	}

}
