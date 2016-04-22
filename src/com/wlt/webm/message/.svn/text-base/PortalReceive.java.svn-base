package com.wlt.webm.message;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;

import whmessgae.TradeMsg;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.action.Flows_Public_Method;
import com.wlt.webm.business.action.JdOrderBean;
import com.wlt.webm.business.action.Jd_Flows;
import com.wlt.webm.business.action.TencentOrdersBean;
import com.wlt.webm.business.action.Tencent_Flows;
import com.wlt.webm.business.bean.beijingFlow.Flow;
import com.wlt.webm.business.bean.unicom3gquery.FillProductInfo;
import com.wlt.webm.business.bean.unicom3gquery.HttpFillOP;
import com.wlt.webm.business.form.TPForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.pccommon.JDThread;
import com.wlt.webm.pccommon.TencentThread;
import com.wlt.webm.util.Tools;

/**
 * portal接收business消息类
 * @author Administrator
 *
 */
public class PortalReceive{

	// ConnectionFactory ：连接工厂，JMS 用它创建连接
	ConnectionFactory connectionFactory;
	// Connection ：JMS 客户端到JMS Provider 的连接
	Connection connection = null;
	// Session： 一个发送或接收消息的线程
	Session session;
	// Destination ：消息的目的地;消息发送给谁.
	Destination destination;
	// 消费者，消息接收者
	MessageConsumer consumer;
	
	Session sessionSmp;
	// Destination ：消息的目的地;消息发送给谁.
	Destination destinationSmp;
	// 消费者，消息接收者
	MessageConsumer consumerSmp;
	
	
	Session sessionSmp2;
	// Destination ：消息的目的地;消息发送给谁.
	Destination destinationSmp2;
	// 消费者，消息接收者
	MessageConsumer consumerSmp2;
	
	
	Session sessionSmp3;
	// Destination ：消息的目的地;消息发送给谁.
	Destination destinationSmp3;
	// 消费者，消息接收者
	MessageConsumer consumerSmp3;
	
	
	Session sessionSmp4;
	// Destination ：消息的目的地;消息发送给谁.
	Destination destinationSmp4;
	// 消费者，消息接收者
	MessageConsumer consumerSmp4;
	
	
	Session sessionSmp5;
	// Destination ：消息的目的地;消息发送给谁.
	Destination destinationSmp5;
	// 消费者，消息接收者
	MessageConsumer consumerSmp5;
	/**
	 * 
	 */
	public PortalReceive() {
		Log.info("启动portal监听...");
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
		try {
			// 构造从工厂得到连接对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			// 获取操作连接
			session = connection.createSession(Boolean.FALSE,
					Session.AUTO_ACKNOWLEDGE);
			// 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
			destination = session.createQueue("B2P");
			consumer = session.createConsumer(destination);
//			while (true) {
//				// 设置接收者接收消息的时间，为了便于测试，这里定为100s
//				TextMessage message = (TextMessage) consumer.receive(10000);
//				if (null != message) {
//					System.out.println("收到消息" + message.getText());
//				} else {
//					break;
//				}
//			}
			consumer.setMessageListener(new MessageListener() {
                public void onMessage(Message arg0) {
                    try {
	                	if(null!=arg0){
	                		if(arg0 instanceof ObjectMessage){
	                        	TradeMsg msg=(TradeMsg)((ObjectMessage)arg0).getObject();
	//                         session.commit();
	                        	Log.info("portal接收消息: "+msg.toString());
	                        	MemcacheConfig.getInstance().add(msg.getSeqNo(), msg);
	                        	System.out.println(msg.getSeqNo()+"===="+MemcacheConfig.getInstance().get(msg.getSeqNo()));
	                        }
	                	 }
						}catch (Exception e) {
							Log.error("portal接收受消息异常:"+e.toString());
							e.printStackTrace();
						}
				}
			});
			//====
			sessionSmp = connection.createSession(Boolean.FALSE,
					Session.AUTO_ACKNOWLEDGE);
			// 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
			destinationSmp = sessionSmp.createQueue("B2M");
			consumerSmp = sessionSmp.createConsumer(destinationSmp);
			consumerSmp.setMessageListener(new MessageListener() {
                public void onMessage(Message arg0) {
                    try {
	                	if(null!=arg0){
	                		if(arg0 instanceof ObjectMessage){
	                        	TradeMsg msg=(TradeMsg)((ObjectMessage)arg0).getObject();
	//                            session.commit();
	                        	Log.info("smp接收消息: "+msg.toString());
	                        	MemcacheConfig.getInstance().add(msg.getSeqNo(), msg);
	                        }
	                	 }
					}catch (Exception e) {
						Log.error("portal接收受消息异常:"+e.toString());
						e.printStackTrace();
					}
                }
			});
			
		//====//腾讯订单充值队列
		sessionSmp2 = connection.createSession(Boolean.FALSE,
				Session.AUTO_ACKNOWLEDGE);
		// 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
		destinationSmp2 = sessionSmp2.createQueue("T2W");
		consumerSmp2 = sessionSmp2.createConsumer(destinationSmp2);
		consumerSmp2.setMessageListener(new MessageListener() {
            public void onMessage(Message arg0) {
                try {
                	if(null!=arg0){
	            		if(arg0 instanceof ObjectMessage){
	            			TencentOrdersBean bean=(TencentOrdersBean)((ObjectMessage)arg0).getObject();
	            			DBService db=null;
	        				try {
	        					db = new DBService();
	        					String time=Tools.getNow3();
	        					bean.setOrdertimee(time);
	        					List<String[]> tencent_arrs=db.getList("SELECT tradeserial,state FROM wht_orderform_" + time.substring(2, 6)+" WHERE writecheck='"+bean.getPaipai_dealid()+"'");
	        					if(tencent_arrs!=null && tencent_arrs.size()>0){
	        						String[] tencent_strs=tencent_arrs.get(0);
	        						bean.setSeqNo1(tencent_strs[0]);
	        						Log.info("腾讯流量,重复订单号,腾讯订单号:"+bean.getPaipai_dealid()+",,,查询的订单信息,,"+Arrays.toString(tencent_strs)+",,订单状态:"+tencent_strs[1]);
	        						if("0".equals(tencent_strs[1])){
	        							Log.info("腾讯流量,重复订单,订单状态,成功,调用通知成功接口,,腾讯订单号:"+bean.getPaipai_dealid()+",,,seqNo1:"+tencent_strs[0]);
	        							Flows_Public_Method.Is_Back(null, "0",HttpFillOP.tencent_code,bean,null);
	        						}
	        						return ;
	        					}
	        					//判断地市
	        					String[] strings=db.getStringArray("SELECT (CASE WHEN a.phone_type=0 THEN '0011' WHEN a.phone_type=1 THEN '0010' WHEN a.phone_type=2 THEN '0009' END) AS yun,b.sa_id,c.interid "+
										" FROM sys_phone_area a RIGHT JOIN sys_area b ON a.province_code=b.sa_id LEFT JOIN  sys_flowinterfaceMaping c ON b.sa_id=c.pid AND a.phone_type=c.type "+
										" WHERE a.phone_num='"+bean.getCustomer().substring(0, 7)+"'");
	        					if(strings==null || strings.length<=0){
	        						Log.info("腾讯流量充值,获取地市错误,调用失败通知接口,腾讯订单号:"+bean.getPaipai_dealid()+",,,seqNo1:"+bean.getSeqNo1());
	        						Flows_Public_Method.Is_Back(null, "1",HttpFillOP.tencent_code,bean,null);
	        						return ;
	        					}
	        					
	        					//队列中取出的订单信息入临时订单表库，防止队列中数据丢失，前提是，取订单队列的熟读要比存入订单的速度要快，否则订单列队还是会有很多订单
	        					StringBuffer buf=new StringBuffer("INSERT INTO tencent_orders(paipai_dealid,cardid," +
	        							"num,customer,pay,price,deal_time,seqNo1,userno,createtime,operator,only,exp1,exp2) VALUES(");
	        					buf.append("'"+bean.getPaipai_dealid()+"',");
	        					buf.append("'"+bean.getCardid()+"',");
	        					buf.append("'"+bean.getNum()+"',");
	        					buf.append("'"+bean.getCustomer()+"',");
	        					buf.append("'"+bean.getPay()+"',");
	        					buf.append("'"+bean.getPrice()+"',");
	        					buf.append("'"+bean.getDeal_time()+"',");
	        					buf.append("'"+bean.getSeqNo1()+"',");
	        					buf.append("'"+bean.getUserno()+"',");
	        					buf.append("'"+time+"',");
	        					buf.append("'"+bean.getOperator()+"',");
	        					buf.append(System.currentTimeMillis()+",");
	        					buf.append("'"+strings[2]+"#"+strings[1]+"',");//接口id#号码归属地
	        					buf.append("'"+HttpFillOP.tencent_code +"'");
	        					buf.append(")");
	        					db.update(buf.toString());
	        					//订单信息入库
	        					TPForm tp=MemcacheConfig.getInstance().getTP(bean.getUserno());
	        					String writeOff = bean.getCardid().substring(6, bean.getCardid().length())+"mb";
	        					Object[] orderObj = { null, tp.getUser_site() + "", bean.getSeqNo1(),
	        							bean.getCustomer(), strings[2], strings[0], Double.parseDouble(bean.getPay()) * 10,
	        							Double.parseDouble(bean.getPay()) * 10, Tencent_Flows.fundacct, time, time, 4,
	        							writeOff, bean.getPaipai_dealid(), 3, bean.getUserno(), tp.getUser_login(),
	        							bean.getOperator(), strings[1],HttpFillOP.tencent_code};// 20
	        					db.logData(20, orderObj, "wht_orderform_" + time.substring(2, 6));
	        					
	        					Log.info("腾讯流量,读取列队订单信息,订单入库成功,腾讯订单号:"+bean.getPaipai_dealid()+",,,seqNo1:"+bean.getSeqNo1());
	        				} catch (Exception e) {
	        					Log.error("腾讯线程读取列队,记录临时订单,订单信息,腾讯订单号:"+bean.getPaipai_dealid()+",,,seqNo1:"+bean.getSeqNo1()+",,异常，，，ex:"+e);
	        				} finally {
	        					if (db != null) {
	        						db.close();
	        						db = null;
	        					}
	        				}
	                    }
                	}
				}catch (Exception e) {
					Log.error("portal接收受消息异常:"+e.toString());
					e.printStackTrace();
				}
			}
		});
		
		//===腾讯订单充值结果查询队列
		sessionSmp3 = connection.createSession(Boolean.FALSE,
				Session.AUTO_ACKNOWLEDGE);
		// 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
		destinationSmp3 = sessionSmp3.createQueue("W2L");
		consumerSmp3 = sessionSmp3.createConsumer(destinationSmp3);
		consumerSmp3.setMessageListener(new MessageListener() {
            public void onMessage(Message arg0) {
                try {
                	if(null!=arg0){
                		if(arg0 instanceof ObjectMessage){
                			TencentOrdersBean ors=(TencentOrdersBean)((ObjectMessage)arg0).getObject();
            				
            				//如果取出的订单 为 null or 取出的订单查询次数大于  自定义单笔订单 查询总的次数 ，则不在调用此订单充值状态查询
            				if(ors==null || ors.getQuery_count()>TencentThread.query_max_count){
            					Log.info("订单查询次数达到最大值,不再查询,订单号："+ors.getSeqNo1()+",,腾讯订单号："+ors.getPaipai_dealid());
            					return ;
            				}
            				
            				//如果 当前时间-此订单上次查询的时间<自定义的单笔订单最小查询空闲时间，则重新添加到查询订单队列尾部
            				long qtime=System.currentTimeMillis();
            				if(qtime-ors.getLast_time_query()<TencentThread.query_min_time){
            					try {Thread.sleep(3*1000); } catch (Exception e) { }
            					boolean bool=PortalSend.getInstance().Send_LianTong_Query_Orders(ors);
            					if(!bool){
            						Log.info("腾讯查询订单监听，卡单订单放入订单查询队列，失败,,腾讯订单号:"+ors.getPaipai_dealid()+",,,seqNo1:"+ors.getSeqNo1());
            					}
            					return ;
            				}else{
            					// 当前订单 查询次数 +1,查询时间更新为当前时间
            					ors.setQuery_count(ors.getQuery_count()+1);
            					ors.setLast_time_query(qtime);
            				}
            				String rs="";
            				if(HttpFillOP.FLOw1.equals(ors.getInterid())){
//            					rs=getRandomDigit();
            					rs=Flows_Public_Method.getQueryOrderFlows(ors.getSeqNo1());
            				}else if(HttpFillOP.FLOw2.equals(ors.getInterid())){
//            					rs=getRandomDigit();
            					rs=Flow.BeiFen_Query_Order(ors.getSeqNo1(),ors.getBeifen_reesult());
            				}else{
            					Log.info("流量查询队列,未知查询接口,return null");
            					return ;
            				}
            				
            				Log.info("流量查询队列,万恒订单号:"+ors.getSeqNo1()+",,查询结果:"+rs+",0成功 -1失败 其他处理中,查询订单调用接口:"+ors.getInterid());
            				
            				if("0".equals(rs)){
            					Flows_Public_Method.Is_Back(ors.getSeqNo1(), "0",ors.getCode(),null,null);
            				}else if("-1".equals(rs)){
            					Flows_Public_Method.Is_Back(ors.getSeqNo1(), "1",ors.getCode(),null,null);
            				}else{
            					PortalSend.getInstance().Send_LianTong_Query_Orders(ors);
            				}
                        }
                	 }
				}catch (Exception e) {
					Log.error("portal接收受消息异常:"+e.toString());
					e.printStackTrace();
				}
            }
		});
		
		//京东订单充值队列
		sessionSmp4 = connection.createSession(Boolean.FALSE,
				Session.AUTO_ACKNOWLEDGE);
		// 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
		destinationSmp4 = sessionSmp4.createQueue("W2J");
		consumerSmp4 = sessionSmp4.createConsumer(destinationSmp4);
		consumerSmp4.setMessageListener(new MessageListener() {
            public void onMessage(Message arg0) {
                try {
                	if(null!=arg0){
	            		if(arg0 instanceof ObjectMessage){
	            			JdOrderBean bean=(JdOrderBean)((ObjectMessage)arg0).getObject();
	            			DBService db=null;
	        				try {
	        					db = new DBService();
	        					String time=Tools.getNow3();
	        					bean.setOrdertime(time);
	        					List<String[]> jd_arrs=db.getList("SELECT 1 FROM wht_orderform_" + time.substring(2, 6)+" WHERE writecheck='"+bean.getFillOrderNo()+"'");
	        					if(jd_arrs!=null && jd_arrs.size()>0){
	        						//订单号重复,,直接调用http回调,这笔订单当做失败处理
	        						Flows_Public_Method.Is_Back(null, "1", HttpFillOP.jd_code, bean,null);//获取本地流量充值接口错误，直接调用http回调，这笔订单当做失败处理
	        						return ;
	        					}
	        					
	        					//判断地市
	        					String[] strings=db.getStringArray("SELECT (CASE WHEN "+bean.getMutCode()+"=0 THEN '0011' WHEN "+bean.getMutCode()+"=1 THEN '0010' WHEN "+bean.getMutCode()+"=2 THEN '0009' END) AS yun,interid FROM sys_flowinterfaceMaping WHERE pid='"+bean.getAreaCode()+"' AND TYPE='"+bean.getMutCode()+"'");
	        					if(strings==null || strings.length<=0 ||  strings[1]==null || "".equals(strings[1])){
	        						Log.info("京东流量充值,获取地市错误,调用失败通知接口,京东订单号:"+bean.getFillOrderNo()+",,,万恒订单号:"+bean.getWh_order_num());
	        						Flows_Public_Method.Is_Back(null, "1", HttpFillOP.jd_code, bean,null);//获取本地流量充值接口错误，直接调用http回调，这笔订单当做失败处理
	        						return ;
	        					}
	        					
	        					//队列中取出的订单信息入临时订单表库，防止队列中数据丢失，前提是，取订单队列的熟读要比存入订单的速度要快，否则订单列队还是会有很多订单
	        					StringBuffer buf=new StringBuffer("INSERT INTO jd_orders(fillOrderNo,fillMobile,fillAmount,finTime,areaUsed,notifyUrl," +
	        							"mutCode,areaCode,costPrice,wh_createtime,wh_mutCode,wh_areaCode,userno,exp1,wh_order_num,exp2) VALUES(");
	        					buf.append("'"+bean.getFillOrderNo()+"',");
	        					buf.append("'"+bean.getFillMobile()+"',");
	        					buf.append("'"+bean.getFillAmount()+"',");
	        					buf.append("'"+bean.getFinTime()+"',");
	        					buf.append("'"+bean.getAreaUsed()+"',");
	        					buf.append("'"+bean.getNotifyUrl()+"',");
	        					buf.append("'"+bean.getMutCode()+"',");
	        					buf.append("'"+bean.getAreaCode()+"',");
	        					buf.append("'"+bean.getCostPrice()+"',");
	        					buf.append("'"+Tools.getNow3()+"',");
	        					buf.append("'"+bean.getMutCode()+"',");
	        					buf.append("'"+bean.getAreaCode()+"',");
	        					buf.append("'"+bean.getUserno()+"',");
	        					buf.append("'"+strings[1]+"#"+bean.getAreaCode()+"',");//接口id#号码归属地
	        					buf.append("'"+bean.getWh_order_num()+"',");
	        					buf.append("'"+HttpFillOP.jd_code+"',");
	        					buf.append(")");
	        					db.setAutoCommit(false);
	        					db.update(buf.toString());
	        					Log.info("京东流量,读取列队订单信息,记录临时订单成功,京东订单号:"+bean.getFillOrderNo()+",,,万恒订单号:"+bean.getWh_order_num());
	        					//订单信息入库
	        					TPForm tp=MemcacheConfig.getInstance().getTP(bean.getUserno());
	        					String writeOff = bean.getFillAmount()+"mb";
	        					Object[] orderObj = { null, tp.getUser_site() + "", bean.getWh_order_num(),
	        							bean.getFillMobile(), strings[1], strings[0], Double.parseDouble(bean.getCostPrice()) * 1000,
	        							Double.parseDouble(bean.getCostPrice()) * 1000, Jd_Flows.fundacct, time, time, 4,
	        							writeOff, bean.getFillOrderNo(), bean.getNotifyUrl(), bean.getUserno(), tp.getUser_login(),
	        							bean.getMutCode(), bean.getAreaCode(), HttpFillOP.jd_code };// 20
	        					db.logData(20, orderObj, "wht_orderform_" + time.substring(2, 6));
	        					db.commit();
	        					
	        					Log.info("京东流量,读取列队订单信息,订单入库成功,京东订单号:"+bean.getFillOrderNo()+",,,万恒订单号:"+bean.getWh_order_num());
	        				} catch (Exception e) {
	        					Log.error("京东线程读取列队,记录临时订单,订单信息,异常，，，ex:"+e);
	        					db.rollback();
	        					Flows_Public_Method.Is_Back(null, "1", HttpFillOP.jd_code, bean,null);//获取本地流量充值接口错误，直接调用http回调，这笔订单当做失败处理
        						return ;
	        				} finally {
	        					if (db != null) {
	        						db.close();
	        						db = null;
	        					}
	        				}
	                    }
                	}
				}catch (Exception e) {
					Log.error("portal接收受消息异常:"+e.toString());
					e.printStackTrace();
				}
			}
		});
		
		
		//京东订单充值结果查询队列
		sessionSmp5 = connection.createSession(Boolean.FALSE,
				Session.AUTO_ACKNOWLEDGE);
		// 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
		destinationSmp5 = sessionSmp5.createQueue("L2J");
		consumerSmp5 = sessionSmp5.createConsumer(destinationSmp5);
		consumerSmp5.setMessageListener(new MessageListener() {
            public void onMessage(Message arg0) {
                try {
                	if(null!=arg0){
                		if(arg0 instanceof ObjectMessage){
                			JdOrderBean ors=(JdOrderBean)((ObjectMessage)arg0).getObject();
            				
            				//如果取出的订单 为 null or 取出的订单查询次数大于  自定义单笔订单 查询总的次数 ，则不在调用此订单充值状态查询
            				if(ors==null || ors.getQuery_count()>JDThread.query_max_count){
            					Log.info("订单查询次数达到最大值,不再查询,万恒订单号："+ors.getWh_order_num()+",,京东订单号："+ors.getFillOrderNo());
            					return ;
            				}
            				
            				//如果 当前时间-此订单上次查询的时间<自定义的单笔订单最小查询空闲时间，则重新添加到查询订单队列尾部
            				long qtime=System.currentTimeMillis();
            				if(qtime-ors.getLast_time_query()<JDThread.query_min_time){
            					try {Thread.sleep(3*1000); } catch (Exception e) { }
            					boolean bool=PortalSend.getInstance().Send_JD_Query_Message(ors);
            					if(!bool){
            						Log.info("京东查询订单监听，卡单订单放入订单查询队列，失败,,万恒订单号："+ors.getWh_order_num()+",,京东订单号："+ors.getFillOrderNo());
            					}
            					return ;
            				}else{
            					// 当前订单 查询次数 +1,查询时间更新为当前时间
            					ors.setQuery_count(ors.getQuery_count()+1);
            					ors.setLast_time_query(qtime);
            				}
            				
            				String rs="";
            				if(HttpFillOP.FLOw1.equals(ors.getInterid())){
            					rs=Flows_Public_Method.getQueryOrderFlows(ors.getWh_order_num());
            				}else if(HttpFillOP.FLOw2.equals(ors.getInterid())){
            					rs=Flow.BeiFen_Query_Order(ors.getWh_order_num(),ors.getBeifen_reesult());
            				}else{
            					Log.info("流量查询队列,未知查询接口,return null");
            					return ;
            				}
            				
            				if("0".equals(rs)){
            					Flows_Public_Method.Is_Back(ors.getWh_order_num(), "0",ors.getCode(),null,null);
            				}else if("-1".equals(rs)){
            					Flows_Public_Method.Is_Back(ors.getWh_order_num(), "1",ors.getCode(),null,null);
            				}else{
            					PortalSend.getInstance().Send_JD_Query_Message(ors);
            				}
                        }
                	 }
				}catch (Exception e) {
					Log.error("portal接收受消息异常:"+e.toString());
					e.printStackTrace();
				}
            }
		});
		
		}catch (Exception e) {
			System.out.println("portal接收受消息异常:"+e.toString());
			e.printStackTrace();
		}
	}
	
//	public static String getRandomDigit() {
//		Random r = new Random();
//		int n = r.nextInt(100);
//		String m;
//		if (n < 35) {
//			m = "0";
//			return m;
//		} else if (n < 70) {
//			m = "-1";
//			return m;
//		}else {
//			m = "2";
//			return m;
//		}
//		
//	}
}

