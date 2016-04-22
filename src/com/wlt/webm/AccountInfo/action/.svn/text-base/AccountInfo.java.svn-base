package com.wlt.webm.AccountInfo.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.AccountInfo.AccountInfoServices;
import com.wlt.webm.AccountInfo.bean.AccountInfoBean;
import com.wlt.webm.business.NetPayUtil;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.YMmessage;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.tool.MD5;
import com.wlt.webm.util.Tools;

/**
 * @author adminA
 *
 */
public class AccountInfo extends DispatchAction {
	
	/**
	 * 账户信息加载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception 
	 * @throws Exception 
	 */
	public ActionForward showAccountInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
		{
		AccountInfoServices acc=new AccountInfoServices();
		request.setAttribute("bankInfo",acc.getBankInfo());
		request.setAttribute("bankCardType",acc.getBankCardInfo());
		request.setAttribute("papersTypeInfo",acc.getPapersTypeInfo());
		request.setAttribute("regionalcode",acc.getRegionalCodeInfo());
		return mapping.findForward("success");
	}
	
	/**
	 * 账户信息录入
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception 
	 */
	public ActionForward saveAccountInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
		
		AccountInfoBean bean=(AccountInfoBean)form;
		//bean.setOrderseq(getTime());// 添加订单号
		bean.setStatus(0);// 未验证
		bean.setUserno(userSession.getUserno());//绑定用户编号
		bean.setPayType(0);//翼支付
		bean.setUsername(userSession.getUserename());//用户姓名
		bean.setUsercat(userSession.getExp2());//用户身份证id
		
		AccountInfoServices info=new AccountInfoServices();
		int status=info.saveAccountInfo(bean);
		request.setAttribute("ts",status);
		
		return mapping.findForward("success");
	}
	
	/**
	 * 生成 订单号，年月日时分秒+六位随机数  组成
	 * @return String  订单编号
	 */
	public static String getTime()
	{
		return Tools.getNow3()+((char)(new Random().nextInt(26) + (int)'A'))+((int)(Math.random()*10000)+10000);
	}
	
	/**
	 * 到处excel 生成数据集( 单行 标头 )
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public synchronized ActionForward abc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//标头列表
		ArrayList list=new ArrayList();
		String[] headerNameList=new String[]{"姓名;colspan=1;rowspan=1;","年龄;colspan=1;rowspan=1;","地址;colspan=2;rowspan=1;"};
		list.add(headerNameList);
		
		//数据列表
		ArrayList list1=new ArrayList();
		String[] strs=new String[]{"小施","22","湖北襄阳","abc"};
		String[] strs1=new String[]{"小丽","23","广东","abc"};
		String[] strs2=new String[]{"小张","24","北京丰台区成寿寺路中街关家坑48号","abc"};
		list1.add(strs);
		list1.add(strs1);
		list1.add(strs2);
		
		request.setAttribute("count",4);//总列数
		request.setAttribute("excelName","所有 信息列   表");//输出的 excel 文件名称
		request.setAttribute("HeaderName","用户信息"); // 表格标题名称
		request.setAttribute("HeaderList",list); // 表格标头 类别
		request.setAttribute("DataList",list1); // 数据列表
		return new ActionForward("/AccountInfo/download.jsp"); // 返回的输出页面
	}
	
	/**
	 * 获取验证图片
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	public  ActionForward getImg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
	 	DBService dd=null;
      try {
      		dd = new DBService();
         String imgStr=dd.getString("SELECT twodimensionalcode FROM mac WHERE userno='"+userSession.getUserno()+"'");
         Tools.generateImage(imgStr,response);
      } catch (Exception ex) {
         Log.error("获取 验证图片字符串异常"+ex);
      } finally {
         if (dd!=null)
      	   dd.close();
      }
		return null;
	}
	
	/**
	 * 找回密码 第一步
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	public  ActionForward mimahuoqu(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		String rand=request.getParameter("rand");
		String userName=request.getParameter("userName");
		if(rand==null || "".equals(rand) || userName=="" || "".equals(userName))
		{
			request.setAttribute("mess","参数不足!");
			return new ActionForward("/rights/password.jsp"); // 返回的输出页面
		}
		String ss=request.getSession().getAttribute("rand")+"";
		if(!ss.equals(rand))
		{
			request.setAttribute("mess","验证码错误!");
			return new ActionForward("/rights/password.jsp"); // 返回的输出页面
		}
	 	DBService dd=null;
      try {
      		dd = new DBService();
         String imgStr=dd.getString("SELECT user_tel FROM sys_user WHERE user_login='"+userName+"'");
         if("".equals(imgStr) || imgStr==null)
         {
 			request.setAttribute("mess","登录账户不存在!");
 			return new ActionForward("/rights/password.jsp"); // 返回的输出页面
 		 } 
         request.setAttribute("userName",userName);
         request.setAttribute("usertel",imgStr.trim());
         return new ActionForward("/rights/passwordTwo.jsp"); // 返回的输出页面
      } catch (Exception ex) {
         Log.error("找回密码，判断用户名是否存在"+ex);
         request.setAttribute("mess","系统异常");
		 return new ActionForward("/rights/password.jsp"); // 返回的输出页面
      } finally {
         if (dd!=null)
      	   dd.close();
      }
	}
	/**
	 * 找回密码提交 第二步
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	public  ActionForward submitMIMA(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String catName=request.getParameter("catName");
		String cat=request.getParameter("cat");
		String dlmima=request.getParameter("dlmima");
		String jymima=request.getParameter("jymima");
		String yanzhengma=request.getParameter("yanzhengma");
		String accountPhone=request.getParameter("accountPhone");
		if(cat==null || "".equals(cat) ||
				dlmima==null || "".equals(dlmima) ||
					jymima==null || "".equals(jymima) ||
						yanzhengma==null || "".equals(yanzhengma) ||
							accountPhone==null || "".equals(accountPhone) ||
								catName==null || "".equals(catName))
		{
			request.setAttribute("mess","参数不足!");
 			return new ActionForward("/rights/password.jsp"); // 返回的输出页面
		}
		if(request.getSession().getAttribute(accountPhone+"_zhmm")==null || "".equals(request.getSession().getAttribute(accountPhone+"_zhmm")))
		{
			request.setAttribute("mess","请使用短信验证!");
 			return new ActionForward("/rights/password.jsp"); // 返回的输出页面
		}
		String abc=request.getSession().getAttribute(accountPhone+"_zhmm")+"".trim();
		request.getSession().removeAttribute(accountPhone+"_zhmm");
		if(!yanzhengma.trim().equals(abc))
		{
			request.setAttribute("mess","短信验证码错误!");
 			return new ActionForward("/rights/password.jsp"); // 返回的输出页面
		}
	 	DBService dd=null;
      try {
      		dd = new DBService();
         String imgStr=dd.getString("SELECT  1 FROM sys_user WHERE user_login='"+accountPhone+"' AND exp2='"+cat+"' AND user_ename='"+catName+"'");
         if("".equals(imgStr) || imgStr==null)
         {
 			request.setAttribute("mess","用户信息不符，无法重置!");
 			return new ActionForward("/rights/password.jsp"); // 返回的输出页面
 		 } 
        dlmima=MD5.encode(dlmima);
        jymima=MD5.encode(jymima);
        String updatesql="UPDATE sys_user SET user_pass='"+dlmima+"',trade_pass='"+jymima+"' WHERE user_login='"+accountPhone+"' AND exp2='"+cat+"'";
        if(dd.update(updatesql)>0)//找回密码成功
        {
        	String upd="UPDATE sys_userloginlimit SET TIME=NULL,errornum=1 WHERE login='"+accountPhone+"'";//解冻
        	if(dd.update(upd)>0)
        	{
	        	request.setAttribute("mess","重置密码成功,解冻成功!");
	        	request.setAttribute("type","1");
	 			return new ActionForward("/rights/passwordTwo.jsp"); // 返回的输出页面
        	}
        	else
        	{
        		request.setAttribute("mess","重置密码成功,解冻失败!");
	        	request.setAttribute("type","1");
	 			return new ActionForward("/rights/passwordTwo.jsp"); // 返回的输出页面
        	}
        }
        else
        {
        	request.setAttribute("mess","系统异常,修改登录交易密码失败!");
 			return new ActionForward("/rights/password.jsp"); // 返回的输出页面
        }
      } catch (Exception ex) {
         Log.error("找回密码，根据用户名获取，，，"+ex);
         request.setAttribute("mess","系统异常");
		 return new ActionForward("/rights/password.jsp"); // 返回的输出页面
      } finally {
         if (dd!=null)
      	   dd.close();
      }
	}
	/**
	 * 找回密码  短信发送
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward sendMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		PrintWriter printWriter = null;
		try {
			String phone=request.getParameter("phone");
			if(phone==null || "".equals(phone))
			{
				printWriter.print(1);
				printWriter.flush();
				printWriter.close();
			}
			printWriter = response.getWriter();
			String msgCode = NetPayUtil.getRandEight();
			if(null != phone && !"".equals(phone)){
				Log.info(phone+" 找回密码验证码:"+msgCode);
					if(YMmessage.qxtSendSMS(phone, YMmessage.zu(msgCode))){
					System.out.println("msgCode:"+msgCode);
				request.getSession().setAttribute(phone+"_zhmm", msgCode);
				printWriter.print(0);
				printWriter.flush();
				printWriter.close();
				}else{
					printWriter.print(1);
					printWriter.flush();
					printWriter.close();		
				}
			}else {
				printWriter.print(1);
				printWriter.flush();
				printWriter.close();
			}
		} catch (IOException e) {
			printWriter.print(1);
			printWriter.flush();
			printWriter.close();
			e.printStackTrace();
		}
		return null;
	}
	
	/** 消息弹出框 信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	public ActionForward showMessage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		response.setContentType("application/json;charset=gbk");
		 response.setCharacterEncoding("gbk");
		PrintWriter out = null;
		DBService db=null;
		try {
			out=response.getWriter();
			db=new DBService();
			List arry=db.getList("SELECT an_id,CONCAT(SUBSTRING(an_title,1,10),'...') ss,an_deaddate FROM sys_annotice WHERE contentType=1 AND an_faceid=0 AND an_deaddate>='"+Tools.getNow4()+"' ORDER BY an_deaddate DESC LIMIT 0,3");
			if(arry==null || "".equals(arry) || arry.size()<=0){
				out.print(1);
				out.flush();
				out.close();
				return null;
			}
			JSONArray json=JSONArray.fromObject(arry); 
			out.print(json);
			out.flush();
			out.close();
			return null;
		} catch (Exception e) {
			out.print(1);
			out.flush();
			out.close();
			return null;
		}finally{
			if(db!=null)
				db.close();
		}
	}
}
