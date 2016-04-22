package com.wlt.webm.business.unicom;


import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.tool.Constant;
import com.wlt.webm.tool.Tools;

/**
 */
public class UnicomPayBusiness {


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
	 * ������ˮ��
	 */
	private String frameID = null;
    /**
     * ���췽��
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
	 * ҵ������
	 */
	public void deal() {
		RequestFill fill = new RequestFill();
		frameID = Unicom.getFrameID();
		try {
			//�ɷ����
			String macStr = new String(Constant.FACTORYID)+new String(Constant.TERMINALID)+frameID+new String(Constant.UNIPASSWORD);
			fill.setBillValue((Tools.add0(payFee, 10)).getBytes());// �ɷѽ��
			fill.setFrameID(frameID.getBytes()) ;// ��ͷ��ˮ��
			fill.setMacStr(Unicom.getMacStr(macStr));
			fill.setReqTraceID(serialNo.getBytes());// ��ֵ��ˮ��
			fill.setUserNumber((Tools.endFillSpace(PayPhone, 20).getBytes()));
			String type=Constant.CMD_FILL_TYPE;
			fill.setType(type.getBytes());
			byte[] sendMsg = fill.fillMsg();
			MsgCache.sendMsgCache.add(sendMsg);//���浽���Ͷ���
		} catch (Exception e) {
			Log.error("�ƶ��ɷѴ���", e);
			MsgCache.unicom.put("unicompay"+serialNo,"2");
		}
	}

}
