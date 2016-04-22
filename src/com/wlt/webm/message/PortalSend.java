package com.wlt.webm.message;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import whmessgae.TradeMsg;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.action.JdOrderBean;
import com.wlt.webm.business.action.TencentOrdersBean;

/**
 * 消息发送队列
 * @author Administrator
 *
 */
public class PortalSend {
	
    // ConnectionFactory ：连接工厂，JMS 用它创建连接
    ConnectionFactory connectionFactory;
    // Connection ：JMS 客户端到JMS Provider 的连接
    Connection connection = null;
    // Session： 一个发送或接收消息的线程
    Session session;
    // Destination ：消息的目的地;消息发送给谁.
    Destination destination;
    // MessageProducer：消息发送者
    MessageProducer producer;
    
    Session sessionSmp;
    // Destination ：消息的目的地;消息发送给谁.
    Destination destinationSmp;
    // MessageProducer：消息发送者
    MessageProducer producerSmp;
    
    Session sessionSmp2;
    // Destination ：消息的目的地;消息发送给谁.
    Destination destinationSmp2;
    // MessageProducer：消息发送者
    MessageProducer producerSmp2;
    
    Session sessionSmp3;
    // Destination ：消息的目的地;消息发送给谁.
    Destination destinationSmp3;
    // MessageProducer：消息发送者
    MessageProducer producerSmp3;
    
    
    Session sessionSmp4;
    // Destination ：消息的目的地;消息发送给谁.
    Destination destinationSmp4;
    // MessageProducer：消息发送者
    MessageProducer producerSmp4;
	
    
    Session sessionSmp5;
    // Destination ：消息的目的地;消息发送给谁.
    Destination destinationSmp5;
    // MessageProducer：消息发送者
    MessageProducer producerSmp5;
	/**
	 * 实例化pcsend
	 */
	private PortalSend(){
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");
        try {
            // 构造从工厂得到连接对象
            connection = connectionFactory.createConnection();
            // 启动
            connection.start();
            // 获取操作连接
            session = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
            destination = session.createQueue("P2B");
            // 得到消息生成者【发送者】
            producer = session.createProducer(destination);
            // 设置不持久化 
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producer.setTimeToLive (180*1000);
            
            sessionSmp = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
            destinationSmp = sessionSmp.createQueue("M2B");
            // 得到消息生成者【发送者】
            producerSmp = sessionSmp.createProducer(destinationSmp);
            // 设置不持久化 
            producerSmp.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producerSmp.setTimeToLive (180*1000);
            
            //腾讯订单队列
            sessionSmp2 = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
            destinationSmp2 = sessionSmp2.createQueue("T2W");
            // 得到消息生成者【发送者】
            producerSmp2 = sessionSmp2.createProducer(destinationSmp2);
            // 设置不持久化 
            producerSmp2.setDeliveryMode(DeliveryMode.PERSISTENT);
            producerSmp2.setTimeToLive(6*60*60*1000);
            
            //腾讯订单查询队列
            sessionSmp3 = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
            destinationSmp3 = sessionSmp3.createQueue("W2L");
            // 得到消息生成者【发送者】
            producerSmp3 = sessionSmp3.createProducer(destinationSmp3);
            // 设置不持久化 
            producerSmp3.setDeliveryMode(DeliveryMode.PERSISTENT);
            producerSmp3.setTimeToLive(6*60*60*1000);
            
            
            
            
            //京东订单队列
            sessionSmp4 = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
            destinationSmp4 = sessionSmp4.createQueue("J2W");
            // 得到消息生成者【发送者】
            producerSmp4 = sessionSmp4.createProducer(destinationSmp4);
            // 设置不持久化 
            producerSmp4.setDeliveryMode(DeliveryMode.PERSISTENT);
            producerSmp4.setTimeToLive(6*60*60*1000);
            
            //京东订单查询队列
            sessionSmp5 = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
            destinationSmp5 = sessionSmp5.createQueue("J2L");
            // 得到消息生成者【发送者】
            producerSmp5 = sessionSmp5.createProducer(destinationSmp5);
            // 设置不持久化 
            producerSmp5.setDeliveryMode(DeliveryMode.PERSISTENT);
            producerSmp5.setTimeToLive(6*60*60*1000);
            
//            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            Log.error("portal初始化发送队列异常 :"+e.toString());
        } 
 }
	
	/**
	 * @author Administrator
	 *
	 */
	private static class HolderSnd{
		static PortalSend snd =new PortalSend();
	}
	
	 /**
	 * @return
	 */
	public static PortalSend getInstance(){    
	        return HolderSnd.snd;    
	    } 
    /**
     * @param msg
     * @return
     */
    public  boolean sendmsg(TradeMsg msg) {
    	try {
    		if(msg.getMsgFrom2().equals("0103")){
	            ObjectMessage obj=session.createObjectMessage(msg);
	            // 发送消息到目的地方
	            Log.info("portal发送消息："+msg.toString());
				producer.send(obj);
	//          session.commit();
	            return true;
    		}else{
                ObjectMessage obj=sessionSmp.createObjectMessage(msg);
                // 发送消息到目的地方
                Log.info("smp发送消息："+msg.toString());
    			producerSmp.send(obj);
//              session.commit();
                return true;	
    		}
        }
        catch (Exception e) {
//            	try {
//					session.rollback();
//				} catch (JMSException e1) {
//					e1.printStackTrace();
//					System.out.println("取消消息发送异常:"+e1.toString());
//				}
        	Log.error("client send 异常:"+e.toString());
			e.printStackTrace();
			return false;
		}
    }
    
    /**
     * 腾讯订单队列发送
     * @param msg
     * @return boolean
     */
    public  boolean Send_Tencent_Message(TencentOrdersBean msg) {
    	try {
			 ObjectMessage obj=sessionSmp2.createObjectMessage(msg);
             // 发送消息到目的地方
             Log.info("腾讯充值订单放入消息列队,腾讯订单号："+msg.getPaipai_dealid()+",,万恒订单号:"+msg.getSeqNo1());
 			 producerSmp2.send(obj);
             return true;	
        } catch (Exception e) {
        	Log.error("腾讯订单队列发送,,,腾讯订单号："+msg.getPaipai_dealid()+",,万恒订单号:"+msg.getSeqNo1()+",,,client send 异常:"+e.toString());
			return false;
		}
    }
    
    /**
     * 腾讯订单查询充值结果队列发送
     * @param msg
     * @return boolean
     */
    public  boolean Send_LianTong_Query_Orders(TencentOrdersBean msg) {
    	try {
			 ObjectMessage obj=sessionSmp3.createObjectMessage(msg);
             // 发送消息到目的地方
             Log.info("腾讯订单查询放入消息列队,订单号："+msg.getPaipai_dealid()+",,万恒订单号:"+msg.getSeqNo1());
 			 producerSmp3.send(obj);
             return true;	
        } catch (Exception e) {
        	Log.error("腾讯订单查询充值结果队列发送,,订单号："+msg.getPaipai_dealid()+",,万恒订单号:"+msg.getSeqNo1()+",,client send 异常:"+e.toString());
			return false;
		}
    }
    
    /**
     * 京东订单消息队列
     * @param msg
     * @return boolean 
     */
    public  boolean Send_JD_Message(JdOrderBean msg) {
    	try {
			 ObjectMessage obj=sessionSmp4.createObjectMessage(msg);
             // 发送消息到目的地方
             Log.info("京东订单放入充值消息列队,京东订单号："+msg.getFillOrderNo()+",,万恒订单号:"+msg.getWh_order_num());
 			 producerSmp4.send(obj);
             return true;	
        } catch (Exception e) {
        	Log.error("京东订单队列发送,,,京东订单号："+msg.getFillOrderNo()+",,万恒订单号:"+msg.getWh_order_num()+",,,client send 异常:"+e.toString());
			return false;
		}
    }
    
    /**
     * 京东订单查询队列
     * @param msg
     * @return boolean
     */
    public  boolean Send_JD_Query_Message(JdOrderBean msg) {
    	try {
			 ObjectMessage obj=sessionSmp5.createObjectMessage(msg);
             // 发送消息到目的地方
             Log.info("京东订单放入查询消息列队,京东订单号："+msg.getFillOrderNo()+",,万恒订单号:"+msg.getWh_order_num());
 			 producerSmp5.send(obj);
             return true;	
        } catch (Exception e) {
        	Log.error("京东订单队列发送,,京东订单号："+msg.getFillOrderNo()+",,万恒订单号:"+msg.getWh_order_num()+",,client send 异常:"+e.toString());
			return false;
		}
    }
    
}
