package com.wlt.webm.ten.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wlt.webm.util.TenpayUtil;

/**
 * �Ƹ�ͨ��ʱ����Ӧ����<br>
 * ============================================================================
 * api˵����
 * getKey()/setKey(),��ȡ/������Կ
 * getParameter()/setParameter(),��ȡ/���ò���ֵ
 * getAllParameters(),��ȡ���в���
 * isTenpaySign(),�Ƿ�Ƹ�ͨǩ��,true:�� false:��
 * doShow(),��ʾ������
 * getDebugInfo(),��ȡdebug��Ϣ
 * 
 * ============================================================================
 *
 */
public class QBPayResponseHandler extends QBResponseHandler {

	/**
	 * @param request
	 * @param response
	 */
	public QBPayResponseHandler(HttpServletRequest request,HttpServletResponse response) {
		
		super(request, response);
		
	}

	/**
	 * �Ƿ�QBǩ��
	 * @Override
	 * @return boolean
	 */
	public boolean isTenpaySign() {
		
		/**
		 * MD5(ret=xxx&mch_id=xxx&tran_date=xxx&tran_seq=xxx
		 * &in_acct=xxx&in_acct_type=xxx&sell_type=xxx&sell_sub_type=xxx&num=xxx&time=xxx||enc_key)
		 */
		//��ȡ����
		String ret = this.getParameter("ret");
		String mch_id = this.getParameter("mch_id");
		String tran_date = this.getParameter("tran_date");
		String tran_seq = this.getParameter("tran_seq");
		String in_acct = this.getParameter("in_acct");
		String in_acct_type = this.getParameter("in_acct_type");		
		String sell_type = this.getParameter("sell_type");
		String sell_sub_type = this.getParameter("sell_sub_type");
		String num = this.getParameter("num");
		String time = this.getParameter("time");
		String key = this.getKey();
		
		String tenpaySign = this.getParameter("sign").toLowerCase();
		
		//��֯ǩ����
		StringBuffer sb = new StringBuffer();
		sb.append("ret=" + ret + "&");
		sb.append("mch_id=" + mch_id + "&");
		sb.append("tran_date=" + tran_date + "&");
		sb.append("tran_seq=" + tran_seq + "&");
		sb.append("in_acct=" + in_acct + "&");
		sb.append("in_acct_type=" + in_acct_type + "&");
		sb.append("sell_type=" + sell_type + "&");
		sb.append("sell_sub_type=" + sell_sub_type + "&");
		sb.append("num=" + num + "&");
		sb.append("time=" + time + "||"+key);
		
		String enc = TenpayUtil.getCharacterEncoding(
				this.getHttpServletRequest(), this.getHttpServletResponse());
		//���ժҪ
		String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();
		
		//debug��Ϣ
		this.setDebugInfo(sb.toString() + " => sign:" + sign +
				" tenpaySign:" + tenpaySign);
		
		return tenpaySign.equals(sign);
	} 
	
	public static void main(String[] args){
		/**
http://183.37.172.26:8080/pc/operation/qbpayResult.do?ret=1&mch_id=qbas_hrdtech
&tran_date=20120312&tran_seq=qbas_hrdtech01203121758022497&in_acct=316708360&in_acct_type=0
&sell_type=1&sell_sub_type=0&num=1&time=1331546320
&ret_msg=ֱͨ�����׺Ϸ���ǩ����֤����&sign=2dc00bfbef20b385439c9e75fb6aeed3&PcacheTime=1331546320
		 * 
		 * ret=xxx&mch_id=xxx&tran_date=xxx&tran_seq=xxx&in_acct=xxx
		 * &in_acct_type=xxx&sell_type=xxx&sell_sub_type=xxx&num=xxx&time=xxx||enc_key
		 */
		
		String sb="ret=1&mch_id=qbas_hrdtech&tran_date=20120312&tran_seq=qbas_hrdtech01203121758022497&in_acct=316708360&in_acct_type=0&sell_type=1&sell_sub_type=0&num=1&time=1331546320||hrdt_698912896118";
//		String sb="mch_id=test_id&tran_seq=0001&tran_date=20081226&in_acct=123456&in_acct_type=0&sell_type=1&sell_sub_type=0&num=1&ret_type=1&ret_url=&time=1331546320||test_12381238";
		String sign = MD5Util.MD5Encode(sb.toString(), "GBK").toLowerCase();
		System.out.println(sign);
	}
	
}
