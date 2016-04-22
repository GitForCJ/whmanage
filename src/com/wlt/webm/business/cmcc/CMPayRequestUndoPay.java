package com.wlt.webm.business.cmcc;

import java.io.ByteArrayOutputStream;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.tool.Constant;
import com.wlt.webm.tool.MD5;
import com.wlt.webm.tool.Tools;

public class CMPayRequestUndoPay {
	byte[] sendMsg=null;
	/**
	 * 冲正组包
	 * @return 返回冲正组包
	 */
	public byte[] payMsg(String SepNo,String PayFee,String PayPhone,String serialNo){
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		PayFee=Tools.addLeftZero(PayFee, 10);
		PayPhone=Tools.headFillSpace(PayPhone,13);
		try{
//			包头信息
			bout.write("0132".getBytes());//长度 4
			bout.write(Tools.getCMPayNO().getBytes());//报文序列号 10
			bout.write(Constant.CMPayBusinessType.getBytes());//业务类型 4
			bout.write("000004".getBytes());//消息码 6
			//包体信息
//			bout.write(MD5.encode(Constant.CMPaySignPWD).getBytes());//企业交易密码  32
			String md5=MD5.encode("123456");
			bout.write(md5.getBytes());//加密后企业登录密码 32
			bout.write(PayFee.getBytes());//充值金额 10
			bout.write(PayPhone.getBytes());//客户手机号码 13
			bout.write(SepNo.getBytes());//原合作单位方充值流水 20
			bout.write(serialNo.getBytes());//发起方流水号 20
			bout.write(Constant.CMPaySUPhone.getBytes());//代理商手机号码 13
		}catch(Exception e){
			MsgCache.cmcc.put("cmccRepay"+serialNo,"2");
			Log.error("冲正组包异常");
			Log.error(e);
		}
		Log.info("冲正报文:" + new String(bout.toByteArray()));
		return bout.toByteArray();
	}
	
}
