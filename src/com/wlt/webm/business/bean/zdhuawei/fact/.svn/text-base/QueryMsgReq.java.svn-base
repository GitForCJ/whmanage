package com.wlt.webm.business.bean.zdhuawei.fact;

import com.wlt.webm.business.bean.zdhuawei.QueryMsgEntryZD;
import com.wlt.webm.business.bean.zdhuawei.ZDSystemParam;

/**
 * 查询请求
 * @author 1989
 */
public class QueryMsgReq extends ZDSystemParam{
	public QueryMsgEntryZD zdws_params;
	public QueryMsgEntryZD getZdws_params() {
		return zdws_params;
	}
	public void setZdws_params(QueryMsgEntryZD zdws_params) {
		this.zdws_params = zdws_params;
	}
	
	@Override
	public void setZdws_sign(String a) {
		//md5(zdws_key+zdws_user_login+timestamp+platform+token+version+format+phone)
		String signsource=MD5Util.zdwz_key + zdws_user_login
		+ timestamp + platform + token + version + format
		+ zdws_params.getPhone();
		System.out.println("同步产品信息请求签名源数据:"+signsource);
		this.zdws_sign = MD5Util.MD5Encode(signsource, "UTF-8").toUpperCase();
		System.out.println("同步产品信息请求签名结果:"+zdws_sign);
	}
}
