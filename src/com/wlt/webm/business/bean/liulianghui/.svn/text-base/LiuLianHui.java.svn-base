package com.wlt.webm.business.bean.liulianghui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.scputil.MD5Util;

/**
 * @author Lijin
 *说明，在发布正式平台上时，请把相应的地址中“.cn”改成“.com”,以及用户账号信息也需改为正式信息
 */
public class LiuLianHui {
	/**
	 * 发布时更改为正式key
	 */
	private static String APP_KEY = "9c9fe4ce062ef84e028733b4c886091c";//测试使用
	/**
	 * 发布时更改为正式user
	 */
	private static String API_USER = "1178623819";//测试使用
	public static void main(String[] args) {
		String danhao = "zb0z20356721809";
		String str = null;
		int a  = LiuLianHui.liuLiangDingGou("13811497810", "1", "30",danhao);					
		System.out.println("充值状态：" + a);
		if (a == 0) {
			System.out.println("提交订单成功/充值成功");
			System.out.println("订单号：" + danhao);
			int bv = liuLiangDingDanFind(danhao);
			System.out.println("执行流量订单查询结果:" + bv);
		}
		str = liuLiangBaoJia();
		System.out.println(str);
		if (str != null) {
			if (str.equals("-1"))
				return;
			else if (str.equals("2"))
				return;
			JSONObject obj2 = JSONObject.fromObject(str);
			try {
				JSONArray array = new JSONArray(obj2.getString("data"));
				for (int i = 0; i < array.length(); i++) {
					org.json.JSONObject obj = array.getJSONObject(i);
					System.out.println("id=" + obj.getInt("id") + "||name="
							+ obj.getString("name") + "||流量包大小="
							+ obj.getString("amount") + "M||价格＝"
							+ obj.getString("price"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		 str=liuLiangBaoFindByMobile("13811497810");
		 System.out.println(str);
		 str=yuEFind();
		 System.out.println(str);
	}

	/**
	 * 帐户余额查询
	 * 
	 * @return String
	 */
	public static String yuEFind() {
		String tr = "app_id=" + API_USER + "_" + APP_KEY;
		String md5 = MD5Util.MD5Encode(tr, "utf-8").toLowerCase();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>(); // 设置参数
		formparams.add(new BasicNameValuePair("app_id", API_USER));
		formparams.add(new BasicNameValuePair("sign", md5));
		String str = post(formparams,
				"http://api.17llh.cn/package/balance_query");
		Log.info(str);
		/*if (str != null) {
			if (str.equals("-1"))
				return "-1";
			else if (str.equals("2"))
				return "2";
			JSONObject obj = JSONObject.fromObject(str);
			System.out.println(obj.getString("msg"));
		}*/
		return str;
	}

	/**
	 * 根据手机号码查询套餐包
	 * 
	 * @param mobile
	 *            /手机号码
	 * @return String
	 */
	public static String liuLiangBaoFindByMobile(String mobile) {
		String tr = "app_id=" + API_USER + "_mobile=" + mobile + "_" + APP_KEY;
		String md5 = MD5Util.MD5Encode(tr, "utf-8").toLowerCase();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>(); // 设置参数
		formparams.add(new BasicNameValuePair("app_id", API_USER));
		formparams.add(new BasicNameValuePair("mobile", mobile));
		formparams.add(new BasicNameValuePair("sign", md5));
		String str = post(formparams,
				"http://api.17llh.cn/package/mobile_query");
		Log.info(str);
		/*if (str != null) {
			if (str.equals("-1"))
				return "-1";
			else if (str.equals("2"))
				return "2";
			JSONObject obj = JSONObject.fromObject(str);
			System.out.println(obj.getString("msg"));
		}*/
		return str;
	}

	/**
	 * 流量报价查询
	 * 
	 * @return String
	 */
	public static String liuLiangBaoJia() {
		String tr = "app_id=" + API_USER + "_" + APP_KEY;
		String md5 = MD5Util.MD5Encode(tr, "utf-8").toLowerCase();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>(); // 设置参数
		formparams.add(new BasicNameValuePair("app_id", API_USER));
		formparams.add(new BasicNameValuePair("sign", md5));
		String str = post(formparams, "http://api.17llh.cn/package/quotation");
		Log.info("报价查询："+str);
		// if (str != null) {
		// if (str.equals("-1"))
		// return "-1";
		// else if(str.equals("2"))
		// return "2";
		// JSONObject obj = JSONObject.fromObject(str);
		// System.out.println(obj.getString("msg"));
		// }
		return str;
	}

	/**
	 * 查询的 返回 0 成功 -1失败 2处理中，异常 订单查询
	 * 
	 * @param statement
	 *            /订单号
	 * @return String
	 */
	public static int liuLiangDingDanFind(String statement) {
		String tr = "app_id=" + API_USER + "_statement=" + statement + "_"
				+ APP_KEY;
		String md5 = MD5Util.MD5Encode(tr, "utf-8").toLowerCase();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>(); // 设置参数
		formparams.add(new BasicNameValuePair("app_id", API_USER));
		formparams.add(new BasicNameValuePair("statement", statement));
		formparams.add(new BasicNameValuePair("sign", md5));
		String str = post(formparams, "http://api.17llh.cn/package/order_query");
		Log.info("订单查询："+str);
		if (str != null)
		{  
			if (str.equals("-1"))
			return -1;
		else if (str.equals("2"))
			return 2;
		JSONObject obj = JSONObject.fromObject(str);
		 if (obj.getString("code").equals("200"))//订单不存在100018
			{
				obj = JSONObject.fromObject(obj.getString("data"));
//				Log.info("充值套餐名：" + obj.getString("package_name")
//						+ "   充值状态：" + obj.getString("status"));
				if (obj.getString("status").equals("1"))
					return 0;
				else if (obj.getString("status").equals("0"))
					return 2;
				else if (obj.getString("status").equals("-1"))
					return -1;
			}else if(obj.getString("code").equals("100018")){
				return -1;
			}else
				return 2;
		}
		return 2;
	}

	/**
	 * 充值
	 * 
	 * @param mobile
	 *            手机号码
	 * @param pingpai
	 *            品牌商 “0” 表示电信  “1”表示移动   “2” 表示联通
	 * @param streamsize
	 *            流量包大小
	 * @param statement
	 *            单号 自定义订单，小于等于16位
	 * @return int 0 成功 -1失败 2 处理中 流量定购
	 */
	public static int liuLiangDingGou(String mobile, String pingpai,
			String streamsize, String statement) {
		int pid = getPid(Integer.parseInt(pingpai),
				Integer.parseInt(streamsize));
		if (pid == 0)// 没有匹配的id时
			return -1;
		String tr = "app_id=" + API_USER + "_mobile=" + mobile + "_pid=" + pid
				+ "_statement=" + statement + "_" + APP_KEY;
		String md5 = MD5Util.MD5Encode(tr, "utf-8").toLowerCase();
		Log.info(tr);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>(); // 设置参数
		formparams.add(new BasicNameValuePair("app_id", API_USER));
		formparams.add(new BasicNameValuePair("mobile", mobile));
		formparams.add(new BasicNameValuePair("pid", "" + pid));
		formparams.add(new BasicNameValuePair("statement", statement));
		formparams.add(new BasicNameValuePair("sign", md5));
		String str = post(formparams, "http://api.17llh.cn/package/dinggou");
		Log.info("提交的订单状态：" + str);
		if (str != null) {
			if (str.equals("-1"))
				return -1;
			else if (str.equals("2"))
				return 2;
			JSONObject obj = JSONObject.fromObject(str);
			if (obj.getInt("code") == 100015) {
				obj = JSONObject.fromObject(obj.getString("data"));
				if (obj.getString("status").equals("1"))
					return 0;
			}
		}
		return -1;
	}

	/**
	 * 
	 * @param params
	 * @param url
	 * @return String
	 */
	public static String post(List<NameValuePair> params, String url) {
		CloseableHttpClient client=HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		UrlEncodedFormEntity uefEntity = null;
		CloseableHttpResponse res = null;
		try {
			uefEntity = new UrlEncodedFormEntity(params, "UTF-8");
			post.setEntity(uefEntity);
			Log.info("正在连接..." + url);
			res = client.execute(post);
			Log.info("连接成功");
			HttpEntity entity = res.getEntity();
			int statusCode = res.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK)
				if (entity != null) {
					InputStream instreams = null;
					String str = null;
					instreams = entity.getContent();
					str = convertStreamToString(instreams);
					//Object obj=JSONValue.parse(str);//未提交json_simple.jar
					//return obj.toString();
					str = new String(str.getBytes(), "UTF-8");
					return str;
				}
		} catch (ClientProtocolException e) {
			Log.error(e);
			return "2";
		} catch (IOException e) {
			Log.error(e);
			return "2";
		} finally {
			try{
			post.abort();
			res.close();
			client.close();
			}catch(IOException e){
				Log.error("关闭流量汇连接异常"+e.toString());
			}
		}
		return null;
	}

	/**
	 * 从inputstream中获取字符串
	 * 
	 * @param is
	 * @return String
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 根据品牌商和流量包大小返回以有的对应id
	 * 
	 * @param pingpai 品牌商
	 *            “0” 表示电信 “1”表示移动 “2” 表示联通
	 * @param streamsize
	 *            流量包大小
	 * @return int
	 */
	public static int getPid(int pingpai, int streamsize) {
		int temp = 0;
		if (pingpai == 1)
			switch (streamsize) {
			case 10:
				temp = 1;
				break;
			case 30:
				temp = 2;
				break;
			case 70:
				temp = 3;
				break;
			case 150:
				temp = 4;
				break;
			case 500:
				temp = 5;
				break;
			case 1024:
				temp = 6;
				break;
			case 2048:
				temp = 7;
				break;
			case 3072:
				temp = 8;
				break;
			case 4096:
				temp = 9;
				break;
			case 6144:
				temp = 10;
				break;
			case 11264:
				temp = 11;
				break;
			}
		else if (pingpai == 0)
			switch (streamsize) {
			case 5:
				temp = 14;
				break;
			case 10:
				temp = 15;
				break;
			case 30:
				temp = 16;
				break;
			case 50:
				temp = 17;
				break;
			case 100:
				temp = 18;
				break;
			case 200:
				temp = 19;
				break;
			case 500:
				temp = 20;
				break;
			case 1024:
				temp = 21;
				break;
			}
		else if (pingpai == 2)
			switch (streamsize) {
			case 20:
				temp = 26;
				break;
			}
		return temp;
	}

}
