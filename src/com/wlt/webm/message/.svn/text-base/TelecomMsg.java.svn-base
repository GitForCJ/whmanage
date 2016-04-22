package com.wlt.webm.message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.Tools;
/**
 * <p>Description: 消息缓冲区，包含接收消息缓冲，测试连接缓冲</p>
 * @version 1.0.0.0
 * 
 */
public class TelecomMsg {

	/**
	 * 报文头标识
	 */
	public static final byte[] head = { 0x01 };

	/**
	 * 报文尾标识
	 */
	public static final byte[] tail = { 0x04 };

	/**
	 * 分隔符
	 */
	public static final byte[] separator = { 0x7E };

	/**
	 * 银行标识
	 */
	public static byte[] bankFlag = { 0x35, 0x38 };

	/**
	 * 计费测试连接标识
	 */
	public static final String TELECOM_TEST = "66";

	/**
	 * 计费查询标识
	 */
	public static final String TELECOM_QUERY = "01";

	/**
	 * 计费缴费标识
	 */
	public static final String TELECOM_FILL = "02";

	/**
	 * 计费返销标识
	 */
	public static final String TELECOM_REVERT = "04";

	/**
	 * 计费核对缴费标识
	 */
	public static final String TELECOM_CHECK_FILL = "05";

	/**
	 * 协议版本
	 */
	public static byte[] version = { 0x31 };

	/**
	 * 电信自增流水号
	 */
	private static int seq = 0;

	/**
	 * 获取自增流水号
	 * 
	 * @return int 流水号 8位
	 */
//	private static synchronized int getSeq() {
//		// 流水号自增
//		seq++;
//		// 如果大于99999996，则初始化为0
//		if (seq > 99999996) {
//			seq = 0;
//		}
//		return seq;
//	}
	
	/**
	 * 获取自增流水号
	 * 
	 * @return int 流水号 6位
	 */
	public static synchronized String getSerial() {
		// 流水号自增
		seq++;
		// 如果大于99999996，则初始化为0
		if (seq > 999999) {
			seq = 0;
		}
		return Tools.add0(String.valueOf(seq), 6);
	}

	/**
	 * 交易流水号 ddhhmmss + 自增流水号
	 * 
	 * @return 流水号
	 */
//	public static String getCheckMsg() {
//		return Tools.getOptTime() + Tools.headFillZero(getSeq() + "", 8);
//	}

	/**
	 * 获得返销组包消息
	 * 
	 * @param list
	 * @return
	 */
	public static byte[] getRevertMsg(ArrayList list) {
		byte[] sendMsg = null;
		ArrayList query = new ArrayList();
		query.add(((String) list.get(3)).getBytes());// 流水号
		query.add(TELECOM_REVERT.getBytes());// 交易码
		query.add(bankFlag);// 银行编码
		query.add(((String) list.get(4)).getBytes());// 返销流水
		query.add(Tools.getNowDate().getBytes());// 日期
		query.add(Tools.getTime().getBytes());// 时间

		sendMsg = packMsg(query);

		return sendMsg;
	}

	/**
	 * 获得查询组包消息
	 * 
	 * @param list
	 * @return
	 */
	public static byte[] getQueryMsg(ArrayList list) {
		byte[] sendMsg = null;
		ArrayList query = new ArrayList();
		query.add(((String) list.get(3)).getBytes());// 流水号
		query.add(TELECOM_QUERY.getBytes());// 交易码
		query.add(bankFlag);// 银行编码
		query.add(((String) list.get(2)).getBytes());// 电话号码
		query.add(Tools.getNowDate().getBytes());// 日期
		query.add(Tools.getTime().getBytes());// 时间
		query.add(version);// 协议

		sendMsg = packMsg(query);

		return sendMsg;
	}
	
	/**
	 * 获得缴费组包消息
	 * 
	 * @param list
	 * @return
	 */
	public static byte[] getFillMsg(ArrayList list) {
		byte[] fillMsg = null;
		ArrayList fill = new ArrayList();
		fill.add(((String) list.get(3)).getBytes());// 流水号
		fill.add(TELECOM_FILL.getBytes());// 交易码
		fill.add(bankFlag);// 银行编码
		fill.add(((String) list.get(2)).getBytes());// 电话号码
		String payFee = Tools.fenToYuan((String)list.get(4));//缴费总金额
		//fill.add(((String) list.get(5)).getBytes());// 欠费金额
		//fill.add(((String) list.get(6)).getBytes());// 实时金额
		fill.add(payFee.getBytes());//欠费金额（缴费金额）
		fill.add("0.00".getBytes());// 实时金额
		fill.add(Tools.getNowDate().getBytes());// 日期
		fill.add(Tools.getTime().getBytes());// 时间
		fill.add(version);// 协议

		fillMsg = packMsg(fill);
		return fillMsg;
	}

	/**
	 * 获得测试连接消息
	 * 
	 * @return
	 */
	public static byte[] getConnTest() {
		byte[] out = null;
		ArrayList conn = new ArrayList();
		conn.add("00000000".getBytes());// 流水号
		conn.add(TELECOM_TEST.getBytes()); // 交易码
		conn.add(Tools.getNowDate().getBytes()); // 交易日期
		conn.add(Tools.getTime().getBytes()); // 交易时间
		out = packMsg(conn);
		return out;
	}

	/**
	 * 组装计费消息报
	 * @param list	消息包字段信息
	 * @return		计费消息包
	 */
	public static byte[] packMsg(ArrayList list) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayOutputStream temp = new ByteArrayOutputStream();
		try {
			int size = list.size();
			for (int i = 0; i < size; i++) {
				temp.write(separator);
				temp.write((byte[]) list.get(i));
			}
			int length = temp.toByteArray().length;
			byte[] msglen = String.valueOf(
					String.valueOf(length).length() + length).getBytes();

			out.write(head);
			out.write(msglen);
			out.write(temp.toByteArray());
			out.write(tail);

		} catch (IOException e) {
			Log.error("组装计费消息错误!", e);
		}
		return out.toByteArray();
	}
	
	
	/**
	 * 返回给计费响应码信息
	 */
	public static HashMap telecomRecode = new HashMap();
	
	/**
	 * 返回给SCP响应码信息
	 */
	public static HashMap reScp = new HashMap();
	
	/**
	 * 初始深圳电信计费返回码
	 */
 static
	{
		
		reScp.put("0000", "000");//交易成功
		reScp.put("3001", "110");//用户不存在
		reScp.put("0015", "500");//电信查询失败，请稍候再试
		reScp.put("3013", "501");//该用户充值受限，请咨询10000
		reScp.put("0006", "502");//网络繁忙，请稍候再试
		reScp.put("4003", "503");//VC系统繁忙，请稍候再试
		reScp.put("4101", "503");
		reScp.put("4301", "504");//总账户保证金余额不足
		reScp.put("9002", "503");//系统超时
		reScp.put("4300", "505");//系统日结中，请10分钟后再试
		
		reScp.put("3002", "506");//用户处于冷冻期，请咨询10000
		reScp.put("3003", "507");//用户未使用
		reScp.put("3004", "508");//用户处于挂失状态
		reScp.put("3007", "509");//跨省后付费用户暂不提供余额查询
		reScp.put("3008", "510");//用户进入风险控制名单，请咨询10000
		reScp.put("3009", "511");//用户归属地区不匹配
		reScp.put("3010", "512");//非中国电信用户，不能充值
		reScp.put("3011", "513");//找不到用户归属地
		reScp.put("3012", "514");//用户资料错误
		reScp.put("3013", "515");//用户不允许被充值，请咨询10000
		reScp.put("4001", "516");//VC平台超时，请稍候再试
		reScp.put("4002", "517");//VC平台内部错误
		reScp.put("4004", "518");//VC平台数据库操作失败
		reScp.put("4006", "519");//VC平台正在升级，暂不能充值
		reScp.put("4102", "518");
		reScp.put("4103", "516");
		reScp.put("4104", "520");//与计费系统连接异常，请稍候再试
		reScp.put("4105", "521");//VC系统充值失败
		reScp.put("4106", "522");//充值失败，被充值用户受限，请咨询10000
		reScp.put("4112", "519");
		reScp.put("4113", "523");//账户下付费用户过多，不能充值
		reScp.put("5001", "524");//充值失败，超过被充值用户的最大限额
		reScp.put("5002", "525");//充值失败，批量充值不可拆分充值
		reScp.put("5003", "526");//充值失败，拆分充值中某笔失败导致整体回滚
		reScp.put("6800", "500");//计费IMC系统出错，请稍候再试(天讯无错误码提供)
		
		
		
		
		reScp.put("01","000");// 正常、成功
		reScp.put("0", "000");
		reScp.put("99","003");// 超时
		reScp.put("97","000");// 流水号重复
		reScp.put("52","102");// 没有欠费
		reScp.put("51","110");// 没有资料
		reScp.put("50","001");// 数据有误
		reScp.put("94","005");// 系统维护，请稍候
		reScp.put("11","000");// 正常，现在是银行扣款时间
		reScp.put("55","072");// 欠费与缴费金额不一致
		
		reScp.put("0501","118");// 已经销帐
		reScp.put("0502","000");// 没有销帐
		
		reScp.put("9999", "002");//未知错误
		
		
		//天作返回码与平台码的对应
		reScp.put("00000", "000");//交易成功
		reScp.put("00001", "600");//合作商或代理商号码不存在
		reScp.put("00002", "601");//合作商密码错误
		reScp.put("00003", "602");//代理商密码错误
		reScp.put("00005", "603");//代理商状态不正常
		reScp.put("10001", "604");//余额不足
		reScp.put("10002", "605");//金额错误
		reScp.put("10003", "606");//5分钟内不能重复充值
		reScp.put("10004", "607");//时间23:50-00:10交易关闭
		reScp.put("10005", "608");//用户号码非本省号码
		reScp.put("10006", "609");//代理商已超过最大冲正次数
		reScp.put("10007", "610");//充值记录不存在
		reScp.put("90001", "611");//运营商返回超时
		reScp.put("90002", "612");//交易超时
		reScp.put("99998", "613");//MD5校验失败
		reScp.put("99999", "001");//交易失败
		
		//易票联返回码与平台对应码
		reScp.put("8", "801");//充值超时
		reScp.put("1", "802");//充值失败
		reScp.put("0", "000");//交易成功
		reScp.put("3", "804");//充值成功，冲正成功，表示充值已取消
		reScp.put("4", "805");//充值成功，冲正失败，表示充值已到账
		reScp.put("5", "806");//充值失败，未查到结果
		reScp.put("6", "807");//不存在该交易
	}
	
	/**
	 *转换电信缴费响应码为SCP响应码
	 * @param code
	 * @return
	 */
	public static String telcomToScpRecode(String code) {
		if (reScp.get(code) == null) {
			return (String) reScp.get("9999");
		} else {
			return (String) reScp.get(code);
		}
	}

	
}
