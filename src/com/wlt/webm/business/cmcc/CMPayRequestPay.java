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
	 * 缴费消息组包
	 * @return 返回缴费消息组包
	 * @throws Exception 
	 */
	public byte[] payMsg(String PayPhone,String payFee,String seqNo) throws Exception{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		MobileChargeService service = new MobileChargeService();
		payFee=Tools.addLeftZero(payFee, 10);
		PayPhone=Tools.headFillSpace(PayPhone,13);
		try{
			//包头信息
			bout.write("0112".getBytes());//长度 4
			bout.write(Tools.getCMPayNO().getBytes());//报文序列号 10
			bout.write(Constant.CMPayBusinessType.getBytes());//业务类型 4
			bout.write("000003".getBytes());//消息码 6
			//包体信息
//			bout.write(MD5.encode(Constant.CMPaySignPWD).getBytes());//企业交易密码  32
			String md5=MD5.encode("123456");
			bout.write(md5.getBytes());//加密后企业登录密码 32
			bout.write(payFee.getBytes());//充值金额 10
			bout.write(PayPhone.getBytes());//客户手机号码 13
			bout.write(seqNo.getBytes());//发起方流水号 20
			bout.write(Constant.CMPaySUPhone.getBytes());//代理商手机号码 13
			Log.info("NO"+Tools.getCMPayNO()+"MD5"+MD5.encode("hr0577").getBytes()+"payFee"+payFee+"PayPhone"+PayPhone+"seqNo"+seqNo+"--"+Constant.CMPaySUPhone);
		}catch(Exception e){
			Log.error("缴费组包异常");
			Log.error(e);
			service.insertState("cmccpay"+seqNo,"2");
		}
//		Log.info("缴费组包:" + new String(bout.toByteArray()));
//		System.out.println("缴费报文:" + new String(bout.toByteArray()));
//		System.out.println(new String(bout.toByteArray()).trim().length());
		sendMsg= bout.toByteArray();
//		MsgCache.CMPaysendMsgCache.add(sendMsg);//保存到发送队列
//		System.out.println("连接内容："+sendMsg);
		Log.info("连接内容:"+new String(sendMsg));
		return bout.toByteArray();
	}
	
}
