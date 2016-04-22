package com.wlt.webm.business.action;

import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;  
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.wlt.webm.business.bean.AcctBillBean;
 
import com.wlt.webm.business.bean.ReportBean;
import com.wlt.webm.business.form.CityReportForm;

public class TradeLogAgentAction extends DispatchAction{

	private  String getStingSel(List list,String fix){
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("请选择"+fix+"[]");
		for(Object tmp : list){
			String[] temp = (String[])tmp;
			sBuffer.append("|"+temp[1]+"["+temp[0]+"]");
		}
		return sBuffer.toString();
	}

	/***
	 *  进入 enter 主页面 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward enter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		AcctBillBean acctBean= new AcctBillBean();
		request.setAttribute("areaSel",getStingSel(acctBean.listArea(),"省份")); 
		return mapping.findForward("enter");
	}


	/***
	 *  query 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		ReportBean bean = new ReportBean();
		CityReportForm f= new CityReportForm();
		f.setAreacode(request.getParameter("areacode"));
		f.setUcity(request.getParameter("user_site_city"));
		f.setStartDate(request.getParameter("startDate"));
		f.setEndDate(request.getParameter("endDate"));
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
		return mapping.findForward("query");
	}

	/***
	 * 
	 * @param result
	 * @param key
	 * @return
	 */
	private int countAgentRowSpan(List<String[]> result ,String key)
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
	 *  query 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception { 

		ReportBean bean = new ReportBean();
		CityReportForm f= new CityReportForm();
		f.setAreacode(request.getParameter("areacode"));
		f.setUcity(request.getParameter("user_site_city"));
		f.setStartDate(request.getParameter("startDate"));
		f.setEndDate(request.getParameter("endDate"));
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
				 */
				arr[temp.length]=countAgentRowSpan(result,temp[0])+"";// 省份跨行数目 
				array.add(arr);
			}
		}

		request.setAttribute("cityCount",array); 
		return mapping.findForward("export");
	}
}
