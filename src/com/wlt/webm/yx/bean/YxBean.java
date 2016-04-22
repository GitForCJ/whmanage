package com.wlt.webm.yx.bean;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;


import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.scputil.MD5Util;
import com.wlt.webm.tool.BASE64Encoder;
import com.wlt.webm.tool.Tools;

/**
 * 北京易迅充值
 * 
 * @ClassName: YxBean
 * @author 谭万龙
 * @date 2014-5-22 上午11:08:51
 */
public class YxBean {
	/**
	 * 易迅充值
	 * 
	 * @param phone
	 *            11位手机号
	 * @param orderID
	 *            26位 订单号
	 * @param fee
	 *            元
	 * @param str
	 *            [] str[0] //号码运营商 YD 移动,UN联通，DX电信 str[1] //号码归属
	 * @param gh 
	 * @return 0 成功 1 充值消息发送失败 -1 系统繁忙 -2 无对应充值接口 2 超时 3失败
	 */
	public int yxFill(String phone, String orderID, String fee, String str[], String gh) {
		int flag = 0;
		HttpClient client = new HttpClient();
		PostMethod post=null;
		try {
			Log.info("易迅开始充值...");
			BiProd biProd = new BiProd();
			String cpid = YxConstant.CPID;
			String trade_type = YxConstant.TRADE_TYPE_CZ;// 交易类型
			String operator = null;// 号码运营商 YD 移动,UN联通，DX电信
			String province = null;// 号码归属
			String create_time = Tools.getNow3();// 时间戳记
			BASE64Encoder b = new BASE64Encoder();
			if (null != str && str.length == 2) {
				operator = str[0];
				province = URLEncoder.encode(b.encode(str[1].getBytes()));
			}
			String s = "cpid=" + cpid + "&trade_type=" + trade_type
					+ "&operator=" + operator + "" + "&province=" + province
					+ "&create_time=" + create_time + "" + "&mobile_num="
					+ phone + "&cp_order_no=" + orderID + "&amount="
					+  Integer.parseInt(fee)*100 + "&ret_para=";
			String Md5Src = "cpid=" + cpid + "&trade_type=" + trade_type
					+ "&operator=" + operator + "" + "&province="
					+ b.encode(str[1].getBytes()) + "&create_time="
					+ create_time + "" + "&mobile_num=" + phone
					+ "&cp_order_no=" + orderID + "&amount="
					+ Integer.parseInt(fee)*100 + "&ret_para=" + YxConstant.KEY;
			String sign = MD5Util.MD5Encode(Md5Src, "UTF-8");
			String urlto = YxConstant.FILL_URL + s + "&sign=" + sign;
			Log.info("易迅充值连接:"+urlto);
			post = new PostMethod(urlto);
			int status = client.executeMethod(post);
			if (status == 200) {
				String result = post.getResponseBodyAsString();
				if (result != null && result.length() > 0) {
					String resCode = result.substring(result.length() - 4, result.length());//截取最后四位
					Log.info("易迅返回结果充值码:"+resCode);
					if ("0000".equals(resCode)) {
						int n = 0;
						if(null!=gh&&gh.equals("OF")){  //殴飞供货
							while (n <= 9) {
								n++;
								int k = yxQuey(orderID,phone);
								if (k == 0) {
									flag = 0;// 成功
									break;
								} else if (k == 1) {
									flag = -1; // 失败
									break;
								} else if (k == -1) {
									flag = -2;//处理中
									continue;
								}
								Thread.sleep(20000);
							}
							if (n >= 9) {
								flag = -2;// 超时或其他错误
							}
						}else{
							flag=0;
						}
					}else{
						flag = -1; // 失败
					}
				}
			} else {
				flag = -1; // 失败
			}
		} catch (Exception e1) {
			Log.error("易迅充值异常"+e1);
			flag = -2;
		} 
		finally
		{
			 post.releaseConnection();
			((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown(); 
			if(null!=client)
			{
				client=null;
			}
		}
		Log.info("手支flag=="+flag);
		return flag;
	}
	 /**
	  * 北京易迅订单查询
	  * @param OrderNo 订单号
	  * @param phone 手机号
	  * @return
	  * @throws HttpException
	  * @throws IOException
	  */
	 public int yxQuey(String OrderNo,String phone) throws HttpException, IOException{
			int flag=0;
			String signSource="cpid="+YxConstant.CPID+"&cp_order_no="+OrderNo+"&mobile_num="+phone;
			String sign=MD5Util.MD5Encode(signSource+YxConstant.KEY, "UTF-8");
			String url=YxConstant.QUERY_URL+signSource+"&sign="+sign;
			Log.info("易迅查询链接:"+url);
			HttpClient client = new HttpClient();
			PostMethod post =new PostMethod(url);
		    int status = client.executeMethod(post);
		    if(status==200){
				String result = post.getResponseBodyAsString();
				Log.info("易迅查询请求结果:"+result);
				String resCode = result.substring(result.length() - 4, result.length());//截取最后四位
				if(resCode.equals("0000")){
					flag=-1;//处理中
				}else if (resCode.equals("0001")) {
					flag=0;//充值成功
				}else if (resCode.equals("0002")) {
					flag=1;//充值失败
				}else if (resCode.equals("0003")) {
					flag=1;//找不到相应的订单
				}else if (resCode.equals("0004")) {
					flag=1;//其他错误
				}
		    }
			post.releaseConnection();
			((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown(); 
			if(null!=client)
			{
				client=null;
			}
			return flag;
			 
		 }
	 

	public static void main(String[] args) throws Exception {
		YxBean yx = new YxBean();
		String str[] = { "YD", "广东" };
		yx.yxFill("13424335775", "wh1111111111", "30", str,null);
		int con=yx.yxQuey("wh1111111111", "13424335775");
		System.out.println(con);
	}
}
