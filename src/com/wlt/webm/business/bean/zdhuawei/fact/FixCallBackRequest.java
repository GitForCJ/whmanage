package com.wlt.webm.business.bean.zdhuawei.fact;

import com.wlt.webm.business.bean.zdhuawei.FixCallBackEntity;
import com.wlt.webm.business.bean.zdhuawei.ZDSystemParam;
/**
 * ƽ̨ά��֪ͨ
 * @author fw
 *
 */
public class FixCallBackRequest extends ZDSystemParam{
	private FixCallBackEntity zdws_params;
	public FixCallBackEntity getZdws_params() {
		return zdws_params;
	}

	public void setZdws_params(FixCallBackEntity zdws_params) {
		this.zdws_params = zdws_params;
	}
	@Override
	public void setZdws_sign(String a) {
		//md5(zdws_key+zdws_user_login+timestamp+platform+token+version+format+area+operator)��д
		String signsource = MD5Util.zdwz_key + zdws_user_login + timestamp
				+ platform + token + version + format + zdws_params.getArea()+zdws_params.getOperator();
				
		System.out.println("�ص�����ǩ��Դ����:" + zdws_sign);
		zdws_sign = MD5Util.MD5Encode(signsource, "UTF-8").toUpperCase();
		System.out.println("�ص�����ǩ�����:" + zdws_sign);
	}
}
