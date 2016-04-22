package com.wlt.webm.business.action;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.app.AppUserBcard;
import com.wlt.webm.db.DBService;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.MD5;

/**
 * @author admin
 */
public class WebAppService extends DispatchAction {
	
	/**
	 * 登陆 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null;
 	 * @throws Exception
	 */
	public ActionForward AppLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String rsCode="";
		PrintWriter out=null;
		DBService db=null;
		try {
			db=new DBService();
			out=response.getWriter();
			
			String account=request.getParameter("account");
			String password=request.getParameter("password");
			
			if(account==null || "".equals(account)
				|| password==null || "".equals(password)){
				Log.info("登陆，参数不足，，"+account);
				rsCode="004";//参数不足
				out.println(RsJson(rsCode,"参数不足"));
				out.flush();
				out.close();
				return null;
			}
			String pwd=db.getString("SELECT user_pass FROM wht_app_user WHERE user_login='"+account+"'");
			if(pwd==null || "".equals(pwd)){
				Log.info("登陆，账户信息不存在，，"+account);
				rsCode="001";//账户信息不存在
				out.println(RsJson(rsCode,"账户信息不存在"));
				out.flush();
				out.close();
				return null;
			}
			if(!pwd.equals(MD5.encode(password))){
				Log.info("登陆，登陆密码错误，，"+account);
				rsCode="002";//登陆密码错误
				out.println(RsJson(rsCode,"登陆密码错误"));
				out.flush();
				out.close();
				return null;
			}
			Log.info("登陆，登陆成功，，"+account);
			rsCode="000";//登陆成功
			out.println(RsJson(rsCode,"登陆成功"));
			out.flush();
			out.close();
			return null;
		}catch(Exception e){
			Log.error("登陆，服务器异常");
			rsCode="003";//服务器异常
			out.println(RsJson(rsCode,"服务器异常"));
			out.flush();
			out.close();
			return null;
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
	}
	
	/**
	 * 编辑 请求
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null;
	 * @throws Exception
	 */
	public ActionForward AppEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PrintWriter out=null;
		DBService db=null;
		AppUserBcard appUserInfo=new AppUserBcard();
		try {
			db=new DBService();
			out=response.getWriter();
			
			String account=request.getParameter("account");
			
			if(account==null || "".equals(account)){
				Log.info("编辑请求，参数不足，，"+account);
				appUserInfo.setCode("004");//参数不足
				out.println(RsEditJson(appUserInfo));
				out.flush();
				out.close();
				return null;
			}
			String[] strs=db.getStringArray("SELECT user_login,NAME,company,POSITION,qq,address,email,note,phone," +
					"telphone,fax,website,msn FROM wht_app_user where user_login='"+account+"'");
			if(strs==null || strs.length<=0){
				Log.info("编辑请求，账户不存在，，"+account);
				appUserInfo.setCode("001");//账户不存在
				out.println(RsEditJson(appUserInfo));
				out.flush();
				out.close();
				return null;
			}
			appUserInfo.setAccount(strs[0]);
			appUserInfo.setName(strs[1]);
			appUserInfo.setCompany(strs[2]);
			appUserInfo.setPassword(strs[3]);
			appUserInfo.setQq(strs[4]);
			appUserInfo.setAddress(strs[5]);
			appUserInfo.setEmail(strs[6]);
			appUserInfo.setNote(strs[7]);
			appUserInfo.setPhone(strs[8]);
			appUserInfo.setTelphone(strs[9]);
			appUserInfo.setFax(strs[10]);
			appUserInfo.setWebsite(strs[11]);
			appUserInfo.setMsn(strs[12]);
			
			Log.info("编辑请求，成功，，"+account);
			appUserInfo.setCode("000");//用户信息
			out.println(RsEditJson(appUserInfo));
			out.flush();
			out.close();
			return null;
		}catch(Exception e){
			Log.error("编辑请求，服务器异常");
			//服务器异常
			appUserInfo.setCode("003");
			out.println(RsEditJson(appUserInfo));
			out.flush();
			out.close();
			return null;
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
	}
	
	/**
	 * 编辑 提交
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward AppEditSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String rsCode="";
		PrintWriter out=null;
		DBService db=null;
		try {
			db=new DBService();
			out=response.getWriter();
			
			String account=request.getParameter("account")==null?"":URLDecoder.decode(request.getParameter("account"),"utf-8");
			String name=request.getParameter("name")==null?"":URLDecoder.decode(request.getParameter("name"),"utf-8");
			String company=request.getParameter("company")==null?"":URLDecoder.decode(request.getParameter("company"),"utf-8");
			String position=request.getParameter("position")==null?"":URLDecoder.decode(request.getParameter("position"),"utf-8");
			String qq=request.getParameter("qq")==null?"":URLDecoder.decode(request.getParameter("qq"),"utf-8");
			String address=request.getParameter("address")==null?"":URLDecoder.decode(request.getParameter("address"),"utf-8");
			String email=request.getParameter("email")==null?"":URLDecoder.decode(request.getParameter("email"),"utf-8");
			String note=request.getParameter("note")==null?"":URLDecoder.decode(request.getParameter("note"),"utf-8");
			String phone=request.getParameter("phone")==null?"":URLDecoder.decode(request.getParameter("phone"),"utf-8");
			String telphone=request.getParameter("telphone")==null?"":URLDecoder.decode(request.getParameter("telphone"),"utf-8");
			String fax=request.getParameter("fax")==null?"":URLDecoder.decode(request.getParameter("fax"),"utf-8");
			String website=request.getParameter("website")==null?"":URLDecoder.decode(request.getParameter("website"),"utf-8");
			String msn=request.getParameter("msn")==null?"":URLDecoder.decode(request.getParameter("msn"),"utf-8");
			
			if(account==null || "".equals(account)){
				Log.info("编辑提交，参数不足，，"+account);
				rsCode="004";//参数不足
				out.println(RsJson(rsCode,"参数不足"));
				out.flush();
				out.close();
				return null;
			}
			String pwd=db.getString("SELECT user_pass FROM wht_app_user WHERE user_login='"+account+"'");
			if(pwd==null || "".equals(pwd)){
				Log.info("编辑提交，账户信息不存在，，"+account);
				rsCode="001";//账户信息不存在
				out.println(RsJson(rsCode,"账户信息不存在"));
				out.flush();
				out.close();
				return null;
			}
			
			StringBuffer buff=new StringBuffer();
			buff.append("UPDATE wht_app_user SET exp6='"+Tools.getNow()+"' ");
			if(name!=null && !"".equals(name)){
				buff.append(",name='"+name+"'");
			}
			if(company!=null && !"".equals(company)){
				buff.append(",company='"+company+"'");
			}
			if(position!=null && !"".equals(position)){
				buff.append(",position='"+position+"'");
			}
			if(qq!=null && !"".equals(qq)){
				buff.append(",qq='"+qq+"'");
			}
			if(address!=null && !"".equals(address)){
				buff.append(",address='"+address+"'");
			}
			if(email!=null && !"".equals(email)){
				buff.append(",email='"+email+"'");
			}
			if(note!=null && !"".equals(note)){
				buff.append(",note='"+note+"'");
			}
			if(phone!=null && !"".equals(phone)){
				buff.append(",phone='"+phone+"'");
			}
			if(telphone!=null && !"".equals(telphone)){
				buff.append(",telphone='"+telphone+"'");
			}
			if(fax!=null && !"".equals(fax)){
				buff.append(",fax='"+fax+"'");
			}
			if(website!=null && !"".equals(website)){
				buff.append(",website='"+website+"'");
			}
			if(msn!=null && !"".equals(msn)){
				buff.append(",msn='"+msn+"'");
			}
			buff.append("WHERE user_login='"+account+"'");
			int updateCon=db.update(buff.toString());
			if(updateCon<=0){
				Log.info("编辑提交，编辑失败，，"+account);
				rsCode="002";//编辑失败
				out.println(RsJson(rsCode,"编辑失败"));
				out.flush();
				out.close();
				return null;
			}
			Log.info("编辑提交，编辑成功，，"+account);
			rsCode="000";//编辑成功
			out.println(RsJson(rsCode,"编辑成功"));
			out.flush();
			out.close();
			return null;
		}catch(Exception e){
			Log.info("编辑提交，服务器异常，");
			rsCode="003";//服务器异常
			out.println(RsJson(rsCode,"服务器异常"));
			out.flush();
			out.close();
			return null;
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
	}
	
	/**
	 * 注册信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null;
	 * @throws Exception
	 */
	public ActionForward Registered(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String rsCode="";
		PrintWriter out=null;
		DBService db=null;
		try {
			db=new DBService();
			out=response.getWriter();
			
			String account=request.getParameter("account");
			String password=request.getParameter("password");
			String url=request.getParameter("url");
			
			if(account==null || "".equals(account)
				|| password==null || "".equals(password)
				|| url==null || "".equals(url)){
				Log.info("参数不足，，，"+account);
				rsCode="004";//参数不足
				out.println(RsJson(rsCode,"参数不足"));
				out.flush();
				out.close();
				return null;
			}
			
			String url_id=url.substring(url.indexOf("id=")+3,url.length());
			if(url_id==null || "".equals(url_id)){
				Log.info("参数不足2，，，"+account);
				rsCode="004";//参数不足
				out.println(RsJson(rsCode,"参数不足"));
				out.flush();
				out.close();
				return null;
			}
			
			String pwd=db.getString("SELECT 1 FROM wht_app_user WHERE user_login='"+account+"'");
			if(pwd!=null && !"".equals(pwd)){
				rsCode="005";//账户信息不存在
				out.println(RsJson(rsCode,"账户信息已存在"));
				out.flush();
				out.close();
				return null;
			}
			int insertCon=db.update("INSERT INTO wht_app_user(user_login,user_pass,user_createdate,user_state,url_id) VALUES('"+account+"','"+MD5.encode(password)+"','"+Tools.getNow()+"',0,"+url_id+")");
			if(insertCon<=0){
				Log.info("注册失败，，，"+account);
				rsCode="002";//注册失败
				out.println(RsJson(rsCode,"注册失败"));
				out.flush();
				out.close();
				return null;
			}
			Log.info("注册成功，，，"+account);
			rsCode="000";//注册成功
			out.println(RsJson(rsCode,"注册成功"));
			out.flush();
			out.close();
			return null;
		}catch(Exception e){
			Log.error("注册异常，，，"+e);
			rsCode="003";//服务器异常
			out.println(RsJson(rsCode,"服务器异常"));
			out.flush();
			out.close();
			return null;
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
	}
	
	/**
	 * 响应
	 * @param code
	 * @param desc
	 * @return null
	 */
	public static String RsJson(String code,String desc){
		String str="{\"code\":\""+code+"\",\"desc\":\""+desc+"\"}";
		return str;
	}
	
	/**
	 * 响应用户信息 json 
	 * @param info
	 * @return string 
	 * @throws UnsupportedEncodingException 
	 */
	public static String RsEditJson(AppUserBcard info) throws UnsupportedEncodingException {
		return URLEncoder.encode(JSON.toJSONString(info),"UTF-8");
	}
	
}
