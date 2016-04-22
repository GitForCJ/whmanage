package com.wlt.webm.business.AppRequest.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ALiPayUtils {

	// �̻�PID
	public static final String PARTNER = "2088021169412345";
	// �̻��տ��˺�
	public static final String SELLER = "wanheng@wanhengtech.com";
	// �̻�˽Կ��pkcs8��ʽ
	public static final String RSA_PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALa+vr8tkgxCvI/wdheOBUGcQ4FslNTPZkoSPAkMvalUX324kUpMtpDbYGECKoQtZ6MaL1JUvxZIBCy6DBFOzBkrTBoGYNtOYXnIE6VA+SEew/NxmaUpH172lp1TIp427ZnnWOWtNFI9mnd26wI7qfAasKyRzYMCql2ImQ0795ABAgMBAAECgYARM6lO46JAxzNqtS7YjsTN5UCxeSAVXCR7ynWeQHXF76xBcFM01D3HKu9KkmKgKIn932Qe8t094q+J6kVih7FMcG27L5xYJTGFdEjjtZIg0sN9wsV1OMnwWufXw7TnLrpmZCWw6nDi989/X0a9RnA9Ri/W8FHOHlS3FXlQGmiYsQJBAOVV9tnd3QFz83l8p0AmHM4dEWxbF2oWuqtCB95rhSiSEthBtK6ztPSjignXTco4x5ME/Zsqk0MF0lA1p/IIUa0CQQDL/gm4tfZ6Ah9GpkN+Mies+LHfQ8hfVUhCMVMJX8qEGqla3Lfl5wOW9gxkkLcCNN+5oRim2MhR7e+tukSkPwolAkBo6sD5vvRCm+dBJvPk9wqqiMXVyn6VvDw96QFskcfjXaBdawHcAr7ARKj8A5HE9+Dls5zKBdpFCT7EDyf8JC4pAkBPG4Ibnn0qDX6hgEK2Qq7+NieMhJXE2qa9/LDN9tbragd5FBVA09ihh2OA8Fn4Qhbsvtt3gKQWmaYUAiMyarv9AkA4eWsYyk/vd2IA/U6HrMhAJqdypenfkPU49czsreELcvJ1Iwn687llEqw2hxcVGQuuKyomJYBqQIyexzly6dV4";
	// ֧������Կ
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2vr6/LZIMQryP8HYXjgVBnEOBbJTUz2ZKEjwJDL2pVF99uJFKTLaQ22BhAiqELWejGi9SVL8WSAQsugwRTswZK0waBmDbTmF5yBOlQPkhHsPzcZmlKR9e9padUyKeNu2Z51jlrTRSPZp3dusCO6nwGrCskc2DAqpdiJkNO/eQAQIDAQAB";


	/**
	 * 
	 * @param subject
	 *            ��Ʒ����
	 * @param body
	 *            ��Ʒ����
	 * @param price
	 *            ֧������λԪ��������λС��
	 * @param orderNum
	 *            Ψһ������
	 * @param backurl �ص���ַ
	 * @return String
	 */
	private static String getOrderInfo(String subject, String body,
			String price, String orderNum,String backurl) {
		// ǩԼ���������ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// ǩԼ����֧�����˺�
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// �̻���վΨһ������
		orderInfo += "&out_trade_no=" + "\"" + orderNum + "\"";

		// ��Ʒ����
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// ��Ʒ����
		orderInfo += "&body=" + "\"" + body + "\"";

		// ��Ʒ���
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// �������첽֪ͨҳ��·��
		orderInfo += "&notify_url=" + "\"" + backurl + "\"";

		// ����ӿ����ƣ� �̶�ֵ
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// ֧�����ͣ� �̶�ֵ
		orderInfo += "&payment_type=\"1\"";

		// �������룬 �̶�ֵ
		orderInfo += "&_input_charset=\"utf-8\"";

		// ����δ����׵ĳ�ʱʱ��
		// Ĭ��30���ӣ�һ����ʱ���ñʽ��׾ͻ��Զ����رա�
		// ȡֵ��Χ��1m��15d��
		// m-���ӣ�h-Сʱ��d-�죬1c-���죨���۽��׺�ʱ����������0��رգ���
		// �ò�����ֵ������С���㣬��1.5h����ת��Ϊ90m��
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_tokenΪ���������Ȩ��ȡ����alipay_open_id,���ϴ˲����û���ʹ����Ȩ���˻�����֧��
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// ֧��������������󣬵�ǰҳ����ת���̻�ָ��ҳ���·�����ɿ�
		orderInfo += "&return_url=\"m.alipay.com\"";

		// �������п�֧���������ô˲���������ǩ���� �̶�ֵ ����ҪǩԼ���������п����֧��������ʹ�ã�
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * ����ͻ��˵���֧����֧������Ĳ���
	 * 
	 * @param subject ����
	 * @param body ��Ʒ����
	 * @param price �۸� Ԫ  ��λС��
	 * @param orderNum ������ 
	 * @param backurl �ص���ַ
	 * @return �ͻ��˵���֧����֧������Ĳ���
	 */
	public static String getSignOrderInfo(String subject, String body,
			String price, String orderNum,String backurl) {

		// ����
		String orderInfo = getOrderInfo(subject, body, price, orderNum,backurl);

		// �Զ�����RSA ǩ��
		String sign = sign(orderInfo);
		try {
			// �����sign ��URL����
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// �����ķ���֧���������淶�Ķ�����Ϣ
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		
		return payInfo;
	}

	/**
	 * �Զ�����Ϣ����ǩ��
	 * 
	 * @param content
	 *            ��ǩ��������Ϣ
	 */
	public static String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * ��ȡǩ����ʽ
	 */
	private static String getSignType() {
		return "sign_type=\"RSA\"";
	}
}
