package com.wlt.webm.business.bean.sikong;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.db.DBService;
import com.wlt.webm.tool.Tools;

public class SiKongBack  extends DispatchAction{
	/**
	 * 思空流量回调
	 * @param maping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws 
	 */
	public ActionForward flow_Back(ActionMapping maping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		Log.info("思空批量订单回调,,,");
		StringBuffer buf = new StringBuffer();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			String lines;
			buf.delete(0, buf.length());
			while ((lines = reader.readLine()) != null) {
				buf.append(lines);
			}
			Log.info("思空批量订单回调,,,回调json字符串:"+buf.toString());
			if(buf.toString()==null || "".equals(buf.toString())){
				return null;
			}
			List<Result> arry=JSON.parseArray(buf.toString(),Result.class);
			if(arry!=null && arry.size()>0){
				new BackTrhead(arry).start();
				PrintWriter out=response.getWriter();
				out.print("0000");
				out.flush();
				out.close();
				Log.info("思空批量订单回调,,返回0000");
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	
	class BackTrhead extends Thread{
		private List<Result> arryList=null;
		
		private BackTrhead(List<Result> r){
			arryList=r;
		}
		
		public void run() {
			String time=Tools.getNow();
			String orderTableName = "wht_orderform_" + time.substring(2, 6);
			String acctTbaleName = "wht_acctbill_" + time.substring(2, 6);
			Log.info("思空批量订单回调,,线程处理批量回调");
			for(Result bean:arryList){
				DBService db=null;
				try {
					db=new DBService();
					String[] strs=db.getStringArray("select o.state,o.userno,o.fundacct,o.tradefee from "+orderTableName+" o where o.tradeserial='"+bean.getMsgId()+"'");
					String[] ars=db.getStringArray("select ac.tradetype from "+acctTbaleName+" ac where ac.tradeserial='"+bean.getMsgId()+"'");
					if(strs==null || strs.length<=0){
						//无此订单
						Log.info("思空批量订单回调,,,无对应的订单,tradeserial:"+bean.getMsgId());
						return;
					}
					
					if("0".equals(strs[0])||"1".equals(strs[0])){
						//重复回调
						Log.info("思空批量订单回调,,,此订单重复回调,tradeserial:"+bean.getMsgId());
						return ;
					}
					
					//成功
					if("0".equals(bean.getErr())){
						db.update("update " + orderTableName+" set state=0 where tradeserial='"+bean.getMsgId()+"'");
						Log.info("思空批量订单回调,,,修改订单状态为成功,tradeserial:"+bean.getMsgId());
					}else if("45".equals(bean.getErr())){ //失败
						Log.info("思空批量订单回调,,,订单失败,调用退费方法,tradeserial:"+bean.getMsgId());
						new BiProd().returnDeal(time,"-1", bean.getMsgId(), strs[1], "", strs[2].substring(0,strs[2].length()-2), "1",
								"", "", strs[3], "", Integer.parseInt(ars[0]));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					if(db!=null){
						db.close();
						db=null;
					}
				}
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String json="["+
		    "{"+
		    "    'mobile': '15101034188',"+
		    "    'userId': 'zjdx',"+
		    "    'msgId': '201401025',"+
		    "    'err': '1',"+
		    "    'fail_describe': 'black_mobile',"+
		    "    'report_time': '2014-01-01 15:15:18'"+
		    "},"+
		    "{"+
		    "    'mobile': '15200001111',"+
		    "   'userId': 'zjdx',"+
		    "    'msgId': '201401025',"+
		    "    'err': '0',"+
		    "    'fail_describe': '订购成功',"+
		    "    'report_time': '2014-01-01 15:15:18'"+
		    "}"+
		"]";
//		net.sf.json.JSONArray jsonarray = net.sf.json.JSONArray.fromObject(json);  
//		 System.out.println(jsonarray);  
//		 List list = (List)net.sf.json.JSONArray.toCollection(jsonarray, Result.class); 
		List<Result> arry=JSON.parseArray(json,Result.class);
		System.out.println();

	}
}
