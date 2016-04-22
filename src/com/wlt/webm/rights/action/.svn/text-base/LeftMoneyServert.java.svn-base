package com.wlt.webm.rights.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wlt.webm.rights.bean.SysUser;
import com.wlt.webm.rights.form.SysUserForm;

/**
 * 
 * @author caisj
 *
 */
public class LeftMoneyServert extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		doPost(req,resp);

	}

	/**
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws
    ServletException, IOException {
		response.setContentType("text/html");
		String userid=request.getParameter("userID");
		Object user1=request.getSession().getAttribute("userSession");
		String str="0";
		if(null!=user1&&null!=userid&&!userid.equals("null")&&((SysUserForm)user1).getUserno().equals(userid)){
			SysUser user=new SysUser();
			str=user.getUseLeft(userid);
	        user=null;
		}
	    PrintWriter out = response.getWriter();
        out.print(str);
        out.flush();
        out.close();

}
	


}
