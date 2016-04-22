package com.wlt.webm.business.AppRequest.baidu;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SDKBaiduPay {

	private static SDKBaiduPay sdkBaiduPay = null;
	private static DateFormat dateFormat = null;

	public SDKBaiduPay() {
		dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	}

	public static SDKBaiduPay getSDKBaiduPay() {
		if (sdkBaiduPay == null) {
			synchronized (SDKBaiduPay.class) {
				if (sdkBaiduPay == null) {
					sdkBaiduPay = new SDKBaiduPay();
				}
			}
		}
		return sdkBaiduPay;
	}

	/**
	 * 获取调起百度钱包的参数
	 * 
	 * @param name
	 *            商品的名称
	 * @param orderNo
	 *            订单号
	 * @param orderCreateTime
	 *            订单创建的时间，格式：YYYYMMDDHHMMSS
	 * @param desc
	 *            商品的描述
	 * @param return_url
	 *            百度钱包主动通知支付结果的地址
	 * @param totalPrice
	 *            支付的总金额，单位 分
	 * @return 成功签名返回 调起百度钱包的支付参数 否则返回 null
	 */
	public String getBdPayParameter(String name, String orderNo,
			String orderCreateTime, String desc, String return_url,
			int totalPrice) {

		/**
		 * 待签名参数
		 */
		StringBuffer orderInfo = new StringBuffer();

		/**
		 * 支付参数
		 */
		StringBuffer orderInfo1 = new StringBuffer();

		try {
			// 支付的币种 1为人民币
			orderInfo.append("currency=1");
			orderInfo1.append("currency=1");

			// 支付超时时间，暂时设为订单创建之后30分钟过期
			orderInfo.append("&expire_time=").append(
					getExpireTime(orderCreateTime));
			orderInfo1.append("&expire_time=").append(
					getExpireTime(orderCreateTime));

			// 商品的描述信息
			orderInfo.append("&goods_desc=").append(desc);
			orderInfo1.append("&goods_desc=").append(
					URLEncoder.encode(desc, "GBK"));

			// 商品名称
			orderInfo.append("&goods_name=").append(name);
			orderInfo1.append("&goods_name=").append(
					URLEncoder.encode(name, "GBK"));

			// 请求参数的字符编码，GBK
			orderInfo.append("&input_charset=1");
			orderInfo1.append("&input_charset=1");

			// 订单创建的时间
			orderInfo.append("&order_create_time=").append(orderCreateTime);
			orderInfo1.append("&order_create_time=").append(orderCreateTime);

			// 订单号
			orderInfo.append("&order_no=").append(orderNo);
			orderInfo1.append("&order_no=").append(orderNo);

			// 支付方法,目前默认取值为2
			orderInfo.append("&pay_type=").append("2");
			orderInfo1.append("&pay_type=").append("2");

			// 支付结果通知地址
			orderInfo.append("&return_url=").append(return_url);
			orderInfo1.append("&return_url=").append(return_url);

			// 服务编号 目前默认为1
			orderInfo.append("&service_code=").append("1");
			orderInfo1.append("&service_code=").append("1");

			// 签名方式 1为MD5,2为SHA1
			orderInfo.append("&sign_method=").append("1");
			orderInfo1.append("&sign_method=").append("1");

			// 商户号
			orderInfo.append("&sp_no=").append(PartnerConfig.PARTNER_ID);
			orderInfo1.append("&sp_no=").append(PartnerConfig.PARTNER_ID);

			// 支付总金额
			orderInfo.append("&total_amount=").append(
					String.valueOf(totalPrice));
			orderInfo1.append("&total_amount=").append(
					String.valueOf(totalPrice));

			// 接口版本号
			orderInfo.append("&version=").append(String.valueOf("2"));
			orderInfo1.append("&version=").append(String.valueOf("2"));

			// 签名参数
			String signed = MD5.toMD5(orderInfo.toString() + "&key="
					+ PartnerConfig.MD5_PRIVATE);

			return orderInfo1.toString() + "&sign=" + signed;

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取支付超时时间，设置为订单创建时间之后30分钟
	 * 
	 * @return
	 * @throws ParseException
	 */
	private String getExpireTime(String orderCreateTime) throws ParseException {
		return dateFormat
				.format(dateFormat.parse(orderCreateTime).getTime() + 1000 * 60 * 30);
	}

	/**
	 * 字符转换从UTF-8到GBK
	 * 
	 * @param gbkStr
	 * @return
	 */
	private byte[] getUTF8toGBKString(String gbkStr) {
		int n = gbkStr.length();
		byte[] utfBytes = new byte[3 * n];
		int k = 0;
		for (int i = 0; i < n; i++) {
			int m = gbkStr.charAt(i);
			if (m < 128 && m >= 0) {
				utfBytes[k++] = (byte) m;
				continue;
			}
			utfBytes[k++] = (byte) (0xe0 | (m >> 12));
			utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
			utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
		}
		if (k < utfBytes.length) {
			byte[] tmp = new byte[k];
			System.arraycopy(utfBytes, 0, tmp, 0, k);
			return tmp;
		}
		return utfBytes;
	}
}
