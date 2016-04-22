package com.wlt.webm.business.bean.zdhuawei;

import java.util.HashMap;
import java.util.Map;

public class ZDHuaweiUtil {

	public static Map<String, String> priviceName = new HashMap<String, String>();
	public static Map<String, String> priviceCodeName = new HashMap<String, String>();
	public static Map<String, String> priviceCodeZD = new HashMap<String, String>();

	/**
	 * 根根运营商类型代码获取对应名称
	 */
	public static String getOperatorCode(String operator) {
		if (operator != null) {// 0 是电信 1移动 2联通
			if (operator.equals("电信"))
				return "0";
			else if (operator.equals("移动"))
				return "1";
			else if (operator.equals("联通"))
				return "2";
			else
				return null;
		} else {
			return null;
		}
	}
	/**
	 * 转换操作代码结果 操作结果(200成功，201失败, 202无此订单号, 203无充值记录)
	 * 对应0/2/other
	 * @param status
	 * @return
	 */
	public static  String getQuerySetatus(String status) {
		if (status.equals("0")) {// 作操成功
			return "200";
		} else if (status.equals("2")) {// 无此订单
			return "202";
		} else {// 失败
			return "201";
		}
	}
	/**
	 * 处理时间，20160303161249
	 * 
	 * @return
	 */
	public static String getFormateTime(String time) {
		String str = null;
		try {
			str = time.substring(0, 4) + "-" + time.substring(4, 6) + "-"
					+ time.substring(6, 8) + " " + time.substring(8, 10) + ":"
					+ time.substring(10, 12) + ":" + time.substring(12, 14);
		} catch (Exception e) {
			System.err.println("转换日期格式异常");
		}
		return str;
	}
	/**
	 * 根根运营商类型代码获取对应名称
	 */
	public static String getOperator(String operatorCode) {
		if (operatorCode != null) {// 0 是电信 1移动 2联通
			if (operatorCode.equals("0"))
				return "电信";
			else if (operatorCode.equals("1"))
				return "移动";
			else if (operatorCode.equals("2"))
				return "联通";
			else
				return null;
		} else {
			return null;
		}
	}
	/**
	 * 充值状态查询转换 1020充值中,1021成功,1022失败 分别对应0/1/other
	 * 
	 * @param status
	 * @return
	 */
	public static String getChgStatus(String status) {
		if (status.equals("0")) {// 成功
			return "1021";
		} else if (status.equals("1")) {// 失败
			return "1022";
		} else {// 充值中
			return "1020";
		}

	}
	static {
		priviceName.put("内蒙古", "10");
		priviceName.put("北京", "11");
		priviceName.put("天津", "13");
		priviceName.put("山东", "17");
		priviceName.put("河北", "18");
		priviceName.put("山西", "19");
		priviceName.put("澳门", "22");
		priviceName.put("安徽", "30");
		priviceName.put("上海", "31");
		priviceName.put("江苏", "34");
		priviceName.put("浙江", "36");
		priviceName.put("福建", "38");
		priviceName.put("海南", "50");
		priviceName.put("广东", "51");
		priviceName.put("广西", "59");
		priviceName.put("青海", "70");
		priviceName.put("湖北", "71");
		priviceName.put("湖南", "74");
		priviceName.put("江西", "75");
		priviceName.put("河南", "76");
		priviceName.put("西藏", "79");
		priviceName.put("四川", "81");
		priviceName.put("重庆", "83");
		priviceName.put("陕西", "84");
		priviceName.put("贵州", "85");
		priviceName.put("云南", "86");
		priviceName.put("甘肃", "87");
		priviceName.put("宁夏", "88");
		priviceName.put("新疆", "89");
		priviceName.put("吉林", "90");
		priviceName.put("辽宁", "91");
		priviceName.put("黑龙江", "97");
		// ////////////////////////////////////
		priviceCodeName.put("10", "内蒙古");
		priviceCodeName.put("11", "北京");
		priviceCodeName.put("13", "天津");
		priviceCodeName.put("17", "山东");
		priviceCodeName.put("18", "河北");
		priviceCodeName.put("19", "山西");
		priviceCodeName.put("22", "澳门");
		priviceCodeName.put("30", "安徽");
		priviceCodeName.put("31", "上海");
		priviceCodeName.put("34", "江苏");
		priviceCodeName.put("36", "浙江");
		priviceCodeName.put("38", "福建");
		priviceCodeName.put("50", "海南");
		priviceCodeName.put("51", "广东");
		priviceCodeName.put("59", "广西");
		priviceCodeName.put("70", "青海");
		priviceCodeName.put("71", "湖北");
		priviceCodeName.put("74", "湖南");
		priviceCodeName.put("75", "江西");
		priviceCodeName.put("76", "河南");
		priviceCodeName.put("79", "西藏");
		priviceCodeName.put("81", "四川");
		priviceCodeName.put("83", "重庆");
		priviceCodeName.put("84", "陕西");
		priviceCodeName.put("85", "贵州");
		priviceCodeName.put("86", "云南");
		priviceCodeName.put("87", "甘肃");
		priviceCodeName.put("88", "宁夏");
		priviceCodeName.put("89", "新疆");
		priviceCodeName.put("90", "吉林");
		priviceCodeName.put("91", "辽宁");
		priviceCodeName.put("97", "黑龙江");
		// ////////////////////////////////////
		priviceCodeZD.put("北京", "1");
		priviceCodeZD.put("上海", "2");
		priviceCodeZD.put("天津", "3");
		priviceCodeZD.put("重庆", "4");
		priviceCodeZD.put("河北", "130000");
		priviceCodeZD.put("山西", "140000");
		priviceCodeZD.put("内蒙古", "150000");
		priviceCodeZD.put("辽宁", "210000");
		priviceCodeZD.put("吉林", "220000");
		priviceCodeZD.put("黑龙江", "230000");
		priviceCodeZD.put("江苏", "320000");
		priviceCodeZD.put("浙江", "330000");
		priviceCodeZD.put("安徽", "340000");
		priviceCodeZD.put("福建", "350000");
		priviceCodeZD.put("江西", "360000");
		priviceCodeZD.put("山东", "370000");
		priviceCodeZD.put("河南", "410000");
		priviceCodeZD.put("湖北", "420000");
		priviceCodeZD.put("湖南", "430000");
		priviceCodeZD.put("广东", "440000");
		priviceCodeZD.put("广西", "450000");
		priviceCodeZD.put("海南", "460000");
		priviceCodeZD.put("四川", "510000");
		priviceCodeZD.put("贵州", "520000");
		priviceCodeZD.put("云南", "530000");
		priviceCodeZD.put("西藏", "540000");
		priviceCodeZD.put("陕西", "610000");
		priviceCodeZD.put("甘肃", "620000");
		priviceCodeZD.put("青海", "630000");
		priviceCodeZD.put("宁夏", "640000");
		priviceCodeZD.put("新疆", "650000");
		priviceCodeZD.put("台湾", "710000");
		priviceCodeZD.put("香港", "810000");
		priviceCodeZD.put("澳门", "820000");
		priviceCodeZD.put("全国", "5");
	}
}
