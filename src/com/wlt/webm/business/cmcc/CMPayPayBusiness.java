package com.wlt.webm.business.cmcc;

import java.util.ArrayList;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.tool.Constant;

/**
 */
public class CMPayPayBusiness {


	/**
	 * �ɷѺ���
	 */
	public final  String PayPhone;

	/**
	 * �ɷѽ��
	 */
	public final  String payFee;
	/**
	 * ��ˮ��
	 */
	public final  String serialNo;
    /**
     * ���췽��
     * @param PayPhone 
     * @param payFee 
     * @param serialNo 
     */
    public CMPayPayBusiness(String PayPhone,String payFee,String serialNo)
    {
        this.PayPhone = PayPhone;
        this.payFee = payFee;
        this.serialNo = serialNo;
    }

	/**
	 * ҵ������
	 * @throws Exception 
	 */
	public void deal() throws Exception {
		Log.info("�ƶ��ɷ�ҵ����ʼ��");
		MobileChargeService service = new MobileChargeService();
		String flag=(String)MsgCache.signCache.get("signFlag");
		if((flag==null||!flag.equals("success"))){
			Log.info("û�гɹ�ǩ�������Ժ�����"+flag);
			//System.out.println("û�гɹ�ǩ�������Ժ�����");
			CMPayRequestSignIn signIn = new CMPayRequestSignIn();
			byte[] sendMsg=signIn.signInMsg();
			MsgCache.CMPaysendMsgCache.add(sendMsg);//���浽���Ͷ���
			//MsgCache.cmcc.put("cmccpay"+serialNo,"2");
			service.insertState("cmccpay"+serialNo,"2");
			return;
		}
		CMPayRequestPay fill = new CMPayRequestPay();
		try {
//			serialNo=Tools.getCMPaySeq(Constant.CMPaySUPhone.trim(),Constant.CMPayBeginSeq);
//			�ɷ����
			byte[] paymsg=fill.payMsg(PayPhone,payFee,serialNo);
//			byte[] paymsg=fill.payMsg(PayPhone,payFee,"44134102204620000001");
			//���浽���Ͷ���
			MsgCache.CMPaysendMsgCache.add(paymsg);
		} catch (Exception e) {
			Log.error("�ƶ��ɷѴ���", e);
			//MsgCache.cmcc.put("cmccpay"+serialNo,"2");
			service.insertState("cmccpay"+serialNo,"2");
		}
	}

}
