package com.wlt.webm.business.dianx.bean;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.commsoft.epay.util.logging.Log;

/**
 * <p>
 * Title:电子代办平台
 * </p>
 * <p>
 * Description: 查询接口业务流程处理类
 * </p>
 * <p>
 * create: 2009-9-25
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: 深圳市万恒科技有限公司
 * </p>
 * 
 * @author shenyijie
 * @version 3.0.0.0
 * 
 */
public class ResponseQueryDetail {

	/**
	 * 返回的数据信息
	 */
	private byte[] recvMsg;

	/**
	 * 数据输入流
	 */
	private DataInputStream input;

	/**
	 * 包头
	 */
	private byte[] head = new byte[4];

	/**
	 * 包的标识号
	 */
	private byte[] bagFlag = new byte[10];

	/**
	 * 包总长度
	 */
	private byte[] bagLength = new byte[8];

	/**
	 * 包的类型
	 */
	private byte[] bagType = new byte[8];

	/**
	 * 结构标识
	 */
	private byte[] structFlag = new byte[1];

	/**
	 * 校验标识
	 */
	private byte[] checkFlag = new byte[1];

	/**
	 * 参数个数
	 */
	private byte[] paramsNum = new byte[2];

	/**
	 * 发送系统ID
	 */
	private byte[] sendID = new byte[2];

	/**
	 * 接收系统ID
	 */
	private byte[] receiveID = new byte[2];

	/**
	 * 预留空间
	 */
	private byte[] blankArea = new byte[26];

	/**
	 * 信息鉴真码
	 */
	private byte[] checkCode = new byte[8];

	/**
	 * 请求结果编码
	 */
	private byte[] responseCode;

	/**
	 * 请求信息说明
	 */
	private byte[] responseInfo;

	/**
	 * 费用
	 */
	private String fee;

	/**
	 * 账期标识
	 */
	private String accountFlagAll;

	/**
	 * 分月账期标识
	 */
	private byte[] accountFlag;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 是否可保留零头
	 */
	private String flag;

	/**
	 * 发票项代码
	 */
	private byte[] invoiceCode;

	/**
	 * 发票项名称
	 */
	private byte[] invoiceName;

	/**
	 * 号码
	 */
	private byte[] number;

	/**
	 * 发票项费用
	 */
	private byte[] invoiceFee;

	/**
	 * 运营商代码
	 */
	private byte[] agentCode;

	/**
	 * 发票明细信息
	 */
	private HashMap detailMap;

	/**
	 * 账期
	 */
	private ArrayList date;

	/**
	 * 账期起始时间
	 */
	private String accountBegin = "99999999";

	/**
	 * 账期结束时间
	 */
	private String accountEnd = "00000000";

	/**
	 * 区号
	 */
	private String areaCode;

	/**
	 * MBOSS区号
	 */
	// private static ArrayList listAreaCode;
	/**
	 * 默认构造函数
	 * 
	 */
	public ResponseQueryDetail() {

	}

	public ResponseQueryDetail(byte[] receiveMsg, String areaCode) {
		this.areaCode = areaCode;
		this.recvMsg = receiveMsg;
		if (recvMsg != null) {
			input = new DataInputStream(new ByteArrayInputStream(recvMsg));
			deal();
		}
	}

	/**
	 * 解析接收到的计费消息
	 * 
	 */
	public void deal() {
		try {
			// 解析包头信息
			input.read(head);
			input.read(bagFlag);
			input.read(bagLength);
			input.read(bagType);
			input.read(structFlag);
			input.read(checkFlag);
			input.read(paramsNum);
			input.read(sendID);
			input.read(receiveID);
			input.read(blankArea);
			input.read(checkCode);
			// 解析包体信息
			input.read(new byte[8]);// 000

			input.read(new byte[4]);
			byte[] tem = new byte[4];
			input.read(tem);
			responseCode = new byte[Integer.parseInt(new String(tem).trim())];
			input.read(responseCode);// 请求结果编码
			Log.info("-----请求结果编码：" + new String(responseCode));

			input.read(new byte[4]);
			byte[] temp1 = new byte[4];
			input.read(temp1);
			responseInfo = new byte[Integer.parseInt(new String(temp1).trim())];
			input.read(responseInfo);// 请求结果说明
			Log.info("----请求结果说明：" + new String(responseInfo));

			if (Integer.parseInt(new String(paramsNum)) > 1) {

				String[] temp = new String[8];
				int index = 0;
				input.read(new byte[8]);// A31

				byte[] b1 = new byte[4];
				input.read(b1);
				temp[index++] = new String(b1).trim();
				byte[] temp2 = new byte[4];
				input.read(temp2);
				byte[] fee1 = new byte[Integer.parseInt(new String(temp2)
						.trim())];
				input.read(fee1);// 所有账期欠费总和
				temp[index++] = new String(fee1).trim();

				// if (!listAreaCode.contains(areaCode.trim())) {
				byte[] b2 = new byte[4];
				input.read(b2);
				temp[index++] = new String(b2).trim();
				byte[] temp3 = new byte[4];
				input.read(temp3);
				byte[] accountFlagAll1 = new byte[Integer.parseInt(new String(
						temp3).trim())];
				input.read(accountFlagAll1);// 账期标识
				temp[index++] = new String(accountFlagAll1).trim();
				// }
				byte[] b3 = new byte[4];
				input.read(b3);
				temp[index++] = new String(b3).trim();
				byte[] temp4 = new byte[4];
				input.read(temp4);
				byte[] userName1 = new byte[Integer.parseInt(new String(temp4)
						.trim())];
				input.read(userName1);// 用户名
				temp[index++] = new String(userName1).trim();

				byte[] b4 = new byte[4];
				input.read(b4);
				temp[index++] = new String(b4).trim();
				byte[] temp5 = new byte[4];
				input.read(temp5);
				byte[] flag1 = new byte[Integer.parseInt(new String(temp5)
						.trim())];
				input.read(flag1);// 是否可保留零头
				temp[index] = new String(flag1).trim();

				for (int i = 0; i < temp.length; i += 2) {
					if (temp[i].equals("A310")) {
						fee = temp[i + 1];
					} else if (temp[i].equals("A311")) {
						accountFlagAll = temp[i + 1];
					} else if (temp[i].equals("A312")) {
						userName = temp[i + 1];
					} else if (temp[i].equals("A313")) {
						flag = temp[i + 1];
					}
				}
			}

			// 通过参数组个数判断A32组是否存在（如果不欠费，计费不返回A32）
			if (Integer.parseInt(new String(paramsNum)) == 3) {
				input.read(new byte[3]);
				byte[] temp6 = new byte[3];
				input.read(temp6);
				input.read(new byte[2]);
				int column = Integer.parseInt(new String(temp6).trim());

				detailMap = new HashMap();
				date = new ArrayList();
				String name = "";
				String value = "";
				byte[] accountDate;

				// 对组内行数进行循环
				for (int i = 0; i < column; i++) {

					input.read(new byte[4]);
					byte[] temp7 = new byte[4];
					input.read(temp7);
					invoiceCode = new byte[Integer.parseInt(new String(temp7)
							.trim())];
					input.read(invoiceCode);

					if (!new String(invoiceCode).equals("0")) {// 如果发票项代码不为0，
																// 则表示明细信息
						input.read(new byte[4]);
						byte[] temp8 = new byte[4];
						input.read(temp8);
						invoiceName = new byte[Integer.parseInt(new String(
								temp8).trim())];
						input.read(invoiceName);
						name = new String(invoiceName);// 发票项名称

						input.read(new byte[4]);
						byte[] temp9 = new byte[4];
						input.read(temp9);
						number = new byte[Integer.parseInt(new String(temp9)
								.trim())];
						input.read(number);// 号码

						input.read(new byte[4]);
						byte[] temp10 = new byte[4];
						input.read(temp10);
						invoiceFee = new byte[Integer.parseInt(new String(
								temp10).trim())];
						input.read(invoiceFee);
						value = new String(invoiceFee);// 发票项费用

						if (detailMap.containsKey(name)) { // 如果有相同的明细名称，则将数值合并
							String v = (String) detailMap.get(name);
							value = String.valueOf(Integer.parseInt(value)
									+ Integer.parseInt(v));
						}
						detailMap.put(name, value);

						input.read(new byte[4]);
						byte[] temp11 = new byte[4];
						input.read(temp11);
						accountFlag = new byte[Integer.parseInt(new String(
								temp11).trim())];
						input.read(accountFlag);// 账期标识

						String[] str = new String(accountFlag).split(":");
						if (str.length == 1) {
							if (accountBegin.compareTo(str[0]) <= 0) {

							} else {
								accountBegin = str[0];
							}
						} else if (str.length == 2) {
							if (accountBegin.compareTo(str[0]) <= 0) {

							} else {
								accountBegin = str[0];
							}
							if (accountEnd.compareTo(str[1]) >= 0) {

							} else {
								accountEnd = str[1];
							}
						}

						input.read(new byte[4]);
						byte[] temp12 = new byte[4];
						input.read(temp12);
						agentCode = new byte[Integer
								.parseInt(new String(temp12).trim())];
						input.read(agentCode);// 运营商代码

					} else {// 如果发票项代码为0，则表示特殊含义：账期的起止时间

						input.read(new byte[4]);
						byte[] temp13 = new byte[4];
						input.read(temp13);
						input.read(new byte[Integer
								.parseInt(new String(temp13))]);// 发票项名称

						input.read(new byte[4]);
						byte[] temp14 = new byte[4];
						input.read(temp14);
						accountDate = new byte[Integer.parseInt(new String(
								temp14))];
						input.read(accountDate);// 号码--开始时间
						// date.add(new String(accountDate));

						if (accountBegin.compareTo(new String(accountDate)) <= 0) {

						} else {
							accountBegin = new String(accountDate);
						}

						input.read(new byte[4]);
						byte[] temp15 = new byte[4];
						input.read(temp15);
						accountDate = new byte[Integer.parseInt(new String(
								temp15))];
						input.read(accountDate);// 发票项费用--结束时间
						// date.add(new String(accountDate));

						if (accountEnd.compareTo(new String(accountDate)) >= 0) {

						} else {
							accountEnd = new String(accountDate);
						}

						input.read(new byte[4]);
						byte[] temp16 = new byte[4];
						input.read(temp16);
						input.read(new byte[Integer
								.parseInt(new String(temp16))]);

						input.read(new byte[4]);
						byte[] temp17 = new byte[4];
						input.read(temp17);
						input.read(new byte[Integer
								.parseInt(new String(temp17))]);
					}
				}
			}

		} catch (Exception e) {
			Log.error("解析计费返回的查询消息出错！");
			Log.error(e);
		}
	}

	public static void main(String[] args) {
		System.out.println("FFFF000731045800001555INF0033110030080000000000000000000000000001111111100000102".length());
		String mes = "FFFF000731045800001555INF00331100300800000000000000000000000000011111111000"
				+ "0010200010004000000020004成功A3100104A31000048269A31100010A3120005*金玉A3130000A32014"
				+ "06A321000810000001A3220008套餐费用A105001118929208898A32300044000A311001720120501:20120531A32"
				+ "400010A321000810000102A3220020月基本费"
				+ "(套餐外费用)A105001118929208898A3230003500A311001720120501:20120531A32400010A321000810"
				+ "000103A3220018通信费(套餐外费用)A105001118929208898A32300047375A311001720120501:20120531"
				+ "A32400010A321000810000009A3220008优惠赠送A105001118929208898A3230005-4105A3110017"
				+ "20120501:20120531A32400010A321000810000012A3220008已付费用"
				+ "A105001118929208898A3230005-4000A311001720120501:20120531A32400010A321000810000011"
				+ "A3220008代收费用A105001118929208898A32300042100A311001720120501:20120531A32400010A321"
				+ "000510905A3220006预付金A105001118929208898A3230005-1590A311001720120501:20120531A32400010A"
				+ "321000510902A3220006违约金A105001118929208898A323000212A311001720120501:20120531A32400010" +
						"A321000810000001A3220008套餐费用A105001118929208898A32300044000A311001720120601:20120630" +
						"A32400010A321000810000102A3220020月基本费(套餐外费用)A105001118929208898A3230003500A31100172" +
						"0120601:20120630A32400010A321000810000103A3220018通信费(套餐外费用)A105001118929208898A3230004348" +
						"3A311001720120601:20120630A32400010A321000810000009A3220008优惠赠送A105001118929208898A3230005-" +
						"2506A311001720120601:20120630A32400010A321000810000012A3220008已付费用A105001118929208898A3230005-30" +
						"00A311001720120601:20120630A32400010A321000810000011A3220008代收费用A105001118929208898A32300041500A" +
						"311001720120601:20120630A32400010";
		ResponseQueryDetail del=new ResponseQueryDetail(mes.getBytes(),"0755");
		
	}

	/**
	 * 请求结果编码
	 * 
	 * @return
	 */
	public String getResponseCode() {
		return new String(responseCode).trim();
	}

	/**
	 * 请求结果说明
	 * 
	 * @return
	 */
	public String getResponseInfo() {
		return new String(responseInfo).trim();
	}

	/**
	 * 代理商编号
	 * 
	 * @return
	 */
	public String getAgentCode() {
		return new String(agentCode).trim();
	}

	/**
	 * 发票费用
	 * 
	 * @return
	 */
	public String getInvoiceFee() {
		return new String(invoiceFee).trim();
	}

	/**
	 * 号码
	 * 
	 * @return
	 */
	public String getNumber() {
		return new String(number).trim();
	}

	/**
	 * 发票名称
	 * 
	 * @return
	 */
	public String getInvoiceName() {
		return new String(invoiceName).trim();
	}

	/**
	 * 发票编号
	 * 
	 * @return
	 */
	public String getInvoiceCode() {
		return new String(invoiceCode).trim();
	}

	/**
	 * 是否可保留零头
	 * 
	 * @return
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * 用户名
	 * 
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 账期标识
	 * 
	 * @return
	 */
	public String getAccountFlag() {
		return new String(accountFlag).trim();
	}

	/**
	 * 费用
	 * 
	 * @return
	 */
	public String getFee() {
		return fee;
	}

	/**
	 * 获取发票明细信息
	 * 
	 * @return
	 */
	public HashMap getDetailMap() {
		return detailMap;
	}

	/**
	 * 获取账期信息
	 * 
	 * @return
	 */
	public ArrayList getDate() {
		return date;
	}

	/**
	 * 获取参数组个数
	 * 
	 * @return
	 */
	public int getParamsNum() {
		return Integer.parseInt(new String(paramsNum).trim());
	}

	/**
	 * 获取账期开始时间
	 * 
	 * @return
	 */
	public String getAccountBegin() {
		return accountBegin;
	}

	/**
	 * 获取账期结束时间
	 * 
	 * @return
	 */
	public String getAccountEnd() {
		return accountEnd;
	}

	/**
	 * 获取区号
	 * 
	 * @return
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * MBoss区号
	 */
	/*
	 * static{ // 广州、深圳、东莞、佛山、江门、珠海、清远、梅州、韶关、云浮 listAreaCode.add("020");//广州
	 * listAreaCode.add("0755");//深圳 listAreaCode.add("0769");//东莞
	 * listAreaCode.add("0757");//佛山 listAreaCode.add("0750");//江门
	 * listAreaCode.add("0756");//珠海 listAreaCode.add("0763");//清远
	 * listAreaCode.add("0753");//梅州 listAreaCode.add("0751");//韶关
	 * listAreaCode.add("0766");//云浮 }
	 */
}
