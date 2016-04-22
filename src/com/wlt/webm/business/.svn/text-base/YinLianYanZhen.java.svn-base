package com.wlt.webm.business;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.wlt.webm.scpcommon.Constant;

import mpi.client.data.OrderData;
import mpi.client.exception.PayException;
import mpi.client.trans.TopPayLink;


public class YinLianYanZhen {

	private  Calendar cal = Calendar.getInstance();
	private String cardNo;//银联卡号
	private String creditNo;//身份证号
	private String creditNoType;//身份证号类别
	private String name;//用户姓名
	
	private String recode;
	
	
	public String getRecode() {
		return recode;
	}
	public void setRecode(String recode) {
		this.recode = recode;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCreditNo() {
		return creditNo;
	}
	public void setCreditNo(String creditNo) {
		this.creditNo = creditNo;
	}
	public String getCreditNoType() {
		return creditNoType;
	}
	public void setCreditNoType(String creditNoType) {
		this.creditNoType = creditNoType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public boolean send()
	{
		/*
		* 
		* 1 .  交易中的报文属性长度和规范可参考
		*      中国银联互联网收单服务平台-商户插件使用手册JAVA版中的第七章（MPI消息域）
		* 2 .  每个交易对应的属性都有输入要求，商户可参考文档第五章（交易接口）中每个交易
		* 	        对应的交易报文格式.
		* 
		*/
		
		
		
		OrderData tstOrderData = new OrderData();
		SimpleDateFormat fullfmt = new SimpleDateFormat("yyyyMMddHHmmss");
		String sDT = fullfmt.format(cal.getTime());
		//以下字段是无密实名认证交易必输字段
		//无密实名认证交易码：4021
		tstOrderData.setTranCode("4021");
		//商户ＩＤ（每个商户对应唯一的ID）
		tstOrderData.setMerchantID(Constant.NETPAY_MERCHANT_ID);
		String serial = Constant.NETPAY_MERCHANT_ID.substring(4,8)+""+Constant.NETPAY_MERCHANT_ID.substring(Constant.NETPAY_MERCHANT_ID.length()-4,Constant.NETPAY_MERCHANT_ID.length())+""+NetPayUtil.getRandEight1();
		//交易时间(注意位数:14位，交易时间和系统当前时间相差不超过2个小时)：20101125112433
		tstOrderData.setTranDateTime(sDT);
		//订单号（生成规则：从商户ID第四位开始的四位[4401]+取商户ID倒数四位[0027]+8位随机序列号）
		tstOrderData.setMerOrderNum(serial);
		//卡号
		//若输入定制标识号，则卡号可填写卡号的后4位或不填
		tstOrderData.setPan(cardNo);
		
		//以下字段是无密实名认证交易可输字段（未全部列出）
		//持卡人信息(01(证件类别)+310123198808083821(证件编号)+zhangsan(姓名))
		tstOrderData.setChInfo(creditNoType+creditNo, name, "", "", "", "");
		//定制标识号
//		tstOrderData.setUserId("001");
		//风险标志
//		tstOrderData.setRiskFlag("0");
		//业务类型
//		tstOrderData.setBusinessType("40");
		
		try {
			//调用交易接口发送到中国银联互联网收单服务平台并同步接收返回结果
			//商户可根据返回结果进行相关后续处理
			tstOrderData = TopPayLink.RemitTrans(tstOrderData);
			if (tstOrderData == null) {
				System.out.println("TopPayLink.RemitTrans error");
				return false;
			} else {
			//打印返回交易结果码,返回6个0(000000)表示交易成功
			//其他的交易码可参考MPI商户插件文档（附录一）.
				
				recode=tstOrderData.getRespCode();
				if(tstOrderData.getRespCode().equals("000000"))
				{
					return true;
				}else
				{
					
					return false;
				}
			//	System.out.println("Txn RespCode is : "+ tstOrderData.getRespCode());
			}
		} catch (PayException ex) {
			System.out.println("TopPayLink.RemitTrans error: RespCode =["+ ex.getErrCd() + "]");
			tstOrderData.setRespCode(ex.getErrCd());
			ex.printStackTrace();
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("TopPayLink.RemitTrans error 2");
			return false;
		}
	}
	
	
	public static void main(String args[])
	{
		YinLianYanZhen test = new YinLianYanZhen();
		test.setCardNo("6222023602899998372");//银联卡号
		test.setCreditNo("310104199009098372");//身份证号
		test.setCreditNoType("01");//身份证类别
		test.setName("互联网");//用户姓名
		System.out.print(test.send());
		System.out.println(test.getRecode());//返回码
	}
}
