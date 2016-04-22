package com.wlt.webm.ten.action;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBService;
import com.wlt.webm.ten.bean.HttpRequest;
import com.wlt.webm.ten.service.QBPayResponseHandler;
import com.wlt.webm.ten.service.TenpayConfigParser;
import com.wlt.webm.ten.service.TenpayXmlPath;

/**  
 * 财付通转账控制<br>
 */
public class QBTenpayResultAction extends DispatchAction {

	/**
	 * 判断返回结果的Action
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response  
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		Log.info("QB充值回调");
		String order = request.getParameter("order");
		String qq = request.getParameter("qq");
		//超时处理
		if(null!=order&&null!=qq&&"1".equals(order)){
			String tranid=request.getParameter("tran");
			request.setAttribute("flag", "1");
			request.setAttribute("tranid", tranid);
			request.setAttribute("qq", qq);
			return new ActionForward("/wlttencent/qbtransfertimeout.jsp");
		}else if(null!=order&&null!=qq&&"0".equals(order)){
			request.setAttribute("flag", "0");
			return new ActionForward("/wlttencent/qbtransfertimeout.jsp?qq="+qq+"order="+order);
		}else{
		TenpayXmlPath tenpayxmlpath = new TenpayXmlPath();
		//密钥
		String key = TenpayConfigParser.getConfig().getQbsign();
		//创建PayResponseHandler实例
		QBPayResponseHandler resHandler = new QBPayResponseHandler(request, response);
		resHandler.setKey(key);
		//支付结果
		String pay_result = resHandler.getParameter("ret");
		String transaction_id = resHandler.getParameter("tran_seq");
		//交易单号
		Log.info("订单号："+transaction_id);
		Log.info("支付结果:"+pay_result);
		//结果
		System.out.println("transaction_id:"+transaction_id);
		System.out.println(HttpRequest.maps.get(transaction_id));
//		if( "0".equals(pay_result)){
//    				String url = TenpayConfigParser.getConfig().getQbshow();
//    				Log.info("show_url为：----->"+url);
//					//调用doShow, 打印meta值跟js代码,告诉财付通处理成功,并在用户浏览器显示$show页面.
//    				request.setAttribute("posid", HttpRequest.maps.remove(transaction_id));
//        			resHandler.doShow(url);
//        			return null;
//			}else {
				Log.info("QB回调");
    			resHandler.doShowMessage(Integer.valueOf(pay_result).intValue());
    			String urll="/wlttencent/qbpay.jsp?posid="+HttpRequest.maps.remove(transaction_id);
    			System.out.println(urll);
    			return new ActionForward(urll);
//			}
		}
		}
	
	/**
	 * 查询是否存在某条记录  或更新某条记录
	 * @param sql
	 * @param params
	 * @param lg 
	 * @return flag
	 */
	public boolean getRecord(String sql,Object[] params,String lg){
		boolean flag =false;
		DBService dbService=null;
		try {
				dbService = new DBService();
				if(lg.equals("1")){
				String sql1="select * from wlt_QBtransferlog where tran_seq=? and neiFlag=?";
				List list =dbService.getStringList(sql, params) ;
				List list1 =dbService.getStringList(sql1, params) ;
				if(list.size()!=0||list1.size()!=0){
					flag=true;
					Log.info("收到重复消息不做处理");
				}
				else{
					flag=false;
					Log.info("没查到记录...");
				}
				}
				else{
					int i=0;
					i=dbService.update(sql, params);
					if(i!=0)
						flag=true;
				}	
		} catch (SQLException e) {
			Log.info("QB异常"+e.toString());
			e.printStackTrace();
		}finally{
			dbService.close();
		}
		return flag;
	}
	
	
}
