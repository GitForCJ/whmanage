package com.wlt.webm.business.bean.dhst;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;

public class FlowsService {

	static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	static HttpClient client = new HttpClient(connectionManager);
	static{
		client.getHttpConnectionManager().getParams().setConnectionTimeout(60*1000);//链接时间 60秒，单位是毫秒
		client.getHttpConnectionManager().getParams().setSoTimeout(60*1000);//读取时间 20秒，单位是毫秒
	}
	
	/**
	 * 充值请求url
	 */
	private static final String CZ_URL="http://if.dahanbank.cn/FCOrderServlet";
	/**
	 * 订单查询结果
	 */
	private static final String Query_URL="http://if.dahanbank.cn/FCCheckOrderExistedServlet";
	/**
	 * 账号
	 */
	private static final String account="AdminWangh";
	/**
	 * 密码
	 */
	private static final String CZ_PWD=com.wlt.webm.util.MD5.encode("test1859");
	
	/**
	 * 大汉三通流量充值订单
	 * @param phoneNum 充值号码
	 * @param mz 500M  则填 500
	 * @param clientOrderId 订单号
	 * @return String  0 成功  -1失败  2处理中，异常
	 */
	public static String CZ_Orders(String phoneNum,String mz,String clientOrderId){
		long timestamp=System.currentTimeMillis();
		String send_json="{\"timestamp\":\""+timestamp+"\",\"account\":\""+account+"\",\"mobiles\":\""+phoneNum+"\",\"sign\":\""+
		com.wlt.webm.util.MD5.encode(account+CZ_PWD+timestamp+(phoneNum.length()>=32?phoneNum.substring(0,32):phoneNum))
		+"\",\"packageSize\":\""+
		(mz.indexOf("M")!=-1?mz.replace("M",""):mz)
		+"\",\"msgTemplateId\":\"8a28e8c25190582d015194439aae0910\",\"clientOrderId\":\""+clientOrderId+"\"}";
		
		Log.info("大汉三通流量订购,订单号:"+clientOrderId+",,请求url:"+send_json);
		
		PostMethod postMt=new PostMethod(CZ_URL);
		try {
			byte[] bytedata=send_json.getBytes("UTF-8");
			RequestEntity en=new ByteArrayRequestEntity(bytedata);
			postMt.setRequestEntity(en);
			postMt.setRequestHeader("Content-Type","text/json;charset=UTF-8");
			int st=client.executeMethod(postMt);
			Log.info("大汉三通流量订购,订单号:"+clientOrderId+",,http响应状态:"+st);
			if(st==200){
				String rs=postMt.getResponseBodyAsString();
				Log.info("大汉三通流量订购,订单号:"+clientOrderId+",,响应json内容:"+rs);
				CZResultJsonBean bean=JSON.parseObject(rs,CZResultJsonBean.class);
				if(bean==null){
					Log.info("大汉三通流量订购,订单号:"+clientOrderId+",,解析json,错误");
					return "2";
				}
				if("00".equals(bean.getResultCode())){
					Log.info("大汉三通流量订购,订单号:"+clientOrderId+",,订单提交成功,return 0");
					return "0";
				}else{
					Log.info("大汉三通流量订购,订单号:"+clientOrderId+",,订单提交失败,return -1");
					return "-1";
				}
			}
		} catch (Exception e) {
			Log.error("大汉三通流量订购,订单号:"+clientOrderId+",,系统异常,ex:"+e);
			return QueryOrders(clientOrderId,phoneNum);
		}finally {
			postMt.releaseConnection();
		}
		Log.info("大汉三通流量订购,订单号:"+clientOrderId+",,未知提交状态,return 2");
		return "2";
	}
	
	/**
	 * 订单查询接口
	 * @param clientOrderId
	 * @param phoneNum
	 * @return String 0 成功  -1失败  2处理中，异常
	 */
	public static String QueryOrders(String clientOrderId,String phoneNum){
		StringBuffer buf=new StringBuffer(Query_URL);
		buf.append("?");
		buf.append("account="+account);
		buf.append("&sign="+com.wlt.webm.util.MD5.encode(account+CZ_PWD));
		buf.append("&clientOrderId="+clientOrderId);
		buf.append("&mobile="+phoneNum);
		
		Log.info("大汉三通流量订单查询,订单号:"+clientOrderId+",,请求url:"+buf.toString());
		
		GetMethod method=new GetMethod(buf.toString());
		try {
			int http_state=client.executeMethod(method);
			Log.info("大汉三通流量订单查询,订单号:"+clientOrderId+",,http响应状态:"+http_state);
			if(http_state==200){
				String result=method.getResponseBodyAsString();
				Log.info("大汉三通流量订单查询,订单号:"+clientOrderId+",,响应内容:"+result);
				QueryOrderStateBean beans = JSON.parseObject(result, QueryOrderStateBean.class); 
				if(beans==null){
					Log.info("大汉三通流量订单查询,订单号:"+clientOrderId+",,json to bean为空");
					return "2";
				}
				if("0000".equals(beans.getResultCode().trim())){
					Log.info("大汉三通流量订单查询,订单号:"+clientOrderId+",订单不存在,,return -1,订单失败");
					return "-1";
				}
				if("1111".equals(beans.getResultCode().trim())){
					String msg="未知";
					if("0".equals(beans.getResultMsg().trim())){
						msg="成功";
					}else if("1".equals(beans.getResultMsg().trim())){
						msg="充值中";
					}else if("2".equals(beans.getResultMsg().trim()) || "3".equals(beans.getResultMsg().trim())){
						msg="充值失败";
					}
					Log.info("大汉三通流量订单查询,订单号:"+clientOrderId+",,订单存在,订单充值状态:"+msg);
					return "0";
				}
			}
		}catch (Exception e) {
			Log.error("大汉三通流量订单查询,订单号:"+clientOrderId+",,系统异常,ex:"+e);
		}
		Log.info("大汉三通流量订单查询,订单号:"+clientOrderId+",,订单处理中,,");
		return "2";
	}
	
	
	
	public static void main(String[] args) throws InterruptedException {
//		String s="123456789"+(int)(Math.random()*10000)+"";
//		CZ_Orders("18824588427","10",s);
//		System.out.println();
////		Thread.sleep(10*1000);
//		QueryOrders("1234567893058","18824588427");
		
	}
}
