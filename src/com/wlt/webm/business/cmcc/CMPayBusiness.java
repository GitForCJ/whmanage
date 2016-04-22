package com.wlt.webm.business.cmcc;


import javax.servlet.http.HttpSession;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.tool.Constant;

/**
 */
public class CMPayBusiness implements Business{
	

	/**
	 * ���ռƷ���Ϣ
	 */
	private byte[] rcvMsg = null;
	/**
	 * ��Ϣ����
	 */
	private String messageType="";																                                                                                       
	/**
	 * 
	 */
	String PhoneFee  = "";
	/**
	 * 
	 */
	String SendSeqNo = "";
	/**
	 * 
	 */
	String userid="";
	/**
	 * 
	 */
	String sepNo="";
	/**
	 * 
	 */
	String nowTime="";
	/**
	 * 
	 */
	String empFee="";
	/**
	 * 
	 */
	String empAcctLevlOne="";
	/**
	 * 
	 */
	String empFeeLevlOne="";
	/**
	 * 
	 */
	String empAcctLevlTwo="";
	/**
	 * 
	 */
	String empFeeLevlTwo="";
	/**
	 * 
	 */
	String payphone="";
	/**
	 * 
	 */
	String payfee="";
	/**
	 * 
	 */
	String revSeqNo  ="";//���ܷ���ˮ��
	/**
	 * 
	 */
	String tradeTime = "";//��ֵ����ʱ��
	/**
	 * 
	 */
	String agentFee  = "";//�������˻����
	/**
	 * 
	 */
	String rollback = "";
	/**
	 * 
	 */
	String fundAcct02 = "";
	/**
	 * ҵ�����߳�
	 */
	public void deal() {
		try{
			if(rcvMsg.length<24)//��Ϣ��Ϊ�գ����߳��Ȳ���
			{
				Log.error("��Ϣ��Ϊ�գ����߳��Ȳ���1:"+rcvMsg.length);
				return;
			}
			CMPayResponseHead CMPayresponseHead = new CMPayResponseHead(rcvMsg);//�������ܵ��ı���
			String len =CMPayresponseHead.getLen() ;//��ȡ���ĳ���
			messageType=CMPayresponseHead.getMessageType();//��ȡ��������
			MobileChargeService service = new MobileChargeService();
			//Ψһ�Ա�ʾ����Ϊkey
			int msgtype = Integer.parseInt(messageType);
			Log.info("��Ϣ����:" + messageType);
			switch(msgtype)//��������ѡ��
			{
				case 800119://������Ϣ
					Log.info("�յ��ƶ�������Ϣ��");
					//System.out.println("�յ��ƶ�������Ϣ��");
					MsgCache.addMoveHtbMsg(rcvMsg);
					break;
					
				case 800001://��½��Ӧ
				{
					MsgCache.addMoveHtbMsg(rcvMsg);
				    Log.info("ǩ����Ӧ��Ϣ��"+new String(rcvMsg));
					String status=CMPayresponseHead.getRespondType();
					//����Ӧ������ж�
					if (!status.equals(Constant.RESPONSE_SUCCESS)) {
						Log.error("��½ʧ�� >> ��Ӧ��:"+ status );
						return;
					}
					MsgCache.signCache.put("signFlag", "success");
					
					break;
				}
				case 800002://ע����Ӧ
				{
					MsgCache.addMoveHtbMsg(rcvMsg);
					Log.info("ǩ����Ӧ��Ϣ��"+new String(rcvMsg));
					String status=CMPayresponseHead.getRespondType();
					//����Ӧ������ж�
					if (!status.equals(Constant.RESPONSE_SUCCESS)) {
						Log.error("ע��ʧ�� >> ��Ӧ��:"+ status );
						return;
					}
					break;
				}
				case 800003://��ֵ��Ӧ
				{
					MsgCache.addMoveHtbMsg(rcvMsg);
					Log.info("�㶫��ͨ�ƶ���ֵ��Ӧ��Ϣ��"+new String(rcvMsg));
					CMPayResponseFill response = new CMPayResponseFill(rcvMsg);
					String status=response.getRespondType();//��ȡ�õ��ı�����Ӧ�룬�жϷ��͵ı����Ƿ���Ӧ�ɹ�
//					ArrayList list=new ArrayList();
//					ArrayList listuserid=new ArrayList();
//					revSeqNo  = response.getRevSeqNo();//���ܷ���ˮ��
//					tradeTime = response.getTradeTime();//��ֵ����ʱ��
//					agentFee  = response.getAgentFee();//�������˻����
//					PhoneFee  = response.getPhoneFee();//�ͻ��ֻ����
					SendSeqNo = response.getSendSeqNo();//������ˮ��
					Log.info("׼��ȡ��"+SendSeqNo+"==="+status+"----");
					    if(status.trim().equals("0000")){
					    	Log.info("׼������----------------"+SendSeqNo+status);
					    	//MsgCache.cmcc.put("cmccpay"+SendSeqNo,"0");
					    	service.insertState("cmccpay"+SendSeqNo,"0");
								
						}else{
							//MsgCache.cmcc.put("cmccpay"+SendSeqNo,"2");
							service.insertState("cmccpay"+SendSeqNo,"2");
							return;
							
						}							
					break;
				}
				case 800004://������Ӧ
				{
					MsgCache.addMoveHtbMsg(rcvMsg);
					Log.info("�㶫��ͨ�ƶ�������Ӧ��Ϣ��"+new String(rcvMsg));
					CMPayResponseUndoPay response = new CMPayResponseUndoPay(rcvMsg);
					String status=response.getRespondType();
					SendSeqNo = response.getSepNo();
					 if(status.equals("0000")){
							//MsgCache.cmcc.put("cmccRepay"+SendSeqNo,"0");
							service.insertState("cmccRepay"+SendSeqNo,"0");
						}else{
							//MsgCache.cmcc.put("cmccRepay"+SendSeqNo,"1");
							service.insertState("cmccRepay"+SendSeqNo,"1");
							return;
						}				
					break;
				}
				default:
				{
					MsgCache.addMoveHtbMsg(rcvMsg);
					Log.error("�ƶ�������Ϣ����޷�ʶ��:" + new String(rcvMsg));
					
					break;
				}
			}
		}catch(Exception e)
		{
			Log.error("�����ƶ��ɷ���Ϣ��ҵ�����̴߳���", e);
		}
	}
	
	/**
	 * ���ô�����Ϣ
	 */
	public void setMessage(Object msg) {
		
		rcvMsg =(byte[]) msg;
	}
	
	

}
