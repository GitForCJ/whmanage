package com.wlt.webm.business.bean.zdhuawei;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.wlt.webm.business.bean.zdhuawei.fact.FixCallBackRequest;

public class ZDHuaweiFixAction  extends DispatchAction{
	private static String user_login="0000000265";
	private static String url="";
	static Logger log=Logger.getLogger(ZDHuaweiFixAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StringBuffer strBu=new  StringBuffer(); 
		String str2=null;
		JSONObject json=null;
		try{
			BufferedReader reader=new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
			String s=null;
			  while((s=reader.readLine())!=null){
			    strBu.append(s); 
			 }
			  str2=strBu.toString();
			 json=JSONObject.fromObject(str2);
		  }catch(Exception e){
			 log.error("读取请求json出错");
			 return null;
		 }					
		FixCallBackRequest fixrequest=new FixCallBackRequest();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		fixrequest.setTimestamp(format.format(new Date()));
		fixrequest.setZdws_method("winksi.topUpPhone.serverDown");		
		fixrequest.setZdws_user_login(user_login);
		FixCallBackEntity entity=new FixCallBackEntity();
		entity.setArea(json.getString("area"));//中大提供省份名称，如全国，北京等
		entity.setCard_type("2");//为1是充话费，为2是充流量
		entity.setOperator(json.getString("operator"));//运营商
		entity.setStatus(json.getString("status"));//为1是维护开始,为2是维护结束
		fixrequest.setZdws_params(entity);
		fixrequest.setZdws_sign(null);
		System.out.println("维护回调发送消息:"+JSONObject.fromObject(fixrequest).toString());
		Object resp=sendPost(url,JSONObject.fromObject(fixrequest).toString());
		response.setCharacterEncoding("UTF-8");
		PrintWriter wr = response.getWriter();
		log.info(resp.toString());
		wr.write(resp.toString());
		wr.flush();
		wr.close();
		return null;
	}
	 private Object sendPost(String url,String message){
		   CloseableHttpClient  httpclient=new DefaultHttpClient();
		   HttpPost httpPost=new HttpPost(url);
			StringEntity entity = new StringEntity(message, "utf-8");
	        entity.setContentEncoding("UTF-8");
	        entity.setContentType("application/json");
	        httpPost.setEntity(entity);
	        String str = null;
	       try{
	        HttpResponse result = httpclient.execute(httpPost);
	        if (result.getStatusLine().getStatusCode() == 200) {	           
	            try {
	                str = EntityUtils.toString(result.getEntity());    
	                System.out.println("返回消息:"+str);
	                log.debug("返回消息:"+str);
	            } catch (Exception e) {
	            	log.error("解析请求数据异常");
	               e.printStackTrace();
	            }
	        }	       
	       }catch(Exception e){
	    	   log.error("发送post请求异常");
	    	   e.printStackTrace();
	       }		
  	   return str;
	   }
}
