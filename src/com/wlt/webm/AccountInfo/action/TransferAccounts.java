package com.wlt.webm.AccountInfo.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.AccountInfo.TransferAccountsServies;
import com.wlt.webm.db.DBService;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.util.MD5;

/**
 * @author adminA
 *
 */
public class TransferAccounts extends DispatchAction {
	/**
	 * 转账信息列表加载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception 
	 * @throws Exception 
	 */
	public ActionForward showTransferAccounts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
		{
		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");//获取当前用户信息
		TransferAccountsServies acc=new TransferAccountsServies();
		List<Object[]> arrList=acc.showTransferAccounts(userSession.getUserno());
		for(int i=0;i<arrList.size();i++)
		{
			Object[] obj=arrList.get(i);
			obj[0]=getDisplayStr(obj[0].toString());
			
			obj[2]=getDisplayStr(obj[2].toString());
			
			obj[4]=getDisplayStr(obj[4].toString());
		}
		
		request.setAttribute("arrList",arrList);
		return mapping.findForward("success");
	}
	
	
	/**
	 * 加密显示字符串
	 * @param str
	 * @return string
	 */
	public static String getDisplayStr(String str)
	{
		String displayStr="";
		for(int i=0;i<str.length();i++)
		{
			if(i>3 && i<(str.length()-4))
			{
				displayStr=displayStr+"*";
				continue;
			}
			displayStr=displayStr+str.charAt(i);
		}
		return displayStr;
	}
	
	/**
	 * 转款
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception 
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	public ActionForward saveTransferAccounts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
		{
		String password=request.getParameter("password");
		
		String id=request.getParameter("radio");//数据库id
		String money=request.getParameter("order_price");
		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
		int flagA=-1;
		
		DBService db = null;
		try{
			db=new DBService();
			String bool=db.getString("SELECT 1 FROM wht_AccountInfo_hidden WHERE userno='"+userSession.getUserno()+"'",null);
			if(bool!=null && !"".equals(bool))
			{
				flagA=-4;
			}
		}catch(Exception e){
			Log.error("系统异常aabbcc,,,"+e);
		}finally
		{
			if(db!=null)
				db.close();
		}
		
		if(flagA==-1)
		{
			//判断交易密码
			String md5password=new MD5().encode(password);
			TransferAccountsServies acc=new TransferAccountsServies();
			if(acc.TimeControl(userSession.getUserno())<=0)
			{
				if(acc.getPassword(userSession.getUserno(), md5password)>0)
				{
					if(id!=null && !"".equals(id) && money!=null && !"".equals(money))
					{
					    flagA = acc.saveTransferAccounts(Integer.parseInt(id),money,userSession.getUsername(),userSession.getUserno(),0);//0 标识翼支付
					}
				}
				else
				{
					flagA=-2;
				}
			}
			else
			{
				flagA=-3;
			}
		}
		request.setAttribute("money",money);
		request.setAttribute("radioid",id);
		request.setAttribute("flagA",flagA);
		return showTransferAccounts( mapping,  form, request,  response);
	}
	
}
