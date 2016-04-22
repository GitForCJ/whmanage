package com.wlt.webm.ten.service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wlt.webm.util.TenpayUtil;

/**
 * 财付通即时到帐请求类<br>
 * * ============================================================================
 * api说明：
 * init(),初始化函数，默认给一些参数赋值，如cmdno,date等。
 * getGateURL()/setGateURL(),获取/设置入口地址,不包含参数值
 * getKey()/setKey(),获取/设置密钥 
 * getParameter()/setParameter(),获取/设置参数值
 * getAllParameters(),获取所有参数
 * getRequestURL(),获取带参数的请求URL
 * doSend(),重定向到财付通支付
 * getDebugInfo(),获取debug信息
 * ============================================================================
 */
public class QBPayRequestHandler extends QBRequestHandler {
	
	/**
	 * 
	 */
	public QBPayRequestHandler(){
		
	}

	/**
	 * @param request
	 * @param response
	 */
	public QBPayRequestHandler(HttpServletRequest request,HttpServletResponse response) {
		
		super(request, response); 

		//支付网关地址
		this.setGateUrl("http://esales.qq.com/cgi-bin/esales_sell_dir.cgi");
//		this.setGateUrl("http://localhost:8080");
	}

	/**
	 * @Override
	 * 初始化函数，默认给一些参数赋值，如cmdno,date等。
	 */
	public void init() {
     /**
(“mch_id=test_id&tran_seq=0001&tran_date=20081226&in_acct=123456&in_acct_type=0&sell_type=1
&sell_sub_type=0&num=1&ret_type=1&ret_url=&time=1230278517||test_12381238” )
      */
		Date now = new Date();
		SimpleDateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
		String strDay = dfDay.format(now);
		
		//商户号
		this.setParameter("mch_id", "");
		//QB交易单号
		this.setParameter("tran_seq", "");
		//日期
		this.setParameter("tran_date", strDay);
		//账户号
		this.setParameter("in_acct", "");
		this.setParameter("in_acct_type", "0");
		this.setParameter("sell_type",  "1");
		this.setParameter("sell_sub_type",  "0");		
		this.setParameter("num",  "");	
		this.setParameter("ret_type",  "2");
		this.setParameter("ret_url",  "");
		this.setParameter("time",  String.valueOf(TenpayUtil.getUnixTime(now)));
		this.setParameter("sign", "");
	}

	/**
	 * @Override
	 * 创建签名
	 */
	protected void createSign() {
		
		//获取参数
		String cmdno = this.getParameter("mch_id");
		String date = this.getParameter("tran_seq");
		String bargainor_id = this.getParameter("tran_date");
		String transaction_id = this.getParameter("in_acct");
		String sp_billno = this.getParameter("in_acct_type");
		String total_fee = this.getParameter("sell_type");
		String fee_type = this.getParameter("sell_sub_type");
		String num = this.getParameter("num");
		String attach = this.getParameter("ret_type");
		String ret_url = this.getParameter("ret_url");
		String time = this.getParameter("time");
		String key = this.getKey();
		
		//组织签名
		StringBuffer sb = new StringBuffer();
		sb.append("mch_id=" + cmdno + "&");
		sb.append("tran_seq=" + date + "&");
		sb.append("tran_date=" + bargainor_id + "&");
		sb.append("in_acct=" + transaction_id + "&");
		sb.append("in_acct_type=" + sp_billno + "&");
		sb.append("sell_type=" + total_fee + "&");
		sb.append("sell_sub_type=" + fee_type + "&");
		sb.append("num=" + num + "&");
		sb.append("ret_type=" + attach + "&");
		sb.append("ret_url=" + ret_url + "&");
		sb.append("time=" + time + "||"+ key);
		
		String enc = TenpayUtil.getCharacterEncoding(
				this.getHttpServletRequest(), this.getHttpServletResponse());
		//算出摘要
		String sign = MD5Util.MD5Encode(sb.toString(), enc).toLowerCase();
				
		this.setParameter("sign", sign);
		
		//debug信息
		this.setDebugInfo(sb.toString() + " => sign:"  + sign);
		
	}
	/**
	 * 获取带参数的请求URL
	 * @return String
	 * @throws UnsupportedEncodingException 
	 */
	public String getRequestURL() throws UnsupportedEncodingException {
		
		this.createSign();	
		String reqPars =this.getDebugInfo().substring(0,this.getDebugInfo().indexOf("||"));// sb.substring(0, sb.lastIndexOf("&"));
		return reqPars+"&sign="+this.getParameter("sign");
		
	}
	
	
}
