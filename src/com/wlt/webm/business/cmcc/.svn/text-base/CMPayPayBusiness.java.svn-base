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
	 * 缴费号码
	 */
	public final  String PayPhone;

	/**
	 * 缴费金额
	 */
	public final  String payFee;
	/**
	 * 流水号
	 */
	public final  String serialNo;
    /**
     * 构造方法
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
	 * 业务流程
	 * @throws Exception 
	 */
	public void deal() throws Exception {
		Log.info("移动缴费业务处理开始。");
		MobileChargeService service = new MobileChargeService();
		String flag=(String)MsgCache.signCache.get("signFlag");
		if((flag==null||!flag.equals("success"))){
			Log.info("没有成功签到，请稍后再试"+flag);
			//System.out.println("没有成功签到，请稍后再试");
			CMPayRequestSignIn signIn = new CMPayRequestSignIn();
			byte[] sendMsg=signIn.signInMsg();
			MsgCache.CMPaysendMsgCache.add(sendMsg);//保存到发送队列
			//MsgCache.cmcc.put("cmccpay"+serialNo,"2");
			service.insertState("cmccpay"+serialNo,"2");
			return;
		}
		CMPayRequestPay fill = new CMPayRequestPay();
		try {
//			serialNo=Tools.getCMPaySeq(Constant.CMPaySUPhone.trim(),Constant.CMPayBeginSeq);
//			缴费组包
			byte[] paymsg=fill.payMsg(PayPhone,payFee,serialNo);
//			byte[] paymsg=fill.payMsg(PayPhone,payFee,"44134102204620000001");
			//保存到发送队列
			MsgCache.CMPaysendMsgCache.add(paymsg);
		} catch (Exception e) {
			Log.error("移动缴费错误", e);
			//MsgCache.cmcc.put("cmccpay"+serialNo,"2");
			service.insertState("cmccpay"+serialNo,"2");
		}
	}

}
