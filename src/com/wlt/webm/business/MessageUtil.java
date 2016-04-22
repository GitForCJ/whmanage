package com.wlt.webm.business;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import org.apache.axis.encoding.Base64;

import com.wlt.webm.scpcommon.Constant;

public class MessageUtil {

	public static boolean send(String phone,String content){
		String msgCode = "";
		try{
			String username = Constant.MESSAGE_USERNAME;
			String pass = Constant.MESSAGE_PASSWORD;
			URL url = new URL("http://www.duanxin100.cn/PostSubSms.do?md=SubSms&mobiles="+phone+"&content="+Base64.encode( content.getBytes("UTF-8") ) +"&username="+Base64.encode( username.getBytes("UTF-8") )+"&password="+pass+"&extSubCode=1");
			 
			long l=System.currentTimeMillis(); 
		    URLConnection uc = url.openConnection();       
		    
		    InputStream in = uc.getInputStream();       
		    int c;     
		    byte b[] =new byte[1024];
		    in.read(b);
		    in.close();
		     
		    String result=new String(b,"utf-8");
		    System.out.println(result);
		    if(result.substring(0, 1).equals("0"))
		    {
		    	return true;
		    }
		     
		    return false;
			
			}catch(Exception e)
			{
				return false;
			}
	}
}
