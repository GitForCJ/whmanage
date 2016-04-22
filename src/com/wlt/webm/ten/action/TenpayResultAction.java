package com.wlt.webm.ten.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import java.util.List;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBService;
import com.wlt.webm.pccommon.SCPConstants;
import com.wlt.webm.ten.bean.SendMsgScpBean;
import com.wlt.webm.ten.service.PayResponseHandler;
import com.wlt.webm.ten.service.TenpayConfigParser;
import com.wlt.webm.ten.service.TenpayXmlPath;
import com.wlt.webm.util.TenpayUtil;

/**
 * 财付通转账控制<br>
 * @author caiSJ
 */
public class TenpayResultAction extends DispatchAction {
	
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
		
		TenpayXmlPath tenpayxmlpath = new TenpayXmlPath();
		
		//密钥
		String key = TenpayConfigParser.getConfig().getKey();

		//创建PayResponseHandler实例
		PayResponseHandler resHandler = new PayResponseHandler(request, response);
		resHandler.setKey(key);
		//判断签名
		if(resHandler.isTenpaySign()) {//
			//交易单号
			String transaction_id = resHandler.getParameter("transaction_id");
			//金额,以分为单位
			String total_fee = resHandler.getParameter("total_fee");
			//支付结果
			String pay_result = resHandler.getParameter("pay_result");
			if( "0".equals(pay_result)){
				Log.info("财付通转账---订单号："+transaction_id);
				Log.info("财付通转账---支付结果:"+pay_result);
				Log.info("财付通转账---:"+request.getParameter("bargainor_id"));
				//处理业务开始
				//判断订单号是否已经存在而且TX状态是否为0
				String sql="select * from wht_tenpaytransferlog where transaction_id=? and  tencentflag=?";
                Object[] params={transaction_id,"0"};
                //如果存在则直接返回不做处理
                if(getRecord(sql,params,"1")){
                	String url = TenpayConfigParser.getConfig().getShow_url();
                	resHandler.doShow(url);
                	return null;
                }else{
        			Object[] params1={"0",TenpayUtil.getCurrTime(),transaction_id};
        			String sql1 ="update wht_tenpaytransferlog set tencentflag=?,txstatechg=? where transaction_id=?";
        			getRecord( sql1,params1,"2");
    				String url = TenpayConfigParser.getConfig().getShow_url();
    				Log.info("财付通转账---show_url为：----->"+url);
        			
					new ToScpMsg(transaction_id,request).start();
					//调用doShow, 打印meta值跟js代码,告诉财付通处理成功,并在用户浏览器显示$show页面.
        			resHandler.doShow(url);
        			return null;
                }
			}else {
				 return new ActionForward("/wlttencent/tenpaytransfererror.jsp");
			}
		} else { 
			return new ActionForward("/wlttencent/tenpaytransfererror.jsp");
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
				List list =dbService.getStringList(sql, params) ;
				if(list.size()!=0){
					flag=true;
					Log.info("财付通转账---收到重复消息不做处理");
				}
				else{
					flag=false;
					Log.info("财付通转账---没查到记录...");
				}
				}
				else{
					dbService.setAutoCommit(false);
					int i=0;
					i=dbService.update(sql, params);
					dbService.commit();
					if(i!=0)
						flag=true;
				}	
		} catch (SQLException e) {
			dbService.rollback();
			e.printStackTrace();
		}
		finally{
			dbService.close();
		}
		return flag;
	}
	
	/**
	 * 内部线程类
	 *
	 */
	class ToScpMsg extends Thread{
		/**
		 * 
		 */
		private String transaction_id;
		private HttpServletRequest request;
		
		/**
		 * @param transaction_id
		 * @param request
		 */
		public ToScpMsg(String transaction_id,HttpServletRequest request) {
			this.transaction_id=transaction_id;
			this.request=request;
		}
		
		/**
		 * 
		 */
		public void run() {
			SendMsgScpBean toScp =new SendMsgScpBean(this.transaction_id);	
            toScp.run(SCPConstants.CFTTRADENO);
		}
	}
}
