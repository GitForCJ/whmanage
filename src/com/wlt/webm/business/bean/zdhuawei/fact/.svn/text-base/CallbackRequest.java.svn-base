package com.wlt.webm.business.bean.zdhuawei.fact;

import com.wlt.webm.business.bean.zdhuawei.CallbackEntityZD;
import com.wlt.webm.business.bean.zdhuawei.ZDSystemParam;

/**
 * 回调请求
 * @author 1989
 * 
 */
public class CallbackRequest extends ZDSystemParam {
	public CallbackEntityZD zdws_params;

	public CallbackEntityZD getZdws_params() {
		return zdws_params;
	}

	public void setZdws_params(CallbackEntityZD zdws_params) {
		this.zdws_params = zdws_params;
	}

	@Override
	public void setZdws_sign(String a) {
		// md5(zdws_key+zdws_user_login+timestamp+platform+token+version+format+
		// phone+card_type+status+price+orderid)
		String signsource = MD5Util.zdwz_key + zdws_user_login + timestamp
				+ platform + token + version + format + zdws_params.getPhone()
				+ zdws_params.getCard_type() + zdws_params.getStatus()
				+ zdws_params.getPrice() + zdws_params.getOrderid();
		System.out.println("回调请求签名源数据:" + zdws_sign);
		this.zdws_sign = MD5Util.MD5Encode(signsource, "UTF-8").toUpperCase();
		System.out.println("回调请求签名结果:" + zdws_sign);
	}
}
