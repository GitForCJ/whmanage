package com.wlt.webm.business.cmcc;

import java.io.ByteArrayOutputStream;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.tool.Constant;
import com.wlt.webm.tool.MD5;
import com.wlt.webm.tool.Tools;

public class CMPayRequestPay {
	byte[] sendMsg=null;
	/**
	 * �ɷ���Ϣ���
	 * @return ���ؽɷ���Ϣ���
	 * @throws Exception 
	 */
	public byte[] payMsg(String PayPhone,String payFee,String seqNo) throws Exception{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		MobileChargeService service = new MobileChargeService();
		payFee=Tools.addLeftZero(payFee, 10);
		PayPhone=Tools.headFillSpace(PayPhone,13);
		try{
			//��ͷ��Ϣ
			bout.write("0112".getBytes());//���� 4
			bout.write(Tools.getCMPayNO().getBytes());//�������к� 10
			bout.write(Constant.CMPayBusinessType.getBytes());//ҵ������ 4
			bout.write("000003".getBytes());//��Ϣ�� 6
			//������Ϣ
//			bout.write(MD5.encode(Constant.CMPaySignPWD).getBytes());//��ҵ��������  32
			String md5=MD5.encode("123456");
			bout.write(md5.getBytes());//���ܺ���ҵ��¼���� 32
			bout.write(payFee.getBytes());//��ֵ��� 10
			bout.write(PayPhone.getBytes());//�ͻ��ֻ����� 13
			bout.write(seqNo.getBytes());//������ˮ�� 20
			bout.write(Constant.CMPaySUPhone.getBytes());//�������ֻ����� 13
			Log.info("NO"+Tools.getCMPayNO()+"MD5"+MD5.encode("hr0577").getBytes()+"payFee"+payFee+"PayPhone"+PayPhone+"seqNo"+seqNo+"--"+Constant.CMPaySUPhone);
		}catch(Exception e){
			Log.error("�ɷ�����쳣");
			Log.error(e);
			service.insertState("cmccpay"+seqNo,"2");
		}
//		Log.info("�ɷ����:" + new String(bout.toByteArray()));
//		System.out.println("�ɷѱ���:" + new String(bout.toByteArray()));
//		System.out.println(new String(bout.toByteArray()).trim().length());
		sendMsg= bout.toByteArray();
//		MsgCache.CMPaysendMsgCache.add(sendMsg);//���浽���Ͷ���
//		System.out.println("�������ݣ�"+sendMsg);
		Log.info("��������:"+new String(sendMsg));
		return bout.toByteArray();
	}
	
}
