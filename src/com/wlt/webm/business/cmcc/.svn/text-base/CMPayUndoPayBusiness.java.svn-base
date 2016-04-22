package com.wlt.webm.business.cmcc;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.tool.Constant;
import com.wlt.webm.tool.Tools;

/**
 */
public class CMPayUndoPayBusiness{
	/**
	 * 销账流水号
	 */
	private String sqlNo=null;
	/**
	 * 返销数据
	 */
	private String serialNo=null;
	/**
	 * 缴费号码
	 */
	public final  String PayPhone;

	/**
	 * 缴费金额
	 */
	public final  String payFee;
    /**
     * 构造方法
     * @param PayPhone 
     * @param payFee 
     * @param serialNo 
     * @param sqlNo 
     */
    public CMPayUndoPayBusiness(String PayPhone,String payFee,String serialNo,String sqlNo)
    {
        this.PayPhone = PayPhone;
        this.payFee = payFee;
        this.serialNo = serialNo;
        this.sqlNo = sqlNo;
    }
	/**
	 * 业务流程
	 */
	public void deal() {
		String flag=(String)MsgCache.signCache.get("signFlag");
		if(!flag.equals("success")){
			Log.info("没有成功签到，请稍后再试"+flag);
			CMPayRequestSignIn signIn = new CMPayRequestSignIn();
			byte[] sendMsg=signIn.signInMsg();
			MsgCache.CMPaysendMsgCache.add(sendMsg);//保存到发送队列
			return;
		}
		Log.info("移动冲正业务开始处理");
		CMPayRequestUndoPay undoPay = new CMPayRequestUndoPay();
		try{
			//serialNo=Tools.getCMPaySeq(Constant.CMPaySUPhone.trim(),Constant.CMPayBeginSeq);
			byte[] sendMsg=undoPay.payMsg(serialNo,payFee,PayPhone,sqlNo);
			MsgCache.CMPaysendMsgCache.add(sendMsg);//保存到发送队列
//			MsgCache.addCMPayscpUIC("000004","44134102204620000002", list);
		}catch(Exception e){
			Log.error("移动冲正,解析包错误", e);
		}
	}
}
