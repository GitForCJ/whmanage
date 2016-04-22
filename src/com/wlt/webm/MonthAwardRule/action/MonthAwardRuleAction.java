package com.wlt.webm.MonthAwardRule.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.MonthAwardRule.MonthAwardRuleServices;
import com.wlt.webm.MonthAwardRule.bean.MonthAwardRule;
import com.wlt.webm.db.DBService;

/**
 * @author adminA
 */
public class MonthAwardRuleAction extends DispatchAction 
{
	/**
	 * 加载显示列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return String
	 * @throws Exception
	 */
	public ActionForward showAwardsAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MonthAwardRuleServices acc=new MonthAwardRuleServices();
		request.setAttribute("arryList",acc.showAwards());
		return mapping.findForward("success");
	}
	
	/**
	 * 加载 下拉框
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return String 
	 * @throws Exception
	 */
	public ActionForward addMonthAwardRule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MonthAwardRuleServices acc=new MonthAwardRuleServices();
		request.setAttribute("arryList",acc.addMonthAwardRule());
		return mapping.findForward("addsuccess");
	}
	
	/**
	 *删除
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delMonthAwardRule(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String id=request.getParameter("id");
		if(id!=null && !"".equals(id))
		{
			MonthAwardRuleServices acc=new MonthAwardRuleServices();
			if(acc.delMonthAwardRule(Integer.parseInt(id))>0)
			{
				request.setAttribute("mess","操作成功!");
				return showAwardsAction( mapping,  form, request,  response);
			}
			else
			{
				request.setAttribute("mess","操作失败!");
				return showAwardsAction( mapping,  form, request,  response);
			}
		}
		request.setAttribute("mess","操作失败!");
		return showAwardsAction( mapping,  form, request,  response);
	}
	
	/**
	 * 添加  
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return boolean 
	 * @throws Exception
	 */
	public ActionForward saveMonthAwardRule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MonthAwardRule bean=(MonthAwardRule)form;
		MonthAwardRuleServices acc=new MonthAwardRuleServices();
		if(acc.saveMonthAwardRule(bean)>0)
		{
			request.setAttribute("mess","操作成功!");
			return showAwardsAction( mapping,  form, request,  response);
		}
		else
		{
			request.setAttribute("mess","操作失败!");
		}
		return mapping.findForward("addsuccess");
	}
	
	/**
	 * 验证添加佣金 区间段是否合适
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null;
	 * @throws Exception
	 */
	public ActionForward getVerifyMoney(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		response.setCharacterEncoding("utf-8");
		String userno=request.getParameter("userno");
		String min=request.getParameter("min");
		String max=request.getParameter("max");
		String faceValue=request.getParameter("faceValue");
		
		PrintWriter out = response.getWriter();
		
		if(userno==null || "".equals(userno) || min==null || "".equals(min) || max==null || "".equals(max) || faceValue==null || "".equals(faceValue))
		{
			out.print(false);
			return null;
		}
		int minmoney=Integer.parseInt(min)*1000;
		int maxmoney=Integer.parseInt(max)*1000;
		
		DBService db = null;
        try {
        	db=new DBService();
//        	 String sql="SELECT SUM(con) cc FROM ( "+
//				" SELECT COUNT(*) con FROM wlt_MonthAwardRule WHERE userid='"+userno.split(";")[1]+"' "+ 
//				"  AND faceValue='"+faceValue+"' AND minmoney>="+minmoney+" AND minmoney<="+maxmoney+"  UNION  "+
//				" SELECT COUNT(*) con FROM wlt_MonthAwardRule WHERE userid='"+userno.split(";")[1]+"' "+ 
//				"  AND faceValue='"+faceValue+"' AND minmoney<="+minmoney+" AND maxmoney>="+maxmoney+"  UNION  "+
//				" SELECT COUNT(*) con FROM wlt_MonthAwardRule WHERE userid='"+userno.split(";")[1]+"' "+ 
//				"  AND faceValue='"+faceValue+"' AND maxmoney>="+minmoney+" AND maxmoney<="+maxmoney+" UNION  "+
//				" SELECT COUNT(*) con FROM wlt_MonthAwardRule WHERE userid='"+userno.split(";")[1]+"' "+ 
//				"  AND faceValue='"+faceValue+"' AND maxmoney>="+minmoney+" AND maxmoney<="+maxmoney+") abc ";
        	 String sql="SELECT COUNT(awardId) con FROM wlt_MonthAwardRule WHERE userid='"+userno.split(";")[1]+"'  AND faceValue='"+faceValue+"' AND ( ( "+minmoney+" BETWEEN minmoney AND maxmoney ) OR ("+maxmoney+" BETWEEN minmoney AND maxmoney ) OR (minmoney>"+minmoney+" AND minmoney<"+minmoney+") OR (maxmoney<"+maxmoney+" AND maxmoney>"+maxmoney+")  OR (minmoney>"+minmoney+" AND maxmoney<"+maxmoney+"))";
        	 if(db.getInt(sql)==0)
        	 {
        		out.print(true);
     			return null;
        	 }
        	 else
        	 {
        		out.print(false);
      			return null;
        	 }
        } 
        catch (Exception ex) 
        {
        	out.print(false);
  			return null;
        } 
        finally 
        {
        	if(db!=null)
        		db.close();
        }
	}
}
//
