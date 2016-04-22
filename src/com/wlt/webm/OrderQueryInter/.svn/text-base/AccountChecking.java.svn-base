package com.wlt.webm.OrderQueryInter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.form.TPForm;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.scputil.MD5Util;
import com.wlt.webm.util.Tools;

/**
 * 对账请求类
 * @author adminA
 *
 */
public class AccountChecking   extends DispatchAction 
{
	/**
	 * 对账请求
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  null 输出
	 * @throws Exception 
	 */
	public ActionForward getAccountCheckings(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		response.setContentType("application/json;charset=utf-8");
		 response.setCharacterEncoding("utf-8");
		String userSysNo=request.getParameter("userSysNo");
		String date=request.getParameter("date");
		String sign=request.getParameter("sign");
		
		//从缓存中获取接口商信息
		TPForm tp=MemcacheConfig.getInstance().getTP(userSysNo);
		if(tp==null){
			Log.info("代理商不存在或者状态不正常...");
			send2TP(fillResponseXML("FAILED", "108", "代理商不存在或者状态不正常"),response);
			return null;
		}
		//验证ip或者终端号是否合法
		String ip=Tools.getIpAddr(request);
		if(tp.getIp().indexOf(ip)<0){
			Log.info("绑定的IP或终端编号地址不合法...");
			send2TP(fillResponseXML("FAILED", "105", "绑定的IP或终端编号地址不合法"),response);
			return null;	
		}
		if(null == userSysNo || null == date || null == sign ) {
			Log.info("参数不完整...");
			send2TP(fillResponseXML("FAILED", "101", "参数不完整"),response);
			return null;
		}

        String signString=tp.getUser_no()+date+tp.getKeyvalue();
		if(!MD5Util.MD5Encode(signString,"UTF-8").equals(sign)){
			Log.info("签名验证失败...");
			send2TP(fillResponseXML("FAILED", "102", "签名验证失败"),response);
			return null;	
		}
		
		// 只能查询今天以前的对账文件
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date d1=df.parse(date);
		Date d2=df.parse(df.format(new Date()));
		if(d1.getTime()>=d2.getTime())
		{
			Log.info("查询日期大于今天,,,,,");
			send2TP(fillResponseXML("FAILED", "204", "只能查询今天以前的对账文件"),response);
			return null;	
		}
		
		//判断对账文件是否存在
		String fileUrl="/opt/eagent/wh_task/interfacecheck/"+userSysNo+"-"+date+".rar";
		
		File file=new File(fileUrl);
		if (!file.exists())  //文件不存在
		{
			Log.info("请求成功，对账文件不存在，，，，");
			send2TP(fillResponseXML("SUCCESS", "204", "对账文件不存在"),response);
			return null;	
		}
		//输出返回的对账文件
		sendFile(file,response);
		return null;
	}
	
	/**
	 * 对账请求失败返回的信息
	 * @param resMsg 
	 * @param failedCode 
	 * @param failedReason 
	 * @return String
	 */
	public String fillResponseXML(String resMsg, String failedCode,
			String failedReason) {
		StringBuilder sf = new StringBuilder();
		sf.append(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?><response><resMsg>")
				.append(resMsg).append("</resMsg>").append("<failedCode>").append(
						failedCode).append("</failedCode>").append("<failedReason>")
				.append(failedReason).append("</failedReason>");
		sf.append("</response>");
		return sf.toString();
	}

	/**
	 * 向第三方发送结果
	 * 
	 * @param rs
	 * @param response
	 */
	public void send2TP(String rs, HttpServletResponse response) {
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(rs);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			Log.error("向第三方发送结果失败：" + rs + "  再次发送...");
			send2TP(rs, response);
		}
	}

	/**
	 * 文件输出
	 * @param file 
	 * @param response
	 * @throws IOException 
	 */
	public void sendFile(File file,HttpServletResponse response) throws IOException
	{
		Log.info("请求成功，准备发送，，，文件名称:"+file.getPath().toString());
		FileInputStream in=new FileInputStream(file);
		BufferedInputStream bin=new  BufferedInputStream (in);
		
		OutputStream os=response.getOutputStream();
		BufferedOutputStream bos=new BufferedOutputStream(os);
		
		 response.setHeader("Pragma", "No-cache");
	    response.setHeader("Cache-Control", "no-cache");
	    response.setDateHeader("Expires", 0);
	    response.setContentType("application/x-msdownload;charset=utf-8");
	    response.setHeader("Content-disposition", "attachment;filename="
	               + URLEncoder.encode(file.getName(), "utf-8"));
	    int bytesRead = 0;
	    byte[] buffer = new byte[8192];
	    while ((bytesRead = bin.read(buffer, 0, 8192)) != -1) {
	     bos.write(buffer, 0, bytesRead);
	    }
	    bos.flush();
	    in.close();
	    bin.close();
	    os.close();
	    bos.close();
	    Log.info("请求成功，输出对账文件成功，，，文件名称:"+file.getPath().toString());
	}
}
