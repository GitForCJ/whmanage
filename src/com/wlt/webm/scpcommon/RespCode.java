/**
 * 
 */
package com.wlt.webm.scpcommon;

/**
 * 返回码信息
 * 创建日期：2013-11-18
 * caiSJ-12-27
 * Company：深圳市万恒科技有限公司
 * Copyright: Copyright (c) 2008
 * @author caiSJ
 */
public class RespCode {
	
	/**
	 * 交易成功
	 */
	public final static String RC_SUCCESS = "000";
	/**
	 * 交易成功
	 */
	public final static String TY = "0000";
	
	/**
	 * 交易失败--交易包格式错误
	 */
	public final static String RC_PKGERR = "001";
	
	/**
	 * 交易失败--未知原因
	 */
	public final static String RC_FAIL = "002";

	/**
	 * 超时,交易被取消
	 */
	public final static String RC_TIMEOUT = "003";
	
	/**
	 * 该账户正在交费
	 */
	public final static String RC_DOINGNOW = "004";
	
	/**
	 * MAC校验错误
	 */
	public final static String RC_MACERR = "005";
	
	/**
	 * 数据库操作异常
	 */
	public final static String RC_DATEBASE_ERR = "006";
	
	/**
	 * 统计日期跨度必须小于2个月
	 */
	public final static String RC_COUNTDATE_ERR = "007";
	
	/**
	 * 系统锁定,无法交易
	 */
	public final static String RC_SYSLOCK = "010";
	
	/**
	 * 未定义的交易码
	 */
	public final static String RC_NOSCODE = "011";

	/**
	 * 资金帐户不存在
	 */
	public final static String RC_FACCT_NOFIND = "020";

	/**
	 * 统一支付帐户密码错误
	 */
	public final static String RC_FACCT_PWDERR = "021";

	/**
	 * 资金帐户已经销户
	 */
	public final static String RC_FACCT_DROP = "022";

	/**
	 * 资金帐户余额不足
	 */
	public final static String RC_FACCT_NOLEFT = "023";
	
	/**
	 * 此笔交易金额超过资金账户单笔消费最高金额
	 */
	public final static String RC_FACCT_OVERONCE = "024";

	/**
	 * 此次交易金额已超过资金账户日消费额度
	 */
	public final static String RC_FACCT_OVERDAYCOST = "025";
	
	/**
	 * 资金帐户尚未设置额度限制
	 */
	public final static String RC_FACCT_NOLIMIT = "026";
	
	/**
	 * 资金账户余额小于预警值
	 */
	public final static String RC_FACCT_LESSWARN = "027";
	
	/**
	 * 银行转账功能已经关闭
	 */
	public final static String RC_SWITCH_OFF = "028";
	/**
	 * 终端信息验证失败
	 */
	public final static String RC_TERMERR ="030";
	
	/**
	 * 终端已经销户
	 */
	public final static String RC_TERMDROP="031";
	
	/**
	 * 呼入号码不是绑定电话
	 */
	public final static String RC_TERMBIND="032";
	
    /**
     * 终端接入号码错误
     */
	public final static String RC_TERMACCESSERR="033";
	
	/**
	 * PSAM卡号不存在
	 */
	public final static String RC_NOPSAMCARD="034";
	
	/**
	 * 向计费发送消息失败
	 */
	public final static String RC_SENDBILL_ERR="035";
	
	/**
	 * 此类卡已售完
	 */
	public final static String RC_NOCARD="036";
	/**
	 * 平台无卡数据
	 */
	public final static String RC_NOCARDDATA = "038";
	/**
	 * 无重打印数据
	 */
	public final static String RC_NOREPRINT_DATA = "039";
	
	/**
	 * 订单已过有效期
	 */
	public final static String RC_ORDER_OVERTIME="040";
	
	/**
	 * 订单不存在
	 */
	public final static String RC_NOFIND_ORDER="041";
	
	/**
	 * 订单已支付
	 */
	public final static String RC_ISPAY_ORDER="042";
	
	/**
	 * 平台无代办信息
	 */
	public final static String RC_QRYBUSI_ORDER="043";
	
	/**
	 * 流水号查询 无此流水号
	 */
	public final static String RC_SERIALQRY_ORDER="044";

	/**
	 * 缴费过于频繁
	 */
	public final static String RC_PAYBUSI="045";
	
	/**
	 * 佣金统计错误 无佣金
	 */
	public final static String RC_COMMSETTERROR="046";
	
	/**
	 * 重复缴费
	 */
	public final static String RC_PAYAGAIN="047";
	
	/**
	 * 
	 */
	public final static String RC_NO_TRADE="050";
	/**
	 * 
	 */
	public final static String RC_BANK_ERR="051";
	/**
	 * 
	 */
	public final static String RC_PAYFEE_ERR="052";
	/**
	 * 
	 */
	public final static String RC_BANKCARD_ERR="053";
	/**
	 * 
	 */
	public final static String RC_BANKCARD_OVER="054";
	/**
	 * 
	 */
	public final static String RC_BANKCARD_LOCK="055";
	/**
	 * 统一支付账户余额不足
	 */
	public final static String RC_BANK_NOLEFT="056";
	/**
	 * 没有公告信息
	 */
	public final static String NOPOSNOTE="057";
	/**
	 * 平台无缴费面值数据
	 */
	public final static String RC_NOFILLFEEDATA = "058";
	/**
	 * 代办户未绑定银行账户
	 */
	public final static String RC_NOBANDBANK = "059";
	/**
	 * 没有欠费
	 */
	public final static String RC_NOAMT="102";
	
	/**
	 * 输入号码不存在
	 */
	public final static String RC_NONBR="110";
	
	/**
	 * 用户密码错误
	 */
	public final static String RC_ERRPASS="111";
	
	/**
	 * 不能返销
	 */
	public final static String RC_NOTCANCEL="112";
	
	/**
	 * 不是指定的客户端
	 */
	public final static String RC_ERRCLIENT="113";
	
	/**
	 * 有多局编账号
	 */
	public final static String RC_ERRACCT="114";
	
	/**
	 * 返销流水不存在
	 */
	public final static String RC_NOCANCELSERIAL="115";
	
	/**
	 * 没有要打印的单据
	 */
	public final static String RC_NOPRN="116";
	
	/**
	 * 该笔流水已返销
	 */
	public final static String RC_HASCANCEL="117";
	
	/**
	 * 该笔流水已销账
	 */
	public final static String RC_HASCOMIT="118";
	
	/**
	 * 对账时总额核对不匹配
	 */
	public final static String RC_CHECK_ERROR="200";
	
	/**
	 * 金额错误
	 */
	public final static String RC_AMTERR="222";
	
	/**
	 * 系统错误
	 */
	public final static String RC_SYSERR="666";
	
	/**
	 * 要求对账的明细太多
	 */
	public final static String RC_TOOMUCH="777";
	
	/**
	 * 综服工单取消成功
	 */
	public final static String RC_CANCLE_CUSTORDER="999";
	
	
	//******************** 省代系统新增错误代码 *********************
	/**
	 * 电信查询失败，请稍候再试
	 */
	public final static String RC_QUERY_FAIL = "500";
	
	/**
	 * 该用户充值受限，请咨询10000
	 */
	public final static String RC_FEE_FAIL = "501";
	
	/**
	 * 网络繁忙，请稍候再试
	 */
	public final static String RC_NET_BUSY = "502";
	
	/**
	 * 系统繁忙，请稍候再试
	 */
	public final static String RC_SYSTEM_BUSY ="503";
	
	/**
	 * 保证金余额不足
	 */
	public final static String RC_LEFTFEE = "504";
	
	/**
	 * 系统日结中，请10分钟后再试
	 */
	public final static String RC_SYSTEM_CHECK = "505";

}
