package com.wlt.webm.business.bean.unicom3gquery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.sf.json.test.JSONAssert;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.text.StrSubstitutor;

import whmessgae.TradeMsg;

import bsh.This;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bonc.wo_key.WoMd5;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.db.DBService;
import com.wlt.webm.ten.service.MD5Util;
import com.wlt.webm.util.Tools;

/**
 * 联通流量充值
 * 
 * @author 1989
 * 
 */
public class CopyOfHttpFillOP {
    public static String FLOw1="21";//省公司流量 需要同步产品
    public static String FLOw2="23";//北分联通
    public static String FLOW3="25";//思空移动流量
    public static String FLOW4="26";//思空联通流量
    public static String FLOW5="27";//思空电信流量
    public static String FLOW6="29";//北京移动流量
    
	public static String ACTIVITYID = "1278";//"2808";// 全国产品目录 对应 21 省公司流量
	public static String ZDY="1234";//产品目录，随意定义
//	public static String SHANGHAI = "2849";// 上海
//	public static String HEILONGJIAN = "2848";// 黑龙江
	
	public static String BEIFENUNICOM="3366";//北分联通产品目录

	public static String SIGNKEY ="womail201409231504"; //"test123";// 密钥
	public static String QUERYURL = "http://192.168.1.61:8080/wh/apps.do?method=getPhoneProduct&";// 产品过滤rl
	public static String FILLURL = "http://1e.wo.com.cn/ewo/server/product/backOrderInterfaceNew.jsonp?";// 产品订购
	public static String RESULTURL = "http://1e.wo.com.cn/ewo/server/product/queryBackOrderResultNew.jsonp?";// 产品订购结果查询

	static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	static HttpClient client = new HttpClient(connectionManager);
	static HttpClient client1 = new HttpClient(connectionManager);
	static HttpClient client2 = new HttpClient(connectionManager);
	static HttpClient client3 = new HttpClient(connectionManager);
	static HttpClient client4 = new HttpClient(connectionManager);
	static HttpClient client5 = new HttpClient(connectionManager);
	static HttpClient client6 = new HttpClient(connectionManager);
	public static Map<String, String> phoneAreas = new HashMap<String, String>();// 省份对应编码  宽带公司--》本地
	public static Map<String, String> phoneAreas1 = new HashMap<String, String>();// 省份对应编码  本地--》宽带公司
	static {
		client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		client.getHttpConnectionManager().getParams().setSoTimeout(5000);
		
		client1.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		client1.getHttpConnectionManager().getParams().setSoTimeout(5000);
		
		client2.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		client2.getHttpConnectionManager().getParams().setSoTimeout(5000);
		
		client3.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		client3.getHttpConnectionManager().getParams().setSoTimeout(5000);
		
		client4.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		client4.getHttpConnectionManager().getParams().setSoTimeout(5000);
		
		client5.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		client5.getHttpConnectionManager().getParams().setSoTimeout(5000);
		
		client6.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		client6.getHttpConnectionManager().getParams().setSoTimeout(5000);
		
		phoneAreas.put("71", "2");
		phoneAreas.put("19", "46");
		phoneAreas.put("51", "35");
		phoneAreas.put("11", "43");
		phoneAreas.put("13", "44");
		phoneAreas.put("18", "45");
		phoneAreas.put("10", "48");
		phoneAreas.put("91", "49");
		phoneAreas.put("90", "50");
		phoneAreas.put("97", "51");
		phoneAreas.put("31", "52");
		phoneAreas.put("34", "53");
		phoneAreas.put("36", "54");
		phoneAreas.put("30", "55");
		phoneAreas.put("38", "56");
		phoneAreas.put("75", "57");
		phoneAreas.put("17", "58");
		phoneAreas.put("76", "59");
		phoneAreas.put("74", "60");
		phoneAreas.put("59", "61");
		phoneAreas.put("50", "62");
		phoneAreas.put("83", "63");
		phoneAreas.put("81", "64");
		phoneAreas.put("85", "65");
		phoneAreas.put("86", "66");
		phoneAreas.put("79", "67");
		phoneAreas.put("84", "68");
		phoneAreas.put("87", "69");
		phoneAreas.put("70", "70");
		phoneAreas.put("88", "71");
		phoneAreas.put("89", "72");
		//省份对应编码  本地--》宽带公司
		phoneAreas1.put("2","71");
		phoneAreas1.put("46","19");
		phoneAreas1.put("35","51");
		phoneAreas1.put("43","11");
		phoneAreas1.put("44","13");
		phoneAreas1.put("45","18");
		phoneAreas1.put("48","10");
		phoneAreas1.put("49","91");
		phoneAreas1.put("50","90");
		phoneAreas1.put("51","97");
		phoneAreas1.put("52","31");
		phoneAreas1.put("53","34");
		phoneAreas1.put("54","36");
		phoneAreas1.put("55","30");
		phoneAreas1.put("56","38");
		phoneAreas1.put("57","75");
		phoneAreas1.put("58","17");
		phoneAreas1.put("59","76");
		phoneAreas1.put("60","74");
		phoneAreas1.put("61","59");
		phoneAreas1.put("62","50");
		phoneAreas1.put("63","83");
		phoneAreas1.put("64","81");
		phoneAreas1.put("65","85");
		phoneAreas1.put("66","86");
		phoneAreas1.put("67","79");
		phoneAreas1.put("68","84");
		phoneAreas1.put("69","87");
		phoneAreas1.put("70","70");
		phoneAreas1.put("71","88");
		phoneAreas1.put("72","89");
	}

	/**
	 * 
	 * @param url
	 *            问号结尾
	 * @param data
	 *            参数
	 * @param method
	 *            GET或者POST
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String OP(String url, HashMap data, String method) {
		Set<String> set = data.keySet();
		for (String key : set) {
			url += (key + "=" + data.get(key) + "&");
		}
		url = url.substring(0, url.length() - 1);
		Log.info("发起请求:"+url);
		HttpMethod obj = null;
		if ("GET".equals(method)) {
			obj = new GetMethod(url);
		} else {
			obj = new PostMethod(url);
		}
		//client.getHttpConnectionManager().getParams().setConnectionTimeout(20*
		// 1000);
		// client.getHttpConnectionManager().getParams().setSoTimeout(60*1000);
		DBService db =null;
		try {
			int status = getRandomDigit().executeMethod(obj);
			if (200 == status) {
				String result = convertStreamToString(obj
						.getResponseBodyAsStream());
				Log.info("产品查询返回结果:" + result);
				if (null == result) {
					return null;
				} else {
					FillProductInfo info = JSON.parseObject(result.toLowerCase(),
							FillProductInfo.class);
					if (!info.getStatus().equals("0") || null == info.getData()
							|| null == info.getData().getList()
							|| info.getData().getList().size() == 0) {// 产品信息为空
						return result;
					} else {//先入库防止充值出现虚假面额
						return result;
					}
				}
			}
		} catch (Exception e) {
           Log.error("流量产品查询异常"+e.toString());
           return "{\"desc\":\"省代服务繁忙\",\"status\":\"8888\"}";
		} finally {
			obj.releaseConnection();
		}
		return null;
	}

	/**
	 * 输入流转成字符串
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
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

	/**
	 * 根据号码 省份id 过滤产品
	 * 
	 * @param phone
	 * @param areaId
	 * @return FillProductInfo
	 */
	public static FillProductInfo filterProduct(String phone, String areaId,
			String ACTIVITYID) {
		FillProductInfo info = null;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("activityId", ACTIVITYID);
		map.put("phoneNum", "");
		map.put("areaId", areaId);
		String rs = OP(QUERYURL, map, "GET");
		if (null != rs) {
			info = JSON.parseObject(rs.toLowerCase(), FillProductInfo.class);
		}
		return info;
	}

	/**
	 * 根据号码 省份id 过滤产品
	 * @param phone
	 * @param areaId  号码省份id
	 * @param areacode   0电信  1移动  2联通
	 * @return FillProductInfo
	 */
	public static String filterProductJSON(String phone, String areaId,String areacode)  {
		List<String[]> arrs=BiProd.getFaceBytype(areacode); //[0]接口id   [1]多少兆
		DBService db=null;
		try{
			String aa="";
			String ff="";
			for(String[] str:arrs){
				if(!str[0].equals(FLOw1)){
					aa=aa+str[1]+",";
				}else{
					ff=ff+str[1]+"mb";
				}
			}
			FillProductInfo info=new FillProductInfo();
			ArrayList<FillProductDetail> pList=new ArrayList<FillProductDetail>();
			
			if(!"".equals(aa)){
				db=new DBService();
				String sql="select name,price from wht_flowprice where type="+areacode+" and name in("+aa.substring(0,aa.length()-1)+")";
				List<String[]> ar=db.getList(sql);
				for(String[] s:ar){
					FillProductDetail d=new FillProductDetail();
					d.setProduct_id(ZDY);
					d.setProduct_name(s[0]+"mb");
					d.setProduct_price(s[1]+"元");
					d.setPrice_num((int)Float.parseFloat(s[1]));
					d.setPro_num(1);
					pList.add(d);
				}
			}
			
			if(!"".equals(ff)){
				FillProductInfo result=filterProduct(phone, areaId, ACTIVITYID);
				if(result!=null && result.getData()!=null && result.getData().getList()!=null && result.getData().getList().size()>0){
					List<FillProductDetail> fList=result.getData().getList();
					for(FillProductDetail f:fList){
						if(ff.indexOf(f.getProduct_name())!=-1){
							pList.add(f);
						}
					}
				}
			}
			if(pList.size()>0){
				ProList p=new ProList();
				p.setList(pList);
				info.setStatus("0");
				info.setDesc("数据读取成功");
				info.setData(p);
				return JSON.toJSONString(info);
			}else{
				return "{\"desc\":\"接口查询超时,请重新获取\",\"status\":\"3\"}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"desc\":\"系统异常\",\"status\":\"10\"}";
		}finally{
			if(null!=db){
				db.close();
				db=null;
			}
		}
	}

	/**
	 * 联通流量充值
	 * 
	 * @param orderNo
	 * @param productId
	 * @param phone
	 * @param realPrice
	 *            分
	 * @param aCTIVITYID
	 * @param userNo
	 * @param login
	 * @param areaCode
	 * @param phonePid
	 * @param userPid
	 * @param parentID
	 * @param groups   组
	 * @param interfaceID
	 * @param flag
	 * @param name            
	 * @return
	 */
	public static HashMap<String, String> fillFlow(String orderNo, String productId,
			String phone, int realPrice, String aCTIVITYID, String userNo,
			String login, String areaCode, String phonePid, String userPid,
			String parentID, String groups,String interfaceID,String flag,String name) {
		TradeMsg reveMess = new TradeMsg();
		reveMess.setSeqNo(orderNo);
		HashMap<String, String> content = new HashMap<String, String>();
		content.put("phoneNum", phone);
		content.put("userNo", userNo);
		content.put("parentID", parentID);
		content.put("isline", flag);// 是否直营标示 0标示非直营 1标示直营
		content.put("userPid", userPid);
		content.put("phonePid", phonePid);
		content.put("areaCode", areaCode);
		content.put("phoneType", "2");
		content.put("tradeFee", realPrice * 10 + "");
		content.put("interfaceID", interfaceID);
		content.put("login", login);
		content.put("time", Tools.getNow3());
		content.put("termType", "3");
		content.put("productId", productId);
		content.put("realPrice", realPrice + "");
		content.put("aCTIVITYID", aCTIVITYID);
		content.put("groups", groups);
		content.put("name", name);
		reveMess.setContent(content);
		BiProd bp = new BiProd();
		int n = bp.yiKaHuiService(reveMess, null, orderNo, "10001", orderNo);
		String str=null;
		if (n == -5) {
			str= "{\"desc\":\"余额不足\",\"status\":\"2030\"}";
		} else if (n == 0) {
			str ="{\"desc\":\"订购成功\",\"status\":\"0\"}";
		} else if (n == -1 || n == 1) {
			str= "{\"desc\":\"订购失败\",\"status\":\"2\"}";
		}else if (n == 8) {
			str= "{\"desc\":\"虚假面额\",\"status\":\"9103\"}";
		} else if(n==3){
			str= "{\"desc\":\"接口异常\",\"status\":\"8\"}";
		}else{
			str= "{\"desc\":\"订购中\",\"status\":\"1\"}";
		}
		content.clear();
		content.put("code",n+"");
		content.put("result",str);
		return content;
	}

	/**
	 * 联通流量订购产品
	 * 
	 * @param orderNo
	 * @param productId
	 * @param phone
	 * @param realPrice
	 * @return 0成功 -1失败 2异常 1未知状态
	 */
	public static int fillProduct(String orderNo, String productId,
			String phone, long realPrice, String aCTIVITYID) {
		int a = 1;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("activityId", aCTIVITYID);
		map.put("phoneNum", phone);
		map.put("orderNo", orderNo);
		map.put("productId", productId);
		map.put("realPrice", realPrice);
		String sign = WoMd5.encode(SIGNKEY + orderNo + phone + productId
				+ aCTIVITYID + realPrice);
		map.put("token", sign);
		String rs1 = OP(FILLURL, map, "GET");
		FillRes rs = null;
		if (null != rs1) {
			rs = JSON.parseObject(rs1.toLowerCase(), FillRes.class);
			if (null != rs) {
				a = Integer.parseInt(rs.getStatus());
				if (a == 0 || a == 1 || a == 2) {
					String b = rs.getData().getCode();
					if ("0".equals(b)) {
						a = 0;
					} else {
						a = -1;
					}
				} else if (a == 8) {
					a = 2;// 异常
				} else {
					a = -1;// 失败
				}
			}
		}
		return a;
	}

	/**
	 * 订购结果查询
	 * 
	 * @param orderNo
	 * @return 0：订购成功（充值成功） 1：订购失败（充值失败） 2：查询失败，无该订单（失败，未充值） 3：订购中（充值中）
	 *         -1：接口调用异常（系统异常，联系管理员解决）
	 */
	@SuppressWarnings("unchecked")
	public static int queryResult(String orderNo) {
		int a = -1;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("orderNo", orderNo);
		String rs = OP(RESULTURL, map, "GET");
		if (null != rs) {
			HashMap<String, String> result = JSON
					.parseObject(rs, HashMap.class);
			a = Integer.parseInt(result.get("status"));
		}
		return a;
	}

	/**
	 * 订购结果查询JSON
	 * 
	 * @param orderNo
	 * @return json
	 */
	@SuppressWarnings("unchecked")
	public static String queryResultJSON(String orderNo) {
		String rs = null;
//		int n=BiProd.getFlowInterface(2);
//		if(n==21){
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("orderNo", orderNo);
//		rs = OP(RESULTURL, map, "GET");
//		}else{
//			rs="{\"desc\":\"查询失败,请联系客服\",\"status\":\"-1\"}";
//		}
		return rs;
	}
	
	/**
	 * 
	 * @param url
	 * @param data
	 * @param method
	 * @throws IOException 
	 * @throws HttpException 
	 */
	@SuppressWarnings("unchecked")
	public static void OPReturn(String url, HashMap data, String method) throws HttpException, IOException {
		Set<String> set = data.keySet();
		for (String key : set) {
			url += (key + "=" + data.get(key) + "&");
		}
		url = url.substring(0, url.length() - 1);
		HttpMethod obj = null;
		if ("GET".equals(method)) {
			obj = new GetMethod(url);
		} else {
			obj = new PostMethod(url);
		}
		int status=client.executeMethod(obj);
		if (200 == status) {
			String result = obj.getResponseBodyAsString();
		}
	}
	
	public static HttpClient getRandomDigit() {
		Random r = new Random();
		int n = r.nextInt(100);
		if (n >90) {
			System.out.println(0+"==========");
			return client;
		} else if (n >80) {
			System.out.println(1+"==========");
			return client1;
		} else if (n >70) {
			System.out.println(2+"==========");
			return client2;
		} else if (n >60) {
			System.out.println(3+"==========");
			return client3;
		} else if (n >50) {
			System.out.println(4+"==========");
			return client4;
		} else if (n >40) {
			System.out.println(5+"==========");
			return client5;
		}else {
			System.out.println(6+"==========");
			return client6;
		}
	}

	public static void main(String[] args) {
		// 调用充值查询
		for(int i=0;i<10;i++){
		 FillProductInfo info=filterProduct("18682033916","51","1278");
		 List<FillProductDetail> list=info.getData().getList();
		}
//		 for(FillProductDetail a:list){
//		 System.out.println(a.getProduct_name()+"  "+a.getProduct_id()+"  "+a.
//		 getProduct_price());
//		 }
		// 调用充值
		/*
		String orderno = new SimpleDateFormat("yyyyMMddHHmmssSSS")
				.format(new Date())
				+ (int) (Math.random() * (1000 - 100) + 100);
		System.out.println(orderno);
		// int map=fillProduct(orderno,"51_000501X","18682033916",6);//传钱无意义?
		// System.out.println("充值结果:"+map);
		System.out
				.println(MD5Util
						.MD5Encode(
								"201505131037080661990000000201236TWX778T8MV9F937GTQVKBB87VT2FY",
								"UTF-8"));
		// System.out.println(queryResult("111111111"));
		 */

	}

}
