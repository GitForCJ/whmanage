package com.wlt.webm.ten.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.ten.service.MD5Util;

/**
 * 鼎信流量
 * 
 * @author 1989
 * 
 */
public class QbZhiTongChe {
	static final String mch_id = "qbas_hrdtech";
	static final String sign = "hrdt_132182670168";
	static final String backURL = "";
	static final String fillURL="http://esales.qq.com/cgi-bin/esales_sell_dir.cgi?";
	static MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
	static HttpClient client = new HttpClient(manager);
	static {
		client.getHttpConnectionManager().getParams().setConnectionTimeout(
				1000 * 60);
		client.getHttpConnectionManager().getParams().setSoTimeout(1000 * 60);
	}

	/**
	 * 特直通车Q币充值请求
	 * @param tran_date 交易日期yyyyddMM
	 * @param tran_seq 订单号
	 * @param in_acct 账号
	 * @param num   数量
	 * @param timestamp 时间戳
	 * @return
	 */
	public static String fill(String tran_date,String tran_seq,String in_acct,
			String num,long timestamp) {
		String str="mch_id="+mch_id+"&tran_seq="+
		tran_seq+"&tran_date="+tran_date+"&in_acct="+in_acct+
        "&in_acct_type=0&sell_type=1&sell_sub_type=0&num="+num+"&ret_type=1&ret_url="+backURL;
		//密钥
		System.out.println("签名内容:"+str+"&time="+timestamp+"||"+sign);
		String userkey = MD5Util.MD5Encode(str+"&time="+timestamp+"||"+sign,"UTF-8");
		String url = fillURL +str+"&time="+timestamp+"&sign=" + userkey;
		System.out.println("请求url:"+url);
		String rs = OP(url, tran_seq, "FILL");
		System.out.println("返回结果:"+rs);
		if (null != rs) {
		return rs;
		}
		return "error";
		//return "ret=2&mch_id=qbas_hrdtech&tran_date=20160316&tran_seq=0009&in_acct=649949633&in_acct_type=0&sell_type=1&sell_sub_type=0&num=2&time=1458120373&ret_msg=?????????&sign=b3452cd044b9e450b245d4528503d1c6";
	}

	/**
	 * 操作
	 * @param url
	 * @param id
	 * @param ops
	 * @return
	 */
	public static String OP(String url, String id, String ops) {
		GetMethod postMt = null;
		try {
			postMt = new GetMethod(url);
			int status = client.executeMethod(postMt);
			Log.info(id + " 直通车操作" + ops + "请求应答码:" + status);
			if (200 == status) {
				String result = convertStreamToString(postMt
						.getResponseBodyAsStream());
				return result;
			}
		} catch (Exception e) {
			Log.error(id + " 直通车操作" + ops + " 系统异常" + e.toString());
		} finally {
			postMt.releaseConnection();
		}
		return null;
	}

	public static String convertStreamToString(InputStream is)
			throws IOException {
		StringBuilder sf = new StringBuilder();
		String line;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while ((line = reader.readLine()) != null) {
				sf.append(line);
			}
		} finally {
			is.close();
			if (null != reader) {
				reader.close();
			}
		}
		return sf.toString();
	}

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sf =new SimpleDateFormat("yyyyMMdd hh:mm:ss");
		long time1 = sf.parse("19700101 00:00:00").getTime();
		Date date = new Date();
		long time2 = date.getTime();
		long time = (time2-time1)/1000;
		String str=fill("20160316", "0009", "649949633",
				 "2",System.currentTimeMillis()/1000);
		System.out.println(str);
		System.out.println("===="+str.length());
//		String str="ret=0&mch_id=qbas_hrdtech&tran_date=20160316&tran_seq=0009&in_acct=649949633&in_acct_type=0&sell_type=1&sell_sub_type=0&num=2&time=1458120116&ret_msg=&sign=8a668f80367a9d8a90231936c14c4613";
		System.out.println(str.substring(str.indexOf("ret=")+4, str.indexOf("&mch_id")));
//		 String sign=MD5Util.MD5Encode("mch_id=test_id&tran_seq=0001&tran_date=20081226&in_acct=123456&in_acct_type=0&sell_type=1&sell_sub_type=0&num=1&ret_type=1&ret_url=&time=1230278517||test_12381238", "UTF-8");
//		 System.out.println(sign);
	}
}
