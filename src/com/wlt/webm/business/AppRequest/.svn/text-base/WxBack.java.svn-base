package com.wlt.webm.business.AppRequest;

import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.wlt.webm.business.AppRequest.weixin.WxPayResult;
import com.wlt.webm.util.MD5;

public class WxBack extends DispatchAction {
	
	//https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
	private final String API_KEY="86LyGv5u2LTrz240i2nM9duDRrMja4y4";

	/**
	 * 微信回调
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		try {
			String return_code="FAIL";
			String return_msg="失败";
			
			WxPayResult result=checkSign(request.getInputStream());
			System.out.println("微信回调支付结果:"+result.getResult_code());
			if("SUCCESS".equals(result.getResult_code())){
				return_code="SUCCESS";
				return_msg="成功";
				//支付成功 业务处理
				BaiduBack.services_oper(result.getOut_trade_no());
			}
			String rs="<xml><return_code>"+return_code+"</return_code><return_msg>"+return_msg+"</return_msg></xml>";
			PrintWriter out=response.getWriter();
			out.write(rs);
			out.flush();
			out.close();
			System.out.println("微信回调响应成功,订单号:"+result.getOut_trade_no()+",响应内容:"+rs);
		} catch (Exception e) {
			System.out.println("微信回调系统异常，，，ex:"+e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 检验回调的签名是否正确
	 * 
	 * @return 校验成功返回 WxPayResult，否则返回null
	 * @throws Exception
	 */
	public WxPayResult checkSign(InputStream in) throws Exception {
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("xml", WxPayResult.class);
		WxPayResult result = null;

		try {
			result = (WxPayResult) xstream.fromXML(in);
		} catch (Exception e) {
			System.out.println("微信回调解析xml异常，，，ex:"+e);
			throw new Exception("xml解析异常");
		}

		if (null == result) {
			System.out.println("微信回调数据为空,,,,");
			return null;
		}
		System.out.println("微信回调xml内容:"+result);
		if (!"SUCCESS".equals(result.getReturn_code())) {
			System.out.println("微信回调状态码:"+result.getReturn_code());
			return null;
		}
		if (!genParameter(result)) {
			System.out.println("微信回调签名不匹配,,");
			return null;
		}
		return result;
	}
	/**
	 * 校验签名
	 * 
	 * @param result
	 * 
	 * @return 校验通过返回 true,否则返回false
	 */
	private boolean genParameter(WxPayResult result) {
		StringBuilder sb = new StringBuilder();
		if (null != result.getAppid()) {
			sb.append("appid").append("=").append(result.getAppid())
					.append("&");
		}

		if (null != result.getAttach()) {
			sb.append("attach").append("=").append(result.getAttach())
					.append("&");
		}

		if (null != result.getBank_type()) {
			sb.append("bank_type").append("=").append(result.getBank_type())
					.append("&");
		}

		if (null != result.getCash_fee()) {
			sb.append("cash_fee").append("=").append(result.getCash_fee())
					.append("&");
		}

		if (null != result.getCash_fee_type()) {
			sb.append("cash_fee_type").append("=")
					.append(result.getCash_fee_type()).append("&");
		}

		if (null != result.getCoupon_count()) {
			sb.append("coupon_count").append("=")
					.append(result.getCoupon_count()).append("&");
		}

		if (null != result.getCoupon_fee()) {
			sb.append("coupon_fee").append("=").append(result.getCoupon_fee())
					.append("&");
		}

		if (null != result.getCoupon_fee_$n()) {
			sb.append("coupon_fee_$n").append("=")
					.append(result.getCoupon_fee_$n()).append("&");
		}

		if (null != result.getCoupon_id_$n()) {
			sb.append("coupon_id_$n").append("=")
					.append(result.getCoupon_id_$n()).append("&");
		}

		if (null != result.getDevice_info()) {
			sb.append("device_info").append("=")
					.append(result.getDevice_info()).append("&");
		}
		//
		if (null != result.getErr_code()) {
			sb.append("err_code").append("=").append(result.getErr_code())
					.append("&");
		}

		if (null != result.getErr_code_des()) {
			sb.append("err_code_des").append("=")
					.append(result.getErr_code_des()).append("&");
		}

		if (null != result.getFee_type()) {
			sb.append("fee_type").append("=").append(result.getFee_type())
					.append("&");
		}

		if (null != result.getIs_subscribe()) {
			sb.append("is_subscribe").append("=")
					.append(result.getIs_subscribe()).append("&");
		}

		if (null != result.getMch_id()) {
			sb.append("mch_id").append("=").append(result.getMch_id())
					.append("&");

		}

		if (null != result.getNonce_str()) {
			sb.append("nonce_str").append("=").append(result.getNonce_str())
					.append("&");
		}

		if (null != result.getOpenid()) {
			sb.append("openid").append("=").append(result.getOpenid())
					.append("&");
		}

		if (null != result.getOut_trade_no()) {
			sb.append("out_trade_no").append("=")
					.append(result.getOut_trade_no()).append("&");
		}

		if (null != result.getResult_code()) {
			sb.append("result_code").append("=")
					.append(result.getResult_code()).append("&");
		}

		if (null != result.getReturn_code()) {
			sb.append("return_code").append("=")
					.append(result.getReturn_code()).append("&");
		}

		if (null != result.getReturn_msg()) {
			sb.append("return_msg").append("=").append(result.getReturn_msg())
					.append("&");
		}

		if (null != result.getSub_mch_id()) {
			sb.append("sub_mch_id").append("=").append(result.getSub_mch_id())
					.append("&");
		}

		if (null != result.getTime_end()) {
			sb.append("time_end").append("=").append(result.getTime_end())
					.append("&");
		}

		if (null != result.getTotal_fee()) {
			sb.append("total_fee").append("=").append(result.getTotal_fee())
					.append("&");
		}

		if (null != result.getTrade_type()) {
			sb.append("trade_type").append("=").append(result.getTrade_type())
					.append("&");
		}

		if (null != result.getTransaction_id()) {
			sb.append("transaction_id").append("=")
					.append(result.getTransaction_id()).append("&");
		}

		// 拼接密钥
		sb.append("key=");
		sb.append(API_KEY);

		System.out.println("参与签名的参数:" + sb.toString());
		// 加密参数
		String sign = MD5.encode(sb.toString()).toUpperCase();
		System.out.println("参数签名后的结果:" + sign);

		if (!sign.equals(result.getSign())) {
			return false;
		}
		return true;
	}
}
