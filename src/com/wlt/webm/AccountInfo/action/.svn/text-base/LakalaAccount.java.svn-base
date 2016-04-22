package com.wlt.webm.AccountInfo.action;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.wlt.webm.AccountInfo.LakalaAccountServices;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.message.PortalSend;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.util.Tools;

/**
 * @author adminA
 *
 */
public class LakalaAccount extends DispatchAction {
	
	/**
	 * 拉卡拉 提现
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward AccountInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
		{
//		//禁止T+0加款
//		if(true){
//			request.setAttribute("mess", "禁止手动T+0加款!");
//			return new ActionForward("/AccountInfo/lakalaList.jsp");
//		}
		//T+0加款
		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
		if(null==userSession || "".equals(userSession))
		{
			request.setAttribute("mess", "登录超时,请重新登录");
			return new ActionForward("/rights/wltlogin.jsp"); 
		}
		if(userSession.getUserno()==null || "".equals(userSession.getUserno()))
		{
			request.setAttribute("mess", "登录超时,请重新登录");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		request.setAttribute("indate",request.getParameter("indate"));
		request.setAttribute("endDate",request.getParameter("endDate"));
		
		String termId=request.getParameter("termId");
		if(termId==null || "".equals(termId))
		{
			request.setAttribute("mess", "系统异常，此次操作为不正当操作!");
			return new ActionForward("/AccountInfo/lakalaList.jsp");
		}
		request.setAttribute("termId", termId);
		
		//绑定状态
		String isbindingL=request.getParameter("isbinding");
		if(isbindingL==null || "".equals(isbindingL))
		{
			request.setAttribute("mess", "绑定状态,此次操作为不正当操作");
			return new ActionForward("/AccountInfo/lakalaListTwo.jsp");
		}
		if("0".equals(isbindingL))
		{
			request.setAttribute("mess", "拉卡拉终端编号在万汇通平台未绑定,此次操作为不正当操作");
			return new ActionForward("/AccountInfo/lakalaListTwo.jsp");
		}
		
		//拉卡拉平台交易状态
		String orderSta=request.getParameter("orderSta");
		if(!"00".equals(orderSta))
		{
			request.setAttribute("mess", "拉卡拉平台交易失败,此次操作为不正当操作");
			return new ActionForward("/AccountInfo/lakalaListTwo.jsp");
		}
		
		//万汇通平台交易状态
		String stateL=request.getParameter("state");
		if("1".equals(stateL))
		{
			request.setAttribute("mess", "已加款,此条为重复操作,此次操作为不正当操作!!");
			return new ActionForward("/AccountInfo/lakalaListTwo.jsp");
		}
		if("2".equals(stateL))
		{
			request.setAttribute("mess", "提现正在处理中，请稍后，此次操作为不正当操作!!");
			return new ActionForward("/AccountInfo/lakalaListTwo.jsp");
		}
		if("3".equals(stateL))
		{
			request.setAttribute("mess", "订单异常中，请联系客服,此次操作为不正当操作!(处理中)");
			return new ActionForward("/AccountInfo/lakalaListTwo.jsp");
		}
		if("4".equals(stateL))
		{
			request.setAttribute("mess", "订单异常中，请联系客服,此次操作为不正当操作!(异常)");
			return new ActionForward("/AccountInfo/lakalaListTwo.jsp");
		}
		if(!"0".equals(stateL))
		{
			request.setAttribute("mess", "系统异常，此次操作为不正当操作!");
			return new ActionForward("/AccountInfo/lakalaListTwo.jsp");
		}
		
		String tradetime=request.getParameter("tradetime");
		String totalfee=request.getParameter("totalfee");
		String userno=request.getParameter("userno");
		String facct=request.getParameter("facct");
		String refNumber=request.getParameter("refNumber");
		String id=request.getParameter("id");
		String amount=request.getParameter("amount");
		
		if(totalfee==null || "".equals(totalfee) ||
				userno==null || "".equals(userno) ||
					facct==null || "".equals(facct) ||
						refNumber==null || "".equals(refNumber) ||
							id==null || "".equals(id) ||
								amount==null || "".equals(amount) ||
									tradetime==null || "".equals(tradetime))
		{
			request.setAttribute("mess", "系统异常，此次操作为不正当操作!");
			return new ActionForward("/AccountInfo/lakalaListTwo.jsp");
		}
		String time=Tools.getNow3();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		
		//判断当前系统时间 时间大于23点，如果大于23点，取今天23点到明天23点钟的数据，否者，取昨天23点到今天23点的数据
		long d1 =df.parse(time).getTime();//当前系统时间
		long d2 =df.parse(Tools.getNow4()+"230000").getTime(); //今天23点
		long nextdate=df.parse(Tools.getNextDate().replace("-","")+"2300000").getTime();//明天23点
		long orderTime=df.parse(tradetime).getTime();//订单时间
		if(d1>d2 && d1<nextdate) //当前系统时间在今天23到明天23点之间
		{
			if(orderTime>=d2 && orderTime<d1) //订单在今天23和明天23点之间
			{
				//可以交易
			}
			else
			{
				request.setAttribute("mess", "此条订单是不可以交易,当前系统时间大于今天23点,只能提取今天23点到明天23的订单!");
				return new ActionForward("/AccountInfo/lakalaListTwo.jsp");
			}
		}
		else //当前时间小于今天23点
		{
			String zDate=Tools.getlastDate().replace("-","")+"230000";//昨天23点
			long d3=df.parse(zDate).getTime();//昨天23点
			if(orderTime>=d3 && orderTime<d2) //订单在昨天23点到今天点之间
			{
				//可以交易
			}
			else
			{
				request.setAttribute("mess", "此条订单是不可以交易,当前系统时间小于今天23点,只能提取昨天23点到今天23点的订单!");
				return new ActionForward("/AccountInfo/lakalaListTwo.jsp");
			}
		}
		//T+0默认手续费费用
		String handCharge=(Float.parseFloat(amount)*0.0005)+"";
		if((Float.parseFloat(amount)/1000)<10)
		{
//			request.setAttribute("mess", "此条订单的交易金额小于10元,不能提现!");
//			return new ActionForward("/AccountInfo/lakalaListTwo.jsp");
			handCharge="100";//小于10元，默认1毛手续费
		}
		String orderNumber=time+"lkl"+((int)(Math.random()*10000)+10000); //生成订单号
		 DBService db =null;
		 int bools=-1;
	        try {
	        	db=new DBService();
	            String sql=" SELECT state FROM wht_lakal_record WHERE id='"+id+"'";
	            String bool=db.getString(sql,null);
	            if(!"0".equals(bool))
	            {
	    			request.setAttribute("mess", "系统异常，此次操作为不正当操作!");
	    			return new ActionForward("/AccountInfo/lakalaListTwo.jsp");
	            }
	            String updateseql=" UPDATE  wht_lakal_record SET state=2,typeT='T+0' WHERE id='"+id+"'";
	            db.update(updateseql,null);
	            if(null!=db)
	            {
	            	db.close();
	            }
	            String tradeNo="10004"; //  转款的交易类型
	            String seqNo=orderNumber;//流水号
	            TradeMsg msg=new TradeMsg();
	    		msg.setSeqNo(seqNo); 
	    		msg.setFlag("0");//有内容
	    		msg.setMsgFrom2("0103");
	    		msg.setServNo("00");
	    		msg.setTradeNo(tradeNo);
	    		Map<String, String> content =new HashMap<String, String>();
	    		content.put("totalfee",totalfee);
	    		content.put("userno",userno);
	    		content.put("handCharge",handCharge);
	    		content.put("facct",facct);
	    		content.put("transaction_id",refNumber);
	    		content.put("id",id);
	    		content.put("time",time);
	    		msg.setContent(content);
	    		Log.info("返销所需订单号:"+seqNo);
	    		if(!PortalSend.getInstance().sendmsg(msg)){
	    			bools=-1;
	    			Log.info("拉卡拉 提现 消息发送失败，，，，");
	    			request.setAttribute("mess", "系统异常");
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
	    			bools=-2;
	    			Log.info("拉卡拉 提现 请求超时，，，，");
	    			request.setAttribute("mess", "异常请联系客服(处理中)");
	    		}
	    		String code1=rsMsg.getRsCode();
	    		if("000".equals(code1))
	    		{
	    			bools=0;
	    			Log.info("拉卡拉 提现 成功，，，，");
	    			request.setAttribute("mess", "提现成功");
	    		}
	    		else
	    		{
	    			bools=-1;
	    			Log.info("拉卡拉 提现 处理失败，，，，");
	    			request.setAttribute("mess", "提现失败");
	    		}
	        } catch (Exception ex) {
	        	bools=-1;
	        	Log.info("拉卡拉 提现 处理 异常，当失败处理，，，，");
	        	request.setAttribute("mess", "提现失败,系统异常");
	        } finally {
	        	if(null!=db)
	        	{
	        		db.close();
	        	}
	        }
	        
	        DBService dbhiber=null;
	        try{
	        	dbhiber = new DBService();
		        if(bools==-1)
	    		{
		        	dbhiber.update("UPDATE  wht_lakal_record SET state=0,typeT='' WHERE id='"+id+"'",null);
		        	Log.info("拉卡拉 交易结束，，，，bools=-1");
	    		}
		        if(bools==-2)
		        {
		        	dbhiber.update("UPDATE  wht_lakal_record SET state=3,typeT='T+0' WHERE id='"+id+"'",null);
		        	Log.info("拉卡拉 交易结束，，，，bools=-2");
		        }
			} catch (Exception ex) {
	        	Log.info("拉卡拉 回掉更新状态异常,,,,");
	        	request.setAttribute("mess", "提现失败,系统异常");
	        } finally {
	        	if(null!=dbhiber)
	        	{
	        		dbhiber.close();
	        	}
	        }
		return new ActionForward("/AccountInfo/lakalaListTwo.jsp"); // 返回的输出页面
	}
	
	
	
	/**
	 * 拉卡拉绑定 号码
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward AccountInfoList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
		{
			String userlogin=request.getParameter("userlogin");
			String userno=request.getParameter("userno");
			
			LakalaAccountServices acc=new LakalaAccountServices();
			
			int index=1;
			int lastIndex=1;
		    int pagesize=20;
			
			if(request.getParameter("index")!=null && !"".equals(request.getParameter("index")))
			{
				index=Integer.parseInt(request.getParameter("index"));
			}
			if(index<=0)
				index=1;
			int count=acc.getAccountVerifyCount(userlogin,userno);
			if(count>0)
				lastIndex=count%pagesize==0?count/pagesize:count/pagesize+1;
			
			if(index>=lastIndex)
				index=lastIndex;
			
			List<Object[]> arrList=acc.showAccountVerify(userlogin,userno,index,pagesize);
			request.setAttribute("arrList",arrList);
			request.setAttribute("userlogin", userlogin);
			request.setAttribute("userno", userno);
			request.setAttribute("index",index);
			request.setAttribute("lastIndex",lastIndex);
			return new ActionForward("/rights/lakalaList.jsp");
		}
}
