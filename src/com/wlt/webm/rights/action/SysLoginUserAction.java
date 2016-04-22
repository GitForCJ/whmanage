package com.wlt.webm.rights.action;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.omg.PortableInterceptor.INACTIVE;

import com.wlt.webm.db.DBToolSCP;
import com.wlt.webm.rights.bean.SysLoginUser;
import com.wlt.webm.rights.form.SysLoginUserForm;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.util.MD5;

/**
 * 登陆账号管理<br>
 */
public class SysLoginUserAction extends DispatchAction
{
    
    /**
     * 添加登陆账号
     * @param mapping 
     * @param form 
     * @param request 
     * @param response 
     * @return ActionForward
     * @throws Exception 
     */
    public ActionForward add(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysLoginUserForm userForm = (SysLoginUserForm) form;
        SysLoginUser user=new SysLoginUser();
        user.add(userForm);
        return new ActionForward("/rights/wltuserloginlist.jsp?uid="+userForm.getUser_id());
    }
    /**
     * 添加登陆账号
     * @param mapping 
     * @param form 
     * @param request 
     * @param response 
     * @return ActionForward
     * @throws Exception 
     */
    public ActionForward addSafe(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysLoginUserForm userForm = (SysLoginUserForm) form;
        SysLoginUser user=new SysLoginUser();
        user.add(userForm);
        return new ActionForward("/rights/wltusersafeloginlist.jsp");
    }
    /**
     * 更新账号信息
     * @param mapping 
     * @param form 
     * @param request 
     * @param response 
     * @return ActionForward
     * @throws Exception 
     */
    public ActionForward updateSafe(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
		"userSession");
    	String userid=request.getParameter("userid");
    	String feeshortflag=request.getParameter("feeshortflag");
    	String shortflag=request.getParameter("shortflag");
    	String czflag=request.getParameter("czflag");
    	String macFlat=request.getParameter("mac");
    	String[] mac=request.getParameterValues("macBY");
    	String userno=request.getParameter("userno");
    	String pwd=request.getParameter("tradepassword");
    	if(null==pwd||!MD5.encode(pwd).equals(userForm.getDealpass())){
    		request.setAttribute("mess", "交易密码错误！");
			return new ActionForward("/rights/wltsafeloginuserupdate.jsp");
    	}
    	if("0".equals(macFlat))
    	{
    		boolean flag=false;
    		for(int i=0;i<mac.length;i++)
    		{
    			if(!"".equals(mac[i]) && mac[i]!=null)
    			{
    				flag=true;
    			}
    		}
    		if(!flag)
    		{
    			request.setAttribute("mess", "不能获取mac地址,请使用ie浏览器操作!");
    			return new ActionForward("/rights/wltsafeloginuserupdate.jsp");
    		}
    	}
    	
    	SysLoginUser user=new SysLoginUser();
        int n=user.update(Integer.parseInt(userid),Integer.parseInt(feeshortflag),Integer.parseInt(shortflag),Integer.parseInt(macFlat), userno, mac,czflag);
        if(n!=-1){
        SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
        userSession.setShortflag(shortflag);
        userSession.setFeeshortflag(feeshortflag);
        request.setAttribute("mess", "修改成功");
        }
        else   request.setAttribute("mess", "修改失败");
        return new ActionForward("/rights/wltsafeloginuserupdate.jsp");
    }
    /**
     * 更新账号信息
     * @param mapping 
     * @param form 
     * @param request 
     * @param response 
     * @return ActionForward
     * @throws Exception 
     */
    public ActionForward update(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
    	SysLoginUserForm userForm = (SysLoginUserForm) form;
    	SysLoginUser user=new SysLoginUser();
    	String pass = request.getParameter("pass");
    	if(!pass.equals(userForm.getUserpassword())){
    		userForm.setUserpassword(MD5.encode(userForm.getUserpassword()));
    	}
//        user.update(userForm);
        return new ActionForward("/rights/wltuserloginlist.jsp?uid="+userForm.getUser_id());
    }
    /**
     * 更新登陆密码
     * @param mapping 
     * @param form 
     * @param request 
     * @param response 
     * @return ActionForward
     * @throws Exception 
     */
    public ActionForward updatePass(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String newPass = request.getParameter("newpass");
    	String userid = request.getParameter("user_id");
    	SysLoginUser user=new SysLoginUser();
        if(user.updatePass(Integer.parseInt(userid),newPass)>0)
        {
        	HttpSession session = request.getSession();
      		session.removeAttribute("userSession");
            request.setAttribute("mess", "修改成功");
      		return new ActionForward("/rights/wltlogin.jsp");
        }
        request.setAttribute("mess", "修改失败!");
        return new ActionForward("/rights/wltusermodify.jsp");
    }
    /**
     * 更新交易密码
     * @param mapping 
     * @param form 
     * @param request 
     * @param response 
     * @return ActionForward
     * @throws Exception 
     */
    public ActionForward updatePassWord(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String newPass = request.getParameter("newpass");
    	String id = request.getParameter("user_id");
    	SysLoginUser user=new SysLoginUser();
        if(user.updatePassWord(Integer.parseInt(id),newPass)>0)
        {
        	HttpSession session = request.getSession();
      		session.removeAttribute("userSession");
            request.setAttribute("mess", "修改成功");
      		return new ActionForward("/rights/wltlogin.jsp");
        }
        request.setAttribute("msg", "修改失败");
        return new ActionForward("/rights/wltpassupdate_2.jsp");
    }
    /**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward checkPass(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			SysLoginUser user=new SysLoginUser();
			String userid = request.getParameter("user_id");
			String password = request.getParameter("password");
			String pwd = user.getPass(Integer.parseInt(userid));
			if(MD5.encode(password).equals(pwd)){
				printWriter.print(0);
				printWriter.flush();
				printWriter.close();
			}else {
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
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward checkPassWord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			SysLoginUser user=new SysLoginUser();
			String userid = request.getParameter("userid");
			String password = request.getParameter("password");
			SysLoginUserForm userForm = new SysLoginUserForm();
			String pwd = user.getPassWord(Integer.parseInt(userid),password);
			if(MD5.encode(password).equals(pwd)){
				printWriter.print(0);
				printWriter.flush();
				printWriter.close();
			}else {
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
}