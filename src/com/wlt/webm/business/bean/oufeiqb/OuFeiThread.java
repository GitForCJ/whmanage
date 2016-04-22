package com.wlt.webm.business.bean.oufeiqb;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import javax.xml.rpc.ServiceException;

import org.apache.struts.action.ActionForward;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.gm.bean.GuanMingBean;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.tool.Tools;

public class OuFeiThread extends TimerTask {
	/** 同步锁 **/
	private static Object lock = new Object();
	
	private String[] str=null;
	
	public OuFeiThread(String[] str){
		this.str=str;
	}
	
	public void run() {
		/**
		 * 23:50 ~ 00:20 不取单
		 */
		if (!Tools.validateTime()) {
			Log.info("殴飞Q币供货:23:50到00:20不允许交易");
			return ;
		}
		
		//查询订单
		GetOrderRespone orders=OuFeiHandle.QueryOrders(this.str);
		//订单查询QueryOrders reverse null
		if(orders==null){
			Log.info("Q币供货查询订单未通过,OuFeiHandle.QueryOrders 返回 null,不处理");
			return ;
		}
		//订单查询QueryOrders status not 0000 or data is null or data is '' or datalist is null
		if(!"0000".equals(orders.getStatus()) ||
				orders.getData()==null || 
				"".equals(orders.getData()) || 
				orders.getData().getDataList()==null ||
				orders.getData().getDataList().size()<=0){
			Log.info("Q币供货查询订单未通过,OuFeiHandle.QueryOrders 返回数据不完整 one,不处理");
			return ;
		}
		List<DateList> arryList=orders.getData().getDataList();
		for(DateList date:arryList)
		{
	//		DateList date=orders.getData().getDataList().get(0);
			//数据验证 数据不完整
			if(date.getOrder_id()==null || "".equals(date.getOrder_id()) ||
					date.getId()==null || "".equals(date.getId()) ||
					date.getOrder_ip()==null || "".equals(date.getOrder_ip()) ||
					date.getOrder_num()==null || "".equals(date.getOrder_num()) ||
					date.getRecharge_account()==null || "".equals(date.getRecharge_account())){
				Log.info("Q币供货查询订单未通过,OuFeiHandle.QueryOrders 返回数据不完整 two,不处理");
				return ;
			}
			
			boolean bool=false;
			//ip验证
			try {
				bool=com.wlt.webm.uictool.Tools.validateIp(date.getOrder_ip().trim());
			} catch (Exception e) {
				Log.error("Q币欧飞供货ip验证异常,ip："+date.getOrder_ip().trim()+",,,,,,"+e);
				bool=false;
			}
			if(!bool){
				Log.info("Q币供货,ip验证不通过,ip："+date.getOrder_ip().trim());
				return;
			}
	//		//三分钟，同一个ip,号码,金额 限制
	//		synchronized (lock) {
	//			if(MemcacheConfig.getInstance().get1(date.getOrder_ip().trim()+"_"+date.getRecharge_account().trim()+"_"+date.getOrder_num().trim())){
	//				Log.info(date.getOrder_ip().trim()+"_"+date.getRecharge_account()+"_"+date.getOrder_num()+" Q币欧飞供货三分钟内存在该交易,不处理");
	//				return ;
	//			}else{
	//				MemcacheConfig.getInstance().AddOuFei(date.getOrder_ip().trim()+"_"+date.getRecharge_account().trim()+"_"+date.getOrder_num().trim(), "");
	//			}
	//		}
			String loginfo="ip:"+date.getOrder_ip().trim()+",号码:"+date.getRecharge_account()+",数量:"+date.getOrder_num();
			
			Log.info("Q币供货查询订单通过,"+loginfo);
			
			//确认订单
			GetOrderRespone or=OuFeiHandle.Confirmation(date.getOrder_id(),date.getId());
			if(or==null){
				Log.info("Q币供货确认订单未通过,OuFeiHandle.Confirmation 返回null,不处理,,,"+loginfo);
				return ;
			}
			if(!"0000".equals(or.getStatus()) || !"true".equals(or.getData().getCanRechaege())){
				Log.info("Q币供货确认订单为通过, 返回订单状态:"+or.getStatus()+",订单是否允许下单:"+or.getData().getCanRechaege()+",,,"+loginfo);
				return ;
			}
			
			//本地充值
			GuanMingBean gmbean = new GuanMingBean();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("num", date.getOrder_num().trim());
			map.put("in_acct", date.getRecharge_account().trim());
			map.put("gameapi","0005");
			map.put("ip", date.getOrder_ip().trim());
			map.put("flag", "0");// 1为直营，0为非直营
			map.put("term_type","4");
			
			
			// 0 成功 1 失败 2处理中或异常 3重复交易 4账号余额不足 5佣金不存在
			Log.info("Q币供货确认订单通过,,进入业务处理方法,,,"+loginfo);
			int state = gmbean.interFaceServiceGameOrder(map,this.str[0],
					this.str[3],this.str[4],"0755", date.getOrder_id(), "1");
			Log.info("Q币供货确认订单通过,,进入业务处理方法,业务方法返回结果:"+state+",,,"+loginfo);
			String ss="";
			if(state==0){
				ss="4";//充值成功
			}else if(state==1 || state==3 || state==4 || state==5){
				ss="5";//充值失败
			}else{
				ss="6";//可疑订单
			}
			Log.info("Q币供货确认订单通过,,进入业务处理方法,返回欧飞订单结果:"+ss+",,,"+loginfo);
			//返回订单
			GetOrderRespone reverseMessage=OuFeiHandle.ReverseStr(date.getOrder_id(),date.getId(),ss,this.str);
			if(reverseMessage==null){
				Log.info("Q币供货充值结果返回未通过,,OuFeiHandle.ReverseStr 返回 null,,,"+loginfo);
				return ;
			}
			if(!"0000".equals(reverseMessage.getStatus()) || reverseMessage.getData()==null || !"true".equals(reverseMessage.getData().getIsok())){
				Log.info("Q币供货充值结果返回未通过,state:"+reverseMessage.getStatus()+",操作结果:"+reverseMessage.getData().getIsok()+",,,"+loginfo);
			}
			Log.info("Q币供货充值结果返回通过state:"+reverseMessage.getStatus()+",操作结果:"+reverseMessage.getData().getIsok()+",,,"+loginfo);
		}
	}
}
