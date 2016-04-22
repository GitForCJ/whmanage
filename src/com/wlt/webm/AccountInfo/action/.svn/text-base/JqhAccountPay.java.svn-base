package com.wlt.webm.AccountInfo.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import whmessgae.TradeMsg;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.AccountInfo.Jqh;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.message.PortalSend;
import com.wlt.webm.scputil.MD5Util;
import com.wlt.webm.util.MD5;
import com.wlt.webm.util.TenpayUtil;

/**
 * @author adminA
 *
 */
public class JqhAccountPay extends DispatchAction {

	/**
	 * 接收 九七惠 电信账单支付回调
	 * @param mapping 
	 * @param form 
	 * @param request 
	 * @param response 
	 * @return null
	 * @throws Exception 
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		String payOrder=request.getParameter("payOrder");//订单号
		String key=request.getParameter("key");// key 
		String status=request.getParameter("status");//返回状态 1 成功  0 失败
		
		if(payOrder==null || "".equals(payOrder) || key==null || "".equals(payOrder) || status==null || "".equals(status))
		{
			Log.info("九七惠 电信账单支付回调 参数不足，，，");
			return null;
		}
		
		Jqh j=new Jqh();
		PrintWriter out = response.getWriter();
		
		String signStr=j.userName+payOrder+j.sign;
		String mm=MD5.encode(signStr);
		if(mm.equals(key))//签名正确
		{
			Log.info("九七惠 电信账单支付回调 签名正确，，，");
			if(!"1".equals(status))
			{
				//交易失败
				Log.info("九七惠 电信账单支付回调 交易失败，，， ,订单号="+payOrder+"   ,处理结果状态 ="+status);
				DBService db = null;
		        try {
		        	db=new DBService();
		        	 String sql="SELECT recordStatus FROM wht_transactionRecord WHERE orderNumber='"+payOrder+"'";
		        	 String str=db.getString(sql,null);
		        	 if(str!=null && !"".equals(str))
		        	 {
		        		 if("0".equals(str))
		        		 {
		        			 String sqlss="UPDATE wht_transactionRecord SET recordStatus=2 WHERE orderNumber='"+payOrder+"'";
				        	 if(db.update(sqlss)>0)
				        	 {
				        		 Log.info("九七惠 电信账单支付回调 交易失败，，， 修改订单状态为失败完成，，");
				        	 }
				        	 else
				        	 {
				        		 Log.info("九七惠 电信账单支付回调 交易失败，，，订单不存在");
				        	 }
		        		 }
		        		 else
		        		 {
		        			 Log.info("九七惠 电信账单支付回调 交易失败，，,,订单状态修改过，此次回调属于重复回调");
		        		 }
		        	 }
		        	 else
		        	 {
		        		 Log.info("九七惠 电信账单支付回调 交易失败，，,本地订单不存在");
		        	 }
		        } 
		        catch (Exception ex) 
		        {
		        	 Log.error("九七惠 电信账单支付回调 交易失败，，， 修改订单状态异常，，"+ex);
		        } 
		        finally 
		        {
		        	if(db!=null)
		        		db.close();
		        }
				out.println("Success");
				out.flush();
				out.close();
			}
			else
			{
				 Log.info("九七惠 电信账单支付回调 状态成功，开始处理，，  ,订单号="+payOrder+"   ,处理结果状态 ="+status);
				DBService db = null;
		        try {
		        	db=new DBService();
		        	 String sql="SELECT recordStatus,userNumber,recordMoney FROM wht_transactionRecord WHERE orderNumber='"+payOrder+"'";
		        	 List arrList=db.getList(sql);
		        	 if(arrList.size()>0)
		        	 {
		        		 Object[] obj=(Object[])arrList.get(0);
		        		 if("0".equals(obj[0]))
		        		 {
			        		 db.update("UPDATE wht_transactionRecord SET recordStatus=3 WHERE orderNumber='"+payOrder+"'");
			 				 new TyPortThread(payOrder,obj[2].toString(),obj[1].toString()).start();
		        		 }
		        		 else
		        		 {
		        			 Log.info("九七惠 电信账单支付回调成功，，，状态已修改过，此次回调为重复回调");
		        		 }
		        	 }
		        	 else
		        	 {
		        		 Log.info("九七惠 电信账单支付回调成功，，，本地订单不存在");
		        	 }
		        } 
		        catch (Exception ex) 
		        {
		        	 Log.error("九七惠 电信账单支付回调成功，，，本地处理异常"+ex);
		        } 
		        finally 
		        {
		        	if(db!=null)
		        		db.close();
		        }
		        out.println("Success");
				out.flush();
				out.close();
			}
		}
		else
		{
			//签名异常，信息被篡改
			Log.info("签名异常，信息被篡改，，，订单号="+payOrder+"   ,处理结果状态 ="+status);
		}
		return null;
	}
	
	
	/**
	 * 内部线程类
	 */
	class TyPortThread extends Thread{
		private String ORDERSEQ;
		private String ORDERAMOUNT;
		private String userno;
		/**
		 * @param transaction_id
		 * @param request
		 */
		public TyPortThread(String ORDERSEQ,String ORDERAMOUNT,String userno) {
			this.ORDERSEQ=ORDERSEQ;
			this.ORDERAMOUNT=ORDERAMOUNT;
			this.userno=userno;
		}
		/* 
		 * 线程启动
		 */
		public void run() {
			//交易成功
			String tradeNo="10003"; //  签约交易类型
			String seqNo=ORDERSEQ;//用订单号，来做流水号 唯一
			TradeMsg msg=new TradeMsg();
	   		msg.setSeqNo(seqNo); 
	   		msg.setFlag("0");//有内容
	   		msg.setMsgFrom2("0103");
	   		msg.setServNo("00");
	   		msg.setTradeNo(tradeNo);
	   		
	   		Map<String, String> content =new HashMap<String, String>();
	   		content.put("transaction_id",ORDERSEQ);
	   		content.put("time",TenpayUtil.getCurrTime());
	   		content.put("total_fee",Float.parseFloat(ORDERAMOUNT)*10+"");
	   		content.put("handCharge",(Float.parseFloat(ORDERAMOUNT)*10)*0.002+"");//手续费
	   		content.put("userno",this.userno);
	           
	   		Log.info("返销所需订单号:"+seqNo);
	   		msg.setContent(content);
	
	   		if(!PortalSend.getInstance().sendmsg(msg)){
	   			//return 1;//发送消息失败,充值失败
	   			Log.info("发送消息失败,充值失败，，，");
	   			return ;
	   		}
	   		int k=0;
	   		TradeMsg rsMsg=null;
	   		while(k<180){
	   			k++;
	   			try {
	   				Thread.sleep(1000);
	   			}catch (InterruptedException e) {
	   				e.printStackTrace();
	   			}
	   			rsMsg=MemcacheConfig.getInstance().get(seqNo);
	   			if(null==rsMsg){
	   				continue;
	   			}else{
	   				MemcacheConfig.getInstance().delete(seqNo);
	   				break;
	   			}
	   		}
	   		if(null==rsMsg&&k>=150)
	   		{
	   			//return 2;//处理中
	   			Log.info("请求处理中");
	   			return ;
	   		}
	   		String code1=rsMsg.getRsCode();
	   		if("000".equals(code1))
	   		{
	   			//return 0;//成功
	   			Log.info("操作成功，，，");
	   			return;
	   		}
	   		else
	   		{
	   		    //return 3;//失败
	   			Log.info("请求处理中");
	   			return ;
	   		}
		}
	}
	
}
