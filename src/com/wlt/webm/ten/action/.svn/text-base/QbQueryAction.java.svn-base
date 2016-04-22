package com.wlt.webm.ten.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.ten.service.MD5Util;
import com.wlt.webm.ten.service.TenpayConfigParser;
import com.wlt.webm.ten.service.TenpayXmlPath;

/**
 * QB查询<br>
 */
public class QbQueryAction extends DispatchAction {
	
	/**
	 * 判断返回结果的Action
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orderID=request.getParameter("order");
		String newstartdate=request.getParameter("newstartdate").replaceAll("-", "");	
		System.out.println("orderID="+orderID+"  newstartdate="+newstartdate);
        StringBuffer sf=new StringBuffer();
		TenpayXmlPath tenpayxmlpath = new TenpayXmlPath();
		//密钥
		String key = TenpayConfigParser.getConfig().getQbsign();
		String bussinessID= TenpayConfigParser.getConfig().getQbid();
		String signStr="mch_id="+bussinessID+"&tran_seq="+orderID+"&tran_date="+newstartdate+"||"+key;
		String sign=MD5Util.MD5Encode(signStr, "UTF-8");
		String content="mch_id=" +bussinessID+
				"&tran_seq=" +orderID+
				"&tran_date=" +newstartdate+
				"&sign="+sign;
		String url="http://esales.qq.com/cgi-bin/esales_sell_dir_qry_order_status.cgi?"+content;
    	try{
	        URL getUrl = new URL(url);
	        HttpURLConnection connection = (HttpURLConnection) getUrl
	                .openConnection();
	        connection.connect();
	        if(!(connection.getResponseCode()==HttpURLConnection.HTTP_OK)){
	        	request.setAttribute("flag","0");
	        	return new ActionForward("/wlttencent/qbtransfertimeout.jsp");
	        }
	        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
	        String lines;
	        sf.delete(0, sf.length());
	        while ((lines = reader.readLine()) != null){
	            sf.append(lines);
	        }
			reader.close();
			connection.disconnect();
	        System.out.println(sf.toString());
//	        ret=4&mch_id=1210846701&tran_date=&tran_seq=
//	        &order_status=0&ret_msg=??????[mch_id ????]&sign=870494bb83e1182aff9375f23c27d657
	        String[] result=sf.toString().split("&");
	        Map maps=new HashMap();
	        for(int i=0;i<result.length;i++){
	        	String [] str=result[i].split("=");
	        	if(str.length>1){
		        	maps.put(str[0], str[1]);
	        	}
	        }
	        String message=null;
	        if("0".equals(maps.get("ret"))){
	        	switch (Integer.valueOf((String)maps.get("order_status")).intValue()) {
	    		case 0:
	    			message = "初始化中,稍后在查";
	    	 break;
	    		case 1:
	    			message = "完成,已扣款,已发货";
	    			break;
	    		case 2:
	    			message = "未扣款,未发货";
	    			break;
	    		case 3:
	    			message = "已扣款,发货不成功,系统会自动补发货";
	    			break;
	    		case 4:
	    			message = "正在交易,已扣款,尚未发货";
	    			break;
	    		case 6:
	    			message = "未扣款,未发货";
	    			break;
	    		default:
	    			 message ="未知错误";
	    		     break;
	    		}
             request.setAttribute("order_status", message);
             request.setAttribute("tran_seq", maps.get("tran_seq"));
             request.setAttribute("flag", "3");
	        }else{
	        	switch (Integer.valueOf((String)maps.get("ret")).intValue()) {
	    		case 4:
	    			message = "订单号不存在";
	    			break;
	    		case 100:
	    			message = "经销商未注册直通车";
	    			break;
	    		case 110:
	    			message = "签名错误";
	    			break;
	    		case 120:
	    			message = "IP不合法";
	    			break;
	    		case 130:
	    			message = "订单不存在";
	    			break;
	    		case 140:
	    			message = "直通车密钥不存在";
	    			break;
	    		}
	        	request.setAttribute("order_status", message);
	        	request.setAttribute("flag", "2");
	        }
	        
    	}catch (Exception e) {
	    	Log.error("Q币充值查询:"+e.toString());
	    	request.setAttribute("flag", "0");
        	return new ActionForward("/wlttencent/qbtransfertimeout.jsp");
			}
            return new ActionForward("/wlttencent/qbtransfertimeout.jsp");
	}
	
}
