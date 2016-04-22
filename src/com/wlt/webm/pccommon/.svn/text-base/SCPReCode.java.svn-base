package com.wlt.webm.pccommon;

import java.util.HashMap;
import java.util.Map;

/**
 * SMP和SCP通讯常量<br>
 */
public class SCPReCode {
    /**
     * 常量Map
     */
	private static Map scprecode = new HashMap();
	/**
	 * 设置scp相应信息
	 *
	 */
	static {
		scprecode.put("000", "交易成功");
		scprecode.put("001", "交易包格式错误");
		scprecode.put("002", "其他错误");
		scprecode.put("003", "处理中,交易被取消");
		scprecode.put("004", "该账户正在交费");
		scprecode.put("005", "MAC校验错误");
		scprecode.put("006", "系统处理失败");
		scprecode.put("010", "系统锁定,无法交易");
		scprecode.put("011", "未定义的交易码");
		scprecode.put("020", "资金账户不存在");
		scprecode.put("021", "资金账户密码错误");
		scprecode.put("022", "资金账户已经销户");
		scprecode.put("023", "资金账户余额不足");
		scprecode.put("024", "此笔交易金额超过资金账户单笔消费最高金额");
		scprecode.put("025", "此次交易金额已超过资金账户日消费额度");
		scprecode.put("026", "资金账户尚未设置额度限制");
		scprecode.put("027", "资金账户余额低于预警值");
		scprecode.put("030", "终端信息验证失败");
		scprecode.put("031", "终端已经销户");
		scprecode.put("032", "呼入号码不是绑定电话");		
		scprecode.put("033", "终端接入号码错误");
		scprecode.put("034", "PSAM卡号不存在");	
		scprecode.put("035", "向计费发送消息失败");	
		scprecode.put("036", "此类卡已售完");		
		scprecode.put("045", "缴费过于频繁,请稍后再缴");		
		scprecode.put("047", "正在处理中，请勿重复缴费");	
		scprecode.put("059", "代办户未绑定银行账户");
		scprecode.put("070", "无可用记录");	
		scprecode.put("071", "非本地用户");	
		scprecode.put("072", "异常金额");	
		scprecode.put("073", "该用户帐单超过7个月,在银行无法正常缴费,请通知用户到电信营业厅缴费");	
		scprecode.put("074", "此银行未签订代收费协议,不允许代收电信话费");	
		scprecode.put("075", "该月发票已打印");	
		scprecode.put("076", "总帐不平");			
		scprecode.put("077", "总帐平");			
		scprecode.put("102", "没有欠费");
		scprecode.put("110", "输入号码不存在");
		scprecode.put("111", "用户密码错误");
		scprecode.put("112", "不能返销");
		scprecode.put("113", "不是指定的客户端");
		scprecode.put("114", "有多局编账号");
		scprecode.put("115", "返销流水不存在");
		scprecode.put("116", "没有要打印的单据");
		scprecode.put("117", "该笔流水已返销");
		scprecode.put("118", "该笔流水已销账");
		scprecode.put("200", "对账时总额核对不匹配");
		scprecode.put("222", "金额错误");
		scprecode.put("666", "计费系统错误");
		scprecode.put("777", "要求对账的明细太多");
		
//		******************** 省代系统新增错误代码 *********************
		scprecode.put("500", "电信查询失败，请稍候再试");
		scprecode.put("501", "该用户充值受限，请咨询10000");
		scprecode.put("502", "网络繁忙，请稍候再试");
		scprecode.put("503", "系统繁忙，请稍候再试");
		scprecode.put("504", "总账户保证金余额不足");
		scprecode.put("505", "系统日结中，请10分钟后再试");
		
		scprecode.put("506", "用户处于冷冻期，请咨询10000");
		scprecode.put("507", "用户未使用");
		scprecode.put("508", "用户处于挂失状态");
		scprecode.put("509", "跨省后付费用户暂不提供余额查询");
		scprecode.put("510", "用户进入风险控制名单，请咨询10000");
		scprecode.put("511", "用户归属地区不匹配");
		scprecode.put("512", "非中国电信用户，不能充值");
		scprecode.put("513", "找不到用户归属地");
		scprecode.put("514", "用户资料错误");
		scprecode.put("515", "用户不允许被充值，请咨询10000");
		scprecode.put("516", "VC平台处理中，请稍候再试");
		scprecode.put("517", "VC平台内部错误");
		scprecode.put("518", "VC平台数据库操作失败");
		scprecode.put("519", "VC平台正在升级，暂不能充值");
		scprecode.put("520", "与计费系统连接异常，请稍候再试");
		scprecode.put("521", "VC系统充值失败");
		scprecode.put("522", "充值失败，被充值用户受限，请咨询10000");
		scprecode.put("523", "账户下付费用户过多，不能充值");
		scprecode.put("524", "充值失败，超过被充值用户的最大限额");
		scprecode.put("525", "充值失败，批量充值不可拆分充值");
		scprecode.put("526", "充值失败，拆分充值中某笔失败导致整体回滚");
	}

	/**
	 * 根据响应码获得相应消息
	 * @param scpReCode
	 * @return 响应消息
	 */
	public static String getSCPreMsg(String scpReCode) {
		String mess=(String) scprecode.get(scpReCode);
		if(mess==null){
			mess="其他错误！";
		}
		return mess;
	}
}
