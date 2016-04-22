package com.wlt.webm.AccountInfo.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.AccountInfo.AccountVerifyServies;
import com.wlt.webm.db.DBService;

/**
 * @author adminA
 *
 */
public class AccountVerify extends DispatchAction {
	
	/**
	 * 账户验证信息加载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception 
	 * @throws Exception 
	 */
	public ActionForward showAccountVerify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
		{
		String userLogins=request.getParameter("userLogins");
		String hh=request.getParameter("hh");
		String hidden=request.getParameter("hidden");
		if("1".equals(hidden) && 
				!"".equals(userLogins) && userLogins!=null &&
					!"".equals(hh) && hh!=null)
		{
			DBService db = null;
			try {
				db = new DBService();
				String ss="SELECT 1 FROM wht_AccountInfo_hidden WHERE userno=(SELECT user_no FROM sys_user WHERE user_login='"+userLogins+"')";
				String bool=db.getString(ss,null);
				String sql="";
				//禁用 或 启用账户  1禁用    0启用
				if("1".equals(hh))//禁用
				{
					if(!"".equals(bool) && bool != null)
					{
						request.setAttribute("mess","此用户已禁用!");
					}
					else
					{
						sql="INSERT INTO wht_AccountInfo_hidden(userno) SELECT user_no FROM sys_user WHERE user_login='"+userLogins+"'";
						if(db.update(sql,null)>0)
						{
							request.setAttribute("mess","操作成功!");
						}
					}
				}
				if("2".equals(hh)) //启用
				{
					if("".equals(bool) || bool == null)
					{
						request.setAttribute("mess","此用户不需要启用!");
					}
					else
					{
						sql="DELETE FROM wht_AccountInfo_hidden WHERE userno = (SELECT user_no FROM sys_user WHERE user_login='"+userLogins+"')";
						if(db.update(sql,null)>0)
						{
							request.setAttribute("mess","操作成功!");
						}
					}
				}
				
			} catch (SQLException e) {
				Log.error("禁用启用系统异常，，"+e);
				request.setAttribute("mess","操作失败,系统异常!");
			}
			finally
			{
				if(db!=null)
					db.close();
			}
		}
		
		String starts=request.getParameter("stat");
		String startDate=request.getParameter("intime");
		String endDate=request.getParameter("endTime");
		if(starts==null || "".equals(starts) || startDate==null || "".equals(startDate) || endDate==null || "".equals(endDate))
		{
			return mapping.findForward("success");
		}
		String hiddenType=request.getParameter("hiddenType");
		AccountVerifyServies acc=new AccountVerifyServies();
		
		int index=1;
		int lastIndex=1;
	    int pagesize=15;
		
		if(request.getParameter("index")!=null && !"".equals(request.getParameter("index")))
		{
			index=Integer.parseInt(request.getParameter("index"));
		}
		if(index<=0)
			index=1;
		int count=acc.getAccountVerifyCount(Integer.parseInt(starts), startDate+" 00:00:00", endDate+" 23:59:59",hiddenType);
		if(count>0)
			lastIndex=count%pagesize==0?count/pagesize:count/pagesize+1;
		
		if(index>=lastIndex)
			index=lastIndex;
		
		List<Object[]> arrList=acc.showAccountVerify(Integer.parseInt(starts), startDate+" 00:00:00", endDate+" 23:59:59",hiddenType,index,pagesize);
		request.setAttribute("arrList",arrList);
		
		request.setAttribute("stat", starts);
		request.setAttribute("intime", startDate);
		request.setAttribute("endTime", endDate);
		request.setAttribute("hiddenType",hiddenType);
		request.setAttribute("index",index);
		request.setAttribute("lastIndex",lastIndex);
		return mapping.findForward("success");
	}

	/**
	 * 发送验证
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward sendVerify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PrintWriter out=response.getWriter();
		String id=request.getParameter("id");
		if(id!=null && !"".equals(id))
		{
			int no=Integer.parseInt(id);//订单记录id
			//String ip=com.wlt.webm.util.Tools.getIpAddr(request);//用户真实ip
			AccountVerifyServies acc=new AccountVerifyServies();
			int status=acc.senVerify(no);
			out.print(status);
		}
		out.flush();
		out.close();
		return null;
	}
	
	/**
	 * 通过区号获取省id
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws Exception
	 */
	public ActionForward getIdName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setContentType("application/json;charset=gbk");
		 response.setCharacterEncoding("gbk");
		PrintWriter out=response.getWriter();
		String sa_zone=request.getParameter("sa_zone");
		if(sa_zone!=null && !"".equals(sa_zone))
		{
			DBService db = null;
			try {
				db = new DBService();
				Object[] obj=db.getStringArray("SELECT sa_id,sa_name FROM sys_area WHERE sa_id=(SELECT sa_pid FROM sys_area WHERE sa_zone='"+sa_zone+"')");
				JSONArray json=JSONArray.fromObject(obj); 
				out.print(json);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally
			{
				if(db!=null)
					db.close();
			}
		}
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 显示详细账户信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return List
	 * @throws Exception
	 */
	public ActionForward showDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 response.setContentType("application/json;charset=gbk");
		 response.setCharacterEncoding("gbk");
		PrintWriter out=response.getWriter();
		String id=request.getParameter("id");
		if(id!=null && !"".equals(id))
		{
			int no=Integer.parseInt(id);//订单记录id
			AccountVerifyServies acc=new AccountVerifyServies();
			List arryList=acc.showDetails(no);
			JSONArray json=JSONArray.fromObject(arryList); 
			out.print(json);
		}
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 删除未验证的账号
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return List
	 * @throws Exception
	 */
	public ActionForward DelAccountVerify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 response.setContentType("application/json;charset=gbk");
		 response.setCharacterEncoding("gbk");
		PrintWriter out=response.getWriter();
		
		 String id=request.getParameter("delID");
		 if(id!=null && !"".equals(id))
		 {
			 AccountVerifyServies acc=new AccountVerifyServies();
			 if(acc.DelAccountVerify(Integer.parseInt(id))>0)
			 {
				 out.print(true);
			 }
			 else
			 {
				 out.print(false);
			 }
		 }
		return null;
	}

	/**
	 * 获取当前用户 类型
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null 
	 * @throws Exception
	 */
	public ActionForward getUserRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		 response.setContentType("application/json;charset=gbk");
		 response.setCharacterEncoding("gbk");
		PrintWriter out=response.getWriter();
		
		 String roleId=request.getParameter("roleId");
		 if(roleId!=null && !"".equals(roleId))
		 {
			 AccountVerifyServies acc=new AccountVerifyServies();
			 out.println(acc.getUserRole(Integer.parseInt(roleId)));
		 }
		return null;
	}
}
