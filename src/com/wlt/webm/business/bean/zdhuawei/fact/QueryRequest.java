package com.wlt.webm.business.bean.zdhuawei.fact;

import com.wlt.webm.business.bean.zdhuawei.QueryEntityZD;
import com.wlt.webm.business.bean.zdhuawei.ZDSystemParam;

public class QueryRequest extends ZDSystemParam {
	public QueryEntityZD zdws_params;
	
	public QueryEntityZD getZdws_params() {
		return zdws_params;
	}

	public void setZdws_params(QueryEntityZD zdws_params) {
		this.zdws_params = zdws_params;
	}
	
	@Override
	public void setZdws_sign(String a) {
		//md5(zdws_key+zdws_user_login+timestamp+platform+token+version+format+phone+card_type+orderid+starttime+endtime)大写
		String signsource=MD5Util.zdwz_key + zdws_user_login
		+ timestamp + platform + token + version + format
		+ zdws_params.getPhone() + zdws_params.getCard_type()
		+ zdws_params.getOrderid() + zdws_params.getStarttime()
		+ zdws_params.getEndtime();
		System.out.println("查询请求签名源数据:"+signsource);
		this.zdws_sign = MD5Util.MD5Encode(signsource, "UTF-8").toUpperCase();
		System.out.println("查询请求签名结果:"+zdws_sign);
	}
}
