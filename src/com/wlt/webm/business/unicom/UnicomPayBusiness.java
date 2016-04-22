package com.wlt.webm.business.unicom;


import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.tool.Constant;
import com.wlt.webm.tool.Tools;

/**
 */
public class UnicomPayBusiness {


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
	 * 自增流水号
	 */
	private String frameID = null;
    /**
     * 构造方法
     * @param PayPhone 
     * @param payFee 
     * @param serialNo 
     */
    public UnicomPayBusiness(String PayPhone,String payFee,String serialNo)
    {
        this.PayPhone = PayPhone;
        this.payFee = payFee;
        this.serialNo = serialNo;
    }

	/**
	 * 业务流程
	 */
	public void deal() {
		RequestFill fill = new RequestFill();
		frameID = Unicom.getFrameID();
		try {
			//缴费组包
			String macStr = new String(Constant.FACTORYID)+new String(Constant.TERMINALID)+frameID+new String(Constant.UNIPASSWORD);
			fill.setBillValue((Tools.add0(payFee, 10)).getBytes());// 缴费金额
			fill.setFrameID(frameID.getBytes()) ;// 包头流水号
			fill.setMacStr(Unicom.getMacStr(macStr));
			fill.setReqTraceID(serialNo.getBytes());// 充值流水号
			fill.setUserNumber((Tools.endFillSpace(PayPhone, 20).getBytes()));
			String type=Constant.CMD_FILL_TYPE;
			fill.setType(type.getBytes());
			byte[] sendMsg = fill.fillMsg();
			MsgCache.sendMsgCache.add(sendMsg);//保存到发送队列
		} catch (Exception e) {
			Log.error("移动缴费错误", e);
			MsgCache.unicom.put("unicompay"+serialNo,"2");
		}
	}

}
