package com.wlt.webm.business.bean.liuliangfan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.action.NetPayAction;

public class LiuLiangFanBack extends DispatchAction {
	/**
	 * 流量饭 接口回调
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String result = request.getParameter("result");
		String order = request.getParameter("order");
		String info = request.getParameter("info");
		Log.info("流量贩回调信息:--------------"+order+"  "+result+" "+info+",,,结果描述：成功为1，失败为0 ");
		System.out.println("流量贩回调信息:--------------"+order+"  "+result+" "+info+",,,结果描述：成功为1，失败为0 ");
		//result	成功为1，失败为0
		PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print("1");
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		if(order==null || "".equals(order.trim()) || result==null || "".equals(result.trim())){
			Log.info("流量饭回调缺少参数,,,");
			System.out.println("流量饭回调缺少参数,,,");
			return null;
		}
		
		String st="";
		if("1".equals(result)){
			st="0";
		}else if("0".equals(result)){
			st="-1";
		}else{
			return null;
		}
		System.out.println("order-----"+order+"st-----"+st);
		//NetPayAction.Flows_service_Back(order,st);
		
		return null;
		
	}

}
