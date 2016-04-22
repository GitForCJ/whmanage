package com.wlt.webm.business.AppRequest.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ALiPayUtils {

	// 商户PID
	public static final String PARTNER = "2088021169412345";
	// 商户收款账号
	public static final String SELLER = "wanheng@wanhengtech.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALa+vr8tkgxCvI/wdheOBUGcQ4FslNTPZkoSPAkMvalUX324kUpMtpDbYGECKoQtZ6MaL1JUvxZIBCy6DBFOzBkrTBoGYNtOYXnIE6VA+SEew/NxmaUpH172lp1TIp427ZnnWOWtNFI9mnd26wI7qfAasKyRzYMCql2ImQ0795ABAgMBAAECgYARM6lO46JAxzNqtS7YjsTN5UCxeSAVXCR7ynWeQHXF76xBcFM01D3HKu9KkmKgKIn932Qe8t094q+J6kVih7FMcG27L5xYJTGFdEjjtZIg0sN9wsV1OMnwWufXw7TnLrpmZCWw6nDi989/X0a9RnA9Ri/W8FHOHlS3FXlQGmiYsQJBAOVV9tnd3QFz83l8p0AmHM4dEWxbF2oWuqtCB95rhSiSEthBtK6ztPSjignXTco4x5ME/Zsqk0MF0lA1p/IIUa0CQQDL/gm4tfZ6Ah9GpkN+Mies+LHfQ8hfVUhCMVMJX8qEGqla3Lfl5wOW9gxkkLcCNN+5oRim2MhR7e+tukSkPwolAkBo6sD5vvRCm+dBJvPk9wqqiMXVyn6VvDw96QFskcfjXaBdawHcAr7ARKj8A5HE9+Dls5zKBdpFCT7EDyf8JC4pAkBPG4Ibnn0qDX6hgEK2Qq7+NieMhJXE2qa9/LDN9tbragd5FBVA09ihh2OA8Fn4Qhbsvtt3gKQWmaYUAiMyarv9AkA4eWsYyk/vd2IA/U6HrMhAJqdypenfkPU49czsreELcvJ1Iwn687llEqw2hxcVGQuuKyomJYBqQIyexzly6dV4";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2vr6/LZIMQryP8HYXjgVBnEOBbJTUz2ZKEjwJDL2pVF99uJFKTLaQ22BhAiqELWejGi9SVL8WSAQsugwRTswZK0waBmDbTmF5yBOlQPkhHsPzcZmlKR9e9padUyKeNu2Z51jlrTRSPZp3dusCO6nwGrCskc2DAqpdiJkNO/eQAQIDAQAB";


	/**
	 * 
	 * @param subject
	 *            商品名称
	 * @param body
	 *            商品详情
	 * @param price
	 *            支付金额：单位元，保留两位小数
	 * @param orderNum
	 *            唯一订单号
	 * @param backurl 回调地址
	 * @return String
	 */
	private static String getOrderInfo(String subject, String body,
			String price, String orderNum,String backurl) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + orderNum + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + backurl + "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * 构造客户端调起支付宝支付所需的参数
	 * 
	 * @param subject 名称
	 * @param body 商品描述
	 * @param price 价格 元  两位小数
	 * @param orderNum 订单号 
	 * @param backurl 回调地址
	 * @return 客户端调起支付宝支付所需的参数
	 */
	public static String getSignOrderInfo(String subject, String body,
			String price, String orderNum,String backurl) {

		// 订单
		String orderInfo = getOrderInfo(subject, body, price, orderNum,backurl);

		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		
		return payInfo;
	}

	/**
	 * 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public static String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * 获取签名方式
	 */
	private static String getSignType() {
		return "sign_type=\"RSA\"";
	}
}
