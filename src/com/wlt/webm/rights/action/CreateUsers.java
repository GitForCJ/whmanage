package com.wlt.webm.rights.action;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.wlt.webm.rights.bean.SysUser;
import com.wlt.webm.rights.form.AutoCreateUser;


/**
 * 
 * @author 1989
 *
 */
public class CreateUsers extends DispatchAction{

	
	/**
	 * 根据代理商自动开户
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward autoAddusers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("utf-8");
		AutoCreateUser files=(AutoCreateUser)form;
		if(null==files||files.getLogin()==null||files.getSourcefile()==null){
			request.setAttribute("mess", "所填数据不完整");
			return new ActionForward("/rights/autouser.jsp");
		}
		BufferedReader  buffer =new BufferedReader(new InputStreamReader(new ByteArrayInputStream(files.getSourcefile().getFileData())));
        String str=null;
        ArrayList<String[]> list=new ArrayList<String[]>();
        while((str=buffer.readLine())!=null){
        	list.add(str.split("#"));
        }
		SysUser user =new SysUser();
        request.setAttribute("mess", user.autoAddUser(files.getLogin() ,list)) ;
        return new ActionForward("/rights/autouser.jsp");
	}
}
