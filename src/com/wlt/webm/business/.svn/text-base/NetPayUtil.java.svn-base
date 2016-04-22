package com.wlt.webm.business;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.wlt.webm.scpcommon.Constant;

import mpi.client.data.OrderData;
import mpi.client.exception.PayException;
import mpi.client.trans.TopPayLink;

public class NetPayUtil {

	/**
	 * 
	 * 1 .  交易中的报文属性长度和规范可参考
	 *      中国银联互联网收单服务平台-商户插件使用手册JAVA版中的第七章（MPI消息域）
	 * 2 .  每个交易对应的属性都有输入要求，商户可参考文档第五章（交易接口）中每个交易
	 * 	        对应的交易报文格式.
	 * 
	 */
	public static OrderData transMoney(String serial,String bankNo,String transFee,String cardType,String cardNo,String cardName){
		Calendar cal = Calendar.getInstance();
		OrderData tstOrderData = new OrderData();
		SimpleDateFormat fullfmt = new SimpleDateFormat("yyyyMMddHHmmss");
		String sDT = fullfmt.format(cal.getTime());
		//以下字段是无密代扣交易必输字段
		//无密代扣交易码：1040，
		tstOrderData.setTranCode("1040");
		//商户ＩＤ（每个商户对应唯一的ID）
		tstOrderData.setMerchantID(Constant.NETPAY_MERCHANT_ID);
		//订单号（生成规则：从商户ID第四位开始的四位[4401]+取商户ID倒数四位[0027]+8位随机序列号）
		tstOrderData.setMerOrderNum(serial);
		//交易时间(注意位数:14位，交易时间和系统当前时间相差不超过2个小时)：20101125112433
		tstOrderData.setTranDateTime(sDT);
		//卡号
		//若输入定制标识号，则卡号可填写卡号的后4位或不填
		tstOrderData.setPan(bankNo);
		//币种（人民币：156）
		tstOrderData.setCurType("156");
		//交易金额(单位：分)
		tstOrderData.setTranAmt(transFee);
		
		//以下字段是无密代扣交易可输字段（未全部列出）
		//持卡人信息(01(证件类别)+310123198808083821(证件编号)+zhangsan(姓名))
		tstOrderData.setChInfo(cardType+cardNo, cardName, "", "", "", "");
		//定制标识号
//		tstOrderData.setUserId("001");
		//风险标志
//		tstOrderData.setRiskFlag("0");
		//业务类型
//		tstOrderData.setBusinessType("40");
		
		try {
			//调用交易接口发送到中国银联互联网收单服务平台并同步接收返回结果
			//商户可根据返回结果进行相关后续处理
			tstOrderData = TopPayLink.CollPayTrans(tstOrderData);
			if (tstOrderData == null) {
				System.out.println("TopPayLink.CollPayTrans error");
			} else {
				//打印返回交易结果码,返回6个0(000000)表示交易成功
				//其他的交易码可参考MPI商户插件文档（附录一）.
				System.out.println("Txn RespCode is : "
						+ tstOrderData.getRespCode());
			}
		} catch (PayException ex) {
			System.out.println("TopPayLink.CollPayTrans error: RespCode =["
					+ ex.getErrCd() + "]");
			tstOrderData.setRespCode(ex.getErrCd());
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("TopPayLink.CollPayTrans error 2");
		}
		return tstOrderData;
	}
	
	/**
	* 
	* 1 .  交易中的报文属性长度和规范可参考
	*      中国银联互联网收单服务平台-商户插件使用手册JAVA版中的第七章（MPI消息域）
	* 2 .  每个交易对应的属性都有输入要求，商户可参考文档第五章（交易接口）中每个交易
	* 	        对应的交易报文格式.
	* 
	*/
	public static OrderData transCancel(String serial,String transFee,String tranOrd,String transTime,String oldFee){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat fullfmt = new SimpleDateFormat("yyyyMMddHHmmss");
		String sDT = fullfmt.format(cal.getTime());
		OrderData tstOrderData = new OrderData();
		//以下字段是退货交易必输字段
		
	    //退货交易码：3030
		tstOrderData.setTranCode("3030");
		//商户ＩＤ（每个商户对应唯一的ID）
		tstOrderData.setMerchantID(Constant.NETPAY_MERCHANT_ID);
		//订单号（生成规则：从商户ID第四位开始的四位[4401]+取商户ID倒数四位[0027]+8位随机序列号）
		tstOrderData.setMerOrderNum(serial);
		//交易时间(注意位数:14位，交易时间和系统当前时间相差不能超过2小时)
		tstOrderData.setTranDateTime(sDT);
		//退货金额(单位：分)
		tstOrderData.setTranAmt(transFee);
		//原订单号
		tstOrderData.setOrgOrderNum(tranOrd);
		//原交易时间（注意位数:14位)
		tstOrderData.setOrgTranDateTime(transTime);
		//原交易金额
		tstOrderData.setOrgTranAmt(oldFee);
		
		//以下字段是消费撤销交易可输字段
		//tstOrderData.setCustName("王七");
		//tstOrderData.setCustEMail("007@126.com");
		//tstOrderData.setProdInfo("IPod");
		try {
			//调用交易接口发送到中国银联互联网收单服务平台并同步接收返回结果
			//商户可根据返回结果进行相关后续处理
			tstOrderData = TopPayLink.PayOtherTrans(tstOrderData);
			if (tstOrderData == null) {
				System.out.println("TopPayLink.PayOtherTrans error");
			} else {
				//打印返回交易结果码,返回6个0(000000)表示交易成功
				//其他的交易码可参考MPI商户插件文档（附录一）.
				System.out.println("Txn RespCode is : "
						+ tstOrderData.getRespCode());
			}
		} catch (PayException ex) {
			System.out.println("TopPayLink.PayOtherTrans error: RespCode =["
					+ ex.getErrCd() + "]");
			tstOrderData.setRespCode(ex.getErrCd());
			ex.printStackTrace();

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("TopPayLink.PayOtherTrans error 2");
		}
		return tstOrderData;
	}
	
	public static String getRandEight(){
		StringBuffer sBuffer = new StringBuffer();
		for(int i=0; i<6; i++){
			sBuffer.append((int)(Math.random()*10+0));
		}
		return sBuffer.toString();
	}
	
	public static String getRandEight1(){
		StringBuffer sBuffer = new StringBuffer();
		for(int i=0; i<8; i++){
			sBuffer.append((int)(Math.random()*10+0));
		}
		return sBuffer.toString();
	}
	public static void main(String[] args) {
		System.out.println(getRandEight());
	}
}
