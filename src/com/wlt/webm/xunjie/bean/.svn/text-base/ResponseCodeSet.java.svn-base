package com.wlt.webm.xunjie.bean;

import java.util.HashMap;

/**
 * 返回码 Company：深圳市万恒科技有限公司 Copyright: Copyright (c) 2013
 * 
 * @author caiSJ 创建日期 2013-5-28 version 1.0.0.0
 */
public class ResponseCodeSet {

	/**
	 * 手拉手返回码
	 */
	public static HashMap responseCode = getCode();

	/**
	 * 
	 * @return
	 */
	public static HashMap getCode() {
		HashMap responseCode = new HashMap();
		responseCode.put("000", "订单提交成功");
		responseCode.put("001", "用户不存在");
		responseCode.put("002", "交易密码错误");
		responseCode.put("003", "用户已禁用");
		responseCode.put("004", "余额不足");
		responseCode.put("005", "订单号已存在");
		responseCode.put("006", "系统异常");
		responseCode.put("101", "数据库错误");
		responseCode.put("201", "IP拒绝访问");
		responseCode.put("202", "订单号格式错误");
		responseCode.put("203", "通知URL错误");
		responseCode.put("301", "暂无此面额缴费产品");
		responseCode.put("303", "地区维护");
		responseCode.put("401", "缺少参数");
		responseCode.put("402", "参数错误");
		responseCode.put("403", "充值金额错误");
		responseCode.put("404", "充值号码错误");
		responseCode.put("405", "签名错误");

		responseCode.put("501", "余额查询失败");
		responseCode.put("555", "余额查询成功");
		responseCode.put("600", "订单号不存在");
		responseCode.put("601", "充值成功");
		responseCode.put("602", "充值失败");

		responseCode.put("603", "订单正在处理");
		responseCode.put("610", "订单状态查询失败");
		return responseCode;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)  {
		
		System.out.println(ResponseCodeSet.responseCode.get("000"));
		System.out.println(ResponseCodeSet.responseCode.get("000"));

	}

}
