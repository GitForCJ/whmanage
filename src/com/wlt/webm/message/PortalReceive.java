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
 * portal����business��Ϣ��
 * @author Administrator
 *
 */
public class PortalReceive{

	// ConnectionFactory �����ӹ�����JMS ������������
	ConnectionFactory connectionFactory;
	// Connection ��JMS �ͻ��˵�JMS Provider ������
	Connection connection = null;
	// Session�� һ�����ͻ������Ϣ���߳�
	Session session;
	// Destination ����Ϣ��Ŀ�ĵ�;��Ϣ���͸�˭.
	Destination destination;
	// �����ߣ���Ϣ������
	MessageConsumer consumer;
	
	Session sessionSmp;
	// Destination ����Ϣ��Ŀ�ĵ�;��Ϣ���͸�˭.
	Destination destinationSmp;
	// �����ߣ���Ϣ������
	MessageConsumer consumerSmp;
	
	
	Session sessionSmp2;
	// Destination ����Ϣ��Ŀ�ĵ�;��Ϣ���͸�˭.
	Destination destinationSmp2;
	// �����ߣ���Ϣ������
	MessageConsumer consumerSmp2;
	
	
	Session sessionSmp3;
	// Destination ����Ϣ��Ŀ�ĵ�;��Ϣ���͸�˭.
	Destination destinationSmp3;
	// �����ߣ���Ϣ������
	MessageConsumer consumerSmp3;
	
	
	Session sessionSmp4;
	// Destination ����Ϣ��Ŀ�ĵ�;��Ϣ���͸�˭.
	Destination destinationSmp4;
	// �����ߣ���Ϣ������
	MessageConsumer consumerSmp4;
	
	
	Session sessionSmp5;
	// Destination ����Ϣ��Ŀ�ĵ�;��Ϣ���͸�˭.
	Destination destinationSmp5;
	// �����ߣ���Ϣ������
	MessageConsumer consumerSmp5;
	/**
	 * 
	 */
	public PortalReceive() {
		Log.info("����portal����...");
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
		try {
			// ����ӹ����õ����Ӷ���
			connection = connectionFactory.createConnection();
			// ����
			connection.start();
			// ��ȡ��������
			session = connection.createSession(Boolean.FALSE,
					Session.AUTO_ACKNOWLEDGE);
			// ��ȡsessionע�����ֵxingbo.xu-queue��һ����������queue��������ActiveMq��console����
			destination = session.createQueue("B2P");
			consumer = session.createConsumer(destination);
//			while (true) {
//				// ���ý����߽�����Ϣ��ʱ�䣬Ϊ�˱��ڲ��ԣ����ﶨΪ100s
//				TextMessage message = (TextMessage) consumer.receive(10000);
//				if (null != message) {
//					System.out.println("�յ���Ϣ" + message.getText());
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
	                        	Log.info("portal������Ϣ: "+msg.toString());
	                        	MemcacheConfig.getInstance().add(msg.getSeqNo(), msg);
	                        	System.out.println(msg.getSeqNo()+"===="+MemcacheConfig.getInstance().get(msg.getSeqNo()));
	                        }
	                	 }
						}catch (Exception e) {
							Log.error("portal��������Ϣ�쳣:"+e.toString());
							e.printStackTrace();
						}
				}
			});
			//====
			sessionSmp = connection.createSession(Boolean.FALSE,
					Session.AUTO_ACKNOWLEDGE);
			// ��ȡsessionע�����ֵxingbo.xu-queue��һ����������queue��������ActiveMq��console����
			destinationSmp = sessionSmp.createQueue("B2M");
			consumerSmp = sessionSmp.createConsumer(destinationSmp);
			consumerSmp.setMessageListener(new MessageListener() {
                public void onMessage(Message arg0) {
                    try {
	                	if(null!=arg0){
	                		if(arg0 instanceof ObjectMessage){
	                        	TradeMsg msg=(TradeMsg)((ObjectMessage)arg0).getObject();
	//                            session.commit();
	                        	Log.info("smp������Ϣ: "+msg.toString());
	                        	MemcacheConfig.getInstance().add(msg.getSeqNo(), msg);
	                        }
	                	 }
					}catch (Exception e) {
						Log.error("portal��������Ϣ�쳣:"+e.toString());
						e.printStackTrace();
					}
                }
			});
			
		//====//��Ѷ������ֵ����
		sessionSmp2 = connection.createSession(Boolean.FALSE,
				Session.AUTO_ACKNOWLEDGE);
		// ��ȡsessionע�����ֵxingbo.xu-queue��һ����������queue��������ActiveMq��console����
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
	        						Log.info("��Ѷ����,�ظ�������,��Ѷ������:"+bean.getPaipai_dealid()+",,,��ѯ�Ķ�����Ϣ,,"+Arrays.toString(tencent_strs)+",,����״̬:"+tencent_strs[1]);
	        						if("0".equals(tencent_strs[1])){
	        							Log.info("��Ѷ����,�ظ�����,����״̬,�ɹ�,����֪ͨ�ɹ��ӿ�,,��Ѷ������:"+bean.getPaipai_dealid()+",,,seqNo1:"+tencent_strs[0]);
	        							Flows_Public_Method.Is_Back(null, "0",HttpFillOP.tencent_code,bean,null);
	        						}
	        						return ;
	        					}
	        					//�жϵ���
	        					String[] strings=db.getStringArray("SELECT (CASE WHEN a.phone_type=0 THEN '0011' WHEN a.phone_type=1 THEN '0010' WHEN a.phone_type=2 THEN '0009' END) AS yun,b.sa_id,c.interid "+
										" FROM sys_phone_area a RIGHT JOIN sys_area b ON a.province_code=b.sa_id LEFT JOIN  sys_flowinterfaceMaping c ON b.sa_id=c.pid AND a.phone_type=c.type "+
										" WHERE a.phone_num='"+bean.getCustomer().substring(0, 7)+"'");
	        					if(strings==null || strings.length<=0){
	        						Log.info("��Ѷ������ֵ,��ȡ���д���,����ʧ��֪ͨ�ӿ�,��Ѷ������:"+bean.getPaipai_dealid()+",,,seqNo1:"+bean.getSeqNo1());
	        						Flows_Public_Method.Is_Back(null, "1",HttpFillOP.tencent_code,bean,null);
	        						return ;
	        					}
	        					
	        					//������ȡ���Ķ�����Ϣ����ʱ������⣬��ֹ���������ݶ�ʧ��ǰ���ǣ�ȡ�������е����Ҫ�ȴ��붩�����ٶ�Ҫ�죬���򶩵��жӻ��ǻ��кܶඩ��
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
	        					buf.append("'"+strings[2]+"#"+strings[1]+"',");//�ӿ�id#���������
	        					buf.append("'"+HttpFillOP.tencent_code +"'");
	        					buf.append(")");
	        					db.update(buf.toString());
	        					//������Ϣ���
	        					TPForm tp=MemcacheConfig.getInstance().getTP(bean.getUserno());
	        					String writeOff = bean.getCardid().substring(6, bean.getCardid().length())+"mb";
	        					Object[] orderObj = { null, tp.getUser_site() + "", bean.getSeqNo1(),
	        							bean.getCustomer(), strings[2], strings[0], Double.parseDouble(bean.getPay()) * 10,
	        							Double.parseDouble(bean.getPay()) * 10, Tencent_Flows.fundacct, time, time, 4,
	        							writeOff, bean.getPaipai_dealid(), 3, bean.getUserno(), tp.getUser_login(),
	        							bean.getOperator(), strings[1],HttpFillOP.tencent_code};// 20
	        					db.logData(20, orderObj, "wht_orderform_" + time.substring(2, 6));
	        					
	        					Log.info("��Ѷ����,��ȡ�жӶ�����Ϣ,�������ɹ�,��Ѷ������:"+bean.getPaipai_dealid()+",,,seqNo1:"+bean.getSeqNo1());
	        				} catch (Exception e) {
	        					Log.error("��Ѷ�̶߳�ȡ�ж�,��¼��ʱ����,������Ϣ,��Ѷ������:"+bean.getPaipai_dealid()+",,,seqNo1:"+bean.getSeqNo1()+",,�쳣������ex:"+e);
	        				} finally {
	        					if (db != null) {
	        						db.close();
	        						db = null;
	        					}
	        				}
	                    }
                	}
				}catch (Exception e) {
					Log.error("portal��������Ϣ�쳣:"+e.toString());
					e.printStackTrace();
				}
			}
		});
		
		//===��Ѷ������ֵ�����ѯ����
		sessionSmp3 = connection.createSession(Boolean.FALSE,
				Session.AUTO_ACKNOWLEDGE);
		// ��ȡsessionע�����ֵxingbo.xu-queue��һ����������queue��������ActiveMq��console����
		destinationSmp3 = sessionSmp3.createQueue("W2L");
		consumerSmp3 = sessionSmp3.createConsumer(destinationSmp3);
		consumerSmp3.setMessageListener(new MessageListener() {
            public void onMessage(Message arg0) {
                try {
                	if(null!=arg0){
                		if(arg0 instanceof ObjectMessage){
                			TencentOrdersBean ors=(TencentOrdersBean)((ObjectMessage)arg0).getObject();
            				
            				//���ȡ���Ķ��� Ϊ null or ȡ���Ķ�����ѯ��������  �Զ��嵥�ʶ��� ��ѯ�ܵĴ��� �����ڵ��ô˶�����ֵ״̬��ѯ
            				if(ors==null || ors.getQuery_count()>TencentThread.query_max_count){
            					Log.info("������ѯ�����ﵽ���ֵ,���ٲ�ѯ,�����ţ�"+ors.getSeqNo1()+",,��Ѷ�����ţ�"+ors.getPaipai_dealid());
            					return ;
            				}
            				
            				//��� ��ǰʱ��-�˶����ϴβ�ѯ��ʱ��<�Զ���ĵ��ʶ�����С��ѯ����ʱ�䣬��������ӵ���ѯ��������β��
            				long qtime=System.currentTimeMillis();
            				if(qtime-ors.getLast_time_query()<TencentThread.query_min_time){
            					try {Thread.sleep(3*1000); } catch (Exception e) { }
            					boolean bool=PortalSend.getInstance().Send_LianTong_Query_Orders(ors);
            					if(!bool){
            						Log.info("��Ѷ��ѯ���������������������붩����ѯ���У�ʧ��,,��Ѷ������:"+ors.getPaipai_dealid()+",,,seqNo1:"+ors.getSeqNo1());
            					}
            					return ;
            				}else{
            					// ��ǰ���� ��ѯ���� +1,��ѯʱ�����Ϊ��ǰʱ��
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
            					Log.info("������ѯ����,δ֪��ѯ�ӿ�,return null");
            					return ;
            				}
            				
            				Log.info("������ѯ����,��㶩����:"+ors.getSeqNo1()+",,��ѯ���:"+rs+",0�ɹ� -1ʧ�� ����������,��ѯ�������ýӿ�:"+ors.getInterid());
            				
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
					Log.error("portal��������Ϣ�쳣:"+e.toString());
					e.printStackTrace();
				}
            }
		});
		
		//����������ֵ����
		sessionSmp4 = connection.createSession(Boolean.FALSE,
				Session.AUTO_ACKNOWLEDGE);
		// ��ȡsessionע�����ֵxingbo.xu-queue��һ����������queue��������ActiveMq��console����
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
	        						//�������ظ�,,ֱ�ӵ���http�ص�,��ʶ�������ʧ�ܴ���
	        						Flows_Public_Method.Is_Back(null, "1", HttpFillOP.jd_code, bean,null);//��ȡ����������ֵ�ӿڴ���ֱ�ӵ���http�ص�����ʶ�������ʧ�ܴ���
	        						return ;
	        					}
	        					
	        					//�жϵ���
	        					String[] strings=db.getStringArray("SELECT (CASE WHEN "+bean.getMutCode()+"=0 THEN '0011' WHEN "+bean.getMutCode()+"=1 THEN '0010' WHEN "+bean.getMutCode()+"=2 THEN '0009' END) AS yun,interid FROM sys_flowinterfaceMaping WHERE pid='"+bean.getAreaCode()+"' AND TYPE='"+bean.getMutCode()+"'");
	        					if(strings==null || strings.length<=0 ||  strings[1]==null || "".equals(strings[1])){
	        						Log.info("����������ֵ,��ȡ���д���,����ʧ��֪ͨ�ӿ�,����������:"+bean.getFillOrderNo()+",,,��㶩����:"+bean.getWh_order_num());
	        						Flows_Public_Method.Is_Back(null, "1", HttpFillOP.jd_code, bean,null);//��ȡ����������ֵ�ӿڴ���ֱ�ӵ���http�ص�����ʶ�������ʧ�ܴ���
	        						return ;
	        					}
	        					
	        					//������ȡ���Ķ�����Ϣ����ʱ������⣬��ֹ���������ݶ�ʧ��ǰ���ǣ�ȡ�������е����Ҫ�ȴ��붩�����ٶ�Ҫ�죬���򶩵��жӻ��ǻ��кܶඩ��
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
	        					buf.append("'"+strings[1]+"#"+bean.getAreaCode()+"',");//�ӿ�id#���������
	        					buf.append("'"+bean.getWh_order_num()+"',");
	        					buf.append("'"+HttpFillOP.jd_code+"',");
	        					buf.append(")");
	        					db.setAutoCommit(false);
	        					db.update(buf.toString());
	        					Log.info("��������,��ȡ�жӶ�����Ϣ,��¼��ʱ�����ɹ�,����������:"+bean.getFillOrderNo()+",,,��㶩����:"+bean.getWh_order_num());
	        					//������Ϣ���
	        					TPForm tp=MemcacheConfig.getInstance().getTP(bean.getUserno());
	        					String writeOff = bean.getFillAmount()+"mb";
	        					Object[] orderObj = { null, tp.getUser_site() + "", bean.getWh_order_num(),
	        							bean.getFillMobile(), strings[1], strings[0], Double.parseDouble(bean.getCostPrice()) * 1000,
	        							Double.parseDouble(bean.getCostPrice()) * 1000, Jd_Flows.fundacct, time, time, 4,
	        							writeOff, bean.getFillOrderNo(), bean.getNotifyUrl(), bean.getUserno(), tp.getUser_login(),
	        							bean.getMutCode(), bean.getAreaCode(), HttpFillOP.jd_code };// 20
	        					db.logData(20, orderObj, "wht_orderform_" + time.substring(2, 6));
	        					db.commit();
	        					
	        					Log.info("��������,��ȡ�жӶ�����Ϣ,�������ɹ�,����������:"+bean.getFillOrderNo()+",,,��㶩����:"+bean.getWh_order_num());
	        				} catch (Exception e) {
	        					Log.error("�����̶߳�ȡ�ж�,��¼��ʱ����,������Ϣ,�쳣������ex:"+e);
	        					db.rollback();
	        					Flows_Public_Method.Is_Back(null, "1", HttpFillOP.jd_code, bean,null);//��ȡ����������ֵ�ӿڴ���ֱ�ӵ���http�ص�����ʶ�������ʧ�ܴ���
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
					Log.error("portal��������Ϣ�쳣:"+e.toString());
					e.printStackTrace();
				}
			}
		});
		
		
		//����������ֵ�����ѯ����
		sessionSmp5 = connection.createSession(Boolean.FALSE,
				Session.AUTO_ACKNOWLEDGE);
		// ��ȡsessionע�����ֵxingbo.xu-queue��һ����������queue��������ActiveMq��console����
		destinationSmp5 = sessionSmp5.createQueue("L2J");
		consumerSmp5 = sessionSmp5.createConsumer(destinationSmp5);
		consumerSmp5.setMessageListener(new MessageListener() {
            public void onMessage(Message arg0) {
                try {
                	if(null!=arg0){
                		if(arg0 instanceof ObjectMessage){
                			JdOrderBean ors=(JdOrderBean)((ObjectMessage)arg0).getObject();
            				
            				//���ȡ���Ķ��� Ϊ null or ȡ���Ķ�����ѯ��������  �Զ��嵥�ʶ��� ��ѯ�ܵĴ��� �����ڵ��ô˶�����ֵ״̬��ѯ
            				if(ors==null || ors.getQuery_count()>JDThread.query_max_count){
            					Log.info("������ѯ�����ﵽ���ֵ,���ٲ�ѯ,��㶩���ţ�"+ors.getWh_order_num()+",,���������ţ�"+ors.getFillOrderNo());
            					return ;
            				}
            				
            				//��� ��ǰʱ��-�˶����ϴβ�ѯ��ʱ��<�Զ���ĵ��ʶ�����С��ѯ����ʱ�䣬��������ӵ���ѯ��������β��
            				long qtime=System.currentTimeMillis();
            				if(qtime-ors.getLast_time_query()<JDThread.query_min_time){
            					try {Thread.sleep(3*1000); } catch (Exception e) { }
            					boolean bool=PortalSend.getInstance().Send_JD_Query_Message(ors);
            					if(!bool){
            						Log.info("������ѯ���������������������붩����ѯ���У�ʧ��,,��㶩���ţ�"+ors.getWh_order_num()+",,���������ţ�"+ors.getFillOrderNo());
            					}
            					return ;
            				}else{
            					// ��ǰ���� ��ѯ���� +1,��ѯʱ�����Ϊ��ǰʱ��
            					ors.setQuery_count(ors.getQuery_count()+1);
            					ors.setLast_time_query(qtime);
            				}
            				
            				String rs="";
            				if(HttpFillOP.FLOw1.equals(ors.getInterid())){
            					rs=Flows_Public_Method.getQueryOrderFlows(ors.getWh_order_num());
            				}else if(HttpFillOP.FLOw2.equals(ors.getInterid())){
            					rs=Flow.BeiFen_Query_Order(ors.getWh_order_num(),ors.getBeifen_reesult());
            				}else{
            					Log.info("������ѯ����,δ֪��ѯ�ӿ�,return null");
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
					Log.error("portal��������Ϣ�쳣:"+e.toString());
					e.printStackTrace();
				}
            }
		});
		
		}catch (Exception e) {
			System.out.println("portal��������Ϣ�쳣:"+e.toString());
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

