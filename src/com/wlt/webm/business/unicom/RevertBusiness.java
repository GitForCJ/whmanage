package com.wlt.webm.business.unicom;

import com.commsoft.epay.util.DateParser;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.pccommon.UniqueNo;
import com.wlt.webm.tool.Constant;
import com.wlt.webm.tool.Tools;

/**
 */
public class RevertBusiness {
	/**	/**
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
	 public RevertBusiness(String PayPhone,String payFee,String serialNo,String sqlNo)
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
		RequestRoll roll = new RequestRoll();
		try{
			//生成计费流水
			//String reqTraceID=Constant.FACTORYID_UNICOM+DateParser.getNowDateTime().substring(4)+UniqueNo.getInstance().getNoTwo();
			//自增流水号
			String frameID = Unicom.getFrameID();
			//冲正组包
			roll.setFrameID(frameID.getBytes());
			roll.setReqTraceID(sqlNo.getBytes());//新流水号
			roll.setOldReqTraceID(serialNo.getBytes());//充值时的流水号
			roll.setUserNumber((Tools.endFillSpace(PayPhone, 20).getBytes()));
			roll.setBillValue(Tools.add0(payFee, 10).getBytes());
			String type=Constant.CMD_ROLL_TYPE;
			roll.setType(type.getBytes());
			String macStr = new String(Constant.FACTORYID)+new String(Constant.TERMINALID)+frameID+new String(Constant.UNIPASSWORD);
			roll.setMacStr(Unicom.getMacStr(macStr));
			byte[] sendMsg = roll.rollMsg();
			MsgCache.sendMsgCache.add(sendMsg);//保存到发送队列
		}catch(Exception e){
			Log.error("广东联通返销,组包错误", e);
			MsgCache.unicom.put("unicomRePay"+sqlNo,"2");
		}finally{
		}
	}
public static void main(String[] args) {
	String reqTraceID=Constant.FACTORYID_UNICOM+DateParser.getNowDateTime().substring(4)+UniqueNo.getInstance().getNoTwo();
	System.out.println("---"+reqTraceID);
}
}
