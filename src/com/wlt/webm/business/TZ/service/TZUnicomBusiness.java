package com.wlt.webm.business.TZ.service;


import java.util.ArrayList;

import com.commsoft.epay.esb.EsbExecption;
import com.commsoft.epay.esb.EsbService;
import com.commsoft.epay.esb.Message;
import com.commsoft.epay.esb.ServiceFactory;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.Business;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.message.TelecomMsg;
import com.wlt.webm.uictool.Constant;


/**
 * <p>Description: ����TZ�ӿڷ�����Ϣ�����</p>
 * 
 */
public class TZUnicomBusiness implements Business{

	/**
	 * ���ռƷ���Ϣ
	 */
	private byte[] rcvMsg = null;
	/**
	 * ��ͷ��ˮ��
	 */
	private String frameID ="";;
	/**
	 * �ɷѺ���
	 */
	private String phone="";
	/**
	 * �ڲ���ˮ��
	 */
	private String seqNo="";
	/**
	 * ҵ��������
	 */
	private String servNo="";
	/**
	 * ҵ��������
	 */
	private String tradeNo="";
	/**
	 * �ն˺���
	 */
	private String termNo="";
	/**
	 * ҵ�����߳�
	 */
	public void deal() {
		int messageType=-1;
		if(rcvMsg==null || rcvMsg.length<132)//��Ϣ��Ϊ�գ����߳��Ȳ���
		{
			Log.error("TZ ������Ϣ��Ϊ�գ����߳��Ȳ���:"+new String(rcvMsg));
			return;
		}
		String msg=null;
	try{
	    msg=new String(rcvMsg);
		int bodyLg=Integer.parseInt(msg.substring(0, 4));
		String signStr=msg.substring(100+bodyLg, msg.length());
		//��ȡ��Ϣ����(���ױ���)
        String msgType=msg.substring(34,40);
		messageType=Integer.parseInt(msgType);
		//Ψһ�Ա�ʾ����Ϊkey
		frameID=msg.substring(4,20);
	}catch(Exception e)
		{
		    Log.error("��ȡ��Ϣ����TZUnicomBusiness"+e);
		}
	switch(messageType)
	{
		case 100006://������Ϣ
			Log.info("������Ϣ");
			MsgCache.addTZHtbMsg("xinTiao".getBytes());
			break;
		case 100001://ǩ����Ӧ
		    Log.info("ǩ����Ӧ��Ϣ��"+msg);
			ResponseSignIn responseSignIn = new ResponseSignIn(msg);
			String status=responseSignIn.getResCode();
			//����Ӧ������ж�
			if (!status.equals(Constant.TZRESPONSE_SUCCESS)) {
				Log.error("����ʧ�� >> ҵ����ˮ�ţ�" + frameID + "; ��Ӧ��:"
						+ status );
				return;
			}
			MsgCache.tzSign.put("sign",responseSignIn.getWorkKey());
			MsgCache.tzStatue=true;
			Log.info("ǩ���õ��Ĺ�����Կ"+(String)MsgCache.tzSign.get("sign"));
			MsgCache.addTZHtbMsg(rcvMsg);
			break;
			
		case 100002://��ֵ��Ӧ
		    Log.info("��ֵ��Ӧ��Ϣ��"+msg);
		    MsgCache.addTZHtbMsg("chongZhi".getBytes());
		    TopUpBack topUpback = new TopUpBack(msg);
			String status2=topUpback.getResCode();
			// ���Ʒѷ�����ת��ΪSCP������
			String respScpCode2 = TelecomMsg.telcomToScpRecode(status2);
			//�ӻ����л�ȡ����
			ArrayList temp2 = new ArrayList();
			temp2 =  MsgCache.getScpTZMap(frameID);
			if(temp2==null || temp2.size()!=2)
			{
				Log.error("��ѯ�Ҳ�����Ӧ������Ϣ��");
				return;
			}
			ArrayList list2 = (ArrayList)temp2.get(0);
			phone = (String)list2.get(0);
			String payFee = (String)list2.get(1);
			String serialNo = (String)list2.get(2);
			//����Ӧ������ж�
			if (!status2.equals(Constant.TZRESPONSE_SUCCESS)) {
				Log.error("ҵ����ˮ�ţ�" + seqNo + "; ��Ӧ��:"+ status2) ;
                //���¶���Ϊʧ��
				return;
			}
			String topUPBody=topUpback.getBody();
			String time=topUPBody.substring(0,14);  //��������ʱ��
			String serial=topUPBody.substring(14,34); //�������ص�ҵ����ˮ�� 
			String agentMon=topUPBody.substring(34,50).replaceAll("^0+", ""); //���������
            Log.info(" ��������ʱ��"+time+" ����ҵ����ˮ��"+serial+" ���������"+agentMon);
			// ֧�����
			String billValue = String.valueOf(Integer.parseInt(payFee));
			// ������������
			String checkBill = phone + "|" + billValue + "|" + serial
					+ "|" + serialNo;
			// ������������
			String rollBill =phone+ "#" +billValue+"#"+ serialNo+"#"+serial;
            //���¶���״̬ ���¶�����������

			Log.info("TZ��ͨ��ֵ���ص���Ϣ����ˮ�ţ�" + seqNo + "; �Ʒѷ����룺" + respScpCode2);
            break;
			
		case 100003://������Ӧ
		    Log.info("������Ӧ��Ϣ��"+msg);
		    MsgCache.addTZHtbMsg("chongZheng".getBytes());
		    ReverseBack reverseBack = new ReverseBack(msg);
			String status4=reverseBack.getResCode();
			//����Ӧ������ж�
			 if(status4.equals("10007")){//��ʾ���ײ����� �����ó���
					status4=Constant.RESPONSE_SUCCESS;
					Log.info("���ײ�����,���ó���");
					return ;
			 }
				//���Ʒѵ���Ӧ��ת����SCP����Ӧ��
				String respScpCode4 = TelecomMsg.telcomToScpRecode(status4);
				//�ӻ����л�ȡ����
				ArrayList temp4 = new ArrayList();
				temp4 =  MsgCache.getScpTZMap(frameID);
				if(temp4==null || temp4.size()!=2)
				{
					Log.error("��ѯ�Ҳ���������Ӧ������Ϣ��");
					return;
				}
				ArrayList list4 = (ArrayList)temp4.get(0);
				seqNo = (String)list4.get(0);
				//����ҵ����ˮ��
				String serialNo4 = (String)list4.get(1);
				//ԭ��ֵ����ҵ����ˮ��
				String oldSerialNo4=(String)list4.get(2);
			if (!status4.equals(Constant.TZRESPONSE_SUCCESS)) {
				Log.error("ҵ����ˮ�ţ�" + seqNo + "; ��Ӧ��:" + status4);
				//���� ������Ϊʧ��
				return;
			}
			String revserseBody=reverseBack.getBody();
			String tradetime=revserseBody.substring(0,14);  //��������ʱ��
			String reverseSerial=revserseBody.substring(14,34); //����ҵ����ˮ�� 
			String cph=revserseBody.substring(34,84).trim(); //�ͻ�����
			String reverseCash=revserseBody.substring(84,100).replaceAll("^0+", ""); //�������
			String leftCount=revserseBody.substring(100,110).replaceAll("^0+", "").replaceAll(" ", "");//ʣ���������
            Log.info("��scp���س�����Ϣ  ��������ʱ��:"+tradetime+" ����ҵ����ˮ��:"+reverseSerial+"�ͻ�����:"+cph+" �������:"+reverseCash+"ʣ���������:"+leftCount);
			//���¶���״̬
            Log.info("");
            break;
			
		default:
			Log.error("��Ϣ������޷�ʶ��");
			break;
		}
	}
	
	
	/**
	 * ���ô�����Ϣ
	 */
	public void setMessage(Object msg) {
		rcvMsg =(byte[]) msg;
	}

}

