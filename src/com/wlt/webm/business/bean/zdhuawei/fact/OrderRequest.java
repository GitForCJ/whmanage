package com.wlt.webm.business.bean.zdhuawei.fact;

import com.wlt.webm.business.bean.zdhuawei.OrderEntityZD;
import com.wlt.webm.business.bean.zdhuawei.ZDSystemParam;

public class OrderRequest extends ZDSystemParam {
	//zdws_key+zdws_user_login+timestamp+platform+token+version+format+phone+area
	// +operator+card_type+cardid+price+orderid
	public OrderEntityZD zdws_params;

	public OrderEntityZD getZdws_params() {
		return zdws_params;
	}

	public void setZdws_params(OrderEntityZD zdws_params) {
		this.zdws_params = zdws_params;
	}

	@Override
	public void setZdws_sign(String a) {
		String signsource=MD5Util.zdwz_key + zdws_user_login
		+ timestamp + platform + token + version + format
		+ zdws_params.getPhone() + zdws_params.getArea()
		+ zdws_params.getOperator() + zdws_params.getCard_type()
		+ zdws_params.getCardid() + zdws_params.getPrice()
		+ zdws_params.getOrderid();
		System.out.println("下单请求签名源数据:"+signsource);
		this.zdws_sign = MD5Util.MD5Encode(signsource, "UTF-8").toUpperCase();
		System.out.println("下单请求签名结果:"+zdws_sign);
	}

}
