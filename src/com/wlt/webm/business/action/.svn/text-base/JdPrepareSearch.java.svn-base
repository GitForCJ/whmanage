package com.wlt.webm.business.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.Tools;

/**
 * 京东流量预查询
 * 
 * @author 1989
 * 
 */
public class JdPrepareSearch extends DispatchAction {

	/**
	 * @param mapping 
	 * @param form 
	 * @param request 
	 * @param response 
	 * @return json
	 */
	@SuppressWarnings({ "unchecked", "unchecked" })
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> params = request.getParameterMap();
        HashMap<String,String> prepare =new HashMap<String,String>();
        HashMap<String,String> yewu =new HashMap<String,String>();
        for (String key : params.keySet()) {
            prepare.put(key,params.get(key)[0]);
        }
        Log.info("JD预查询请求:"+prepare.get("fillMobile")+" MB:"+prepare.get("fillAmount"));
        String sign=Tools.jdSign(prepare);
        if(!prepare.get("sign").equals(sign)){//签名错误
        	yewu.put("isSuccess", "F");
        	yewu.put("errorCode", "JDI_00002");
        }else if("3".equals(yewu.get("mutCode")) || "99".equals(prepare.get("areaCode"))){//京东运营商不处理 || 地市为 全国
        	yewu.put("isSuccess", "F");
        	yewu.put("errorCode", "JDI_00003");
        }else {
        	yewu.put("isSuccess", "T");
        	yewu.put("errorCode", "");
        }
        yewu=Tools.jDParams(yewu);
        try {
			PrintWriter wr=response.getWriter();
			wr.write(JSON.toJSONString(yewu));
			wr.flush();
			wr.close();
		} catch (IOException e) {
			 Log.error("JD预查询请求,号码:"+prepare.get("fillMobile")+",,MB:"+prepare.get("fillAmount")+" ex:"+e.toString());	
		}
		return null;
	}
	
	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client=new HttpClient();
		PostMethod post=new PostMethod("http://localhost/wh/trafficPrepareSearch.do?fillMobile=13811286112&fillAmount=50&fillType=100&areaUsed=1&timestamp=20090707122322&version=1.0&signType=MD5&sign=1f3870be274f6c49b3e31a0c6728957f&mutCode=1&areaCode=1");
		client.executeMethod(post);
		System.out.println(post.getResponseBodyAsString());
	}
}
