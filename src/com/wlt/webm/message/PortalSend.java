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
 * ��Ϣ���Ͷ���
 * @author Administrator
 *
 */
public class PortalSend {
	
    // ConnectionFactory �����ӹ�����JMS ������������
    ConnectionFactory connectionFactory;
    // Connection ��JMS �ͻ��˵�JMS Provider ������
    Connection connection = null;
    // Session�� һ�����ͻ������Ϣ���߳�
    Session session;
    // Destination ����Ϣ��Ŀ�ĵ�;��Ϣ���͸�˭.
    Destination destination;
    // MessageProducer����Ϣ������
    MessageProducer producer;
    
    Session sessionSmp;
    // Destination ����Ϣ��Ŀ�ĵ�;��Ϣ���͸�˭.
    Destination destinationSmp;
    // MessageProducer����Ϣ������
    MessageProducer producerSmp;
    
    Session sessionSmp2;
    // Destination ����Ϣ��Ŀ�ĵ�;��Ϣ���͸�˭.
    Destination destinationSmp2;
    // MessageProducer����Ϣ������
    MessageProducer producerSmp2;
    
    Session sessionSmp3;
    // Destination ����Ϣ��Ŀ�ĵ�;��Ϣ���͸�˭.
    Destination destinationSmp3;
    // MessageProducer����Ϣ������
    MessageProducer producerSmp3;
    
    
    Session sessionSmp4;
    // Destination ����Ϣ��Ŀ�ĵ�;��Ϣ���͸�˭.
    Destination destinationSmp4;
    // MessageProducer����Ϣ������
    MessageProducer producerSmp4;
	
    
    Session sessionSmp5;
    // Destination ����Ϣ��Ŀ�ĵ�;��Ϣ���͸�˭.
    Destination destinationSmp5;
    // MessageProducer����Ϣ������
    MessageProducer producerSmp5;
	/**
	 * ʵ����pcsend
	 */
	private PortalSend(){
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");
        try {
            // ����ӹ����õ����Ӷ���
            connection = connectionFactory.createConnection();
            // ����
            connection.start();
            // ��ȡ��������
            session = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);
            // ��ȡsessionע�����ֵxingbo.xu-queue��һ����������queue��������ActiveMq��console����
            destination = session.createQueue("P2B");
            // �õ���Ϣ�����ߡ������ߡ�
            producer = session.createProducer(destination);
            // ���ò��־û� 
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producer.setTimeToLive (180*1000);
            
            sessionSmp = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);
            // ��ȡsessionע�����ֵxingbo.xu-queue��һ����������queue��������ActiveMq��console����
            destinationSmp = sessionSmp.createQueue("M2B");
            // �õ���Ϣ�����ߡ������ߡ�
            producerSmp = sessionSmp.createProducer(destinationSmp);
            // ���ò��־û� 
            producerSmp.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producerSmp.setTimeToLive (180*1000);
            
            //��Ѷ��������
            sessionSmp2 = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);
            // ��ȡsessionע�����ֵxingbo.xu-queue��һ����������queue��������ActiveMq��console����
            destinationSmp2 = sessionSmp2.createQueue("T2W");
            // �õ���Ϣ�����ߡ������ߡ�
            producerSmp2 = sessionSmp2.createProducer(destinationSmp2);
            // ���ò��־û� 
            producerSmp2.setDeliveryMode(DeliveryMode.PERSISTENT);
            producerSmp2.setTimeToLive(6*60*60*1000);
            
            //��Ѷ������ѯ����
            sessionSmp3 = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);
            // ��ȡsessionע�����ֵxingbo.xu-queue��һ����������queue��������ActiveMq��console����
            destinationSmp3 = sessionSmp3.createQueue("W2L");
            // �õ���Ϣ�����ߡ������ߡ�
            producerSmp3 = sessionSmp3.createProducer(destinationSmp3);
            // ���ò��־û� 
            producerSmp3.setDeliveryMode(DeliveryMode.PERSISTENT);
            producerSmp3.setTimeToLive(6*60*60*1000);
            
            
            
            
            //������������
            sessionSmp4 = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);
            // ��ȡsessionע�����ֵxingbo.xu-queue��һ����������queue��������ActiveMq��console����
            destinationSmp4 = sessionSmp4.createQueue("J2W");
            // �õ���Ϣ�����ߡ������ߡ�
            producerSmp4 = sessionSmp4.createProducer(destinationSmp4);
            // ���ò��־û� 
            producerSmp4.setDeliveryMode(DeliveryMode.PERSISTENT);
            producerSmp4.setTimeToLive(6*60*60*1000);
            
            //����������ѯ����
            sessionSmp5 = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);
            // ��ȡsessionע�����ֵxingbo.xu-queue��һ����������queue��������ActiveMq��console����
            destinationSmp5 = sessionSmp5.createQueue("J2L");
            // �õ���Ϣ�����ߡ������ߡ�
            producerSmp5 = sessionSmp5.createProducer(destinationSmp5);
            // ���ò��־û� 
            producerSmp5.setDeliveryMode(DeliveryMode.PERSISTENT);
            producerSmp5.setTimeToLive(6*60*60*1000);
            
//            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            Log.error("portal��ʼ�����Ͷ����쳣 :"+e.toString());
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
	            // ������Ϣ��Ŀ�ĵط�
	            Log.info("portal������Ϣ��"+msg.toString());
				producer.send(obj);
	//          session.commit();
	            return true;
    		}else{
                ObjectMessage obj=sessionSmp.createObjectMessage(msg);
                // ������Ϣ��Ŀ�ĵط�
                Log.info("smp������Ϣ��"+msg.toString());
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
//					System.out.println("ȡ����Ϣ�����쳣:"+e1.toString());
//				}
        	Log.error("client send �쳣:"+e.toString());
			e.printStackTrace();
			return false;
		}
    }
    
    /**
     * ��Ѷ�������з���
     * @param msg
     * @return boolean
     */
    public  boolean Send_Tencent_Message(TencentOrdersBean msg) {
    	try {
			 ObjectMessage obj=sessionSmp2.createObjectMessage(msg);
             // ������Ϣ��Ŀ�ĵط�
             Log.info("��Ѷ��ֵ����������Ϣ�ж�,��Ѷ�����ţ�"+msg.getPaipai_dealid()+",,��㶩����:"+msg.getSeqNo1());
 			 producerSmp2.send(obj);
             return true;	
        } catch (Exception e) {
        	Log.error("��Ѷ�������з���,,,��Ѷ�����ţ�"+msg.getPaipai_dealid()+",,��㶩����:"+msg.getSeqNo1()+",,,client send �쳣:"+e.toString());
			return false;
		}
    }
    
    /**
     * ��Ѷ������ѯ��ֵ������з���
     * @param msg
     * @return boolean
     */
    public  boolean Send_LianTong_Query_Orders(TencentOrdersBean msg) {
    	try {
			 ObjectMessage obj=sessionSmp3.createObjectMessage(msg);
             // ������Ϣ��Ŀ�ĵط�
             Log.info("��Ѷ������ѯ������Ϣ�ж�,�����ţ�"+msg.getPaipai_dealid()+",,��㶩����:"+msg.getSeqNo1());
 			 producerSmp3.send(obj);
             return true;	
        } catch (Exception e) {
        	Log.error("��Ѷ������ѯ��ֵ������з���,,�����ţ�"+msg.getPaipai_dealid()+",,��㶩����:"+msg.getSeqNo1()+",,client send �쳣:"+e.toString());
			return false;
		}
    }
    
    /**
     * ����������Ϣ����
     * @param msg
     * @return boolean 
     */
    public  boolean Send_JD_Message(JdOrderBean msg) {
    	try {
			 ObjectMessage obj=sessionSmp4.createObjectMessage(msg);
             // ������Ϣ��Ŀ�ĵط�
             Log.info("�������������ֵ��Ϣ�ж�,���������ţ�"+msg.getFillOrderNo()+",,��㶩����:"+msg.getWh_order_num());
 			 producerSmp4.send(obj);
             return true;	
        } catch (Exception e) {
        	Log.error("�����������з���,,,���������ţ�"+msg.getFillOrderNo()+",,��㶩����:"+msg.getWh_order_num()+",,,client send �쳣:"+e.toString());
			return false;
		}
    }
    
    /**
     * ����������ѯ����
     * @param msg
     * @return boolean
     */
    public  boolean Send_JD_Query_Message(JdOrderBean msg) {
    	try {
			 ObjectMessage obj=sessionSmp5.createObjectMessage(msg);
             // ������Ϣ��Ŀ�ĵط�
             Log.info("�������������ѯ��Ϣ�ж�,���������ţ�"+msg.getFillOrderNo()+",,��㶩����:"+msg.getWh_order_num());
 			 producerSmp5.send(obj);
             return true;	
        } catch (Exception e) {
        	Log.error("�����������з���,,���������ţ�"+msg.getFillOrderNo()+",,��㶩����:"+msg.getWh_order_num()+",,client send �쳣:"+e.toString());
			return false;
		}
    }
    
}
