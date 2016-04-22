package com.wlt.webm.branchManage.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.wlt.webm.branchManage.BranchManageServices;
import com.wlt.webm.rights.action.SysUserAction;

/**
 * @author adminA
 *
 */
public class BranchManageAction extends DispatchAction {
	
	/** 
	 * 获取上级用户名称
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward getFatherUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
		{
		 response.setContentType("application/json;charset=gbk");
		 response.setCharacterEncoding("gbk");
		PrintWriter out=response.getWriter();
		String userno=request.getParameter("userno");
		if(userno!=null && !"".equals(userno))
		{
			BranchManageServices acc=new BranchManageServices();
			
			JSONObject json=new JSONObject();
			json.put("username", acc.getFatherUser(userno));
			out.print(json.toString());
		}
		out.flush();
		out.close();
		return null;
	}
	
	
	/**
	 * 获取代理商下拉框列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null 
	 * @throws Exception
	 */
	public ActionForward getAgentInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
			{
			BranchManageServices acc=new BranchManageServices();
			request.setAttribute("roleSel", SysUserAction.getStingSel(acc.getAgentInfo(),""));
			return mapping.findForward("success");
		}
	
	/**
	 * 获取代理商下列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null 
	 * @throws Exception
	 */
	public ActionForward showAgentInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
			{
		String inputName=request.getParameter("inputName");
		String selectName=request.getParameter("selectName");
		String sr_type=request.getParameter("sr_type");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		if(inputName==null && selectName==null&&sr_type==null )
		{
			return getAgentInfo( mapping,  form,
					 request,  response);
		}
		
		BranchManageServices acc=new BranchManageServices();
		
		int index=1;
		int lastIndex=1;
	    int pagesize=20;
		
		if(request.getParameter("index")!=null && !"".equals(request.getParameter("index")))
		{
			index=Integer.parseInt(request.getParameter("index"));
		}
		if(index<=0)
			index=1;
		int count=acc.getAgentInfoCount(inputName,selectName,sr_type,startDate,endDate);
		if(count>0)
			lastIndex=count%pagesize==0?count/pagesize:count/pagesize+1;
		
		if(index>=lastIndex)
			index=lastIndex;
		
		List<Object[]> arrList=acc.showAgentInfo(inputName,selectName,index,pagesize,sr_type,startDate,endDate);
		request.setAttribute("arrList",arrList);
		
		request.setAttribute("selectName", selectName);
		request.setAttribute("sr_type", sr_type);
		request.setAttribute("inputName", inputName);
		request.setAttribute("startDate",startDate);
		request.setAttribute("endDate",endDate);
		request.setAttribute("index",index);
		request.setAttribute("lastIndex",lastIndex);
		return getAgentInfo( mapping,  form,
				 request,  response);
		}
}
