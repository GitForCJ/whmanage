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
import com.wlt.webm.business.bean.TyPortPayBean;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.message.PortalSend;
import com.wlt.webm.util.MD5;
import com.wlt.webm.util.TenpayUtil;

/**
 * @author adminA
 *
 */
public class TyPortInfo extends DispatchAction {

	/**
	 * 接收翼支付 网关平台接口  信息
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
		String UPTRANSEQ=request.getParameter("UPTRANSEQ");//交易流水号
		String TRANDATE=request.getParameter("TRANDATE");//交易日期
		String RETNCODE=request.getParameter("RETNCODE");//处理结果状态 
		String RETNINFO=request.getParameter("RETNINFO");//处理结果解释码
		//String ORDERREQTRANSEQ=request.getParameter("ORDERREQTRANSEQ");//订单请求交易流水号
		String ORDERSEQ=request.getParameter("ORDERSEQ");//订单号
		String ORDERAMOUNT=request.getParameter("ORDERAMOUNT");//订单总金额
		//String PRODUCTAMOUNT=request.getParameter("PRODUCTAMOUNT");//产品金额
		//String ATTACHAMOUNT=request.getParameter("ATTACHAMOUNT");//附加金额
		//String CURTYPE=request.getParameter("CURTYPE");//币种
		//String ENCODETYPE=request.getParameter("ENCODETYPE");//加密方式
		//String BANKID=request.getParameter("BANKID");//银行编码
		String SIGN=request.getParameter("SIGN");//数字签名
		if(UPTRANSEQ==null || TRANDATE==null || RETNCODE==null || RETNINFO==null || ORDERSEQ==null || ORDERAMOUNT==null || SIGN==null)
		{
			Log.info("回调参数不足，，，");
			return null;
		}
		
		TyPortPayBean ty=new TyPortPayBean();
		PrintWriter out = response.getWriter();
		
		String signStr="UPTRANSEQ="+UPTRANSEQ+"&MERCHANTID="+ty.getMERCHANTID()+"&ORDERID="+ORDERSEQ+"&PAYMENT="+ORDERAMOUNT+"&RETNCODE="+RETNCODE+"&RETNINFO="+RETNINFO+"&PAYDATE="+TRANDATE+"&KEY="+ty.getKEY();
		String mm=(MD5.encode(signStr)).toUpperCase();
		if(mm.equals(SIGN))//签名正确
		{
			Log.info("签名正确，，，");
			if(!"0000".equals(RETNCODE))
			{
				//交易失败
				Log.info("交易失败，，，交易流水号="+UPTRANSEQ+"  ,订单号="+ORDERSEQ+"   ,处理结果状态 ="+RETNCODE);
				DBService db = null;
		        try {
		        	db=new DBService();
		        	 String sql="UPDATE wht_transactionRecord SET recordStatus=2 WHERE orderNumber='"+ORDERSEQ+"'";
		        	 if(db.update(sql)>0)
		        	 {
		        		 Log.info("交易失败，，， 修改订单状态为失败完成，，");
		        	 }
		        	 else
		        	 {
		        		 Log.info("交易失败，，，订单不存在");
		        	 }
		        } 
		        catch (Exception ex) 
		        {
		        	 Log.error("交易失败，，， 修改订单状态异常，，"+ex);
		        } 
		        finally 
		        {
		        	if(db!=null)
		        		db.close();
		        }
				out.println("UPTRANSEQ_"+UPTRANSEQ);
				out.flush();
				out.close();
			}
			else
			{
				 Log.info("翼支付交易成功,,本地订单状态已改为处理中，，交易流水号="+UPTRANSEQ+"  ,订单号="+ORDERSEQ+"   ,处理结果状态 ="+RETNCODE);
				DBService db = null;
		        try {
		        	db=new DBService();
		        	 String sql="SELECT recordStatus,userNumber FROM wht_transactionRecord WHERE orderNumber='"+ORDERSEQ+"'";
		        	 List arrList=db.getList(sql);
		        	 if(arrList.size()>0)
		        	 {
		        		 Object[] obj=(Object[])arrList.get(0);
		        		 if("0".equals(obj[0]))
		        		 {
			        		 db.update("UPDATE wht_transactionRecord SET recordStatus=3 WHERE orderNumber='"+ORDERSEQ+"'");
			 				 new TyPortThread(ORDERSEQ,ORDERAMOUNT,obj[1].toString()).start();
		        		 }
		        		 else
		        		 {
		        			 Log.info("翼支付交易成功，，，状态已修改过，此次回调为重复回调");
		        		 }
		        	 }
		        	 else
		        	 {
		        		 Log.info("翼支付交易成功，，，本地订单不存在");
		        	 }
		        } 
		        catch (Exception ex) 
		        {
		        	 Log.error("翼支付交易成功，，，本地订单不存在"+ex);
		        } 
		        finally 
		        {
		        	if(db!=null)
		        		db.close();
		        }
				out.println("UPTRANSEQ_"+UPTRANSEQ);
				out.flush();
				out.close();
			}
		}
		else
		{
			//签名异常，信息被篡改
			Log.info("签名异常，信息被篡改，，，交易流水号="+UPTRANSEQ+"  ,订单号="+ORDERSEQ+"   ,处理结果状态 ="+RETNCODE);
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
			String tradeNo="10002"; //  签约交易类型
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
	   		content.put("handCharge","0");//手续费  (Float.parseFloat(ORDERAMOUNT)*10)*0.002+""
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
	   			//return 2;//超时
	   			Log.info("请求超时,充值失败，，，");
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
	   			Log.info("请求超时,充值失败，，，");
	   			return ;
	   		}
		}
	}
	
}
