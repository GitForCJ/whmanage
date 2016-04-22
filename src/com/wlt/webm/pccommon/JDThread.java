package com.wlt.webm.pccommon;

import java.util.ArrayList;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.action.Flows_Public_Method;
import com.wlt.webm.business.action.JdOrderBean;
import com.wlt.webm.business.action.Jd_Flows;
import com.wlt.webm.business.bean.beijingFlow.Flow;
import com.wlt.webm.business.bean.dhst.FlowsService;
import com.wlt.webm.business.bean.leliu.LeliuFlowCharge;
import com.wlt.webm.business.bean.lianlian.LianlianFlows;
import com.wlt.webm.business.bean.unicom3gquery.FillProductDetail;
import com.wlt.webm.business.bean.unicom3gquery.FillProductInfo;
import com.wlt.webm.business.bean.unicom3gquery.HttpFillOP;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.PortalSend;
import com.wlt.webm.tool.Tools;

public class JDThread extends Thread {
	

	/**
	 * 线程对象集合，存放 在使用的线程对象
	 */
	public static ArrayList thread_list=new ArrayList();
	
	/**
	 * 一个线程处理订单数量
	 */
	public static final int t_count=20;
	
	/**
	 * 处理京东订单，最大线程数
	 */
	public static final int t_size=50;
	
	/**
	 * 轮询查询订单充值结果，同一笔订单最大查询 次数
	 */
	public static final int query_max_count=30;
	
	/**
	 * 轮询查询订单充值结果，同一笔订单查询的最小间隔时间
	 */
	public static final int query_min_time=20*1000;
	
	public void run() {
		while(true){
			//是否压单  0不压单,,其他压单中
			if(Tools.FLOW_SWITCH_FLAG!=0){
				Log.info("京东流量订单压单中,,,");
				try { Thread.sleep(60*1000);} catch (Exception e) {
					Log.error("京东压单系统异常，，，ex:"+e);
				}
				continue;
			}
			
			if(thread_list.size()>=t_size){//如果当前在使用的线程数大于或等于自定义的最大线程数，则等待三秒，继续判断是否有空线程
				try { Thread.sleep(5*1000);} catch (Exception e) {}
				continue;
			}
			//从订单临时表中取一批数据,条数根据自定义 值 t_count,一次取一个线程需要处理的订单数量
			ArrayList<JdOrderBean> arry=JDThread.getOrders_del();
			if(arry==null || arry.size()<=0){//没有京东需要处理的订单信息
				try { Thread.sleep(5*1000);} catch (Exception e) {}
				continue;
			}else{//有京东订单信息
				Log.info("=========一批订单数量:"+arry.size()+",,当前线程集合中在使用线程数："+thread_list.size());
				if(arry.size()>0){
					//启动单独线程，处理一批订单数据
					Jd_Thread_Service tt=new Jd_Thread_Service(arry);
					tt.start();
				}
			}
		}
	}
	
	/**
	 * 获取一批订单信息信息
	 * @return ArrayList
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<JdOrderBean> getOrders_del(){
		DBService db=null;
		try {
			db = new DBService();
			ArrayList<String[]> arrys=(ArrayList<String[]>)db.getList("SELECT fillOrderNo,fillMobile,fillAmount,finTime,areaUsed,notifyUrl," +
					" mutCode,areaCode,costPrice,wh_createtime,wh_mutCode,userno,exp1,wh_order_num,sys_id,exp2 FROM jd_orders ORDER BY wh_createtime asc LIMIT 0,"+JDThread.t_count);
			if(arrys==null || arrys.size()<=0){
				return null;
			}
			Log.info("京东批量读取临时订单消息,条数:"+arrys.size());
			
			String buf="DELETE FROM jd_orders WHERE sys_id IN (";
			ArrayList<JdOrderBean> list=new ArrayList<JdOrderBean>();
			for(String[] s:arrys){
				JdOrderBean b=new JdOrderBean();
				b.setFillOrderNo(s[0]);
				b.setFillMobile(s[1]);
				b.setFillAmount(s[2]);
				b.setFinTime(s[3]);
				b.setAreaUsed(s[4]);
				b.setNotifyUrl(s[5]);
				b.setMutCode(s[6]);
				b.setAreaCode(s[7]);
				b.setCostPrice(s[8]);
				b.setOrdertime(s[9]);
				b.setWh_mutCode(s[10]);
				b.setUserno(s[11]);
				b.setInterid(s[12]);
				b.setWh_order_num(s[13]);
				b.setCode(s[15]);
				list.add(b);
				buf=buf+"'"+s[14]+"',";
			}
			buf=buf.substring(0,buf.length()-1);
			buf=buf+")";
			
			Log.info("京东批量删除读取后的临时订单,sql:"+buf);
			
			if(db.update(buf.toString())>0){
				Log.info("京东批量删除读取后的临时订单,删除成功,返回批量查询订单数据,sql:"+buf);
				return list;
			}
			return null;
		} catch (Exception e) {
			Log.error("京东批量读取,删除临时订单异常，，，ex:"+e);
		} finally {
			if (db != null) {
				db.close();
				db = null;
			}
		}
		return null;
	}
	
}

/**
 * 京东流量 多线程 业务处理
 * @author 1989
 */
class Jd_Thread_Service extends Thread{
	
	/**
	 * 每个线程分配的，一批订单数据集合
	 */
	private ArrayList<JdOrderBean> arrays=null;
	
	/**
	 * @param arrays
	 */
	@SuppressWarnings("unchecked")
	public Jd_Thread_Service(ArrayList arrays){
		this.arrays=arrays;
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		//将当前线程 添加到线程集合中
		JDThread.thread_list.add(this);
		
		//循环处理一批订单 充值
		int size=this.arrays.size();
		for(int c=0;c<size;c++){
			//每次循环都取第一笔订单，取完自定删除对象
			JdOrderBean bean=this.arrays.remove(0);
			Log.info("万恒订单号："+bean.getWh_order_num()+",,,京东订单号："+bean.getFillOrderNo()+",,,,开始发起充值处理,,,");
			
			
			String[] strs = bean.getInterid().split("#");//接口id#宽带公司地市
			StringBuffer buf=new StringBuffer();
			String rs=Flows_Public_Method.Request_Flows(bean.getFillAmount(),
					bean.getWh_order_num(), bean.getFillMobile(), bean.getWh_mutCode(), strs[0], strs[1],buf);
			bean.setBeifen_reesult(buf.toString());
			Flows_Public_Method.Result_Processing(rs, strs[0], bean, bean.getCode());
		}
		//当前线程一批订单处理完成，删除 线程集合中 当前线程对象，并记录一批订单处理时间
		JDThread.thread_list.remove(this);
	}
}